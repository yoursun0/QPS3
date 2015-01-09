package qpses.business;

import java.sql.Date;

public class CeilingRate3Info{
    
    private Date effectiveDate;
    private String contractorId;
    private String contractorName;
    private String currency;
    private int activeInd;
    private int SuppInd;
    private float oneoffSc1Resident;
    private float oneoffSc2Resident;
    private float oneoffSc3Resident;
    private float oneoffSc4Resident;
    private float oneoffSc5Resident;
    private float oneoffSc6Resident;
    private float oneoffSc7Resident;
    private float oneoffSc8Resident;
    private float oneoffSc9Resident;
    private float oneoffSc10Resident;
    private float oneoffSc11Resident;
    private float oneoffSc12Resident;
    private float oneoffSc1NonResident;
    private float oneoffSc2NonResident;
    private float oneoffSc3NonResident;
    private float oneoffSc4NonResident;
    private float oneoffSc5NonResident;
    private float oneoffSc6NonResident;
    private float oneoffSc7NonResident;
    private float oneoffSc8NonResident;
    private float oneoffSc9NonResident;
    private float oneoffSc10NonResident;
    private float oneoffSc11NonResident;
    private float oneoffSc12NonResident;
    private float oneoffSc1Offshore;
    private float oneoffSc2Offshore;
    private float oneoffSc3Offshore;
    private float oneoffSc4Offshore;
    private float oneoffSc5Offshore;
    private float oneoffSc6Offshore;
    private float oneoffSc7Offshore;
    private float oneoffSc8Offshore;
    private float oneoffSc9Offshore;
    private float oneoffSc10Offshore;
    private float oneoffSc11Offshore;
    private float oneoffSc12Offshore;
    private float oneoffSupp1Resident;
    private float oneoffSupp1NonResident;
    private float oneoffSupp1Offshore;
    
    
    private float ongoingSc1ResidentM1;
    private float ongoingSc2ResidentM1;
    private float ongoingSc3ResidentM1;
    private float ongoingSc4ResidentM1;
    private float ongoingSc5ResidentM1;
    private float ongoingSc6ResidentM1;
    private float ongoingSc7ResidentM1;
    private float ongoingSc8ResidentM1;
    private float ongoingSc9ResidentM1;
    private float ongoingSc10ResidentM1;
    private float ongoingSc1ResidentM2;
    private float ongoingSc2ResidentM2;
    private float ongoingSc3ResidentM2;
    private float ongoingSc4ResidentM2;
    private float ongoingSc5ResidentM2;
    private float ongoingSc6ResidentM2;
    private float ongoingSc7ResidentM2;
    private float ongoingSc8ResidentM2;
    private float ongoingSc9ResidentM2;
    private float ongoingSc10ResidentM2;
    private float ongoingSc1ResidentM3;
    private float ongoingSc2ResidentM3;
    private float ongoingSc3ResidentM3;
    private float ongoingSc4ResidentM3;
    private float ongoingSc5ResidentM3;
    private float ongoingSc6ResidentM3;
    private float ongoingSc7ResidentM3;
    private float ongoingSc8ResidentM3;
    private float ongoingSc9ResidentM3;
    private float ongoingSc10ResidentM3;
    
    private float ongoingSc1NonResidentM1;
    private float ongoingSc2NonResidentM1;
    private float ongoingSc3NonResidentM1;
    private float ongoingSc4NonResidentM1;
    private float ongoingSc5NonResidentM1;
    private float ongoingSc6NonResidentM1;
    private float ongoingSc7NonResidentM1;
    private float ongoingSc8NonResidentM1;
    private float ongoingSc9NonResidentM1;
    private float ongoingSc10NonResidentM1;
    private float ongoingSc1NonResidentM2;
    private float ongoingSc2NonResidentM2;
    private float ongoingSc3NonResidentM2;
    private float ongoingSc4NonResidentM2;
    private float ongoingSc5NonResidentM2;
    private float ongoingSc6NonResidentM2;
    private float ongoingSc7NonResidentM2;
    private float ongoingSc8NonResidentM2;
    private float ongoingSc9NonResidentM2;
    private float ongoingSc10NonResidentM2;
    private float ongoingSc1NonResidentM3;
    private float ongoingSc2NonResidentM3;
    private float ongoingSc3NonResidentM3;
    private float ongoingSc4NonResidentM3;
    private float ongoingSc5NonResidentM3;
    private float ongoingSc6NonResidentM3;
    private float ongoingSc7NonResidentM3;
    private float ongoingSc8NonResidentM3;
    private float ongoingSc9NonResidentM3;
    private float ongoingSc10NonResidentM3;
    
    private float ongoingSc1OffshoreM1;
    private float ongoingSc2OffshoreM1;
    private float ongoingSc3OffshoreM1;
    private float ongoingSc4OffshoreM1;
    private float ongoingSc5OffshoreM1;
    private float ongoingSc6OffshoreM1;
    private float ongoingSc7OffshoreM1;
    private float ongoingSc8OffshoreM1;
    private float ongoingSc9OffshoreM1;
    private float ongoingSc10OffshoreM1;
    
    private float ongoingSc1OffshoreM2;
    private float ongoingSc2OffshoreM2;
    private float ongoingSc3OffshoreM2;
    private float ongoingSc4OffshoreM2;
    private float ongoingSc5OffshoreM2;
    private float ongoingSc6OffshoreM2;
    private float ongoingSc7OffshoreM2;
    private float ongoingSc8OffshoreM2;
    private float ongoingSc9OffshoreM2;
    private float ongoingSc10OffshoreM2;
    
    private float ongoingSc1OffshoreM3;
    private float ongoingSc2OffshoreM3;
    private float ongoingSc3OffshoreM3;
    private float ongoingSc4OffshoreM3;
    private float ongoingSc5OffshoreM3;
    private float ongoingSc6OffshoreM3;
    private float ongoingSc7OffshoreM3;
    private float ongoingSc8OffshoreM3;
    private float ongoingSc9OffshoreM3;
    private float ongoingSc10OffshoreM3;
    
    private float ongoingSupp1ResidentM1;
    private float ongoingSupp1ResidentM2;
    private float ongoingSupp1ResidentM3;
    private float ongoingSupp1NonResidentM1;
    private float ongoingSupp1NonResidentM2;
    private float ongoingSupp1NonResidentM3;
    private float ongoingSupp1OffshoreM1;
    private float ongoingSupp1OffshoreM2;
    private float ongoingSupp1OffshoreM3;
    
