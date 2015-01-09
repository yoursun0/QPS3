<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.LogInfo,
				 qpses.business.LogDataBean,
  		 		java.io.BufferedInputStream"%>
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    
// initialize variable    
    java.sql.Blob pdf_file = null;
    int log_id;

 // get parameters    
    log_id = Integer.parseInt(request.getParameter("orgKey1"));       
    
// get file
    LogInfo srvLog = new LogInfo();
    LogDataBean logDB =new LogDataBean();
    srvLog = logDB.selectSRVLogFile(log_id);

    
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
    <form name="form1" action="">
     
    <p>Please wait ...</p>

<%
      if (srvLog!=null){        
          pdf_file = srvLog.getStaffRateValidationReport();

       response.setContentType("application/pdf");
       response.setHeader("Content-Disposition", "inline");
       BufferedInputStream in   = new BufferedInputStream(pdf_file.getBinaryStream());
       byte[] fileBytes         = pdf_file.getBytes(1, (int)pdf_file.length());
       ServletOutputStream outs = response.getOutputStream();
       outs.write(fileBytes);
       outs.flush();
       outs.close();
      }else{
            out.write("File open error!");
      }
    
%> 
    </form>
    </body>
</html>
