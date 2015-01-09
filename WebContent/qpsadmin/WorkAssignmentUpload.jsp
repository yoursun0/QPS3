<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // check and display for message
    String msg =   getValue((String)  session.getAttribute("WORK_ASSIGNMENT_MSG"));
    if (!msg.equals("")){
        session.removeAttribute("WORK_ASSIGNMENT_MSG");
        session.removeAttribute("WORK_ASSIGNMENT_MSGTYPE");
    }
%>			
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Work Assignment Information Import</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">             
        <script language="JavaScript" src="../js/wacheck.js" type="text/javascript"></script>            		
    </head>
    <body> 
        <%@include file="include/header.jsp"%>        
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Work Assignment Information Maintenance</td></tr></table>            		
            <form name="form1" method="POST" enctype="multipart/form-data" action="WorkAssignmentUpload.servlet">            
                <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" class="tableBorder">
                    <tr> 
                        <td width="24%" class="tabUnSelectLeft" onclick="changepage('List')">Work 
                        Assignment List </td>
                        <td width="18%" class="tabSelectedMiddle">Import</td>
                        <td width="15%" class="tabUnSelectRight" onclick="changepage('Search')">Search</td>
                        <td width="43%"> 
                            <div align="right">&nbsp;</div>
                        </td>
                    </tr>
                </table>					
                    <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" class="tableborder">                    
                    <td class="title1"> 
                        <div align="left">Import Work Assignment Information</div>
                    </td>
                </tr>
                    <tr>                    
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div><BR>":""%> 
                            
        <table width="100%" border="0" cellspacing="0" cellpadding="5">
          <tr> 
            <td> 
              <div align="center"> 
                <table  width="98%" cellspacing="1" class="tableBackground">
                  <tr> 
                    <td width="19%" class="tableVerticalHeaderAlignLeft1">Import 
                      File</td>
                    <td width="81%" class="tableVerticalContentAlignLeft1"> 
                      <input type="file" name="UploadFileName" value="" size="80" class="inputText"  />
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
        </table>
                            <table border="0" cellpadding="3" align="center">
                                <tr> 
                                    <td width="50%"> 
                                    
                                        <div align="center"> <a href="#"><img name="ConfirmImport" src="../images/btn_import.jpg" width="98" height="42" border="0" alt="Import" onClick="selectaction(form1,this)"></a> 
                                        </div>
                                    </td>
                                    <td width="50%">
                                        <div align="center"><a href="#"><img src="../images/btn_cancel.jpg" name="Cancel" width="98" height="42" border="0" alt="Cancel" onClick="selectaction(form1,this)"></a> 
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </form>
        </fieldset> 		    
    </body>
</html>
