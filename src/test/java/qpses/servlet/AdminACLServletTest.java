package qpses.servlet;

import static org.easymock.EasyMock.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import qpses.business.ACLInfo;
import qpses.business.CeilingRateInfo;
import qpses.security.SecurityContext;
import qpses.util.SysException;
import qpses.util.SysManager;

public class AdminACLServletTest {

    private AdminACLServlet servlet;
    private IMocksControl control;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SecurityContext secCtx;
    private HttpSession session;
    private ServletOutputStream sos;
    private ServletInputStream sis;
    private RequestDispatcher dispatcher;
    private String category;
    
	@Before
	public void setUp(){
    	control = createControl();
        request = control.createMock(HttpServletRequest.class);
        response = control.createMock(HttpServletResponse.class);
        session = control.createMock(HttpSession.class);
        sos = control.createMock(ServletOutputStream.class);
        sis = control.createMock(ServletInputStream.class);
        dispatcher = control.createMock(RequestDispatcher.class);
        secCtx = new SecurityContext();
        servlet = new TestForAdminACLServlet();
		secCtx.setUserId("junit.test");
		secCtx.setDPDeptId("ogcio");
		secCtx.setSOADeptId("OGCIO");
	}

	@After
	public void tearDown(){
    	servlet = null;
    	control = null;
    	request = null;
    	response = null;
    	secCtx = null;
    	session = null;
    	sos = null;
    	sis = null;
    	dispatcher = null;
	}

