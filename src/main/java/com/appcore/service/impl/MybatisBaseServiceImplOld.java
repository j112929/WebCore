/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.service.impl;

import com.appcore.dao.MyBatisBaseDaoOld;
import com.appcore.page.Page;
import com.appcore.service.MybatisBaseServiceOld;
import java.util.List;
import java.util.Map;

public abstract class MybatisBaseServiceImplOld
  implements MybatisBaseServiceOld
{
  public abstract MyBatisBaseDaoOld getDao();

  public <T> int insert(T parameter)
  {
    return getDao().insert("insert", parameter);
  }

  public <T> int update(T parameter)
  {
    return getDao().update("update", parameter);
  }

  public <K, V> int delete(Map<K, V> parameter)
  {
    return getDao().delete("delete", parameter);
  }

  public <K, V, T> T selectObject(Map<K, V> parameter)
  {
    return getDao().selectOne("selectObject", parameter);
  }

  public <K, V, T> List<T> selectObjectList(Map<K, V> parameter)
  {
    return getDao().selectList("selectObjectList", parameter);
  }

  public <K, V, O> Map<K, O> selectMap(Map<K, V> parameter)
  {
    return ((Map)getDao().selectOne("selectMap", parameter));
  }

  public <K, V, O> List<Map<K, O>> selectMapList(Map<K, V> parameter)
  {
    return getDao().selectList("selectMapList", parameter);
  }

  public <K, V, T> Page<T> page(Map<K, V> parameter, int pageIndex, int pageSize)
  {
    return getDao().page("page", parameter, pageIndex, pageSize);
  }
}