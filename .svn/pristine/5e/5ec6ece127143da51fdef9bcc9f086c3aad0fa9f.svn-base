package qpses.servlet;

import static org.easymock.EasyMock.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.junit.Test;

import qpses.business.AvgCeilingRateDBUser;
import qpses.business.AvgCeilingRateInfo;
import qpses.security.UserStatus;
import qpses.util.SysException;

public final class TestForAvgCeilingRate extends AvgCeilingRateSLUser {

	private ServletContext servCtx; 
	private RequestDispatcher dispatcher;
	private AvgCeilingRateDBUser dbUser;
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
	public AvgCeilingRateDBUser getAvgCeilingRateDBUser() throws SysException, SerialException, SQLException{
    	List<AvgCeilingRateInfo> infoList = new ArrayList<AvgCeilingRateInfo>() ;
    	AvgCeilingRateInfo avgCRInfo = new AvgCeilingRateInfo();
		Blob blob = new SerialBlob(AvgCeilingRateSLUserTest.byteArray);
		avgCRInfo.setPDFFileName("a.pdf");
		avgCRInfo.setPDFFile(blob);
		
    	infoList.add(avgCRInfo);
        
		// Pass a Mock DataBean
		dbUser = createMock(AvgCeilingRateDBUser.class);
		
		expect(dbUser.getAvgCeilingRateEff()).andReturn(infoList).anyTimes();
		expect(dbUser.getAvgCeilingRate(isA(String.class), isA(String.class), isA(String.class), isA(String.class), isA(String.class))).andReturn(avgCRInfo).anyTimes();
		replay(dbUser);
        return dbUser;
    }
    
    
    @Test
    public void test() throws SysException, SerialException, SQLException{
    	AvgCeilingRateDBUser db = getAvgCeilingRateDBUser();
    	db = null;
    }
    
}