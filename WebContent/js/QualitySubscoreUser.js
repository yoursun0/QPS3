/*************************************************************************/
function GetWA_WAOnClick(scg, dept_id, wa_file_part, wa_file_no)
{   
    var thisForm = document.form;
    
    thisForm.scg.value          = scg;
    thisForm.dept_id.value      = dept_id;
    thisForm.wa_file_part.value = wa_file_part;
    thisForm.wa_file_no.value   = wa_file_no;

    thisForm.target = "_self";
    thisForm.action = "WAChallenge.servlet";
    thisForm.submit();
}

/*************************************************************************/

function WAChallenge_Validation(f) 
{
    // regular expresssion for checking of empty string
    reEmpty = new RegExp("[a-zA-Z.0-9]+");

    reInteger = new RegExp("[0-9]+");

    challengeNo = f.ChallengeNo.value;

            if (f.IssuedDate.value.search(reEmpty) < 0)
            {
                alert("Please input the issued date of the invitation letter!");
                f.IssuedDate.select();
                f.IssuedDate.focus();
                return false;
            }

            if (f.ClosingDate.value.search(reEmpty) < 0)
            {
                alert("Please input the invitation closing date of the work assignment!");
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
function ShowImage_DownloadFile()
{   
    document.form.action               = "../qpsuser/CPSSLUserWAC";
    document.form.request_action.value = "getPDF";
    document.form.target               = "_self";
    document.form.submit();
}

function ShowImage_BtnBackOnClick()
{
    document.form.action               = "../qpsuser/CPSSLUser";
    document.form.request_action.value = "";
    document.form.target               = "_self";
    document.form.submit();
}

function ShowImage_PageNoOnClick(pageNo)
{
    document.form.action        = "../qpsuser/CPSShowImage.jsp";
    document.form.page_no.value = pageNo;
    document.form.target        = "_self";
    document.form.submit();
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
