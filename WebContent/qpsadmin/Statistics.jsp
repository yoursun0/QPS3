<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.DeptInfo,
                 qpses.business.DeptDataBean,
                 qpses.business.ContractorInfo,
                 qpses.business.ContractorDataBean,
                 qpses.business.StatInfo,
                 qpses.business.StatDataBean,
                 qpses.util.SysManager,
                  java.util.HashMap,
                 java.util.List,
                 java.sql.Date" %>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // initialize variable
    String searchedInvitationStartDate = "";
    String searchedInvitationEndDate = "";
    String searchedAwardStartDate = "";
    String searchedAwardEndDate = "";
    
    StatDataBean waDB =new StatDataBean();
    boolean searched = false;

    // check and display for message
    //String msg =   getValue((String)  session.getAttribute("STAT_MSG"));
    //if (!msg.equals("")){
    //    session.removeAttribute("STAT_MSG");
    //    session.removeAttribute("STAT_MSGTYPE");
    //}
    
    //StatInfo wa = new StatInfo();
    HashMap searchParaHashMap = (HashMap) session.getAttribute("STAT_PARAMETER");
    StatInfo searchStat = new StatInfo();

    if (searchParaHashMap!=null){
    	searchStat = (StatInfo) searchParaHashMap.get("Stat");
    	List<Date> searchParaDates = (List<Date>) searchParaHashMap.get("DateFilters");
    	
        if (searchParaDates.get(0) != null) searchedInvitationStartDate = SysManager.getStringfromSQLDate(searchParaDates.get(0));
        if (searchParaDates.get(1) != null) searchedInvitationEndDate = SysManager.getStringfromSQLDate(searchParaDates.get(1));
        if (searchParaDates.get(2) != null) searchedAwardStartDate = SysManager.getStringfromSQLDate(searchParaDates.get(2));
        if (searchParaDates.get(3) != null) searchedAwardEndDate = SysManager.getStringfromSQLDate(searchParaDates.get(3));
        
        searched = true; 
    } 

