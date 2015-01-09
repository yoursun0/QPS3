package qpses.security;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import qpses.business.DebarDataBean;
import qpses.business.DebarInfo;
import qpses.business.LogDataBean;
import qpses.business.StaffRateValidationDBUser;
import qpses.business.WAChallengeInfo;
import qpses.business.WorkAssignmentDataBean;
import qpses.business.WorkAssignmentInfo;
import qpses.util.Constant;
import qpses.util.SysException;
import qpses.util.SysManager;

@RunWith(value = Parameterized.class)
public class TestLoginForSecurityServlet extends SecurityServlet {

	protected SecurityDataBean secDB; 
	protected LogDataBean logDB; 
	protected DebarDataBean debarDB; 
	protected WorkAssignmentDataBean waDB; 
	protected StaffRateValidationDBUser dbUser;
	protected SecurityContext secCtx;
	protected UserStatus uStatus;
	protected List pvgList;
	protected List<String> adminEmails;
	
	public	TestLoginForSecurityServlet(UserStatus userStatus){
		uStatus = userStatus;
	}
	
    @Parameters
	 public static List<Object[]> data() {
		 UserStatus userStatus = new UserStatus();
	   Object[][] data = new Object[][] { { userStatus } };
	   return Arrays.asList(data);
	 }
	 
	@Override
	public String getInitParameter(String str){
		return "qpsuser/SystemNotices.jsp";
	}
	
    @Override
	public SecurityDataBean getSecurityDataBean() throws SysException{
    	try {
    		// Pass a Mock DataBean
    		secDB = createMock(SecurityDataBean.class);
    		secCtx = new SecurityContext();
    		
    		if (uStatus != null){
    			uStatus.expiryDate = SysManager.getSQLDate(Constant.QPSIS_EXPIRY_DATE);
    			uStatus.userId = "test.junit";
    			uStatus.dpDeptId = "junit";
    			uStatus.soaDeptId = "junit";
    		}
    		secCtx.userGroup = "U";
    		
    		pvgList = new ArrayList();
    		pvgList.add("user");
    		adminEmails = new ArrayList();
    		adminEmails.add("helic.leung@hkmci.com");
    		
    		expect(secDB.selectSecCTX("test.junit")).andReturn(secCtx).times(0,10);
    		expect(secDB.getUserStatus(secCtx,"password1")).andReturn(uStatus).times(0,10);
    		
    		expect(secDB.getFailureCount(secCtx,"Login")).andReturn(Constant.QPSIS_WRONG_PASSWORD_ATTEMPT_LIMIT).times(0,10);
    		expect(secDB.getFailureCount(EasyMock.isA(SecurityContext.class),EasyMock.contains("Access"))).andReturn(Constant.QPSIS_CHALLENGE_ATTEMPT_LIMIT).times(0,10);
    		expect(secDB.lockUserAccount(secCtx,0,true)).andReturn(1).times(0,10);
    		expect(secDB.lockUserAccount(EasyMock.isA(SecurityContext.class),EasyMock.gt(2),EasyMock.anyBoolean())).andReturn(1).times(0,10);
    		expect(secDB.getAdminEmails()).andReturn(adminEmails).times(0,10);
    		
    		secDB.updateLastAccessAttempt(secCtx);
    		expectLastCall().times(0,10);
            secDB.resetFailureCount(secCtx,"Login");
            expectLastCall().times(0,10);
            secDB.resetFailureCount(isA(SecurityContext.class),contains("Access"));
            expectLastCall().times(0,10);
            secDB.insertChallengeLog(null,null,"junit","RATEVALID_CHALLENGE","The invitation issue date of the work assignment", "22-Jun-2013+22-Jun-2013","Y","N",null,null,0,null);
            expectLastCall().times(0,10);
            
            expect(secDB.createAccessPrivileges(secCtx)).andReturn(pvgList).times(0,10);
            expect(secDB.getAccessibleFunction("U")).andReturn("Login").times(0,10);
            
			replay(secDB);

		} catch (SecurityException e) {
			throw new SysException(e.getMessage());
		}
        return secDB;
    }
    
    @Override
	public LogDataBean getLogDataBean() throws SysException{
    	try {
    		// Pass a Mock DataBean
    		logDB = createMock(LogDataBean.class);
    		
    		logDB.writeAccessLog(uStatus,secCtx);
    		expectLastCall().times(0,10);

			replay(logDB);

		} catch (SecurityException e) {
			throw new SysException(e.getMessage());
		}
        return logDB;
    }

    @Override
    public DebarDataBean getDebarDataBean() throws SysException{
    	List<DebarInfo> debarments = new ArrayList<DebarInfo>();
    	DebarInfo debarment = new DebarInfo();
    	debarment.setContractorId("1");
    	debarment.setContractorName("1");
    	debarments.add(debarment);
    	
     	// Pass a Mock DataBean
		debarDB = createMock(DebarDataBean.class);
		expect(debarDB.selectDebarmentByCat(null,SysManager.getSQLDate("22-Jun-2013"))).andReturn(debarments).anyTimes();

		replay(debarDB);
        return debarDB;
    }
    
    @Override
	public StaffRateValidationDBUser getStaffRateValidationDBUser() throws SysException{
    	List allDContractorName = Arrays.asList("a", "b", "c");
    	WorkAssignmentInfo aWAInfo = new WorkAssignmentInfo();
		
		// Pass a Mock DataBean
		dbUser = createMock(StaffRateValidationDBUser.class);
		
		expect(dbUser.getDContractorName(null)).andReturn(allDContractorName).anyTimes();
		expect(dbUser.getWAByKeys(null,null,0,null)).andReturn(aWAInfo).anyTimes();
		
		replay(dbUser);
        return dbUser;
    }
    

    @Override
	public WorkAssignmentDataBean getWorkAssignmentDataBean() throws SysException{

    	WAChallengeInfo wac = new WAChallengeInfo();
    	wac.setServiceCategoryGroup("1");
    	wac.setDepartmentId("OGCIO");
    	wac.setFilePartNo("I");
    	wac.setFileNo(1);
    	
    	// Pass a Mock DataBean
    	waDB = createMock(WorkAssignmentDataBean.class);
    	expect(waDB.selectWorkAssignmentByKeys(EasyMock.isA(WAChallengeInfo.class))).andReturn(wac).times(0,10);
    	expect(waDB.getNoOfInvCont("1", "OGCIO","I", 1)).andReturn(Constant.CEILING_RATE_1_MINOR).times(0,10);
		replay(waDB);

        return waDB;
    }
    
    @Test
    public void test(){
    }
}