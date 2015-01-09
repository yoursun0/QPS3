package qpses.business;

import org.apache.log4j.Logger;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import qpses.util.*;

public class StaffRateValidationDBUser extends DataBean
{
    // Get class name
    private static String MyClassName = StaffRateValidationDBUser.class.getName();
    
    // Set up Log4j
    private static Logger logger = Logger.getLogger(MyClassName);
    
    // Function ID
    private static String FunctionID = "RATEVALID";
    
    // Stored procedure or query
    private static final String SELECT_CEILING_RATE_1 = "{call sp_select_srv_ceiling_rate_1_user(?, ?)}";
    private static final String SELECT_CEILING_RATE_2 = "{call sp_select_srv_ceiling_rate_2_user(?, ?)}";
    private static final String SELECT_CEILING_RATE_3 = "{call sp_select_srv_ceiling_rate_3_user(?, ?)}";
    private static final String SELECT_CEILING_RATE_4 = "{call sp_select_srv_ceiling_rate_4_user(?, ?)}";
    private static final String SELECT_QS             = "{call sp_select_srv_qs_user(?, ?, ?)}";
    private static final String INSERT_SRV_LOG        = "{call sp_insert_srv_log_user(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String SELECT_WA             = "{call sp_select_srv_wa_user(?)}";
    private static final String SELECT_DCONTRACTOR    = "{call sp_select_srv_dcontractor_user(?)}";
    private static final String SELECT_SRV_LOG        = "{call sp_select_srv_validation_log_user(?)}";
    private static final String SELECT_WA_BY_KEYS     = "{call sp_select_srv_wa_by_keys_user(?, ?, ?, ?)}";
    
    public StaffRateValidationDBUser() throws SysException
    { super("USER"); }
        
