<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.CeilingRateInfo,
                                qpses.util.SysManager" %>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
        <%
            // get imported service Category
            CeilingRateInfo cr = (CeilingRateInfo) session.getAttribute("PRINT_CEILING_RATE_DATA");
            String service_category = cr.getServiceCategory();
            String effective_date = SysManager.getStringfromSQLDate(cr.getEffectiveDate());            

            // check and display for message
            String msg =   getValue((String)  session.getAttribute("IMPORT_CEILING_RATE_MSG"));
            
        %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Ceiling Rate Import Acknowledgement</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">                
        <script language="JavaScript" src="../js/ceilingratecheck.js" type="text/javascript"></script>        
        <script language="JavaScript" src="../js/nw.js" type="text/javascript"></script>        
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Ceiling Rate Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">                
                <tr> 
                    <td> 
                        <div align="center"  class="errMessage"><%=msg%> <br>
                            <br>
                            <a href="#"><img src="../images/btn_print.jpg" width="98" height="42" alt="Print Imported Report" border="0" name="Continue" onClick="selectaction(form1,this)"></a>
                            <a href="#"><img src="../images/btn_continue.jpg" width="98" height="42" alt="Return to Ceiling Rate List" border="0" name="Return" onClick="selectaction(form1,this)"></a>                             
                        </div>
                    </td>
                </tr>
                <input type="hidden" name="PostScreen" value="CeilingRateList.jsp">                 
            </form>
        </fieldset>
    </body>
</html>
