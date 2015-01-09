package qpses.business;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;

import java.util.*;

public class CeilingRateCat1InfoTest {
	
	private final int TEST_NUM = 9;
	private List<CeilingRateCat1Info> infos;
	private String user = "henry.ogcio";
    private String waTypeStr = "Proposal Evaluation";
    private String outcome;
    
	@Before
    public void setUp(){
		
		// Initialize Ceiling Rate Info
		infos = new ArrayList<CeilingRateCat1Info>();
		int add = 0;
		

		CeilingRateCat1Info info = new CeilingRateCat1Info();

		for (int j = 0; j < CeilingRateCat1Info.NumOfWorkingLocation; j++){
				
			for (int k = 1; k < CeilingRateCat1Info.NumOfStaffCategory; k++){
				// Standard Staff Rate
				info.setStdStaffRate(k, j, Double.valueOf(CeilingRateCat1Info.NoOfferDBNumber).doubleValue());
			}
			// Supplementary Staff Rate
			info.setSupStaffRate(j, Double.valueOf(CeilingRateCat1Info.NoOfferDBNumber).doubleValue());
		}
        
		infos.add(info);
		
		for (int i=1;i<TEST_NUM;i++){
			info = new CeilingRateCat1Info();

			for (int j = 0; j < CeilingRateCat1Info.NumOfWorkingLocation; j++){
				info.setStdStaffRate(0, j, Double.valueOf(CeilingRateCat1Info.NoOfferDBNumber).doubleValue());
				for (int k = 0; k < CeilingRateCat1Info.NumOfStaffCategory; k++){
					// Standard Staff Rate
					info.setStdStaffRate(k, j, Double.valueOf(j*k+add).doubleValue());
				}
				// Supplementary Staff Rate
				info.setSupStaffRate(j, Double.valueOf(j+add).doubleValue());
			}
        
			add+=10;
			infos.add(info);
        }
        
    }

    @After
    public void tearDown() {
    	infos.clear();
    }

    @Test
    public void testCheckCeilingRateNotExceed()
    {
    	// Should Not Exceed the larger DB ceiling rates
    	for (int i=1;i<TEST_NUM-1;i++){
			CeilingRateCat1Info info = infos.get(i);
			CeilingRateCat1Info info2 = infos.get(i+1);
			info.CheckCeilingRate(info2);
			for (int j = 0; j < CeilingRateCat1Info.NumOfWorkingLocation; j++){
				assertEquals(info.NotExceedString,info.getSupStaffRateExceed(j));
				for (int k = 1; k < CeilingRateCat1Info.NumOfStaffCategory; k++){
					// Standard Staff Rate
					assertEquals(info.NotExceedString,info.getStdStaffRateExceed(k,j));
				}

			}	
        }
    }
    
    @Test
    public void testCheckCeilingRateExceed()
    {
    	// Should Exceed the smaller DB ceiling rates
    	for (int i=1;i<TEST_NUM-1;i++){
			CeilingRateCat1Info info = infos.get(i+1);
			CeilingRateCat1Info info2 = infos.get(i);
			info.CheckCeilingRate(info2);
			for (int j = 0; j < CeilingRateCat1Info.NumOfWorkingLocation; j++){
				assertEquals(info.ExceedString,info.getSupStaffRateExceed(j));
				for (int k = 1; k < CeilingRateCat1Info.NumOfStaffCategory; k++){
					// Standard Staff Rate
					assertEquals(info.ExceedString,info.getStdStaffRateExceed(k,j));
				}
			}	
        }
    }
    
    @Test
    public void testCheckCeilingRateNoOffer()
    {
    	// For No offer ceiling rates
    	CeilingRateCat1Info info = infos.get(3);
		CeilingRateCat1Info info2 = infos.get(0);
		info.CheckCeilingRate(info2);
		for (int j = 1; j < CeilingRateCat1Info.NumOfWorkingLocation ; j++){
			for (int k = 1; k < CeilingRateCat1Info.NumOfStaffCategory ; k++){
				// Standard Staff Rate
				assertEquals(info.NoOfferString,info.getStdStaffRateExceed(k,j));
			}
		}	
    }
    
    @Test
    public void testSupStaffRateTableField0()
    {
    	CeilingRateCat1Info info;
    	int workingLocation = 0;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		assertEquals("supplementary_1_resident",info.toSupStaffRateTableField(workingLocation));
    	}
    }
    
    @Test
    public void testSupStaffRateTableField1()
    {
    	CeilingRateCat1Info info;
    	int workingLocation = 1;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		assertEquals("supplementary_1_non_resident",info.toSupStaffRateTableField(workingLocation));
    	}
    }
    
    @Test
    public void testSupStaffRateTableField2()
    {
    	CeilingRateCat1Info info;
    	int workingLocation = 2;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		assertEquals("supplementary_1_offshore",info.toSupStaffRateTableField(workingLocation));
    	}
    }
    
    @Test
    public void testStdStaffRateTableField0()
    {
    	CeilingRateCat1Info info;
    	int workingLocation = 0;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		for (int sc=3;sc<13;sc++){
    			assertEquals("sc" + sc + "_resident",info.toStdStaffRateTableField(sc-3, workingLocation));
    		}
    	}
    }
    
    @Test
    public void testStdStaffRateTableField1()
    {
    	String outcome;
    	CeilingRateCat1Info info;
    	int workingLocation = 1;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		for (int sc=3;sc<13;sc++){
    			assertEquals("sc" + sc + "_non_resident",info.toStdStaffRateTableField(sc-3, workingLocation));
    		}
    	}
    }
    
    @Test
    public void testStdStaffRateTableField2()
    {
    	String outcome;
    	CeilingRateCat1Info info;
    	int workingLocation = 2;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		for (int sc=3;sc<13;sc++){
    			assertEquals("sc" + sc + "_offshore",info.toStdStaffRateTableField(sc-3, workingLocation));
    		}
    	}
    }
    
}
