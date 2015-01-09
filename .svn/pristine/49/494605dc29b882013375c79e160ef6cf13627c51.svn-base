<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
String user_id = request.getHeader("originaluserid");
String dept_id = request.getHeader("deptid");
String user_name = request.getHeader("username");

// * set user for testing
// Cat 1 user
user_id ="user1.ogcio";
dept_id="ogcio";
user_name="User1 in OGCIO";

// Unauthorized user
//user_id ="userA.ogcio";
//dept_id="ogcio";
//user_name="UserA in OGCIO";

user_id= request.getParameter("UserId");
dept_id= request.getParameter("DeptId");
user_name= request.getParameter("UserName");


//* end 

if (user_id == null || dept_id == null || user_name == null){ // Not login, redirect to portal login screen
    response.sendRedirect("unauthorized.jsp");
}
%>   
   
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QPSES Authorization Process</title>
    </head>
    <body onload="javascript:form1.submit();">
    <H1>SOA-QPS Quality Professional Services Information System (QPSIS)</H1>
    
<P>Authorization in progess .... please wait </P>
<form name="form1" method="post" action="QPSESEntry">
  <input type="hidden" name="UserId"   value="<%=user_id%>">
  <input type="hidden" name="UserName" value="<%=user_name%>">
  <input type="hidden" name="DPDeptId"   value="<%=dept_id%>">
</form>
<P>&nbsp;</P>

    </body>
</html>
