package qpses.util;

import static org.junit.Assert.*;

import org.junit.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SysManagerTest {
	
	private SysManager manager;
	
	@Before
    public void setUp() {
		manager = new SysManager();
    }

    @After
    public void tearDown() {
    	manager = null;
    }
    
    @Test(expected=qpses.util.SysException.class)
    public void testGetResourceException() throws SysException {   
    	String resource;
    	String entry = "NOT_EXIST_PARAMETER";
		resource = (String) SysManager.getResource(entry);

    }
    
    @Test(expected=qpses.util.SysException.class)
    public void testGetPropertiesException() throws SysException {   
    	String resource;
    	String entry = "NOT_EXIST_PARAMETER";
		resource = (String) SysManager.getProperty(entry);
    }
    
    @Test
    public void testGetStringFromSQLDate(){   
    	
    	String[] dates = new String[] {"02-Jan-2013","12-Feb-2012","31-Mar-2011","22-Apr-2000","01-Dec-1987","30-Nov-2019"};
    	
    	for (String dateStr:dates){
    		try {
    			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    			java.util.Date inputDate;
				inputDate = sdf.parse(dateStr);

				Date day = new java.sql.Date(inputDate.getTime());
				assertEquals(dateStr,SysManager.getStringfromSQLDate(day).intern());
			} catch (ParseException e) {
				e.printStackTrace();
				fail("For date = "+dateStr+", encounter unexpected ParseException "+e);
			} catch (SysException e) {
				e.printStackTrace();
				fail("For date = "+dateStr+", encounter unexpected SysException "+e);
			}
    	}

    }
    
    @Test
    public void testFormatDecimal(){   
    	
    	String[] strings = new String[] {"1.10","2.24","39.00","-2.00","63.12","11.20"};
    	double[] doubles = new double[] {1.1,2.24,39.,-2.,63.12,11.2};
    	
    	for (int i=0;i<6;i++){
				try {
					assertEquals(strings[i],SysManager.formatDecimal(doubles[i]));
				} catch (SysException e) {
					e.printStackTrace();
					fail("For double = "+strings[i]+", encounter unexpected SysException "+e);
				}
    	}

    }
    
    @Test
    public void testGetCurDateTimeStr(){   
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		try {
			assertEquals(sdf.format(new java.util.Date()),SysManager.getCurDateTimeStr("dd-MMM-yyyy"));
		} catch (SysException e) {
			e.printStackTrace();
			fail("Encounter unexpected SysException "+e);
		}

    }
    
    @Test(expected=SysException.class)
    public void testGetCurDateTimeStrErr() throws SysException{   
    	String abc = SysManager.getCurDateTimeStr("abcd");
    }
    
    @Test
    public void testGetSQLDate() throws SysException, ParseException{   
    	
    	String[] dates = new String[] {"2013-01-20","2012-02-28","2011-03-31","2000-02-29","1987-12-31","2019-11-01"};
    	
    	for (String dateStr:dates){
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    			java.util.Date utilDate = sdf.parse(dateStr);
				java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
				
				assertEquals(sqlDate,SysManager.getSQLDate(utilDate));
    	}
    }
    
    @Test
    public void testGetSpecialFormatStringfromSQLDate() throws ParseException, SysException{   
    	
    	String dateStr = "01-Jan-2019";
		assertEquals("01/01/19",SysManager.getSpecialFormatStringfromSQLDate(SysManager.getSQLDate(dateStr)));

    }
    
    @Test
    public void testGetStringfromSQLDateTime() throws SysException{   
    	
    	String dateStr = "2019-11-01 01:02:03";
    	String dateStr2 = "01-Nov-2019 01:02";
		assertEquals(dateStr2,SysManager.getStringfromSQLDateTime(dateStr));

    }
    
    @Test
    public void testGetFileName(){   
		assertEquals("good.docx",SysManager.getFileName("/happy/play/good.docx"));
    }
    
    @Test
    public void testGenerateRandomPassword(){   
		for (int i=1;i<10;i++){
			assertNotNull(SysManager.generateRandomPassword(i));
		}
    }
    
    @Test
    public void testGetValue(){   
    	String[] strings = new String[] {"110","224","3900","-200","6312","1120"};
		for (String str : strings){
			assertEquals(SysManager.getValue(str),Integer.toString(SysManager.getIntValue(str)));
		}
    }
    
}
