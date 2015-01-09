<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    //get the parameter
    
// get file
    ByteArrayOutputStream baso = (ByteArrayOutputStream) session.getAttribute("PDF_FILE_STREAM");
    
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <script language="JavaScript" src="../js/nw.js" type="text/javascript"></script>
                        <script language="JavaScript" src="../js/ceilingratecheck.js" type="text/javascript"></script>
    </head>
    <body onload="javascript:form1.submit()">
    <form name="form1" action="CeilingRatePrintReport.servlet">     
        <INPUT type="hidden"  name="selectedKey1" value="<%=request.getParameter("selectedKey1")%>">
        <INPUT type="hidden"  name="selectedKey2" value="<%=request.getParameter("selectedKey2")%>">
    </form>
    </body>
</html>
