/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString().replaceAll("-", "");
		return str;
	}
}