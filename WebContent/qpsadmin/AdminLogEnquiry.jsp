<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.LogInfo,
                                qpses.business.LogDataBean,
                                qpses.business.DeptInfo,
                                qpses.business.DeptDataBean,
                                qpses.util.SysManager,
                                java.util.List,
                                java.util.ArrayList" %>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // initialize variable
    String searched_user_id = "";
    String searched_activity = "WAIMPORT,"+"ACRUPLOAD,ACRDELETE,ACRRELEASE,"+
            "CRIMPORT,CRDELETE,CRRELEASE,"+
            "QSIMPORT,QSDELETE,"+
            "DEBARADD,DEBARRELEASE,DEBARDELETE" +
            "ACLADD,ACLIMPORT,ACLUPDATE,ACLDELETE,"+"ACLUNLOCK,ACLENABLE," +
            "CPAR_MAINTENANCE,ACPAR_UPDATE,ACPAR_RELEASE,ACPAR_UNRELEASE,ACPAR_DELETE,"+
            "CONADD,CONUPDATE,CONDELETE,"+"DEPTADD,DEPTUPDATE,DEPTDELETE" +
            "STAT,OUTSTAND,GENERATECPS,ACPAR_EXPORT";
    String searched_from_date ="";
    String searched_to_date ="";

    String order_by = "audit_trail_id";
    String order_dir = "DESC";
    int start_rec = 1;
    int page_size = 10;
    int current_page = 0;
    int total_page =0;
    int total_records =0;
   

    LogInfo aclog = new LogInfo();
    LogDataBean logDB = new LogDataBean();
    List allLogList = new ArrayList();
    boolean searched = false;

    LogInfo searchPara = (LogInfo) session.getAttribute("ADMIN_LOG_ENQUIRY_PARAMETER");
    if (searchPara!=null){
        order_by = getValue(request.getParameter("OrderBy"));
        order_dir = getValue(request.getParameter("OrderDir"));
        if (order_by.equals("")) order_by = "audit_trail_id";
        if (order_dir.equals("")) order_dir = "DESC";
        if ((String) request.getParameter("StartRec") == null) {
            start_rec = 1;
        }else{
            start_rec = Integer.parseInt((String) request.getParameter("StartRec"));
        }    
        if ((String) request.getParameter("PageSize") == null) {
            page_size = 10;
        }else{
            page_size = Integer.parseInt((String) request.getParameter("PageSize"));
        }                                    
        allLogList = logDB.searchAdminLog(searchPara,order_by,order_dir,start_rec,page_size);
        searched = true;
        searched_user_id = getValue(searchPara.getUserId());
        searched_activity = getValue(searchPara.getActivity());
        searched_from_date = getValue(searchPara.getFromDate());
        searched_to_date = getValue(searchPara.getToDate());

    }
    // Get department list
    DeptDataBean dpDB = new DeptDataBean();
    List deptList = dpDB.selectDept();


