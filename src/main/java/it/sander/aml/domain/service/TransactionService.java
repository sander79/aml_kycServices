package it.sander.aml.domain.service;

public interface TransactionService {
	
	public static enum TransactionState {
		
		INSERTED("toProfile"),
		PROFILED("toManage"),
		MANAGED("toConfirm"),
		CONFIRMED("confirmed");

		private String action;

		TransactionState(String action) {
			this.action = action;
		}

		public String getAction() {
			return action;
		}
		
		static public TransactionState actionOf(String s) {
			for(TransactionState t : TransactionState.values()) {
				if(t.getAction().equals(s))
					return t;
			}
			return null;
		}
		
		
	}
	
	void notifyTransaction(String processId, TransactionState tr);


}
