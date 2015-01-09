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

public final class TestForAdminCeilingRateServlet extends AdminCeilingRateServlet {

	private CeilingRateDataBean crDB;
	private DiskFileUpload upload;
	private FileItem fileItem;
	protected UserStatus uCeilingRateus;
	private InputStream fis;
	
	
    @Override
    public CeilingRateDataBean getCeilingRateDataBean() throws SysException{
    	try {
    		new ArrayList<CPARInfo>();
    		List<CeilingRateCat1Info> CeilingRate1List = new ArrayList<CeilingRateCat1Info>();
    		List<CeilingRateCat2Info> CeilingRate2List = new ArrayList<CeilingRateCat2Info>();
    		List<CeilingRateCat3Info> CeilingRate3List = new ArrayList<CeilingRateCat3Info>();
    		List<CeilingRateCat4Info> CeilingRate4List = new ArrayList<CeilingRateCat4Info>();
    		
    		// Pass a Mock DataBean
    		crDB = createMock(CeilingRateDataBean.class);
    		expect(crDB.insertCeilingRate1(isA(CeilingRate1Info.class))).andReturn(1).times(0,100);
    		expect(crDB.insertCeilingRate2(isA(CeilingRate2Info.class))).andReturn(1).times(0,100);
    		expect(crDB.insertCeilingRate3(isA(CeilingRate3Info.class))).andReturn(1).times(0,100);
    		expect(crDB.insertCeilingRate4(isA(CeilingRate4Info.class))).andReturn(1).times(0,100);
    		expect(crDB.deleteCeilingRate(isA(CeilingRateInfo.class),isA(SecurityContext.class))).andReturn(1).times(0,100);
    		expect(crDB.releaseCeilingRate(isA(CeilingRateInfo.class),isA(SecurityContext.class))).andReturn(1).times(0,100);
    		expect(crDB.getCeilingRate1(isA(String.class))).andReturn(CeilingRate1List).times(0,100);
    		expect(crDB.getCeilingRate2(isA(String.class))).andReturn(CeilingRate2List).times(0,100);
    		expect(crDB.getCeilingRate3(isA(String.class))).andReturn(CeilingRate3List).times(0,100);
    		expect(crDB.getCeilingRate4(isA(String.class))).andReturn(CeilingRate4List).times(0,100);
    		expect(crDB.createTempCeilingRate(isA(String.class))).andReturn(1).times(0,100);
    		expect(crDB.replaceTempCeilingRate(isA(String.class))).andReturn(1).times(0,100);
    		expect(crDB.deleteTempCeilingRate(isA(String.class))).andReturn(1).times(0,100);
    		expect(crDB.deleteTempCeilingRate(null)).andReturn(1).times(0,100);
    		expect(crDB.writeAuditTrail(isA(CeilingRateInfo.class),isA(SecurityContext.class),lt(10000))).andReturn(1).times(0,100);
            
			replay(crDB);
		} catch (SecurityException e) {
			throw new SysException(e.getMessage());
		}
        return crDB;
    }
    
    @Override
    public DiskFileUpload getDiskFileUpload() throws ServletException{
    	try {
    		
    		fis = createMock(InputStream.class);
    		expect(fis.read(isA(byte[].class), gt(-1), gt(-1))).andAnswer(new IAnswer() {
    		    public Object answer() {
    		        //supply your mock implementation here...
    		    	byte[] arg1 = (byte[]) getCurrentArguments()[0];
    		    	
    		        arg1[0] = (byte)0xd0;
    		        arg1[1] = (byte)0xcf;
    		        arg1[2] = (byte)0x11;
    		        arg1[3] = (byte)0xe0;
    		        arg1[4] = (byte)0xa1;
    		        arg1[5] = (byte)0xb1;
    		        arg1[6] = (byte)0x1a;
    		        arg1[7] = (byte)0xe1;
    		        return 512;
    		    }
    		}).anyTimes();
    		expect(fis.markSupported()).andReturn(true).anyTimes();
    		fis.close();
    		expectLastCall().anyTimes();
    		replay(fis);
    		
    		fileItem = createMock(FileItem.class);
    		expect(fileItem.isFormField()).andReturn(false).anyTimes();
    		expect(fileItem.getFieldName()).andReturn("ServiceCategory").anyTimes();
    		expect(fileItem.getString()).andReturn("1").anyTimes();
    		expect(fileItem.getSize()).andReturn((long) 1).anyTimes();
    		expect(fileItem.getName()).andReturn("a.xls").anyTimes();
    		expect(fileItem.getContentType()).andReturn("multipart/mixed;boundary=gc0p4Jq0M2Yt08jU534c0p").anyTimes();
    		expect(fileItem.getInputStream()).andReturn(fis).anyTimes();
    		
    		replay(fileItem);
    		List<FileItem> fileItemList = new ArrayList<FileItem>();
    		fileItemList.add(fileItem);
    		
			fileItem = createMock(FileItem.class);
    		expect(fileItem.isFormField()).andReturn(true).anyTimes();
    		expect(fileItem.getFieldName()).andReturn("EffectiveDate").anyTimes();
    		expect(fileItem.getString()).andReturn("11-May-2013").anyTimes();
    		
    		replay(fileItem);
    		fileItemList.add(fileItem);
    		
    		
    		fileItem = createMock(FileItem.class);
    		expect(fileItem.isFormField()).andReturn(true).anyTimes();
    		expect(fileItem.getFieldName()).andReturn("PublishInd").anyTimes();
    		expect(fileItem.getString()).andReturn("-1").anyTimes();
    		
    		replay(fileItem);
    		fileItemList.add(fileItem);
    		
    		String[] data = new String[] { "SC1" , "SC2" , "SC3" , "SC4" };
    		List<String> categories = Arrays.asList(data);
    		
    		for (String category : categories){
    			fileItem = createMock(FileItem.class);
        		expect(fileItem.isFormField()).andReturn(true).anyTimes();
        		expect(fileItem.getFieldName()).andReturn("ServiceCategory").anyTimes();
        		expect(fileItem.getString()).andReturn(category).anyTimes();
        		
        		replay(fileItem);
        		fileItemList.add(fileItem);
    		}
    		   
    		// Pass a Mock DataBean
    		upload = createMock(DiskFileUpload.class);
    		
    		upload.setRepositoryPath(Constant.QPSIS_TEMP_DIR);
            upload.setSizeThreshold(102400);// limit Memory Size
            upload.setSizeMax(-1); // unlimit file size
            
			expect(upload.parseRequest(isA(HttpServletRequest.class))).andReturn(fileItemList).anyTimes();
			replay(upload);

		} catch (FileUploadException e) {
			e.printStackTrace();
			throw new ServletException(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e.toString());
		}
        return upload;
    }
    
    @Test
    public void test() throws SysException{
    	getCeilingRateDataBean();
    }
}