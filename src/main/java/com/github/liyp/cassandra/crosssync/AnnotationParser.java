package com.github.liyp.cassandra.crosssync;


import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.datastax.driver.mapping.annotations.QueryParameters;
import com.github.liyp.cassandra.crosssync.annotation.CrossSync;
import com.github.liyp.cassandra.crosssync.CrossyncMethodMapper.ParamMapper;
import com.google.common.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class AnnotationParser {


    public static <T> AccessorMapper<T> parseAccessor(Class<T> accClass, AccessorMapper.Factory factory) {
        {
            if (!accClass.isInterface())
                throw new IllegalArgumentException("@Accessor annotation is only allowed on interfaces");

            //AnnotationChecks.getTypeAnnotation(Accessor.class, accClass);

            List<CrossyncMethodMapper> methods = new ArrayList<>();
            for (Method m : accClass.getDeclaredMethods()) {
                CrossSync crossSync = m.getAnnotation(CrossSync.class);
                if(crossSync == null)
                    continue;

                if(!crossSync.synchronizing())
                    continue;

                Query query = m.getAnnotation(Query.class);
                if (query == null)
                    continue;

                String queryString = query.value();

                Annotation[][] paramAnnotations = m.getParameterAnnotations();
                Type[] paramTypes = m.getGenericParameterTypes();
                ParamMapper[] paramMappers = new ParamMapper[paramAnnotations.length];
                Boolean allParamsNamed = null;
                for (int i = 0; i < paramMappers.length; i++) {
                    String paramName = null;
                    for (Annotation a : paramAnnotations[i]) {
                        if (a.annotationType().equals(Param.class)) {
                            Param param = (Param) a;
                            paramName = param.value();
                            if (paramName.isEmpty())
                                paramName = null;
                            break;
                        }
                    }
                    boolean thisParamNamed = (paramName != null);
                    if (allParamsNamed == null)
                        allParamsNamed = thisParamNamed;
                    else if (allParamsNamed != thisParamNamed)
                        throw new IllegalArgumentException(String.format("For method '%s', either all or none of the parameters must be named", m.getName()));

                    paramMappers[i] = new ParamMapper(paramName, i, TypeToken.of(paramTypes[i]));
                }

                ConsistencyLevel cl = null;
                int fetchSize = -1;
                boolean tracing = false;

                QueryParameters options = m.getAnnotation(QueryParameters.class);
                if (options != null) {
                    cl = options.consistency().isEmpty() ? null : ConsistencyLevel.valueOf(options.consistency().toUpperCase());
                    fetchSize = options.fetchSize();
                    tracing = options.tracing();
                }

                methods.add(new CrossyncMethodMapper(m, queryString, paramMappers, cl, fetchSize, tracing));
            }

            return factory.create(accClass, methods);
        }
    }

}
