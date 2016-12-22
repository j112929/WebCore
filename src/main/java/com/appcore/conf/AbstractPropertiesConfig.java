/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.conf;

import com.appcore.model.AbstractObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPropertiesConfig extends AbstractObject {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractPropertiesConfig.class);

	public static Properties getProperties(String fileName) {
		LOGGER.info("开始读取文件【{}】...", new Object[] { fileName });

		InputStream is = AbstractPropertiesConfig.class.getClassLoader()
				.getResourceAsStream(fileName);

		Properties properties = new Properties();
		try {
			properties.load(is);
			if (is != null)
				is.close();
		} catch (IOException e) {
			LOGGER.error("读取配置文件【{}】出现异常，异常信息【{}】",
					new Object[] { fileName, e });
		}

		LOGGER.info("读取文件【{}】结束...", new Object[] { fileName });

		return properties;
	}
}