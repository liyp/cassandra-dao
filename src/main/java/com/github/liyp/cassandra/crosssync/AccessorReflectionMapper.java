package com.github.liyp.cassandra.crosssync;


import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by liyp on 6/11/16.
 */
class AccessorReflectionMapper<T> extends AccessorMapper<T> {

    private static AccessorReflectionFactory factory = new AccessorReflectionFactory();

    private final Class<T>[] proxyClasses;
    private final AccessorInvocationHandler<T> handler;


    @SuppressWarnings({"unchecked", "rawtypes"})
    private AccessorReflectionMapper(Class<T> daoClass, List<CrossyncMethodMapper> methods) {
        super(daoClass, methods);
        this.proxyClasses = (Class<T>[]) new Class[]{daoClass};
        this.handler = new AccessorInvocationHandler<>(this);
    }

    public static Factory factory() {
        return factory;
    }

    @SuppressWarnings("unchecked")
    @Override
    T createProxy() {
        try {
            return (T) Proxy.newProxyInstance(daoClass.getClassLoader(), proxyClasses, handler);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create instance for Accessor interface " + daoClass.getName());
        }
    }

    private static class AccessorReflectionFactory implements AccessorMapper.Factory {
        @Override
        public <T> AccessorMapper<T> create(Class<T> daoClass, List<CrossyncMethodMapper> methods) {
            return new AccessorReflectionMapper<>(daoClass, methods);
        }
    }
}
