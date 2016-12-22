/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.server;

public abstract interface IServer {
	public abstract void init();

	public abstract void start();

	public abstract void stop();
}