package qpses.business;

import java.util.ArrayList;

public class CeilingRateCat3Info
{
    public static final String MinorGroupInd = "N";
    public static final String MajorGroupInd = "J";
    
    public static final int NumOfWorkingLocation      = 3;
    public static final int NumOfOneOffStaffCategory  = 12;
    public static final int NumOfOnGoingStaffCategory = 10;
    public static final int NumOfMScheme              = 3;
    
    public static final String []WorkingLocationName   = {"Resident", "Non-Resident", "Off-Shore"};
    public static final String []WorkingLocationDBName = {"resident", "non_resident", "offshore"};
    public static final String []MSchemeName           = {"M1", "M2", "M3"};
    
    public static final String ExceedString    = "Yes";
    public static final String NotExceedString = "No";
    public static final String NoOfferString   = "No Offer";
    
    public static final double NoOfferDBNumber  = -999;   // Value indicates "no offer" in database
    public static final double NoOfferWebNumber = -1000;  // Value indicates "no offer" from web page
    
    private double [][][] StdOnGoingStaffRate =
            new double[NumOfOnGoingStaffCategory][NumOfWorkingLocation][NumOfMScheme];
    
    private String [][][] StdOnGoingStaffRateExceed =
            new String[NumOfOnGoingStaffCategory][NumOfWorkingLocation][NumOfMScheme];
    
    private double [][] SupOnGoingStaffRate      = new double[NumOfWorkingLocation][NumOfMScheme];
    private String[][] SupOnGoingStaffRateExceed = new String[NumOfWorkingLocation][NumOfMScheme];
    
    private double [][] StdOneOffStaffRate       = new double[NumOfOneOffStaffCategory][NumOfWorkingLocation];
    private String [][] StdOneOffStaffRateExceed = new String[NumOfOneOffStaffCategory][NumOfWorkingLocation];
    
    private double [] SupOneOffStaffRate       = new double[NumOfWorkingLocation];
    private String[] SupOneOffStaffRateExceed = new String[NumOfWorkingLocation];
    
    private String ContractorName;
    private String ContractorID;
    private String ServiceGroup;
    private String Currency;
    private int ActiveIndicator;
    private String CreatedDate;
    private String LastUpdatedDate;
    private String LastUpdatedBy;
    private int SupStaffIndicator;
    
    /** Creates a new instance of CeilingRateCat3Info */
    public CeilingRateCat3Info()
    {}
    