    public WorkAssignmentInfo getWAByKeys(String scg, String filePartNo, int fileNo, String deptID) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getWAByKeys" + "]";
        logger.debug(myName + " " + "started");
        
        WorkAssignmentInfo aWAInfo = null;
        Connection cnn             = null;
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(this.SELECT_WA_BY_KEYS);
            
            aCStmt.setString("p_scg"         , scg);
            aCStmt.setString("p_file_part_no", filePartNo);
            aCStmt.setInt("p_file_no"        , fileNo);
            aCStmt.setString("p_dept_id"     , deptID);
            
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            if (rs.next())
            {
                aWAInfo = new WorkAssignmentInfo();
                
                aWAInfo.setServiceCategoryGroup(rs.getString("service_category_group"));
                aWAInfo.setAuthorizedPerson(rs.getString("authorized_person"));
                aWAInfo.setClosingDate(rs.getDate("closing_date"));
                aWAInfo.setDebarredContractor(rs.getString("debarred_contractor"));
                aWAInfo.setDepartmentName(rs.getString("bd_name"));
                aWAInfo.setDepartmentId(rs.getString("department_id"));
                aWAInfo.setFileNo(rs.getInt("project_file_no"));
                aWAInfo.setFilePart(rs.getString("project_file_part"));
                aWAInfo.setIssuedDate(rs.getDate("issue_date"));
                aWAInfo.setStatus(rs.getString("assignment_status"));
                aWAInfo.setTitle(rs.getString("work_assignment_title"));
                aWAInfo.setAwardedContractor(rs.getString("awarded_contractor"));
                aWAInfo.setAwardedDate(rs.getDate("awarded_date"));
            }
            
            cnn.commit();
        }
        catch (Exception ex)
        {
        	String error =
                    "SysManager ERROR: StaffRateValidationDBUser:getWAByKeys\n"
                    + ex.getMessage();
        	logger.error(error, ex);

        	try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { 
            	String error2 =
                        "SysManager ERROR: StaffRateValidationDBUser:getWAByKeys\n"
                        + ex2.getMessage();
            	logger.error(error2, ex2);
                throw new SysException(myName, ex2.getMessage());
            }

            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return aWAInfo;
    }
    
    public Blob getPDF(String logID) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getPDF" + "]";
        logger.debug(myName + " " + "started");
        
        List allContractorInfo = new ArrayList();
        Connection cnn = null;
        
        Blob aBlob = null;
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(this.SELECT_SRV_LOG);
            
            aCStmt.setInt("p_log_id", Integer.parseInt(logID));
            
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            if (rs.next())
            { aBlob = rs.getBlob("ceiling_rate_file"); }
            else
            { 
                throw new SysException(myName, "Cannot get PDF file for ID [" + logID + "]"); 
            }
            
            cnn.commit();
        }
        catch (Exception ex)
        {
        	String error =
                    "SysManager ERROR: StaffRateValidationDBUser:getPDF\n"
                    + ex.getMessage();
        	logger.error(error, ex);
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            {            
            	String error2 =
                        "SysManager ERROR: StaffRateValidationDBUser:getPDF\n"
                        + ex.getMessage();
            	logger.error(error2, ex2);
            	throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return aBlob;
    }
    
    public List<CeilingRateCat1Info> getCeilingRateCat1ByList(String contractorIDList, String closingDate) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getCeilingRateCat1ByList" + "]";
        logger.debug(myName + " " + "started");
        
        List<CeilingRateCat1Info> crCat1InfoList = new ArrayList<CeilingRateCat1Info>();
        CeilingRateCat1Info aCRCat1Info = null;
        
        Connection cnn = null;
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(SELECT_CEILING_RATE_1);
            
            aCStmt.setDate("p_closing_date" , SysManager.getSQLDate(closingDate));
            aCStmt.setString("p_contractor_id_list", contractorIDList);
            
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            CeilingRateDataBean crDB = new CeilingRateDataBean();
            crCat1InfoList = crDB.RecordCat1Info(aCRCat1Info, rs);
            
            cnn.commit();
        }
        catch (Exception ex)
        {
        	String error =
                    "SysManager ERROR: StaffRateValidationDBUser:getCeilingRateCat1ByList\n"
                    + ex.getMessage();
        	logger.error(error, ex);
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { 
            	String error2 =
                        "SysManager ERROR: StaffRateValidationDBUser:getCeilingRateCat1ByList\n"
                        + ex2.getMessage();
            	logger.error(error2, ex2);
              throw new SysException(myName, ex2.getMessage());
            }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return crCat1InfoList;
    }
    
    
    public List<CeilingRateCat2Info> getCeilingRateCat2ByList(String contractorIDList, String closingDate) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getCeilingRateCat2ByList" + "]";
        logger.debug(myName + " " + "started");
        
        List<CeilingRateCat2Info> crCat2InfoList = new ArrayList<CeilingRateCat2Info>();
        CeilingRateCat2Info aCRCat2Info = null;
        
        Connection cnn = null;
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(SELECT_CEILING_RATE_2);
            
            aCStmt.setDate("p_closing_date" , SysManager.getSQLDate(closingDate));
            aCStmt.setString("p_contractor_id_list", contractorIDList);
            
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            CeilingRateDataBean crDB = new CeilingRateDataBean();
            crCat2InfoList = crDB.RecordCat2Info(aCRCat2Info, rs);
            
            cnn.commit();
        }
        catch (Exception ex)
        {
        	String error =
                    "SysManager ERROR: StaffRateValidationDBUser:getCeilingRateCat2ByList\n"
                    + ex.getMessage();
        	logger.error(error, ex);
        	
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { 
            	String error2 =
                        "SysManager ERROR: StaffRateValidationDBUser:getCeilingRateCat2ByList\n"
                        + ex2.getMessage();
            	logger.error(error2, ex2);
        	  throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return crCat2InfoList;
    }
    
    
    public List<CeilingRateCat3Info> getCeilingRateCat3ByList(String contractorIDList, String closingDate) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getCeilingRateCat3ByList" + "]";
        logger.debug(myName + " " + "started");
        
        List<CeilingRateCat3Info> crCat3InfoList = new ArrayList<CeilingRateCat3Info>();
        CeilingRateCat3Info aCRCat3Info = null;
        
        Connection cnn = null;
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(SELECT_CEILING_RATE_3);
            
            aCStmt.setDate("p_closing_date" , SysManager.getSQLDate(closingDate));
            aCStmt.setString("p_contractor_id_list", contractorIDList);
            
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            CeilingRateDataBean crDB = new CeilingRateDataBean();
            crCat3InfoList = crDB.RecordCat3Info(aCRCat3Info, rs);
            
            cnn.commit();
        }
        catch (Exception ex)
        {
        	String error =
                    "SysManager ERROR: StaffRateValidationDBUser:getCeilingRateCat3ByList\n"
                    + ex.getMessage();
        	logger.error(error, ex);
        	
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { 
            	String error2 =
                        "SysManager ERROR: StaffRateValidationDBUser:getCeilingRateCat3ByList\n"
                        + ex2.getMessage();
            	logger.error(error2, ex2);
        	  throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return crCat3InfoList;
    }
    
    
    public List<CeilingRateCat4Info> getCeilingRateCat4ByList(String contractorIDList, String closingDate) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getCeilingRateCat4ByList" + "]";
        logger.debug(myName + " " + "started");
        
        List<CeilingRateCat4Info> crCat4InfoList = new ArrayList<CeilingRateCat4Info>();
        CeilingRateCat4Info aCRCat4Info = null;
        
        Connection cnn = null;
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(SELECT_CEILING_RATE_4);
            
            aCStmt.setDate("p_closing_date" , SysManager.getSQLDate(closingDate));
            aCStmt.setString("p_contractor_id_list", contractorIDList);
            
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            CeilingRateDataBean crDB = new CeilingRateDataBean();
            crCat4InfoList = crDB.RecordCat4Info(aCRCat4Info, rs);
            
            cnn.commit();
        }
        catch (Exception ex)
        {
        	String error =
                    "SysManager ERROR: StaffRateValidationDBUser:getCeilingRateCat4ByList\n"
                    + ex.getMessage();
        	logger.error(error, ex);
        	
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { 
            	String error2 =
                        "SysManager ERROR: StaffRateValidationDBUser:getCeilingRateCat4ByList\n"
                        + ex2.getMessage();
            	logger.error(error2, ex2);
              throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return crCat4InfoList;
    }
    
    public List<QualitySubscoreInfo> getQualitySubscoreByList(String contractorIDList, String closingDate, String scg) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getQualitySubscoreByList" + "]";
        logger.debug(myName + " " + "started");
        
        QualitySubscoreInfo aQSInfo = null;
        List<QualitySubscoreInfo> allQSInfo = new ArrayList<QualitySubscoreInfo>();
        
        Connection cnn = null;
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(SELECT_QS);
            
            aCStmt.setDate("p_closing_date" , SysManager.getSQLDate(closingDate));
            aCStmt.setString("p_contractor_id_list", contractorIDList);
            aCStmt.setString("p_scg", scg);
            
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            while(rs.next())
            {
                aQSInfo = new QualitySubscoreInfo();
                aQSInfo.setEffectiveStartDate(rs.getDate("effective_start_date"));
                aQSInfo.setContractorName(rs.getString("contractor_name"));
                aQSInfo.setScore(Float.valueOf(rs.getString("quality_subscore")).floatValue());
                
                allQSInfo.add(aQSInfo);
            }
            
            cnn.commit();
        }
        catch (Exception ex)
        {
        	String error =
                    "SysManager ERROR: StaffRateValidationDBUser:getQualitySubscoreByList\n"
                    + ex.getMessage();
        	logger.error(error, ex);
        	
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { 
            	String error2 =
                        "SysManager ERROR: StaffRateValidationDBUser:getQualitySubscoreByList\n"
                        + ex2.getMessage();
            	logger.error(error2, ex2);
            	throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return allQSInfo;
    }
    
    public List<WorkAssignmentInfo> getWA(String soaDeptId) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getWA" + "]";
        logger.debug(myName + " " + "started");
        
        WorkAssignmentInfo aWAInfo = null;
        List<WorkAssignmentInfo> allWAInfo = new ArrayList<WorkAssignmentInfo>();
        
        Connection cnn = null;
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(SELECT_WA);
            
            aCStmt.setString("p_dept_id", soaDeptId);
            
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            while(rs.next())
            {
                aWAInfo = new WorkAssignmentInfo();
                aWAInfo.setServiceCategoryGroup(rs.getString("service_category_group"));
                aWAInfo.setAuthorizedPerson(rs.getString("authorized_person"));
                aWAInfo.setClosingDate(rs.getDate("closing_date"));
                aWAInfo.setDebarredContractor(rs.getString("debarred_contractor"));
                aWAInfo.setDepartmentName(rs.getString("bd_name"));
                aWAInfo.setDepartmentId(rs.getString("department_id"));
                aWAInfo.setFileNo(rs.getInt("project_file_no"));
                aWAInfo.setFilePart(rs.getString("project_file_part"));
                aWAInfo.setIssuedDate(rs.getDate("issue_date"));
                aWAInfo.setStatus(rs.getString("assignment_status"));
                aWAInfo.setTitle(rs.getString("work_assignment_title"));
                aWAInfo.setAwardedContractor(rs.getString("awarded_contractor"));
                aWAInfo.setAwardedDate(rs.getDate("awarded_date"));
                
                allWAInfo.add(aWAInfo);
            }
            
            cnn.commit();
        }
        catch (Exception ex)
        {
        	String error =
                    "SysManager ERROR: StaffRateValidationDBUser:getWA\n"
                    + ex.getMessage();
        	logger.error(error, ex);
        	
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { 
            	String error2 =
                        "SysManager ERROR: StaffRateValidationDBUser:getWA\n"
                        + ex2.getMessage();
            	logger.error(error2, ex2);
            	throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return allWAInfo;
    }
    
    public List getDContractorName(String contractorIDList) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getDContractorName" + "]";
        logger.debug(myName + " " + "started");
        
        List allContractorName = new ArrayList();
        
        Connection cnn = null;
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(this.SELECT_DCONTRACTOR);
            
            aCStmt.setString("p_contractor_id_list", contractorIDList);
            
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            while(rs.next())
            { allContractorName.add(rs.getString("contractor_name")); }
            
            cnn.commit();
        }
        catch (Exception ex)
        {
        	String error =
                    "SysManager ERROR: StaffRateValidationDBUser:getDContractorName\n"
                    + ex.getMessage();
        	logger.error(error, ex);
        	
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { 
            	String error2 =
                        "SysManager ERROR: StaffRateValidationDBUser:getDContractorName\n"
                        + ex2.getMessage();
            	logger.error(error2, ex2);
            	throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return allContractorName;
    }
    
    public int addLog(String scg, String contractorIDList, String deptID, String closingDate, String projectFilePart, String projectFileNo, ByteArrayOutputStream ceilingRateBAOS, String showSubscoreRpt, String userID, String dpDeptId, String soaDeptId) throws SysException
    {
        String myName = "[" + MyClassName + "." + "AddLog" + "]";
        logger.debug(myName + " " + "started");
        
        Connection cnn  = null;
        int returnLogID = -1;
        
        try
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(ceilingRateBAOS.toByteArray());
            
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(INSERT_SRV_LOG);
            
            aCStmt.setString("service_category_group" , scg);
            aCStmt.setString("contractor_id_list"     , contractorIDList);
            aCStmt.setString("dept_id"                , deptID);
            aCStmt.setDate("closing_date"             , SysManager.getSQLDate(closingDate));
            aCStmt.setString("project_file_part"      , projectFilePart);
            aCStmt.setString("project_file_no"        , projectFileNo);
            aCStmt.setBinaryStream("ceiling_rate_file", bais, ceilingRateBAOS.size());
            aCStmt.setString("show_subscore_rpt"      , showSubscoreRpt);
            aCStmt.setString("user_id"                , userID);
            aCStmt.setString("p_dp_department_id"     , dpDeptId);
            aCStmt.setString("p_soa_department_id"    , soaDeptId);
            
            aCStmt.registerOutParameter("log_id", java.sql.Types.BIGINT);
            
            aCStmt.executeUpdate();
            
            cnn.commit();
            
            returnLogID = aCStmt.getInt("log_id");
        }
        catch (Exception ex)
        {
        	String error =
                    "SysManager ERROR: StaffRateValidationDBUser:addLog\n"
                    + ex.getMessage();
        	logger.error(error, ex);
        	
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { 
            	String error2 =
                        "SysManager ERROR: StaffRateValidationDBUser:addLog\n"
                        + ex2.getMessage();
            	logger.error(error2, ex2);
            throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return returnLogID;
    }
}