    private String createdDate;
    private String lastUpdatedDate;
    private String lastUpdatedBy;
    private Date orgKey1;
    private String orgKey2;
    
    
    /**
     * Constructor for QualitySubscoreInfo.
     */
    public CeilingRate3Info() {
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
    public int getSuppInd() {
        return SuppInd;
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
     * Returns the staff category 7 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc7Resident() {
        return oneoffSc7Resident;
    }
    
    /**
     * Returns the staff category 8 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc8Resident() {
        return oneoffSc8Resident;
    }
    
    /**
     * Returns the staff category 9 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc9Resident() {
        return oneoffSc9Resident;
    }
    
    /**
     * Returns the staff category 10 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc10Resident() {
        return oneoffSc10Resident;
    }
    
    /**
     * Returns the staff category 11 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc11Resident() {
        return oneoffSc11Resident;
    }
    
    /**
     * Returns the staff category 12 resident rate for one off service
     * @return Float
     */
    public float getOneoffSc12Resident() {
        return oneoffSc12Resident;
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
     * Returns the staff category 7 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc7NonResident() {
        return oneoffSc7NonResident;
    }
    
    /**
     * Returns the staff category 8 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc8NonResident() {
        return oneoffSc8NonResident;
    }
    
    /**
     * Returns the staff category 9 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc9NonResident() {
        return oneoffSc9NonResident;
    }
    
    /**
     * Returns the staff category 10 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc10NonResident() {
        return oneoffSc10NonResident;
    }
    
    /**
     * Returns the staff category 11 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc11NonResident() {
        return oneoffSc11NonResident;
    }
    
    /**
     * Returns the staff category 12 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSc12NonResident() {
        return oneoffSc12NonResident;
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
     * Returns the staff category 7 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc7Offshore() {
        return oneoffSc7Offshore;
    }
    
    /**
     * Returns the staff category 8 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc8Offshore() {
        return oneoffSc8Offshore;
    }
    
    /**
     * Returns the staff category 9 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc9Offshore() {
        return oneoffSc9Offshore;
    }
    
    /**
     * Returns the staff category 10 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc10Offshore() {
        return oneoffSc10Offshore;
    }
    
    /**
     * Returns the staff category 11 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc11Offshore() {
        return oneoffSc11Offshore;
    }
    
    /**
     * Returns the staff category 12 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSc12Offshore() {
        return oneoffSc12Offshore;
    }
    
    
    /**
     * Returns the ongoingSupplementary staff category 1 resident rate for one off service
     * @return Float
     */
    public float getOneoffSupp1Resident() {
        return oneoffSupp1Resident;
    }
    
    /**
     * Returns the ongoingSupplementary staff category 1 non resident rate for one off service
     * @return Float
     */
    public float getOneoffSupp1NonResident() {
        return oneoffSupp1NonResident;
    }
    
    /**
     * Returns the ongoingSupplementary staff category 1 offshore rate for one off service
     * @return Float
     */
    public float getOneoffSupp1Offshore() {
        return oneoffSupp1Offshore;
    }
    
    /**
     * Returns the staff category 1 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1ResidentM1() {
        return ongoingSc1ResidentM1;
    }
    
    /**
     * Returns the staff category 2 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2ResidentM1() {
        return ongoingSc2ResidentM1;
    }
    
    /**
     * Returns the staff category 3 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3ResidentM1() {
        return ongoingSc3ResidentM1;
    }
    
    /**
     * Returns the staff category 4 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4ResidentM1() {
        return ongoingSc4ResidentM1;
    }
    
    /**
     * Returns the staff category 5 resident M1rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5ResidentM1() {
        return ongoingSc5ResidentM1;
    }
    
    /**
     * Returns the staff category 6 resident M1rate for ongoing service
     * @return Float
     */
    public float getOngoingSc6ResidentM1() {
        return ongoingSc6ResidentM1;
    }
    
    /**
     * Returns the staff category 7 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc7ResidentM1() {
        return ongoingSc7ResidentM1;
    }
    
    /**
     * Returns the staff category 8 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc8ResidentM1() {
        return ongoingSc8ResidentM1;
    }
    
    /**
     * Returns the staff category 9 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc9ResidentM1() {
        return ongoingSc9ResidentM1;
    }
    
    /**
     * Returns the staff category 10 resident M1rate for ongoing service
     * @return Float
     */
    public float getOngoingSc10ResidentM1() {
        return ongoingSc10ResidentM1;
    }
    
    /**
     * Returns the staff category 1 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1ResidentM2() {
        return ongoingSc1ResidentM2;
    }
    
    /**
     * Returns the staff category 2 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2ResidentM2() {
        return ongoingSc2ResidentM2;
    }
    
    /**
     * Returns the staff category 3 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3ResidentM2() {
        return ongoingSc3ResidentM2;
    }
    
    /**
     * Returns the staff category 4 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4ResidentM2() {
        return ongoingSc4ResidentM2;
    }
    
    /**
     * Returns the staff category 5 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5ResidentM2() {
        return ongoingSc5ResidentM2;
    }
    
    /**
     * Returns the staff category 6 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc6ResidentM2() {
        return ongoingSc6ResidentM2;
    }
    
    /**
     * Returns the staff category 7 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc7ResidentM2() {
        return ongoingSc7ResidentM2;
    }
    
    /**
     * Returns the staff category 8 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc8ResidentM2() {
        return ongoingSc8ResidentM2;
    }
    
    /**
     * Returns the staff category 9 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc9ResidentM2() {
        return ongoingSc9ResidentM2;
    }
    
    /**
     * Returns the staff category 10 resident M2rate for ongoing service
     * @return Float
     */
    public float getOngoingSc10ResidentM2() {
        return ongoingSc10ResidentM2;
    }
    
    /**
     * Returns the staff category 1 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1ResidentM3() {
        return ongoingSc1ResidentM3;
    }
    
    /**
     * Returns the staff category 2 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2ResidentM3() {
        return ongoingSc2ResidentM3;
    }
    
    /**
     * Returns the staff category 3 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3ResidentM3() {
        return ongoingSc3ResidentM3;
    }
    
    /**
     * Returns the staff category 4 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4ResidentM3() {
        return ongoingSc4ResidentM3;
    }
    
    /**
     * Returns the staff category 5 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5ResidentM3() {
        return ongoingSc5ResidentM3;
    }
    
    /**
     * Returns the staff category 6 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc6ResidentM3() {
        return ongoingSc6ResidentM3;
    }
    
    /**
     * Returns the staff category 7 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc7ResidentM3() {
        return ongoingSc7ResidentM3;
    }
    
    /**
     * Returns the staff category 8 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc8ResidentM3() {
        return ongoingSc8ResidentM3;
    }
    
    /**
     * Returns the staff category 9 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc9ResidentM3() {
        return ongoingSc9ResidentM3;
    }
    
    /**
     * Returns the staff category 10 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc10ResidentM3() {
        return ongoingSc10ResidentM3;
    }
    
    
    /**
     * Returns the staff category 1 Non Resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1NonResidentM1() {
        return ongoingSc1NonResidentM1;
    }
    
    /**
     * Returns the staff category 2 Non Resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2NonResidentM1() {
        return ongoingSc2NonResidentM1;
    }
    
    /**
     * Returns the staff category 3 Non Resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3NonResidentM1() {
        return ongoingSc3NonResidentM1;
    }
    
    /**
     * Returns the staff category 4 Non Resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4NonResidentM1() {
        return ongoingSc4NonResidentM1;
    }
    
    /**
     * Returns the staff category 5 Non Resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5NonResidentM1() {
        return ongoingSc5NonResidentM1;
    }
    
    /**
     * Returns the staff category 6 Non Resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc6NonResidentM1() {
        return ongoingSc6NonResidentM1;
    }
    
    /**
     * Returns the staff category 7 Non Resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc7NonResidentM1() {
        return ongoingSc7NonResidentM1;
    }
    
    /**
     * Returns the staff category 8 Non Resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc8NonResidentM1() {
        return ongoingSc8NonResidentM1;
    }
    
    /**
     * Returns the staff category 9 Non Resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc9NonResidentM1() {
        return ongoingSc9NonResidentM1;
    }
    
    /**
     * Returns the staff category 10 Non Resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc10NonResidentM1() {
        return ongoingSc10NonResidentM1;
    }
    
    /**
     * Returns the staff category 1 Non Resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1NonResidentM2() {
        return ongoingSc1NonResidentM2;
    }
    
    /**
     * Returns the staff category 2 Non Resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2NonResidentM2() {
        return ongoingSc2NonResidentM2;
    }
    
    /**
     * Returns the staff category 3 Non Resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3NonResidentM2() {
        return ongoingSc3NonResidentM2;
    }
    
    /**
     * Returns the staff category 4 Non Resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4NonResidentM2() {
        return ongoingSc4NonResidentM2;
    }
    
    /**
     * Returns the staff category 5 Non Resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5NonResidentM2() {
        return ongoingSc5NonResidentM2;
    }
    
    /**
     * Returns the staff category 6 Non Resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc6NonResidentM2() {
        return ongoingSc6NonResidentM2;
    }
    
    /**
     * Returns the staff category 7 Non Resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc7NonResidentM2() {
        return ongoingSc7NonResidentM2;
    }
    
    /**
     * Returns the staff category 8 Non Resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc8NonResidentM2() {
        return ongoingSc8NonResidentM2;
    }
    
    /**
     * Returns the staff category 9 Non Resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc9NonResidentM2() {
        return ongoingSc9NonResidentM2;
    }
    
    /**
     * Returns the staff category 10 Non Resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc10NonResidentM2() {
        return ongoingSc10NonResidentM2;
    }
    
    /**
     * Returns the staff category 1 Non Resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1NonResidentM3() {
        return ongoingSc1NonResidentM3;
    }
    
    /**
     * Returns the staff category 2 Non Resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2NonResidentM3() {
        return ongoingSc2NonResidentM3;
    }
    
    /**
     * Returns the staff category 3 Non Resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3NonResidentM3() {
        return ongoingSc3NonResidentM3;
    }
    
    /**
     * Returns the staff category 4 Non Resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4NonResidentM3() {
        return ongoingSc4NonResidentM3;
    }
    
    /**
     * Returns the staff category 5 Non Resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5NonResidentM3() {
        return ongoingSc5NonResidentM3;
    }
    
    /**
     * Returns the staff category 6 Non Resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc6NonResidentM3() {
        return ongoingSc6NonResidentM3;
    }
    
    /**
     * Returns the staff category 7 Non Resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc7NonResidentM3() {
        return ongoingSc7NonResidentM3;
    }
    
    /**
     * Returns the staff category 8 Non Resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc8NonResidentM3() {
        return ongoingSc8NonResidentM3;
    }
    
    /**
     * Returns the staff category 9 Non Resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc9NonResidentM3() {
        return ongoingSc9NonResidentM3;
    }
    
    /**
     * Returns the staff category 10 Non Resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc10NonResidentM3() {
        return ongoingSc10NonResidentM3;
    }
    
    /**
     * Returns the staff category 1 Offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1OffshoreM1() {
        return ongoingSc1OffshoreM1;
    }
    
    /**
     * Returns the staff category 2 Offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2OffshoreM1() {
        return ongoingSc2OffshoreM1;
    }
    
    /**
     * Returns the staff category 3 Offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3OffshoreM1() {
        return ongoingSc3OffshoreM1;
    }
    
    /**
     * Returns the staff category 4 Offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4OffshoreM1() {
        return ongoingSc4OffshoreM1;
    }
    
    /**
     * Returns the staff category 5 Offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5OffshoreM1() {
        return ongoingSc5OffshoreM1;
    }
    
    /**
     * Returns the staff category 6 Offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc6OffshoreM1() {
        return ongoingSc6OffshoreM1;
    }
    
    /**
     * Returns the staff category 7 Offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc7OffshoreM1() {
        return ongoingSc7OffshoreM1;
    }
    
    /**
     * Returns the staff category 8 Offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc8OffshoreM1() {
        return ongoingSc8OffshoreM1;
    }
    
    /**
     * Returns the staff category 9 Offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc9OffshoreM1() {
        return ongoingSc9OffshoreM1;
    }
    
    /**
     * Returns the staff category 10 Offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc10OffshoreM1() {
        return ongoingSc10OffshoreM1;
    }
    
    /**
     * Returns the staff category 1 Offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1OffshoreM2() {
        return ongoingSc1OffshoreM2;
    }
    
    /**
     * Returns the staff category 2 Offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2OffshoreM2() {
        return ongoingSc2OffshoreM2;
    }
    
    /**
     * Returns the staff category 3 Offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3OffshoreM2() {
        return ongoingSc3OffshoreM2;
    }
    
    /**
     * Returns the staff category 4 Offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4OffshoreM2() {
        return ongoingSc4OffshoreM2;
    }
    
    /**
     * Returns the staff category 5 Offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5OffshoreM2() {
        return ongoingSc5OffshoreM2;
    }
    
    /**
     * Returns the staff category 6 Offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc6OffshoreM2() {
        return ongoingSc6OffshoreM2;
    }
    
    /**
     * Returns the staff category 7 Offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc7OffshoreM2() {
        return ongoingSc7OffshoreM2;
    }
    
    /**
     * Returns the staff category 8 Offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc8OffshoreM2() {
        return ongoingSc8OffshoreM2;
    }
    
    /**
     * Returns the staff category 9 Offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc9OffshoreM2() {
        return ongoingSc9OffshoreM2;
    }
    
    /**
     * Returns the staff category 10 Offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc10OffshoreM2() {
        return ongoingSc10OffshoreM2;
    }
    
    /**
     * Returns the staff category 1 Offshore M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc1OffshoreM3() {
        return ongoingSc1OffshoreM3;
    }
    
    /**
     * Returns the staff category 2 Offshore M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc2OffshoreM3() {
        return ongoingSc2OffshoreM3;
    }
    
    /**
     * Returns the staff category 3 Offshore M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc3OffshoreM3() {
        return ongoingSc3OffshoreM3;
    }
    
    /**
     * Returns the staff category 4 Offshore M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc4OffshoreM3() {
        return ongoingSc4OffshoreM3;
    }
    
    /**
     * Returns the staff category 5 Offshore M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc5OffshoreM3() {
        return ongoingSc5OffshoreM3;
    }
    
    /**
     * Returns the staff category 6 Offshore M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc6OffshoreM3() {
        return ongoingSc6OffshoreM3;
    }
    
    /**
     * Returns the staff category 7 Offshore M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc7OffshoreM3() {
        return ongoingSc7OffshoreM3;
    }
    
    /**
     * Returns the staff category 8 Offshore M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc8OffshoreM3() {
        return ongoingSc8OffshoreM3;
    }
    
    /**
     * Returns the staff category 9 Offshore M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSc9OffshoreM3() {
        return ongoingSc9OffshoreM3;
    }
    
    /**
     * Returns the staff category 10 Offshore M3rate for ongoing service
     * @return Float
     */
    public float getOngoingSc10OffshoreM3() {
        return ongoingSc10OffshoreM3;
    }
    
        /**
     * Returns the ongoingSupplementary staff category 1 resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSupp1ResidentM1() {
        return ongoingSupp1ResidentM1;
    }
        
        /**
     * Returns the ongoingSupplementary staff category 1 resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSupp1ResidentM2() {
        return ongoingSupp1ResidentM2;
    }
    
        
        /**
     * Returns the ongoingSupplementary staff category 1 resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSupp1ResidentM3() {
        return ongoingSupp1ResidentM3;
    }
    
    
    /**
     * Returns the ongoingSupplementary staff category 1 non resident M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSupp1NonResidentM1() {
        return ongoingSupp1NonResidentM1;
    }
    
    /**
     * Returns the ongoingSupplementary staff category 1 non resident M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSupp1NonResidentM2() {
        return ongoingSupp1NonResidentM2;
    }
    
    /**
     * Returns the ongoingSupplementary staff category 1 non resident M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSupp1NonResidentM3() {
        return ongoingSupp1NonResidentM3;
    }
    
    /**
     * Returns the ongoingSupplementary staff category 1 offshore M1 rate for ongoing service
     * @return Float
     */
    public float getOngoingSupp1OffshoreM1() {
        return ongoingSupp1OffshoreM1;
    }
    
    /**
     * Returns the ongoingSupplementary staff category 1 offshore M2 rate for ongoing service
     * @return Float
     */
    public float getOngoingSupp1OffshoreM2() {
        return ongoingSupp1OffshoreM2;
    }
    
    /**
     * Returns the ongoingSupplementary staff category 1 offshore M3 rate for ongoing service
     * @return Float
     */
    public float getOngoingSupp1OffshoreM3() {
        return ongoingSupp1OffshoreM3;
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
     * Sets the Supplementary indicator for contractor
     * @param SuppInd - The ongoingSupplementary indicator to set
     */
    public void setSuppInd(int SuppInd) {
        this.SuppInd = SuppInd;
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
     * Sets the Staff Category 7 Resident rate for one off service
     * @param oneoffSc7Resident - The Staff Category 7 Resident rate to set
     */
    public void setOneoffSc7Resident(float oneoffSc7Resident) {
        this.oneoffSc7Resident = oneoffSc7Resident;
    }
    
    /**
     * Sets the Staff Category 8 Resident rate for one off service
     * @param oneoffSc8Resident - The Staff Category 8 Resident rate to set
     */
    public void setOneoffSc8Resident(float oneoffSc8Resident) {
        this.oneoffSc8Resident = oneoffSc8Resident;
    }
    
    /**
     * Sets the Staff Category 9 Resident rate for one off service
     * @param oneoffSc9Resident - The Staff Category 9 Resident rate to set
     */
    public void setOneoffSc9Resident(float oneoffSc9Resident) {
        this.oneoffSc9Resident = oneoffSc9Resident;
    }
    
    /**
     * Sets the Staff Category 10 Resident rate for one off service
     * @param oneoffSc10Resident - The Staff Category 10 Resident rate to set
     */
    public void setOneoffSc10Resident(float oneoffSc10Resident) {
        this.oneoffSc10Resident = oneoffSc10Resident;
    }
    
    /**
     * Sets the Staff Category 11 Resident rate for one off service
     * @param oneoffSc11Resident - The Staff Category 11 Resident rate to set
     */
    public void setOneoffSc11Resident(float oneoffSc11Resident) {
        this.oneoffSc11Resident = oneoffSc11Resident;
    }
    
    /**
     * Sets the Staff Category 12 Resident rate for one off service
     * @param oneoffSc12Resident - The Staff Category 12 Resident rate to set
     */
    public void setOneoffSc12Resident(float oneoffSc12Resident) {
        this.oneoffSc12Resident = oneoffSc12Resident;
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
     * Sets the Staff Category 7 Non Resident rate for one off service
     * @param oneoffSc7NonResident - The Staff Category 7 Non Resident rate to set
     */
    public void setOneoffSc7NonResident(float oneoffSc7NonResident) {
        this.oneoffSc7NonResident = oneoffSc7NonResident;
    }
    
    /**
     * Sets the Staff Category 8 Non Resident rate for one off service
     * @param oneoffSc8NonResident - The Staff Category 8 Non Resident rate to set
     */
    public void setOneoffSc8NonResident(float oneoffSc8NonResident) {
        this.oneoffSc8NonResident = oneoffSc8NonResident;
    }
    
    /**
     * Sets the Staff Category 9 Non Resident rate for one off service
     * @param oneoffSc9NonResident - The Staff Category 9 Non Resident rate to set
     */
    public void setOneoffSc9NonResident(float oneoffSc9NonResident) {
        this.oneoffSc9NonResident = oneoffSc9NonResident;
    }
    
    /**
     * Sets the Staff Category 10 Non Resident rate for one off service
     * @param oneoffSc10NonResident - The Staff Category 10 Non Resident rate to set
     */
    public void setOneoffSc10NonResident(float oneoffSc10NonResident) {
        this.oneoffSc10NonResident = oneoffSc10NonResident;
    }
    
    /**
     * Sets the Staff Category 11 Non Resident rate for one off service
     * @param oneoffSc11NonResident - The Staff Category 11 Non Resident rate to set
     */
    public void setOneoffSc11NonResident(float oneoffSc11NonResident) {
        this.oneoffSc11NonResident = oneoffSc11NonResident;
    }
    
    /**
     * Sets the Staff Category 12 Non Resident rate for one off service
     * @param oneoffSc12NonResident - The Staff Category 12 Non Resident rate to set
     */
    public void setOneoffSc12NonResident(float oneoffSc12NonResident) {
        this.oneoffSc12NonResident = oneoffSc12NonResident;
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
     * Sets the Staff Category 7 Offshore rate for one off service
     * @param oneoffSc7Offshore - The Staff Category 7 Offshore rate to set
     */
    public void setOneoffSc7Offshore(float oneoffSc7Offshore) {
        this.oneoffSc7Offshore = oneoffSc7Offshore;
    }
    
    /**
     * Sets the Staff Category 8 Offshore rate for one off service
     * @param oneoffSc8Offshore - The Staff Category 8 Offshore rate to set
     */
    public void setOneoffSc8Offshore(float oneoffSc8Offshore) {
        this.oneoffSc8Offshore = oneoffSc8Offshore;
    }    
    
    /**
     * Sets the Staff Category 9 Offshore rate for one off service
     * @param oneoffSc9Offshore - The Staff Category 9 Offshore rate to set
     */
    public void setOneoffSc9Offshore(float oneoffSc9Offshore) {
        this.oneoffSc9Offshore = oneoffSc9Offshore;
    }
    
    /**
     * Sets the Staff Category 10 Offshore rate for one off service
     * @param oneoffSc10Offshore - The Staff Category 10 Offshore rate to set
     */
    public void setOneoffSc10Offshore(float oneoffSc10Offshore) {
        this.oneoffSc10Offshore = oneoffSc10Offshore;
    }
    
    /**
     * Sets the Staff Category 11 Offshore rate for one off service
     * @param oneoffSc11Offshore - The Staff Category 11 Offshore rate to set
     */
    public void setOneoffSc11Offshore(float oneoffSc11Offshore) {
        this.oneoffSc11Offshore = oneoffSc11Offshore;
    }
    
    /**
     * Sets the Staff Category 12 Offshore rate for one off service
     * @param oneoffSc12Offshore - The Staff Category 12 Offshore rate to set
     */
    public void setOneoffSc12Offshore(float oneoffSc12Offshore) {
        this.oneoffSc12Offshore = oneoffSc12Offshore;
    }
    
    /**
     * Sets the Supplementary Staff Category 1 Resident rate for one off service
     * @param oneoffSupp1Resident - The Supplementary Staff Category 1 Resident rate to set
     */
    public void setOneoffSupp1Resident(float oneoffSupp1Resident ) {
        this.oneoffSupp1Resident = oneoffSupp1Resident;
    }
    
    /**
     * Sets the Supplementary Staff Category 1 Non Resident rate for one off service
     * @param oneoffSupp1NonResident - The Supplementary Staff Category 1 Non Resident rate to set
     */
    public void setOneoffSupp1NonResident(float oneoffSupp1NonResident) {
        this.oneoffSupp1NonResident = oneoffSupp1NonResident;
    }
    
    /**
     * Sets the Supplementary Staff Category 1 Offshore rate for one off service
     * @param oneoffSupp1Offshore - The Supplementary Staff Category 1 Offshore rate to set
     */
    public void setOneoffSupp1Offshore(float oneoffSupp1Offshore) {
        this.oneoffSupp1Offshore = oneoffSupp1Offshore;
    }
    
    
    /**
     * Sets the Staff Category 1 Resident M1 rate for ongoing service
     * @param ongoingSc1ResidentM1  - The Staff Category 1 Resident M1 rate to set
     */
    public void setOngoingSc1ResidentM1(float ongoingSc1ResidentM1) {
        this.ongoingSc1ResidentM1  = ongoingSc1ResidentM1;
    }
    
    /**
     * Sets the Staff Category 2 Resident M1 rate for ongoing service
     * @param ongoingSc2ResidentM1  - The Staff Category 2 Resident M1 rate to set
     */
    public void setOngoingSc2ResidentM1(float ongoingSc2ResidentM1) {
        this.ongoingSc2ResidentM1  = ongoingSc2ResidentM1;
    }
    
    /**
     * Sets the Staff Category 3 Resident M1 rate for ongoing service
     * @param ongoingSc3ResidentM1 - The Staff Category 3 Resident M1 rate to set
     */
    public void setOngoingSc3ResidentM1(float ongoingSc3ResidentM1) {
        this.ongoingSc3ResidentM1 = ongoingSc3ResidentM1;
    }
    
    /**
     * Sets the Staff Category 4 Resident M1 rate for ongoing service
     * @param ongoingSc4ResidentM1 - The Staff Category 4 Resident M1 rate to set
     */
    public void setOngoingSc4ResidentM1(float ongoingSc4ResidentM1) {
        this.ongoingSc4ResidentM1 = ongoingSc4ResidentM1;
    }
    
    /**
     * Sets the Staff Category 5 Resident M1 rate for ongoing service
     * @param ongoingSc5ResidentM1 - The Staff Category 5 Resident M1 rate to set
     */
    public void setOngoingSc5ResidentM1(float ongoingSc5ResidentM1) {
        this.ongoingSc5ResidentM1 = ongoingSc5ResidentM1;
    }
    
    /**
     * Sets the Staff Category 6 Resident M1 rate for ongoing service
     * @param ongoingSc6ResidentM1 - The Staff Category 6 Resident M1 rate to set
     */
    public void setOngoingSc6ResidentM1(float ongoingSc6ResidentM1) {
        this.ongoingSc6ResidentM1 = ongoingSc6ResidentM1;
    }
    
    /**
     * Sets the Staff Category 7 Resident M1 rate for ongoing service
     * @param ongoingSc7ResidentM1 - The Staff Category 7 Resident M1 rate to set
     */
    public void setOngoingSc7ResidentM1(float ongoingSc7ResidentM1) {
        this.ongoingSc7ResidentM1 = ongoingSc7ResidentM1;
    }
    
    /**
     * Sets the Staff Category 8 ResidentM1 rate for ongoing service
     * @param ongoingSc8ResidentM1 - The Staff Category 8 Resident M1 rate to set
     */
    public void setOngoingSc8ResidentM1(float ongoingSc8ResidentM1) {
        this.ongoingSc8ResidentM1 = ongoingSc8ResidentM1;
    }    
    
    /**
     * Sets the Staff Category 9 Resident M1 rate for ongoing service
     * @param ongoingSc9ResidentM1 - The Staff Category 9 Resident M1 rate to set
     */
    public void setOngoingSc9ResidentM1(float ongoingSc9ResidentM1) {
        this.ongoingSc9ResidentM1 = ongoingSc9ResidentM1;
    }
    
    /**
     * Sets the Staff Category 10 Resident M1 rate for ongoing service
     * @param ongoingSc10ResidentM1 - The Staff Category 10 Resident M1 rate to set
     */
    public void setOngoingSc10ResidentM1(float ongoingSc10ResidentM1) {
        this.ongoingSc10ResidentM1 = ongoingSc10ResidentM1;
    }
    
    /**
     * Sets the Staff Category 1 Resident M2 rate for ongoing service
     * @param ongoingSc1ResidentM2  - The Staff Category 1 Resident M2 rate to set
     */
    public void setOngoingSc1ResidentM2(float ongoingSc1ResidentM2) {
        this.ongoingSc1ResidentM2  = ongoingSc1ResidentM2;
    }
    
    /**
     * Sets the Staff Category 2 Resident M2 rate for ongoing service
     * @param ongoingSc2ResidentM2  - The Staff Category 2 Resident M2 rate to set
     */
    public void setOngoingSc2ResidentM2(float ongoingSc2ResidentM2) {
        this.ongoingSc2ResidentM2  = ongoingSc2ResidentM2;
    }
    
    /**
     * Sets the Staff Category 3 Resident M2 rate for ongoing service
     * @param ongoingSc3ResidentM2 - The Staff Category 3 Resident M2 rate to set
     */
    public void setOngoingSc3ResidentM2(float ongoingSc3ResidentM2) {
        this.ongoingSc3ResidentM2 = ongoingSc3ResidentM2;
    }
    
    /**
     * Sets the Staff Category 4 Resident M2 rate for ongoing service
     * @param ongoingSc4ResidentM2 - The Staff Category 4 Resident M2 rate to set
     */
    public void setOngoingSc4ResidentM2(float ongoingSc4ResidentM2) {
        this.ongoingSc4ResidentM2 = ongoingSc4ResidentM2;
    }
    
    /**
     * Sets the Staff Category 5 Resident M2 rate for ongoing service
     * @param ongoingSc5ResidentM2 - The Staff Category 5 Resident M2 rate to set
     */
    public void setOngoingSc5ResidentM2(float ongoingSc5ResidentM2) {
        this.ongoingSc5ResidentM2 = ongoingSc5ResidentM2;
    }
    
    /**
     * Sets the Staff Category 6 Resident M2 rate for ongoing service
     * @param ongoingSc6ResidentM2 - The Staff Category 6 Resident M2 rate to set
     */
    public void setOngoingSc6ResidentM2(float ongoingSc6ResidentM2) {
        this.ongoingSc6ResidentM2 = ongoingSc6ResidentM2;
    }
    
    /**
     * Sets the Staff Category 7 Resident M2 rate for ongoing service
     * @param ongoingSc7ResidentM2 - The Staff Category 7 Resident M2 rate to set
     */
    public void setOngoingSc7ResidentM2(float ongoingSc7ResidentM2) {
        this.ongoingSc7ResidentM2 = ongoingSc7ResidentM2;
    }
    
    /**
     * Sets the Staff Category 8 ResidentM2 rate for ongoing service
     * @param ongoingSc8ResidentM2 - The Staff Category 8 Resident M2 rate to set
     */
    public void setOngoingSc8ResidentM2(float ongoingSc8ResidentM2) {
        this.ongoingSc8ResidentM2 = ongoingSc8ResidentM2;
    }
        
    /**
     * Sets the Staff Category 9 Resident M2 rate for ongoing service
     * @param ongoingSc9ResidentM2 - The Staff Category 9 Resident M2 rate to set
     */
    public void setOngoingSc9ResidentM2(float ongoingSc9ResidentM2) {
        this.ongoingSc9ResidentM2 = ongoingSc9ResidentM2;
    }
    
    /**
     * Sets the Staff Category 10 Resident M2 rate for ongoing service
     * @param ongoingSc10ResidentM2 - The Staff Category 10 Resident M2 rate to set
     */
    public void setOngoingSc10ResidentM2(float ongoingSc10ResidentM2) {
        this.ongoingSc10ResidentM2 = ongoingSc10ResidentM2;
    }
    
       /**
     * Sets the Staff Category 1 Resident M3 rate for ongoing service
     * @param ongoingSc1ResidentM3  - The Staff Category 1 Resident M3 rate to set
     */
    public void setOngoingSc1ResidentM3(float ongoingSc1ResidentM3) {
        this.ongoingSc1ResidentM3  = ongoingSc1ResidentM3;
    }
    
    /**
     * Sets the Staff Category 2 Resident M3 rate for ongoing service
     * @param ongoingSc2ResidentM3  - The Staff Category 2 Resident M3 rate to set
     */
    public void setOngoingSc2ResidentM3(float ongoingSc2ResidentM3) {
        this.ongoingSc2ResidentM3  = ongoingSc2ResidentM3;
    }
    
    /**
     * Sets the Staff Category 3 Resident M3 rate for ongoing service
     * @param ongoingSc3ResidentM3 - The Staff Category 3 Resident M3 rate to set
     */
    public void setOngoingSc3ResidentM3(float ongoingSc3ResidentM3) {
        this.ongoingSc3ResidentM3 = ongoingSc3ResidentM3;
    }
    
    /**
     * Sets the Staff Category 4 Resident M3 rate for ongoing service
     * @param ongoingSc4ResidentM3 - The Staff Category 4 Resident M3 rate to set
     */
    public void setOngoingSc4ResidentM3(float ongoingSc4ResidentM3) {
        this.ongoingSc4ResidentM3 = ongoingSc4ResidentM3;
    }
    
    /**
     * Sets the Staff Category 5 Resident M3 rate for ongoing service
     * @param ongoingSc5ResidentM3 - The Staff Category 5 Resident M3 rate to set
     */
    public void setOngoingSc5ResidentM3(float ongoingSc5ResidentM3) {
        this.ongoingSc5ResidentM3 = ongoingSc5ResidentM3;
    }
    
    /**
     * Sets the Staff Category 6 Resident M3 rate for ongoing service
     * @param ongoingSc6ResidentM3 - The Staff Category 6 Resident M3 rate to set
     */
    public void setOngoingSc6ResidentM3(float ongoingSc6ResidentM3) {
        this.ongoingSc6ResidentM3 = ongoingSc6ResidentM3;
    }
    
    /**
     * Sets the Staff Category 7 Resident M3 rate for ongoing service
     * @param ongoingSc7ResidentM3 - The Staff Category 7 Resident M3 rate to set
     */
    public void setOngoingSc7ResidentM3(float ongoingSc7ResidentM3) {
        this.ongoingSc7ResidentM3 = ongoingSc7ResidentM3;
    }
    
    /**
     * Sets the Staff Category 8 ResidentM3 rate for ongoing service
     * @param ongoingSc8ResidentM3 - The Staff Category 8 Resident M3 rate to set
     */
    public void setOngoingSc8ResidentM3(float ongoingSc8ResidentM3) {
        this.ongoingSc8ResidentM3 = ongoingSc8ResidentM3;
    }
        
    /**
     * Sets the Staff Category 9 Resident M3 rate for ongoing service
     * @param ongoingSc9ResidentM3 - The Staff Category 9 Resident M3 rate to set
     */
    public void setOngoingSc9ResidentM3(float ongoingSc9ResidentM3) {
        this.ongoingSc9ResidentM3 = ongoingSc9ResidentM3;
    }
    
    /**
     * Sets the Staff Category 10 Resident M3 rate for ongoing service
     * @param ongoingSc10ResidentM3 - The Staff Category 10 Resident M3 rate to set
     */
    public void setOngoingSc10ResidentM3(float ongoingSc10ResidentM3) {
        this.ongoingSc10ResidentM3 = ongoingSc10ResidentM3;
    }
    
    
    /**
     * Sets the Staff Category 1 Non Resident M1 rate for ongoing service
     * @param ongoingSc1NonResidentM1  - The Staff Category 1 Non Resident M1 rate to set
     */
    public void setOngoingSc1NonResidentM1(float ongoingSc1NonResidentM1) {
        this.ongoingSc1NonResidentM1  = ongoingSc1NonResidentM1;
    }
    
    /**
     * Sets the Staff Category 2 Non Resident M1 rate for ongoing service
     * @param ongoingSc2NonResidentM1  - The Staff Category 2 Non Resident M1 rate to set
     */
    public void setOngoingSc2NonResidentM1(float ongoingSc2NonResidentM1) {
        this.ongoingSc2NonResidentM1  = ongoingSc2NonResidentM1;
    }
    
    /**
     * Sets the Staff Category 3 Non Resident M1 rate for ongoing service
     * @param ongoingSc3NonResidentM1 - The Staff Category 3 Non Resident M1 rate to set
     */
    public void setOngoingSc3NonResidentM1(float ongoingSc3NonResidentM1) {
        this.ongoingSc3NonResidentM1 = ongoingSc3NonResidentM1;
    }
    
    /**
     * Sets the Staff Category 4 Non Resident M1 rate for ongoing service
     * @param ongoingSc4NonResidentM1 - The Staff Category 4 Non Resident M1 rate to set
     */
    public void setOngoingSc4NonResidentM1(float ongoingSc4NonResidentM1) {
        this.ongoingSc4NonResidentM1 = ongoingSc4NonResidentM1;
    }
    
    /**
     * Sets the Staff Category 5 Non Resident M1 rate for ongoing service
     * @param ongoingSc5NonResidentM1 - The Staff Category 5 Non Resident M1 rate to set
     */
    public void setOngoingSc5NonResidentM1(float ongoingSc5NonResidentM1) {
        this.ongoingSc5NonResidentM1 = ongoingSc5NonResidentM1;
    }
    
    /**
     * Sets the Staff Category 6 Non Resident M1 rate for ongoing service
     * @param ongoingSc6NonResidentM1 - The Staff Category 6 Non Resident M1 rate to set
     */
    public void setOngoingSc6NonResidentM1(float ongoingSc6NonResidentM1) {
        this.ongoingSc6NonResidentM1 = ongoingSc6NonResidentM1;
    }
    
    /**
     * Sets the Staff Category 7 Non Resident M1 rate for ongoing service
     * @param ongoingSc7NonResidentM1 - The Staff Category 7 Non Resident M1 rate to set
     */
    public void setOngoingSc7NonResidentM1(float ongoingSc7NonResidentM1) {
        this.ongoingSc7NonResidentM1 = ongoingSc7NonResidentM1;
    }
    
    /**
     * Sets the Staff Category 8 Non ResidentM1 rate for ongoing service
     * @param ongoingSc8NonResidentM1 - The Staff Category 8 Non Resident M1 rate to set
     */
    public void setOngoingSc8NonResidentM1(float ongoingSc8NonResidentM1) {
        this.ongoingSc8NonResidentM1 = ongoingSc8NonResidentM1;
    }    
    
    /**
     * Sets the Staff Category 9 Non Resident M1 rate for ongoing service
     * @param ongoingSc9NonResidentM1 - The Staff Category 9 Non Resident M1 rate to set
     */
    public void setOngoingSc9NonResidentM1(float ongoingSc9NonResidentM1) {
        this.ongoingSc9NonResidentM1 = ongoingSc9NonResidentM1;
    }
    
    /**
     * Sets the Staff Category 10 Non Resident M1 rate for ongoing service
     * @param ongoingSc10NonResidentM1 - The Staff Category 10 Non Resident M1 rate to set
     */
    public void setOngoingSc10NonResidentM1(float ongoingSc10NonResidentM1) {
        this.ongoingSc10NonResidentM1 = ongoingSc10NonResidentM1;
    }
    
    /**
     * Sets the Staff Category 1 Non Resident M2 rate for ongoing service
     * @param ongoingSc1NonResidentM2  - The Staff Category 1 Non Resident M2 rate to set
     */
    public void setOngoingSc1NonResidentM2(float ongoingSc1NonResidentM2) {
        this.ongoingSc1NonResidentM2  = ongoingSc1NonResidentM2;
    }
    
    /**
     * Sets the Staff Category 2 Non Resident M2 rate for ongoing service
     * @param ongoingSc2NonResidentM2  - The Staff Category 2 Non Resident M2 rate to set
     */
    public void setOngoingSc2NonResidentM2(float ongoingSc2NonResidentM2) {
        this.ongoingSc2NonResidentM2  = ongoingSc2NonResidentM2;
    }
    
    /**
     * Sets the Staff Category 3 Non Resident M2 rate for ongoing service
     * @param ongoingSc3NonResidentM2 - The Staff Category 3 Non Resident M2 rate to set
     */
    public void setOngoingSc3NonResidentM2(float ongoingSc3NonResidentM2) {
        this.ongoingSc3NonResidentM2 = ongoingSc3NonResidentM2;
    }
    
    /**
     * Sets the Staff Category 4 Non Resident M2 rate for ongoing service
     * @param ongoingSc4NonResidentM2 - The Staff Category 4 Non Resident M2 rate to set
     */
    public void setOngoingSc4NonResidentM2(float ongoingSc4NonResidentM2) {
        this.ongoingSc4NonResidentM2 = ongoingSc4NonResidentM2;
    }
    
    /**
     * Sets the Staff Category 5 Non Resident M2 rate for ongoing service
     * @param ongoingSc5NonResidentM2 - The Staff Category 5 Non Resident M2 rate to set
     */
    public void setOngoingSc5NonResidentM2(float ongoingSc5NonResidentM2) {
        this.ongoingSc5NonResidentM2 = ongoingSc5NonResidentM2;
    }
    
    /**
     * Sets the Staff Category 6 Non Resident M2 rate for ongoing service
     * @param ongoingSc6NonResidentM2 - The Staff Category 6 Non Resident M2 rate to set
     */
    public void setOngoingSc6NonResidentM2(float ongoingSc6NonResidentM2) {
        this.ongoingSc6NonResidentM2 = ongoingSc6NonResidentM2;
    }
    
    /**
     * Sets the Staff Category 7 Non Resident M2 rate for ongoing service
     * @param ongoingSc7NonResidentM2 - The Staff Category 7 Non Resident M2 rate to set
     */
    public void setOngoingSc7NonResidentM2(float ongoingSc7NonResidentM2) {
        this.ongoingSc7NonResidentM2 = ongoingSc7NonResidentM2;
    }
    
    /**
     * Sets the Staff Category 8 Non ResidentM2 rate for ongoing service
     * @param ongoingSc8NonResidentM2 - The Staff Category 8 Non Resident M2 rate to set
     */
    public void setOngoingSc8NonResidentM2(float ongoingSc8NonResidentM2) {
        this.ongoingSc8NonResidentM2 = ongoingSc8NonResidentM2;
    }
        
    /**
     * Sets the Staff Category 9 Non Resident M2 rate for ongoing service
     * @param ongoingSc9NonResidentM2 - The Staff Category 9 Non Resident M2 rate to set
     */
    public void setOngoingSc9NonResidentM2(float ongoingSc9NonResidentM2) {
        this.ongoingSc9NonResidentM2 = ongoingSc9NonResidentM2;
    }
    
    /**
     * Sets the Staff Category 10 Non Resident M2 rate for ongoing service
     * @param ongoingSc10NonResidentM2 - The Staff Category 10 Non Resident M2 rate to set
     */
    public void setOngoingSc10NonResidentM2(float ongoingSc10NonResidentM2) {
        this.ongoingSc10NonResidentM2 = ongoingSc10NonResidentM2;
    }
    
       /**
     * Sets the Staff Category 1 Non Resident M3 rate for ongoing service
     * @param ongoingSc1NonResidentM3  - The Staff Category 1 Non Resident M3 rate to set
     */
    public void setOngoingSc1NonResidentM3(float ongoingSc1NonResidentM3) {
        this.ongoingSc1NonResidentM3  = ongoingSc1NonResidentM3;
    }
    
    /**
     * Sets the Staff Category 2 Non Resident M3 rate for ongoing service
     * @param ongoingSc2NonResidentM3  - The Staff Category 2 Non Resident M3 rate to set
     */
    public void setOngoingSc2NonResidentM3(float ongoingSc2NonResidentM3) {
        this.ongoingSc2NonResidentM3  = ongoingSc2NonResidentM3;
    }
    
    /**
     * Sets the Staff Category 3 Non Resident M3 rate for ongoing service
     * @param ongoingSc3NonResidentM3 - The Staff Category 3 Non Resident M3 rate to set
     */
    public void setOngoingSc3NonResidentM3(float ongoingSc3NonResidentM3) {
        this.ongoingSc3NonResidentM3 = ongoingSc3NonResidentM3;
    }
    
    /**
     * Sets the Staff Category 4 Non Resident M3 rate for ongoing service
     * @param ongoingSc4NonResidentM3 - The Staff Category 4 Non Resident M3 rate to set
     */
    public void setOngoingSc4NonResidentM3(float ongoingSc4NonResidentM3) {
        this.ongoingSc4NonResidentM3 = ongoingSc4NonResidentM3;
    }
    
    /**
     * Sets the Staff Category 5 Non Resident M3 rate for ongoing service
     * @param ongoingSc5NonResidentM3 - The Staff Category 5 Non Resident M3 rate to set
     */
    public void setOngoingSc5NonResidentM3(float ongoingSc5NonResidentM3) {
        this.ongoingSc5NonResidentM3 = ongoingSc5NonResidentM3;
    }
    
    /**
     * Sets the Staff Category 6 Non Resident M3 rate for ongoing service
     * @param ongoingSc6NonResidentM3 - The Staff Category 6 Non Resident M3 rate to set
     */
    public void setOngoingSc6NonResidentM3(float ongoingSc6NonResidentM3) {
        this.ongoingSc6NonResidentM3 = ongoingSc6NonResidentM3;
    }
    
    /**
     * Sets the Staff Category 7 Non Resident M3 rate for ongoing service
     * @param ongoingSc7NonResidentM3 - The Staff Category 7 Non Resident M3 rate to set
     */
    public void setOngoingSc7NonResidentM3(float ongoingSc7NonResidentM3) {
        this.ongoingSc7NonResidentM3 = ongoingSc7NonResidentM3;
    }
    
    /**
     * Sets the Staff Category 8 Non ResidentM3 rate for ongoing service
     * @param ongoingSc8NonResidentM3 - The Staff Category 8 Non Resident M3 rate to set
     */
    public void setOngoingSc8NonResidentM3(float ongoingSc8NonResidentM3) {
        this.ongoingSc8NonResidentM3 = ongoingSc8NonResidentM3;
    }
        
    /**
     * Sets the Staff Category 9 Non Resident M3 rate for ongoing service
     * @param ongoingSc9NonResidentM3 - The Staff Category 9 Non Resident M3 rate to set
     */
    public void setOngoingSc9NonResidentM3(float ongoingSc9NonResidentM3) {
        this.ongoingSc9NonResidentM3 = ongoingSc9NonResidentM3;
    }
    
    /**
     * Sets the Staff Category 10 Non Resident M3 rate for ongoing service
     * @param ongoingSc10NonResidentM3 - The Staff Category 10 Non Resident M3 rate to set
     */
    public void setOngoingSc10NonResidentM3(float ongoingSc10NonResidentM3) {
        this.ongoingSc10NonResidentM3 = ongoingSc10NonResidentM3;
    }
    
    
    /**
     * Sets the Staff Category 1 Offshore M1 rate for ongoing service
     * @param ongoingSc1OffshoreM1  - The Staff Category 1 Offshore M1 rate to set
     */
    public void setOngoingSc1OffshoreM1(float ongoingSc1OffshoreM1) {
        this.ongoingSc1OffshoreM1  = ongoingSc1OffshoreM1;
    }
    
    /**
     * Sets the Staff Category 2 Offshore M1 rate for ongoing service
     * @param ongoingSc2OffshoreM1  - The Staff Category 2 Offshore M1 rate to set
     */
    public void setOngoingSc2OffshoreM1(float ongoingSc2OffshoreM1) {
        this.ongoingSc2OffshoreM1  = ongoingSc2OffshoreM1;
    }
    
    /**
     * Sets the Staff Category 3 Offshore M1 rate for ongoing service
     * @param ongoingSc3OffshoreM1 - The Staff Category 3 Offshore M1 rate to set
     */
    public void setOngoingSc3OffshoreM1(float ongoingSc3OffshoreM1) {
        this.ongoingSc3OffshoreM1 = ongoingSc3OffshoreM1;
    }
    
    /**
     * Sets the Staff Category 4 Offshore M1 rate for ongoing service
     * @param ongoingSc4OffshoreM1 - The Staff Category 4 Offshore M1 rate to set
     */
    public void setOngoingSc4OffshoreM1(float ongoingSc4OffshoreM1) {
        this.ongoingSc4OffshoreM1 = ongoingSc4OffshoreM1;
    }
    
    /**
     * Sets the Staff Category 5 Offshore M1 rate for ongoing service
     * @param ongoingSc5OffshoreM1 - The Staff Category 5 Offshore M1 rate to set
     */
    public void setOngoingSc5OffshoreM1(float ongoingSc5OffshoreM1) {
        this.ongoingSc5OffshoreM1 = ongoingSc5OffshoreM1;
    }
    
    /**
     * Sets the Staff Category 6 Offshore M1 rate for ongoing service
     * @param ongoingSc6OffshoreM1 - The Staff Category 6 Offshore M1 rate to set
     */
    public void setOngoingSc6OffshoreM1(float ongoingSc6OffshoreM1) {
        this.ongoingSc6OffshoreM1 = ongoingSc6OffshoreM1;
    }
    
    /**
     * Sets the Staff Category 7 Offshore M1 rate for ongoing service
     * @param ongoingSc7OffshoreM1 - The Staff Category 7 Offshore M1 rate to set
     */
    public void setOngoingSc7OffshoreM1(float ongoingSc7OffshoreM1) {
        this.ongoingSc7OffshoreM1 = ongoingSc7OffshoreM1;
    }
    
    /**
     * Sets the Staff Category 8 OffshoreM1 rate for ongoing service
     * @param ongoingSc8OffshoreM1 - The Staff Category 8 Offshore M1 rate to set
     */
    public void setOngoingSc8OffshoreM1(float ongoingSc8OffshoreM1) {
        this.ongoingSc8OffshoreM1 = ongoingSc8OffshoreM1;
    }    
    
    /**
     * Sets the Staff Category 9 Offshore M1 rate for ongoing service
     * @param ongoingSc9OffshoreM1 - The Staff Category 9 Offshore M1 rate to set
     */
    public void setOngoingSc9OffshoreM1(float ongoingSc9OffshoreM1) {
        this.ongoingSc9OffshoreM1 = ongoingSc9OffshoreM1;
    }
    
    /**
     * Sets the Staff Category 10 Offshore M1 rate for ongoing service
     * @param ongoingSc10OffshoreM1 - The Staff Category 10 Offshore M1 rate to set
     */
    public void setOngoingSc10OffshoreM1(float ongoingSc10OffshoreM1) {
        this.ongoingSc10OffshoreM1 = ongoingSc10OffshoreM1;
    }
    
    /**
     * Sets the Staff Category 1 Offshore M2 rate for ongoing service
     * @param ongoingSc1OffshoreM2  - The Staff Category 1 Offshore M2 rate to set
     */
    public void setOngoingSc1OffshoreM2(float ongoingSc1OffshoreM2) {
        this.ongoingSc1OffshoreM2  = ongoingSc1OffshoreM2;
    }
    
    /**
     * Sets the Staff Category 2 Offshore M2 rate for ongoing service
     * @param ongoingSc2OffshoreM2  - The Staff Category 2 Offshore M2 rate to set
     */
    public void setOngoingSc2OffshoreM2(float ongoingSc2OffshoreM2) {
        this.ongoingSc2OffshoreM2  = ongoingSc2OffshoreM2;
    }
    
    /**
     * Sets the Staff Category 3 Offshore M2 rate for ongoing service
     * @param ongoingSc3OffshoreM2 - The Staff Category 3 Offshore M2 rate to set
     */
    public void setOngoingSc3OffshoreM2(float ongoingSc3OffshoreM2) {
        this.ongoingSc3OffshoreM2 = ongoingSc3OffshoreM2;
    }
    
    /**
     * Sets the Staff Category 4 Offshore M2 rate for ongoing service
     * @param ongoingSc4OffshoreM2 - The Staff Category 4 Offshore M2 rate to set
     */
    public void setOngoingSc4OffshoreM2(float ongoingSc4OffshoreM2) {
        this.ongoingSc4OffshoreM2 = ongoingSc4OffshoreM2;
    }
    
    /**
     * Sets the Staff Category 5 Offshore M2 rate for ongoing service
     * @param ongoingSc5OffshoreM2 - The Staff Category 5 Offshore M2 rate to set
     */
    public void setOngoingSc5OffshoreM2(float ongoingSc5OffshoreM2) {
        this.ongoingSc5OffshoreM2 = ongoingSc5OffshoreM2;
    }
    
    /**
     * Sets the Staff Category 6 Offshore M2 rate for ongoing service
     * @param ongoingSc6OffshoreM2 - The Staff Category 6 Offshore M2 rate to set
     */
    public void setOngoingSc6OffshoreM2(float ongoingSc6OffshoreM2) {
        this.ongoingSc6OffshoreM2 = ongoingSc6OffshoreM2;
    }
    
    /**
     * Sets the Staff Category 7 Offshore M2 rate for ongoing service
     * @param ongoingSc7OffshoreM2 - The Staff Category 7 Offshore M2 rate to set
     */
    public void setOngoingSc7OffshoreM2(float ongoingSc7OffshoreM2) {
        this.ongoingSc7OffshoreM2 = ongoingSc7OffshoreM2;
    }
    
    /**
     * Sets the Staff Category 8 OffshoreM2 rate for ongoing service
     * @param ongoingSc8OffshoreM2 - The Staff Category 8 Offshore M2 rate to set
     */
    public void setOngoingSc8OffshoreM2(float ongoingSc8OffshoreM2) {
        this.ongoingSc8OffshoreM2 = ongoingSc8OffshoreM2;
    }
        
    /**
     * Sets the Staff Category 9 Offshore M2 rate for ongoing service
     * @param ongoingSc9OffshoreM2 - The Staff Category 9 Offshore M2 rate to set
     */
    public void setOngoingSc9OffshoreM2(float ongoingSc9OffshoreM2) {
        this.ongoingSc9OffshoreM2 = ongoingSc9OffshoreM2;
    }
    
    /**
     * Sets the Staff Category 10 Offshore M2 rate for ongoing service
     * @param ongoingSc10OffshoreM2 - The Staff Category 10 Offshore M2 rate to set
     */
    public void setOngoingSc10OffshoreM2(float ongoingSc10OffshoreM2) {
        this.ongoingSc10OffshoreM2 = ongoingSc10OffshoreM2;
    }
    
       /**
     * Sets the Staff Category 1 Offshore M3 rate for ongoing service
     * @param ongoingSc1OffshoreM3  - The Staff Category 1 Offshore M3 rate to set
     */
    public void setOngoingSc1OffshoreM3(float ongoingSc1OffshoreM3) {
        this.ongoingSc1OffshoreM3  = ongoingSc1OffshoreM3;
    }
    
    /**
     * Sets the Staff Category 2 Offshore M3 rate for ongoing service
     * @param ongoingSc2OffshoreM3  - The Staff Category 2 Offshore M3 rate to set
     */
    public void setOngoingSc2OffshoreM3(float ongoingSc2OffshoreM3) {
        this.ongoingSc2OffshoreM3  = ongoingSc2OffshoreM3;
    }
    
    /**
     * Sets the Staff Category 3 Offshore M3 rate for ongoing service
     * @param ongoingSc3OffshoreM3 - The Staff Category 3 Offshore M3 rate to set
     */
    public void setOngoingSc3OffshoreM3(float ongoingSc3OffshoreM3) {
        this.ongoingSc3OffshoreM3 = ongoingSc3OffshoreM3;
    }
    
    /**
     * Sets the Staff Category 4 Offshore M3 rate for ongoing service
     * @param ongoingSc4OffshoreM3 - The Staff Category 4 Offshore M3 rate to set
     */
    public void setOngoingSc4OffshoreM3(float ongoingSc4OffshoreM3) {
        this.ongoingSc4OffshoreM3 = ongoingSc4OffshoreM3;
    }
    
    /**
     * Sets the Staff Category 5 Offshore M3 rate for ongoing service
     * @param ongoingSc5OffshoreM3 - The Staff Category 5 Offshore M3 rate to set
     */
    public void setOngoingSc5OffshoreM3(float ongoingSc5OffshoreM3) {
        this.ongoingSc5OffshoreM3 = ongoingSc5OffshoreM3;
    }
    
    /**
     * Sets the Staff Category 6 Offshore M3 rate for ongoing service
     * @param ongoingSc6OffshoreM3 - The Staff Category 6 Offshore M3 rate to set
     */
    public void setOngoingSc6OffshoreM3(float ongoingSc6OffshoreM3) {
        this.ongoingSc6OffshoreM3 = ongoingSc6OffshoreM3;
    }
    
    /**
     * Sets the Staff Category 7 Offshore M3 rate for ongoing service
     * @param ongoingSc7OffshoreM3 - The Staff Category 7 Offshore M3 rate to set
     */
    public void setOngoingSc7OffshoreM3(float ongoingSc7OffshoreM3) {
        this.ongoingSc7OffshoreM3 = ongoingSc7OffshoreM3;
    }
    
    /**
     * Sets the Staff Category 8 OffshoreM3 rate for ongoing service
     * @param ongoingSc8OffshoreM3 - The Staff Category 8 Offshore M3 rate to set
     */
    public void setOngoingSc8OffshoreM3(float ongoingSc8OffshoreM3) {
        this.ongoingSc8OffshoreM3 = ongoingSc8OffshoreM3;
    }
        
    /**
     * Sets the Staff Category 9 Offshore M3 rate for ongoing service
     * @param ongoingSc9OffshoreM3 - The Staff Category 9 Offshore M3 rate to set
     */
    public void setOngoingSc9OffshoreM3(float ongoingSc9OffshoreM3) {
        this.ongoingSc9OffshoreM3 = ongoingSc9OffshoreM3;
    }
    
    /**
     * Sets the Staff Category 10 Offshore M3 rate for ongoing service
     * @param ongoingSc10OffshoreM3 - The Staff Category 10 Offshore M3 rate to set
     */
    public void setOngoingSc10OffshoreM3(float ongoingSc10OffshoreM3) {
        this.ongoingSc10OffshoreM3 = ongoingSc10OffshoreM3;
    }
    
    
    
    
    /**
     * Sets the Supplementary Staff Category 1 Resident M1 rate for ongoing service
     * @param ongoingSupp1ResidentM1 - The Supplementary Staff Category 1 Resident M1 rate to set
     */
    public void setOngoingSupp1ResidentM1(float ongoingSupp1ResidentM1) {
        this.ongoingSupp1ResidentM1 = ongoingSupp1ResidentM1;
    }
    
    /**
     * Sets the Supplementary Staff Category 1 Resident M2 rate for ongoing service
     * @param ongoingSupp1ResidentM2 - The Supplementary Staff Category 1 Resident M2 rate to set
     */
    public void setOngoingSupp1ResidentM2(float ongoingSupp1ResidentM2) {
        this.ongoingSupp1ResidentM2 = ongoingSupp1ResidentM2;
    }
    
    /**
     * Sets the Supplementary Staff Category 1 Resident M3 rate for ongoing service
     * @param ongoingSupp1ResidentM3 - The Supplementary Staff Category 1 Resident M3 rate to set
     */
    public void setOngoingSupp1ResidentM3(float ongoingSupp1ResidentM3) {
        this.ongoingSupp1ResidentM3 = ongoingSupp1ResidentM3;
    }
    
    /**
     * Sets the Supplementary Staff Category 1 Non Resident M1 rate for ongoing service
     * @param ongoingSupp1NonResidentM1 - The Supplementary Staff Category 1 Non Resident M1 rate to set
     */
    public void setOngoingSupp1NonResidentM1(float ongoingSupp1NonResidentM1) {
        this.ongoingSupp1NonResidentM1 = ongoingSupp1NonResidentM1;
    }
    
    
    /**
     * Sets the Supplementary Staff Category 1 Non Resident M2 rate for ongoing service
     * @param ongoingSupp1NonResidentM2 - The Supplementary Staff Category 1 Non Resident M2 rate to set
     */
    public void setOngoingSupp1NonResidentM2(float ongoingSupp1NonResidentM2) {
        this.ongoingSupp1NonResidentM2 = ongoingSupp1NonResidentM2;
    }
    
    
    /**
     * Sets the Supplementary Staff Category 1 Non Resident M3 rate for ongoing service
     * @param ongoingSupp1NonResidentM3 - The Supplementary Staff Category 1 Non Resident M3 rate to set
     */
    public void setOngoingSupp1NonResidentM3(float ongoingSupp1NonResidentM3) {
        this.ongoingSupp1NonResidentM3 = ongoingSupp1NonResidentM3;
    }
    

    /**
     * Sets the Supplementary Staff Category 1 Offshore M1 rate for ongoing service
     * @param ongoingSupp1OffshoreM1 - The Supplementary Staff Category 1 Offshore M1 rate to set
     */
    public void setOngoingSupp1OffshoreM1(float ongoingSupp1OffshoreM1) {
        this.ongoingSupp1OffshoreM1 = ongoingSupp1OffshoreM1;
    }
    
    /**
     * Sets the Supplementary Staff Category 1 Offshore M2 rate for ongoing service
     * @param ongoingSupp1OffshoreM2 - The Supplementary Staff Category 1 Offshore M2 rate to set
     */
    public void setOngoingSupp1OffshoreM2(float ongoingSupp1OffshoreM2) {
        this.ongoingSupp1OffshoreM2 = ongoingSupp1OffshoreM2;
    }
    
    /**
     * Sets the Supplementary Staff Category 1 Offshore M3 rate for ongoing service
     * @param ongoingSupp1OffshoreM3 - The Supplementary Staff Category 1 Offshore M3 rate to set
     */
    public void setOngoingSupp1OffshoreM3(float ongoingSupp1OffshoreM3) {
        this.ongoingSupp1OffshoreM3 = ongoingSupp1OffshoreM3;
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
