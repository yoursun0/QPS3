<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.DebarInfo,
                                qpses.business.DebarDataBean,
                                qpses.util.SysManager,
                                java.util.HashMap"%>
                                
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // initialization

    String debarmentId = request.getParameter("selectedKey1");
    if ((debarmentId == null)||("".equals(debarmentId))){
    	  String postScreen = "DebarList.jsp";
    	  String errMsg = "Failed to delete debarment from debarment list! Selected debarment not found!";
    	  request.getSession().setAttribute("DEBAR_MSG",errMsg);
    	  request.getSession().setAttribute("DEBAR_MSGTYPE","ERR");
    	  RequestDispatcher dispatcher = request.getRequestDispatcher(postScreen);
    	  dispatcher.forward(request, response);
    }

	DebarDataBean debarDB =new DebarDataBean();
    DebarInfo debarment = debarDB.selectDebarmentById(Integer.parseInt(debarmentId));
    
    // check  for message and get last input from session variables
    String msg =   getValue((String)  session.getAttribute("DEBAR_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("DEBAR_MSGTYPE"));
    if (!msg.equals("")){
        session.removeAttribute("DEBAR_MSG");
        session.removeAttribute("DEBAR_MSGTYPE");
    }

%>  

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Delete debarment</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/debarcheck.js" type="text/javascript"></script>                               
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Contractor Debarment Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">            
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableNoBorder" align="center">
                    <tr class="title1"> 
                        <td> 
                            <p align="left">Delete debarment</p>
                        </td>
                    </tr>
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div><br>":""%></td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <table  width="98%" class="tableBackground" cellspacing="1">
 												<%-- <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Debarment ID</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> <%=debarment.getDebarmentId() %></td>
                                                </tr> --%>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Service Category Group</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"><%=debarment.getServiceCategoryGroup() %></td>
                                                </tr>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Contractor ID</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"><%=debarment.getContractorId() %></td>
                                                </tr>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Contractor Name</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"><%=debarment.getContractorName() %></td>
                                                </tr>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Start Date</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"><%=SysManager.getStringfromSQLDate(debarment.getStartDate()) %></td>
                                                </tr>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">End Date </td>
                                                    <td class="tableVerticalContentAlignLeft1" width="71%"><%=SysManager.getStringfromSQLDate(debarment.getEndDate()) %></td>
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
                 <input type="hidden" name="DebarmentId" value="<%=debarment.getDebarmentId() %>" />
                 <input type="hidden" name="EndDate" value="<%=SysManager.getStringfromSQLDate(debarment.getEndDate()) %>" />
            </form>    
        </fieldset> 		
    </body>
</html>
