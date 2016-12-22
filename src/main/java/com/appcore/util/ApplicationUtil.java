/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import com.appcore.context.AppContext;
import com.appcore.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationUtil
{
  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUtil.class);

  public static void setAttribute(String key, Object obj)
  {
    setAttribute(key, obj, 3600000L);
  }

  public static void setAttribute(String key, Object obj, long intervalTime)
  {
    CacheService cacheService = (CacheService)AppContext.getBean("cacheService");

    cacheService.put(key, obj, intervalTime);
  }

  public static <T> T getAttribute(String key)
  {
    CacheService cacheService = (CacheService)AppContext.getBean("cacheService");

    Object object = cacheService.get(key);

    return (T)object;
  }

  public static void removeAttribute(String key)
  {
    CacheService cacheService = (CacheService)AppContext.getBean("cacheService");

    cacheService.remove(key);
  }
}