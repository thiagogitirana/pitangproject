package com.pitangproject.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe responsável por mapear o retorno de erros
 * @author Thiago Gitirana
 *
 */
public class OutputError {
	@JsonProperty("message")
	private String message;

	@JsonProperty("errorCode")
	private int errorCode;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
