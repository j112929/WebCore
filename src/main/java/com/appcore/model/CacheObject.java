/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.model;

public class CacheObject extends AbstractObject {
	private String key;
	private Object object;
	private long intervalTime;
	private long expire;

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getObject() {
		return this.object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public long getIntervalTime() {
		return this.intervalTime;
	}

	public void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}

	public long getExpire() {
		return this.expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}
}