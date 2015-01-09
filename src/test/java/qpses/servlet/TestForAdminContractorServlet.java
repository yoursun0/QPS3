package qpses.servlet;

import static org.easymock.EasyMock.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import qpses.business.ContractorDataBean;
import qpses.business.ContractorInfo;
import qpses.security.SecurityContext;
import qpses.security.UserStatus;
import qpses.util.SysException;

public final class TestForAdminContractorServlet extends AdminContractorServlet {

	private ContractorDataBean contractorDB;
	
    @Override
    public ContractorDataBean getContractorDataBean() throws SysException{
    	try {
    		List conList = new ArrayList();
    		ContractorInfo con = new ContractorInfo();
    		conList.add(con);
    		
    		// Pass a Mock DataBean
    		contractorDB = createMock(ContractorDataBean.class);
    		expect(contractorDB.insertContractor(isA(ContractorInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		expect(contractorDB.updateContractor(isA(ContractorInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		expect(contractorDB.deleteContractor(isA(ContractorInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		
			replay(contractorDB);
		} catch (SecurityException e) {
			throw new SysException(e.getMessage());
		}
        return contractorDB;
    }
    
    @Test
    public void test() throws SysException{
    	contractorDB =  getContractorDataBean();
    	contractorDB = null;
    }
}