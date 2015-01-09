<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.Date"%>
<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

<%
    String numOfPage = (String)request.getSession().getAttribute("QPSES_DR_RPT_NO_OF_PAGE");
    String pageNo    = request.getParameter("page_no");

    if (pageNo == null) pageNo = "1";
   
    String scg           = request.getParameter("scg");
    String effectiveDate = request.getParameter("effective_date");

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
    
    //String sg     = scg.substring(2, 3);
    //String scgStr = "Service Category " + sc + " " + (sg.equals("J") ? "Major" : "Minor") + " Group";
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
        <title>Discount Rate Enquiry - Preview & Download Discount Rates File</title>
        <script language="javascript" type="text/javascript" src="../js/DiscountRateUser.js"></script>
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">
    </head>

    <body>
        <%@include file="include/header.jsp"%>
        <form name="form" method="post" action="">            
            <input type="hidden" name="page_no"        value="<%=pageNo%>">
            <input type="hidden" name="no_of_page"     value="<%=numOfPage%>">
            <input type="hidden" name="scg"            value="<%=scg%>">
            <input type="hidden" name="effective_date" value="<%=effectiveDate%>">
            <input type="hidden" name="request_action" value="">
            
            <fieldset class="function">  
                <table class="function_title"><tr><td>Discount Rate Enquiry - Preview & Download Discount Rates File</td></tr></table>  
             
                <fieldset class="sub_function">
                    <legend class="sub_function"> Discount Rates Information</legend>
                    <table class="no_border" width="97.5%">
                        <tr valign="top"><td width="135"><b>Service Category/Group</b></td><td width="2">:</td><td><%=scgStr%></td></tr>
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
                        <tr><td colspan="3"><img src="DiscountRateSLUser?request_action=getImage&scg=<%=scg%>&effective_date=<%=effectiveDate%>&page_no=<%=pageNo%>" border="1"></td></tr>
                    </table> 
                </div>
                
            </fieldset>
        </form>
    </body>
</html> 