package qpses.util;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.*;

public class MSWordHelperTest {
	
	private MSWordHelper manager;
	private Map<String, String> replaces = new HashMap<String, String>();
	
	@Before
    public void setUp() {
		String inputFilename = "src/main/java/qpses/util/"+Constant.CPAR_TEMPLATE_NAME;
		String outputFilename = "noproblem1.doc";
		replaces = new HashMap<String, String>();
		
        replaces.put(Constant.REPORT_MARKER+"1", "rongzhi_li");
        replaces.put(Constant.REPORT_MARKER+"2", new String(""));
        replaces.put(Constant.REPORT_MARKER+"3", "   ");
        replaces.put(Constant.REPORT_MARKER+"4", "   ");
		manager = new MSWordHelper(inputFilename, outputFilename, replaces);
    }

    @After
    public void tearDown() {
    	manager = null;
    }
    
    @Test(expected=qpses.util.SysException.class)
    public void testGetHtmlErrorText() throws SysException{   	
    	String htmlTest = manager.getHtmlText();
    }    
    
    @Test
    public void testProcess(){   
    	
    	boolean isSucess = false;
   
		try {
			isSucess = manager.process();
			
			assertTrue(isSucess);
			
		} catch (SysException e) {
			//e.printStackTrace();
			fail("Process Error, encounter unexpected SysException "+e);
		}
    	
    }
    
    @Test
    public void testGetHtmlText(){   
    	
    	try {
    	
    		String htmlTest = manager.getHtmlText();
    		
    		assertNotNull(htmlTest);
    	} catch (SysException e) {
			//e.printStackTrace();
			
		}
    	
    } 
    
    @Test
    public void testGetOutputFilePath(){   
    	
    	try {
    	
    		String path = manager.getOutputFilePath();
    		
    		assertNotNull(path);
    	} catch (SysException e) {
			//e.printStackTrace();
			
		}
    	
    }    
    
    @Test
    public void testClose() throws SysException{   
    	manager.close();
    }   
    
}
