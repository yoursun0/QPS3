/*
 * CeilingRateDataBean.java
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
import qpses.util.DataBean;
import qpses.util.SysException;
import qpses.util.SysManager;

public class CeilingRateDataBean extends DataBean {
    
    
    // log4j
    static Logger logger = Logger.getLogger(CeilingRateDataBean.class.getName());
    
    private static final String SEARCH_CEILING_RATE =
            "{Call sp_search_ceiling_rate(?,?,?,?,?)}";  // 5 parameters
    private static final String SELECT_CEILING_RATE =
            "{Call sp_select_ceiling_rate(?,?)}";
    private static final String SELECT_CEILING_RATE_1_DATA =
            "{Call sp_select_ceiling_rate_1_datat(?)}";
    private static final String SELECT_CEILING_RATE_BY_KEYS =
            "{Call sp_select_ceiling_rate_by_keys(?,?)}";
    private static final String SELECT_EFFECTIVE_DATE =
            "{Call sp_select_effective_date_of_ceiling_rate()}";
    private static final String SELECT_CEILING_RATE_ADMIN =
            "{Call sp_select_ceiling_rate_admin(?,?)}";
    private static final String CREATE_TEMP_CEILING_RATE =
            "{Call sp_create_temp_ceiling_rate(?,?)}";
    private static final String DELETE_TEMP_CEILING_RATE =
            "{Call sp_delete_temp_ceiling_rate(?,?)}";
    private static final String REPLACE_TEMP_CEILING_RATE =
            "{Call sp_replace_temp_ceiling_rate(?,?)}";
    
    private static final String INSERT_CEILING_RATE_1 =
            "{Call sp_insert_ceiling_rate_1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; // 40 parameters
    private static final String INSERT_CEILING_RATE_2 =
            "{Call sp_insert_ceiling_rate_2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
            +"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
            +"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; // 106 parameters
    private static final String INSERT_CEILING_RATE_3 =
            "{Call sp_insert_ceiling_rate_3(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
            +"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
            +"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
            +"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; // 145 parameters
    private static final String INSERT_CEILING_RATE_4 =
            "{Call sp_insert_ceiling_rate_4(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; // 39 parameters
    private static final String DELETE_CEILING_RATE =
            "{Call sp_delete_ceiling_rate(?,?,?,?,?,?)}";
    private static final String RELEASE_CEILING_RATE =
            "{Call sp_release_ceiling_rate(?,?,?,?,?,?)}";
    private static final String WRITE_AUDIT_TRAIL =
            "{Call sp_write_ceiling_rate_audit_trail(?,?,?,?,?,?)}";
    
    
    
    ResultSet rs = null;
    
    /**
     * Constructor for CeilingRateDataBean
     */
    public CeilingRateDataBean()throws SysException {
        super("ADMIN");
    }
    
    public CeilingRateInfo selectCeilingRateByKeys(String service_category, String effective_date) throws SysException {
        
        CeilingRateInfo cr = new CeilingRateInfo();
        try {
            CallableStatement cs = null;
            cs = getCStmt(SELECT_CEILING_RATE_BY_KEYS);
            
            //execute
            cs.setDate("p_effective_date",SysManager.getSQLDate(effective_date));
            cs.setString("p_service_category",service_category);
            rs = cs.executeQuery();
            
            // put information into info object
            while (rs.next()) {
                cr.setServiceCategory(service_category);
                cr.setEffectiveDate(rs.getDate("effective_date"));
                cr.setActiveInd(rs.getInt("active_indicator"));
                cr.setCreatedDate(rs.getTimestamp("created_datetime").toString());
                cr.setLastUpdatedDate(rs.getTimestamp("last_updated_datetime").toString());
                cr.setLastUpdatedBy(rs.getString("last_updated_by_user"));
            }
        } catch (Exception ex) {
            String error =
                    "DB ERROR: CeilingRateDataBean:selectCeilingRateByKeys\n"
                    + ex.getMessage();
            logger.error(error,ex);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return cr;
        
    }
    
    public List selectEffectiveDate() throws SysException {
        
        List allEffectiveDateList = new ArrayList();
        try {
            CallableStatement cs = null;
            cs = getCStmt(SELECT_EFFECTIVE_DATE);
            //execute
            rs = cs.executeQuery();
            // put information into List
            while (rs.next()) {
                allEffectiveDateList.add(rs.getDate("effective_date"));
            }
        } catch (Exception ex) {
            String error =
                    "DB ERROR: CeilingRateDataBean:selectEffectiveDate\n" + ex.getMessage();
            logger.error(error,ex);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return allEffectiveDateList;
        
    }
    
    public List selectCeilingRate(String order_by, String order_dir) throws SysException {
        
        List allCeilingRateList = new ArrayList();
        try {
            CallableStatement cs = null;
            cs = getCStmt(SELECT_CEILING_RATE);
            cs.setString("p_order_by",order_by);
            cs.setString("p_order_dir",order_dir);                                    
            rs = cs.executeQuery();
            
            // put information into info object
            while (rs.next()) {
                CeilingRateInfo cr = new CeilingRateInfo();
                cr.setServiceCategory(rs.getString("service_category"));
                cr.setEffectiveDate(rs.getDate("effective_date"));
                cr.setActiveInd(rs.getInt("active_indicator"));
                allCeilingRateList.add(cr);
            }
        } catch (Exception ex) {
            String error =
                    "DB ERROR: CeilingRateDataBean:selectCeilingRate1\n"
                    + ex.getMessage();
            logger.error(error,ex);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return allCeilingRateList;
        
    }
    
    public List searchCeilingRate(CeilingRateInfo searchCR,String order_by, String order_dir) throws SysException {
        
        List CeilingRateList = new ArrayList();
        try {
            CallableStatement cs = null;
            cs = getCStmt(SEARCH_CEILING_RATE);
            
            cs.setDate("p_effective_date",searchCR.getEffectiveDate());
            cs.setString("p_service_category",searchCR.getServiceCategory());
            cs.setInt("p_active_ind",searchCR.getActiveInd());
            cs.setString("p_order_by",order_by);
            cs.setString("p_order_dir",order_dir);                                                
            // execute
            rs = cs.executeQuery();
            
            // get data and put into List
            while (rs.next()) {
                CeilingRateInfo cr = new CeilingRateInfo();
                cr.setServiceCategory(rs.getString("service_category"));
                cr.setEffectiveDate(rs.getDate("effective_date"));
                cr.setActiveInd(rs.getInt("active_indicator"));
                CeilingRateList.add(cr);
            }
        } catch (Exception ex) {
            String error =
                    "DB ERROR: CeilingRateDataBean:searchCeilingRate\n"
                    + ex.getMessage();
            logger.error(error,ex);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return CeilingRateList;
        
    }
    
    public int createTempCeilingRate(String serviceCategory) throws SysException {
        
        Connection connection = null;
        CallableStatement cs = null;
        int result=0;
        
        try {
            connection = this.getConnection();
            cs = connection.prepareCall(CREATE_TEMP_CEILING_RATE);
            cs.setString("p_service_category",serviceCategory);
            cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
            cs.executeUpdate();
            result = cs.getInt("return_code");
            if (result !=1){
                String error = "Problem in creating temporary Ceiling Rate file";
                throw new SysException(error);
            }
        } catch (SQLException ex0) {
            String error = "CeilingRateDataBean:createTempCeilingRate: SQL Error\n" +
                    "SQLState:"+ ex0.getSQLState() +":"+ex0.getMessage();
            logger.error(error,ex0);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return result;
    }
    
    public int deleteTempCeilingRate(String serviceCategory) throws SysException {
        
        Connection connection = null;
        CallableStatement cs = null;
        int result=0;
        
        try {
            connection = this.getConnection();
            cs = connection.prepareCall(DELETE_TEMP_CEILING_RATE);
            cs.setString("p_service_category",serviceCategory);
            cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
            cs.executeUpdate();
            result = cs.getInt("return_code");
            if (result !=1){
                String error = "Problem in deleting temporary Ceiling Rate file";
                throw new SysException(error);
            }
        } catch (SQLException ex0) {
            String error = "CeilingRateDataBean:deleteTempCeilingRate: SQL Error\n" +
                    "SQLState:"+ ex0.getSQLState() +":"+ex0.getMessage();
            logger.error(error,ex0);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return result;
    }
    
    public int replaceTempCeilingRate(String serviceCategory) throws SysException {
        
        Connection connection = null;
        CallableStatement cs = null;
        int result=0;
        
        try {
            connection = this.getConnection();
            cs = connection.prepareCall(REPLACE_TEMP_CEILING_RATE);
            cs.setString("p_service_category",serviceCategory);
            cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
            cs.executeUpdate();
            result = cs.getInt("return_code");
            if (result !=1){
                String error = "Problem in replacing temporary Ceiling Rate file";
                throw new SysException(error);
            }
        } catch (SQLException ex0) {
            String error = "CeilingRateDataBean:replaceTempCeilingRate: SQL Error\n" +
                    "SQLState:"+ ex0.getSQLState() +":"+ex0.getMessage();
            logger.error(error,ex0);
            throw new SysException(error);
        } finally {
            super.close();
        }
        return result;
    }
    
    public int insertCeilingRate1(CeilingRate1Info cr) throws SysException {
        
        Connection connection = null;
        CallableStatement cs = null;
        
        int result = 0;
        int returnCode=0;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
            cs = connection.prepareCall(INSERT_CEILING_RATE_1);
            // get information from info object
            cs.setDate("p_effective_date",cr.getEffectiveDate());
            cs.setString("p_contractor_id",cr.getContractorId());
            cs.setString("p_currency",cr.getCurrency());
            cs.setInt("p_active_ind",cr.getActiveInd());
            cs.setFloat("p_sc3_resident",cr.getsc3Resident());
            cs.setFloat("p_sc4_resident",cr.getsc4Resident());
            cs.setFloat("p_sc5_resident",cr.getsc5Resident());
            cs.setFloat("p_sc6_resident",cr.getsc6Resident());
            cs.setFloat("p_sc7_resident",cr.getsc7Resident());
            cs.setFloat("p_sc8_resident",cr.getsc8Resident());
            cs.setFloat("p_sc9_resident",cr.getsc9Resident());
            cs.setFloat("p_sc10_resident",cr.getsc10Resident());
            cs.setFloat("p_sc11_resident",cr.getsc11Resident());
            cs.setFloat("p_sc12_resident",cr.getsc12Resident());
            cs.setFloat("p_sc3_non_resident",cr.getsc3NonResident());
            cs.setFloat("p_sc4_non_resident",cr.getsc4NonResident());
            cs.setFloat("p_sc5_non_resident",cr.getsc5NonResident());
            cs.setFloat("p_sc6_non_resident",cr.getsc6NonResident());
            cs.setFloat("p_sc7_non_resident",cr.getsc7NonResident());
            cs.setFloat("p_sc8_non_resident",cr.getsc8NonResident());
            cs.setFloat("p_sc9_non_resident",cr.getsc9NonResident());
            cs.setFloat("p_sc10_non_resident",cr.getsc10NonResident());
            cs.setFloat("p_sc11_non_resident",cr.getsc11NonResident());
            cs.setFloat("p_sc12_non_resident",cr.getsc12NonResident());
            cs.setFloat("p_sc3_offshore",cr.getsc3Offshore());
            cs.setFloat("p_sc4_offshore",cr.getsc4Offshore());
            cs.setFloat("p_sc5_offshore",cr.getsc5Offshore());
            cs.setFloat("p_sc6_offshore",cr.getsc6Offshore());
            cs.setFloat("p_sc7_offshore",cr.getsc7Offshore());
            cs.setFloat("p_sc8_offshore",cr.getsc8Offshore());
            cs.setFloat("p_sc9_offshore",cr.getsc9Offshore());
            cs.setFloat("p_sc10_offshore",cr.getsc10Offshore());
            cs.setFloat("p_sc11_offshore",cr.getsc11Offshore());
            cs.setFloat("p_sc12_offshore",cr.getsc12Offshore());
            cs.setInt("p_supp_ind",cr.getSuppInd());
            cs.setFloat("p_supp1_resident",cr.getSupp1Resident());
            cs.setFloat("p_supp1_non_resident",cr.getSupp1NonResident());
            cs.setFloat("p_supp1_offshore",cr.getSupp1Offshore());
            
            cs.setString("p_admin_id",cr.getLastUpdatedBy());
            cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
            
            //execute
            result =cs.executeUpdate();
            returnCode =cs.getInt("return_code");
            
            // check execution result
            String msg1 = "SQL error for Service Category 1!<BR>" +
                    		"Contractor: "+cr.getContractorId() + "; <BR> Effective Date: " + cr.getEffectiveDate().toString()+";";
            String msg2 = "No record was inserted for Service Category 1! <BR>"+
                    "Contractor: "+cr.getContractorId() + "; <BR> Effective Date: " + cr.getEffectiveDate().toString()+";";
            commitResult(returnCode, result, msg1, msg2);
            
            return returnCode;
        } catch (SQLException ex0) {
            String err = "CeilingRateDataBean:insertCeilingRate1: SQL Error\n" +"SQLState:"+ ex0.getSQLState() +":"+ ex0.getMessage();
            logger.error(err,ex0);
            try{
                connection.rollback();
                throw new SysException(err);
            }catch (SQLException ex1){
                err = "SQL Error: CeilingRateDataBean:insertCeilingRate1.rollback\n" + ex1.getMessage();
                throw new SysException(err);
            }
        } finally {
            super.close();
        }
    }
    
    public int insertCeilingRate2(CeilingRate2Info cr) throws SysException {
        
        Connection connection = null;
        CallableStatement cs = null;
        
        int result = 0;
        int returnCode=0;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
            cs = connection.prepareCall(INSERT_CEILING_RATE_2);
            // get information from info object
            cs.setDate("p_effective_date",cr.getEffectiveDate());
            cs.setString("p_contractor_id",cr.getContractorId());
            cs.setString("p_currency",cr.getCurrency());
            cs.setInt("p_active_ind",cr.getActiveInd());
            
            cs.setFloat("p_sc1_resident_m1",cr.getsc1ResidentM1());
            cs.setFloat("p_sc2_resident_m1",cr.getsc2ResidentM1());
            cs.setFloat("p_sc3_resident_m1",cr.getsc3ResidentM1());
            cs.setFloat("p_sc4_resident_m1",cr.getsc4ResidentM1());
            cs.setFloat("p_sc5_resident_m1",cr.getsc5ResidentM1());
            cs.setFloat("p_sc6_resident_m1",cr.getsc6ResidentM1());
            cs.setFloat("p_sc7_resident_m1",cr.getsc7ResidentM1());
            cs.setFloat("p_sc8_resident_m1",cr.getsc8ResidentM1());
            cs.setFloat("p_sc9_resident_m1",cr.getsc9ResidentM1());
            cs.setFloat("p_sc10_resident_m1",cr.getsc10ResidentM1());
            
            cs.setFloat("p_sc1_resident_m2",cr.getsc1ResidentM2());
            cs.setFloat("p_sc2_resident_m2",cr.getsc2ResidentM2());
            cs.setFloat("p_sc3_resident_m2",cr.getsc3ResidentM2());
            cs.setFloat("p_sc4_resident_m2",cr.getsc4ResidentM2());
            cs.setFloat("p_sc5_resident_m2",cr.getsc5ResidentM2());
            cs.setFloat("p_sc6_resident_m2",cr.getsc6ResidentM2());
            cs.setFloat("p_sc7_resident_m2",cr.getsc7ResidentM2());
            cs.setFloat("p_sc8_resident_m2",cr.getsc8ResidentM2());
            cs.setFloat("p_sc9_resident_m2",cr.getsc9ResidentM2());
            cs.setFloat("p_sc10_resident_m2",cr.getsc10ResidentM2());
            
            cs.setFloat("p_sc1_resident_m3",cr.getsc1ResidentM3());
            cs.setFloat("p_sc2_resident_m3",cr.getsc2ResidentM3());
            cs.setFloat("p_sc3_resident_m3",cr.getsc3ResidentM3());
            cs.setFloat("p_sc4_resident_m3",cr.getsc4ResidentM3());
            cs.setFloat("p_sc5_resident_m3",cr.getsc5ResidentM3());
            cs.setFloat("p_sc6_resident_m3",cr.getsc6ResidentM3());
            cs.setFloat("p_sc7_resident_m3",cr.getsc7ResidentM3());
            cs.setFloat("p_sc8_resident_m3",cr.getsc8ResidentM3());
            cs.setFloat("p_sc9_resident_m3",cr.getsc9ResidentM3());
            cs.setFloat("p_sc10_resident_m3",cr.getsc10ResidentM3());
            
            cs.setFloat("p_sc1_non_resident_m1",cr.getsc1NonResidentM1());
            cs.setFloat("p_sc2_non_resident_m1",cr.getsc2NonResidentM1());
            cs.setFloat("p_sc3_non_resident_m1",cr.getsc3NonResidentM1());
            cs.setFloat("p_sc4_non_resident_m1",cr.getsc4NonResidentM1());
            cs.setFloat("p_sc5_non_resident_m1",cr.getsc5NonResidentM1());
            cs.setFloat("p_sc6_non_resident_m1",cr.getsc6NonResidentM1());
            cs.setFloat("p_sc7_non_resident_m1",cr.getsc7NonResidentM1());
            cs.setFloat("p_sc8_non_resident_m1",cr.getsc8NonResidentM1());
            cs.setFloat("p_sc9_non_resident_m1",cr.getsc9NonResidentM1());
            cs.setFloat("p_sc10_non_resident_m1",cr.getsc10NonResidentM1());
            
            cs.setFloat("p_sc1_non_resident_m2",cr.getsc1NonResidentM2());
            cs.setFloat("p_sc2_non_resident_m2",cr.getsc2NonResidentM2());
            cs.setFloat("p_sc3_non_resident_m2",cr.getsc3NonResidentM2());
            cs.setFloat("p_sc4_non_resident_m2",cr.getsc4NonResidentM2());
            cs.setFloat("p_sc5_non_resident_m2",cr.getsc5NonResidentM2());
            cs.setFloat("p_sc6_non_resident_m2",cr.getsc6NonResidentM2());
            cs.setFloat("p_sc7_non_resident_m2",cr.getsc7NonResidentM2());
            cs.setFloat("p_sc8_non_resident_m2",cr.getsc8NonResidentM2());
            cs.setFloat("p_sc9_non_resident_m2",cr.getsc9NonResidentM2());
            cs.setFloat("p_sc10_non_resident_m2",cr.getsc10NonResidentM2());
            
            cs.setFloat("p_sc1_non_resident_m3",cr.getsc1NonResidentM3());
            cs.setFloat("p_sc2_non_resident_m3",cr.getsc2NonResidentM3());
            cs.setFloat("p_sc3_non_resident_m3",cr.getsc3NonResidentM3());
            cs.setFloat("p_sc4_non_resident_m3",cr.getsc4NonResidentM3());
            cs.setFloat("p_sc5_non_resident_m3",cr.getsc5NonResidentM3());
            cs.setFloat("p_sc6_non_resident_m3",cr.getsc6NonResidentM3());
            cs.setFloat("p_sc7_non_resident_m3",cr.getsc7NonResidentM3());
            cs.setFloat("p_sc8_non_resident_m3",cr.getsc8NonResidentM3());
            cs.setFloat("p_sc9_non_resident_m3",cr.getsc9NonResidentM3());
            cs.setFloat("p_sc10_non_resident_m3",cr.getsc10NonResidentM3());
            
            cs.setFloat("p_sc1_offshore_m1",cr.getsc1OffshoreM1());
            cs.setFloat("p_sc2_offshore_m1",cr.getsc2OffshoreM1());
            cs.setFloat("p_sc3_offshore_m1",cr.getsc3OffshoreM1());
            cs.setFloat("p_sc4_offshore_m1",cr.getsc4OffshoreM1());
            cs.setFloat("p_sc5_offshore_m1",cr.getsc5OffshoreM1());
            cs.setFloat("p_sc6_offshore_m1",cr.getsc6OffshoreM1());
            cs.setFloat("p_sc7_offshore_m1",cr.getsc7OffshoreM1());
            cs.setFloat("p_sc8_offshore_m1",cr.getsc8OffshoreM1());
            cs.setFloat("p_sc9_offshore_m1",cr.getsc9OffshoreM1());
            cs.setFloat("p_sc10_offshore_m1",cr.getsc10OffshoreM1());
            
            cs.setFloat("p_sc1_offshore_m2",cr.getsc1OffshoreM2());
            cs.setFloat("p_sc2_offshore_m2",cr.getsc2OffshoreM2());
            cs.setFloat("p_sc3_offshore_m2",cr.getsc3OffshoreM2());
            cs.setFloat("p_sc4_offshore_m2",cr.getsc4OffshoreM2());
            cs.setFloat("p_sc5_offshore_m2",cr.getsc5OffshoreM2());
            cs.setFloat("p_sc6_offshore_m2",cr.getsc6OffshoreM2());
            cs.setFloat("p_sc7_offshore_m2",cr.getsc7OffshoreM2());
            cs.setFloat("p_sc8_offshore_m2",cr.getsc8OffshoreM2());
            cs.setFloat("p_sc9_offshore_m2",cr.getsc9OffshoreM2());
            cs.setFloat("p_sc10_offshore_m2",cr.getsc10OffshoreM2());
            
            cs.setFloat("p_sc1_offshore_m3",cr.getsc1OffshoreM3());
            cs.setFloat("p_sc2_offshore_m3",cr.getsc2OffshoreM3());
            cs.setFloat("p_sc3_offshore_m3",cr.getsc3OffshoreM3());
            cs.setFloat("p_sc4_offshore_m3",cr.getsc4OffshoreM3());
            cs.setFloat("p_sc5_offshore_m3",cr.getsc5OffshoreM3());
            cs.setFloat("p_sc6_offshore_m3",cr.getsc6OffshoreM3());
            cs.setFloat("p_sc7_offshore_m3",cr.getsc7OffshoreM3());
            cs.setFloat("p_sc8_offshore_m3",cr.getsc8OffshoreM3());
            cs.setFloat("p_sc9_offshore_m3",cr.getsc9OffshoreM3());
            cs.setFloat("p_sc10_offshore_m3",cr.getsc10OffshoreM3());
            
            cs.setInt("p_supp_ind",cr.getSuppInd());
            cs.setFloat("p_supp1_resident_m1",cr.getSupp1ResidentM1());
            cs.setFloat("p_supp1_non_resident_m1",cr.getSupp1NonResidentM1());
            cs.setFloat("p_supp1_offshore_m1",cr.getSupp1OffshoreM1());
            cs.setFloat("p_supp1_resident_m2",cr.getSupp1ResidentM2());
            cs.setFloat("p_supp1_non_resident_m2",cr.getSupp1NonResidentM2());
            cs.setFloat("p_supp1_offshore_m2",cr.getSupp1OffshoreM2());
            cs.setFloat("p_supp1_resident_m3",cr.getSupp1ResidentM3());
            cs.setFloat("p_supp1_non_resident_m3",cr.getSupp1NonResidentM3());
            cs.setFloat("p_supp1_offshore_m3",cr.getSupp1OffshoreM3());
            
            cs.setString("p_admin_id",cr.getLastUpdatedBy());
            cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
            
            // execute
            result =cs.executeUpdate();
            returnCode =cs.getInt("return_code");
            
         // check execution result
            String msg1 = "SQL error for Service Category 2!<BR>" +
                    		"Contractor: "+cr.getContractorId() + "; <BR> Effective Date: " + cr.getEffectiveDate().toString()+";";
            String msg2 = "No record was inserted for Service Category 2! <BR>"+
                    "Contractor: "+cr.getContractorId() + "; <BR> Effective Date: " + cr.getEffectiveDate().toString()+";";
            commitResult(returnCode, result, msg1, msg2);
            
            return returnCode;
            
        } catch (SQLException ex0) {
            String err = "CeilingRateDataBean:insertCeilingRate2: SQL Error\n" +"SQLState:"+ ex0.getSQLState() +":"+ ex0.getMessage();
            logger.error(err,ex0);
            try{
                connection.rollback();
                throw new SysException(err);
            }catch (SQLException ex1){
                err = "SQL Error: CeilingRateDataBean:insertCeilingRate2.rollback\n" + ex1.getMessage();
                throw new SysException(err);
            }
            
        } finally {
            super.close();
        }
    }
    
    public int insertCeilingRate3(CeilingRate3Info cr) throws SysException {
        
        Connection connection = null;
        CallableStatement cs = null;
        
        int result = 0;
        int returnCode=0;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
            cs = connection.prepareCall(INSERT_CEILING_RATE_3);
            // get information from info object
            cs.setDate("p_effective_date",cr.getEffectiveDate());
            cs.setString("p_contractor_id",cr.getContractorId());
            cs.setString("p_currency",cr.getCurrency());
            cs.setInt("p_active_ind",cr.getActiveInd());
            cs.setFloat("p_oneoff_sc1_resident",cr.getOneoffSc1Resident());
            cs.setFloat("p_oneoff_sc2_resident",cr.getOneoffSc2Resident());
            cs.setFloat("p_oneoff_sc3_resident",cr.getOneoffSc3Resident());
            cs.setFloat("p_oneoff_sc4_resident",cr.getOneoffSc4Resident());
            cs.setFloat("p_oneoff_sc5_resident",cr.getOneoffSc5Resident());
            cs.setFloat("p_oneoff_sc6_resident",cr.getOneoffSc6Resident());
            cs.setFloat("p_oneoff_sc7_resident",cr.getOneoffSc7Resident());
            cs.setFloat("p_oneoff_sc8_resident",cr.getOneoffSc8Resident());
            cs.setFloat("p_oneoff_sc9_resident",cr.getOneoffSc9Resident());
            cs.setFloat("p_oneoff_sc10_resident",cr.getOneoffSc10Resident());
            cs.setFloat("p_oneoff_sc11_resident",cr.getOneoffSc11Resident());
            cs.setFloat("p_oneoff_sc12_resident",cr.getOneoffSc12Resident());
            cs.setFloat("p_oneoff_sc1_non_resident",cr.getOneoffSc1NonResident());
            cs.setFloat("p_oneoff_sc2_non_resident",cr.getOneoffSc2NonResident());
            cs.setFloat("p_oneoff_sc3_non_resident",cr.getOneoffSc3NonResident());
            cs.setFloat("p_oneoff_sc4_non_resident",cr.getOneoffSc4NonResident());
            cs.setFloat("p_oneoff_sc5_non_resident",cr.getOneoffSc5NonResident());
            cs.setFloat("p_oneoff_sc6_non_resident",cr.getOneoffSc6NonResident());
            cs.setFloat("p_oneoff_sc7_non_resident",cr.getOneoffSc7NonResident());
            cs.setFloat("p_oneoff_sc8_non_resident",cr.getOneoffSc8NonResident());
            cs.setFloat("p_oneoff_sc9_non_resident",cr.getOneoffSc9NonResident());
            cs.setFloat("p_oneoff_sc10_non_resident",cr.getOneoffSc10NonResident());
            cs.setFloat("p_oneoff_sc11_non_resident",cr.getOneoffSc11NonResident());
            cs.setFloat("p_oneoff_sc12_non_resident",cr.getOneoffSc12NonResident());
            cs.setFloat("p_oneoff_sc1_offshore",cr.getOneoffSc1Offshore());
            cs.setFloat("p_oneoff_sc2_offshore",cr.getOneoffSc2Offshore());
            cs.setFloat("p_oneoff_sc3_offshore",cr.getOneoffSc3Offshore());
            cs.setFloat("p_oneoff_sc4_offshore",cr.getOneoffSc4Offshore());
            cs.setFloat("p_oneoff_sc5_offshore",cr.getOneoffSc5Offshore());
            cs.setFloat("p_oneoff_sc6_offshore",cr.getOneoffSc6Offshore());
            cs.setFloat("p_oneoff_sc7_offshore",cr.getOneoffSc7Offshore());
            cs.setFloat("p_oneoff_sc8_offshore",cr.getOneoffSc8Offshore());
            cs.setFloat("p_oneoff_sc9_offshore",cr.getOneoffSc9Offshore());
            cs.setFloat("p_oneoff_sc10_offshore",cr.getOneoffSc10Offshore());
            cs.setFloat("p_oneoff_sc11_offshore",cr.getOneoffSc11Offshore());
            cs.setFloat("p_oneoff_sc12_offshore",cr.getOneoffSc12Offshore());
            
            cs.setFloat("p_ongoing_sc1_resident_m1",cr.getOngoingSc1ResidentM1());
            cs.setFloat("p_ongoing_sc2_resident_m1",cr.getOngoingSc2ResidentM1());
            cs.setFloat("p_ongoing_sc3_resident_m1",cr.getOngoingSc3ResidentM1());
            cs.setFloat("p_ongoing_sc4_resident_m1",cr.getOngoingSc4ResidentM1());
            cs.setFloat("p_ongoing_sc5_resident_m1",cr.getOngoingSc5ResidentM1());
            cs.setFloat("p_ongoing_sc6_resident_m1",cr.getOngoingSc6ResidentM1());
            cs.setFloat("p_ongoing_sc7_resident_m1",cr.getOngoingSc7ResidentM1());
            cs.setFloat("p_ongoing_sc8_resident_m1",cr.getOngoingSc8ResidentM1());
            cs.setFloat("p_ongoing_sc9_resident_m1",cr.getOngoingSc9ResidentM1());
            cs.setFloat("p_ongoing_sc10_resident_m1",cr.getOngoingSc10ResidentM1());
            
            cs.setFloat("p_ongoing_sc1_resident_m2",cr.getOngoingSc1ResidentM2());
            cs.setFloat("p_ongoing_sc2_resident_m2",cr.getOngoingSc2ResidentM2());
            cs.setFloat("p_ongoing_sc3_resident_m2",cr.getOngoingSc3ResidentM2());
            cs.setFloat("p_ongoing_sc4_resident_m2",cr.getOngoingSc4ResidentM2());
            cs.setFloat("p_ongoing_sc5_resident_m2",cr.getOngoingSc5ResidentM2());
            cs.setFloat("p_ongoing_sc6_resident_m2",cr.getOngoingSc6ResidentM2());
            cs.setFloat("p_ongoing_sc7_resident_m2",cr.getOngoingSc7ResidentM2());
            cs.setFloat("p_ongoing_sc8_resident_m2",cr.getOngoingSc8ResidentM2());
            cs.setFloat("p_ongoing_sc9_resident_m2",cr.getOngoingSc9ResidentM2());
            cs.setFloat("p_ongoing_sc10_resident_m2",cr.getOngoingSc10ResidentM2());
            
            cs.setFloat("p_ongoing_sc1_resident_m3",cr.getOngoingSc1ResidentM3());
            cs.setFloat("p_ongoing_sc2_resident_m3",cr.getOngoingSc2ResidentM3());
            cs.setFloat("p_ongoing_sc3_resident_m3",cr.getOngoingSc3ResidentM3());
            cs.setFloat("p_ongoing_sc4_resident_m3",cr.getOngoingSc4ResidentM3());
            cs.setFloat("p_ongoing_sc5_resident_m3",cr.getOngoingSc5ResidentM3());
            cs.setFloat("p_ongoing_sc6_resident_m3",cr.getOngoingSc6ResidentM3());
            cs.setFloat("p_ongoing_sc7_resident_m3",cr.getOngoingSc7ResidentM3());
            cs.setFloat("p_ongoing_sc8_resident_m3",cr.getOngoingSc8ResidentM3());
            cs.setFloat("p_ongoing_sc9_resident_m3",cr.getOngoingSc9ResidentM3());
            cs.setFloat("p_ongoing_sc10_resident_m3",cr.getOngoingSc10ResidentM3());
            
            cs.setFloat("p_ongoing_sc1_non_resident_m1",cr.getOngoingSc1NonResidentM1());
            cs.setFloat("p_ongoing_sc2_non_resident_m1",cr.getOngoingSc2NonResidentM1());
            cs.setFloat("p_ongoing_sc3_non_resident_m1",cr.getOngoingSc3NonResidentM1());
            cs.setFloat("p_ongoing_sc4_non_resident_m1",cr.getOngoingSc4NonResidentM1());
            cs.setFloat("p_ongoing_sc5_non_resident_m1",cr.getOngoingSc5NonResidentM1());
            cs.setFloat("p_ongoing_sc6_non_resident_m1",cr.getOngoingSc6NonResidentM1());
            cs.setFloat("p_ongoing_sc7_non_resident_m1",cr.getOngoingSc7NonResidentM1());
            cs.setFloat("p_ongoing_sc8_non_resident_m1",cr.getOngoingSc8NonResidentM1());
            cs.setFloat("p_ongoing_sc9_non_resident_m1",cr.getOngoingSc9NonResidentM1());
            cs.setFloat("p_ongoing_sc10_non_resident_m1",cr.getOngoingSc10NonResidentM1());
            
            cs.setFloat("p_ongoing_sc1_non_resident_m2",cr.getOngoingSc1NonResidentM2());
            cs.setFloat("p_ongoing_sc2_non_resident_m2",cr.getOngoingSc2NonResidentM2());
            cs.setFloat("p_ongoing_sc3_non_resident_m2",cr.getOngoingSc3NonResidentM2());
            cs.setFloat("p_ongoing_sc4_non_resident_m2",cr.getOngoingSc4NonResidentM2());
            cs.setFloat("p_ongoing_sc5_non_resident_m2",cr.getOngoingSc5NonResidentM2());
            cs.setFloat("p_ongoing_sc6_non_resident_m2",cr.getOngoingSc6NonResidentM2());
            cs.setFloat("p_ongoing_sc7_non_resident_m2",cr.getOngoingSc7NonResidentM2());
            cs.setFloat("p_ongoing_sc8_non_resident_m2",cr.getOngoingSc8NonResidentM2());
            cs.setFloat("p_ongoing_sc9_non_resident_m2",cr.getOngoingSc9NonResidentM2());
            cs.setFloat("p_ongoing_sc10_non_resident_m2",cr.getOngoingSc10NonResidentM2());
            
            cs.setFloat("p_ongoing_sc1_non_resident_m3",cr.getOngoingSc1NonResidentM3());
            cs.setFloat("p_ongoing_sc2_non_resident_m3",cr.getOngoingSc2NonResidentM3());
            cs.setFloat("p_ongoing_sc3_non_resident_m3",cr.getOngoingSc3NonResidentM3());
            cs.setFloat("p_ongoing_sc4_non_resident_m3",cr.getOngoingSc4NonResidentM3());
            cs.setFloat("p_ongoing_sc5_non_resident_m3",cr.getOngoingSc5NonResidentM3());
            cs.setFloat("p_ongoing_sc6_non_resident_m3",cr.getOngoingSc6NonResidentM3());
            cs.setFloat("p_ongoing_sc7_non_resident_m3",cr.getOngoingSc7NonResidentM3());
            cs.setFloat("p_ongoing_sc8_non_resident_m3",cr.getOngoingSc8NonResidentM3());
            cs.setFloat("p_ongoing_sc9_non_resident_m3",cr.getOngoingSc9NonResidentM3());
            cs.setFloat("p_ongoing_sc10_non_resident_m3",cr.getOngoingSc10NonResidentM3());
            
            cs.setFloat("p_ongoing_sc1_offshore_m1",cr.getOngoingSc1OffshoreM1());
            cs.setFloat("p_ongoing_sc2_offshore_m1",cr.getOngoingSc2OffshoreM1());
            cs.setFloat("p_ongoing_sc3_offshore_m1",cr.getOngoingSc3OffshoreM1());
            cs.setFloat("p_ongoing_sc4_offshore_m1",cr.getOngoingSc4OffshoreM1());
            cs.setFloat("p_ongoing_sc5_offshore_m1",cr.getOngoingSc5OffshoreM1());
            cs.setFloat("p_ongoing_sc6_offshore_m1",cr.getOngoingSc6OffshoreM1());
            cs.setFloat("p_ongoing_sc7_offshore_m1",cr.getOngoingSc7OffshoreM1());
            cs.setFloat("p_ongoing_sc8_offshore_m1",cr.getOngoingSc8OffshoreM1());
            cs.setFloat("p_ongoing_sc9_offshore_m1",cr.getOngoingSc9OffshoreM1());
            cs.setFloat("p_ongoing_sc10_offshore_m1",cr.getOngoingSc10OffshoreM1());
            
            cs.setFloat("p_ongoing_sc1_offshore_m2",cr.getOngoingSc1OffshoreM2());
            cs.setFloat("p_ongoing_sc2_offshore_m2",cr.getOngoingSc2OffshoreM2());
            cs.setFloat("p_ongoing_sc3_offshore_m2",cr.getOngoingSc3OffshoreM2());
            cs.setFloat("p_ongoing_sc4_offshore_m2",cr.getOngoingSc4OffshoreM2());
            cs.setFloat("p_ongoing_sc5_offshore_m2",cr.getOngoingSc5OffshoreM2());
            cs.setFloat("p_ongoing_sc6_offshore_m2",cr.getOngoingSc6OffshoreM2());
            cs.setFloat("p_ongoing_sc7_offshore_m2",cr.getOngoingSc7OffshoreM2());
            cs.setFloat("p_ongoing_sc8_offshore_m2",cr.getOngoingSc8OffshoreM2());
            cs.setFloat("p_ongoing_sc9_offshore_m2",cr.getOngoingSc9OffshoreM2());
            cs.setFloat("p_ongoing_sc10_offshore_m2",cr.getOngoingSc10OffshoreM2());
            
            cs.setFloat("p_ongoing_sc1_offshore_m3",cr.getOngoingSc1OffshoreM3());
            cs.setFloat("p_ongoing_sc2_offshore_m3",cr.getOngoingSc2OffshoreM3());
            cs.setFloat("p_ongoing_sc3_offshore_m3",cr.getOngoingSc3OffshoreM3());
            cs.setFloat("p_ongoing_sc4_offshore_m3",cr.getOngoingSc4OffshoreM3());
            cs.setFloat("p_ongoing_sc5_offshore_m3",cr.getOngoingSc5OffshoreM3());
            cs.setFloat("p_ongoing_sc6_offshore_m3",cr.getOngoingSc6OffshoreM3());
            cs.setFloat("p_ongoing_sc7_offshore_m3",cr.getOngoingSc7OffshoreM3());
            cs.setFloat("p_ongoing_sc8_offshore_m3",cr.getOngoingSc8OffshoreM3());
            cs.setFloat("p_ongoing_sc9_offshore_m3",cr.getOngoingSc9OffshoreM3());
            cs.setFloat("p_ongoing_sc10_offshore_m3",cr.getOngoingSc10OffshoreM3());
            
            cs.setInt("p_supp_ind",cr.getSuppInd());
            cs.setFloat("p_oneoff_supp1_resident",cr.getOneoffSupp1Resident());
            cs.setFloat("p_oneoff_supp1_non_resident",cr.getOneoffSupp1NonResident());
            cs.setFloat("p_oneoff_supp1_offshore",cr.getOneoffSupp1Offshore());
            cs.setFloat("p_ongoing_supp1_resident_m1",cr.getOngoingSupp1ResidentM1());
            cs.setFloat("p_ongoing_supp1_non_resident_m1",cr.getOngoingSupp1NonResidentM1());
            cs.setFloat("p_ongoing_supp1_offshore_m1",cr.getOngoingSupp1OffshoreM1());
            cs.setFloat("p_ongoing_supp1_resident_m2",cr.getOngoingSupp1ResidentM2());
            cs.setFloat("p_ongoing_supp1_non_resident_m2",cr.getOngoingSupp1NonResidentM2());
            cs.setFloat("p_ongoing_supp1_offshore_m2",cr.getOngoingSupp1OffshoreM2());
            cs.setFloat("p_ongoing_supp1_resident_m3",cr.getOngoingSupp1ResidentM3());
            cs.setFloat("p_ongoing_supp1_non_resident_m3",cr.getOngoingSupp1NonResidentM3());
            cs.setFloat("p_ongoing_supp1_offshore_m3",cr.getOngoingSupp1OffshoreM3());
            
            cs.setString("p_admin_id",cr.getLastUpdatedBy());
            cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
            
            // execute
            result =cs.executeUpdate();
            returnCode =cs.getInt("return_code");
            
            // check execution result
            String msg1 = "SQL error for Service Category 3!<BR>" +
                    		"Contractor: "+cr.getContractorId() + "; <BR> Effective Date: " + cr.getEffectiveDate().toString()+";";
            String msg2 = "No record was inserted for Service Category 3! <BR>"+
                    "Contractor: "+cr.getContractorId() + "; <BR> Effective Date: " + cr.getEffectiveDate().toString()+";";
            commitResult(returnCode, result, msg1, msg2);
            
            return returnCode;
        } catch (SQLException ex0) {
            String err = "CeilingRateDataBean:insertCeilingRate3: SQL Error\n" +"SQLState:"+ ex0.getSQLState() +":"+ ex0.getMessage();
            logger.error(err,ex0);
            try{
                connection.rollback();
                throw new SysException(err);
            }catch (SQLException ex1){
                err = "SQL Error: CeilingRateDataBean:insertCeilingRate3.rollback\n" + ex1.getMessage();
                throw new SysException(err);
            }
            
        } finally {
            super.close();
        }
    }
    
    public int insertCeilingRate4(CeilingRate4Info cr) throws SysException {
        
        Connection connection = null;
        CallableStatement cs = null;
        
        
        int result = 0;
        int returnCode=0;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
            cs = connection.prepareCall(INSERT_CEILING_RATE_4);
            // get information from info object
            cs.setDate("p_effective_date",cr.getEffectiveDate());
            cs.setString("p_contractor_id",cr.getContractorId());
            cs.setString("p_currency",cr.getCurrency());
            cs.setInt("p_active_ind",cr.getActiveInd());
            cs.setFloat("p_sc1_resident",cr.getOneoffSc1Resident());
            cs.setFloat("p_sc2_resident",cr.getOneoffSc2Resident());
            cs.setFloat("p_sc3_resident",cr.getOneoffSc3Resident());
            cs.setFloat("p_sc4_resident",cr.getOneoffSc4Resident());
            cs.setFloat("p_sc5_resident",cr.getOneoffSc5Resident());
            cs.setFloat("p_sc6_resident",cr.getOneoffSc6Resident());
            cs.setFloat("p_sc1_non_resident",cr.getOneoffSc1NonResident());
            cs.setFloat("p_sc2_non_resident",cr.getOneoffSc2NonResident());
            cs.setFloat("p_sc3_non_resident",cr.getOneoffSc3NonResident());
            cs.setFloat("p_sc4_non_resident",cr.getOneoffSc4NonResident());
            cs.setFloat("p_sc5_non_resident",cr.getOneoffSc5NonResident());
            cs.setFloat("p_sc6_non_resident",cr.getOneoffSc6NonResident());
            cs.setFloat("p_sc1_offshore",cr.getOneoffSc1Offshore());
            cs.setFloat("p_sc2_offshore",cr.getOneoffSc2Offshore());
            cs.setFloat("p_sc3_offshore",cr.getOneoffSc3Offshore());
            cs.setFloat("p_sc4_offshore",cr.getOneoffSc4Offshore());
            cs.setFloat("p_sc5_offshore",cr.getOneoffSc5Offshore());
            cs.setFloat("p_sc6_offshore",cr.getOneoffSc6Offshore());
            
            cs.setFloat("p_ongoing_sc1_office_hours",cr.getOngoingSc1OfficeHours());
            cs.setFloat("p_ongoing_sc2_office_hours",cr.getOngoingSc2OfficeHours());
            cs.setFloat("p_ongoing_sc3_office_hours",cr.getOngoingSc3OfficeHours());
            cs.setFloat("p_ongoing_sc4_office_hours",cr.getOngoingSc4OfficeHours());
            cs.setFloat("p_ongoing_sc5_office_hours",cr.getOngoingSc5OfficeHours());
            cs.setFloat("p_ongoing_sc1_non_office_hours",cr.getOngoingSc1NonOfficeHours());
            cs.setFloat("p_ongoing_sc2_non_office_hours",cr.getOngoingSc2NonOfficeHours());
            cs.setFloat("p_ongoing_sc3_non_office_hours",cr.getOngoingSc3NonOfficeHours());
            cs.setFloat("p_ongoing_sc4_non_office_hours",cr.getOngoingSc4NonOfficeHours());
            cs.setFloat("p_ongoing_sc5_non_office_hours",cr.getOngoingSc5NonOfficeHours());
            
            cs.setFloat("p_ongoing_sc1_round_the_clock",cr.getOngoingSc1RoundTheClock());
            cs.setFloat("p_ongoing_sc2_round_the_clock",cr.getOngoingSc2RoundTheClock());
            cs.setFloat("p_ongoing_sc3_round_the_clock",cr.getOngoingSc3RoundTheClock());
            cs.setFloat("p_ongoing_sc4_round_the_clock",cr.getOngoingSc4RoundTheClock());
            cs.setFloat("p_ongoing_sc5_round_the_clock",cr.getOngoingSc5RoundTheClock());
            
            cs.setString("p_admin_id",cr.getLastUpdatedBy());
            cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
            
            // execute
            result =cs.executeUpdate();
            returnCode =cs.getInt("return_code");
            
         // check execution result
            String msg1 = "SQL error for Service Category 4!<BR>" +
                    		"Contractor: "+cr.getContractorId() + "; <BR> Effective Date: " + cr.getEffectiveDate().toString()+";";
            String msg2 = "No record was inserted for Service Category 4! <BR>"+
                    "Contractor: "+cr.getContractorId() + "; <BR> Effective Date: " + cr.getEffectiveDate().toString()+";";
            commitResult(returnCode, result, msg1, msg2);
            
            return returnCode;
        } catch (SQLException ex0) {
            String err = "CeilingRateDataBean:insertCeilingRate4: SQL Error\n" +"SQLState:"+ ex0.getSQLState() +":"+ ex0.getMessage();
            logger.error(err,ex0);
            try{
                connection.rollback();
                throw new SysException(err);
            }catch (SQLException ex1){
                err = "SQL Error: CeilingRateDataBean:insertCeilingRate4.rollback\n" + ex1.getMessage();
                throw new SysException(err);
            }
            
        } finally {
            super.close();
        }
    }
    
    public int deleteCeilingRate(CeilingRateInfo cr, SecurityContext secCtx) throws SysException {
        
        Connection connection = null;
        CallableStatement cs = null;
        
        int result = 0;
        int returnCode=0;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
            cs = connection.prepareCall(DELETE_CEILING_RATE);
            // get information from info object
            cs.setString("p_service_category",cr.getServiceCategory());
            cs.setDate("p_effective_date",cr.getEffectiveDate());            
            cs.setString("p_admin_id",secCtx.getUserId());
            cs.setString("p_dp_dept_id",secCtx.getDPDeptId());
            cs.setString("p_soa_dept_id",secCtx.getSOADeptId());            
            cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
            
            // execute
            result = cs.executeUpdate();
            returnCode = cs.getInt("return_code");
            
            // check execution result
            String msg1 = "SQL error for Service Category "+ cr.getServiceCategory()+"!<BR>Effective Date: " + cr.getEffectiveDate().toString()+";";
            String msg2 = "No record was deleted for Service Category "+ cr.getServiceCategory()+"! <BR> Effective Date: " + cr.getEffectiveDate().toString()+";";
            commitResult(returnCode, result, msg1, msg2);
            
            return result;
            
        } catch (SQLException ex0) {
            String err = "CeilingRateDataBean:DeleteCeilingRate: SQL Error\n" + ex0.getMessage();
            logger.error(err,ex0);
            try{
                connection.rollback();
                throw new SysException(err);
            }catch (SQLException ex1){
                err = "SQL Error: CeilingRateDataBean:deleteCeilingRate.rollback\n" + ex1.getMessage();
                throw new SysException(err);
            }
        } finally {
            super.close();
        }
    }
    
    /*
     *  Release Ceiling Rate for publishing
     */
    
    public int releaseCeilingRate(CeilingRateInfo cr, SecurityContext secCtx) throws SysException {
        
        Connection connection = null;
        CallableStatement cs = null;
        int returnCode=0;
        
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
            cs = connection.prepareCall(RELEASE_CEILING_RATE);
            // get information from info object
            cs.setString("p_service_category",cr.getServiceCategory());
            cs.setDate("p_effective_date",cr.getEffectiveDate());
            cs.setString("p_admin_id",secCtx.getUserId());
            cs.setString("p_dp_dept_id",secCtx.getDPDeptId());
            cs.setString("p_soa_dept_id",secCtx.getSOADeptId());
            cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
            
            returnCode = cs.getInt("return_code");
            
            // check execution result
            if (returnCode !=1){
                connection.rollback();
                String err = "SQL error for Service Category "+ cr.getServiceCategory()+"!";
                throw new SysException(err);
            }
            connection.commit();
            return returnCode;
            
        } catch (SQLException ex0) {
            String err = "CeilingRateDataBean:DeleteCeilingRate: SQL Error\n" + ex0.getMessage();
            logger.error(err,ex0);
            try{
                connection.rollback();
                throw new SysException(err);
            }catch (SQLException ex1){
                err = "SQL Error: CeilingRateDataBean:deleteCeilingRate.rollback\n" + ex1.getMessage();
                throw new SysException(err);
            }
        } finally {
            super.close();
        }
    }
    
    public List<CeilingRateCat1Info> getCeilingRate1(String effectiveDate) throws SysException {
        Connection connection  = null;
        CallableStatement cs = null;
        List<CeilingRateCat1Info> crCat1InfoList  = new ArrayList<CeilingRateCat1Info>();
        CeilingRateCat1Info aCRCat1Info = null;
        
        try {
            connection = this.getConnection();
            cs = connection.prepareCall(SELECT_CEILING_RATE_ADMIN);
            cs.setDate("p_effective_date", SysManager.getSQLDate(effectiveDate));
            cs.setInt("p_service_category",1);
            rs=cs.executeQuery();
            crCat1InfoList = RecordCat1Info(aCRCat1Info, rs);
            
        } catch (SQLException ex) {
            String err = "SQL Error: CeilingRateDataBean:getCeilingRate1\n" + ex.getMessage();
            logger.error(err,ex);
            throw new SysException(err);
        } finally {
            super.close();
        }
        return crCat1InfoList;
    }
    
    
    public List<CeilingRateCat2Info> getCeilingRate2(String effectiveDate) throws SysException {
        Connection connection  = null;
        CallableStatement cs = null;
        List<CeilingRateCat2Info> crCat2InfoList  = new ArrayList<CeilingRateCat2Info>();
        CeilingRateCat2Info aCRCat2Info = null;
        
        try {
            connection = this.getConnection();
            cs = connection.prepareCall(SELECT_CEILING_RATE_ADMIN);
            cs.setDate("p_effective_date", SysManager.getSQLDate(effectiveDate));
            cs.setInt("p_service_category",2);
            rs=cs.executeQuery();
            crCat2InfoList = RecordCat2Info(aCRCat2Info, rs);
            
        } catch (SQLException ex) {
            String err = "SQL Error: CeilingRateDataBean:getCeilingRate2\n" + ex.getMessage();
            logger.error(err,ex);
            throw new SysException(err);
        } finally {
            super.close();
        }
        
        return crCat2InfoList;
    }
    
    public List<CeilingRateCat3Info> getCeilingRate3(String effectiveDate) throws SysException {
        Connection connection  = null;
        CallableStatement cs = null;
        List<CeilingRateCat3Info> crCat3InfoList  = new ArrayList<CeilingRateCat3Info>();
        CeilingRateCat3Info aCRCat3Info = null;
        
        try {
            connection = this.getConnection();
            cs = connection.prepareCall(SELECT_CEILING_RATE_ADMIN);
            cs.setDate("p_effective_date", SysManager.getSQLDate(effectiveDate));
            cs.setInt("p_service_category",3);
            rs = cs.executeQuery();
            crCat3InfoList = RecordCat3Info(aCRCat3Info, rs);
            
        } catch (SQLException ex) {
            String err = "SQL Error: CeilingRateDataBean:getCeilingRate3\n" + ex.getMessage();
            logger.error(err,ex);
            throw new SysException(err);
        } finally {
            super.close();
        }
        return crCat3InfoList;
    }
    
    public List<CeilingRateCat4Info> getCeilingRate4(String effectiveDate) throws SysException {
        Connection connection  = null;
        CallableStatement cs = null;
        List<CeilingRateCat4Info> crCat4InfoList  = new ArrayList<CeilingRateCat4Info>();
        CeilingRateCat4Info aCRCat4Info = null;
        
        try {
            connection = this.getConnection();
            cs = connection.prepareCall(SELECT_CEILING_RATE_ADMIN);
            cs.setDate("p_effective_date", SysManager.getSQLDate(effectiveDate));
            cs.setInt("p_service_category",4);
            rs = cs.executeQuery();
            crCat4InfoList = RecordCat4Info(aCRCat4Info, rs);
            
        } catch (SQLException ex) {
            String err = "SQL Error: CeilingRateDataBean:getCeilingRate4\n" + ex.getMessage();
            logger.error(err,ex);
            throw new SysException(err);
        } finally {
            super.close();
        }
        
        return crCat4InfoList;
    }
    
    
    public int writeAuditTrail(CeilingRateInfo cr, SecurityContext secCtx, int uploadStatus) throws SysException {
        
        Connection connection = null;
        CallableStatement cs = null;
        
        int result = 0;
        int returnCode=0;
        
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
            cs = connection.prepareCall(WRITE_AUDIT_TRAIL);
            // get information
            
            String recordId = "[" +cr.getServiceCategory() +"]"+ "+" + cr.getEffectiveDate().toString();
            cs.setString("p_record_id",recordId);
            cs.setString("p_admin_id",secCtx.getUserId());
            cs.setString("p_dp_dept_id",secCtx.getDPDeptId());
            cs.setString("p_soa_dept_id",secCtx.getSOADeptId());
            if (uploadStatus ==1){
                cs.setString("p_remarks","CEILING RATES UPLOAD SUCCESSFULLY");
            }else{
                cs.setString("p_remarks","CEILING RATES UPLOAD FAILED");
            }
            cs.registerOutParameter("return_code", java.sql.Types.INTEGER);
            //execute
            result = cs.executeUpdate();
            returnCode =cs.getInt("return_code");
            
            connection.commit();
            return result;
        } catch (SQLException ex0) {
            String err = "SQL Error : CeilingRateDataBean:writeAuditTrail\n" +"SQLState:"+ ex0.getSQLState() +":"+  ex0.getMessage();
            logger.error(err,ex0);
            try{
                connection.rollback();
                throw new SysException(err);
            }catch (SQLException ex1){
                err = "SQL Error: CeilingRateDataBean:writeAuditTrail.rollback\n" + ex1.getMessage();
                throw new SysException(err);
            }
        } finally {
            super.close();
        }
    }
    
    public List<CeilingRateCat1Info> RecordCat1Info(CeilingRateCat1Info aCRCat1Info, ResultSet rs) throws NumberFormatException, SQLException{
    	List<CeilingRateCat1Info> crCat1InfoList  = new ArrayList<CeilingRateCat1Info>();
    	
    	while(rs.next()) {
            aCRCat1Info = new CeilingRateCat1Info();
            aCRCat1Info.setContractorName(rs.getString("contractor_name"));
            aCRCat1Info.setContractorID(rs.getString("contractor_id"));
            aCRCat1Info.setCurrency(rs.getString("currency"));
            aCRCat1Info.setActiveIndicator(rs.getInt("active_indicator"));
            aCRCat1Info.setCreatedDate(rs.getTimestamp("created_datetime").toString());
            aCRCat1Info.setLastUpdatedDate(rs.getTimestamp("last_updated_datetime").toString());
            aCRCat1Info.setLastUpdatedBy(rs.getString("last_updated_by_user"));
            aCRCat1Info.setSupStaffIndicator(rs.getInt("supplementary_indicator"));
            
            for (int i = 0; i < CeilingRateCat1Info.NumOfWorkingLocation; i++)
            {
                aCRCat1Info.setSupStaffRate(i, Double.valueOf(rs.getString(aCRCat1Info.toSupStaffRateTableField(i))).doubleValue());
            }
            
            if (rs.getInt("1N_indicator") == -1)
                aCRCat1Info.setServiceGroup("N");
            else if (rs.getInt("1J_indicator") == -1)
                aCRCat1Info.setServiceGroup("J");
            else
                aCRCat1Info.setServiceGroup("");
            
            for (int i = 0; i < CeilingRateCat1Info.NumOfStaffCategory; i++)
            {
                for (int j = 0; j < CeilingRateCat1Info.NumOfWorkingLocation; j++)
                {
                    aCRCat1Info.setStdStaffRate(i, j, Double.valueOf(rs.getString(aCRCat1Info.toStdStaffRateTableField(i, j))).doubleValue());
                }
            }
            
            crCat1InfoList.add(aCRCat1Info); 
        }
    	return crCat1InfoList;
    }
    
    public List<CeilingRateCat2Info> RecordCat2Info(CeilingRateCat2Info aCRCat2Info, ResultSet rs) throws NumberFormatException, SQLException{
    	List<CeilingRateCat2Info> crCat2InfoList  = new ArrayList<CeilingRateCat2Info>();
    	
    	while(rs.next()) {
            aCRCat2Info = new CeilingRateCat2Info();
            aCRCat2Info.setContractorName(rs.getString("contractor_name"));
            aCRCat2Info.setContractorID(rs.getString("contractor_id"));
            aCRCat2Info.setCurrency(rs.getString("currency"));
            aCRCat2Info.setActiveIndicator(rs.getInt("active_indicator"));
            aCRCat2Info.setCreatedDate(rs.getTimestamp("created_datetime").toString());
            aCRCat2Info.setLastUpdatedDate(rs.getTimestamp("last_updated_datetime").toString());
            aCRCat2Info.setLastUpdatedBy(rs.getString("last_updated_by_user"));
            aCRCat2Info.setSupStaffIndicator(rs.getInt("supplementary_indicator"));
            
            if (rs.getInt("2N_indicator") == -1)
                aCRCat2Info.setServiceGroup("N");
            else if (rs.getInt("2J_indicator") == -1)
                aCRCat2Info.setServiceGroup("J");
            else
                aCRCat2Info.setServiceGroup("");
            
            for (int i = 0; i < aCRCat2Info.NumOfStaffCategory; i++)
            {
                for (int j = 0; j < aCRCat2Info.NumOfWorkingLocation; j++)
                {
                    for (int k = 0; k < aCRCat2Info.NumOfMScheme; k++)
                    {
                        aCRCat2Info.setStdStaffRate
                                (
                                i, j, k,
                                Double.valueOf(rs.getString(aCRCat2Info.toStdStaffRateTableField(i, j, k))).doubleValue()
                                );
                    }
                }
            }
            
            for (int i = 0; i < aCRCat2Info.NumOfWorkingLocation; i++)
            {
                for (int j = 0; j < aCRCat2Info.NumOfMScheme; j++)
                {
                    aCRCat2Info.setSupStaffRate
                            (
                            i, j,
                            Double.valueOf(rs.getString(aCRCat2Info.toSupStaffRateTableField(i, j))).doubleValue()
                            );
                }
            }
            crCat2InfoList.add(aCRCat2Info);
        }
    	return crCat2InfoList;
    }
    
    public List<CeilingRateCat3Info> RecordCat3Info(CeilingRateCat3Info aCRCat3Info, ResultSet rs) throws NumberFormatException, SQLException{
    	List<CeilingRateCat3Info> crCat3InfoList  = new ArrayList<CeilingRateCat3Info>();
    	while(rs.next()) {
            aCRCat3Info = new CeilingRateCat3Info();
            aCRCat3Info.setContractorName(rs.getString("contractor_name"));
            aCRCat3Info.setContractorID(rs.getString("contractor_id"));
            aCRCat3Info.setCurrency(rs.getString("currency"));
            aCRCat3Info.setActiveIndicator(rs.getInt("active_indicator"));
            aCRCat3Info.setCreatedDate(rs.getTimestamp("created_datetime").toString());
            aCRCat3Info.setLastUpdatedDate(rs.getTimestamp("last_updated_datetime").toString());
            aCRCat3Info.setLastUpdatedBy(rs.getString("last_updated_by_user"));
            aCRCat3Info.setSupStaffIndicator(rs.getInt("supplementary_indicator"));
            
            if (rs.getInt("3N_indicator") == -1)
                aCRCat3Info.setServiceGroup("N");
            else if (rs.getInt("3J_indicator") == -1)
                aCRCat3Info.setServiceGroup("J");
            else
                aCRCat3Info.setServiceGroup("");
            
            for (int i = 0; i < CeilingRateCat3Info.NumOfOneOffStaffCategory; i++)
            {
                for (int j = 0; j < CeilingRateCat3Info.NumOfWorkingLocation; j++)
                {
                    aCRCat3Info.setStdOneOffStaffRate
                            (
                            i, j,
                            Double.valueOf(rs.getString(aCRCat3Info.toStdOneOffStaffRateTableField(i, j))).doubleValue()
                            );
                }
            }
            
            for (int i = 0; i < CeilingRateCat3Info.NumOfWorkingLocation; i++)
            {
                aCRCat3Info.setSupOneOffStaffRate
                        (i, Double.valueOf(rs.getString(aCRCat3Info.toSupOneOffStaffRateTableField(i))).doubleValue());
            }
            
            for (int i = 0; i < aCRCat3Info.NumOfOnGoingStaffCategory; i++)
            {
                for (int j = 0; j < aCRCat3Info.NumOfWorkingLocation; j++)
                {
                    for (int k = 0; k < aCRCat3Info.NumOfMScheme; k++)
                    {
                        aCRCat3Info.setStdOnGoingStaffRate
                                (
                                i, j, k,
                                Double.valueOf(rs.getString(aCRCat3Info.toStdOnGoingStaffRateTableField(i, j, k))).doubleValue()
                                );
                    }
                }
            }
            
            for (int i = 0; i < aCRCat3Info.NumOfWorkingLocation; i++)
            {
                for (int j = 0; j < aCRCat3Info.NumOfMScheme; j++)
                {
                    aCRCat3Info.setSupOnGoingStaffRate
                            (
                            i, j,
                            Double.valueOf(rs.getString(aCRCat3Info.toSupOnGoingStaffRateTableField(i, j))).doubleValue()
                            );
                }
            }  
            crCat3InfoList.add(aCRCat3Info);
        }
    	
    	return crCat3InfoList;
    }
    	
    public List<CeilingRateCat4Info> RecordCat4Info(CeilingRateCat4Info aCRCat4Info, ResultSet rs) throws NumberFormatException, SQLException{
    	List<CeilingRateCat4Info> crCat4InfoList  = new ArrayList<CeilingRateCat4Info>();
    	
    	while(rs.next()) {
            aCRCat4Info = new CeilingRateCat4Info();
            aCRCat4Info.setContractorName(rs.getString("contractor_name"));
            aCRCat4Info.setContractorID(rs.getString("contractor_id"));
            aCRCat4Info.setCurrency(rs.getString("currency"));
            aCRCat4Info.setActiveIndicator(rs.getInt("active_indicator"));
            aCRCat4Info.setCreatedDate(rs.getTimestamp("created_datetime").toString());
            aCRCat4Info.setLastUpdatedDate(rs.getTimestamp("last_updated_datetime").toString());
            aCRCat4Info.setLastUpdatedBy(rs.getString("last_updated_by_user"));
            
            if (rs.getInt("4N_indicator") == -1)
                aCRCat4Info.setServiceGroup("N");
            else if (rs.getInt("4J_indicator") == -1)
                aCRCat4Info.setServiceGroup("J");
            else
                aCRCat4Info.setServiceGroup("");
            
            for (int i = 0; i < CeilingRateCat4Info.NumOfOneOffStaffCategory; i++)
            {
                for (int j = 0; j < CeilingRateCat4Info.NumOfWorkingLocation; j++)
                {
                    aCRCat4Info.setOneOffStaffRate
                            (
                            i, j,
                            Double.valueOf(rs.getString(aCRCat4Info.toOneOffStaffRateTableField(i, j))).doubleValue()
                            );
                }
            }
            
            for (int i = 0; i < aCRCat4Info.NumOfOnGoingStaffCategory; i++)
            {
                for (int j = 0; j < aCRCat4Info.NumOfWorkingHourType; j++)
                {
                    aCRCat4Info.setOnGoingStaffRate
                            (
                            i, j,
                            Double.valueOf(rs.getString(aCRCat4Info.toOnGoingStaffRateTableField(i, j))).doubleValue()
                            );
                }
            }
            crCat4InfoList.add(aCRCat4Info);
        }
    	
    	return crCat4InfoList;
    }
}
