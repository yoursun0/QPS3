/**********************************************************************************
 * Author            : Master Concept Ltd.
 * Version           : 1.0
 * Create Date       : June 20, 2013
 * Last Updated Date : July 14, 2013
 *********************************************************************************/
package qpses.util;

import org.apache.log4j.Logger;

public final class SysException extends Exception
{

	private static final long serialVersionUID = 1L;
	private static String MyClassName = SysException.class.getName();
    private static Logger logger = Logger.getLogger(MyClassName);
    
/*
    static
    { logger.debug("[" + MyClassName + "]" + " " + "started"); }
 */
    
    private SysExceptionLevel ExceptionLevel = SysExceptionLevel.FATAL; // Set default level to fatal
    
    public SysException(String errorMessage)
    {
        super(
                errorMessage.startsWith("[QPSES Exception]")?
                    errorMessage :
                    "[QPSES Exception]" + errorMessage
                );
        
        if (!errorMessage.startsWith("[QPSES Exception]"))
            logger.fatal(errorMessage);
    }

    
    /** Constructor for SysException */
    public SysException(String functionName, String errorMessage)
    {
        super(
                errorMessage.startsWith("[QPSES Exception]")?
                    errorMessage :
                    "[QPSES Exception]" + functionName + " " + errorMessage
                );
        
        if (!errorMessage.startsWith("[QPSES Exception]"))
            logger.fatal(functionName + " " + errorMessage);
    }
    
    /** Constructor for SysException */
    public SysException(String functionName, String errorMessage, SysExceptionLevel aSysExceptionLevel)
    {
        super(
                errorMessage.startsWith("[QPSES Exception]")?
                    errorMessage :
                    "[QPSES Exception]" + functionName + " " + errorMessage
                );
        
        if (!errorMessage.startsWith("[QPSES Exception]"))
            logger.fatal(functionName + " " + errorMessage);
        
        this.ExceptionLevel = aSysExceptionLevel;
    }
    
/*
    protected void finalize()
    { logger.debug("[" + this.MyClassName + "]" + " " + "ended"); }
 */
}