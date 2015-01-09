package qpses.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import qpses.business.*;
import qpses.util.*;
import qpses.security.*;

@SuppressWarnings("serial")
public final class AdminCPARServlet extends HttpServlet
{

	private static String adminId = null;
	
	// Get class name
    static String MyClassName = AdminCPARServlet.class.getName();
    
    // Set up Log4J
    static Logger logger = Logger.getLogger(MyClassName);
    
    // Admin web page base URL
    public static final String AdminBaseUrl = "/qpsadmin/";
    
    private static final int BYTES_DOWNLOAD = 1024;
    
    private static String tick = "X";
    private static String space = " ";
        
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
           
            if (requestAction.equals(AdminBaseUrl+"CPARAdmin"))
            { this.searchCPAR(request, response); }
            else if (requestAction.equals(AdminBaseUrl+"CPARList.servlet"))
            { this.listCPAR(request, response); }
            /*else if (requestAction.equals(AdminBaseUrl+"CPARCreate.servlet"))
            { this.createCPAR(request, response); }*/
            else if (requestAction.equals(AdminBaseUrl+"CPARUpdate.servlet"))
            { this.updateCPAR(request, response); }
            else if (requestAction.equals(AdminBaseUrl+"CPARSave.servlet"))
            { this.saveCPAR(request, response); }
            else if (requestAction.equals(AdminBaseUrl+"CPARDelete.servlet"))
            { this.deleteCPAR(request, response); }
            else if (requestAction.equals(AdminBaseUrl+"CPARPrint.servlet"))
            { this.printCPAR(request, response); }
            else if (requestAction.equals(AdminBaseUrl+"CPARHistory.servlet"))
            { this.historyCPAR(request, response); }
            else if (requestAction.equals(AdminBaseUrl+"CPARConfirmDelete.servlet"))
            { this.confirmDeleteCPAR(request, response); }
            else if (requestAction.equals(AdminBaseUrl+"CPARReleaseInList.servlet"))
            { this.releaseInListCPAR(request, response); }
            else if (requestAction.equals(AdminBaseUrl+"CPARUnreleaseInList.servlet"))
            { this.unreleaseInListCPAR(request, response); }
            else	
            { throw new SysException(myName, "[" + requestAction + "]" + " " + "is not allowed."); }
        }
        catch (Exception ex)
        { 
        	String err = myName+ex.getMessage();
        	logger.error(err,ex);
        	throw new ServletException(err); 
        }
        
        logger.debug(myName + " " + "ended");
    }
    
    private void searchCPAR(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "searchCPAR" + "]";
        logger.debug(myName + " " + "started");
        
        String url = AdminBaseUrl + "CPARSearch.jsp";
        
        request.getSession().setAttribute("WA_TYPE", "acpar");
        
        logger.debug(myName + " " + "Forward to URL: " + url);
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        
        logger.debug(myName + " " + "ended");
    }
    
    
    private void listCPAR(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
    	String myName = "[" + MyClassName + "." + "searchCPAR" + "]";
    	logger.debug(myName + " " + "started");
    	        
    	String url = AdminBaseUrl + "CPARList.jsp";
    	        
    	List<CPARInfo> allCPARList = new ArrayList<CPARInfo>();
    	request.getSession().removeAttribute("QPSES_CPAR");
    	request.getSession().removeAttribute("QPSES_CPAR_HISTORY");
    	request.getSession().removeAttribute("CPAR_List");
    	
    	WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
        if (wac == null)  {
        	throw new ServletException("Unable to get work assignment info.");
        }
    	
		try {
			CPARDataBean cparDB = new CPARDataBean();
			allCPARList = cparDB.selectCparByWac(wac);
		} catch (SysException e) {
        	logger.error(e);
			throw new ServletException(e.getMessage());
		}
		
		if (("".equals(wac.getServiceCategoryGroup())) || (wac.getServiceCategoryGroup() == null)){
			String strValidationMsg = "Work assignment with this service contract ref. no " + wac.getServiceContractNo() + " does not exist in the system. Please retry!";
			request.getSession().setAttribute("WA_CHALLENGE_MSG", strValidationMsg);
	        response.sendRedirect("CPARAdmin");
	        return;
		}

		
        request.getSession().setAttribute("CPAR_List", allCPARList);
    	
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
        	        
       	String url = AdminBaseUrl + "CPARUpdate.jsp";
       	int cparNo = SysManager.getIntValue(request.getParameter("cparNo"));
       	
       	// Get admin information
        WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
        CPARInfo cpar = new CPARInfo();
        
        String nextDate = "";
        
       	try {
			CPARDataBean cparDB = new CPARDataBean();
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
       	// Get admin information
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
			
			cpar.setScore(calculateCPARScore(cpar));
			//float a = Float.valueOf(String.format("%.2f", calculateCPARScore(cpar)));
			//cpar.setScore(a);
			
			CPARDataBean cparDB = new CPARDataBean();
			if ("r".equals(cpar.getFinalized())){
				result = cparDB.insertOrUpdateCPAR(cpar,secCtx,isCreate,"release");
			}else{
				result = cparDB.insertOrUpdateCPAR(cpar,secCtx,isCreate,"save");				
			}

			allCPARList = cparDB.selectCparByWac(wac);
		} catch (SysException e) {
			logger.error(e);
			throw new ServletException(e.getMessage());
		}
       	
       	if (result != 1){
       		request.getSession().setAttribute("CPAR_MSG","Fail to delete CPAR!");
       		request.getSession().setAttribute("CPAR_MSGTYPE","ERR");
       	}else{
       		request.getSession().setAttribute("CPAR_MSG","Delete CPAR No."+cpar.getCparNo()+" successfully!");
       		request.getSession().setAttribute("CPAR_MSGTYPE","LIST");
       	}
		
        request.getSession().setAttribute("CPAR_List", allCPARList);
       	String url = AdminBaseUrl + "CPARList.jsp";
    	    	        
       	request.getSession().setAttribute("WA_TYPE", "acpar");
       	request.getSession().setAttribute("CPAR_MSG", "The CPAR is saved successfully.");
       	request.getSession().setAttribute("CPAR_MSGTYPE", "SUCCESS");
    	    	
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
       	
       	List<CPARInfo> allCparList = (List<CPARInfo>) request.getSession().getAttribute("CPAR_List");
    	CPARInfo cpar = new CPARInfo();
    	
    	for (CPARInfo index : allCparList){
    		String a = Integer.toString(index.getCparNo());
    		if (cparNo.equals(a)){
    			cpar = index;
    		}
    	} 
    	
       	request.getSession().setAttribute("QPSES_CPAR", cpar);
       	String url = AdminBaseUrl + "CPARDelete.jsp";
    	    	        
       	logger.debug(myName + " " + "Forward to URL: " + url);
        	        
       	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
       	requestDispatcher.forward(request, response);
    	    	        
       	logger.debug(myName + " " + "ended");
    }	    
    
    private void releaseInListCPAR(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {
       	String myName = "[" + MyClassName + "." + "releaseInListCPAR" + "]";
       	logger.debug(myName + " " + "started");
        	        
       	int cparNo = SysManager.getIntValue(request.getParameter("cparNo"));
       	
       	// Get admin information
        WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
        CPARInfo cpar = new CPARInfo();
        
       	try {
			CPARDataBean cparDB = new CPARDataBean();
			cpar = cparDB.selectCparByCparNo(wac,cparNo);
		} catch (SysException ex) {
			String error =
                    "DB ERROR: CPARDataBean:selectCparByCparNo\n"
                    + ex.getMessage();
            logger.error(error, ex);
			throw new ServletException(ex.getMessage());
		}
        
        //=================================================================================================================================
       	List<CPARInfo> allCPARList = new ArrayList<CPARInfo>();
       	
       	// Get admin information
        SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
        int result = -1;
        
       	try {
       		CPARDataBean cparDB = new CPARDataBean();
       		if (releaseInListCPARValidation(cpar)){
       			cpar.setProjectFilePart(wac.getFilePartNo());
    			cpar.setProjectFileNo(wac.getFileNo());
       			cpar.setFinalized("r");
       			cpar.setScore(calculateCPARScore(cpar));
       			
       			result = cparDB.insertOrUpdateCPAR(cpar,secCtx,false,"release");
    			allCPARList = cparDB.selectCparByWac(wac);
       		}

		} catch (SysException e) {
			logger.error(e);
			throw new ServletException(e.getMessage());
		}

       	if (result != 1){
       		request.getSession().setAttribute("CPAR_MSG", "Fail to release CPAR! Please fill in all the mandatory fields in the CPAR!");
           	request.getSession().setAttribute("CPAR_MSGTYPE", "SUCCESS");
       	}else{
       		request.getSession().setAttribute("CPAR_MSG", "Release CPAR No."+cpar.getCparNo()+" successfully!");
           	request.getSession().setAttribute("CPAR_MSGTYPE", "SUCCESS");
       	}
		
        request.getSession().setAttribute("CPAR_List", allCPARList);
       	String url = AdminBaseUrl + "CPARList.jsp";
    	    	        
       	request.getSession().setAttribute("WA_TYPE", "acpar");
       	
       	
       	//=================================================================================================================================
        
       	
       	logger.debug(myName + " " + "Forward to URL: " + url);
        	        
       	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
       	requestDispatcher.forward(request, response);
    	    	        
       	logger.debug(myName + " " + "ended");
    }	
    
    private void unreleaseInListCPAR(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {
       	String myName = "[" + MyClassName + "." + "unreleaseInListCPAR" + "]";
       	logger.debug(myName + " " + "started");
        	        
       	int cparNo = SysManager.getIntValue(request.getParameter("cparNo"));
       	
       	// Get admin information
        WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
        CPARInfo cpar = new CPARInfo();
        
       	try {
			CPARDataBean cparDB = new CPARDataBean();
			cpar = cparDB.selectCparByCparNo(wac,cparNo);
		} catch (SysException ex) {
			String error =
                    "DB ERROR: CPARDataBean:selectCparByCparNo\n"
                    + ex.getMessage();
            logger.error(error, ex);
			throw new ServletException(ex.getMessage());
		}
        
       	List<CPARInfo> allCPARList = new ArrayList<CPARInfo>();

       	// Get admin information
        SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
        int result = -1;
        
        
       	try {
       		CPARDataBean cparDB = new CPARDataBean();
       		if (releaseInListCPARValidation(cpar)){
       			cpar.setProjectFilePart(wac.getFilePartNo());
    			cpar.setProjectFileNo(wac.getFileNo());
       			cpar.setFinalized("f");
       			
       			result = cparDB.insertOrUpdateCPAR(cpar,secCtx,false,"unrelease");
    			allCPARList = cparDB.selectCparByWac(wac);
       		}
			
		} catch (SysException e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
       	

       	if (result != 1){
       		request.getSession().setAttribute("CPAR_MSG", "Fail to unrelease CPAR!");
           	request.getSession().setAttribute("CPAR_MSGTYPE", "SUCCESS");
       	}else{
       		request.getSession().setAttribute("CPAR_MSG", "Unrelease CPAR No."+cpar.getCparNo()+" successfully!");
           	request.getSession().setAttribute("CPAR_MSGTYPE", "SUCCESS");
       	}
		
        request.getSession().setAttribute("CPAR_List", allCPARList);
       	String url = AdminBaseUrl + "CPARList.jsp";
    	    	        
       	request.getSession().setAttribute("WA_TYPE", "acpar");
       	
       	logger.debug(myName + " " + "Forward to URL: " + url);
        	        
       	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
       	requestDispatcher.forward(request, response);
    	    	        
       	logger.debug(myName + " " + "ended");
    }
    
    private void confirmDeleteCPAR(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {
       	String myName = "[" + MyClassName + "." + "confirmDeleteCPAR" + "]";
       	String url = "CPARList.servlet";
       	logger.debug(myName + " " + "started");
        	        
       	String cparNo = request.getParameter("cparNo");
       	WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
       	
     // Get admin information
        SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
       	
        int result = -1;
       	try {
			CPARDataBean cparDB = new CPARDataBean();
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
        	        
       	response.sendRedirect(url);
    	    	        
       	logger.debug(myName + " " + "ended");
    }	
    
    private void historyCPAR(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {
    	    String myName = "[" + MyClassName + "." + "historyCPAR" + "]";
    	    logger.debug(myName + " " + "started");
    	    	        
    	    String url = AdminBaseUrl + "CPARHistory.jsp";
    	    	        
    	    List<CPARInfo> allCPARList = new ArrayList<CPARInfo>();
    	    
    	    WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
    	    if (wac == null)  {
    	        throw new ServletException("Unable to get work assignment info.");
    	    }
    	    
    	    String cparNo = request.getParameter("cparNo");
    	    
    	    if ((cparNo == null)||("".equals(cparNo)))  {
    	        throw new ServletException("Unable to get cparNo parameter from CPARList.jsp");
    	    }
    	    
    	    CPARInfo cpar = new CPARInfo();
    	    List<CPARInfo> allCPARHistoryList = new ArrayList<CPARInfo>();
    	    try {
    			CPARDataBean cparDB = new CPARDataBean();
    			cpar = cparDB.selectCparByCparNo(wac, Integer.parseInt(cparNo));
    			allCPARHistoryList = cparDB.selectCparHistory(wac, Integer.parseInt(cparNo));
    		} catch (SysException e) {
    	        logger.error(e);
    			throw new ServletException(e.getMessage());
    		}
    			
    	    request.getSession().setAttribute("QPSES_CPAR", cpar);
    	    
    	    request.getSession().setAttribute("QPSES_CPAR_HISTORY", allCPARHistoryList);
    	    	
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
        	               
       	request.getSession().setAttribute("WA_TYPE", "acpar");
       	logger.debug(myName + " " + "Forward to URL: " + AdminBaseUrl);

       	int cparNo = Integer.parseInt(request.getParameter("cparNo"));
       	// Get admin information
       	WAChallengeInfo wac = (WAChallengeInfo)request.getSession().getAttribute("QPSES_WA_CHALLENGE");
        CPARInfo cpar = new CPARInfo();
        
        
       	try {
			CPARDataBean cparDB = new CPARDataBean();
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
    			  logger.error(ex);
    			  throw new ServletException("Servlet Error: " + MyClassName + ":printCPAR Error\n" + ex.toString());
	        }
			
			//Clear Memory and delete output file
			mswdHelper.close();
			
		} catch (SysException ex) {
			String error =
                    "DB ERROR: CPARDataBean:selectCparByCparNo\n"
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
    
    
    private boolean releaseInListCPARValidation(CPARInfo cpar) throws SysException {  
    	/*Date today = new Date();
		Date startDate = cpar.getStartDate();
		Date endDate = cpar.getEndDate();
		
		// Date checking
		if ( (!"".equals(startDate) && startDate == null) || (!"".equals(endDate) && endDate == null) 
				|| (startDate.getTime() > endDate.getTime()) || (endDate.getTime() > today.getTime()) ){
			return false;
		}*/
		
		// Relative field checking 
		if ( "".equals(cpar.getStatus()) 
				|| "".equals(cpar.getAuthorizedPerson()) 
				|| "".equals(cpar.getPostRank()) ){
			return false;
		}
		
		// Table A checking
		if ( cpar.getA1Score() == -1
				|| cpar.getA2Score() == -1
				|| cpar.getA3Score() == -1
				|| cpar.getA4Score() == -1
				|| cpar.getA5Score() == -1
				|| cpar.getA6Score() == -1
				|| cpar.getA7Score() == -1
				|| cpar.getA8Score() == -1
				|| cpar.getA9Score() == -1 ){
			return false;
		}
		// Table B checking
		if ( cpar.getB1Score() == -1
				|| cpar.getB2Score() == -1
				|| cpar.getB3Score() == -1
				|| cpar.getB4Score() == -1
				|| cpar.getB5Score() == -1 ){
			return false;
		}
		// Table C checking
		if ( cpar.getC1Score() == -1
				|| cpar.getC2Score() == -1
				|| cpar.getC3Score() == -1
				|| cpar.getC4Score() == -1
				|| cpar.getC5Score() == -1 ){
			return false;
		}
		// Table D checking
		if ( cpar.getD1Score() == -1
				|| cpar.getD2Score() == -1
				|| cpar.getD3Score() == -1 ){
			return false;
		}
    	return true;		 
	}
    
    private float calculateCPARScore(CPARInfo cpar) throws SysException {  
    	int sum = 0;
    	int threeMark = 3;
    	int twoMark = 2;
    	int oneMark = 1;
    	float score = 0 ;
    	float divisionMark = 4;
    	//ArrayList cparScore = new ArrayList();
    	List<Integer> cparScore = new ArrayList<Integer>();
    	
    	cparScore.add(cpar.getA1Score() * threeMark);
    	cparScore.add(cpar.getA2Score() * threeMark);
    	cparScore.add(cpar.getA3Score() * threeMark);
    	cparScore.add(cpar.getA4Score() * oneMark);
    	cparScore.add(cpar.getA5Score() * oneMark);
    	cparScore.add(cpar.getA6Score() * oneMark);
    	cparScore.add(cpar.getA7Score() * oneMark);
    	cparScore.add(cpar.getA8Score() * oneMark);
    	cparScore.add(cpar.getA9Score() * oneMark);

    	cparScore.add(cpar.getB1Score() * twoMark);
    	cparScore.add(cpar.getB2Score() * twoMark);
    	cparScore.add(cpar.getB3Score() * twoMark);
    	cparScore.add(cpar.getB4Score() * twoMark);
    	cparScore.add(cpar.getB5Score() * twoMark);
    	
    	cparScore.add(cpar.getC1Score() * oneMark);
    	cparScore.add(cpar.getC2Score() * oneMark);
    	cparScore.add(cpar.getC3Score() * oneMark);
    	cparScore.add(cpar.getC4Score() * oneMark);
    	cparScore.add(cpar.getC5Score() * oneMark);
    	
    	for (int scorePerCPAR :cparScore){
    		sum = sum + scorePerCPAR;
    	}

    	score =  ((float)sum / divisionMark);

    	return score;	 
	}

			
}