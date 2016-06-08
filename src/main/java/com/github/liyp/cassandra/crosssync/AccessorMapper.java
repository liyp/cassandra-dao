package com.github.liyp.cassandra.crosssync;

import java.util.List;

/**
 * Created by liyunpeng on 6/7/16.
 */
abstract class AccessorMapper<T> {
    public final Class<T> daoClass;
    protected final List<MethodMapper> methods;

    protected AccessorMapper(Class<T> daoClass, List<MethodMapper> methods) {
        this.daoClass = daoClass;
        this.methods = methods;
    }

    abstract T createProxy();

    public void prepare(MappingManager manager) {

    }
}
