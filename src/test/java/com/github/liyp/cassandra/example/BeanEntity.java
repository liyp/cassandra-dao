package com.github.liyp.cassandra.example;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

/**
 * Created by liyp on 6/5/16.
 */
@Table(name = "bean", keyspace = "test")
public class BeanEntity {

    @PartitionKey
    private int pk;

    @ClusteringColumn
    private String ck;

    private String name;

    private Date date;

    public BeanEntity(int pk, String ck, String name, Date date) {
        this.pk = pk;
        this.ck = ck;
        this.name = name;
        this.date = date;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getCk() {
        return ck;
    }

    public void setCk(String ck) {
        this.ck = ck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BeanEntity{");
        sb.append("pk=").append(pk);
        sb.append(", ck='").append(ck).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