%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Administrator Activities Log Enquiry</title>        
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <link rel="STYLESHEET" type="text/css" href="../styles/calendar.css">        
        <script language="JavaScript" src="../js/simplecalendar.js" type="text/javascript"></script>
        <script language="JavaScript" src="../js/adminlogenquiry.js" type="text/javascript"></script>        
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr>
                
                <td>Administrator Activities Log Enquiry</td>
            </tr></table>            
            <form name="form1" method="POST" action="">
                <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" class="tableborder">
                    <tr> 
                        <td> 
                            <div align="left">Input searching criteria below: </div>
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                        <div align="center"> 
                            <table  width="98%" cellspacing="1" class="tableBackground">
                                <tr width="15%"> 
                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">User ID</td>
                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                        <input type="text" name="UserId" size="30" maxlength="30" value="<%=searched_user_id%>" />
                                    </td>
                                </tr>
                                <tr> 
                                    <td class="tableVerticalHeaderAlignLeft1" width="25%"> Activities</td>
                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                        <table class="defaultfont" width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td valign="top"><b>Data Maintenance</b><br>
                                                <input type="checkbox" name="Activity" value="WAIMPORT" <%= (searched_activity.indexOf("WAIMPORT")>=0)?"CHECKED":""%>>
                                                Work Assignment<br>
                                                <input type="checkbox" name="Activity" value="ACRUPLOAD,ACRDELETE,ACRRELEASE" <%= (searched_activity.indexOf("ACRUPLOAD,ACRDELETE,ACRRELEASE")>=0)?"CHECKED":""%>>
                                                Average Ceiling Rate<br>
                                                <input type="checkbox" name="Activity" value="CRIMPORT,CRDELETE,CRRELEASE"  <%= (searched_activity.indexOf("CRIMPORT,CRDELETE,CRRELEASE")>=0)?"CHECKED":""%>>
                                                Ceiling Rate<br>
                                                <input type="checkbox" name="Activity" value="QSIMPORT,QSDELETE"  <%= (searched_activity.indexOf("QSIMPORT,QSDELETE")>=0)?"CHECKED":""%>>
                                                Contractor Performance Score<br>
                                                <input type="checkbox" name="Activity" value="DEBARADD,DEBARRELEASE,DEBARDELETE"  <%= (searched_activity.indexOf("DEBARADD,DEBARRELEASE,DEBARDELETE")>=0)?"CHECKED":""%>>
                                                Contractor Debarment</td>
                                                <td valign="top"><b>Access Control</b><br>
                                                <input type="checkbox" name="Activity" value="ACLADD,ACLIMPORT,ACLUPDATE,ACLDELETE"  <%= (searched_activity.indexOf("ACLADD,ACLIMPORT,ACLUPDATE,ACLDELETE")>=0)?"CHECKED":""%>>
                                                Access Control Maintenance<br>
                                                <input type="checkbox" name="Activity" value="ACLUNLOCK" <%= (searched_activity.indexOf("ACLUNLOCK")>=0)?"CHECKED":""%>>
                                                Unlock User<br>
                                                <input type="checkbox" name="Activity" value="ACLENABLE" <%= (searched_activity.indexOf("ACLENABLE")>=0)?"CHECKED":""%>>
                                                Enable User<br><br>
                                                <b>Statistics</b><br>
                                                <input type="checkbox" name="Activity" value="STAT" <%= (searched_activity.indexOf("STAT")>=0)?"CHECKED":""%>>
                                                Key Statistics Report</td>
                                                <td valign="top"><b>System Codes Maintenance</b><br>
                                                <input type="checkbox" name="Activity" value="CONADD,CONUPDATE,CONDELETE" <%= (searched_activity.indexOf("CONADD,CONUPDATE,CONDELETE")>=0)?"CHECKED":""%>>
                                                Contractor<br>
                                                <input type="checkbox" name="Activity" value="DEPTADD,DEPTUPDATE,DEPTDELETE" <%= (searched_activity.indexOf("DEPTADD,DEPTUPDATE,DEPTDELETE")>=0)?"CHECKED":""%>>
                                                Department<br><br>
                                                <b>CPS Calculation</b><br>
                                                <input type="checkbox" name="Activity" value="GENERATECPS" <%= (searched_activity.indexOf("GENERATECPS")>=0)?"CHECKED":""%>>
                                                Calculate Contractor Performance Score</td>
                                                <td valign="top"><b>CPAR Maintenance</b><br>
                                                <input type="checkbox" name="Activity" value="ACPAR_UPDATE" <%= (searched_activity.indexOf("ACPAR_UPDATE")>=0)?"CHECKED":""%>>
                                                Update CPAR<br>
                                                <input type="checkbox" name="Activity" value="ACPAR_DELETE" <%= (searched_activity.indexOf("ACPAR_DELETE")>=0)?"CHECKED":""%>>
                                                Delete CPAR<br>
                                                <input type="checkbox" name="Activity" value="ACPAR_RELEASE,ACPAR_UNRELEASE" <%= (searched_activity.indexOf("ACPAR_RELEASE,ACPAR_UNRELEASE")>=0)?"CHECKED":""%>>
                                                Release / Unrelease CPAR<br>
                                                <input type="checkbox" name="Activity" value="OUTSTANDING" <%= (searched_activity.indexOf("OUTSTANDING")>=0)?"CHECKED":""%>>
                                                Outstanding CPAR List<br>
                                                <input type="checkbox" name="Activity" value="ACPAR_EXPORT" <%= (searched_activity.indexOf("ACPAR_EXPORT")>=0)?"CHECKED":""%>>
                                                Search CPARs</td>
                                            </tr>
                                        </table>
                                      <table class="defaultfont" width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td align="center"><a href="#"><img src="../images/s_btn_select_all.jpg" width="63" height="21" alt="Select All" border="0" onClick="selectAll(form1)"></a><a href="#"><img src="../images/s_btn_unselect_all.jpg" width="86" height="21" alt="Unselect All" border="0" onClick="unselectAll(form1)"></a>                                             
                                        </td>
                                    </tr>
                                </table>

                                    </td>
                                </tr>
                                <tr> 
                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Date Range</td>
                                    <td class="tableVerticalContentAlignLeft1" colspan="2">From : 
                                    <input type="text" name="FromDate" size="15" maxlength="15" value="<%=searched_from_date%>" readonly/>
                                    <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'form1.FromDate',true, 'dd-mmm-yyyy'); return false;"><img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"></a> 
                                    To: 
                                    <input type="text" name="ToDate" size="15" maxlength="15" value="<%=searched_to_date%>" readonly/>
                                    <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'form1.ToDate',true, 'dd-mmm-yyyy'); return false;"><img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"></a> 
                                </tr>
                            </table>
                        </div>
                        </td>
                    </tr>
                </table>
                <table border="0" cellpadding="3" align="center">
                    <tr> 
                        <td width="50%">                                     
                            <div align="center"> <a href="#"><img src="../images/btn_search.jpg" width="98" height="42" name="Search" onClick="selectaction(form1,this)" border="0" alt="Search"></a></div>
                        </td>
                        <td width="50%">             
                            <div align="center"> <a href="#"><img src="../images/btn_reset.jpg" width="98" height="42" name="SearchReset" onClick="selectaction(form1,this)" border="0" alt="Reset searching criteria"></a></div>
                        </td>
                    </tr>
                </table>
                <% if (searched) {%>
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr class="title1"> 
                          <td colspan="3"> <%="Search Result"%> </td>
                    </tr>
                    <tr> 
                        <td align="left" colspan="1"> 
                            <%if (searched){
                                total_records = Integer.parseInt((String) allLogList.get(0));
                                allLogList.remove(0);
                                current_page = ((int) start_rec/page_size);
                                if (start_rec % page_size != 0) current_page++;
                                total_page = total_records/page_size;
                                if (total_records % page_size !=0) total_page++;
                            %>
                            <%=total_records%>  records found!<br>                        
                            <%}else{%>
                            <div align="left">No. of records : <%=allLogList.size()%> </div>
                            <%}%>
                        </td>
                    <%if (searched && total_records>0){%>                        
                        <td align="right" colspan="2"> 
                             Display &nbsp; 
                             <Select name="SelectPageSize" value = "All" onchange="changepagesize(form1)">                             
                             <option value="-1" <%= ((page_size==-1)?"SELECTED":"")%>>All</option>                                
                             <option value="10" <%= ((page_size==10)?"SELECTED":"")%>>10</option>
                             <option value="30" <%= ((page_size==30)?"SELECTED":"")%>>30</option>
                             <option value="50" <%= ((page_size==50)?"SELECTED":"")%>>50</option>
                             <option value="100" <%= ((page_size==100)?"SELECTED":"")%>>100</option>
                            </Select> &nbsp; records in a page
                        </td>                                                 
                        <%}else{%>
                        <td align="right" colspan="2"> &nbsp;</td>
                        <%}%> 
                    </tr>
                     <%if (searched && total_records>0  && page_size != -1){%>                    
                    <tr> 
                         <td align="left"> 
                             Record :  <%= start_rec%> to <%=(((start_rec + page_size -1 )< total_records)?(start_rec+page_size-1):total_records)%>   
                        </td>
                        <td align="center">
                            <table class="title1">
                                <tr>
                                <% if (current_page ==1){%>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <%}else{%>
                                <td><a href="#"><img src="../images/first.gif" border="0" alt="First Page" onclick="shiftpage(form1,1)"></a></td>                                
                                <td>&nbsp;</td>                                 
                                <td><a href="#"><img src="../images/previous.gif" border="0" alt="Previous Page" onclick="shiftpage(form1,<%=(current_page-1)%>)"></a></td>
                                <%}%>
                                <td>&nbsp;</td>                                
                                <td>Page <%= current_page%> / <%=total_page%></td>        
                                <td>&nbsp;</td>
                                <% if (current_page == total_page){%>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <%}else{%>
                                <td><a href="#"><img src="../images/next.gif" border="0" alt="Next Page" onclick="shiftpage(form1,<%=(current_page+1)%>)"></a></td>
                                <td>&nbsp;</td>
                                <td><a href="#"><img src="../images/last.gif" border="0" alt="Last Page" onclick="shiftpage(form1,<%=total_page%>)"></a></td>                                    
                                 <%}%>
                              </tr>
                             </table>
                        </td>
                        <td align="right"> 
                             Go To Page : 
                             <Select name="GotoPage" value = "1" onchange="changepage(form1)">
                             <% for (int i=1; i < total_page+1; i++){%>
                                <option value=<%=i%> <%= ((i==current_page)?"SELECTED":"")%>><%=i%></option>                                
                            <%}%>
                            </Select>
                        </td>
                    </tr> 
                    <%}%>                                       
                    <tr> 
                        <td colspan="3"> 
                            <div align="center"> 
                                <%if (! allLogList.isEmpty()){%>
                            
                                <table width="98%" cellspacing="1" class="tableBackground">
                                    <tr> 
                                        <td width="4%" nowrap class="tableHeaderAlignCenter1">No.</td>
                                        <td width="5%" nowrap class="tableHeaderAlignCenter1">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'audit_trail_id','<%= ((order_by.equals("audit_trail_id") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Log ID</a>
                                            <% if (order_by.equals("audit_trail_id") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("audit_trail_id") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>											
                                            </a>
                                        </td>
                                        <td width="17%" nowrap class="tableHeaderAlignCenter1">Activity 
                                        Date/Time</td>
                                        <td class="tableHeaderAlignCenter1" width="10%" nowrap>
                                            <a href="#" class="table_header" onClick="changeorder(form1,'user_id','<%= ((order_by.equals("user_id") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">User ID</a>
                                            <% if (order_by.equals("user_id") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("user_id") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                            
                                        <td class="tableHeaderAlignCenter1" width="25%" nowrap>
                                            <a href="#" class="table_header" onClick="changeorder(form1,'function_id','<%= ((order_by.equals("function_id") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Activity</a>
                                            <% if (order_by.equals("function_id") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("function_id") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                            
                                        <td class="tableHeaderAlignCenter1" width="13%" nowrap>Action Performed</td>                                        
                                        <td class="tableHeaderAlignCenter1" width="9%" nowrap>Record Keys</td>
                                        <td class="tableHeaderAlignCenter1" width="17%" nowrap>Remarks</td>
                                    </tr>
                                    <%
                                        // Display records
                                        for (int i = 0; i<allLogList.size();i++) {
                                                    LogInfo userLog = new LogInfo();
                                                    userLog = (LogInfo) allLogList.get(i);
                                                    String user_id = "";
                                                    String dept_name = "";
                                                    String log_id = "";
                                                    String activity_name = "";
                                                    String activity_desc = "";
                                                    String activity_date_time ="";
                                                    String record_key ="";
                                                    String remarks ="";

                                                    // get values from vector element
                                                    log_id = Integer.toString(userLog.getLogId());
                                                    user_id = userLog.getUserId();
                                                    dept_name = userLog.getDeptName();
                                                    activity_name = userLog.getActivityName();
                                                    activity_desc = userLog.getActionDesc();
                                                    activity_date_time = SysManager.getStringfromSQLDateTime(userLog.getActivityDateTime());
                                                    record_key = userLog.getRecordKey();
                                                    remarks = userLog.getRemarks();

                                    %>
                                    <tr> 
                                        <td class="tableContentAlignCenter1" nowrap width="4%"><%=(start_rec + i)%></td>
                                        <td class="tableContentAlignCenter1" nowrap width="5%"><%=log_id%></td>
                                        <td class="tableContentAlignLeft1" nowrap width="17%"><%=activity_date_time%></td>
                                        <td class="tableContentAlignLeft1" nowrap width="10%"><%=user_id%></td>
                                        <td class="tableContentAlignLeft1" width="25%"><%=activity_name%></td>
                                        <td class="tableContentAlignLeft1" nowrap width="13%"><%=activity_desc%></td>
                                        <td class="tableContentAlignLeft1" width="9%"><%=record_key%></td>
                                        <td class="tableContentAlignLeft1" width="17%"><%=remarks%></td>
                                    </tr>
                                    <%} // End for 
                                    }else{
                                    if (! searched) out.print("No records!");
                                    } // End if vector size >0 %>
                                    </table>
                            </div>
                        </td>
                    </tr>
                </table>
            
                <%}%>
                <input type="hidden" name="OrderBy" value="<%=order_by%>">
                <input type="hidden" name="OrderDir" value="<%=order_dir%>">
                <input type="hidden" name="StartRec" value="<%=start_rec%>">
                <input type="hidden" name="PageSize" value="<%=page_size%>">                
            </form>
        </fieldset>
    </body>
</html>
