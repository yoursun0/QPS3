package qpses.business;

import java.sql.Date;

import org.apache.log4j.Logger;

public class CPARInfo
{
	// log4j
    static Logger logger = Logger.getLogger(CPARInfo.class.getName());
    
    private String serviceCategoryGroup;
    private String projectFilePart;
    private int projectFileNo;
    private String departmentId;
    private String serviceContractNo;
    private String authorizedPerson;
    private String postRank;
    private int cparNo;
    private Date startDate;
    private Date endDate;
    private String finalized;
    private String action;
    private String status;
    private long wordFileSize;    
    private String createdDate;
    private String lastUpdatedDate;
    private String lastUpdatedBy;

    private double score =-1;
    private int a1Score =-1;
    private int a2Score =-1;
    private int a3Score =-1;
    private int a4Score =-1;
    private int a5Score =-1;
    private int a6Score =-1;
    private int a7Score =-1;
    private int a8Score =-1;
    private int a9Score =-1;
    
    private int b1Score =-1;
    private int b2Score =-1;
    private int b3Score =-1;
    private int b4Score =-1;
    private int b5Score =-1;
    
    private int c1Score =-1;
    private int c2Score =-1;
    private int c3Score =-1;
    private int c4Score =-1;
    private int c5Score =-1;
    
    private int d1Score =-1;
    private int d2Score =-1;
    private int d3Score =-1;

    private int e1Score =-1;
    private int e2Score =-1;
    private int e3Score =-1;
    private int e4Score =-1;
    
    // Time box 6: Add Awarded Contractor, Work Assignment Title, Category, Contact Phone Number and Internet Mail for Download CPARs Record 
    private String awardedContractor;
    private String workAssignmentTitle;
    private String category;
    private String contactPhoneNumber;
    private String internetMail;
    
    
    private String orgKey1;
    
    /**
     * Constructor for CPARInfo.
     */
    public CPARInfo()
    {
        super();
    }

	/**
	 * @return the serviceCategoryGroup
	 */
	public String getServiceCategoryGroup() {
		return serviceCategoryGroup;
	}

	/**
	 * @param serviceCategoryGroup the serviceCategoryGroup to set
	 */
	public void setServiceCategoryGroup(String serviceCategoryGroup) {
		this.serviceCategoryGroup = serviceCategoryGroup;
	}

	/**
	 * @return the projectFilePart
	 */
	public String getProjectFilePart() {
		return projectFilePart;
	}

	/**
	 * @param projectFilePart the projectFilePart to set
	 */
	public void setProjectFilePart(String projectFilePart) {
		this.projectFilePart = projectFilePart;
	}

	/**
	 * @return the projectFileNo
	 */
	public int getProjectFileNo() {
		return projectFileNo;
	}

	/**
	 * @param projectFileNo the projectFileNo to set
	 */
	public void setProjectFileNo(int projectFileNo) {
		this.projectFileNo = projectFileNo;
	}

	/**
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the serviceContractNo
	 */
	public String getServiceContractNo() {
		return serviceContractNo;
	}

	/**
	 * @param serviceContractNo the serviceContractNo to set
	 */
	public void setServiceContractNo(String serviceContractNo) {
		this.serviceContractNo = serviceContractNo;
	}

	/**
	 * @return the cparNo
	 */
	public int getCparNo() {
		return cparNo;
	}

