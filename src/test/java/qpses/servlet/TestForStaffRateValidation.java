package qpses.servlet;

import static org.easymock.EasyMock.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.Test;

import qpses.business.CPARDataBean;
import qpses.business.CPARInfo;
import qpses.business.CeilingRateCat1Info;
import qpses.business.CeilingRateCat2Info;
import qpses.business.CeilingRateCat3Info;
import qpses.business.CeilingRateCat4Info;
import qpses.business.ContractorDataBean;
import qpses.business.ContractorInfo;
import qpses.business.DeptDataBean;
import qpses.business.DeptInfo;
import qpses.business.LogDataBean;
import qpses.business.QualitySubscoreInfo;
import qpses.business.StaffRateValidationDBUser;
import qpses.business.WAChallengeInfo;
import qpses.business.WorkAssignmentInfo;
import qpses.security.SecurityContext;
import qpses.security.SecurityDataBean;
import qpses.security.SecurityServlet;
import qpses.security.UserStatus;
import qpses.util.Constant;
import qpses.util.SysException;
import qpses.util.SysManager;

public final class TestForStaffRateValidation extends StaffRateValidationSLUser {

	private ServletContext servCtx; 
	private DeptDataBean deptDB; 
	private ContractorDataBean conDB;
	private RequestDispatcher dispatcher;
	private StaffRateValidationDBUser dbUser;
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
	public DeptDataBean getDeptDataBean() throws SysException{
    	try {
    		
    		DeptInfo aDeptInfo = new DeptInfo(); 
    		aDeptInfo.setDeptName("OGCIO");
    		// Pass a Mock DataBean
    		deptDB = createMock(DeptDataBean.class);
    		expect(deptDB.selectDeptByKeys(isA(String.class), isA(String.class))).andReturn(aDeptInfo).anyTimes();
    		
			replay(deptDB);
		} catch (SecurityException e) {
			throw new SysException(e.getMessage());
		}
        return deptDB;
    }
    
    @Override
    public ContractorDataBean getContractorDataBean() throws SysException{
    	try {
    		List<ContractorInfo> allCPARList = new ArrayList<ContractorInfo>();

    		// Pass a Mock DataBean
    		conDB = createMock(ContractorDataBean.class);
    		expect(conDB.selectContractorByCatgp(isA(String.class))).andReturn(allCPARList).times(0,10);
    		
			replay(conDB);
		} catch (SecurityException e) {
			throw new SysException(e.getMessage());
		}
        return conDB;
    }
    
    
    @Override
	public StaffRateValidationDBUser getStaffRateValidationDBUser() throws SysException{
    	List allDContractorName = Arrays.asList("a", "b", "c");
    	CeilingRateCat1Info cat1Info = new CeilingRateCat1Info();
    	CeilingRateCat2Info cat2Info = new CeilingRateCat2Info();
    	CeilingRateCat3Info cat3Info = new CeilingRateCat3Info();
    	CeilingRateCat4Info cat4Info = new CeilingRateCat4Info();
    	
    	cat1Info.setContractorID("HP");
    	cat1Info.setContractorName("HP");
    	cat1Info.setCurrency("hkd");
    	cat1Info.setStdStaffRate(1, 1, 1.0);
    	cat1Info.setSupStaffRate(1, 1.0);
    	
    	cat2Info.setContractorID("HP");
    	cat2Info.setContractorName("HP");
    	cat2Info.setCurrency("hkd");
    	cat2Info.setStdStaffRate(1, 1,1,1.0);
    	cat2Info.setSupStaffRate(1, 1,1.0);
    	
    	cat3Info.setContractorID("HP");
    	cat3Info.setContractorName("HP");
    	cat3Info.setCurrency("hkd");
    	cat3Info.setStdOnGoingStaffRate(1, 1,1, 1.0);
    	cat3Info.setStdOneOffStaffRate(1, 1, 1.0);
    	cat3Info.setSupOnGoingStaffRate(1, 1,1.0);
    	cat3Info.setSupOneOffStaffRate(1, 1.0);
    	
    	cat4Info.setContractorID("HP");
    	cat4Info.setContractorName("HP");
    	cat4Info.setCurrency("hkd");
    	cat4Info.setOnGoingStaffRate(1,1, 1.0);
    	cat4Info.setOneOffStaffRate(1,1, 1.0);
    	
    	List<CeilingRateCat1Info> info1List = new ArrayList<CeilingRateCat1Info>();
    	List<CeilingRateCat2Info> info2List = new ArrayList<CeilingRateCat2Info>();
    	List<CeilingRateCat3Info> info3List = new ArrayList<CeilingRateCat3Info>();
    	List<CeilingRateCat4Info> info4List = new ArrayList<CeilingRateCat4Info>();
    	
    	info1List.add(cat1Info);
    	info2List.add(cat2Info);
    	info3List.add(cat3Info);
    	info4List.add(cat4Info);
    	
    	List<QualitySubscoreInfo> qualitySubscores = new ArrayList<QualitySubscoreInfo>();
    	QualitySubscoreInfo aQSInfo = new QualitySubscoreInfo();
    	aQSInfo.setEffectiveStartDate(SysManager.getSQLDate("11-May-2011"));
    	qualitySubscores.add(aQSInfo);
    	
    	List<WorkAssignmentInfo> infoList = new ArrayList<WorkAssignmentInfo>();
    	WorkAssignmentInfo aWAInfo = new WorkAssignmentInfo();
		
		// Pass a Mock DataBean
		dbUser = createMock(StaffRateValidationDBUser.class);
		
		expect(dbUser.getDContractorName(null)).andReturn(allDContractorName).anyTimes();
		expect(dbUser.getWAByKeys(null,null,0,null)).andReturn(aWAInfo).anyTimes();
		expect(dbUser.getWA(isA(String.class))).andReturn(infoList).anyTimes();
		expect(dbUser.getCeilingRateCat1ByList(isA(String.class),contains("-"))).andReturn(info1List).anyTimes();
		expect(dbUser.getCeilingRateCat2ByList(isA(String.class),contains("-"))).andReturn(info2List).anyTimes();
		expect(dbUser.getCeilingRateCat3ByList(isA(String.class),contains("-"))).andReturn(info3List).anyTimes();
		expect(dbUser.getCeilingRateCat4ByList(isA(String.class),contains("-"))).andReturn(info4List).anyTimes();
		expect(dbUser.getQualitySubscoreByList(isA(String.class), isA(String.class), isA(String.class))).andReturn(qualitySubscores).anyTimes();
		expect(dbUser.addLog(isA(String.class),isA(String.class),isA(String.class),isA(String.class),isA(String.class),isA(String.class),isA(ByteArrayOutputStream.class),isA(String.class),isA(String.class),isA(String.class),isA(String.class))).andReturn(1).anyTimes();
		replay(dbUser);
        return dbUser;
    }
    
    
    @Test
    public void test() throws SysException{
    	ContractorDataBean contractorDB = getContractorDataBean();
    	DeptDataBean departmentDB = getDeptDataBean();
    	contractorDB = null;
    	departmentDB = null;
    }
    
}