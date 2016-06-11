package com.github.liyp.cassandra.crosssync;

import com.datastax.driver.mapping.MappingManager;

import java.util.List;

/**
 * Created by liyunpeng on 6/7/16.
 */
abstract class AccessorMapper<T> {

    public final Class<T> daoClass;
    protected final List<CrossyncMethodMapper> methods;

    private T daoInstance;

    protected AccessorMapper(Class<T> daoClass, List<CrossyncMethodMapper> methods) {
        this.daoClass = daoClass;
        this.methods = methods;
    }

    abstract T createProxy();

    public void prepare(MappingManager mappingManager) {
        // TODO: 6/12/16
        // 原实现 `Prepares the provided query string asynchronously`
        // 这里代理实际的proxy，做本地调用实现
        T daoInstance = mappingManager.createAccessor(daoClass);
    }

    public T getDaoInstance() {
        return daoInstance;
    }

    interface Factory {
        <T> AccessorMapper<T> create(Class<T> daoClass, List<CrossyncMethodMapper> methods);
    }
}
