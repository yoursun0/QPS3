<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.ContractorDataBean,
                                qpses.business.ContractorInfo,
                                java.util.List,
                                java.util.ArrayList" %>
<%   
    String order_by = "";
    String order_dir = "";
    order_by = getValue(request.getParameter("OrderBy"));
    order_dir = getValue(request.getParameter("OrderDir"));
    if (order_by.equals("")) order_by = "Name";
    if (order_dir.equals("")) order_dir = "ASC";
    
    ContractorDataBean contractorDB =new ContractorDataBean();
    List allContractorList = new ArrayList();
    allContractorList = contractorDB.selectContractor(order_by,order_dir);

    // check  for message and get last input from session variables
    String msg =   getValue((String)  session.getAttribute("CONTRACTOR_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("CONTRACTOR_MSG_TYPE"));
    if (!msg.equals("")){
        session.removeAttribute("CONTRACTOR_MSG");
        session.removeAttribute("CONTRACTOR_MSG_TYPE");
    }

%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Contractor Maintenance</title>        
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/contractorcheck.js" type="text/javascript"></script>        
    </head>
    <body>
        <%@include file="include/header.jsp"%>		
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Contractor Code Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="defaultfont" align="center">
                    <tr class="title1"> 
                        <td>List of Contractors</td>
                    </tr>
                    <tr>
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%></td>
                    </tr>					                        
                    <tr  align="left">         
                        <td>No. of records : <%=allContractorList.size()%>
                        </td>
                    </tr> 
                    <tr>         
                        <td>&nbsp;</td>
                    </tr>                    
                    <tr><%if (! allContractorList.isEmpty()){%>
                        <td>                                    
                            <table width="98%" cellspacing="1" class="tableBackground">
                                <tr> 
                                    <td width="3%" nowrap class="tableHeaderAlignLeft1" rowspan="2">&nbsp;</td>
                                    <td width="13%" nowrap class="tableHeaderAlignLeft1" rowspan="2">
                                        <a href="#" class="table_header" onClick="changeorder(form1,'ID','<%= ((order_by.equals("ID") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Contractor 
                                        Code</a>
                                        <% if (order_by.equals("ID") && order_dir.equals("ASC")){%>
                                        <img src="../images/up_arrow.gif" border = 0>
                                        <%}%>
                                        <% if (order_by.equals("ID") && order_dir.equals("DESC")){%>
                                        <img src="../images/down_arrow.gif" border = 0>
                                        <%}%>																						                                        
                                    </td>                                                                        
                                    <td class="tableHeaderAlignCenter1" width="25%" nowrap rowspan="2">
                                        <a href="#" class="table_header" onClick="changeorder(form1,'Name','<%= ((order_by.equals("Name") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Contractor 
                                        Name</a>
                                        <% if (order_by.equals("Name") && order_dir.equals("ASC")){%>
                                        <img src="../images/up_arrow.gif" border = 0>
                                        <%}%>
                                        <% if (order_by.equals("Name") && order_dir.equals("DESC")){%>
                                        <img src="../images/down_arrow.gif" border = 0>
                                        <%}%>																						                                        
                                    </td>                                                                        
                                    <td class="tableHeaderAlignCenter1" nowrap colspan="6">Service 
                                    Category/Group</td>
                                    <td class="tableHeaderAlignCenter1" width="11%" nowrap rowspan="2">Action</td>
                                </tr>
                                <tr> 
                                  
                                    <td class="tableHeaderAlignCenter1" nowrap width="6%">1</td>
                                    <td class="tableHeaderAlignCenter1" nowrap width="6%">2/N</td>
                                    <td class="tableHeaderAlignCenter1" nowrap width="6%">2/J</td>
                                    <td class="tableHeaderAlignCenter1" nowrap width="6%">3/N</td>
                                    <td class="tableHeaderAlignCenter1" nowrap width="6%">3/J</td>
                                   
                                    <td class="tableHeaderAlignCenter1" nowrap width="6%">4</td>
                                </tr>
                                <%
                                    // Display records
                                    for (int i = 0; i<allContractorList.size();i++) {
                                                ContractorInfo c = new ContractorInfo();
                                                c = (ContractorInfo) allContractorList.get(i);

                                                // get values from vector element
                                                String contractor_id = c.getContractorId();
                                                String contractor_name =  c.getContractorName();
                                                boolean group_1N = (c.get1NInd()==-1)?true:false;
                                                boolean group_1J = (c.get1JInd()==-1)?true:false;
                                                boolean group_2N = (c.get2NInd()==-1)?true:false;
                                                boolean group_2J = (c.get2JInd()==-1)?true:false;
                                                boolean group_3N = (c.get3NInd()==-1)?true:false;
                                                boolean group_3J = (c.get3JInd()==-1)?true:false;
                                                boolean group_4N = (c.get4NInd()==-1)?true:false;
                                                boolean group_4J = (c.get4JInd()==-1)?true:false;
                                %>
                                <tr> 
                                    <td nowrap class="tableContentAlignCenter1" width="3%"><%=i+1%></td>
                                    <td class="tableContentAlignLeft1" width="13%"><%=contractor_id%></td>
                                    <td nowrap class="tableContentAlignLeft1" width="25%"><%=contractor_name%></td>
                                   


                                    <td nowrap class="tableContentAlignCenter1" width="6%">
                                        <% if (group_1J){%>
                                        <img src="../images/btn_tick.jpg">
                                        <%}%>									
                                    </td>
                                    <td nowrap class="tableContentAlignCenter1" width="6%">
                                        <% if (group_2N){%>
                                        <img src="../images/btn_tick.jpg">
                                        <%}%>								
                                    </td>
                                    <td nowrap class="tableContentAlignCenter1" width="6%">
                                        <% if (group_2J){%>
                                        <img src="../images/btn_tick.jpg">
                                        <%}%>																		
                                    </td>
                                    <td nowrap class="tableContentAlignCenter1" width="6%">
                                        <% if (group_3N){%>
                                        <img src="../images/btn_tick.jpg">
                                        <%}%>								
                                    </td>
                                    <td nowrap class="tableContentAlignCenter1" width="6%">
                                        <% if (group_3J){%>
                                        <img src="../images/btn_tick.jpg">
                                        <%}%>																		
                                    </td>

                                    <td nowrap class="tableContentAlignCenter1" width="6%">
                                        <% if (group_4J){%>
                                        <img src="../images/btn_tick.jpg">
                                        <%}%>																		
                                    </td>
                                    
                                    <td nowrap class="tableContentAlignCenter1" width="11%"> <a href="#"><img name="Update" src="../images/s_btn_update.jpg" width="63" height="21" alt="Update" border="0" onClick="selectaction(form1,this,'<%=contractor_id%>')"></a> 
                                    </td>
                                </tr>
                                <%} // End for %>
                                <%}else{%>
                                No records! 
                                <%} // End if vector size >0 %>
                            </table>
                        </td>
                    </tr>
                </table>  
                <p> 
                    <input type="hidden" name="selectedKey1" value="">
                    <input type="hidden" name="OrderBy" value="<%=order_by%>">
                    <input type="hidden" name="OrderDir" value="<%=order_dir%>">                    
                </p><BR>
            </form>
        </fieldset> 			
    </body>
</html>