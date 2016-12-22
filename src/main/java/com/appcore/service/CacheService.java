/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.service;

import java.util.Set;

public abstract interface CacheService
{
  public abstract void put(String paramString, Object paramObject);

  public abstract void put(String paramString, Object paramObject, long paramLong);

  public abstract Object get(String paramString);

  public abstract void remove(String paramString);

  public abstract Set<String> keySet();

  public abstract int size();

  public abstract boolean containsKey(String paramString);
}