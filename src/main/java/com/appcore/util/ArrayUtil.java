/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

public class ArrayUtil {
	public static int getValueByIndex(int[] ii, int index) {
		if (index > ii.length - 1) {
			index = ii.length - 1;
		}
		int value = ii[index];
		return value;
	}
}