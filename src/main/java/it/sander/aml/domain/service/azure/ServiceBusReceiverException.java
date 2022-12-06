package it.sander.aml.domain.service.azure;

public class ServiceBusReceiverException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceBusReceiverException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceBusReceiverException(String message) {
		super(message);
	}

	public ServiceBusReceiverException(Throwable cause) {
		super(cause);
	}

}
