package com.blueoptima.emdm.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class defines bad request exception.
 * @author prafullkulshrestha
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1789828466245607334L;

	public BadRequestException(String message) {
	    super(message);
	  }

}

