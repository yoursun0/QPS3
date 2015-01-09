/*************************************************************************/
function GetWA_InfoPdfOnClick()
{
    document.form.action               = "../qpsuser/StaffRateValidationSLUser";
    document.form.request_action.value = "getInfoPdf";
    document.form.target               = "_self";
    document.form.submit();
}


function GetWA_WAOnClick(scg, dept_id, wa_file_part, wa_file_no)
{   
    var thisForm = document.form;
    
    thisForm.scg.value                  = scg;
    thisForm.dept_id.value              = dept_id;
    thisForm.wa_file_part.value         = wa_file_part;
    thisForm.wa_file_no.value           = wa_file_no;

    thisForm.target = "_self";
    thisForm.action = "WAChallenge.servlet";
    thisForm.submit();
}

/*************************************************************************/
function GetContractor_OnLoad()
{
    var thisForm = document.form; 

    var sc = thisForm.sc.value;

    // Selected maintenance scheme
    var mScheme = thisForm.m_scheme.value;
    
    // Selected contractor id list
    var contractorIDList = thisForm.contractor_id_list.value.split(",");

    if (thisForm.list_mscheme != null && mScheme != "")
    { 
        if (sc == "2")
            thisForm.list_mscheme[parseInt(mScheme) - 1].checked = true; 
            
        if (sc == "3")
            thisForm.list_mscheme[parseInt(mScheme)].checked = true;    
    }

    // select contractor
    if (thisForm.contractor_id_list.value != "")
    {
        for (var i = 0; i < contractorIDList.length; i++)
        { thisForm.all["ChkBox_" + contractorIDList[i]].checked = true; }
    }
    else
    {
        for (var i = 0; i < thisForm.elements.length; i++)
        {
            if 
            (
                (thisForm.elements[i].name != null) &&
                (thisForm.elements[i].name.substring(0, 7) == "ChkBox_") && 
                (thisForm.elements[i].type == "checkbox")
            )
            {  thisForm.elements[i].checked = true; }
        }        
    }

    var wa_type = thisForm.wa_type.value;
    var sc      = thisForm.sc.value;
    
    if (wa_type == "change_request" && sc!= "2" && sc != "3") 
    {
        // For the work assignment with awarded contractor and no need to select Maintenance Scheme, go to "staff rates" page directly
        GetContractor_BtnNextOnClick();
    }
    else
    {
        document.getElementById('form').style.display = 'block';
    }
}


function GetContractor_BtnBackOnClick()
{
    var thisForm = document.form;    
    thisForm.target = "_self";
	thisForm.action               = "../qpsuser/" + thisForm.prev_page.value;
	
	var wa_type = thisForm.wa_type.value;
	// If WA type is proposal, go to the "WA challenge" page
    if (wa_type == "proposal") {
        thisForm.request_action.value = "waChallenge";
    }else{
	// If WA type is change request, go to the WA list page directly
		thisForm.request_action.value = "";   
	}

    thisForm.submit();    
}


function GetContractor_GetSelectedContractorList()
{
    var thisForm = document.form;
        
    thisForm.contractor_id_list.value   = "";
        
    for (var i = 0; i < thisForm.elements.length; i++)
    {
        if 
        (
            (thisForm.elements[i].name != null) &&
            (thisForm.elements[i].name.substring(0, 7) == "ChkBox_") && 
            (thisForm.elements[i].type == "checkbox") &&
            (thisForm.elements[i].checked)
        )       
        if (thisForm.contractor_id_list.value == "")
        {       
            thisForm.contractor_id_list.value = thisForm.elements[i].name.substring(7, thisForm.elements[i].name.length);
            thisForm.contractor_list.value    = thisForm.elements[i].value;
        }
        else
        {
            thisForm.contractor_id_list.value += "," + thisForm.elements[i].name.substring(7, thisForm.elements[i].name.length);
            thisForm.contractor_list.value    += "," + thisForm.elements[i].value;
        }
    }
}


function GetContractor_BtnNextOnClick()
{
    var thisForm = document.form;
        
    GetContractor_GetSelectedContractorList();
    
    if ((thisForm.contractor_id_list.value == "") && !(wa_type == "change_request"))
    {
        alert("Please select at least one contractor.");
        return false;
    }

    var sc = thisForm.sc.value;
    
    thisForm.m_scheme.value = "";
    
    if (sc == "2" || sc == "3") 
    {
        for (var i = 0; i < thisForm.list_mscheme.length; i++)
        {
            if (thisForm.list_mscheme[i].checked)
                thisForm.m_scheme.value = thisForm.list_mscheme[i].value;
        }
    
        if (thisForm.m_scheme.value == "")
        {
            alert("Please select a Maintenance Scheme.");
            return false;
        }
    }                
    
    thisForm.target               = "_self";     
    thisForm.action               = "../qpsuser/StaffRateValidationSLUserWAC";
    thisForm.request_action.value = "getStaffRates";
    thisForm.submit();    
}


