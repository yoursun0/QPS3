<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.Random"%>
<%@page import="java.util.List"%>
<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

    <%
    String waType = (String)request.getSession().getAttribute("WA_TYPE");

    if (waType == null)
    { throw new Exception("Session [WAType] is NULL"); }   

    String waTypeStr = (waType.equals("proposal") ? "Proposal Evaluation" : "Change Request");
    
        if (request.getSession().getAttribute("allCeilingRateInfo") == null)
            throw new Exception("Session variable [allCeilingRateInfo] is NULL");

        List allCeilingRateInfo = (List)request.getSession().getAttribute("allCeilingRateInfo");

        String dContractorList = request.getParameter("d_contractor_list");

        String allStaffRates = request.getParameter("all_staff_rates");
        if (allStaffRates == null) allStaffRates = "";
        
        String awardedDate         = request.getParameter("wa_awarded_date");
        String authorizedPerson    = request.getParameter("wa_authorized_person");
    %>
    
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">   
        <script language="javascript" type="text/javascript" src="../js/StaffRateValidationUser.js"></script>
        <title>Staff Rate Validation for <%=waTypeStr%> - Input Proposed Staff Rates</title>
    </head>
    
    <body onload="GetStaffRates_OnLoad()">
        <%@include file="include/header.jsp"%>
        <form name="form" method="POST" target="" action="">
            <input type="hidden" name="request_action"       value=""> 
            <input type="hidden" name="show_subscore_rpt"    value="n">
            <input type="hidden" name="show_validation_rpt"  value="y">
            <input type="hidden" name="contractor_id_list"   value="<%=request.getParameter("contractor_id_list")%>">
            <input type="hidden" name="contractor_list"      value="<%=request.getParameter("contractor_list")%>">
            <input type="hidden" name="d_contractor_id_list" value="<%=request.getParameter("d_contractor_id_list")%>">
            <input type="hidden" name="d_contractor_list"    value="<%=request.getParameter("d_contractor_list")%>">
            <input type="hidden" name="sc"                   value="<%=request.getParameter("sc")%>">
            <input type="hidden" name="sg"                   value="<%=request.getParameter("sg")%>">
            <input type="hidden" name="scg"                  value="<%=request.getParameter("scg")%>">
            <input type="hidden" name="scg_name"             value="<%=request.getParameter("scg_name")%>">
            <input type="hidden" name="dept_id"              value="<%=request.getParameter("dept_id")%>">
            <input type="hidden" name="dept_name"            value="<%=request.getParameter("dept_name")%>">
            <input type="hidden" name="closing_date"         value="<%=request.getParameter("closing_date")%>">
            <input type="hidden" name="wa_title"             value="<%=request.getParameter("wa_title").replaceAll("\"", "&quot;")%>">
            <input type="hidden" name="wa_file_part"         value="<%=request.getParameter("wa_file_part")%>">
            <input type="hidden" name="wa_file_no"           value="<%=request.getParameter("wa_file_no")%>">
            <input type="hidden" name="wa_awarded_date"      value="<%=request.getParameter("wa_awarded_date")%>">
            <input type="hidden" name="wa_authorized_person" value="<%=request.getParameter("wa_authorized_person")%>">            
            <input type="hidden" name="m_scheme"             value="<%=request.getParameter("m_scheme")%>">
            <input type="hidden" name="all_staff_rates"      value="<%=allStaffRates%>">     
            <input type="hidden" name="wa_type"              value="<%=waType%>">           
            
            <fieldset class="function">  
                <table class="function_title"><tr><td>Staff Rate Validation for <%=waTypeStr%> - Input Proposed Staff Rates</td></tr></table>   
                        
                <fieldset class="sub_function">
                    <legend class="sub_function">Work Assignment Information</legend>
                    <table class="no_border" width="97.5%">
                        <tr valign="top"><td width="195"><b>Service Category/Group          </b></td><td width="2">:</td><td><%=request.getParameter("scg_name")%></td></tr>
                        <tr valign="top"><td            ><b>Work Assignment Title           </b></td><td          >:</td><td><%=request.getParameter("wa_title")%></td></tr>
                        <tr valign="top"><td            ><b>Bureau/Department               </b></td><td          >:</td><td><%=request.getParameter("dept_name")%></td></tr>
                        <%if (waType.equals("proposal")) {%>
                        <tr valign="top"><td            ><b>Proposal Submission Closing Date</b></td><td          >:</td><td><%=request.getParameter("closing_date")%></td></tr>
                        <tr valign="top"><td            ><b>Debarred Contractor(s)       	</b></td><td          >:</td><td><%=(dContractorList.equals("") ? "N/A" : dContractorList)%></td></tr>
                        <% } %>
                        <%if (waType.equals("change_request")) {%>
                        <tr valign="top"><td            ><b>Authorised Person               </b></td><td          >:</td><td><%=authorizedPerson%></td></tr>
                        <tr valign="top"><td            ><b>Awarded Date                    </b></td><td          >:</td><td><%=awardedDate%></td></tr>
                        <% } %>
                    </table>
                </fieldset>

                <table class="no_border">
                    <tr><td><font color="red">Note: If there exists multiple proposed staff rates for the same staff category, please input the highest rate.</font></tr></td>
                </table>
                
                <fieldset class="sub_function">
                    <legend class="sub_function">Staff Rates</legend>            
                    <%
                        for (int i = 0; i < CeilingRateCat1Info.NumOfWorkingLocation; i++)
                        {
                    %>
                    <table class="list">
                        <tr class="list_header_gradient_blue">
                            <td class="list" colspan="2" align="center"><%=CeilingRateCat1Info.WorkingLocationName[i]%></td>
                            <td class="list" colspan="10" align="center">Daily Rate</td>
                        </tr>
                        <tr class="list_header_gradient_gray">
                            <td class="list" colspan="2">&nbsp;</td>
                            <td class="list" colspan="10" align="center">Standard Staff Category</td>
                        </tr>
                        <tr class="list">
                            <td class="list" colspan="2">&nbsp;</td>
                            <td class="list" align="center">3</td>
                            <td class="list" align="center">4</td>
                            <td class="list" align="center">5</td>
                            <td class="list" align="center">6</td>
                            <td class="list" align="center">7</td>
                            <td class="list" align="center">8</td>
                            <td class="list" align="center">9</td>
                            <td class="list" align="center">10</td>
                            <td class="list" align="center">11</td>
                            <td class="list" align="center">12</td>                      
                        </tr>                  
                        <%
                            int count= 0;
                            for (int j = 0; j < allCeilingRateInfo.size(); j++)
                            {
                                count++;

                                CeilingRateCat1Info aCRCat1Info = (CeilingRateCat1Info)allCeilingRateInfo.get(j);

                                String rowClassName = (count % 2 == 1) ? "list_odd" : "list_even";
                        %>
                        <tr class="<%=rowClassName%>" onmouseover="this.className='list_hl'" onmouseout="this.className='<%=rowClassName%>'">
                            <td class="list" width="120"><%=aCRCat1Info.getContractorID()%></td>
                            <td class="list" width="30"><%=aCRCat1Info.getCurrency()%></td>
                            <%
                                for (int k = 0; k < CeilingRateCat1Info.NumOfStaffCategory; k++)
                                {
                                        String displayValue = (aCRCat1Info.getStdStaffRate(k, i) == -999) ? "No Offer" : ""; %>
                            <td class="list" align="center"> <input class="text" type="text" name="TxtSR__<%=aCRCat1Info.getContractorID()%>__<%=aCRCat1Info.toStdStaffRateTableField(k, i)%>" style="width: 60px" value="<%=displayValue%>"></td> 
                            <% } // end for k %>
                        </tr> 
                        <%  } // end for j %>
                    </table>                
                    <% }// end for i %>

                    <table id="sup_table" class="list" style="width:250">   
                        <tr class="list_header_gradient_blue">
                            <td class="list" colspan="2" width="180">&nbsp;</td>
                            <td class="list" align="center">Daily Rate</td>
                        </tr>
                        <tr class="list">
                            <td class="list" align="center" colspan="2"><b>Supplementary Staff Category</b></td>
                            <td class="list" align="center">1</td>
                        </tr>
                        <% 
                            int totalSupRecCount = 0;

                            for (int i = 0; i < CeilingRateCat1Info.NumOfWorkingLocation; i++)
                            {
                        %>
                        <tr class="list_header_gradient_gray">
                            <td class="list" colspan="2"><%=CeilingRateCat1Info.WorkingLocationName[i]%></td>
                            <td class="list">&nbsp;</td>
                        </tr>  
                        <%
                            int count = 0;

                            for (int j = 0; j < allCeilingRateInfo.size(); j++)
                            {
                                CeilingRateCat1Info aCRCat1Info = (CeilingRateCat1Info)allCeilingRateInfo.get(j);

                                if (aCRCat1Info.getSupStaffIndicator() == -1)
                                {
                                    count++;

                                    totalSupRecCount++;

                                    String rowClassName = (count % 2 == 1) ? "list_odd" : "list_even";

                                    String displayValue = (aCRCat1Info.getSupStaffRate(i) == -999) ? "No Offer" : ""; %>
                        <tr class="<%=rowClassName%>" onmouseover="this.className='list_hl'" onmouseout="this.className='<%=rowClassName%>'">                        
                            <td class="list" width="120"><%=aCRCat1Info.getContractorID()%></td>
                            <td class="list" width="30"><%=aCRCat1Info.getCurrency()%></td>  
                            <td class="list" align="center"><input class="text" type="text" name="TxtSR__<%=aCRCat1Info.getContractorID()%>__<%=aCRCat1Info.toSupStaffRateTableField(i)%>" style="width: 60px" value="<%=displayValue%>"></td>
                        </tr>
                        <% 
                                    } // end if
                                } // end for j
                                } // end for i
                        %>
                    </table>
                </fieldset>

                <table class="no_border" width="97.5%">
                    <tr> 
                        <td align="center"><img src="../images/btn_back.jpg"     class="function_button" alt="Go back to previous screen"      onclick="GetStaffRates_BtnBackOnClick()"></td>
                        <td>&nbsp;</td>
                        <td align="center"><img src="../images/btn_vr.jpg"       class="function_button" alt="Enquire validation results" onclick="GetStaffRates_BtnSubmitOnClick('n', '<%=request.getParameter("sc")%>')"></td>
                        <td>&nbsp;</td>
                        <%if (waType.equals("proposal"))
                        {%>
                        <td align="center"><img src="../images/btn_vrqs.jpg"     class="function_button" alt="Enquire validation results and Contractor Performance Scores of contractors selected" onclick="GetStaffRates_BtnSubmitOnClick('y', '<%=request.getParameter("sc")%>')"></td>
                        <td>&nbsp;</td>
                        <%}%>
                        <td align="center"><img src="../images/btn_clear.jpg"    class="function_button" alt="Clear staff rates filled in"    onclick="GetStaffRates_BtnResetOnClick()"></td>
                    </tr>
                </table>
                
            </fieldset>
        </form>
    </body>
</html>

<script language="javascript">    
    <% if (totalSupRecCount == 0) out.println("document.getElementById('sup_table').style.display='none';"); %>
</script>
