<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.CeilingRateDataBean,
                                qpses.business.CeilingRateInfo,
                                 java.util.List,
                                 java.util.ArrayList,
                                 java.util.HashMap,
                                qpses.util.SysManager" %>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // initialization
    boolean searched = false;
    CeilingRateDataBean crDB =new CeilingRateDataBean();
    List CeilingRateList = new ArrayList();
    
    String order_by = "";
    String order_dir = "";

    // get list order
    HashMap orderHash = (HashMap) session.getAttribute("CR_LIST_ORDER");
    if (orderHash !=null){
        order_by = (String) orderHash.get("ORDER_BY");
        order_dir = (String) orderHash.get("ORDER_DIR");
        session.removeAttribute("CR_LIST_ORDER");
    } else{
        order_by = getValue(request.getParameter("OrderBy"));
        order_dir = getValue(request.getParameter("OrderDir"));
    }        
    if (order_by.equals("")) order_by = "ServiceCategory";
    if (order_dir.equals("")) order_dir = "ASC";
   
    // get full list of ceiling rates
    CeilingRateList = crDB.selectCeilingRate(order_by,order_dir);

    // check and display for message
    String msg =   getValue((String)  session.getAttribute("CEILING_RATE_MSG"));
    if (!msg.equals("")){
        session.removeAttribute("CEILING_RATE_MSG");
        session.removeAttribute("CEILING_RATE_MSGTYPE");
    }

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Ceiling Rate</title>        
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/ceilingratecheck.js" type="text/javascript"></script>        
        <script language="JavaScript" src="../js/nw.js" type="text/javascript"></script>                
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
        <table class="function_title"><tr><td>Ceiling Rate Maintenance</td></tr></table>            		        
        <form name="form1" method="POST" action="">            
            <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                <tr>
                    <td width="20%" class="tabSelectedLeft"> Ceiling Rate List</td> 
                    <td width="14%" class="tabUnselectMiddle" onclick="changepage('Import')">Import</td>                    
                    <td width="11%" class="tabUnselectRight" onclick="changepage('Search')">Search</td>                                        
                    <td width="55%">&nbsp;</td>
                </tr>
            </table> 
            <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                <tr class="title1">
                    <td>List of Ceiling Rate</td>
                </tr>
                <tr>                     
                    <td colspan="4"> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%></td>
                </tr>
                <tr> 
                    <td align="left"> 
                        No. of records : <%=CeilingRateList.size()%>
                    </td>
                </tr>
                <tr> 
                    <td> 
                        <%if (! CeilingRateList.isEmpty()){%>
                        <div align="center"> 
                            <table width="98%" cellspacing="1" class="tableBackground">
                                <tr> 
                                    <td align="center" width="7%" nowrap class="tableHeaderAlignCenter1">&nbsp;</td>
                                    <td align="center" width="22%" nowrap class="tableHeaderAlignCenter1">
                                    <a href="#" class="table_header" onClick="changeorder(form1,'ServiceCategory','<%= ((order_by.equals("ServiceCategory") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Service Category</a>
                                    <% if (order_by.equals("ServiceCategory") && order_dir.equals("ASC")){%>
                                    <img src="../images/up_arrow.gif" border = 0>
                                    <%}%>
                                    <% if (order_by.equals("ServiceCategory") && order_dir.equals("DESC")){%>
                                    <img src="../images/down_arrow.gif" border = 0>
                                    <%}%>																					                                                                                                                   
                                    <td align="center" width="30%" nowrap class="tableHeaderAlignCenter1">
                                    <a href="#" class="table_header" onClick="changeorder(form1,'EffectiveDate','<%= ((order_by.equals("EffectiveDate") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Effective Date</a>
                                    <% if (order_by.equals("EffectiveDate") && order_dir.equals("ASC")){%>
                                    <img src="../images/up_arrow.gif" border = 0>
                                    <%}%>
                                    <% if (order_by.equals("EffectiveDate") && order_dir.equals("DESC")){%>
                                    <img src="../images/down_arrow.gif" border = 0>
                                    <%}%>																						                                                                            
                                    <td align="center" width="25%" nowrap class="tableHeaderAlignCenter1">Release 
                                    for Publishing?</td>
                                    <td align="center" width="16%" nowrap class="tableHeaderAlignCenter1">Action</td>
                                </tr>
                                <%
                                    // Display records
                                    for (int i = 0; i<CeilingRateList.size();i++) {
                                        CeilingRateInfo cr = new CeilingRateInfo();
                                        cr = (CeilingRateInfo) CeilingRateList.get(i);
                                        // initialize variables
                                        String effective_date = "";
                                        String service_category = "";
                                        String active_ind = "";

                                        // get values from vector element
                                        effective_date = SysManager.getStringfromSQLDate(cr.getEffectiveDate());
                                        service_category = cr.getServiceCategory();
                                        active_ind = (cr.getActiveInd() == -1) ? "Yes": "No";
                                %>
                                <tr> 
                                    <td align="center" width="7%" nowrap class="tableContentAlignCenter1"><%=i+1%></td>
                                    <td align="center" width="22%" nowrap class="tableContentAlignCenter1"><%=service_category%></td>
                                    <td align="center" width="30%" class="tableContentAlignCenter1"><%=effective_date%></td>
                                    <td align="center" width="25%" class="tableContentAlignCenter1"><%=active_ind %></td>                                    
                                    <td width="16%" nowrap class="tableContentAlignCenter1"> 
                                    <%if (active_ind.equals("No")){%>
                                    <a href="#"><img name="Release" src="../images/s_btn_release.jpg" width="63" height="21" alt="Release for publishing" border="0" onClick="selectaction(form1,this,'<%=service_category%>','<%=effective_date%>')"></a> 
                                    <%}%>
                                    <a href="#"><img name="Print" src="../images/s_btn_print.jpg" width="63" height="21" alt="Print PDF" border="0" onClick="selectaction(form1,this,'<%=service_category%>','<%=effective_date%>')"></a>
                                    <a href="#"><img name="Delete" src="../images/s_btn_delete.jpg" width="63" height="21" alt="Delete" border="0" onClick="selectaction(form1,this,'<%=service_category%>','<%=effective_date%>')"></a></td>
                                </tr>
                                <%} // End for 
                            }else{
                                out.print("No records!");
                            } // End if vector size >0 %>
                                </table>
                        </div>
                    </td>
                </tr>
            </table>                                       
            <p>
                <input type="hidden" name="selectedKey1" value="">
                <input type="hidden" name="selectedKey2" value="">
                <input type="hidden" name="PostScreen" value="CeilingRateList.jsp">                
                <input type="hidden" name="OrderBy" value="<%=order_by%>">
                <input type="hidden" name="OrderDir" value="<%=order_dir%>">                                                
            </p><BR>		
        </form>
    </body>
</html>
