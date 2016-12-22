/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfPathUtil {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfPathUtil.class);
	private static String confPath;

	public static String getConfPath() {
		return confPath;
	}

	public static void main(String[] args) {
	}

	static {
		URL url = ConfPathUtil.class.getResource("/");

		if (url != null) {
			confPath = url.getPath();

			confPath = UrlUtil.urlDecode(confPath);

			LOGGER.info(
					"通过 【ConfPathUtil.class.getResource(\"/\").getPath()】获取的confPath为【{}】 ",
					new Object[] { confPath });
		} else {
			confPath = System.getProperty("file.separator")
					+ System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "conf"
					+ System.getProperty("file.separator");
			LOGGER.info(
					"通过【System.getProperty(\"user.dir\")+\\+conf】获取的confPath为【{}】 ",
					new Object[] { confPath });
		}
	}
}