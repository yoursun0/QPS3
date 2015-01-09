<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.DeptInfo,
                 qpses.business.DeptDataBean,
                 qpses.business.ContractorInfo,
                 qpses.business.ContractorDataBean,
                 qpses.business.WorkAssignmentInfo,
                 qpses.business.WorkAssignmentDataBean,
                 qpses.util.SysManager,
                  java.util.HashMap,
                 java.util.ArrayList,
                 java.util.List,
                 java.sql.Date" %>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // initialize variable
    String searched_dept_id = "";
    String searched_service_category_group="";
    String searched_wa_status="";
    String searched_contractor_id = "";
    String searched_invitation_start_date = "";
    String searched_invitation_end_date = "";
    String searched_award_start_date = "";
    String searched_award_end_date = "";
    String order_by = "";
    String order_dir = "";
    order_by = getValue(request.getParameter("OrderBy"));
    order_dir = getValue(request.getParameter("OrderDir"));
    if (order_by.equals("")) order_by = "ServiceCategory";
    if (order_dir.equals("")) order_dir = "ASC";
    List<WorkAssignmentInfo> allWorkAssignmentList = new ArrayList<WorkAssignmentInfo>();
    
    WorkAssignmentDataBean waDB =new WorkAssignmentDataBean();
   boolean searched = false;

    // check and display for message
    String msg =   getValue((String)  session.getAttribute("WORK_ASSIGNMENT_MSG"));
    if (!msg.equals("")){
        session.removeAttribute("WORK_ASSIGNMENT_MSG");
        session.removeAttribute("WORK_ASSIGNMENT_MSGTYPE");
    }

    //WorkAssignmentInfo wa = new WorkAssignmentInfo();
    HashMap searchParaHashMap = (HashMap) session.getAttribute("WORK_ASSIGNMENT_SEARCH_PARAMETER");
    if (searchParaHashMap!=null){
        DeptInfo searchParaDept = (DeptInfo) searchParaHashMap.get("DeptInfo");
        ContractorInfo searchParaContractor = (ContractorInfo) searchParaHashMap.get("ContractorInfo");
        WorkAssignmentInfo searchParaWA = (WorkAssignmentInfo) searchParaHashMap.get("WorkAssignment");
        List<Date> searchParaDates = (List<Date>) searchParaHashMap.get("DateFilters");
        
        if (searchParaDates.get(0) != null) searched_invitation_start_date = SysManager.getStringfromSQLDate(searchParaDates.get(0));
        if (searchParaDates.get(1) != null) searched_invitation_end_date = SysManager.getStringfromSQLDate(searchParaDates.get(1));
        if (searchParaDates.get(2) != null) searched_award_start_date = SysManager.getStringfromSQLDate(searchParaDates.get(2));
        if (searchParaDates.get(3) != null) searched_award_end_date = SysManager.getStringfromSQLDate(searchParaDates.get(3));
        
        searched_dept_id = getValue(searchParaDept.getCombinedDeptId());
        searched_contractor_id = getValue(searchParaContractor.getContractorId());
        searched_service_category_group = getValue(searchParaWA.getServiceCategoryGroup());
        searched_wa_status = getValue(searchParaWA.getStatus());
        
        allWorkAssignmentList = waDB.searchWorkAssignment(searchParaWA,searchParaDates,order_by,order_dir);
        searched = true;
    }

    // Get department list
    DeptDataBean dpDB = new DeptDataBean();
    List<DeptInfo> deptList = dpDB.selectDept();
    
 	// Get contractor list
    ContractorDataBean conDB = new ContractorDataBean();
    List<ContractorInfo> conList = conDB.selectContractor();

