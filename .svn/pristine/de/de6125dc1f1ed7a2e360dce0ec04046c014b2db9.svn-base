/**********************************************************************************
 * Author            : Master Concept Ltd.
 * Version           : 1.0
 * Create Date       : June 20, 2013
 * Last Updated Date : July 14, 2013
 *********************************************************************************/
package qpses.util;

import org.apache.log4j.Logger;

public final class SysExceptionLevel
{
    private static String MyClassName = SysExceptionLevel.class.getName();
    static Logger logger = Logger.getLogger(MyClassName);
    
    /*
    static
    { logger.debug("[" + MyClassName + "]" + " " + "started"); }
     */
    
    private final String LevelLabel;
    
    public static final SysExceptionLevel FATAL        = new SysExceptionLevel("FATAL");
    public static final SysExceptionLevel WARNING      = new SysExceptionLevel("WARNING");
    public static final SysExceptionLevel CONFIRMATION = new SysExceptionLevel("CONFIRMATION");
    public static final SysExceptionLevel INFO         = new SysExceptionLevel("INFO");
    
    private SysExceptionLevel(String label)
    { this.LevelLabel = label; }
    
    public final String toString()
    { return this.LevelLabel; };
    
    /*
    protected void finalize()
    { logger.debug("[" + MyClassName + "]" + " " + "ended"); }
     */
}