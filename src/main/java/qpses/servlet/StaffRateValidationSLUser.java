package qpses.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import qpses.business.*;
import qpses.util.*;
import qpses.security.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.Color;
import java.sql.Blob;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;

public class StaffRateValidationSLUser extends HttpServlet
{
    // Get class name
    static String MyClassName = StaffRateValidationSLUser.class.getName();
    
    // Set up Log4J
    static Logger logger = Logger.getLogger(MyClassName);
    
    // User web page base URL
    static String UserBaseUrl = "/qpsuser/";
    
    // Define backgorund color for ceiling rate row
    static private Color ExceedBGColor = new Color(0xFF, 0x00, 0x33);
    static private Color NoOfferBGColor = new Color(0xFF, 0xFF, 0x33);
    
    public DeptDataBean getDeptDataBean() throws SysException{
    	return new DeptDataBean();
    }
    
    public ContractorDataBean getContractorDataBean() throws SysException{
    	return new ContractorDataBean();
    }
    
    public StaffRateValidationDBUser getStaffRateValidationDBUser() throws SysException{
    	return new StaffRateValidationDBUser();
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
        String servletPath   = "";
        
        try
        {
            requestAction = request.getParameter("request_action");
            servletPath   = request.getServletPath();
            
            if (requestAction == null){ requestAction = "" ; }
            
            logger.debug(myName + " " + "Request Action = " + requestAction);
            
/*            if (servletPath.indexOf("StaffRateValidationCRSLUser") >= 0)
            { this.getCRWA(request, response); }
            else */if (requestAction.equals(""))
            { this.getWA(request, response); }
            else if (requestAction.equals("waChallenge"))
            { this.waChallenge(request, response); }
            else if (requestAction.equals("getInfoPdf"))
            { this.getInfoPdf(request, response); }            
            else if ((servletPath.indexOf("StaffRateValidationSLUserWAC") >= 0 || servletPath.indexOf("StaffRateValidationCRSLUserWAC") >= 0) && requestAction.equals("getContractor"))
            { this.getContractor(request, response); }
            else if ((servletPath.indexOf("StaffRateValidationSLUserWAC") >= 0 || servletPath.indexOf("StaffRateValidationCRSLUserWAC") >= 0) && requestAction.equals("getStaffRates"))
            { this.getStaffRates(request, response); }
            else if ((servletPath.indexOf("StaffRateValidationSLUserWAC") >= 0 || servletPath.indexOf("StaffRateValidationCRSLUserWAC") >= 0) && requestAction.equals("genCat1ValidationReport"))
            { this.genCat1ValidationReport(request, response); }
            else if ((servletPath.indexOf("StaffRateValidationSLUserWAC") >= 0 || servletPath.indexOf("StaffRateValidationCRSLUserWAC") >= 0) && requestAction.equals("genCat2ValidationReport"))
            { this.genCat2ValidationReport(request, response); }
            else if ((servletPath.indexOf("StaffRateValidationSLUserWAC") >= 0 || servletPath.indexOf("StaffRateValidationCRSLUserWAC") >= 0) && requestAction.equals("genCat3ValidationReport"))
            { this.genCat3ValidationReport(request, response); }
            else if ((servletPath.indexOf("StaffRateValidationSLUserWAC") >= 0 || servletPath.indexOf("StaffRateValidationCRSLUserWAC") >= 0) && requestAction.equals("genCat4ValidationReport"))
            { this.genCat4ValidationReport(request, response); }
            else if ((servletPath.indexOf("StaffRateValidationSLUserWAC") >= 0 || servletPath.indexOf("StaffRateValidationCRSLUserWAC") >= 0) && requestAction.equals("getPDF"))
            { this.getFile(request, response, "PDF"); }
            else if ((servletPath.indexOf("StaffRateValidationSLUserWAC") >= 0 || servletPath.indexOf("StaffRateValidationCRSLUserWAC") >= 0) && requestAction.equals("getImage"))
            { this.getFile(request, response, "IMAGE"); }
            else
            { throw new SysException(myName, "[" + requestAction + "]" + " " + "is not allowed."); }
        }
        catch(Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "ended");
    }
    
    private void getInfoPdf(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "getInfoPdf" + "]";
        logger.debug(myName + " " + "started");
        
        String url = this.UserBaseUrl + "StaffCategoriesExplanatoryNote.pdf";
        
        logger.debug(myName + " " + "Forward to URL: " + url);
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + "Explanatory Notes on Staff Categories.pdf" + "\"");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        
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
            
            if (request.getSession().getAttribute("QPSES_SRV_RPT_FILE_BYTES") == null){
                throw new SysException(myName, "Cannot find file bytes.");
            }
            if (request.getSession().getAttribute("QPSES_SRV_RPT_FILE_NAME") == null){
                throw new SysException(myName, "Cannot find file name.");
            }
            byte[] fileBytes = ( byte[])(request.getSession().getAttribute("QPSES_SRV_RPT_FILE_BYTES"));
            String fileName  = (String)(request.getSession().getAttribute("QPSES_SRV_RPT_FILE_NAME"));
            
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
                
