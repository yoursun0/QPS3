package qpses.business;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import qpses.util.SysException;
import qpses.util.DataBean;
import qpses.util.SysManager;

public class DiscountRateDBUser extends DataBean
{
    // Get class name
    private static String MyClassName = DiscountRateDBUser.class.getName();    

    // Set up Log4j    
    static Logger logger = Logger.getLogger(DiscountRateDBUser.class.getName());

    // Function ID
    private static String FunctionID = "DRENQ";
    
    // Stored procedure or query
    private static final String SELECT_DISCOUNT_RATE     = "{call sp_select_discount_rate_user(?, ?, ?, ?, ?, ?)}";
    private static final String SELECT_DISCOUNT_RATE_EFF = "{call sp_select_discount_rate_eff_user()}";       
    
    ResultSet rs = null;
    
    /** Constructor for ProductDataBean */
    public DiscountRateDBUser() throws SysException
    { super("USER"); }

    
    public List<String> getDiscountRateEff() throws SysException
    {
        String myName = "[" + MyClassName + "." + "getDiscountRateEff" + "]";
        logger.debug(myName + " " + "started");
        
        List<String> allDREff = new ArrayList<String>();
        
        try
        {            
            CallableStatement aCStmt = this.getCStmt(SELECT_DISCOUNT_RATE_EFF);
            aCStmt.executeUpdate();
            
            ResultSet rs = aCStmt.getResultSet();
            
            while (rs.next())
            {
                allDREff.add(rs.getString("service_category_group"));
                allDREff.add(SysManager.getStringfromSQLDate(rs.getDate("effective_date")));
            }
        }
        catch (Exception ex)
        { throw new SysException(myName, ex.getMessage()); }
        finally
        { super.close(); }        
                
        logger.debug(myName + " " + "ended");
        
        return allDREff;
    }

    
    public DiscountRateInfo getDiscountRate(String scg, String effectiveDate, String userId, String dpDeptId, String soaDeptId) throws SysException
    {
        String myName = "[" + MyClassName + "." + "getDiscountRate" + "]";
        logger.debug(myName + " " + "started");
        
        Connection cnn = null;
        
        CallableStatement aCStmt = null;
        DiscountRateInfo aDiscountRateInfo = null;
        
        try
        {            
            cnn = this.getConnection();
            cnn.setAutoCommit(false);
            
            aCStmt = this.getCStmt(SELECT_DISCOUNT_RATE);
            aCStmt.setString("p_scg", scg);
            aCStmt.setDate("p_effective_date", SysManager.getSQLDate(effectiveDate));
            aCStmt.setString("p_user_id", userId);
            aCStmt.setString("p_dp_department_id", dpDeptId);
            aCStmt.setString("p_soa_department_id", soaDeptId);
            aCStmt.setString("p_function_id", FunctionID);         
            
            aCStmt.executeUpdate();
            rs = aCStmt.getResultSet();
            
            if (rs.next())
            {
                aDiscountRateInfo = new DiscountRateInfo();
                aDiscountRateInfo.setPDFFile(rs.getBlob("pdf_file"));
                aDiscountRateInfo.setPDFFileName(rs.getString("file_name"));
            }
            
            if (aDiscountRateInfo == null)
                throw new SysException("Cannot find current discount rate for service category/group [" + scg + "]");
            
            cnn.commit();
        }
        catch (SysException ex)
        {            
            try
            { if (cnn != null && !cnn.getAutoCommit()) cnn.rollback(); }
            catch (Exception ex2)
            { throw new SysException(myName, ex2.getMessage()); }
            
            throw new SysException(ex.getMessage());
        }
        catch (Exception ex)
        { throw new SysException(ex.getMessage()); }
        finally
        { super.close(); }
        
        logger.debug(myName + " " + "ended");
        return aDiscountRateInfo;
    }
}