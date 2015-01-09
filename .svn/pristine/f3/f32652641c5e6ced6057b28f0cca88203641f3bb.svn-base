package qpses.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.sql.rowset.serial.SerialException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;

import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import qpses.business.*;
import qpses.util.*;
import qpses.security.*;

public class ReportSLAdmin extends HttpServlet
{
    // Get class name
    static String MyClassName = ReportSLAdmin.class.getName();
    
    // Set up Log4J
    static Logger logger = Logger.getLogger(MyClassName);
    
    // User web page base URL
    String UserBaseUrl = "/qpsadmin/";
    
    public ReportDBAdmin getReportDBAdmin() throws SysException, SerialException, SQLException{
        return new ReportDBAdmin();
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    { doPost(request, response); }
    
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "doPost" + "]";
        logger.debug(myName + " " + "started");
        
        String requestAction = request.getServletPath();
        
        logger.debug(myName + " " + "Request Action = " + requestAction);
        
        try
        {
            if (requestAction.equals(this.UserBaseUrl + "ReportSLAdmin.getExcessWaAccess"))
            { this.getExcessWaAccess(request, response); }
            else if (requestAction.equals(this.UserBaseUrl + "ReportSLAdmin.getWaAccessMTOneUser"))
            { this.getWaAccessMTOneUser(request, response); }
            else if (requestAction.equals(this.UserBaseUrl + "ReportSLAdmin.getFunctionAccess"))
            { this.getFunctionAccess(request, response); }
            else
            { throw new SysException(myName, "[" + requestAction + "]" + " " + "is not allowed."); }
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "ended");
    }
    
    public void getFunctionAccess(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "getFunctionAccess" + "]";
        logger.debug(myName + " " + "started");
        
        try
        {
            // Get user information
            SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
            if (secCtx == null)
            {throw new SysException(myName, "Unable to get user context.");}
            String userId   = secCtx.getUserId();
            String userName = secCtx.getUserName();
            
            // Variable
            int numOfCol        = 0;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            CeilingRateCat1Info aCRCat1Info = null;
            ByteArrayOutputStream baos      = new ByteArrayOutputStream();
            
            // Initialize PDF helper
            PdfHelper pdfHelper = new PdfHelper();
            pdfHelper.setDisplayUserName(userId);
            pdfHelper.setHeaderText("RESTRICTED (CONTRACT)" + "\n" + "QPSES Function Access Log");
            pdfHelper.setFooterText("RESTRICTED (CONTRACT)");
            
            // Initialize PDF document
            Document aPdfDoc = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(aPdfDoc, baos);
            writer.setPageEvent(pdfHelper);
            aPdfDoc.setMargins(50, 50, 100, 100);
            aPdfDoc.open();
            
            // Set Title Paragraph as QPS3 enhancement
            Paragraph aParagraph = pdfHelper.getTitleParagraph("The Government of the Hong Kong Special Administrative Region");
            aParagraph.setAlignment(1);
            aPdfDoc.add(aParagraph);            
            aParagraph = pdfHelper.getTitleParagraph("Quality Professional Services Information System (SOA-QPS)");
            aParagraph.setAlignment(1);
            aPdfDoc.add(aParagraph);                                 
            aPdfDoc.add(Chunk.NEWLINE);
            
            // PDF table setting
            numOfCol = 4;
            aPdfTable = new PdfPTable(numOfCol);
            int tableColWidths[] = {25, 25, 25, 25}; // percentage
            aPdfTable.setWidths(tableColWidths);
            aPdfTable.setWidthPercentage(100);
            aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            
            // Row 1 (Table Header)
            cell = pdfHelper.getHeaderCell("Function");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Description");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("User ID");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Date/Time");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            aPdfTable.setHeaderRows(1);
            
            ReportDBAdmin rptDBAdmin = getReportDBAdmin();
            List allFuncAccess = rptDBAdmin.getFunctionAccess();
            
            // Data
            for (int i = 0; i < allFuncAccess.size(); i+=numOfCol)
            {
                cell = pdfHelper.getCell((String)allFuncAccess.get(i));
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell((String)allFuncAccess.get(i + 1));
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell((String)allFuncAccess.get(i + 2));
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                               
                cell = pdfHelper.getCell((String)allFuncAccess.get(i + 3));
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);                
            }
            
            if (allFuncAccess.size() == 0)
            {
                cell = pdfHelper.getCell("-- No Record --");
                cell.setColspan(numOfCol);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
            }
            
            // Add table to document
            aPdfDoc.add(aPdfTable);
            
            // Write PDF objects to stream
            aPdfDoc.close();
            
