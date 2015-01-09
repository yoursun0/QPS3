package qpses.business;

public class DeptInfo{
    
    private String dpDeptId;
    private String soaDeptId;
    private String combinedKey;
    private String deptName;
    private String orgKey1;
    private String orgKey2;
    private String lastUpdatedBy;
    
    /**
     * Constructor for DeptInfo.
     */
    public DeptInfo() {
        super();
    }
    
    /**
     * Returns the original Key 1 (DP Dept Id).
     * @return String
     */
    public String getOrgKey1() {
        return orgKey1;
    }
    
    /**
     * Returns the original Key 2 (SOA-QPS Dept Id).
     * @return String
     */
    public String getOrgKey2() {
        return orgKey2;
    }
    
    
    /**
     * Returns the DP deptartment id
     * @return String
     */
    public String getDPDeptId() {
        return dpDeptId;
    }
    
    /**
     * Returns the SOA-QPS deptartment id
     * @return String
     */
    public String getSOADeptId() {
        return soaDeptId;
    }
    /**
     * Returns the department name
     * @return String
     */
    public String getDeptName() {
        return deptName;
    }
    
    /**
     * Returns the combined key (DP dept id + SOA-QPS dept id)
     * @return String
     */
    public String getCombinedDeptId() {
        return dpDeptId+"+"+soaDeptId;
    }
    
    /**
     * Returns the last updated by
     * @return String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    /**
     * Sets the original key 1 (DP Dept ID)
     * @param original DP department id  - The original DP Department ID to set
     */
    public void setOrgKey1(String orgKey1) {
        this.orgKey1 = orgKey1;
    }
    
    /**
     * Sets the original key 2 (SOA-QPS Dept ID)
     * @param original SOA-QPS department id  - The original SOA-QPS Department ID to set
     */
    public void setOrgKey2(String orgKey2) {
        this.orgKey2 = orgKey2;
    }
    
    /**
     * Sets the DP Department ID
     * @param DP deptId - The DP Department ID  to set
     */
    public void setDPDeptId(String dpDeptId) {
        this.dpDeptId = dpDeptId;
    }
    
    /**
     * Sets the SOA-QPS Department ID
     * @param SOA-QPSdeptId - The SOA-QPS Department ID  to set
     */
    public void setSOADeptId(String soaDeptId) {
        this.soaDeptId = soaDeptId;
    }
    
    /**
     * Sets the Department Name
     * @param deptName - The Department Name  to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    /**
     * Sets the DP Department ID and SOA-QPS Department ID from combined key
     * @param combinedKey - The combinedKey  to spilt
     */
    public void setSplitDeptId(String combinedKey) {
        String[] arrayKey = combinedKey.trim().split("\\+",2);
        this.dpDeptId = arrayKey[0];
        this.soaDeptId = arrayKey[1];
    }
    
    /**
     * Sets the last updated by
     * @param last updated by - The last updated by to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    
}
