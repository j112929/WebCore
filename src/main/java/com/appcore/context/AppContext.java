/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.context;

import com.appcore.util.Gather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class AppContext
{
  private static final Logger LOGGER = LoggerFactory.getLogger(AppContext.class);

  private static ApplicationContext context = null;

  public static <T> T getBean(String beanName)
  {
    Object obj = context.getBean(beanName);
    return (T) obj;
  }

  public static void setContext(ApplicationContext context) {
    context = context;
  }

  static {
    try {
      new Gather().gather();
    }
    catch (Exception e)
    {
    }
  }
}