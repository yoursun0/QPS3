package qpses.business;

import java.sql.Date;

public class CeilingRate4Info{
    
    private Date effectiveDate;
    private String contractorId;
    private String contractorName;
    private String currency;
    private int activeInd;
    private int ongoingSuppInd;
    private float oneoffSc1Resident;
    private float oneoffSc2Resident;
    private float oneoffSc3Resident;
    private float oneoffSc4Resident;
    private float oneoffSc5Resident;
    private float oneoffSc6Resident;    
    private float oneoffSc1NonResident;
    private float oneoffSc2NonResident;
    private float oneoffSc3NonResident;
    private float oneoffSc4NonResident;
    private float oneoffSc5NonResident;
    private float oneoffSc6NonResident;    
    private float oneoffSc1Offshore;
    private float oneoffSc2Offshore;
    private float oneoffSc3Offshore;
    private float oneoffSc4Offshore;
    private float oneoffSc5Offshore;
    private float oneoffSc6Offshore;       
    
    private float ongoingSc1OfficeHours;
    private float ongoingSc2OfficeHours;
    private float ongoingSc3OfficeHours;
    private float ongoingSc4OfficeHours;
    private float ongoingSc5OfficeHours;
    private float ongoingSc1NonOfficeHours;
    private float ongoingSc2NonOfficeHours;
    private float ongoingSc3NonOfficeHours;
    private float ongoingSc4NonOfficeHours;
    private float ongoingSc5NonOfficeHours;
    private float ongoingSc1RoundTheClock;
    private float ongoingSc2RoundTheClock;
    private float ongoingSc3RoundTheClock;
    private float ongoingSc4RoundTheClock;
    private float ongoingSc5RoundTheClock;
    
    private String createdDate;
    private String lastUpdatedDate;
    private String lastUpdatedBy;
    private Date orgKey1;
    private String orgKey2;
    
    
    /**
     * Constructor for QualitySubscoreInfo.
     */
    public CeilingRate4Info() {
        super();
    }
    
