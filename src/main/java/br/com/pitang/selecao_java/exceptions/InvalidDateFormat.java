package br.com.pitang.selecao_java.exceptions;

import java.text.ParseException;
@SuppressWarnings("serial")
public class InvalidDateFormat extends ParseException {

	public InvalidDateFormat(String s, int errorOffset) {
		super(s, errorOffset);
		System.out.println(s);
		// TODO Auto-generated constructor stub
	}
	
	public String getMessage() {
		return String.format("Invalid Date Format! Please use the format dd/mm/yyyy");
	}

}
