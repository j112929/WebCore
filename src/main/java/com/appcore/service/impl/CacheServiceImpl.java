/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.service.impl;

import com.appcore.core.pool.TaskPoolFactory;
import com.appcore.model.CacheObject;
import com.appcore.service.CacheService;
import com.appcore.util.TimeUtil;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("cacheService")
public class CacheServiceImpl
  implements CacheService, Serializable
{
  private static final long serialVersionUID = 1L;
  private static final long INTERVAL_TIME = 1800000L;
  private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

  private static ConcurrentHashMap<String, CacheObject> cache = new ConcurrentHashMap();

  public void put(String key, Object obj)
  {
    put(key, obj, 1800000L);
  }

  public void put(String key, Object obj, long intervalTime)
  {
    if (key == null) {
      LOGGER.error("缓存对象【{}】失败，key不能为null！", new Object[] { obj });
      throw new RuntimeException("缓存对象【{" + obj + "}】失败，key不能为null！");
    }

    if (intervalTime <= 0L) {
      intervalTime = 9223372036854775807L;
    }

    CacheObject cacheObj = new CacheObject();
    cacheObj.setKey(key);
    cacheObj.setObject(obj);
    cacheObj.setIntervalTime(intervalTime);
    cacheObj.setExpire(System.currentTimeMillis() + cacheObj.getIntervalTime());

    cache.put(key, cacheObj);
  }

  public Object get(String key)
  {
    if (key == null) {
      LOGGER.error("获取缓存对象失败，key不能为null！");
      throw new RuntimeException("获取缓存对象失败，key不能为null！");
    }

    CacheObject cacheObj = (CacheObject)cache.get(key);

    if (cacheObj != null) {
      cacheObj.setExpire(System.currentTimeMillis() + 1800000L);
      return cacheObj.getObject();
    }
    return null;
  }

  public void remove(String key)
  {
    if (key == null) {
      LOGGER.error("删除缓存对象失败，key不能为null！");
      throw new RuntimeException("删除缓存对象失败，key不能为null！");
    }

    cache.remove(key);
  }

  public Set<String> keySet()
  {
    return cache.keySet();
  }

  public int size()
  {
    return cache.size();
  }

  public boolean containsKey(String key)
  {
    return cache.containsKey(key);
  }

  static
  {
    CleanCacheTask task = new CleanCacheTask();
    TaskPoolFactory.scheduleAtFixedRate("CleanCacheTask", task, 10L, 10L, TimeUnit.SECONDS);
  }

  private static class CleanCacheTask
    implements Runnable
  {
    public void run()
    {
      for (CacheObject cacheObject : CacheServiceImpl.cache.values())
        if (cacheObject.getExpire() < System.currentTimeMillis()) {
          CacheServiceImpl.cache.remove(cacheObject.getKey());
          CacheServiceImpl.LOGGER.info("删除过期的缓存对象【{}】,过期时间【{}】", new Object[] { cacheObject.getObject(), TimeUtil.getFormatTime(cacheObject.getExpire(), "yyyy-MM-dd HH:mm:ss") });
        }
    }
  }
}