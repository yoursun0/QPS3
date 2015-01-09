package qpses.business;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

public class DiscountRateInfo
{
    private Date EffectiveDate;
    private String ServiceCategoryGroup;
    private int ActiveInd;
    private Blob PdfFile;
    private String PdfFileName;
    private String FileContentType;
    private long PdfFileSize;
    private String CreatedDate;
    private String LastUpdatedDate;
    private String LastUpdatedBy;
    private Date OrgKey1;
    private String OrgKey2;
    
    /** Constructor for DiscountRateInfo */
    public DiscountRateInfo()
    { super(); }
    
    /** Returns the original key 1 (Effective Date) */
    public Date getOrgKey1()
    { return this.OrgKey1; }
    
    /** Returns the original key 2 (Service Category) */
    public String getOrgKey2()
    { return this.OrgKey2; }
    
    /** Returns the effective date */
    public Date getEffectiveDate()
    { return this.EffectiveDate; }
    
    /** Returns the service category */
    public String getServiceCategoryGroup()
    { return this.ServiceCategoryGroup; }
    
    /** Returns the active indicator of the discount rate record */
    public int getActiveInd()
    { return this.ActiveInd; }
    
    /** Returns the file name */
    public String getPDFFileName()
    { return this.PdfFileName; }
   
    /** Returns the pdf file */
    public Blob getPDFFile()
    { return this.PdfFile; }
    
    /** Returns the pdf file content type */
    public String getFileContentType()
    { return this.FileContentType; }
    
    /** Returns the pdf file size */
    public long getPDFFileSize()
    { return this.PdfFileSize; }
    
    /** Returns the creation date */
    public String getCreatedDate()
    { return this.CreatedDate; }
    
    /** Returns the last update date */
    public String getLastUpdatedDate()
    { return this.LastUpdatedDate; }
    
    /** Returns the last updated by */
    public String getLastUpdatedBy()
    { return this.LastUpdatedBy; }
    
    /** Sets the original key 1 (Effective Date) */
    public void setOrgKey1(Date orgKey1)
    { this.OrgKey1 = orgKey1; }
    
    /** Sets the original key 2 (Service Category) */
    public void setOrgKey2(String orgKey2)
    { this.OrgKey2 = orgKey2; }
    
    /** Sets the effective Date */
    public void setEffectiveDate(Date effectiveDate)
    { this.EffectiveDate = effectiveDate; }
    
    /** Sets the service category */
    public void setServiceCategoryGroup(String serviceCategoryGroup)
    { this.ServiceCategoryGroup = serviceCategoryGroup; }
    
    /** Sets the active indicator of the discount rate record */
    public void setActiveInd(int activeInd)
    { this.ActiveInd = activeInd; }
    
    /** Sets the file name */
    public void setPDFFileName(String pdfFileName)
    { this.PdfFileName = pdfFileName; }
    
    /** Sets the pdf file */
    public void setPDFFile(Blob pdfFile)
    { this.PdfFile = pdfFile; }
    
    /** Sets the pdf file content type */
    public void setFileContentType(String fileContentType)
    { this.FileContentType = fileContentType; }
    
    /** Sets the pdf file size */
    public void setPDFFileSize(long pdfFileSize)
    { this.PdfFileSize = pdfFileSize; }
    
    /** Sets the creation date */
    public void setCreatedDate(String createdDate)
    { this.CreatedDate = createdDate; }
    
    /** Sets the last update date */
    public void setLastUpdatedDate(String lastUpdatedDate)
    { this.LastUpdatedDate = lastUpdatedDate; }
    
    /** Sets the last updated by */
    public void setLastUpdatedBy(String lastUpdatedBy)
    { this.LastUpdatedBy = lastUpdatedBy; }
}
