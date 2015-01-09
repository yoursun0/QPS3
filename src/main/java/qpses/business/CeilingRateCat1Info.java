package qpses.business;

public class CeilingRateCat1Info
{     
    public static final String MinorGroupInd = "N";
    public static final String MajorGroupInd = "J";
    
    public static final int NumOfWorkingLocation = 3;
    public static final int NumOfStaffCategory   = 10;
    
    public static final String ExceedString    = "Yes";
    public static final String NotExceedString = "No";
    public static final String NoOfferString   = "No Offer";
    
    public static final String[] WorkingLocationName = {"Resident", "Non-Resident", "Off-Shore"};
    
    public static final double NoOfferDBNumber  = -999;   // Value indicates "no offer" in database
    public static final double NoOfferWebNumber = -1000;  // Value indicates "no offer" from web page
    
    private double[][] StdStaffRate        = new double[this.NumOfStaffCategory][this.NumOfWorkingLocation];
    private String[][] StdStaffRateExceed = new String[this.NumOfStaffCategory][this.NumOfWorkingLocation];
    
    private double[] SupStaffRate        = new double[this.NumOfWorkingLocation];
    private String[] SupStaffRateExceed = new String[this.NumOfWorkingLocation];
    
    private String ContractorName;
    private String ContractorID;
    private String ServiceGroup;
    private String Currency;
    private int ActiveIndicator;
    private String CreatedDate;
    private String LastUpdatedDate;
    private String LastUpdatedBy;
    private int SupStaffIndicator;
    
    /** Creates a new instance of CeilingRateCat1Info */
    public CeilingRateCat1Info()
    {}
    
    public void CheckCeilingRate(CeilingRateCat1Info aCeilingRateCat1Info)
    {
        // Check standard staff rate
        for (int i = 0; i < this.NumOfStaffCategory; i++)
        {
            for (int j = 0; j < this.NumOfWorkingLocation; j++)
            {
                if (aCeilingRateCat1Info.getStdStaffRate(i, j) == this.NoOfferDBNumber && this.getStdStaffRate(i, j) != this.NoOfferDBNumber) // User input value for "no offer" field
                    this.setStdStaffRateExceed(i, j, this.NoOfferString);
                else if (this.getStdStaffRate(i, j) > aCeilingRateCat1Info.getStdStaffRate(i, j))
                    this.setStdStaffRateExceed(i, j, this.ExceedString);
                else
                    this.setStdStaffRateExceed(i, j, this.NotExceedString);
            }
        }
        
        // Check supplementary staff rate
        for (int i = 0; i < this.NumOfWorkingLocation; i++)
        {
            if (aCeilingRateCat1Info.getSupStaffRate(i) == this.NoOfferDBNumber && this.getSupStaffRate(i) != this.NoOfferDBNumber)
                this.setSupStaffRateExceed(i, this.NoOfferString);
            else if (this.getSupStaffRate(i) > aCeilingRateCat1Info.getSupStaffRate(i))
                this.setSupStaffRateExceed(i, this.ExceedString);
            else
                this.setSupStaffRateExceed(i, this.NotExceedString);
        }
    }
    
    public void setSupStaffRateExceed(int workingLocation, String exceedString)
    {
        assert (workingLocation >= 0 && workingLocation < this.NumOfWorkingLocation);
        assert (exceedString.equals(this.ExceedString) || exceedString.equals(this.NotExceedString) || exceedString.equals(this.NoOfferString));
        
        this.SupStaffRateExceed[workingLocation] = exceedString;
    }
    
    public String getSupStaffRateExceed(int workingLocation)
    {
        assert (workingLocation >= 0 && workingLocation < this.NumOfWorkingLocation);
        
        return this.SupStaffRateExceed[workingLocation];
    }
    
