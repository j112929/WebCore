/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.dao.impl;

import com.appcore.dao.MongodbBaseDao;
import com.appcore.page.Page;
import com.appcore.util.ReflectionUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract class MongodbBaseDaoImpl<T>
  implements MongodbBaseDao<T>
{

  @Autowired
  protected MongoTemplate mongoTemplate;

  public List<T> find(Query query)
  {
    return this.mongoTemplate.find(query, getEntityClass());
  }

  public T findOne(Query query)
  {
    return this.mongoTemplate.findOne(query, getEntityClass());
  }

  public void update(Query query, Update update)
  {
    this.mongoTemplate.findAndModify(query, update, getEntityClass());
  }

  public T save(T entity)
  {
    this.mongoTemplate.insert(entity);
    return entity;
  }

  public T findById(String id)
  {
    return this.mongoTemplate.findById(id, getEntityClass());
  }

  public T findById(String id, String collectionName)
  {
    return this.mongoTemplate.findById(id, getEntityClass(), collectionName);
  }

  public Page<T> findPage(Page<T> page, Query query, int currentPage, int pageSize)
  {
    long count = count(query);
    page.setCount(count);
    page.setPageSize(pageSize);
    query.skip((currentPage - 1) * pageSize).limit(pageSize);
    List list = find(query);
    page.setData(list);
    return page;
  }

  public long count(Query query)
  {
    return this.mongoTemplate.count(query, getEntityClass());
  }

  private Class<T> getEntityClass()
  {
    return ReflectionUtils.getSuperClassGenricType(super.getClass());
  }
}