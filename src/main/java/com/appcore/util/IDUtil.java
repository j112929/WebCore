/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class IDUtil
{
  private static int c = 0;

  private static final long lowMaxValue = (long)Math.pow(10.0D, 3.0D);

  private static final long hightMultiple = lowMaxValue;
  private static final long cutMillis = 1261440000000L;
  private static Object lock = new Object();

  private static long lastTimeSecond = 0L;

  private static int currentSecondIdCount = 0;

  public static long getId()
  {
    synchronized (lock)
    {
      long timeSecond = (int)((System.currentTimeMillis() - 1261440000000L) / 1000L);

      if (timeSecond == lastTimeSecond)
        if (currentSecondIdCount >= lowMaxValue) {
          while (timeSecond == lastTimeSecond) {
            timeSecond = (int)((System.currentTimeMillis() - 1261440000000L) / 1000L);
            System.out.println("当前id超出界限，睡眠100毫秒！c=" + c);
            try {
              Thread.sleep(100L);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          currentSecondIdCount = 0;
        }
      else {
        currentSecondIdCount = 0;
      }

      if (c % lowMaxValue == 0L) {
        c = 0;
      }
      c += 1;

      long hight = timeSecond * hightMultiple;

      long low = c;

      long id = hight + low;

      currentSecondIdCount += 1;
      lastTimeSecond = timeSecond;

      return id;
    }
  }

  public static void main(String[] args)
    throws Exception
  {
    System.out.println(getId());
    Set set = new HashSet();

    long b = System.currentTimeMillis();

    int cc = 0;
    for (int i = 0; i < 2000000; ++i) {
      long id = getId();

      if (set.contains(Long.valueOf(id))) {
        System.out.println("重复的id!" + id);
        ++cc;
      } else {
        set.add(Long.valueOf(id));
      }
      set.add(Long.valueOf(id));
      if (System.currentTimeMillis() - b > 1000L) {
        break;
      }
    }
    System.out.println("生成的id数量=" + set.size());
    System.out.println("重复的id数量=" + cc);
  }
}