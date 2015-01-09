package qpses.business;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;

import java.util.*;

public class CeilingRateCat3InfoTest {
	
	private final int TEST_NUM = 9;
	private List<CeilingRateCat3Info> infos;
	private String user = "henry.ogcio";
    private String waTypeStr = "Proposal Evaluation";
    private String outcome;
    
	@Before
    public void setUp(){
		
		// Initialize Ceiling Rate Info
		infos = new ArrayList<CeilingRateCat3Info>();
		int add = 0;
		

		CeilingRateCat3Info info = new CeilingRateCat3Info();

		for (int j = 0; j < CeilingRateCat3Info.NumOfWorkingLocation; j++){
			for (int m = 1; m < CeilingRateCat3Info.NumOfMScheme; m++){
				
				// Standard Staff Rate
				for (int k = 1; k < CeilingRateCat3Info.NumOfOneOffStaffCategory; k++){
					info.setStdOneOffStaffRate(k, j, Double.valueOf(CeilingRateCat3Info.NoOfferDBNumber).doubleValue());
				}
				for (int k = 1; k < CeilingRateCat3Info.NumOfOnGoingStaffCategory; k++){
					info.setStdOnGoingStaffRate(k, j, m, Double.valueOf(CeilingRateCat3Info.NoOfferDBNumber).doubleValue());
				}

				// Supplementary Staff Rate
				info.setSupOneOffStaffRate(j, Double.valueOf(CeilingRateCat3Info.NoOfferDBNumber).doubleValue());
				info.setSupOnGoingStaffRate(j, m, Double.valueOf(CeilingRateCat3Info.NoOfferDBNumber).doubleValue());
			}
		}
        
		infos.add(info);
		
		for (int i=1;i<TEST_NUM;i++){
			info = new CeilingRateCat3Info();

			for (int j = 0; j < CeilingRateCat3Info.NumOfWorkingLocation; j++){
				for (int m = 1; m < CeilingRateCat3Info.NumOfMScheme; m++){
					
					// Standard Staff Rate
					for (int k = 1; k < CeilingRateCat3Info.NumOfOneOffStaffCategory; k++){
						info.setStdOneOffStaffRate(k, j, Double.valueOf(i*100+j*k*m).doubleValue());
					}
					for (int k = 1; k < CeilingRateCat3Info.NumOfOnGoingStaffCategory; k++){
						info.setStdOnGoingStaffRate(k, j, m, Double.valueOf(i*100+j*k*m).doubleValue());
					}

					// Supplementary Staff Rate
					info.setSupOneOffStaffRate(j, Double.valueOf(i*100+j*m).doubleValue());
					info.setSupOnGoingStaffRate(j, m, Double.valueOf(i*100+j*m).doubleValue());
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
			CeilingRateCat3Info info = infos.get(i);
			CeilingRateCat3Info info2 = infos.get(i+1);
			info.CheckCeilingRate(info2);
			
			for (int j = 0; j < CeilingRateCat3Info.NumOfWorkingLocation; j++){
				for (int m = 1; m < CeilingRateCat3Info.NumOfMScheme; m++){
					
					// Supplementary Staff Rate
					assertEquals(info.NotExceedString,info.getSupOneOffStaffRateExceed(j));
					assertEquals(info.NotExceedString,info.getSupOnGoingStaffRateExceed(j,m));
					
					// Standard Staff Rate
					for (int k = 1; k < CeilingRateCat3Info.NumOfOneOffStaffCategory; k++){
						assertEquals(info.NotExceedString,info.getStdOneOffStaffRateExceed(k,j));
					}
					for (int k = 1; k < CeilingRateCat3Info.NumOfOnGoingStaffCategory; k++){
						assertEquals(info.NotExceedString,info.getStdOnGoingStaffRateExceed(k,j,m));
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
			CeilingRateCat3Info info = infos.get(i+1);
			CeilingRateCat3Info info2 = infos.get(i);
			info.CheckCeilingRate(info2);
			
			for (int j = 0; j < CeilingRateCat3Info.NumOfWorkingLocation; j++){
				for (int m = 1; m < CeilingRateCat3Info.NumOfMScheme; m++){
				
					// Supplementary Staff Rate
					assertEquals(info.ExceedString,info.getSupOneOffStaffRateExceed(j));
					assertEquals(info.ExceedString,info.getSupOnGoingStaffRateExceed(j,m));
					
					// Standard Staff Rate
					for (int k = 1; k < CeilingRateCat3Info.NumOfOneOffStaffCategory; k++){
						assertEquals(info.ExceedString,info.getStdOneOffStaffRateExceed(k,j));
					}
					for (int k = 1; k < CeilingRateCat3Info.NumOfOnGoingStaffCategory; k++){
						assertEquals(info.ExceedString,info.getStdOnGoingStaffRateExceed(k,j,m));
					}
				}
			}	
        }
    }
    
    @Test
    public void testCheckCeilingRateNoOffer()
    {
    	// For No offer ceiling rates
    	CeilingRateCat3Info info = infos.get(3);
		CeilingRateCat3Info info2 = infos.get(0);
		info.CheckCeilingRate(info2);
		for (int j = 1; j < CeilingRateCat3Info.NumOfWorkingLocation ; j++){
			for (int m = 1; m < CeilingRateCat3Info.NumOfMScheme; m++){
				
				// Supplementary Staff Rate
				assertEquals(info.NoOfferString,info.getSupOneOffStaffRateExceed(j));
				assertEquals(info.NoOfferString,info.getSupOnGoingStaffRateExceed(j,m));
				
				// Standard Staff Rate
				for (int k = 1; k < CeilingRateCat3Info.NumOfOneOffStaffCategory; k++){
					assertEquals(info.NoOfferString,info.getStdOneOffStaffRateExceed(k,j));
				}
				for (int k = 1; k < CeilingRateCat3Info.NumOfOnGoingStaffCategory; k++){
					assertEquals(info.NoOfferString,info.getStdOnGoingStaffRateExceed(k,j,m));
				}
			}
		}	
    }
    
    @Test
    public void testSupOnGoingStaffRateTableField0()
    {
    	CeilingRateCat3Info info;
    	int workingLocation = 0;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 0; m < CeilingRateCat3Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			assertEquals("ongoing_supplementary_1_resident_m"+(m+1),info.toSupOnGoingStaffRateTableField(workingLocation,m));
    		}
    	}
    }
    
    @Test
    public void testSupOneOffStaffRateTableField0()
    {
    	CeilingRateCat3Info info;
    	int workingLocation = 0;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		assertEquals("one_off_supplementary_1_resident",info.toSupOneOffStaffRateTableField(workingLocation));
    	}
    }
    
    @Test
    public void testSupOnGoingStaffRateTableField1()
    {
    	CeilingRateCat3Info info;
    	int workingLocation = 1;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 0; m < CeilingRateCat3Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			assertEquals("ongoing_supplementary_1_non_resident_m"+(m+1),info.toSupOnGoingStaffRateTableField(workingLocation,m));
    		}	
    	}
    }
    
