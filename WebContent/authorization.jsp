<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
    String user_id = request.getHeader("originaluserid");
    String password = "";
	String dept_id = null;
    
    user_id= request.getParameter("UserId");
    password= request.getParameter("Password");
    dept_id= "ogcio";

    //* end

    if (user_id == null || dept_id == null || password == null){ // Not login, redirect to portal login screen
        response.sendRedirect("unauthorized.jsp");
    }
%>   
   
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QPSIS Authorization Process</title>
        <link rel="stylesheet" type="text/css" href="styles/styles_user.css">
    </head>
    <body class="defaultfont" onload="javascript:form1.submit()">    
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td background="images/banner_only_background.jpg"><img src="images/banner_only.jpg"></td>
            </tr>
        </table>
        <div align="center">
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <p><b>Authorization in progess. Please wait ...</b></p>
</div>
        <form name="form1" method="post" action="QPSESEntry">
            <input type="hidden" name="UserId"   value="<%=user_id%>">
            <input type="hidden" name="Password" value="<%=password%>">
        </form>
        <P>&nbsp;</P>

    </body>
</html>