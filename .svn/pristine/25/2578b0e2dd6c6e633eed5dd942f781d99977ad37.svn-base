/**********************************************************************************
 * UserStatusTest.java
 * Author            : Helic Leung, Master Concept HK Ltd.
 * Version           : 1.0
 * Create Date       : Jul 22, 2013
 * Last Updated Date : Aug 22, 2013
 *********************************************************************************/

package qpses.security;

import java.util.Calendar;

import static org.junit.Assert.*;
import org.junit.*;

public class UserStatusTest {
	private UserStatus status;
	
	@Before
    public void setUp() {
		status = new UserStatus();
    }

    @After
    public void tearDown() {
    	status = null;
    }

    @Test
    public void testIsExpired() {
    	
        java.util.Calendar cal = java.util.Calendar.getInstance();
        
        cal.add(Calendar.DAY_OF_MONTH, -2); // day before yesterday
        
        status.expiryDate = new java.sql.Date(cal.getTime().getTime());
        
        assertTrue("The date should be expired but return false.", status.isExpired());
    }
    
    @Test
    public void testNotExpired() {
    	
        java.util.Calendar cal = java.util.Calendar.getInstance();
        
        cal.add(Calendar.DAY_OF_MONTH, 1); // tomorrow
        
        status.expiryDate = new java.sql.Date(cal.getTime().getTime());
        
        assertFalse("The date should not be expired but return true.", status.isExpired());
    }
    
	
}