	@Test
	public void testPerformACLAdd() throws ServletException, IOException {
		
		expect(request.getServletPath()).andReturn("/qpsadmin/ACLAdd.servlet").anyTimes();
		expect(request.getParameter("UserId")).andReturn("junit.test").anyTimes();
		expect(request.getParameter("DeptId")).andReturn("ogcio+OGCIO").anyTimes();
		expect(request.getParameter("EffectiveDate")).andReturn("11-May-2013").anyTimes();
		expect(request.getParameter("ExpiryDate")).andReturn("11-May-2015").anyTimes();
		expect(request.getParameter("EmailInd")).andReturn("-1").anyTimes();
		expect(request.getParameter("UserRole")).andReturn("U").anyTimes();
		expect(request.getParameter("Password")).andReturn("password").anyTimes();
		expect(request.getParameter(isA(String.class))).andReturn("1").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("WA_TYPE")).andReturn("proposal").anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        
        response.sendRedirect(isA(String.class));
        expectLastCall().anyTimes();
        session.removeAttribute(isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(ACLInfo.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(List.class));
        expectLastCall().anyTimes();
        dispatcher.forward(request, response);
        expectLastCall().anyTimes();
        response.setContentType(isA(String.class));
        expectLastCall().anyTimes();
        response.setContentLength(lt(100));
        expectLastCall().anyTimes();
        response.setHeader(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        control.replay();
    	
        servlet.doGet(request, response);
	}
	
	@Test
	public void testPerformACLUpdate() throws ServletException, IOException {
		
		expect(request.getServletPath()).andReturn("/qpsadmin/ACLUpdate.servlet").anyTimes();
		expect(request.getParameter("UserId")).andReturn("junit.test").anyTimes();
		expect(request.getParameter("DeptId")).andReturn("ogcio+OGCIO").anyTimes();
		expect(request.getParameter("EffectiveDate")).andReturn("11-May-2013").anyTimes();
		expect(request.getParameter("ExpiryDate")).andReturn("11-May-2015").anyTimes();
		expect(request.getParameter("EmailInd")).andReturn("-1").anyTimes();
		expect(request.getParameter("UserRole")).andReturn("U").anyTimes();
		expect(request.getParameter("Password")).andReturn("password").anyTimes();
		expect(request.getParameter(isA(String.class))).andReturn("1").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("WA_TYPE")).andReturn("proposal").anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        
        response.sendRedirect(isA(String.class));
        expectLastCall().anyTimes();
        session.removeAttribute(isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(ACLInfo.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(HashMap.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(List.class));
        expectLastCall().anyTimes();
        dispatcher.forward(request, response);
        expectLastCall().anyTimes();
        response.setContentType(isA(String.class));
        expectLastCall().anyTimes();
        response.setContentLength(lt(100));
        expectLastCall().anyTimes();
        response.setHeader(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        control.replay();
    	
        servlet.doGet(request, response);
	}
	
	@Test
	public void testPerformACLSearch() throws ServletException, IOException {
		
		expect(request.getServletPath()).andReturn("/qpsadmin/ACLSearch.servlet").anyTimes();
		expect(request.getParameter("UserId")).andReturn("junit.test").anyTimes();
		expect(request.getParameter("DeptId")).andReturn("ogcio+OGCIO").anyTimes();
		expect(request.getParameter("EffectiveDate")).andReturn("11-May-2013").anyTimes();
		expect(request.getParameter("ExpiryDate")).andReturn("11-May-2015").anyTimes();
		expect(request.getParameter("EmailInd")).andReturn("-1").anyTimes();
		expect(request.getParameter("UserRole")).andReturn("U").anyTimes();
		expect(request.getParameter("Password")).andReturn("password").anyTimes();
		expect(request.getParameter(isA(String.class))).andReturn("1").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("WA_TYPE")).andReturn("proposal").anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        
        response.sendRedirect(isA(String.class));
        expectLastCall().anyTimes();
        session.removeAttribute(isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(ACLInfo.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(HashMap.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(List.class));
        expectLastCall().anyTimes();
        dispatcher.forward(request, response);
        expectLastCall().anyTimes();
        response.setContentType(isA(String.class));
        expectLastCall().anyTimes();
        response.setContentLength(lt(100));
        expectLastCall().anyTimes();
        response.setHeader(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        control.replay();
    	
        servlet.doGet(request, response);
	}
	

	@Test
	public void testPerformACLSearchReset() throws ServletException, IOException {
		
		expect(request.getServletPath()).andReturn("/qpsadmin/ACLSearchReset.servlet").anyTimes();
		expect(request.getHeader("referer")).andReturn("junit.test").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("WA_TYPE")).andReturn("proposal").anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        
        response.sendRedirect(isA(String.class));
        expectLastCall().anyTimes();
        session.removeAttribute(isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(ACLInfo.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(HashMap.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(List.class));
        expectLastCall().anyTimes();
        control.replay();
    	
        servlet.doGet(request, response);
	}
	

	@Test
	public void testPerformUnlockUser() throws ServletException, IOException {

		expect(request.getServletPath()).andReturn("/qpsadmin/UnlockUser.servlet").anyTimes();
		expect(request.getParameter("UserId")).andReturn("junit.test").anyTimes();
		expect(request.getParameter("DeptId")).andReturn("ogcio+OGCIO").anyTimes();
		expect(request.getParameter("EffectiveDate")).andReturn("11-May-2013").anyTimes();
		expect(request.getParameter("ExpiryDate")).andReturn("11-May-2015").anyTimes();
		expect(request.getParameter("EmailInd")).andReturn("-1").anyTimes();
		expect(request.getParameter("UserRole")).andReturn("U").anyTimes();
		expect(request.getParameter("Password")).andReturn("password").anyTimes();
		expect(request.getParameter(isA(String.class))).andReturn("1").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("WA_TYPE")).andReturn("proposal").anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        
        response.sendRedirect(isA(String.class));
        expectLastCall().anyTimes();
        session.removeAttribute(isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(ACLInfo.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(HashMap.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(List.class));
        expectLastCall().anyTimes();
        dispatcher.forward(request, response);
        expectLastCall().anyTimes();
        response.setContentType(isA(String.class));
        expectLastCall().anyTimes();
        response.setContentLength(lt(100));
        expectLastCall().anyTimes();
        response.setHeader(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        control.replay();
    	
        servlet.doGet(request, response);
	}
	

	@Test
	public void testPerformUnlockSingleUser() throws ServletException, IOException {

		expect(request.getServletPath()).andReturn("/qpsadmin/UnlockSingleUser.servlet").anyTimes();
		expect(request.getParameter("UserId")).andReturn("junit.test").anyTimes();
		expect(request.getParameter("DeptId")).andReturn("ogcio+OGCIO").anyTimes();
		expect(request.getParameter("EffectiveDate")).andReturn("11-May-2013").anyTimes();
		expect(request.getParameter("ExpiryDate")).andReturn("11-May-2015").anyTimes();
		expect(request.getParameter("EmailInd")).andReturn("-1").anyTimes();
		expect(request.getParameter("UserRole")).andReturn("U").anyTimes();
		expect(request.getParameter("Password")).andReturn("password").anyTimes();
		expect(request.getParameter(isA(String.class))).andReturn("1").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("WA_TYPE")).andReturn("proposal").anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        
        response.sendRedirect(isA(String.class));
        expectLastCall().anyTimes();
        session.removeAttribute(isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(ACLInfo.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(HashMap.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(List.class));
        expectLastCall().anyTimes();
        dispatcher.forward(request, response);
        expectLastCall().anyTimes();
        response.setContentType(isA(String.class));
        expectLastCall().anyTimes();
        response.setContentLength(lt(100));
        expectLastCall().anyTimes();
        response.setHeader(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        control.replay();
    	
        servlet.doGet(request, response);
	}
	
}
