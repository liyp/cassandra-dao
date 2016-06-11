package com.github.liyp.cassandra.crosssync;

import com.datastax.driver.mapping.MappingManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyp on 6/12/16.
 */
public class CrossyncManager {

    private final MappingManager mappingManager;

    private volatile Map<Class<?>, Object> accessors = Collections.emptyMap();

    public CrossyncManager(MappingManager mappingManager) {
        this.mappingManager = mappingManager;
    }

    /**
     * Creates an accessor object based on the provided interface.
     */
    public <T> T createAccessor(Class<T> klass) {
        return getAccessor(klass);
    }

    @SuppressWarnings("unchecked")
    private <T> T getAccessor(Class<T> klass) {
        T accessor = (T) accessors.get(klass);
        if (accessor == null) {
            synchronized (accessors) {
                accessor = (T) accessors.get(klass);
                if (accessor == null) {
                    AccessorMapper<T> mapper = AnnotationParser.parseAccessor(klass, AccessorReflectionMapper.factory());
                    mapper.prepare(mappingManager);
                    accessor = mapper.createProxy();
                    Map<Class<?>, Object> newAccessors = new HashMap<>(accessors);
                    newAccessors.put(klass, accessor);
                    accessors = newAccessors;
                }
            }
        }
        return accessor;
    }

}