    @Test
    public void testSupOneOffStaffRateTableField1()
    {
    	CeilingRateCat3Info info;
    	int workingLocation = 1;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		assertEquals("one_off_supplementary_1_non_resident",info.toSupOneOffStaffRateTableField(workingLocation));
    	}
    }
    
    @Test
    public void testSupOnGoingStaffRateTableField2()
    {
    	CeilingRateCat3Info info;
    	int workingLocation = 2;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 0; m < CeilingRateCat3Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			assertEquals("ongoing_supplementary_1_offshore_m"+(m+1),info.toSupOnGoingStaffRateTableField(workingLocation,m));
    		}
    	}
    }
    
    @Test
    public void testSupOneOffStaffRateTableField2()
    {
    	CeilingRateCat3Info info;
    	int workingLocation = 2;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		assertEquals("one_off_supplementary_1_offshore",info.toSupOneOffStaffRateTableField(workingLocation));
    	}
    }
    
    @Test
    public void testStdOnGoingStaffRateTableField0()
    {
    	CeilingRateCat3Info info;
    	int workingLocation = 0;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 1; m < CeilingRateCat3Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			for (int sc=3;sc<info.NumOfOnGoingStaffCategory;sc++){
    				assertEquals("ongoing_sc" + sc + "_resident_m"+(m+1),info.toStdOnGoingStaffRateTableField(sc-1, workingLocation,m));
    			}
    		}
    	}
    }
    
    @Test
    public void testStdOneOffStaffRateTableField0()
    {
    	CeilingRateCat3Info info;
    	int workingLocation = 0;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		for (int sc=3;sc<info.NumOfOneOffStaffCategory;sc++){
    			assertEquals("one_off_sc" + sc + "_resident",info.toStdOneOffStaffRateTableField(sc-1, workingLocation));
    		}
    	}
    }
    
    @Test
    public void testStdOnGoingStaffRateTableField1()
    {
    	String outcome;
    	CeilingRateCat3Info info;
    	int workingLocation = 1;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 1; m < CeilingRateCat3Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			for (int sc=3;sc<info.NumOfOnGoingStaffCategory;sc++){
    				assertEquals("ongoing_sc" + sc + "_non_resident_m"+(m+1),info.toStdOnGoingStaffRateTableField(sc-1, workingLocation,m));
    			}
    		}
    	}
    }
    
    @Test
    public void testStdOneOffStaffRateTableField1()
    {
    	CeilingRateCat3Info info;
    	int workingLocation = 1;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		for (int sc=3;sc<info.NumOfOneOffStaffCategory;sc++){
    			assertEquals("one_off_sc" + sc + "_non_resident",info.toStdOneOffStaffRateTableField(sc-1, workingLocation));
    		}
    	}
    }
    
    @Test
    public void testStdOnGoingStaffRateTableField2()
    {
    	String outcome;
    	CeilingRateCat3Info info;
    	int workingLocation = 2;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		for (int m = 1; m < CeilingRateCat3Info.NumOfMScheme; m++){
    			info = infos.get(i);
    			for (int sc=3;sc<info.NumOfOnGoingStaffCategory;sc++){
    				assertEquals("ongoing_sc" + sc + "_offshore_m"+(m+1),info.toStdOnGoingStaffRateTableField(sc-1, workingLocation,m));
    			}
    		}
    	}
    }
    
    @Test
    public void testStdOneOffStaffRateTableField2()
    {
    	CeilingRateCat3Info info;
    	int workingLocation = 2;
    	
    	for (int i=0; i<TEST_NUM; i++){
    		info = infos.get(i);
    		for (int sc=3;sc<info.NumOfOneOffStaffCategory;sc++){
    			assertEquals("one_off_sc" + sc + "_offshore",info.toStdOneOffStaffRateTableField(sc-1, workingLocation));
    		}
    	}
    }
    
}
