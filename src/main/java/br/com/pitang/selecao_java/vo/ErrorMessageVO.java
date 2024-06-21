package br.com.pitang.selecao_java.vo;

public class ErrorMessageVO {
	private String message;
	private int errorCode;
	
	public ErrorMessageVO(String message, int errorCode) {
		super();
		this.message = message;
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
	
}
