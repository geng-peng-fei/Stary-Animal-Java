package com.gpf.animal.common;

import org.springframework.stereotype.Component;

/**
 * 封装ThreadLocal工具类  可以对同一线程内的值进行操作
 *
 * @author gengpengfei
 */
@Component
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     *
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     *
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
