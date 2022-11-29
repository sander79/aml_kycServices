package it.sander.aml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

import it.sander.aml.application.security.OktaOpaqueTokenIntrospector;

@SpringBootApplication
//@EnableWebFluxSecurity
//@ComponentScan({"it.sander.aml"})
//@ComponentScan(basePackages = "it.sander.aml")
//@EnableAutoConfiguration
public class AmlKycService {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AmlKycService.class, args);
		context.getEnvironment();
		context.getBeanNamesForAnnotation(Component.class);
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
    
}


