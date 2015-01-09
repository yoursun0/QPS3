<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="qpses.business.*"%>
<%@page import="qpses.util.*"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
        <title>Discount Rate Enquiry - Select Service Category/Group</title>
        <script language="javascript" type="text/javascript" src="../js/DiscountRateUser.js"></script>
        <link rel="stylesheet" type="text/css" href="../styles/styles_user.css">        
    </head>

    <body>
        <%@include file="include/header.jsp"%>     
        <form name="form" method="post" action="">
            
            <input type="hidden" name="scg"            value="">
            <input type="hidden" name="effective_date" value="">
            <input type="hidden" name="request_action" value="">

            <fieldset class="function">  
            <table class="function_title"><tr><td>Discount Rate Enquiry - Select Service Category/Group</td></tr></table>  

            <fieldset class="sub_function">
                <table class="no_border">
                    <tr><td><li>Please click on a service category/group to enquire the discount rates offered by contactors of that service category/group</li></td></tr>       
                </table>
            </fieldset>

            
            <fieldset class="sub_function">
               <table class="no_border" style="width:97.5%">
                    <%
                    List allDREff = (List)request.getSession().getAttribute("allDREff");

                    if (allDREff == null) throw new SysException("Session [allDREff] is NULL.");

                        for (int i = 1; i <= 4; i++)
                        {
                        
                        if (i==1 || i==4)
                        {
                    %>     
                       
                            <tr valign="top">
                            <td width="15%">&nbsp;</td>
                            <td width="70%" colspan="2" >
                                <%
                                    int count = 0;

                                    for (int j = 0; j < allDREff.size(); j+=2)
                                    {
                                        String scgStr        = (String)allDREff.get(j);
                                        String effectiveDate = (String)allDREff.get(j + 1);

                                        if (scgStr.equals(Integer.toString(i)))
                                        {
                                            count++;

                                            {%><a class="list" href="#" OnClick="GetInfo_LinkOnClick('<%=effectiveDate%>', '<%=i%>')">Service Category <%=i%></a><%}
                                        }
                                    }

                                    if (count == 0)
                                    { %><font color="red">Not Available</font><% }
                                %>
                            </td>                        
                            <td width="15%">&nbsp;</td>
                        </tr>
                        <tr><td colspan="5">&nbsp;</td></tr>  
                    <%  }
                        else
                        {
                            
                    %>
                    <tr valign="top">
                        <td width="15%">&nbsp;</td>
                        <td width="35%">
                            <%
                                int count = 0;

                                for (int j = 0; j < allDREff.size(); j+=2)
                                {
                                    String scgStr        = (String)allDREff.get(j);
                                    String effectiveDate = (String)allDREff.get(j + 1);

                                    if (scgStr.equals(i + "/N"))
                                    {
                                        count++;

                                        {%><a class="list" href="#" OnClick="GetInfo_LinkOnClick('<%=effectiveDate%>', '<%=i + "/N"%>')">Service Category <%=i%> Minor Group</a><%}
                                    }
                                }

                                if (count == 0)
                                { %><font color="red">Not Available</font><% }
                            %>
                        </td>
                        <td width="35%" >
                            <%
                                count = 0;

                                for (int j = 0; j < allDREff.size(); j+=2)
                                {
                                    String scgStr        = (String)allDREff.get(j);
                                    String effectiveDate = (String)allDREff.get(j + 1);

                                    if (scgStr.equals(i + "/J"))
                                    {
                                        count++;

                                        {%><a class="list" href="#" OnClick="GetInfo_LinkOnClick('<%=effectiveDate%>', '<%=i + "/J"%>')">Service Category <%=i%> Major Group</a><%}
                                    }
                                }

                                if (count == 0)
                                { %><font color="red">Not Available</font><% }
                            %>
                        </td>
                        <td width="15%">&nbsp;</td>
                    </tr>
                    <tr><td colspan="5">&nbsp;</td></tr>
                    <%
                            }
                        }
                    %>
                </table>
            </fieldset>
        </form>
    </body>
</html>