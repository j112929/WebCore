/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.service;

import com.appcore.security.BmSession;

public abstract interface SessionService {
	public abstract BmSession createSession();

	public abstract void setAttribute(String paramString1, String paramString2,
			Object paramObject);

	public abstract Object getAttribute(String paramString1, String paramString2);

	public abstract void removeAttribute(String paramString1,
			String paramString2);

	public abstract boolean hasSession(String paramString);
}