	/**
	 * @param cparNo the cparNo to set
	 */
	public void setCparNo(int cparNo) {
		this.cparNo = cparNo;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the finalized
	 */
	public String getFinalized() {
		return finalized;
	}

	/**
	 * @param finalized the finalized to set
	 */
	public void setFinalized(String finalized) {
		this.finalized = finalized;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the wordFileSize
	 */
	public long getWordFileSize() {
		return wordFileSize;
	}

	/**
	 * @param wordFileSize the wordFileSize to set
	 */
	public void setWordFileSize(long wordFileSize) {
		this.wordFileSize = wordFileSize;
	}
	
	/**
	 * @return the authorizedPerson
	 */
	public String getAuthorizedPerson() {
		return authorizedPerson;
	}

	/**
	 * @param authorizedPerson the authorizedPerson to set
	 */
	public void setAuthorizedPerson(String authorizedPerson) {
		this.authorizedPerson = authorizedPerson;
	}

	/**
	 * @return the postRank
	 */
	public String getPostRank() {
		return postRank;
	}

	/**
	 * @param postRank the postRank to set
	 */
	public void setPostRank(String postRank) {
		this.postRank = postRank;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastUpdatedDate
	 */
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/**
	 * @return the lastUpdatedBy
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * @param lastUpdatedBy the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return the a1Score
	 */
	public int getA1Score() {
		return a1Score;
	}

	/**
	 * @param a1Score the a1Score to set
	 */
	public void setA1Score(int a1Score) {
		this.a1Score = a1Score;
	}

	/**
	 * @return the a2Score
	 */
	public int getA2Score() {
		return a2Score;
	}

	/**
	 * @param a2Score the a2Score to set
	 */
	public void setA2Score(int a2Score) {
		this.a2Score = a2Score;
	}

	/**
	 * @return the a3Score
	 */
	public int getA3Score() {
		return a3Score;
	}

	/**
	 * @param a3Score the a3Score to set
	 */
	public void setA3Score(int a3Score) {
		this.a3Score = a3Score;
	}

	/**
	 * @return the a4Score
	 */
	public int getA4Score() {
		return a4Score;
	}

	/**
	 * @param a4Score the a4Score to set
	 */
	public void setA4Score(int a4Score) {
		this.a4Score = a4Score;
	}

	/**
	 * @return the a5Score
	 */
	public int getA5Score() {
		return a5Score;
	}

	/**
	 * @param a5Score the a5Score to set
	 */
	public void setA5Score(int a5Score) {
		this.a5Score = a5Score;
	}

	/**
	 * @return the a6Score
	 */
	public int getA6Score() {
		return a6Score;
	}

	/**
	 * @param a6Score the a6Score to set
	 */
	public void setA6Score(int a6Score) {
		this.a6Score = a6Score;
	}

	/**
	 * @return the a7Score
	 */
	public int getA7Score() {
		return a7Score;
	}

	/**
	 * @param a7Score the a7Score to set
	 */
	public void setA7Score(int a7Score) {
		this.a7Score = a7Score;
	}

	/**
	 * @return the a8Score
	 */
	public int getA8Score() {
		return a8Score;
	}

	/**
	 * @param a8Score the a8Score to set
	 */
	public void setA8Score(int a8Score) {
		this.a8Score = a8Score;
	}

	/**
	 * @return the a9Score
	 */
	public int getA9Score() {
		return a9Score;
	}

	/**
	 * @param a9Score the a9Score to set
	 */
	public void setA9Score(int a9Score) {
		this.a9Score = a9Score;
	}
	
	

	/**
	 * @return the b1Score
	 */
	public int getB1Score() {
		return b1Score;
	}

	/**
	 * @param b1Score the b1Score to set
	 */
	public void setB1Score(int b1Score) {
		this.b1Score = b1Score;
	}

	/**
	 * @return the b2Score
	 */
	public int getB2Score() {
		return b2Score;
	}

	/**
	 * @param b2Score the b2Score to set
	 */
	public void setB2Score(int b2Score) {
		this.b2Score = b2Score;
	}

	/**
	 * @return the b3Score
	 */
	public int getB3Score() {
		return b3Score;
	}

	/**
	 * @param b3Score the b3Score to set
	 */
	public void setB3Score(int b3Score) {
		this.b3Score = b3Score;
	}

	/**
	 * @return the b4Score
	 */
	public int getB4Score() {
		return b4Score;
	}

	/**
	 * @param b4Score the b4Score to set
	 */
	public void setB4Score(int b4Score) {
		this.b4Score = b4Score;
	}

	/**
	 * @return the b5Score
	 */
	public int getB5Score() {
		return b5Score;
	}

	/**
	 * @param b5Score the b5Score to set
	 */
	public void setB5Score(int b5Score) {
		this.b5Score = b5Score;
	}

	/**
	 * @return the c1Score
	 */
	public int getC1Score() {
		return c1Score;
	}

	/**
	 * @param c1Score the c1Score to set
	 */
	public void setC1Score(int c1Score) {
		this.c1Score = c1Score;
	}

	/**
	 * @return the c2Score
	 */
	public int getC2Score() {
		return c2Score;
	}

	/**
	 * @param c2Score the c2Score to set
	 */
	public void setC2Score(int c2Score) {
		this.c2Score = c2Score;
	}

	/**
	 * @return the c3Score
	 */
	public int getC3Score() {
		return c3Score;
	}

	/**
	 * @param c3Score the c3Score to set
	 */
	public void setC3Score(int c3Score) {
		this.c3Score = c3Score;
	}

	/**
	 * @return the c4Score
	 */
	public int getC4Score() {
		return c4Score;
	}

	/**
	 * @param c4Score the c4Score to set
	 */
	public void setC4Score(int c4Score) {
		this.c4Score = c4Score;
	}

	/**
	 * @return the c5Score
	 */
	public int getC5Score() {
		return c5Score;
	}

	/**
	 * @param c5Score the c5Score to set
	 */
	public void setC5Score(int c5Score) {
		this.c5Score = c5Score;
	}

	/**
	 * @return the d1Score
	 */
	public int getD1Score() {
		return d1Score;
	}

	/**
	 * @param d1Score the d1Score to set
	 */
	public void setD1Score(int d1Score) {
		this.d1Score = d1Score;
	}

	/**
	 * @return the d2Score
	 */
	public int getD2Score() {
		return d2Score;
	}

	/**
	 * @param d2Score the d2Score to set
	 */
	public void setD2Score(int d2Score) {
		this.d2Score = d2Score;
	}

	/**
	 * @return the d3Score
	 */
	public int getD3Score() {
		return d3Score;
	}

	/**
	 * @param d3Score the d3Score to set
	 */
	public void setD3Score(int d3Score) {
		this.d3Score = d3Score;
	}

	/**
	 * @return the e1Score
	 */
	public int getE1Score() {
		return e1Score;
	}

	/**
	 * @param e1Score the e1Score to set
	 */
	public void setE1Score(int e1Score) {
		this.e1Score = e1Score;
	}

	/**
	 * @return the e2Score
	 */
	public int getE2Score() {
		return e2Score;
	}

	/**
	 * @param e2Score the e2Score to set
	 */
	public void setE2Score(int e2Score) {
		this.e2Score = e2Score;
	}

	/**
	 * @return the e3Score
	 */
	public int getE3Score() {
		return e3Score;
	}

	/**
	 * @param e3Score the e3Score to set
	 */
	public void setE3Score(int e3Score) {
		this.e3Score = e3Score;
	}

	/**
	 * @return the e4Score
	 */
	public int getE4Score() {
		return e4Score;
	}

	/**
	 * @param e4Score the e4Score to set
	 */
	public void setE4Score(int e4Score) {
		this.e4Score = e4Score;
	}

	/**
	 * @return the orgKey1
	 */
	public String getOrgKey1() {
		return orgKey1;
	}

	/**
	 * @param orgKey1 the orgKey1 to set
	 */
	public void setOrgKey1(String orgKey1) {
		this.orgKey1 = orgKey1;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the awardedContractor
	 */
	public String getAwardedContractor() {
		return awardedContractor;
	}

	/**
	 * @param awardedContractor the awardedContractor to set
	 */
	public void setAwardedContractor(String awardedContractor) {
		this.awardedContractor = awardedContractor;
	}

	/**
	 * @return the workAssignmentTitle
	 */
	public String getWorkAssignmentTitle() {
		return workAssignmentTitle;
	}

	/**
	 * @param workAssignmentTitle the workAssignmentTitle to set
	 */
	public void setWorkAssignmentTitle(String workAssignmentTitle) {
		this.workAssignmentTitle = workAssignmentTitle;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the contactPhoneNumber
	 */
	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	/**
	 * @param contactPhoneNumber the contactPhoneNumber to set
	 */
	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	/**
	 * @return the internetMail
	 */
	public String getInternetMail() {
		return internetMail;
	}

	/**
	 * @param internetMail the internetMail to set
	 */
	public void setInternetMail(String internetMail) {
		this.internetMail = internetMail;
	}
	
    
}
