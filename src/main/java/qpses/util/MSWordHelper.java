/**********************************************************************************
 * Author            : Owen Mang, Master Concept HK Ltd.
 * Version           : 1.0
 * Create Date       : Aug 12, 2013
 * Last Updated Date : Aug 12, 2013
 *********************************************************************************/
package qpses.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

public class MSWordHelper {

	private static Logger logger = Logger.getLogger(MSWordHelper.class);
	
	private Map<String, String> replacements = null;
	private String inputFilePath = null; 
	private String outputFilePath = null;
	private boolean isNewWordCreated = false;
	private String pdfFilePath = null;
	private String imgFilePath = null;
	

	/**
	 * @return created Word document path
	 * @throws SysException
	 */
	public String getOutputFilePath() throws SysException {
		
		if (!isNewWordCreated)
			throw new SysException(this.getClass().getName() 
					+ ".getOutputFilePath() : Document Conversion is not yet process");
		
		return outputFilePath;
	}

	/*
	private void generatePDF() throws SysException {
		
		if (!isNewWordCreated)
			throw new SysException(this.getClass().getName() 
					+ ".generatePDF() : Document Conversion is not yet process. PDF cannot be generated");
		
		//Generate PDF coding
		Document pdfDocument = new Document();
		try {
			pdfFilePath = outputFilePath.replace(".doc", ".pdf");
			FileOutputStream fileOutputStream = new FileOutputStream(pdfFilePath);
			PdfWriter writer = null;
			writer = PdfWriter.getInstance(pdfDocument, fileOutputStream);
			writer.open();
			pdfDocument.open();
			
			File file = new File(outputFilePath);
			pdfDocument.add(new com.lowagie.text.Paragraph
					(org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8")));
			
			pdfDocument.close();
			writer.close();
		} catch (Exception ex) {
			pdfFilePath = null;
			throw new SysException(this.getClass().getName() 
					+ ".generatePDF() : " + ex.getMessage());
		}
		
	}*/

	/*
	private void generateImg() throws SysException {
		if (!isNewWordCreated)
			throw new SysException(this.getClass().getName() 
					+ ".generateImg() : Document Conversion is not yet process. Img cannot be generated");
		
		//Generate PDF first.
		if (pdfFilePath == null)
			generatePDF();
		
		//Generate Img Coding
		
		
	}*/
	

    /**
     * @return Word document in HTML code
     * @throws SysException
     */
    public String getHtmlText() throws SysException
    {
    	if (!isNewWordCreated)
    		throw new SysException(this.getClass().getName() 
    				+ ".getHtmlText() : Document Conversion is not yet process. HTML Code cannot be generated");
    	
    	String result = "";
    	File inputFile = null;
    	FileInputStream fileIStream = null;
    	BufferedInputStream bufIStream = null;
    	POIFSFileSystem fileSystem = null;
    	HWPFDocument hwpfDocument = null;
    	org.w3c.dom.Document newDocument = null;
    	WordToHtmlConverter wordToHtmlConverter = null;
    	StringWriter stringWriter = null;
    	Transformer transformer = null;
    	
    	try {
	    	inputFile = new File(outputFilePath);
	    	fileIStream = new FileInputStream(inputFile);
	    	bufIStream = new BufferedInputStream(fileIStream);
	    	fileSystem = new POIFSFileSystem(bufIStream);
	    	hwpfDocument = new HWPFDocument(fileSystem);
	
	    	newDocument = DocumentBuilderFactory.newInstance()
	                .newDocumentBuilder().newDocument();
	        wordToHtmlConverter = new WordToHtmlConverter(
	                newDocument );
	
	        wordToHtmlConverter.processDocument( hwpfDocument );
	
	        stringWriter = new StringWriter();
	
	        transformer = TransformerFactory.newInstance()
	                .newTransformer();
	        transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
	        transformer.setOutputProperty( OutputKeys.ENCODING, "utf-8" );
	        transformer.setOutputProperty( OutputKeys.METHOD, "html" );
	        transformer.transform(
	                new DOMSource( wordToHtmlConverter.getDocument() ),
	                new StreamResult( stringWriter ) );
	        
	        result = stringWriter.toString();
	        
    	} catch (Exception ex) {
    		String err = this.getClass().getName() 
    				+ ".getHtmlText() : " + ex.getMessage();
    		logger.error(err,ex);
    		throw new SysException(err);
    	} finally {
    		try {
	    		stringWriter.close();
	    		stringWriter = null;
    		} catch (Exception ex2) {
    			String err = this.getClass().getName() 
	    				+ ".getHtmlText() : " + ex2.getMessage();
	    		logger.error(err,ex2);
	    		throw new SysException(err);
	    	}
	    	try {
	    		bufIStream.close();
	    		bufIStream = null;
	    	} catch (Exception ex3) {
	    		String err = this.getClass().getName() 
	    				+ ".getHtmlText() : " + ex3.getMessage();
	    		logger.error(err,ex3);
	    		throw new SysException(err);
	    	}
	
	    	try {
	    		fileIStream.close();
	    		fileIStream = null;
	    	} catch (Exception ex4) {
	    		String err = this.getClass().getName() 
	    				+ ".getHtmlText() : " + ex4.getMessage();
	    		logger.error(err,ex4);
	    		throw new SysException(err);
	    	}
    	}

        return result;
    }

