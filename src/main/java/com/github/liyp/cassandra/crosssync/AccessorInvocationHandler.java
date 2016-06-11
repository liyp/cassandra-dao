package com.github.liyp.cassandra.crosssync;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyp on 6/11/16.
 */
public class AccessorInvocationHandler<T> implements InvocationHandler {


    private static final Object[] NO_ARGS = new Object[0];

    private final AccessorMapper<T> mapper;

    private final Map<Method, CrossyncMethodMapper> methodMap = new HashMap<>();

    AccessorInvocationHandler(AccessorMapper<T> mapper) {
        this.mapper = mapper;

        for (CrossyncMethodMapper method : mapper.methods)
            methodMap.put(method.method, method);
    }

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {

        final T daoInstance = mapper.getDaoInstance();
        // TODO: 6/12/16
        Object ret = m.invoke(daoInstance, args);

        // TODO: 6/12/16  check ret

        // TODO: 6/12/16 cross sync remotely
        CrossyncMethodMapper method = methodMap.get(m);
        if (method != null)
            method.invoke(args == null ? NO_ARGS : args);

        return ret;
    }

}
