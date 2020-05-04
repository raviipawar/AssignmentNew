package com.writerpad.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * <h1>DataSource</h1>
 * <p>
 * This is the DataSource class for different environment
 * </p>
 *
 * @author Ravindra Pawar
 */
@SuppressWarnings("deprecation")
@Profile(value = {"dev","local"})
@Configuration
public class Datasource extends AbstractMongoConfiguration {

    @Value("${db.database}")
    private String database;

    @Value("${mongoDBURI}")
    private String uri;

    @Value("${db.uri}")
    private String dburi;

    private Environment environment;

    public Datasource(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    @Bean("mongo")
    public MongoClient mongoClient() {
        if (environment.getActiveProfiles()[0].equals("local")) {
            return new MongoClient(new MongoClientURI(dburi));
        } else {
            return new MongoClient(new MongoClientURI(uri));
        }
    }
}
