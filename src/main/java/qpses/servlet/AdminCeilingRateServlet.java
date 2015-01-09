/*
 * AdminCeilingRateServlet.java
 *
 * Created on 4th July, 2013
 */

package qpses.servlet;

import java.io.*;
import java.util.Iterator;
import java.util.List;


import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import qpses.business.CeilingRate1Info;
import qpses.business.CeilingRate2Info;
import qpses.business.CeilingRate3Info;
import qpses.business.CeilingRate4Info;
import qpses.business.CeilingRateDataBean;
import qpses.business.CeilingRateInfo;
import qpses.security.SecurityContext;
import qpses.util.SysManager;
import qpses.util.Constant;
import qpses.util.SysException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.util.HashMap;
import qpses.business.CeilingRateCat1Info;
import qpses.business.CeilingRateCat2Info;
import qpses.business.CeilingRateCat3Info;
import qpses.business.CeilingRateCat4Info;
import qpses.business.PdfHelper;


@SuppressWarnings({ "deprecation", "serial" })
public class AdminCeilingRateServlet extends HttpServlet
{
    
    // log4j
    static Logger logger = Logger.getLogger(AdminCeilingRateServlet.class.getName());
    
    
    private static String postScreen = null;
    private static String requestAction = null;
    private static String adminId= null;
    
    public DiskFileUpload getDiskFileUpload() throws ServletException{
    	return new DiskFileUpload();
	}

    public CeilingRateDataBean getCeilingRateDataBean() throws SysException{
    	return new CeilingRateDataBean();
	}

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        requestAction = request.getServletPath();
        if (requestAction.equals("/qpsadmin/CeilingRateUpload.servlet"))
        {
            performCeilingRateUpload(request, response);
            return;
        }
        
