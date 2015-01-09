<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.ACLDataBean,
                                qpses.business.ACLInfo,
                                java.util.List,
                                java.util.ArrayList,
                                java.util.HashMap,
                                qpses.util.SysManager" %>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // check for any searching parameter
    boolean searched = false;
    ACLDataBean aclDB =new ACLDataBean();
    List allACLList = new ArrayList();

    
    String order_by = "";
    String order_dir = "";
    HashMap orderHash = (HashMap) session.getAttribute("ACLUNLOCK_SEARCH_ORDER");
    if (orderHash !=null){
        order_by = (String) orderHash.get("ORDER_BY");
        order_dir = (String) orderHash.get("ORDER_DIR");
        session.removeAttribute("ACLUNLOCK_SEARCH_ORDER");
    } else{
        order_by = getValue(request.getParameter("OrderBy"));
        order_dir = getValue(request.getParameter("OrderDir"));
    }        
    if (order_by.equals("")) order_by = "Dept";
    if (order_dir.equals("")) order_dir = "ASC";
    
    allACLList = aclDB.selectLockedUsers(order_by,order_dir);

    // check  for message and get last input from session variables
    String msg =   getValue((String)  session.getAttribute("ACL_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("ACL_MSGTYPE"));
    if (!msg.equals("")){
        session.removeAttribute("ACL_MSG");
        session.removeAttribute("ACL_MSGTYPE");
        session.removeAttribute("ACL_DATA");
    }

%>  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Privilege Administration</title>        
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/unlockuser.js" type="text/javascript"></script>        
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Access Control List Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableNoBorder" align="center">
                    <tr class="title1">                         
                        <td> Unlock User</td>
                    </tr>
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%> 
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <%if (! allACLList.isEmpty()){%>
                                <table width="98%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td><a href="#"><img src="../images/s_btn_select_all.jpg" width="63" height="21" alt="Select All" border="0" onClick="selectAll()"></a><a href="#"><img src="../images/s_btn_unselect_all.jpg" width="86" height="21" alt="Unselect All" border="0" onClick="unselectAll()"></a> 
                                            <br>
                                            <div align="right"></div>
                                        </td>
                                    </tr>
                                    <tr>                      
                                        <td align="left" class="defaultfont"> 
                                            <div align="left">No. of records : <%=allACLList.size()%> </div>                            
                                        </td>
                                    </tr>									
                                </table>
                                
                                <table width="98%" cellspacing="1" class="tableBackground">
                                    <tr> 
                                        <td width="5%" nowrap class="tableHeaderAlignLeft1">&nbsp;</td>
                                        <td width="5%" nowrap class="tableHeaderAlignLeft1">&nbsp;</td>
                                        <td width="42%" nowrap class="tableHeaderAlignLeft1">
                                            <a href="#" class="table_header" onClick="changeorder(form1,'Dept','<%= ((order_by.equals("Dept") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Department</a>
                                            <% if (order_by.equals("Dept") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("Dept") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                                                        
                                        <td class="tableHeaderAlignCenter1" width="11%" nowrap>
                                            <a href="#" class="table_header" onClick="changeorder(form1,'UserId','<%= ((order_by.equals("UserId") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">User ID</a>
                                            <% if (order_by.equals("UserId") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("UserId") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                                                                                              
                                        <td class="tableHeaderAlignCenter1" width="13%" nowrap>
                                            <a href="#" class="table_header" onClick="changeorder(form1,'EffectiveDate','<%= ((order_by.equals("EffectiveDate") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Effective Date</a>
                                            <% if (order_by.equals("EffectiveDate") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("EffectiveDate") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                                                                                                                                     
                                        <td class="tableHeaderAlignCenter1" width="13%" nowrap>
                                            <a href="#" class="table_header" onClick="changeorder(form1,'ExpiryDate','<%= ((order_by.equals("ExpiryDate") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Expiry Date</a>
                                            <% if (order_by.equals("ExpiryDate") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("ExpiryDate") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                        
                                        <td class="tableHeaderAlignCenter1" width="10%" nowrap>Locked?</td>
                                        <td class="tableHeaderAlignCenter1" width="10%" nowrap>Locked Date/Time</td>
                                    </tr>
                                    <%
                                        // Display records
                                        for (int i = 0; i<allACLList.size();i++) {
                                            ACLInfo acl = new ACLInfo();
                                            acl = (ACLInfo) allACLList.get(i);
                                            // initialize variables
                                            String user_id = "";
                                            String dept_id ="";
                                            String dept_name= "";
                                            String locked_ind = "";
                                            String effective_date ="";
                                            String expiry_date ="";
                                            String last_updated_date="";

                                            // get values from vector element
                                            user_id = acl.getUserId();
                                            dept_id = acl.getDeptId();
                                            dept_name =  acl.getDeptName();
                                            locked_ind = (acl.getActiveInd()==-1) ? "No": "Yes";
                                            last_updated_date = SysManager.getStringfromSQLDateTime(acl.getLastUpdatedDate());
                                            if (acl.getEffectiveDate() != null) effective_date = SysManager.getStringfromSQLDate(acl.getEffectiveDate());
                                            if (acl.getExpiryDate() != null) expiry_date = SysManager.getStringfromSQLDate(acl.getExpiryDate());
                                    %>
                                    <tr> 
                                        <td nowrap class="tableContentAlignCenter1" width="5%"> 
                                            <input name="ChkEdit<%=i%>" type="checkbox" value="changed"/>
                                        </td>
                                        <td nowrap class="tableContentAlignCenter1" width="5%"><%=i+1%></td>
                                        <td class="tableContentAlignLeft1" width="42%"><%=dept_name%> 
                                            <input type="hidden" name="DeptId<%=i%>" value="<%=dept_id%>">
                                        </td>
                                        <td nowrap class="tableContentAlignLeft1" width="11%"><%=user_id%> 
                                            <input type="hidden" name="UserId<%=i%>" value="<%=user_id%>">
                                        </td>
                                        <td class="tableContentAlignCenter1" width="13%"><%=effective_date%></td>
                                        <td class="tableContentAlignCenter1" width="13%"><%=expiry_date%></td>
                                        <td class="tableContentAlignCenter1" width="10%"><%=locked_ind%></td>
                                        
              <td class="tableContentAlignCenter1" width="10%" nowrap><%=last_updated_date%></td>
                                    </tr>
                                    <%} // End for%>
                                </table>                            
                                
                                <table border="0" cellpadding="3" align="center">
                                    <tr> 
                                        <td width="50%"> 
                                            <div align="center"><a href="#"> </a><a href="#"><img src="../images/btn_unlock_selected.jpg" name="UnlockUser"  width="98" height="42" alt="Unlock Selected User" border="0"  onClick="selectaction(form1,this)"></a></div>
                                        </td>
                                    </tr>
                                </table>            
                                <%}else if(msg.equals("")){
            out.print("No locked user!");
                                    } // End if vector size >0 %>
                                <input type="hidden" name="RowNo" value="<%=allACLList.size()%>">
                            </div>
                        </td>
                    </tr>
                </table>
                    <input type="hidden" name="OrderBy" value="<%=order_by%>">
                    <input type="hidden" name="OrderDir" value="<%=order_dir%>">   
                    <input type="hidden" name="PostScreen" value="ACLUnlockUser.jsp">                                 
            </form>
        </fieldset> 		
    </body>
</html>
