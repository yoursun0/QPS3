package qpses.business;

import static org.junit.Assert.*;

import org.junit.*;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.*;

public class PdfHelperTest {

private WAChallengeInfo info;
	
	private String user = "henry.ogcio";
    private String waTypeStr = "Proposal Evaluation";
    
    private PdfHelper pdfHelper;
    private Document aPdfDoc;
	private PdfWriter writer;
	
	@Before
    public void setUp() throws DocumentException {
		
		// Initialize PDF helper
	    pdfHelper = new PdfHelper();
	    pdfHelper.setDisplayUserName(user);
	    pdfHelper.setFooterText("RESTRICTED (TENDER)");
	 
    }

    @After
    public void tearDown() {
    	pdfHelper = null;
        aPdfDoc = null;
    	writer = null;
    }

    @Test
    public void testOpenDocument() {    	
    	
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	try
        {
    		// Initialize PDF document
            aPdfDoc = new Document(PageSize.A4);
            writer = PdfWriter.getInstance(aPdfDoc, baos);
            writer.setPageEvent(pdfHelper);
            pdfHelper.setHeaderText("RESTRICTED (TENDER)" + "\n" + "Validation of Proposed Staff Rates for " + waTypeStr);
            aPdfDoc.setMargins(50, 50, 100, 100);
            aPdfDoc.open();
            
    		pdfHelper.onOpenDocument(writer, aPdfDoc);	
        }
        catch (Exception e)
        {
            fail("Expected exception encountered" +e);
        }
    }
    
    @Test(expected = com.lowagie.text.ExceptionConverter.class)
    public void testOpenDocumentException() {    	
    	// Initialize PDF document
        aPdfDoc = new Document(PageSize.A4);
    	pdfHelper.onOpenDocument(writer, aPdfDoc);	
    		
    }

}
