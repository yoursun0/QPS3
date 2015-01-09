<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.QualitySubscoreInfo,
                                qpses.business.QualitySubscoreDataBean,
                                qpses.util.SysManager"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%

    // initialize variable
    String effective_period = "";
    int effective_period_id=0;
    String contractor_name = "";
    String creation_date="";
    String last_updated_date="";
    String last_updated_by="";
    int org_key1=0;

    QualitySubscoreInfo qs = new QualitySubscoreInfo();
    qs = (QualitySubscoreInfo) session.getAttribute("QUALITY_SUBSCORE_DATA");
    if (qs!=null){
        if (qs.getOrgKey1() ==0) org_key1 = qs.getOrgKey1();
        if (qs!=null){
            QualitySubscoreDataBean qsDB =new QualitySubscoreDataBean();
            qs = qsDB.selectQualitySubscoreByPeriod(org_key1);
            effective_period = qs.getPeriodDesc();
            effective_period_id = qs.getEffectivePeriodID();
            contractor_name = qs.getContractorName();
            creation_date = SysManager.getStringfromSQLDateTime(qs.getCreatedDate());
            last_updated_date = SysManager.getStringfromSQLDateTime(qs.getLastUpdatedDate());
            last_updated_by = qs.getLastUpdatedBy();
        }
    }else{
        org_key1 = Integer.parseInt(request.getParameter("selectedKey1"));
        QualitySubscoreDataBean qsDB =new QualitySubscoreDataBean();
        qs = qsDB.selectQualitySubscoreByPeriod(org_key1);
        effective_period = qs.getPeriodDesc();
        effective_period_id = qs.getEffectivePeriodID();
        contractor_name = qs.getContractorName();
        creation_date = qs.getCreatedDate();
        last_updated_date = qs.getLastUpdatedDate();
        last_updated_by = qs.getLastUpdatedBy();
        if (qs.getOrgKey1()==0) org_key1 = qs.getOrgKey1();

    }
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
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Contractor Performance Score Delete</title>                
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">                
        <script language="JavaScript" src="../js/qscheck.js" type="text/javascript"></script>                           
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Contractor Performance Score Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableNoBorder" align="center">
                    <tr class="title1"> 
                        <td>Delete Contractor Performance Scores</td>
                    </tr>
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%> 
                        <td> 
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <table  width="98%" cellspacing="1" class="tableBackground">
                                    <tr> 
                                        <td width="25%" class="tableVerticalHeaderAlignLeft1">Effective 
                                        Start Date</td>
                                        <td width="75%" class="tableVerticalContentAlignLeft1"><%=effective_period%> 
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Creation Date</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=creation_date%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Last Updated Date</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=last_updated_date%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Last Updated By</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=last_updated_by%></td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
                <table border="0" cellpadding="3" align="center">
                    <tr> 
                        <td width="50%"> 
                            <div align="center"> <a href="#"><img src="../images/btn_delete.jpg" width="98" height="42" name="ConfirmDelete" onClick="selectaction(form1,this)" border="0" alt="Delete"></a></div>
                        </td>
                        <td width="50%"> 
                            <div align="center"> <a href="#"><img src="../images/btn_cancel.jpg" width="98" height="42" name="Cancel" onClick="selectaction(form1,this)" border="0" alt="Cancel"></a></div>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="orgKey1" value="<%=org_key1%>">            
            </form>
        </fieldset>    
    </body>
</html>
