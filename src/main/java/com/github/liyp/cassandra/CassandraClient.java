package com.github.liyp.cassandra;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.LatencyTracker;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.AccessorMapper;
import com.datastax.driver.mapping.AccessorReflectionMapper;
import com.datastax.driver.mapping.AnnotationParser;
import com.datastax.driver.mapping.MappingManager;
import com.github.liyp.cassandra.crosssync.CrossyncManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyp on 6/5/16.
 */
public class CassandraClient implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(CassandraClient.class);

    private Cluster cluster;
    private Session session;
    private MappingManager mappingManager;

    private CrossyncManager crossyncManager;

    public CassandraClient(String[] addresses, String keyspace) {
        this.cluster = Cluster.builder().addContactPoints(addresses).build();
        this.session = cluster.connect(keyspace);
        this.mappingManager = new MappingManager(session);

        this.crossyncManager = new CrossyncManager(mappingManager);

        cluster.register(new QueryLogg());
    }

    public <T> T createAccessor(Class<T> klass) {

        return mappingManager.createAccessor(klass);
    }

    public void close() throws IOException {
        session.close();
        cluster.close();
    }


    class QueryLogg implements LatencyTracker {

        public void update(Host host, Statement statement, Exception exception, long newLatencyNanos) {
            logger.debug("### {} {} {} {}", host, statement, exception, newLatencyNanos);
            BoundStatement bs = (BoundStatement) statement;
            logger.debug("### {}", bs.preparedStatement().getQueryString());
            ColumnDefinitions metadata = bs.preparedStatement().getVariables();
            int numberOfParameters = metadata.size();
            if (numberOfParameters > 0) {
                List<ColumnDefinitions.Definition> definitions = metadata.asList();
                for (int i = 0; i < numberOfParameters; i++) {
                    String info = String.format("%s:%s:%s", metadata.getName(i), metadata.getType(i), bs.getObject(i));
                    logger.debug("### " + info);
                }
            }
        }

        public void onRegister(Cluster cluster) {

        }

        public void onUnregister(Cluster cluster) {

        }
    }
}
