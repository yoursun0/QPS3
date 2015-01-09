<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.ACLInfo,
                 qpses.business.ACLDataBean,
				 qpses.business.WAChallengeInfo,
				 qpses.business.CPARInfo,
                 qpses.security.UserStatus,
                 qpses.util.SysManager"%>
                 
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // check  for message and get last input from session variables
    WAChallengeInfo wac = (WAChallengeInfo) session.getAttribute("QPSES_WA_CHALLENGE");
    CPARInfo cpar  = (CPARInfo) session.getAttribute("QPSES_CPAR");
    String msg     = (String) session.getAttribute("CPAR_MSG");
    String msgtype = getValue((String) session.getAttribute("CPAR_MSGTYPE"));
    int cparNo = cpar.getCparNo();
    
    if (!"".equals(getValue(msg))){
        session.removeAttribute("CPAR_MSG");
    }
    
    String awardedDate = SysManager.getStringfromSQLDate(wac.getAwardedDate());
    String nextDate = (String) session.getAttribute("CPAR_NEXTDATE");
    if (!"".equals(getValue(nextDate))){
        session.removeAttribute("CPAR_NEXTDATE");
    }
    
    String authorizedPerson = getValue(cpar.getAuthorizedPerson());
    String postRank = getValue(cpar.getPostRank());
  
