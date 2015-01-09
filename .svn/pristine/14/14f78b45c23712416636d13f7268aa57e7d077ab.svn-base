<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.AvgCeilingRateDataBean,
                                qpses.business.AvgCeilingRateInfo,
                                java.util.List,
                                java.util.ArrayList,
                                java.util.HashMap,
                                qpses.util.SysManager" %>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%

    // initialization
    boolean searched = false;
    AvgCeilingRateDataBean acrDB =new AvgCeilingRateDataBean();
    List allAvgCeilingRateList = new ArrayList();

    // get list order
    String order_by = "";
    String order_dir = "";
    HashMap orderHash = (HashMap) session.getAttribute("ACR_LIST_ORDER");
    if (orderHash !=null){
        order_by = (String) orderHash.get("ORDER_BY");
        order_dir = (String) orderHash.get("ORDER_DIR");
        session.removeAttribute("ACR_LIST_ORDER");
    } else{
        order_by = getValue(request.getParameter("OrderBy"));
        order_dir = getValue(request.getParameter("OrderDir"));
    }        
    if (order_by.equals("")) order_by = "EffectiveDate";
    if (order_dir.equals("")) order_dir = "DESC";

    // get full list of average ceiling rates
    allAvgCeilingRateList = acrDB.selectAvgCeilingRate(order_by,order_dir);

    // check error message
    String msg =   getValue((String)  session.getAttribute("AVG_CEILING_RATE_MSG"));
    if (!msg.equals("")){
        session.removeAttribute("AVG_CEILING_RATE_DATA");
        session.removeAttribute("AVG_CEILING_RATE_MSG");
        session.removeAttribute("AVG_CEILING_RATE_MSGTYPE");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Average Ceiling Rate</title>        
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/nw.js" type="text/javascript"></script>
        <script language="JavaScript" src="../js/avgcrcheck.js" type="text/javascript"></script>              
    </head>
    <body>
        <%@include file="include/header.jsp"%>      
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Average Ceiling Rate Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">            
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td width="26%" class="tabSelectedLeft">Average Ceiling Rate List</td>
                        <td width="14%" class="tabUnselectMiddle" onclick="changepage('Upload')">Upload</td>
                        <td width="11%" class="tabUnselectRight" onclick="changepage('Search')">Search</td>
                        <td width="49%">&nbsp;</td>
                    </tr>
                </table> 
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr class="title1"> 
                        <td >List of Average Ceiling Rate</td>
                    </tr>
                    <tr> 
                        <td><%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%> 
                        </td>
                    </tr>
                    <tr> 
                        <td align="left">                            
                            No. of records : <%=allAvgCeilingRateList.size()%> 
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                        <%if (allAvgCeilingRateList.size()>0){%>
                        <div align="center"> 
                            <table width="98%" cellspacing="1" class="tableBackground">
                                <tr> 
                                    <td width="4%" class="tableHeaderAlignCenter1">&nbsp;</td>
                                    <td width="12%" class="tableHeaderAlignCenter1">
                                    <a href="#" class="table_header" onClick="changeorder(form1,'EffectiveDate','<%= ((order_by.equals("EffectiveDate") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Effective Date</a>
                                    <% if (order_by.equals("EffectiveDate") && order_dir.equals("ASC")){%>
                                    <img src="../images/up_arrow.gif" border = 0>
                                    <%}%>
                                    <% if (order_by.equals("EffectiveDate") && order_dir.equals("DESC")){%>
                                    <img src="../images/down_arrow.gif" border = 0>
                                    <%}%>																						                                                                                
                                    <td width="15%" class="tableHeaderAlignCenter1">
                                    <a href="#" class="table_header" onClick="changeorder(form1,'ServiceCategory','<%= ((order_by.equals("ServiceCategory") && order_dir.equals("ASC"))?"DESC":"ASC")%>')">Service Category</a>
                                    <% if (order_by.equals("ServiceCategory") && order_dir.equals("ASC")){%>
                                    <img src="../images/up_arrow.gif" border = 0>
                                    <%}%>
                                    <% if (order_by.equals("ServiceCategory") && order_dir.equals("DESC")){%>
                                    <img src="../images/down_arrow.gif" border = 0>
                                    <%}%>																					                                                                               
                                    <td width="11%" class="tableHeaderAlignCenter1">Release for Publishing?</td>
                                    <td width="43%" class="tableHeaderAlignCenter1">Uploaded File</td>
                                    <td width="15%" class="tableHeaderAlignCenter1">Action</td>
                                </tr>
                                <tr> 
                                    <%
                                        // Display records
                                        for (int i = 0; i<allAvgCeilingRateList.size();i++) {
                                            AvgCeilingRateInfo acr = new AvgCeilingRateInfo();
                                            acr = (AvgCeilingRateInfo) allAvgCeilingRateList.get(i);
                                            // initialize variables
                                            String effective_date = "";
                                            int service_category = 0;
                                            String active_ind = "";
                                            String file_name ="";
                                            long file_size = 0;

                                            // get values from vector element
                                            effective_date = SysManager.getStringfromSQLDate(acr.getEffectiveDate());
                                            service_category = acr.getServiceCategory();
                                            active_ind = (acr.getActiveInd() == -1) ? "Yes": "No";
                                            file_name = acr.getPDFFileName();
                                            file_size = (acr.getPDFFileSize()/1024);
                                    %>
                                    <td align="center" width="4%" nowrap class="tableContentAlignCenter1"><%=i+1%></td>
                                    <td align="center" width="12%" nowrap class="tableContentAlignCenter1"><%=effective_date%></td>
                                    <td align="center" width="15%" class="tableContentAlignCenter1"><%=service_category%></td>
                                    <td align="center" width="11%" class="tableContentAlignCenter1"><%=active_ind %></td>
                                    <td width="43%" class="tableContentAlignLeft1" nowrap>
                                    <a href="AvgCeilingRateFile.servlet?orgKey1=<%=service_category%>&orgKey2=<%=effective_date%>" target="_blank"><img src="../images/pdf.gif" border="0">
                                    <%=file_name%> (<%=file_size%>K)</a></td>                                        
                                    <td width="15%" nowrap class="tableContentAlignCenter1"> 
                                    <%if (active_ind.equals("No")){%>
                                    <a href="#"><img name="Release" src="../images/s_btn_release.jpg" width="63" height="21" alt="Release for publishing" border="0" onClick="selectaction(form1,this,'<%=service_category%>','<%=effective_date%>')"></a> &nbsp;  
                                    <%}%>
                                    <a href="#"><img name="Delete" src="../images/s_btn_delete.jpg" width="63" height="21" alt="Delete" border="0" onClick="selectaction(form1,this,'<%=service_category%>','<%=effective_date%>')"></a></td>
                                </tr>
                                <%} // End for                             
                            } // End if vector size >0 %>
                                </table>
                        </div>
                        </td>
                    </tr>
                </table>
                <p>&nbsp;</p>
                <input type="hidden" name="selectedKey1" value="">
                <input type="hidden" name="selectedKey2" value="">
                <input type="hidden" name="PostScreen" value="AvgCeilingRateList.jsp">                                
                <input type="hidden" name="OrderBy" value="<%=order_by%>">
                <input type="hidden" name="OrderDir" value="<%=order_dir%>">                                
            </form>
        </fieldset>
    </body>
</html>