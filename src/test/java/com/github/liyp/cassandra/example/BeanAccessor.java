package com.github.liyp.cassandra.example;

import java.util.Date;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

/**
 * Created by liyp on 6/5/16.
 */

@Accessor
public interface BeanAccessor {

    @Query("SELECT * FROM test.bean where pk = :pk and ck = :ck")
    BeanEntity getBean(@Param("pk") int pk, @Param("ck") String ck);

    //@CrossSync
    @Query("INSERT INTO test.bean (pk, ck, name, date) VALUES (:pk, :ck, :name, :date)")
    ResultSet insertBean(@Param("pk") int pk, @Param("ck") String ck, @Param("name") String name,
                         @Param("date") Date date);

    //@CrossSync
    @Query("INSERT INTO test.bean (pk, ck, name, date) VALUES (:pk, :ck, :ck, :date)")
    ResultSet insertBean2(@Param("pk") int pk1, @Param("ck") String ck, @Param("date") Date date);

    //@CrossSync
    @Query("INSERT INTO test.bean (pk, ck, name, date) VALUES (?, ?, ?, ?)")
    ResultSet insertBean3(int pk1, String ck, String name, Date date);

    //Error
    //@CrossSync
    @Query("INSERT INTO test.bean (pk, ck, name, date) VALUES (:pk, :ck, :ck, :date)")
    ResultSet insertBean4(int pk1, String ck, String ck2, Date date);
}
