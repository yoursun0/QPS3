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

import qpses.business.ContractorDataBean;
import qpses.business.DeptDataBean;
import qpses.business.DiscountRateDBUser;
import qpses.business.DiscountRateInfo;
import qpses.security.UserStatus;
import qpses.util.SysException;

public final class TestForDiscountRate extends DiscountRateSLUser {

	private ServletContext servCtx; 
	private DeptDataBean deptDB; 
	private ContractorDataBean conDB;
	private RequestDispatcher dispatcher;
	private DiscountRateDBUser dbUser;
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
	public DiscountRateDBUser getDiscountRateDBUser() throws SysException, SerialException, SQLException{
    	List<String> infoList = new ArrayList<String>() ;
    	DiscountRateInfo aDiscountRateInfo = new DiscountRateInfo();
		Blob blob = new SerialBlob(AvgCeilingRateSLUserTest.byteArray);
		aDiscountRateInfo.setPDFFileName("a.pdf");
		aDiscountRateInfo.setPDFFile(blob);
		
    	infoList.add("a");
        
		// Pass a Mock DataBean
		dbUser = createMock(DiscountRateDBUser.class);
		
		expect(dbUser.getDiscountRateEff()).andReturn(infoList).anyTimes();
		expect(dbUser.getDiscountRate(isA(String.class), isA(String.class), isA(String.class), isA(String.class), isA(String.class))).andReturn(aDiscountRateInfo).anyTimes();
		replay(dbUser);
        return dbUser;
    }
    
    
    @Test
    public void test() throws SysException, SerialException, SQLException{
    	DiscountRateDBUser db = getDiscountRateDBUser();
    	db = null;
    }
    
}