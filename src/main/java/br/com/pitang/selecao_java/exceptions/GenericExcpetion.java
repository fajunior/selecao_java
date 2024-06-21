package br.com.pitang.selecao_java.exceptions;

@SuppressWarnings("serial")
public class GenericExcpetion extends RuntimeException {
	private String fieldName;
	private int codeError;
	
	public GenericExcpetion(String fieldName, int codeError) {
		super();
		this.fieldName = fieldName;
		this.codeError = codeError;
	}
	
	public int getCodeError() {
		return codeError;
	}

	public String getFieldName() {
		return fieldName;
	}
	
}
