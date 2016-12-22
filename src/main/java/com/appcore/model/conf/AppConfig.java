/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.model.conf;

public class AppConfig extends AbstractObject {
	private int id;
	private String name;
	private String description;
	private int shutdownPort;
	private String secretKey;
	private long defaultSessionTimeOut;
	private long defaultTokenTimeOut;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getShutdownPort() {
		return this.shutdownPort;
	}

	public void setShutdownPort(int shutdownPort) {
		this.shutdownPort = shutdownPort;
	}

	public String getSecretKey() {
		return this.secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public long getDefaultSessionTimeOut() {
		return this.defaultSessionTimeOut;
	}

	public void setDefaultSessionTimeOut(long defaultSessionTimeOut) {
		this.defaultSessionTimeOut = defaultSessionTimeOut;
	}

	public long getDefaultTokenTimeOut() {
		return this.defaultTokenTimeOut;
	}

	public void setDefaultTokenTimeOut(long defaultTokenTimeOut) {
		this.defaultTokenTimeOut = defaultTokenTimeOut;
	}
}