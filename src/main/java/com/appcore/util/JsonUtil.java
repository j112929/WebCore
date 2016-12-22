/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.appcore.model.conf.AppConfig;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil
{
  public static String getJsonString(Object obj)
  {
    String text = JSON.toJSONString(obj);
    return text;
  }

  public static <T> T getObject(String json, Class<T> clazz)
  {
    Object obj = JSON.parseObject(json, clazz);
    return (T)obj;
  }

  public static <T> T getObject(String json, TypeReference<T> type)
  {
    Object obj = JSON.parseObject(json, type, new Feature[0]);
    return (T)obj;
  }

  public static void main(String[] args)
  {
    List list = new ArrayList();
    for (int i = 1; i < 3; ++i) {
      AppConfig conf = new AppConfig();
      conf.setId(1000 * i);
      conf.setName("name-" + i);
      list.add(conf);
    }

    System.out.println("list=" + list);
    String json = getJsonString(list);

    System.out.println("json=" + json);

    List list2 = (List)getObject(json, List.class);
    List list3 = (List)getObject(json, new TypeReference()
    {
    });
    System.out.println("list2=" + list2);
    System.out.println("list3=" + list3);
  }
}