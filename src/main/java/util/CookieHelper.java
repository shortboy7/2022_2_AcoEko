package util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
	public static Cookie getCookie(HttpServletRequest request,String name) {
		Cookie [] cookies = request.getCookies();
		if (cookies == null) return null;
		for (int i = 0 ; i < cookies.length; i++) {
			if (cookies[i].getName().equals(name)) {
				return cookies[i];
			}
		}
		return null;
	}
	public static boolean deleteCookie(HttpServletRequest request,HttpServletResponse response, String name) {
		Cookie cookie = getCookie(request, name);
		if (cookie == null) return false;
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return true;
	}
}
