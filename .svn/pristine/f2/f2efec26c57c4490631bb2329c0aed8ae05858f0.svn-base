package qpses.business;

import java.sql.Blob;
import java.sql.Date;
import java.util.List;

public class ACLInfo{
    
    private String userId;
    private String deptId;
    private String deptName;
    private String soaDeptId;
    private String dpDeptId;
    private String userGroup;
    private String userGroupName;
    private String firstName;
    private String lastName;
    private String email;
    private int activeInd;
    private String createdDate;
    private String lastUpdatedDate;
    private String lastUpdatedBy;
    private Date effectiveDate;
    private String orgKey1;
    private String orgKey2;
    private String orgKey3;
    private Date expiryDate;
    private List privileges;
    private int accessFailureCount;
    private int cparFailureCount;
    private Blob excelFile;
    private String excelFileName;
    private String fileContentType;
    private long excelFileSize;
    private String password;
    private int emailInd;
    
    /**
     * Constructor for ACLInfo.
     */
    public ACLInfo() {
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
     * Returns the original Key 3 (User Id).
     * @return String
     */
    public String getOrgKey3() {
        return orgKey3;
    }
    
    /**
     * Returns the user id
     * @return String
     */
    public String  getUserId() {
        return userId;
    }
    
    
    /**
     * Returns the user group
     * @return String
     */
    public String  getUserGroup() {
        return userGroup;
    }
    
    /**
     * Returns the user group name
     * @return String
     */
    public String  getUserGroupName() {
    	if (userGroup == null) return "";
        if (userGroup.equals("U")) userGroupName = "User";
        if (userGroup.equals("M")) userGroupName = "Manager";
        if (userGroup.equals("SP")) userGroupName = "System Administrator (P)";
        if (userGroup.equals("S")) userGroupName = "System Administrator";
        return userGroupName;
    }
    /**
     * Returns the deptartment id
     * @return String
     */
    public String  getDeptId() {
        return dpDeptId +"+"+soaDeptId;
    }
    
    /**
     * Returns the deptartment id
     * @return String
     */
    public String  getDeptName() {
        return deptName;
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
     * Returns the active indicator of the ceiling rate record
     * @return boolean
     */
    public int getActiveInd() {
        return activeInd;
    }
    
    
    /**
     * Returns the creation date
     * @return Date
     */
    public String getCreatedDate() {
        return createdDate;
    }
    
    /**
     * Returns the last update date
     * @return Date
     */
    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }
    
    /**
     * Returns the last updated by
     * @return String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    
    /**
     * Returns the effective date of privileges of Cat 1 User
     * @return Date
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    
    /**
     * Returns the expiry date of privileges of Cat 1 User
     * @return int
     */
    public Date getExpiryDate() {
        return expiryDate;
    }
    
    /**
     * Returns the privileges of the User
     * @return List
     */
    public List getPrivileges() {
        return privileges;
    }
    
    
    /**
     * Returns the access failure count
     * @return int
     */
    public int getAccessFailureCount() {
        return accessFailureCount;
    }
    
            
	/**
	 * Returns the excel file name.
	 * @return String
	 */
	public String getExcelFileName() {
		return excelFileName;
	}

	/**
	 * Returns the excel file.
	 * @return Blob
	 */
	public Blob getExcelFile() {
		return excelFile;
	}

	/**
	 * Returns the excel File Content Type.
	 * @return String
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * Returns the excel file size.
	 * @return long
	 */
	public long getExcelFileSize() {
		return excelFileSize;
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
     * Sets the original key 3 (User ID)
     * @param original user id  - The original user Id  to set
     */
    public void setOrgKey3(String orgKey3) {
        this.orgKey3 = orgKey3;
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
     * Sets the Department Name
     * @param deptName - The Department Name  to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    /**
     * Sets the User Group
     * @param userGroup - The User Group  to set
     */
    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
    
    /**
     * Sets the active indicator of the ceiling rate record
     * @param activInd - The active indicator to set
     */
    public void setActiveInd(int activeInd) {
        this.activeInd = activeInd;
    }
    
    
    /**
     * Sets the creation date
     * @param creation date - The creation date to set
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    
    /**
     * Sets the last update date
     * @param last update date - The last update date to set
     */
    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    
    /**
     * Sets the last updated by
     * @param last updated by - The last updated by to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Sets the effective date of privileges of Cat 1 User
     * @param effectivedate - The effective date to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    
    /**
     * Sets the expiry date of privileges of Cat 1 User
     * @param expiryDate - The expiry Date  to set
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    /**
     * Sets the privileges of the user
     * @param privileges - The privileges  to set
     */
    public void setPrivileges(List privileges) {
        this.privileges = privileges;
    }
    
    
    /**
     * Sets the access failure count
     * @param accessFailureCount - The access failure count  to set
     */
    public void setAccessFailureCount(int accessFailureCount) {
        this.accessFailureCount = accessFailureCount;
    }
    
     	/**
	 * Sets the file name.
	 * @param excelFileName - The excel file name to set
	 */
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	/**
	 * Sets the excel file.
	 * @param excelFile - The excel file to set
	 */
	public void setExcelFile(Blob excelFile) {
		this.excelFile = excelFile;
	}

	/**
	 * Sets the excel File Content Type.
	 * @param fileContentType - The file content type to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * Sets the excel file size.
	 * @param excelFileSize - The excel file size to set
	 */
	public void setExcelFileSize(long excelFileSize) {
		this.excelFileSize = excelFileSize;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the cparFailureCount
	 */
	public int getCparFailureCount() {
		return cparFailureCount;
	}

	/**
	 * @param cparFailureCount the cparFailureCount to set
	 */
	public void setCparFailureCount(int cparFailureCount) {
		this.cparFailureCount = cparFailureCount;
	}

	/**
	 * @return the soaDeptId
	 */
	public String getSoaDeptId() {
		return soaDeptId;
	}

	/**
	 * @param soaDeptId the soaDeptId to set
	 */
	public void setSoaDeptId(String soaDeptId) {
		this.soaDeptId = soaDeptId;
	}

	/**
	 * @return the dpDeptId
	 */
	public String getDpDeptId() {
		return dpDeptId;
	}

	/**
	 * @param dpDeptId the dpDeptId to set
	 */
	public void setDpDeptId(String dpDeptId) {
		this.dpDeptId = dpDeptId;
	}

	/**
	 * @return the emailInd
	 */
	public int getEmailInd() {
		return emailInd;
	}

	/**
	 * @param emailInd the emailInd to set
	 */
	public void setEmailInd(int emailInd) {
		this.emailInd = emailInd;
	}

	/**
	 * @param userGroupName the userGroupName to set
	 */
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	
}
