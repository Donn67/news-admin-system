package com.iov.utils;

import java.util.HashMap;
import java.util.Map;


public class ThreadLocalUtil {
    //提供ThreadLocal对象,
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();


    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }
	

    public static  void  set(Object value){
        THREAD_LOCAL.set(value);
    }


    //清除ThreadLocal 防止内存泄漏
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
