package it.sander.aml.domain.service;

public interface TransactionService {
	
	public static enum TransactionState {
		INSERTED,
		PROFILRD,
		MANAGED,
		CONFIRMED;
	}
	
	void notifyTransaction(String processId, TransactionState tr);


}
