package pl.krzywyyy.barter.exception;

import org.springframework.util.StringUtils;

public class AlreadyExistsException extends Exception
{
	public AlreadyExistsException(Class clazz, String field){
	super(AlreadyExistsException.message(clazz.getSimpleName(),field));
	}
	
	private static String message(String entity, String field){
		return String.format("%s already exists as a %s",StringUtils.capitalize(entity),field);
	}
}
