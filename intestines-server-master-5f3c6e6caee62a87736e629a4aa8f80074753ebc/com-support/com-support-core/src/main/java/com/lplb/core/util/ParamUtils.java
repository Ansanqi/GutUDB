/**
 * $RCSfile: ParamUtils.java,v $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/09/09 13:51:07 $
 *
 * New Jive  from Jdon.com.
 *
 * This software is the proprietary information of CoolServlets, Inc.
 * Use is subject to license terms.
 */

package com.lplb.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class assists skin writers in getting parameters.
 */
public class ParamUtils {

	private static final Logger log = LoggerFactory.getLogger(ParamUtils.class);
	
	private static final String common_regex = "[^0-9]{1}.*";
	
	private static final String common_num = "-9999999";
	/**
	 * Gets a parameter as a string.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The value of the parameter or null if the parameter was not found
	 *         or if the parameter is a zero-length string.
	 */

	/**
	 * Gets a parameter as a string.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @param emptyStringsOK
	 *            Return the parameter values even if it is an empty string.
	 * @return The value of the parameter or null if the parameter was not
	 *         found.
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		String temp = request.getParameter(name);

		if (temp == null) {
			return "";
		}
		return temp.trim();
	}

	public static String getParameter(HttpServletRequest request, String name,
			String defaultValue) {
		String temp = request.getParameter(name);
		if (temp == null) {
			return defaultValue;
		}
		return temp;
	}

	/**
	 * Gets a parameter as a boolean.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return True if the value of the parameter was "true", false otherwise.
	 */
	public static boolean getBooleanParameter(HttpServletRequest request,
			String name) {
		return getBooleanParameter(request, name, false);
	}

	/**
	 * Gets a parameter as a boolean.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return True if the value of the parameter was "true", false otherwise.
	 */
	public static boolean getBooleanParameter(HttpServletRequest request,
			String name, boolean defaultVal) {
		String temp = request.getParameter(name);
		if ("true".equals(temp) || "on".equals(temp)) {
			return true;
		} else if ("false".equals(temp) || "off".equals(temp)) {
			return false;
		} else {
			return defaultVal;
		}
	}

