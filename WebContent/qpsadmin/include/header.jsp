<%
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ page import="qpses.security.SecurityContext"%>
<%
    SecurityContext secCtx = (SecurityContext)session.getAttribute("QPSES_SECURITY_CONTEXT");
    String selectedColor = "brown";
    String unselectedColor="white";
    int layerTop=95;
    int layerOffset=33;
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
     	<script language="JavaScript" src="../js/nw.js" type="text/javascript"></script>                 
	     <table width="100%" id="titlebar" border="0" cellspacing="0" cellpadding="0">
	         <tr> 
	             <td><img src="../images/admin_banner.jpg"></td>
	             <td>
	                 <div class="defaultfont" align="right">Login as <%=secCtx.getUserId()%>&nbsp;</div>
	             </td>
	         </tr>
	     </table>    
	     
	     <table width="100%" id="menubar" border="0" cellspacing="0" cellpadding="0">
	         <tr> 
	             <td background="../images/mmiddle.jpg" width="33" height="38"> </td>
	             <td background="../images/mmiddle.jpg"> 
	                 
	                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                     <tr height="38"> 
	                     	 <%  if (secCtx.isPrivilegedFunction("SYSCMAINT")) { %>
	                         <td width="90" nowrap valign="middle"><a class="header_menu" href="index.jsp">Home</a> 
	                         </td>
	                         <% } %>
	                         <!-- // Data Maintenance-->
	                         <% if (secCtx.isPrivilegedFunction("WAMAINT") ||
	                secCtx.isPrivilegedFunction("ACRMAINT") ||
	                secCtx.isPrivilegedFunction("CRMAINT") ||
	                secCtx.isPrivilegedFunction("QSMAINT")) {
	            layerOffset += 90; //add width of first cell%>
	                         <td  width="140"
	                             onMouseOver="MM_showHideLayers('LayerDM','','show')"
	                             onMouseOut="MM_showHideLayers('LayerDM','','hide')" nowrap
	                             ><a class="header_menu">Data Maintenance</a> 
	                             <!-- // Data Maintenance sub menu-->
	                             <div id="LayerDM" style="position:absolute; width:150px; height:200px; z-index:1; visibility: hidden; left: <%=layerOffset%>px; top: <%=layerTop%>px"
	                                 onMouseOver="MM_showHideLayers('LayerDM','','show')" 
	                                 onMouseOut="MM_showHideLayers('LayerDM','','hide')"> 
	                                 <table width="100%" border="1" cellpadding="5" bordercolor="#A8AEFD" bgcolor="#FFFFFF" cellspacing="0">
	                                     <!-- // Work Assignment maintenance-->
	                                     <% if (secCtx.isPrivilegedFunction("WAMAINT")) {%>
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="WorkAssignmentList.jsp">Work 
	                                         Assignment</a></td>
	                                     </tr>
	                                     <% }%>
	                                     <!-- // Average Ceiling Rate maintenance-->
	                                     <% if (secCtx.isPrivilegedFunction("ACRMAINT")) {%>
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="AvgCeilingRateList.jsp">Average 
	                                         Ceiling Rate</a></td>
	                                     </tr>
	                                     <% }%>
	                                     <!-- // Ceiling Rate maintenance-->
	                                     <% if (secCtx.isPrivilegedFunction("CRMAINT")) {%>
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="CeilingRateList.jsp">Ceiling 
	                                         Rate</a></td>
	                                     </tr>
	                                     <% }%>
	                                     <!-- // Contractor Performance Score maintenance-->
	                                     <% if (secCtx.isPrivilegedFunction("QSMAINT")) {%>
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="CPSList.jsp">Contractor Performance Score</a></td>
	                                     </tr>
	                                     <% }%>
	                                     <!-- // Contractor Debarment maintenance-->
	                                     <% if (secCtx.isPrivilegedFunction("DEBAR")) {%>
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="DebarList.jsp">Contractor Debarment</a></td>
	                                     </tr>
	                                     <% }%>
	                                 </table>
	                             </div>
	                         </td>
	                         <% }%>
	                         <% if (secCtx.isPrivilegedFunction("ACPAR"  )) { 
	                         layerOffset += 140; //add width of second cell%>
	                         <td width="150px"
	                             onMouseOver="MM_showHideLayers('LayerCPAR','','show')"
	                             onMouseOut="MM_showHideLayers('LayerCPAR','','hide')" 			
	                             nowrap> <a class="header_menu">CPAR Maintenance</a> 
	                             <!-- // CPAR Maintenance sub menu-->
	                             <div id=LayerCPAR style="position:absolute; width:200px; height:200px; z-index:1; visibility: hidden; left: <%=layerOffset%>px; top: <%=layerTop%>px"
	                                 onMouseOver="MM_showHideLayers('LayerCPAR','','show')" 
	                                 onMouseOut="MM_showHideLayers('LayerCPAR','','hide')"> 
	                                 <table width="100%" border="1" cellpadding="5" bordercolor="#A8AEFD" bgcolor="#FFFFFF" cellspacing="0">
	                                     <!-- // Update CPAR-->
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="CPARAdmin">Update CPAR</a></td>
	                                     </tr>
	                                     <!-- // Generate CPS-->
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="CPSGenerate.jsp">Generate CPS</a></td>
	                                     </tr>
	                                     <!-- // CPARs Outstanding List-->
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="CPAROutstandingList.servlet">CPARs Outstanding List</a></td>
	                                     </tr>
	                                     <!-- // Download CPARs Record-->
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="CPARRecordDownload.jsp">Search CPARs</a></td>
	                                     </tr>

	                                 </table>
	                             </div>
	                         </td>
	                         
	                         
	                         
	                         <!-- <td width="140" nowrap>
	                             <a class="header_menu" href="CPARAdmin" title="To update or Print a CPAR">CPAR Maintenance</a>
	                          </td> -->
	                          
	                          
	                          
	                          
	                         <% }%>
	                         <% if (secCtx.isPrivilegedFunction("STAT"  )) { %>
	                         <td width="140" nowrap>
	                             <a class="header_menu" href="StatSearch.servlet" title="To generate key statistics report">Statistics Report</a>
	                          </td>
	                         <% }%>  
	                         <!-- // Access Control List Maintenance-->
	                         <% if (secCtx.isPrivilegedFunction("ACLMAINT")) {
	            				layerOffset += 287; //add width of third cell%>
	                         <td width="150px"
	                             onMouseOver="MM_showHideLayers('LayerAC','','show')"
	                             onMouseOut="MM_showHideLayers('LayerAC','','hide')" 			
	                             nowrap> <a class="header_menu">Access Control</a> 
	                             <!-- // Access Control sub menu-->
	                             <div id="LayerAC" style="position:absolute; width:240px; height:200px; z-index:1; visibility: hidden; left: <%=layerOffset%>px; top: <%=layerTop%>px"
	                                 onMouseOver="MM_showHideLayers('LayerAC','','show')" 
	                                 onMouseOut="MM_showHideLayers('LayerAC','','hide')"> 
	                                 <table width="100%" border="1" cellpadding="5" bordercolor="#A8AEFD" bgcolor="#FFFFFF" cellspacing="0">
	                                     <!-- // Access Control List Maintenance-->
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="ACLList.jsp">Access Control 
	                                         List Maintenance</a></td>
	                                     </tr>
	                                     <!-- // Unlock User-->
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="ACLUnlockUser.jsp">Unlock 
	                                         User</a></td>
	                                     </tr>
	                                     <!-- // Forgot Password-->
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="ACLEnableUser.jsp">Enable 
	                                         User</a></td>
	                                     </tr>
	                                 </table>
	                             </div>
	                         </td>
	                         <% }%>
	                         <!-- // Report List-->
	                         <% if (secCtx.isPrivilegedFunction("REPORT")) {
	            			 layerOffset += 153; //add width of fourth cell%>
	                         <td width="130px" 
	                             onMouseOver="MM_showHideLayers('LayerLogsReport','','show')"
	                             onMouseOut="MM_showHideLayers('LayerLogsReport','','hide')" nowrap			
	                             ><a class="header_menu">Logs & Reports</a> 
	                             <!-- // Logs & Reports sub menu-->
	                             <div id="LayerLogsReport" style="position:absolute; width:100px; height:200px; z-index:1; visibility: hidden; left: <%=layerOffset%>px; top: <%=layerTop%>px"
	                                 onMouseOver="MM_showHideLayers('LayerLogsReport','','show')" 
	                                 onMouseOut="MM_showHideLayers('LayerLogsReport','','hide')"> 
	                                 <table width="100%" border="1" cellpadding="5" bordercolor="#A8AEFD" bgcolor="#FFFFFF" cellspacing="0">
	                                     <!-- // Logs-->
	                                     <tr height="15px"> 
	                                         <td
	                                         onMouseOver="MM_showHideLayers('LayerLogs','','show')"
	                                         onMouseOut="MM_showHideLayers('LayerLogs','','hide')" nowrap								
	                                         ><a class="header_menu">Logs ...</a></td>
	                                     </tr>
	                                     <!-- // Report-->
	                                     <tr height="15px">
	                                         <td
	                                         onMouseOver="MM_showHideLayers('LayerReport','','show')"
	                                         onMouseOut="MM_showHideLayers('LayerReport','','hide')" nowrap 
	                                         ><a class="header_menu" href="EnquiryList.jsp">Reports ...</a> </td>
	                                     </tr>
	                                 </table>
	                             </div>							 
	                             <!-- // Logs sub menu-->
	                             <div id="LayerLogs" style="position:absolute; width:180px; height:200px; z-index:1; visibility: hidden; left: <%=(layerOffset+90)%>px; top: <%=(layerTop+5)%>px"
	                                 onMouseOver="MM_showHideLayers('LayerLogs','','show')" 
	                                 onMouseOut="MM_showHideLayers('LayerLogs','','hide')"> 
	                                 <table width="100%" border="1" cellpadding="5" bordercolor="#A8AEFD" bgcolor="#FFFFFF" cellspacing="0">
	                                     <!-- // Inactive Users-->
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="ACLogEnquiry.jsp">Access Log 
	                                         </a></td>
	                                     </tr>
	                                     <!-- // Excess Work Assignment-->
	                                     <tr height="15"> 
	                                         <td><a class="header_menu" href="UserLogEnquiry.jsp">User Activities Log</a></td>
	                                     </tr>
	                                     <tr height="15"> 
	                                         <td><a class="header_menu" href="SRVLogEnquiry.jsp">Staff Rate Validation Log</a></td>
	                                     </tr>				  
	                                     <!-- // Work Assignment Accessed by More than 1 users-->
	                                     <tr height="15"> 
	                                         <td><a class="header_menu" href="AdminLogEnquiry.jsp">Administrator Activities Log</a></td>
	                                     </tr>
	                                 </table>
	                             </div>
	                             <!-- // Reports sub menu-->			  
	                             <div id="LayerReport" style="position:absolute; width:180px; height:200px; z-index:1; visibility: hidden; left: <%=(layerOffset+90)%>px; top: <%=(layerTop +35)%>px"
	                                 onMouseOver="MM_showHideLayers('LayerReport','','show')" 
	                                 onMouseOut="MM_showHideLayers('LayerReport','','hide')"> 
	                                 <table width="100%" border="1" cellpadding="5" bordercolor="#A8AEFD" bgcolor="#FFFFFF" cellspacing="0">
	                                     <!-- // Inactive Users-->
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="javascript:new_window('InactiveUsersReport.servlet')">Inactive 
	                                         users</a></td>
	                                     </tr>
	                                     <!-- // Excess Work Assignment-->
	                                     <tr height="15"> 
	                                         <td><a class="header_menu" href="javascript:new_window('ReportSLAdmin.getExcessWaAccess')">Excessive 
	                                         WA access</a></td>
	                                     </tr>
	                                     <!-- // Work Assignment Accessed by More than 1 users-->
	                                     <tr height="15"> 
	                                         <td><a class="header_menu" href="javascript:new_window('ReportSLAdmin.getWaAccessMTOneUser')"> 
	                                         WA accessed by > 1 users</a></td>
	                                     </tr>
	                                 </table>
	                             </div>
	                         </td>
	                         <% }%>
	                         <!-- // System Code Maintenance-->
	                         <%  if (secCtx.isPrivilegedFunction("SYSCMAINT")) {
	                             layerOffset += 130; //add width of fifth cell%>
	                         <td width="220" 
	                             onMouseOver="MM_showHideLayers('LayerSysM','','show')"
	                             onMouseOut="MM_showHideLayers('LayerSysM','','hide')" nowrap
	                             ><a class="header_menu">System Codes Maintenance</a> 
	                             <!-- // System Code Maintenance sub menu-->
	                             <div id="LayerSysM" style="position:absolute; width:150px; height:200px; z-index:1; visibility: hidden; left: <%=layerOffset%>px; top: <%=layerTop%>px"
	                                 onMouseOver="MM_showHideLayers('LayerSysM','','show')" 
	                                 onMouseOut="MM_showHideLayers('LayerSysM','','hide')"> 
	                                 <table width="100%" border="1" cellpadding="5" bordercolor="#A8AEFD" bgcolor="#FFFFFF" cellspacing="0">
	                                     <!-- // Contractor Code maintenance-->
	                                     <%// if (secCtx.isPrivilegedFunction("CONMAINT")) {%>
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="ContractorList.jsp">Contractor</a></td>
	                                     </tr>
	                                     <%// }%>
	                                     <!-- // Department Code maintenance-->
	                                     <%// if (secCtx.isPrivilegedFunction("DEPTMAINT")) {%>
	                                     <tr height="15"> 
	                                         <td> <a class="header_menu" href="DeptList.jsp">Department</a></td>
	                                     </tr>
	                                     <%// }%>
	                                 </table>
	                             </div>
	                         </td>
	                         <% }%>
	                         <!-- // Switch to User Interface-->
	                         <% if (secCtx.isPrivilegedFunction("SWITCHUSER")){
	                                     layerOffset += 220; //add width of fifth cell%>
	                         <td width="150" nowrap><a class="header_menu" href="SwitchUser.jsp">User Interface</a>
	                         </td>
	                         <%}%>			
	                         <td valign="middle">&nbsp; </td>
	                         <td width="60" nowrap valign="middle" align="right"> <a class="header_menu" href="#" OnClick="headerform.submit()">Log Out</a> 
	                         </td>
	                     </tr>
	                 </table>
	             </td>
	             
	             <td background="../images/mright.jpg" width="33" height="38">&nbsp;</td>
	         </tr>
	     </table>	
	</form>

 
<script>
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
