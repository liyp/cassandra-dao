package com.github.liyp.cassandra.crosssync;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Method;

import com.datastax.driver.core.ConsistencyLevel;

/**
 * Created by liyunpeng on 6/7/16.
 *
 * accessor method info
 */
class CrossyncMethodMapper {


    public final Method method;
    public final String queryString;
    public final ParamMapper[] paramMappers;

    private final ConsistencyLevel consistency;
    private final int fetchSize;
    private final boolean tracing;
    private final Boolean idempotent;

    private boolean async;

    public CrossyncMethodMapper(Method method, String queryString, ParamMapper[] paramMappers, ConsistencyLevel consistency,
                                int fetchSize, boolean tracing, Boolean idempotent) {
        this.method = method;
        this.queryString = queryString;
        this.paramMappers = paramMappers;
        this.consistency = consistency;
        this.fetchSize = fetchSize;
        this.tracing = tracing;
        this.idempotent = idempotent;
    }

    public Object invoke(Object[] args) {
        // TODO: 6/11/16 write cql into kinesis stream

        return null;
    }


    static class ParamMapper {

        private final String paramName;
        private final int paramIdx;
        private final TypeToken<Object> paramType;

        ParamMapper(String paramName, int paramIdx, TypeToken<Object> paramType) {
            this.paramName = paramName;
            this.paramIdx = paramIdx;
            this.paramType = paramType;
        }
    }
}
