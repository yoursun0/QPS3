<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.ContractorDataBean,
                                qpses.business.ContractorInfo"%>
<%

    // initialize variable
    String contractor_id = "";
    String contractor_name = "";
    boolean group_1N = false;
    boolean group_1J = false;
    boolean group_2N = false;
    boolean group_2J = false;
    boolean group_3N = false;
    boolean group_3J = false;
    boolean group_4N = false;
    boolean group_4J = false;
    String org_key1 = "";

    // check  for message
    String msg =   getValue((String)  session.getAttribute("CONTRACTOR_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("CONTRACTOR_MSG_TYPE"));
    if (!msg.equals("")){
        session.removeAttribute("CONTRACTOR_MSG");
        session.removeAttribute("CONTRACTOR_MSG_TYPE");
    }

    // get last input from session variable, if any
    ContractorInfo c = new ContractorInfo();
    c = (ContractorInfo) session.getAttribute("CONTRACTOR_DATA");
    if (c!=null){
        contractor_id = c.getContractorId();
        contractor_name = c.getContractorName();
        group_1N = (c.get1NInd()==-1)?true:false;
        group_1J = (c.get1JInd()==-1)?true:false;
        group_2N = (c.get2NInd()==-1)?true:false;
        group_2J = (c.get2JInd()==-1)?true:false;
        group_3N = (c.get3NInd()==-1)?true:false;
        group_3J = (c.get3JInd()==-1)?true:false;
        group_4N = (c.get4NInd()==-1)?true:false;
        group_4J = (c.get4JInd()==-1)?true:false;
        org_key1 = c.getOrgKey1();
        session.removeAttribute("CONTRACTOR_DATA");
    }else{
        // get values from database

        // check keys passed from ContractorList.jsp, if any
        org_key1 = getValue(request.getParameter("selectedKey1"));

        // otherwise, check keys passed from this page, if any
        if (org_key1.equals("")){
            org_key1=getValue(request.getParameter("OrgKey1"));
        }

        // if no keys, force to return to ContractorList.jsp
        if (org_key1.equals("")){
            response.sendRedirect("ContractorList.jsp");
            return;
        }

        ContractorDataBean contractorDB =new ContractorDataBean();
        c = contractorDB.selectContractorByKeys(org_key1);
        if (c!=null){
            contractor_id = c.getContractorId();
            contractor_name = c.getContractorName();
            group_1N = (c.get1NInd()==-1)?true:false;
            group_1J = (c.get1JInd()==-1)?true:false;
            group_2N = (c.get2NInd()==-1)?true:false;
            group_2J = (c.get2JInd()==-1)?true:false;
            group_3N = (c.get3NInd()==-1)?true:false;
            group_3J = (c.get3JInd()==-1)?true:false;
            group_4N = (c.get4NInd()==-1)?true:false;
            group_4J = (c.get4JInd()==-1)?true:false;
        }
    }

%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Add User</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <script language="JavaScript" src="../js/contractorcheck.js" type="text/javascript"></script>                   
    </head>
    <body>
        <%@include file="include/header.jsp"%>        
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Contractor Code Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableNoBorder" align="center">
                    <tr class="title1"> 
                        <td>Update Contractor</td>
                    </tr>
                    <tr> 
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%></td>
                    </tr>
                    <tr> 
                    <td> 
                        <div align="center"> 
                                
                                
                            <table  width="98%" cellspacing="1" class="tableBackground">
                                <tr> 
                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Contractor 
                                    ID</td>
                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                        <input type="text" name="ContractorId" value="<%=contractor_id%>" size="50" maxlength="20" class="inputText" />
                                    </td>
                                </tr>
                                <tr> 
                                    <td class="tableVerticalHeaderAlignLeft1">Contractor Name</td>
                                    <td class="tableVerticalContentAlignLeft1"> 
                                        <input type="text" name="ContractorName" class="inputText" size="60" maxlength="150" value="<%=contractor_name%>">
                                    </td>
                                </tr>
                                <tr> 
                                    <td class="tableVerticalHeaderAlignLeft1" rowspan="4">Service Category/Group</td>
                                    <td class="tableVerticalContentAlignLeft1"> 
                                    <% if (group_1J){%>
                                    <img src="../images/btn_tick.jpg" align="absbottom"> 
                                    <%}else{%>
                                    &nbsp;&nbsp;&nbsp; 
                                    <%}%>
                                    Category 1</td>
                                </tr>
                                <tr> 
                                    <td class="tableVerticalContentAlignLeft1"> 
                                    <% if (group_2N){%>
                                    <img src="../images/btn_tick.jpg" align="absbottom"> 
                                    <%}else{%>
                                    &nbsp;&nbsp;&nbsp; 
                                    <%}%>
                                    Category 2 Minor Group<br>
                                    <% if (group_2J){%>
                                    <img src="../images/btn_tick.jpg" align="absbottom"> 
                                    <%}else{%>
                                    &nbsp;&nbsp;&nbsp; 
                                    <%}%>
                                    Category 2 Major Group</td>
                                </tr>
                                <tr> 
                                <td class="tableVerticalContentAlignLeft1"> 
                                <% if (group_3N){%>
                                <img src="../images/btn_tick.jpg" align="absbottom"> 
                                <%}else{%>
                                &nbsp;&nbsp;&nbsp; 
                                <%}%>
                                Category 3 Minor Group<br>
                                <% if (group_3J){%>
                                <img src="../images/btn_tick.jpg" align="absbottom"> 
                                <%}else{%>
                                &nbsp;&nbsp;&nbsp; 
                                <%}%>
                                Category 3 Major Group</td>
                                <tr> 
                                    <td class="tableVerticalContentAlignLeft1"> 
                                    <% if (group_4J){%>
                                    <img src="../images/btn_tick.jpg" align="absbottom"> 
                                    <%}else{%>
                                    &nbsp;&nbsp;&nbsp; 
                                    <%}%>
                                    Category 4 </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                    </tr>
                </table>
                <div align="center"></div>
                <table border="0" cellpadding="3" align="center">
                    <tr> 
                        <td width="50%">                                     
                            <div align="center"> <a href="#"><img src="../images/btn_update.jpg" width="98" height="42" name="ConfirmUpdate" onClick="selectaction(form1,this)" border="0" alt="Update"></a></div>
                        </td>
                        <td width="50%">                                     
                            <div align="center"> <a href="#"><img src="../images/btn_cancel.jpg" width="98" height="42" name="Cancel" onClick="selectaction(form1,this)" border="0" alt="Cancel"></a></div>
                        </td>
                    </tr>
                </table>    
                <input type="hidden" name="OrgKey1" value="<%=org_key1%>">
            </form>  
        </fieldset>
    </body>
</html>
