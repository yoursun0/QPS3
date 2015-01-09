<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter,
               javax.servlet.http.HttpServlet,
               javax.servlet.http.HttpServletRequest,
               javax.servlet.http.HttpServletResponse,
               javax.servlet.ServletException,
               org.json.simple.JSONArray,
               org.json.simple.JSONObject,
               qpses.business.ContractorDataBean,
               qpses.business.ContractorInfo,
               java.util.List,
               java.util.ArrayList" %>

<%

    ContractorDataBean contractorDB =new ContractorDataBean();
    List<ContractorInfo> allContractorList = new ArrayList<ContractorInfo>();

  JSONArray list = new JSONArray();
  JSONObject obj = new JSONObject();
  String catg = request.getParameter("Category");

  allContractorList = contractorDB.selectContractorByCatgp(catg);
  for (ContractorInfo contractor : allContractorList){
    obj = new JSONObject();
    obj.put("id",contractor.getContractorId());
	obj.put("name",contractor.getContractorName());
	list.add(obj);
  }
  System.out.println(list.toJSONString());
  out.write(list.toJSONString());
  out.flush();
%>
