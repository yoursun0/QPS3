package qpses.security;

import org.apache.log4j.Logger;

public class SecurityException extends Exception {

	// log4j
	static Logger logger = Logger.getLogger(SecurityException.class.getName());
	
	/**
	 * Constructor for SecurityException
	 */
	public SecurityException() {
		super();
	}

	/**
	 * Constructor for SecurityException
	 */
	public SecurityException(String arg0) {
		
		logger.error("SecurityModuleError :" + arg0);
//		super(arg0);
	}

}

