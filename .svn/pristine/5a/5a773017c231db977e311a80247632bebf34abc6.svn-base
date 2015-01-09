<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

<%
    String deptName = (String)request.getSession().getAttribute("SRV_DEPT_NAME");
    
    if (deptName == null)
    {throw new Exception( "Unable to get department name.");}    
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contractor Performance Score Enquiry - Select Work Assignment</title>
        <script language="javascript" type="text/javascript" src="../js/QualitySubscoreUser.js"></script>
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">
    </head>
    <body>    
        <%@include file="include/header.jsp"%> 
        <form name="form" method="post" action="" target="">
            <input type="hidden" name="scg"           value="">
            <input type="hidden" name="dept_id"       value="">
            <input type="hidden" name="wa_file_part"  value="">
            <input type="hidden" name="wa_file_no"    value="">
            
            <a name="link_top"></a>
            
            <fieldset class="function">  
            <table class="function_title"><tr><td>Contractor Performance Score Enquiry - Select Work Assignment</td></tr></table>

            <fieldset class="sub_function">
                <table class="no_border">
                    <tr><td><li>Please click on a work assignment record to enquire the Contractor Performance Scores of the contractors invited for the work assignment</li></tr></td>
                    <tr><td><li>Work assignment is sorted by service category/group, then work assignment title</li></tr></td>                  
                </table>
            </fieldset>
           
            <fieldset class="sub_function">
                <legend class="sub_function">Work Assignment for <%=deptName%></legend>

                <table class="list">
                    <tr class="list_header_gradient_blue">
                        <td class="list" width="175">Service Category/Group</td>
                        <td class="list">Work Assignment Title</td>
                    </tr>
                    
                    <%
                        try
                        {
                            int numOfRec = 0;
                            
                            List allWAInfo = (List)request.getSession().getAttribute("allWAInfo");

                            if (allWAInfo == null)
                            { throw new Exception("Session [allWAInfo] is NULL"); }

                            for (int i = 0; i < allWAInfo.size(); i++) // for i
                            {
                                WorkAssignmentInfo aWAInfo = (WorkAssignmentInfo)allWAInfo.get(i);

                                if (aWAInfo.getStatus().equals("p") || aWAInfo.getStatus().equals("b"))
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
                                    //String scgStr = "Service Category " + sc + " " + (sg.equals("J") ? "Major" : "Minor") + " Group";
                    %>    
                    <tr class="<%=rowClassName%>" onmouseover="this.className='list_hl_hand'" onmouseout="this.className='<%=rowClassName%>'" onclick="GetWA_WAOnClick('<%=scg%>', '<%="" + aWAInfo.getDepartmentId()%>', '<%=aWAInfo.getFilePart()%>', '<%=aWAInfo.getFileNo()%>')"> 
                        <td class="list"><%=scgStr%></td>
                        <td class="list"><%=aWAInfo.getTitle()%></td>
                    </tr>
                    <%          }
                            } // end for i %>  
                    <%
                        if (allWAInfo.size() == 0)
                        { %> <tr class="list"><td class="list" colspan="2" align="center"><font class="warning">No work assignment is available</font></td></tr> <% } %>
                    </table>   
            </fieldset>
        </form>
        <%      
                            }
                            catch(Exception ex)
                            { throw new Exception(ex.getMessage()); }
        %>           
    </body>
</html>