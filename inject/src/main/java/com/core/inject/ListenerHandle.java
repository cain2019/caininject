package com.core.inject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理
 */
public class ListenerHandle implements InvocationHandler {

    private Method leoMethod;

    private Object obj;

    public ListenerHandle(Method method, Object obj) {
        this.leoMethod = method;
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.leoMethod.invoke(obj,args);
    }
}
