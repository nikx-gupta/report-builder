package org.devignite.reportBuilder;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @Bean
    public ConnectionString connectionString() {
        return new ConnectionString("mongodb://192.168.29.69/sample_mflix?ssl=false");
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(connectionString());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @Override
    protected String getDatabaseName() {
        return connectionString().getDatabase();
    }
}
