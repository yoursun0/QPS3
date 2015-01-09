package qpses.servlet;

import static org.easymock.EasyMock.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;

import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import qpses.business.AvgCeilingRateInfo;
import qpses.business.WAChallengeInfo;
import qpses.security.SecurityContext;
import qpses.security.TestLogoutForSecurityServlet;

public class ReportSLAdminTest {

    private IMocksControl control;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SecurityContext secCtx;
    private HttpSession session;
    private ReportSLAdmin slUser;
    private RequestDispatcher dispatcher;
    private ServletOutputStream sos;
    private WAChallengeInfo wac;
    
	@Before
	public void setUp(){
    	control = createControl();
        request = control.createMock(HttpServletRequest.class);
        response = control.createMock(HttpServletResponse.class);
        session = control.createMock(HttpSession.class);
        dispatcher = control.createMock(RequestDispatcher.class);
        sos =  control.createMock(ServletOutputStream.class);
        secCtx = new SecurityContext();
        secCtx.setUserId("junit.test");
        secCtx.setDPDeptId("ogcio");
        secCtx.setSOADeptId("OGCIO");
        secCtx.setUserDPDeptId("ogcio");
        secCtx.setUserSOADeptId("OGCIO");
        secCtx.setEmail("helic.leung@hkmci.com");
	}

	@After
	public void tearDown(){
    	control = null;
    	request = null;
    	response = null;
    	secCtx = null;
    	session = null;
    	dispatcher = null;
    	wac = null;
	}

	@Test
	public void testGetExcessWaAccess() throws ServletException, IOException {
		new TestLogoutForSecurityServlet();
		slUser = new TestForReport();

        expect(request.getServletPath()).andReturn("/qpsadmin/ReportSLAdmin.getExcessWaAccess").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        expect(response.getOutputStream()).andReturn(sos).anyTimes();
        		
        sos.write(isA(byte[].class),lt(10),gt(-1));
        expectLastCall().anyTimes();
        sos.flush();
        expectLastCall().anyTimes();
        response.setContentType(isA(String.class));
        expectLastCall().anyTimes();
        response.setContentLength(gt(-1));
        expectLastCall().anyTimes();
        response.setHeader(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        dispatcher.forward(request, response);
        expectLastCall().anyTimes();
        session.removeAttribute(isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(List.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(AvgCeilingRateInfo.class));
        expectLastCall().anyTimes();
        control.replay();
    	
        slUser.doGet(request, response);
	}
	

	@Test
	public void testGetWaAccessMTOneUser() throws ServletException, IOException {
		new TestLogoutForSecurityServlet();
		slUser = new TestForReport();

        expect(request.getServletPath()).andReturn("/qpsadmin/ReportSLAdmin.getWaAccessMTOneUser").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        expect(response.getOutputStream()).andReturn(sos).anyTimes();
        		
        sos.write(isA(byte[].class),lt(10),gt(-1));
        expectLastCall().anyTimes();
        sos.flush();
        expectLastCall().anyTimes();
        response.setContentType(isA(String.class));
        expectLastCall().anyTimes();
        response.setContentLength(gt(-1));
        expectLastCall().anyTimes();
        response.setHeader(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        dispatcher.forward(request, response);
        expectLastCall().anyTimes();
        session.removeAttribute(isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(List.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(AvgCeilingRateInfo.class));
        expectLastCall().anyTimes();
        control.replay();
    	
        slUser.doGet(request, response);
	}

	@Test
	public void testGetFunctionAccess() throws ServletException, IOException {
		new TestLogoutForSecurityServlet();
		slUser = new TestForReport();

        expect(request.getServletPath()).andReturn("/qpsadmin/ReportSLAdmin.getFunctionAccess").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        expect(response.getOutputStream()).andReturn(sos).anyTimes();
        		
        sos.write(isA(byte[].class),lt(10),gt(-1));
        expectLastCall().anyTimes();
        sos.flush();
        expectLastCall().anyTimes();
        response.setContentType(isA(String.class));
        expectLastCall().anyTimes();
        response.setContentLength(gt(-1));
        expectLastCall().anyTimes();
        response.setHeader(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        dispatcher.forward(request, response);
        expectLastCall().anyTimes();
        session.removeAttribute(isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(String.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(List.class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(AvgCeilingRateInfo.class));
        expectLastCall().anyTimes();
        control.replay();
    	
        slUser.doGet(request, response);
	}
}
