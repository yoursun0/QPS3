<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    String msg =   getValue((String)  session.getAttribute("AUTHORIZATION_MSG"));    

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QPSIS Authorization Process</title>
        <link rel="stylesheet" type="text/css" href="styles/styles_user.css">
        <link rel="stylesheet" type="text/css" href="styles/login.css">
    </head>
    <body class="defaultfont">

        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td background="images/banner_only_background.jpg"><img src="images/banner_only.jpg"></td>
            </tr>
        </table>
		<br /><br />
		
        <div class="front-login"> 
			<p>
			 <%
                if (! msg.equals("")) out.print(msg);
             %>
             <br />
    		<form name="form1" class="unauthorized" method="post" action="">
                <a href="index.jsp" name="Back" alt="Back" ><img src="images/btn_back.jpg"></a>
                <input type="image" name="Close" src="images/btn_close.jpg" alt="Close" onclick="javascript:window.close()" />
        	</form>
			</p>
        </div>
        <br /> <br />
	    <hr />
    </body>
</html>
