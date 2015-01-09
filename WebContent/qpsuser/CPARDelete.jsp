<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="java.util.List,qpses.business.CPARInfo,
                                qpses.business.CeilingRateDataBean,
                                qpses.util.SysManager"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%

    // initialize variable
    
    String cparNo = getValue(request.getParameter("cparNo"));
	List<CPARInfo> allCparList = (List<CPARInfo>)session.getAttribute("CPAR_List");
	CPARInfo cpar = new CPARInfo();
	
	for (CPARInfo index : allCparList){
		String a = Integer.toString(index.getCparNo());
		if (cparNo.equals(a)){
			cpar = index;
		}
	} 
	
	//CPARInfo cpar = allCparList.get(Integer.parseInt(cparNo)-1);
    // check  for message and get last input from session variables
    String msg =   getValue((String)  session.getAttribute("CPAR_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("CPAR_MSGTYPE"));


    if (!msg.equals("")){
        session.removeAttribute("CPAR_MSG");
        session.removeAttribute("CPAR_MSGTYPE");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Delete CPAR</title>                
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">        
        <script language="JavaScript" src="../js/CPARCreation.js" type="text/javascript"></script>        
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>CPAR Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableNoBorder" align="center">
                    <tr> 
                        <td class="title1">Delete CPAR</td>
                    </tr>
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%> 
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <table  width="98%" cellspacing="1" class="tableBackground">
                                    <tr> 
                                        <td width="25%" class="tableVerticalHeaderAlignLeft1">Service Category</td>
                                        <td width="75%" class="tableVerticalContentAlignLeft1"><%=cpar.getServiceCategoryGroup() %></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Service Contract Ref. No</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=cpar.getServiceContractNo() %> </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">CPAR No</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=cpar.getCparNo() %></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">CPAR Start Date</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=SysManager.getStringfromSQLDate(cpar.getStartDate()) %></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">CPAR End Date</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=SysManager.getStringfromSQLDate(cpar.getEndDate()) %></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Authorised Person</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=cpar.getAuthorizedPerson() %></td>
                                    </tr>
                                     <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Post / Rank</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=cpar.getPostRank() %></td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Last Updated By</td>
                                        <td class="tableVerticalContentAlignLeft1"><%=cpar.getLastUpdatedBy() %></td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
                <table border="0" cellpadding="3" align="center">
                    <tr> 
                        <td width="50%"> 
                            <div align="center"> <a href="#"><img src="../images/btn_delete.jpg" width="98" height="42" name="ConfirmDelete" onClick="selectaction(form1,this,'<%=cpar.getCparNo() %>')" border="0" alt="Delete"></a></div>
                        </td>
                        <td width="50%"> 
                            <div align="center"> <a href="#"><img src="../images/btn_cancel.jpg" width="98" height="42" name="Cancel" onClick="selectaction(form1,this)" border="0" alt="Cancel"></a></div>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="PostScreen" value="CPARList.servlet">
                <input type="hidden" name="cparNo" value="">
            </form>
        </fieldset>
    
    </body>
</html>