/*************************************************************************/
function WAChallenge_Validation(f) 
{
    // regular expresssion for checking of empty string
    reEmpty = new RegExp("[a-zA-Z.0-9]+");

    reInteger = new RegExp("[0-9]+");

    if (f.IssuedDate.value.search(reEmpty) < 0){
                alert("Please input the issued date of the invitation letter!");
                f.IssuedDate.select();
                f.IssuedDate.focus();
                return false;
    }

    if (f.ClosingDate.value.search(reEmpty) < 0){
                alert("Please input the proposal submission deadline of the work assignment!");
                f.ClosingDate.select();
                f.ClosingDate.focus();
                return false;
    }
      
    var issuedDate = formatDate(f.IssuedDate.value);
	var closingDate = formatDate(f.ClosingDate.value);
	
	if (issuedDate == 0) {
		alert("The issue date format is incorrect! Please input again!");
		f.ClosingDate.select();
		f.ClosingDate.focus();
		return false;
	}
	
	if (closingDate == 0) {
		alert("The closing date format is incorrect! Please input again!");
		f.ClosingDate.select();
		f.ClosingDate.focus();
		return false;
	}

    return true;  
}

function WAChallenge_BtnBackOnClick(obj)
{
    var f = document.form;
    
    f.action = f.forwardScreen.value;
    f.target = "_self";
    f.submit();
}


function WAChallenge_BtnNextOnClick(obj)
{
    var f = document.form;

    if (f.ChallengeNo.value == "5")
    {
        f.DebarredContractor.value = "";
        
        for (var i = 0; i < f.chkDebarredContractor.length; i++)
        {
            if (f.chkDebarredContractor[i].checked)
            {
                if (f.DebarredContractor.value == "")
                    f.DebarredContractor.value = f.chkDebarredContractor[i].value;
                else
                {	
                    if (f.chkDebarredContractor[i].value != "") // Avoid adding "No debarred contractor" value
                        f.DebarredContractor.value += "," + f.chkDebarredContractor[i].value;
                }
            }
        }        
    }

    if (WAChallenge_Validation(f)) 
    {
        f.action = "WAChallengeValidation.servlet";
        f.target = "_self";
        f.submit();
    }

}

function WAChallenge_chkDebarredContractor_OnClick(flag)
{
    var f = document.form;
 
    // Clear all contractor selection if "No debarred contractor is selected" is selected
    if (flag == "NO_DEBARRED")
    {
        for (var i = 1; i < f.chkDebarredContractor.length; i++)
            f.chkDebarredContractor[i].checked = false;
    }
    else
    {
        f.chkDebarredContractor[0].checked = false;
    }
}
/*************************************************************************/
function GetStaffRates_OnLoad()
{
    var thisForm = document.form;

    if (thisForm.all_staff_rates.value != "")
    {
        var fPrefix = "TxtSR__";

        var allStaffRates = thisForm.all_staff_rates.value.split(",");

        for (var i = 0; i < allStaffRates.length; i+=2)
        { thisForm.all[fPrefix + allStaffRates[i]].value = allStaffRates[i + 1]; }
    }    
}


function GetStaffRates_BtnResetOnClick()
{
    var thisForm = document.form;

    thisForm.all_staff_rates.value = "";

    thisForm.target               = "_self";
    thisForm.action               = "../qpsuser/StaffRateValidationSLUserWAC";
    thisForm.request_action.value = "getStaffRates";
    thisForm.submit();
}


function GetStaffRates_BtnBackOnClick()
{    
    var thisForm = document.form;

    var wa_type = thisForm.wa_type.value;
    var sc                  = thisForm.sc.value;
    
    if (wa_type == "change_request" && sc!= "2" && sc != "3") 
    {
        // For the work assignment with awarded contractor and no need to select Maintenance Scheme, go back to the first page directly
        thisForm.target               = "_self";
        thisForm.action               = "../qpsuser/StaffRateValidationCRSLUser";
        thisForm.submit();       
    }
    else
    {   // Otherwise, go to the "contractor selection" page
        thisForm.target               = "_self";
        thisForm.action               = "../qpsuser/StaffRateValidationSLUserWAC";
        thisForm.request_action.value = "getContractor";
        thisForm.submit();
    }
}

    
function GetStaffRates_BtnSubmitOnClick(showSubscoreRpt, sc)
{
    var thisForm = document.form;
    
    if (!GetStaffRates_ChkStaffRates()) return false;
    
    thisForm.show_subscore_rpt.value = showSubscoreRpt;
    
    // Construct staff rate string
     thisForm.all_staff_rates.value = "";
    
    var fPrefix = "TxtSR__";
    
    for (var i = 0; i < thisForm.elements.length; i++)
    {
        if 
        (
            (thisForm.elements[i].name != null) &&          
            (thisForm.elements[i].name.substring(0, 7) == fPrefix) && 
            (thisForm.elements[i].type == "text")
        )
        {  
            thisForm.all_staff_rates.value += 
                (thisForm.all_staff_rates.value == "" ? "" : ",") + 
                thisForm.elements[i].name.substring(fPrefix.length) + "," + thisForm.elements[i].value; 
        }    
    } 

    thisForm.target               = "_self";  
    thisForm.action               = "../qpsuser/StaffRateValidationSLUserWAC";
    thisForm.request_action.value = "genCat" + sc + "ValidationReport";   
    thisForm.submit();
}


