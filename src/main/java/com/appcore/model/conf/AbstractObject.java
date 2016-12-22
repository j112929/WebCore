/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.model.conf;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

class AbstractObject implements Serializable, Cloneable {
	public static final long serialVersionUID = 1L;

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}