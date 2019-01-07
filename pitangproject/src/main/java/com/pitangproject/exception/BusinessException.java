package com.pitangproject.exception;

/**
 * Classe responsável pelas exceções de negócio
 * 
 * @author Thiago Gitirana
 *
 */
public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	private int code;

	public BusinessException(String message, int code) {
		super();
		this.message = message;
		this.code = code;
	}

	public BusinessException() {
		super();

	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);

	}

	public BusinessException(String message) {
		super(message);

	}

	public BusinessException(Throwable cause) {
		super(cause);

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
