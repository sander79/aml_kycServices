package it.sander.aml.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;

import it.sander.aml.application.security.OktaOpaqueTokenIntrospector;
import it.sander.aml.domain.service.SurveyService;
import it.sander.aml.domain.service.SurveyServiceImpl;

@SpringBootApplication
//@EnableWebFluxSecurity
@ComponentScan({"it.sander.aml"})
public class AmlKycServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmlKycServiceApplication.class, args);
	}
	
    @Bean(name = "surveyService")
    public SurveyService getSurveyService() {	
		return new SurveyServiceImpl();
    }  
    
    @Bean
    @Profile("okta")
    public ReactiveOpaqueTokenIntrospector keycloakIntrospector(OAuth2ResourceServerProperties props) {
        
        NimbusReactiveOpaqueTokenIntrospector delegate = new NimbusReactiveOpaqueTokenIntrospector(
           props.getOpaquetoken().getIntrospectionUri(),
           props.getOpaquetoken().getClientId(),
           props.getOpaquetoken().getClientSecret());
        
        return new OktaOpaqueTokenIntrospector(delegate);
    }
    
    @Bean public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
    
}


