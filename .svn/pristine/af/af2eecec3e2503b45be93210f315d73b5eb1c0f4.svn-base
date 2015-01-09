<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

<%
    try
    {
        String waType = (String)request.getSession().getAttribute("WA_TYPE");

        if (waType == null)
        { throw new Exception("Session [WAType] is NULL"); }   

        String waTypeStr = (waType.equals("proposal") ? "Proposal Evaluation" : "Change Request");
        String waStatus = (waType.equals("proposal") ? "bp" : "n");
                        
        List allWAInfo = (List)request.getSession().getAttribute("allWAInfo");

        if (allWAInfo == null)
        { throw new Exception("Session [allWAInfo] is NULL"); }
                        
        String deptName = (String)request.getSession().getAttribute("SRV_DEPT_NAME");
        if (deptName == null)
        {throw new Exception( "Unable to get department name.");}           
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Rate Validation for <%=waTypeStr%> - Select Work Assignment</title>
        <script language="javascript" type="text/javascript" src="../js/StaffRateValidationUser.js"></script>        
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">
    </head>
    <body>
        <%@include file="include/header.jsp"%> 
        <form name="form" method="post" action="" target="_self">
            <input type="hidden" name="scg"            value="">
            <input type="hidden" name="dept_id"        value="">
            <input type="hidden" name="wa_file_part"   value="">
            <input type="hidden" name="wa_file_no"     value="">
            <input type="hidden" name="request_action" value="">            
            
            <fieldset class="function">  
            <table class="function_title"><tr><td>Staff Rate Validation for <%=waTypeStr%> - Select Work Assignment</td></tr></table>

            <fieldset class="sub_function">
                <table class="no_border">
                    <tr><td><li>Please click on a work assignment record to select the work assignment to validate staff rates proposed by contractors</li></tr></td>
                    <tr><td><li>Work assignment is sorted by service category/group, then work assignment title</li></tr></td>
                    <tr><td><li>Explanatory notes on staff categories can be downloaded from <a class="list" target="_self" href="#" onclick="GetWA_InfoPdfOnClick()" title="Download Ceiling Rate Type And Standard Staff Category">here</a></li></td></tr>
                </table>
            </fieldset>
           
            <fieldset class="sub_function">
                <legend class="sub_function">Work Assignment for <%=deptName%></legend>
                <table class="list">
                    <tr class="list_header_gradient_blue">
                        <td class="list" width="175">Service Category/Group</td>
                        <td class="list">Work Assignment Title</td>
                <%
                                    if (waType.equals("change_request"))
                                    {    
                %>
                        <td class="list" width="130">Authorised Person</td>
                        <td class="list" width="85">Awarded Date</td>                
                <%
                                    }
                %>        
                    </tr>
                <%
                            int numOfRec = 0;
                        
                            for (int i = 0; i < allWAInfo.size(); i++) // for i
                            {
                                WorkAssignmentInfo aWAInfo = (WorkAssignmentInfo)allWAInfo.get(i);
                            
                                if (waStatus.contains(aWAInfo.getStatus()))
                                {
                                    numOfRec++;

                                    String rowClassName = (numOfRec % 2 == 1) ? "list_odd_hand" : "list_even_hand";

                                    String scg    = aWAInfo.getServiceCategoryGroup();
                                    String sc     = scg.substring(0, 1);
                                    String scgStr = "";
                                    if (sc.equals("1") || sc.equals("4"))
                                    {
                                        scgStr = "Service Category " + sc;
                                    }
                                    else
                                    {
                                        String sg     = scg.substring(2, 3);
                                        scgStr = "Service Category " + sc + " " + (sg.equals("J") ? "Major" : "Minor") + " Group";
                                    }    
                %>    
                    <tr class="<%=rowClassName%>" onmouseover="this.className='list_hl_hand'" onmouseout="this.className='<%=rowClassName%>'" onclick="GetWA_WAOnClick('<%=scg%>', '<%="" + aWAInfo.getDepartmentId()%>', '<%=aWAInfo.getFilePart()%>', '<%=aWAInfo.getFileNo()%>')"> 
                        <td class="list"><%=scgStr%></td>
                        <td class="list"><%=aWAInfo.getTitle()%></td>
                <%
                                    if (waType.equals("change_request"))
                                    {    
                %>
                        <td class="list"><%=aWAInfo.getAuthorizedPerson()%></td>
                        <td class="list"><%=SysManager.getStringfromSQLDate(aWAInfo.getAwardedDate())%></td>                                                        
                    </tr>
                <%                  } // end if
                                } // end if    
                        } // end for i
                    if (numOfRec == 0)
                            { %> 
                    <tr class="list">
                        <td class="list" colspan="3" align="center"><font class="warning">No work assignment is available</font></td>
                    </tr>
                <% 
                            }                             
                %>
                </table>                
                <%
                    }
                    catch(Exception ex)
                    { throw new Exception(ex.getMessage()); }
                %>
            </fieldset>
        </form>
         
    </body>
</html>