    /**
     * Constructor for MSWordHelper
     */
	public MSWordHelper(String inputFileName, String outputFileName, Map<String, String> replaces) {
		this.inputFilePath = inputFileName;
		this.outputFilePath = outputFileName;
		this.replacements = replaces;
	}
	
	/**
	 * Method to free memory
	 * @throws SysException
	 */
	public void close() throws SysException{
		
		//Clear Map
		if (replacements != null) {
			replacements.clear();
			replacements = null;
		}
		
		File tempFile = null;
		//Delete output file
		try {
			tempFile = new File(outputFilePath);
			if (tempFile.exists() && tempFile.isFile()) {
				tempFile.delete();
			}
			
		} catch (Exception ex) {
			String err = this.getClass().getName() 
					+ ".close() : " + ex.getMessage();
    		logger.error(err,ex);
    		throw new SysException(err);
		}
		
		/* 
		 * //Delete PDF file if exist
		try {
			tempFile = new File(pdfFilePath);
			if (tempFile.exists() && tempFile.isFile()) {
				tempFile.delete();
			}
			
		} catch (Exception ex) {
    		throw new SysException(this.getClass().getName() 
					+ ".close() : " + ex.getMessage());
		}
		
		//Delete img file if exist
		try {
			tempFile = new File(imgFilePath);
			if (tempFile.exists() && tempFile.isFile()) {
				tempFile.delete();
			}
			
		} catch (Exception ex) {
    		throw new SysException(this.getClass().getName() 
					+ ".close() " + ex.getMessage());
		}
		*/
		
	}
	
	
	/**
	 * Method to process Word Document Generation
	 * @return success or fail
	 * @throws SysException
	 */
	public boolean process() throws SysException {
	
		File outputFile = null;
	    File inputFile = null;
	    FileInputStream fileIStream = null;
	    FileOutputStream fileOStream = null;
	    BufferedInputStream bufIStream = null;
	    BufferedOutputStream bufOStream = null;
	    POIFSFileSystem fileSystem = null;
	    HWPFDocument document = null;
	    Range docRange = null;
	    Paragraph paragraph = null;
	    CharacterRun charRun = null;
	    Set<String> keySet = null;
	    Iterator<String> keySetIterator = null;
	    int numParagraphs = 0;
	    int numCharRuns = 0;
	    String text = null;
	    String key = null;
	    String value = null;
        try {
        	
        	logger.debug("Start processing...");
        	
            // Create an instance of the POIFSFileSystem class and
            // attach it to the Word document using an InputStream.
            inputFile = new File(inputFilePath);
            fileIStream = new FileInputStream(inputFile);
            bufIStream = new BufferedInputStream(fileIStream);
            fileSystem = new POIFSFileSystem(bufIStream);
            document = new HWPFDocument(fileSystem);
            docRange = document.getRange();
            numParagraphs = docRange.numParagraphs();
            keySet = replacements.keySet();
            for (int i = 0; i < numParagraphs; i++) {
                paragraph = docRange.getParagraph(i);
                text = paragraph.text();
                numCharRuns = paragraph.numCharacterRuns();
                for (int j = 0; j < numCharRuns; j++) {
                    charRun = paragraph.getCharacterRun(j);
                    text = charRun.text();
                    
                    keySetIterator = keySet.iterator();
                    while (keySetIterator.hasNext()) {
                        key = keySetIterator.next();
                        if (text.contains(key)) {
                        	
                            value = replacements.get(key);
                            
                        	logger.debug("Found Keyword:" + key + ", replace by:" + value);
                            
                            charRun.replaceText(key, value);
                            docRange = document.getRange();
                            paragraph = docRange.getParagraph(i);
                            charRun = paragraph.getCharacterRun(j);
                            text = charRun.text();
                            
                        }
                    }
                }
            }
            
            bufIStream.close();
            bufIStream = null;
            
            outputFile = new File(outputFilePath);
            fileOStream = new FileOutputStream(outputFile);
            bufOStream = new BufferedOutputStream(fileOStream);
            document.write(bufOStream);
            bufOStream.flush();
            bufOStream.close();            

        } catch (Exception ex) {
        	String err = this.getClass().getName() 
					+ ".process() : " + ex.getMessage();
        	logger.error(err,ex);
        	isNewWordCreated = false;
    		throw new SysException(this.getClass().getName() 
					+ ".process() : " + ex.getMessage());
        } finally {
        	try {
	        	if (bufIStream != null) {
	        		bufIStream.close();
	        		bufIStream = null;
	        	}
        	} catch (Exception ex2) {
        		String err = this.getClass().getName() 
    					+ ".process() : " + ex2.getMessage();
        		logger.error(err,ex2);
        		throw new SysException(err);
        	}
        	try {
	        	if  (bufOStream != null) {
	        		bufOStream.close();
	        		bufOStream = null; 
	        	}
        	} catch (Exception ex3) {
        		String err = this.getClass().getName() 
    					+ ".process() : " + ex3.getMessage();
        		logger.error(err,ex3);
        		throw new SysException(err);
        	}
        	try {
	        	if (fileOStream != null) {
	        		fileOStream.close();
	        		fileOStream = null;
	        	}
        	} catch (Exception ex4) {
        		String err = this.getClass().getName() 
    					+ ".process() : " + ex4.getMessage();
        		logger.error(err,ex4);
        		throw new SysException(err);
        	}
        	try {
	        	if (fileIStream != null) {
	        		fileIStream.close();
	        		fileIStream = null;
	        	}
        	} catch (Exception ex5) {
        		String err = this.getClass().getName() 
    					+ ".process() : " + ex5.getMessage();
        		logger.error(err,ex5);
        		throw new SysException(err);
        	}
        }
        logger.debug("Process end.");
        isNewWordCreated = true;
        return isNewWordCreated;
	}
	
	
	/*public static void main(String[] args) throws SysException {
		
		// TODO Auto-generated method stub
		String inputFilename = "/Users/raku/Documents/workspace/QPSIS3/src/main/java/qpses/util/"+Constant.CPAR_TEMPLATE_NAME;
		inputFilename = "/Users/raku/Documents/workspace/QPSIS3/src/main/java/qpses/util/QPS3-CPAR2.doc";
		String outputFilename = "/Users/raku/Documents/workspace/QPSIS3/src/main/java/qpses/util/noproblem1.doc";
		Map<String, String> replaces = new HashMap<String, String>();
		
        replaces.put(Constant.REPORT_MARKER+"1", "rongzhi_li");
        replaces.put(Constant.REPORT_MARKER+"2", new String(""));
        replaces.put(Constant.REPORT_MARKER+"3", "   ");
        replaces.put(Constant.REPORT_MARKER+"4", "   ");
		
        
		MSWordHelper mswdHelper = new MSWordHelper(inputFilename, outputFilename, replaces);
		mswdHelper.process();
		System.out.println(mswdHelper.getHtmlText());
		//mswdHelper.generatePDF();
		
		//Clear Memory and delete output file
		//mswdHelper.close();
	}*/

}
