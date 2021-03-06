<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.List,java.util.ArrayList"%>
<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%

        WAChallengeInfo wac = (WAChallengeInfo)session.getAttribute("QPSES_WA_CHALLENGE");
        if (wac == null)
        {throw new Exception( "Unable to get work assignment info.");}           
        
        CPARDataBean cparDB= new CPARDataBean();
        List<CPARInfo> allCPARList = new ArrayList<CPARInfo>();
        allCPARList = cparDB.selectCparByWac(wac);
        
        boolean cparCreate = true;
        for (CPARInfo cpar : allCPARList){
        	if ( ("s".equals(cpar.getFinalized())) || ("c".equals(cpar.getStatus())) ){
        		cparCreate = false;
        	}
        }
        	
     	// check and display for message
        String msg =   getValue((String)  session.getAttribute("CPAR_MSG"));
        if (!msg.equals("")){
            session.removeAttribute("CPAR_MSG");
            session.removeAttribute("CPAR_MSGTYPE");
        }
        
        String awardedDate = SysManager.getStringfromSQLDate(wac.getAwardedDate());

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contractor Performance Appraisal Report List</title>
        <script language="javascript" type="text/javascript" src="../js/CPARCreation.js"></script>
        <script language="javascript" type="text/javascript" src="../js/nw.js"></script>           
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">
        <link rel="stylesheet" type="text/css" href="../styles/style.css">
    </head>
    <body>
        <%@include file="include/header.jsp"%> 
    
        <fieldset class="function">  
            <table class="function_title"><tr><td>Contractor Performance Appraisal Report - Select CPAR</td></tr></table>

            <fieldset class="sub_function">
                    <legend class="sub_function">Work Assignment Information</legend>
                    <table class="no_border" width="97.5%">
                        <tr valign="top"><td width="195"><b>Service Category/Group          </b></td><td width="2">:</td><td><%=wac.getServiceCategoryGroup() %></td></tr>
                        <tr valign="top"><td            ><b>Service Contract Ref. No        </b></td><td width="2">:</td><td><%=wac.getServiceContractNo() %></td></tr>
                        <tr valign="top"><td            ><b>Work Assignment Title           </b></td><td          >:</td><td><%=wac.getTitle() %></td></tr>
                        <tr valign="top"><td            ><b>Bureau/Department               </b></td><td          >:</td><td><%=wac.getDepartmentName() %></td></tr>
                        <tr valign="top"><td            ><b>Authorised Person               </b></td><td          >:</td><td><%=wac.getAuthorizedPerson() %></td></tr>
                        <tr valign="top"><td            ><b>Post / Rank                     </b></td><td          >:</td><td><%=wac.getPostRank() %></td></tr>
                        <tr valign="top"><td            ><b>Awarded Date                    </b></td><td          >:</td><td><%=awardedDate %></td></tr>
                        <tr valign="top"><td            ><b>Awarded Contractor              </b></td><td          >:</td><td><%=wac.getAwardedContractor() %></td></tr>
                    </table>
            </fieldset>

            <fieldset class="sub_function">
                
			<form name="form1" method="POST" action="">            
            <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                <tr>                    
                    <td width="16%" class="tabSelectedLeft"> CPAR List</td> 
                    <% if(cparCreate){ %>
                    <td width="14%" class="tabUnselectRight" name="Create" onclick="createCpar(form1,this,'<%=allCPARList.size()+1 %>')">Create CPAR</td>  
                    <% } %>
                    <td width="60%">&nbsp;</td>
                </tr>
            </table> 
            <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                <tr class="title1"> 
                    <td>List of Contractor Performance Appraisal Reports</td>
                </tr>
                <tr> 
                    <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%>
                    <td> 
                </tr>
                <tr> 
                    <td>
                    <div align="left">No. of records : 
                    <%=allCPARList.size()%></div></td>
                </tr>
                <tr> 
                    <td> 
						<%if (! allCPARList.isEmpty()){%>
                        <div align="center"> 
                            <table width="98%" cellspacing="1" class="tableBackground">
                                <tr> 
                                    <td align="center" width="5%" class="tableHeaderAlignCenter1">&nbsp;</td>
                                    <td align="center" width="12%" class="tableHeaderAlignCenter1">CPAR Start Date</td>
                                    <td align="center" width="12%" class="tableHeaderAlignCenter1">CPAR End Date</td>
                                    <td align="center" width="13%" class="tableHeaderAlignCenter1">CPAR Status</td>
                                    <td align="center" width="12%" class="tableHeaderAlignCenter1">Created On</td>
                                    <td align="center" width="12%" class="tableHeaderAlignCenter1">Last Updated On</td>
                                    <td align="center" width="14%" class="tableHeaderAlignCenter1">Last Updated By</td>
                                    <td align="center" width="13%" class="tableHeaderAlignCenter1">Action</td>
                                </tr>
							  <%
                                    // Display records
                                    for (int i = allCPARList.size()-1; i>=0 ; i--) {
                                        CPARInfo cpar = new CPARInfo();
                                        cpar = (CPARInfo) allCPARList.get(i);
                                        String cparStatus = ("p".equals(cpar.getStatus()))?"In-progress":(("c".equals(cpar.getStatus()))?"Completed":"N/A");
                                %>
                                <tr valign="top" class="tableContentAlignCenter1"> 
                                    <td align="center" width="5%" nowrap><%=cpar.getCparNo() %></td>
                                    <td align="center" width="12%" nowrap><%=SysManager.getStringfromSQLDate(cpar.getStartDate()) %></td>
                                    <td align="center" width="12%" nowrap><%=SysManager.getStringfromSQLDate(cpar.getEndDate()) %></td>
                                    <td align="center" width="13%" nowrap><%=cparStatus %></td>
                                    <td align="center" width="12%" nowrap><%=SysManager.getStringfromSQLDateTime(cpar.getCreatedDate()) %></td>
                                    <td align="center" width="12%" nowrap><%=SysManager.getStringfromSQLDateTime(cpar.getLastUpdatedDate()) %></td>
                                    <td align="center" width="14%" nowrap><%=cpar.getLastUpdatedBy() %></td>
                                   <td align="center" width="20%" nowrap>
                                    <%if ("s".equals(cpar.getFinalized())){%>
                                     <a href="#"><img name="Update" src="../images/s_btn_update.jpg" width="63" height="21" alt="Update" border="0" onClick="selectaction(form1,this,'<%=cpar.getCparNo() %>')"></a>
									 <a href="#"><img name="Delete" src="../images/s_btn_delete.jpg" width="63" height="21" alt="Delete" border="0" onClick="selectaction(form1,this,'<%=cpar.getCparNo() %>')"></a>
                                    <%}else{%> 
                                     <a href="#"><img name="Print" src="../images/s_btn_print.jpg" width="63" height="21" alt="Print" border="0" onClick="selectaction(form1,this,'<%=cpar.getCparNo() %>')"></a>                                   
                                    <%} %>                                                                       
                                    </td>
                                </tr>
                                <%} // End for %>
                            </table>
                        </div>
                        <%}else{%>
                                No CPAR was found for this service contract. 
                        <%} // End if vector size >0 %>
                    </td>
                </tr>
            </table>        
            <p>
                <input type="hidden" name="cparNo" value="">
            </p>
        </form>
				        
            </fieldset>
         </fieldset>
    </body>
</html>