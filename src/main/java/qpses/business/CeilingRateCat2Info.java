package qpses.business;

import java.util.ArrayList;

public class CeilingRateCat2Info
{
    public static final int NumOfWorkingLocation = 3;
    public static final int NumOfStaffCategory   = 10;
    public static final int NumOfMScheme         = 3;
    
    public static final String ExceedString    = "Yes";
    public static final String NotExceedString = "No";
    public static final String NoOfferString   = "No Offer";
    
    private double [][][] StdStaffRate       = new double[NumOfStaffCategory][NumOfWorkingLocation][NumOfMScheme];
    private String[][][] StdStaffRateExceed = new String[NumOfStaffCategory][NumOfWorkingLocation][NumOfMScheme];
    
    private double [][] SupStaffRate       = new double[NumOfWorkingLocation][NumOfMScheme];
    private String[][] SupStaffRateExceed = new String[NumOfWorkingLocation][NumOfMScheme];
    
    public static final String MinorGroupInd = "N";
    public static final String MajorGroupInd = "J";
    
    public static final String []WorkingLocationName = {"Resident", "Non-Resident", "Off-Shore"};
    public static final String []MSchemeName         = {"M1", "M2", "M3"};
    
    public static final double NoOfferDBNumber  = -999;   // Value indicates "no offer" in database
    public static final double NoOfferWebNumber = -1000;  // Value indicates "no offer" from web page
    
    private String ContractorName;
    private String ContractorID;
    private String ServiceGroup;
    private String Currency;
    private int ActiveIndicator;
    private String CreatedDate;
    private String LastUpdatedDate;
    private String LastUpdatedBy;
    private int SupStaffIndicator;
    
    /** Creates a new instance of CeilingRateCat2Info */
    public CeilingRateCat2Info()
    {}
    
    public void CheckCeilingRate(CeilingRateCat2Info aCeilingRateCat2Info)
    {
        // Check standard staff rate
        for (int i = 0; i < NumOfStaffCategory; i++)
        {
            for (int j = 0; j < NumOfWorkingLocation; j++)
            {
                for (int k = 0; k < NumOfMScheme; k++)
                {
                    if (aCeilingRateCat2Info.getStdStaffRate(i, j, k) == NoOfferDBNumber &&
                            this.getStdStaffRate(i, j, k) != NoOfferDBNumber ) // User input value for "no offer" field
                        this.setStdStaffRateExceed(i, j, k, NoOfferString);
                    else if (this.getStdStaffRate(i, j, k) > aCeilingRateCat2Info.getStdStaffRate(i, j, k))
                        this.setStdStaffRateExceed(i, j, k, ExceedString);
                    else
                        this.setStdStaffRateExceed(i, j, k, NotExceedString);
                }
            }
        }
        
        // Check supplementary staff rate
        for (int i = 0; i < NumOfWorkingLocation; i++)
        {
            for (int j = 0; j < NumOfMScheme; j++)
            {
                if (aCeilingRateCat2Info.getSupStaffRate(i, j) == NoOfferDBNumber &&
                        this.getSupStaffRate(i, j) != NoOfferDBNumber ) // User input value for "no offer" field
                    this.setSupStaffRateExceed(i, j, NoOfferString);
                else if (this.getSupStaffRate(i, j) > aCeilingRateCat2Info.getSupStaffRate(i, j))
                    this.setSupStaffRateExceed(i, j, ExceedString);
                else
                    this.setSupStaffRateExceed(i, j, NotExceedString);
            }
        }
    }
    
    public void setSupStaffRateExceed(int workingLocation, int mScheme, String exceedString)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        assert (exceedString.equals(ExceedString) || exceedString.equals(NotExceedString) || exceedString.equals(NoOfferString));
        
