/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.service;

import com.appcore.page.Page;
import java.util.List;
import java.util.Map;

public abstract interface MybatisBaseService
{
  public abstract <P> int insert(P paramP);

  public abstract <P> int update(P paramP);

  public abstract <P> int delete(P paramP);

  public abstract <T, P> T selectObject(P paramP);

  public abstract <T, P> List<T> selectObjectList(P paramP);

  public abstract <V, P> Map<String, V> selectMap(P paramP);

  public abstract <V, P> List<Map<String, V>> selectMapList(P paramP);

  public abstract <T> Page<T> page(Map<String, Object> paramMap, int paramInt1, int paramInt2);

  public abstract <T> Page<T> page(String paramString1, String paramString2, Map<String, Object> paramMap, int paramInt1, int paramInt2);

  public abstract <P> void asyncInsert(P paramP);

  public abstract <P> void asyncUpdate(P paramP);

  public abstract <P> void asyncDelete(P paramP);
}