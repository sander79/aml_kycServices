package it.sander.aml.application.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SurveyDtoList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<SurveyDto> list;
	
	public SurveyDtoList() {
		list = new LinkedList<>();
	}

	public SurveyDtoList(int size) {
		list = new ArrayList<>(size);
	}
	
	public SurveyDtoList(SurveyDto oneItem) {
		if(oneItem==null) {
			new SurveyDtoList(0);
		} else {
			new SurveyDtoList(1);
			list.add(oneItem);
		}
	}

	public List<SurveyDto> getList() {
		return list;
	}

	public boolean addSurveyDto(SurveyDto surveyView) {
		return this.list.add(surveyView);
	}

}
