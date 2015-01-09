<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

<%
    String waType = (String)request.getSession().getAttribute("WA_TYPE");

    if (waType == null)
    { throw new Exception("Session [WAType] is NULL"); }   

    String waTypeStr = (waType.equals("proposal") ? "Proposal Evaluation" : "Change Request");
   
    WAChallengeInfo wac = (WAChallengeInfo) session.getAttribute("QPSES_WA_CHALLENGE");
    String msg          = (String) session.getAttribute("WA_CHALLENGE_MSG");

    int challengeNo = wac.getChallengeNo();
    String scg      = wac.getServiceCategoryGroup();
    String deptName = (String)request.getSession().getAttribute("SRV_DEPT_NAME");
    String waTitle  = wac.getTitle();

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

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Rate Validation for <%=waTypeStr%> - Work Assignment Challenge</title>
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">
        <link rel="stylesheet" type="text/css" href="../styles/calendar.css">    
        <script language="javascript" type="text/javascript" src="../js/simplecalendar.js"></script>        
        <script language="javascript" type="text/javascript" src="../js/StaffRateValidationUser.js"></script>        
    </head>
    
    <body style="display:none" id="form">
        <%@include file="include/header.jsp"%>     
        <form  name="form" method="post" action="WAChallengeValidation.servlet" target="_self">
        
            <input type="hidden" name="forwardScreen"      value="<%=wac.getForwardScreen()%>">
            <input type="hidden" name="ChallengeNo"        value="<%=challengeNo%>">
            <input type="hidden" name="wa_type"            value="<%=waType%>">
            <input type="hidden" name="DebarredContractor" value="">
            
            
            <fieldset class="function"> 
            <table class="function_title">
                <tr><td>Staff Rate Validation for <%=waTypeStr%> - Work Assignment Specific Question</td></tr>                 
            </table>
                        
            <fieldset class="sub_function"> 
                <legend class="sub_function">Work Assignment Information</legend>                
                <table class="no_border" width="97.5%">
                    <tr valign="top"> 
                        <td width="135"><b>Service Category/Group</b></td>
                        <td width="2">:</td>
                        <td><%=scgStr%></td>
                    </tr>
                    <tr valign="top"> 
                        <td><b>Work Assignment Title </b></td>
                        <td>:</td>
                        <td><%=waTitle%></td>
                    </tr>
                    <tr valign="top"> 
                        <td><b>Bureau/Department </b></td>
                        <td>:</td>
                        <td><%=deptName%></td>
                    </tr>
                </table>
            </fieldset> 

            <table class="no_border" width="98%">    
                <tr>
                    <td colspan="2">
                        <b>To access the restricted information, you are required to provide the following information 
                        about the work assignment:</b>
                    </td> 
                </tr>
                <tr> 
                    <td width="280">The invitation issue date of the work assignment</td>
                    <td style="display:block" id="calendar_link"> 
                        <input class="text" type="text" name="IssuedDate" value="" >
                        <a href="javascript: void(0);" 
                            onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                            onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                            onClick="g_Calendar.show(event, 'form.IssuedDate', true, 'dd-mmm-yyyy'); return false;"> 
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
                        onClick="g_Calendar.show(event, 'form.ClosingDate', true, 'dd-mmm-yyyy'); return false;"><img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"></a> 
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
			
            <%= (msg != null) ? "<table width=\"98%\" class=\"no_border\"><tr align=\"center\"><td><font color=\"red\">" + msg + "</font></td></tr></table>" : ""%>
            
            <table class="no_border" width="98%">
                <tr> 
                    <td align="right"><img type="image" name="Back" class="function_button" src="../images/btn_back.jpg" alt="Go back to previous screen" onclick="WAChallenge_BtnBackOnClick(this)"></td>
                    <td>&nbsp;</td>
                    <td align="left"><img type="image" name="Next" class="function_button" src="../images/btn_next.jpg" alt="Go to next screen" onclick="WAChallenge_BtnNextOnClick(this)"></td>
                </tr>
            </table>
           
        </form>
    </body>
</html>

<script language="javascript">
    var calendarLinkObj = document.getElementById("calendar_link");

    if (calendarLinkObj != null)
        calendarLinkObj.style.display = "block";    
        
    var thisForm = document.form; 

    var wa_type = thisForm.wa_type.value;
    
    if (wa_type == "change_request") 
        thisForm.submit();        
    else
        document.getElementById('form').style.display = 'block';   
</script>