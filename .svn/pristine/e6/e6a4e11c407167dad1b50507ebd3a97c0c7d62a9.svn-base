<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.AvgCeilingRateInfo,
                                qpses.util.SysManager"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%

    // initialize variable
    String effective_date = "";
    int service_category = 0;
    int active_ind = 0;

    // get values from session, if any
    AvgCeilingRateInfo acr = new AvgCeilingRateInfo();
    acr = (AvgCeilingRateInfo) session.getAttribute("AVG_CEILING_RATE_DATA");
    if (acr!=null){
        effective_date = SysManager.getStringfromSQLDate(acr.getEffectiveDate());
        service_category = acr.getServiceCategory();
        active_ind = acr.getActiveInd();
    }
    String msg =   getValue((String)  session.getAttribute("AVG_CEILING_RATE_MSG"));
    if (!msg.equals("")){
        session.removeAttribute("AVG_CEILING_RATE_DATA");
        session.removeAttribute("AVG_CEILING_RATE_MSG");
        session.removeAttribute("AVG_CEILING_RATE_MSGTYPE");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Average Ceiling Rate Upload</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">        
        <link rel="STYLESHEET" type="text/css" href="../styles/calendar.css">        
        <script language="JavaScript" src="../js/simplecalendar.js" type="text/javascript"></script>
        <script language="JavaScript" src="../js/avgcrcheck.js" type="text/javascript"></script>                   
    </head>
    <body>
        <%@include file="include/header.jsp"%> 
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Average Ceiling Rate Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action=""  enctype="multipart/form-data">            
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td width="26%" class="tabUnselectLeft" onclick="changepage('List')">Average Ceiling Rate List</td>
                        <td width="14%" class="tabSelectedMiddle">Upload</td>
                        <td width="11%" class="tabUnselectRight" onclick="changepage('Search')">Search</td>
                        <td width="49%">&nbsp;</td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <td class="title1">Upload Average Ceiling Rate</td>
                    </tr>
                    <tr> 
                        <td><%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%></td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <table  width="98%" cellspacing="1" class="tableBackground">
                                    <tr width="15%"> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="25%">Effective 
                                        Date </td>
                                        <td width="75%" class="tableVerticalContentAlignLeft1"> 
                                        <input type="text" name="EffectiveDate" value="<%=effective_date%>" class="inputText" readonly/>
                                        <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'form1.EffectiveDate',true, 'dd-mmm-yyyy'); return false;"><img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"></a> 
                                        (dd-MMM-yyyy) </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Service Category</td>
                                        <td class="tableVerticalContentAlignLeft1"> 
                                            <select name="ServiceCategory" class="inputText">
                                                <option value="0" <%if (service_category ==0) out.write("SELECTED");%>>Please 
                                                select Category </option>
                                                <option value="1" <%if (service_category ==1) out.write("SELECTED");%>>Category 
                                                1</option>
                                                <option value="2" <%if (service_category == 2) out.write("SELECTED");%>>Category 
                                                2</option>
                                                <option value="3" <%if (service_category == 3) out.write("SELECTED");%>>Category 
                                                3</option>
                                                <option value="4" <%if (service_category == 4) out.write("SELECTED");%>>Category 
                                                4</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Release for Publishing?</td>
                                        <td class="tableVerticalContentAlignLeft1"> 
                                        <input type="radio" name="PublishInd" value="-1" <%= ((active_ind == -1)?"CHECKED":"")%>/>
                                        Yes 
                                        <input type="radio" name="PublishInd" value="0" <%= ((active_ind != -1)?"CHECKED":"")%> />
                                        No </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">File Upload</td>
                                        <td class="tableVerticalContentAlignLeft1"> 
                                            <input type="file" name="UploadFileName" value="" class="inputText" size="80"  />
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
                            <div align="center"> <a href="#"><img src="../images/btn_upload.jpg" width="98" height="42" name="ConfirmUpload" onClick="selectaction(form1,this)" border="0" alt="Upload"></a></div>
                        </td>
                        <td width="50%">
                            <div align="center"> <a href="#"><img src="../images/btn_cancel.jpg" width="98" height="42" name="Cancel" onClick="selectaction(form1,this)" border="0" alt="Cancel"></a></div>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="PostScreen" value="AvgCeilingRateList.jsp">                
            </form>
        </fieldset>
    </body>	
</html>
