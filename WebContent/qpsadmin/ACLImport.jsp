<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.ACLInfo,
                                qpses.business.ACLDataBean,
                                qpses.business.DeptInfo,
                                qpses.business.DeptDataBean,
                                qpses.util.SysManager, 
                                java.util.List"%>
<%

    // initialize variable
    String dept_id = "";
    String expiry_date ="";

    ACLInfo acl = new ACLInfo();

    // check  for message and get last input from session variables
    String msg =   getValue((String)  session.getAttribute("ACL_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("ACL_MSGTYPE"));

    if (!msg.equals("")){
        session.removeAttribute("ACL_MSG");
        session.removeAttribute("ACL_MSGTYPE");
    }

    acl = (ACLInfo) session.getAttribute("ACL_DATA");
    if (acl!=null){
        dept_id = acl.getDeptId();
        if (acl.getExpiryDate()!=null) expiry_date =  SysManager.getStringfromSQLDate(acl.getExpiryDate());
        session.removeAttribute("ACL_DATA");
    }

    // Get department list
    DeptDataBean dpDB = new DeptDataBean();
    List deptList = dpDB.selectDept();

    // Get list of expiry date from end date of each assessment period
    ACLDataBean aclDB = new ACLDataBean();
    List expiryDateList = aclDB.selectExpiryDate();

%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Import Users</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <link rel="STYLESHEET" type="text/css" href="../styles/calendar.css">
        <script language="JavaScript" src="../js/simplecalendar.js" type="text/javascript"></script>                        
        <script language="JavaScript" src="../js/aclcheck.js" type="text/javascript"></script>                         
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Access Control List Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="" enctype="multipart/form-data">
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td width="20%" class="tabUnselectLeft" onclick="changepage('List')">Access 
                        Control List</td>
                        <td width="21%" class="tabUnselectMiddle" onclick="changepage('Add')">Add User </td>
                        <td width="17%" class="tabSelectedMiddle">Import 
                        Users </td>
                        <td width="16%" class="tabUnselectRight" onclick="changepage('Search')">Search 
                        User </td>
                        <td width="26%">&nbsp;</td>                   
                    <tr> 
                </table>       
                    <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <td class="title1"> 
                        <div align="left">Import Users</div>
                    </td>
                </tr>
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%> 
                            <table width="100%" border="0" cellspacing="0" cellpadding="5">
                                <tr> 
                                    <td> 
                                        <div align="center"> 
                                            <table  width="98%" cellspacing="1" class="tableBackground">
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Department</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <select name="DeptId">
                                                            <option value="">Please select department</option>
                                                            <%      
                                                                for (int i =0; i<deptList.size(); i++){
            DeptInfo dept = (DeptInfo) deptList.get(i);
            out.write("<option ");
            out.write("value=\""+ dept.getCombinedDeptId()+"\"");
            if (dept.getCombinedDeptId().equals(dept_id)) out.write(" SELECTED");
            out.write(">"+dept.getDeptName()+"</option>\n");
                                                                }
                                                            %>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1">Import File</td>
                                                    <td class="tableVerticalContentAlignLeft1"> 
                                                        <input type="file" name="ImportFileName" value="" class="inputText" size="80"  />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="tableVerticalHeaderAlignLeft1">Expiry Date</td>
                                                    <td class="tableVerticalContentAlignLeft1">
                                                        <input type="radio" name="ExpiryDate" value="<%=expiryDateList.get(0)%>"   
                                                        <%=(expiry_date.equals(""))?"CHECKED":""%>                                                                
                                                        <%=(expiryDateList.get(0).equals(expiry_date))?"CHECKED":""%>>
                                                        &nbsp;<%=expiryDateList.get(0)%> 
                                                        <% if (expiryDateList.size() > 1){%>
                                                        &nbsp; 
                                                        <input type="radio" name="ExpiryDate" value="<%=expiryDateList.get(1)%>" 
                                                        <%=(expiryDateList.get(1).equals(expiry_date))?"CHECKED":""%>>
                                                        &nbsp;<%=expiryDateList.get(1)%> 
                                                        <%}%>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <div align="center"></div>
                            <table border="0" cellpadding="3" align="center">
                                <tr> 
                                    <td width="50%">                                        
                                        <div align="center"><a href="#"> <img src="../images/btn_import.jpg" width="98" height="42" name="ConfirmImport" onclick="selectaction(form1,this)" border="0" alt="Import"></a></div>
                                    </td>
                                    <td width="50%">                                        
                                        <div align="center"><a href="#"><img src="../images/btn_cancel.jpg" width="98" height="42" name="Cancel" onclick="selectaction(form1,this)" border="0" alt="Cancel"></a></div>
                                    </td>
                                </tr>
                            </table>    
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="PostScreen" value="ACLList.jsp">                      
            </form>
        </fieldset> 		    			      		
    </body>
</html>
