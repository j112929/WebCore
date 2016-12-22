/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class HMLIDUtil
{
  private static int c = 0;

  private static int c2 = 0;

  public static final int serverId = getServerId();

  private static final long hightMultiple = (long)Math.pow(10.0D, 14.0D);

  private static final long middleMultiple = (long)Math.pow(10.0D, 5.0D);

  private static final long lowMaxValue = (long)Math.pow(10.0D, 5.0D);
  private static final long cutMillis = 1261440000000L;
  public static final long hight = serverId * hightMultiple;

  private static Object lock = new Object();

  private static long lastTimeSecond = 0L;

  private static int idNum = 0;

  public static long getId()
  {
    synchronized (lock)
    {
      long timeSecond = (int)((System.currentTimeMillis() - 1261440000000L) / 1000L);

      if (timeSecond == lastTimeSecond)
        if (idNum >= lowMaxValue) {
          while (timeSecond == lastTimeSecond) {
            timeSecond = (int)((System.currentTimeMillis() - 1261440000000L) / 1000L);
            System.out.println("当前id超出界限，睡眠100毫秒！c=" + c);
            try {
              Thread.sleep(100L);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          idNum = 0;
        }
      else {
        idNum = 0;
      }

      long middle = timeSecond * middleMultiple;

      long low = c % lowMaxValue;
      if (low == 0L) {
        c = 0;
      }
      c += 1;
      long id = hight + middle + low;

      idNum += 1;
      lastTimeSecond = timeSecond;

      return id;
    }
  }

  private static int getServerId()
  {
    int pfId = 10;
    int zoneId = 1;

    if ((pfId < 10) || (pfId > 99)) {
      throw new RuntimeException("平台id必须在10-99之间，当前pfId【" + pfId + "】");
    }
    if (zoneId > 999) {
      throw new RuntimeException("区号必须在1-999之间，当前zoneId【" + zoneId + "】");
    }

    String zoneIdStr = "00" + zoneId;
    zoneIdStr = zoneIdStr.substring(zoneIdStr.length() - 3);

    int serverId = Integer.valueOf(pfId + zoneIdStr).intValue();

    return serverId;
  }

  public static long getId2()
  {
    synchronized (lock) {
      c2 += 1;
      return ((serverId & 0xFFFF) << 48 | (System.currentTimeMillis() / 1000L & 0xFFFFFFFF) << 16 | c2 & 0xFFFF);
    }
  }

  public static void main(String[] args)
    throws Exception
  {
    Set set = new HashSet();

    long b = System.currentTimeMillis();

    System.out.println(getId());

    int cc = 0;
    for (int i = 0; i < 2000000; ++i) {
      long id = getId();
      Thread.sleep(300L);
      System.out.println(id);
      if (set.contains(Long.valueOf(id))) {
        System.out.println("重复的id!" + id);
        ++cc;
      } else {
        set.add(Long.valueOf(id));
      }
      set.add(Long.valueOf(id));
      if (System.currentTimeMillis() - b > 10000L) {
        break;
      }
    }
    System.out.println("生成的id数量=" + set.size());
    System.out.println("重复的id数量=" + cc);
  }
}