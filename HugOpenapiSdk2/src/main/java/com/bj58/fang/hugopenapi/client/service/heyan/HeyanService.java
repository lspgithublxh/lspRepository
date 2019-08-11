package com.bj58.fang.hugopenapi.client.service.heyan;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bj58.fang.hugopenapi.client.Entity.result.CommonResult;
import com.bj58.fang.hugopenapi.client.consta.UrlConst;
import com.bj58.fang.hugopenapi.client.exception.ESFException;
import com.bj58.fang.hugopenapi.client.provider.TokenProvider;
import com.bj58.fang.hugopenapi.client.service.InitService;
import com.bj58.fang.hugopenapi.client.service.heyan.entity.HeyanEntity;
import com.bj58.fang.hugopenapi.client.util.DefaultHttpPoolingManager;
import com.bj58.fang.hugopenapi.client.util.JSONUtils;
import com.bj58.fang.hugopenapi.client.util.PostUtils;
import com.bj58.fang.hugopenapi.client.util.StringUtils;

public class HeyanService {

	private static final Logger logger = LoggerFactory.getLogger(HeyanService.class);

    private static HeyanService instance = new HeyanService();
    
    public static HeyanService getInstance() {
    	return instance;
    }
    
	public CommonResult heyanESF(HeyanEntity entity) throws ESFException {
		//1.验证
		String rs = "";
		CommonResult cr = null;
		try {
			 Map<String, String> data = PostUtils.getInstance().entityToMapByAnno(entity, null, null);
			 Map<String, String> headers = new HashMap<String, String>();
			 long time = System.currentTimeMillis();
			 rs = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(
					 String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.BRO_ESF_HEYAN,
							 InitService.getInstance().getClientId(),
							 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
			 if(!StringUtils.isEmptyString(rs)) {
				 cr = JSONUtils.getInstance().jsonCommonToJAVACommonResult(rs);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("heyan esf house failed! clientId:" + InitService.getInstance().getClientId());
		}
		return cr;
	
	}
}
