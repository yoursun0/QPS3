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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jpedal.PdfDecoder;

import qpses.business.*;
import qpses.util.*;
import qpses.security.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class QualitySubscoreSLUser extends HttpServlet
{
    // Get class name
    static String MyClassName = QualitySubscoreSLUser.class.getName();
    
    // Set up Log4J
    static Logger logger = Logger.getLogger(MyClassName);
    
    // User web page base URL
    String UserBaseUrl = "/qpsuser/";
    
    public QualitySubscoreDBUser getQualitySubscoreDBUser() throws SysException, SerialException, SQLException{
    	return new QualitySubscoreDBUser();
    }
    
    public DeptDataBean getDeptDataBean() throws SysException{
    	return new DeptDataBean();
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    { doPost(request, response); }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "doPost" + "]";
        logger.debug(myName + " " + "started");
        
        String requestAction = "";
        try
        {
            requestAction = request.getParameter("request_action");
            if (requestAction == null) requestAction = "";
            
            logger.debug(myName + " " + "Request Action = " + requestAction);
            
            if (requestAction.equals(""))
            { this.getWA(request, response); }
            else if (requestAction.equals("waChallenge"))
            { this.waChallenge(request, response); }
            else if (requestAction.equals("showImagePage"))
            { this.showImagePage(request, response); }
            else if (requestAction.equals("getImage"))
            { this.getFile(request, response, "IMAGE"); }
            else if (requestAction.equals("getPDF"))
            { this.getFile(request, response, "PDF"); }
            else
            { throw new SysException(myName, "[" + requestAction + "]" + " " + "is not allowed."); }
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "ended");
    }
    
    private void getWA(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "getWA" + "]";
        logger.debug(myName + " " + "started");
        
        String url = this.UserBaseUrl + "CPSGetWA.jsp";
        try
        {
            request.getSession().removeAttribute("QPSES_WA_CHALLENGE");
            request.getSession().removeAttribute("WA_CHALLENGE_MSG");
            request.getSession().removeAttribute("WA_TYPE");            
            
            // Get user information
            SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
            if (secCtx == null)
            {throw new SysException(myName, "Unable to get user information.");}
            String userDPDeptId  = secCtx.getUserDPDeptId();
            String userSOADeptId = secCtx.getUserSOADeptId();
            
            DeptDataBean aDeptDB = getDeptDataBean();
            DeptInfo aDeptInfo   = aDeptDB.selectDeptByKeys(userDPDeptId, userSOADeptId);
            
            if (aDeptInfo == null) throw new Exception("Cannot find department name.");
            
            String deptName = aDeptInfo.getDeptName();
            request.getSession().setAttribute("SRV_DEPT_NAME", deptName);
            
            QualitySubscoreDBUser aQSDB = getQualitySubscoreDBUser();
            List allWAInfo = aQSDB.getWA(userDPDeptId);
            
            request.getSession().setAttribute("allWAInfo", allWAInfo);
            
            request.getSession().setAttribute("WA_TYPE", "quality_subscore");            
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "Forward to URL: " + url);
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        
        logger.debug(myName + " " + "ended");
    }
    
    private void waChallenge(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "waChallenge" + "]";
        logger.debug(myName + " " + "started");
        
        String url = this.UserBaseUrl + "CPSWAChallenge.jsp";
        
        logger.debug(myName + " " + "Forward to URL: " + url);
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        
        logger.debug(myName + " " + "ended");
    }
    
    
    private void createPdfBytesInSession(HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        String myName = "[" + MyClassName + "." + "createPdfBytesInSession" + "]";
        logger.debug(myName + " " + "started");
        
        try
        {
          // Get user information
            SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
            if (secCtx == null)
            {throw new SysException(myName, "Cannot get user information.");}
            String userId   = secCtx.getUserId();
            String dpDeptId  = secCtx.getDPDeptId();
            String soaDeptId = secCtx.getSOADeptId();
            
            WAChallengeInfo wac =(WAChallengeInfo)(request.getSession().getAttribute("QPSES_WA_CHALLENGE"));
            
            if (wac == null)
            {throw new SysException(myName, "Unable to get work assignment challenge session.");}
            
            String scg           = wac.getServiceCategoryGroup();
            String closingDate   = SysManager.getStringfromSQLDate(wac.getClosingDate());
            String effectiveDate = null;
            String deptId        = wac.getDepartmentId();
            String waFilePart    = wac.getFilePartNo();
            String waFileNo      = wac.getFileNo() + "";
            String waTitle       = wac.getTitle();
            
            String sc     = scg.substring(0, 1);
            
            String scgStr = "";
            if (sc.equals("1") || sc.equals("4"))
            {
                scgStr = "Service Category " + sc;
            }
            else
            {
                String sg     = scg.substring(2, 3);
                scgStr = "Service Category " + sc + " " + (sg.equals("J") ? "Major" : "Minor") + " Group";
            } 
            //String scgStr = "Service Category " + sc + " " + (sg.equals("J") ? "Major" : "Minor") + " Group";
            
            // Get Contractor Performance Score from DB
            QualitySubscoreInfo aQSInfo = null;
            List qualitySubscores     = new ArrayList();
            QualitySubscoreDBUser aQSDB = getQualitySubscoreDBUser();
            qualitySubscores            = aQSDB.getQualitySubscore(closingDate, scg, deptId, waFilePart, waFileNo, userId, dpDeptId, soaDeptId);
            
            if (qualitySubscores.size() != 0)
            {
                aQSInfo       = (QualitySubscoreInfo)qualitySubscores.get(0);
                effectiveDate = SysManager.getStringfromSQLDate(aQSInfo.getEffectiveStartDate());
            }
            
            // Get debarred contractor names
            QualitySubscoreDBUser aQSDB2 = getQualitySubscoreDBUser();
            String dContractorNameList = aQSDB2.getDContractorNameList(scg, deptId, waFilePart, waFileNo);
            
            int numOfCol;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            
            // Initialize PDF helper
            PdfHelper pdfHelper = new PdfHelper();
            pdfHelper.setDisplayUserName(userId);
            pdfHelper.setHeaderText("RESTRICTED (CONTRACT)" + "\n" + "Contractor Performance Score");
            pdfHelper.setFooterText("RESTRICTED (CONTRACT)");
            
            // Initialize PDF document
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document aPdfDoc = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(aPdfDoc, baos);
            
            writer.setPageEvent(pdfHelper);
            aPdfDoc.setMargins(50, 50, 100, 100);
            aPdfDoc.open();
            
         // Set Title Paragraph as QPS3 enhancement
            Paragraph aParagraph = pdfHelper.getTitleParagraph("Standing Offer Agreements for");
            aParagraph.setAlignment(1);
            aPdfDoc.add(aParagraph);      
            aParagraph = pdfHelper.getTitleParagraph("Quality Professional Services 3 (SOA-QPS3)");
            aParagraph.setAlignment(1);
            aPdfDoc.add(aParagraph); 
                             
            aPdfDoc.add(Chunk.NEWLINE);
            
            // Create Header
            numOfCol = 3;
            aPdfTable = new PdfPTable(numOfCol);
            int tableColWidthsHD[] = {37, 2, 61}; // percentage
            aPdfTable.setWidths(tableColWidthsHD);
            aPdfTable.setWidthPercentage(100);
            aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            
            cell = pdfHelper.getHeaderCell("Service Category/Group"); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            cell = pdfHelper.getHeaderCell(":"); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            cell = pdfHelper.getCell(scgStr); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Work Assignment Title"); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            cell = pdfHelper.getHeaderCell(":"); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            cell = pdfHelper.getCell(waTitle); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Proposal Submission Closing Date"); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            cell = pdfHelper.getHeaderCell(":"); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            cell = pdfHelper.getCell(closingDate); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Debarred Contractor(s)"); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            cell = pdfHelper.getHeaderCell(":"); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            cell = pdfHelper.getCell(dContractorNameList.equals("")? "N/A" : dContractorNameList); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            
            // Blank row
            cell = pdfHelper.getHeaderCell("");
            cell.setBorder(Cell.NO_BORDER);
            cell.setColspan(numOfCol);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Contractor Performance Scores effective as at the closing date for proposal submission are as follows.");
            cell.setBorder(Cell.NO_BORDER);
            cell.setColspan(numOfCol);
            aPdfTable.addCell(cell);
            
            aPdfTable.setSpacingAfter(10f);
            aPdfDoc.add(aPdfTable);
            
            
            // Set up table
            numOfCol = 2;
            aPdfTable            = new PdfPTable(numOfCol);
            int tableColWidths[] = {50, 50};
            aPdfTable.setWidths(tableColWidths);
            aPdfTable.setWidthPercentage(100);
            aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            
            // Row 1
            cell = pdfHelper.getHeaderCell("Contractor Name");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Contractor Performance Score");
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            aPdfTable.addCell(cell);
            
            if (qualitySubscores.size() == 0)
            {
                cell = pdfHelper.getCell("-- No Contractor Performance Score is available --");
                cell.setColspan(2);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
            }
            
            for (int i = 0; i < qualitySubscores.size(); i++)
            {
                // Data row
                aQSInfo = (QualitySubscoreInfo)qualitySubscores.get(i);
                
                cell = pdfHelper.getCell(aQSInfo.getContractorName());
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell("" + aQSInfo.getScore());
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
            }
            
            aPdfDoc.add(aPdfTable);
            
            // Write PDF objects to stream
            aPdfDoc.close();
            
            byte[] fileBytes = baos.toByteArray();
            
            PdfDecoder decoder = new PdfDecoder();
            decoder.openPdfArray(fileBytes);
            int noOfPage = decoder.getPageCount();
            
            String fileName = "(RESTRICTED) SOA-QPS Contractor Performance Score - " + scgStr + ".pdf";
            
            // Renew number of page in session
            request.getSession().removeAttribute("QPSES_QS_RPT_NO_OF_PAGE");
            request.getSession().setAttribute("QPSES_QS_RPT_NO_OF_PAGE", noOfPage + "");
            
            // Renew file bytes in session
            request.getSession().removeAttribute("QPSES_QS_RPT_FILE_BYTES");
            request.getSession().setAttribute("QPSES_QS_RPT_FILE_BYTES", fileBytes);
            
            // Renew file bytes in session
            request.getSession().removeAttribute("QPSES_QS_RPT_FILE_NAME");
            request.getSession().setAttribute("QPSES_QS_RPT_FILE_NAME", fileName);
        }
        catch(Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "ended");
    }
    
    
    private void showImagePage(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "showImagePage" + "]";
        logger.debug(myName + " " + "started");
        
        try
        {           
            this.createPdfBytesInSession(request, response);
            
            String url = this.UserBaseUrl + "CPSShowImage.jsp";           
            logger.debug(myName + " " + "Forward to URL: " + url);
            
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
            requestDispatcher.forward(request, response);
        }
        catch(Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "ended");
    }
    
    
    private void getFile(HttpServletRequest request, HttpServletResponse response, String outFormat)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "getFile" + "]";
        logger.debug(myName + " " + "started");
                
        try
        {
            assert outFormat.equals("IMAGE") || outFormat.equals("PDF");
                    
            if (request.getSession().getAttribute("QPSES_QS_RPT_FILE_BYTES") == null)
                throw new SysException(myName, "Cannot find file bytes.");
            
            if (request.getSession().getAttribute("QPSES_QS_RPT_FILE_NAME") == null)
                throw new SysException(myName, "Cannot find file name.");
            
            byte[] fileBytes = ( byte[])(request.getSession().getAttribute("QPSES_QS_RPT_FILE_BYTES"));
            String fileName  = (String)(request.getSession().getAttribute("QPSES_QS_RPT_FILE_NAME"));

            // Output PDF file
            if (outFormat.equals("PDF"))
            {
                // Response Setting
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setContentLength(fileBytes.length);
                
                ServletOutputStream sos = response.getOutputStream();
                sos.write(fileBytes);
                sos.flush();
            }
            else if (outFormat.equals("IMAGE"))
            { 
                String pageNo = request.getParameter("page_no");
                
                if (pageNo == null) throw new SysException(myName, "Page number is NULL");
                
                SysManager.pdfToImage(fileBytes, response, fileName.replaceAll(".pdf", ".jpg"), Integer.parseInt(pageNo));
            }
        }
        catch(Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "ended");
    }
}
