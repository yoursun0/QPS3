package qpses.servlet;

import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import qpses.business.CPARInfo;
import qpses.business.WAChallengeInfo;
import qpses.security.SecurityContext;
import qpses.security.TestLogoutForSecurityServlet;
import qpses.util.Constant;
import qpses.util.SysException;
import qpses.util.SysManager;

public class CPARSLUserTest {

    private IMocksControl control;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SecurityContext secCtx;
    private HttpSession session;
    private CPARSLUser slUser;
    
	@Before
	public void setUp(){
    	control = createControl();
        request = control.createMock(HttpServletRequest.class);
        response = control.createMock(HttpServletResponse.class);
        session = control.createMock(HttpSession.class);
        secCtx = new SecurityContext();
	}

	@After
	public void tearDown(){
    	control = null;
    	request = null;
    	response = null;
    	secCtx = null;
    	session = null;
	}

	@Test
	public void testSearchCPAR() throws ServletException, IOException {
		new TestLogoutForSecurityServlet();
		slUser = new TestCRUDForCPARSLUser();
		
		secCtx.setUserId("junit.test");
		secCtx.setDPDeptId("ogcio");
		secCtx.setSOADeptId("OGCIO");
		
        expect(request.getServletPath()).andReturn(CPARSLUser.UserBaseUrl + "CPARSLUser");
        expect(request.getSession()).andReturn(session).times(5);
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx);
        
        // expect(request.getRequestDispatcher("../logout.jsp")).andReturn(dispatcher);
        
        session.removeAttribute(isA(String.class));
        expectLastCall().times(0,2);
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().times(0,2);
        session.invalidate();
        control.replay();
    	
