/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtil
{
  private static final Logger LOGGER = LoggerFactory.getLogger(ByteUtil.class);

  public static String getString(ByteBuffer byteBuffer)
  {
    short len = byteBuffer.getShort();
    byte[] bb = new byte[len];
    byteBuffer.get(bb);
    String str = null;
    try {
      str = new String(bb, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      LOGGER.error("读取字符串错误.");
    }

    return str;
  }

  public static byte[] mergeByteArray(byte[] bb1, byte[] bb2)
  {
    int len1 = bb1.length;
    int len2 = bb2.length;
    byte[] bb = new byte[len1 + len2];
    System.arraycopy(bb1, 0, bb, 0, len1);
    System.arraycopy(bb2, 0, bb, len1, len2);
    return bb;
  }

  public static byte[] booleanToBytes(boolean b)
  {
    byte[] bb = new byte[1];
    bb[0] = (byte)((b) ? 1 : 0);
    return bb;
  }

  public static byte[] byteToBytes(byte b)
  {
    byte[] bb = new byte[1];
    bb[0] = b;
    return bb;
  }

  public static byte[] shortToBytes(short s)
  {
    ByteBuffer byteBuffer = ByteBuffer.allocate(2);
    byteBuffer.putShort(s);
    byteBuffer.flip();
    byte[] bb = new byte[byteBuffer.limit()];
    byteBuffer.get(bb);
    return bb;
  }

  public static byte[] intToBytes(int i)
  {
    ByteBuffer byteBuffer = ByteBuffer.allocate(4);
    byteBuffer.putInt(i);
    byteBuffer.flip();
    byte[] bb = new byte[byteBuffer.limit()];
    byteBuffer.get(bb);
    return bb;
  }

  public static byte[] longToBytes(long l)
  {
    ByteBuffer byteBuffer = ByteBuffer.allocate(8);
    byteBuffer.putLong(l);
    byteBuffer.flip();
    byte[] bb = new byte[byteBuffer.limit()];
    byteBuffer.get(bb);
    return bb;
  }

  public static byte[] stringToBytes(String s)
  {
    byte[] bb = null;
    try {
      bb = s.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return bb;
  }

  public static <T> T byteArrayToObject(byte[] bb)
  {
    T obj = null;
    ByteArrayInputStream bais = null;
    ObjectInputStream ois = null;
    try {
      bais = new ByteArrayInputStream(bb);
      ois = new ObjectInputStream(bais);
      obj = (T) ois.readObject();
    } catch (Exception e) {
      e.printStackTrace(); } finally {
      try {
        if (ois != null) ois.close();  } catch (IOException e) {
        e.printStackTrace(); }
      try { if (bais != null) bais.close();  } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return obj;
  }

  public static byte[] objectToByteArray(Object obj)
  {
    byte[] bb = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(baos);
      oos.writeObject(obj);
      bb = baos.toByteArray();
    } catch (Exception e) {
      e.printStackTrace(); } finally {
      try {
        if (oos != null) oos.close();  } catch (IOException e) {
        e.printStackTrace(); }
      try { if (baos != null) baos.close();  } catch (IOException e) {
        e.printStackTrace(); }
    }
    return bb;
  }
}