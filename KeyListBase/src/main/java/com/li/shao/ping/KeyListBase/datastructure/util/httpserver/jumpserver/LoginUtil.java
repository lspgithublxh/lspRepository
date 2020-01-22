package com.li.shao.ping.KeyListBase.datastructure.util.httpserver.jumpserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.config.RuntimeBeanNameReference;

import com.li.shao.ping.KeyListBase.datastructure.util.httpserver.runtime.CmdRuntimeUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 跳板机ssh登录；监控远程服务器上的日志
 *
 * @author lishaoping
 * @date 2020年1月21日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.httpserver.jumpserver
 */
@Slf4j
public class LoginUtil {

	public void login(String username, String password, String host, int port) {
		String cmd = "ssh -p " + port + " " + username + "@" + host;
//		cmd = "";
		byte[] data = CmdRuntimeUtil.instance.exec(cmd);
		log.info(new String(data));
	}
	
	public static void main(String[] args) {
		new LoginUtil().login("lishaoping", "12345678", "git.4e.ltd", 2222);
		try {
			Thread.sleep(1000 * 3600);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