%>			
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Work Assignment Information Import</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <link rel="stylesheet" type="text/css" href="../styles/calendar.css">               
        <script language="JavaScript" type="text/javascript" src="../js/wacheck.js"></script>
        <script language="javascript" type="text/javascript" src="../js/simplecalendar.js"></script>            		
    </head>
    <body> 
        <%@include file="include/header.jsp"%>        
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Work Assignment Information Maintenance</td></tr></table>            		
            <form name="form1" method="POST" action="WorkAssignmentSearch.jsp">            
                
                <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" class="tableBorder">
                    <tr> 
                        <td width="24%" class="tabUnSelectLeft" onclick="changepage('List')">Work 
                        Assignment List </td>
                        <td width="18%" class="tabUnSelectMiddle" onclick="changepage('Import')">Import</td>
                        <td width="15%" class="tabSelectedRight">Search</td>
                        <td width="43%"> 
                            <div align="right">&nbsp;</div>
                        </td>
                    </tr>
                </table>					
                    
                <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" class="tableborder">
                    <tr> 
                        <td> 
                            <table width="100%" border="0" cellspacing="0" cellpadding="5">
                                <tr class="tableborder"> 
                                    <td class="title1"> Search Work Assignment</td>
                                </tr>
                                <tr class="tableborder"> 
                                    <td> 
                                        <div align="left">Input searching criteria below: </div>
                                    </td>
                                </tr>
                                <tr class="tableborder"> 
                                    <td> 
                                        <div align="center"> 
                                            
                                            <table  width="98%" cellspacing="1" class="tableBackground">
                                                <tr>
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Service Category/Group</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%">
                                                        <select name="ServiceCategoryGroup" class="inputText">
                                                            <option value="" <%if (searched_service_category_group.equals("")) out.write("SELECTED");%>>All Category/Group</option>
                                                            
                                                            <option value="1" <%if (searched_service_category_group.equals("1")) out.write("SELECTED");%>>Category 
                                                            1</option>
                                                            <option value="2/N" <%if (searched_service_category_group.equals("2/N")) out.write("SELECTED");%>>Category 
                                                            2 Minor Group</option>
                                                            <option value="2/J" <%if (searched_service_category_group.equals("2/J")) out.write("SELECTED");%>>Category 
                                                            2 Major Group</option>
                                                            <option value="3/N" <%if (searched_service_category_group.equals("3/N")) out.write("SELECTED");%>>Category 
                                                            3 Minor Group</option>
                                                            <option value="3/J" <%if (searched_service_category_group.equals("3/J")) out.write("SELECTED");%>>Category 
                                                            3 Major Group</option>
                                                            
                                                            <option value="4" <%if (searched_service_category_group.equals("4")) out.write("SELECTED");%>>Category 
                                                            4</option>				
                                                        </select>
                                                    </td>
                                                </tr>
                                                
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Department</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <select name="DeptId" class="inputText">
                                                            <option value="">All Department</option>
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
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Assignment Status</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%">
                                                        <select name="WAStatus" class="inputText">
                                                            <option value="" <%if (searched_wa_status.equals("")) out.write("SELECTED");%>>All Status</option>
                                                            <option value="b" <%if (searched_wa_status.equals("b")) out.write("SELECTED");%>>Invitation Issued</option>
                                                            <option value="p" <%if (searched_wa_status.equals("p")) out.write("SELECTED");%>>Proposal submitted</option>
                                                            <option value="n" <%if (searched_wa_status.equals("n")) out.write("SELECTED");%>>Assignment awarded</option>
                                                            <option value="c" <%if (searched_wa_status.equals("c")) out.write("SELECTED");%>>Assignment completed</option>
                                                            <option value="w" <%if (searched_wa_status.equals("w")) out.write("SELECTED");%>>Invitation withdrew</option>
                                                            <option value="t" <%if (searched_wa_status.equals("t")) out.write("SELECTED");%>>Assignment terminated</option>
                                                            <option value="x" <%if (searched_wa_status.equals("x")) out.write("SELECTED");%>>Invitation cancelled</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Invitation Date Range</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <input class="text" type="text" name="InvitationStartDate" value="<%=searched_invitation_start_date %>" readonly>
                       									<a class="cal" href="javascript: void(0);" 
                       									     onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                     									     onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                          									 onClick="g_Calendar.show(event, 'form1.InvitationStartDate', true, 'dd-mmm-yyyy'); return false;"> 
                            									<img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom">
                        								</a>
                        								</input>
                        								 &nbsp;&nbsp; to &nbsp;&nbsp;
                        								<input class="text" type="text" name="InvitationEndDate" value="<%=searched_invitation_end_date %>" readonly>
                       									<a class="cal" href="javascript: void(0);" 
                       									     onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                     									     onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                          									 onClick="g_Calendar.show(event, 'form1.InvitationEndDate', true, 'dd-mmm-yyyy'); return false;"> 
                            									<img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom">
                        								</a>
                        								</input>
                                                    </td>
                                                </tr>

												<tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Awarded Date Range</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <input class="text" type="text" name="AwardStartDate" value="<%=searched_award_start_date %>" readonly>
                       									 <a class="cal" href="javascript: void(0);" 
                       									     onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                     									     onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                          									 onClick="g_Calendar.show(event, 'form1.AwardStartDate', true, 'dd-mmm-yyyy'); return false;"> 
                            									<img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"> 
                        								 </a>
                        								</input>
                        								 &nbsp;&nbsp; to &nbsp;&nbsp;
                        								<input class="text" type="text" name="AwardEndDate" value="<%=searched_award_end_date %>" readonly>
                       									 <a class="cal" href="javascript: void(0);" 
                       									     onMouseOver="if (timeoutId) clearTimeout(timeoutId); window.status='Show Calendar'; return true;" 
                     									     onMouseOut="if (timeoutDelay) calendarTimeout(); window.status='';" 
                          									 onClick="g_Calendar.show(event, 'form1.AwardEndDate', true, 'dd-mmm-yyyy'); return false;"> 
                            									<img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt="" align="absbottom"> 
                        								 </a>
                        								</input>
                                                    </td>
                                                </tr>
                                                
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Awarded Contractor</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <select name="ContractorId" class="inputText">
                                                            <option value="">All Contractors</option>
                                                            <%      
                                                                for (int i =0; i<conList.size(); i++){
           															ContractorInfo contractor = (ContractorInfo) conList.get(i);
            														out.write("<option ");
            														out.write("value=\""+ contractor.getContractorId()+"\"");
            														if (contractor.getContractorId().equals(searched_contractor_id)) out.write(" SELECTED");
            														out.write(">"+contractor.getContractorName()+"</option>\n");
                                                                }
                                                            %>
                                                        </select>
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
                                        <div align="center"> <a href="#"><img src="../images/btn_search.jpg" width="98" height="42" name="Search" onClick="selectaction(form1,this)" border="0" alt="Search"></a></div>
                                    </td>
                                    <td width="50%"> 
                                        <div align="center"> <a href="#"><img src="../images/btn_reset.jpg" width="98" height="42" name="SearchReset" onClick="selectaction(form1,this)" border="0" alt="Reset searching criteria"></a></div>
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
                        <td> Search Result </td>
                    </tr>
                    <tr> 
                        <td align="left"> 
                            <%=allWorkAssignmentList.size()%> records found!<br>                        
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <%if (! allWorkAssignmentList.isEmpty()){%>			  
                                <table width="98%" cellspacing="1" class="tableBackground">
                                    <tr> 
                                        <td align="center" width="3%" class="tableHeaderAlignCenter1">&nbsp;</td>
                                        <td align="center" width="6%" class="tableHeaderAlignCenter1">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'ServiceCategory','<%= ((order_by.equals("ServiceCategory") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Service<BR>Cat/Gp</a>
                                            <% if (order_by.equals("ServiceCategory") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("ServiceCategory") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                                                
                                        <td align="center" width="9%" class="tableHeaderAlignCenter1">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'Dept','<%= ((order_by.equals("Dept") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">B/D</a>
                                            <% if (order_by.equals("Dept") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("Dept") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                                                
                                        <td align="center" width="8%" class="tableHeaderAlignCenter1">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'IssueDate','<%= ((order_by.equals("IssueDate") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Invitation Issue Date</a>
                                            <% if (order_by.equals("IssueDate") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("IssueDate") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                                               
                                        <td align="center" width="8%" class="tableHeaderAlignCenter1">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'ClosingDate','<%= ((order_by.equals("ClosingDate") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Invitation Closing Date</a>
                                            <% if (order_by.equals("ClosingDate") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("ClosingDate") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                                                                               
                                        <td align="center" width="30%" class="tableHeaderAlignCenter1">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'Title','<%= ((order_by.equals("Title") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Work Assignment Title</a>
                                            <% if (order_by.equals("Title") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("Title") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>
                                        <td align="center" width="8%" class="tableHeaderAlignCenter1">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'Status','<%= ((order_by.equals("Status") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Status</a>
                                            <% if (order_by.equals("Status") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("Status") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                
                                        <td align="center" width="8%" class="tableHeaderAlignCenter1">
                                  		    <a href="#" class="table_header" onClick="changeorder(form1,'AwardedDate','<%= ((order_by.equals("AwardedDate") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Awarded Date</a>
                             		        <% if (order_by.equals("AwardedDate") && order_dir.equals("ASC")){%>
                                    		<img src="../images/up_arrow.gif" border = 0>
                                    		<%}%>
                                    		<% if (order_by.equals("AwardedDate") && order_dir.equals("DESC")){%>
                                    		<img src="../images/down_arrow.gif" border = 0>
                                    		<%}%>																						                                        
                                		</td> 
                                		<td align="center" width="12%" class="tableHeaderAlignCenter1">
                                    		<a href="#" class="table_header" onClick="changeorder(form1,'Contractor','<%= ((order_by.equals("Contractor") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Awarded Contractor</a>
                                    		<% if (order_by.equals("Contractor") && order_dir.equals("ASC")){%>
                                		    <img src="../images/up_arrow.gif" border = 0>
                         		            <%}%>
                         		            <% if (order_by.equals("Contractor") && order_dir.equals("DESC")){%>
                       		    	        <img src="../images/down_arrow.gif" border = 0>
                                    		<%}%>																						                                        
                                		</td> 
                                		<td align="center" width="8%" class="tableHeaderAlignCenter1">Authorised 
                                		Person</td>
                                    </tr>
                                    <%
                                        // Display records
                                        for (int i = 0; i<allWorkAssignmentList.size();i++) {
                                                    WorkAssignmentInfo wa = new WorkAssignmentInfo();
                                                    wa = (WorkAssignmentInfo) allWorkAssignmentList.get(i);
                                                    // initialize variables
                                                    String service_category = "";
                                                    String dept_id ="";
                                                    String issued_date ="";
                                                    String closing_date ="";
                                                    String awarded_date ="";
                                                    String awarded_contractor ="";
                                                    String wa_title="";
                                                    String wa_status="";
                                                    String authorized_person="";

                                                    // get values from vector element
                                                    service_category = wa.getServiceCategoryGroup();
                                                    dept_id = wa.getDepartmentId();
                                                    issued_date = SysManager.getStringfromSQLDate(wa.getIssuedDate());
                                                    closing_date = SysManager.getStringfromSQLDate(wa.getClosingDate());
                                                    if  (wa.getAwardedDate() != null){
                                                    	awarded_date = SysManager.getStringfromSQLDate(wa.getAwardedDate());
                                                    }
                                                    wa_title=wa.getTitle();
                                                    if  (wa.getStatus().equals("b")){
                                                        wa_status="Invitation Issued";
                                                    }else if (wa.getStatus().equals("p")){
                                                        wa_status ="Proposal submitted";
                                                    }else if (wa.getStatus().equals("n")){
                                                        wa_status ="Assignment awarded";    
                                                    }else if (wa.getStatus().equals("c")){
                                                        wa_status ="Assignment completed";    
                                                    }else if (wa.getStatus().equals("w")){
                                                        wa_status ="Invitation withdrew";
                                                    }else if (wa.getStatus().equals("t")){
                                                        wa_status ="Assignment terminated";    
                                                    }else if (wa.getStatus().equals("x")){
                                                        wa_status ="Invitation cancelled";    
                                                    }
                                                    authorized_person=wa.getAuthorizedPerson();
                                                    awarded_contractor=getValue(wa.getAwardedContractor());
                                    %>
                                    <tr valign="top"> 
                                        <td align="center" width="3%" nowrap class="tableContentAlignCenter1"><%=(i+1)%></td>
                                        <td align="center" width="6%" nowrap class="tableContentAlignCenter1"><%=service_category%></td>
                                        <td align="center" width="9%" class="tableContentAlignCenter1"><%=dept_id%></td>
                                        <td align="center" width="8%" nowrap class="tableContentAlignCenter1"><%=issued_date%></td>
                                        <td align="center" width="8%" nowrap class="tableContentAlignCenter1"><%=closing_date%></td>
                                        <td width="30%" class="tableContentAlignLeft1"><%=wa_title%></td>
                                        <td width="8%" nowrap class="tableContentAlignCenter1"><%=wa_status%></td>
                                        <td width="8%" nowrap class="tableContentAlignCenter1"><%=awarded_date%></td>
                                        <td width="12%" class="tableContentAlignCenter1"><%=awarded_contractor%></td>
                                        <td width="8%" class="tableContentAlignCenter1"><%=authorized_person%> 
                                        </td>
                                    </tr>
                                    <%} // End for                                     
                                    } // End if vector size >0 %>
                                    </table>
                            </div>
                        </td>
                    </tr>
                </table>            
                <%}%>
                <BR>
                <input type="hidden" name="OrderBy" value="<%=order_by%>">
                <input type="hidden" name="OrderDir" value="<%=order_dir%>">               
                
            </form>
        </fieldset> 		    
    </body>
</html>
