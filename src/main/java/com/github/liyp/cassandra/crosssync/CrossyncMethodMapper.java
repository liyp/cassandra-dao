package com.github.liyp.cassandra.crosssync;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Method;

import com.datastax.driver.core.ConsistencyLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liyunpeng on 6/7/16.
 *
 * accessor method info
 */
class CrossyncMethodMapper {

    private static final Logger logger = LoggerFactory.getLogger(CrossyncMethodMapper.class);

    public final Method method;
    public final String queryString;
    public final ParamMapper[] paramMappers;

    private final ConsistencyLevel consistency;
    private final int fetchSize;
    private final boolean tracing;

    public CrossyncMethodMapper(Method method, String queryString, ParamMapper[] paramMappers, ConsistencyLevel consistency,
                                int fetchSize, boolean tracing) {
        this.method = method;
        this.queryString = queryString;
        this.paramMappers = paramMappers;
        this.consistency = consistency;
        this.fetchSize = fetchSize;
        this.tracing = tracing;
    }

    public Object invoke(Object[] args) {
        // TODO: 6/11/16 write cql into kinesis stream
        logger.debug("### remote write Kinesis Stream. method={}, cql={}", method, queryString);
        for (ParamMapper pm : paramMappers) {
            logger.debug("### \t\t\t params {} {} {} {}", pm.paramName, pm.paramIdx, pm.paramType, args[pm.paramIdx]);
        }
        return null;
    }


    static class ParamMapper {

        final String paramName;
        final int paramIdx;
        final TypeToken<Object> paramType;

        ParamMapper(String paramName, int paramIdx, TypeToken<?> paramType) {
            this.paramName = paramName;
            this.paramIdx = paramIdx;
            this.paramType = (TypeToken<Object>) paramType;
        }
    }
}
