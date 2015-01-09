/*
 * AdminContractorServlet.java
 *
 * Created on 28th June, 2013
 */

package qpses.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import qpses.business.ContractorDataBean;
import qpses.business.ContractorInfo;
import qpses.security.SecurityContext;
import qpses.util.SysException;

public class AdminContractorServlet extends HttpServlet {
    
    // log4j
    static Logger logger = Logger.getLogger(AdminContractorServlet.class.getName());
    
    private static String postScreen = null;
    private static String requestAction = null;
    
    public ContractorDataBean getContractorDataBean() throws SysException{
    	return new ContractorDataBean();
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        requestAction = request.getServletPath();
        if (requestAction.equals("/qpsadmin/ContractorAdd.servlet")) {
            performContractorAdd(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/ContractorUpdate.servlet")) {
            performContractorUpdate(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/ContractorDelete.servlet")) {
            performContractorDelete(request, response);
            return;
        }
        
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    }
    
    private void performContractorAdd(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        ContractorInfo c = new ContractorInfo();
        try {
            if (request.getParameter("ContractorId") == null ||
                request.getParameter("ContractorName")==null){
                postScreen = "ContractorList.jsp";
                String errMsg = "Failed to add contractor! Invalid data captured";
                request.getSession().setAttribute("CONTRACTOR_MSG",errMsg);
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);                
            }
            
            // read data from posted form and put into info object
            c.setContractorId(request.getParameter("ContractorId"));
            c.setContractorName(request.getParameter("ContractorName"));
            c.set1JInd((request.getParameter("Group1J") != null && request.getParameter("Group1J").equals("1J"))?-1:0);
            c.set2JInd((request.getParameter("Group2J") != null && request.getParameter("Group2J").equals("2J"))?-1:0);
            c.set3JInd((request.getParameter("Group3J") != null && request.getParameter("Group3J").equals("3J"))?-1:0);
            c.set4JInd((request.getParameter("Group4J") != null && request.getParameter("Group4J").equals("4J"))?-1:0);
            c.set1NInd((request.getParameter("Group1N") != null && request.getParameter("Group1N").equals("1N"))?-1:0);
            c.set2NInd((request.getParameter("Group2N") != null && request.getParameter("Group2N").equals("2N"))?-1:0);
            c.set3NInd((request.getParameter("Group3N") != null && request.getParameter("Group3N").equals("3N"))?-1:0);
            c.set4NInd((request.getParameter("Group4N") != null && request.getParameter("Group4N").equals("4N"))?-1:0);
            c.setLastUpdatedBy(secCtx.getUserId());
            request.getSession().setAttribute("CONTRACTOR_DATA",c); // save the collected data into session variable
            
            // write data
            ContractorDataBean contractorDB = getContractorDataBean();
            updateStatus = contractorDB.insertContractor(c,secCtx);
            
            // check return status and redirect
            if (updateStatus == 1){ // success
                postScreen = "ContractorList.jsp";
                request.getSession().removeAttribute("CONTRACTOR_DATA"); // remove the session variable
                String Msg = "Contractor added successfully!";
                request.getSession().setAttribute("CONTRACTOR_MSG",Msg);
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "ContractorAdd.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("CONTRACTOR_MSG",errMsg);
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("Duplicate key")>=0){ // duplicate key
                postScreen = "ContractorAdd.jsp";
                request.getSession().setAttribute("CONTRACTOR_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("SQL error")>=0){
                postScreen = "ContractorAdd.jsp";
                request.getSession().setAttribute("CONTRACTOR_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "ContractorAdd.jsp";
                request.getSession().setAttribute("CONTRACTOR_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
                throw new ServletException("ContractorAddERROR: AdminContractorServlet:performContractorAdd: Get Request Error\n" + ex.toString());
            }
        }
    }
    
    private void performContractorUpdate(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        ContractorInfo c = new ContractorInfo();
        try {
            if (request.getParameter("OrgKey1") == null){
                postScreen = "ContractorList.jsp";
                String errMsg = "Failed to update contractor! Invalid data captured";
                request.getSession().setAttribute("CONTRACTOR_MSG",errMsg);
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);                
            }
                        
            // read data from posted form and put into info object
            c.setContractorId(request.getParameter("ContractorId"));
            c.setContractorName(request.getParameter("ContractorName"));
            /*
            c.set1JInd((request.getParameter("Group1J") != null && request.getParameter("Group1J").equals("1J"))?-1:0);
            c.set2JInd((request.getParameter("Group2J") != null && request.getParameter("Group2J").equals("2J"))?-1:0);
            c.set3JInd((request.getParameter("Group3J") != null && request.getParameter("Group3J").equals("3J"))?-1:0);
            c.set4JInd((request.getParameter("Group4J") != null && request.getParameter("Group4J").equals("4J"))?-1:0);
            c.set1NInd((request.getParameter("Group1N") != null && request.getParameter("Group1N").equals("1N"))?-1:0);
            c.set2NInd((request.getParameter("Group2N") != null && request.getParameter("Group2N").equals("2N"))?-1:0);
            c.set3NInd((request.getParameter("Group3N") != null && request.getParameter("Group3N").equals("3N"))?-1:0);
            c.set4NInd((request.getParameter("Group4N") != null && request.getParameter("Group4N").equals("4N"))?-1:0);
            */
            c.setOrgKey1(request.getParameter("OrgKey1"));
            c.setLastUpdatedBy(secCtx.getUserId());
            request.getSession().setAttribute("CONTRACTOR_DATA",c); // save the collected data into session variable
            // write data
            ContractorDataBean ContractorDB = getContractorDataBean();
            updateStatus = ContractorDB.updateContractor(c,secCtx);
            
            // check return status and redirect
            if (updateStatus == 1){
                postScreen = "ContractorList.jsp";
                request.getSession().removeAttribute("CONTRACTOR_DATA"); // remove the session variable
                String Msg = "Contractor updated successfully!";
                request.getSession().setAttribute("CONTRACTOR_MSG",Msg);
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "ContractorUpdate.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("CONTRACTOR_MSG",errMsg);
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("Duplicate key")>=0){
                postScreen = "ContractorUpdate.jsp";
                request.getSession().setAttribute("CONTRACTOR_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("SQL error")>=0){
                postScreen = "ContractorUpdate.jsp";
                request.getSession().setAttribute("CONTRACTOR_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "ContractorUpdate.jsp";
                request.getSession().setAttribute("CONTRACTOR_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
                throw new ServletException("ContractorUpdateERROR: AdminContractorServlet:performContractorUpdate: Get Request Error\n" + ex.toString());
            }
        }
        
    }
    
    private void performContractorDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        ContractorInfo c = new ContractorInfo();
        try {
            if (request.getParameter("OrgKey1") == null){
                postScreen = "ContractorList.jsp";
                String errMsg = "Failed to delete contractor! Invalid data captured";
                request.getSession().setAttribute("CONTRACTOR_MSG",errMsg);
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);                
            }
            
            
            c.setOrgKey1(request.getParameter("OrgKey1"));
            c.setLastUpdatedBy(secCtx.getUserId());
            request.getSession().setAttribute("CONTRACTOR_DATA",c);
            // write data
            ContractorDataBean ContractorDB = getContractorDataBean();
            updateStatus = ContractorDB.deleteContractor(c,secCtx);
            
            // check return status and redirect
            if (updateStatus == 1){
                request.getSession().removeAttribute("CONTRACTOR_DATA");
                postScreen = "ContractorList.jsp";
                String Msg = "Contractor deleted successfully!";
                request.getSession().setAttribute("CONTRACTOR_MSG",Msg);
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "ContractorDelete.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("CONTRACTOR_MSG",errMsg);
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("SQL error")>=0){
                postScreen = "ContractorDelete.jsp";
                request.getSession().setAttribute("CONTRACTOR_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "ContractorDelete.jsp";
                request.getSession().setAttribute("CONTRACTOR_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("used in other tables")>=0){
                postScreen = "ContractorDelete.jsp";
                request.getSession().setAttribute("CONTRACTOR_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CONTRACTOR_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);                
            }else{
                throw new ServletException("ContractorDeleteERROR: AdminContractorServlet:performContractorDelete: Get Request Error\n" + ex.toString());
            }
        }
    }
    
    
}