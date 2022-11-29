package it.sander.aml.application.config;

import org.springframework.stereotype.Component;

@Component
public interface AmlConfiguration {
	
	public String getAmlConfiguration(String key) throws AmlConfigurationError;

}
