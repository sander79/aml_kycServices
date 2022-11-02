package it.sander.aml.infrastructure.repository.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories
@Profile("mongo")
public class MongoClientConfiguration extends AbstractMongoClientConfiguration {
       
    @Value("${spring.data.mongodb.uri}")
    public String mongoUri;
    
    @Value("${spring.data.mongodb.database}")
    public String mongoDatabase;
    
    @Override
    protected String getDatabaseName() {
        return this.mongoDatabase;
    }
    
    @Override
    public MongoClient mongoClient() {       	
        final ConnectionString connectionString = new ConnectionString(mongoUri);
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder
            .applyConnectionString(new ConnectionString(mongoUri))
            .readPreference(ReadPreference.secondary());
    }
    
	public MongoTemplate mongoTemplate() {
		MongoTemplate template = new MongoTemplate(mongoDbFactory());
		return template;
	}
    
}