        this.SupStaffRateExceed[workingLocation][mScheme] = exceedString;
    }
    
    public String getSupStaffRateExceed(int workingLocation, int mScheme)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        return this.SupStaffRateExceed[workingLocation][mScheme];
    }
    
    public void setStdStaffRateExceed(int staffCategory, int workingLocation, int mScheme, String exceedString)
    {
        assert (staffCategory   >= 0 && staffCategory   < NumOfStaffCategory);
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        assert (exceedString.equals(ExceedString) || exceedString.equals(NotExceedString) || exceedString.equals(NoOfferString));
        
        this.StdStaffRateExceed[staffCategory][workingLocation][mScheme] = exceedString;
    }
    
    public String getStdStaffRateExceed(int staffCategory, int workingLocation, int mScheme)
    {
        assert (staffCategory   >= 0 && staffCategory   < NumOfStaffCategory);
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        
        return this.StdStaffRateExceed[staffCategory][workingLocation][mScheme];
    }
    
    public String toStdStaffRateTableField(int staffCategory, int workingLocation, int mScheme)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (staffCategory   >= 0 && staffCategory   < NumOfStaffCategory);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        String workingLocationStr = null ;
        
        if (workingLocation == 0)
        { workingLocationStr = "resident"; }
        else if (workingLocation == 1)
        { workingLocationStr = "non_resident"; }
        else if (workingLocation == 2)
        { workingLocationStr = "offshore"; }
        
        // Return something like: sc1_resident_m1
        return "sc" + (staffCategory + 1) + "_" + workingLocationStr + "_m" + (mScheme + 1);
    }
    
    public String toSupStaffRateTableField(int workingLocation, int mScheme)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        String workingLocationStr = null ;
        
        if (workingLocation == 0)
        { workingLocationStr = "resident"; }
        else if (workingLocation == 1)
        { workingLocationStr = "non_resident"; }
        else if (workingLocation == 2)
        { workingLocationStr = "offshore"; }
        
        // Return something like: supplementary_1_resident_m1
        return "supplementary_1_" + workingLocationStr + "_m" + (mScheme + 1);
    }
    
    public String getServiceGroup()
    { return this.ServiceGroup; }
    
    public void setServiceGroup(String serviceGroup)
    {
        assert (serviceGroup.equals(this.MajorGroupInd) || serviceGroup.equals(this.MinorGroupInd));
        this.ServiceGroup = serviceGroup;
    }
    
    public void setContractorName(String contractorName)
    {
        assert (contractorName != null && !contractorName.equals(""));
        this.ContractorName = contractorName;
    }
    
    public String getContractorName()
    { return this.ContractorName; }
    
    public void setSupStaffIndicator(int supStaffIndicator)
    {
        assert (supStaffIndicator == 0 || supStaffIndicator == -1);
        this.SupStaffIndicator = supStaffIndicator;
    }
    
    public int getSupStaffIndicator()
    { return this.SupStaffIndicator; }
    
    public void setActiveIndicator(int activeIndicator)
    {
        assert (activeIndicator == 0 || activeIndicator == -1);
        this.ActiveIndicator = activeIndicator;
    }
    
    public int getActiveIndicator()
    { return this.ActiveIndicator; }
    
    public void setCreatedDate(String createdDate)
    {
        assert (createdDate != null && !createdDate.equals(""));
        this.CreatedDate = createdDate;
    }
    
    public String getCreatedDate()
    { return this.CreatedDate; }
    
    public void setLastUpdatedDate(String lastUpdatedDate)
    {
        assert (lastUpdatedDate != null && !lastUpdatedDate.equals(""));
        this.LastUpdatedDate = lastUpdatedDate;
    }
    
    public String getLastUpdatedDate()
    { return this.LastUpdatedDate; }
    
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        assert (lastUpdatedBy != null && !lastUpdatedBy.equals(""));
        this.LastUpdatedBy = lastUpdatedBy;
    }
    
    public String getLastUpdatedBy()
    { return this.LastUpdatedBy; }
    
    public void setStdStaffRate(int staffCategory, int workingLocation, int mScheme, double staffRate)
    {
        assert (staffCategory   >= 0 && staffCategory   < NumOfStaffCategory);
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        this.StdStaffRate[staffCategory][workingLocation][mScheme] = staffRate;
    }
    
    public double getStdStaffRate(int staffCategory, int workingLocation, int mScheme)
    {
        assert (staffCategory   >= 0 && staffCategory   < NumOfStaffCategory);
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        return this.StdStaffRate[staffCategory][workingLocation][mScheme];
    }
    
    public void setSupStaffRate(int workingLocation, int mScheme, double staffRate)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        this.SupStaffRate[workingLocation][mScheme] = staffRate;
    }
    
    public double getSupStaffRate(int workingLocation, int mScheme)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        return this.SupStaffRate[workingLocation][mScheme];
    }
    
    public void setContractorID(String contractorID)
    { this.ContractorID = contractorID; }
    
    public String getContractorID()
    { return this.ContractorID; }
    
    public void setCurrency(String currency)
    {
        assert (currency != null && !currency.equals(""));
        this.Currency = currency;
    }
    
    public String getCurrency()
    { return this.Currency; }
    
}