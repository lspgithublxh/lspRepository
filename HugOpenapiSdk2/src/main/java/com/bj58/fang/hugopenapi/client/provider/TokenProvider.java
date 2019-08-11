package com.bj58.fang.hugopenapi.client.provider;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.bj58.fang.hugopenapi.client.Entity.pub.ToKen;
import com.bj58.fang.hugopenapi.client.consta.UrlConst;
import com.bj58.fang.hugopenapi.client.exception.TokenGetException;
import com.bj58.fang.hugopenapi.client.service.InitService;
import com.bj58.fang.hugopenapi.client.util.DefaultHttpPoolingManager;
import com.bj58.fang.hugopenapi.client.util.DigestUtils;
import com.bj58.fang.hugopenapi.client.util.StringUtils;

/**
 * 分布式环境:tokenserver
 * 
 * @ClassName:TokenProvider
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月17日
 * @Version V1.0
 * @Package com.bj58.fang.hugopenapi.client.provider
 */
public class TokenProvider {
	
	private static Logger logger = LoggerFactory.getLogger(TokenProvider.class);
	private static Map<String, ToKen> tokenMap = new ConcurrentHashMap<String, ToKen>();
	private static final long LAST_DAY = 1 * 24 * 60 * 60 * 1000;
	private static TokenProvider provider = new TokenProvider();
	
	private ExecutorService executor = Executors.newFixedThreadPool(30);

	public static TokenProvider getInstance() {
		return provider;
	}

	public String getTokenSign(long time) throws TokenGetException {
		String tokenSign = null;
		InitService initS = InitService.getInstance();
		try {
			if(StringUtils.isEmptyString(initS.getClientId()) ||
					StringUtils.isEmptyString(initS.getClientSecret())) {
				logger.error("clientId, clientSecret has not init, please init first by call InitService.getInstance().init(clientId, clientSecret, saveTime);");
				throw new TokenGetException("clientId, clientSecret has not init, please init first by call InitService.getInstance().init(clientId, clientSecret, saveTime);");
			}
			String token = getToken(initS.getClientId(), initS.getClientSecret());
			tokenSign = DigestUtils.getInstance().digest(initS.getClientId() + time + token, "MD5");
			//tokenSign是否一直需要加密---否则可以缓存;;不可以，因为time的实时要求 否则400110错误
		}catch (TokenGetException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("tokenSign get failed! " + initS.getClientId() + " " + initS.getClientSecret());
			throw new TokenGetException("tokenSign get failed! " + initS.getClientId() + " " + initS.getClientSecret());
		}
		return tokenSign;
	}
	
	public String getTokenSign(String clientId, String clientSecret) {
		String tokenSign = null;
		try {
			long now = System.currentTimeMillis();
			String token = getToken(clientId, clientSecret);
			tokenSign = DigestUtils.getInstance().digest(clientId + now + token, "MD5");
			//tokenSign是否一直需要加密---否则可以缓存
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("tokenSign get failed! " + clientId + " " + clientSecret);
		}
		return tokenSign;
	}
	
	private String getToken(String clientId, String clientSecret) throws Exception {
		if(InitService.isTest()) {
			return InitService.getToken();
		}
		String key = clientId + clientSecret;
		ToKen token = tokenMap.get(key);
		long now = System.currentTimeMillis();
		if(null != token) {//test || 
			//1.是否过期
			if(now - token.getDeadline() < LAST_DAY) {//重新获取--刷新 --重新取 test || 
				String data = null;
				if(InitService.getInstance().isDistribute()) {
					data = getByCenterService(clientId, clientSecret);
				}else {
					data = refreshToken(clientId, clientSecret);
				}
				if(StringUtils.isEmptyString(data)) {
					throw new Exception("token refresh failed! " + clientId + " " + clientSecret);
				}else {
					token.setToken(data);
					token.setDeadline(now + InitService.getInstance().getSaveTime());
				}
			}
		}else {
			token = new ToKen(clientId, clientSecret, now + InitService.getInstance().getSaveTime());
			String data = null;
			if(InitService.getInstance().isDistribute()) {
				data = getByCenterService(clientId, clientSecret);
			}else {
				data = getTokenFirst(clientId, clientSecret);
			}
			if(StringUtils.isEmptyString(data)) {
				throw new Exception("token get failed!");
			}else {
				token.setToken(data);
			}
			tokenMap.put(key, token);
		}
		return token.getToken();
		
	}
	
