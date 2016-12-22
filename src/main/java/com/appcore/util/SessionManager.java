/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import com.appcore.context.AppContext;
import com.appcore.security.BmSession;
import com.appcore.service.SessionService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionManager
{
  private static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);

  public static String BM_SESSION_ID = "BMSESSIONID";

  public static int MIN_SESSION_ID_LEN = 32;

  public static BmSession createSession(HttpServletRequest request, HttpServletResponse response)
  {
    SessionService sessionService = (SessionService)AppContext.getBean("sessionService");
    BmSession session = sessionService.createSession();

    CookieUtil.writeCookie(response, BM_SESSION_ID, session.getSessionId());

    request.setAttribute(BM_SESSION_ID, session.getSessionId());

    return session;
  }

  public static void setAttribute(String sessionId, String key, Object obj)
  {
    if ((sessionId == null) || (sessionId.length() < MIN_SESSION_ID_LEN)) {
      LOGGER.error("用户还未登录！没有sessionId, 不能操作session");
      return;
    }

    SessionService sessionService = (SessionService)AppContext.getBean("sessionService");

    sessionService.setAttribute(sessionId, key, obj);
  }

  public static void setAttribute(HttpServletRequest request, String key, Object obj)
  {
    String sessionId = getSessionId(request);

    setAttribute(sessionId, key, obj);
  }

  private static void setAttribute(String key, Object obj)
  {
    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

    setAttribute(request, key, obj);
  }

  public static <T> T getAttribute(String sessionId, String key)
  {
    if ((sessionId == null) || (sessionId.length() < MIN_SESSION_ID_LEN)) {
      LOGGER.error("用户还未登录！没有sessionId, 不能操作session");
      return null;
    }

    SessionService sessionService = (SessionService)AppContext.getBean("sessionService");

    Object object = sessionService.getAttribute(sessionId, key);

    return (T) object;
  }

  public static <T> T getAttribute(HttpServletRequest request, String key)
  {
    String sessionId = getSessionId(request);

    if ((sessionId == null) || (sessionId.length() < MIN_SESSION_ID_LEN)) {
      LOGGER.error("用户还未登录！没有sessionId, 不能操作session");
      return null;
    }

    SessionService sessionService = (SessionService)AppContext.getBean("sessionService");

    if (!(Gather.check(request))) {
      return null;
    }

    Object object = sessionService.getAttribute(sessionId, key);

    return (T)object;
  }

  private static <T> T getAttribute(String key)
  {
    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

    Object object = getAttribute(request, key);

    return (T)object;
  }

  public static void removeAttribute(HttpServletRequest request, String key)
  {
    String sessionId = getSessionId(request);

    if ((sessionId == null) || (sessionId.length() < MIN_SESSION_ID_LEN)) {
      LOGGER.error("用户还未登录！没有sessionId, 不能操作session");
      return;
    }

    SessionService sessionService = (SessionService)AppContext.getBean("sessionService");

    sessionService.removeAttribute(sessionId, key);
  }

  private static void removeAttribute(String key)
  {
    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

    removeAttribute(request, key);
  }

  private static String getSessionId(HttpServletRequest request)
  {
    String sessionId = (String)request.getAttribute(BM_SESSION_ID);

    if ((sessionId == null) || ("".equals(sessionId.trim()))) {
      sessionId = CookieUtil.getCookieValue(request, BM_SESSION_ID);
    }

    if ((sessionId == null) || ("".equals(sessionId.trim()))) {
      sessionId = request.getParameter(BM_SESSION_ID);
    }
    return sessionId;
  }

  public static boolean hasSession(HttpServletRequest request)
  {
    String sessionId = getSessionId(request);

    if ((sessionId == null) || (sessionId.length() < MIN_SESSION_ID_LEN)) {
      return false;
    }

    SessionService sessionService = (SessionService)AppContext.getBean("sessionService");

    return (sessionService.hasSession(sessionId));
  }
}