    /**
     * Returns the original Key 1 (Effective Date).
     * @return Date
     */
    public Date getOrgKey1() {
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
     * Returns the Effective Date.
     * @return Date
     */
    public Date getEffectiveDate() {
        return effectiveDate;
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
     * Returns the currency
     * @return String
     */
    public String getCurrency() {
        return currency;
    }
    
    /**
     * Returns the active indicator of the ceiling rate record
     * @return boolean
     */
    public int getActiveInd() {
        return activeInd;
    }
    
    /**
     * Returns the ongoingSupplementary indicator for the contractor
     * @return boolean
     */
    public int getOngoingSuppInd() {
        return ongoingSuppInd;
    }
    
    /**
     * Returns the staff category 1 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc1Resident() {
        return oneoffSc1Resident;
    }
    
    /**
     * Returns the staff category 2 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc2Resident() {
        return oneoffSc2Resident;
    }
    
    /**
     * Returns the staff category 3 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc3Resident() {
        return oneoffSc3Resident;
    }
    
    /**
     * Returns the staff category 4 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc4Resident() {
        return oneoffSc4Resident;
    }
    
    /**
     * Returns the staff category 5 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc5Resident() {
        return oneoffSc5Resident;
    }
    
    /**
     * Returns the staff category 6 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc6Resident() {
        return oneoffSc6Resident;
    }   
   
    /**
     * Returns the staff category 1 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc1NonResident() {
        return oneoffSc1NonResident;
    }
    
    /**
     * Returns the staff category 2 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc2NonResident() {
        return oneoffSc2NonResident;
    }
    
    /**
     * Returns the staff category 3 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc3NonResident() {
        return oneoffSc3NonResident;
    }
    
    /**
     * Returns the staff category 4 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc4NonResident() {
        return oneoffSc4NonResident;
    }
    
    /**
     * Returns the staff category 5 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc5NonResident() {
        return oneoffSc5NonResident;
    }
    
    /**
     * Returns the staff category 6 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc6NonResident() {
        return oneoffSc6NonResident;
    }
        
    /**
     * Returns the staff category 1 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc1Offshore() {
        return oneoffSc1Offshore;
    }
    
    /**
     * Returns the staff category 2 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc2Offshore() {
        return oneoffSc2Offshore;
    }
    
    /**
     * Returns the staff category 3 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc3Offshore() {
        return oneoffSc3Offshore;
    }
    
    /**
     * Returns the staff category 4 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc4Offshore() {
        return oneoffSc4Offshore;
    }
    
    /**
     * Returns the staff category 5 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc5Offshore() {
        return oneoffSc5Offshore;
    }
    
    /**
     * Returns the staff category 6 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc6Offshore() {
        return oneoffSc6Offshore;
    }      
    
    /**
     * Returns the staff category 1 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1OfficeHours() {
        return ongoingSc1OfficeHours;
    }
    
    /**
     * Returns the staff category 2 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2OfficeHours() {
        return ongoingSc2OfficeHours;
    }
    
    /**
     * Returns the staff category 3 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3OfficeHours() {
        return ongoingSc3OfficeHours;
    }
    
    /**
     * Returns the staff category 4 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4OfficeHours() {
        return ongoingSc4OfficeHours;
    }
    
    /**
     * Returns the staff category 5 resident M1rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5OfficeHours() {
        return ongoingSc5OfficeHours;
    }
        
    /**
     * Returns the staff category 1 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1NonOfficeHours() {
        return ongoingSc1NonOfficeHours;
    }
    
    /**
     * Returns the staff category 2 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2NonOfficeHours() {
        return ongoingSc2NonOfficeHours;
    }
    
    /**
     * Returns the staff category 3 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3NonOfficeHours() {
        return ongoingSc3NonOfficeHours;
    }
    
    /**
     * Returns the staff category 4 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4NonOfficeHours() {
        return ongoingSc4NonOfficeHours;
    }
    
    /**
     * Returns the staff category 5 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5NonOfficeHours() {
        return ongoingSc5NonOfficeHours;
    }
        
    /**
     * Returns the staff category 1 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1RoundTheClock() {
        return ongoingSc1RoundTheClock;
    }
    
    /**
     * Returns the staff category 2 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2RoundTheClock() {
        return ongoingSc2RoundTheClock;
    }
    
    /**
     * Returns the staff category 3 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3RoundTheClock() {
        return ongoingSc3RoundTheClock;
    }
    
    /**
     * Returns the staff category 4 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4RoundTheClock() {
        return ongoingSc4RoundTheClock;
    }
    
    /**
     * Returns the staff category 5 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5RoundTheClock() {
        return ongoingSc5RoundTheClock;
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
     * Sets the original key 1 (Effective Start Date)
     * @param original effectiveDate - The original Effective Date to set
     */
    public void setOrgKey1(Date orgKey1) {
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
     * Sets the Effective Date.
     * @param effectiveDate - The Effective Date to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
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
     * Sets the currency
     * @param  currency - The  currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    /**
     * Sets the active indicator of the ceiling rate record
     * @param activInd - The active indicator to set
     */
    public void setActiveInd(int activeInd) {
        this.activeInd = activeInd;
    }
    
    /**
     * Sets the ongoingSupplementary indicator for contractor
     * @param ongoingSuppInd - The ongoingSupplementary indicator to set
     */
    public void setSuppInd(int ongoingSuppInd) {
        this.ongoingSuppInd = ongoingSuppInd;
    }
    
    /**
     * Sets the Staff Category 1 Resident rate for one off service
     * @param oneoffSc1Resident - The Staff Category 1 Resident rate to set
     */
    public void setOneoffSc1Resident(float oneoffSc1Resident) {
        this.oneoffSc1Resident = oneoffSc1Resident;
    }
    
    /**
     * Sets the Staff Category 2 Resident rate for one off service
     * @param oneoffSc2Resident - The Staff Category 2 Resident rate to set
     */
    public void setOneoffSc2Resident(float oneoffSc2Resident) {
        this.oneoffSc2Resident = oneoffSc2Resident;
    }
    
    /**
     * Sets the Staff Category 3 Resident rate for one off service
     * @param oneoffsc3Resident - The Staff Category 3 Resident rate to set
     */
    public void setOneoffSc3Resident(float oneoffSc3Resident) {
        this.oneoffSc3Resident = oneoffSc3Resident;
    }
    
    /**
     * Sets the Staff Category 4 Resident rate for one off service
     * @param oneoffSc4Resident - The Staff Category 4 Resident rate to set
     */
    public void setOneoffSc4Resident(float oneoffSc4Resident) {
        this.oneoffSc4Resident = oneoffSc4Resident;
    }
    
    /**
     * Sets the Staff Category 5 Resident rate for one off service
     * @param oneoffSc5Resident - The Staff Category 5 Resident rate to set
     */
    public void setOneoffSc5Resident(float oneoffSc5Resident) {
        this.oneoffSc5Resident = oneoffSc5Resident;
    }
    
    /**
     * Sets the Staff Category 6 Resident rate for one off service
     * @param oneoffSc6Resident - The Staff Category 6 Resident rate to set
     */
    public void setOneoffSc6Resident(float oneoffSc6Resident) {
        this.oneoffSc6Resident = oneoffSc6Resident;
    }
        
    /**
     * Sets the Staff Category 1 Non Resident rate for one off service
     * @param oneoffSc1NonResident - The Staff Category 1 Non Resident rate to set
     */
    public void setOneoffSc1NonResident(float oneoffSc1NonResident) {
        this.oneoffSc1NonResident = oneoffSc1NonResident;
    }
    
    /**
     * Sets the Staff Category 2 Non Resident rate for one off service
     * @param oneoffSc2NonResident - The Staff Category 2 Non Resident rate to set
     */
    public void setOneoffSc2NonResident(float oneoffSc2NonResident) {
        this.oneoffSc2NonResident = oneoffSc2NonResident;
    }
    
    /**
     * Sets the Staff Category 3 Non Resident rate for one off service
     * @param oneoffSc3NonResident - The Staff Category 3 Non Resident rate to set
     */
    public void setOneoffSc3NonResident(float oneoffSc3NonResident) {
        this.oneoffSc3NonResident = oneoffSc3NonResident;
    }
    
    /**
     * Sets the Staff Category 4 Non Resident rate for one off service
     * @param oneoffSc4NonResident - The Staff Category 4 Non Resident rate to set
     */
    public void setOneoffSc4NonResident(float oneoffSc4NonResident) {
        this.oneoffSc4NonResident = oneoffSc4NonResident;
    }
    
    /**
     * Sets the Staff Category 5 Non Resident rate for one off service
     * @param oneoffSc5NonResident - The Staff Category 5 Non Resident rate to set
     */
    public void setOneoffSc5NonResident(float oneoffSc5NonResident) {
        this.oneoffSc5NonResident = oneoffSc5NonResident;
    }
    
    /**
     * Sets the Staff Category 6 Non Resident rate for one off service
     * @param oneoffSc6NonResident - The Staff Category 6 Non Resident rate to set
     */
    public void setOneoffSc6NonResident(float oneoffSc6NonResident) {
        this.oneoffSc6NonResident = oneoffSc6NonResident;
    }
        
    /**
     * Sets the Staff Category 1 Offshore rate for one off service
     * @param oneoffSc1Offshore - The Staff Category 1 Offshore rate to set
     */
    public void setOneoffSc1Offshore(float oneoffSc1Offshore) {
        this.oneoffSc1Offshore = oneoffSc1Offshore;
    }
    
    /**
     * Sets the Staff Category 2 Offshore rate for one off service
     * @param oneoffSc2Offshore - The Staff Category 2 Offshore rate to set
     */
    public void setOneoffSc2Offshore(float oneoffSc2Offshore) {
        this.oneoffSc2Offshore = oneoffSc2Offshore;
    }
    
    /**
     * Sets the Staff Category 3 Offshore rate for one off service
     * @param oneoffSc3Offshore - The Staff Category 3 Offshore rate to set
     */
    public void setOneoffSc3Offshore(float oneoffSc3Offshore) {
        this.oneoffSc3Offshore = oneoffSc3Offshore;
    }
    
    /**
     * Sets the Staff Category 4 Offshore rate for one off service
     * @param oneoffSc4Offshore - The Staff Category 4 Offshore rate to set
     */
    public void setOneoffSc4Offshore(float oneoffSc4Offshore) {
        this.oneoffSc4Offshore = oneoffSc4Offshore;
    }
    
    /**
     * Sets the Staff Category 5 Offshore rate for one off service
     * @param oneoffSc5Offshore - The Staff Category 5 Offshore rate to set
     */
    public void setOneoffSc5Offshore(float oneoffSc5Offshore) {
        this.oneoffSc5Offshore = oneoffSc5Offshore;
    }
    
    /**
     * Sets the Staff Category 6 Offshore rate for one off service
     * @param oneoffSc6Offshore - The Staff Category 6 Offshore rate to set
     */
    public void setOneoffSc6Offshore(float oneoffSc6Offshore) {
        this.oneoffSc6Offshore = oneoffSc6Offshore;
    }
        
    /**
     * Sets the Staff Category 1 Resident M1 rate for ongoing service
     * @param ongoingSc1OfficeHours  - The Staff Category 1 Resident M1 rate to set
     */
    public void setOngoingSc1OfficeHours(float ongoingSc1OfficeHours) {
        this.ongoingSc1OfficeHours  = ongoingSc1OfficeHours;
    }
    
    /**
     * Sets the Staff Category 2 Resident M1 rate for ongoing service
     * @param ongoingSc2OfficeHours  - The Staff Category 2 Resident M1 rate to set
     */
    public void setOngoingSc2OfficeHours(float ongoingSc2OfficeHours) {
        this.ongoingSc2OfficeHours  = ongoingSc2OfficeHours;
    }
    
    /**
     * Sets the Staff Category 3 Resident M1 rate for ongoing service
     * @param ongoingSc3OfficeHours - The Staff Category 3 Resident M1 rate to set
     */
    public void setOngoingSc3OfficeHours(float ongoingSc3OfficeHours) {
        this.ongoingSc3OfficeHours = ongoingSc3OfficeHours;
    }
    
    /**
     * Sets the Staff Category 4 Resident M1 rate for ongoing service
     * @param ongoingSc4OfficeHours - The Staff Category 4 Resident M1 rate to set
     */
    public void setOngoingSc4OfficeHours(float ongoingSc4OfficeHours) {
        this.ongoingSc4OfficeHours = ongoingSc4OfficeHours;
    }
    
    /**
     * Sets the Staff Category 5 Resident M1 rate for ongoing service
     * @param ongoingSc5OfficeHours - The Staff Category 5 Resident M1 rate to set
     */
    public void setOngoingSc5OfficeHours(float ongoingSc5OfficeHours) {
        this.ongoingSc5OfficeHours = ongoingSc5OfficeHours;
    }
    
    /**
     * Sets the Staff Category 1 Resident M2 rate for ongoing service
     * @param ongoingSc1NonOfficeHours  - The Staff Category 1 Resident M2 rate to set
     */
    public void setOngoingSc1NonOfficeHours(float ongoingSc1NonOfficeHours) {
        this.ongoingSc1NonOfficeHours  = ongoingSc1NonOfficeHours;
    }
    
    /**
     * Sets the Staff Category 2 Resident M2 rate for ongoing service
     * @param ongoingSc2NonOfficeHours  - The Staff Category 2 Resident M2 rate to set
     */
    public void setOngoingSc2NonOfficeHours(float ongoingSc2NonOfficeHours) {
        this.ongoingSc2NonOfficeHours  = ongoingSc2NonOfficeHours;
    }
    
    /**
     * Sets the Staff Category 3 Resident M2 rate for ongoing service
     * @param ongoingSc3NonOfficeHours - The Staff Category 3 Resident M2 rate to set
     */
    public void setOngoingSc3NonOfficeHours(float ongoingSc3NonOfficeHours) {
        this.ongoingSc3NonOfficeHours = ongoingSc3NonOfficeHours;
    }
    
    /**
     * Sets the Staff Category 4 Resident M2 rate for ongoing service
     * @param ongoingSc4NonOfficeHours - The Staff Category 4 Resident M2 rate to set
     */
    public void setOngoingSc4NonOfficeHours(float ongoingSc4NonOfficeHours) {
        this.ongoingSc4NonOfficeHours = ongoingSc4NonOfficeHours;
    }
    
    /**
     * Sets the Staff Category 5 Resident M2 rate for ongoing service
     * @param ongoingSc5NonOfficeHours - The Staff Category 5 Resident M2 rate to set
     */
    public void setOngoingSc5NonOfficeHours(float ongoingSc5NonOfficeHours) {
        this.ongoingSc5NonOfficeHours = ongoingSc5NonOfficeHours;
    }
    
    /**
     * Sets the Staff Category 1 Resident M3 rate for ongoing service
     * @param ongoingSc1RoundTheClock  - The Staff Category 1 Resident M3 rate to set
     */
    public void setOngoingSc1RoundTheClock(float ongoingSc1RoundTheClock) {
        this.ongoingSc1RoundTheClock  = ongoingSc1RoundTheClock;
    }
    
    /**
     * Sets the Staff Category 2 Resident M3 rate for ongoing service
     * @param ongoingSc2RoundTheClock  - The Staff Category 2 Resident M3 rate to set
     */
    public void setOngoingSc2RoundTheClock(float ongoingSc2RoundTheClock) {
        this.ongoingSc2RoundTheClock  = ongoingSc2RoundTheClock;
    }
    
    /**
     * Sets the Staff Category 3 Resident M3 rate for ongoing service
     * @param ongoingSc3RoundTheClock - The Staff Category 3 Resident M3 rate to set
     */
    public void setOngoingSc3RoundTheClock(float ongoingSc3RoundTheClock) {
        this.ongoingSc3RoundTheClock = ongoingSc3RoundTheClock;
    }
    
    /**
     * Sets the Staff Category 4 Resident M3 rate for ongoing service
     * @param ongoingSc4RoundTheClock - The Staff Category 4 Resident M3 rate to set
     */
    public void setOngoingSc4RoundTheClock(float ongoingSc4RoundTheClock) {
        this.ongoingSc4RoundTheClock = ongoingSc4RoundTheClock;
    }
    
    /**
     * Sets the Staff Category 5 Resident M3 rate for ongoing service
     * @param ongoingSc5RoundTheClock - The Staff Category 5 Resident M3 rate to set
     */
    public void setOngoingSc5RoundTheClock(float ongoingSc5RoundTheClock) {
        this.ongoingSc5RoundTheClock = ongoingSc5RoundTheClock;
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
