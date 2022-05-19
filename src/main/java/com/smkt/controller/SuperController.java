package com.smkt.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Action的基类，集成了一些常用的方法，
 *
 * @author Administrator
 */
public class SuperController {
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;
    }

    public HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        return response;
    }

    /**
     * 添加一个Session属性对象
     *
     * @param sessionKey
     * @param object
     * @return
     */
    protected void setSessionAttribute(String sessionKey, Object object) {
        HttpSession session = getRequest().getSession(false);
        if (session != null) {
            session.setAttribute(sessionKey, object);
        }
    }

    /**
     * 获取一个Session属性对象
     *
     * @param sessionKey
     * @return
     */
    protected Object getSessionAttribute(String sessionKey) {
        Object objSessionAttribute = null;
        HttpSession session = getRequest().getSession(false);
        if (session != null) {
            objSessionAttribute = session.getAttribute(sessionKey);
        }
        return objSessionAttribute;
    }

    /**
     * 移除Session对象属性值
     *
     * @param sessionKey
     * @return
     */
    protected void removeSessionAttribute(String sessionKey) {
        HttpSession session = getRequest().getSession();
        if (session != null) {
            session.removeAttribute(sessionKey);
        }
    }

    protected Map getParameterMap() {
        return getRequest().getParameterMap();

    }

    protected HashMap<String, Object> getParameterAll() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (String key : getRequest().getParameterMap().keySet()) {
            if (!"".equals(getRequest().getParameter(key))) {
                map.put(key, getRequest().getParameter(key));
            }
        }
        return map;
    }

    protected HashMap<String, Object> getParameterAll_() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (String key : getRequest().getParameterMap().keySet()) {
            map.put(key, getRequest().getParameter(key));
        }
        return map;
    }

    /**
     * 读取表单参数(int类型)
     *
     * @param name
     * @return
     */
    protected int GetParameterInt(String name) {
        return Integer.parseInt(getRequest().getParameter(name));
    }

    /**
     * 读取表单参数(string类型)
     *
     * @param name
     * @return
     */
    protected String GetParameterStr(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 读取表单参数(Long类型)
     *
     * @param name
     * @return
     */
    protected Long GetParameterLong(String name) {
        return Long.parseLong(getRequest().getParameter(name));
    }

    /*
     * 字符串转换为数组
     */
    public String[] getArrayFromString(String mids) {
        String[] strArray = mids.split(",");
        return strArray;
    }

    /*
     * 字符串转换为List
     */
    public ArrayList<String> getListFromString(String mids) {
        ArrayList<String> longslist = new ArrayList<String>();
        String[] strArray = getArrayFromString(mids);
        for (String item : strArray) {
            longslist.add(item);
        }
        return longslist;
    }

    public boolean ifcheck(List<String> indusList, String id) {
        boolean bl = false;
        for (String string : indusList) {
            if (string.equals(id)) {
                bl = true;
            }
        }
        return bl;
    }

    public ArrayList<Long> getTListFromString(String mids) {
        ArrayList<Long> longslist = new ArrayList<Long>();
        String[] strArray = getArrayFromString(mids);
        for (String item : strArray) {
            if (!item.isEmpty()) {
                longslist.add(Long.parseLong(item));
            }
        }
        return longslist;
    }

    public boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 获取客户端ip
     *
     * @param request
     * @return
     * @throws UnknownHostException
     */
    public String getIpAddr(HttpServletRequest request) {
        String IP = request.getHeader("x-forwarded-for");
        try {
            if (IP == null || IP.length() == 0 || "unknown".equalsIgnoreCase(IP)) {
                IP = request.getHeader("Proxy-Client-IP");
            }
            if (IP == null || IP.length() == 0 || "unknown".equalsIgnoreCase(IP)) {
                IP = request.getHeader("WL-Proxy-Client-IP");
            }
            if (IP == null || IP.length() == 0 || "unknown".equalsIgnoreCase(IP)) {
                IP = request.getRemoteAddr();
            }
        } catch (Exception e) {
        }
        return IP;
    }

    /**
     * 输出响应，并将输出类型转换为json类型
     *
     * @param str
     * @throws IOException
     */
    protected void SetJsonWrit(String str) throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        response.getWriter().write(str);
        response.getWriter().flush();
        response.getWriter().close();
    }


    /**
     * 输出响应
     *
     * @param str
     * @throws IOException
     */
    protected void write(String str) throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        response.getWriter().write(str);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