            // Response Setting
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=\"" + "QPSES Function Access Log (Restricted).pdf" + "\"");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentLength(baos.size());
            
            // Output PDF file
            ServletOutputStream sos = response.getOutputStream();
            baos.writeTo(sos);
            sos.flush();
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "ended");
    }
    
    public void getWaAccessMTOneUser(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "getWaAccessMTOneUser" + "]";
        logger.debug(myName + " " + "started");
        
        try
        {
            // Get user information
            SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
            if (secCtx == null)
            {throw new SysException(myName, "Unable to get user context.");}
            String userId   = secCtx.getUserId();
            String userName = secCtx.getUserName();
            
            // Variable
            int numOfCol        = 0;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            CeilingRateCat1Info aCRCat1Info = null;
            ByteArrayOutputStream baos      = new ByteArrayOutputStream();
            
            // Initialize PDF helper
            PdfHelper pdfHelper = new PdfHelper();
            pdfHelper.setDisplayUserName(userId);
            pdfHelper.setHeaderText("RESTRICTED (CONTRACT)" + "\n" + "Work assignment has been selected by more than one user");
            pdfHelper.setFooterText("RESTRICTED (CONTRACT)");
            
            // Initialize PDF document
            Document aPdfDoc = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(aPdfDoc, baos);
            writer.setPageEvent(pdfHelper);
            aPdfDoc.setMargins(50, 50, 100, 100);
            aPdfDoc.open();
            
            // Set Title Paragraph as QPS3 enhancement
            Paragraph aParagraph = pdfHelper.getTitleParagraph("The Government of the Hong Kong Special Administrative Region");
            aParagraph.setAlignment(1);
            aPdfDoc.add(aParagraph);            
            aParagraph = pdfHelper.getTitleParagraph("Quality Professional Services Information System (SOA-QPS)");
            aParagraph.setAlignment(1);
            aPdfDoc.add(aParagraph);                                 
            aPdfDoc.add(Chunk.NEWLINE);
            
            // PDF table setting
            numOfCol = 7;
            aPdfTable = new PdfPTable(numOfCol);
            int tableColWidths[] = {20, 25, 10, 15, 10, 10, 20}; // percentage
            aPdfTable.setWidths(tableColWidths);
            aPdfTable.setWidthPercentage(100);
            aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            
            // Row 1 (Table Header)
            cell = pdfHelper.getHeaderCell("Service Category" + "\n"+ "/Group");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);            
            
            cell = pdfHelper.getHeaderCell("Work Assignment Title");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Function");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Bureau" + "\n"+ "/Department");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Issue Date");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Closing Date");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("User ID");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            aPdfTable.setHeaderRows(1);
            
            ReportDBAdmin rptDBAdmin = getReportDBAdmin();
            List allExcessWaAccess = rptDBAdmin.getWaAccessMTOneUser();
            
            String lastFunction    = "";
            String lastWaTitle     = "";
            String lastSCG         = "";
            String lastBD          = "";
            String lastIssueDate   = "";
            String lastClosingDate = "";
            String userIDList      = "";
            
            // Data
            for (int i = 0; i < allExcessWaAccess.size(); i+=numOfCol)
            {
                String curSCG         = (String)allExcessWaAccess.get(i);
                String curWaTitle     = (String)allExcessWaAccess.get(i + 1);
                String curFunction    = (String)allExcessWaAccess.get(i + 2);
                String curBD          = (String)allExcessWaAccess.get(i + 3);
                String curIssueDate   = (String)allExcessWaAccess.get(i + 4);
                String curClosingDate = (String)allExcessWaAccess.get(i + 5);
                String curUserID      = (String)allExcessWaAccess.get(i + 6);
                
                if (curFunction.equals(lastFunction) && curWaTitle.equals(lastWaTitle) && curSCG.equals(lastSCG) &&
                        curBD.equals(lastBD) && curIssueDate.equals(lastIssueDate) && curClosingDate.equals(lastClosingDate) )
                {
                    userIDList += ", " + curUserID;
                }
                else
                {
                    if (!userIDList.equals(""))
                    {
                        cell = pdfHelper.getCell(userIDList);
                        aPdfTable.addCell(cell);
                        
                        userIDList = "";
                    }
                    
                    cell = pdfHelper.getCell(curSCG);
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);                    
                    aPdfTable.addCell(cell);
                    
                    cell = pdfHelper.getCell(curWaTitle);
                    aPdfTable.addCell(cell);
                    
                    cell = pdfHelper.getCell(curFunction);
                    aPdfTable.addCell(cell);
                    
                    cell = pdfHelper.getCell(curBD);
                    aPdfTable.addCell(cell);
                    
                    cell = pdfHelper.getCell(curIssueDate);
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                    
                    cell = pdfHelper.getCell(curClosingDate);
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                    
                    userIDList = curUserID;
                    
                    lastFunction    = curFunction;
                    lastWaTitle     = curWaTitle;
                    lastSCG         = curSCG;
                    lastBD          = curBD;
                    lastIssueDate   = curIssueDate;
                    lastClosingDate = curClosingDate;
                }
            }
            
            if (!userIDList.equals(""))
            {
                cell = pdfHelper.getCell(userIDList);
                aPdfTable.addCell(cell);
            }
            
            if (allExcessWaAccess.size() == 0)
            {
                cell = pdfHelper.getCell("-- No Record --");
                cell.setColspan(numOfCol);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
            }
            
            // Add table to document
            aPdfDoc.add(aPdfTable);
            
            // Write PDF objects to stream
            aPdfDoc.close();
            
            // Response Setting
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=\"" + "Work assignment has been selected by more than one user(Restricted).pdf" + "\"");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentLength(baos.size());
            
            // Output PDF file
            ServletOutputStream sos = response.getOutputStream();
            baos.writeTo(sos);
            sos.flush();
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "ended");
    }
    
    
    public void getExcessWaAccess(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "getExcessWaAccess" + "]";
        logger.debug(myName + " " + "started");
        
        try
        {
            // Get user information
            SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
            if (secCtx == null)
            {throw new SysException(myName, "Unable to get user context.");}
            String userId   = secCtx.getUserId();
            String userName = secCtx.getUserName();
            
            // Variable
            int numOfCol        = 0;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            CeilingRateCat1Info aCRCat1Info = null;
            ByteArrayOutputStream baos      = new ByteArrayOutputStream();
            
            // Initialize PDF helper
            PdfHelper pdfHelper = new PdfHelper();
            pdfHelper.setDisplayUserName(userId);
            pdfHelper.setHeaderText("RESTRICTED (CONTRACT)" + "\n" + "Users that have excessive access to work assignment");
            pdfHelper.setFooterText("RESTRICTED (CONTRACT)");
            
            // Initialize PDF document
            Document aPdfDoc = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(aPdfDoc, baos);
            writer.setPageEvent(pdfHelper);
            aPdfDoc.setMargins(50, 50, 100, 100);
            aPdfDoc.open();
            
            // Set Title Paragraph as QPS3 enhancement
            Paragraph aParagraph = pdfHelper.getTitleParagraph("The Government of the Hong Kong Special Administrative Region");
            aParagraph.setAlignment(1);
            aPdfDoc.add(aParagraph);            
            aParagraph = pdfHelper.getTitleParagraph("Quality Professional Services Information System (SOA-QPS)");
            aParagraph.setAlignment(1);
            aPdfDoc.add(aParagraph);                                 
            aPdfDoc.add(Chunk.NEWLINE);
            
            // PDF table setting
            numOfCol = 8;
            aPdfTable = new PdfPTable(numOfCol);
            int tableColWidths[] = {15, 20, 10, 15, 10, 10, 10, 10}; // percentage
            aPdfTable.setWidths(tableColWidths);
            aPdfTable.setWidthPercentage(100);
            aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            
            // Row 1 (Table Header)
            cell = pdfHelper.getHeaderCell("Service Category" + "\n"+ "/Group");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);            
            
            cell = pdfHelper.getHeaderCell("Work Assignment Title");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Function");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Bureau" + "\n"+ "/Department");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Issue Date");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Closing Date");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("User ID");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Number of Accesses");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            aPdfTable.setHeaderRows(1);
            
            ReportDBAdmin rptDBAdmin = getReportDBAdmin();
            List allExcessWaAccess = rptDBAdmin.getExcessWaAccess();
            
            // Data
            for (int i = 0; i < allExcessWaAccess.size(); i+=numOfCol)
            {
                cell = pdfHelper.getCell((String)allExcessWaAccess.get(i));
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell((String)allExcessWaAccess.get(i + 1));
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell((String)allExcessWaAccess.get(i + 2));
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell((String)allExcessWaAccess.get(i + 3));
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell((String)allExcessWaAccess.get(i + 4));
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell((String)allExcessWaAccess.get(i + 5));
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell((String)allExcessWaAccess.get(i + 6));
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell((String)allExcessWaAccess.get(i + 7));
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
            }
            
            
            if (allExcessWaAccess.size() == 0)
            {
                cell = pdfHelper.getCell("-- No Record --");
                cell.setColspan(numOfCol);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
            }
            
            // Add table to document
            aPdfDoc.add(aPdfTable);
            
            // Write PDF objects to stream
            aPdfDoc.close();
            
            // Response Setting
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=\"" + "Users that have excessive access to work assignment (Restricted).pdf" + "\"");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentLength(baos.size());
            
            // Output PDF file
            ServletOutputStream sos = response.getOutputStream();
            baos.writeTo(sos);
            sos.flush();
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "ended");
    }
}
