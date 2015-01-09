package qpses.servlet;

import static org.easymock.EasyMock.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import qpses.business.DeptDataBean;
import qpses.business.DeptInfo;
import qpses.security.SecurityContext;
import qpses.security.UserStatus;
import qpses.util.SysException;

public final class TestForAdminDeptServlet extends AdminDeptServlet {

	private DeptDataBean DeptDB;
	
    @Override
    public DeptDataBean getDeptDataBean() throws SysException{
    	try {
    		List conList = new ArrayList();
    		DeptInfo con = new DeptInfo();
    		conList.add(con);
    		
    		// Pass a Mock DataBean
    		DeptDB = createMock(DeptDataBean.class);
    		expect(DeptDB.insertDept(isA(DeptInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		expect(DeptDB.updateDept(isA(DeptInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		expect(DeptDB.deleteDept(isA(DeptInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		
			replay(DeptDB);
		} catch (SecurityException e) {
			throw new SysException(e.getMessage());
		}
        return DeptDB;
    }
    
    @Test
    public void test() throws SysException{
    	DeptDB =  getDeptDataBean();
    	DeptDB = null;
    }
}