package br.senai.sc.services.exceptions;

public class InvalidArgumentException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InvalidArgumentException(String msg) {
		super(msg);
	}

}
