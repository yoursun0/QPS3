<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.DeptInfo,
                 qpses.business.DeptDataBean,
                 qpses.business.ContractorInfo,
                 qpses.business.ContractorDataBean,
                 qpses.business.CPSInfo,
                 qpses.business.CPSDataBean,
                 qpses.util.SysManager,
                 java.util.HashMap,
                 java.util.List,
                 java.util.LinkedList,
                 java.sql.Date                 " %>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // initialize variable
    String searchedCutOffDate = "";

    CPSDataBean waDB =new CPSDataBean();
    boolean searched = false;

    // check and display for message
    //String msg =   getValue((String)  session.getAttribute("STAT_MSG"));
    //if (!msg.equals("")){
    //    session.removeAttribute("STAT_MSG");
    //    session.removeAttribute("STAT_MSGTYPE");
    //}
    
    //CPSInfo wa = new CPSInfo();
    HashMap searchParaHashMap = (HashMap) session.getAttribute("CPS_PARAMETER");

    LinkedList<CPSInfo> cpsCats = new LinkedList<CPSInfo>(); 
    
    if (searchParaHashMap!=null){
    	
    	Date cutOffDate = (Date)searchParaHashMap.get("CUTOFFDATE");
    	if (cutOffDate != null) searchedCutOffDate = SysManager.getStringfromSQLDate(cutOffDate);
    	
    	cpsCats.add((CPSInfo) searchParaHashMap.get("CPS1"));
    	cpsCats.add((CPSInfo) searchParaHashMap.get("CPS2/N"));
    	cpsCats.add((CPSInfo) searchParaHashMap.get("CPS2/J"));
    	cpsCats.add((CPSInfo) searchParaHashMap.get("CPS3/N"));
    	cpsCats.add((CPSInfo) searchParaHashMap.get("CPS3/J"));
    	cpsCats.add((CPSInfo) searchParaHashMap.get("CPS4"));

        searched = true; 
    } 

