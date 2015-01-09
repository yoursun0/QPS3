<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
        <title>Average Ceiling Rate Enquiry - Select Service Category</title>
        <script language="javascript" type="text/javascript" src="../js/AvgCeilingRateUser.js"></script>
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">      
    </head>

    <body>
        <%@include file="include/header.jsp"%>     
        <form name="form" method="post" action="">
            
            <input type="hidden" name="sc"             value="">
            <input type="hidden" name="effective_date" value="">
            <input type="hidden" name="request_action" value="">

            <fieldset class="function">  
            <table class="function_title"><tr><td>Average Ceiling Rate Enquiry - Select Service Category</td></tr></table>  
             
            <fieldset class="sub_function">
                <table class="no_border">
                    <tr><td><li>Please click on a service category to enquire the prevailing average ceiling rates for that service category</li></td></tr>
                    <tr><td><li>Explanatory notes on staff categories can be downloaded from <a class="list" target="_self" href="#" onclick="GetInfo_InfoPdfOnClick()" title="Download Ceiling Rate Type And Standard Staff Category">here</a></li></td></tr>
                    </table>
            </fieldset>
            
            <fieldset class="sub_function">       
                <%
                    List avgCRInfoList = (List)request.getSession().getAttribute("avgCRInfoEff");

                    for (int i = 1; i <= 4; i++)
                    {
                %>
                <table class="no_border">
                    <tr>
                        <td>
                            <%
                                int count = 0;

                                for (int j = 0; j < avgCRInfoList.size(); j++)
                                {
                                    AvgCeilingRateInfo avgCRInfo = (AvgCeilingRateInfo)avgCRInfoList.get(j);

                                    if (avgCRInfo.getServiceCategory() == i)
                                    {
                                        count++;

                                        String dateStr = SysManager.getStringfromSQLDate(avgCRInfo.getEffectiveDate());
                            %>
                            <a class="list" href="#" OnClick="GetInfo_SCLinkOnClick('<%=dateStr%>','<%=i%>')">Service Category <%=i%> (effective as at <%=dateStr%>)</a><br>
                            <%
                                } // end if
                                } // end for j

                                if (count == 0)
                                { %><li><font color="red">Not Available</font></li><% }
                            %>
                        </td>
                    </tr>
                    <% } // end for i %> 
                </table>

            </fieldset>
        </form>
    </body>
</html>
   