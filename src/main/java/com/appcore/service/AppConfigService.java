/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.service;

import com.appcore.conf.CoreConfig;
import com.appcore.model.conf.AppConfig;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfigService {
	private static final Logger initConfigLog = LoggerFactory
			.getLogger("InitConfigLog");

	public static AppConfig initAppConfig() {
		initConfigLog.info("开始初始化应用服配置...");

		Element e = CoreConfig.getRootElement("app-config.xml");

		AppConfig appConfig = new AppConfig();

		String description = e.getChild("description").getValue();
		String name = e.getChild("server-name").getValue();
		int id = Integer.valueOf(e.getChild("server-id").getValue()).intValue();
		int shutdownPort = Integer.valueOf(
				e.getChild("shutdown-port").getValue()).intValue();
		String secretKey = e.getChild("secret-key").getValue();
		long defaultSessionTimeOut = Long.valueOf(
				e.getChild("default-session-time-out").getValue()).longValue();
		long defaultTokenTimeOut = Long.valueOf(
				e.getChild("default-token-time-out").getValue()).longValue();

		appConfig.setDescription(description);
		appConfig.setName(name);
		appConfig.setId(id);
		appConfig.setShutdownPort(shutdownPort);
		appConfig.setSecretKey(secretKey);
		appConfig.setDefaultSessionTimeOut(defaultSessionTimeOut);
		appConfig.setDefaultTokenTimeOut(defaultTokenTimeOut);

		initConfigLog.info("初始化应用服配置结束，配置信息:【{}】",
				new Object[] { appConfig.toString() });

		return appConfig;
	}
}