                // Output PDF file
                ServletOutputStream sos = response.getOutputStream();
                sos.write(fileBytes);
                sos.flush();
            }
            else
            {
                String pageNo = request.getParameter("page_no");
                
                if (pageNo == null){ throw new SysException(myName, "Page number is NULL"); }
                
                SysManager.pdfToImage(fileBytes, response, fileName.replaceAll(".pdf", ".jpg"), Integer.parseInt(pageNo));
            }
        }
        catch(Exception ex)
        { throw new ServletException(ex.getMessage()); }
        
        logger.debug(myName + " " + "ended");
    }
    
    private void getWA(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "getWA" + "]";
        logger.debug(myName + " " + "started");
        
        String url = this.UserBaseUrl + "StaffRateValidationGetWA.jsp";
        
        try
        {
            request.getSession().removeAttribute("QPSES_WA_CHALLENGE");
            request.getSession().removeAttribute("WA_CHALLENGE_MSG");
            request.getSession().removeAttribute("WA_TYPE");
            
            // Get user information
            SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
            
            if (secCtx == null)
            {throw new SysException(myName, "Unable to get user context.");}
            String userDPDeptId  = secCtx.getUserDPDeptId();
            String userSOADeptId = secCtx.getUserSOADeptId();
            
            DeptDataBean aDeptDB = getDeptDataBean();
            DeptInfo aDeptInfo   = aDeptDB.selectDeptByKeys(userDPDeptId, userSOADeptId);
            
            if (aDeptInfo == null){ throw new Exception("Cannot find department name."); }
            
            String deptName = aDeptInfo.getDeptName();
            request.getSession().setAttribute("SRV_DEPT_NAME", deptName);
            
            StaffRateValidationDBUser aSRVDBUser = getStaffRateValidationDBUser();
            List<WorkAssignmentInfo> allWAInfo = aSRVDBUser.getWA(userSOADeptId);
            
            request.getSession().setAttribute("allWAInfo", allWAInfo);
            
            String servletPath   = request.getServletPath();
            
            if (servletPath.indexOf("StaffRateValidationCRSLUser") >= 0)
                request.getSession().setAttribute("WA_TYPE", "change_request");
            else
                request.getSession().setAttribute("WA_TYPE", "proposal");
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
        
        String url = this.UserBaseUrl + "StaffRateValidationWAChallenge.jsp";
        
        logger.debug(myName + " " + "Forward to URL: " + url);
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        
        logger.debug(myName + " " + "ended");
    }
       
    private void getContractor(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "getContractor" + "]";
        logger.debug(myName + " " + "started");
        
        String url = this.UserBaseUrl + "StaffRateValidationGetContractor.jsp";
        
        try
        {
            WAChallengeInfo wac =(WAChallengeInfo)(request.getSession().getAttribute("QPSES_WA_CHALLENGE"));
            
            if (wac == null)
            {throw new SysException(myName, "Unable to get work assignment challenge session.");}
            
            String scg = wac.getServiceCategoryGroup();
            if (scg == null){ throw new SysException(myName, "Parameter [scg] is NULL"); }
            
            ContractorDataBean contractorDB = getContractorDataBean();
            List<ContractorInfo> allContractorInfo = contractorDB.selectContractorByCatgp(scg);

            request.getSession().setAttribute("allContractorInfo", allContractorInfo);
            
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage());}
        
        logger.debug(myName + " " + "Forward to URL: " + url);
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        
        logger.debug(myName + " " + "ended");
    }
    
    private void getStaffRates(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "getStaffRates" + "]";
        logger.debug(myName + " " + "started");
        
        String url = this.UserBaseUrl + "StaffRateValidationGetStaffRates.jsp";
        
        try
        {
            String scg              = request.getParameter("scg");
            String closingDate      = request.getParameter("closing_date");
            String contractorIDList = request.getParameter("contractor_id_list");
            
            StaffRateValidationDBUser aSRVDBUser = getStaffRateValidationDBUser();
            
            if (scg == null){ throw new SysException(myName, "scg is NULL"); }
            if (contractorIDList == null){ throw new SysException(myName, "contractor_id_list is NULL"); }
            
            //if (scg.equals("1/N") || scg.equals("1/J"))
            if (scg.equals("1"))
            {
                List<CeilingRateCat1Info> allCeilingRateInfo = aSRVDBUser.getCeilingRateCat1ByList(contractorIDList, closingDate);
                request.getSession().setAttribute("allCeilingRateInfo", allCeilingRateInfo);
                url = this.UserBaseUrl + "StaffRateValidationGetStaffRates1.jsp";
            }
            else if (scg.equals("2/N") || scg.equals("2/J"))
            {
                List<CeilingRateCat2Info>  allCeilingRateInfo = aSRVDBUser.getCeilingRateCat2ByList(contractorIDList, closingDate);
                request.getSession().setAttribute("allCeilingRateInfo", allCeilingRateInfo);
                url = this.UserBaseUrl + "StaffRateValidationGetStaffRates2.jsp";
            }
            else if (scg.equals("3/N") || scg.equals("3/J"))
            {
                List<CeilingRateCat3Info>  allCeilingRateInfo = aSRVDBUser.getCeilingRateCat3ByList(contractorIDList, closingDate);
                request.getSession().setAttribute("allCeilingRateInfo", allCeilingRateInfo);
                url = this.UserBaseUrl + "StaffRateValidationGetStaffRates3.jsp";
            }
            else if (scg.equals("4"))
            //else if (scg.equals("4/N") || scg.equals("4/J"))
            {
                List<CeilingRateCat4Info>  allCeilingRateInfo = aSRVDBUser.getCeilingRateCat4ByList(contractorIDList, closingDate);
                request.getSession().setAttribute("allCeilingRateInfo", allCeilingRateInfo);
                url = this.UserBaseUrl + "StaffRateValidationGetStaffRates4.jsp";
            }
            else
            { throw new SysException(myName, "Unknown service category group [" + scg + "]"); }
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage());}
        
        logger.debug(myName + " " + "Forward to URL: " + url);
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        
        logger.debug(myName + " " + "ended");
    }
    
    private void getQSPDFTable(String contractorIDList, String dContractorList, String closingDate, String scg, String waTitle, Document aPdfDoc) throws ServletException
    {
        String myName = "[" + MyClassName + "." + "getQSPDFTable" + "]";
        logger.debug(myName + " " + "started");
        
        try
        {
            String effectiveDate = null;
            
            String sc     = scg.substring(0, 1);            
            String scgStr = "";
            String sg     = "";
            
            if (sc.equals("1") || sc.equals("4"))
            {
                scgStr = "Service Category " + sc;
                sg     = "";
            }
            else
            {
                sg     = scg.substring(2, 3);
                scgStr = "Service Category " + sc + " " + (sg.equals("J") ? "Major" : "Minor") + " Group";
                //sg     = scg.substring(2, 3);
            }
            StaffRateValidationDBUser aSRVDBUser = getStaffRateValidationDBUser();
            List<QualitySubscoreInfo> qualitySubscores = aSRVDBUser.getQualitySubscoreByList(contractorIDList, closingDate, scg);
            
            QualitySubscoreInfo aQSInfo = null;
            
            if (qualitySubscores.size() != 0)
            {
                aQSInfo       = (QualitySubscoreInfo)qualitySubscores.get(0);
                effectiveDate = SysManager.getStringfromSQLDate(aQSInfo.getEffectiveStartDate());
            }
            
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            PdfHelper pdfHelper = new PdfHelper();
            
            // Create Header
            int numOfCol = 3;
            aPdfTable = new PdfPTable(3);
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
            cell = pdfHelper.getCell(dContractorList.equals("")? "N/A" : dContractorList); cell.setBorder(Cell.NO_BORDER);
            aPdfTable.addCell(cell);
            
            // Blank row
            cell = pdfHelper.getHeaderCell("");
            cell.setBorder(Cell.NO_BORDER);
            cell.setColspan(numOfCol);
            aPdfTable.addCell(cell);
            
            cell = pdfHelper.getHeaderCell("Contractor Performance Scores effective as at the closing date for proposal submission are printed as follows.");
            cell.setBorder(Cell.NO_BORDER);
            cell.setColspan(numOfCol);
            aPdfTable.addCell(cell);
            
            aPdfTable.setSpacingAfter(10f);
            aPdfDoc.add(aPdfTable);
            
            
            // Set up data table
            numOfCol = 2;
            aPdfTable  = new PdfPTable(numOfCol);
            int tableColWidths[] = {50, 50}; // percentage
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
            
            aPdfTable.setHeaderRows(1);
            
            int recordCount = 0;
            
            for (int i = 0; i < qualitySubscores.size(); i++)
            {
                recordCount++;
                
                // Data row
                aQSInfo = (QualitySubscoreInfo)qualitySubscores.get(i);
                
                cell = pdfHelper.getCell(aQSInfo.getContractorName());
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getCell("" + aQSInfo.getScore());
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
            }
            
            if (recordCount == 0)
            {
                cell = pdfHelper.getCell("-- No Contractor Performance Score is available --");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setColspan(numOfCol);
                aPdfTable.addCell(cell);
            }
            
            aPdfDoc.add(aPdfTable);
            
            logger.debug(myName + " " + "ended");
            
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage());}
    }
     
    private void genCat1ValidationReport(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "getCat1ValidationReport" + "]";
        logger.debug(myName + " " + "started");
        
        try
        {
            // Get user information
            SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
            if (secCtx == null)
            {throw new SysException(myName, "Cannot get user information.");}
            String userId    = secCtx.getUserId();
            String dpDeptId  = secCtx.getDPDeptId();
            String soaDeptId = secCtx.getSOADeptId();
            
            // Get Work Assignment Type
            String waType = (String)request.getSession().getAttribute("WA_TYPE");
            
            if (waType == null)
            { throw new Exception("Session [WAType] is NULL"); }
            
            String waTypeStr = (waType.equals("proposal") ? "Proposal Evaluation" : "Change Request");            
            
            // Get paramater values
            String scg               = request.getParameter("scg");
            String closingDate       = request.getParameter("closing_date");
            String contractorIDList  = request.getParameter("contractor_id_list");
            String dContractorIDList = request.getParameter("d_contractor_id_list");
            String contractorList    = request.getParameter("contractor_list");
            String dContractorList   = request.getParameter("d_contractor_list");
            String deptID            = request.getParameter("dept_id");
            String deptName          = request.getParameter("dept_name");
            String waTitle           = request.getParameter("wa_title");
            String waFilePart        = request.getParameter("wa_file_part");
            String waFileNo          = request.getParameter("wa_file_no");
            String showSubscoreRpt   = request.getParameter("show_subscore_rpt");
            String mScheme           = request.getParameter("m_scheme");
            String sc                = request.getParameter("sc");
            String sg                = request.getParameter("sg");
            
            validateReportContext(myName, scg, closingDate, contractorList, contractorIDList, dContractorList, dContractorIDList, deptID, deptName, waTitle, waFilePart, waFileNo, showSubscoreRpt, mScheme, sc, sg);
            
            // Split the contractor list into array
            String[] contractorID = contractorIDList.split(",");
            
            // Create service category string
            //String scgStr = "Service Category " + sc + " " + (sg.equals("J") ? "Major" : "Minor") + " Group";
            String scgStr = "Service Category " + sc;
            
            // Get input staff rates and construct ceiling rate objects
            List<CeilingRateCat1Info> allCrCat1InfoWeb = new ArrayList<CeilingRateCat1Info>();
            
            for (int i = 0; i < contractorID.length; i++)
            {
                String aContractorID = contractorID[i].replaceAll("'", "");
                CeilingRateCat1Info aCRCat1Info = new CeilingRateCat1Info();
                
                aCRCat1Info.setContractorID(aContractorID);
                aCRCat1Info.setServiceGroup(sg);
                
                // Standard Staff Rate
                for (int j = 0; j < CeilingRateCat1Info.NumOfWorkingLocation; j++)
                {
                    for (int k = 0; k < CeilingRateCat1Info.NumOfStaffCategory; k++)
                    {
                        String paramName = "TxtSR__" + aContractorID + "__" + aCRCat1Info.toStdStaffRateTableField(k, j);
                        String paramValue = request.getParameter(paramName);
                        
                        if (paramValue == null || paramValue.equals("") || paramValue.toLowerCase().equals("no offer")){
                            paramValue = "" + CeilingRateCat1Info.NoOfferWebNumber;
                        }
                        aCRCat1Info.setStdStaffRate(k, j, Double.valueOf(paramValue).doubleValue());
                    }
                }
                
                // Supplementary Staff Rate
                for (int j = 0; j < CeilingRateCat1Info.NumOfWorkingLocation; j++)
                {
                    String paramName = "TxtSR__" + aContractorID + "__" + aCRCat1Info.toSupStaffRateTableField(j);
                    String paramValue = request.getParameter(paramName);
                    
                    if (paramValue == null || paramValue.equals("") || paramValue.toLowerCase().equals("no offer")){
                        paramValue = "" + CeilingRateCat1Info.NoOfferWebNumber;
                    }
                    aCRCat1Info.setSupStaffRate(j, Double.valueOf(paramValue).doubleValue());
                }
                
                allCrCat1InfoWeb.add(aCRCat1Info);
            }
            
            // Get contractor information for comparison
            StaffRateValidationDBUser aSRVDBUser = getStaffRateValidationDBUser();
            List<CeilingRateCat1Info> allCrCat1InfoDB = aSRVDBUser.getCeilingRateCat1ByList(contractorIDList, closingDate);
            
            // Validate ceiling rate
            for (int i = 0; i < allCrCat1InfoDB.size(); i++)
            {
                for (int j = 0; j < allCrCat1InfoWeb.size(); j++)
                {
                    CeilingRateCat1Info aCRCat1InfoDB  = (CeilingRateCat1Info)allCrCat1InfoDB.get(i);
                    CeilingRateCat1Info aCRCat1InfoWeb = (CeilingRateCat1Info)allCrCat1InfoWeb.get(j);
                    
                    if ((aCRCat1InfoDB.getContractorID()).equals(aCRCat1InfoWeb.getContractorID()))
                    {
                        // Copy other information
                        aCRCat1InfoWeb.setContractorName(aCRCat1InfoDB.getContractorName());
                        aCRCat1InfoWeb.setCurrency(aCRCat1InfoDB.getCurrency());
                        
                        // Check ceiling rates
                        aCRCat1InfoWeb.CheckCeilingRate(aCRCat1InfoDB);
                        break;
                    }
                }
            }
            
    		// Variable
            int numOfCol		= 3;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            int recordCount      = 0;
            int totalRecordCount = 0;
            PdfHelper pdfHelper = new PdfHelper();
            Document aPdfDoc = new Document(PageSize.A4);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(aPdfDoc, baos);
            
            genReport(userId, waTypeStr, deptName, waTitle, closingDate, dContractorList, scgStr, numOfCol, aPdfTable, cell, pdfHelper, aPdfDoc, writer);
                        
            // Data
            numOfCol = 4;
            
            CeilingRateCat1Info aCRCat1Info = null;
            
            for (int i = 0; i < allCrCat1InfoWeb.size(); i++)
            {
                totalRecordCount = 0;
                
                aCRCat1Info = (CeilingRateCat1Info)allCrCat1InfoWeb.get(i);
                
                // Skip listing contractor if its ceiling rate cannot be found or it is not selected to be listed
                if (aCRCat1Info.getContractorName() == null){ continue; }
                
                // Set up table
                aPdfTable = new PdfPTable(numOfCol);
                int tableColWidths[] = {25, 25, 25, 25}; // percentage
                aPdfTable.setWidths(tableColWidths);
                aPdfTable.setWidthPercentage(100);
                aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                
                // Contractor Name
                cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat1Info.getContractorName());
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // Row 1
                cell = pdfHelper.getHeaderCell("Resident/Non-resident/Off-shore");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Staff Category");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Daily Rate(" + aCRCat1Info.getCurrency() + ")");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Exceed Ceiling Rate?");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                aPdfTable.setHeaderRows(2);
                
                recordCount = 0;
                
                for (int k = 0; k < CeilingRateCat1Info.NumOfWorkingLocation; k++)
                {
                    for (int j = 0; j < CeilingRateCat1Info.NumOfStaffCategory; j++)
                    {
                        if (aCRCat1Info.getStdStaffRate(j, k) != CeilingRateCat1Info.NoOfferWebNumber)
                        {
                            recordCount++;
                            
                            boolean exceed = (aCRCat1Info.getStdStaffRateExceed(j, k).equals(aCRCat1Info.ExceedString));
                            boolean noOffer = (aCRCat1Info.getStdStaffRateExceed(j, k).equals(aCRCat1Info.NoOfferString));
                            
                            cell = pdfHelper.getCell(CeilingRateCat1Info.WorkingLocationName[k]);
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("" + (j + 3));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat1Info.getStdStaffRate(j, k)));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell(aCRCat1Info.getStdStaffRateExceed(j, k));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            
                            aPdfTable.addCell(cell);
                        }
                    }
                    
                    if (aCRCat1Info.getSupStaffRate(k) != CeilingRateCat1Info.NoOfferWebNumber)
                    {
                        recordCount++;
                        
                        boolean exceed = (aCRCat1Info.getSupStaffRateExceed(k).equals(aCRCat1Info.ExceedString));
                        boolean noOffer = (aCRCat1Info.getSupStaffRateExceed(k).equals(aCRCat1Info.NoOfferString));
                        
                        cell = pdfHelper.getCell(CeilingRateCat1Info.WorkingLocationName[k]);
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell("Supplementary 1");
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat1Info.getSupStaffRate(k)));
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell(aCRCat1Info.getSupStaffRateExceed(k));
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        
                        aPdfTable.addCell(cell);
                    }
                }
                
                totalRecordCount += recordCount;
                
                if (recordCount != 0){ aPdfDoc.add(aPdfTable); }
                
                // If there is no record for a contractor, show "No input"
                if (totalRecordCount == 0)
                {
                    // Create Table
                    aPdfTable = new PdfPTable(1);
                    aPdfTable.setWidthPercentage(100);
                    aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                    
                    // Contractor Name
                    cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat1Info.getContractorName());
                    cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                    aPdfTable.addCell(cell);
                    
                    // Show no record
                    cell = pdfHelper.getCell("-- No input --");
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                    
                    aPdfDoc.add(aPdfTable);
                }
                
                // Add new page
                if (i != allCrCat1InfoWeb.size() - 1){ aPdfDoc.newPage(); }
                if (i == allCrCat1InfoWeb.size() - 1 && showSubscoreRpt.equals("y"))
                {
                    pdfHelper.setHeaderText("RESTRICTED (CONTRACT)" + "\n" + "Contractor Performance Score" + "\n");
                    pdfHelper.setFooterText("RESTRICTED (CONTRACT)");
                    aPdfDoc.newPage();
                }
            }
            
            /********************************* Contractor Performance Score ***********************************************/
            if (showSubscoreRpt.equals("y"))
            { this.getQSPDFTable(contractorIDList, dContractorList, closingDate, scg, waTitle, aPdfDoc); }
            
            // Write PDF objects to stream
            aPdfDoc.close();
            
            // Add to log
            aSRVDBUser = getStaffRateValidationDBUser();
            int returnLogID = aSRVDBUser.addLog(scg, contractorIDList, deptID, closingDate, waFilePart, waFileNo, baos, showSubscoreRpt, userId, dpDeptId, soaDeptId);
            
            forwardToPrintReportJSP(request, response, baos, scgStr);
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage());}
        
        logger.debug(myName + " " + "ended");
    }

    private void genCat2ValidationReport(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "genCat2ValidationReport" + "]";
        logger.debug(myName + " " + "started");
        
        try
        {
            // Get user information
            SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
            if (secCtx == null)
            {throw new SysException(myName, "Cannot get user information.");}
            String userId    = secCtx.getUserId();
            String dpDeptId  = secCtx.getDPDeptId();
            String soaDeptId = secCtx.getSOADeptId();
            
            // Get Work Assignment Type
            String waType = (String)request.getSession().getAttribute("WA_TYPE");
            
            if (waType == null)
            { throw new Exception("Session [WAType] is NULL"); }
            
            String waTypeStr = (waType.equals("proposal") ? "Proposal Evaluation" : "Change Request");            
            
            // Get paramater values
            String scg               = request.getParameter("scg");
            String closingDate       = request.getParameter("closing_date");
            String contractorIDList  = request.getParameter("contractor_id_list");
            String dContractorIDList = request.getParameter("d_contractor_id_list");
            String contractorList    = request.getParameter("contractor_list");
            String dContractorList   = request.getParameter("d_contractor_list");
            String deptID            = request.getParameter("dept_id");
            String deptName          = request.getParameter("dept_name");
            String waTitle           = request.getParameter("wa_title");
            String waFilePart        = request.getParameter("wa_file_part");
            String waFileNo          = request.getParameter("wa_file_no");
            String showSubscoreRpt   = request.getParameter("show_subscore_rpt");
            String mScheme           = request.getParameter("m_scheme");
            String sc                = request.getParameter("sc");
            String sg                = request.getParameter("sg");
            
            validateReportContext(myName, scg, closingDate, contractorList, contractorIDList, dContractorList, dContractorIDList, deptID, deptName, waTitle, waFilePart, waFileNo, showSubscoreRpt, mScheme, sc, sg);
            
            // Split the contractor list into array
            String[] contractorID = contractorIDList.split(",");
            
            // Create service category string
            String scgStr = "Service Category " + sc + " " + (sg.equals("J") ? "Major" : "Minor") + " Group";
            
            // Get input staff rates and construct ceiling rate objects
            List<CeilingRateCat2Info> allCrCat2InfoWeb = new ArrayList<CeilingRateCat2Info>();
            
            for (int i = 0; i < contractorID.length; i++)
            {
                String aContractorID = contractorID[i].replaceAll("'", "");
                CeilingRateCat2Info aCRCat2Info = new CeilingRateCat2Info();
                
                aCRCat2Info.setContractorID(aContractorID);
                aCRCat2Info.setServiceGroup(sg);
                
                // Standard Staff Rate
                for (int j = 0; j < CeilingRateCat2Info.NumOfWorkingLocation; j++)
                {
                    for (int k = 0; k < CeilingRateCat2Info.NumOfStaffCategory; k++)
                    {
                        for (int l = 0; l < CeilingRateCat2Info.NumOfMScheme; l++)
                        {
                            String paramName = "TxtSR__" + aContractorID + "__" + aCRCat2Info.toStdStaffRateTableField(k, j, l);
                            String paramValue = request.getParameter(paramName);
                            
                            if (paramValue == null || paramValue.equals("") || paramValue.toLowerCase().equals("no offer")){
                                paramValue = "" + CeilingRateCat2Info.NoOfferWebNumber;
                            }
                            aCRCat2Info.setStdStaffRate(k, j, l, Double.valueOf(paramValue).doubleValue());
                        }
                    }
                }
                
                // Supplementary Staff Rate
                for (int j = 0; j < CeilingRateCat2Info.NumOfWorkingLocation; j++)
                {
                    for (int k = 0; k < CeilingRateCat2Info.NumOfMScheme; k++)
                    {
                        String paramName = "TxtSR__" + aContractorID + "__" + aCRCat2Info.toSupStaffRateTableField(j, k);
                        String paramValue = request.getParameter(paramName);
                        
                        if (paramValue == null || paramValue.equals("") || paramValue.toLowerCase().equals("no offer")){
                            paramValue = "" + CeilingRateCat2Info.NoOfferWebNumber;
                        }
                        
                        aCRCat2Info.setSupStaffRate(j, k, Double.valueOf(paramValue).doubleValue());
                    }
                }
                
                allCrCat2InfoWeb.add(aCRCat2Info);
            }
            
            // Get contractor informarion for compStaffRateValidationDBUser aSRVDBUser = getStaffRateValidationDBUser();arison
            StaffRateValidationDBUser aSRVDBUser = getStaffRateValidationDBUser();
            List<CeilingRateCat2Info> allCrCat2InfoDB = aSRVDBUser.getCeilingRateCat2ByList(contractorIDList, closingDate);
            
            // Validate ceiling rate
            for (int i = 0; i < allCrCat2InfoDB.size(); i++)
            {
                for (int j = 0; j < allCrCat2InfoWeb.size(); j++)
                {
                    CeilingRateCat2Info aCRCat2InfoDB  = (CeilingRateCat2Info)allCrCat2InfoDB.get(i);
                    CeilingRateCat2Info aCRCat2InfoWeb = (CeilingRateCat2Info)allCrCat2InfoWeb.get(j);
                    
                    if ((aCRCat2InfoDB.getContractorID()).equals(aCRCat2InfoWeb.getContractorID()))
                    {
                        // Copy other information
                        aCRCat2InfoWeb.setContractorName(aCRCat2InfoDB.getContractorName());
                        aCRCat2InfoWeb.setCurrency(aCRCat2InfoDB.getCurrency());
                        
                        // Check ceiling rates
                        aCRCat2InfoWeb.CheckCeilingRate(aCRCat2InfoDB);
                        break;
                    }
                }
            }
            
            // Variable
            int numOfCol		= 3;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            int recordCount      = 0;
            int totalRecordCount = 0;
            PdfHelper pdfHelper = new PdfHelper();
            Document aPdfDoc = new Document(PageSize.A4);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(aPdfDoc, baos);
            
            genReport(userId, waTypeStr, deptName, waTitle, closingDate, dContractorList, scgStr, numOfCol, aPdfTable, cell, pdfHelper, aPdfDoc, writer);
                        
            // Data
            numOfCol = 4;
            
            CeilingRateCat2Info aCRCat2Info = null;
            
            for (int i = 0; i < allCrCat2InfoWeb.size(); i++)
            {
                totalRecordCount = 0;
                
                aCRCat2Info = (CeilingRateCat2Info)allCrCat2InfoWeb.get(i);
                
                // Skip listing contractor if its ceiling rate cannot be found or it is not selected to be listed
                if (aCRCat2Info.getContractorName() == null){ continue; }
                
                // Set up table
                aPdfTable = new PdfPTable(numOfCol);
                int tableColWidths[] = {25, 25, 25, 25}; // percentage
                aPdfTable.setWidths(tableColWidths);
                aPdfTable.setWidthPercentage(100);
                aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                
                // Contractor Name
                cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat2Info.getContractorName());
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // Row 1
                cell = pdfHelper.getHeaderCell("Resident/Non-resident/Off-shore");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Staff Category");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Daily Rate(" + aCRCat2Info.getCurrency() + ")");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Exceed Ceiling Rate?");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                aPdfTable.setHeaderRows(2);
                
                recordCount = 0;
                
                for (int k = 0; k < CeilingRateCat2Info.NumOfWorkingLocation; k++)
                {
                    for (int j = 0; j < CeilingRateCat2Info.NumOfStaffCategory; j++)
                    {
                        for (int l = 0; l < CeilingRateCat2Info.NumOfMScheme; l++)
                        {
                            if (aCRCat2Info.getStdStaffRate(j, k, l) != CeilingRateCat2Info.NoOfferWebNumber)
                            {
                                recordCount++;
                                
                                boolean exceed = (aCRCat2Info.getStdStaffRateExceed(j, k, l).equals(aCRCat2Info.ExceedString));
                                boolean noOffer = (aCRCat2Info.getStdStaffRateExceed(j, k, l).equals(aCRCat2Info.NoOfferString));
                                
                                cell = pdfHelper.getCell(CeilingRateCat2Info.WorkingLocationName[k]);
                                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                                cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                                aPdfTable.addCell(cell);
                                
                                cell = pdfHelper.getCell("" + (j + 1));
                                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                                cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                                aPdfTable.addCell(cell);
                                
                                cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat2Info.getStdStaffRate(j, k, l)));
                                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                                cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                                aPdfTable.addCell(cell);
                                
                                cell = pdfHelper.getCell(aCRCat2Info.getStdStaffRateExceed(j, k, l));
                                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                                cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                                
                                aPdfTable.addCell(cell);
                            }
                        }
                    }
                    
                    for (int l = 0; l < CeilingRateCat2Info.NumOfMScheme; l++)
                    {
                        if (aCRCat2Info.getSupStaffRate(k, l) != CeilingRateCat2Info.NoOfferWebNumber)
                        {
                            recordCount++;
                            
                            boolean exceed = (aCRCat2Info.getSupStaffRateExceed(k, l).equals(aCRCat2Info.ExceedString));
                            boolean noOffer = (aCRCat2Info.getSupStaffRateExceed(k, l).equals(aCRCat2Info.NoOfferString));
                            
                            cell = pdfHelper.getCell(CeilingRateCat2Info.WorkingLocationName[k]);
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("Supplementary 1");
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat2Info.getSupStaffRate(k, l)));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell(aCRCat2Info.getSupStaffRateExceed(k, l));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            
                            aPdfTable.addCell(cell);
                        }
                    }
                }
                
                totalRecordCount += recordCount;
                
                if (recordCount != 0){ aPdfDoc.add(aPdfTable); }
                
                // If there is no record for a contractor, show "No input"
                if (totalRecordCount == 0)
                {
                    aPdfTable = new PdfPTable(1);
                    aPdfTable.setWidthPercentage(100);
                    aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                    
                    // Contractor Name
                    cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat2Info.getContractorName());
                    cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                    aPdfTable.addCell(cell);
                    
                    // Show no record
                    cell = pdfHelper.getCell("-- No input --");
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                    
                    aPdfDoc.add(aPdfTable);
                }
                
                // Add new page
                if (i != allCrCat2InfoWeb.size() - 1){ aPdfDoc.newPage(); }
                if (i == allCrCat2InfoWeb.size() - 1 && showSubscoreRpt.equals("y"))
                {
                    pdfHelper.setHeaderText("RESTRICTED (CONTRACT)" + "\n" + "Contractor Performance Score" + "\n");
                    pdfHelper.setFooterText("RESTRICTED (CONTRACT)");
                    aPdfDoc.newPage();
                }
            }
            
            /********************************* Contractor Performance Score ***********************************************/
            if (showSubscoreRpt.equals("y"))
            { this.getQSPDFTable(contractorIDList, dContractorList, closingDate, scg, waTitle, aPdfDoc); }
            
            // Renew number of page in session
            request.getSession().removeAttribute("QPSES_SRV_RPT_NO_OF_PAGE");
            request.getSession().setAttribute("QPSES_SRV_RPT_NO_OF_PAGE", writer.getPageNumber() + "");
            
            // Write PDF objects to stream
            aPdfDoc.close();
            
            // Add to log
            aSRVDBUser = getStaffRateValidationDBUser();
            int returnLogID = aSRVDBUser.addLog(scg, contractorIDList, deptID, closingDate, waFilePart, waFileNo, baos, showSubscoreRpt, userId, dpDeptId, soaDeptId);
            
            forwardToPrintReportJSP(request, response, baos, scgStr);
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage());}
        
        logger.debug(myName + " " + "ended");
    }
    
    private void genCat3ValidationReport(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "genCat3ValidationReport" + "]";
        logger.debug(myName + " " + "started");
        
        try
        {
            // Get user information
            SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
            if (secCtx == null)
            {throw new SysException(myName, "Cannot get user information.");}
            String userId    = secCtx.getUserId();
            String dpDeptId  = secCtx.getDPDeptId();
            String soaDeptId = secCtx.getSOADeptId();
            
            // Get Work Assignment Type
            String waType = (String)request.getSession().getAttribute("WA_TYPE");
            
            if (waType == null)
            { throw new Exception("Session [WAType] is NULL"); }
            
            String waTypeStr = (waType.equals("proposal") ? "Proposal Evaluation" : "Change Request");            
            
            // Get paramater values
            String scg               = request.getParameter("scg");
            String closingDate       = request.getParameter("closing_date");
            String contractorIDList  = request.getParameter("contractor_id_list");
            String dContractorIDList = request.getParameter("d_contractor_id_list");
            String contractorList    = request.getParameter("contractor_list");
            String dContractorList   = request.getParameter("d_contractor_list");
            String deptID            = request.getParameter("dept_id");
            String deptName          = request.getParameter("dept_name");
            String waTitle           = request.getParameter("wa_title");
            String waFilePart        = request.getParameter("wa_file_part");
            String waFileNo          = request.getParameter("wa_file_no");
            String showSubscoreRpt   = request.getParameter("show_subscore_rpt");
            String mScheme           = request.getParameter("m_scheme");
            String sc                = request.getParameter("sc");
            String sg                = request.getParameter("sg");
            
            validateReportContext(myName, scg, closingDate, contractorList, contractorIDList, dContractorList, dContractorIDList, deptID, deptName, waTitle, waFilePart, waFileNo, showSubscoreRpt, mScheme, sc, sg);
            
            // Split the contractor list into array
            String[] contractorID = contractorIDList.split(",");
            
            // Create service category string
            String scgStr = "Service Category " + sc + " " + (sg.equals("J") ? "Major" : "Minor") + " Group";
            
            // Get input staff rates and construct ceiling rate objects
            List<CeilingRateCat3Info> allCrCat3InfoWeb = new ArrayList<CeilingRateCat3Info>();
            
            for (int i = 0; i < contractorID.length; i++)
            {
                String aContractorID = contractorID[i].replaceAll("'", "");
                CeilingRateCat3Info aCRCat3Info = new CeilingRateCat3Info();
                
                aCRCat3Info.setContractorID(aContractorID);
                aCRCat3Info.setServiceGroup(sg);
                
                // Standard On-going Staff Rate
                for (int j = 0; j < CeilingRateCat3Info.NumOfWorkingLocation; j++)
                {
                    for (int k = 0; k < CeilingRateCat3Info.NumOfOnGoingStaffCategory; k++)
                    {
                        for (int l = 0; l < CeilingRateCat3Info.NumOfMScheme; l++)
                        {
                            String paramName = "TxtSR__" + aContractorID + "__" + aCRCat3Info.toStdOnGoingStaffRateTableField(k, j, l);
                            String paramValue = request.getParameter(paramName);
                            
                            if (paramValue == null || paramValue.equals("") || paramValue.toLowerCase().equals("no offer"))
                                paramValue = "" + CeilingRateCat3Info.NoOfferWebNumber;
                            
                            aCRCat3Info.setStdOnGoingStaffRate(k, j, l, Double.valueOf(paramValue).doubleValue());
                        }
                    }
                }
                
                // Supplementary On-going Staff Rate
                for (int j = 0; j < CeilingRateCat3Info.NumOfWorkingLocation; j++)
                {
                    for (int k = 0; k < CeilingRateCat3Info.NumOfMScheme; k++)
                    {
                        String paramName = "TxtSR__" + aContractorID + "__" + aCRCat3Info.toSupOnGoingStaffRateTableField(j, k);
                        String paramValue = request.getParameter(paramName);
                        
                        if (paramValue == null || paramValue.equals("") || paramValue.toLowerCase().equals("no offer")){
                            paramValue = "" + CeilingRateCat3Info.NoOfferWebNumber;
                        }
                        aCRCat3Info.setSupOnGoingStaffRate(j, k, Double.valueOf(paramValue).doubleValue());
                    }
                }
                
                // Check standard one-off staff rate
                for (int j = 0; j < CeilingRateCat3Info.NumOfOneOffStaffCategory; j++)
                {
                    for (int k = 0; k < CeilingRateCat3Info.NumOfWorkingLocation; k++)
                    {
                        String paramName = "TxtSR__" + aContractorID + "__" + aCRCat3Info.toStdOneOffStaffRateTableField(j, k);
                        String paramValue = request.getParameter(paramName);
                        
                        if (paramValue == null || paramValue.equals("") || paramValue.toLowerCase().equals("no offer")){
                            paramValue = "" + CeilingRateCat3Info.NoOfferWebNumber;
                        }
                        aCRCat3Info.setStdOneOffStaffRate(j, k, Double.valueOf(paramValue).doubleValue());
                    }
                }
                
                // Check supplementary one-off staff rate
                for (int j = 0; j < CeilingRateCat3Info.NumOfWorkingLocation; j++)
                {
                    String paramName = "TxtSR__" + aContractorID + "__" + aCRCat3Info.toSupOneOffStaffRateTableField(j);
                    String paramValue = request.getParameter(paramName);
                    
                    if (paramValue == null || paramValue.equals("") || paramValue.toLowerCase().equals("no offer")){
                        paramValue = "" + CeilingRateCat3Info.NoOfferWebNumber;
                    }
                    aCRCat3Info.setSupOneOffStaffRate(j, Double.valueOf(paramValue).doubleValue());
                }
                
                allCrCat3InfoWeb.add(aCRCat3Info);
            }
            
            // Get contractor informarion for comparison
            StaffRateValidationDBUser aSRVDBUser = getStaffRateValidationDBUser();
            List<CeilingRateCat3Info> allCrCat3InfoDB = aSRVDBUser.getCeilingRateCat3ByList(contractorIDList, closingDate);
            
            // Validate ceiling rate
            for (int i = 0; i < allCrCat3InfoDB.size(); i++)
            {
                for (int j = 0; j < allCrCat3InfoWeb.size(); j++)
                {
                    CeilingRateCat3Info aCRCat3InfoDB  = (CeilingRateCat3Info)allCrCat3InfoDB.get(i);
                    CeilingRateCat3Info aCRCat3InfoWeb = (CeilingRateCat3Info)allCrCat3InfoWeb.get(j);
                    
                    if ((aCRCat3InfoDB.getContractorID()).equals(aCRCat3InfoWeb.getContractorID()))
                    {
                        // Copy other information
                        aCRCat3InfoWeb.setContractorName(aCRCat3InfoDB.getContractorName());
                        aCRCat3InfoWeb.setCurrency(aCRCat3InfoDB.getCurrency());
                        
                        // Check ceiling rates
                        aCRCat3InfoWeb.CheckCeilingRate(aCRCat3InfoDB);
                        break;
                    }
                }
            }
            
            // Variable
            int numOfCol		= 3;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            int totalRecordCount = 0;
            PdfHelper pdfHelper = new PdfHelper();
            Document aPdfDoc = new Document(PageSize.A4);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(aPdfDoc, baos);
            
            genReport(userId, waTypeStr, deptName, waTitle, closingDate, dContractorList, scgStr, numOfCol, aPdfTable, cell, pdfHelper, aPdfDoc, writer);
                        
            // Data
            CeilingRateCat3Info aCRCat3Info = null;
            
            for (int i = 0; i < allCrCat3InfoWeb.size(); i++)
            {
                totalRecordCount = 0;
                aCRCat3Info = (CeilingRateCat3Info)allCrCat3InfoWeb.get(i);
                
                // Skip listing contractor if its ceiling rate cannot be found or it is not selected to be listed
                if (aCRCat3Info.getContractorName() == null){ continue; }
                
                /*********************** One-off services ***********************/
                // Set up table
                numOfCol = 4;
                // Set up table
                aPdfTable = new PdfPTable(numOfCol);
                int tableColWidths2[] = {25, 25, 25, 25}; // percentage
                aPdfTable.setWidths(tableColWidths2);
                aPdfTable.setWidthPercentage(100);
                aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                
                // Contractor Name
                cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat3Info.getContractorName());
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // Put One-off Services
                cell = pdfHelper.getHeaderCell("One-off Services");
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // Row 1
                cell = pdfHelper.getHeaderCell("Resident/Non-resident/Off-shore");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Staff Category");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Daily Rate(" + aCRCat3Info.getCurrency() + ")");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Exceed Ceiling Rate?");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                aPdfTable.setHeaderRows(2);
                
                int recordCount2 = 0;
                
                for (int k = 0; k < CeilingRateCat3Info.NumOfWorkingLocation; k++)
                {
                    for (int j = 0; j < CeilingRateCat3Info.NumOfOneOffStaffCategory; j++)
                    {
                        if (aCRCat3Info.getStdOneOffStaffRate(j, k) != CeilingRateCat3Info.NoOfferWebNumber)
                        {
                            recordCount2++;
                            
                            boolean exceed = (aCRCat3Info.getStdOneOffStaffRateExceed(j, k).equals(aCRCat3Info.ExceedString));
                            boolean noOffer = (aCRCat3Info.getStdOneOffStaffRateExceed(j, k).equals(aCRCat3Info.NoOfferString));
                            
                            cell = pdfHelper.getCell(CeilingRateCat3Info.WorkingLocationName[k]);
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("" + (j + 1));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat3Info.getStdOneOffStaffRate(j, k)));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell(aCRCat3Info.getStdOneOffStaffRateExceed(j, k));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            
                            aPdfTable.addCell(cell);
                        }
                    }
                    
                    if (aCRCat3Info.getSupOneOffStaffRate(k) != CeilingRateCat3Info.NoOfferWebNumber)
                    {
                        recordCount2++;
                        
                        boolean exceed = (aCRCat3Info.getSupOneOffStaffRateExceed(k).equals(CeilingRateCat3Info.ExceedString));
                        boolean noOffer = (aCRCat3Info.getSupOneOffStaffRateExceed(k).equals(CeilingRateCat3Info.NoOfferString));
                        
                        cell = pdfHelper.getCell(CeilingRateCat3Info.WorkingLocationName[k]);
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell("Supplementary 1");
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat3Info.getSupOneOffStaffRate(k)));
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell(aCRCat3Info.getSupOneOffStaffRateExceed(k));
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        
                        aPdfTable.addCell(cell);
                    }
                }
                
                totalRecordCount += recordCount2;
                
                if (recordCount2 != 0)
                { aPdfDoc.add(aPdfTable); }
                
                /*********************** On-going services ***********************/
                // Set up table
                numOfCol = 4;
                aPdfTable = new PdfPTable(numOfCol);
                int tableColWidths[] = {25, 25, 25, 25}; // percentage
                aPdfTable.setWidths(tableColWidths);
                aPdfTable.setWidthPercentage(100);
                aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                
                // Contractor Name
                cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat3Info.getContractorName());
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // Put On-going Services
                cell = pdfHelper.getHeaderCell("On-going Services");
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // Row 1
                cell = pdfHelper.getHeaderCell("Resident/Non-resident/Off-shore");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Staff Category");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Daily Rate(" + aCRCat3Info.getCurrency() + ")");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Exceed Ceiling Rate?");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                aPdfTable.setHeaderRows(3);
                
                int recordCount1 = 0;
                
                // Standard on-going service
                for (int k = 0; k < CeilingRateCat3Info.NumOfWorkingLocation; k++)
                {
                    for (int j = 0; j < CeilingRateCat3Info.NumOfOnGoingStaffCategory; j++)
                    {
                        for (int l = 0; l < CeilingRateCat3Info.NumOfMScheme; l++)
                        {
                            if (aCRCat3Info.getStdOnGoingStaffRate(j, k, l) != CeilingRateCat3Info.NoOfferWebNumber)
                            {
                                recordCount1++;
                                
                                boolean exceed = (aCRCat3Info.getStdOnGoingStaffRateExceed(j, k, l).equals(CeilingRateCat3Info.ExceedString));
                                boolean noOffer = (aCRCat3Info.getStdOnGoingStaffRateExceed(j, k, l).equals(CeilingRateCat3Info.NoOfferString));
                                
                                cell = pdfHelper.getCell(CeilingRateCat3Info.WorkingLocationName[k]);
                                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                                cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                                aPdfTable.addCell(cell);
                                
                                cell = pdfHelper.getCell("" + (j + 1));
                                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                                cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                                aPdfTable.addCell(cell);
                                
                                cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat3Info.getStdOnGoingStaffRate(j, k, l)));
                                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                                cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                                aPdfTable.addCell(cell);
                                
                                cell = pdfHelper.getCell(aCRCat3Info.getStdOnGoingStaffRateExceed(j, k, l));
                                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                                cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                                
                                aPdfTable.addCell(cell);
                            }
                        }
                    }
                    
                    for (int l = 0; l < CeilingRateCat3Info.NumOfMScheme; l++)
                    {
                        if (aCRCat3Info.getSupOnGoingStaffRate(k, l) != CeilingRateCat3Info.NoOfferWebNumber)
                        {
                            recordCount1++;
                            
                            boolean exceed = (aCRCat3Info.getSupOnGoingStaffRateExceed(k, l).equals(CeilingRateCat3Info.ExceedString));
                            boolean noOffer = (aCRCat3Info.getSupOnGoingStaffRateExceed(k, l).equals(CeilingRateCat3Info.NoOfferString));
                            
                            cell = pdfHelper.getCell(CeilingRateCat3Info.WorkingLocationName[k]);
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("Supplementary 1");
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat3Info.getSupOnGoingStaffRate(k, l)));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell(aCRCat3Info.getSupOnGoingStaffRateExceed(k, l));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            
                            aPdfTable.addCell(cell);
                        }
                    }
                }
                
                totalRecordCount += recordCount1;
                
                if (recordCount1 != 0)
                {
                    if (recordCount2 != 0){ aPdfDoc.newPage(); }
                    aPdfDoc.add(aPdfTable);
                }
                
                // If there is no record for a contractor, show "No input"
                if (totalRecordCount == 0)
                {
                    aPdfTable = new PdfPTable(1);
                    aPdfTable.setWidthPercentage(100);
                    aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                    
                    // Contractor Name
                    cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat3Info.getContractorName());
                    cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                    aPdfTable.addCell(cell);
                    
                    // Show no record
                    cell = pdfHelper.getCell("-- No input --");
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                    
                    aPdfDoc.add(aPdfTable);
                }
                
                // Add new page
                if (i != allCrCat3InfoWeb.size() - 1){ aPdfDoc.newPage(); }
                if ((i == allCrCat3InfoWeb.size() - 1) && showSubscoreRpt.equals("y"))
                {
                    pdfHelper.setHeaderText("RESTRICTED (CONTRACT)" + "\n" + "Contractor Performance Score" + "\n");
                    pdfHelper.setFooterText("RESTRICTED (CONTRACT)");
                    aPdfDoc.newPage();
                }
            }
            
            /********************************* Contractor Performance Score ***********************************************/
            if (showSubscoreRpt.equals("y"))
            { this.getQSPDFTable(contractorIDList, dContractorList, closingDate, scg, waTitle, aPdfDoc); }
            
            // Renew number of page in session
            request.getSession().removeAttribute("QPSES_SRV_RPT_NO_OF_PAGE");
            request.getSession().setAttribute("QPSES_SRV_RPT_NO_OF_PAGE", writer.getPageNumber() + "");
            
            // Write PDF objects to stream
            aPdfDoc.close();
            
            // Add to log
            aSRVDBUser = getStaffRateValidationDBUser();
            int returnLogID = aSRVDBUser.addLog(scg, contractorIDList, deptID, closingDate, waFilePart, waFileNo, baos, showSubscoreRpt, userId, dpDeptId, soaDeptId);
            
            forwardToPrintReportJSP(request, response, baos, scgStr);
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage());}
        
        logger.debug(myName + " " + "ended");
    }
    
    private void genCat4ValidationReport(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String myName = "[" + MyClassName + "." + "genCat1ValidationReport" + "]";
        logger.debug(myName + " " + "started");
        
        try
        {
            // Get user information
            SecurityContext secCtx = (SecurityContext)(request.getSession().getAttribute("QPSES_SECURITY_CONTEXT"));
            if (secCtx == null)
            {throw new SysException(myName, "Cannot get user information.");}
            String userId    = secCtx.getUserId();
            String dpDeptId  = secCtx.getDPDeptId();
            String soaDeptId = secCtx.getSOADeptId();
            
            // Get Work Assignment Type
            String waType = (String)request.getSession().getAttribute("WA_TYPE");
            
            if (waType == null)
            { throw new Exception("Session [WAType] is NULL"); }
            
            String waTypeStr = (waType.equals("proposal") ? "Proposal Evaluation" : "Change Request");
            
            // Get paramater values
            String scg               = request.getParameter("scg");
            String closingDate       = request.getParameter("closing_date");
            String contractorIDList  = request.getParameter("contractor_id_list");
            String dContractorIDList = request.getParameter("d_contractor_id_list");
            String contractorList    = request.getParameter("contractor_list");
            String dContractorList   = request.getParameter("d_contractor_list");
            String deptID            = request.getParameter("dept_id");
            String deptName          = request.getParameter("dept_name");
            String waTitle           = request.getParameter("wa_title");
            String waFilePart        = request.getParameter("wa_file_part");
            String waFileNo          = request.getParameter("wa_file_no");
            String showSubscoreRpt   = request.getParameter("show_subscore_rpt");
            String mScheme           = request.getParameter("m_scheme");
            String sc                = request.getParameter("sc");
            String sg                = request.getParameter("sg");
            
            validateReportContext(myName, scg, closingDate, contractorList, contractorIDList, dContractorList, dContractorIDList, deptID, deptName, waTitle, waFilePart, waFileNo, showSubscoreRpt, mScheme, sc, sg);
            
            // Split the contractor list into array
            String[] contractorID = contractorIDList.split(",");
            
            // Create service category string
            //String scgStr = "Service Category " + sc + " " + (sg.equals("J") ? "Major" : "Minor") + " Group";
            String scgStr = "Service Category " + sc;
            
            // Get input staff rates and construct ceiling rate objects
            List<CeilingRateCat4Info> allCrCat4InfoWeb = new ArrayList<CeilingRateCat4Info>();
            
            for (int i = 0; i < contractorID.length; i++)
            {
                String aContractorID = contractorID[i].replaceAll("'", "");
                CeilingRateCat4Info aCRCat4Info = new CeilingRateCat4Info();
                
                aCRCat4Info.setContractorID(aContractorID);
                aCRCat4Info.setServiceGroup(sg);
                
                // One-off Staff Rate
                for (int j = 0; j < CeilingRateCat4Info.NumOfWorkingLocation; j++)
                {
                    for (int k = 0; k < CeilingRateCat4Info.NumOfOneOffStaffCategory; k++)
                    {
                        String paramName = "TxtSR__" + aContractorID + "__" + aCRCat4Info.toOneOffStaffRateTableField(k, j);
                        String paramValue = request.getParameter(paramName);
                        
                        if (paramValue == null || paramValue.equals("") || paramValue.toLowerCase().equals("no offer")){
                            paramValue = "" + CeilingRateCat4Info.NoOfferWebNumber;
                        }
                        aCRCat4Info.setOneOffStaffRate(k, j, Double.valueOf(paramValue).doubleValue());
                    }
                }
                
                // One-going Staff Rate
                for (int j = 0; j < CeilingRateCat4Info.NumOfWorkingLocation; j++)
                {
                    for (int k = 0; k < CeilingRateCat4Info.NumOfOnGoingStaffCategory; k++)
                    {
                        String paramName = "TxtSR__" + aContractorID + "__" + aCRCat4Info.toOnGoingStaffRateTableField(k, j);
                        String paramValue = request.getParameter(paramName);
                        
                        if (paramValue == null || paramValue.equals("") || paramValue.toLowerCase().equals("no offer")){
                            paramValue = "" + CeilingRateCat4Info.NoOfferWebNumber;
                        }
                        aCRCat4Info.setOnGoingStaffRate(k, j, Double.valueOf(paramValue).doubleValue());
                    }
                }
                
                allCrCat4InfoWeb.add(aCRCat4Info);
            }
            
            // Get contractor informarion for comparison
            StaffRateValidationDBUser aSRVDBUser = getStaffRateValidationDBUser();
            List<CeilingRateCat4Info> allCrCat4InfoDB = aSRVDBUser.getCeilingRateCat4ByList(contractorIDList, closingDate);
            
            // Validate ceiling rate
            for (int i = 0; i < allCrCat4InfoDB.size(); i++)
            {
                for (int j = 0; j < allCrCat4InfoWeb.size(); j++)
                {
                    CeilingRateCat4Info aCRCat4InfoDB  = (CeilingRateCat4Info)allCrCat4InfoDB.get(i);
                    CeilingRateCat4Info aCRCat4InfoWeb = (CeilingRateCat4Info)allCrCat4InfoWeb.get(j);
                    
                    if ((aCRCat4InfoDB.getContractorID()).equals(aCRCat4InfoWeb.getContractorID()))
                    {
                        // Copy other information
                        aCRCat4InfoWeb.setContractorName(aCRCat4InfoDB.getContractorName());
                        aCRCat4InfoWeb.setCurrency(aCRCat4InfoDB.getCurrency());
                        
                        // Check ceiling rates
                        aCRCat4InfoWeb.CheckCeilingRate(aCRCat4InfoDB);
                        break;
                    }
                }
            }
            
            // Variable
            int numOfCol		= 3;
            PdfPCell cell       = null;
            PdfPTable aPdfTable = null;
            int recordCount      = 0;
            int totalRecordCount = 0;
            PdfHelper pdfHelper = new PdfHelper();
            Document aPdfDoc = new Document(PageSize.A4);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(aPdfDoc, baos);
            
            genReport(userId, waTypeStr, deptName, waTitle, closingDate, dContractorList, scgStr, numOfCol, aPdfTable, cell, pdfHelper, aPdfDoc, writer);
                        
            // Data
            
            CeilingRateCat4Info aCRCat4Info = null;
            
            for (int i = 0; i < allCrCat4InfoWeb.size(); i++)
            {
                totalRecordCount = 0;
                
                aCRCat4Info = (CeilingRateCat4Info)allCrCat4InfoWeb.get(i);
                
                // Skip listing contractor if its ceiling rate cannot be found or it is not selected to be listed
                if (aCRCat4Info.getContractorName() == null){ continue; }
                
                /******************* One-off services ***********************/
                // Set up table
                numOfCol = 4;
                aPdfTable = new PdfPTable(numOfCol);
                int tableColWidths[] = {25, 25, 25, 25}; // percentage
                aPdfTable.setWidths(tableColWidths);
                aPdfTable.setWidthPercentage(100);
                aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                
                // Contractor Name
                cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat4Info.getContractorName());
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // One-off Services
                cell = pdfHelper.getHeaderCell("One-off Services");
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // Row 1
                cell = pdfHelper.getHeaderCell("Resident/Non-resident/Off-shore");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Staff Category");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Daily Rate (" + aCRCat4Info.getCurrency() + ")");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Exceed Ceiling Rate?");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                aPdfTable.setHeaderRows(3);
                
                int recordCount1 = 0;
                
                for (int k = 0; k < CeilingRateCat4Info.NumOfWorkingLocation; k++)
                {
                    for (int j = 0; j < CeilingRateCat4Info.NumOfOneOffStaffCategory; j++)
                    {
                        if (aCRCat4Info.getOneOffStaffRate(j, k) != CeilingRateCat4Info.NoOfferWebNumber)
                        {
                            recordCount1++;
                            
                            boolean exceed = (aCRCat4Info.getOneOffStaffRateExceed(j, k).equals(aCRCat4Info.ExceedString));
                            boolean noOffer = (aCRCat4Info.getOneOffStaffRateExceed(j, k).equals(aCRCat4Info.NoOfferString));
                            
                            cell = pdfHelper.getCell(CeilingRateCat4Info.WorkingLocationName[k]);
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("" + (j + 1));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOneOffStaffRate(j, k)));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell(aCRCat4Info.getOneOffStaffRateExceed(j, k));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            
                            aPdfTable.addCell(cell);
                        }
                    }
                }
                
                totalRecordCount += recordCount1;
                
                if (recordCount1 != 0)
                { aPdfDoc.add(aPdfTable); }
                
                
                /******************* On-going (Hourly Rate) ***********************/
                // Set up table
                numOfCol = 4;
                aPdfTable = new PdfPTable(numOfCol);
                int tableColWidths2[] = {25, 25, 25, 25}; // percentage
                aPdfTable.setWidths(tableColWidths2);
                aPdfTable.setWidthPercentage(100);
                aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                
                // Contractor Name
                cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat4Info.getContractorName());
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // On-going Services
                cell = pdfHelper.getHeaderCell("On-going Services (Non-resident/Off-shore)");
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // Row 1
                cell = pdfHelper.getHeaderCell("Office/Non-office Hours");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Staff Category");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Hourly Rate (" + aCRCat4Info.getCurrency() + ")");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Exceed Ceiling Rate?");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                aPdfTable.setHeaderRows(3);
                
                int recordCount2 = 0;
                
                for (int k = 0; k < CeilingRateCat4Info.NumOfWorkingHourType - 1; k++)
                {
                    for (int j = 0; j < CeilingRateCat4Info.NumOfOnGoingStaffCategory; j++)
                    {
                        if (aCRCat4Info.getOnGoingStaffRate(j, k) != CeilingRateCat4Info.NoOfferWebNumber)
                        {
                            recordCount2++;
                            
                            boolean exceed = (aCRCat4Info.getOnGoingStaffRateExceed(j, k).equals(aCRCat4Info.ExceedString));
                            boolean noOffer = (aCRCat4Info.getOnGoingStaffRateExceed(j, k).equals(aCRCat4Info.NoOfferString));
                            
                            cell = pdfHelper.getCell(CeilingRateCat4Info.WorkingHourTypeName[k]);
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("" + (j + 1));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOnGoingStaffRate(j, k)));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            aPdfTable.addCell(cell);
                            
                            cell = pdfHelper.getCell(aCRCat4Info.getOnGoingStaffRateExceed(j, k));
                            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                            
                            aPdfTable.addCell(cell);
                        }
                    }
                }
                
                totalRecordCount += recordCount2;
                
                if (recordCount2 != 0)
                {
                    if (recordCount1 != 0){ aPdfDoc.newPage(); }
                    aPdfDoc.add(aPdfTable);
                }
                
                /******************* On-going (24-Hour Round-the-clock Daily Rate) ***********************/
                // Set up table
                numOfCol = 3;
                aPdfTable = new PdfPTable(numOfCol);
                int tableColWidths3[] = {40, 30, 30}; // percentage
                aPdfTable.setWidths(tableColWidths3);
                aPdfTable.setWidthPercentage(100);
                aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                
                // Contractor Name
                cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat4Info.getContractorName());
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // On-going Services
                cell = pdfHelper.getHeaderCell("On-going Services (Non-resident/Off-shore)");
                cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                aPdfTable.addCell(cell);
                
                // Row 1
                cell = pdfHelper.getHeaderCell("Staff Category");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("24-Hour Round-the-clock Daily Rate (" + aCRCat4Info.getCurrency() + ")");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                cell = pdfHelper.getHeaderCell("Exceed Ceiling Rate?");
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                aPdfTable.addCell(cell);
                
                aPdfTable.setHeaderRows(3);
                
                int recordCount3 = 0;
                
                for (int j = 0; j < CeilingRateCat4Info.NumOfOnGoingStaffCategory; j++)
                {
                    int k = CeilingRateCat4Info.NumOfWorkingHourType - 1;
                    
                    if (aCRCat4Info.getOnGoingStaffRate(j, k) != CeilingRateCat4Info.NoOfferWebNumber)
                    {
                        recordCount3++;
                        
                        boolean exceed = (aCRCat4Info.getOnGoingStaffRateExceed(j, k).equals(aCRCat4Info.ExceedString));
                        boolean noOffer = (aCRCat4Info.getOnGoingStaffRateExceed(j, k).equals(aCRCat4Info.NoOfferString));
                        
                        cell = pdfHelper.getCell("" + (j + 1));
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell("" + SysManager.formatDecimal(aCRCat4Info.getOnGoingStaffRate(j, k)));
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        aPdfTable.addCell(cell);
                        
                        cell = pdfHelper.getCell(aCRCat4Info.getOnGoingStaffRateExceed(j, k));
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setBackgroundColor((exceed? this.ExceedBGColor : (noOffer? this.NoOfferBGColor : Color.WHITE)));
                        
                        aPdfTable.addCell(cell);
                    }
                }
                
                totalRecordCount += recordCount3;
                
                if (recordCount3 != 0)
                {
                    if (recordCount2 != 0){ aPdfDoc.newPage(); }
                    aPdfDoc.add(aPdfTable);
                }
                
                // If there is no record for a contractor, show "No input"
                if (totalRecordCount == 0)
                {
                    aPdfTable = new PdfPTable(1);
                    aPdfTable.setWidthPercentage(100);
                    aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                    
                    // Contractor Name
                    cell = pdfHelper.getHeaderCell("Contractor : " + aCRCat4Info.getContractorName());
                    cell.setColspan(numOfCol); cell.setBorder(Cell.NO_BORDER);
                    aPdfTable.addCell(cell);
                    
                    // Show no record
                    cell = pdfHelper.getCell("-- No input --");
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    aPdfTable.addCell(cell);
                    
                    aPdfDoc.add(aPdfTable);
                }
                
                // Add new page
                if (i != allCrCat4InfoWeb.size() - 1){ aPdfDoc.newPage(); }
                if (i == allCrCat4InfoWeb.size() - 1 && showSubscoreRpt.equals("y"))
                {
                    pdfHelper.setHeaderText("RESTRICTED (CONTRACT)" + "\n" + "Contractor Performance Score" + "\n");
                    pdfHelper.setFooterText("RESTRICTED (CONTRACT)");
                    aPdfDoc.newPage();
                }
            }
            
            /********************************* Contractor Performance Score ***********************************************/
            if (showSubscoreRpt.equals("y"))
            { this.getQSPDFTable(contractorIDList, dContractorList, closingDate, scg, waTitle, aPdfDoc); }
            
            // Renew number of page in session
            request.getSession().removeAttribute("QPSES_SRV_RPT_NO_OF_PAGE");
            request.getSession().setAttribute("QPSES_SRV_RPT_NO_OF_PAGE", writer.getPageNumber() + "");
            
            // Write PDF objects to stream
            aPdfDoc.close();
            
            // Add to log
            aSRVDBUser = getStaffRateValidationDBUser();
            int returnLogID = aSRVDBUser.addLog(scg, contractorIDList, deptID, closingDate, waFilePart, waFileNo, baos, showSubscoreRpt, userId, dpDeptId, soaDeptId);
            
            forwardToPrintReportJSP(request, response, baos, scgStr);
        }
        catch (Exception ex)
        { throw new ServletException(ex.getMessage());}
        
        logger.debug(myName + " " + "ended");
    }
    
	private void genReport(String userId, String waTypeStr, String deptName, String waTitle, String closingDate, String dContractorList, String scgStr, int numOfCol, PdfPTable aPdfTable, PdfPCell cell, PdfHelper pdfHelper, Document aPdfDoc, PdfWriter writer) throws DocumentException{
        
        // Initialize PDF helper
        pdfHelper.setDisplayUserName(userId);
        pdfHelper.setFooterText("RESTRICTED (TENDER)");
        
        // Initialize PDF document        
        writer.setPageEvent(pdfHelper);
        pdfHelper.setHeaderText("RESTRICTED (TENDER)" + "\n" + "Validation of Proposed Staff Rates for " + waTypeStr);
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
        
        /********************************* Validation Report ******************************************/
        // Create Header
        aPdfTable = new PdfPTable(numOfCol);
        int tableColWidthsHD[] = {37, 2, 61}; // percentage
        aPdfTable.setWidths(tableColWidthsHD);
        aPdfTable.setWidthPercentage(100);
        aPdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
        
        cell = pdfHelper.getHeaderCell("B/D Name"); cell.setBorder(Cell.NO_BORDER);
        aPdfTable.addCell(cell);
        cell = pdfHelper.getHeaderCell(":"); cell.setBorder(Cell.NO_BORDER);
        aPdfTable.addCell(cell);
        cell = pdfHelper.getCell(deptName); cell.setBorder(Cell.NO_BORDER);
        aPdfTable.addCell(cell);
        
        cell = pdfHelper.getHeaderCell("Assignment Title"); cell.setBorder(Cell.NO_BORDER);
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
        cell = pdfHelper.getCell(dContractorList.equals("")? "N/A" : dContractorList); cell.setBorder(Cell.NO_BORDER);
        aPdfTable.addCell(cell);
        
        cell = pdfHelper.getHeaderCell("Service Category/Group"); cell.setBorder(Cell.NO_BORDER);
        aPdfTable.addCell(cell);
        cell = pdfHelper.getHeaderCell(":"); cell.setBorder(Cell.NO_BORDER);
        aPdfTable.addCell(cell);
        cell = pdfHelper.getCell(scgStr); cell.setBorder(Cell.NO_BORDER);
        aPdfTable.addCell(cell);
        
        // Blank row
        cell = pdfHelper.getHeaderCell("");
        cell.setBorder(Cell.NO_BORDER);
        cell.setColspan(numOfCol);
        aPdfTable.addCell(cell);
        
        cell = pdfHelper.getHeaderCell("Ceiling Rates effective as at the closing date for proposal submission are used for validation of staff rates.");
        cell.setBorder(Cell.NO_BORDER);
        cell.setColspan(numOfCol);
        aPdfTable.addCell(cell);
        
        aPdfTable.setSpacingAfter(10f);
        aPdfDoc.add(aPdfTable);
	}
	
	private void validateReportContext(String myName, String scg, String closingDate, String contractorList, String contractorIDList, String dContractorList, String dContractorIDList, String deptID, String deptName, String waTitle, String waFilePart, String waFileNo, String showSubscoreRpt, String mScheme, String sc, String sg) throws SysException {
		if (scg == null)
        {throw new SysException(myName, "Parameter [scg] is NULL");}
        if (closingDate == null)
        {throw new SysException(myName, "Parameter [closingDate] is NULL");}
        if (contractorList == null)
        {throw new SysException(myName, "Parameter [contractorList] is NULL");}
        if (contractorIDList == null)
        {throw new SysException(myName, "Parameter [contractorIDList] is NULL");}
        if (dContractorList == null)
        {throw new SysException(myName, "Parameter [dContractorList] is NULL");}
        if (dContractorIDList == null)
        {throw new SysException(myName, "Parameter [dContractorIDList] is NULL");}
        if (deptID == null)
        {throw new SysException(myName, "Parameter [deptID] is NULL");}
        if (deptName == null)
        {throw new SysException(myName, "Parameter [deptName] is NULL");}
        if (waTitle == null)
        {throw new SysException(myName, "Parameter [waTitle] is NULL");}
        if (waFilePart == null)
        {throw new SysException(myName, "Parameter [waFilePart] is NULL");}
        if (waFileNo == null)
        {throw new SysException(myName, "Parameter [waFileNo] is NULL");}
        if (showSubscoreRpt == null)
        {throw new SysException(myName, "Parameter [showSubscoreRpt] is NULL");}
        if (mScheme == null)
        {throw new SysException(myName, "Parameter [mScheme] is NULL");}
        if (sc == null)
        {throw new SysException(myName, "Parameter [sc] is NULL");}
        if (sg == null)
        {throw new SysException(myName, "Parameter [sg] is NULL");}
	}
	
	private void forwardToPrintReportJSP(HttpServletRequest request, HttpServletResponse response, ByteArrayOutputStream baos, String scgStr) throws ServletException, IOException, PdfException{
		
		String myName = "[" + MyClassName + "." + "forwardToJSP" + "]";
        logger.debug(myName + " " + "started");
        
        // Create Session
        byte[] fileBytes = baos.toByteArray();
        PdfDecoder decoder = new PdfDecoder();
        decoder.openPdfArray(fileBytes);
        int noOfPage = decoder.getPageCount();
        
        String fileName = "(RESTRICTED) SOA-QPS Staff Rate Validation - " + scgStr + ".pdf";
        
        // Renew number of page in session
        request.getSession().removeAttribute("QPSES_SRV_RPT_NO_OF_PAGE");
        request.getSession().setAttribute("QPSES_SRV_RPT_NO_OF_PAGE", noOfPage + "");
        
        // Renew file bytes in session
        request.getSession().removeAttribute("QPSES_SRV_RPT_FILE_BYTES");
        request.getSession().setAttribute("QPSES_SRV_RPT_FILE_BYTES", fileBytes);
        
        // Renew file bytes in session
        request.getSession().removeAttribute("QPSES_SRV_RPT_FILE_NAME");
        request.getSession().setAttribute("QPSES_SRV_RPT_FILE_NAME", fileName);
        
        // Forward to the next page
        String url = this.UserBaseUrl + "StaffRateValidationShowImage.jsp";
        
        logger.debug(myName + " " + "Forward to URL: " + url);
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
	}
}
