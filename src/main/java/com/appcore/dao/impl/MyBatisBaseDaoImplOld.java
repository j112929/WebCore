/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.dao.impl;

import com.appcore.dao.MyBatisBaseDaoOld;
import com.appcore.page.Page;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MyBatisBaseDaoImplOld
  implements MyBatisBaseDaoOld
{

  @Autowired
  protected SqlSessionTemplate sqlSessionTemplate;

  public int insert(String statement, Object parameter)
  {
    return this.sqlSessionTemplate.insert(getPrefix() + statement, parameter);
  }

  public int update(String statement, Object parameter) {
    return this.sqlSessionTemplate.update(getPrefix() + statement, parameter);
  }

  public int delete(String statement, Object parameter) {
    return this.sqlSessionTemplate.delete(getPrefix() + statement, parameter);
  }

  public <T> T selectOne(String statement, Object parameter) {
    return this.sqlSessionTemplate.selectOne(getPrefix() + statement, parameter);
  }

  public <T> List<T> selectList(String statement, Object parameter) {
    return this.sqlSessionTemplate.selectList(getPrefix() + statement, parameter);
  }

  public <T, K, V> Page<T> page(String pageStatement, Map<K, V> parameter, int current, int pageSize)
  {
    PageBounds pageBounds = null;
    if (parameter == null) {
      parameter = new HashMap();
    }
    if (parameter.get("sort") == null)
      pageBounds = new PageBounds(current, pageSize);
    else {
      pageBounds = new PageBounds(current, pageSize, Order.formString(parameter.get("sort").toString() + "." + parameter.get("order").toString()));
    }

    PageList result = (PageList)this.sqlSessionTemplate.selectList(getPrefix() + pageStatement, parameter, pageBounds);
    Paginator paginator = result.getPaginator();
    return new Page(current, pageSize, paginator.getTotalCount(), result);
  }

  public abstract String getPrefix();
}