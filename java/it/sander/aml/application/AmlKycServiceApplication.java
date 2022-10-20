package it.sander.aml.application;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import it.sander.aml.domain.SurveyService;
import it.sander.aml.domain.SurveyServiceImpl;
import it.sander.aml.domain.repository.SurveyRepository;
import it.sander.aml.infrastructure.repository.mock.SanderUserRepositoryMock;
import it.sander.aml.infrastructure.repository.mongo.SurveyRepositoryMongo;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class AmlKycServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmlKycServiceApplication.class, args);
	}
	
    @Bean(name = "surveyService")
    public SurveyService getSurveyService() {	
		return new SurveyServiceImpl();
    }  
    
    
    @Configuration
    @EnableMongoRepositories
    public class MongoClientConfiguration extends AbstractMongoClientConfiguration {
    	
    	@Value("${application.mode}")
    	String mode;
           
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
    
    
    @Configuration
	public class SurveyRepoFactory {

		@Value("${application.mode}")
		String mode;

		@Autowired
		private ApplicationContext applicationContext;

		@Bean(name = "surveyRepository")
		public SurveyRepository getRepository() {
			SurveyRepository userRepository = null;

			if (userRepository == null) {
				if ("mock".equals(mode))
					return new SanderUserRepositoryMock();

				if ("mongo".equals(mode)) {
					MongoClientConfiguration mongoClientConfiguration = applicationContext
							.getBean(MongoClientConfiguration.class);
					return new SurveyRepositoryMongo(mongoClientConfiguration.mongoTemplate());
				}
			}

			return userRepository;
		}
	}
    
    
    @Configuration
    public class SwaggerDocket {
    	
    	@Bean
        public Docket api() { 
            return new Docket(DocumentationType.SWAGGER_2)  
              .select()                                  
              .apis(RequestHandlerSelectors.any())              
              .paths(PathSelectors.any())                          
              .build();                                           
        }
/*
    	@Bean
    	public Docket swaggerSurveyApi10() {
    		return new Docket(DocumentationType.SWAGGER_2).groupName("/surveys v1.0").select()
    				.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.versioning.controller"))
    				.paths(regex("/surveys/v1.0*")).build().apiInfo(new ApiInfoBuilder().version("1.0").title("surveys API")
    						.description("Documentation surveys API v1.0").build());
    	}

    	@Bean
    	public Docket swaggerPersonApi11() {
    		return new Docket(DocumentationType.SWAGGER_2).groupName("surveys v1.1").select()
    				.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.versioning.controller"))
    				.paths(regex("/surveys/v1.1*")).build().apiInfo(new ApiInfoBuilder().version("1.1").title("surveys API")
    						.description("Documentation surveys API v1.1").build());
    	}
*/
    	private Predicate<String> regex(String string) {
    		return Pattern.compile(string).asPredicate();
    	}

    }
    
}


