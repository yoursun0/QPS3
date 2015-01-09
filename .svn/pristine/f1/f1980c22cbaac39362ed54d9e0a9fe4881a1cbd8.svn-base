/*
 * LogDataBean.java
 */

package qpses.business;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import qpses.security.SecurityContext;
import qpses.security.SecurityException;
import qpses.security.UserStatus;
import qpses.util.DataBean;
import qpses.util.SysException;
import qpses.util.SysManager;

public class LogDataBean extends DataBean {
    
    
    // log4j
    static Logger logger = Logger.getLogger(LogDataBean.class.getName());

    private static final String SEARCH_ACCESS_LOG =
            "{Call sp_search_access_log(?,?,?,?,?,?,?,?,?,?)}";  // 10 parameters
    private static final String SEARCH_USER_LOG =
            "{Call sp_search_user_log(?,?,?,?,?,?,?,?,?,?)}";  // 8 parameters
    private static final String SEARCH_SRV_LOG =
            "{Call sp_search_srv_log(?,?,?,?,?,?,?,?,?,?)}";  // 10 parameters     
    private static final String SEARCH_ADMIN_LOG =
            "{Call sp_search_admin_log(?,?,?,?,?,?,?,?)}";  // 8 parameters
    private static final String SELECT_SRV_LOG_FILE =
            "{Call sp_select_srv_log_file(?)}";  // 1 parameters
    private static final String INSERT_ADMIN_LOG =
            "{Call sp_insert_admin_log(?,?,?,?,?,?,?)}";  // 7 parameters
    private static final String WRITE_ACCESS_LOG =
            "{Call sp_write_access_log(?,?,?,?)}"; // 4 parameters
    private static final String INSERT_CHANGE_PASSWORD_LOG = 
            "{Call sp_insert_change_password_log(?,?,?,?)}"; // 4 parameters
    
    ResultSet rs = null;
    
    /**
     * Constructor for ACLDataBean
     */
    public LogDataBean() throws SysException{
        super("ADMIN");
    }   
    
