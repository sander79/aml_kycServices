package it.sander.aml.application.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Profile("localConfig")
public class LocalAmlConfiguration implements AmlConfiguration {
	
	private Map<String,String> cache = new HashMap<String,String>();
	
	@Autowired
	private Environment env;

	@Override
	public String getAmlConfiguration(String key) throws AmlConfigurationError {
		if(cache.containsKey(key))
			return cache.get(key);			
		
		String config = env.getProperty(key);
		if(config == null)
			throw new AmlConfigurationError("Configuration unavailable:" + key);
		
		cache.put(key, config);
		return config;
	}

}


