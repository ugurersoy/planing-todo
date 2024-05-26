package com.ersoy.planing_todo.config;


import com.couchbase.client.core.msg.kv.DurabilityLevel;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.manager.bucket.BucketSettings;
import com.couchbase.client.java.manager.bucket.BucketType;
import com.ersoy.planing_todo.domain.model.ToDo;
import com.ersoy.planing_todo.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;

import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

@Slf4j
@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${spring.couchbase.connection-string}")
    private String connectionString;

    @Value("${spring.couchbase.username}")
    private String username;

    @Value("${spring.couchbase.password}")
    private String password;

    @Value("${spring.couchbase.bucket.bucket-name}")
    private String bucketName;

    @Override
    public String getConnectionString() {
        return connectionString;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }


    @Override
    public void configureRepositoryOperationsMapping(RepositoryOperationsMapping baseMapping) {
        try {
            CouchbaseTemplate personTemplate = myCouchbaseTemplate(myCouchbaseClientFactory("todo"), new MappingCouchbaseConverter());
            baseMapping.mapEntity(ToDo.class, personTemplate);
            CouchbaseTemplate userTemplate = myCouchbaseTemplate(myCouchbaseClientFactory("todo"), new MappingCouchbaseConverter());
            baseMapping.mapEntity(User.class, userTemplate);
            // everything else goes in getBucketName()
        } catch (Exception e) {
            throw e;
        }
    }

    // do not use couchbaseTemplate for the name of this method, otherwise the value of that been
    // will be used instead of the result from this call (the client factory arg is different)
    public CouchbaseTemplate myCouchbaseTemplate(CouchbaseClientFactory couchbaseClientFactory,
                                                 MappingCouchbaseConverter mappingCouchbaseConverter) {
        return new CouchbaseTemplate(couchbaseClientFactory, mappingCouchbaseConverter);
    }

    // do not use couchbaseClientFactory for the name of this method, otherwise the value of that bean will
// will be used instead of this call being made ( bucketname is an arg here, instead of using bucketName() )
    public CouchbaseClientFactory myCouchbaseClientFactory(String bucketName) {
        return new SimpleCouchbaseClientFactory(getConnectionString(), authenticator(), bucketName);
    }


    @Bean
    public Bucket getCouchbaseBucket(Cluster cluster){
        //Creates the bucket if it does not exist yet
        if( !cluster.buckets().getAllBuckets().containsKey(bucketName)) {
            cluster.buckets().createBucket(
                    BucketSettings.create(bucketName)
                            .bucketType(BucketType.COUCHBASE)
                            .minimumDurabilityLevel(DurabilityLevel.NONE)
                            .ramQuotaMB(128));
        }
        return cluster.bucket(bucketName);
    }
}
