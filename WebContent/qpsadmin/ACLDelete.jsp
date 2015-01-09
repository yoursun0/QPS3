<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.ACLInfo,
                                qpses.business.ACLDataBean,
                                qpses.util.SysManager"%>
<%

    // initialize variable
    String user_id = "";
	String first_name = "";
	String last_name = "";
	String dept_name = "";
    String email = "";
    String user_group = "";
    int active_ind = 0;
    String creation_date="";
    String last_updated_date="";
    String last_updated_by="";
    String org_key1="";
    String org_key2="";
    String org_key3="";
    String effective_date ="";
    String expiry_date ="";
    int access_failure_count=0;

    // get parameters from previous screen
    String pre_screen = getValue(request.getParameter("PostScreen"));
    if (pre_screen.equals("")){
        pre_screen = (String) session.getAttribute("ACL_POST_SCREEN");
    }
    String order_by = getValue(request.getParameter("OrderBy"));
    String order_dir = getValue(request.getParameter("OrderDir"));


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
        org_key1 = acl.getOrgKey1();
        org_key2 = acl.getOrgKey2();
        org_key3 = acl.getOrgKey3();
        ACLDataBean aclDB =new ACLDataBean();
        acl = aclDB.selectACLByKeys(org_key1, org_key2, org_key3);
    } else{
        org_key1 = request.getParameter("selectedKey1");
        org_key2 = request.getParameter("selectedKey2");
        org_key3 = request.getParameter("selectedKey3");

        if (org_key1.equals("")||
                org_key2.equals("")||
                org_key3.equals("")){
            org_key1 = getValue(request.getParameter("OrgKey1"));
            org_key2 = getValue(request.getParameter("OrgKey2"));
            org_key3 = getValue(request.getParameter("OrgKey3"));
        }
        if (org_key1.equals("")||
                org_key2.equals("")||
                org_key3.equals("")){
            response.sendRedirect("ACLList.jsp");
            return;
        }
        ACLDataBean aclDB =new ACLDataBean();
        acl = aclDB.selectACLByKeys(org_key1, org_key2, org_key3);
    }
    if (acl!=null){
        user_id = acl.getUserId();
        dept_name = acl.getDeptName();
        user_group = getValue(acl.getUserGroupName());
        first_name = getValue(acl.getFirstName());
        last_name = getValue(acl.getLastName());
        email = getValue(acl.getEmail());
        active_ind = acl.getActiveInd();
        access_failure_count = acl.getAccessFailureCount();
        if(acl.getCreatedDate() != null) { 
        	creation_date = SysManager.getStringfromSQLDateTime(acl.getCreatedDate());
        }
        if(acl.getLastUpdatedDate() != null) { 
            last_updated_date = SysManager.getStringfromSQLDateTime(acl.getLastUpdatedDate());
        }
        last_updated_by = acl.getLastUpdatedBy();
        if (acl.getEffectiveDate()!=null) effective_date =  SysManager.getStringfromSQLDate(acl.getEffectiveDate());
        if (acl.getExpiryDate()!=null) expiry_date =  SysManager.getStringfromSQLDate(acl.getExpiryDate());
    }


%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Delete User</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/aclcheck.js" type="text/javascript"></script>                               
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Access Control List Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">            
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableNoBorder" align="center">
                    <tr class="title1"> 
                        <td> 
                            <p align="left">Delete User</p>
                        </td>
                    </tr>
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div><br>":""%></td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <table  width="98%" class="tableBackground" cellspacing="1">
                                    <tr width="15%"> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="25%">User ID</td>
                                        <td class="tableVerticalContentAlignLeft1" width="75%"><%=user_id%> 
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">First Name</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=first_name%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Last Name</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=last_name%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Department</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=dept_name%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">User Role</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=user_group%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Email Address</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=email%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="19%">Effective 
                                        Date</td>
                                        <td class="tableVerticalContentAlignLeft1" width="81%"><%=effective_date%> 
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="19%">Expiry Date</td>
                                        <td class="tableVerticalContentAlignLeft1" width="81%"><%=expiry_date%> 
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Locked?</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=((active_ind==-1)?"No":"Yes")%></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Access Failure Count</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=access_failure_count%> 
                                        </td>
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
                <div align="center"></div>
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
                <input type="hidden" name="OrgKey1" value="<%=org_key1%>">
                <input type="hidden" name="OrgKey2" value="<%=org_key2%>">
                <input type="hidden" name="OrgKey3" value="<%=org_key3%>">
                <input type="hidden" name="PostScreen" value="<%=pre_screen%>">              
                <input type="hidden" name="OrderBy" value="<%=order_by%>">
                <input type="hidden" name="OrderDir" value="<%=order_dir%>">                                                                     
            </form>    
        </fieldset> 		
    </body>
</html>