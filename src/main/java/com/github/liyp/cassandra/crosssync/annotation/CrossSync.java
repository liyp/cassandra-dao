package com.github.liyp.cassandra.crosssync.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.datastax.driver.mapping.annotations.Accessor;

/**
 * Defines to whether the {@link Accessor} method synchronizes across regions.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CrossSync {

    /**
     * Whether synchronizing should be enable for the operation.
     *
     * @return Whether synchronizing should be enable for the operation.
     */
    boolean synchronizing() default true;
}
