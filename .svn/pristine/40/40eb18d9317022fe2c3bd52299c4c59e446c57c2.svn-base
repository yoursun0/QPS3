package qpses.business;

import java.sql.Blob;
import java.sql.Date;

public class LogInfo{
    
    private int logId;
    private String userId;
    private String deptId;
    private String deptName;
    private String soaDeptId;
    private String dpDeptId;
    private String activity;
    private String status;
    private String remarks;
    private String recordKey;
    private String QSEnquiryInd;
    private String activityDateTime;
    private Date effectiveDate;
    private int orgKey1;
    private Blob staffRateValidationReport;
    private String fromDate;
    private String toDate;
    private String activityName;
    private String actionDesc;
    private String serviceCategoryGroup;
    
    private String filePart;
    private String fileNo;
    
    /**
     * Constructor for LogInfo.
     */
    public LogInfo() {
        super();
    }
    
    /**
     * Returns the original Key 1 (Log Id).
     * @return int
     */
    public int getOrgKey1() {
        return orgKey1;
    }
    
    /**
     * Returns the Log ID (Log Id).
     * @return int
     */
    public int getLogId() {
        return logId;
    }
    
    /**
     * Returns the user id
     * @return String
     */
    public String  getUserId() {
        return userId;
    }
    
    
    /**
     * Returns the deptartment id
     * @return String
     */
    public String  getDeptId() {
        return dpDeptId +"+"+soaDeptId;
    }
    
    
    /**
     * Returns the dp deptartment id
     * @return String
     */
    public String  getDPDeptId() {
        return dpDeptId;
    }
    
    /**
     * Returns the SOA-QPS deptartment id
     * @return String
     */
    public String  getSOADeptId() {
        return soaDeptId;
    }
    
    /**
     * Returns the department name
     * @return String
     */
    public String  getDeptName() {
        return deptName;
    }
    
    /**
     * Returns the activitiy date time
     * @return Date
     */
    public String getActivityDateTime() {
        return activityDateTime;
    }
    
    
    /**
     * Returns the activitiy
     * @return String
     */
    public String getActivity() {
        return activity;
    }
    
    /**
     * Returns the status
     * @return String
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Returns the QSEnquiryInd
     * @return int
     */
    public String getQSEnquiryInd() {
        return QSEnquiryInd;
    }
    
    
        /**
     * Returns Service Category/Group of Work Assignment
     * @return String
     */
    public String getServiceCategoryGroup() {
        return serviceCategoryGroup;
    }
    
    /**
     * Returns the Activity Name
     * @return String
     */
    public String getActivityName() {
        activityName ="";
        if (this.activity.equals("CHPWD")) activityName = "Change Password";
        if (this.activity.equals("DRENQ")) activityName = "Enquiry of Discount Rate";
        if (this.activity.equals("WAIMPORT")) activityName = "Data Maintenance - Work Assignment";
        if (this.activity.startsWith("ACR")){
            if (this.activity.equals("ACRUSERENQ")) {
                activityName = "Enquiry of Average Ceiling Rate";
            }else{
                activityName = "Data Maintenance - Average Ceiling Rate";
            }
        }
        if (this.activity.startsWith("CR")) activityName = "Data Maintenance - Ceiling Rate Maintenance";
        if (this.activity.startsWith("QS")){
            if (this.activity.equals("QSENQ")) {
                activityName = "Enquiry of Contractor Performance Score";
            }else{
                activityName = "Data Maintenance - Contractor Performance Score";
            }
        }
        if (this.activity.startsWith("ACL")) activityName = "Access Control Maintenance";
        if (this.activity.startsWith("CON")) activityName = "System Code Maintenance - Contractor";
        if (this.activity.startsWith("DEPT")) activityName = "System Code Maintenance - Department";
        
        // Challenge Activities
        if (this.activity.startsWith("QSENQ_CHALLENGE"))     activityName = "Work Assignment Challenge for Enquiry of Contractor Performance Score";
        if (this.activity.startsWith("RATEVALID_CHALLENGE")) activityName = "Work Assignment Challenge for Staff Rate Validation";
        if (this.activity.startsWith("CPAR_CHALLENGE")) activityName = "Work Assignment Challenge for CPAR Creation";
        if (this.activity.startsWith("CPAR_MAINTENANCE")) activityName = "CPAR Maintenance";
        
        // CPAR Activities
        if (this.activity.startsWith("CPARADD")) activityName = "Create CPAR Records";
        if (this.activity.startsWith("CPARUPDATE")) activityName = "Update CPAR Records";
        if (this.activity.startsWith("CPARFINALIZE")) activityName = "Finalize CPAR Records";
        if (this.activity.startsWith("CPARDELETE")) activityName = "Delete CPAR Records";
        
        // Admin CPAR Activities
        if (this.activity.startsWith("ACPAR_UPDATE")) activityName = "CPAR Maintenance - Update CPAR Records";
        if (this.activity.startsWith("ACPAR_RELEASE")) activityName = "CPAR Maintenance - Release CPAR Records";
        if (this.activity.startsWith("ACPAR_UNRELEASE")) activityName = "CPAR Maintenance - Unrelease CPAR Records";
        if (this.activity.startsWith("ACPAR_DELETE")) activityName = "CPAR Maintenance - Delete CPAR Records";
        if (this.activity.startsWith("ACPAR_EXPORT")) activityName = "CPAR Maintenance - Search CPARs";
        
        // Debarment
        if (this.activity.equals("DEBARADD")) activityName = "Add Debarment";
        if (this.activity.equals("DEBARRELEASE")) activityName = "Release Debarment";
        if (this.activity.equals("DEBARDELETE")) activityName = "Delete Debarment";
        
        // Statistics Report
        if (this.activity.equals("STAT")) activityName = "Statistics Report";
        
        // Outstanding CPARs
        if (this.activity.equals("OUTSTAND")) activityName = "Outstanding CPARs List";
        
        // Generate CPS
        if (this.activity.equals("GENERATECPS")) activityName = "Generate CPS";
        
        return activityName;
    }
    
