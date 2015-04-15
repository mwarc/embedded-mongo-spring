package com.github.mwarc.embeddedmongo.testutils;


import com.github.fakemongo.Fongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


@Configuration
public class MongoConfiguration {

    private final String dbName;

    public MongoConfiguration() {
        this.dbName = "myDbName";
    }

    public MongoConfiguration(String dbName) {
        this.dbName = dbName;
    }

    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new Fongo("InMemoryMongo").getMongo(), dbName);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
