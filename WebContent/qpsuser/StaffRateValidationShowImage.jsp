<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.Date"%>
<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

<%
    String waType = (String)request.getSession().getAttribute("WA_TYPE");

    if (waType == null)
    { throw new Exception("Session [WAType] is NULL"); }   

   String waTypeStr = (waType.equals("proposal") ? "Proposal Evaluation" : "Change Request");
    
    String numOfPage = (String)request.getSession().getAttribute("QPSES_SRV_RPT_NO_OF_PAGE");
    String pageNo    = request.getParameter("page_no");

    if (pageNo == null) pageNo = "1";

    String allStaffRates = request.getParameter("all_staff_rates");
    if (allStaffRates == null) allStaffRates = "";
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
        <title>Staff Rate Validation for <%=waTypeStr%> - Preview & Download Staff Rate Validation Report</title>
        <script language="javascript" type="text/javascript" src="../js/StaffRateValidationUser.js"></script>
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">
    </head>

    <body>
        <%@include file="include/header.jsp"%>
        <form name="form" method="post" action="">            
            <input type="hidden" name="page_no"        value="<%=pageNo%>">
            <input type="hidden" name="no_of_page"     value="<%=numOfPage%>">
            <input type="hidden" name="request_action"       value=""> 
            <input type="hidden" name="show_subscore_rpt"    value="<%=request.getParameter("show_subscore_rpt")%>">
            <input type="hidden" name="show_validation_rpt"  value="<%=request.getParameter("show_validation_rpt")%>">
            <input type="hidden" name="contractor_id_list"   value="<%=request.getParameter("contractor_id_list")%>">
            <input type="hidden" name="contractor_list"      value="<%=request.getParameter("contractor_list")%>">
            <input type="hidden" name="d_contractor_id_list" value="<%=request.getParameter("d_contractor_id_list")%>">
            <input type="hidden" name="d_contractor_list"    value="<%=request.getParameter("d_contractor_list")%>">
            <input type="hidden" name="sc"                   value="<%=request.getParameter("sc")%>">
            <input type="hidden" name="sg"                   value="<%=request.getParameter("sg")%>">
            <input type="hidden" name="scg"                  value="<%=request.getParameter("scg")%>">
            <input type="hidden" name="scg_name"             value="<%=request.getParameter("scg_name")%>">
            <input type="hidden" name="dept_id"              value="<%=request.getParameter("dept_id")%>">
            <input type="hidden" name="dept_name"            value="<%=request.getParameter("dept_name")%>">
            <input type="hidden" name="closing_date"         value="<%=request.getParameter("closing_date")%>">
            <input type="hidden" name="wa_title"             value="<%=request.getParameter("wa_title").replaceAll("\"", "&quot;")%>">
            <input type="hidden" name="wa_file_part"         value="<%=request.getParameter("wa_file_part")%>">
            <input type="hidden" name="wa_file_no"           value="<%=request.getParameter("wa_file_no")%>"> 
            <input type="hidden" name="wa_awarded_date"      value="<%=request.getParameter("wa_awarded_date")%>">
            <input type="hidden" name="wa_authorized_person" value="<%=request.getParameter("wa_authorized_person")%>">                
            <input type="hidden" name="m_scheme"             value="<%=request.getParameter("m_scheme")%>">
            <input type="hidden" name="all_staff_rates"      value="<%=allStaffRates%>">            
            <input type="hidden" name="wa_awarded_contractor" value="<%=request.getParameter("wa_awarded_contractor")%>">  
            
            <fieldset class="function">
                <table class="function_title"><tr><td>Staff Rate Validation for <%=waTypeStr%> - Preview & Download Staff Rate Validation Report</td></tr></table>  

                <div align="center">
                    <table class="no_border">
                        <tr>
                            <td width="49%" align="right"><img type="image" name="Cancel" class="function_button" src="../images/btn_back.jpg" alt="Go back to previous screen" onclick="ShowImage_BtnBackOnClick()"></td>
                            <td>&nbsp;</td>
                            <td width="49%" align="left"><img type="image" name="Cancel" class="function_button" src="../images/btn_download.jpg" alt="Download PDF File" onclick="ShowImage_DownloadFile()"></td>
                        </tr>
                        <tr>
                            <td align="center" colspan="3">Page&nbsp;:
                                <b>
                                    <% for (int i = 1; i <= Integer.parseInt(numOfPage); i++)
                                        {
                                    %>
                                    &nbsp;
                                    <% 
                                        if (i != Integer.parseInt(pageNo) )
                                        { %> <a class="list" href="#" OnClick="ShowImage_PageNoOnClick('<%=i%>')"><%=i%></a> <% }
                                        else
                                        { %> <%=i%> <% } %>
                                    &nbsp;
                                    <%
                                        }
                                    %>
                                </b>
                            </td>
                        </tr>
                        <tr><td colspan="3"><img src="StaffRateValidationSLUserWAC?request_action=getImage&page_no=<%=pageNo%>" border="1"></td></tr>
                    </table>
                </div>            
            </fieldset>
        </form>
    </body>
</html> 