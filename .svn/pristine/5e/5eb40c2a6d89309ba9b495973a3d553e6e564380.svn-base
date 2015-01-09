<%@page import="qpses.security.SecurityContext"%>

<%
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    SecurityContext secCtx = (SecurityContext)session.getAttribute("QPSES_SECURITY_CONTEXT");
%>

 <form name="headerform" method="POST" action="SystemOut.servlet">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td background="../images/banner_only_background.jpg"><img src="../images/banner_only.jpg"></td>
      <td background="../images/banner_only_background.jpg">
        <div class="defaultfont" align="right">Login as <%=secCtx.getUserId()%>&nbsp;</div>
      </td>
    </tr>
  </table>
     </form>
 

