<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.WorkAssignmentDataBean,
                                qpses.business.WorkAssignmentInfo,
                                qpses.business.DeptInfo,
                                java.util.List,
                                java.util.ArrayList,
                                java.util.HashMap,
                                qpses.util.SysManager" %>
<%

    // initialization
    HashMap allOutstandingMap = new HashMap();
    allOutstandingMap =   ((HashMap) session.getAttribute("CPAR_OUTSTANDING_LIST"));

%>            
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - CPARs Outstanding List</title>             
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/CPARListcheck.js" type="text/javascript"></script>
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>
Contractor Performance Appraisal Reports Outstanding List</td></tr></table>
                <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" class="tableBorder">
                    <tr class="title1"> 
                        <td>List of Outstanding CPARs</td>
                    </tr>
                    <tr>                         
                        <td align="left"> 
                            <div align="left">No. of records : <%=allOutstandingMap.size()%> </div>
                        </td>
                    </tr>
                    <tr> 
	                    <td> 
	                    <div align="center"> 
	                        <%if (! allOutstandingMap.isEmpty()){%>
							<form name="form2" method="POST" action="CPAROutstanding.jsp" >                            
								<img type="image" name="DownloadCPARsOutstanding" class="function_button" src="../images/btn_download.jpg" alt="Download EXCEL File" onclick="DownloadFile(form2, this)">
							</form>
	                        <table width="100%" cellspacing="1" class="tableBackground">
	                            <tr> 
	                                <td align="center" width="3%" class="tableHeaderAlignCenter1">No.</td>
	                                <td align="center" width="13%" class="tableHeaderAlignCenter1">Service Contract Ref. No</td>
	                                <td align="center" width="10%" class="tableHeaderAlignCenter1">Bureau / Department</td>
	                                <td align="center" width="10%" class="tableHeaderAlignCenter1">Awarded Contractor</td>
	                                <td align="center" width="54%" class="tableHeaderAlignCenter1">Assignment Title</td>
	                                <td align="center" width="10%" class="tableHeaderAlignCenter1">Authorised Person</td>
									
	                            </tr>
	                            <%
	                                // Display records
	                                int i = 0;
	                                for (Object key : allOutstandingMap.keySet()) {
										i++;
	                                	WorkAssignmentInfo wa = new WorkAssignmentInfo();
	                                	wa = (WorkAssignmentInfo) allOutstandingMap.get(key);
	                            %>                       
	                            <tr valign="top"> 
	                                <td align="center" width="3%" nowrap class="tableContentAlignCenter1"><%=(i)%></td>
	                                <td width="13%" class="tableContentAlignLeft1"><%=wa.getServiceContractNo()%></td>
	                                <td width="10%" class="tableContentAlignLeft1"><%=wa.getDepartmentId()%></td>
	                                <td width="10%" class="tableContentAlignLeft1"><%=wa.getAwardedContractor()%></td>
	                                <td width="54%" class="tableContentAlignLeft1"><%=wa.getTitle()%></td>
	                                <td width="10%" class="tableContentAlignLeft1"><%=wa.getAuthorizedPerson()%></td>
	                            </tr>
	                            <%} // End for 
	                            }else{
	                                out.print("No records!");
	                            } // End if Map size >0 %>
	                    	</table>
	                    </div>
                    </td>
        		</tr>
         	</table>    
        </fieldset>
    </body>
</html>
