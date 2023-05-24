package com.cn.jmw.color;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年04月15日 17:38
 * @Version 1.0
 */
public class ThreadColor {

    static final Map<String, ColorEnum8> map8 = new ConcurrentHashMap(8);

    static final Map<String, ColorEnum256> map256 = new ConcurrentHashMap(256);

    public static ColorEnum8 getColor8(String threadName) {
        return map8.computeIfAbsent(threadName, k -> ColorEnum8.getRandomColor());
    }

    public static ColorEnum256 getColor256(String threadName) {
        return map256.computeIfAbsent(threadName, k -> ColorEnum256.getRandomColor256());
    }

}
