package qpses.servlet;

import static org.easymock.EasyMock.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import qpses.business.AvgCeilingRateInfo;
import qpses.business.CPARInfo;
import qpses.business.WAChallengeInfo;
import qpses.security.SecurityContext;
import qpses.security.TestLogoutForSecurityServlet;
import qpses.util.Constant;
import qpses.util.SysException;
import qpses.util.SysManager;

@RunWith(value = Parameterized.class)
public class QualitySubscoreSLUserTest {

    private IMocksControl control;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SecurityContext secCtx;
    private HttpSession session;
    private QualitySubscoreSLUser slUser;
    private RequestDispatcher dispatcher;
    private ServletOutputStream sos;
    private WAChallengeInfo wac;
    private String category;
    
    public QualitySubscoreSLUserTest(String category) {
	    this.category = category;
	 }
    
    @Parameters
	 public static List<Object[]> data() {
	   Object[][] data = new Object[][] { { "1" }, { "2/N" }, { "2/J" }, { "3/N" }, { "3/J" }, { "4" } };
	   return Arrays.asList(data);
	 }
    
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
        wac = new WAChallengeInfo();
        wac.setServiceCategoryGroup(category);
        wac.setDepartmentId("OGCIO");
        wac.setFilePartNo("I");
        wac.setFileNo(1);
        wac.setTitle("Agile");
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
	public void testGetWA() throws ServletException, IOException {
		new TestLogoutForSecurityServlet();
		slUser = new TestForQualitySubscore();

        expect(request.getParameter("request_action")).andReturn("").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac).anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        
        response.setContentType(isA(String.class));
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
	public void testGetWAChallenge() throws ServletException, IOException {
		new TestLogoutForSecurityServlet();
		slUser = new TestForQualitySubscore();

        expect(request.getParameter("request_action")).andReturn("waChallenge").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac).anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        
        response.setContentType(isA(String.class));
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
	public void testShowImagePage() throws ServletException, IOException, SerialException, SQLException {
		new TestLogoutForSecurityServlet();
		slUser = new TestForQualitySubscore();
		secCtx.setUserId("junit.test");
		secCtx.setDPDeptId("ogcio");
		secCtx.setSOADeptId("OGCIO");
		
        expect(request.getParameter("request_action")).andReturn("showImagePage").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac).anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        expect(request.getParameter("effective_date")).andReturn("11-May-2013").anyTimes();
        expect(request.getParameter("scg")).andReturn("1").anyTimes();
        
        
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        
        response.setContentType(isA(String.class));
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
        session.setAttribute(isA(String.class),isA(byte[].class));
        expectLastCall().anyTimes();
        session.setAttribute(isA(String.class),isA(AvgCeilingRateInfo.class));
        expectLastCall().anyTimes();
        control.replay();
    	
        slUser.doGet(request, response);
	}
	
	
	@Test(expected=java.lang.AssertionError.class)
	public void testGetImage() throws ServletException, IOException, SerialException, SQLException {
		new TestLogoutForSecurityServlet();
		slUser = new TestForQualitySubscore();
		secCtx.setUserId("junit.test");
		secCtx.setDPDeptId("ogcio");
		secCtx.setSOADeptId("OGCIO");
		
        expect(request.getParameter("request_action")).andReturn("getImage").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSES_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac).anyTimes();
        expect(session.getAttribute("QPSES_QS_RPT_FILE_BYTES")).andReturn(AvgCeilingRateSLUserTest.byteArray).anyTimes();
        expect(session.getAttribute("QPSES_QS_RPT_FILE_NAME")).andReturn("a.pdf").anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        expect(request.getParameter("effective_date")).andReturn("11-May-2013").anyTimes();
        expect(request.getParameter("page_no")).andReturn("1").anyTimes();
        expect(request.getParameter("sc")).andReturn("1").anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        expect(response.getOutputStream()).andReturn(sos).anyTimes();
        
        response.setContentType(isA(String.class));
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
        sos.write(isA(byte[].class));
        expectLastCall().anyTimes();
        control.replay();
    	
        slUser.doGet(request, response);
	}
	

	@Test
	public void testGetFile() throws ServletException, IOException, SerialException, SQLException {
		new TestLogoutForSecurityServlet();
		slUser = new TestForQualitySubscore();
		secCtx.setUserId("junit.test");
		secCtx.setDPDeptId("ogcio");
		secCtx.setSOADeptId("OGCIO");
		
        expect(request.getParameter("request_action")).andReturn("getPDF").anyTimes();
        expect(request.getSession()).andReturn(session).anyTimes();
        expect(session.getAttribute("QPSE S_SECURITY_CONTEXT")).andReturn(secCtx).anyTimes();
        expect(session.getAttribute("QPSES_WA_CHALLENGE")).andReturn(wac).anyTimes();
        expect(session.getAttribute("QPSES_QS_RPT_FILE_BYTES")).andReturn(AvgCeilingRateSLUserTest.byteArray).anyTimes();
        expect(session.getAttribute("QPSES_QS_RPT_FILE_NAME")).andReturn("a.pdf").anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        expect(request.getParameter("effective_date")).andReturn("11-May-2013").anyTimes();
        expect(request.getParameter("page_no")).andReturn("1").anyTimes();
        expect(request.getParameter("sc")).andReturn("1").anyTimes();
        expect(request.getRequestDispatcher(isA(String.class))).andReturn(dispatcher).anyTimes();
        expect(response.getOutputStream()).andReturn(sos).anyTimes();
       
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
        sos.write(isA(byte[].class));
        expectLastCall().anyTimes();
        control.replay();
    	
        slUser.doGet(request, response);
	}
	
}
