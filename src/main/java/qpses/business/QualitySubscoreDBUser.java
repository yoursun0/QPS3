package qpses.business;

import org.apache.log4j.Logger;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import qpses.util.*;

public class QualitySubscoreDBUser extends DataBean
{
    // Get class name
    private static String MyClassName = QualitySubscoreDBUser.class.getName();
    
    // Set up Log4j
    private static Logger logger = Logger.getLogger(MyClassName);
    
    // Function ID
    private static String FunctionID = "QSENQ";
    
    // Stored procedure or query
    private static final String SELECT_QS          = "{call sp_select_quality_subscore_user(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String SELECT_WA          = "{call sp_select_quality_subscore_wa_user(?)}";
    private static final String SELECT_DCONTRACTOR = "{call sp_select_quality_subscore_dcontractor_user(?, ?, ?, ?)}";
    
    
    public QualitySubscoreDBUser() throws SysException
    { super("USER"); }
    
    
    public List getWA(String dpDeptId) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getWA" + "]";
        logger.debug(myName + " " + "started");
        
        WorkAssignmentInfo aWAInfo = null;
        List allWAInfo = new ArrayList();
        
        Connection cnn = null;
        
        try
        {            
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(this.SELECT_WA);
            
            aCStmt.setString("p_dp_department_id", dpDeptId);
                        
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
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
        { throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
         finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return allWAInfo;
    }
    
    public List getQualitySubscore(String closingDate, String scg, String deptId, String waFilePart, String waFileNo, String userId, String dpDeptId, String soaDeptId) 
    throws SysException
    {
        String myName = "[" + MyClassName + "." + "getQualitySubscore" + "]";
        logger.debug(myName + " " + "started");
        
        Connection cnn              = null;
        QualitySubscoreInfo aQSInfo = null;
        List allQSInfo            = new ArrayList();
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(SELECT_QS);
            
            aCStmt.setString("p_scg"              , scg);
            aCStmt.setDate("p_closing_date"       , SysManager.getSQLDate(closingDate));
            aCStmt.setString("p_user_id"          , userId);
            aCStmt.setString("p_dp_department_id" , dpDeptId);
            aCStmt.setString("p_soa_department_id", soaDeptId);            
            aCStmt.setString("p_dept_id"          , deptId);
            aCStmt.setString("p_wa_file_part"     , waFilePart);
            aCStmt.setString("p_wa_file_no"       , waFileNo);            
            aCStmt.setString("p_function_id"      , this.FunctionID);
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            while (rs.next())
            {
                aQSInfo = new QualitySubscoreInfo();
                aQSInfo.setContractorName(rs.getString("contractor_name"));
                aQSInfo.setScore(Float.valueOf(rs.getString("quality_subscore")).floatValue());
                aQSInfo.setEffectiveStartDate(rs.getDate("effective_start_date"));
                
                allQSInfo.add(aQSInfo);
            }

            cnn.commit();
        }
        catch (Exception ex)
        {
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return allQSInfo;
    }
    
    public String getDContractorNameList(String scg, String deptId, String waFilePart, String waFileNo) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getDContractorNameList" + "]";
        logger.debug(myName + " " + "started");
        
        String allContractorName = "";
        
        Connection cnn = null;
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(this.SELECT_DCONTRACTOR);
            
            aCStmt.setString("p_scg"         , scg);
            aCStmt.setString("p_dept_id"     , deptId);
            aCStmt.setString("p_wa_file_part", waFilePart);
            aCStmt.setInt("p_wa_file_no"     , Integer.parseInt(waFileNo));
            
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            while(rs.next())
            { allContractorName += (allContractorName.equals("") ? "" : ", ") + rs.getString("contractor_name"); }
            
            cnn.commit();
        }
        catch (Exception ex)
        {
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(myName, ex.getMessage());
        }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        
        return allContractorName;
    }    
}