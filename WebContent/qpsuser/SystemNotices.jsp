<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="qpses.security.UserStatus"%>
<%@page import="qpses.util.SysManager"%>

<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    UserStatus uStatus = new UserStatus();
    uStatus            = (UserStatus) session.getAttribute("QPSES_USER_STATUS");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System - System Notices</title>
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">            
    </head>
    
    <body>
        <%@include file="include/headerOnly.jsp"%>
		
		<% if (uStatus.isForgotPassword()){ %>
		 <form name="form1" method="POST" action="ChangePassword.jsp">
		<% }else{ %>
         <form name="form1" method="POST" action="index.jsp">
        <% } %>
            <table width="100%" border="0" cellspacing="0" cellpadding="5">
                <tr>
                    <td> 
                        <table class="system_notices" width="47%" cellspacing="0" cellpadding="0" align="center" class="no_border">
                            <tr> 
                                <td width="50%" align="right"  nowrap>Last Access Date and Time</td>
                                <td width="20"  align="center" nowrap>:</td>
                                <td width="50%" align="left"   nowrap><%=(uStatus.getLastAccessAttempt()==null)?"N/A":SysManager.getStringfromSQLDateTime(uStatus.getLastAccessAttempt().toString())%></td>
                            </tr>
                        </table>                     
                        
                        <table class="system_notices" width="70%" border="0" cellspacing="0" cellpadding="0" align="center">
                            <tr> 
                                <td width="95%"> 
                                    <fieldset class="sub_function">
                                        <legend class="sub_function" align="center"><font>&nbsp;Important Notice&nbsp;</font></legend>
                                        <table class="system_notices" width="95%" border="0" cellspacing="0" cellpadding="5" align="center">
                                            <tr><td>&nbsp;</td></tr>
                                            <tr>
                                                <td width="6%" valign="top"><p>1.</p></td>
                                                <td width="94%">Please log out properly each time after using the system.</td>
                                            </tr>
                                            <tr> 
                                                <td width="6%" valign="top">2.</td>
                                                <td width="94%">Upon clicking the Continue button, you will be given access to information of RESTRICTED(CONTRACT) classification.  The information should only be disclosed on a need-to-know basis and should not be released to personnel engaged under contracts for the provision of contract staff services (the "T-" contract) or contractors working in government projects.</td>
                                            </tr>
                                            <tr> 
                                                <td width="6%" valign="top">3.</td>
                                                <td width="94%">For enquiry, please contact the QPS3-RRC Secretariat Office by Notes mail.</td>
                                            </tr>
                                            <tr><td>&nbsp;</td></tr>                            
                                        </table>
                                    </fieldset>
                                </td>
                            </tr>
                        </table>
        
                        <table width="100%" border="0" cellspacing="0" cellpadding="5">
                            <tr><td align="center"><a href="#"><img src="../images/btn_continue.jpg" alt="Continue" border="0" name="Continue" onclick="form1.submit()"></a></td></tr>
                        </table>
                    </td>
                </tr>
            </table>         
        </form>
    </body>
</html>
