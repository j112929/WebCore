/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtil {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CookieUtil.class);

	public static void writeCookie(HttpServletResponse response, String name,
			String value) {
		writeCookie(response, name, value, 2592000, "/");
	}

	public static void writeCookie(HttpServletResponse response, String name,
			String value, int maxAge, String path) {
		try {
			Cookie cookie = new Cookie(name, value);

			cookie.setMaxAge(maxAge);

			cookie.setPath(path);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getCookieValue(HttpServletRequest request, String name)
  {
    Cookie[] cc = request.getCookies();
    if (cc == null)
      return null;
    for (Cookie cookie : cc) {
      if (cookie.getName().equals(name)) {
        return cookie.getValue();
      }
    }
    return null;
  }

	public static void removeCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		removeCookie(request, response, name, "/");
	}

	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path)
  {
    Cookie[] cc = request.getCookies();
    if ((cc == null) || (cc.length < 1))
    {
      LOGGER.error("ÇëÇóÀïcookieÎªnull");
    }
    for (Cookie cookie : cc)
      if (cookie.getName().equals(name)) {
        cookie.setMaxAge(0);
        cookie.setPath(path);
        response.addCookie(cookie);
      }
  }
}