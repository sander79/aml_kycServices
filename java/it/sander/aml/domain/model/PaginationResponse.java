package it.sander.aml.domain.model;

import java.util.List;

public class PaginationResponse<T> {

	private List<T> content;
	private long totalElements;
	private boolean countRequest;
	
	public PaginationResponse(long totalElements) {
		this.countRequest = true;
		this.content = null;
		this.totalElements = totalElements;
	}
	
	public PaginationResponse(List<T> content) {
		this.content = content;
		this.totalElements = -1;
	}

	public List<T> getContent() {
		return content;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public boolean isCountRequest() {
		return countRequest;
	}
	
}
