<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.ACLInfo,
                 qpses.business.ACLDataBean,
				 qpses.business.WAChallengeInfo,
                 qpses.security.UserStatus,
                 qpses.util.SysManager"%>
<%
    // check  for message and get last input from session variables
    WAChallengeInfo wac = (WAChallengeInfo) session.getAttribute("QPSES_WA_CHALLENGE");
    String msg          = (String) session.getAttribute("WA_CHALLENGE_MSG");
    
    if (!"".equals(getValue(msg))){
        session.removeAttribute("WA_CHALLENGE_MSG");
    }

    String waType = (String)request.getSession().getAttribute("WA_TYPE");

    if (waType == null)
    { throw new Exception("Session [WAType] is NULL"); }   
    
  
%>  
      
    
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System - CPAR Creation</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">                  
        <link rel="stylesheet" type="text/css" href="../styles/calendar.css">    
        <script language="javascript" type="text/javascript" src="../js/simplecalendar.js"></script>
        <script language="javascript" type="text/javascript" src="../js/CPARCreation.js"></script>                              
    </head>
    <body>
        <%@include file="include/header.jsp"%>

        <fieldset class="function"> 
            <table class="function_title"><tr><td>Contractor Performance Appraisal Report</td></tr></table>       
            <fieldset class="sub_function"> 
                <legend class="sub_function">Work Assignment Search</legend>                
                 		        
            <form name="form1" method="POST" action="">            
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableNoBorder" align="center">
                    <tr>
                        <td> 
                            Service Contract Ref. No: <b>GCIO 5/11-</b>
                            <input name="ServiceContractNo" type="text" class="text" value="" />
                        </td>
                    </tr>
                </table>
                
				<%= (msg != null) ? "<div class=\"errMessage\">"+ msg + "</div>" : ""%>
                <div align="center"><img type="image" name="Search" class="function_button" src="../images/btn_search.jpg" alt="Search" onclick="AdminWAChallenge_BtnSearchOnClick(this)"></div>

            </form>    
            </fieldset> 
        </fieldset> 
    </body>
</html>
