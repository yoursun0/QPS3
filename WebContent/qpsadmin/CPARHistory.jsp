<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.List,java.util.ArrayList"%>
<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%

        WAChallengeInfo wac = (WAChallengeInfo)session.getAttribute("QPSES_WA_CHALLENGE");
        if (wac == null)
        {throw new SysException("Unable to get work assignment info.");}           
        
        CPARInfo cpar = (CPARInfo)session.getAttribute("QPSES_CPAR");
        if (cpar == null)
        {throw new SysException("Unable to get CPAR info.");}      
        
        List<CPARInfo> allCPARHistoryList = (List<CPARInfo>) session.getAttribute("QPSES_CPAR_HISTORY");
        if (allCPARHistoryList == null)
        {throw new SysException("Unable to get CPAR History info.");}      
        
     	// check and display for message
        String msg =   getValue((String)  session.getAttribute("CPAR_MSG"));
        if (!msg.equals("")){
            session.removeAttribute("CPAR_MSG");
            session.removeAttribute("CPAR_MSGTYPE");
        }
        
        String cparStatus = ("p".equals(cpar.getStatus()))?"In-progress":(("c".equals(cpar.getStatus()))?"Completed":"N/A");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contractor Performance Appraisal Report History</title>
        <script language="javascript" type="text/javascript" src="../js/CPARCreation.js"></script>
        <script language="javascript" type="text/javascript" src="../js/nw.js"></script>           
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">
        <link rel="stylesheet" type="text/css" href="../styles/style.css">
    </head>
    <body>
        <%@include file="include/header.jsp"%> 
    
        <fieldset class="function">  
            <table class="function_title"><tr><td>Contractor Performance Appraisal Report - CPAR History</td></tr></table>

            <fieldset class="sub_function">
                    <legend class="sub_function">CPAR Information</legend>
                    <table class="no_border" width="97.5%">
                        <tr valign="top"><td width="195"><b>Service Category/Group          </b></td><td width="2">:</td><td><%=wac.getServiceCategoryGroup() %></td></tr>
                        <tr valign="top"><td            ><b>Service Contract Ref. No        </b></td><td width="2">:</td><td><%=wac.getServiceContractNo() %></td></tr>
                        <tr valign="top"><td            ><b>Work Assignment Title           </b></td><td          >:</td><td><%=wac.getTitle() %></td></tr>
                        <tr valign="top"><td            ><b>Bureau/Department               </b></td><td          >:</td><td><%=wac.getDepartmentName() %></td></tr>
                        <tr valign="top"><td            ><b>CPAR No                       </b></td><td          >:</td><td><%=cpar.getCparNo() %></td></tr>
                        <tr valign="top"><td            ><b>CPAR Status                    </b></td><td          >:</td><td><%=cparStatus %></td></tr>
                        <tr valign="top"><td            ><b>Post / Rank                     </b></td><td          >:</td><td><%=cpar.getPostRank() %></td></tr>
                        <tr valign="top"><td            ><b>Start Date                    </b></td><td          >:</td><td><%=cpar.getStartDate() %></td></tr>
                        <tr valign="top"><td            ><b>End Date              </b></td><td          >:</td><td><%=cpar.getEndDate() %></td></tr>
                    </table>
            </fieldset>

            <fieldset class="sub_function">
                
			<form name="form1" method="POST" action="">            
            <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                <tr class="title1"> 
                    <td>Contractor Performance Appraisal Reports History</td>
                </tr>
                <tr> 
                    <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%>
                    <td> 
                </tr>
                <tr> 
                    <td>
                    <div align="left">No. of history records : 
                    <%=allCPARHistoryList.size()%></div></td>
                </tr>
                <tr> 
                    <td> 
						<%if (! allCPARHistoryList.isEmpty()){%>
                        <div align="center"> 
                            <table width="98%" cellspacing="1" class="tableBackground">
                                <tr> 
                                    <td align="center" width="10%" class="tableHeaderAlignCenter1">&nbsp;</td>
                                    <td align="center" width="24%" class="tableHeaderAlignCenter1">Updated By User ID</td>
                                    <td align="center" width="20%" class="tableHeaderAlignCenter1">Action</td>
                                    <td align="center" width="32%" class="tableHeaderAlignCenter1">Updated Date/Time</td>
                                </tr>
							  <%
                                    // Display records
                                    for (int i = allCPARHistoryList.size()-1; i>=0 ; i--) {
                                        CPARInfo cparHistory = new CPARInfo();
                                        cpar = (CPARInfo) allCPARHistoryList.get(i);
                                %>
                                <tr valign="top" class="tableContentAlignCenter1"> 
                                    <td align="center" width="10%" nowrap><%=i+1 %></td>
                                    <td align="center" width="24%" nowrap><%=cpar.getLastUpdatedBy() %></td>
                                    <td align="center" width="20%" nowrap><%=cpar.getAction() %></td>
                                    <td align="center" width="32%" nowrap><%=SysManager.getStringfromSQLDateTime(cpar.getLastUpdatedDate()) %></td>
                                </tr>
                                <%} // End for %>
                            </table>
                        </div>
                        <%}else{%>
                                No CPAR history was found for this CPAR. 
                        <%} // End if vector size >0 %>
                    </td>
                </tr>
            </table>        
            <p>
                <input type="hidden" name="PostScreen" value="CPARList.jsp">
                <div align="center"> <a href="#"><img src="../images/btn_back.jpg" width="98" height="42" name="Return" onClick="selectaction(form1,this)" border="0" alt="Back"></a></div>
            </p>
        </form>
				        
            </fieldset>
         </fieldset>
    </body>
</html>