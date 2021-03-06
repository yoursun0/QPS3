    // regular expresssion for checking of empty string
    reEmpty = new RegExp("[a-zA-Z.0-9]+");
    reInteger = new RegExp("[0-9]+");
    
    // regular expression for checking date format
    reDateFormat = new RegExp("[0-9]{1,2}-[a-zA-Z]{3}-[0-9]{4}","i");

function WAChallenge_Validation(f) 
{
    
    if (f.ServiceContractNo.value.search(reEmpty) < 0){
        alert("Please input the Service Contract Reference Number to search for a work assignment!");
        f.ServiceContractNo.select();
        f.ServiceContractNo.focus();
        return false;
    }
    
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

function AdminWAChallenge_Validation(f) 
{

    if (f.ServiceContractNo.value.search(reEmpty) < 0){
        alert("Please input the Service Contract Reference Number to search for a work assignment!");
        f.ServiceContractNo.select();
        f.ServiceContractNo.focus();
        return false;
    }
    
      
    return true;  
}

function CPARFormClosingDateValidation(f) {
	var today = new Date();
	var closingDate = formatDate(f.ClosingDate.value);

	if (closingDate == 0) {
		alert("The CPAR end date format is incorrect! Please input again!");
		f.ClosingDate.select();
		f.ClosingDate.focus();
		return false;
	}

	if (closingDate.getTime() > today.getTime()) {
		alert("The CPAR end date cannot be future date! Please input again!");
		f.ClosingDate.select();
		f.ClosingDate.focus();
		return false;
	}


	return true;
}

function CPARFormNormalDateValidation(f) {
	var today = new Date();
	var issuedDate = formatDate(f.IssuedDate.value);
	var closingDate = formatDate(f.ClosingDate.value);

	if (f.IssuedDate.value.search(reEmpty) < 0) {
		alert("Please input the CPAR start date!");
		f.IssuedDate.select();
		f.IssuedDate.focus();
		return false;
	}
	if (f.ClosingDate.value.search(reEmpty) < 0) {
		alert("Please input the CPAR end date!");
		f.ClosingDate.select();
		f.ClosingDate.focus();
		return false;
	}
	
	if (issuedDate == 0) {
		alert("The CPAR start date format is incorrect! Please input again!");
		f.ClosingDate.select();
		f.ClosingDate.focus();
		return false;
	}
	
	if (closingDate == 0) {
		alert("The CPAR end date format is incorrect! Please input again!");
		f.ClosingDate.select();
		f.ClosingDate.focus();
		return false;
	}
	
	if (issuedDate.getTime() > closingDate.getTime()) {
		alert("The CPAR end date cannot be earlier than start date! Please input again!");
		f.ClosingDate.select();
		f.ClosingDate.focus();
		return false;
	}
	
	var kickOffDate = formatDate("31-Jul-2013");
	
	if (issuedDate < kickOffDate){
        alert("All CPAR assessment start date in QPSIS must be on or after the inception of QPS3, i.e. 31 Jul 2013.");            
        return false;
    }

	return true;
}


function CPARFormTextAndRadioValidation(f) {
	if (!f.CparStatus[0].checked && 
		!f.CparStatus[1].checked) {
		alert("Please select the CPAR status!");
		f.CparStatus[0].focus();
		return false;
	}
	if (f.AuthorizedPerson.value.search(reEmpty) < 0) {
		alert("Please input the Authorised Person!");
		f.AuthorizedPerson.select();
		f.AuthorizedPerson.focus();
		return false;
	}
	if (f.PostRank.value.search(reEmpty) < 0) {
		alert("Please input the Post/Rank!");
		f.PostRank.select();
		f.PostRank.focus();
		return false;
	}
	
	// Check Table (A) Delivery of Work
	if (!f.table1q1[0].checked && 
		!f.table1q1[1].checked &&
		!f.table1q1[2].checked &&
		!f.table1q1[3].checked) {
		alert("Please select Q1 of the (A) Delivery of Work!");
		f.table1q1[0].focus();
		return false;
	}
	if (!f.table1q2[0].checked && 
			!f.table1q2[1].checked &&
			!f.table1q2[2].checked &&
			!f.table1q2[3].checked) {
		alert("Please select Q2 of the (A) Delivery of Work!");
		f.table1q2[0].focus();
		return false;
	}
	if (!f.table1q3[0].checked && 
			!f.table1q3[1].checked &&
			!f.table1q3[2].checked &&
			!f.table1q3[3].checked) {
		alert("Please select Q3 of the (A) Delivery of Work!");
		f.table1q3[0].focus();
		return false;
	}
	if (!f.table1q4[0].checked && 
			!f.table1q4[1].checked &&
			!f.table1q4[2].checked &&
			!f.table1q4[3].checked) {
		alert("Please select Q4 of the (A) Delivery of Work!");
		f.table1q4[0].focus();
		return false;
	}
	if (!f.table1q5[0].checked && 
			!f.table1q5[1].checked &&
			!f.table1q5[2].checked &&
			!f.table1q5[3].checked) {
		alert("Please select Q5 of the (A) Delivery of Work!");
		f.table1q5[0].focus();
		return false;
	}
	if (!f.table1q6[0].checked && 
			!f.table1q6[1].checked &&
			!f.table1q6[2].checked &&
			!f.table1q6[3].checked) {
		alert("Please select Q6 of the (A) Delivery of Work!");
		f.table1q6[0].focus();
		return false;
	}
	if (!f.table1q7[0].checked && 
			!f.table1q7[1].checked &&
			!f.table1q7[2].checked &&
			!f.table1q7[3].checked) {
		alert("Please select Q7 of the (A) Delivery of Work!");
		f.table1q7[0].focus();
		return false;
	}
	if (!f.table1q8[0].checked && 
			!f.table1q8[1].checked &&
			!f.table1q8[2].checked &&
			!f.table1q8[3].checked) {
		alert("Please select Q8 of the (A) Delivery of Work!");
		f.table1q8[0].focus();
		return false;
	}
	if (!f.table1q9[0].checked && 
			!f.table1q9[1].checked &&
			!f.table1q9[2].checked &&
			!f.table1q9[3].checked) {
		alert("Please select Q9 of the (A) Delivery of Work!");
		f.table1q9[0].focus();
		return false;
	}
	
	// Check Table (B) Quality of Work
	if (!f.table2q1[0].checked && 
		!f.table2q1[1].checked &&
		!f.table2q1[2].checked &&
		!f.table2q1[3].checked) {
		alert("Please select Q1 of the (B) Quality of Work!");
		f.table2q1[0].focus();
		return false;
	}
	if (!f.table2q2[0].checked && 
			!f.table2q2[1].checked &&
			!f.table2q2[2].checked &&
			!f.table2q2[3].checked) {
		alert("Please select Q2 of the (B) Quality of Work!");
		f.table2q2[0].focus();
		return false;
	}
	if (!f.table2q3[0].checked && 
			!f.table2q3[1].checked &&
			!f.table2q3[2].checked &&
			!f.table2q3[3].checked) {
		alert("Please select Q3 of the (B) Quality of Work!");
		f.table2q3[0].focus();
		return false;
	}
	if (!f.table2q4[0].checked && 
			!f.table2q4[1].checked &&
			!f.table2q4[2].checked &&
			!f.table2q4[3].checked) {
		alert("Please select Q4 of the (B) Quality of Work!");
		f.table2q4[0].focus();
		return false;
	}
	if (!f.table2q5[0].checked && 
			!f.table2q5[1].checked &&
			!f.table2q5[2].checked &&
			!f.table2q5[3].checked) {
		alert("Please select Q5 of the (B) Quality of Work!");
		f.table2q5[0].focus();
		return false;
	}
	
	// Check Table (C) Managing of Resources
	if (!f.table3q1[0].checked && 
		!f.table3q1[1].checked &&
		!f.table3q1[2].checked &&
		!f.table3q1[3].checked) {
		alert("Please select Q1 of the (C) Managing of Resources!");
		f.table3q1[0].focus();
		return false;
	}
	if (!f.table3q2[0].checked && 
			!f.table3q2[1].checked &&
			!f.table3q2[2].checked &&
			!f.table3q2[3].checked) {
		alert("Please select Q2 of the (C) Managing of Resources!");
		f.table3q2[0].focus();
		return false;
	}
	if (!f.table3q3[0].checked && 
			!f.table3q3[1].checked &&
			!f.table3q3[2].checked &&
			!f.table3q3[3].checked) {
		alert("Please select Q3 of the (C) Managing of Resources!");
		f.table3q3[0].focus();
		return false;
	}
	if (!f.table3q4[0].checked && 
			!f.table3q4[1].checked &&
			!f.table3q4[2].checked &&
			!f.table3q4[3].checked) {
		alert("Please select Q4 of the (C) Managing of Resources!");
		f.table3q4[0].focus();
		return false;
	}
	if (!f.table3q5[0].checked && 
			!f.table3q5[1].checked &&
			!f.table3q5[2].checked &&
			!f.table3q5[3].checked) {
		alert("Please select Q5 of the (C) Managing of Resources!");
		f.table3q5[0].focus();
		return false;
	}
	
	// Check Table (D) Overall Assessment
	if (!f.table4q1[0].checked && 
		!f.table4q1[1].checked &&
		!f.table4q1[2].checked &&
		!f.table4q1[3].checked) {
		alert("Please select Q1 of the (D) Overall Assessment!");
		f.table4q1[0].focus();
		return false;
	}
	if (!f.table4q2[0].checked && 
			!f.table4q2[1].checked &&
			!f.table4q2[2].checked &&
			!f.table4q2[3].checked) {
		alert("Please select Q2 of the (D) Overall Assessment!");
		f.table4q2[0].focus();
		return false;
	}
	if (!f.table4q3[0].checked && 
			!f.table4q3[1].checked &&
			!f.table4q3[2].checked &&
			!f.table4q3[3].checked) {
		alert("Please select Q3 of the (D) Overall Assessment!");
		f.table4q3[0].focus();
		return false;
	}
	

	return true;
}

function validateCR(f) {

      // regular expression for checking pdf filename
      // rePDF = new RegExp("^[a-zA-Z]:\\","i");


      // check for empty
        if (f.EffectiveDate.value.search(reEmpty) < 0){
            alert("Please input the effective date!");
            f.EffectiveDate.select();
            f.EffectiveDate.focus();
            return false;
        }

         // check date format
         if (f.EffectiveDate.value.search(reDateFormat) < 0){
               alert("Invalid date format for effective date!");
                f.EffectiveDate.select();
                f.EffectiveDate.focus();
                return false;
         }

        if (! f.ServiceCategory[0].checked && 
            ! f.ServiceCategory[1].checked && 
            ! f.ServiceCategory[2].checked && 
            ! f.ServiceCategory[3].checked){
            alert("Please select the Service Category!");
            f.ServiceCategory[0].focus();
            return false;
        }
        if (! f.PublishInd[0].checked &&
            ! f.PublishInd[1].checked ){
            alert("Please select the release for publishing indicator!");
            f.PublishInd[0].focus();
          return false;
        }

         if (f.UploadFileName.value.search(reEmpty) < 0){
            alert("Please input the upload file name!");
            f.UploadFileName.select();
            f.UploadFileName.focus();
            return false;
        }

/*
         if (f.UploadFileName.value.search(rePDF) < 0){
            alert("Invalid file!");
            f.UploadFileName.select();
            f.UploadFileName.focus();
            return false;
        } 
*/
        return true;
}

function WAChallenge_BtnSearchOnClick(obj)
{
    var f = document.form1;

    if (WAChallenge_Validation(f)) 
    {
        f.action = "WAChallengeValidation.servlet";
        f.target = "_self";
        f.submit();
    }

}

function AdminWAChallenge_BtnSearchOnClick(obj)
{
    var f = document.form1;

    if (AdminWAChallenge_Validation(f)) 
    {
        f.action = "WAChallengeValidation.servlet";
        f.target = "_self";
        f.submit();
    }

}

function createCpar(f,obj,key1){
    	f.cparNo.value=key1;
        f.action="CPARCreate.servlet";
        f.submit();
}

function selectaction(f,obj,key1){
    fsubmit=false;    
    if (obj.name =="Delete"){
        f.action="CPARDelete.servlet";
        f.cparNo.value=key1;
        fsubmit=true;        
    }
    if (obj.name =="Update"){
        f.action="CPARUpdate.servlet";
        f.cparNo.value=key1;
        fsubmit=true;        
    }
    if (obj.name =="Save"){        
        if ( CPARFormNormalDateValidation(f) && CPARFormClosingDateValidation(f)) {
        	alert("Your CPAR has been saved and you may modify it later.");
            f.cparNo.value=key1;
            f.finalized.value="s";
            f.action="CPARSave.servlet";
            fsubmit=true;
        } else {
        	fsubmit=false;
        }
    }
    if (obj.name =="AdminSave"){        
        if (CPARFormNormalDateValidation(f) && CPARFormClosingDateValidation(f) ){
        	alert("Your CPAR has been saved and you may modify it later.");
            f.cparNo.value=key1;
            f.action="CPARSave.servlet";
            fsubmit=true;
        } else {
        	fsubmit=false;
        }
    }
	if (obj.name == "Finalize") {
		if ( CPARFormTextAndRadioValidation(f) && CPARFormNormalDateValidation(f) && CPARFormClosingDateValidation(f) ){
			if (confirm("You are finalizing a CPAR. Once finalized, it will be used for CPS calculation and no further modification can be made. Do you want to finalize?")) {

				f.cparNo.value = key1;
				f.finalized.value = "f";
				f.action = "CPARSave.servlet";
				fsubmit = true;

			} else {
				fsubmit = false;
			}
		} else {
			fsubmit = false;
		}
	}
	if (obj.name == "ReleaseInUpdatePage") {
		if ( CPARFormTextAndRadioValidation(f) && CPARFormNormalDateValidation(f) && CPARFormClosingDateValidation(f) ){
			if (confirm("You are releasing a CPAR and it will be released for CPS calculation. Do you want to release?")) {
				f.cparNo.value = key1;
				f.finalized.value = "r";
				f.action = "CPARSave.servlet";
				fsubmit = true;
			} else {
				fsubmit = false;
			}
		} else {
			fsubmit = false;
		}
	}
	if (obj.name == "ReleaseInListPage") {
		if (confirm("You are releasing a CPAR and it will be released for CPS calculation. Do you want to release?")) {
			f.cparNo.value = key1;
			f.action = "CPARReleaseInList.servlet";
			fsubmit = true;
		} else {
			fsubmit = false;
		}
	}
	
	if (obj.name == "UnreleaseInListPage") {
		if (confirm("You are unreleasing a CPAR. Do you want to unrelease?")) {
			f.cparNo.value = key1;
			f.action = "CPARUnreleaseInList.servlet";
			fsubmit = true;
		} else {
			fsubmit = false;
		}
	}
    if (obj.name =="Cancel" || obj.name== "Return"){         
        f.action=f.PostScreen.value;
        fsubmit=true;
    }
    if (obj.name =="Print"){
    	f.cparNo.value=key1;
        f.action="CPARPrint.servlet";     
        fsubmit=true;
    }
    if (obj.name =="History"){
    	f.cparNo.value=key1;
        f.action="CPARHistory.servlet";     
        fsubmit=true;
    }
    if (obj.name =="ConfirmDelete"){
         if (confirm("Are you sure you want to delete CPAR?")) {
             fsubmit=true;
             f.cparNo.value=key1;
             f.action="CPARConfirmDelete.servlet";
        }else{
             fsubmit=false;
        }        
    }

    if (fsubmit) f.submit();
    return;
}


function changeorder(f,order,odir){    
    f.OrderBy.value =order;
    f.OrderDir.value =odir;
    f.submit();
    return;
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