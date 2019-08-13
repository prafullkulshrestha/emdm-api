package com.blueoptima.emdm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class returns no data found exception
 * @author prafullkulshrestha
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoDataFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 991645392618031372L;

	public NoDataFoundException(String message) {
	    super(message);
	  }

}
