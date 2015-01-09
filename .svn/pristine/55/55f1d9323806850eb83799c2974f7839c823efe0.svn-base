<%@page import="qpses.security.SecurityContext"%>
<%
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    SecurityContext secCtx = (SecurityContext)session.getAttribute("QPSES_SECURITY_CONTEXT");

    String selectedColor   = "brown";
    String unselectedColor = "white";
    int layerTop           = 91;
    int layerOffset        = 110;
%>

 <script type="text/javascript" src="../js/jquery.min.js"></script> 
<script type="text/javascript">
$( document ).ready(function() {
    var menubarwidth = $('#menubar').width();
    var divheaderformstyle = $('#divheaderform').attr( 'style');
    var function_title_width = $('.function_title').width();
    if (menubarwidth < function_title_width){
	    divheaderformstyle = divheaderformstyle + ';width: ' + menubarwidth + ';';
		$('#divheaderform').attr( 'style', divheaderformstyle);
    }
});
</script>

 	<form name="headerform" method="POST" action="SystemOut.servlet">  
	    <table width="100%" id="titlebar" border="0" cellspacing="0" cellpadding="0">
	         <tr> 
	             <td><img src="../images/banner.jpg"></td>
	             <td>
	                 <div class="defaultfont" align="right">Login as <%=secCtx.getUserId()%>&nbsp;</div>
	             </td>
	         </tr>
	    </table>
	    
	     <table width="100%" id="menubar" border="0" cellspacing="0" cellpadding="0">
	         <tr> 
	             <td background="../images/mmiddle.jpg" width="33" height="38"></td>
	             <td background="../images/mmiddle.jpg" valign="middle"> 
	                 <table width="960" border="0" cellspacing="0" cellpadding="0">
	                     <tr height="38"> 
	                         <td width="80" nowrap>
	                            <a class="header_menu" href="index.jsp" title="Go to the home page of user functions">Home</a>
	                         </td>
	
	                         <% if (secCtx.isPrivilegedFunction("RATEVALID")) { %>
	                         <td width="140" onMouseOver="MM_showHideLayers('LayerDM','','show')" onMouseOut="MM_showHideLayers('LayerDM','','hide')" nowrap><a class="header_menu">Staff Rate Validation</a> 
	                             <div id="LayerDM" style="position:absolute; width:150px; height:200px; z-index:1; visibility: hidden; left: <%=layerOffset%>px; top: <%=layerTop%>px" onMouseOver="MM_showHideLayers('LayerDM','','show')" onMouseOut="MM_showHideLayers('LayerDM','','hide')"> 
	                                 <table width="100%" border="1" cellpadding="5" bordercolor="#A8AEFD" bgcolor="#FFFFFF" cellspacing="0">
	                                     <tr> 
	                                        <td nowrap>
	                                            <a class="header_menu" href="StaffRateValidationSLUser" title="To validate proposed staff rates against the ceiling rates for proposal evaluation of work assignment">Staff Rate Validation for Proposal Evaluation</a>
	                                        </td>
	                                     </tr>
	<!--                                      <tr>  -->
	                                        <td nowrap>
	                                            <a class="header_menu" href="StaffRateValidationCRSLUser" title="To validate proposed staff rates against the ceiling rates for change request of work assignment">Staff Rate Validation for Change Request</a>                                             
	                                         </td>
	                                     </tr>
	                                 </table>
	                             </div>
	                         </td>
	                         <% }%>                          
	                         <% if (secCtx.isPrivilegedFunction("QSENQ"     )) {%>
	                         <td width="250" nowrap>
	                             <a class="header_menu" href="CPSSLUser" title="To enquire the Contractor Performance Scores for proposal evaluation">Contractor Performance Score Enquiry</a>
	                          </td>
	                         <% }%>
	                         <% if (secCtx.isPrivilegedFunction("DRENQ"       )) {%>
	                         <td width="160" nowrap>
	                             <a class="header_menu" href="DiscountRateSLUser" title="To enquire discount rates">Discount Rate Enquiry</a>
	                          </td>
	                         <% }%>
	                         <% if (secCtx.isPrivilegedFunction("ACRUSERENQ"  )) { %>
	                         <td width="190" nowrap>
	                             <a class="header_menu" href="AvgCeilingRateSLUser" title="To enquire the average ceiling rates">Average Ceiling Rate Enquiry</a>
	                          </td>
	                         <% }%>          
	                         <% if (secCtx.isPrivilegedFunction("CPAR"  )) { %>
	                         <td width="120" nowrap>
	                             <a class="header_menu" href="CPARSLUser" title="To create or update a CPAR">CPAR creation</a>
	                          </td>
	                         <% }%>  
	                         <% if (secCtx.isPrivilegedFunction("CHPWD"  )) { %>
	                         <td width="120" nowrap>
	                             <a class="header_menu" href="ChangePassword.jsp" title="To change password">Change Password</a>
	                          </td>
	                         <% }%>              
	                         <td width="80" nowrap align="right">
	                            <a class="header_menu" href="#" OnClick="FnLogOut()" title="Log out of the system">Log Out</a>
	                         </td>
	                     </tr>
	                 </table>
	             </td>
	             <td background="../images/mright.jpg" width="33" height="38">&nbsp;</td>
	         </tr>
	     </table>
 	</form>
 
 <script language="javascript">
     function FnLogOut()
     {
     if (confirm("Are you sure to log out of the system?"))
     location.href = "SystemOut.servlet";
     }    

     function MM_changeProp(objName,x,theProp,theValue) { //v6.0
     var obj = MM_findObj(objName);
     if (obj && (theProp.indexOf("style.")==-1 || obj.style)){
     if (theValue == true || theValue == false)
     eval("obj."+theProp+"="+theValue);
     else eval("obj."+theProp+"='"+theValue+"'");
     }
     }

     function MM_findObj(n, d) { //v4.01
     var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
     d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
     if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
     for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
     if(!x && d.getElementById) x=d.getElementById(n); return x;
     }

     function MM_showHideLayers() { //v6.0
     var i,p,v,obj,args=MM_showHideLayers.arguments;
     for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
     if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
     obj.visibility=v; }
     }
     
     function selectFunction(url){
     f=document.all("headerform"); 
     f.action = url;    
     f.submit();
     }
 </script>
