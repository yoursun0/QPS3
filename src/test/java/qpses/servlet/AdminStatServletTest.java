package qpses.servlet;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import qpses.business.CPARInfo;
import qpses.business.StatInfo;
import qpses.business.WAChallengeInfo;
import qpses.security.SecurityContext;
import qpses.security.SecurityDataBean;
import qpses.security.SecurityServlet;
import qpses.security.UserStatus;
import qpses.util.Constant;
import qpses.util.SysException;
import qpses.util.SysManager;

public class AdminStatServletTest {

    private AdminStatServlet servlet;
    private IMocksControl control;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SecurityContext secCtx;
    private HttpSession session;
    private UserStatus uStatus;
    
	@Before
	public void setUp(){
    	control = createControl();
        request = control.createMock(HttpServletRequest.class);
        response = control.createMock(HttpServletResponse.class);
        session = control.createMock(HttpSession.class);
        uStatus = new UserStatus();
        secCtx = new SecurityContext();
	}

	@After
	public void tearDown(){
    	servlet = null;
    	control = null;
    	request = null;
    	response = null;
    	secCtx = null;
    	session = null;
	}

	@Test
	public void testStatSearch() throws ServletException, IOException {
		servlet = new TestForAdminStatServlet();
		
		secCtx.setUserId("junit.test");
		secCtx.setDPDeptId("ogcio");
		secCtx.setSOADeptId("OGCIO");
		
        expect(request.getServletPath()).andReturn("/qpsadmin/StatSearch.servlet").times(0,99);
        expect(request.getSession()).andReturn(session).times(0,99);
        expect(request.getHeader("referer")).andReturn("a.jsp").times(0,99);
        expect(request.getParameter(contains("Date"))).andReturn("11-May-2013").times(0,99);
        
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx);
        
        // expect(request.getRequestDispatcher("../logout.jsp")).andReturn(dispatcher);
        
        session.removeAttribute(isA(String.class));
        expectLastCall().times(0,99);
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().times(0,5);
        session.setAttribute(isA(String.class),isA(HashMap.class));
        expectLastCall().times(0,5);
        response.sendRedirect("Statistics.jsp");
        expectLastCall().times(0,2);
        control.replay();
    	
        servlet.doGet(request, response);
	}
	
	@Test
	public void testStatSearchReset() throws ServletException, IOException {
		servlet = new AdminStatServlet();
		
		secCtx.setUserId("junit.test");
		secCtx.setDPDeptId("ogcio");
		secCtx.setSOADeptId("OGCIO");
		
        expect(request.getServletPath()).andReturn("/qpsadmin/StatSearchReset.servlet").times(0,99);
        expect(request.getSession()).andReturn(session).times(0,99);
        expect(request.getHeader("referer")).andReturn("a.jsp").times(0,99);
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx);
        
        // expect(request.getRequestDispatcher("../logout.jsp")).andReturn(dispatcher);
        
        session.removeAttribute(isA(String.class));
        expectLastCall().times(0,99);
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().times(0,2);
        response.sendRedirect("a.jsp");
        expectLastCall().times(0,2);
        session.invalidate();
        control.replay();
    	
        servlet.doGet(request, response);
	}
	

	@Test(expected=javax.servlet.ServletException.class)
	public void testPerformStatDownloadReport() throws ServletException, IOException {
		servlet = new AdminStatServlet();
		StatInfo statValue = new StatInfo();
		statValue.setInvitationNo("1");
		statValue.setAwardedContractNo("1");
		statValue.setAwardedTotalValue("1");
		statValue.setContractTotalValue("1");
		statValue.setChangeTotalValue("1");
		statValue.setInprogressContractNo("1");
		statValue.setCompletedContractNo("1");
		statValue.setCancelledInvitationNo("1");
		statValue.setReceivedCPARNo("1");
		
		statValue.setCat1AwardedContractNo("1");
		statValue.setCat1InprogressContractNo("1");
		statValue.setCat1ChangeTotalValue("1");
		statValue.setCat1CompletedContractNo("1");
		statValue.setCat1ContractTotalValue("1");

		statValue.setCat2MajorAwardedContractNo("1");
		statValue.setCat2MajorInprogressContractNo("1");
		statValue.setCat2MajorChangeTotalValue("1");
		statValue.setCat2MajorCompletedContractNo("1");
		statValue.setCat2MajorContractTotalValue("1");
		
		statValue.setCat2MinorAwardedContractNo("1");
		statValue.setCat2MinorInprogressContractNo("1");
		statValue.setCat2MinorChangeTotalValue("1");
		statValue.setCat2MinorCompletedContractNo("1");
		statValue.setCat2MinorContractTotalValue("1");
		
		statValue.setCat3MinorAwardedContractNo("1");
		statValue.setCat3MinorInprogressContractNo("1");
		statValue.setCat3MinorChangeTotalValue("1");
		statValue.setCat3MinorCompletedContractNo("1");
		statValue.setCat3MinorContractTotalValue("1");
		
		statValue.setCat3MajorAwardedContractNo("1");
		statValue.setCat3MajorInprogressContractNo("1");
		statValue.setCat3MajorChangeTotalValue("1");
		statValue.setCat3MajorCompletedContractNo("1");
		statValue.setCat3MajorContractTotalValue("1");
		
		statValue.setCat4AwardedContractNo("1");
		statValue.setCat4InprogressContractNo("1");
		statValue.setCat4ChangeTotalValue("1");
		statValue.setCat4CompletedContractNo("1");
		statValue.setCat4ContractTotalValue("1");
		
		List searchParaDates = new ArrayList();
		searchParaDates.add(null);
		searchParaDates.add(null);
		searchParaDates.add(null);
		searchParaDates.add(null);
		HashMap searchParaHashMap = new HashMap();
		searchParaHashMap.put("DateFilters", searchParaDates);
		searchParaHashMap.put("Stat",statValue);
		
        expect(request.getServletPath()).andReturn("/qpsadmin/StatDownloadReport.servlet").times(0,99);
        expect(request.getSession()).andReturn(session).times(0,99);
        expect(request.getHeader("referer")).andReturn("a.jsp").times(0,99);
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).times(0,99);
        expect(session.getAttribute("STAT_REPORT_PARAMETER")).andReturn(searchParaHashMap).times(0,99);
        
        // expect(request.getRequestDispatcher("../logout.jsp")).andReturn(dispatcher);
        
        session.removeAttribute(isA(String.class));
        expectLastCall().times(0,99);
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().times(0,2);
        response.sendRedirect("a.jsp");
        expectLastCall().times(0,2);
        session.invalidate();
        control.replay();
    	
        servlet.doGet(request, response);
	}
	
}