%>			
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Generate CPS</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <link rel="stylesheet" type="text/css" href="../styles/calendar.css">               
        <script language="JavaScript" type="text/javascript" src="../js/generateCPScheck.js"></script>
        <script language="javascript" type="text/javascript" src="../js/simplecalendar.js"></script>            		
    </head>
    <body> 
        <%@include file="include/header.jsp"%>        
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Generate Contractor Performance Score</td></tr></table>            		
            <!-- <form name="form1" method="POST" action="CPSGenerate.jsp"> -->            		
                    
                <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" class="tableborder">
                    <tr> 
                        <td> 
                            <table width="100%" border="0" cellspacing="0" cellpadding="5">
                                <!-- <tr class="tableborder"> 
                                    <td class="title1"> Search Work Assignment</td>
                                </tr>  -->
                                <tr class="tableborder"> 
                                    <td> 
                                        <div align="left">Input searching criteria below: </div>
                                    </td>
                                </tr>
                                <tr class="tableborder"> 
                                    <td> 
                                        <div align="center"> 
                                            <form name="form1" method="POST" action="CPSGenerate.jsp">
                                            <table  width="98%" cellspacing="1" class="tableBackground">                                                
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Cut off Date</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <input class="text" type="text" name="CutOffDate" value="<%= searchedCutOffDate %>" readonly>
                       									<a class="cal" href="javascript: void(0);" 
                       									     onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                     									     onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                          									 onClick="g_Calendar.show(event, 'form1.CutOffDate', true, 'dd-mmm-yyyy'); return false;"> 
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
                                        <div align="center"> <a href="#"><img src="../images/btn_generate.jpg" width="98" height="42" name="Search" onClick="cpsSelectaction(form1,this)" border="0" alt="Search"></a></div>
                                    </td>
                                    <td width="50%"> 
                                        <div align="center"> <a href="#"><img src="../images/btn_reset.jpg" width="98" height="42" name="SearchReset" onClick="cpsSelectaction(form1,this)" border="0" alt="Reset searching criteria"></a></div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <BR>
                <% if (searched) {%>  
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr class="title1"> 
                        <td> Report </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                            
                            
<form name="form2" method="POST" action="CPSGenerate.jsp" >                            
<img type="image" name="Download" class="function_button" src="../images/btn_download.jpg" alt="Download EXCEL File" onclick="DownloadFile(form2, this)">
</form>



<div style="width: 800px;" align="left">
<h2>SOA-QPS3 Contractor Performance Scores calculated as of 
<%= ("".equals(searchedCutOffDate))?SysManager.getCurDateTimeStr("dd-MMM-yyyy"):searchedCutOffDate %></h2>



<%
	for (CPSInfo cat:cpsCats){
		String catGroup = cat.getServiceCategoryGroup();
		String outputCatGroup = "";
		if ( "1".equals(catGroup) || "2/N".equals(catGroup) || "3/N".equals(catGroup) || "4".equals(catGroup) ){
			if ("1".equals(catGroup)) outputCatGroup = "1";
			if ("2/N".equals(catGroup)) outputCatGroup = "2";
			if ("3/N".equals(catGroup)) outputCatGroup = "3";
			if ("4".equals(catGroup)) outputCatGroup = "4";
			%>
			
			<p>&nbsp;</p>
			<h3>Category <%= outputCatGroup%></h3>
			<table cellspacing="0" cellpadding="0" width="100%" border="1" class="tableBackground">
			  <col width="5%">
			  <col width="20%">
			  <col width="50%">
			  <col width="25%">
			  <tr>
			    <th class="tableHeaderAlignCenter1">No.</th>
			    <th class="tableHeaderAlignCenter1">Contractor</th>
			    <th class="tableHeaderAlignCenter1">Full Name</th>
			    <th class="tableHeaderAlignCenter1">Contractor Performance Score</th>
			  </tr>
			<% 
			if ( "2/N".equals(catGroup) || "3/N".equals(catGroup) ){
			%>
			  <tr>
			    <td class="tableContentAlignLeft1" colspan="4"><Strong>Minor Works Group</Strong></td>
			  </tr>
			
			<%
			}
			for (int i = 0; i < cat.getContractorIds().size(); i++){ %>
			  <tr>
			    <td class="tableContentAlignCenter2"><%=i + 1 %></td>
			    <td class="tableContentAlignLeft1"><%=cat.getContractorIds().get(i) %></td>
			    <td class="tableContentAlignLeft1"><%=cat.getContractorNames().get(i) %></td>
			    <td class="tableContentAlignCenter2"><%=cat.getScores().get(i) %></td>
			  </tr>
			<%
			}
			if ( "1".equals(catGroup) || "4".equals(catGroup) ){
			%>
			</table>
			<%
			}
		} else {
			if ("2/J".equals(catGroup) || "3/J".equals(catGroup)){%>
			  <tr>
			    <td class="tableContentAlignLeft1" colspan="4"><Strong>Major Works Group</Strong></td>
			  </tr>
			  <% for (int i = 0; i < cat.getContractorIds().size(); i++){ %>
			  <tr>
			    <td class="tableContentAlignCenter2"><%=i + 1 %></td>
			    <td class="tableContentAlignLeft1"><%=cat.getContractorIds().get(i) %></td>
			    <td class="tableContentAlignLeft1"><%=cat.getContractorNames().get(i) %></td>
			    <td class="tableContentAlignCenter2"><%=cat.getScores().get(i) %></td>
			  </tr>
			  <%} %>
			</table>
			<%
			}
			
		}
		
	}

%>

<div>




                               
                            </div>
                        </td>
                    </tr>
                </table>            
             	<%
             	session.removeAttribute("CPS_PARAMETER");
             	}%> 
                <BR>          
                
            <!-- </form> -->
        </fieldset> 		    
    </body>
</html>
