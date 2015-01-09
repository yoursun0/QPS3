package qpses.business;

import static org.junit.Assert.*;
import org.junit.*;
import java.sql.Date;
import java.util.*;

public class WAChallengeInfoTest {
	
	private WAChallengeInfo info;
	
	private List<Date> testDate;
	
	@Before
    public void setUp() {
		info = new WAChallengeInfo();
		testDate = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -250); // 250 days before today
        
		for (int i=0;i<10;i++){
			cal.add(Calendar.DAY_OF_YEAR, 50);
			testDate.add(new java.sql.Date(cal.getTime().getTime()));
		}
    }

    @After
    public void tearDown() {
    	info = null;
    	testDate.clear();
    }

    @Test
    public void testClosingDate() {    	
		for (int i=0;i<10;i++){
			info.setClosingDate(testDate.get(i));
	        assertTrue("The closing date should be valid but return false.", info.validateClosingDate(testDate.get(i)));
		}
		
		for (int i=0;i<9;i++){
			info.setClosingDate(testDate.get(i));
	        assertFalse("The closing date is invalid but return true.", info.validateClosingDate(testDate.get(i+1)));
		}		
    }
    
    @Test
    public void testIssuedDate() {    	
		for (int i=0;i<10;i++){
			info.setIssuedDate(testDate.get(i));
	        assertTrue("The closing date should be valid but return false.", info.validateIssuedDate(testDate.get(i)));
		}
		
		for (int i=0;i<9;i++){
			info.setIssuedDate(testDate.get(i));
	        assertFalse("The closing date is invalid but return true.", info.validateIssuedDate(testDate.get(i+1)));
		}		
    }
}
