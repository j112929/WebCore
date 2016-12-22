/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolManager
{
  private static final Logger LOGGER = LoggerFactory.getLogger(JedisPoolManager.class);

  @Autowired
  private JedisPool jedisPool;

  public Jedis getResource()
  {
    LOGGER.debug("开始获取redis连接");
    Jedis jedis = this.jedisPool.getResource();
    LOGGER.debug("获取redis连接成功");
    return jedis;
  }

  public void returnResourceObject(Jedis jedis)
  {
    LOGGER.debug("开始释放redis连接");
    this.jedisPool.returnResourceObject(jedis);
    LOGGER.debug("释放redis连接成功");
  }
}