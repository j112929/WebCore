/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.dao;

import com.appcore.page.Page;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract interface BaseDao<T>
{
  public abstract T getById(Serializable paramSerializable);

  public abstract long getCount(String paramString, Object[] paramArrayOfObject);

  public abstract void deleteById(Serializable paramSerializable);

  public abstract void deleteByIds(String paramString1, String paramString2);

  public abstract void deleteNotTrueByIds(String paramString1, String paramString2);

  public abstract List<T> find(String paramString);

  public abstract List<T> find(String paramString, Object paramObject);

  public abstract List<T> find(String paramString, Object[] paramArrayOfObject);

  public abstract Page<T> findByPage(int paramInt1, int paramInt2, String paramString1, String paramString2, Object[] paramArrayOfObject);

  public abstract Page<Map<String, Object>> findByPage(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject);

  public abstract void asyncSave(T paramT);

  public abstract void asyncSaveAll(Collection<T> paramCollection);

  public abstract void asyncUpdate(T paramT);

  public abstract void asyncUpdateAll(Collection<T> paramCollection);

  public abstract void asyncDelete(T paramT);

  public abstract void asyncDeleteAll(Collection<T> paramCollection);

  @Deprecated
  public abstract void asyncSaveOrUpdate(T paramT);

  @Deprecated
  public abstract void asyncSaveOrUpdateAll(Collection<T> paramCollection);

  public abstract void save(T paramT);

  public abstract void saveAll(Collection<T> paramCollection);

  public abstract void update(T paramT);

  public abstract void updateAll(Collection<T> paramCollection);

  public abstract void delete(T paramT);

  public abstract void deleteAll(Collection<T> paramCollection);

  @Deprecated
  public abstract void saveOrUpdate(T paramT);

  @Deprecated
  public abstract void saveOrUpdateAll(Collection<T> paramCollection);

  public abstract String dbThreadName();
}