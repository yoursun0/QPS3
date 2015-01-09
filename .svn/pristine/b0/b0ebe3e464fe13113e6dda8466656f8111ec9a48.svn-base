<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.DeptDataBean,
                                qpses.business.DeptInfo,
                                java.util.HashMap,
                                java.util.List,
                                java.util.ArrayList"%>
<%
    // initialize variable
    String searched_dp_dept_id = "";
    String searched_soa_dept_id = "";
    String searched_dept_name = "";

    boolean searched = false;
    DeptDataBean deptDB =new DeptDataBean();
    List allDeptList = new ArrayList();

    String order_by = "";
    String order_dir = "";
    HashMap orderHash = (HashMap) session.getAttribute("DEPT_SEARCH_ORDER");
    if (orderHash !=null){
        order_by = (String) orderHash.get("ORDER_BY");
        order_dir = (String) orderHash.get("ORDER_DIR");
        session.removeAttribute("DEPT_SEARCH_ORDER");
    } else{
        order_by = getValue(request.getParameter("OrderBy"));
        order_dir = getValue(request.getParameter("OrderDir"));
    }
    if (order_by.equals("")) order_by = "Name";
    if (order_dir.equals("")) order_dir = "ASC";    


    // get values from session, if any
    DeptInfo searchPara = (DeptInfo) session.getAttribute("DEPARTMENT_SEARCH_PARAMETER");
    if (searchPara!=null){
        searched_dp_dept_id = searchPara.getDPDeptId();
        searched_soa_dept_id = searchPara.getSOADeptId();
        searched_dept_name = searchPara.getDeptName();
        allDeptList = deptDB.searchDept(searchPara,order_by,order_dir);
        searched = true;
    }
    // check  for message and get last input from session variables
    String msg =   getValue((String)  session.getAttribute("DEPARTMENT_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("DEPARTMENT_MSG_TYPE"));
    if (!msg.equals("")){
        session.removeAttribute("DEPARTMENT_MSG");
        session.removeAttribute("DEPARTMENT_MSG_TYPE");
    }

%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Search Department</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/deptcheck.js" type="text/javascript"></script>                   
    </head>
    <body>
        <%@include file="include/header.jsp"%>        
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Department Code Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">            
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td width="14%" class="tabUnselectLeft"  onclick="changepage('List')">B/D List</td>
                        <td width="11%" class="tabUnselectMiddle"  onclick="changepage('Add')">Add 
                        </td>
                        <td width="12%" class="tabSelectedRight">Search</td>
                        <td width="63%">&nbsp;</td>
                    </tr>
                </table> 
                
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr class="title1"> 
                        <td>Search Department</td>
                    </tr>
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%></td>
                    </tr> 
                    <tr> 
                        <td> 
                            <div align="left">Input searching criteria below:</div>
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <table  width="98%" cellspacing="1" class="tableBackground">
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="26%">Department 
                                        Code (DP)</td>
                                        <td class="tableVerticalContentAlignLeft1" width="74%"> 
                                            <input type="text" name="DPDeptId" value="<%=searched_dp_dept_id%>" size="50" maxlength="10" class="inputText" />
                                        </td>
                                    </tr>
                                    <tr width="15%"> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="26%">Department 
                                        Code (SOA-QPS)</td>
                                        <td class="tableVerticalContentAlignLeft1" width="74%"> 
                                            <input type="text" name="SOADeptId" value="<%=searched_soa_dept_id%>" size="50" maxlength="10" class="inputText" />
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="26%">Department 
                                        Name</td>
                                        <td class="tableVerticalContentAlignLeft1" width="74%"> 
                                            <input type="text" name="DeptName" class="inputText" size="80" maxlength="200" value="<%=searched_dept_name%>">
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
                <BR>
                <% if (searched) {%>
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr class="title1"> 
                        <td> Search Result </td>
                    </tr>
                    <tr> 
                        <td align="left"> 
                            <%=allDeptList.size()%> records found!<br>                        
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                            <%if (! allDeptList.isEmpty()){%>
                            <div align="center"> 
                                <table width="98%" cellspacing="1" class="tableBackground">
                                    <tr> 
                                        <td width="8%" nowrap class="tableHeaderAlignLeft1" rowspan="2">&nbsp;</td>
                                        <td width="15%" class="tableHeaderAlignCenter1" rowspan="2">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'DPCode','<%= ((order_by.equals("DPCode") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Department 
                                            Code <BR>(DP)</a>
                                            <% if (order_by.equals("DPCode") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("DPCode") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                
                                        <td width="16%" class="tableHeaderAlignCenter1" rowspan="2">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'SOACode','<%= ((order_by.equals("SOACode") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Department 
                                            Code<BR>(SOA-QPS)</a>
                                            <% if (order_by.equals("SOACode") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("SOACode") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                                                        
                                        <td class="tableHeaderAlignCenter1" width="48%" rowspan="2">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'Name','<%= ((order_by.equals("Name") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Department 
                                            Name</a>
                                            <% if (order_by.equals("Name") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("Name") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                                                                                                
                                        <td class="tableHeaderAlignCenter1" width="13%" nowrap rowspan="2">Action</td>
                                    </tr>
                                    <tr> </tr>
                                    <%
                                        // Display records
                                        for (int i = 0; i<allDeptList.size();i++) {
                                                    DeptInfo dept = new DeptInfo();
                                                    dept = (DeptInfo) allDeptList.get(i);

                                                    // get values from vector element
                                                    String dp_dept_id = dept.getDPDeptId();
                                                    String soa_dept_id = dept.getSOADeptId();
                                                    String dept_name =  dept.getDeptName();
                                    %>
                                    <tr> 
                                        <td nowrap class="tableContentAlignCenter1" width="8%"><%=i+1%></td>
                                        <td class="tableContentAlignLeft1" width="15%"><%=dp_dept_id%></td>
                                        <td class="tableContentAlignLeft1" width="16%"><%=soa_dept_id%></td>
                                        <td class="tableContentAlignLeft1" width="48%"><%=dept_name%></td>                                        
                                        <td nowrap class="tableContentAlignLeft1" width="13%">
                                        <a href="#"><img name="Update" src="../images/s_btn_update.jpg" width="63" height="21" alt="Update" border="0" onClick="selectaction(form1,this,'<%=dp_dept_id%>','<%=soa_dept_id%>')"></a> 
                                        <a href="#"><img name="Delete" src="../images/s_btn_delete.jpg" width="63" height="21" alt="Delete" border="0" onClick="selectaction(form1,this,'<%=dp_dept_id%>','<%=soa_dept_id%>')"></a></td>
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
                <input type="hidden" name="selectedKey1" value="">
                <input type="hidden" name="selectedKey2" value="">                
                <input type="hidden" name="PostScreen" value="DeptSearch.jsp">                 
                <input type="hidden" name="OrderBy" value="<%=order_by%>">
                <input type="hidden" name="OrderDir" value="<%=order_dir%>">                                 
            </form>  
        </fieldset>
    </body>
</html>
