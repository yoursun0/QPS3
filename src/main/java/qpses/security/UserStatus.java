package qpses.security;

import  java.util.Calendar;
import java.sql.Date;
import java.sql.Timestamp;

public final class UserStatus {
    protected String userId ;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String dpDeptId;
    protected String soaDeptId;
    protected Timestamp lastAccessAttempt;    
    protected int activeInd;
    protected int passwordInd;
    protected int loginFailure;
    protected Date expiryDate;
    
    public UserStatus() {
    }
    
    /**
     * @return user id
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * @return dp department id
     */
    public String getDPDeptId() {
        return dpDeptId;
    }
    
    /**
     * @return dp department id
     */
    public String getSOADeptId() {
        return soaDeptId;
    }
    
    /**
     * @return last login attempt date and time
     */
    public Timestamp getLastAccessAttempt() {
        return lastAccessAttempt;
    }

    
     
    /**
     * @return whether user account  is locked
     */
    
    public boolean isLocked(){
    	return (this.activeInd != -1);
    }
    
    
    /**
     * @return whether the user just forgot and changed his / her password
     */
    
    public boolean isForgotPassword(){
    	return (this.passwordInd != -1);
    }
 
    /**
     * @return whether user account  is expired
     */
    
    public boolean isExpired()
    {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        
        cal.add(Calendar.DAY_OF_MONTH, -1);
        
        java.util.Date today = cal.getTime();
        
        return !(this.expiryDate.after(today));

    }    
    
        /**
     * @return expiry date
     */
    public Date getExpiryDate() {
        return expiryDate;
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
		 * @return the loginFailure
		 */
		public int getLoginFailure() {
			return loginFailure;
		}

		/**
		 * @param loginFailure the loginFailure to set
		 */
		public void setLoginFailure(int loginFailure) {
			this.loginFailure = loginFailure;
		}
		
		
    
}