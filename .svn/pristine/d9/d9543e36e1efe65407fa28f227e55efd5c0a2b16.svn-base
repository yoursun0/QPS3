package qpses.business;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;

import java.util.*;

public class CeilingRateCat4InfoTest {
	
	private final int TEST_NUM = 9;
	private List<CeilingRateCat4Info> infos;
	private String user = "henry.ogcio";
    private String waTypeStr = "Proposal Evaluation";
    private String outcome;
    
	@Before
    public void setUp(){
		
		// Initialize Ceiling Rate Info
		infos = new ArrayList<CeilingRateCat4Info>();
		int add = 0;

		CeilingRateCat4Info info = new CeilingRateCat4Info();

		for (int j = 0; j < CeilingRateCat4Info.NumOfWorkingLocation; j++){
				
				// Standard Staff Rate
				for (int k = 1; k < CeilingRateCat4Info.NumOfOneOffStaffCategory; k++){
					info.setOneOffStaffRate(k, j, Double.valueOf(CeilingRateCat4Info.NoOfferDBNumber).doubleValue());
				}
				for (int k = 1; k < CeilingRateCat4Info.NumOfOnGoingStaffCategory; k++){
					info.setOnGoingStaffRate(k, j, Double.valueOf(CeilingRateCat4Info.NoOfferDBNumber).doubleValue());
				}

		}
        
		infos.add(info);
		
		for (int i=1;i<TEST_NUM;i++){
			info = new CeilingRateCat4Info();

			for (int j = 0; j < CeilingRateCat4Info.NumOfWorkingLocation; j++){
					
					// Standard Staff Rate
					for (int k = 1; k < CeilingRateCat4Info.NumOfOneOffStaffCategory; k++){
						info.setOneOffStaffRate(k, j, Double.valueOf(i*100+j*k).doubleValue());
					}
					for (int k = 1; k < CeilingRateCat4Info.NumOfOnGoingStaffCategory; k++){
						info.setOnGoingStaffRate(k, j, Double.valueOf(i*100+j*k).doubleValue());
				}

			}
			
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
			CeilingRateCat4Info info = infos.get(i);
			CeilingRateCat4Info info2 = infos.get(i+1);
			info.CheckCeilingRate(info2);
			
			for (int j = 0; j < CeilingRateCat4Info.NumOfWorkingLocation; j++){
					
					for (int k = 1; k < CeilingRateCat4Info.NumOfOneOffStaffCategory; k++){
						assertEquals(info.NotExceedString,info.getOneOffStaffRateExceed(k,j));
					}
					for (int k = 1; k < CeilingRateCat4Info.NumOfOnGoingStaffCategory; k++){
						assertEquals(info.NotExceedString,info.getOnGoingStaffRateExceed(k,j));
					}
				
			}	
        }
    }
    
    @Test
    public void testCheckCeilingRateExceed()
    {
    	// Should Exceed the smaller DB ceiling rates
    	for (int i=1;i<TEST_NUM-1;i++){
			CeilingRateCat4Info info = infos.get(i+1);
			CeilingRateCat4Info info2 = infos.get(i);
			info.CheckCeilingRate(info2);
			
			for (int j = 0; j < CeilingRateCat4Info.NumOfWorkingLocation; j++){
				
					for (int k = 1; k < CeilingRateCat4Info.NumOfOneOffStaffCategory; k++){
						assertEquals(info.ExceedString,info.getOneOffStaffRateExceed(k,j));
					}
					for (int k = 1; k < CeilingRateCat4Info.NumOfOnGoingStaffCategory; k++){
						assertEquals(info.ExceedString,info.getOnGoingStaffRateExceed(k,j));
					}
				
			}	
        }
    }
    
    @Test
    public void testCheckCeilingRateNoOffer()
    {
    	// For No offer ceiling rates
    	CeilingRateCat4Info info = infos.get(3);
		CeilingRateCat4Info info2 = infos.get(0);
		info.CheckCeilingRate(info2);
		for (int j = 1; j < CeilingRateCat4Info.NumOfWorkingLocation ; j++){
				
				for (int k = 1; k < CeilingRateCat4Info.NumOfOneOffStaffCategory; k++){
					assertEquals(info.NoOfferString,info.getOneOffStaffRateExceed(k,j));
				}
				for (int k = 1; k < CeilingRateCat4Info.NumOfOnGoingStaffCategory; k++){
					assertEquals(info.NoOfferString,info.getOnGoingStaffRateExceed(k,j));
				}
			
		}	
    }
    
    @Test
    public void testOnGoingStaffRateTableField0()
    {
    	CeilingRateCat4Info info;
    	int workingLocation = 0;
    	
    	for (int i=0; i<TEST_NUM; i++){
    			info = infos.get(i);
    			for (int sc=1;sc<5;sc++){
    				assertEquals("ongoing_sc" + sc + "_office_hours",info.toOnGoingStaffRateTableField(sc-1, workingLocation));
    			}
    	}
    }
    
    @Test
    public void testStdOneOffStaffRateTableField0()
    {
    	CeilingRateCat4Info info;
    	int workingLocation = 0;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		for (int sc=1;sc<6;sc++){
    			assertEquals("one_off_sc" + sc + "_resident",info.toOneOffStaffRateTableField(sc-1, workingLocation));
    		}
    	}
    }
    
    @Test
    public void testStdOnGoingStaffRateTableField1()
    {
    	String outcome;
    	CeilingRateCat4Info info;
    	int workingLocation = 1;
    	
    	for (int i=0; i<TEST_NUM; i++){
    			info = infos.get(i);
    			for (int sc=1;sc<5;sc++){
    				assertEquals("ongoing_sc" + sc + "_non_office_hours",info.toOnGoingStaffRateTableField(sc-1, workingLocation));
    			}
    	}
    }
    
    @Test
    public void testStdOneOffStaffRateTableField1()
    {
    	CeilingRateCat4Info info;
    	int workingLocation = 1;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		for (int sc=1;sc<6;sc++){
    			assertEquals("one_off_sc" + sc + "_non_resident",info.toOneOffStaffRateTableField(sc-1, workingLocation));
    		}
    	}
    }
    
    @Test
    public void testStdOnGoingStaffRateTableField2()
    {
    	String outcome;
    	CeilingRateCat4Info info;
    	int workingLocation = 2;
    	
    	for (int i=0; i<TEST_NUM; i++){
    			info = infos.get(i);
    			for (int sc=1;sc<5;sc++){
    				assertEquals("ongoing_sc" + sc + "_round_the_clock",info.toOnGoingStaffRateTableField(sc-1, workingLocation));
    			}
    	}
    }
    
    @Test
    public void testStdOneOffStaffRateTableField2()
    {
    	CeilingRateCat4Info info;
    	int workingLocation = 2;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		for (int sc=1;sc<6;sc++){
    			assertEquals("one_off_sc" + sc + "_offshore",info.toOneOffStaffRateTableField(sc-1, workingLocation));
    		}
    	}
    }
    
}
