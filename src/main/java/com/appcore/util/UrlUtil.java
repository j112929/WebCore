/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class UrlUtil
{
  public static String getSortedUrl(Map parameterMap)
  {
    Map map = new TreeMap(parameterMap);

    Set<String> keySet = map.keySet();
    StringBuilder sb = new StringBuilder();
    for (String key : keySet) {
      sb.append(key).append("=").append((String)map.get(key)).append("&");
    }
    String str = sb.substring(0, sb.length() - 1);
    return str;
  }

  public static Map<String, String> getMapbyUrlParams(String params)
  {
    Map map = new TreeMap();
    String[] ss = params.split("&");
    for (String s : ss) {
      int index = s.indexOf("=");
      if (index > 0) {
        String key = s.substring(0, index);
        String value = s.substring(index + 1);
        map.put(key, value);
      }

    }

    return map;
  }

  public static String urlEncode(String str)
  {
    String result = null;
    try {
      result = URLEncoder.encode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static String urlDecode(String str)
  {
    String result = null;
    try {
      result = URLDecoder.decode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static String getDemainByRequestURL(String requestURL)
  {
    int begin = requestURL.indexOf("://") + 3;
    String requestURL2 = requestURL.substring(begin);
    int end = 0;
    if (requestURL2.contains(":"))
      end = requestURL2.indexOf(":");
    else {
      end = requestURL2.indexOf("/");
    }

    String domain = requestURL2.substring(0, end);

    return domain;
  }

  public static void main(String[] args)
  {
    System.out.println(getDemainByRequestURL("http://web_server_pool/test/"));
  }
}