    public void setStdStaffRateExceed(int staffCategory, int workingLocation, String exceedString)
    {
        assert (staffCategory   >= 0 && staffCategory   < this.NumOfStaffCategory);
        assert (workingLocation >= 0 && workingLocation < this.NumOfWorkingLocation);
        assert (exceedString.equals(this.ExceedString) || exceedString.equals(this.NotExceedString) || exceedString.equals(this.NoOfferString));
        
        this.StdStaffRateExceed[staffCategory][workingLocation] = exceedString;
    }
    
    public String getStdStaffRateExceed(int staffCategory, int workingLocation)
    {
        assert (staffCategory   >= 0 && staffCategory   < this.NumOfStaffCategory);
        assert (workingLocation >= 0 && workingLocation < this.NumOfWorkingLocation);
        
        return this.StdStaffRateExceed[staffCategory][workingLocation];
    }
    
    public String toSupStaffRateTableField(int workingLocation)
    {
        assert (workingLocation >= 0 && workingLocation < this.NumOfWorkingLocation);
        
        String workingLocationStr = null;
        
        if (workingLocation == 0)
        { workingLocationStr = "resident"; }
        else if (workingLocation == 1)
        { workingLocationStr = "non_resident"; }
        else if (workingLocation == 2)
        { workingLocationStr = "offshore"; }
        
        // Return something like: supplementary_1_resident
        return "supplementary_1_" + workingLocationStr;
    }
    
    public String toStdStaffRateTableField(int staffCategory, int workingLocation)
    {
        assert (workingLocation >= 0 && workingLocation < this.NumOfWorkingLocation);
        assert (staffCategory   >= 0 && staffCategory   < this.NumOfStaffCategory);
        
        String workingLocationStr = null ;
        
        if (workingLocation == 0)
        { workingLocationStr = "resident"; }
        else if (workingLocation == 1)
        { workingLocationStr = "non_resident"; }
        else if (workingLocation == 2)
        { workingLocationStr = "offshore"; }
        
        // Return something like: sc3_resident
        return "sc" + (staffCategory + 3) + "_" + workingLocationStr;
    }
    
    public void setStdStaffRate(int staffCategory, int workingLocation, double staffRate)
    {
        assert (staffCategory   >= 0 && staffCategory   < this.NumOfStaffCategory);
        assert (workingLocation >= 0 && workingLocation < this.NumOfWorkingLocation);
        
        this.StdStaffRate[staffCategory][workingLocation] = staffRate;
    }
    
    public double getStdStaffRate(int staffCategory, int workingLocation)
    {
        assert (staffCategory   >= 0 && staffCategory   < this.NumOfStaffCategory);
        assert (workingLocation >= 0 && workingLocation < this.NumOfWorkingLocation);
        
        return this.StdStaffRate[staffCategory][workingLocation];
    }
    
    public void setSupStaffRate(int workingLocation, double staffRate)
    {
        assert (workingLocation >= 0 && workingLocation < this.NumOfWorkingLocation);
        
        this.SupStaffRate[workingLocation] = staffRate;
    }
    
    public double getSupStaffRate(int workingLocation)
    {
        assert (workingLocation >= 0 && workingLocation < this.NumOfWorkingLocation);
        
        return this.SupStaffRate[workingLocation];
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
        this.ContractorName = contractorName;
    }
    
    public String getContractorName()
    { return this.ContractorName; }
    
    public void setContractorID(String contractorID)
    { this.ContractorID = contractorID; }
    
    public String getContractorID()
    { return this.ContractorID; }
    
    public void setCurrency(String currency)
    {
        this.Currency = currency;
    }
    
    public String getCurrency()
    { return this.Currency; }
    
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
        this.CreatedDate = createdDate;
    }
    
    public String getCreatedDate()
    { return this.CreatedDate; }
    
    public void setLastUpdatedDate(String lastUpdatedDate)
    {
        this.LastUpdatedDate = lastUpdatedDate;
    }
    
    public String getLastUpdatedDate()
    { return this.LastUpdatedDate; }
    
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.LastUpdatedBy = lastUpdatedBy;
    }
    
    public String getLastUpdatedBy()
    { return this.LastUpdatedBy; }
}
