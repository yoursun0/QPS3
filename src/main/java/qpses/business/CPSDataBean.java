/*
 * WorkAssignmentDataBean.java
 */

package qpses.business;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import org.apache.log4j.Logger;

import qpses.util.Constant;
import qpses.util.SysException;
import qpses.util.DataBean;

public class CPSDataBean extends DataBean {
    
    // log4j
    static Logger logger = Logger.getLogger(CPSDataBean.class.getName());
    
    private static final String SELECT_CPAR_SCORE =
            "{Call sp_select_cps_score(?,?,?)}";
    private static final String COUNT_CPAR_PER_CAT =
            "{Call sp_count_cps_per_cat(?,?)}";
      
    ResultSet rs = null;
    
    /**
     * CPS for CPSDataBean
     */
    public CPSDataBean() throws SysException {
        super("ADMIN");
    }
    
	
    public String genCPSScore(String contractorId, String Cat, Date cutOffDate, String cpsAverageScore) throws SysException {    
    	double sumValue = 0;
        int sumCount = 0;
        String result = "";
        
        try {
        	CallableStatement cs = null;
            cs = getCStmt(SELECT_CPAR_SCORE);
            cs.setString("p_service_category_group", Cat);
            cs.setString("p_contractor_id", contractorId);
            cs.setDate("p_end_date", cutOffDate);
            rs = cs.executeQuery();
            while (rs.next()) {
            	sumValue = sumValue +  rs.getDouble("score");
        		sumCount++;
            }
        	
        	DecimalFormat myFormatter = new DecimalFormat("#.00");
        	if (sumCount == 0){
        		if ( Constant.CPS_DEFAULT_MARK > Double.parseDouble(cpsAverageScore) ){
        			result = cpsAverageScore;
        		} else {
        			//result = String.valueOf(Constant.CPS_DEFAULT_MARK);
        			result = (String)myFormatter.format( Constant.CPS_DEFAULT_MARK );
        		}
        			
        	} else {
        		result = (String)myFormatter.format( sumValue/sumCount );
        	}
        	
        } catch (Exception ex) {
            String error = "DB ERROR: CPSDataBean:genCPSScore\n" + ex.getMessage();
            throw new SysException(error);
        } finally { super.close();}
        
        return result;
    }  
    
    public String genCPSAverageScorePerCat(String Cat, Date cutOffDate) throws SysException {    
    	double sumValue = 0;
        int sumCount = 0;
        String result = "";
        
        try {
        	CallableStatement cs = null;
            cs = getCStmt(COUNT_CPAR_PER_CAT);
            cs.setString("p_service_category_group", Cat);
            cs.setDate("p_end_date", cutOffDate);
            rs = cs.executeQuery();
            while (rs.next()) {
            	sumValue = sumValue +  rs.getDouble("score");
        		sumCount++;
            }
        	
        	DecimalFormat myFormatter = new DecimalFormat("#.00");
        	if (sumCount == 0){
        		//result = String.valueOf(Constant.CPS_DEFAULT_MARK);
        		result = (String)myFormatter.format( Constant.CPS_DEFAULT_MARK );
        	} else {
        		result = (String)myFormatter.format( sumValue/sumCount );
        	}
        	
        } catch (Exception ex) {
            String error = "DB ERROR: CPSDataBean:genCPSAverageScorePerCat\n" + ex.getMessage();
            throw new SysException(error);
        } finally { super.close();}
        
        return result;
    }  
    
    public static void main(String[] args){
    	DecimalFormat myFormatter = new DecimalFormat("#.00");
    	String result = (String)myFormatter.format( 3.1 );
    	System.out.println(result);
    }
}