package in.co.rays.proj4.exception;

/**
 * ApplicationException is propogated from Service classes when an business
 * logic exception occurered.
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class ApplicationException extends Exception {

	/**
	 * @param msg
	 */
	public ApplicationException(String msg) {
		super(msg);
	}

}
