<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.CeilingRateInfo,qpses.util.SysManager"%>
<%

    // initialize variable
    String effective_date = "";
    String service_category = "";
    int active_ind = 0;

    CeilingRateInfo cr = new CeilingRateInfo();

    // check  for message and get last input from session variables
    String msg =   getValue((String)  session.getAttribute("CEILING_RATE_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("CEILING_RATE_MSG_TYPE"));
    if (!msg.equals("")){
        cr = (CeilingRateInfo) session.getAttribute("CEILING_RATE_DATA");
        if (cr!=null){
            effective_date = SysManager.getStringfromSQLDate(cr.getEffectiveDate());
            service_category = cr.getServiceCategory();
            active_ind = cr.getActiveInd();
        }
        session.removeAttribute("CEILING_RATE_MSG");
        session.removeAttribute("CEILING_RATE_MSGTYPE");
        session.removeAttribute("CEILING_RATE_DATA");
    }
%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Ceiling Rate Import</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">        
        <link rel="STYLESHEET" type="text/css" href="../styles/calendar.css">        
        <script language="JavaScript" src="../js/simplecalendar.js" type="text/javascript"></script>        
        <script language="JavaScript" src="../js/ceilingratecheck.js" type="text/javascript"></script>        
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Ceiling Rate Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action=""  enctype="multipart/form-data">            
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td width="20%" class="tabUnselectLeft" onclick="changepage('List')"> Ceiling 
                        Rate List</td> 
                        <td width="14%" class="tabSelectedMiddle">Import</td>
                        <td width="11%" class="tabUnselectRight"  onclick="changepage('Search')">Search</td>
                        <td width="55%">&nbsp;</td>
                    </tr>
                </table>                
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td class="title1">Import Ceiling Rate</td>
                    </tr>
                    <tr> 
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%> 
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <table  width="98%" cellspacing="1" class="tableBackground">
                                    <tr width="15%"> 
                                        
              <td class="tableVerticalHeaderAlignLeft1">Effective Date</td>
                                        <td class="tableVerticalContentAlignLeft1"> 
                                        <input type="text" name="EffectiveDate" value="<%=effective_date%>" class="inputText" readonly/>
                                        <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'form1.EffectiveDate',true, 'dd-mmm-yyyy'); return false;"><img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"></a> 
                                        (dd-MMM-yyyy) </td>
                                    </tr>
                                    <tr width="85%"> 
                                        
              <td class="tableVerticalHeaderAlignLeft1">Service Category</td>
                                        <td class="tableVerticalContentAlignLeft1"> 
                                            <p>Please select the Service Category to be uploaded:<br>
                                                <input type="checkbox" name="ServiceCategory" value="SC1" <% if (service_category==null || getValue(service_category).indexOf("SC1")<0) out.write(""); else out.write("checked");%>>
                                                Service Category 1<br>
                                                <input type="checkbox" name="ServiceCategory" value="SC2" <% if (service_category==null || getValue(service_category).indexOf("SC2")<0) out.write(""); else out.write("checked");%>>
                                                Service Category 2<br>
                                                <input type="checkbox" name="ServiceCategory" value="SC3" <% if (service_category==null || getValue(service_category).indexOf("SC3")<0) out.write(""); else out.write("checked");%>>
                                                Service Category 3<br>
                                                <input type="checkbox" name="ServiceCategory" value="SC4" <% if (service_category==null || getValue(service_category).indexOf("SC4")<0) out.write(""); else out.write("checked");%>>
                                                Service Category 4<br>
                                            </p>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Release for Publishing?</td>
                                        <td class="tableVerticalContentAlignLeft1"> 
                                        <input type="radio" name="PublishInd" value="-1" <% if (active_ind == -1) out.write("CHECKED");%>/>
                                        Yes 
                                        <input type="radio" name="PublishInd" value="0" <% if (active_ind == 0) out.write("CHECKED");%> />
                                        No </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">File Import</td>
                                        <td class="tableVerticalContentAlignLeft1"> 
                                            <input type="file" name="UploadFileName" value="" size="80" class="inputText"  />
                                        </td>
                                    </tr>
                                </table>
                            </div>
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
                <input type="hidden" name="PostScreen" value="CeilingRateList.jsp">         
            </form> 
        </fieldset>
    </body>
</html>