    public void CheckCeilingRate(CeilingRateCat3Info aCeilingRateCat3Info)
    {
        // Check standard on-going staff rate
        for (int i = 0; i < NumOfOnGoingStaffCategory; i++)
        {
            for (int j = 0; j < NumOfWorkingLocation; j++)
            {
                for (int k = 0; k < NumOfMScheme; k++)
                {
                    if (aCeilingRateCat3Info.getStdOnGoingStaffRate(i, j, k) == NoOfferDBNumber &&
                            this.getStdOnGoingStaffRate(i, j, k) != NoOfferDBNumber ) // User input value for "no offer" field
                        this.setStdOnGoingStaffRateExceed(i, j, k, NoOfferString);
                    else if (this.getStdOnGoingStaffRate(i, j, k) > aCeilingRateCat3Info.getStdOnGoingStaffRate(i, j, k))
                        this.setStdOnGoingStaffRateExceed(i, j, k, ExceedString);
                    else
                        this.setStdOnGoingStaffRateExceed(i, j, k, NotExceedString);
                }
            }
        }
        
        // Check supplementary on-going staff rate
        for (int i = 0; i < NumOfWorkingLocation; i++)
        {
            for (int j = 0; j < NumOfMScheme; j++)
            {
                if (aCeilingRateCat3Info.getSupOnGoingStaffRate(i, j) == NoOfferDBNumber &&
                        this.getSupOnGoingStaffRate(i, j) != NoOfferDBNumber ) // User input value for "no offer" field
                    this.setSupOnGoingStaffRateExceed(i, j, NoOfferString);
                else if (this.getSupOnGoingStaffRate(i, j) > aCeilingRateCat3Info.getSupOnGoingStaffRate(i, j))
                    this.setSupOnGoingStaffRateExceed(i, j, ExceedString);
                else
                    this.setSupOnGoingStaffRateExceed(i, j, NotExceedString);
            }
        }
        
        // Check standard one-off staff rate
        for (int i = 0; i < NumOfOneOffStaffCategory; i++)
        {
            for (int j = 0; j < NumOfWorkingLocation; j++)
            {
                if (aCeilingRateCat3Info.getStdOneOffStaffRate(i, j) == NoOfferDBNumber &&
                        this.getStdOneOffStaffRate(i, j) != NoOfferDBNumber ) // User input value for "no offer" field
                    this.setStdOneOffStaffRateExceed(i, j, NoOfferString);
                else if (this.getStdOneOffStaffRate(i, j) > aCeilingRateCat3Info.getStdOneOffStaffRate(i, j))
                    this.setStdOneOffStaffRateExceed(i, j, ExceedString);
                else
                    this.setStdOneOffStaffRateExceed(i, j, NotExceedString);
            }
        }
        
        // Check supplementary one-off staff rate
        for (int i = 0; i < NumOfWorkingLocation; i++)
        {
            if (aCeilingRateCat3Info.getSupOneOffStaffRate(i) == NoOfferDBNumber &&
                    this.getSupOneOffStaffRate(i) != NoOfferDBNumber ) // User input value for "no offer" field
                this.setSupOneOffStaffRateExceed(i, NoOfferString);
            else if (this.getSupOneOffStaffRate(i) > aCeilingRateCat3Info.getSupOneOffStaffRate(i))
                this.setSupOneOffStaffRateExceed(i, ExceedString);
            else
                this.setSupOneOffStaffRateExceed(i, NotExceedString);
        }
    }
    
    public void setSupOnGoingStaffRateExceed(int workingLocation, int mScheme, String exceedString)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        assert (exceedString.equals(ExceedString) || exceedString.equals(NotExceedString) || exceedString.equals(NoOfferString));
        
