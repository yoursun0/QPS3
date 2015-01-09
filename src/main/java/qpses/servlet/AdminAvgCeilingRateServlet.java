/*
 * AdminAvgCeilingRateServlet.java
 * 
 * Created on 28th June, 2013
 */

package qpses.servlet;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.RecordFormatException;
import qpses.business.AvgCeilingRateDataBean;
import qpses.business.AvgCeilingRateInfo;
import qpses.security.SecurityContext;
import qpses.util.Constant;
import qpses.util.SysManager;

public class AdminAvgCeilingRateServlet extends HttpServlet {
    
    // log4j
    static Logger logger = Logger.getLogger(AdminAvgCeilingRateServlet.class.getName());
    
    
    private static String postScreen = "AvgCeilingRateList.jsp";
    private static String requestAction = null;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        requestAction = request.getServletPath();
        if (requestAction.equals("/qpsadmin/AvgCeilingRateUpload.servlet")) {
            performAvgCeilingRateUpload(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/AvgCeilingRateDelete.servlet")) {
            performAvgCeilingRateDelete(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/AvgCeilingRateRelease.servlet")) {
            performAvgCeilingRateRelease(request, response);
            return;
        }
        
        if (requestAction.equals("/qpsadmin/AvgCeilingRateSearch.servlet")) {
            performAvgCeilingRateSearch(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/AvgCeilingRateSearchReset.servlet")) {
            performAvgCeilingRateSearchReset(request, response);
            return;
        }
        
        if (requestAction.equals("/qpsadmin/AvgCeilingRateFile.servlet")) {
            performAvgCeilingRateFile(request, response);
            return;
        }
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    }
    
    private void performAvgCeilingRateUpload(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        
        // Prepare for file upload
        DiskFileUpload upload = new DiskFileUpload();
        upload.setRepositoryPath(Constant.QPSIS_TEMP_DIR);
        upload.setSizeThreshold(102400);// limit Memory Size
        upload.setSizeMax(-1); // unlimit file size
        List allUploadeditems = null;
        int uploadCounter =0;
        int rejectedCounter =0;
        int rowNo=0;
        int uploadStatus = 0;
        AvgCeilingRateInfo acr = new AvgCeilingRateInfo();
        
        try {   // parse the request to iterator
            allUploadeditems = upload.parseRequest(request);
        } catch (FileUploadException exFile) {
            String UploadError = "Parse Request Error: AdminAvgCeilingRateServlet:performAvgCeilingRateUpload\r\n" + exFile.getMessage();
            logger.error(UploadError,exFile);
            throw new IOException(UploadError);
        }
        
        // get file and parameters
        try {
            Iterator iter = allUploadeditems.iterator();
            FileItem pdffile = null;
            
            // read data from iterator and put into info object
            while (iter.hasNext()) {
                FileItem uploadeditem = (FileItem) iter.next();
                if ((! uploadeditem.isFormField() && uploadeditem.getSize() != 0)
                && uploadeditem.getContentType().startsWith("application/pdf")){ // get the pdf file
                    pdffile = uploadeditem;
                    long pdffilesize = pdffile.getSize();
                    acr.setPDFFileSize(pdffile.getSize());
                    File filePath = new File(pdffile.getName());
                    acr.setPDFFileName(filePath.getName());
                    acr.setFileContentType(pdffile.getContentType());
                } else if (uploadeditem.getFieldName().trim().endsWith("EffectiveDate")) {
                    acr.setEffectiveDate(SysManager.getSQLDate(uploadeditem.getString()));
                } else if (uploadeditem.getFieldName().trim().endsWith("Category")) {
                    acr.setServiceCategory(Integer.parseInt(uploadeditem.getString()));
                } else if (uploadeditem.getFieldName().trim().endsWith("PublishInd")) {
                    acr.setActiveInd(Integer.parseInt(uploadeditem.getString()));
                }
            }
            acr.setLastUpdatedBy(secCtx.getUserId());
            request.getSession().setAttribute("AVG_CEILING_RATE_DATA",acr);
            
            if (pdffile ==null) {     // check for any PDF file uploaded; otherwise, return error;
                uploadStatus = -89;
            }else{
                // call insert
                AvgCeilingRateDataBean acrDB = new AvgCeilingRateDataBean();
                uploadStatus = acrDB.insertAvgCeilingRate(acr, pdffile);
            }
            
            // check return status and redirect
            if (uploadStatus == 1){
                request.getSession().removeAttribute("AVG_CEILING_RATE_DATA");
                postScreen = "AvgCeilingRateList.jsp";
                String Msg = "File uploaded successfully! ";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",Msg);
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            } else if (uploadStatus == -89){
                postScreen = "AvgCeilingRateUpload.jsp";
                String errMsg = "File cannot be uploaded! Please check if the file is still exists and the file is in PDF format.";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }  else {
                postScreen = "AvgCeilingRateUpload.jsp";
                String errMsg = "System Error! upload status ="+ uploadStatus;
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        } catch (RecordFormatException ex0) {
            postScreen = "AvgCeilingRateUpload.jsp";
            String errMsg = "File cannot be imported! Please remove the macros in excel file.";
            logger.error(errMsg,ex0);
            request.getSession().setAttribute("AVG_CEILING_RATE_MSG",errMsg);
            request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
            RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
            dispatcher.forward(request, response);
            return;            
        }catch(Exception ex){
        	logger.error(ex);
            if (ex.getMessage().indexOf("Duplicate key")>=0){
                postScreen = "AvgCeilingRateUpload.jsp";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("SQL error")>=0){
                postScreen = "AvgCeilingRateUpload.jsp";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "AvgCeilingRateUpload.jsp";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
                throw new ServletException("Servlet Error: AvgCeilingRateUploadERROR: AdminAvgCeilingRateServlet:performAvgCeilingRateUpload: Get Request Error\n" + ex.toString());
            }
        }finally{
            try{
                AvgCeilingRateDataBean acrDB = new AvgCeilingRateDataBean();
                acrDB.writeAuditTrail(acr,secCtx,uploadStatus);
            }catch(Exception ex){
            	logger.error(ex);
                throw new ServletException("Servlet Error: AdminAvgCeilingRateServlet:performAvgCeilingRateUpload: Write Audit Trail Error\n" + ex.toString());
            }
        }
    }
    
    private void performAvgCeilingRateDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        
        int updateStatus = 0;
        AvgCeilingRateInfo acr = new AvgCeilingRateInfo();
        String postScreen = request.getParameter("PostScreen");        
        request.getSession().setAttribute("ACR_POST_SCREEN",postScreen);
        try {
            // save parameters
            String order_by = request.getParameter("OrderBy");
            String order_dir = request.getParameter("OrderDir");
            if (order_by != null || order_dir !=null){
                HashMap orderHash = new HashMap();
                orderHash.put("ORDER_BY",order_by);
                orderHash.put("ORDER_DIR",order_dir);
                if (postScreen.indexOf("Search") >=0){
                    request.getSession().setAttribute("ACR_SEARCH_ORDER",orderHash);
                }else{
                    request.getSession().setAttribute("ACR_LIST_ORDER",orderHash);
                }
            }
            
            if (request.getParameter("orgKey1") == null ||
                    request.getParameter("orgKey2") == null ){
                String errMsg = "Failed to delete records! Invalid parameters for deletion";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            
            // read data from posted form and put into info object
            acr.setServiceCategory(Integer.parseInt(request.getParameter("orgKey1")));
            acr.setEffectiveDate(SysManager.getSQLDate(request.getParameter("orgKey2")));
            acr.setOrgKey1(Integer.parseInt(request.getParameter("orgKey1")));
            acr.setOrgKey2(SysManager.getSQLDate(request.getParameter("orgKey2")));
            acr.setLastUpdatedBy(secCtx.getUserId());
            request.getSession().setAttribute("AVG_CEILING_RATE_DATA",acr);
            
            //call delete
            AvgCeilingRateDataBean acrDB = new AvgCeilingRateDataBean();
            updateStatus = acrDB.deleteAvgCeilingRate(acr,secCtx);
            
            // check return status and redirect
            if (updateStatus == 1){
                request.getSession().removeAttribute("AVG_CEILING_RATE_DATA");
                request.getSession().removeAttribute("ACR_POST_SCREEN");
                String Msg = "File deleted successfully! ";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",Msg);
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                postScreen = "AvgCeilingRateDelete.jsp";
                String errMsg = "System Error! update status ="+ updateStatus;
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        } catch (Exception ex) {
            if (ex.getMessage().indexOf("SQL error")>=0){
                postScreen = "AvgCeilingRateDelete.jsp";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                postScreen = "AvgCeilingRateDelete.jsp";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
            	logger.error(ex);
                throw new ServletException("Servlet Error: AvgCeilingRateDeleteERROR: AdminAvgCeilingRateServlet:performAvgCeilingRateDelete\n" + ex.toString());
            }
        }
        
    }
    
    private void performAvgCeilingRateRelease(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        
        int updateStatus = 0;
        AvgCeilingRateInfo acr = new AvgCeilingRateInfo();
        try {
            
            String order_by = request.getParameter("OrderBy");
            String order_dir = request.getParameter("OrderDir");
            if (order_by != null || order_dir !=null){
                HashMap orderHash = new HashMap();
                orderHash.put("ORDER_BY",order_by);
                orderHash.put("ORDER_DIR",order_dir);
                if (postScreen.indexOf("Search") >=0){
                    request.getSession().setAttribute("ACR_SEARCH_ORDER",orderHash);
                }else{
                    request.getSession().setAttribute("ACR_LIST_ORDER",orderHash);
                }
            }
            
            
            if (request.getParameter("selectedKey1") == null ||
                    request.getParameter("selectedKey2") == null ){
                String errMsg = "Failed to release records! Invalid parameters for release publishing!";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            
            // read data from posted form and put into info object
            acr.setServiceCategory(Integer.parseInt(request.getParameter("selectedKey1")));
            acr.setEffectiveDate(SysManager.getSQLDate(request.getParameter("selectedKey2")));
            acr.setLastUpdatedBy(secCtx.getUserId());
            
            // call update
            AvgCeilingRateDataBean acrDB = new AvgCeilingRateDataBean();
            updateStatus = acrDB.releaseAvgCeilingRate(acr,secCtx);
            
            // check return status and redirect
            if (updateStatus == 1){
                String Msg = "Average Ceiling Rate released for publishing successfully! ";
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",Msg);
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }  else {
                String errMsg = "System Error! update status ="+ updateStatus;
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        } catch (Exception ex) {
            if (ex.getMessage().indexOf("SQL error")>=0){
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else if (ex.getMessage().indexOf("No record")>=0){
                request.getSession().setAttribute("AVG_CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("AVG_CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }else{
            	logger.error(ex);
                throw new ServletException("Servlet Error: AvgCeilingRateDeleteERROR: AdminAvgCeilingRateServlet:performAvgCeilingRateDelete\n" + ex.toString());
            }
        }
        
    }
    
    private void performAvgCeilingRateSearch(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        AvgCeilingRateInfo acr = new AvgCeilingRateInfo();
        try {
            String effectiveDate = request.getParameter("EffectiveDate");
            String serviceCategory = request.getParameter("ServiceCategory");
            String activeInd=request.getParameter("PublishInd");
            
            if (activeInd!=null){
                acr.setActiveInd(Integer.parseInt(activeInd));
            }else{
                acr.setActiveInd(-9); // to override the value (0) stored in initialized AvgCeilingRateInfo
            }
            if (! effectiveDate.equals("")) acr.setEffectiveDate(SysManager.getSQLDate(effectiveDate));
            if (! serviceCategory.equals("0")) acr.setServiceCategory(Integer.parseInt(serviceCategory));
            
            request.getSession().setAttribute("AVG_CEILING_RATE_SEARCH_PARAMETER",acr);
            request.getSession().removeAttribute("ACR_SEARCH_ORDER");            
            postScreen = "AvgCeilingRateSearch.jsp";
            response.sendRedirect(postScreen);
            return;
        }catch(Exception ex){
        	logger.error(ex);
            throw new ServletException("Servlet Error: AvgCeilingRateSearch ERROR: AdminAvgCeilingRateServlet:performAvgCeilingRateSearch: Get Request Error\n" + ex.toString());
        }
    }
    
    private void performAvgCeilingRateSearchReset(HttpServletRequest request, HttpServletResponse response)
    throws ServletException {
        
        try{
            request.getSession().removeAttribute("AVG_CEILING_RATE_SEARCH_PARAMETER");
            request.getSession().removeAttribute("ACR_SEARCH_ORDER");
            postScreen = request.getHeader("referer");
            response.sendRedirect(postScreen);
            return;
        }catch(Exception ex){
        	logger.error(ex);
            throw new ServletException("Servlet Error: AvgCeilingRateSearchReset ERROR: AdminAvgCeilingRateServlet:performAvgCeilingRateSearchReset: Get Request Error\n" + ex.toString());
        }
    }
    
    private void performAvgCeilingRateFile(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // initialize variable
        java.sql.Blob pdf_file = null;
        String effective_date="";
        int service_category=0;
        String file_name ="";
        long file_size = 0;
        
        try{
            
            // get parameters
            service_category = Integer.parseInt(request.getParameter("orgKey1"));
            effective_date = request.getParameter("orgKey2");
            
            // get file
            AvgCeilingRateInfo acr = new AvgCeilingRateInfo();
            AvgCeilingRateDataBean acrDB =new AvgCeilingRateDataBean();
            acr = acrDB.selectAvgCeilingRateFile(service_category,effective_date);
            
            if (acr!=null){
                pdf_file = acr.getPDFFile();
                file_name = acr.getPDFFileName();
                file_size = acr.getPDFFileSize()/1024;
                
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=\"" + file_name + "\"");
                BufferedInputStream in   = new BufferedInputStream(pdf_file.getBinaryStream());
                byte[] fileBytes         = pdf_file.getBytes(1, (int)pdf_file.length());
                ServletOutputStream outs = response.getOutputStream();
                outs.write(fileBytes);
                outs.flush();
                outs.close();
            }
        } catch (Exception ex) {
        	logger.error(ex);
            throw new ServletException("Servlet Error: AdminAvgCeilingRateServlet:performAvgCeilingRateFile\n" + ex.toString());
        }
        
    }
}