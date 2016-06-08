package com.github.liyp.cassandra.crosssync;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyunpeng on 6/7/16.
 */
public class MappingManager {

    private volatile Map<Class<?>, Object> accessors = Collections.emptyMap();

    /**
     * Creates an accessor object based on the provided interface.
     *
     * @param klass
     * @param <T>
     * @return
     */
    public <T> T createAccessor(Class<T> klass) {
        return getAccessor(klass);
    }

    @SuppressWarnings("unchecked")
    private <T> T getAccessor(Class<T> klass) {
        T accessor = (T)accessors.get(klass);
        if (accessor == null) {
            synchronized (accessors) {
                accessor = (T)accessors.get(klass);
                if (accessor == null) {
                    // TODO: 6/8/16
//                    AccessorMapper<T> mapper = AnnotationParser.parseAccessor(klass, AccessorReflectionMapper.factory(),
//                                                                              this);
//                    mapper.prepare(this);
//                    accessor = mapper.createProxy();
//                    Map<Class<?>, Object> newAccessors = new HashMap<Class<?>, Object>(accessors);
//                    newAccessors.put(klass, accessor);
//                    accessors = newAccessors;
                }
            }
        }
        return accessor;
    }

}
