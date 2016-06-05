package com.github.liyp.cassandra.example;

import com.datastax.driver.core.Host;
import com.datastax.driver.core.QueryLogger;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.github.liyp.cassandra.CassandraClient;

import java.io.IOException;
import java.util.Date;

/**
 * Created by liyp on 6/5/16.
 */
public class Main {

    public static void main(String[] args) {
        try (CassandraClient cassClient = new CassandraClient(new String[] {"127.0.0.1"}, "test")) {

            BeanAccessor acc = cassClient.createAccessor(BeanAccessor.class);

            ResultSet rs = acc.insertBean(1, "1", "name1", new Date());
            System.out.println(rs == null ? "null" : rs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