        this.SupOnGoingStaffRateExceed[workingLocation][mScheme] = exceedString;
    }
    
    public String getSupOnGoingStaffRateExceed(int workingLocation, int mScheme)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        return this.SupOnGoingStaffRateExceed[workingLocation][mScheme];
    }
    
    public void setStdOnGoingStaffRateExceed(int staffCategory, int workingLocation, int mScheme, String exceedString)
    {
        assert (staffCategory   >= 0 && staffCategory   < NumOfOnGoingStaffCategory);
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        assert (exceedString.equals(ExceedString) || exceedString.equals(NotExceedString) || exceedString.equals(NoOfferString));
        
        this.StdOnGoingStaffRateExceed[staffCategory][workingLocation][mScheme] = exceedString;
    }
    
    public String getStdOnGoingStaffRateExceed(int staffCategory, int workingLocation, int mScheme)
    {
        assert (staffCategory   >= 0 && staffCategory   < NumOfOnGoingStaffCategory);
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        return this.StdOnGoingStaffRateExceed[staffCategory][workingLocation][mScheme];
    }
    
    public String toStdOneOffStaffRateTableField(int staffCategory, int workingLocation)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (staffCategory   >= 0 && staffCategory   < NumOfOneOffStaffCategory);
        
        // Return something like: one_off_sc3_resident
        return "one_off_sc" + (staffCategory + 1/*3*/) + "_" + WorkingLocationDBName[workingLocation];
    }
    
    public String toSupOneOffStaffRateTableField(int workingLocation)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        
        // Return something like: one_off_supplementary_1_resident
        return "one_off_supplementary_1_" + WorkingLocationDBName[workingLocation];
    }
    
    public String toStdOnGoingStaffRateTableField(int staffCategory, int workingLocation, int mScheme)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (staffCategory   >= 0 && staffCategory   < NumOfOnGoingStaffCategory);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        // Return something like: ongoing_sc1_resident_m1
        return "ongoing_sc" + (staffCategory + 1) + "_" + WorkingLocationDBName[workingLocation] + "_m" + (mScheme + 1);
    }
    
    public String toSupOnGoingStaffRateTableField(int workingLocation, int mScheme)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        // Return something like: ongoing_supplementary_1_resident_m1
        return "ongoing_supplementary_1_" + WorkingLocationDBName[workingLocation] + "_m" + (mScheme + 1);
    }
    
    public void setStdOnGoingStaffRate(int staffCategory, int workingLocation, int mScheme, double staffRate)
    {
        assert (staffCategory   >= 0 && staffCategory   < NumOfOnGoingStaffCategory);
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        this.StdOnGoingStaffRate[staffCategory][workingLocation][mScheme] = staffRate;
    }
    
    public double getStdOnGoingStaffRate(int staffCategory, int workingLocation, int mScheme)
    {
        assert (staffCategory   >= 0 && staffCategory   < NumOfOnGoingStaffCategory);
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        return this.StdOnGoingStaffRate[staffCategory][workingLocation][mScheme];
    }
    
    public void setSupOnGoingStaffRate(int workingLocation, int mScheme, double staffRate)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        this.SupOnGoingStaffRate[workingLocation][mScheme] = staffRate;
    }
    
    public double getSupOnGoingStaffRate(int workingLocation, int mScheme)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (mScheme         >= 0 && mScheme         < NumOfMScheme);
        
        return this.SupOnGoingStaffRate[workingLocation][mScheme];
    }
    
    public void setStdOneOffStaffRate(int staffCategory, int workingLocation, double staffRate)
    {
        assert (staffCategory   >= 0 && staffCategory   < NumOfOneOffStaffCategory);
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        
        this.StdOneOffStaffRate[staffCategory][workingLocation] = staffRate;
    }
    
    public double getStdOneOffStaffRate(int staffCategory, int workingLocation)
    {
        assert (staffCategory   >= 0 && staffCategory   < NumOfOneOffStaffCategory);
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        
        return this.StdOneOffStaffRate[staffCategory][workingLocation];
    }
    
    public String getServiceGroup()
    { return this.ServiceGroup; }
    
    public void setServiceGroup(String serviceGroup)
    {
        assert (serviceGroup.equals(MajorGroupInd) || serviceGroup.equals(MinorGroupInd));
        this.ServiceGroup = serviceGroup;
    }
    
    public void setContractorName(String contractorName)
    {
        assert (contractorName != null && !contractorName.equals(""));
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
        assert (currency != null && !currency.equals(""));
        this.Currency = currency;
    }
    
    public String getCurrency()
    { return this.Currency; }
    
    public String getStdOneOffStaffRateExceed(int staffCategory, int workingLocation)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (staffCategory   >= 0 && staffCategory   < NumOfOneOffStaffCategory);
        
        return this.StdOneOffStaffRateExceed[staffCategory][workingLocation];
    }
    
    public void setStdOneOffStaffRateExceed(int staffCategory, int workingLocation, String exceedString)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        assert (staffCategory   >= 0 && staffCategory   < NumOfOneOffStaffCategory);
        assert (exceedString.equals(ExceedString) || exceedString.equals(NotExceedString) || exceedString.equals(NoOfferString));
        
        this.StdOneOffStaffRateExceed[staffCategory][workingLocation] = exceedString;
    }
    
    public String getSupOneOffStaffRateExceed(int workingLocation)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        return this.SupOneOffStaffRateExceed[workingLocation];
    }
    
    public void setSupOneOffStaffRateExceed(int workingLocation, String exceedString)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        this.SupOneOffStaffRateExceed[workingLocation] = exceedString;
    }
    
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
    
    public void setSupOneOffStaffRate(int workingLocation, double staffRate)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        
        this.SupOneOffStaffRate[workingLocation] = staffRate;
    }
    
    public double getSupOneOffStaffRate(int workingLocation)
    {
        assert (workingLocation >= 0 && workingLocation < NumOfWorkingLocation);
        
        return this.SupOneOffStaffRate[workingLocation];
    }
    
}
