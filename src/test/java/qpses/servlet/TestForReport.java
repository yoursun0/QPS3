package qpses.servlet;

import static org.easymock.EasyMock.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

import org.junit.Test;

import qpses.business.ContractorDataBean;
import qpses.business.DeptDataBean;
import qpses.business.ReportDBAdmin;
import qpses.business.WorkAssignmentInfo;
import qpses.security.UserStatus;
import qpses.util.SysException;

public final class TestForReport extends ReportSLAdmin {

	private ServletContext servCtx; 
	private DeptDataBean deptDB; 
	private ContractorDataBean conDB;
	private RequestDispatcher dispatcher;
	private ReportDBAdmin dbUser;
	protected UserStatus uStatus;
	
	@Override
	public ServletContext getServletContext(){
		try{
			InputStream is = new FileInputStream("pom.xml");
			dispatcher = createMock(RequestDispatcher.class);
			servCtx = createMock(ServletContext.class);
			expect(servCtx.getRequestDispatcher(isA(String.class))).andReturn(dispatcher);
			dispatcher.forward(isA(HttpServletRequest.class), isA(HttpServletResponse.class));
			expectLastCall().times(0,10);
			expect(servCtx.getResourceAsStream(isA(String.class))).andReturn(is).times(0,9);
		
			replay(servCtx);
			replay(dispatcher);
		}catch(ServletException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return servCtx;
	}
	
    @Override
	public ReportDBAdmin getReportDBAdmin() throws SysException, SerialException, SQLException{
    	List<String> strList = new ArrayList<String>() ;
    	for(int i=0;i<56;i++){
    		strList.add("a,b");
    	}
/*    	
    	List<WorkAssignmentInfo> infoList = new ArrayList<WorkAssignmentInfo>() ;
    	WorkAssignmentInfo aWAInfo = new WorkAssignmentInfo();
    	for(int i=0;i<10;i++){
    		infoList.add(aWAInfo);
    	}    	*/
		// Pass a Mock DataBean
		dbUser = createMock(ReportDBAdmin.class);
		
		expect(dbUser.getFunctionAccess()).andReturn(strList).anyTimes();
		expect(dbUser.getWaAccessMTOneUser()).andReturn(strList).anyTimes();
		expect(dbUser.getExcessWaAccess()).andReturn(strList).anyTimes();
		replay(dbUser);
        return dbUser;
    }
    
    @Test
    public void test() throws SysException, SerialException, SQLException{
    	ReportDBAdmin db = getReportDBAdmin();
    	db = null;
    }
    
}