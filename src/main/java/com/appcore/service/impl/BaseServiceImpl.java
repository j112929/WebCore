/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.service.impl;

import com.appcore.dao.BaseDao;
import com.appcore.service.BaseService;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseServiceImpl<T>
  implements BaseService<T>
{
  private static Log log = LogFactory.getLog(BaseServiceImpl.class);

  @Autowired
  public JdbcTemplate jdbcTemplate;

  public abstract BaseDao<T> getDao();

  public T getById(Serializable id)
  {
    Object t = getDao().getById(id);
    return (T)t;
  }

  public void deleteById(Serializable id)
  {
    getDao().deleteById(id);
  }

  public void deleteByIds(String ids, String idColumnName)
  {
    getDao().deleteByIds(ids, idColumnName);
  }

  public void deleteNotTrueByIds(String ids, String idColumnName)
  {
    getDao().deleteNotTrueByIds(ids, idColumnName);
  }

  public void save(T t)
  {
    getDao().save(t);
  }

  public void saveAll(Collection<T> coll) {
    getDao().saveAll(coll);
  }

  public void update(T t) {
    getDao().update(t);
  }

  public void updateAll(Collection<T> coll) {
    getDao().updateAll(coll);
  }

  public void delete(T t) {
    getDao().delete(t);
  }

  public void deleteAll(Collection<T> coll) {
    getDao().deleteAll(coll);
  }

  public void saveOrUpdate(T t) {
    getDao().saveOrUpdate(t);
  }

  public void saveOrUpdateAll(Collection<T> coll) {
    getDao().saveOrUpdateAll(coll);
  }
}