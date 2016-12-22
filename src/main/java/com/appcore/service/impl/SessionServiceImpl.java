/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.service.impl;

import com.appcore.conf.CoreConfig;
import com.appcore.core.pool.TaskPoolFactory;
import com.appcore.model.conf.AppConfig;
import com.appcore.security.BmSession;
import com.appcore.service.SessionService;
import com.appcore.util.SessionManager;
import com.appcore.util.StringUtil;
import com.appcore.util.TimeUtil;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("sessionService")
public class SessionServiceImpl
  implements SessionService
{
  private static final Logger LOGGER = LoggerFactory.getLogger(SessionServiceImpl.class);

  private static ConcurrentHashMap<String, BmSession> sessionMap = new ConcurrentHashMap();

  public BmSession createSession()
  {
    BmSession session = new BmSession();
    session.setSessionId(StringUtil.md5(System.currentTimeMillis() + UUID.randomUUID().toString()).toUpperCase());
    session.setExpire(System.currentTimeMillis() + CoreConfig.appConfig.getDefaultSessionTimeOut() * 1000L);
    sessionMap.put(session.getSessionId(), session);
    LOGGER.info("创建session，并放入sessionMap，session信息【{}】", new Object[] { session });
    return session;
  }

  public void setAttribute(String sessionId, String key, Object obj)
  {
    BmSession session = (BmSession)sessionMap.get(sessionId);
    if (session == null) {
      LOGGER.error("setAttribute 的时候，session 为null");
      return;
    }
    session.getMap().put(key, obj);
  }

  public Object getAttribute(String sessionId, String key)
  {
    BmSession session = (BmSession)sessionMap.get(sessionId);
    if (session == null) {
      LOGGER.error("getAttribute 的时候，session 为null");
      return null;
    }
    Object obj = session.getMap().get(key);
    return obj;
  }

  public void removeAttribute(String sessionId, String key)
  {
    BmSession session = (BmSession)sessionMap.get(sessionId);
    if (session == null) {
      LOGGER.error("removeAttribute 的时候，session 为null");
      return;
    }
    session.getMap().remove(key);
  }

  public boolean hasSession(String sessionId)
  {
    if ((sessionId == null) || (sessionId.length() < SessionManager.MIN_SESSION_ID_LEN)) {
      LOGGER.error("无效的sessionId! sessionId长度不得小于【{}】！", new Object[] { Integer.valueOf(SessionManager.MIN_SESSION_ID_LEN) });
      return false;
    }
    BmSession session = (BmSession)sessionMap.get(sessionId);
    if (session != null) {
      session.setExpire(System.currentTimeMillis() + CoreConfig.appConfig.getDefaultSessionTimeOut() * 1000L);
      return true;
    }
    return false;
  }

  static
  {
    CleanSessionTask task = new CleanSessionTask();
    TaskPoolFactory.scheduleAtFixedRate("CleanSessionTask", task, 10L, 10L, TimeUnit.SECONDS);
  }

  private static class CleanSessionTask
    implements Runnable
  {
    public void run()
    {
      for (BmSession session : SessionServiceImpl.sessionMap.values())
        if (session.getExpire() < System.currentTimeMillis()) {
          SessionServiceImpl.sessionMap.remove(session.getSessionId());
          SessionServiceImpl.LOGGER.info("删除过期的Session对象【{}】,过期时间【{}】", new Object[] { session.getSessionId(), TimeUtil.getFormatTime(session.getExpire(), "yyyy-MM-dd HH:mm:ss") });
        }
    }
  }
}