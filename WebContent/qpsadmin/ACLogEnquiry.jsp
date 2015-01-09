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
    String searched_dept_id = "";

    String searched_status = "SUCCESS,WRONG PASSWORD,EXPIRED,LOCKED,UNAUTHORIZED ACCESS";
    String searched_activity ="";
    String searched_from_date ="";
    String searched_to_date ="";

    String order_by = "log_id";
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

    LogInfo searchPara = (LogInfo) session.getAttribute("ACCESS_LOG_ENQUIRY_PARAMETER");
    if (searchPara!=null){
        order_by = getValue(request.getParameter("OrderBy"));
        order_dir = getValue(request.getParameter("OrderDir"));
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
   
        if (order_by.equals("")) order_by = "log_id";
        if (order_dir.equals("")) order_dir = "DESC";

        allLogList = logDB.searchAccessLog(searchPara,order_by,order_dir,start_rec,page_size);
        searched = true;
        searched_user_id = getValue(searchPara.getUserId());
        searched_dept_id = getValue(searchPara.getDeptId());
        searched_activity = getValue(searchPara.getActivity());
        searched_status = getValue(searchPara.getStatus());
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
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Audit Trail Enquiry</title>        
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <link rel="STYLESHEET" type="text/css" href="../styles/calendar.css">        
        <script language="JavaScript" src="../js/simplecalendar.js" type="text/javascript"></script>
        <script language="JavaScript" src="../js/aclogenquiry.js" type="text/javascript"></script>        
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr>
                
                <td>Access Log Enquiry</td>
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
                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Department</td>
                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                        <select name="DeptId">
                                            <option value="">All Departments</option>
                                            <%      
                                                for (int i =0; i<deptList.size(); i++){
            DeptInfo dept = (DeptInfo) deptList.get(i);
            out.write("<option ");
            out.write("value=\""+ dept.getCombinedDeptId()+"\"");
            if (dept.getCombinedDeptId().equals(searched_dept_id)) out.write(" SELECTED");
            out.write(">"+dept.getDeptName()+"</option>\n");
                                                }
                                            %>
                                        </select>
                                    </td>
                                </tr>
                                <tr> 
                                    <td class="tableVerticalHeaderAlignLeft1" width="25%"> Activity</td>
                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                    <input type="radio" name="Activity" value="" <%= ((searched_activity.equals(""))?"CHECKED":"")%>/>
                                    All 
                                    <input type="radio" name="Activity" value="IN" <%= ((searched_activity.equals("IN"))?"CHECKED":"")%>/>
                                    System IN 
                                    <input type="radio" name="Activity" value="OUT" <%= ((searched_activity.equals("OUT"))?"CHECKED":"")%> />
                                    System OUT</td>
                                </tr>
                                <tr> 
                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Status</td>
                                    <td class="tableVerticalContentAlignLeft1" colspan="2"> 
                                    <p> 
                                    <input type="checkbox" name="Status" value="SUCCESS" <%= ((searched_status.indexOf("SUCCESS")>=0)?"CHECKED":"")%>>
                                    Success<br>
                                    <input type="checkbox" name="Status" value="WRONG PASSWORD" <%= ((searched_status.indexOf("WRONG PASSWORD"))>=0?"CHECKED":"")%>>
                                    Wrong Password<br>
                                    <input type="checkbox" name="Status" value="EXPIRED" <%= ((searched_status.indexOf("EXPIRED"))>=0?"CHECKED":"")%>>
                                    Expired<br>
                                    <input type="checkbox" name="Status" value="LOCKED" <%= ((searched_status.indexOf("LOCKED"))>=0?"CHECKED":"")%>>
                                    Locked<br>
                                    <input type="checkbox" name="Status" value="UNAUTHORIZED ACCESS" <%= ((searched_status.indexOf("UNAUTHORIZED ACCESS")>=0)?"CHECKED":"")%>>
                                    Unauthorised Access<br>

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
                    <tr class="title1" > 
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
                            <%=total_records%> records found!<br>                        
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
                     <%if (searched && total_records>0 && page_size != -1){%>                    
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
                                        <td width="7%" nowrap class="tableHeaderAlignCenter1">No.</td>                                    
                                        <td width="10%" nowrap class="tableHeaderAlignCenter1">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'log_id','<%= ((order_by.equals("log_id") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Log ID</a>
                                            <% if (order_by.equals("log_id") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("log_id") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>											
                                        </td>                                    
                                        <td width="18%" nowrap class="tableHeaderAlignCenter1">Activity Date/Time</td>
                                    
                                        <td width="17%" nowrap class="tableHeaderAlignCenter1" >
                                            <a href="#" class="table_header" onClick="changeorder(form1,'dp_department_id','<%= ((order_by.equals("dp_department_id") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Department Code (DP)</a>
                                            <% if (order_by.equals("dp_department_id") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("dp_department_id") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						
                                        </td>                                    
                                        <td class="tableHeaderAlignCenter1" width="19%" nowrap>
                                        <a href="#" class="table_header" onClick="changeorder(form1,'user_id','<%= ((order_by.equals("user_id") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">User ID</a>
                                            <% if (order_by.equals("user_id") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("user_id") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                    
                                        <td class="tableHeaderAlignCenter1" width="17%" nowrap>
                                        <a href="#" class="table_header" onClick="changeorder(form1,'access_type','<%= ((order_by.equals("access_type") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Activity</a>
                                            <% if (order_by.equals("access_type") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("access_type") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                    
                                        <td class="tableHeaderAlignCenter1" width="12%" nowrap>
                                        <a href="#" class="table_header" onClick="changeorder(form1,'access_status','<%= ((order_by.equals("access_status") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Status</a>
                                            <% if (order_by.equals("access_status") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("access_status") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>
                                    </tr>
                                    <%
                                        // Display records
                                        for (int i = 0; i< allLogList.size();i++) {
                                                    LogInfo acLog = new LogInfo();
                                                    acLog = (LogInfo) allLogList.get(i);
                                                    String user_id = "";
                                                    String dept_id = "";
                                                    String log_id = "";
                                                    String activity = "";
                                                    String activity_status = "";
                                                    String activity_date_time ="";

                                                    // get values from vector element
                                                    log_id = Integer.toString(acLog.getLogId());
                                                    user_id = acLog.getUserId();
                                                    dept_id = acLog.getDPDeptId();
                                                    activity = acLog.getActivity();
                                                    activity_status = acLog.getStatus();
                                                    activity_date_time = SysManager.getStringfromSQLDateTime(acLog.getActivityDateTime());

                                    %>
                                    <tr> 
                                    
                                        <td nowrap class="tableContentAlignCenter1" width="7%"><%=(start_rec + i)%></td>
                                    
                                        <td class="tableContentAlignCenter1" width="10%"><%=log_id%></td>
                                    
                                        <td class="tableContentAlignLeft1" width="18%"><%=activity_date_time%></td>
                                    
                                        <td class="tableContentAlignLeft1" width="17%"><%=dept_id%></td>
                                    
                                        <td nowrap class="tableContentAlignLeft1" width="19%"><%=user_id%></td>
                                    
                                        <td class="tableContentAlignCenter1" width="17%"><%="SYSTEM "+activity%></td>
                                    
                                        <td class="tableContentAlignCenter1" width="12%"><%=activity_status%></td>
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
            <BR>
        </fieldset>
        <p></p>
    </body>
</html>
