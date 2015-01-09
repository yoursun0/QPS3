package qpses.business;

import org.apache.log4j.Logger;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import qpses.util.*;

public class ReportDBAdmin extends DataBean
{
    // Get class name
    private static String MyClassName = ReportDBAdmin.class.getName();
    
    // Set up Log4j
    private static Logger logger = Logger.getLogger(MyClassName);
    
    // Stored procedure or query
    private static final String SEL_EXCESS_WA_ACCESS      = "{call sp_select_excess_wa_access()}";
    private static final String SEL_WA_ACCESS_MT_ONE_USER = "{call sp_select_wa_access_mt_one_user()}";
    private static final String SEL_FUNCTION_ACCESS       = "{call sp_select_function_access()}";
    
    public ReportDBAdmin() throws SysException
    { super("ADMIN"); }
    
    public List getFunctionAccess() throws SysException
    {
        String myName = "[" + MyClassName + "." + "getFunctionAccess" + "]";
        logger.debug(myName + " " + "started");
        
        Connection cnn = null;
        List rptInfo = new ArrayList();
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(SEL_FUNCTION_ACCESS);

            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();

            while (rs.next())
            {
                rptInfo.add(rs.getString("privilege_name"));
                rptInfo.add(rs.getString("remarks"));
                rptInfo.add(rs.getString("user_id"));
                rptInfo.add(SysManager.getStringfromSQLDate(rs.getDate("date_time")) + " " + rs.getTime("date_time"));
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
        
        return rptInfo;
    }    
    
    public List getWaAccessMTOneUser() throws SysException
    {
        String myName = "[" + MyClassName + "." + "getWaAccessMTOneUser" + "]";
        logger.debug(myName + " " + "started");
        
        Connection cnn = null;
        List rptInfo = new ArrayList();
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(SEL_WA_ACCESS_MT_ONE_USER);

            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            while (rs.next())
            {
                rptInfo.add(rs.getString("service_category_group"));                      
                rptInfo.add(rs.getString("work_assignment_title"));          
                rptInfo.add(rs.getString("privilege_name"));
                rptInfo.add(rs.getString("bd_name"));
                rptInfo.add(SysManager.getStringfromSQLDate(rs.getDate("issue_date")));
                rptInfo.add(SysManager.getStringfromSQLDate(rs.getDate("closing_date")));
                rptInfo.add(rs.getString("user_id"));
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
        
        return rptInfo;
    }
    
    public List getExcessWaAccess() throws SysException
    {
        String myName = "[" + MyClassName + "." + "getExcessWaAccess" + "]";
        logger.debug(myName + " " + "started");
        
        Connection cnn = null;
        List rptInfo = new ArrayList();
        
        try
        {
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            CallableStatement aCStmt = this.getCStmt(SEL_EXCESS_WA_ACCESS);

            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            while (rs.next())
            {
                rptInfo.add(rs.getString("service_category_group"));
                rptInfo.add(rs.getString("work_assignment_title"));
                rptInfo.add(rs.getString("privilege_name"));
                rptInfo.add(rs.getString("bd_name"));
                rptInfo.add(SysManager.getStringfromSQLDate(rs.getDate("issue_date")));
                rptInfo.add(SysManager.getStringfromSQLDate(rs.getDate("closing_date")));
                rptInfo.add(rs.getString("user_id"));                
                rptInfo.add(rs.getString("no_of_access"));
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
        
        return rptInfo;
    }
}