%>  
    
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System - CPAR Creation</title>
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">
        <link rel="stylesheet" type="text/css" href="../styles/style.css">                  
        <link rel="stylesheet" type="text/css" href="../styles/calendar.css">    
        <script language="javascript" type="text/javascript" src="../js/simplecalendar.js"></script>
        <script language="javascript" type="text/javascript" src="../js/CPARCreation.js"></script>
    </head>
    <body>
        <%@include file="include/header.jsp"%>

        <fieldset class="function"> 
            <table class="function_title"><tr><td>Contractor Performance Appraisal Report</td></tr></table>     
            
            <fieldset class="sub_function">
                    <legend class="sub_function">Service Contract Information</legend>
                    <table class="no_border" width="97.5%">
                        <tr valign="top"><td width="195"><b>Service Category/Group          </b></td><td width="2">:</td><td><%=wac.getServiceCategoryGroup() %></td></tr>
                        <tr valign="top"><td            ><b>Service Contract Ref. No        </b></td><td width="2">:</td><td><%=wac.getServiceContractNo() %></td></tr>
                        <tr valign="top"><td            ><b>Work Assignment Title           </b></td><td          >:</td><td><%=wac.getTitle() %></td></tr>
                        <tr valign="top"><td            ><b>Bureau/Department               </b></td><td          >:</td><td><%=wac.getDepartmentName() %></td></tr>
                        <tr valign="top"><td            ><b>Awarded Date                    </b></td><td          >:</td><td><%=awardedDate %></td></tr>
                        <tr valign="top"><td            ><b>Awarded Contractor              </b></td><td          >:</td><td><%=wac.getAwardedContractor() %></td></tr>
                    </table>
            </fieldset>
            
            <form name="form1" method="POST" action="">  
            <fieldset class="sub_function"> 
                <legend class="sub_function">Period and Type of Assessment</legend>                
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableNoBorder" align="center">
                	<tr> 
                    <td width="280">CPAR start date:</td>
                    <td style="display:block" id="calendar_link"> 
                    	<% if("".equals(getValue(nextDate))){ %>
                        <input class="text" type="text" name="IssuedDate" value="<%=SysManager.getStringfromSQLDate(cpar.getStartDate()) %>" readonly>
                        <a href="javascript: void(0);" 
                            onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                            onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                            onClick="g_Calendar.show(event, 'form1.IssuedDate', true, 'dd-mmm-yyyy'); return false;"> 
                            <img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"> 
                        </a> 
                        <% }else{ %>
                        	<input class="text" type="text" name="IssuedDate" value="<%=getValue(nextDate) %>" readonly>
                        <% } %>
                    </td>
                	</tr>
                	<tr> 
                    <td width="360">CPAR end date:</td>
                    <td style="display:block" id="calendar_link"> 
                        <input class="text" type="text" name="ClosingDate" value="<%=SysManager.getStringfromSQLDate(cpar.getEndDate()) %>" readonly>
                        <a href="javascript: void(0);" 
                        onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                        onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                        onClick="g_Calendar.show(event, 'form1.ClosingDate', true, 'dd-mmm-yyyy'); return false;"><img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"></a> 
                    </td>
                	</tr>
                	<tr>
                        <td width="360"> 
                            CPAR status:<br />
                        </td>
                        <td>
                            <input name="CparStatus" type="radio" class="text" value="c" <%=("c".equals(cpar.getStatus()))?"checked":"" %>/>&nbsp;&nbsp;<b>Completed/Terminated</b> Service Contract
                            <br />
                            <input name="CparStatus" type="radio" class="text" value="p" <%=("p".equals(cpar.getStatus()))?"checked":"" %>/>&nbsp;&nbsp;<b>In-progress</b> Service Contract
                        </td>
                    </tr>
                	<tr>
                        <td width="360"> 
                            Authorised Person:<br />
                        </td>
                        <td>
                            <input name="AuthorizedPerson" type="text" class="text" value="<%=("".equals(authorizedPerson))?getValue(wac.getAuthorizedPerson()):authorizedPerson %>" />
                        </td>
                    </tr>
                    <tr>
                        <td width="360"> 
                            Post / Rank:<br />
                        </td>
                        <td>
                            <input name="PostRank" type="text" class="text" value="<%=("".equals(postRank))?getValue(wac.getPostRank()):postRank %>" />
                        </td>
                    </tr>
                	<tr>
                    <td colspan="2">
                    	<br />
                        <i style="color: blue;">The Authorised Person information would be updated for this CPAR only. Should you need to change the Authorised Person information permanently, please contact QPS3 Contract Administrator by email to QPS3 Contract Administrator/OGCIO/HKSARG.</i>
                    </td> 
                	</tr>
                </table>
                
            </fieldset>
            
            <!-- Start of (A) Delivery of Work -->
            <fieldset>
              <legend class="sub_function">(A) Delivery of Work</legend>
              <table cellspacing="0" class="tableCpar" border="1" width="100%" >
                <thead>
                  <tr>
                    <th width="50%">&nbsp;</th>
                    <th width="10%">Good</th>
                    <th width="10%">Satisfactory</th>
                    <th width="10%">Fair</th>
                    <th width="10%">Poor</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>1.	Effectiveness in planning, scheduling and monitoring</td>
                    <td align="center"><input type="radio" name="table1q1" value="4" <%=(cpar.getA1Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q1" value="3" <%=(cpar.getA1Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q1" value="2" <%=(cpar.getA1Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q1" value="0" <%=(cpar.getA1Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>2.	Completion of major tasks/milestones/deliverables on schedule, including the administrative aspects (e.g., project progress reports, minutes of meeting, etc.)</td>
                    <td align="center"><input type="radio" name="table1q2" value="4" <%=(cpar.getA2Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q2" value="3" <%=(cpar.getA2Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q2" value="2" <%=(cpar.getA2Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q2" value="0" <%=(cpar.getA2Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>3.	Ability to identify risk factors and alternatives for alleviating risk</td>
                    <td align="center"><input type="radio" name="table1q3" value="4" <%=(cpar.getA3Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q3" value="3" <%=(cpar.getA3Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q3" value="2" <%=(cpar.getA3Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q3" value="0" <%=(cpar.getA3Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>4.	Ability to manage changes</td>
                    <td align="center"><input type="radio" name="table1q4" value="4" <%=(cpar.getA4Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q4" value="3" <%=(cpar.getA4Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q4" value="2" <%=(cpar.getA4Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q4" value="0" <%=(cpar.getA4Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>5.	Ability to control cost/resources to complete the services</td>
                    <td align="center"><input type="radio" name="table1q5" value="4" <%=(cpar.getA5Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q5" value="3" <%=(cpar.getA5Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q5" value="2" <%=(cpar.getA5Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q5" value="0" <%=(cpar.getA5Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>6.	Follow-up on issues and problems identified</td>
                    <td align="center"><input type="radio" name="table1q6" value="4" <%=(cpar.getA6Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q6" value="3" <%=(cpar.getA6Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q6" value="2" <%=(cpar.getA6Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q6" value="0" <%=(cpar.getA6Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>7.	Effective communication with the Government</td>
                    <td align="center"><input type="radio" name="table1q7" value="4" <%=(cpar.getA7Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q7" value="3" <%=(cpar.getA7Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q7" value="2" <%=(cpar.getA7Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q7" value="0" <%=(cpar.getA7Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>8.	Promptness in responding to client’s requests and enquiries</td>
                    <td align="center"><input type="radio" name="table1q8" value="4" <%=(cpar.getA8Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q8" value="3" <%=(cpar.getA8Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q8" value="2" <%=(cpar.getA8Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q8" value="0" <%=(cpar.getA8Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>9.	Effective interactions & co-ordination with other third party, i.e., contractors, suppliers or other Government Bureaux/Departments</td>
                    <td align="center"><input type="radio" name="table1q9" value="4" <%=(cpar.getA9Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q9" value="3" <%=(cpar.getA9Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q9" value="2" <%=(cpar.getA9Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table1q9" value="0" <%=(cpar.getA9Score() == 0)?"checked":"" %> ></td>
                  </tr>
                </tbody>
              </table>
            </fieldset>
            <!-- End of (A) Delivery of Work -->
            <br >
            <!-- Start of (B) Quality of Work -->
            <fieldset>
              <legend class="sub_function">(B) Quality of Work</legend>
              <table cellspacing="0" class="tableCpar" border="1" width="100%" >
                <thead>
                  <tr>
                    <th width="50%">&nbsp;</th>
                    <th width="10%">Good</th>
                    <th width="10%">Satisfactory</th>
                    <th width="10%">Fair</th>
                    <th width="10%">Poor</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>1.	Quality of deliverables including the administrative aspects (e.g., project progress reports, minutes of meeting, etc.)</td>
                    <td align="center"><input type="radio" name="table2q1" value="4" <%=(cpar.getB1Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q1" value="3" <%=(cpar.getB1Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q1" value="2" <%=(cpar.getB1Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q1" value="0" <%=(cpar.getB1Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>2.	Knowledge of work to be performed</td>
                    <td align="center"><input type="radio" name="table2q2" value="4" <%=(cpar.getB2Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q2" value="3" <%=(cpar.getB2Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q2" value="2" <%=(cpar.getB2Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q2" value="0" <%=(cpar.getB2Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>3.	Ability to observe Government regulations, procedures and standards </td>
                    <td align="center"><input type="radio" name="table2q3" value="4" <%=(cpar.getB3Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q3" value="3" <%=(cpar.getB3Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q3" value="2" <%=(cpar.getB3Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q3" value="0" <%=(cpar.getB3Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>4.	Ability to employ and adhere to professional standards and methods</td>
                    <td align="center"><input type="radio" name="table2q4" value="4" <%=(cpar.getB4Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q4" value="3" <%=(cpar.getB4Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q4" value="2" <%=(cpar.getB4Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q4" value="0" <%=(cpar.getB4Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>5.	Ability to appreciate business environment needs and challenge </td>
                    <td align="center"><input type="radio" name="table2q5" value="4" <%=(cpar.getB5Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q5" value="3" <%=(cpar.getB5Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q5" value="2" <%=(cpar.getB5Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table2q5" value="0" <%=(cpar.getB5Score() == 0)?"checked":"" %> ></td>
                  </tr>
                </tbody>
              </table>
            </fieldset>
             <!-- End of (B) Quality of Work -->
             <br >
             <!-- Start of (C) Managing of Resources -->
            <fieldset>
              <legend class="sub_function">(C) Managing of Resources</legend>
              <table cellspacing="0" class="tableCpar" border="1" width="100%" >
                <thead>
                  <tr>
                    <th width="50%">&nbsp;</th>
                    <th width="10%">Good</th>
                    <th width="10%">Satisfactory</th>
                    <th width="10%">Fair</th>
                    <th width="10%">Poor</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>1.	Adequacy of project team members</td>
                    <td align="center"><input type="radio" name="table3q1" value="4" <%=(cpar.getC1Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q1" value="3" <%=(cpar.getC1Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q1" value="2" <%=(cpar.getC1Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q1" value="0" <%=(cpar.getC1Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>2.	Effectiveness and reliability of project team</td>
                    <td align="center"><input type="radio" name="table3q2" value="4" <%=(cpar.getC2Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q2" value="3" <%=(cpar.getC2Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q2" value="2" <%=(cpar.getC2Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q2" value="0" <%=(cpar.getC2Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>3.	Skills and experience of project team members</td>
                    <td align="center"><input type="radio" name="table3q3" value="4" <%=(cpar.getC3Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q3" value="3" <%=(cpar.getC3Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q3" value="2" <%=(cpar.getC3Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q3" value="0" <%=(cpar.getC3Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>4.	Continuity of project team members</td>
                    <td align="center"><input type="radio" name="table3q4" value="4" <%=(cpar.getC4Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q4" value="3" <%=(cpar.getC4Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q4" value="2" <%=(cpar.getC4Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q4" value="0" <%=(cpar.getC4Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>5.	Ability to mitigate impact of staff turnover</td>
                    <td align="center"><input type="radio" name="table3q5" value="4" <%=(cpar.getC5Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q5" value="3" <%=(cpar.getC5Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q5" value="2" <%=(cpar.getC5Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table3q5" value="0" <%=(cpar.getC5Score() == 0)?"checked":"" %> ></td>
                  </tr>
                </tbody>
              </table>
            </fieldset>
             <!-- End of (C) Managing of Resources -->
             <br >
             <!-- Start of (D) Overall Assessment -->
            <fieldset>
              <legend class="sub_function">(D) Overall Assessment</legend>
              <table cellspacing="0" class="tableCpar" border="1" width="100%" >
                <thead>
                  <tr>
                    <th width="50%">&nbsp;</th>
                    <th width="10%">Good</th>
                    <th width="10%">Satisfactory</th>
                    <th width="10%">Fair</th>
                    <th width="10%">Poor</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>1.	Delivery of Work</td>
                    <td align="center"><input type="radio" name="table4q1" value="4" <%=(cpar.getD1Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table4q1" value="3" <%=(cpar.getD1Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table4q1" value="2" <%=(cpar.getD1Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table4q1" value="0" <%=(cpar.getD1Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>2.	Quality of Work</td>
                    <td align="center"><input type="radio" name="table4q2" value="4" <%=(cpar.getD2Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table4q2" value="3" <%=(cpar.getD2Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table4q2" value="2" <%=(cpar.getD2Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table4q2" value="0" <%=(cpar.getD2Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>3.	Managing of Resources</td>
                    <td align="center"><input type="radio" name="table4q3" value="4" <%=(cpar.getD3Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table4q3" value="3" <%=(cpar.getD3Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table4q3" value="2" <%=(cpar.getD3Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table4q3" value="0" <%=(cpar.getD3Score() == 0)?"checked":"" %> ></td>
                  </tr>
                </tbody>
              </table>
            </fieldset>
             <!-- End of (D) Overall Assessment -->
            <br >
            <!-- Start of Client FeedBack on SOA-QPS2 Support Services (Optional) -->
            <fieldset>
              <legend class="sub_function">Client Feedback on SOA-QPS3 Support Services (Optional)</legend>
              <p class="NormalParagraph">Please provide further feedback for improvement of SOA-QPS3.
              <br >This section is optional. You may proceed without answering all the questions.</p>
              <table cellspacing="0" class="tableCpar" border="1" width="100%" >
                <thead>
                  <tr>
                    <th width="50%">&nbsp;</th>
                    <th width="10%">Strongly Agree</th>
                    <th width="10%">Agree</th>
                    <th width="10%">Disagree</th>
                    <th width="10%">Strongly Disagree</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>1.	Do you agree that the information provided on SOA-QPS3 Theme Page is adequate and clear enough?</td>
                    <td align="center"><input type="radio" name="table5q1" value="4" <%=(cpar.getE1Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q1" value="3" <%=(cpar.getE1Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q1" value="2" <%=(cpar.getE1Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q1" value="0" <%=(cpar.getE1Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>2.	Do you agree that the SOA-QPS3 central administration support provided by the OGCIO is adequate?</td>
                    <td align="center"><input type="radio" name="table5q2" value="4" <%=(cpar.getE2Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q2" value="3" <%=(cpar.getE2Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q2" value="2" <%=(cpar.getE2Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q2" value="0" <%=(cpar.getE2Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>3.	All in all, do you agree that the services provided by the SOA-QPS3 are satisfactory?</td>
                    <td align="center"><input type="radio" name="table5q3" value="4" <%=(cpar.getE3Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q3" value="3" <%=(cpar.getE3Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q3" value="2" <%=(cpar.getE3Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q3" value="0" <%=(cpar.getE3Score() == 0)?"checked":"" %> ></td>
                  </tr>
                  <tr>
                    <td>4.	Do you agree that the SOA-QPS3 is a preferred means to acquire IT professional services for future projects?</td>
                    <td align="center"><input type="radio" name="table5q4" value="4" <%=(cpar.getE4Score() == 4)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q4" value="3" <%=(cpar.getE4Score() == 3)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q4" value="2" <%=(cpar.getE4Score() == 2)?"checked":"" %> ></td>
                    <td align="center"><input type="radio" name="table5q4" value="0" <%=(cpar.getE4Score() == 0)?"checked":"" %> ></td>
                  </tr>
                </tbody>
              </table>
            </fieldset>
             <!-- End of Client FeedBack on SOA-QPS2 Support Services (Optional) -->
             
             
            <table border="0" cellpadding="3" align="center">
            		<tr>
            			<%= (msg != null) ? "<div class=\"errMessage\">"+ msg + "</div>" : ""%>
            		</tr>
                    <tr>
                        <td>
                            <div align="center"><img type="image" name="Cancel" class="function_button" src="../images/btn_cancel.jpg" alt="Cancel" onclick="selectaction(form1,this,<%=cparNo %>)"></div>
                        </td>					 
                        <td >                            
                            <div align="center"><img type="image" name="AdminSave" class="function_button" src="../images/btn_save.jpg" alt="AdminSave" onclick="selectaction(form1,this,<%=cparNo %>)"></div>
                        </td>
						<%if ( !("r".equals(cpar.getFinalized())) ){%>
                        <td>
                            <div align="center"><img type="image" name="ReleaseInUpdatePage" class="function_button" src="../images/btn_release.jpg" alt="Release" onclick="selectaction(form1,this,<%=cparNo %>)"></div>
                        </td>
                        <% } %>
                    </tr>
                </table>
                <input type="hidden" name="PostScreen" value="CPARList.servlet">
                <input type="hidden" name="cparNo" value="">
                <input type="hidden" name="finalized" value="<%=cpar.getFinalized() %>">
                <input type="hidden" name="create" value="<%=msgtype %>">
            </form> 
        </fieldset> 
    </body>
</html>
