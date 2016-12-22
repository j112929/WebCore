/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.server;

public abstract interface IAppServer extends IServer {
	public abstract void initLog4j();

	public abstract void initSpring();

	public abstract void startStopServer();

	public abstract void addShutdownHook();

	public abstract void startTimerServer();
}