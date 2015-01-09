<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.AvgCeilingRateInfo,
                                qpses.business.AvgCeilingRateDataBean,
                                qpses.util.SysManager,
                                java.util.List,
                                java.util.ArrayList,
                                java.util.HashMap,
                                java.sql.Date"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%

    // initialize variable
    String searched_effective_date = "";
    int searched_service_category = 0;
    int searched_active_ind = -9;
    String order_by = "";    
    String order_dir = "";
    
    HashMap orderHash = (HashMap) session.getAttribute("ACR_SEARCH_ORDER");
    if (orderHash !=null){
        order_by = (String) orderHash.get("ORDER_BY");
        order_dir = (String) orderHash.get("ORDER_DIR");
        session.removeAttribute("ACR_SEARCH_ORDER");
    } else{
        order_by = getValue(request.getParameter("OrderBy"));
        order_dir = getValue(request.getParameter("OrderDir"));
    }        
    
   
    if (order_by.equals("")) order_by = "EffectiveDate";
    if (order_dir.equals("")) order_dir = "DESC";
    boolean searched = false;
    List allAvgCeilingRateList = new ArrayList();
    AvgCeilingRateDataBean acrDB = new AvgCeilingRateDataBean();

    // get values from session, if any
   
    AvgCeilingRateInfo searchPara = (AvgCeilingRateInfo) session.getAttribute("AVG_CEILING_RATE_SEARCH_PARAMETER");
    if (searchPara!=null){
        if (searchPara.getEffectiveDate() != null){
            searched_effective_date = getValue(SysManager.getStringfromSQLDate(searchPara.getEffectiveDate()));
        }else{
            searched_effective_date = "";
        }
        searched_service_category = searchPara.getServiceCategory();
        searched_active_ind = searchPara.getActiveInd();
        allAvgCeilingRateList = acrDB.searchAvgCeilingRate(searchPara,order_by,order_dir);
        searched = true;        
    }

    // Get  list of effective date exist in the tables
    
    List effectiveDateList = acrDB.selectEffectiveDate();
    
        // check and display for message
    String msg =   getValue((String)  session.getAttribute("AVG_CEILING_RATE_MSG"));
    if (!msg.equals("")){
        session.removeAttribute("AVG_CEILING_RATE_MSG");
        session.removeAttribute("AVG_CEILING_RATE_MSGTYPE");
    }

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Average Ceiling Rate Upload</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">        
        <script language="JavaScript" src="../js/avgcrcheck.js" type="text/javascript"></script>                   
    </head>
    <body>
        <%@include file="include/header.jsp"%> 
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Average Ceiling Rate Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="AvgCeilingRateSearch.jsp">            
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td width="26%" class="tabUnselectLeft" onclick="changepage('List')">Average Ceiling Rate List</td>
                        <td width="14%" class="tabUnselectMiddle" onclick="changepage('Upload')">Upload</td>
                        <td width="11%" class="tabSelectedRight">Search</td>
                        <td width="49%">&nbsp;</td>
                    </tr>
                </table>                
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td class="title1">Search Average Ceiling Rate</td>
                    </tr>
                    <tr> 
                        <td><%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%> 
                        </td>
                    </tr>                    
                    <tr> 
                        <td> 
                            <div align="left">Input searching criteria below: </div>
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
                                <table  width="98%" cellspacing="1" class="tableBackground">
                                    <tr width="15%"> 
                                        <td class="tableVerticalHeaderAlignLeft1" width="25%">Effective 
                                        Date </td>
                                        <td width="75%" class="tableVerticalContentAlignLeft1"> 
                                            <select name="EffectiveDate" class="inputText">
                                                <option value="">All effective date</option>
                                                <%      
                                                    for (int i =0; i<effectiveDateList.size(); i++){
            String effectiveDate = SysManager.getStringfromSQLDate((java.sql.Date) effectiveDateList.get(i));
            out.write("<option ");
            out.write("value=\""+ effectiveDate+"\"");
            if (effectiveDate.equals(searched_effective_date)) out.write(" SELECTED");
            out.write(">"+effectiveDate+"</option>\n");
                                                    }
                                                %>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Service Category</td>
                                        <td class="tableVerticalContentAlignLeft1"> 
                                            <select name="ServiceCategory" class="inputText">
                                                <option value="0" <%if (searched_service_category ==0) out.write("SELECTED");%>>All 
                                                Category </option>
                                                <option value="1" <%if (searched_service_category ==1) out.write("SELECTED");%>>Category 
                                                1</option>
                                                <option value="2" <%if (searched_service_category == 2) out.write("SELECTED");%>>Category 
                                                2</option>
                                                <option value="3" <%if (searched_service_category == 3) out.write("SELECTED");%>>Category 
                                                3</option>
                                                <option value="4" <%if (searched_service_category == 4) out.write("SELECTED");%>>Category 
                                                4</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="tableVerticalHeaderAlignLeft1">Release for Publishing?</td>
                                        <td class="tableVerticalContentAlignLeft1"> 
                                        <input type="radio" name="PublishInd" value="-9" <%= ((searched_active_ind == -9)?"CHECKED":"")%>/>
                                        All                                         
                                        <input type="radio" name="PublishInd" value="-1" <%= ((searched_active_ind == -1)?"CHECKED":"")%>/>
                                        Yes 
                                        <input type="radio" name="PublishInd" value="0" <%= ((searched_active_ind == 0)?"CHECKED":"")%> />
                                        No </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
                <table border="0" cellpadding="3" align="center">
                    <tr> 
                        <td width="50%">                             
                            <div align="center"> <a href="#"><img src="../images/btn_search.jpg" width="98" height="42" name="Search" onClick="selectaction(form1,this)" border="0" alt="Search"></a></div>
                        </td>
                        <td width="50%">                             
                            <div align="center"> <a href="#"><img src="../images/btn_reset.jpg" width="98" height="42" name="SearchReset" onClick="selectaction(form1,this)" border="0" alt="Reset searching criteria"></a></div>
                        </td>
                    </tr>
                </table> 
                <BR>
                <% if (searched) {%>
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr class="title1"> 
                        <td> Search Result </td>
                    </tr>
                    <tr> 
                        <td align="left"> 
                            <%=allAvgCeilingRateList.size()%> records found!<br>                        
                        </td>
                    </tr>
                    <tr> 
                        <td> 
                            <div align="center"> 
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
                            }else{
                                 out.print("No records!");
                            } // End if vector size >0 %>
                                    </table>
                            </div>
                        </td>
                    </tr>
                </table>            
                <%}%>                
               <BR>
                <input type="hidden" name="selectedKey1" value="">
                <input type="hidden" name="selectedKey2" value="">               
                <input type="hidden" name="OrderBy" value="<%=order_by%>">
                <input type="hidden" name="OrderDir" value="<%=order_dir%>">  
                <input type="hidden" name="PostScreen" value="AvgCeilingRateSearch.jsp">                                
            </form>
        </fieldset>					
    </body>
</html>
