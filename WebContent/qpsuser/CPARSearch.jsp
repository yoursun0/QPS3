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
					<tr> 
                        <td> <br />
                        </td>
                    </tr>
                    <tr>
                    <td colspan="2">
                        <b>To access the restricted information, you are required to provide the following information 
                        about the work assignment:</b>
                    </td> 
                	</tr>
                	<tr>
                    <td colspan="2">
                        <br />
                    </td> 
                	</tr>
                	<tr> 
                    <td width="280">The invitation issue date of the work assignment</td>
                    <td style="display:block" id="calendar_link"> 
                        <input class="text" type="text" name="IssuedDate" value="" >
                        <a href="javascript: void(0);" 
                            onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                            onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                            onClick="g_Calendar.show(event, 'form1.IssuedDate', true, 'dd-mmm-yyyy'); return false;"> 
                            <img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"> 
                        </a> 
                    </td>
                	</tr>
                	<tr> 
                    <td width="360">The proposal submission deadline of the work assignment</td>
                    <td style="display:block" id="calendar_link"> 
                        <input class="text" type="text" name="ClosingDate" value="" >
                        <a href="javascript: void(0);" 
                        onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                        onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                        onClick="g_Calendar.show(event, 'form1.ClosingDate', true, 'dd-mmm-yyyy'); return false;"><img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"></a> 
                    </td>
                	</tr>
                	
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        You may enter or edit the date in the format dd-MMM-yyyy in the above text boxes directly.
                    </td> 
                </tr>
                </table>
                
                
				<%= (msg != null) ? "<div class=\"errMessage\">"+ msg + "</div>" : ""%>
                <div align="center"><img type="image" name="Search" class="function_button" src="../images/btn_search.jpg" alt="Search" onclick="WAChallenge_BtnSearchOnClick(this)"></div>

            </form>    
            </fieldset> 
        </fieldset> 
    </body>
</html>