        slUser.doGet(request, response);
	}
	
	@Test
	public void testListCPAR() throws ServletException, IOException {
	
		WAChallengeInfo wac = new WAChallengeInfo();
		slUser = new TestCRUDForCPARSLUser();
		
        expect(request.getServletPath()).andReturn(CPARSLUser.UserBaseUrl + "CPARList.servlet");
        expect(request.getSession()).andReturn(session).times(0,5);
        expect(session.getAttribute("WA_TYPE")).andReturn("acpar");
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac);
        session.setAttribute(EasyMock.contains("CPAR_List"),EasyMock.isA(List.class));
        expectLastCall().times(0,2);
        
        control.replay();
    	
        slUser.doGet(request, response);
	}

	@Test
	public void testCreateCPAR() throws ServletException, IOException {
	
		WAChallengeInfo wac = new WAChallengeInfo();
		wac.setServiceCategoryGroup("1");
		wac.setServiceContractNo("1-C1-012");
		wac.setFilePartNo("I");
		wac.setFileNo(1);
		wac.setDepartmentId("OGCIO");
		
		slUser = new TestCRUDForCPARSLUser();
		
        expect(request.getServletPath()).andReturn(CPARSLUser.UserBaseUrl + "CPARCreate.servlet");
        expect(request.getSession()).andReturn(session).times(0,5);
        expect(session.getAttribute("WA_TYPE")).andReturn("acpar");
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac);
        
        session.setAttribute("CPAR_MSGTYPE","CREATE");
        expectLastCall().times(0,2);
        session.setAttribute(EasyMock.contains("QPSES_CPAR"),EasyMock.isA(CPARInfo.class));
        expectLastCall().times(0,2);
        session.setAttribute(EasyMock.contains("CPAR_NEXTDATE"),EasyMock.contains("2012"));
        expectLastCall().times(0,2);
        control.replay();
    	
        slUser.doGet(request, response);
	}
	
	@Test
	public void testUpdateCPAR() throws ServletException, IOException {
	
		WAChallengeInfo wac = new WAChallengeInfo();
		slUser = new TestCRUDForCPARSLUser();
		
		expect(request.getParameter("cparNo")).andReturn("23");
        expect(request.getServletPath()).andReturn(CPARSLUser.UserBaseUrl + "CPARUpdate.servlet");
        expect(request.getSession()).andReturn(session).times(0,5);
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac);
        
        session.setAttribute("CPAR_MSGTYPE","UPDATE");
        expectLastCall().times(0,2);
        session.setAttribute(EasyMock.contains("QPSES_CPAR"),EasyMock.isA(CPARInfo.class));
        expectLastCall().times(0,2);
        session.setAttribute(EasyMock.contains("CPAR_NEXTDATE"),EasyMock.isA(String.class));
        expectLastCall().times(0,2);
        
        control.replay();
    	
        slUser.doGet(request, response);
	}

	@Test
	public void testSaveCPAR() throws ServletException, IOException, SysException {
	
		CPARInfo cpar = new CPARInfo();
		cpar.setStartDate(SysManager.getCurDateTime());
		cpar.setFinalized("f");
		
		WAChallengeInfo wac = new WAChallengeInfo();
		secCtx = new SecurityContext();
		slUser = new TestCRUDForCPARSLUser();
		
		expect(request.getParameter("IssuedDate")).andReturn("31-July-2013");
		expect(request.getParameter("ClosingDate")).andReturn("31-July-2015");
		expect(request.getParameter(isA(String.class))).andReturn("1").times(0,120);
        expect(request.getServletPath()).andReturn(CPARSLUser.UserBaseUrl + "CPARSave.servlet");
        expect(request.getSession()).andReturn(session).times(0,15);
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac);
        expect(session.getAttribute("QPSES_CPAR")).andReturn(cpar);
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx);
        expect(session.getAttribute("CPAR_MSGTYPE")).andReturn("CREATE");
        
        session.setAttribute("CPAR_MSG","The CPAR is saved successfully.");
        session.setAttribute("CPAR_MSGTYPE","SUCCESS");
        session.setAttribute(EasyMock.contains("CPAR_List"),EasyMock.isA(List.class));
        expectLastCall().times(0,2);
        session.setAttribute("WA_TYPE","cpar");
        
        control.replay();
    	
        slUser.doGet(request, response);
	}
	
	@Test
	public void testDeleteCPAR() throws ServletException, IOException {
		slUser = new TestCRUDForCPARSLUser();
		
        expect(request.getServletPath()).andReturn(CPARSLUser.UserBaseUrl + "CPARDelete.servlet");
        expect(request.getSession()).andReturn(session).times(0,5);
        expect(request.getParameter("cparNo")).andReturn("23");

        control.replay();
    	
        slUser.doGet(request, response);
	}

	@Test
	public void testConfirmDeleteCPAR() throws ServletException, IOException, SysException {
	
		CPARInfo cpar = new CPARInfo();
		cpar.setStartDate(SysManager.getCurDateTime());
		cpar.setFinalized("f");
		
		WAChallengeInfo wac = new WAChallengeInfo();
		secCtx = new SecurityContext();
		slUser = new TestCRUDForCPARSLUser();
		
		expect(request.getParameter("cparNo")).andReturn("1");
		expect(request.getParameter("ClosingDate")).andReturn("31-July-2015");
		expect(request.getParameter(isA(String.class))).andReturn("1").times(0,120);
        expect(request.getServletPath()).andReturn(CPARSLUser.UserBaseUrl + "CPARConfirmDelete.servlet");
        expect(request.getSession()).andReturn(session).times(0,15);
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac);
        expect(session.getAttribute("QPSES_CPAR")).andReturn(cpar);
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx);
        expect(session.getAttribute("CPAR_MSGTYPE")).andReturn("CREATE");
        
        session.setAttribute(EasyMock.contains("CPAR_MSG"),EasyMock.contains("successfully"));
        session.setAttribute("CPAR_MSGTYPE","LIST");
        
        control.replay();
    	
        slUser.doGet(request, response);
	}
	

	@Test(expected=javax.servlet.ServletException.class)
	public void testPrintCPAR() throws ServletException, IOException, SysException {
	
		CPARInfo cpar = new CPARInfo();
		cpar.setCreatedDate("2012-12-12 12:12:12.2");
		cpar.setLastUpdatedDate("2032-12-12 12:12:12.2");
		cpar.setFinalized("f");
		
		OutputStream os = new FileOutputStream("abc.txt");
		
		WAChallengeInfo wac = new WAChallengeInfo();
		secCtx = new SecurityContext();
		slUser = new TestCRUDForCPARSLUser();
		
		expect(request.getParameter("cparNo")).andReturn("1");
		expect(request.getParameter("ClosingDate")).andReturn("31-July-2015");
		expect(request.getParameter(isA(String.class))).andReturn("1").times(0,120);
        expect(request.getServletPath()).andReturn(CPARSLUser.UserBaseUrl + "CPARPrint.servlet");
        expect(request.getSession()).andReturn(session).times(0,15);
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac);
        expect(session.getAttribute("QPSES_CPAR")).andReturn(cpar);
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx);
        expect(session.getAttribute("CPAR_MSGTYPE")).andReturn("CREATE");
        
        session.setAttribute("WA_TYPE","cpar");
        session.setAttribute(EasyMock.contains("CPAR_MSG"),EasyMock.contains("successfully"));
        session.setAttribute("CPAR_MSGTYPE","LIST");
        
        response.setContentType("application/msword");
		response.setHeader("Content-Disposition", "inline;filename=\""+ Constant.CPAR_TEMPLATE_NAME);
		response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        
        control.replay();
    	
        slUser.doGet(request, response);
	}
}