    public List searchAccessLog(LogInfo searchACLog,String orderBy, String orderDir,int startRec, int pageSize) throws SysException {
        List ACLogList = new ArrayList();
        try {
            CallableStatement cs = null;
            cs = getCStmt(SEARCH_ACCESS_LOG);
            cs.setString("p_user_id",searchACLog.getUserId());
            cs.setString("p_dp_dept_id",searchACLog.getDPDeptId());            
            cs.setString("p_activity",searchACLog.getActivity());
            if (searchACLog.getFromDate()!=null){
                cs.setDate("p_from_date",SysManager.getSQLDate(searchACLog.getFromDate()));
            }else{
                cs.setDate("p_from_date",null);
            }
            if (searchACLog.getToDate()!=null){
                cs.setDate("p_to_date",SysManager.getSQLDate(searchACLog.getToDate()));            
            }else{
                cs.setDate("p_to_date",null);
            }
            cs.setString("p_status",searchACLog.getStatus());
            // execute
            cs.setString("p_order_by",orderBy);
            cs.setString("p_order_dir",orderDir);
            cs.setInt("p_start_rec", startRec);
            cs.setInt("p_page_size",pageSize);
            

            // excute the query to get 2 result sets
            cs.execute();
            
            // get the first result set containing the no. of records
            rs = cs.getResultSet();
            rs.next();
            String recordSize = rs.getString("count");
            ACLogList.add(recordSize);
            
            // get the second result set's data and put into List
            cs.getMoreResults();
            rs = cs.getResultSet();
            while (rs.next()) {
                LogInfo acLog = new LogInfo();
                acLog.setLogId(rs.getInt("log_id"));
                acLog.setUserId(rs.getString("user_id"));
                acLog.setDPDeptId(rs.getString("dp_department_id"));                
                acLog.setActivity(rs.getString("access_type"));
                acLog.setStatus(rs.getString("access_status"));
                acLog.setActivityDateTime(rs.getTimestamp("date_time").toString());
                ACLogList.add(acLog);
            }
        } catch (Exception ex) {
            String error =
                    "DB ERROR: LogDataBean:searchAccessLog\n"
                    + ex.getMessage();
            logger.error(error,ex);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return ACLogList;
        
    }
    
    public List searchUserLog(LogInfo searchUserLog,String orderBy, String orderDir, int startRec, int pageSize) throws SysException {
        List userLogList = new ArrayList();
        try {
            CallableStatement cs = null;
            cs = getCStmt(SEARCH_USER_LOG);
            cs.setString("p_user_id",searchUserLog.getUserId());
            cs.setString("p_dp_dept_id",searchUserLog.getDPDeptId());
            cs.setString("p_soa_dept_id",searchUserLog.getSOADeptId());
            cs.setString("p_activity",searchUserLog.getActivity());           
            if (searchUserLog.getFromDate()!=null){
                cs.setDate("p_from_date",SysManager.getSQLDate(searchUserLog.getFromDate()));
            }else{
                cs.setDate("p_from_date",null);
            }
            if (searchUserLog.getToDate()!=null){
                cs.setDate("p_to_date",SysManager.getSQLDate(searchUserLog.getToDate()));            
            }else{
                cs.setDate("p_to_date",null);
            }
            cs.setString("p_order_by",orderBy);
            cs.setString("p_order_dir",orderDir);

            cs.setInt("p_start_rec", startRec);
            cs.setInt("p_page_size",pageSize);
           

            // excute the query to get 2 result sets
            cs.execute();
            
            // get the first result set containing the no. of records
            rs = cs.getResultSet();
            rs.next();
            String recordSize = rs.getString("count");
            userLogList.add(recordSize);
            
            // get the second result set's data and put into List
            cs.getMoreResults();
            rs = cs.getResultSet();           

            while (rs.next()) {
                LogInfo userLog = new LogInfo();
                userLog.setLogId(rs.getInt("audit_trail_id"));
                userLog.setUserId(rs.getString("user_id"));
                userLog.setRecordKey(rs.getString("record_id"));
                userLog.setDPDeptId(rs.getString("dp_department_id"));
                userLog.setSOADeptId(rs.getString("soa_department_id"));
                userLog.setDeptName(rs.getString("bd_name"));
                userLog.setActivity(rs.getString("function_id"));
                userLog.setActivityDateTime(rs.getTimestamp("date_time").toString());
                userLog.setRemarks(rs.getString("remarks"));
                userLogList.add(userLog);
            }
        } catch (Exception ex) {
            String error =
                    "DB ERROR: LogDataBean:searchUserLog\n"
                    + ex.getMessage();
            logger.error(error,ex);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return userLogList;
        
    }
    
    public List searchSRVLog(LogInfo searchSRVLog,String orderBy, String orderDir, int startRec, int pageSize) throws SysException {
        List srvLogList = new ArrayList();
        try {
            CallableStatement cs = null;
            cs = getCStmt(SEARCH_SRV_LOG);
            cs.setString("p_user_id",searchSRVLog.getUserId());
            cs.setString("p_dp_dept_id",searchSRVLog.getDPDeptId());
            cs.setString("p_soa_dept_id",searchSRVLog.getSOADeptId());
            cs.setString("p_service_category_group",searchSRVLog.getServiceCategoryGroup());           
            if (searchSRVLog.getFromDate()!=null){
                cs.setDate("p_from_date",SysManager.getSQLDate(searchSRVLog.getFromDate()));
            }else{
                cs.setDate("p_from_date",null);
            }
            if (searchSRVLog.getToDate()!=null){
                cs.setDate("p_to_date",SysManager.getSQLDate(searchSRVLog.getToDate()));            
            }else{
                cs.setDate("p_to_date",null);
            }
            cs.setString("p_order_by",orderBy);
            cs.setString("p_order_dir",orderDir);
 
            cs.setInt("p_start_rec", startRec);
            cs.setInt("p_page_size",pageSize);
           

            // excute the query to get 2 result sets
            cs.execute();
            
            // get the first result set containing the no. of records
            rs = cs.getResultSet();
            rs.next();
            String recordSize = rs.getString("count");
            srvLogList.add(recordSize);
            
            // get the second result set's data and put into List
            cs.getMoreResults();
            rs = cs.getResultSet();           

            while (rs.next()) {
                LogInfo srvLog = new LogInfo();
                srvLog.setLogId(rs.getInt("log_id"));
                srvLog.setUserId(rs.getString("user_id"));                
                srvLog.setDPDeptId(rs.getString("dp_department_id"));
                srvLog.setSOADeptId(rs.getString("soa_department_id"));
                srvLog.setDeptName(rs.getString("bd_name"));
                srvLog.setActivityDateTime(rs.getTimestamp("date_time").toString());
                srvLog.setServiceCategoryGroup(rs.getString("service_category_group"));
                srvLog.setProjectFilePart(rs.getString("project_file_part"));
                srvLog.setProjectFileNo(rs.getString("project_file_no"));
                srvLog.setQSEnquiryInd(rs.getString("show_subscore_rpt"));                
                srvLogList.add(srvLog);
            }
        } catch (Exception ex) {
            String error =
                    "DB ERROR: LogDataBean:searchSRVLog\n"
                    + ex.getMessage();
            logger.error(error,ex);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return srvLogList;
        
    }
    
    public LogInfo selectSRVLogFile(int log_id) throws SysException {
        
        LogInfo srvLog = new LogInfo();
        try {
            CallableStatement cs = null;
            cs = getCStmt(SELECT_SRV_LOG_FILE);
            cs.setInt("p_log_id",log_id);
            //execute
            rs = cs.executeQuery();
            // put information into info object
            if (rs.next()) {
                srvLog.setStaffRateValidationReport(rs.getBlob("ceiling_rate_file"));
            }
        } catch (Exception ex) {
            String error =
                    "DB ERROR: LogDataBean:selectSRVLogFile\n" + ex.getMessage();
            logger.error(error,ex);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return srvLog;
        
    }
    
    public List searchAdminLog(LogInfo searchAdminLog,String orderBy, String orderDir,int startRec, int pageSize) throws SysException {
        List adminLogList = new ArrayList();
        try {
            CallableStatement cs = null;
            cs = getCStmt(SEARCH_ADMIN_LOG);
            cs.setString("p_user_id",searchAdminLog.getUserId());            
            cs.setString("p_activity",searchAdminLog.getActivity());           
            if (searchAdminLog.getFromDate()!=null){
                cs.setDate("p_from_date",SysManager.getSQLDate(searchAdminLog.getFromDate()));
            }else{
                cs.setDate("p_from_date",null);
            }
            if (searchAdminLog.getToDate()!=null){
                cs.setDate("p_to_date",SysManager.getSQLDate(searchAdminLog.getToDate()));            
            }else{
                cs.setDate("p_to_date",null);
            }
            cs.setString("p_order_by",orderBy);
            cs.setString("p_order_dir",orderDir);            
            cs.setInt("p_start_rec", startRec);
            cs.setInt("p_page_size",pageSize);
           

            // excute the query to get 2 result sets
            cs.execute();
            
            // get the first result set containing the no. of records
            rs = cs.getResultSet();
            rs.next();
            String recordSize = rs.getString("count");
            adminLogList.add(recordSize);
            
            // get the second result set's data and put into List
            cs.getMoreResults();
            rs = cs.getResultSet();           

            while (rs.next()) {
                LogInfo adminLog = new LogInfo();
                adminLog.setLogId(rs.getInt("audit_trail_id"));
                adminLog.setUserId(rs.getString("user_id"));
                adminLog.setRecordKey(rs.getString("record_id"));
                adminLog.setRemarks(rs.getString("remarks"));
                adminLog.setActivity(rs.getString("function_id"));
                adminLog.setActivityDateTime(rs.getTimestamp("date_time").toString());
                adminLogList.add(adminLog);
            }
        } catch (Exception ex) {
            String error =
                    "DB ERROR: LogDataBean:searchAdminLog\n"
                    + ex.getMessage();
            logger.error(error,ex);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return adminLogList;
        
    } 

    /**
     *  Write Access Log to Database
     */
    public void writeAccessLog(UserStatus uStatus, SecurityContext secCtx)
    		throws SecurityException,SysException {
             
     Connection connection = null;
     CallableStatement cs = null;
     int returnCode =0;
     try {
         connection = this.getConnection();
         cs = connection.prepareCall(WRITE_ACCESS_LOG);
         cs.setString("p_user_id",secCtx.getUserId());
         cs.setString("p_dp_dept_id",secCtx.getDPDeptId());            
         String remarks = "SUCCESS";
         if (uStatus.isLocked()){
             remarks = "LOCKED";                
         }else if (uStatus.isExpired()){
             remarks = "EXPIRED";
         }
         
         cs.setString("p_remarks",remarks);
         cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
         rs = cs.executeQuery();
         returnCode = cs.getInt("return_code");
         return;
     } catch (SQLException ex) {
         String error =
                 "SecurityDataBean:writeAccessLog: SQL Exception: \r\n"+
                 "SQLState:" + ex.getSQLState() +":" + ex.getMessage();
         logger.error(error,ex);
         throw new SecurityException(error);
     } catch (Exception ex) {
         String error =
                 "SecurityDataBean:writeAccessLog\r\n" + ex.getMessage();
         logger.error(error,ex);
         throw new SecurityException(error);
     } finally {
         super.close();
     }

    }
    
    public void insertChangePasswordLog(String userID, String dpDeptID, String soaDeptID, String isSuccess)
    throws SysException 
    {            
        Connection cnn  = null;

        try 
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement cs = this.getCStmt(INSERT_CHANGE_PASSWORD_LOG);    
            
            cs.setString("p_user_id", userID);
            cs.setString("p_dp_department_id", dpDeptID);
            cs.setString("p_soa_department_id", soaDeptID);
            cs.setString("p_is_success", isSuccess);           

            cs.executeUpdate();
            
            cnn.commit(); 
        }
        catch (Exception ex)
        {
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { throw new SysException("SecurityDataBean: insertChallengeLog\n", ex2.getMessage()); }
            
            throw new SysException("SecurityDataBean: insertChallengeLog\n", ex.getMessage());
        }
        finally
        { super.close(); }   
    }
    
    
    public void insertAdminLog(String userID, String dpDeptID, String soaDeptID, String entityName, String functionId, String recordKey, String remarks)
    throws SysException 
    {            
        Connection cnn  = null;

        try 
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement cs = this.getCStmt(INSERT_ADMIN_LOG);    
            
            cs.setString("p_user_id", userID);
            cs.setString("p_dp_department_id", dpDeptID);
            cs.setString("p_soa_department_id", soaDeptID);
            cs.setString("p_entity_name", entityName);
            cs.setString("p_function_id", functionId);
            cs.setString("p_record_key", recordKey);
            cs.setString("p_remarks", remarks);

            cs.executeUpdate();
            
            cnn.commit(); 
        }
        catch (Exception ex)
        {
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { throw new SysException("LogDataBean: insertAdminReportLog\n", ex2.getMessage()); }
            
            throw new SysException("LogDataBean: insertAdminReportLog\n", ex.getMessage());
        }
        finally
        { super.close(); }   
    }
}
