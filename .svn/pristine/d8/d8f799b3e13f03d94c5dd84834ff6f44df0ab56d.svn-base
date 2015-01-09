package qpses.security;

import java.util.List;

public final class SecurityContext {
    protected String userId ;
    protected String userName ;
    protected String userGroup ;
    protected String dpDeptId;
    protected String soaDeptId;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected List privileges;
    protected String functionList;    
    protected String userSOADeptId;
    protected String userDPDeptId;
 
    public SecurityContext() {
    }
    
    /**
     * @return user id
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * @return user name
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * @return user group
     */
    public String getUserGroup() {
        return userGroup;
    }
    
    /**
     * @return dp department id
     */
    public String getDPDeptId() {
        return dpDeptId;
    }
    
    /**
     * @return soa department id
     */
    public String getSOADeptId() {
        return soaDeptId;
    }
    
        /**
     * @return user's dp department id for admin
     */
    public String getUserDPDeptId() {
        return userDPDeptId;
    }
    
    /**
     * @return user's soa department id for admin
     */
    public String getUserSOADeptId() {
        return userSOADeptId;
    }
    
    /**
     * @return privileges
     */
    public List getPrivileges() {
        return privileges;
    }
   
    /**
     * @return isPrivilegedFunction
     */
    
    public boolean isPrivilegedFunction(String fid){
    	return (this.functionList.indexOf(","+fid+",") >=0);
    }

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
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
	 * @param dpDeptId the dpDeptId to set
	 */
	public void setDPDeptId(String dpDeptId) {
		this.dpDeptId = dpDeptId;
	}

	/**
	 * @param soaDeptId the soaDeptId to set
	 */
	public void setSOADeptId(String soaDeptId) {
		this.soaDeptId = soaDeptId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @param userSOADeptId the userSOADeptId to set
	 */
	public void setUserSOADeptId(String userSOADeptId) {
		this.userSOADeptId = userSOADeptId;
	}

	/**
	 * @param userDPDeptId the userDPDeptId to set
	 */
	public void setUserDPDeptId(String userDPDeptId) {
		this.userDPDeptId = userDPDeptId;
	}
    
}