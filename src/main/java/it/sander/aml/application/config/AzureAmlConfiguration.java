package it.sander.aml.application.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.azure.data.appconfiguration.ConfigurationClient;
import com.azure.data.appconfiguration.ConfigurationClientBuilder;
import com.azure.data.appconfiguration.models.SettingSelector;

@Component
@Profile("azureConfig")
public class AzureAmlConfiguration implements AmlConfiguration {
	
	@Value("${appconfiguration.connectionString}")
	private String connectionString;
	
	private ConfigurationClient configurationClient;
	
	private Map<String,String> cache;
	
	@PostConstruct
	private void init() {
		cache = new HashMap<String,String>();
		configurationClient = new ConfigurationClientBuilder()
			    .connectionString(connectionString)
			    .buildClient();
	}

	@Override
	public String getAmlConfiguration(String key) throws AmlConfigurationError {
		if(cache.containsKey(key))
			return cache.get(key);			
		
		String config = null;
		try {	
			
			config = configurationClient.getConfigurationSetting(key, null).getValue();
		} catch(Exception e) {
			throw new AmlConfigurationError("Configuration unavailable:" + key + "  error: " + e.getMessage(), e);
		}
		
		if(config == null)
			throw new AmlConfigurationError("Configuration unavailable:" + key);
		
		cache.put(key, config);
		return config;
	}
}