        if (requestAction.equals("/qpsadmin/CeilingRateDelete.servlet"))
        {
            performCeilingRateDelete(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/CeilingRateRelease.servlet"))
        {
            performCeilingRateRelease(request, response);
            return;
        }
        
        if (requestAction.equals("/qpsadmin/CeilingRateSearch.servlet"))
        {
            performCeilingRateSearch(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/CeilingRateSearchReset.servlet"))
        {
            performCeilingRateSearchReset(request, response);
            return;
        }
        if (requestAction.equals("/qpsadmin/CeilingRatePrintReport.servlet"))
        {
            performCeilingRatePrintReport(request, response);
            return;
        }
        
        
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        doPost(request, response);
    }
    
    private void performCeilingRateUpload(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        adminId = secCtx.getUserId();
        
        // Prepare for file upload
        DiskFileUpload upload = getDiskFileUpload();
        upload.setRepositoryPath(Constant.QPSIS_TEMP_DIR);
        upload.setSizeThreshold(102400);// limit Memory Size
        upload.setSizeMax(-1); // unlimit file size
        List allUploadeditems = null;
        
        // initialize variables
        //int rowNo=0;
        int uploadCounter1=0;
        int uploadCounter2=0;
        int uploadCounter3=0;
        int uploadCounter4=0;
        int uploadStatus = 0;
        boolean sc1=false;
        boolean sc2=false;
        boolean sc3=false;
        boolean sc4=false;
        String serviceCategory=null;
        CeilingRateInfo cr = new CeilingRateInfo();
        request.getSession().removeAttribute("CEILING_RATE_DATA");
        request.getSession().removeAttribute("PRINT_CEILING_RATE_DATA");
        
        try
        {    // prase the request to iterator
            allUploadeditems = upload.parseRequest(request);
        }
        catch (FileUploadException exFile)
        {
            String UploadError = "Servlet Error:AdminCeilingRateServlet:performCeilingRateUpload\r\n" + exFile.getMessage();
            throw new IOException(UploadError);
        }
        
        try
        {
            Iterator iter = allUploadeditems.iterator();
            FileItem excelfile = null;
            
            // read info from iterator and put into info object
            while (iter.hasNext())
            {
                FileItem uploadeditem = (FileItem) iter.next();
                if ((! uploadeditem.isFormField() && uploadeditem.getSize() != 0))
                {// get the excel file
                    // && uploadeditem.getContentType().startsWith("application/vnd.ms-excel")){ // get the excel file
                    excelfile = uploadeditem;
                    long excelfilesize = excelfile.getSize();
                    cr.setExcelFileSize(excelfile.getSize());
                    File filePath = new File(excelfile.getName());
                    cr.setExcelFileName(filePath.getName());
                    cr.setFileContentType(excelfile.getContentType());
                }
                else if (uploadeditem.getFieldName().trim().endsWith("EffectiveDate"))
                {
                    cr.setEffectiveDate(SysManager.getSQLDate(uploadeditem.getString()));
                }
                else if (uploadeditem.getFieldName().trim().endsWith("ServiceCategory"))
                {
                    cr.setServiceCategory((cr.getServiceCategory()==null)?uploadeditem.getString():cr.getServiceCategory()+","+uploadeditem.getString());
                }
                else if (uploadeditem.getFieldName().trim().endsWith("PublishInd"))
                {
                    cr.setActiveInd(Integer.parseInt(uploadeditem.getString()));
                }
            }
            request.getSession().setAttribute("CEILING_RATE_DATA",cr);
            request.getSession().setAttribute("PRINT_CEILING_RATE_DATA",cr);
            if (excelfile ==null)
            {     // check for any excel file uploaded; otherwise, return error;
                uploadStatus = -89;
            }
            else
            {
                serviceCategory = cr.getServiceCategory();
                CeilingRateDataBean crDB = getCeilingRateDataBean();
                if (serviceCategory==null)
                {
                    uploadStatus = -59;
                }
                else
                {
                    if (serviceCategory.indexOf("SC1") >= 0) sc1=true;
                    if (serviceCategory.indexOf("SC2") >= 0) sc2=true;
                    if (serviceCategory.indexOf("SC3") >= 0) sc3=true;
                    if (serviceCategory.indexOf("SC4") >= 0) sc4=true;
                    
                    // create temporary file
                    try
                    {
                        crDB.createTempCeilingRate(serviceCategory);
                    }
                    catch(SysException dbEx)
                    {
                        String err = "Problem encountered in creating temporary Ceiling Rate";
                        throw new ServletException(err);
                    }
                    
                    if (sc1) uploadCounter1=performCeilingRate1Upload(excelfile,cr);
                    if (sc2) uploadCounter2=performCeilingRate2Upload(excelfile,cr);
                    if (sc3) uploadCounter3=performCeilingRate3Upload(excelfile,cr);
                    if (sc4) uploadCounter4=performCeilingRate4Upload(excelfile,cr);
                    
                    // replace temporary file
                    try
                    {
                        crDB.replaceTempCeilingRate(serviceCategory);
                    }
                    catch(SysException dbEx)
                    {
                        String err = "Problem encountered in replacing Ceiling Rate";
                        throw new ServletException(err);
                    }
                    uploadStatus = 1;
                    if (uploadCounter1 < 0) uploadStatus = uploadCounter1;
                    if (uploadCounter2 < 0) uploadStatus = uploadCounter2;
                    if (uploadCounter3 < 0) uploadStatus = uploadCounter3;
                    if (uploadCounter4 < 0) uploadStatus = uploadCounter4;
                }
            }
            // return page
            if (uploadStatus == 1)
            {
                request.getSession().removeAttribute("CEILING_RATE_DATA");
                postScreen = "CeilingRateAck.jsp";
                String Msg = "File imported successfully!<BR>" + uploadCounter1 + " records imported for Service Category 1; <BR> "+
                        uploadCounter2 +  " records imported for Service Category 2;<BR> " +
                        uploadCounter3 +  " records imported for Service Category 3;<BR>" +
                        uploadCounter4 +  " records imported for Service Category 4;<BR> " +
                        "Total "+ (uploadCounter1+uploadCounter2+uploadCounter3+uploadCounter4)+ " records imported.";
                request.getSession().setAttribute("IMPORT_CEILING_RATE_MSG",Msg);
                request.getSession().setAttribute("IMPORT_CEILING_RATE_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }
            else if (uploadStatus == -89)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "File cannot be imported! Please close the file if it was opened and retry again.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (uploadStatus == -391)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "Cannot find the work sheet ["+ Constant.CEILING_RATE_1_SHEETNAME+"]! <BR>"+
                        "Imported file name:"+ cr.getExcelFileName()+"!Please check the excel file.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (uploadStatus == -392)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "Cannot find the work sheet ["+ Constant.CEILING_RATE_2_SHEETNAME+"]! <BR>"+
                        "Imported file name:"+ cr.getExcelFileName()+"!Please check the excel file.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (uploadStatus == -393)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "Cannot find the work sheet ["+ Constant.CEILING_RATE_3_SHEETNAME+"]! <BR>"+
                        "Imported file name:"+ cr.getExcelFileName()+"!Please check the excel file.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (uploadStatus == -394)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "Cannot find the work sheet ["+ Constant.CEILING_RATE_4_SHEETNAME+"]! <BR>"+
                        "Imported file name:"+ cr.getExcelFileName()+"!Please check the excel file.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (uploadStatus == -491)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "The header does not contains the title ["+ Constant.CEILING_RATE_1_TITLE+"] at Column "+ Constant.CEILING_RATE_1_TITLE_COLNO+
                        " and Row " +Constant.CEILING_RATE_1_TITLE_ROWNO+ "! Please check the excel file.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (uploadStatus == -492)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "The header does not contains the title ["+ Constant.CEILING_RATE_2_TITLE+"] at Column "+ Constant.CEILING_RATE_2_TITLE_COLNO+
                        " and Row " +Constant.CEILING_RATE_2_TITLE_ROWNO+ "! Please check the excel file.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (uploadStatus == -493)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "The header does not contains the title ["+ Constant.CEILING_RATE_3_TITLE+"] at Column "+ Constant.CEILING_RATE_3_TITLE_COLNO+
                        " and Row " +Constant.CEILING_RATE_3_TITLE_ROWNO+ "! Please check the excel file.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (uploadStatus == -494)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "The header does not contains the title ["+ Constant.CEILING_RATE_4_TITLE+"] at Column "+ Constant.CEILING_RATE_4_TITLE_COLNO+
                        " and Row " +Constant.CEILING_RATE_4_TITLE_ROWNO+ "! Please check the excel file.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
                
            }
            else if (uploadStatus == -59)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "Please select at least one service category for upload.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "System Error! upload status ="+ uploadStatus;
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }
        catch (IOException ex)
        {
            if (ex.getMessage().indexOf("Invalid header signature")>=0)
            {
                postScreen = "CeilingRateUpload.jsp";
                String errMsg = "File format is incorrect! Imported file name: " + cr.getExcelFileName() + " <BR> Please check the excel file.";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
                return;
            }
            else
            {
                throw new ServletException("File IO Error: AdminCeilingRateServlet:performCeilingRateUpload" + ex.toString());
            }
        }
        catch(Exception ex)
        {
        	logger.error(ex);
            if (ex.getMessage().indexOf("Invalid data")>=0)
            {
                postScreen = "CeilingRateUpload.jsp";
                request.getSession().setAttribute("CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (ex.getMessage().indexOf("Duplicate key")>=0)
            {
                postScreen = "CeilingRateUpload.jsp";
                request.getSession().setAttribute("CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (ex.getMessage().indexOf("SQL error")>=0)
            {
                postScreen = "CeilingRateUpload.jsp";
                request.getSession().setAttribute("CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (ex.getMessage().indexOf("No record")>=0)
            {
                postScreen = "CeilingRateUpload.jsp";
                request.getSession().setAttribute("CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (ex.getMessage().indexOf("Problem in creating temporary Ceiling Rate file")>=0)
            {
                postScreen = "CeilingRateUpload.jsp";
                request.getSession().setAttribute("CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (ex.getMessage().indexOf("Problem in replacing temporary Ceiling Rate file")>=0)
            {
                postScreen = "CeilingRateUpload.jsp";
                request.getSession().setAttribute("CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else if (ex.getMessage().indexOf("Problem in deleting temporary Ceiling Rate file")>=0)
            {
                postScreen = "CeilingRateUpload.jsp";
                request.getSession().setAttribute("CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else
            {
                throw new ServletException("Servlet Error: CeilingRateUploadERROR: AdminCeilingRateServlet:performCeilingRateUpload: Get Request Error\n" + ex.toString());
            }
        }
        finally
        {
            try
            {
                CeilingRateDataBean crDB = getCeilingRateDataBean();
                crDB.deleteTempCeilingRate(serviceCategory);
                crDB.writeAuditTrail(cr,secCtx,uploadStatus);
            }
            catch(Exception ex)
            {
            	logger.error(ex);
                throw new ServletException("Servlet Error: CeilingRateUploadERROR: "+
                        "AdminCeilingRateServlet:performCeilingRateUpload: "+"" +
                        "Delete Temporary Ceiling Rate file error\n" + ex.toString());
            }
        }
    }
    
    private int performCeilingRate1Upload(FileItem excelfile,CeilingRateInfo cr)
    throws SysException,ServletException
    {
        int uploadCounter=0;
        int uploadStatus=0;
        HSSFSheet sheet = null;
        try
        {
            InputStream excelFileStream = excelfile.getInputStream();
            HSSFWorkbook wb    = new HSSFWorkbook(excelFileStream);
            
            // check worksheet name = "Cat1"
            for (int i =0;i<wb.getNumberOfSheets();i++)
            {
                if (wb.getSheetName(i).indexOf(Constant.CEILING_RATE_1_SHEETNAME)>=0)
                {
                    sheet  = wb.getSheetAt(i);
                }
            }
            if (sheet == null)
            {
                uploadStatus = -391;  // wrong sheet name
                return uploadStatus;
            }
            
            // check worksheet title
            HSSFCell aCell = null;
            aCell =sheet.getRow(Constant.CEILING_RATE_1_TITLE_ROWNO).getCell((short) Constant.CEILING_RATE_1_TITLE_COLNO);
            if (aCell.getStringCellValue().trim().indexOf(Constant.CEILING_RATE_1_TITLE) < 0 )
            {
                uploadStatus =-491; // wrong sheet title
                return uploadStatus;
            }
            
            
            // begin import data
            //for (int i=0; i <2; i++)
            //{
                String group;
                int contractorNo;
                //if (i ==0)
                //{
                    group = "N";
                    contractorNo = Constant.CEILING_RATE_1_MINOR;
                //}
                //else
                //{
                //    group = "J";
                //    contractorNo = Constant.CEILING_RATE_1_MAJOR;
                //}
                for (int j =0; j< contractorNo;j++)
                {
                    CeilingRate1Info cr1=new CeilingRate1Info();
                    cr1=setSC1Rate(group,j,sheet);
                    cr1.setEffectiveDate(cr.getEffectiveDate());
                    cr1.setActiveInd(cr.getActiveInd());
                    cr1.setLastUpdatedBy(adminId);
                    CeilingRateDataBean crDB = getCeilingRateDataBean();
                    uploadStatus = crDB.insertCeilingRate1(cr1);
                    uploadCounter+=uploadStatus;
                }
            //}
            
            return uploadCounter;
        }
        catch (IOException ex){
        	String err = "IO Exception: AdminCeilingRateServlet:performCeilingRate1Upload:"+ ex.getMessage();
        	logger.error(err,ex);
        	throw new SysException(err); 
        }
    }
    
    private int performCeilingRate2Upload(FileItem excelfile,CeilingRateInfo cr)
    throws SysException,ServletException
    {
        int uploadCounter=0;
        int uploadStatus=0;
        HSSFSheet sheet = null;
        try
        {
            InputStream excelFileStream = excelfile.getInputStream();
            HSSFWorkbook wb    = new HSSFWorkbook(excelFileStream);
            
            // check worksheet name = "Cat2"
            for (int i =0;i<wb.getNumberOfSheets();i++)
            {
                if (wb.getSheetName(i).indexOf(Constant.CEILING_RATE_2_SHEETNAME)>=0)
                {
                    sheet  = wb.getSheetAt(i);
                }
            }
            if (sheet == null)
            {
                uploadStatus = -392;  // wrong sheet name
                return uploadStatus;
            }
            
            // check worksheet title
            HSSFCell aCell = null;
            aCell =sheet.getRow(Constant.CEILING_RATE_2_TITLE_ROWNO).getCell((short) Constant.CEILING_RATE_2_TITLE_COLNO);
            if (aCell.getStringCellValue().trim().indexOf(Constant.CEILING_RATE_2_TITLE) < 0 )
            {
                uploadStatus =-492; // wrong file title
                return uploadStatus;
            }
            
            // begin import data
            for (int i=0; i <2; i++)
            {
                String group;
                int contractorNo;
                if (i ==0)
                {
                    group = "N";
                    contractorNo = Constant.CEILING_RATE_2_MINOR;
                }
                else
                {
                    group = "J";
                    contractorNo = Constant.CEILING_RATE_2_MAJOR;
                }
                for (int j =0; j< contractorNo;j++)
                {
                    CeilingRate2Info cr2=new CeilingRate2Info();
                    cr2=setSC2Rate(group,j,sheet);
                    cr2.setEffectiveDate(cr.getEffectiveDate());
                    cr2.setActiveInd(cr.getActiveInd());
                    cr2.setLastUpdatedBy(adminId);
                    CeilingRateDataBean crDB = getCeilingRateDataBean();
                    uploadStatus = crDB.insertCeilingRate2(cr2);
                    uploadCounter+=uploadStatus;
                }
            }
            
            return uploadCounter;
        }
        catch (IOException ex){
        	String err = "IO Exception: AdminCeilingRateServlet:performCeilingRate2Upload:"+ ex.getMessage();
        	logger.error(err,ex);
        	throw new SysException(err); 
        }
    }
    
    private int performCeilingRate3Upload(FileItem excelfile,CeilingRateInfo cr)
    throws SysException,ServletException
    {
        int uploadCounter=0;
        int uploadStatus=0;
        HSSFSheet sheet = null;
        try
        {
            InputStream excelFileStream = excelfile.getInputStream();
            HSSFWorkbook wb    = new HSSFWorkbook(excelFileStream);
            
            // check worksheet name = "Cat3"
            for (int i =0;i<wb.getNumberOfSheets();i++)
            {
                if (wb.getSheetName(i).indexOf(Constant.CEILING_RATE_3_SHEETNAME)>=0)
                {
                    sheet  = wb.getSheetAt(i);
                }
            }
            if (sheet == null)
            {
                uploadStatus = -393;  // wrong sheet name
                return uploadStatus;
            }
            
            // check worksheet title
            HSSFCell aCell = null;
            aCell =sheet.getRow(Constant.CEILING_RATE_3_TITLE_ROWNO).getCell((short) Constant.CEILING_RATE_3_TITLE_COLNO);
            if (aCell.getStringCellValue().trim().indexOf(Constant.CEILING_RATE_3_TITLE) < 0 )
            {
                uploadStatus =-493; // wrong file title
                return uploadStatus;
            }
            
            // begin import data
            for (int i=0; i <2; i++)
            {
                String group;
                int contractorNo;
                if (i ==0)
                {
                    group = "N";
                    contractorNo = Constant.CEILING_RATE_3_MINOR;
                }
                else
                {
                    group = "J";
                    contractorNo = Constant.CEILING_RATE_3_MAJOR;
                }
                for (int j =0; j< contractorNo;j++)
                {
                    CeilingRate3Info cr3=new CeilingRate3Info();
                    cr3=setSC3Rate(group,j,sheet);
                    cr3.setEffectiveDate(cr.getEffectiveDate());
                    cr3.setActiveInd(cr.getActiveInd());
                    cr3.setLastUpdatedBy(adminId);
                    CeilingRateDataBean crDB = getCeilingRateDataBean();
                    uploadStatus = crDB.insertCeilingRate3(cr3);
                    uploadCounter+=uploadStatus;
                }
            }
            return uploadCounter;
        }
        catch (IOException ex){
        	String err = "IO Exception: AdminCeilingRateServlet:performCeilingRate3Upload:"+ ex.getMessage();
        	logger.error(err,ex);
        	throw new SysException(err); 
        }
    }
    
    private int performCeilingRate4Upload(FileItem excelfile,CeilingRateInfo cr)
    throws SysException,ServletException
    {
        int uploadCounter=0;
        int uploadStatus=0;
        HSSFSheet sheet = null;
        try
        {
            InputStream excelFileStream = excelfile.getInputStream();
            HSSFWorkbook wb    = new HSSFWorkbook(excelFileStream);
            
            // check worksheet name = "Cat4"
            for (int i =0;i<wb.getNumberOfSheets();i++)
            {
                if (wb.getSheetName(i).indexOf(Constant.CEILING_RATE_4_SHEETNAME)>=0)
                {
                    sheet  = wb.getSheetAt(i);
                }
            }
            if (sheet == null)
            {
                uploadStatus = -394;  // wrong sheet name
                return uploadStatus;
            }
            
            // check worksheet title
            HSSFCell aCell = null;
            aCell =sheet.getRow(Constant.CEILING_RATE_4_TITLE_ROWNO).getCell((short) Constant.CEILING_RATE_4_TITLE_COLNO);
            
            if (aCell.getStringCellValue().trim().indexOf(Constant.CEILING_RATE_4_TITLE) < 0 )
            {
                uploadStatus =-494; // wrong file title
                return uploadStatus;
            }
            
            // begin import data
            //for (int i=0; i <2; i++)
            //{
                String group;
                int contractorNo;
              //  if (i ==0)
              //  {
                    group = "N";
                    contractorNo = Constant.CEILING_RATE_4_MINOR;
              //  }
              //  else
              //  {
              //      group = "J";
              //      contractorNo = Constant.CEILING_RATE_4_MAJOR;
              //  }
                for (int j =0; j< contractorNo;j++)
                {
                    CeilingRate4Info cr4=new CeilingRate4Info();
                    cr4=setSC4Rate(group,j,sheet);
                    cr4.setEffectiveDate(cr.getEffectiveDate());
                    cr4.setActiveInd(cr.getActiveInd());
                    cr4.setLastUpdatedBy(adminId);
                    CeilingRateDataBean crDB = getCeilingRateDataBean();
                    uploadStatus = crDB.insertCeilingRate4(cr4);
                    uploadCounter+=uploadStatus;
                }
            //}
            return uploadCounter;
        }
        catch (IOException ex){
        	String err = "IO Exception: AdminCeilingRateServlet:performCeilingRate4Upload:"+ ex.getMessage();
        	logger.error(err,ex);
        	throw new SysException(err); 
        }
    }
    
    private CeilingRate1Info setSC1Rate(String group, int i, HSSFSheet sheet) throws ServletException
    {
        
        HSSFRow dataRow=null;
        CeilingRate1Info cr1=new CeilingRate1Info();
        int rowNo=0;
        int CEILING_RATE_1_RESIDENT_ROWNO=0;
        int CEILING_RATE_1_NON_RESIDENT_ROWNO=0;
        int CEILING_RATE_1_OFFSHORE_ROWNO=0;
        int CEILING_RATE_1_COL_OFFSET= Constant.CEILING_RATE_1_COL_OFFSET;
        if (group.equals("N"))
        {
            CEILING_RATE_1_RESIDENT_ROWNO=Constant.CEILING_RATE_1_MINOR_RESIDENT_ROWNO;
            CEILING_RATE_1_NON_RESIDENT_ROWNO=Constant.CEILING_RATE_1_MINOR_NON_RESIDENT_ROWNO;
            CEILING_RATE_1_OFFSHORE_ROWNO=Constant.CEILING_RATE_1_MINOR_OFFSHORE_ROWNO;
        }
        else if (group.equals("J"))
        {
            CEILING_RATE_1_RESIDENT_ROWNO=Constant.CEILING_RATE_1_MAJOR_RESIDENT_ROWNO;
            CEILING_RATE_1_NON_RESIDENT_ROWNO=Constant.CEILING_RATE_1_MAJOR_NON_RESIDENT_ROWNO;
            CEILING_RATE_1_OFFSHORE_ROWNO=Constant.CEILING_RATE_1_MAJOR_OFFSHORE_ROWNO;
        }
        
        rowNo = CEILING_RATE_1_RESIDENT_ROWNO+i;
        dataRow =sheet.getRow(rowNo);
        String contractorId = dataRow.getCell((short) CEILING_RATE_1_COL_OFFSET).getStringCellValue().trim();
        String currency = dataRow.getCell((short) (CEILING_RATE_1_COL_OFFSET+1)).getStringCellValue().trim();
        cr1.setContractorId(contractorId);
        cr1.setCurrency(currency);
        
        // get Resident rate
        cr1.setsc3Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+2));
        cr1.setsc4Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+3));
        cr1.setsc5Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+4));
        cr1.setsc6Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+5));
        cr1.setsc7Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+6));
        cr1.setsc8Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+7));
        cr1.setsc9Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+8));
        cr1.setsc10Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+9));
        cr1.setsc11Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+10));
        cr1.setsc12Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+11));
        
        // get Non Resident rate
        rowNo = CEILING_RATE_1_NON_RESIDENT_ROWNO+i;
        cr1.setsc3NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+2));
        cr1.setsc4NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+3));
        cr1.setsc5NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+4));
        cr1.setsc6NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+5));
        cr1.setsc7NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+6));
        cr1.setsc8NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+7));
        cr1.setsc9NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+8));
        cr1.setsc10NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+9));
        cr1.setsc11NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+10));
        cr1.setsc12NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+11));
        
        
        // get Offshore rate
        rowNo = CEILING_RATE_1_OFFSHORE_ROWNO+i;
        cr1.setsc3Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+2));
        cr1.setsc4Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+3));
        cr1.setsc5Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+4));
        cr1.setsc6Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+5));
        cr1.setsc7Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+6));
        cr1.setsc8Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+7));
        cr1.setsc9Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+8));
        cr1.setsc10Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+9));
        cr1.setsc11Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+10));
        cr1.setsc12Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_1_COL_OFFSET+11));
        
        for (int j=0;j<Constant.CEILING_RATE_1_SUPP_NO;j++)
        {
            dataRow =sheet.getRow(Constant.CEILING_RATE_1_SUPP_RESIDENT_ROWNO+j);
            rowNo = Constant.CEILING_RATE_1_SUPP_RESIDENT_ROWNO+i;
            HSSFCell cellContractorId = dataRow.getCell((short) CEILING_RATE_1_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr1.setSuppInd(-1);
                cr1.setSupp1Resident((float) dataRow.getCell((short) (CEILING_RATE_1_COL_OFFSET+3)).getNumericCellValue());
            }
            else
            {
                cr1.setSuppInd(0);
                cr1.setSupp1Resident(0);
            }
            dataRow =sheet.getRow(Constant.CEILING_RATE_1_SUPP_NON_RESIDENT_ROWNO+j);
            rowNo = Constant.CEILING_RATE_1_SUPP_NON_RESIDENT_ROWNO+i;
            cellContractorId = dataRow.getCell((short) CEILING_RATE_1_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr1.setSuppInd(-1);
                cr1.setSupp1NonResident((float) dataRow.getCell((short) (CEILING_RATE_1_COL_OFFSET+3)).getNumericCellValue());
            }
            else
            {
                cr1.setSupp1NonResident(0);
            }
            dataRow =sheet.getRow(Constant.CEILING_RATE_1_SUPP_OFFSHORE_ROWNO+j);
            rowNo = Constant.CEILING_RATE_1_SUPP_OFFSHORE_ROWNO+i;
            cellContractorId = dataRow.getCell((short) CEILING_RATE_1_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr1.setSuppInd(-1);
                cr1.setSupp1Offshore((float) dataRow.getCell((short) (CEILING_RATE_1_COL_OFFSET+3)).getNumericCellValue());
            }
            else
            {
                cr1.setSupp1Offshore(0);
            }
        }
        return cr1;
    }
    
    private CeilingRate2Info setSC2Rate(String group, int i, HSSFSheet sheet) throws ServletException
    {
        
        HSSFRow dataRow=null;
        CeilingRate2Info cr2=new CeilingRate2Info();
        int rowNo=0;
        int CEILING_RATE_2_RESIDENT_ROW1NO=0;
        int CEILING_RATE_2_RESIDENT_ROW2NO=0;
        int CEILING_RATE_2_NON_RESIDENT_ROW1NO=0;
        int CEILING_RATE_2_NON_RESIDENT_ROW2NO=0;
        int CEILING_RATE_2_OFFSHORE_ROW1NO=0;
        int CEILING_RATE_2_OFFSHORE_ROW2NO=0;
        int CEILING_RATE_2_COL_OFFSET= Constant.CEILING_RATE_2_COL_OFFSET;
        if (group.equals("N"))
        {
            CEILING_RATE_2_RESIDENT_ROW1NO=Constant.CEILING_RATE_2_MINOR_RESIDENT_ROW1NO;
            CEILING_RATE_2_RESIDENT_ROW2NO=Constant.CEILING_RATE_2_MINOR_RESIDENT_ROW2NO;
            CEILING_RATE_2_NON_RESIDENT_ROW1NO=Constant.CEILING_RATE_2_MINOR_NON_RESIDENT_ROW1NO;
            CEILING_RATE_2_NON_RESIDENT_ROW2NO=Constant.CEILING_RATE_2_MINOR_NON_RESIDENT_ROW2NO;
            CEILING_RATE_2_OFFSHORE_ROW1NO=Constant.CEILING_RATE_2_MINOR_OFFSHORE_ROW1NO;
            CEILING_RATE_2_OFFSHORE_ROW2NO=Constant.CEILING_RATE_2_MINOR_OFFSHORE_ROW2NO;
        }
        else if (group.equals("J"))
        {
            CEILING_RATE_2_RESIDENT_ROW1NO=Constant.CEILING_RATE_2_MAJOR_RESIDENT_ROW1NO;
            CEILING_RATE_2_RESIDENT_ROW2NO=Constant.CEILING_RATE_2_MAJOR_RESIDENT_ROW2NO;
            CEILING_RATE_2_NON_RESIDENT_ROW1NO=Constant.CEILING_RATE_2_MAJOR_NON_RESIDENT_ROW1NO;
            CEILING_RATE_2_NON_RESIDENT_ROW2NO=Constant.CEILING_RATE_2_MAJOR_NON_RESIDENT_ROW2NO;
            CEILING_RATE_2_OFFSHORE_ROW1NO=Constant.CEILING_RATE_2_MAJOR_OFFSHORE_ROW1NO;
            CEILING_RATE_2_OFFSHORE_ROW2NO=Constant.CEILING_RATE_2_MAJOR_OFFSHORE_ROW2NO;
        }
        
        
        rowNo = CEILING_RATE_2_RESIDENT_ROW1NO+i;
        dataRow =sheet.getRow(rowNo);
        String contractorId = dataRow.getCell((short) CEILING_RATE_2_COL_OFFSET).getStringCellValue().trim();
        String currency = dataRow.getCell((short) (CEILING_RATE_2_COL_OFFSET+1)).getStringCellValue().trim();
        cr2.setContractorId(contractorId);
        cr2.setCurrency(currency);
        cr2.setsc1ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+2));
        cr2.setsc1ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+3));
        cr2.setsc1ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+4));
        cr2.setsc2ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+5));
        cr2.setsc2ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+6));
        cr2.setsc2ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+7));
        cr2.setsc3ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+8));
        cr2.setsc3ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+9));
        cr2.setsc3ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+10));
        cr2.setsc4ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+11));
        cr2.setsc4ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+12));
        cr2.setsc4ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+13));
        cr2.setsc5ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+14));
        cr2.setsc5ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+15));
        cr2.setsc5ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+16));
        
        rowNo = CEILING_RATE_2_RESIDENT_ROW2NO+i;
        cr2.setsc6ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+2));
        cr2.setsc6ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+3));
        cr2.setsc6ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+4));
        cr2.setsc7ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+5));
        cr2.setsc7ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+6));
        cr2.setsc7ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+7));
        cr2.setsc8ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+8));
        cr2.setsc8ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+9));
        cr2.setsc8ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+10));
        cr2.setsc9ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+11));
        cr2.setsc9ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+12));
        cr2.setsc9ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+13));
        cr2.setsc10ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+14));
        cr2.setsc10ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+15));
        cr2.setsc10ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+16));
        
        
        // get Non Resident rate
        rowNo = CEILING_RATE_2_NON_RESIDENT_ROW1NO+i;
        cr2.setsc1NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+2));
        cr2.setsc1NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+3));
        cr2.setsc1NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+4));
        cr2.setsc2NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+5));
        cr2.setsc2NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+6));
        cr2.setsc2NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+7));
        cr2.setsc3NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+8));
        cr2.setsc3NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+9));
        cr2.setsc3NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+10));
        cr2.setsc4NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+11));
        cr2.setsc4NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+12));
        cr2.setsc4NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+13));
        cr2.setsc5NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+14));
        cr2.setsc5NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+15));
        cr2.setsc5NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+16));
        
        
        rowNo = CEILING_RATE_2_NON_RESIDENT_ROW2NO+i;
        cr2.setsc6NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+2));
        cr2.setsc6NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+3));
        cr2.setsc6NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+4));
        cr2.setsc7NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+5));
        cr2.setsc7NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+6));
        cr2.setsc7NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+7));
        cr2.setsc8NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+8));
        cr2.setsc8NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+9));
        cr2.setsc8NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+10));
        cr2.setsc9NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+11));
        cr2.setsc9NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+12));
        cr2.setsc9NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+13));
        cr2.setsc10NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+14));
        cr2.setsc10NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+15));
        cr2.setsc10NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+16));
        
        // get Offshore rate
        
        rowNo = CEILING_RATE_2_OFFSHORE_ROW1NO+i;
        cr2.setsc1OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+2));
        cr2.setsc1OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+3));
        cr2.setsc1OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+4));
        cr2.setsc2OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+5));
        cr2.setsc2OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+6));
        cr2.setsc2OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+7));
        cr2.setsc3OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+8));
        cr2.setsc3OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+9));
        cr2.setsc3OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+10));
        cr2.setsc4OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+11));
        cr2.setsc4OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+12));
        cr2.setsc4OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+13));
        cr2.setsc5OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+14));
        cr2.setsc5OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+15));
        cr2.setsc5OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+16));
        
        rowNo = CEILING_RATE_2_OFFSHORE_ROW2NO+i;
        cr2.setsc6OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+2));
        cr2.setsc6OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+3));
        cr2.setsc6OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+4));
        cr2.setsc7OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+5));
        cr2.setsc7OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+6));
        cr2.setsc7OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+7));
        cr2.setsc8OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+8));
        cr2.setsc8OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+9));
        cr2.setsc8OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+10));
        cr2.setsc9OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+11));
        cr2.setsc9OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+12));
        cr2.setsc9OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+13));
        cr2.setsc10OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+14));
        cr2.setsc10OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+15));
        cr2.setsc10OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_2_COL_OFFSET+16));
        
        for (int j=0;j<Constant.CEILING_RATE_2_SUPP_NO;j++)
        {
            dataRow =sheet.getRow(Constant.CEILING_RATE_2_SUPP_RESIDENT_ROWNO+j);
            rowNo = Constant.CEILING_RATE_2_SUPP_RESIDENT_ROWNO+i;
            HSSFCell cellContractorId = dataRow.getCell((short) CEILING_RATE_2_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr2.setSuppInd(-1);
                cr2.setSupp1ResidentM1((float) dataRow.getCell((short) (CEILING_RATE_2_COL_OFFSET+3)).getNumericCellValue());
                cr2.setSupp1ResidentM2((float) dataRow.getCell((short) (CEILING_RATE_2_COL_OFFSET+4)).getNumericCellValue());
                cr2.setSupp1ResidentM3((float) dataRow.getCell((short) (CEILING_RATE_2_COL_OFFSET+5)).getNumericCellValue());
            }
            else
            {
                cr2.setSuppInd(0);
                cr2.setSupp1ResidentM1(0);
                cr2.setSupp1ResidentM2(0);
                cr2.setSupp1ResidentM3(0);
            }
            dataRow =sheet.getRow(Constant.CEILING_RATE_2_SUPP_NON_RESIDENT_ROWNO+j);
            rowNo = Constant.CEILING_RATE_2_SUPP_NON_RESIDENT_ROWNO+i;
            cellContractorId = dataRow.getCell((short) CEILING_RATE_2_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr2.setSuppInd(-1);
                cr2.setSupp1NonResidentM1((float) dataRow.getCell((short) (CEILING_RATE_2_COL_OFFSET+3)).getNumericCellValue());
                cr2.setSupp1NonResidentM2((float) dataRow.getCell((short) (CEILING_RATE_2_COL_OFFSET+4)).getNumericCellValue());
                cr2.setSupp1NonResidentM3((float) dataRow.getCell((short) (CEILING_RATE_2_COL_OFFSET+5)).getNumericCellValue());
            }
            else
            {
                cr2.setSupp1NonResidentM1(0);
                cr2.setSupp1NonResidentM2(0);
                cr2.setSupp1NonResidentM3(0);
            }
            dataRow =sheet.getRow(Constant.CEILING_RATE_2_SUPP_OFFSHORE_ROWNO+j);
            rowNo = Constant.CEILING_RATE_2_SUPP_OFFSHORE_ROWNO+i;
            cellContractorId = dataRow.getCell((short) CEILING_RATE_2_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr2.setSuppInd(-1);
                cr2.setSupp1OffshoreM1((float) dataRow.getCell((short) (CEILING_RATE_2_COL_OFFSET+3)).getNumericCellValue());
                cr2.setSupp1OffshoreM2((float) dataRow.getCell((short) (CEILING_RATE_2_COL_OFFSET+4)).getNumericCellValue());
                cr2.setSupp1OffshoreM3((float) dataRow.getCell((short) (CEILING_RATE_2_COL_OFFSET+5)).getNumericCellValue());
            }
            else
            {
                cr2.setSupp1OffshoreM1(0);
                cr2.setSupp1OffshoreM2(0);
                cr2.setSupp1OffshoreM3(0);
            }
        }
        return cr2;
    }
    
    private CeilingRate3Info setSC3Rate(String group, int i, HSSFSheet sheet) throws ServletException
    {
        
        HSSFRow dataRow = null;
        CeilingRate3Info cr3=new CeilingRate3Info();
        int rowNo=0;
        int CEILING_RATE_3_ONEOFF_RESIDENT_ROWNO=0;
        int CEILING_RATE_3_ONEOFF_NON_RESIDENT_ROWNO=0;
        int CEILING_RATE_3_ONEOFF_OFFSHORE_ROWNO=0;
        int CEILING_RATE_3_ONGOING_RESIDENT_ROW1NO=0;
        int CEILING_RATE_3_ONGOING_RESIDENT_ROW2NO=0;
        int CEILING_RATE_3_ONGOING_NON_RESIDENT_ROW1NO=0;
        int CEILING_RATE_3_ONGOING_NON_RESIDENT_ROW2NO=0;
        int CEILING_RATE_3_ONGOING_OFFSHORE_ROW1NO=0;
        int CEILING_RATE_3_ONGOING_OFFSHORE_ROW2NO=0;
        int CEILING_RATE_3_COL_OFFSET= Constant.CEILING_RATE_3_COL_OFFSET;
        if (group.equals("N"))
        {
            CEILING_RATE_3_ONEOFF_RESIDENT_ROWNO=Constant.CEILING_RATE_3_MINOR_ONEOFF_RESIDENT_ROWNO;
            CEILING_RATE_3_ONEOFF_NON_RESIDENT_ROWNO=Constant.CEILING_RATE_3_MINOR_ONEOFF_NON_RESIDENT_ROWNO;
            CEILING_RATE_3_ONEOFF_OFFSHORE_ROWNO=Constant.CEILING_RATE_3_MINOR_ONEOFF_OFFSHORE_ROWNO;
            CEILING_RATE_3_ONGOING_RESIDENT_ROW1NO=Constant.CEILING_RATE_3_MINOR_ONGOING_RESIDENT_ROW1NO;
            CEILING_RATE_3_ONGOING_RESIDENT_ROW2NO=Constant.CEILING_RATE_3_MINOR_ONGOING_RESIDENT_ROW2NO;
            CEILING_RATE_3_ONGOING_NON_RESIDENT_ROW1NO=Constant.CEILING_RATE_3_MINOR_ONGOING_NON_RESIDENT_ROW1NO;
            CEILING_RATE_3_ONGOING_NON_RESIDENT_ROW2NO=Constant.CEILING_RATE_3_MINOR_ONGOING_NON_RESIDENT_ROW2NO;
            CEILING_RATE_3_ONGOING_OFFSHORE_ROW1NO=Constant.CEILING_RATE_3_MINOR_ONGOING_OFFSHORE_ROW1NO;
            CEILING_RATE_3_ONGOING_OFFSHORE_ROW2NO=Constant.CEILING_RATE_3_MINOR_ONGOING_OFFSHORE_ROW2NO;
        }
        else if (group.equals("J"))
        {
            CEILING_RATE_3_ONEOFF_RESIDENT_ROWNO=Constant.CEILING_RATE_3_MAJOR_ONEOFF_RESIDENT_ROWNO;
            CEILING_RATE_3_ONEOFF_NON_RESIDENT_ROWNO=Constant.CEILING_RATE_3_MAJOR_ONEOFF_NON_RESIDENT_ROWNO;
            CEILING_RATE_3_ONEOFF_OFFSHORE_ROWNO=Constant.CEILING_RATE_3_MAJOR_ONEOFF_OFFSHORE_ROWNO;
            CEILING_RATE_3_ONGOING_RESIDENT_ROW1NO=Constant.CEILING_RATE_3_MAJOR_ONGOING_RESIDENT_ROW1NO;
            CEILING_RATE_3_ONGOING_RESIDENT_ROW2NO=Constant.CEILING_RATE_3_MAJOR_ONGOING_RESIDENT_ROW2NO;
            CEILING_RATE_3_ONGOING_NON_RESIDENT_ROW1NO=Constant.CEILING_RATE_3_MAJOR_ONGOING_NON_RESIDENT_ROW1NO;
            CEILING_RATE_3_ONGOING_NON_RESIDENT_ROW2NO=Constant.CEILING_RATE_3_MAJOR_ONGOING_NON_RESIDENT_ROW2NO;
            CEILING_RATE_3_ONGOING_OFFSHORE_ROW1NO=Constant.CEILING_RATE_3_MAJOR_ONGOING_OFFSHORE_ROW1NO;
            CEILING_RATE_3_ONGOING_OFFSHORE_ROW2NO=Constant.CEILING_RATE_3_MAJOR_ONGOING_OFFSHORE_ROW2NO;
        }
        
        rowNo = CEILING_RATE_3_ONEOFF_RESIDENT_ROWNO+i;
        dataRow = sheet.getRow(rowNo);
        String contractorId = dataRow.getCell((short) CEILING_RATE_3_COL_OFFSET).getStringCellValue().trim();
        String currency = dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+1)).getStringCellValue().trim();
        cr3.setContractorId(contractorId);
        cr3.setCurrency(currency);
        
        // get Resident rate for one off service
        cr3.setOneoffSc1Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+2));
        cr3.setOneoffSc2Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+3));
        cr3.setOneoffSc3Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+4));
        cr3.setOneoffSc4Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+5));
        cr3.setOneoffSc5Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+6));
        cr3.setOneoffSc6Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+7));
        cr3.setOneoffSc7Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+8));
        cr3.setOneoffSc8Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+9));
        cr3.setOneoffSc9Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+10));
        cr3.setOneoffSc10Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+11));
        cr3.setOneoffSc11Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+12));
        cr3.setOneoffSc12Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+13));
        
        
        // get Non Resident rate for one off service
        rowNo = CEILING_RATE_3_ONEOFF_NON_RESIDENT_ROWNO+i;
        cr3.setOneoffSc1NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+2));
        cr3.setOneoffSc2NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+3));
        cr3.setOneoffSc3NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+4));
        cr3.setOneoffSc4NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+5));
        cr3.setOneoffSc5NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+6));
        cr3.setOneoffSc6NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+7));
        cr3.setOneoffSc7NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+8));
        cr3.setOneoffSc8NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+9));
        cr3.setOneoffSc9NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+10));
        cr3.setOneoffSc10NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+11));
        cr3.setOneoffSc11NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+12));
        cr3.setOneoffSc12NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+13));
        
        
        // get Offshore rate for one off service
        rowNo = CEILING_RATE_3_ONEOFF_OFFSHORE_ROWNO+i;
        cr3.setOneoffSc1Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+2));
        cr3.setOneoffSc2Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+3));
        cr3.setOneoffSc3Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+4));
        cr3.setOneoffSc4Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+5));
        cr3.setOneoffSc5Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+6));
        cr3.setOneoffSc6Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+7));
        cr3.setOneoffSc7Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+8));
        cr3.setOneoffSc8Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+9));
        cr3.setOneoffSc9Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+10));
        cr3.setOneoffSc10Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+11));
        cr3.setOneoffSc11Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+12));
        cr3.setOneoffSc12Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+13));
        
        // get supplmentary rate for one off service
        for (int j=0;j<Constant.CEILING_RATE_3_SUPP_NO;j++)
        {
            dataRow =sheet.getRow(Constant.CEILING_RATE_3_ONEOFF_SUPP_RESIDENT_ROWNO+j);
            rowNo = Constant.CEILING_RATE_3_ONEOFF_SUPP_RESIDENT_ROWNO+i;
            HSSFCell cellContractorId = dataRow.getCell((short) CEILING_RATE_3_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr3.setSuppInd(-1);
                cr3.setOneoffSupp1Resident((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+3)).getNumericCellValue());
            }
            else
            {
                cr3.setSuppInd(0);
                cr3.setOneoffSupp1Resident(0);
            }
            dataRow =sheet.getRow(Constant.CEILING_RATE_3_ONEOFF_SUPP_NON_RESIDENT_ROWNO+j);
            rowNo = Constant.CEILING_RATE_3_ONEOFF_SUPP_NON_RESIDENT_ROWNO+i;
            cellContractorId = dataRow.getCell((short) CEILING_RATE_3_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr3.setSuppInd(-1);
                cr3.setOneoffSupp1NonResident((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+3)).getNumericCellValue());
            }
            else
            {
                cr3.setOneoffSupp1NonResident(0);
            }
            dataRow =sheet.getRow(Constant.CEILING_RATE_3_ONEOFF_SUPP_OFFSHORE_ROWNO+j);
            rowNo = Constant.CEILING_RATE_3_ONEOFF_SUPP_OFFSHORE_ROWNO+i;
            cellContractorId = dataRow.getCell((short) CEILING_RATE_3_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr3.setSuppInd(-1);
                cr3.setOneoffSupp1Offshore((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+3)).getNumericCellValue());
            }
            else
            {
                cr3.setOneoffSupp1Offshore(0);
            }
        }
        
        // get Resident rate for ongoing service
        rowNo = CEILING_RATE_3_ONGOING_RESIDENT_ROW1NO+i;
        cr3.setOngoingSc1ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+2));
        cr3.setOngoingSc1ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+3));
        cr3.setOngoingSc1ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+4));
        cr3.setOngoingSc2ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+5));
        cr3.setOngoingSc2ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+6));
        cr3.setOngoingSc2ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+7));
        cr3.setOngoingSc3ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+8));
        cr3.setOngoingSc3ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+9));
        cr3.setOngoingSc3ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+10));
        cr3.setOngoingSc4ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+11));
        cr3.setOngoingSc4ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+12));
        cr3.setOngoingSc4ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+13));
        cr3.setOngoingSc5ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+14));
        cr3.setOngoingSc5ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+15));
        cr3.setOngoingSc5ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+16));
        
        // get Resident rate for ongoing service
        rowNo = CEILING_RATE_3_ONGOING_RESIDENT_ROW2NO+i;
        cr3.setOngoingSc6ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+2));
        cr3.setOngoingSc6ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+3));
        cr3.setOngoingSc6ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+4));
        cr3.setOngoingSc7ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+5));
        cr3.setOngoingSc7ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+6));
        cr3.setOngoingSc7ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+7));
        cr3.setOngoingSc8ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+8));
        cr3.setOngoingSc8ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+9));
        cr3.setOngoingSc8ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+10));
        cr3.setOngoingSc9ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+11));
        cr3.setOngoingSc9ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+12));
        cr3.setOngoingSc9ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+13));
        cr3.setOngoingSc10ResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+14));
        cr3.setOngoingSc10ResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+15));
        cr3.setOngoingSc10ResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+16));
        
        
        // get Non Resident rate for ongoing service
        rowNo = CEILING_RATE_3_ONGOING_NON_RESIDENT_ROW1NO+i;
        cr3.setOngoingSc1NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+2));
        cr3.setOngoingSc1NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+3));
        cr3.setOngoingSc1NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+4));
        cr3.setOngoingSc2NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+5));
        cr3.setOngoingSc2NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+6));
        cr3.setOngoingSc2NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+7));
        cr3.setOngoingSc3NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+8));
        cr3.setOngoingSc3NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+9));
        cr3.setOngoingSc3NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+10));
        cr3.setOngoingSc4NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+11));
        cr3.setOngoingSc4NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+12));
        cr3.setOngoingSc4NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+13));
        cr3.setOngoingSc5NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+14));
        cr3.setOngoingSc5NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+15));
        cr3.setOngoingSc5NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+16));
        
        // get Non resident rate for ongoing service
        rowNo = CEILING_RATE_3_ONGOING_NON_RESIDENT_ROW2NO+i;
        cr3.setOngoingSc6NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+2));
        cr3.setOngoingSc6NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+3));
        cr3.setOngoingSc6NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+4));
        cr3.setOngoingSc7NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+5));
        cr3.setOngoingSc7NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+6));
        cr3.setOngoingSc7NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+7));
        cr3.setOngoingSc8NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+8));
        cr3.setOngoingSc8NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+9));
        cr3.setOngoingSc8NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+10));
        cr3.setOngoingSc9NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+11));
        cr3.setOngoingSc9NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+12));
        cr3.setOngoingSc9NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+13));
        cr3.setOngoingSc10NonResidentM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+14));
        cr3.setOngoingSc10NonResidentM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+15));
        cr3.setOngoingSc10NonResidentM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+16));
        
        // get Offshore rate for ongoing service
        rowNo = CEILING_RATE_3_ONGOING_OFFSHORE_ROW1NO+i;
        cr3.setOngoingSc1OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+2));
        cr3.setOngoingSc1OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+3));
        cr3.setOngoingSc1OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+4));
        cr3.setOngoingSc2OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+5));
        cr3.setOngoingSc2OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+6));
        cr3.setOngoingSc2OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+7));
        cr3.setOngoingSc3OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+8));
        cr3.setOngoingSc3OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+9));
        cr3.setOngoingSc3OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+10));
        cr3.setOngoingSc4OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+11));
        cr3.setOngoingSc4OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+12));
        cr3.setOngoingSc4OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+13));
        cr3.setOngoingSc5OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+14));
        cr3.setOngoingSc5OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+15));
        cr3.setOngoingSc5OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+16));
        
        // get offshore rate for ongoing service
        rowNo = CEILING_RATE_3_ONGOING_OFFSHORE_ROW2NO+i;
        cr3.setOngoingSc6OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+2));
        cr3.setOngoingSc6OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+3));
        cr3.setOngoingSc6OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+4));
        cr3.setOngoingSc7OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+5));
        cr3.setOngoingSc7OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+6));
        cr3.setOngoingSc7OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+7));
        cr3.setOngoingSc8OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+8));
        cr3.setOngoingSc8OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+9));
        cr3.setOngoingSc8OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+10));
        cr3.setOngoingSc9OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+11));
        cr3.setOngoingSc9OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+12));
        cr3.setOngoingSc9OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+13));
        cr3.setOngoingSc10OffshoreM1(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+14));
        cr3.setOngoingSc10OffshoreM2(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+15));
        cr3.setOngoingSc10OffshoreM3(getCeilingRateValue(sheet,rowNo,CEILING_RATE_3_COL_OFFSET+16));
        
        // get supplementary rate for ongoing service
        for (int j=0;j<Constant.CEILING_RATE_3_SUPP_NO;j++)
        {
            dataRow =sheet.getRow(Constant.CEILING_RATE_3_ONGOING_SUPP_RESIDENT_ROWNO+j);
            rowNo = Constant.CEILING_RATE_3_ONGOING_SUPP_RESIDENT_ROWNO+i;
            HSSFCell cellContractorId = dataRow.getCell((short) CEILING_RATE_3_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr3.setSuppInd(-1);
                cr3.setOngoingSupp1ResidentM1((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+3)).getNumericCellValue());
                cr3.setOngoingSupp1ResidentM2((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+4)).getNumericCellValue());
                cr3.setOngoingSupp1ResidentM3((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+5)).getNumericCellValue());
            }
            else
            {
                cr3.setSuppInd(0);
                cr3.setOngoingSupp1ResidentM1(0);
                cr3.setOngoingSupp1ResidentM2(0);
                cr3.setOngoingSupp1ResidentM3(0);
            }
            dataRow =sheet.getRow(Constant.CEILING_RATE_3_ONGOING_SUPP_NON_RESIDENT_ROWNO+j);
            rowNo = Constant.CEILING_RATE_3_ONGOING_SUPP_NON_RESIDENT_ROWNO+i;
            cellContractorId = dataRow.getCell((short) CEILING_RATE_3_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr3.setSuppInd(-1);
                cr3.setOngoingSupp1NonResidentM1((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+3)).getNumericCellValue());
                cr3.setOngoingSupp1NonResidentM2((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+4)).getNumericCellValue());
                cr3.setOngoingSupp1NonResidentM3((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+5)).getNumericCellValue());
            }
            else
            {
                cr3.setOngoingSupp1NonResidentM1(0);
                cr3.setOngoingSupp1NonResidentM2(0);
                cr3.setOngoingSupp1NonResidentM3(0);
            }
            dataRow =sheet.getRow(Constant.CEILING_RATE_3_ONGOING_SUPP_OFFSHORE_ROWNO+j);
            rowNo = Constant.CEILING_RATE_3_ONGOING_SUPP_OFFSHORE_ROWNO+i;
            cellContractorId = dataRow.getCell((short) CEILING_RATE_3_COL_OFFSET);
            if (contractorId.equals(cellContractorId.getStringCellValue().trim()))
            {
                cr3.setSuppInd(-1);
                cr3.setOngoingSupp1OffshoreM1((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+3)).getNumericCellValue());
                cr3.setOngoingSupp1OffshoreM2((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+4)).getNumericCellValue());
                cr3.setOngoingSupp1OffshoreM3((float) dataRow.getCell((short) (CEILING_RATE_3_COL_OFFSET+5)).getNumericCellValue());
            }
            else
            {
                cr3.setOngoingSupp1OffshoreM1(0);
                cr3.setOngoingSupp1OffshoreM2(0);
                cr3.setOngoingSupp1OffshoreM3(0);
            }
        }
        return cr3;
    }
    
    private CeilingRate4Info setSC4Rate(String group, int i, HSSFSheet sheet) throws ServletException
    {
        
        HSSFRow dataRow=null;
        CeilingRate4Info cr4=new CeilingRate4Info();
        int rowNo=0;
        int CEILING_RATE_4_RESIDENT_ROWNO=0;
        int CEILING_RATE_4_NON_RESIDENT_ROWNO=0;
        int CEILING_RATE_4_OFFSHORE_ROWNO=0;
        int CEILING_RATE_4_INCIDENT_RESPONSE_SUPP_SERVICE_ROWNO=0;
        int CEILING_RATE_4_ITSECURITY_MONITORING_SERVICE_ROWNO=0;
        int CEILING_RATE_4_COL_OFFSET= Constant.CEILING_RATE_4_COL_OFFSET;
        if (group.equals("N"))
        {
            CEILING_RATE_4_RESIDENT_ROWNO=Constant.CEILING_RATE_4_MINOR_RESIDENT_ROWNO;
            CEILING_RATE_4_NON_RESIDENT_ROWNO=Constant.CEILING_RATE_4_MINOR_NON_RESIDENT_ROWNO;
            CEILING_RATE_4_OFFSHORE_ROWNO=Constant.CEILING_RATE_4_MINOR_OFFSHORE_ROWNO;
            CEILING_RATE_4_INCIDENT_RESPONSE_SUPP_SERVICE_ROWNO=Constant.CEILING_RATE_4_MINOR_INCIDENT_RESPONSE_SUPP_SERVICE_ROWNO;
            CEILING_RATE_4_ITSECURITY_MONITORING_SERVICE_ROWNO=Constant.CEILING_RATE_4_MINOR_ITSECURITY_MONITORING_SERVICE_ROWNO;
        }
        else if (group.equals("J"))
        {
            CEILING_RATE_4_RESIDENT_ROWNO=Constant.CEILING_RATE_4_MAJOR_RESIDENT_ROWNO;
            CEILING_RATE_4_NON_RESIDENT_ROWNO=Constant.CEILING_RATE_4_MAJOR_NON_RESIDENT_ROWNO;
            CEILING_RATE_4_OFFSHORE_ROWNO=Constant.CEILING_RATE_4_MAJOR_OFFSHORE_ROWNO;
            CEILING_RATE_4_INCIDENT_RESPONSE_SUPP_SERVICE_ROWNO=Constant.CEILING_RATE_4_MAJOR_INCIDENT_RESPONSE_SUPP_SERVICE_ROWNO;
            CEILING_RATE_4_ITSECURITY_MONITORING_SERVICE_ROWNO=Constant.CEILING_RATE_4_MAJOR_ITSECURITY_MONITORING_SERVICE_ROWNO;
        }
        
        rowNo = CEILING_RATE_4_RESIDENT_ROWNO+i;
        dataRow=sheet.getRow(CEILING_RATE_4_RESIDENT_ROWNO+i);
        String contractorId = dataRow.getCell((short) CEILING_RATE_4_COL_OFFSET).getStringCellValue().trim();
        String currency = dataRow.getCell((short) (CEILING_RATE_4_COL_OFFSET+1)).getStringCellValue().trim();
        cr4.setContractorId(contractorId);
        cr4.setCurrency(currency);
        
        // get Resident rate
        cr4.setOneoffSc1Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+2));
        cr4.setOneoffSc2Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+3));
        cr4.setOneoffSc3Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+4));
        cr4.setOneoffSc4Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+5));
        cr4.setOneoffSc5Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+6));
        cr4.setOneoffSc6Resident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+7));
        
        
        // get Non Resident rate
        rowNo = CEILING_RATE_4_NON_RESIDENT_ROWNO+i;
        cr4.setOneoffSc1NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+2));
        cr4.setOneoffSc2NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+3));
        cr4.setOneoffSc3NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+4));
        cr4.setOneoffSc4NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+5));
        cr4.setOneoffSc5NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+6));
        cr4.setOneoffSc6NonResident(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+7));
        
        
        // get Offshore rate
        rowNo = CEILING_RATE_4_OFFSHORE_ROWNO+i;
        cr4.setOneoffSc1Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+2));
        cr4.setOneoffSc2Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+3));
        cr4.setOneoffSc3Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+4));
        cr4.setOneoffSc4Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+5));
        cr4.setOneoffSc5Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+6));
        cr4.setOneoffSc6Offshore(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+7));
        
        // get incident response support services rate
        rowNo = CEILING_RATE_4_INCIDENT_RESPONSE_SUPP_SERVICE_ROWNO+i;
        cr4.setOngoingSc1OfficeHours(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+2));
        cr4.setOngoingSc1NonOfficeHours(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+3));
        cr4.setOngoingSc2OfficeHours(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+4));
        cr4.setOngoingSc2NonOfficeHours(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+5));
        cr4.setOngoingSc3OfficeHours(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+6));
        cr4.setOngoingSc3NonOfficeHours(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+7));
        cr4.setOngoingSc4OfficeHours(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+8));
        cr4.setOngoingSc4NonOfficeHours(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+9));
        cr4.setOngoingSc5OfficeHours(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+10));
        cr4.setOngoingSc5NonOfficeHours(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+11));
        
        // get IT security monitoring service rate
        rowNo = CEILING_RATE_4_ITSECURITY_MONITORING_SERVICE_ROWNO+i;
        cr4.setOngoingSc1RoundTheClock(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+2));
        cr4.setOngoingSc2RoundTheClock(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+3));
        cr4.setOngoingSc3RoundTheClock(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+4));
        cr4.setOngoingSc4RoundTheClock(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+5));
        cr4.setOngoingSc5RoundTheClock(getCeilingRateValue(sheet,rowNo,CEILING_RATE_4_COL_OFFSET+6));
        
        return cr4;
    }
    
    private float getCeilingRateValue(HSSFSheet sheet, int rowNo, int offset) throws ServletException
    {
        HSSFRow dataRow = sheet.getRow(rowNo);
        if (dataRow == null) throw new ServletException("Import Error: Invalid data row at Row:" + (rowNo+1)+ " !");
        HSSFCell dataCell=dataRow.getCell((short) offset);
        if (dataCell.getCellType() == HSSFCell.CELL_TYPE_STRING)
        {
            if (dataCell.getStringCellValue().trim().matches("<\\s*No offer\\s*>"))
            {
                return -999;
            }
            else
            {
                throw new ServletException("Import Error: Invalid data at Row:"+ (rowNo+1)+ "; Col:" + (offset+1)+ "; Value:"+dataCell.getStringCellValue().trim());
            }
        }
        else
        {
            return (float) dataCell.getNumericCellValue();
             //return Double(dataCell.getNumericCellValue()).floatValue;
        }
    }
    
    private void performCeilingRateDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus = 0;
        int updateCounter =0;
        String postScreen = request.getParameter("PostScreen");
        request.getSession().setAttribute("CR_POST_SCREEN",postScreen);
        try
        {
            String order_by = request.getParameter("OrderBy");
            String order_dir = request.getParameter("OrderDir");
            if (order_by != null || order_dir !=null)
            {
                HashMap orderHash = new HashMap();
                orderHash.put("ORDER_BY",order_by);
                orderHash.put("ORDER_DIR",order_dir);
                if (postScreen.indexOf("Search") >=0)
                {
                    request.getSession().setAttribute("CR_SEARCH_ORDER",orderHash);
                }
                else
                {
                    request.getSession().setAttribute("CR_LIST_ORDER",orderHash);
                }
            }
            // get information from info object
            CeilingRateInfo cr = new CeilingRateInfo();
            if (request.getParameter("orgKey1") == null ||
                    request.getParameter("orgKey2") == null )
            {
                String errMsg = "Failed to delete records! Invalid parameters for deletion!";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            cr.setServiceCategory(request.getParameter("orgKey1"));
            cr.setEffectiveDate(SysManager.getSQLDate(request.getParameter("orgKey2")));
            cr.setLastUpdatedBy(secCtx.getUserId());
            request.getSession().setAttribute("CEILING_RATE_DATA",cr);
            
            CeilingRateDataBean crDB = getCeilingRateDataBean();
            
            //call delete
            updateCounter = crDB.deleteCeilingRate(cr,secCtx);
            updateStatus = updateCounter;
            
            // check return status and redirect
            if (updateStatus >= 1)
            {
                request.getSession().removeAttribute("CEILING_RATE_DATA");
                request.getSession().removeAttribute("CR_POST_SCREEN");
                String Msg = "Ceiling Rate records deleted successfully! " + updateCounter + " records deleted.";
                request.getSession().setAttribute("CEILING_RATE_MSG",Msg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }
            else if (updateStatus ==0)
            {
                postScreen = "CeilingRateDelete.jsp";
                String errMsg = "Failed to delete ceiling rate records!";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else
            {
                postScreen = "CeilingRateDelete.jsp";
                String errMsg = "System error encountered!";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }
        catch (Exception ex)
        {
            throw new ServletException("CeilingRateDelete Database ERROR: AdminServlet:performCeilingRateDelete: database deletion error\n" + ex.toString());
        }
        
    }
    
    private void performCeilingRateRelease(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus = 0;
        int updateCounter =0;
        String postScreen = request.getHeader("referer");
        
        String order_by = request.getParameter("OrderBy");
        String order_dir = request.getParameter("OrderDir");
        if (order_by != null || order_dir !=null)
        {
            HashMap orderHash = new HashMap();
            orderHash.put("ORDER_BY",order_by);
            orderHash.put("ORDER_DIR",order_dir);
            if (postScreen.indexOf("Search") >=0)
            {
                request.getSession().setAttribute("CR_SEARCH_ORDER",orderHash);
            }
            else
            {
                request.getSession().setAttribute("CR_LIST_ORDER",orderHash);
            }
        }
        
        try
        {
            if (request.getParameter("selectedKey1") == null ||
                    request.getParameter("selectedKey2") == null )
            {
                String errMsg = "Failed to release records! Invalid parameters for release publishing!";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            
            // read data from posted form and put into info object
            CeilingRateInfo cr = new CeilingRateInfo();
            cr.setServiceCategory(request.getParameter("selectedKey1"));
            cr.setEffectiveDate(SysManager.getSQLDate(request.getParameter("selectedKey2")));
            cr.setLastUpdatedBy(secCtx.getUserId());
            CeilingRateDataBean crDB = getCeilingRateDataBean();
            
            //call update
            updateCounter = crDB.releaseCeilingRate(cr,secCtx);
            updateStatus = updateCounter;
            
            // check return status and redirect
            if (updateStatus >= 1)
            {
                request.getSession().removeAttribute("CEILING_RATE_DATA");
                String Msg = "Ceiling Rate records released for publishing successfully! ";
                request.getSession().setAttribute("CEILING_RATE_MSG",Msg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","MSG");
                response.sendRedirect(postScreen);
            }
            else if (updateStatus ==0)
            {
                String errMsg = "Failed to release ceiling rate records!";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else
            {
                String errMsg = "System error encountered!";
                request.getSession().setAttribute("CEILING_RATE_MSG",errMsg);
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
        }
        catch (Exception ex)
        {
            if (ex.getMessage().indexOf("SQL error")>=0)
            {
                request.getSession().setAttribute("CEILING_RATE_MSG",ex.getMessage().replaceAll("\\[QPSES Exception\\]",""));
                request.getSession().setAttribute("CEILING_RATE_MSGTYPE","ERR");
                RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
                dispatcher.forward(request, response);
            }
            else
            {
                throw new ServletException("Servlet Error: CeilingRateRelease Database ERROR: AdminServlet:performCeilingRateRelease\n" + ex.toString());
            }
        }
        
    }
    
    private void performCeilingRateSearch(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        
        SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
        int updateStatus =0;
        CeilingRateInfo cr = new CeilingRateInfo();
        
        try
        {
            String effectiveDate = request.getParameter("EffectiveDate");
            String serviceCategory = request.getParameter("ServiceCategory");
            String activeInd=request.getParameter("PublishInd");
            
            if (activeInd!=null)
            {
                cr.setActiveInd(Integer.parseInt(activeInd));
            }
            else
            {
                cr.setActiveInd(-9); // to override the value (0) stored in initialized CeilingRateInfo
            }
            if (! effectiveDate.equals("")) cr.setEffectiveDate(SysManager.getSQLDate(effectiveDate));
            if (! serviceCategory.equals("0")) cr.setServiceCategory(serviceCategory);
            
            request.getSession().setAttribute("CEILING_RATE_SEARCH_PARAMETER",cr);
            request.getSession().removeAttribute("CR_SEARCH_ORDER");
            postScreen = "CeilingRateSearch.jsp";
            response.sendRedirect(postScreen);
            return;
        }
        catch(Exception ex)
        {
        	logger.error(ex);
            throw new ServletException("Servlet Error: CeilingRateSearch ERROR: \r\n" +
                    "AdminCeilingRateServlet:performCeilingRateSearch: Get Request Error\n" + ex.toString());
        }
    }
    
    private void performCeilingRateSearchReset(HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        
        
        try
        {
            request.getSession().removeAttribute("CEILING_RATE_SEARCH_PARAMETER");
            request.getSession().removeAttribute("CR_SEARCH_ORDER");
            postScreen = request.getHeader("referer");
            response.sendRedirect(postScreen);
            return;
        }
        catch(Exception ex)
        {
        	logger.error(ex);
            throw new ServletException("Servlet Error: CeilingRateSearchReset ERROR: \r\n " +
                    "AdminCeilingRateServlet:performCeilingRateSearchReset: Get Request Error\n" + ex.toString());
        }
    }
    
    private void performCeilingRatePrintReport(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        try
        {
            // get user id
            SecurityContext secCtx = (SecurityContext) request.getSession().getAttribute("QPSES_SECURITY_CONTEXT");
            adminId = secCtx.getUserId();
            // initialize variables
            boolean sc1=false;
            boolean sc2=false;
            boolean sc3=false;
            boolean sc4=false;
            
            // get to be printed service category
            String serviceCategory=request.getParameter("selectedKey1");
            String effectiveDate = request.getParameter("selectedKey2");
            
            if (serviceCategory == null || effectiveDate == null)
            {
                // get imported service category
                CeilingRateInfo cr = new CeilingRateInfo();
                cr =(CeilingRateInfo) request.getSession().getAttribute("PRINT_CEILING_RATE_DATA");
                serviceCategory = cr.getServiceCategory();
                effectiveDate = SysManager.getStringfromSQLDate(cr.getEffectiveDate());
            }
            if (serviceCategory.indexOf("1") >= 0) sc1=true;
            if (serviceCategory.indexOf("2") >= 0) sc2=true;
            if (serviceCategory.indexOf("3") >= 0) sc3=true;
            if (serviceCategory.indexOf("4") >= 0) sc4=true;
            
            
            // Variable
            int numOfCol = 0;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            CeilingRateCat1Info aCRCat1Info = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            // Initialize PDF helper
            PdfHelper pdfHelper = new PdfHelper();
            pdfHelper.setDisplayUserName(adminId);
            pdfHelper.setHeaderText("RESTRICTED (CONTRACT)");
            pdfHelper.setFooterText("RESTRICTED (CONTRACT)");
            
            pdfHelper.setHeaderTextPos(15);
            pdfHelper.setFooterTextPos(50);
            
            // Initialize PDF document
            Document aPdfDoc = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(aPdfDoc, baos);
            writer.setPageEvent(pdfHelper);
            aPdfDoc.setMargins(50, 50, 50, 50);
            aPdfDoc.open();
            
            // Set Title Paragraph as QPS3 enhancement
            Paragraph aParagraph = pdfHelper.getTitleParagraph("Ceiling Rates of the Standing Offer Agreements for");
            aParagraph.setAlignment(1);
            aPdfDoc.add(aParagraph);            
            aParagraph = pdfHelper.getTitleParagraph("Quality Professional Services 3 (SOA-QPS3)");
            aParagraph.setAlignment(1);
            aPdfDoc.add(aParagraph);                              
            aPdfDoc.add(Chunk.NEWLINE);
            
            if (sc1)
            {
                CeilingRateDataBean crDB = getCeilingRateDataBean();
                List<CeilingRateCat1Info> CeilingRate1List=crDB.getCeilingRate1(effectiveDate);
                createCat1PdfFile(effectiveDate,aPdfDoc,pdfHelper,CeilingRate1List);
            }
            if (sc2)
            {
                if (sc1) aPdfDoc.newPage(); // break page before continue printing
                CeilingRateDataBean crDB = getCeilingRateDataBean();
                List<CeilingRateCat2Info> CeilingRate2List=crDB.getCeilingRate2(effectiveDate);
                createCat2PdfFile(effectiveDate,aPdfDoc,pdfHelper,CeilingRate2List);
            }
            if (sc3)
            {
                if (sc1||sc2) aPdfDoc.newPage(); // break page before continue printing
                CeilingRateDataBean crDB = getCeilingRateDataBean();
                List<CeilingRateCat3Info> CeilingRate3List=crDB.getCeilingRate3(effectiveDate);
                createCat3PdfFile(effectiveDate,aPdfDoc,pdfHelper,CeilingRate3List);
            }
            if (sc4)
            {
                if (sc1||sc2||sc3) aPdfDoc.newPage(); // break page before continue printing
                CeilingRateDataBean crDB = getCeilingRateDataBean();
                List<CeilingRateCat4Info> CeilingRate4List=crDB.getCeilingRate4(effectiveDate);
                createCat4PdfFile(effectiveDate,aPdfDoc,pdfHelper,CeilingRate4List);
            }
            
            
            // Write PDF objects to stream
            aPdfDoc.close();
            
            // Response Setting
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=\"" + "(Restricted) SOA-QPS Ceiling Rates ("+effectiveDate+").pdf" + "\"");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentLength(baos.size());
            
            // Output PDF file
            ServletOutputStream sos = response.getOutputStream();
            baos.writeTo(sos);
            sos.flush();
            request.getSession().setAttribute("PDF_FILE_STREAM",baos);
        }
        catch(Exception ex)
        {
        	logger.error(ex);
            throw new ServletException("Servlet Error: CeilingRatePrintReport ERROR: " +
                    "AdminCeilingRateServlet:performCeilingRatePrintReport: " +
                    "Get Request Error\n" + ex.toString());
        }
    }
    
    
    private void createCat1PdfFile(String effectiveDate, Document aPdfDoc, PdfHelper pdfHelper,List allCrCat1Info)
    throws ServletException, IOException
    {
        try
        {
            int numOfCol = 0;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            CeilingRateCat1Info aCRCat1Info = null;
            
            // Create Header
            aPdfDoc.add(pdfHelper.getTitleParagraph("Service Category 1"));
            aPdfDoc.add(pdfHelper.getTitleParagraph("Effective Date: " + effectiveDate));
            aPdfDoc.add(Chunk.NEWLINE);
            aPdfDoc.add(pdfHelper.getTitleParagraph("Ceiling Rates for the following professional services" ));
            aPdfDoc.add(pdfHelper.getParagraph("- Departmental Information Technology Plan"));
            aPdfDoc.add(pdfHelper.getParagraph("- Feasibility and Technical Study"));
            aPdfDoc.add(pdfHelper.getParagraph("- Independent Programme Management"));
            aPdfDoc.add(pdfHelper.getParagraph("- Independent Project Management"));
            aPdfDoc.add(Chunk.NEWLINE);
            
            /*****************************************************
             * Standard staff category table
             *****************************************************/
            numOfCol = 12;
            
            for (int i = 0; i < CeilingRateCat1Info.NumOfWorkingLocation; i++)
            {
                // Set up table
                
                if  (i != 0 ) aPdfDoc.newPage();
                aPdfTable = new PdfPTable(numOfCol);
                int tableColWidths[] = {10, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}; // percentage
                aPdfTable.setWidths(tableColWidths);
                
                genRow1ToRow3Report(aPdfTable, pdfHelper, cell, numOfCol, i); 
                
                for (int j = 1; j <= CeilingRateCat1Info.NumOfStaffCategory; j++)
                {
                    cell = pdfHelper.getCell("" + (j + 2));  // start from 3
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                }
                
                // Row 4
                //cell = pdfHelper.getHeaderCell("Minor Works Group");
                //cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
                //aPdfTable.addCell(cell);
                
                //cell = pdfHelper.getCell("");
                //cell.setColspan(numOfCol - 2);
                //aPdfTable.addCell(cell);
                
                // Row 5 (Data)
              /* for (int j = 0; j < allCrCat1Info.size(); j++)
                {
                    aCRCat1Info = (CeilingRateCat1Info) allCrCat1Info.get(j);
                    
                    if (aCRCat1Info.getServiceGroup().equals(aCRCat1Info.MinorGroupInd))
                    {
                        cell = pdfHelper.getCell(aCRCat1Info.getContractorID());
                        cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell(aCRCat1Info.getCurrency());
                        cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        for (int k = 0; k < CeilingRateCat1Info.NumOfStaffCategory; k++) // Staff Category
                        {
                            cell = pdfHelper.getCellSmall("" + SysManager.formatDecimal(aCRCat1Info.getStdStaffRate(k, i)));
                            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                            aPdfTable.addCell(cell);
                        }
                    }
                }*/
                
                // Row 6
              /*  cell = pdfHelper.getCell("");
                cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell("");
                cell.setColspan(numOfCol - 2);
                aPdfTable.addCell(cell);
              */ 
                // Row 7
                //cell = pdfHelper.getHeaderCell("Major Works Group");
                //cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                //aPdfTable.addCell(cell);
                
                //cell = pdfHelper.getCell("");
                //cell.setColspan(numOfCol - 2);
                //aPdfTable.addCell(cell);
                
                // Row 8 (Data)
                for (int j = 0; j < allCrCat1Info.size(); j++)
                {
                    aCRCat1Info = (CeilingRateCat1Info)allCrCat1Info.get(j);
                    
                    if (aCRCat1Info.getServiceGroup().equals(aCRCat1Info.MajorGroupInd))
                    {
                        cell = pdfHelper.getCell(aCRCat1Info.getContractorID());
                        cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell(aCRCat1Info.getCurrency());
                        cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        for (int k = 0; k < CeilingRateCat1Info.NumOfStaffCategory; k++) // Staff Category
                        {
                            cell = pdfHelper.getCellSmall("" + SysManager.formatDecimal(aCRCat1Info.getStdStaffRate(k, i)));
                            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                            aPdfTable.addCell(cell);
                        }
                    }
                }
                
                aPdfDoc.add(aPdfTable);
                //aPdfDoc.newPage();
            }
            
            

            
        }
        catch(Exception ex)
        {
        	logger.error(ex);
            throw new ServletException(ex.getMessage()); }
    }
    
    private void createCat2PdfFile(String effectiveDate, Document aPdfDoc, PdfHelper pdfHelper,List allCrCat2Info)
    throws ServletException, IOException
    {
        
        try
        {
            
            // Variable
            int numOfCol = 0;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            CeilingRateCat2Info aCRCat2Info = null;
            
            
            // Create Header
            aPdfDoc.add(pdfHelper.getTitleParagraph("Service Category 2"));
            aPdfDoc.add(pdfHelper.getTitleParagraph("Effective Date: " + effectiveDate));
            aPdfDoc.add(Chunk.NEWLINE);
            aPdfDoc.add(pdfHelper.getTitleParagraph("Ceiling Rates for the following professional services"));
            aPdfDoc.add(pdfHelper.getParagraph("- System Maintenance and Support"));
            aPdfDoc.add(pdfHelper.getParagraph("- Network Support Services"));
            aPdfDoc.add(Chunk.NEWLINE);
            
            /*****************************************************
             * Standard staff category table
             *****************************************************/
            numOfCol = 17;
            
            for (int i = 0; i < CeilingRateCat2Info.NumOfWorkingLocation; i++)
            {
                if  (i != 0 ) aPdfDoc.newPage();
                for (int m = 0; m <= 1; m++)
                {
                    // Set up table
                    
                    aPdfTable = new PdfPTable(numOfCol);
                    int tableColWidths[] = {10, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}; // percentage
                    aPdfTable.setWidths(tableColWidths);
                    genRow1ToRow3Report(aPdfTable, pdfHelper, cell, numOfCol, i);
                    
                    for (int j = 1; j <= 5; j++)
                    {
                        cell = pdfHelper.getCell("" + (j + (m * 5)));
                        cell.setColspan(3);
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        aPdfTable.addCell(cell);
                    }
                    
                    // Row 4
                    cell = pdfHelper.getHeaderCell("");
                    cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.BOTTOM);
                    aPdfTable.addCell(cell);
                    
                    for (int j = 0; j < 5; j++)
                    {
                        for (int k = 0; k < CeilingRateCat2Info.NumOfMScheme; k++)
                        {
                            cell = pdfHelper.getCell(CeilingRateCat2Info.MSchemeName[k]);
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            aPdfTable.addCell(cell);
                        }
                    }
                    
                    // Row 5
                    cell = pdfHelper.getHeaderCell("Minor Works Group");
                    cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
                    aPdfTable.addCell(cell);
                    
                    for (int j = 0; j < 5; j++)
                    {
                        cell = pdfHelper.getCell("");
                        cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell("");
                        cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell("");
                        cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                    }
                    
                    // Row 6 (Data)
                    for (int j = 0; j < allCrCat2Info.size(); j++)
                    {
                        aCRCat2Info = (CeilingRateCat2Info)allCrCat2Info.get(j);
                        
                        if (aCRCat2Info.getServiceGroup().equals(aCRCat2Info.MinorGroupInd))
                        {
                            cell = pdfHelper.getCell(aCRCat2Info.getContractorID());
                            cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell(aCRCat2Info.getCurrency());
                            cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                            aPdfTable.addCell(cell);
                            
                            for (int k = 0 + (m * 5); k < 5 + (m * 5); k++) // Staff Category
                            {
                                for (int l = 0; l < CeilingRateCat2Info.NumOfMScheme; l++)
                                {
                                    cell = pdfHelper.getCellSmall("" + SysManager.formatDecimal(aCRCat2Info.getStdStaffRate(k, i, l)));
                                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                                    aPdfTable.addCell(cell);
                                }
                            }
                        }
                    }
                    
                    // Row 7
                    cell = pdfHelper.getCell("");
                    cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
                    aPdfTable.addCell(cell);
                    
                    for (int j = 0; j < 5; j++)
                    {
                        cell = pdfHelper.getCell("");
                        cell.setColspan(3);
                        aPdfTable.addCell(cell);
                    }
                    
                    // Row 8
                    cell = pdfHelper.getHeaderCell("Major Works Group");
                    cell.setColspan(2);
                    aPdfTable.addCell(cell);
                    
                    for (int j = 0; j < 5; j++)
                    {
                        cell = pdfHelper.getCell("");
                        cell.setColspan(3);
                        aPdfTable.addCell(cell);
                    }
                    
                    // Row 9 (Data)
                    for (int j = 0; j < allCrCat2Info.size(); j++)
                    {
                        aCRCat2Info = (CeilingRateCat2Info)allCrCat2Info.get(j);
                        
                        if (aCRCat2Info.getServiceGroup().equals(aCRCat2Info.MajorGroupInd))
                        {
                            cell = pdfHelper.getCell(aCRCat2Info.getContractorID());
                            cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell(aCRCat2Info.getCurrency());
                            cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                            aPdfTable.addCell(cell);
                            
                            for (int k = 0 + (m * 5); k < 5 + (m * 5); k++) // Staff Category
                            {
                                for (int l = 0; l < CeilingRateCat2Info.NumOfMScheme; l++)
                                {
                                    cell = pdfHelper.getCellSmall("" + SysManager.formatDecimal(aCRCat2Info.getStdStaffRate(k, i, l)));
                                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                                    aPdfTable.addCell(cell);
                                }
                            }
                        }
                    }
                    
                    aPdfDoc.add(aPdfTable);
                    //aPdfDoc.newPage();
                }
            }
            
            
            
        }
        catch(Exception ex)
        {
        	logger.error(ex);
            throw new ServletException(ex.getMessage()); }
    }
    
    private void createCat3PdfFile(String effectiveDate, Document aPdfDoc, PdfHelper pdfHelper,List allCrCat3Info)
    throws ServletException, IOException
    {
        
        try
        {
            
            // Variable
            int numOfCol = 0;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            CeilingRateCat3Info aCRCat3Info = null;
            
            // Create Header
            aPdfDoc.add(pdfHelper.getTitleParagraph("Service Category 3"));
            aPdfDoc.add(pdfHelper.getTitleParagraph("Effective Date: " + effectiveDate));
            aPdfDoc.add(Chunk.NEWLINE);
            aPdfDoc.add(pdfHelper.getTitleParagraph("Ceiling Rates for the following one-off services"));
            aPdfDoc.add(pdfHelper.getParagraph("- Network Planning, Design and Implementation"));
            aPdfDoc.add(pdfHelper.getParagraph("- Office System Implementation"));
            aPdfDoc.add(pdfHelper.getParagraph("- System Analysis and Design"));
            aPdfDoc.add(pdfHelper.getParagraph("- System Implementation and System Integration"));
            aPdfDoc.add(pdfHelper.getParagraph("- Full System Development Life Cycle Services - Feasibility and Technical Study"));
            aPdfDoc.add(Chunk.NEWLINE);
            
            /*****************************************************
             * Standard staff category table (One-off)
             *****************************************************/
            numOfCol = 14;
            
            for (int i = 0; i < CeilingRateCat3Info.NumOfWorkingLocation; i++)
            {
                // Set up table
                if  (i != 0 ) aPdfDoc.newPage();
                aPdfTable = new PdfPTable(numOfCol);
                int tableColWidths[] = {10, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}; // percentage
                aPdfTable.setWidths(tableColWidths);
                genRow1ToRow3Report(aPdfTable, pdfHelper, cell, numOfCol, i);
                
                for (int j = 1; j <= CeilingRateCat3Info.NumOfOneOffStaffCategory; j++)
                {
                    cell = pdfHelper.getCell("" + j);
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                }
                
                // Row 4
                cell = pdfHelper.getHeaderCell("Minor Works Group");
                cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell("");
                cell.setColspan(numOfCol - 2);
                aPdfTable.addCell(cell);
                
                // Row 5 (Data)
                for (int j = 0; j < allCrCat3Info.size(); j++)
                {
                    aCRCat3Info = (CeilingRateCat3Info)allCrCat3Info.get(j);
                    
                    if (aCRCat3Info.getServiceGroup().equals(aCRCat3Info.MinorGroupInd))
                    {
                        cell = pdfHelper.getCell(aCRCat3Info.getContractorID());
                        cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell(aCRCat3Info.getCurrency());
                        cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        for (int k = 0; k < CeilingRateCat3Info.NumOfOneOffStaffCategory; k++) // Staff Category
                        {
                            cell = pdfHelper.getCellSmall("" + SysManager.formatDecimal(aCRCat3Info.getStdOneOffStaffRate(k, i)));
                            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                            aPdfTable.addCell(cell);
                        }
                    }
                }
                
                // Row 6
                cell = pdfHelper.getCell("");
                cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell("");
                cell.setColspan(numOfCol - 2);
                aPdfTable.addCell(cell);
                
                // Row 7
                cell = pdfHelper.getHeaderCell("Major Works Group");
                cell.setColspan(2);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell("");
                cell.setColspan(numOfCol - 2);
                aPdfTable.addCell(cell);
                
                // Row 8 (Data)
                for (int j = 0; j < allCrCat3Info.size(); j++)
                {
                    aCRCat3Info = (CeilingRateCat3Info)allCrCat3Info.get(j);
                    
                    if (aCRCat3Info.getServiceGroup().equals(aCRCat3Info.MajorGroupInd))
                    {
                        cell = pdfHelper.getCell(aCRCat3Info.getContractorID());
                        cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell(aCRCat3Info.getCurrency());
                        cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        for (int k = 0; k < CeilingRateCat3Info.NumOfOneOffStaffCategory; k++) // Staff Category
                        {
                            cell = pdfHelper.getCellSmall("" + SysManager.formatDecimal(aCRCat3Info.getStdOneOffStaffRate(k, i)));
                            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                            aPdfTable.addCell(cell);
                        }
                    }
                }
                
                aPdfDoc.add(aPdfTable);
                //aPdfDoc.newPage();
            }
            
            

            
            /*****************************************************
             * Standard staff category table (On-going)
             *****************************************************/
            // Create Header
            aPdfDoc.add(pdfHelper.getTitleParagraph("Ceiling Rates for the following on-going services in Full System Development Life Cycle Services"));
            aPdfDoc.add(pdfHelper.getParagraph("- System Maintenance and Support"));
            aPdfDoc.add(pdfHelper.getParagraph("- Network Support Services"));
            aPdfDoc.add(Chunk.NEWLINE);
            
            
            for (int i = 0; i < CeilingRateCat3Info.NumOfWorkingLocation; i++)
            {
                if  (i != 0 ) aPdfDoc.newPage();
                for (int m = 0; m <= 1; m++)
                {
                    // Set up table
                    numOfCol = 17;
                    
                    aPdfTable = new PdfPTable(numOfCol);
                    int tableColWidths[] = {10, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}; // percentage
                    aPdfTable.setWidths(tableColWidths);
                    genRow1ToRow3Report(aPdfTable, pdfHelper, cell, numOfCol, i);
                    
                    for (int j = 1; j <= 5; j++)
                    {
                        cell = pdfHelper.getCell("" + (j + (m * 5)));
                        cell.setColspan(3);
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        aPdfTable.addCell(cell);
                    }
                    
                    // Row 4
                    cell = pdfHelper.getHeaderCell("");
                    cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.BOTTOM);
                    aPdfTable.addCell(cell);
                    
                    for (int j = 0; j < 5; j++)
                    {
                        for (int k = 0; k < CeilingRateCat3Info.NumOfMScheme; k++)
                        {
                            cell = pdfHelper.getCell(CeilingRateCat3Info.MSchemeName[k]);
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            aPdfTable.addCell(cell);
                        }
                    }
                    
                    // Row 5
                    cell = pdfHelper.getHeaderCell("Minor Works Group");
                    cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
                    aPdfTable.addCell(cell);
                    
                    for (int j = 0; j < 5; j++)
                    {
                        cell = pdfHelper.getCell("");
                        cell.setColspan(3);
                        aPdfTable.addCell(cell);
                    }
                    
                    // Row 6 (Data)
                    for (int j = 0; j < allCrCat3Info.size(); j++)
                    {
                        aCRCat3Info = (CeilingRateCat3Info)allCrCat3Info.get(j);
                        
                        if (aCRCat3Info.getServiceGroup().equals(aCRCat3Info.MinorGroupInd))
                        {
                            cell = pdfHelper.getCell(aCRCat3Info.getContractorID());
                            cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell(aCRCat3Info.getCurrency());
                            cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                            aPdfTable.addCell(cell);
                            
                            for (int k = 0 + (m * 5); k < 5 + (m * 5); k++) // Staff Category
                            {
                                for (int l = 0; l < CeilingRateCat3Info.NumOfMScheme; l++)
                                {
                                    cell = pdfHelper.getCellSmall("" + SysManager.formatDecimal(aCRCat3Info.getStdOnGoingStaffRate(k, i, l)));
                                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                                    aPdfTable.addCell(cell);
                                }
                            }
                        }
                    }
                    
                    // Row 7
                    cell = pdfHelper.getCell("");
                    cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
                    aPdfTable.addCell(cell);
                    
                    for (int j = 0; j < 5; j++)
                    {
                        cell = pdfHelper.getCell("");
                        cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell("");
                        cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell("");
                        cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                    }
                    
                    // Row 8
                    cell = pdfHelper.getHeaderCell("Major Works Group");
                    cell.setColspan(2);
                    aPdfTable.addCell(cell);
                    
                    for (int j = 0; j < 5; j++)
                    {
                        cell = pdfHelper.getCell("");
                        cell.setColspan(3);
                        aPdfTable.addCell(cell);
                    }
                    
                    // Row 9 (Data)
                    for (int j = 0; j < allCrCat3Info.size(); j++)
                    {
                        aCRCat3Info = (CeilingRateCat3Info)allCrCat3Info.get(j);
                        
                        if (aCRCat3Info.getServiceGroup().equals(aCRCat3Info.MajorGroupInd))
                        {
                            cell = pdfHelper.getCell(aCRCat3Info.getContractorID());
                            cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell(aCRCat3Info.getCurrency());
                            cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                            aPdfTable.addCell(cell);
                            
                            for (int k = 0 + (m * 5); k < 5 + (m * 5); k++) // Staff Category
                            {
                                for (int l = 0; l < CeilingRateCat3Info.NumOfMScheme; l++)
                                {
                                    cell = pdfHelper.getCellSmall("" + SysManager.formatDecimal(aCRCat3Info.getStdOnGoingStaffRate(k, i, l)));
                                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                                    aPdfTable.addCell(cell);
                                }
                            }
                        }
                    }
                    
                    aPdfDoc.add(aPdfTable);
                    //aPdfDoc.newPage();
                }
            }
            
            

            
        }
        catch(Exception ex)
        {
        	logger.error(ex);
            throw new ServletException(ex.getMessage()); }
    }
    
    private void createCat4PdfFile(String effectiveDate, Document aPdfDoc, PdfHelper pdfHelper,List allCrCat4Info)
    throws ServletException, IOException
    {
        
        try
        {
            
            // Variable
            int numOfCol = 0;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            CeilingRateCat4Info aCRCat4Info = null;
            
            // Create Header
            aPdfDoc.add(pdfHelper.getTitleParagraph("Service Category 4"));
            aPdfDoc.add(pdfHelper.getTitleParagraph("Effective Date: " + effectiveDate));
            aPdfDoc.add(Chunk.NEWLINE);
            aPdfDoc.add(pdfHelper.getTitleParagraph("Ceiling Rates for the following one-off services"));
            aPdfDoc.add(pdfHelper.getParagraph("- Security Risk Assessment"));
            aPdfDoc.add(pdfHelper.getParagraph("- Security Audit"));
            aPdfDoc.add(pdfHelper.getParagraph("- Security Management Design and Implementation Services"));
            aPdfDoc.add(pdfHelper.getParagraph("- Independent Testing"));
            aPdfDoc.add(Chunk.NEWLINE);
            
            /*****************************************************
             * One-off services table
             *****************************************************/
            numOfCol = 8;
            
            for (int i = 0; i < CeilingRateCat4Info.NumOfWorkingLocation; i++)
            {
                // Set up table
                aPdfTable = new PdfPTable(numOfCol);
                int tableColWidths[] = {10, 2, 5, 5, 5, 5, 5, 5}; // percentage
                aPdfTable.setWidths(tableColWidths);
                genRow1ToRow3Report(aPdfTable, pdfHelper, cell, numOfCol, i);
                
                for (int j = 1; j <= CeilingRateCat4Info.NumOfOneOffStaffCategory; j++)
                {
                    cell = pdfHelper.getCell("" + j);
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                }
                
                // Row 4
/*              cell = pdfHelper.getHeaderCell("Minor Works Group");
                cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell("");
                cell.setColspan(CeilingRateCat4Info.NumOfOneOffStaffCategory);
                aPdfTable.addCell(cell);
                
                // Row 5 (Data)
                for (int j = 0; j < allCrCat4Info.size(); j++)
                {
                    aCRCat4Info = (CeilingRateCat4Info)allCrCat4Info.get(j);
                    
                    if (aCRCat4Info.getServiceGroup().equals(aCRCat4Info.MinorGroupInd))
                    {
                        cell = pdfHelper.getCell(aCRCat4Info.getContractorID());
                        cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell(aCRCat4Info.getCurrency());
                        cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        for (int k = 0; k < CeilingRateCat4Info.NumOfOneOffStaffCategory; k++)
                        {
                            cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOneOffStaffRate(k, i)));
                            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                            aPdfTable.addCell(cell);
                        }
                    }
                }
                
                
                // Row 6
                cell = pdfHelper.getHeaderCell("");
                cell.setColspan(2);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("");
                cell.setColspan(CeilingRateCat4Info.NumOfOneOffStaffCategory);
                aPdfTable.addCell(cell);
                
                
                // Row 7
                cell = pdfHelper.getHeaderCell("Major Works Group");
                cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell("");
                cell.setColspan(CeilingRateCat4Info.NumOfOneOffStaffCategory);
                aPdfTable.addCell(cell);
*/                
                // Row 8 (Data)
                for (int j = 0; j < allCrCat4Info.size(); j++)
                {
                    aCRCat4Info = (CeilingRateCat4Info)allCrCat4Info.get(j);
                    
                    if (aCRCat4Info.getServiceGroup().equals(aCRCat4Info.MajorGroupInd))
                    {
                        cell = pdfHelper.getCell(aCRCat4Info.getContractorID());
                        cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell(aCRCat4Info.getCurrency());
                        cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                        aPdfTable.addCell(cell);
                        
                        for (int k = 0; k < CeilingRateCat4Info.NumOfOneOffStaffCategory; k++)
                        {
                            cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOneOffStaffRate(k, i)));
                            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                            aPdfTable.addCell(cell);
                        }
                    }
                }
                
                aPdfDoc.add(aPdfTable);
                aPdfDoc.newPage();
            }
            
            
            /*****************************************************
             * On-going services table (Office/Non-office Hour)
             *****************************************************/
            // Initialize PDF Table
            int tableColWidths2[] = {10, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}; // percentage
            numOfCol = 12;
            aPdfTable = new PdfPTable(numOfCol);
            aPdfTable.setWidths(tableColWidths2);
            aPdfTable.setWidthPercentage(100);
            aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            
            // Create Header
            aPdfDoc.add(pdfHelper.getTitleParagraph("Ceiling Rates for the following on-going services"));
            aPdfDoc.add(pdfHelper.getParagraph("- Information Security Incident Response Support Services"));
            aPdfDoc.add(Chunk.NEWLINE);
            
            // Row 1
            cell = pdfHelper.getHeaderCell("Non-Resident / Off-Shore");
            cell.setColspan(2); cell.setBorder(Rectangle.BOTTOM); cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Hourly Rate");
            cell.setColspan(CeilingRateCat4Info.NumOfOnGoingStaffCategory * 2); cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            // Row 2
            cell = pdfHelper.getHeaderCell("");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Standard Staff Category");
            cell.setColspan(numOfCol - 2); cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            // Row 3
            cell = pdfHelper.getHeaderCell("");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            for (int i = 1; i <= CeilingRateCat4Info.NumOfOnGoingStaffCategory; i++)
            {
                cell = pdfHelper.getHeaderCell("" + i);
                cell.setColspan(2); cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
            }
            
            // Row 4
            cell = pdfHelper.getHeaderCell("Office / Non-office Hour");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            for (int i = 1; i <= CeilingRateCat4Info.NumOfOnGoingStaffCategory; i++)
            {
                cell = pdfHelper.getHeaderCell("Office Hours");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Non-office Hours");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
            }
/*            
            // Row 5
            cell = pdfHelper.getHeaderCell("Minor Works Group");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getCell("");
            cell.setColspan(CeilingRateCat4Info.NumOfOnGoingStaffCategory * 2);
            aPdfTable.addCell(cell);
            
            // Row 6 (Data)
            for (int j = 0; j < allCrCat4Info.size(); j++)
            {
                aCRCat4Info = (CeilingRateCat4Info)allCrCat4Info.get(j);
                
                if (aCRCat4Info.getServiceGroup().equals(aCRCat4Info.MinorGroupInd))
                {
                    cell = pdfHelper.getCell(aCRCat4Info.getContractorID());
                    cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                    aPdfTable.addCell(cell);
                    
                    cell = pdfHelper.getCell(aCRCat4Info.getCurrency());
                    cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                    aPdfTable.addCell(cell);
                    
                    for (int k = 0; k < CeilingRateCat4Info.NumOfOnGoingStaffCategory; k++)
                    {
                        // Office hour
                        cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOnGoingStaffRate(k, 0)));
                        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        aPdfTable.addCell(cell);
                        
                        // Non-office hour
                        cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOnGoingStaffRate(k, 1)));
                        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        aPdfTable.addCell(cell);
                    }
                }
            }
            
            // Row 7
            cell = pdfHelper.getHeaderCell("");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("");
            cell.setColspan(CeilingRateCat4Info.NumOfOnGoingStaffCategory * 2);
            aPdfTable.addCell(cell);
            
            // Row 8
            cell = pdfHelper.getHeaderCell("Major Works Group");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getCell("");
            cell.setColspan(CeilingRateCat4Info.NumOfOnGoingStaffCategory * 2);
            aPdfTable.addCell(cell);
*/            
            // Row 9 (Data)
            for (int j = 0; j < allCrCat4Info.size(); j++)
            {
                aCRCat4Info = (CeilingRateCat4Info)allCrCat4Info.get(j);
                
                if (aCRCat4Info.getServiceGroup().equals(aCRCat4Info.MajorGroupInd))
                {
                    cell = pdfHelper.getCell(aCRCat4Info.getContractorID());
                    cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                    aPdfTable.addCell(cell);
                    
                    cell = pdfHelper.getCell(aCRCat4Info.getCurrency());
                    cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                    aPdfTable.addCell(cell);
                    
                    for (int k = 0; k < CeilingRateCat4Info.NumOfOnGoingStaffCategory; k++)
                    {
                        // Office hour
                        cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOnGoingStaffRate(k, 0)));
                        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        aPdfTable.addCell(cell);
                        
                        // Non-office hour
                        cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOnGoingStaffRate(k, 1)));
                        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        aPdfTable.addCell(cell);
                    }
                }
            }
            
            aPdfDoc.add(aPdfTable);
            aPdfDoc.newPage();
            
            
            /*****************************************************
             * On-going services table (24-hour Round-the-clock)
             *****************************************************/
            // Initialize PDF Table
            int tableColWidths3[] = {20, 4, 10, 10, 10, 10, 10}; // percentage
            numOfCol = 7;
            aPdfTable = new PdfPTable(numOfCol);
            aPdfTable.setWidths(tableColWidths3);
            aPdfTable.setWidthPercentage(70);
            aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            
            // Create Header
            aPdfDoc.add(pdfHelper.getTitleParagraph("Ceiling Rates for the following on-going services"));
            aPdfDoc.add(pdfHelper.getParagraph(" - IT Security Monitoring Services"));
            aPdfDoc.add(Chunk.NEWLINE);
            
            // Row 1
            cell = pdfHelper.getHeaderCell("Non-Resident / Off-Shore");
            cell.setColspan(2); cell.setBorder(Rectangle.BOTTOM); cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("24-Hour Round-the-clock Daily Rate");
            cell.setColspan(CeilingRateCat4Info.NumOfOnGoingStaffCategory); cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            // Row 2
            cell = pdfHelper.getHeaderCell("");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getCell("Standard Staff Category");
            cell.setColspan(CeilingRateCat4Info.NumOfOnGoingStaffCategory); cell.setHorizontalAlignment(cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            // Row 3
            cell = pdfHelper.getHeaderCell("");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            for (int i = 1; i <= CeilingRateCat4Info.NumOfOnGoingStaffCategory; i++)
            {
                cell = pdfHelper.getHeaderCell("" + i);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
            }
/*            
            // Row 4
            cell = pdfHelper.getHeaderCell("Minor Works Group");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("");
            cell.setColspan(CeilingRateCat4Info.NumOfOnGoingStaffCategory);
            aPdfTable.addCell(cell);
            
            // Row 5 (Data)
            for (int j = 0; j < allCrCat4Info.size(); j++)
            {
                aCRCat4Info = (CeilingRateCat4Info)allCrCat4Info.get(j);
                
                if (aCRCat4Info.getServiceGroup().equals(aCRCat4Info.MinorGroupInd))
                {
                    cell = pdfHelper.getCell(aCRCat4Info.getContractorID());
                    cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                    aPdfTable.addCell(cell);
                    
                    cell = pdfHelper.getCell(aCRCat4Info.getCurrency());
                    cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                    aPdfTable.addCell(cell);
                    
                    for (int k = 0; k < CeilingRateCat4Info.NumOfOnGoingStaffCategory; k++)
                    {
                        // 4-Hour Round-the-clock Daily Rate
                        cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOnGoingStaffRate(k, 2)));
                        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        aPdfTable.addCell(cell);
                    }
                }
            }
            
            // Row 6
            cell = pdfHelper.getHeaderCell("");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("");
            cell.setColspan(CeilingRateCat4Info.NumOfOnGoingStaffCategory);
            aPdfTable.addCell(cell);
            
            // Row 7
            cell = pdfHelper.getHeaderCell("Major Works Group");
            cell.setColspan(2);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("");
            cell.setColspan(CeilingRateCat4Info.NumOfOnGoingStaffCategory);
            aPdfTable.addCell(cell);
*/            
            // Row 8 (Data)
            for (int j = 0; j < allCrCat4Info.size(); j++)
            {
                aCRCat4Info = (CeilingRateCat4Info)allCrCat4Info.get(j);
                
                if (aCRCat4Info.getServiceGroup().equals(aCRCat4Info.MajorGroupInd))
                {
                    cell = pdfHelper.getCell(aCRCat4Info.getContractorID());
                    cell.setBorder(Rectangle.LEFT|Rectangle.TOP|Rectangle.BOTTOM);
                    aPdfTable.addCell(cell);
                    
                    cell = pdfHelper.getCell(aCRCat4Info.getCurrency());
                    cell.setBorder(Rectangle.RIGHT|Rectangle.TOP|Rectangle.BOTTOM);
                    aPdfTable.addCell(cell);
                    
                    for (int k = 0; k < CeilingRateCat4Info.NumOfOnGoingStaffCategory; k++)
                    {
                        // 4-Hour Round-the-clock Daily Rate
                        cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOnGoingStaffRate(k, 2)));
                        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        aPdfTable.addCell(cell);
                    }
                }
            }
            
            aPdfDoc.add(aPdfTable);
            
        }
        catch(Exception ex)
        {
        	logger.error(ex);
            throw new ServletException(ex.getMessage()); }
    }
    
    private void genRow1ToRow3Report(PdfPTable aPdfTable, PdfHelper pdfHelper, PdfPCell cell, int numOfCol, int i) throws DocumentException{
        aPdfTable.setWidthPercentage(100);
        aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
        
        // Row 1
        cell = pdfHelper.getHeaderCell(CeilingRateCat3Info.WorkingLocationName[i]);
        cell.setBorder(Rectangle.NO_BORDER); cell.setColspan(2); cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        aPdfTable.addCell(cell);
        
        cell = pdfHelper.getHeaderCell("Daily Rate");
        cell.setColspan(numOfCol - 2); cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        aPdfTable.addCell(cell);
        
        // Row 2
        cell = pdfHelper.getHeaderCell("");
        cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
        aPdfTable.addCell(cell);
        
        cell = pdfHelper.getHeaderCell("Standard Staff Category");
        cell.setColspan(numOfCol - 2); cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        aPdfTable.addCell(cell);
        
        // Row 3
        cell = pdfHelper.getCell("");
        cell.setColspan(2); cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT);
        aPdfTable.addCell(cell);
    }
    
}