/*
 * AdminDeptServlet.java
 *
 * Created on 28th June, 2013
 */

package qpses.servlet;

import java.io.*;
import java.util.HashMap;


import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import qpses.business.DeptDataBean;
import qpses.business.DeptInfo;
import qpses.security.SecurityContext;
import qpses.util.SysException;

public class AdminDeptServlet extends HttpServlet {
    
    // log4j
    static Logger logger = Logger.getLogger(AdminDeptServlet.class.getName());
    
    private static String postScreen = null;
    private static String requestAction = null;
    
    public DeptDataBean getDeptDataBean() throws SysException{
    	return new DeptDataBean();
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        requestAction = request.getServletPath();
        if (requestAction.equals("/qpsadmin/DeptAdd.servlet")) {
            performDeptAdd(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/DeptUpdate.servlet")) {
            performDeptUpdate(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/DeptDelete.servlet")) {
            performDeptDelete(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/DeptSearch.servlet")) {
            performDeptSearch(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/DeptSearchReset.servlet")) {
            performDeptSearchReset(request, response);
            return;
        }
        
        
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    }
    
    private void performDeptAdd(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        DeptInfo dept = new DeptInfo();
        try {
            if (request.getParameter("DPDeptId") == null ||
                request.getParameter("SOADeptId")==null ||
                request.getParameter("DeptName")==null){
                postScreen = "DeptList.jsp";
                String errMsg = "Failed to add B/D! Invalid data captured";
                request.getSession().setAttribute("DEPARTMENT_MSG",errMsg);
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);                
            }
           
            
            // read data from posted form and put into info object
            dept.setDPDeptId(request.getParameter("DPDeptId"));
            dept.setSOADeptId(request.getParameter("SOADeptId"));
            dept.setDeptName(request.getParameter("DeptName"));
            dept.setLastUpdatedBy(secCtx.getUserId());
            request.getSession().setAttribute("DEPARTMENT_DATA",dept); // save the collected data into session variable
            
            // write data
            DeptDataBean deptDB = getDeptDataBean();
            updateStatus = deptDB.insertDept(dept,secCtx);
            
            // check return status and redirect
            if (updateStatus == 1){ // success
                postScreen = "DeptList.jsp";
                request.getSession().removeAttribute("DEPARTMENT_DATA"); // remove the session variable
                String Msg = "Department added successfully!";
                request.getSession().setAttribute("DEPARTMENT_MSG",Msg);
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "DeptAdd.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("DEPARTMENT_MSG",errMsg);
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("Duplicate key")>=0){ // duplicate key
                postScreen = "DeptAdd.jsp";
                request.getSession().setAttribute("DEPARTMENT_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
             }else if (ex.getMessage().indexOf("SQL error")>=0){
                postScreen = "DeptAdd.jsp";
                request.getSession().setAttribute("DEPARTMENT_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "DeptAdd.jsp";
                request.getSession().setAttribute("DEPARTMENT_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);               
            }else{
                throw new ServletException("DeptAddERROR: AdminDeptServlet:performDeptAdd: Get Request Error\n" + ex.toString());
            }
        }
    }
    
    private void performDeptUpdate(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        

        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        DeptInfo dept = new DeptInfo();
        String postScreen = request.getParameter("PostScreen");        
        request.getSession().setAttribute("DEPT_POST_SCREEN",postScreen);                
        
        try {
            
            // save parameters
            String order_by = request.getParameter("OrderBy");
            String order_dir = request.getParameter("OrderDir");
            if (order_by != null || order_dir !=null){
                HashMap orderHash = new HashMap();
                orderHash.put("ORDER_BY",order_by);
                orderHash.put("ORDER_DIR",order_dir);
                if (postScreen.indexOf("Search") >=0){
                    request.getSession().setAttribute("DEPT_SEARCH_ORDER",orderHash);
                }else{
                    request.getSession().setAttribute("DEPT_LIST_ORDER",orderHash);
                }
            }            
            
            if (request.getParameter("OrgKey1") == null ||
                request.getParameter("OrgKey2")==null){
                postScreen = "DeptList.jsp";
                String errMsg = "Failed to update B/D! Invalid data captured";
                request.getSession().setAttribute("DEPARTMENT_MSG",errMsg);
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);                
            }
            
            // read data from posted form and put into info object
            dept.setDPDeptId(request.getParameter("DPDeptId"));
            dept.setSOADeptId(request.getParameter("SOADeptId"));
            dept.setDeptName(request.getParameter("DeptName"));
            dept.setLastUpdatedBy(secCtx.getUserId());
            dept.setOrgKey1(request.getParameter("OrgKey1"));
            dept.setOrgKey2(request.getParameter("OrgKey2"));           
            request.getSession().setAttribute("DEPARTMENT_DATA",dept); // save the collected data into session variable
            // write data
            DeptDataBean DeptDB = getDeptDataBean();
            updateStatus = DeptDB.updateDept(dept,secCtx);
            
            // check return status and redirect
            if (updateStatus == 1){                
                request.getSession().removeAttribute("DEPARTMENT_DATA"); // remove the session variable
                String Msg = "Dept updated successfully!";
                request.getSession().setAttribute("DEPARTMENT_MSG",Msg);
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "DeptUpdate.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("DEPARTMENT_MSG",errMsg);
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("Duplicate key")>=0){
                postScreen = "DeptUpdate.jsp";
                request.getSession().setAttribute("DEPARTMENT_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("SQL error")>=0){
                postScreen = "DeptUpdate.jsp";
                request.getSession().setAttribute("DEPARTMENT_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "DeptUpdate.jsp";
                request.getSession().setAttribute("DEPARTMENT_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
                throw new ServletException("DeptUpdateERROR: AdminDeptServlet:performDeptUpdate: Get Request Error\n" + ex.toString());
            }
        }
        
    }
    
    private void performDeptDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        DeptInfo dept = new DeptInfo();
        String postScreen = request.getParameter("PostScreen");        
        request.getSession().setAttribute("DEPT_POST_SCREEN",postScreen);                
        try {
            // save parameters
            String order_by = request.getParameter("OrderBy");
            String order_dir = request.getParameter("OrderDir");
            if (order_by != null || order_dir !=null){
                HashMap orderHash = new HashMap();
                orderHash.put("ORDER_BY",order_by);
                orderHash.put("ORDER_DIR",order_dir);
                if (postScreen.indexOf("Search") >=0){
                    request.getSession().setAttribute("DEPT_SEARCH_ORDER",orderHash);
                }else{
                    request.getSession().setAttribute("DEPT_LIST_ORDER",orderHash);
                }
            }                        
            if (request.getParameter("OrgKey1") == null ||
                request.getParameter("OrgKey2")==null){
                postScreen = "DeptList.jsp";
                String errMsg = "Failed to delete B/D! Invalid data captured";
                request.getSession().setAttribute("DEPARTMENT_MSG",errMsg);
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);                
            }
            
            dept.setOrgKey1(request.getParameter("OrgKey1"));            
            dept.setOrgKey2(request.getParameter("OrgKey2"));
            dept.setLastUpdatedBy(secCtx.getUserId());
            request.getSession().setAttribute("DEPARTMENT_DATA",dept);
            // write data
            DeptDataBean DeptDB = getDeptDataBean();
            updateStatus = DeptDB.deleteDept(dept,secCtx);    
            
            // check return status and redirect
            if (updateStatus == 1){
                request.getSession().removeAttribute("DEPARTMENT_DATA");                
                String Msg = "Department deleted successfully!";
                request.getSession().setAttribute("DEPARTMENT_MSG",Msg);
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "DeptDelete.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("DEPARTMENT_MSG",errMsg);
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
           if (ex.getMessage().indexOf("SQL error")>=0){
                postScreen = "DeptDelete.jsp";
                request.getSession().setAttribute("DEPARTMENT_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "DeptDelete.jsp";
                request.getSession().setAttribute("DEPARTMENT_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("used in other tables")>=0){
                postScreen = "DeptDelete.jsp";
                request.getSession().setAttribute("DEPARTMENT_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("DEPARTMENT_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);                
            }else{
                throw new ServletException("DeptDeleteERROR: AdminDeptServlet:performDeptDelete: Get Request Error\n" + ex.toString());
            }                
        }
    }
    
        
    private void performDeptSearch(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       

        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        DeptInfo dept = new DeptInfo();
        try {
            String dpDeptId = request.getParameter("DPDeptId");
            String soaDeptId=request.getParameter("SOADeptId");
            String deptName = request.getParameter("DeptName");
            
            if ( dpDeptId != null) dept.setDPDeptId(dpDeptId);
            if ( soaDeptId != null) dept.setSOADeptId(soaDeptId);
            if ( deptName != null) dept.setDeptName(deptName);

            request.getSession().setAttribute("DEPARTMENT_SEARCH_PARAMETER",dept);
            request.getSession().removeAttribute("DEPT_SEARCH_ORDER"); 
            postScreen = "DeptSearch.jsp";
            response.sendRedirect(postScreen);
            return;
        }catch(Exception ex){
        	logger.error(ex);
            throw new ServletException("Servlet Error: DeptSearch ERROR: AdminDeptServlet:performDeptSearch: Get Request Error\n" + ex.toString());
        }
    }
    
    private void performDeptSearchReset(HttpServletRequest request, HttpServletResponse response)
    throws ServletException {
        
        try{
            request.getSession().removeAttribute("DEPARTMENT_SEARCH_PARAMETER");
            request.getSession().removeAttribute("DEPT_SEARCH_ORDER");            
            postScreen = request.getHeader("referer");            
            response.sendRedirect(postScreen);
            return;
        }catch(Exception ex){
        	logger.error(ex);
            throw new ServletException("Servlet Error: DeptSearchReset ERROR: AdminDeptServlet:performDeptSearchReset: Get Request Error\n" + ex.toString());
        }
    } 
}