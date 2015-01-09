package qpses.servlet;

import static org.easymock.EasyMock.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.easymock.IAnswer;
import org.junit.Test;

import qpses.business.ACLDataBean;
import qpses.business.ACLInfo;
import qpses.business.CPARInfo;
import qpses.business.CeilingRate1Info;
import qpses.business.CeilingRate2Info;
import qpses.business.CeilingRate3Info;
import qpses.business.CeilingRate4Info;
import qpses.business.CeilingRateCat1Info;
import qpses.business.CeilingRateCat2Info;
import qpses.business.CeilingRateCat3Info;
import qpses.business.CeilingRateCat4Info;
import qpses.business.CeilingRateDataBean;
import qpses.business.CeilingRateInfo;
import qpses.security.SecurityContext;
import qpses.security.UserStatus;
import qpses.util.Constant;
import qpses.util.SysException;

public final class TestForAdminACLServlet extends AdminACLServlet {

	private ACLDataBean aclDB;
	private DiskFileUpload upload;
	private FileItem fileItem;
	protected UserStatus uACLus;
	private InputStream fis;
	
	
    @Override
    public ACLDataBean getACLDataBean() throws SysException{
    	try {
    		List aclList = new ArrayList();
    		ACLInfo acl = new ACLInfo();
    		acl.setUserId("junit.test");
    		acl.setPassword("password");
    		aclList.add(acl);
    		
    		// Pass a Mock DataBean
    		aclDB = createMock(ACLDataBean.class);
    		expect(aclDB.insertACL(isA(ACLInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		expect(aclDB.updateACL(isA(ACLInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		expect(aclDB.deleteACL(isA(ACLInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		expect(aclDB.selectInactiveUsers()).andReturn(aclList).anyTimes();
    		expect(aclDB.insertUnlockLog(isA(SecurityContext.class),isA(String.class),lt(100))).andReturn(1).anyTimes();
    		expect(aclDB.unlockUser(isA(List.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		expect(aclDB.unlockSingleUser(isA(ACLInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		expect(aclDB.replaceACL(isA(ACLInfo.class),isA(SecurityContext.class))).andReturn(1).anyTimes();
    		expect(aclDB.createTempACL()).andReturn(1).anyTimes();
    		expect(aclDB.replaceTempACL()).andReturn(1).anyTimes();
    		expect(aclDB.deleteTempACL()).andReturn(1).anyTimes();
    		expect(aclDB.writeAuditTrail(isA(ACLInfo.class),isA(SecurityContext.class),gt(-1))).andReturn(1).anyTimes();
    		
			replay(aclDB);
		} catch (SecurityException e) {
			throw new SysException(e.getMessage());
		}
        return aclDB;
    }
    
    @Test
    public void test() throws SysException{
    	aclDB =  getACLDataBean();
    	aclDB = null;
    }
}