/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.dao;

import com.appcore.page.Page;
import java.util.List;
import java.util.Map;

public abstract interface MyBatisBaseDaoOld
{
  public abstract int insert(String paramString, Object paramObject);

  public abstract int update(String paramString, Object paramObject);

  public abstract int delete(String paramString, Object paramObject);

  public abstract <T> T selectOne(String paramString, Object paramObject);

  public abstract <T> List<T> selectList(String paramString, Object paramObject);

  public abstract <T, K, V> Page<T> page(String paramString, Map<K, V> paramMap, int paramInt1, int paramInt2);

  public abstract String getPrefix();
}