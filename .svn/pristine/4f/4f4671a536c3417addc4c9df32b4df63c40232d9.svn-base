<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.QualitySubscoreDataBean,
                                qpses.business.QualitySubscoreInfo"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // Get the latest effective period
    int effective_period_id;
    String effective_period ="";
    QualitySubscoreDataBean qsdb = new QualitySubscoreDataBean();
    QualitySubscoreInfo qs = new QualitySubscoreInfo();
    qs = qsdb.selectNextEffectivePeriod();
    effective_period_id =qs.getEffectivePeriodID();
    effective_period =qs.getPeriodDesc();

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
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Contractor Performance Score Import</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/qscheck.js" type="text/javascript"></script>                   
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Contractor Performance Score Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action=""  enctype="multipart/form-data">            
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td width="26%" class="tabUnselectLeft" onclick="changepage('List')">Contractor Performance Score List</td> 
                        <td width="14%" class="tabSelectedRight">Import</td>                    
                        <td width="60%">&nbsp;</td>
                    </tr>
                    <tr>                    
                </table>                
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr class="title1">                     
                        <td>Import Contractor Performance Scores</td>
                    </tr>                     
                    <tr> 
                        <td> <%= (! msg.equals(""))?((msg.contains("Upload status =11"))?"<div class=\"errMessage\">No effective period avaliable! Please delete some existing CPS records to perform replacement.</div>" : "<div class=\"errMessage\">"+ msg + "</div>"):""%> 
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table  width="98%" cellspacing="1" class="tableBackground">
                                <tr width="15%"> 
                                    <td class="tableVerticalHeaderAlignLeft1" width="15%">Effective 
                                    Period</td>
                                    <td width="85%" class="tableVerticalContentAlignLeft1"><%=(effective_period != null)?effective_period:"Latest effective period achieved" %> 
                                    </td>
                                </tr>
                                <tr> 
                                    <td width="15%" class="tableVerticalHeaderAlignLeft1">Import 
                                    File</td>
                                    <td width="85%" class="tableVerticalContentAlignLeft1"> 
                                        <input type="file" name="UploadFileName" size="80" class="inputText"  />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <table border="0" cellpadding="3" align="center">
                    <tr> 
                        <td width="50%"> 
                            <div align="center"> <a href="#"><img src="../images/btn_import.jpg" width="98" height="42" name="ConfirmImport" onClick="selectaction(form1,this)" border="0" alt="Import"></a></div>
                        </td>
                        <td width="50%"> 
                            <div align="center"> <a href="#"><img src="../images/btn_cancel.jpg" width="98" height="42" name="Cancel" onClick="selectaction(form1,this)" border="0" alt="Cancel"></a></div>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="selectedKey1" value="<%=effective_period_id%>">
            </form>
        </fieldset>
    </body>
</html>
