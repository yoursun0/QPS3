package qpses.business;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;

import java.util.*;

public class CeilingRateCat2InfoTest {
	
	private final int TEST_NUM = 9;
	private List<CeilingRateCat2Info> infos;
	private String user = "henry.ogcio";
    private String waTypeStr = "Proposal Evaluation";
    private String outcome;
    
	@Before
    public void setUp(){
		
		// Initialize Ceiling Rate Info
		infos = new ArrayList<CeilingRateCat2Info>();
		int add = 0;
		

		CeilingRateCat2Info info = new CeilingRateCat2Info();

		for (int j = 0; j < CeilingRateCat2Info.NumOfWorkingLocation; j++){
			for (int m = 1; m < CeilingRateCat2Info.NumOfMScheme; m++){
				
				for (int k = 1; k < CeilingRateCat2Info.NumOfStaffCategory; k++){

					// Standard Staff Rate
					info.setStdStaffRate(k, j, m, Double.valueOf(CeilingRateCat2Info.NoOfferDBNumber).doubleValue());
				}
				// Supplementary Staff Rate
				info.setSupStaffRate(j, m, Double.valueOf(CeilingRateCat2Info.NoOfferDBNumber).doubleValue());
			}
		}
        
		infos.add(info);
		
		for (int i=1;i<TEST_NUM;i++){
			info = new CeilingRateCat2Info();

			for (int j = 0; j < CeilingRateCat2Info.NumOfWorkingLocation; j++){
				for (int m = 1; m < CeilingRateCat2Info.NumOfMScheme; m++){
					
					info.setStdStaffRate(0, j, m, Double.valueOf(CeilingRateCat2Info.NoOfferDBNumber).doubleValue());
					for (int k = 0; k < CeilingRateCat2Info.NumOfStaffCategory; k++){
						// Standard Staff Rate
						info.setStdStaffRate(k, j, m, Double.valueOf(j*k*m+add).doubleValue());
					}
					// Supplementary Staff Rate
					info.setSupStaffRate(j, m, Double.valueOf(j*m+add).doubleValue());
				}
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
			CeilingRateCat2Info info = infos.get(i);
			CeilingRateCat2Info info2 = infos.get(i+1);
			info.CheckCeilingRate(info2);
			
			for (int j = 0; j < CeilingRateCat2Info.NumOfWorkingLocation; j++){
				for (int m = 1; m < CeilingRateCat2Info.NumOfMScheme; m++){
					
					assertEquals(info.NotExceedString,info.getSupStaffRateExceed(j,m));
					for (int k = 1; k < CeilingRateCat2Info.NumOfStaffCategory; k++){
						// Standard Staff Rate
						assertEquals(info.NotExceedString,info.getStdStaffRateExceed(k,j,m));
					}
				}
			}	
        }
    }
    
    @Test
    public void testCheckCeilingRateExceed()
    {
    	// Should Exceed the smaller DB ceiling rates
    	for (int i=1;i<TEST_NUM-1;i++){
			CeilingRateCat2Info info = infos.get(i+1);
			CeilingRateCat2Info info2 = infos.get(i);
			info.CheckCeilingRate(info2);
			
			for (int j = 0; j < CeilingRateCat2Info.NumOfWorkingLocation; j++){
				for (int m = 1; m < CeilingRateCat2Info.NumOfMScheme; m++){
				
					assertEquals(info.ExceedString,info.getSupStaffRateExceed(j,m));
					for (int k = 1; k < CeilingRateCat2Info.NumOfStaffCategory; k++){
						// Standard Staff Rate
						assertEquals(info.ExceedString,info.getStdStaffRateExceed(k,j,m));
					}
				}
			}	
        }
    }
    
    @Test
    public void testCheckCeilingRateNoOffer()
    {
    	// For No offer ceiling rates
    	CeilingRateCat2Info info = infos.get(3);
		CeilingRateCat2Info info2 = infos.get(0);
		info.CheckCeilingRate(info2);
		for (int j = 1; j < CeilingRateCat2Info.NumOfWorkingLocation ; j++){
			for (int k = 1; k < CeilingRateCat2Info.NumOfStaffCategory ; k++){
				for (int m = 1; m < CeilingRateCat2Info.NumOfMScheme; m++){
					
					// Standard Staff Rate
					assertEquals(info.NoOfferString,info.getStdStaffRateExceed(k,j,m));
				}
			}
		}	
    }
    
    @Test
    public void testSupStaffRateTableField0()
    {
    	CeilingRateCat2Info info;
    	int workingLocation = 0;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 0; m < CeilingRateCat2Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			assertEquals("supplementary_1_resident_m"+(m+1),info.toSupStaffRateTableField(workingLocation,m));
    		}
    	}
    }
    
    @Test
    public void testSupStaffRateTableField1()
    {
    	CeilingRateCat2Info info;
    	int workingLocation = 1;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 0; m < CeilingRateCat2Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			assertEquals("supplementary_1_non_resident_m"+(m+1),info.toSupStaffRateTableField(workingLocation,m));
    		}	
    	}
    }
    
    @Test
    public void testSupStaffRateTableField2()
    {
    	CeilingRateCat2Info info;
    	int workingLocation = 2;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 0; m < CeilingRateCat2Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			assertEquals("supplementary_1_offshore_m"+(m+1),info.toSupStaffRateTableField(workingLocation,m));
    		}
    	}
    }
    
    @Test
    public void testStdStaffRateTableField0()
    {
    	CeilingRateCat2Info info;
    	int workingLocation = 0;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 1; m < CeilingRateCat2Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			for (int sc=3;sc<10;sc++){
    				assertEquals("sc" + sc + "_resident_m"+(m+1),info.toStdStaffRateTableField(sc-1, workingLocation,m));
    			}
    		}
    	}
    }
    
    @Test
    public void testStdStaffRateTableField1()
    {
    	String outcome;
    	CeilingRateCat2Info info;
    	int workingLocation = 1;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 1; m < CeilingRateCat2Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			for (int sc=3;sc<10;sc++){
    				assertEquals("sc" + sc + "_non_resident_m"+(m+1),info.toStdStaffRateTableField(sc-1, workingLocation,m));
    			}
    		}
    	}
    }
    
    @Test
    public void testStdStaffRateTableField2()
    {
    	String outcome;
    	CeilingRateCat2Info info;
    	int workingLocation = 2;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 1; m < CeilingRateCat2Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			for (int sc=3;sc<10;sc++){
    				assertEquals("sc" + sc + "_offshore_m"+(m+1),info.toStdStaffRateTableField(sc-1, workingLocation,m));
    			}
    		}
    	}
    }
    
}