	/**
	 * 本方法同步调用
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月18日
	 * @Package com.bj58.fang.hugopenapi.client.provider
	 * @return String
	 * @throws TokenGetException 
	 */
	private synchronized String getByCenterService(String clientId, String clientSecret) throws TokenGetException {
		Map<String, Integer> ipPortMap = InitService.getInstance().getIpPortMap();
		String data = null;
		int size = ipPortMap.size();
		if(size > 0) {
			List<String> ipL = new ArrayList<String>(ipPortMap.keySet());
			for(int i = 0; i < size; i++) {
				int id = (int) Math.round(Math.random() * (size - 1));
				String ip = ipL.get(id);
				Integer port = ipPortMap.get(ip);
				final String[] tokens = new String[] {null, clientId, clientSecret};
				final Object mainLock = new Object();
				try {
					final Socket socket = new Socket(ip, port);
					executor.execute(new Runnable() {
						@Override
						public void run() {
							InputStream in = null;
							try {
								in = socket.getInputStream();
								DataInputStream din = new DataInputStream(in);
								String token = din.readUTF();
								logger.info(" read ok");
								if(!StringUtils.isEmptyString(token)) {
									JSONObject tokJ = JSONObject.parseObject(token);
									if(200 == tokJ.getIntValue("status")) {
										token = tokJ.getString("data");
										tokens[0] = token;
									}
								}
								din.close();
								synchronized (mainLock) {
									mainLock.notify();
								}
								logger.info("get token from server success!");
							} catch (IOException e) {
								e.printStackTrace();
							}finally {
								System.out.println("read finally");
								try {
									in.close();
									socket.close();
								} catch (IOException e) {
									e.printStackTrace();
									e.printStackTrace();
								}
								synchronized (mainLock) {
									mainLock.notify();
								}
							}
						}
					});
					executor.execute(new Runnable() {
						@Override
						public void run() {
							OutputStream out = null;
							try {
								out = socket.getOutputStream();
								DataOutputStream dout = new DataOutputStream(out);
								dout.writeUTF(String.format("token|%s|%s", tokens[1], tokens[2]));
								dout.flush();
								logger.info("write to tokenServer ok");
							} catch (IOException e) {
								e.printStackTrace();
							}finally {
//								try {
//									out.close();
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
							}
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
				synchronized (mainLock) {
					try {
						mainLock.wait(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				data = tokens[0];
				if(data == null) {
					logger.info("get token from server error!");
				}else {
					break;
				}
			}
		}else {
			logger.info("token server has not config, please init by call: InitService.configDistributeCondition(true, ipPortMap);");
			throw new TokenGetException("token server has not config, please init by call: InitService.configDistributeCondition(true, ipPortMap);");
		}
		return data;
	}

	private String getTokenFirst(String clientId, String clientSecret) {
		String token = null;
		try {
			long timeSign = System.currentTimeMillis();
			String signature = DigestUtils.getInstance().sha1Hex(clientSecret + UrlConst.HOST + timeSign);
		    String url = String.format(UrlConst.GET_TOKEN_URL, clientId, timeSign, signature);
		    String res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(url, 
					new HashMap<String, String>(), new HashMap<String, String>());
			if(!StringUtils.isEmptyString(res)) {
				JSONObject tokenObject = JSONObject.parseObject(res);
				if(tokenObject != null && tokenObject.containsKey("code") && tokenObject.getIntValue("code") == 400102) {
					token = refreshToken(clientId, clientSecret);
				}else if(tokenObject != null){
				    token = tokenObject.getString("access_token");
				}
			    if(StringUtils.isEmptyString(token)) {
			    	logger.error(res);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("token getfirst failed! clientId:%s, clientSecret:%s", clientId, clientSecret));
		}
		return token;
	}
	
	private String refreshToken(String clientId, String clientSecret) {
		String token = null;
		try {
			long timeSign = System.currentTimeMillis();
			InitService.getInstance().setTimeSign(timeSign);
			String signature = DigestUtils.getInstance().sha1Hex(clientSecret + UrlConst.HOST + timeSign);
			String res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.REFRESH_TOKEN_URL, clientId, timeSign, signature), null, null);
			if(!StringUtils.isEmptyString(res)) {
				JSONObject tokenObject = JSONObject.parseObject(res);
			    token = tokenObject.getString("refresh_token");
			    if(StringUtils.isEmptyString(token)) {
			    	logger.error(res);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
}
