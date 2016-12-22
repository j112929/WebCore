/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.dao.impl;

import com.appcore.core.pool.ThreadPoolFactory;
import com.appcore.core.task.DBTask;
import com.appcore.dao.BaseDao;
import com.appcore.page.Page;
import com.appcore.util.StringUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class BaseDaoImpl<T>
  implements BaseDao<T>
{
  private static final Logger LOGGER = LoggerFactory.getLogger(BaseDaoImpl.class);

  @Autowired
  public JdbcTemplate jdbcTemplate;

  @Autowired
  private SessionFactory sessionFactory;
  private Class<T> domainClass;

  public BaseDaoImpl() { this.domainClass = getDomainClass(); }

  public abstract Class<T> getDomainClass();

  protected final Session getCurrentSession() { return this.sessionFactory.getCurrentSession();
  }

  public long getCount(String sql, Object[] oo)
  {
    long count = this.jdbcTemplate.queryForLong(sql, oo);
    return count;
  }

  public T getById(Serializable id)
  {
    T t = (T) getCurrentSession().get(getDomainClass(), id);
    return t;
  }

  public void deleteById(Serializable id)
  {
    Object t = getById(id);
    getCurrentSession().delete(t);
  }

  public void deleteByIds(String ids, String idColumnName)
  {
    StringBuilder sb = new StringBuilder();
    int[] ii = StringUtil.stringToIntArray(ids, ",");
    for (int i = 0; i < ii.length; ++i) {
      if (i != 0) {
        sb.append(",");
      }
      sb.append("?");
    }
    Query query = getCurrentSession().createQuery(new StringBuilder().append("delete ").append(getDomainClass().getName()).append(" where ").append(idColumnName).append(" in (").append(sb.toString()).append(")").toString());
    for (int i = 0; i < ii.length; ++i) {
      query.setInteger(i, ii[i]);
    }
    query.executeUpdate();
  }

  public void deleteNotTrueByIds(String ids, String idColumnName)
  {
    StringBuilder sb = new StringBuilder();
    int[] ii = StringUtil.stringToIntArray(ids, ",");
    for (int i = 0; i < ii.length; ++i) {
      if (i != 0) {
        sb.append(",");
      }
      sb.append("?");
    }
    Query query = getCurrentSession().createQuery(new StringBuilder().append("update ").append(getDomainClass().getName()).append(" set is_del = 1 where ").append(idColumnName).append(" in (").append(sb.toString()).append(")").toString());
    for (int i = 0; i < ii.length; ++i) {
      query.setInteger(i, ii[i]);
    }
    query.executeUpdate();
  }

  public List<T> find(String queryString)
  {
    return find(queryString, (Object[])null);
  }

  public List<T> find(String queryString, Object value) {
    return find(queryString, new Object[] { value });
  }

  public List<T> find(String queryString, Object[] values)
  {
    Query queryObject = getCurrentSession().createQuery(queryString);
    if (values != null) {
      for (int i = 0; i < values.length; ++i)
        queryObject.setParameter(i, values[i]);
    }
    return queryObject.list();
  }

  public Page<T> findByPage(int pageIndex, int pageSize, String fromStr, String orderByStr, Object[] oo)
  {
    String sql1 = new StringBuilder().append("select count(1) from ( select 1 ").append(fromStr).append(" ) t ").toString();

    long rowCount = this.jdbcTemplate.queryForLong(sql1, oo);

    int begin = pageIndex * pageSize;

    String sql2 = new StringBuilder().append("select * ").append(fromStr).append(" ").append(orderByStr).append(" limit ").append(begin).append(",").append(pageSize).toString();

    RowMapper rowMapper = new BeanPropertyRowMapper(getDomainClass());
    List list = this.jdbcTemplate.query(sql2, rowMapper);

    LOGGER.info("sql1--->【{}】", sql1);
    LOGGER.info("sql2--->【{}】", sql2);

    Page page = new Page(pageIndex, pageSize, rowCount, list);

    return page;
  }

  public Page<Map<String, Object>> findByPage(int pageIndex, int pageSize, String selectStr, String fromStr, String orderByStr, Object[] oo)
  {
    String sql1 = new StringBuilder().append("select count(1) from ( select 1 ").append(fromStr).append(" ) t ").toString();
    long rowCount = this.jdbcTemplate.queryForLong(sql1, oo);

    int begin = pageIndex * pageSize;

    String sql2 = new StringBuilder().append(selectStr).append(" ").append(fromStr).append(" ").append(orderByStr).append(" limit ").append(begin).append(",").append(pageSize).toString();

    List list = this.jdbcTemplate.queryForList(sql2, oo);

    LOGGER.info("sql1--->【{}】", sql1);
    LOGGER.info("sql2--->【{}】", sql2);

    Page page = new Page(pageIndex, pageSize, rowCount, list);

    return page;
  }

  public void save(T t)
  {
    getCurrentSession().save(t);
  }

  public void saveAll(Collection<T> coll) {
    for (Iterator i$ = coll.iterator(); i$.hasNext(); ) { Object obj = i$.next();
      getCurrentSession().save(obj);
    }
  }

  public void update(T t) {
    getCurrentSession().update(t);
  }

  public void updateAll(Collection<T> coll) {
    for (Iterator i$ = coll.iterator(); i$.hasNext(); ) { Object obj = i$.next();
      getCurrentSession().update(obj);
    }
  }

  public void delete(T t) {
    getCurrentSession().delete(t);
  }

  public void deleteAll(Collection<T> coll) {
    for (Iterator i$ = coll.iterator(); i$.hasNext(); ) { Object obj = i$.next();
      getCurrentSession().delete(obj);
    }
  }

  public void saveOrUpdate(T t) {
    getCurrentSession().saveOrUpdate(t);
  }

  public void saveOrUpdateAll(Collection<T> coll) {
    for (Iterator i$ = coll.iterator(); i$.hasNext(); ) { Object obj = i$.next();
      getCurrentSession().saveOrUpdate(obj);
    }
  }

  public void asyncSave(T t)
  {
    DBTask task = new DBTask(1, t);
    ThreadPoolFactory.submit(dbThreadName(), task);
  }

  public void asyncSaveAll(Collection<T> coll) {
    DBTask task = new DBTask(1, coll);
    ThreadPoolFactory.submit(dbThreadName(), task);
  }

  public void asyncUpdate(T t) {
    DBTask task = new DBTask(2, t);
    ThreadPoolFactory.submit(dbThreadName(), task);
  }

  public void asyncUpdateAll(Collection<T> coll) {
    DBTask task = new DBTask(2, coll);
    ThreadPoolFactory.submit(dbThreadName(), task);
  }

  public void asyncDelete(T t) {
    DBTask task = new DBTask(3, t);
    ThreadPoolFactory.submit(dbThreadName(), task);
  }

  public void asyncDeleteAll(Collection<T> coll) {
    DBTask task = new DBTask(3, coll);
    ThreadPoolFactory.submit(dbThreadName(), task);
  }

  public void asyncSaveOrUpdate(T t) {
    DBTask task = new DBTask(4, t);
    ThreadPoolFactory.submit(dbThreadName(), task);
  }

  public void asyncSaveOrUpdateAll(Collection<T> coll) {
    DBTask task = new DBTask(4, coll);
    ThreadPoolFactory.submit(dbThreadName(), task);
  }

  public String dbThreadName()
  {
    String threadName = new StringBuilder().append("DB_").append(super.getClass().getSimpleName().replace("DaoImpl", "").toUpperCase()).append("_THREAD").toString();
    return threadName;
  }
}