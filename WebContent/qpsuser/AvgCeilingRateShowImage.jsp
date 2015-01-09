<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.Date"%>
<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

<%
    String numOfPage = (String)request.getSession().getAttribute("QPSES_ACR_RPT_NO_OF_PAGE");
    String pageNo    = request.getParameter("page_no");

    if (pageNo == null) pageNo = "1";

    String sc            = request.getParameter("sc");
    String effectiveDate = request.getParameter("effective_date");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
        <title>Average Ceiling Rate Enquiry - Preview & Download Average Ceiling Rates</title>
        <script language="javascript" type="text/javascript" src="../js/AvgCeilingRateUser.js"></script>
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">
    </head>

    <body>
        <%@include file="include/header.jsp"%>
        <form name="form" method="post" action="">
            <input type="hidden" name="page_no"        value="<%=pageNo%>">
            <input type="hidden" name="no_of_page"     value="<%=numOfPage%>">            
            <input type="hidden" name="sc"             value="<%=sc%>">
            <input type="hidden" name="effective_date" value="<%=effectiveDate%>">
            <input type="hidden" name="request_action" value="">
            
            <fieldset class="function">  
                <table class="function_title"><tr><td>Average Ceiling Rate Enquiry - Preview & Download Average Ceiling Rates</td></tr></table>  
            
                <fieldset class="sub_function">
                    <legend class="sub_function">Average Ceiling Rates Information</legend>
                    <table class="no_border" width="97.5%">
                        <tr valign="top"><td width="100"><b>Service Category</b></td><td width="2">:</td><td><%=sc%></td></tr>
                        <tr valign="top"><td            ><b>Effective Date  </b></td><td          >:</td><td><%=effectiveDate%></td></tr>
                    </table>
                </fieldset>              

                <div align="center">
                    <table class="no_border">
                        <tr>
                            <td width="49%" align="right"><img type="image" name="Back" class="function_button" src="../images/btn_back.jpg" alt="Go back to previous screen" onclick="ShowImage_BtnBackOnClick()"></td>
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
                        <tr><td colspan="3"><img src="AvgCeilingRateSLUser?request_action=getImage&sc=<%=sc%>&effective_date=<%=effectiveDate%>&page_no=<%=pageNo%>" border="1"></td></tr>
                    </table>
                </div> 
            </fieldset>
        </form>
    </body>
</html>   