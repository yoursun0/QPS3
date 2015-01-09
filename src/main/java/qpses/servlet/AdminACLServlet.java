/**
 * AdminACLServlet.java
 * 
 * Created on 10th July, 2013
 **/

package qpses.servlet;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.RecordFormatException;
import qpses.business.ACLDataBean;
import qpses.business.ACLInfo;
import qpses.business.PdfHelper;
import qpses.security.SecurityContext;
import qpses.security.SecurityDataBean;
import qpses.security.SecurityException;
import qpses.security.SecurityServlet;
import qpses.util.Constant;
import qpses.util.SendEmail;
import qpses.util.SysException;
import qpses.util.SysManager;


@SuppressWarnings({ "deprecation", "serial" })
public class AdminACLServlet extends HttpServlet {
    
    // log4j
    static Logger logger = Logger.getLogger(AdminACLServlet.class.getName());
    
    
    private static String postScreen = null;
    private static String requestAction = null;
    
    public ACLDataBean getACLDataBean() throws SysException{
    	return new ACLDataBean();
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        requestAction = request.getServletPath();
        if (requestAction.equals("/qpsadmin/ACLAdd.servlet")) {
            performACLAdd(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/ACLUpdate.servlet")) {
            performACLUpdate(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/ACLDelete.servlet")) {
            performACLDelete(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/ACLSearch.servlet")) {
            performACLSearch(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/ACLSearchReset.servlet")) {
            performACLSearchReset(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/InactiveUsersReport.servlet")) {
            performPrintInactiveUsersReport(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/UnlockUser.servlet")) {
            performUnlockUser(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/UnlockSingleUser.servlet")) {
            performUnlockSingleUser(request, response);
            return;
        }
       /* if (requestAction.equals("/qpsadmin/ACLImport.servlet")) {
            performACLImport(request, response);
            return;
        }*/
        if (requestAction.equals("/qpsadmin/EnableUser.servlet")) {
        	String userId = request.getParameter("UserId");
        	SecurityServlet servlet = new SecurityServlet();
        	servlet.performForgotPassword(userId,request,response);
            response.sendRedirect("ACLEnableUser.jsp");
        	return;
        }

    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    }
    
    private void performACLAdd(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // initialization
        int updateStatus =0;
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        ACLInfo acl = new ACLInfo();
        try {
            // get data from input form
            acl.setUserId(request.getParameter("UserId"));
            acl.setDeptId(request.getParameter("DeptId"));
            acl.setFirstName(request.getParameter("FirstName"));
            acl.setLastName(request.getParameter("LastName"));
            acl.setPassword(request.getParameter("Password"));
            acl.setEmail(request.getParameter("Email"));
            acl.setUserGroup(request.getParameter("UserRole"));
            acl.setLastUpdatedBy(secCtx.getUserId());
            acl.setEmailInd(Integer.parseInt(request.getParameter("EmailInd")));
            acl.setEffectiveDate(SysManager.getSQLDate(request.getParameter("EffectiveDate")));
            if (!"".equals(request.getParameter("ExpiryDate"))){
            	acl.setExpiryDate(SysManager.getSQLDate(request.getParameter("ExpiryDate")));
            }else{
            	acl.setExpiryDate(SysManager.getSQLDate(Constant.QPSIS_EXPIRY_DATE));
            }
            request.getSession().setAttribute("ACL_DATA",acl);
            ACLDataBean aclDB = getACLDataBean();
            updateStatus = aclDB.insertACL(acl,secCtx);
            
            // return page
            if (updateStatus == 1){
                postScreen = "ACLList.jsp";
                request.getSession().removeAttribute("ACL_DATA");
                String Msg = "User added to Access Control List successfully!";
                request.getSession().setAttribute("ACL_MSG",Msg);
                request.getSession().setAttribute("ACL_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "ACLAdd.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("Duplicate key")>=0){
                postScreen = "ACLAdd.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("SQL Error")>=0){
                postScreen = "ACLAdd.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "ACLAdd.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
                throw new ServletException("Servlet Error: ACLAdd ERROR: AdminACLServlet:performACLAdd: \r\n"+
                        "Get Request Error\n" + ex.toString());
            }
        }
    }
    
    private void performACLUpdate(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // initialization
        int updateStatus =0;
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        ACLInfo acl = new ACLInfo();
        String postScreen = request.getParameter("PostScreen");
        request.getSession().setAttribute("ACL_POST_SCREEN",postScreen);
        
        try {
            
            // save parameters
            String order_by = request.getParameter("OrderBy");
            String order_dir = request.getParameter("OrderDir");
            if (order_by != null || order_dir !=null){
                HashMap orderHash = new HashMap();
                orderHash.put("ORDER_BY",order_by);
                orderHash.put("ORDER_DIR",order_dir);
                if (postScreen.indexOf("Search") >=0){
                    request.getSession().setAttribute("ACL_SEARCH_ORDER",orderHash);
                }else{
                    request.getSession().setAttribute("ACL_LIST_ORDER",orderHash);
                }
            }
            String password = request.getParameter("Password");
            
            if ((password == null)||("".equals(password))){
            	password = "NO";
            }
            
            acl.setActiveInd(Integer.parseInt(request.getParameter("ActiveInd")));
            acl.setEmailInd(Integer.parseInt(request.getParameter("EmailInd")));
            acl.setPassword(password);
            acl.setLastUpdatedBy(secCtx.getUserId());
            acl.setOrgKey1(request.getParameter("OrgKey1"));
            acl.setOrgKey2(request.getParameter("OrgKey2"));
            acl.setOrgKey3(request.getParameter("OrgKey3"));
            acl.setEffectiveDate(SysManager.getSQLDate(request.getParameter("EffectiveDate")));
            acl.setExpiryDate(SysManager.getSQLDate(request.getParameter("ExpiryDate")));
            request.getSession().setAttribute("ACL_DATA",acl);
            ACLDataBean aclDB = getACLDataBean();
            updateStatus = aclDB.updateACL(acl,secCtx);
            
            // return page
            if (updateStatus == 1){
                request.getSession().removeAttribute("ACL_POST_SCREEN");
                request.getSession().removeAttribute("ACL_DATA");
                String Msg = "User in Access Control List updated successfully!";
                request.getSession().setAttribute("ACL_MSG",Msg);
                request.getSession().setAttribute("ACL_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "ACLUpdate.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("Duplicate key")>=0){
                postScreen = "ACLUpdate.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("SQL Error")>=0){
                postScreen = "ACLUpdate.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "ACLUpdate.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
                throw new ServletException("Servlet Error: ACLUpdate ERROR: AdminACLServlet:performACLUpdate: \r\n"+
                        "Get Request Error\n" + ex.toString());
            }
        }
        
    }
    
    private void performACLDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        ACLInfo acl = new ACLInfo();
        String postScreen = request.getParameter("PostScreen");
        request.getSession().setAttribute("ACL_POST_SCREEN",postScreen);
        try {
            // save parameters
            String order_by = request.getParameter("OrderBy");
            String order_dir = request.getParameter("OrderDir");
            if (order_by != null || order_dir !=null){
                HashMap orderHash = new HashMap();
                orderHash.put("ORDER_BY",order_by);
                orderHash.put("ORDER_DIR",order_dir);
                if (postScreen.indexOf("Search") >=0){
                    request.getSession().setAttribute("ACL_SEARCH_ORDER",orderHash);
                }else{
                    request.getSession().setAttribute("ACL_LIST_ORDER",orderHash);
                }
            }
            
            if (request.getParameter("OrgKey1") == null ||
                    request.getParameter("OrgKey2") == null ||
                    request.getParameter("OrgKey3") == null){
                postScreen = "ACLList.jsp";
                String errMsg = "Failed to delete user from Access Control List! Invalid parameters for deletion! ";
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            
            acl.setOrgKey1(request.getParameter("OrgKey1"));
            acl.setOrgKey2(request.getParameter("OrgKey2"));
            acl.setOrgKey3(request.getParameter("OrgKey3"));
            acl.setLastUpdatedBy(secCtx.getUserId());
            request.getSession().setAttribute("ACL_DATA",acl);
            ACLDataBean aclDB = getACLDataBean();
            updateStatus = aclDB.deleteACL(acl,secCtx);
            
            // return page
            if (updateStatus == 1){
                request.getSession().removeAttribute("ACL_DATA");
                request.getSession().removeAttribute("ACL_POST_SCREEN");
                String Msg = "User deleted from Access Control List successfully!";
                request.getSession().setAttribute("ACL_MSG",Msg);
                request.getSession().setAttribute("ACL_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "ACLDelete.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("SQL Error")>=0){
                postScreen = "ACLDelete.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "ACLDelete.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
                throw new ServletException("Servlet Error: ACLDelete ERROR: AdminACLServlet:performACLDelete: \r\n"+
                        "Get Request Error\n" + ex.toString());
            }
        }
    }
    
    private void performACLSearch(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        ACLInfo acl = new ACLInfo();
        try {
            String userId = request.getParameter("UserId");
            String userGroup = request.getParameter("UserRole");
            String deptId=  request.getParameter("DeptId");
            String activeInd=request.getParameter("ActiveInd");
            String expiryDate=request.getParameter("ExpiryDate");
            
            if (activeInd!=null){
                acl.setActiveInd(Integer.parseInt(activeInd));
            }else{
                acl.setActiveInd(-9); // to override the value (0) stored in initialized ACLInfo
            }
            if (! userId.equals("")) acl.setUserId(userId);
            if (! userGroup.equals("")) acl.setUserGroup(userGroup);
            if (! deptId.equals("")) acl.setDeptId(deptId);
            if (! expiryDate.equals("")) acl.setExpiryDate(SysManager.getSQLDate(expiryDate));
            
            request.getSession().setAttribute("ACL_SEARCH_PARAMETER",acl);
            request.getSession().removeAttribute("ACL_SEARCH_ORDER");
            postScreen = "ACLSearch.jsp";
            response.sendRedirect(postScreen);
            return;
        }catch(Exception ex){
        	logger.error(ex);
            throw new ServletException("Servlet Error: ACLSearch ERROR: AdminACLServlet:performACLSearch: \r\n"+
                    "Get Request Error\n" + ex.toString());
        }
    }
    
    private void performACLSearchReset(HttpServletRequest request, HttpServletResponse response)
    throws ServletException {
        
        try{
            request.getSession().removeAttribute("ACL_SEARCH_PARAMETER");
            request.getSession().removeAttribute("ACL_SEARCH_ORDER");
            postScreen = request.getHeader("referer");
            response.sendRedirect(postScreen);
            return;
        }catch(Exception ex){
        	logger.error(ex);
            throw new ServletException("Servlet Error: ACLSearchReset ERROR: AdminACLServlet:performACLSearchReset: \r\n"+
                    "Get Request Error\n" + ex.toString());
        }
    }
    
    private void performPrintInactiveUsersReport(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try{
            // get user id
            SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
            String adminId = secCtx.getUserId();
            
            // get the data
            ACLDataBean aclDB = getACLDataBean();
            List InactiveUserList = aclDB.selectInactiveUsers();
            
            // Variable
            int numOfRow = 0;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            ACLInfo InactiveUserInfo = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            // Initialize PDF helper
            PdfHelper pdfHelper = new PdfHelper();
            pdfHelper.setDisplayUserName(adminId);
            
            // Initialize PDF document
            Document aPdfDoc = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(aPdfDoc, baos);
            writer.setPageEvent(pdfHelper);
            aPdfDoc.setMargins(50, 50, 50, 50);
            aPdfDoc.open();
            
            // Create Header
            aPdfDoc.add(pdfHelper.getTitleParagraph("SOA-QPS3 Quality Professional Services Information System"));
            aPdfDoc.add(Chunk.NEWLINE);
            aPdfDoc.add(pdfHelper.getTitleParagraph("Inactive User Report"));
            aPdfDoc.add(Chunk.NEWLINE);
            
            aPdfTable = new PdfPTable(5);
            int tableColWidths[] = {5, 40, 25, 15, 15}; // percentage
            aPdfTable.setWidths(tableColWidths);
            aPdfTable.setWidthPercentage(100);
            aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            
            // Row 1
            cell = pdfHelper.getHeaderCell("");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("B/D");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("User ID");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Effective Date");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Expiry Date");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            
            if (InactiveUserList.isEmpty()){
                cell = pdfHelper.getCellSmall("No inactive user");
                cell.setColspan(5);
                cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM|Rectangle.RIGHT);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                aPdfDoc.add(aPdfTable);
                aPdfDoc.add(Chunk.NEWLINE);
            }else{
                numOfRow = InactiveUserList.size();        // no. of records
                int numOfPrintRow =1;                                  // no. of printed rows
                int numOfRowPerPage = 22;                         //no. of rows in a page
                
                // build content
                for (int i = 0; i < numOfRow; i++) {
                    // print the table and create new page
                    if (numOfPrintRow > numOfRowPerPage){
                        aPdfDoc.add(aPdfTable);
                        aPdfDoc.newPage();
                    }
                    
                    // print header for first page and after page break
                    if (numOfPrintRow > numOfRowPerPage){
                        aPdfTable = new PdfPTable(5);
                        //tableColWidths[] = {5, 40, 25, 15, 15}; // percentage
                        aPdfTable.setWidths(tableColWidths);
                        aPdfTable.setWidthPercentage(100);
                        aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                        
                        // Create Header
                        aPdfDoc.add(pdfHelper.getTitleParagraph("SOA-QPS3 Quality Professional Services Information System"));
                        aPdfDoc.add(Chunk.NEWLINE);
                        aPdfDoc.add(pdfHelper.getTitleParagraph("Inactive User Report"));
                        aPdfDoc.add(Chunk.NEWLINE);
                        
                        // Row 1
                        
                        cell = pdfHelper.getHeaderCell("");
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getHeaderCell("B/D");
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getHeaderCell("User ID");
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getHeaderCell("Effective Date");
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getHeaderCell("Expiry Date");
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        aPdfTable.addCell(cell);
                        
                        numOfPrintRow =1;
                    }
                    
                    InactiveUserInfo = (ACLInfo) InactiveUserList.get(i);
                    String userGroup = InactiveUserInfo.getUserGroup();
                    
                    // write counter
                    cell = pdfHelper.getCellSmall(Integer.toString(i+1));
                    cell.setBorder(Rectangle.LEFT|Rectangle.BOTTOM);
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                    
                    
                    // write dept id
                    cell = pdfHelper.getCellSmall(InactiveUserInfo.getDeptName());
                    cell.setBorder(Rectangle.LEFT|Rectangle.BOTTOM);
                    cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    aPdfTable.addCell(cell);
                    
                    // write user id
                    cell = pdfHelper.getCellSmall(InactiveUserInfo.getUserId());
                    cell.setBorder(Rectangle.LEFT|Rectangle.BOTTOM);
                    cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    aPdfTable.addCell(cell);
                    
                    // write effective date
                    if (InactiveUserInfo.getEffectiveDate() !=null){
                        cell = pdfHelper.getCellSmall(SysManager.getStringfromSQLDate(InactiveUserInfo.getEffectiveDate()));
                    }else{
                        cell = pdfHelper.getCellSmall("");
                    }
                    cell.setBorder(Rectangle.LEFT|Rectangle.BOTTOM);
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                    
                    // write expiry date
                    if (InactiveUserInfo.getExpiryDate() !=null){
                        cell = pdfHelper.getCellSmall(SysManager.getStringfromSQLDate(InactiveUserInfo.getExpiryDate()));
                    }else{
                        cell = pdfHelper.getCellSmall("");
                    }
                    cell.setBorder(Rectangle.LEFT|Rectangle.BOTTOM|Rectangle.RIGHT);
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                    numOfPrintRow++;
                }
                aPdfDoc.add(aPdfTable);
            }
            
            // Write PDF objects to stream
            aPdfDoc.close();
            
            // Response Setting
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=\"" + "SOA-QPS3 - Inactive Users Report.pdf" + "\"");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentLength(baos.size());
            
            // Output PDF file
            ServletOutputStream sos = response.getOutputStream();
            baos.writeTo(sos);
            sos.flush();
            
        } catch(Exception ex) {
        	logger.error(ex);
            throw new ServletException("Servlet Error: Print Privileges Report ERROR: " +
                    "AdminACL:performPrintInactiveUsersReport: " +
                    "Get Request Error\n" + ex.toString());
        }
    }
    
    private void performUnlockUser(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // initialization
        int updateStatus =0;
        int i = 0;
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        List ACLList = new ArrayList();
        boolean rowChanged=false;
        boolean validData = true;
        // save parameters
        String order_by = request.getParameter("OrderBy");
        String order_dir = request.getParameter("OrderDir");
        if (order_by != null || order_dir !=null){
            HashMap orderHash = new HashMap();
            orderHash.put("ORDER_BY",order_by);
            orderHash.put("ORDER_DIR",order_dir);
            request.getSession().setAttribute("ACLUNLOCK_SEARCH_ORDER",orderHash);
        }
        
        try {
        	ACLDataBean aclDB = getACLDataBean();
            int rowNo = Integer.parseInt((String) request.getParameter("RowNo"));
            while (validData && (i < rowNo)){
                rowChanged =(request.getParameter("ChkEdit"+i) !=null &&
                        request.getParameter("ChkEdit"+i).equals("changed"))?true:false;
                if (rowChanged){
                    ACLInfo acl = new ACLInfo();
                    String userId = request.getParameter("UserId"+i).trim();
                    String deptId = request.getParameter("DeptId"+i).trim();
                    
                    // validate data input
                    if (userId == null || deptId == null) { // if nothing recevied
                        validData =false;
                    }
                    acl.setUserId(userId);
                    acl.setDeptId(deptId);
                    aclDB.insertUnlockLog(secCtx,userId,0);
                    ACLList.add(acl);                   
                }
                 i++; 
            }
            if (!validData){
                postScreen = "ACLUnlockUser.jsp";
                String errMsg = "Invalid data found!";
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            if (! ACLList.isEmpty()){
                updateStatus = aclDB.unlockUser(ACLList,secCtx);
            }
            // return page
            if (updateStatus == 1){
                postScreen = "ACLUnlockUser.jsp";
                request.getSession().removeAttribute("ACL_DATA");
                String Msg = "Selected users are unlocked successfully!";
                request.getSession().setAttribute("ACL_MSG",Msg);
                request.getSession().setAttribute("ACL_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }else {
                postScreen = "ACLUnlockUser.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("No record was updated")>=0){
                postScreen = "ACLUnlockUser.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("SQL Error")>=0){
                postScreen = "ACLUnlockUser.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
                throw new ServletException("Servlet Error: AdminACLServlet:performUnlockUser: \r\n"+
                        "Get Request Error\n" + ex.toString());
            }
        }
    }
    
    private void performUnlockSingleUser(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // initialization
        int updateStatus =0;
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        ACLInfo acl = new ACLInfo();
        String postScreen = request.getParameter("PostScreen");
        request.getSession().setAttribute("ACL_POST_SCREEN",postScreen);
        try {
            // save parameters
            String order_by = request.getParameter("OrderBy");
            String order_dir = request.getParameter("OrderDir");
            if (order_by != null || order_dir !=null){
                HashMap orderHash = new HashMap();
                orderHash.put("ORDER_BY",order_by);
                orderHash.put("ORDER_DIR",order_dir);
                if (postScreen.indexOf("Search") >=0){
                    request.getSession().setAttribute("ACL_SEARCH_ORDER",orderHash);
                }else{
                    request.getSession().setAttribute("ACL_LIST_ORDER",orderHash);
                }
            }
            
            acl.setLastUpdatedBy(secCtx.getUserId());
            acl.setOrgKey1(request.getParameter("OrgKey1"));
            acl.setOrgKey2(request.getParameter("OrgKey2"));
            acl.setOrgKey3(request.getParameter("OrgKey3"));
            acl.setEffectiveDate(SysManager.getSQLDate(request.getParameter("EffectiveDate")));
            acl.setExpiryDate(SysManager.getSQLDate(request.getParameter("ExpiryDate")));
            request.getSession().setAttribute("ACL_DATA",acl);
            ACLDataBean aclDB = getACLDataBean();
            updateStatus = aclDB.unlockSingleUser(acl,secCtx);
            
            // return page
            if (updateStatus == 1){
                aclDB.insertUnlockLog(secCtx,acl.getOrgKey3(),0);
                request.getSession().removeAttribute("ACL_DATA");
                request.getSession().removeAttribute("ACL_POST_SCREEN");
                String Msg = "User unlocked successfully!";
                request.getSession().setAttribute("ACL_MSG",Msg);
                request.getSession().setAttribute("ACL_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "ACLUpdate.jsp";
                String errMsg = "System Error! Status Code = " + updateStatus;
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("Duplicate key")>=0){
                postScreen = "ACLUpdate.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("SQL Error")>=0){
                postScreen = "ACLUpdate.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "ACLUpdate.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
                throw new ServletException("Servlet Error: ACLUpdate ERROR: AdminACLServlet:performUnlockSingleUser: \r\n"+
                        "Get Request Error\n" + ex.toString());
            }
        }
        
    }
    
    /* private void performACLImport(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // initialization
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        DiskFileUpload upload = new DiskFileUpload();
        upload.setRepositoryPath(Constant.QPSIS_TEMP_DIR);
        upload.setSizeThreshold(102400);// limit Memory Size
        upload.setSizeMax(-1); // unlimit file size
        List allUploadeditems = null;
        int uploadCounter =0;
        int uploadStatus =0;
        
        
        // parse request
        try {
            allUploadeditems = upload.parseRequest(request);
        } catch (FileUploadException exFile) {
            String UploadError = "ServletError:AdminACLServlet:performACLImport\r\n" + exFile.getMessage();
            logger.error(UploadError,exFile);
            throw new IOException(UploadError);
        }
        
        ACLInfo acl = new ACLInfo();
        String deptId ="";
        String expiryDate ="";
        try {
            // get data from input form
            Iterator iter = allUploadeditems.iterator();
            FileItem excelfile = null;
            while (iter.hasNext()) {
                FileItem uploadeditem = (FileItem) iter.next();
                if ((! uploadeditem.isFormField() && uploadeditem.getSize() != 0)){ // get excel file
                    //&& uploadeditem.getContentType().startsWith("application/vnd.ms-excel")){ // file without zero size
                    excelfile = uploadeditem;
                    long excelfilesize = excelfile.getSize();
                    acl.setExcelFileSize(excelfile.getSize());
                    File filePath = new File(excelfile.getName());
                    acl.setExcelFileName(filePath.getName());
                    acl.setFileContentType(excelfile.getContentType());
                } else  if (uploadeditem.getFieldName().trim().endsWith("DeptId")) {
                    acl.setDeptId(uploadeditem.getString().trim());
                } else  if (uploadeditem.getFieldName().trim().endsWith("ExpiryDate")) {
                    acl.setExpiryDate(SysManager.getSQLDate(uploadeditem.getString().trim()));
                }
            }
            request.getSession().setAttribute("ACL_DATA",acl);
            // check for input parameters
            
            if (acl.getExpiryDate() == null || acl.getDeptId() == null){
                uploadStatus = -49;  // invalid parameter
            }else{
                if (excelfile ==null) {     // check for any excel file uploaded; otherwise, return error;
                    uploadStatus = -89;
                }else{
                    InputStream excelFileStream = excelfile.getInputStream();
                    HSSFWorkbook wb    = new HSSFWorkbook(excelFileStream);
                    HSSFSheet sheet = null;
                    ACLDataBean aclDB = getACLDataBean();
                    
                    // create temporary file
                    try{
                        aclDB.createTempACL();
                    }catch(SysException dbEx){
                        String err = "Problem encountered in creating temporary ACL";
                        throw new ServletException(err);
                    }
                    
                    // check works  HSSFSheet
                    // check worksheet name = "Part I, II & III"
                    for (int i =0;i<wb.getNumberOfSheets();i++){
                        if (wb.getSheetName(i).indexOf(Constant.ACL_SHEETNAME)>=0){
                            sheet  = wb.getSheetAt(i);
                        }
                    }
                    
                    if (sheet == null){
                        uploadStatus = -39;  // wrong sheet name
                    }else{
                        int i =Constant.ACL_HEADER_ROWNO;
                        if (! (sheet.getRow(i).getCell((short) Constant.ACL_USER_ID_COLNO).getStringCellValue().trim())
                        .equals(Constant.ACL_USER_ID_COL_NAME)){
                            uploadStatus = -19;     //invalid file format
                        }
                        if (uploadStatus ==0 &&
                                ! (sheet.getRow(i).getCell((short) Constant.ACL_EFFECTIVE_DATE_COLNO).getStringCellValue().trim())
                                .equals(Constant.ACL_EFFECTIVE_DATE_COL_NAME)){
                            uploadStatus = -29;     //invalid file format
                        }
                        i =Constant.ACL_START_ROWNO;
                        if (uploadStatus == 0){
                            // begin import data
                            
                            boolean importFinish = false;
                            while (! importFinish && uploadStatus >=0){
                                HSSFCell aCell = sheet.getRow(i).getCell((short) Constant.ACL_USER_ID_COLNO);
                                String userId = aCell.getStringCellValue().trim();
                                if (userId.equals("")){
                                    importFinish = true;
                                }else{
                                    aCell = sheet.getRow(i).getCell((short) Constant.ACL_VALID_USER_COLNO);
                                    if (aCell.getStringCellValue().trim().equals("")){
                                        aCell = sheet.getRow(i).getCell((short) Constant.ACL_EFFECTIVE_DATE_COLNO);
                                        java.sql.Date effectiveDate = SysManager.getSQLDate(aCell.getDateCellValue());
                                        acl.setUserId(userId);
                                        acl.setEffectiveDate(effectiveDate);
                                        //acl.setLastUpdatedBy(secCtx.getUserId());
                                        if (acl.getExpiryDate().before(effectiveDate)){
                                            uploadStatus = -59;
                                        }else{
                                            uploadStatus = aclDB.replaceACL(acl,secCtx);
                                            uploadCounter+=uploadStatus;
                                        }
                                    }
                                }
                                i++;
                            }
                        }
                    }
                    
                    // replace temporary file
                    try{
                        aclDB.replaceTempACL();
                    }catch(SysException dbEx){
                        String err = "Problem encountered in replacing ACL";
                        throw new ServletException(err);
                    }
                }
            }
            
            // return page
            if (uploadStatus > 0){
                request.getSession().removeAttribute("ACL_DATA");
                postScreen = "ACLList.jsp";
                String Msg = "File imported successfully!<BR>" + uploadCounter + " records imported.";
                request.getSession().setAttribute("ACL_MSG",Msg);
                request.getSession().setAttribute("ACL_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            } else if (uploadStatus == -19){
                postScreen = "ACLImport.jsp";
                String errMsg = "Wrong column identity for user id at Column "+ (Constant.ACL_USER_ID_COLNO +1) + "! <BR>" +
                        "Required column name should be '" + Constant.ACL_USER_ID_COL_NAME + "'";
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            } else if (uploadStatus == -29){
                postScreen = "ACLImport.jsp";
                String errMsg = "Wrong column identity for effective date at Column "+ (Constant.ACL_EFFECTIVE_DATE_COLNO +1)+ "! <BR>" +
                        "Required column name should be '" + Constant.ACL_EFFECTIVE_DATE_COL_NAME + "'";
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            } else if (uploadStatus == -49){
                postScreen = "ACLImport.jsp";
                String errMsg = "Invalid parameter! <BR> DP Department ID = " + acl.getDPDeptId() + "; <BR>"+
                        "SOA Department ID = " + acl.getSOADeptId() + "; <BR>"+
                        "Expiry Date = " + acl.getExpiryDate();
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            } else if (uploadStatus == -39){
                postScreen = "ACLImport.jsp";
                String errMsg = "Cannot find the worksheet! <BR> Import file name : " + acl.getExcelFileName();
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            } else if (uploadStatus == -59){
                postScreen = "ACLImport.jsp";
                String errMsg = "Expiry Date cannot be earlier than the Effective Date! <BR> User ID : " + acl.getUserId() + ";<BR>" +
                        "Effective Date: " + acl.getEffectiveDate().toString() + ";<BR>" +
                        "Expiry Date: " + acl.getExpiryDate().toString();
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            } else if (uploadStatus == -89){
                postScreen = "ACLImport.jsp";
                String errMsg = "File cannot be imported! Please close the file if it was opened and retry again.";
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        } catch (RecordFormatException ex0){
            postScreen = "ACLImport.jsp";
            String errMsg = "File format Error! Please check whether the file has autorun macros and remove the macros";
            logger.error(errMsg,ex0);
            request.getSession().setAttribute("ACL_MSG",errMsg);
            request.getSession().setAttribute("ACL_MSGTYPE","ERR");
            RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
            dispatcher.forward(request, response);
        }catch (IOException ex){
            if (ex.getMessage().indexOf("Invalid header signature")>=0){
                postScreen = "ACLImport.jsp";
                String errMsg = "File format is incorrect! Imported file name: " + acl.getExcelFileName() + " <BR> Please check the excel file.";
                logger.error(errMsg,ex);
                request.getSession().setAttribute("ACL_MSG",errMsg);
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
                return;
            }else{
            	String errMsg = "File IO Error: AdminWorkAssignmentServlet:performWorkAssignmentUpload" + ex.toString();
            	logger.error(errMsg,ex);
                throw new ServletException(errMsg);
            }
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("Duplicate key")>=0){
                postScreen = "ACLImport.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("SQL Error")>=0){
                postScreen = "ACLImport.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "ACLImport.jsp";
                request.getSession().setAttribute("ACL_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("ACL_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
                throw new ServletException("Servlet Error: ACLImport ERROR: AdminACLServlet:performACLImport: \r\n"+
                        "Get Request Error\n" + ex.toString());
            }
        }finally{
            try{
                ACLDataBean aclDB = getACLDataBean();
                aclDB.deleteTempACL();
                aclDB.writeAuditTrail(acl,secCtx,uploadStatus);
            }catch (Exception ex){
                throw new ServletException("ACLImportERROR: "+
                        "AdminACLServlet:performACLImport: "+
                        "Get Request Error\n" + ex.toString());
            }
        }
    }*/
    
}