function GetStaffRates_ChkStaffRates()
{
    var thisForm = document.form;
    
    for (var i = 0; i < thisForm.elements.length; i++)
    {
        var objStaffRateField = thisForm.elements[i];
    
        if
        (
            (objStaffRateField.name != null) &&    
            (objStaffRateField.name.substring(0, 7) == "TxtSR__") && 
            (objStaffRateField.type == "text") &&
            (objStaffRateField.value != "")
        )
        {
            if (!GetStaffRates_IsValidStaffRate(objStaffRateField))
            { return false; }
        }    
    }
        
    return true;
}


function GetStaffRates_IsValidStaffRate(objStaffRateField)
{
	var objRegExp = /^\d*(\.\d{1,2})?$/;

    var staffRateStr = objStaffRateField.value;

    var isFormatOK = objRegExp.test(staffRateStr);
    
    if (staffRateStr.toLowerCase() == "no offer") // is "no offer"
    {
        return true;
    }

    if (parseFloat(staffRateStr, 10) != (staffRateStr * 1)) // is float number ?
    { 
        objStaffRateField.select();
        alert("Please input a valid number or \"No Offer\" here."); 
        return false;
    }
    
    if (!(staffRateStr > 0 && ((10000000000.0 - staffRateStr) > 0))) // Within range ?
    { 
        objStaffRateField.select();
        alert("Staff rate must be greater than 0 and less than 10,000,000,000.");
        return false;
    }
    
    if ((!isFormatOK)&&(staffRateStr != "")) // within 2 decimal place ?
    { 
        objStaffRateField.select();
        alert("Please input a decimal number within 2 decimal places."); 
        return false;
    }
    
    return true;
}


function GetStaffRates_RandNum()
{
    var max               = 1000;
    var numOfDigit        = 2;
    var percentToGetEmpty = 50;

    var maxF        = max * Math.pow(10, numOfDigit);
    var numOfDigitF = Math.pow(10, numOfDigit);
        
    var result = (Math.floor(Math.random() * maxF) / numOfDigitF);
        
    if (result > (max * percentToGetEmpty / 100.0))
        return "";
    else
        return (result);
}


function GetStaffRates_FillStaffRates()
{
    var thisForm = document.form;
        
    for (var i = 0; i < thisForm.elements.length; i++)
    {
        if 
        (
            (thisForm.elements[i].name != null) &&          
            (thisForm.elements[i].name.substring(0, 7) == "TxtSR__") && 
            (thisForm.elements[i].type == "text")
        )
        {
            thisForm.elements[i].value = GetStaffRates_RandNum();
        }    
    }       
}

/*************************************************************************/
function ShowImage_PageNoOnClick(pageNo)
{
    document.form.action        = "../qpsuser/StaffRateValidationShowImage.jsp";
    document.form.page_no.value = pageNo;
    document.form.target        = "_self";
    document.form.submit();
}
    
function ShowImage_DownloadFile()
{   
    document.form.action               = "../qpsuser/StaffRateValidationSLUserWAC";
    document.form.request_action.value = "getPDF";
    document.form.target               = "_self";
    document.form.submit();
}


function ShowImage_BtnBackOnClick()
{
    var thisForm = document.form;    
    thisForm.target               = "_self";
    thisForm.action               = "../qpsuser/StaffRateValidationSLUserWAC";
    thisForm.request_action.value = "getStaffRates";   
    thisForm.submit();        
}


function formatDate(inputDate){
    var error = -1;
	var mArray = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
    var re = /^(\d{1,2})-([a-zA-Z]{3})-(\d{4})$/;
	
	splitDate = inputDate.split("-");
    
    if (!(inputDate.match(re))){
    	error = -100;
    }
    fDay = splitDate[0];
    
    for (var i=0;i<12;i++){
        if (splitDate[1] == mArray[i]){
            fMonth = i;
            error++;
            break;
        }        
    }
    fYear = splitDate[2];    
    if (error!=0){
    	return 0;
    }else{
    	returnDate = new Date(fYear+"/"+(fMonth+1)+"/"+fDay);  
    	return returnDate;
    }
}
/*************************************************************************/