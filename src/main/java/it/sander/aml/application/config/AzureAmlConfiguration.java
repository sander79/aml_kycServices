package it.sander.aml.application.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.azure.data.appconfiguration.ConfigurationClient;
import com.azure.data.appconfiguration.ConfigurationClientBuilder;

@Component
@Profile("azureConfig")
public class AzureAmlConfiguration implements AmlConfiguration {
	
	private String connectionString = "Endpoint=https://aml-config.azconfig.io;Id=EJWx-l9-s0:u0WI95G5BdQPICNAUobP;Secret=EPVUzdMJ9GsQU2TTz91c5XfpFkRQp9FaUDH+Z31+2lE=";
	private ConfigurationClient configurationClient;
	
	private Map<String,String> cache;
	
	public AzureAmlConfiguration() {
		cache = new HashMap<String,String>();
		configurationClient = new ConfigurationClientBuilder()
			    .connectionString(connectionString)
			    .buildClient();
	}

	@Override
	public String getAmlConfiguration(String key) throws AmlConfigurationError {
		if(cache.containsKey(key))
			return cache.get(key);			
		
		String config = configurationClient.getConfigurationSetting(key, null).getValue();
		if(config == null)
			throw new AmlConfigurationError("Configuration unavailable:" + key);
		
		return cache.put(key, config);
	}
}


