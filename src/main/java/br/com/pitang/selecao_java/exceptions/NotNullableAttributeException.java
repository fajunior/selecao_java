package br.com.pitang.selecao_java.exceptions;

@SuppressWarnings("serial")
public class NotNullableAttributeException extends GenericExcpetion {
	
	public NotNullableAttributeException(String fieldName, int codeError) {
		super(fieldName, codeError);
	}
	
	public String getMessage() {
		return "Missing fields";
	}
	
}
