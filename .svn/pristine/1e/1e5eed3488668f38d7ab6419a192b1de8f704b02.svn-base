<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.DebarDataBean,
                                qpses.business.DebarInfo,
                                java.util.List,
                                java.util.ArrayList,
                                java.util.HashMap,
                                qpses.util.SysManager" %>
                                
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // initialization
    boolean searched = false;
	DebarDataBean debarDB =new DebarDataBean();
    List allDebarList = new ArrayList();

    String order_by = "";                                                                                
    String order_dir = "";
    // get list order
    HashMap orderHash = (HashMap) session.getAttribute("DEBAR_LIST_ORDER");
    if (orderHash !=null){
        order_by = (String) orderHash.get("ORDER_BY");
        order_dir = (String) orderHash.get("ORDER_DIR");
        session.removeAttribute("DEBAR_LIST_ORDER");
    } else{
        order_by = getValue(request.getParameter("OrderBy"));
        order_dir = getValue(request.getParameter("OrderDir"));
    }          
    if (order_by.equals("")) order_by = "ID";
    if (order_dir.equals("")) order_dir = "DESC";

    allDebarList = debarDB.selectDebarment(order_by,order_dir);
    // check  for message and get last input from session variables
    String msg =   getValue((String)  session.getAttribute("DEBAR_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("DEBAR_MSGTYPE"));
    if (!msg.equals("")){
        session.removeAttribute("DEBAR_MSG");
        session.removeAttribute("DEBAR_MSGTYPE");
        session.removeAttribute("DEBAR_DATA");
    }

%>  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Contractor Debarment Administration</title>        
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/debarcheck.js" type="text/javascript"></script>        
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Contractor Debarment Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">            
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td width="20%" class="tabSelectedLeft">Debarment List</td>
                        <td width="21%" class="tabUnselectMiddle" onclick="changepage('Add')">Debar Contractor </td>

                        <td width="56%">&nbsp;</td>                 
                    </tr> 
                </table>       
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr class="title1"> 
                        <td> 
                            Debarment List
                        </td>
                    </tr>
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%> 
                    </tr>
                    <tr>                         
                        <td align="left"> 
                            <div align="left">No. of records : <%=allDebarList.size()%> </div>
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <%if (! allDebarList.isEmpty()){%>
                                <table width="98%" cellspacing="1" class="tableBackground">
                                    <tr> 
                                        <td width="4%" nowrap class="tableHeaderAlignLeft1">&nbsp;</td>
                                        <td class="tableHeaderAlignCenter1" width="7%" nowrap>
                                            <a href="#" class="table_header" onClick="changeorder(form1,'Category','<%= ((order_by.equals("Category") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Service Cateogry</a>
                                            <% if (order_by.equals("Category") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("Category") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>      
                                        <td class="tableHeaderAlignCenter1" width="10%" nowrap>
                                            <a href="#" class="table_header" onClick="changeorder(form1,'ContractorId','<%= ((order_by.equals("ContractorId") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Contractor ID</a>
                                            <% if (order_by.equals("ContractorId") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("ContractorId") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>         
                                        <td width="35%" nowrap class="tableHeaderAlignLeft1">                                    
                                            <a href="#" class="table_header" style="padding-left: 10px" onClick="changeorder(form1,'Contractor','<%= ((order_by.equals("Contractor") && order_dir.equals("ASC"))?"DESC":"ASC")%>')"> Contractor Name</a>
                                            <% if (order_by.equals("Contractor") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("Contractor") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                
                                        <td class="tableHeaderAlignCenter1" width="10%" nowrap>
                                            <a href="#" class="table_header" onClick="changeorder(form1,'StartDate','<%= ((order_by.equals("StartDate") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Start Date</a>
                                            <% if (order_by.equals("StartDate") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("StartDate") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>                                                                                                                                                                                             
                                        <td class="tableHeaderAlignCenter1" width="10%" nowrap>
                                            <a href="#" class="table_header" onClick="changeorder(form1,'EndDate','<%= ((order_by.equals("EndDate") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">End Date</a>
                                            <% if (order_by.equals("EndDate") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("EndDate") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>
                                        <td class="tableHeaderAlignCenter1" width="10%" nowrap>
                                            <a href="#" class="table_header" onClick="changeorder(form1,'LastUpdateBy','<%= ((order_by.equals("LastUpdateBy") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Last Updated by</a>
                                            <% if (order_by.equals("LastUpdateBy") && order_dir.equals("ASC")){%>
                                            <img src="../images/up_arrow.gif" border = 0>
                                            <%}%>
                                            <% if (order_by.equals("LastUpdateBy") && order_dir.equals("DESC")){%>
                                            <img src="../images/down_arrow.gif" border = 0>
                                            <%}%>																						                                        
                                        </td>
                                        <td class="tableHeaderAlignCenter1" width="9%" nowrap>Released?</td>
                                        <td class="tableHeaderAlignCenter1" width="18%" nowrap>Action</td>
                                    </tr>
                                    <%
                                        // Display records
                                        for (int i = 0; i<allDebarList.size();i++) {
                                        			DebarInfo debarment = new DebarInfo();
                                                    debarment = (DebarInfo) allDebarList.get(i);

                                                    // get values from vector element
                                                    int debarment_id = debarment.getDebarmentId();
                                                    String category = debarment.getServiceCategoryGroup();
                                                    String contractor_id = debarment.getContractorId();
                                                    String contractor_name = debarment.getContractorName();
                                                    String start_date = SysManager.getStringfromSQLDate(debarment.getStartDate());
                                                    String end_date =  SysManager.getStringfromSQLDate(debarment.getEndDate());
                                                    String last_update_by = debarment.getLastUpdatedBy();
                                                    String active_ind = (debarment.getActiveInd()==-1) ? "Yes": "No";

                                    %>
                                    <tr> 
                                        <td nowrap class="tableContentAlignCenter1" width="4%"><%=i+1 %></td>
                                        <td class="tableContentAlignCenter1" width="7%"><%=category%></td>
                                        <td nowrap class="tableContentAlignLeft1" width="10%"><%=contractor_id%></td>
                                        <td class="tableContentAlignLeft1" width="35%"><%=contractor_name%></td>
                                        <td class="tableContentAlignCenter1" width="10%"><%=start_date%></td>
                                        <td class="tableContentAlignCenter1" width="10%"><%=end_date%></td>
                                        <td class="tableContentAlignCenter1" width="10%"><%=last_update_by%></td>
                                        <td class="tableContentAlignCenter1" width="9%"><%=active_ind%></td>
                                        <td nowrap class="tableContentAlignCenter1" width="18%">
                                        	<%if ("No".equals(active_ind)){%>
                                             <a href="#"><img name="Release" src="../images/s_btn_release.jpg" width="63" height="21" alt="Update" border="0" onClick="selectaction(form1,this,'<%=debarment_id%>')"></a> 
                                            <% } %>
                                             <a href="#"><img name="Delete" src="../images/s_btn_delete.jpg" width="63" height="21" alt="Delete" border="0" onClick="selectaction(form1,this,'<%=debarment_id %>')"></a> 
                                        </td>
                                    </tr>
                                    <%} // End for 
                                    }else{
                                         out.print("No debarment records!");
                                    } // End if vector size >0 %>
                                    </table>
                            </div>
                        </td>
                    </tr>
                </table>        
                <p> 
                    <input type="hidden" name="selectedKey1" value="">
                    <input type="hidden" name="selectedKey2" value="">
                    <input type="hidden" name="selectedKey3" value="">
                    <input type="hidden" name="OrderBy" value="<%=order_by%>">
                    <input type="hidden" name="OrderDir" value="<%=order_dir%>">   
                    <input type="hidden" name="PostScreen" value="DebarList.jsp">                         
                </p> <p align="center">&nbsp;</p>
            </form>
        </fieldset> 		
    </body>
</html>
