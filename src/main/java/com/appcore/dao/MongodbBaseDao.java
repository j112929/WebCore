/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.dao;

import com.appcore.page.Page;
import java.util.List;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract interface MongodbBaseDao<T>
{
  public abstract List<T> find(Query paramQuery);

  public abstract T findOne(Query paramQuery);

  public abstract void update(Query paramQuery, Update paramUpdate);

  public abstract T save(T paramT);

  public abstract T findById(String paramString);

  public abstract T findById(String paramString1, String paramString2);

  public abstract Page<T> findPage(Page<T> paramPage, Query paramQuery, int paramInt1, int paramInt2);

  public abstract long count(Query paramQuery);
}