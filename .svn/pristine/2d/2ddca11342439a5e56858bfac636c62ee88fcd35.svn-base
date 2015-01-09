<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.CeilingRateInfo,
                                qpses.business.CeilingRateDataBean,
                                qpses.util.SysManager"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%

    // initialize variable
    String effective_date = "";
    String service_category = "";
    int active_ind = 0;
    String creation_date="";
    String last_updated_date="";
    String last_updated_by="";
    String org_key1="";
    String org_key2="";

    CeilingRateInfo cr = new CeilingRateInfo();   
    
    // get parameters from previous screen
    String pre_screen = getValue(request.getParameter("PostScreen"));
    if (pre_screen.equals("")){
        pre_screen = (String) session.getAttribute("CR_POST_SCREEN");
    }
    String order_by = getValue(request.getParameter("OrderBy"));
    String order_dir = getValue(request.getParameter("OrderDir"));


    cr = (CeilingRateInfo) session.getAttribute("CEILING_RATE_DATA");
    if (cr!=null){
        org_key1 = cr.getOrgKey1();
        org_key2 = SysManager.getStringfromSQLDate(cr.getOrgKey2());
        CeilingRateDataBean crDB =new CeilingRateDataBean();
        cr = crDB.selectCeilingRateByKeys(org_key1, org_key2);
        effective_date = SysManager.getStringfromSQLDate(cr.getEffectiveDate());
        service_category = cr.getServiceCategory();
        active_ind = cr.getActiveInd();
        creation_date = SysManager.getStringfromSQLDateTime(cr.getCreatedDate());
        last_updated_date = SysManager.getStringfromSQLDateTime(cr.getLastUpdatedDate());
        last_updated_by = cr.getLastUpdatedBy();
    }else{
        org_key1 = request.getParameter("selectedKey1");
        org_key2 = request.getParameter("selectedKey2");
        CeilingRateDataBean crDB =new CeilingRateDataBean();
        cr = crDB.selectCeilingRateByKeys(org_key1, org_key2);
        if (cr!=null){
            effective_date = SysManager.getStringfromSQLDate(cr.getEffectiveDate());
            service_category = cr.getServiceCategory();
            active_ind = cr.getActiveInd();
            creation_date = SysManager.getStringfromSQLDateTime(cr.getCreatedDate());
            last_updated_date = SysManager.getStringfromSQLDateTime(cr.getLastUpdatedDate());
            last_updated_by = cr.getLastUpdatedBy();
        }
    }
    // check  for message and get last input from session variables
    String msg =   getValue((String)  session.getAttribute("CEILING_RATE_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("CEILING_RATE_MSG_TYPE"));


    if (!msg.equals("")){
        session.removeAttribute("CEILING_RATE_MSG");
        session.removeAttribute("CEILING_RATE_MSGTYPE");
        session.removeAttribute("CEILING_RATE_DATA");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Delete Ceiling Rate</title>                
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">        
        <script language="JavaScript" src="../js/ceilingratecheck.js" type="text/javascript"></script>        
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Ceiling Rate Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableNoBorder" align="center">
                    <tr> 
                        <td class="title1">Delete Ceiling Rate</td>
                    </tr>
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%> 
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <table  width="98%" cellspacing="1" class="tableBackground">
                                    <tr> 
                                        <td width="25%" class="tableVerticalHeaderAlignLeft1">Service Category</td>
                                        <td width="75%" class="tableVerticalContentAlignLeft1"><%=service_category%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Effective Date</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=effective_date%> </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Release for Publishing?</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=((active_ind == -1)?"Yes":"No")%></td>
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
                <input type="hidden" name="orgKey2" value="<%=org_key2%>">
                <input type="hidden" name="PostScreen" value="<%=pre_screen%>">
                <input type="hidden" name="OrderBy" value="<%=order_by%>">
                <input type="hidden" name="OrderDir" value="<%=order_dir%>">                                                                
            </form>
        </fieldset>

    
    </body>
</html>
