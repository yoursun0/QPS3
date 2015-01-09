<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.AvgCeilingRateInfo,qpses.business.AvgCeilingRateDataBean,qpses.util.SysManager"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%

    // initialize variable
    String effective_date = "";
    int service_category = 0;
    int active_ind = 0;
    String creation_date="";
    String last_updated_date="";
    String last_updated_by="";
    int org_key1=0;
    String org_key2="";
    String file_name ="";
    long file_size = 0;
    
    // get parameters from previous screen
    String pre_screen = getValue(request.getParameter("PostScreen"));
    if (pre_screen.equals("")){
        pre_screen = (String) session.getAttribute("ACR_POST_SCREEN");
    }
    String order_by = getValue(request.getParameter("OrderBy"));
    String order_dir = getValue(request.getParameter("OrderDir"));

    // get data from database
    AvgCeilingRateInfo acr = new AvgCeilingRateInfo();
    acr = (AvgCeilingRateInfo) session.getAttribute("AVG_CEILING_RATE_DATA");

    if (acr!=null){
        org_key1 = acr.getOrgKey1();
        org_key2 = SysManager.getStringfromSQLDate(acr.getOrgKey2());
        AvgCeilingRateDataBean acrDB =new AvgCeilingRateDataBean();
        acr = acrDB.selectAvgCeilingRateByKeys(org_key1, org_key2);
        if (acr!=null){
            effective_date = SysManager.getStringfromSQLDate(acr.getEffectiveDate());
            service_category = acr.getServiceCategory();
            active_ind = acr.getActiveInd();            
            file_name = acr.getPDFFileName();
            file_size = acr.getPDFFileSize()/1024;
            creation_date =  SysManager.getStringfromSQLDateTime(acr.getCreatedDate());
            last_updated_date =  SysManager.getStringfromSQLDateTime(acr.getLastUpdatedDate());
            last_updated_by = acr.getLastUpdatedBy();
        }
    }else{
        String selectedKey1 = request.getParameter("selectedKey1");
        if (!getValue(selectedKey1).equals("")) org_key1 = Integer.parseInt(selectedKey1);
        org_key2 = request.getParameter("selectedKey2");
        AvgCeilingRateDataBean acrDB =new AvgCeilingRateDataBean();
        acr = acrDB.selectAvgCeilingRateByKeys(org_key1, org_key2);
        if (acr!=null){
            effective_date = SysManager.getStringfromSQLDate(acr.getEffectiveDate());
            service_category = acr.getServiceCategory();
            active_ind = acr.getActiveInd();
            file_name = acr.getPDFFileName();
            file_size = acr.getPDFFileSize()/1024;
            creation_date = SysManager.getStringfromSQLDateTime(acr.getCreatedDate());
            last_updated_date =  SysManager.getStringfromSQLDateTime(acr.getLastUpdatedDate());
            last_updated_by = acr.getLastUpdatedBy();
            if (acr.getOrgKey1()!=0) org_key1 = acr.getOrgKey1();
            if (acr.getOrgKey2() !=null) org_key2 = SysManager.getStringfromSQLDate(acr.getOrgKey2());
        }
    }
    // check and display for message
    String msg =   getValue((String)  session.getAttribute("AVG_CEILING_RATE_MSG"));
    if (!msg.equals("")){
        session.removeAttribute("AVG_CEILING_RATE_MSG");
        session.removeAttribute("AVG_CEILING_RATE_MSGTYPE");
    }

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Average Ceiling Rate Delete</title>                
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">                        
        <script language="JavaScript" src="../js/nw.js" type="text/javascript"></script>
        <script language="JavaScript" src="../js/avgcrcheck.js" type="text/javascript"></script>                           
    </head>
    <body>
        <%@include file="include/header.jsp"%>              
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Average Ceiling Rate Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableNoBorder" align="center">
                    <tr> 
                        <td class="title1">Delete Average Ceiling Rate</td>
                    </tr>
                    <tr> 
                        <td> 
                            <%if (! msg.equals("")) out.print("<p><div align=\"center\" class=\"errMessage\">"+ msg + "</div></p>");%>
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <table  width="98%" cellspacing="1" class="tableBackground">
                                    <tr width="15%"> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="29%">Effective 
                                        Date</td>
                                        <td class="tableVerticalContentAlignLeft1" width="71%"><%=effective_date%> 
                                        </td>
                                    </tr>
                                    <tr width="85%"> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="29%">Service Category</td>
                                        <td class="tableVerticalContentAlignLeft1" width="71%"><%=service_category%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="29%">Release for 
                                        Publishing?</td>
                                        <td class="tableVerticalContentAlignLeft1" width="71%"> <%= ((active_ind == -1)?"Yes":"No")%> 
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td valign="top" class="tableVerticalHeaderAlignLeft1" width="29%">Uploaded 
                                        File</td>
                                        <td class="tableVerticalContentAlignLeft1" width="71%"> 
                                            <p><a href="AvgCeilingRateFile.servlet?orgKey1=<%=service_category%>&orgKey2=<%=effective_date%>" target="_blank");"><img src="../images/pdf.gif" border="0"> 
                                            <%=file_name%> (<%=file_size%>K) </a></p>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="29%">Creation Date</td>
                                        <td class="tableVerticalContentAlignLeft1" width="71%"><%=creation_date%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="29%">Last Updated 
                                        Date</td>
                                        <td class="tableVerticalContentAlignLeft1" width="71%"><%=last_updated_date%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="29%">Last Updated 
                                        By</td>
                                        <td class="tableVerticalContentAlignLeft1" width="71%"><%=last_updated_by%></td>
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
