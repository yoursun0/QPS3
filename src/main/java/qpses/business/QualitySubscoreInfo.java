package qpses.business;

import java.sql.Blob;
import java.sql.Date;

public class QualitySubscoreInfo{
		
		private String serviceCategory;
    	private Date effectiveStartDate;
        private Date effectiveEndDate;
        private int effectivePeriodId;
        private String periodDesc;
        private String contractorId;
        private String contractorName;
        private float score;
        private Blob excelFile;
        private String excelFileName;
        private String fileContentType;
        private long excelFileSize;    
        private String createdDate;
        private String lastUpdatedDate;
        private String lastUpdatedBy;     
        private int orgKey1;
        private String orgKey2;        
        
		
	/**
     * Constructor for QualitySubscoreInfo.
     */
	public QualitySubscoreInfo() {
		super();
	}

        /**
	 * Returns the original Key 1 (Effective Date).
	 * @return Date
	 */
	public int getOrgKey1() {
		return orgKey1;
	}
        
        /**
	 * Returns the original Key 2 (Contractor Id).
	 * @return String
	 */
	public String getOrgKey2() {
		return orgKey2;
	}
                
        /**
	 * Returns the Effective Period ID 
	 * @return int
	 */
	public int getEffectivePeriodID() {
		return effectivePeriodId;
	}
         
        /**
	 * Returns the Effective Period
	 * @return String 
	 */
	public String getPeriodDesc() {
		return periodDesc;
	}
        
                /**
	 * Returns the Effective Start Date.
	 * @return Date
	 */
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
         
        /**
	 * Returns the Effective End Date.
	 * @return Date
	 */
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	
    	/**
	 * Returns the service category
	 * @return String
	 */
	public String getServiceCategory() {
		return serviceCategory;
        } 
	
        /**
	 * Returns the contractor Id
	 * @return String
	 */
        
	public String getContractorId() {
		return contractorId;
        }        
        
        /**
	 * Returns the contractor name
	 * @return String
	 */
	public String getContractorName() {
		return contractorName;
        }  
        
        /**
	 * Returns the Contractor Performance Score
	 * @return Float
	 */
	public float getScore() {
		return score;
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
	 * Sets the original key 1 (Effective Period ID)
	 * @param original Period ID - The original Effective Period ID to set
	 */
	public void setOrgKey1(int orgKey1) {
                this.orgKey1 = orgKey1;		
	}        
        
        /**
	 * Sets the original key 2 (Contractor Id)
	 * @param original contractor id  - The original contractor Id to set
	 */
	public void setOrgKey2(String orgKey2) {
                this.orgKey2 = orgKey2;		
	}       


        	/**
	 * Sets the Effective Period ID
	 * @param effectivePeriodId - The Effective Period ID to set
	 */
	public void setEffectivePeriodID(int effectivePeriodId) {
                this.effectivePeriodId = effectivePeriodId;		
	}
        
        	/**
	 * Sets the Effective Period
	 * @param effectiveEndDate - The Effective Period to set
	 */
	public void setPeriodDesc(String periodDesc) {
                this.periodDesc = periodDesc;		
	}


        	/**
	 * Sets the Effective Start Date.
	 * @param effectiveStartDate - The Effective Start Date to set
	 */
	public void setEffectiveStartDate(Date effectiveStartDate) {
                this.effectiveStartDate = effectiveStartDate;		
	}
        
        	/**
	 * Sets the Effective End Date.
	 * @param effectiveEndDate - The Effective End Date to set
	 */
	public void setEffectiveEndDate(Date effectiveEndDate) {
                this.effectiveEndDate = effectiveEndDate;		
	}
    
    /**
     * Sets the service category
     * @param  serviceCategory - The service category to set
     */
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

         /**
	 * Sets the contractor Id
	 * @param  contractorId - The  contractor Id to set
	 */
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

         /**
	 * Sets the contractor Name
	 * @param  contractorId - The  contractor Id to set
	 */
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
        
         /**
	 * Sets the Contractor Performance Score
	 * @param score - The Contractor Performance Score to set
	 */
	public void setScore(float score) {
		this.score = score;
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
        
}
