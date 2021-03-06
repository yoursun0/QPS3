package qpses.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jpedal.PdfDecoder;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import qpses.business.*;
import qpses.util.*;
import qpses.security.*;
import qpses.security.SecurityException;

@SuppressWarnings("serial")
public class CPARSLUser extends HttpServlet
{

	private static String userId = null;
	
	// Get class name
    static String MyClassName = CPARSLUser.class.getName();
    
    // Set up Log4J
    static Logger logger = Logger.getLogger(MyClassName);
    
    // User web page base URL
    public static final String UserBaseUrl = "/qpsuser/";

    private static final int BYTES_DOWNLOAD = 1024;
    
    private static String tick = "X";
    private static String space = " ";

    
    private static String requestAction;
    
    public SecurityDataBean getSecurityDataBean() throws SysException{
    	return new SecurityDataBean();
    }
    
    public CPARDataBean getCPARDataBean() throws SysException{
    	return new CPARDataBean();
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        doPost(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "doPost" + "]";
        logger.debug(myName + " " + "started");
        
        String requestAction = "";
        
        try
        {
        	requestAction = request.getServletPath();
            
            if (requestAction == null) requestAction = "";
            
            logger.debug(myName + " " + "Request Action = " + requestAction);
            
            if (requestAction.equals(UserBaseUrl+"CPARSLUser"))
            { this.searchCPAR(request, response); }
            else if (requestAction.equals(UserBaseUrl+"CPARList.servlet"))
            { this.listCPAR(request, response); }
            else if (requestAction.equals(UserBaseUrl+"CPARCreate.servlet"))
            { this.createCPAR(request, response); }
            else if (requestAction.equals(UserBaseUrl+"CPARUpdate.servlet"))
            { this.updateCPAR(request, response); }
            else if (requestAction.equals(UserBaseUrl+"CPARSave.servlet"))
            { this.saveCPAR(request, response); }
            else if (requestAction.equals(UserBaseUrl+"CPARDelete.servlet"))
            { this.deleteCPAR(request, response); }
            else if (requestAction.equals(UserBaseUrl+"CPARPrint.servlet"))
            { this.printCPAR(request, response); }
            else if (requestAction.equals(UserBaseUrl+"CPARConfirmDelete.servlet"))
            { this.confirmDeleteCPAR(request, response); }
            else	
            { throw new SysException(myName, "[" + requestAction + "]" + " " + "is not allowed."); }
        }
        catch (Exception ex)
        { 
        	String error =
					this.getClass().getName() + ":doPost\n"
                    + ex.getMessage();
            logger.error(error, ex);
        	throw new ServletException(ex.getMessage()); 
        }
        
        logger.debug(myName + " " + "ended");
    }
    
    private void searchCPAR(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SysException
    {
        String myName = "[" + MyClassName + "." + "searchCPAR" + "]";
        logger.debug(myName + " " + "started");
        
        String url = UserBaseUrl + "CPARSearch.jsp";
        
        // Audit Log for CPAR challenge
        String functionID = "CPAR_CHALLENGE";
        SecurityDataBean secDB = getSecurityDataBean();
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        secDB.insertChallengeLog(secCtx.getUserId(), secCtx.getDPDeptId(), secCtx.getSOADeptId(), functionID, SecurityServlet.challengeQuestion[0], "", "", "", "Add", "CPAR", 0, secCtx.getSOADeptId());
        
        request.getSession().setAttribute("WA_TYPE", "cpar");
        
        logger.debug(myName + " " + "Forward to URL: " + url);
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        
        logger.debug(myName + " " + "ended");
    }
    
    
    private void listCPAR(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
    	String myName = "[" + MyClassName + "." + "listCPAR" + "]";
    	logger.debug(myName + " " + "started");
    	        
    	String url = "";
    	
    	String waType = (String) request.getSession().getAttribute("WA_TYPE");

    	if (("cpar").equals(waType)){
    		url = "/qpsuser/" + "CPARList.jsp";
    	} else {
    		url = "/qpsadmin/" + "CPARList.jsp";
    	}
    			
    	List<CPARInfo> allCPARList = new ArrayList<CPARInfo>();
    	WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
        if (wac == null)  {
        	throw new ServletException("Unable to get work assignment info.");
        }
    	
		try {
			CPARDataBean cparDB = getCPARDataBean();
			allCPARList = cparDB.selectCparByWac(wac);
		} catch (SysException e) {
			String error =
        			"Servlet ERROR: CPARSLUser:listCPAR\n"
                    + e.getMessage();
			logger.error(error,e);
			throw new ServletException(e.getMessage());
		}
		
        request.getSession().setAttribute("CPAR_List", allCPARList);
    	
    	logger.debug(myName + " " + "Forward to URL: " + url);
    	        
    	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
    	requestDispatcher.forward(request, response);
    	        
    	logger.debug(myName + " " + "ended");
    }
    
    private void createCPAR(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {
       	String myName = "[" + MyClassName + "." + "createCPAR" + "]";
       	logger.debug(myName + " " + "started");
        	        
       	String url = UserBaseUrl + "CPARUpdate.jsp";
       	
       	
       	// Get user information
        WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
        CPARInfo cpar = new CPARInfo();
        cpar.setServiceCategoryGroup(wac.getServiceCategoryGroup());
        cpar.setServiceContractNo(wac.getServiceContractNo());
        cpar.setProjectFilePart(wac.getFilePartNo());
        cpar.setProjectFileNo(wac.getFileNo());
        cpar.setDepartmentId(wac.getDepartmentId());
        
        String nextDate = "";
       	try {
			CPARDataBean cparDB = getCPARDataBean();
			cpar.setCparNo(cparDB.selectNextCparNo(wac));
			nextDate = cparDB.selectNextCparDate(wac);
		} catch (SysException e) {
			String error =
					this.getClass().getName() + ":createCPAR\n"
                    + e.getMessage();
            logger.error(error, e);
			throw new ServletException(e.getMessage());
		}
    	    	        
   		request.getSession().setAttribute("CPAR_MSGTYPE","CREATE");
       	request.getSession().setAttribute("QPSES_CPAR", cpar);
       	request.getSession().setAttribute("CPAR_NEXTDATE", nextDate);
       	
       	logger.debug(myName + " " + "Forward to URL: " + url);
        	        
       	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
       	requestDispatcher.forward(request, response);
    	    	        
       	logger.debug(myName + " " + "ended");
    }
    
    private void updateCPAR(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {
       	String myName = "[" + MyClassName + "." + "updateCPAR" + "]";
       	logger.debug(myName + " " + "started");
        	        
       	String url = UserBaseUrl + "CPARUpdate.jsp";
       	int cparNo = SysManager.getIntValue(request.getParameter("cparNo"));
       	
       	// Get user information
        WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
        CPARInfo cpar = new CPARInfo();
        
        String nextDate = "";
        
       	try {
			CPARDataBean cparDB = getCPARDataBean();
			cpar = cparDB.selectCparByCparNo(wac,cparNo);
			nextDate = SysManager.getStringfromSQLDate(cpar.getStartDate());
		} catch (SysException ex) {
			String error =
                    "DB ERROR: CPARDataBean:selectCparByCparNo\n"
                    + ex.getMessage();
            logger.error(error, ex);
			throw new ServletException(ex.getMessage());
		}
       	 
   		request.getSession().setAttribute("CPAR_MSGTYPE","UPDATE");
       	request.getSession().setAttribute("QPSES_CPAR", cpar);
       	request.getSession().setAttribute("CPAR_NEXTDATE",nextDate);
    	    	
       	logger.debug(myName + " " + "Forward to URL: " + url);
        	        
       	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
       	requestDispatcher.forward(request, response);
    	    	        
       	logger.debug(myName + " " + "ended");
    }
    
    private void saveCPAR(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {
       	String myName = "[" + MyClassName + "." + "saveCPAR" + "]";
       	logger.debug(myName + " " + "started");
        
       	List<CPARInfo> allCPARList = new ArrayList<CPARInfo>();
       	WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
       	
       	CPARInfo cpar = (CPARInfo) request.getSession().getAttribute("QPSES_CPAR");
       	// Get user information
        SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
        int result = -1;
        boolean isCreate = ("CREATE".equals(request.getSession().getAttribute("CPAR_MSGTYPE")))? true:false;
        
        
       	try {
			cpar.setStartDate(SysManager.getSQLDate(request.getParameter("IssuedDate")));
			cpar.setEndDate(SysManager.getSQLDate(request.getParameter("ClosingDate")));
			cpar.setStatus(SysManager.getValue(request.getParameter("CparStatus")));
			cpar.setAuthorizedPerson(SysManager.getValue(request.getParameter("AuthorizedPerson")));
			cpar.setPostRank(SysManager.getValue(request.getParameter("PostRank")));
			cpar.setProjectFilePart(wac.getFilePartNo());
			cpar.setProjectFileNo(wac.getFileNo());
			
			cpar.setA1Score(SysManager.getIntValue(request.getParameter("table1q1")));
			cpar.setA2Score(SysManager.getIntValue(request.getParameter("table1q2")));
			cpar.setA3Score(SysManager.getIntValue(request.getParameter("table1q3")));
			cpar.setA4Score(SysManager.getIntValue(request.getParameter("table1q4")));
			cpar.setA5Score(SysManager.getIntValue(request.getParameter("table1q5")));
			cpar.setA6Score(SysManager.getIntValue(request.getParameter("table1q6")));
			cpar.setA7Score(SysManager.getIntValue(request.getParameter("table1q7")));
			cpar.setA8Score(SysManager.getIntValue(request.getParameter("table1q8")));
			cpar.setA9Score(SysManager.getIntValue(request.getParameter("table1q9")));
			
			cpar.setB1Score(SysManager.getIntValue(request.getParameter("table2q1")));
			cpar.setB2Score(SysManager.getIntValue(request.getParameter("table2q2")));
			cpar.setB3Score(SysManager.getIntValue(request.getParameter("table2q3")));
			cpar.setB4Score(SysManager.getIntValue(request.getParameter("table2q4")));
			cpar.setB5Score(SysManager.getIntValue(request.getParameter("table2q5")));
			
			cpar.setC1Score(SysManager.getIntValue(request.getParameter("table3q1")));
			cpar.setC2Score(SysManager.getIntValue(request.getParameter("table3q2")));
			cpar.setC3Score(SysManager.getIntValue(request.getParameter("table3q3")));
			cpar.setC4Score(SysManager.getIntValue(request.getParameter("table3q4")));
			cpar.setC5Score(SysManager.getIntValue(request.getParameter("table3q5")));
			
			cpar.setD1Score(SysManager.getIntValue(request.getParameter("table4q1")));
			cpar.setD2Score(SysManager.getIntValue(request.getParameter("table4q2")));
			cpar.setD3Score(SysManager.getIntValue(request.getParameter("table4q3")));
			
			cpar.setE1Score(SysManager.getIntValue(request.getParameter("table5q1")));
			cpar.setE2Score(SysManager.getIntValue(request.getParameter("table5q2")));
			cpar.setE3Score(SysManager.getIntValue(request.getParameter("table5q3")));
			cpar.setE4Score(SysManager.getIntValue(request.getParameter("table5q4")));
			
			cpar.setFinalized(SysManager.getValue(request.getParameter("finalized")));
			
			CPARDataBean cparDB = getCPARDataBean();
			if ("f".equals(cpar.getFinalized())){
				result = cparDB.insertOrUpdateCPAR(cpar,secCtx,isCreate,"finalize");
			}else{
				result = cparDB.insertOrUpdateCPAR(cpar,secCtx,isCreate,"save");				
			}
			allCPARList = cparDB.selectCparByWac(wac);
		} catch (SysException e) {
			String error =
					this.getClass().getName() + ":saveCPAR\n"
                    + e.getMessage();
            logger.error(error, e);
			throw new ServletException(e.getMessage());
		}
       	
       	if (result != 1){
       		request.getSession().setAttribute("CPAR_MSG","Fail to delete CPAR!");
       		request.getSession().setAttribute("CPAR_MSGTYPE","ERR");
       	}else{
           	request.getSession().setAttribute("CPAR_MSG", "The CPAR is saved successfully.");
           	request.getSession().setAttribute("CPAR_MSGTYPE", "SUCCESS");
       	}
		
        request.getSession().setAttribute("CPAR_List", allCPARList);
       	String url = UserBaseUrl + "CPARList.jsp";
    	    	        
       	request.getSession().setAttribute("WA_TYPE", "cpar");

    	    	
       	logger.debug(myName + " " + "Forward to URL: " + url);
        	        
       	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
       	requestDispatcher.forward(request, response);
    	    	        
       	logger.debug(myName + " " + "ended");
    }
    
    private void deleteCPAR(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {
       	String myName = "[" + MyClassName + "." + "deleteCPAR" + "]";
       	logger.debug(myName + " " + "started");
        	        
       	String cparNo = request.getParameter("cparNo");
       	String url = UserBaseUrl + "CPARDelete.jsp?cparNo="+cparNo;
    	    	        
       	logger.debug(myName + " " + "Forward to URL: " + url);
        	        
       	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
       	requestDispatcher.forward(request, response);
    	    	        
       	logger.debug(myName + " " + "ended");
    }	    
    
    private void confirmDeleteCPAR(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {
       	String myName = "[" + MyClassName + "." + "confirmDeleteCPAR" + "]";
       	String url = UserBaseUrl + "CPARList.jsp";
       	logger.debug(myName + " " + "started");
        	        
       	String cparNo = request.getParameter("cparNo");
       	WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
       	
     // Get user information
        SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
       	
        int result = -1;
       	try {
			CPARDataBean cparDB = getCPARDataBean();
			result = cparDB.deleteCPAR(wac,Integer.parseInt(cparNo),secCtx);
		} catch (SysException ex) {
			String error =
                    "DB ERROR: CPARDataBean:selectCparByCparNo\n"
                    + ex.getMessage();
            logger.error(error, ex);
			throw new ServletException(ex.getMessage());
		}
       	
       	if (result != 1){
       		request.getSession().setAttribute("CPAR_MSG","Fail to delete CPAR!");
       		request.getSession().setAttribute("CPAR_MSGTYPE","ERR");
       	}else{
       		request.getSession().setAttribute("CPAR_MSG","Delete CPAR No."+cparNo+" successfully!");
       		request.getSession().setAttribute("CPAR_MSGTYPE","LIST");
       	}
       	
       	logger.debug(myName + " " + "Forward to URL: " + url);
        	        
       	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
       	requestDispatcher.forward(request, response);
    	    	        
       	logger.debug(myName + " " + "ended");
    }	
        
    private void printCPAR(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {
       	String myName = "[" + MyClassName + "." + "printCPAR" + "]";
       	logger.debug(myName + " " + "started");
        	              
       	request.getSession().setAttribute("WA_TYPE", "cpar");
       	logger.debug(myName + " " + "Forward to URL: " + UserBaseUrl);

       	int cparNo = Integer.parseInt(request.getParameter("cparNo"));
       	// Get user information
       	WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
        CPARInfo cpar = new CPARInfo();
        
        
       	try {
			CPARDataBean cparDB = getCPARDataBean();
			cpar = cparDB.selectCparByCparNo(wac,cparNo);
			//nextDate = SysManager.getStringfromSQLDate(cpar.getStartDate());
			
			URL getDirectoryPath = Thread.currentThread().getContextClassLoader().getResource("");
    		String directoryPath = "/" + getDirectoryPath.toString().replace("file:/", "") + "qpses/util/";
    		String inputFilename = directoryPath + Constant.CPAR_TEMPLATE_NAME;
			String outputFilename = directoryPath + "ExportCPAR.doc";
			String outputFileRelativePath = "/WEB-INF/classes/qpses/util/ExportCPAR.doc";
			String clientOutputFileName = "QPS3-CPAR_" + cpar.getServiceContractNo() + ".doc";
			Map<String, String> replaces = new HashMap<String, String>();
			

			replaces.put(Constant.REPORT_MARKER+"department", wac.getDepartmentName());
		    replaces.put(Constant.REPORT_MARKER+"personName", cpar.getAuthorizedPerson());
		    replaces.put(Constant.REPORT_MARKER+"post", cpar.getPostRank());
		    replaces.put(Constant.REPORT_MARKER+"contractTitle", wac.getTitle());
		    replaces.put(Constant.REPORT_MARKER+"contractReferenceNo", cpar.getServiceContractNo());
		    replaces.put(Constant.REPORT_MARKER+"contractorName", wac.getAwardedContractor());
		    
		    //replaces.put(Constant.REPORT_MARKER+"startDate", convertDateFormat(cpar.getCreatedDate()));
		    //replaces.put(Constant.REPORT_MARKER+"endDate", convertDateFormat(cpar.getLastUpdatedDate()));
		    replaces.put(Constant.REPORT_MARKER+"startDate", SysManager.getDateStringfromSQLDate(cpar.getStartDate()));
		    replaces.put(Constant.REPORT_MARKER+"endDate", SysManager.getDateStringfromSQLDate(cpar.getEndDate()));
		    
		    if ( "c".equals(cpar.getStatus()) ){
		    	replaces.put(Constant.REPORT_MARKER+"periodCompleted", tick);
		    	replaces.put(Constant.REPORT_MARKER+"periodInprogess", space);
		    } else if ( "p".equals(cpar.getStatus()) ){
		    	replaces.put(Constant.REPORT_MARKER+"periodCompleted", space);
		    	replaces.put(Constant.REPORT_MARKER+"periodInprogess", tick);
		    } else {
		    	replaces.put(Constant.REPORT_MARKER+"periodCompleted", space);
		    	replaces.put(Constant.REPORT_MARKER+"periodInprogess", space);
		    }
		    
		    // a1
		    // a1-1[last number digit(1:poor, 2:fair, 3:satisfactory, 4: good)]
		    replaces.put(Constant.REPORT_MARKER+"a1-1", checkIsSelect(0, cpar.getA1Score()));
		    replaces.put(Constant.REPORT_MARKER+"a1-2", checkIsSelect(2, cpar.getA1Score()));
		    replaces.put(Constant.REPORT_MARKER+"a1-3", checkIsSelect(3, cpar.getA1Score()));
		    replaces.put(Constant.REPORT_MARKER+"a1-4", checkIsSelect(4, cpar.getA1Score()));
		    // a2
		    replaces.put(Constant.REPORT_MARKER+"a2-1", checkIsSelect(0, cpar.getA2Score()));
		    replaces.put(Constant.REPORT_MARKER+"a2-2", checkIsSelect(2, cpar.getA2Score()));
		    replaces.put(Constant.REPORT_MARKER+"a2-3", checkIsSelect(3, cpar.getA2Score()));
		    replaces.put(Constant.REPORT_MARKER+"a2-4", checkIsSelect(4, cpar.getA2Score()));
		    // a3
		    replaces.put(Constant.REPORT_MARKER+"a3-1", checkIsSelect(0, cpar.getA3Score()));
		    replaces.put(Constant.REPORT_MARKER+"a3-2", checkIsSelect(2, cpar.getA3Score()));
		    replaces.put(Constant.REPORT_MARKER+"a3-3", checkIsSelect(3, cpar.getA3Score()));
		    replaces.put(Constant.REPORT_MARKER+"a3-4", checkIsSelect(4, cpar.getA3Score()));
		    // a4
		    replaces.put(Constant.REPORT_MARKER+"a4-1", checkIsSelect(0, cpar.getA4Score()));
		    replaces.put(Constant.REPORT_MARKER+"a4-2", checkIsSelect(2, cpar.getA4Score()));
		    replaces.put(Constant.REPORT_MARKER+"a4-3", checkIsSelect(3, cpar.getA4Score()));
		    replaces.put(Constant.REPORT_MARKER+"a4-4", checkIsSelect(4, cpar.getA4Score()));
		    // a5
		    replaces.put(Constant.REPORT_MARKER+"a5-1", checkIsSelect(0, cpar.getA5Score()));
		    replaces.put(Constant.REPORT_MARKER+"a5-2", checkIsSelect(2, cpar.getA5Score()));
		    replaces.put(Constant.REPORT_MARKER+"a5-3", checkIsSelect(3, cpar.getA5Score()));
		    replaces.put(Constant.REPORT_MARKER+"a5-4", checkIsSelect(4, cpar.getA5Score()));
		    // a6
		    replaces.put(Constant.REPORT_MARKER+"a6-1", checkIsSelect(0, cpar.getA6Score()));
		    replaces.put(Constant.REPORT_MARKER+"a6-2", checkIsSelect(2, cpar.getA6Score()));
		    replaces.put(Constant.REPORT_MARKER+"a6-3", checkIsSelect(3, cpar.getA6Score()));
		    replaces.put(Constant.REPORT_MARKER+"a6-4", checkIsSelect(4, cpar.getA6Score()));
		    // a7
		    replaces.put(Constant.REPORT_MARKER+"a7-1", checkIsSelect(0, cpar.getA7Score()));
		    replaces.put(Constant.REPORT_MARKER+"a7-2", checkIsSelect(2, cpar.getA7Score()));
		    replaces.put(Constant.REPORT_MARKER+"a7-3", checkIsSelect(3, cpar.getA7Score()));
		    replaces.put(Constant.REPORT_MARKER+"a7-4", checkIsSelect(4, cpar.getA7Score()));
		    // a8
		    replaces.put(Constant.REPORT_MARKER+"a8-1", checkIsSelect(0, cpar.getA8Score()));
		    replaces.put(Constant.REPORT_MARKER+"a8-2", checkIsSelect(2, cpar.getA8Score()));
		    replaces.put(Constant.REPORT_MARKER+"a8-3", checkIsSelect(3, cpar.getA8Score()));
		    replaces.put(Constant.REPORT_MARKER+"a8-4", checkIsSelect(4, cpar.getA8Score()));
		    // a9
		    replaces.put(Constant.REPORT_MARKER+"a9-1", checkIsSelect(0, cpar.getA9Score()));
		    replaces.put(Constant.REPORT_MARKER+"a9-2", checkIsSelect(2, cpar.getA9Score()));
		    replaces.put(Constant.REPORT_MARKER+"a9-3", checkIsSelect(3, cpar.getA9Score()));
		    replaces.put(Constant.REPORT_MARKER+"a9-4", checkIsSelect(4, cpar.getA9Score()));
		   
		    

		    // b1
		    replaces.put(Constant.REPORT_MARKER+"b1-1", checkIsSelect(0, cpar.getB1Score()));
		    replaces.put(Constant.REPORT_MARKER+"b1-2", checkIsSelect(2, cpar.getB1Score()));
		    replaces.put(Constant.REPORT_MARKER+"b1-3", checkIsSelect(3, cpar.getB1Score()));
		    replaces.put(Constant.REPORT_MARKER+"b1-4", checkIsSelect(4, cpar.getB1Score()));
		    // b2
		    replaces.put(Constant.REPORT_MARKER+"b2-1", checkIsSelect(0, cpar.getB2Score()));
		    replaces.put(Constant.REPORT_MARKER+"b2-2", checkIsSelect(2, cpar.getB2Score()));
		    replaces.put(Constant.REPORT_MARKER+"b2-3", checkIsSelect(3, cpar.getB2Score()));
		    replaces.put(Constant.REPORT_MARKER+"b2-4", checkIsSelect(4, cpar.getB2Score()));
		    // b3
		    replaces.put(Constant.REPORT_MARKER+"b3-1", checkIsSelect(0, cpar.getB3Score()));
		    replaces.put(Constant.REPORT_MARKER+"b3-2", checkIsSelect(2, cpar.getB3Score()));
		    replaces.put(Constant.REPORT_MARKER+"b3-3", checkIsSelect(3, cpar.getB3Score()));
		    replaces.put(Constant.REPORT_MARKER+"b3-4", checkIsSelect(4, cpar.getB3Score()));
		    // b4
		    replaces.put(Constant.REPORT_MARKER+"b4-1", checkIsSelect(0, cpar.getB4Score()));
		    replaces.put(Constant.REPORT_MARKER+"b4-2", checkIsSelect(2, cpar.getB4Score()));
		    replaces.put(Constant.REPORT_MARKER+"b4-3", checkIsSelect(3, cpar.getB4Score()));
		    replaces.put(Constant.REPORT_MARKER+"b4-4", checkIsSelect(4, cpar.getB4Score()));
		    // b5
		    replaces.put(Constant.REPORT_MARKER+"b5-1", checkIsSelect(0, cpar.getB5Score()));
		    replaces.put(Constant.REPORT_MARKER+"b5-2", checkIsSelect(2, cpar.getB5Score()));
		    replaces.put(Constant.REPORT_MARKER+"b5-3", checkIsSelect(3, cpar.getB5Score()));
		    replaces.put(Constant.REPORT_MARKER+"b5-4", checkIsSelect(4, cpar.getB5Score()));

		    // c1
		    replaces.put(Constant.REPORT_MARKER+"c1-1", checkIsSelect(0, cpar.getC1Score()));
		    replaces.put(Constant.REPORT_MARKER+"c1-2", checkIsSelect(2, cpar.getC1Score()));
		    replaces.put(Constant.REPORT_MARKER+"c1-3", checkIsSelect(3, cpar.getC1Score()));
		    replaces.put(Constant.REPORT_MARKER+"c1-4", checkIsSelect(4, cpar.getC1Score()));
		    // c2
		    replaces.put(Constant.REPORT_MARKER+"c2-1", checkIsSelect(0, cpar.getC2Score()));
		    replaces.put(Constant.REPORT_MARKER+"c2-2", checkIsSelect(2, cpar.getC2Score()));
		    replaces.put(Constant.REPORT_MARKER+"c2-3", checkIsSelect(3, cpar.getC2Score()));
		    replaces.put(Constant.REPORT_MARKER+"c2-4", checkIsSelect(4, cpar.getC2Score()));
		    // c3
		    replaces.put(Constant.REPORT_MARKER+"c3-1", checkIsSelect(0, cpar.getC3Score()));
		    replaces.put(Constant.REPORT_MARKER+"c3-2", checkIsSelect(2, cpar.getC3Score()));
		    replaces.put(Constant.REPORT_MARKER+"c3-3", checkIsSelect(3, cpar.getC3Score()));
		    replaces.put(Constant.REPORT_MARKER+"c3-4", checkIsSelect(4, cpar.getC3Score()));
		    // c4
		    replaces.put(Constant.REPORT_MARKER+"c4-1", checkIsSelect(0, cpar.getC4Score()));
		    replaces.put(Constant.REPORT_MARKER+"c4-2", checkIsSelect(2, cpar.getC4Score()));
		    replaces.put(Constant.REPORT_MARKER+"c4-3", checkIsSelect(3, cpar.getC4Score()));
		    replaces.put(Constant.REPORT_MARKER+"c4-4", checkIsSelect(4, cpar.getC4Score()));
		    // c5
		    replaces.put(Constant.REPORT_MARKER+"c5-1", checkIsSelect(0, cpar.getC5Score()));
		    replaces.put(Constant.REPORT_MARKER+"c5-2", checkIsSelect(2, cpar.getC5Score()));
		    replaces.put(Constant.REPORT_MARKER+"c5-3", checkIsSelect(3, cpar.getC5Score()));
		    replaces.put(Constant.REPORT_MARKER+"c5-4", checkIsSelect(4, cpar.getC5Score()));

		    // d1
		    replaces.put(Constant.REPORT_MARKER+"d1-1", checkIsSelect(0, cpar.getD1Score()));
		    replaces.put(Constant.REPORT_MARKER+"d1-2", checkIsSelect(2, cpar.getD1Score()));
		    replaces.put(Constant.REPORT_MARKER+"d1-3", checkIsSelect(3, cpar.getD1Score()));
		    replaces.put(Constant.REPORT_MARKER+"d1-4", checkIsSelect(4, cpar.getD1Score()));
		    // d2
		    replaces.put(Constant.REPORT_MARKER+"d2-1", checkIsSelect(0, cpar.getD2Score()));
		    replaces.put(Constant.REPORT_MARKER+"d2-2", checkIsSelect(2, cpar.getD2Score()));
		    replaces.put(Constant.REPORT_MARKER+"d2-3", checkIsSelect(3, cpar.getD2Score()));
		    replaces.put(Constant.REPORT_MARKER+"d2-4", checkIsSelect(4, cpar.getD2Score()));
		    // d3
		    replaces.put(Constant.REPORT_MARKER+"d3-1", checkIsSelect(0, cpar.getD3Score()));
		    replaces.put(Constant.REPORT_MARKER+"d3-2", checkIsSelect(2, cpar.getD3Score()));
		    replaces.put(Constant.REPORT_MARKER+"d3-3", checkIsSelect(3, cpar.getD3Score()));
		    replaces.put(Constant.REPORT_MARKER+"d3-4", checkIsSelect(4, cpar.getD3Score()));

		    // e1
		    replaces.put(Constant.REPORT_MARKER+"e1-1", checkIsSelect(0, cpar.getE1Score()));
		    replaces.put(Constant.REPORT_MARKER+"e1-2", checkIsSelect(2, cpar.getE1Score()));
		    replaces.put(Constant.REPORT_MARKER+"e1-3", checkIsSelect(3, cpar.getE1Score()));
		    replaces.put(Constant.REPORT_MARKER+"e1-4", checkIsSelect(4, cpar.getE1Score()));
		    // e2
		    replaces.put(Constant.REPORT_MARKER+"e2-1", checkIsSelect(0, cpar.getE2Score()));
		    replaces.put(Constant.REPORT_MARKER+"e2-2", checkIsSelect(2, cpar.getE2Score()));
		    replaces.put(Constant.REPORT_MARKER+"e2-3", checkIsSelect(3, cpar.getE2Score()));
		    replaces.put(Constant.REPORT_MARKER+"e2-4", checkIsSelect(4, cpar.getE2Score()));
		    // e3
		    replaces.put(Constant.REPORT_MARKER+"e3-1", checkIsSelect(0, cpar.getE3Score()));
		    replaces.put(Constant.REPORT_MARKER+"e3-2", checkIsSelect(2, cpar.getE3Score()));
		    replaces.put(Constant.REPORT_MARKER+"e3-3", checkIsSelect(3, cpar.getE3Score()));
		    replaces.put(Constant.REPORT_MARKER+"e3-4", checkIsSelect(4, cpar.getE3Score()));
		    // e4
		    replaces.put(Constant.REPORT_MARKER+"e4-1", checkIsSelect(0, cpar.getE4Score()));
		    replaces.put(Constant.REPORT_MARKER+"e4-2", checkIsSelect(2, cpar.getE4Score()));
		    replaces.put(Constant.REPORT_MARKER+"e4-3", checkIsSelect(3, cpar.getE4Score()));
		    replaces.put(Constant.REPORT_MARKER+"e4-4", checkIsSelect(4, cpar.getE4Score()));
		    

			MSWordHelper mswdHelper = new MSWordHelper(inputFilename, outputFilename, replaces);
			mswdHelper.process();
			//System.out.println(mswdHelper.getHtmlText());

			response.setContentType("application/msword");
    		response.setHeader("Content-Disposition", "inline;filename=\"" + clientOutputFileName + "\"");
    		response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
    		ServletContext ctx = getServletContext();
    		InputStream is = ctx.getResourceAsStream(outputFileRelativePath);
    	 
    		try {
    			
    			logger.debug("Start Loading file...");
	    		int read=0;
	    		byte[] bytes = new byte[BYTES_DOWNLOAD];
	    		OutputStream os = response.getOutputStream();
	    	 
	    		while((read = is.read(bytes))!= -1){
	    			os.write(bytes, 0, read);
	    		}
	    		os.flush();
	    		os.close();	
	    		logger.debug("Loading End.");
    		}
    		catch(Exception ex){
    			String err = this.getClass().getName() 
    					+ ".showImagePage() : " + ex.getMessage();
            	logger.error(err,ex);
    			throw new ServletException("Servlet Error: " + MyClassName + ":printCPAR Error\n" + ex.toString());
	        }
			
			//Clear Memory and delete output file
			mswdHelper.close();
			
		} catch (SysException ex) {
			String error =
					this.getClass().getName() + ":printCPAR\n"
                    + ex.getMessage();
            logger.error(error, ex);
			throw new ServletException(ex.getMessage());
		}    	    	        
       	logger.debug(myName + " " + "ended");
    }

    private String checkIsSelect(int rating, int getQuestionValue) throws SysException {    
		if (getQuestionValue == rating){
			return tick;
		} else {
			return space;
		}
	} 
    
    private String convertDateFormat(String dateTime) throws SysException {  
    	String newDateFormat = " ";
    	SimpleDateFormat origianlDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    	SimpleDateFormat newDF = new SimpleDateFormat("dd/MM/yyyy");
    	try {
			Date dateDat = origianlDF.parse(dateTime);
			newDateFormat = newDF.format(dateDat);
		} catch (ParseException ex) {
			String error =
                    MyClassName + " ERROR: convertDateFormat\n"
                    + ex.getMessage();
            logger.error(error, ex);
			//ex.printStackTrace();
		}
    	return newDateFormat;
    			 
	}
   
    
}
