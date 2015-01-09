<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.QualitySubscoreDataBean,
                                qpses.business.QualitySubscoreInfo,
                                java.util.List,
                                java.util.ArrayList,
                                qpses.util.SysManager" %>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%

    QualitySubscoreDataBean qsDB= new QualitySubscoreDataBean();
    List allQualitySubscoreList = new ArrayList();
    allQualitySubscoreList = qsDB.selectQualitySubscoreByPeriod();

    // check and display for message
    String msg =   getValue((String)  session.getAttribute("QUALITY_SUBSCORE_MSG"));
    if (!msg.equals("")){
        session.removeAttribute("QUALITY_SUBSCORE_MSG");
        session.removeAttribute("QUALITY_SUBSCORE_MSGTYPE");
    }

%>            
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Contractor Performance Score</title>        
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/qscheck.js" type="text/javascript"></script>                       
        <script language="JavaScript" src="../js/nw.js" type="text/javascript"></script>                        
    </head>
    
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
        <table class="function_title"><tr><td>Contractor Performance Score Maintenance</td></tr></table>            		        
        <form name="form1" method="POST" action="">            
            <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                <tr>                    
                    <td width="26%" class="tabSelectedLeft"> Contractor Performance Score List</td> 
                    <td width="14%" class="tabUnselectRight" onclick="changepage('Import')">Import</td>                    
                    <td width="60%">&nbsp;</td>
                </tr>
            </table> 
            <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                <tr class="title1"> 
                    <td>List of Contractor Performance Scores</td>
                </tr>
                <tr> 
                    <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%>
                    <td> 
                </tr>
                <tr> 
                    <td>
                    <div align="left">No. of records : 
                    <%=allQualitySubscoreList.size()%></div></td>
                </tr>
                <tr> 
                    <td> 
                        <%if (! allQualitySubscoreList.isEmpty()){%>
                        <div align="center"> 
                            <table width="98%" cellspacing="1" class="tableBackground">
                                <tr> 
                                    <td align="center" width="5%" class="tableHeaderAlignCenter1">&nbsp;</td>
                                    <td align="center" width="22%" class="tableHeaderAlignCenter1">Effective 
                                    Period </td>
                                    <td align="center" width="20%" class="tableHeaderAlignCenter1">Created 
                                    On</td>
                                    <td align="center" width="18%" class="tableHeaderAlignCenter1">Last 
                                    Updated On</td>
                                    <td align="center" width="22%" class="tableHeaderAlignCenter1">Last 
                                    Updated By</td>
                                    <td align="center" width="13%" class="tableHeaderAlignCenter1">Action</td>
                                </tr>
                                <%
                                    // Display records
                                    for (int i = 0; i<allQualitySubscoreList.size();i++) {
                                        QualitySubscoreInfo qs = new QualitySubscoreInfo();
                                        qs = (QualitySubscoreInfo) allQualitySubscoreList.get(i);
                                        // initialize variables
                                        int effective_period_id;
                                        String effective_period ="";
                                        String created_datetime="";
                                        String last_updated_datetime="";
                                        String last_updated_by="";

                                        // get values from vector element
                                        effective_period_id =qs.getEffectivePeriodID();
                                        effective_period =qs.getPeriodDesc();
                                        created_datetime = qs.getCreatedDate();
                                        last_updated_datetime = qs.getLastUpdatedDate();
                                        last_updated_by = qs.getLastUpdatedBy();
                                %>
                                <tr valign="top" class="tableContentAlignCenter1"> 
                                    <td align="center" width="5%" nowrap><%=(i+1)%></td>
                                    <td align="center" width="22%" nowrap><%=effective_period%></td>
                                    <td align="center" width="20%" nowrap><%=created_datetime%></td>
                                    <td align="center" width="18%" nowrap><%=last_updated_datetime%></td>
                                    <td align="center" width="22%" nowrap><%=last_updated_by%></td>                                    
                                    <td align="center" width="13%" nowrap>
                                        <a  href="#"><img name="Print" src="../images/s_btn_print.jpg" width="63" height="21" alt="Print" border="0" onClick="selectaction(form1,this,'<%=effective_period_id%>')"></a>                                   
                                    <%if (i==0){%>
					<a href="#"><img name="Delete" src="../images/s_btn_delete.jpg" width="63" height="21" alt="Delete" border="0" onClick="selectaction(form1,this,'<%=effective_period_id%>')"></a>
                                    <%}%>                                                                        
                                    </td>
                                </tr>
                                <%} // End for %>
                            </table>
                        </div>
                        <%}else{%>
                                No records! 
                        <%} // End if vector size >0 %>
                    </td>
                </tr>
            </table>        
            <p>
                <input type="hidden" name="selectedKey1" value="">
            </p>
        </form>
    </body>
</html>