%>			
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Statistics Report</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <link rel="stylesheet" type="text/css" href="../styles/calendar.css">               
        <script language="JavaScript" type="text/javascript" src="../js/statcheck.js"></script>
        <script language="javascript" type="text/javascript" src="../js/simplecalendar.js"></script>            		
    </head>
    <body> 
        <%@include file="include/header.jsp"%>        
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Statistics Report</td></tr></table>            		
            <!-- <form name="form1" method="POST" action="Statistics.jsp">    		
                    
                <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" class="tableborder">
                    <tr> 
                        <td> 
                            <table width="100%" border="0" cellspacing="0" cellpadding="5">
                                <tr class="tableborder"> 
                                    <td> 
                                        <div align="left">Input searching criteria below: </div>
                                    </td>
                                </tr>
                                <tr class="tableborder"> 
                                    <td> 
                                        <div align="center"> 
                                            <form name="form1" method="POST" action="Statistics.jsp">
                                            <table  width="98%" cellspacing="1" class="tableBackground">                                                
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Invitation Date Range</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <input class="text" type="text" name="InvitationStartDate" value="<%=searchedInvitationStartDate %>" readonly>
                       									<a class="cal" href="javascript: void(0);" 
                       									     onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                     									     onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                          									 onClick="g_Calendar.show(event, 'form1.InvitationStartDate', true, 'dd-mmm-yyyy'); return false;"> 
                            									<img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom">
                        								</a>
                        								</input>
                        								 &nbsp;&nbsp; to &nbsp;&nbsp;
                        								<input class="text" type="text" name="InvitationEndDate" value="<%=searchedInvitationEndDate %>" readonly>
                       									<a class="cal" href="javascript: void(0);" 
                       									     onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                     									     onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                          									 onClick="g_Calendar.show(event, 'form1.InvitationEndDate', true, 'dd-mmm-yyyy'); return false;"> 
                            									<img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom">
                        								</a>
                        								</input>
                                                    </td>
                                                </tr>

												<tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Awarded Date Range</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <input class="text" type="text" name="AwardStartDate" value="<%=searchedAwardStartDate %>" readonly>
                       									 <a class="cal" href="javascript: void(0);" 
                       									     onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                     									     onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                          									 onClick="g_Calendar.show(event, 'form1.AwardStartDate', true, 'dd-mmm-yyyy'); return false;"> 
                            									<img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"> 
                        								 </a>
                        								</input>
                        								 &nbsp;&nbsp; to &nbsp;&nbsp;
                        								<input class="text" type="text" name="AwardEndDate" value="<%=searchedAwardEndDate %>" readonly>
                       									 <a class="cal" href="javascript: void(0);" 
                       									     onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                     									     onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                          									 onClick="g_Calendar.show(event, 'form1.AwardEndDate', true, 'dd-mmm-yyyy'); return false;"> 
                            									<img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"> 
                        								 </a>
                        								</input>
                                                    </td>
                                                </tr>
                                            </table>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <table border="0" cellpadding="3" align="center">
                                <tr> 
                                    <td width="50%"> 
                                        <div align="center"> <a href="#"><img src="../images/btn_search.jpg" width="98" height="42" name="Search" onClick="statselectaction(form1,this)" border="0" alt="Search"></a></div>
                                    </td>
                                    <td width="50%"> 
                                        <div align="center"> <a href="#"><img src="../images/btn_reset.jpg" width="98" height="42" name="SearchReset" onClick="statselectaction(form1,this)" border="0" alt="Reset searching criteria"></a></div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table> 
                <BR> --> 
                <%-- if (searched) {--%>  
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr class="title1"> 
                        <td> Report </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                            
                            
<form name="form2" method="POST" action="Statistics.jsp" >                            
<img type="image" name="Download" class="function_button" src="../images/btn_download.jpg" alt="Download EXCEL File" onclick="DownloadFile(form2, this)">
</form>

<table cellspacing="0" cellpadding="0" width="900px" border="0">
  <col width="32%">
  <col width="16%">
  <col width="4%">
  <col width="32%">
  <col width="16%">
  <tr>
    <td colspan="5"><h2>SOA-QPS3 Management Statistics</h2></td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5"><h3>(A)  Overall Status</h3></td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td>No. of invitations received:</td>
    <td class="value_border"><%= getValue(searchStat.getInvitationNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>No. of service contracts    awarded:</td>
    <td class="value_border"><%= getValue(searchStat.getAwardedContractNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Total awarded project value    (in HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getAwardedTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5">Breakdown</td>
  </tr>
  <tr>
    <td>Total contracts value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getContractTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>Total change value (in HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getChangeTotalValue()) %></td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td>No. of in-progress service    contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getInprogressContractNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>No. of completed service    contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCompletedContractNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td>No. of cancelled invitations:</td>
    <td class="value_border"><%= getValue(searchStat.getCancelledInvitationNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <%-- <tr>
    <td>No. of CPAR received:</td>
    <td class="value_border"><%= getValue(searchStat.getReceivedCPARNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> --%>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5"><h3>(B)  Breakdown by Categories/Groups</h3></td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5"><h4>Cat. 1</h4></td>
  </tr>
  <tr>
    <td>No. of service contracts    awarded:</td>
    <td class="value_border"><%= getValue(searchStat.getCat1AwardedContractNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>No. of in-progress service    contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat1InprogressContractNo()) %></td>
    <td>&nbsp;</td>
    <td>No. of completed service contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat1CompletedContractNo()) %></td>
  </tr>
  <tr>
    <td>Awarded project value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat1AwardedTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Awarded contracts value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat1ContractTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>Total change value (in HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat1ChangeTotalValue()) %></td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5"><h4>Cat. 2 Major</h4></td>
  </tr>
  <tr>
    <td>No. of service contracts    awarded:</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MajorAwardedContractNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>No. of in-progress service    contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MajorInprogressContractNo()) %></td>
    <td>&nbsp;</td>
    <td>No. of completed service contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MajorCompletedContractNo()) %></td>
  </tr>
  <tr>
    <td>Awarded project value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MajorAwardedTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Awarded contracts value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MajorContractTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>Total change value (in HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MajorChangeTotalValue()) %></td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5"><h4>Cat. 2 Minor</h4></td>
  </tr>
  <tr>
    <td>No. of service contracts    awarded:</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MinorAwardedContractNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>No. of in-progress service    contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MinorInprogressContractNo()) %></td>
    <td>&nbsp;</td>
    <td>No. of completed service contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MinorCompletedContractNo()) %></td>
  </tr>
  <tr>
    <td>Awarded project value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MinorAwardedTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Awarded contracts value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MinorContractTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>Total change value (in HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat2MinorChangeTotalValue()) %></td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5"><h4>Cat. 3 Major</h4></td>
  </tr>
  <tr>
    <td>No. of service contracts    awarded:</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MajorAwardedContractNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>No. of in-progress service    contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MajorInprogressContractNo()) %></td>
    <td>&nbsp;</td>
    <td>No. of completed service contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MajorCompletedContractNo()) %></td>
  </tr>
  <tr>
    <td>Awarded project value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MajorAwardedTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Awarded contracts value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MajorContractTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>Total change value (in HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MajorChangeTotalValue()) %></td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5"><h4>Cat. 3 Minor</h4></td>
  </tr>
  <tr>
    <td>No. of service contracts    awarded:</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MinorAwardedContractNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>No. of in-progress service    contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MinorInprogressContractNo()) %></td>
    <td>&nbsp;</td>
    <td>No. of completed service contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MinorCompletedContractNo()) %></td>
  </tr>
  <tr>
    <td>Awarded project value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MinorAwardedTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Awarded contracts value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MinorContractTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>Total change value (in HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat3MinorChangeTotalValue()) %></td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5"><h4>Cat. 4</h4></td>
  </tr>
  <tr>
    <td>No. of service contracts    awarded:</td>
    <td class="value_border"><%= getValue(searchStat.getCat4AwardedContractNo()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>No. of in-progress service    contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat4InprogressContractNo()) %></td>
    <td>&nbsp;</td>
    <td>No. of completed service contracts:</td>
    <td class="value_border"><%= getValue(searchStat.getCat4CompletedContractNo()) %></td>
  </tr>
  <tr>
    <td>Awarded project value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat4AwardedTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Awarded contracts value (in    HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat4ContractTotalValue()) %></td>
    <td>&nbsp;</td>
    <td>Total change value (in HK$):</td>
    <td class="value_border"><%= getValue(searchStat.getCat4ChangeTotalValue()) %></td>
  </tr>
</table>


                               
                            </div>
                        </td>
                    </tr>
                </table>            
             	<%--
             	session.removeAttribute("STAT_PARAMETER");
             	}--%> 
                <BR>          
                
            <!-- </form> -->
        </fieldset> 		    
    </body>
</html>
