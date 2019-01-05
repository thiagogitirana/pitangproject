package com.pitangproject.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe responsável pelo retorno do serviço rest
 * 
 * @author Thiago Gitirana
 *
 */
public class OutputUser {
	
	@JsonProperty("message")
	private String message = null;
	
	@JsonProperty("result")
	private Object result;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	
	
}
