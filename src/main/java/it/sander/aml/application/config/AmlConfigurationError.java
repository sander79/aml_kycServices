package it.sander.aml.application.config;

public class AmlConfigurationError extends Exception {

	public AmlConfigurationError(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AmlConfigurationError(String arg0) {
		super(arg0);
	}

	public AmlConfigurationError(Throwable arg0) {
		super(arg0);
	}

}
