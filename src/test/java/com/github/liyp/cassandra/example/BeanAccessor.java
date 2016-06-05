package com.github.liyp.cassandra.example;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import java.util.Date;

/**
 * Created by liyp on 6/5/16.
 */

@Accessor
public interface BeanAccessor {

    @Query("SELECT * FROM test.bean where pk = :pk and ck = :ck")
    BeanEntity getBean(@Param("pk") int pk, @Param("ck") String ck);

    @Query("INSERT INTO test.bean (pk, ck, name, date) VALUES (:pk, :ck, :name, :date)")
    ResultSet insertBean(@Param("pk") int pk, @Param("ck") String ck, @Param("name") String name,
                         @Param("date") Date date);

}
