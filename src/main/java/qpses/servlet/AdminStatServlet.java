/*
 * AdminStatServlet.java
 *
 * Created on 8th July, 2013
 */

package qpses.servlet;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import qpses.business.LogDataBean;
import qpses.business.StatDataBean;
import qpses.business.StatInfo;
import qpses.security.SecurityContext;
import qpses.util.Constant;
import qpses.util.SysException;
import qpses.util.SysManager;
import qpses.util.MSExcelHelper;

public class AdminStatServlet extends HttpServlet {
    
    // log4j
    static Logger logger = Logger.getLogger(AdminStatServlet.class.getName());
    
    
    private static String postScreen = null;
    private static String requestAction = null;
    private static final int BYTES_DOWNLOAD = 1024;
    
    public LogDataBean getLogDataBean() throws SysException{
    	return new LogDataBean();
    }
    
    public StatDataBean getStatDataBean() throws SysException{
    	return new StatDataBean();
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        requestAction = request.getServletPath();

        if (requestAction.equals("/qpsadmin/StatSearch.servlet")) {
            performStatSearch(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/StatSearchReset.servlet")) {
            performStatSearchReset(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/StatDownloadReport.servlet")) {
            performStatDownloadReport(request, response);
            return;
        }
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    }
     
    private void performStatSearch(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        HashMap statHashMap = new HashMap();
        StatInfo stat = new StatInfo();
        List<Date> dateFilters = new ArrayList<Date>();
                
        try {
        	StatDataBean statDB = getStatDataBean();
        	
            String invitationStartDate = ""; // request.getParameter("InvitationStartDate");
            String invitationEndDate = ""; // request.getParameter("InvitationEndDate");
            String awardStartDate = ""; // request.getParameter("AwardStartDate");
            String awardEndDate = ""; // request.getParameter("AwardEndDate");
            
            dateFilters.add(("".equals(invitationStartDate))?null:SysManager.getSQLDate(invitationStartDate));
            dateFilters.add(("".equals(invitationEndDate))?null:SysManager.getSQLDate(invitationEndDate));
            dateFilters.add(("".equals(awardStartDate))?null:SysManager.getSQLDate(awardStartDate));
            dateFilters.add(("".equals(awardEndDate))?null:SysManager.getSQLDate(awardEndDate));
            
            // Overall Status
            stat.setInvitationNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "", ""));
            stat.setAwardedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n/c", ""));
            stat.setAwardedTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "total_project_cost", "", ""));
            stat.setContractTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "awarded_contract_value", "", ""));
            stat.setChangeTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "aggregated_effort", "", ""));
            stat.setInprogressContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n", ""));
            stat.setCompletedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "c", ""));
            stat.setCancelledInvitationNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "w/x", ""));
            //stat.setReceivedCPARNo(statDB.XXXXXX());
            stat.setReceivedCPARNo(statDB.genNoOfCpars(dateFilters));
            
            // Cat 1
            stat.setCat1AwardedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n/c", "Cat1"));
            stat.setCat1InprogressContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n", "Cat1"));
            stat.setCat1CompletedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "c", "Cat1"));
            stat.setCat1AwardedTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "total_project_cost", "", "Cat1"));
            stat.setCat1ContractTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "awarded_contract_value", "", "Cat1"));
            stat.setCat1ChangeTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "aggregated_effort", "", "Cat1"));
            
            // Cat 2 Major
            stat.setCat2MajorAwardedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n/c", "Cat2j"));
            stat.setCat2MajorInprogressContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n", "Cat2j"));
            stat.setCat2MajorCompletedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "c", "Cat2j"));
            stat.setCat2MajorAwardedTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "total_project_cost", "", "Cat2j"));
            stat.setCat2MajorContractTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "awarded_contract_value", "", "Cat2j"));
            stat.setCat2MajorChangeTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "aggregated_effort", "", "Cat2j"));
            
            // Cat 2 Minor
            stat.setCat2MinorAwardedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n/c", "Cat2n"));
            stat.setCat2MinorInprogressContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n", "Cat2n"));
            stat.setCat2MinorCompletedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "c", "Cat2n"));
            stat.setCat2MinorAwardedTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "total_project_cost", "", "Cat2n"));
            stat.setCat2MinorContractTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "awarded_contract_value", "", "Cat2n"));
            stat.setCat2MinorChangeTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "aggregated_effort", "", "Cat2n"));
            
            // Cat 3 Major
            stat.setCat3MajorAwardedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n/c", "Cat3j"));
            stat.setCat3MajorInprogressContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n", "Cat3j"));
            stat.setCat3MajorCompletedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "c", "Cat3j"));
            stat.setCat3MajorAwardedTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "total_project_cost", "", "Cat3j"));
            stat.setCat3MajorContractTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "awarded_contract_value", "", "Cat3j"));
            stat.setCat3MajorChangeTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "aggregated_effort", "", "Cat3j"));
            
            // Cat 3 Minor
            stat.setCat3MinorAwardedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n/c", "Cat3n"));
            stat.setCat3MinorInprogressContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n", "Cat3n"));
            stat.setCat3MinorCompletedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "c", "Cat3n"));
            stat.setCat3MinorAwardedTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "total_project_cost", "", "Cat3n"));
            stat.setCat3MinorContractTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "awarded_contract_value", "", "Cat3n"));
            stat.setCat3MinorChangeTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "aggregated_effort", "", "Cat3n"));
            
            // Cat 4
            stat.setCat4AwardedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n/c", "Cat4"));
            stat.setCat4InprogressContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "n", "Cat4"));
            stat.setCat4CompletedContractNo(statDB.genWorkAssignmentValue(dateFilters, "count", "", "c", "Cat4"));
            stat.setCat4AwardedTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "total_project_cost", "", "Cat4"));
            stat.setCat4ContractTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "awarded_contract_value", "", "Cat4"));
            stat.setCat4ChangeTotalValue(statDB.genWorkAssignmentValue(dateFilters, "sum", "aggregated_effort", "", "Cat4"));            

            
            statHashMap.put("Stat",stat);
            statHashMap.put("DateFilters",dateFilters);
            request.getSession().setAttribute("STAT_PARAMETER",statHashMap);
            request.getSession().setAttribute("STAT_REPORT_PARAMETER",statHashMap);
            
            String remark = "Invitation Date: " + dateFilters.get(0) + " to "+ dateFilters.get(1) + " , Awarded Date: " + dateFilters.get(2) + " to "+ dateFilters.get(3);
            LogDataBean logDB = getLogDataBean();
            logDB.insertAdminLog(secCtx.getUserId(),secCtx.getDPDeptId(),secCtx.getSOADeptId(),"Statistics","STAT","Statistics",remark);

            postScreen = "Statistics.jsp";
            response.sendRedirect(postScreen);
            return;
        }catch(Exception ex){
        	logger.error(ex);
            throw new ServletException("Servlet Error: AdminStatServlet:performStatSearch\n" + ex.toString());
        }
    }
    
    private void performStatSearchReset(HttpServletRequest request, HttpServletResponse response)
    throws ServletException {
        
        try{
            request.getSession().removeAttribute("STAT_PARAMETER");
            request.getSession().removeAttribute("STAT_REPORT_PARAMETER");
            postScreen = request.getHeader("referer");
            response.sendRedirect(postScreen);
            return;
        }catch(Exception ex){
        	logger.error(ex);
            throw new ServletException("Servlet Error:  AdminStatServlet:performStatSearchReset\n" + ex.toString());
        }
    }
    
    private void performStatDownloadReport(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException {
    	        
    	        try{
    	        	
    	        	String searchedInvitationStartDate = "";
    	            String searchedInvitationEndDate = "";
    	            String searchedAwardStartDate = "";
    	            String searchedAwardEndDate = "";
    	            String searchedInvitationDate = "";
    	            String searchedAwardDate = "";
    	            String currentDate = SysManager.getCurDateTimeStr("dd-MMM-yyyy");
    	            
    	        	HashMap searchParaHashMap = (HashMap) request.getSession().getAttribute("STAT_REPORT_PARAMETER");
    	        	StatInfo statValue = new StatInfo();

    	        	
    	        	statValue = (StatInfo) searchParaHashMap.get("Stat");
    	        	List<Date> searchParaDates = (List<Date>) searchParaHashMap.get("DateFilters");
    	        	    	
    	        	if (searchParaDates.get(0) != null) {
    	        		searchedInvitationStartDate = SysManager.getStringfromSQLDate(searchParaDates.get(0));
    	        	} else{
    	        		searchedInvitationStartDate = " --- ";
    	        	}
    	        	if (searchParaDates.get(1) != null) {
    	        		searchedInvitationEndDate = SysManager.getStringfromSQLDate(searchParaDates.get(1));
    	        	}else{
    	        		searchedInvitationEndDate = " --- ";
    	        	}
    	        	if (searchParaDates.get(2) != null) {
    	        		searchedAwardStartDate = SysManager.getStringfromSQLDate(searchParaDates.get(2));
    	        	}else{
    	        		searchedAwardStartDate = " --- ";
    	        	}
    	        	if (searchParaDates.get(3) != null) {
    	        		searchedAwardEndDate = SysManager.getStringfromSQLDate(searchParaDates.get(3));
    	        	}else{
    	        		searchedAwardEndDate = " --- ";
    	        	}
    	        	
    	        	searchedInvitationDate = searchedInvitationStartDate + " to " + searchedInvitationEndDate;
    	        	searchedAwardDate = searchedAwardStartDate + " to " + searchedAwardEndDate;
    	        	if (" ---  to  --- ".equals(searchedInvitationDate)){
    	        		searchedInvitationDate = "N/A";
    	        	}
    	        	if (" ---  to  --- ".equals(searchedAwardDate)){
    	        		searchedAwardDate = "N/A";
    	        	}
    	            
    	            URL getDirectoryPath = Thread.currentThread().getContextClassLoader().getResource("");
    	    		String directoryPath = "/" + getDirectoryPath.toString().replace("file:/", "") + "qpses/util/";
    	    		String serverInputFilePath = directoryPath + Constant.STAT_REPORT_NAME;
    	    		String serverOutputFilePath = directoryPath + "ExportStatistics.xls";
    	    		String serverOutputFileRelativePath = "/WEB-INF/classes/qpses/util/ExportStatistics.xls";
    	    		String clientOutputFileName = "Management Statistics.xls";
    	    		
    	    		MSExcelHelper msxlsHelper = new MSExcelHelper(serverInputFilePath, serverOutputFilePath);
    	    		
    	    		msxlsHelper.setExcelValue(1, 3, 3, currentDate, 0);
    	    		/* msxlsHelper.setExcelValue(1, 4, 3, searchedInvitationDate, false);
    	    		msxlsHelper.setExcelValue(1, 5, 3, searchedAwardDate, false); */
    	        	
    	      		msxlsHelper.setExcelValue(1, 9, 3, statValue.getInvitationNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 10, 3, statValue.getAwardedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 11, 3, statValue.getAwardedTotalValue(), 1);
    	    		
    	    		msxlsHelper.setExcelValue(1, 14, 3, statValue.getContractTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 14, 7, statValue.getChangeTotalValue(), 1);
    	    		
    	    		msxlsHelper.setExcelValue(1, 16, 3, statValue.getInprogressContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 17, 3, statValue.getCompletedContractNo(), 1);
    	    		
    	    		msxlsHelper.setExcelValue(1, 19, 3, statValue.getCancelledInvitationNo(), 1);
    	    		/*msxlsHelper.setExcelValue(1, 20, 3, statValue.getReceivedCPARNo(), 1);*/
    	    		
    	    		msxlsHelper.setExcelValue(1, 26, 3, statValue.getCat1AwardedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 27, 3, statValue.getCat1InprogressContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 27, 7, statValue.getCat1CompletedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 28, 3, statValue.getCat1AwardedTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 29, 3, statValue.getCat1ContractTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 29, 7, statValue.getCat1ChangeTotalValue(), 1);
    	    		
    	    		msxlsHelper.setExcelValue(1, 32, 3, statValue.getCat2MajorAwardedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 33, 3, statValue.getCat2MajorInprogressContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 33, 7, statValue.getCat2MajorCompletedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 34, 3, statValue.getCat2MajorAwardedTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 35, 3, statValue.getCat2MajorContractTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 35, 7, statValue.getCat2MajorChangeTotalValue(), 1);		

    	    		msxlsHelper.setExcelValue(1, 38, 3, statValue.getCat2MinorAwardedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 39, 3, statValue.getCat2MinorInprogressContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 39, 7, statValue.getCat2MinorCompletedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 40, 3, statValue.getCat2MinorAwardedTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 41, 3, statValue.getCat2MinorContractTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 41, 7, statValue.getCat2MinorChangeTotalValue(), 1);

    	    		msxlsHelper.setExcelValue(1, 44, 3, statValue.getCat3MajorAwardedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 45, 3, statValue.getCat3MajorInprogressContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 45, 7, statValue.getCat3MajorCompletedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 46, 3, statValue.getCat3MajorAwardedTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 47, 3, statValue.getCat3MajorContractTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 47, 7, statValue.getCat3MajorChangeTotalValue(), 1);
    	    		
    	    		msxlsHelper.setExcelValue(1, 50, 3, statValue.getCat3MinorAwardedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 51, 3, statValue.getCat3MinorInprogressContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 51, 7, statValue.getCat3MinorCompletedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 52, 3, statValue.getCat3MinorAwardedTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 53, 3, statValue.getCat3MinorContractTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 53, 7, statValue.getCat3MinorChangeTotalValue(), 1);		
    	    		
    	    		msxlsHelper.setExcelValue(1, 56, 3, statValue.getCat4AwardedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 57, 3, statValue.getCat4InprogressContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 57, 7, statValue.getCat4CompletedContractNo(), 1);
    	    		msxlsHelper.setExcelValue(1, 58, 3, statValue.getCat4AwardedTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 59, 3, statValue.getCat4ContractTotalValue(), 1);
    	    		msxlsHelper.setExcelValue(1, 59, 7, statValue.getCat4ChangeTotalValue(), 1);
    	    		
    	    		msxlsHelper.process();
    	    		//System.out.println(msxlsHelper.getHtmlText());
    	    		
    	    		response.setContentType("application/vnd.ms-excel");
    	    		response.setHeader("Content-Disposition", "attachment;filename=\"" + clientOutputFileName + "\"");
    	    		ServletContext ctx = getServletContext();
    	    		InputStream is = ctx.getResourceAsStream(serverOutputFileRelativePath);
    	    	 
    	    		try {
    	    			
    	    			logger.debug("Start Download...");
	    	    		int read=0;
	    	    		byte[] bytes = new byte[BYTES_DOWNLOAD];
	    	    		OutputStream os = response.getOutputStream();
	    	    	 
	    	    		while((read = is.read(bytes))!= -1){
	    	    			os.write(bytes, 0, read);
	    	    		}
	    	    		os.flush();
	    	    		os.close();	
	    	    		logger.debug("Download End.");
    	    		}
    	    		catch(Exception ex){
    	    			logger.error(ex);
        	            throw new ServletException("Servlet Error:  AdminStatServlet:performStatDownloadReport \nDownload Error\n" + ex.toString());
        	        }

    	    		//Clear Memory and delete output file
    	    		msxlsHelper.close();
    	    		//postScreen = request.getHeader("referer");
    	            //response.sendRedirect(postScreen);
    	            return;
    	        }catch(Exception ex){
    	        	logger.error(ex);
    	            throw new ServletException("Servlet Error:  AdminStatServlet:performStatDownloadReport\n" + ex.toString());
    	        }
    	    }
}