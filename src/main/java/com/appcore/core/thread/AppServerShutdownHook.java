/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.core.thread;

import com.appcore.server.IAppServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppServerShutdownHook extends Thread {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppServerShutdownHook.class);
	private IAppServer server;

	public AppServerShutdownHook(IAppServer server) {
		this.server = server;
	}

	public void run() {
		this.server.stop();
	}
}