    /**
     * Returns the Action Desc
     * @return String
     */
    public String getActionDesc() {
        actionDesc ="";
        if (this.activity.endsWith("IMPORT")) actionDesc = "Import records";
        if (this.activity.endsWith("UPLOAD")) actionDesc = "Upload records";
        if (this.activity.endsWith("ADD")) actionDesc = "Add record";
        if (this.activity.endsWith("UPDATE")) actionDesc = "Update record";
        if (this.activity.endsWith("DELETE")) actionDesc = "Delete record";
        if (this.activity.endsWith("UNLOCK")) actionDesc = "Unlock User";
        if (this.activity.endsWith("ENABLE")) actionDesc = "Enable User";
        if (this.activity.endsWith("RELEASE")) actionDesc = "Release for Publishing";
        if (this.activity.endsWith("ENQ")) actionDesc = "Enquiry";
        if (this.activity.endsWith("STAT")) actionDesc = "Report";
        if (this.activity.endsWith("CHALLENGE")) actionDesc = "Work Assignment Challenge";
        if (this.activity.endsWith("OUTSTAND")) actionDesc = "Outstanding List";
        if (this.activity.endsWith("CPS")) actionDesc = "CPS List";
        if (this.activity.endsWith("EXPORT")) actionDesc = "Download file";
        return actionDesc;
    }
    
    /**
     * Returns the remarks
     * @return String
     */
    public String getRemarks() {
        return remarks;
    }
    
    /**
     * Returns the enquiry date range
     * @return fromDate
     */
    public String getFromDate() {
        return fromDate;
    }
    
    /**
     * Returns the enquiry date range
     * @return toDate
     */
    public String getToDate() {
        return toDate;
    }
    
    /**
     * Returns the record keys
     * @return String
     */
    public String getRecordKey() {
        return recordKey;
    }
    
    /**
     * Returns the Staff Rate Validation Report
     * @return Blob
     */
    public Blob getStaffRateValidationReport() {
        return staffRateValidationReport;
    }
    
     /**
     * Returns the project file part 
     * @return String
     */
    public String getProjectFilePart() {
        return filePart;
    }

    
     /**
     * Returns the project file no.
     * @return String
     */
    public String getProjectFileNo() {
        return fileNo;
    }
    
    /**
     * Sets the original key 1 (Log ID)
     * @param original  key 1  - The original  key 1 to set
     */
    public void setOrgKey1(int orgKey1) {
        this.orgKey1 = orgKey1;
    }
    
    /**
     * Sets the Log ID
     * @param original Log ID  - The original Log ID to set
     */
    public void setLogId(int logId) {
        this.logId = logId;
    }
    /**
     * Sets the User ID
     * @param userId - The User ID  to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
    /**
     * Sets the DPDepartment ID
     * @param deptId - The Department ID  to set
     */
    public void setDPDeptId(String dpDeptId) {
        this.dpDeptId = dpDeptId;
    }
    
    /**
     * Sets the Department ID for SOA-QPS
     * @param deptId - The Department ID  to set
     */
    public void setSOADeptId(String soaDeptId) {
        this.soaDeptId = soaDeptId;
    }
    
    /**
     * Sets the Department name
     * @param deptName - The Department name
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    /**
     * Sets the Department ID
     * @param deptId - The Department ID  to set
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
        String[] arrayDeptId = deptId.split("\\+");
        this.dpDeptId=arrayDeptId[0];
        this.soaDeptId=arrayDeptId[1];
    }
    
    
    /**
     * Sets the staff rate validation report.
     * @param StaffRateValidationReport - The staff rate validation report to set
     */
    public void setStaffRateValidationReport(Blob staffRateValidationReport) {
        this.staffRateValidationReport = staffRateValidationReport;
    }
    
    /**
     * Sets the date range of enquiry
     * @param fromDate  - The from date to set
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    
    /**
     * Sets the date range of enquiry
     * @param toDate  - The to date to set
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    
    
    /**
     * Sets the activity date
     * @param activityDate  - The activity date to set
     */
    public void setActivityDateTime(String activityDateTime) {
        this.activityDateTime = activityDateTime;
    }
    
    
    /**
     * Sets the activity
     * @param activity  - The activity  to set
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }
    
    /**
     * Sets the status
     * @param status  - The status  to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Sets the remarks
     * @param remarks  - The remarks  to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
        /**
     * Sets the Service Category/Group
     * @param serviceCategoryGroup  - The serviceCategoryGroup  to set
     */
    public void setServiceCategoryGroup(String serviceCategoryGroup) {
        this.serviceCategoryGroup = serviceCategoryGroup;
    }
    
    /**
     * Sets the record keys
     * @param recordKey  - The record key  to set
     */
    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }
    
        /**
     * Sets the Contractor Performance Score enquiry indicator
     * @param QSEnquiryInd  - The QSEnquiryInd  to set
     */
    public void setQSEnquiryInd(String QSEnquiryInd) {
        this.QSEnquiryInd = QSEnquiryInd;
    }
    
    
    /**
     * Sets the Project file part
     * @param filePart  - The Project file part
     */
    public void setProjectFilePart(String filePart) {
        this.filePart = filePart;
    }
    
        
     /**
     * Sets the Project file no
     * @param fileNo  - The Project file no.
     */
    public void setProjectFileNo(String fileNo) {
        this.fileNo = fileNo;
    }
}