	/**
	 * Gets a parameter as an int.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The int value of the parameter specified or the default value if
	 *         the parameter is not found.
	 */
	public static int getIntParameter(HttpServletRequest request, String name,
			int defaultNum) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll(common_regex, "");
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
				log.error(ignored.getMessage());
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * Gets a parameter as an int.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The Integer value of the parameter specified or the default value
	 *         if the parameter is not found.
	 */
	public static Integer getIntegerParameter(HttpServletRequest request,
			String name) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll(common_regex, "");
			Integer num = Integer.valueOf(common_num);
			try {
				num = Integer.valueOf(temp);
			} catch (Exception ignored) {
				log.error(ignored.getMessage());
			}
			return num;
		} else {
			return Integer.valueOf(common_num);
		}
	}

	/**
	 * Gets a list of int parameters.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @param defaultNum
	 *            The default value of a parameter, if the parameter can't be
	 *            converted into an int.
	 */
	public static int[] getIntParameters(HttpServletRequest request,
			String name, int defaultNum) {
		String[] paramValues = request.getParameterValues(name);
		if (paramValues == null) {
			return null;
		}
		if (paramValues.length < 1) {
			return new int[0];
		}
		int[] values = new int[paramValues.length];
		for (int i = 0; i < paramValues.length; i++) {
			try {
				values[i] = Integer.parseInt(paramValues[i]);
			} catch (Exception e) {
				values[i] = defaultNum;
				log.error(e.getMessage());
			}
		}
		return values;
	}

	/**
	 * Gets a parameter as a double.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The double value of the parameter specified or the default value
	 *         if the parameter is not found.
	 */
	public static double getDoubleParameter(HttpServletRequest request,
			String name, double defaultNum) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll("[^0-9\\\\.]{1}.*", "");
			double num = defaultNum;
			try {
				num = Double.parseDouble(temp);
			} catch (Exception ignored) {
				log.error(ignored.getMessage());
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * Gets a parameter as a long.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The long value of the parameter specified or the default value if
	 *         the parameter is not found.
	 */
	public static long getLongParameter(HttpServletRequest request,
			String name, long defaultNum) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll(common_regex, "");
			long num = defaultNum;
			try {
				num = Long.parseLong(temp);
			} catch (Exception ignored) {
				log.error(ignored.getMessage());
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * Gets a list of long parameters.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @param defaultNum
	 *            The default value of a parameter, if the parameter can't be
	 *            converted into a long.
	 */
	public static long[] getLongParameters(HttpServletRequest request,
			String name, long defaultNum) {
		String[] paramValues = request.getParameterValues(name);
		if (paramValues == null) {
			return null;
		}
		if (paramValues.length < 1) {
			return new long[0];
		}
		long[] values = new long[paramValues.length];
		for (int i = 0; i < paramValues.length; i++) {
			try {
				values[i] = Long.parseLong(paramValues[i]);
			} catch (Exception e) {
				values[i] = defaultNum;
				log.error(e.getMessage());
			}
		}
		return values;
	}

	/**
	 * Gets a parameter as a string.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The value of the parameter or null if the parameter was not found
	 *         or if the parameter is a zero-length string.
	 */
	public static String getAttribute(HttpServletRequest request, String name) {
		return getAttribute(request, name, false);
	}

	/**
	 * Gets a parameter as a string.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @param emptyStringsOK
	 *            Return the parameter values even if it is an empty string.
	 * @return The value of the parameter or null if the parameter was not
	 *         found.
	 */
	public static String getAttribute(HttpServletRequest request, String name,
			boolean emptyStringsOK) {
		String temp = (String) request.getAttribute(name);
		if (temp != null) {
			if (temp.equals("") && !emptyStringsOK) {
				return null;
			} else {
				return temp;
			}
		} else {
			return null;
		}
	}

	/**
	 * Gets an attribute as a boolean.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the attribute you want to get
	 * @return True if the value of the attribute is "true", false otherwise.
	 */
	public static boolean getBooleanAttribute(HttpServletRequest request,
			String name) {
		String temp = (String) request.getAttribute(name);
		if (temp != null && temp.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets an attribute as a int.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the attribute you want to get
	 * @return The int value of the attribute or the default value if the
	 *         attribute is not found or is a zero length string.
	 */
	public static int getIntAttribute(HttpServletRequest request, String name,
			int defaultNum) {
		String temp = (String) request.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll(common_regex, "");
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
				log.error(ignored.getMessage());
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * Gets an attribute as a long.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the attribute you want to get
	 * @return The long value of the attribute or the default value if the
	 *         attribute is not found or is a zero length string.
	 */
	public static long getLongAttribute(HttpServletRequest request,
			String name, long defaultNum) {
		String temp = (String) request.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll(common_regex, "");
			long num = defaultNum;
			try {
				num = Long.parseLong(temp);
			} catch (Exception ignored) {
				log.error(ignored.getMessage());
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static int getIntAttribute(HttpSession session, String name,
			int defaultNum) {
		String temp = (String) session.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll(common_regex, "");
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
				log.error(ignored.getMessage());
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * Gets a Attribute as a double.
	 * 
	 * @param session
	 *            The HttpSession object, known as "session" in a JSP page.
	 * @param name
	 *            The name of the Attribute you want to get
	 * @return The double value of the Attribute specified or the default value
	 *         if the Attribute is not found.
	 */
	public static double getDoubleAttribute(HttpSession session, String name,
			double defaultNum) {
		String temp = (String) session.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll("[^0-9\\\\.]{1}.*", "");
			double num = defaultNum;
			try {
				num = Double.parseDouble(temp);
			} catch (Exception ignored) {
				log.error(ignored.getMessage());
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * Gets a Attribute as a long.
	 * 
	 * @param session
	 *            The HttpSession object, known as "session" in a JSP page.
	 * @param name
	 *            The name of the Attribute you want to get
	 * @return The long value of the Attribute specified or the default value if
	 *         the Attribute is not found.
	 */
	public static long getLongAttribute(HttpSession session, String name,
			long defaultNum) {
		String temp = (String) session.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll(common_regex, "");
			long num = defaultNum;
			try {
				num = Long.parseLong(temp);
			} catch (Exception ignored) {
				log.error(ignored.getMessage());
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static String getAttribute(HttpSession session, String name) {
		String temp = (String) session.getAttribute(name);

		if (temp == null) {
			return "";
		}
		return temp;
	}

	public static String getAttribute(HttpSession session,
                                      String name, String defaultValue) {
		String temp = (String) session.getAttribute(name);
		if (temp == null) {
			return defaultValue;
		}
		return temp;
	}

	public static Date getDateParameter(HttpServletRequest req, String name) {
		Date d = null;
		DateFormat df = DateFormat.getDateInstance();
		String datestring = getParameter(req, name, "");
		if (datestring.trim().length() == 0)
			return null;
		try {
			d = df.parse(datestring);
		} catch (ParseException ex) {
			log.error(ex.getMessage());
		}
		return d;
	}

	public static boolean chkInteger(Integer id) {
		if (id == null)
			return false;
		try {
			if (id.intValue() <= 0)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public static boolean chkLong(Long id) {
		if (id == null)
			return false;
		try {
			if (id.longValue() <= 0)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean chkIntegerAll(Integer id) {
		if (id == null)
			return false;
		try {
			if (id.intValue() >= 0)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static boolean chkIntegerMinus(Integer id) {
		if (id == null)
			return false;
		try {
			if (id.intValue() < 0)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static boolean chkIntegerPlus(Integer id) {
		if (id == null)
			return false;
		try {
			if (id.intValue() > 0)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static boolean chkString(String str) {
		if (str == null)
			return false;
		if (str.trim().length() <= 0)
			return false;
		return true;
	}

	public static String chkStringNotNull(String str) {
		if (str == null)
			return "";
		if (str.trim().length() <= 0)
			return "";
		return str;
	}

	public static boolean isNumeric(String str) {
		if (str == null)
			return false;
		if (str.trim().length() <= 0)
			return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static boolean isNumber(String str) {
		if (str == null)
			return false;
		if (str.trim().length() <= 0)
			return false;
		int index = str.indexOf(".");
		if (index < 0) {
			return ParamUtils.isNumeric(str);
		} else {
			String num1 = str.substring(0, index);
			String num2 = str.substring(index + 1);
			return ParamUtils.isNumeric(num1) && ParamUtils.isNumeric(num2);
		}
	}


	public static boolean chkIntegerPlusOrMinus(Integer id) {
		if (id == null)
			return false;
		try {
			if (id != 0)
				return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return false;
	}

	public static String getStringNotNull(String str) {
		if (str == null || str.trim().length() == 0)
			return "";
		return str;
	}

	public static String getStringNotNull(String str, String def) {
		if (str == null || str.trim().length() == 0)
			return def;
		return str;
	}

	public static String getParameter(Map<String, String> map, String name) {
		String temp = (String) map.get(name);
		if (temp == null) {
			return "";
		}
		return temp.trim();
	}

	public static String getParameter(Map<String, String> map, String name,
			String defaultValue) {
		String temp = (String) map.get(name);
		if (temp == null || temp.trim().length() == 0) {
			return defaultValue;
		}
		return temp;
	}

	public static int getIntParameter(Map<String, String> map, String name,
			int defaultNum) {
		String temp = (String) map.get(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll(common_regex, "");
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static Integer getIntegerParameter(Map<String, String> map,
			String name) {
		String temp = (String) map.get(name);
		if (temp != null && !temp.equals("")) {
			temp = temp.replaceAll(common_regex, "");
			Integer num = Integer.valueOf(common_num);
			try {
				num = Integer.valueOf(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return Integer.valueOf(common_num);
		}
	}
	/**
	 * 
	 * @param url
	 * @return null 代表url有错误
	 */
	public static Map<String, String> toMap(String url) {
//		System.out.println("url:>>>>>>>>>>>>>"+url);
		if(url.indexOf("?")>-1){
			url=url.subSequence(url.indexOf("?")+1, url.length()).toString();
		}
        Map<String, String> map = null;
        if (url != null && url.indexOf("=") > -1) {
            map = new HashMap<String, String>();
             
            String[] arrTemp = url.split("&");          
            for (String str : arrTemp) {
                String[] qs = str.split("=");
                map.put(qs[0], qs[1]);
            }
        }
        return map;
    }

	public static boolean chkBigDecimal(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return true;
		}
		return false;
	}
}
