package br.com.pitang.selecao_java.exceptions;

@SuppressWarnings("serial")
public class FieldValueExistisException extends GenericExcpetion {
	
	public FieldValueExistisException(String fieldName, int codeError) {
		super(fieldName, codeError);
	}
		
	public String getMessage() {
		return String.format("%s already exists", super.getFieldName());
	}
	
	
}
