function validateDate(f) {

       // regular expresssion for checking of empty string
      reEmpty = new RegExp("[a-zA-Z.0-9]+");
      reAlphaNum = new RegExp("^([0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+)[0-9a-zA-Z]*$");
      reEmail = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

        // check Invitation date
        if ((f.InvitationStartDate.value.search(reEmpty) >= 0)&&(f.InvitationEndDate.value.search(reEmpty) >= 0)){
            
        	var invitationStart = formatDate(f.InvitationStartDate.value);
        	var invitationEnd = formatDate(f.InvitationEndDate.value);
            
        	 if (invitationEnd < invitationStart){
                 alert("The date range you entered is invalid, please input again!");        
                 return false;
             }
        }

        // check Award date
        if ((f.AwardStartDate.value.search(reEmpty) >= 0)&&(f.AwardEndDate.value.search(reEmpty) >= 0)){
            
        	var awardStart = formatDate(f.AwardStartDate.value);
        	var awardEnd = formatDate(f.AwardEndDate.value);
            
        	 if (awardEnd < awardStart){
                 alert("The date range you entered is invalid, please input again!");        
                 return false;
             }
        }

        return true;
}


function statselectaction(f,obj){
    fsubmit=false;    

    if (obj.name  =="Search"){
        f.action="StatSearch.servlet";
        fsubmit = validateDate(f);
    }
    if (obj.name  =="SearchReset"){
        f.action="StatSearchReset.servlet";
        fsubmit=true;
    }

    if (fsubmit) f.submit();
    return;
}

function DownloadFile(form,obj){   
	fsubmit=false;    

    if (obj.name  =="Download"){
    	form.action = "StatDownloadReport.servlet";
    	form.value = "getPDF";
    	form.target = "_self";
        fsubmit = true;
    }

    if (fsubmit) form.submit();
    return;
}


function formatDate(inputDate){
    var mArray = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
    splitDate = inputDate.split("-");
    fDay = splitDate[0];
    for (i=0;i<12;i++){
        if (splitDate[1] == mArray[i]){
            fMonth = i;
            break;
        }        
    }
    fYear = splitDate[2];    
    returnDate = new Date(fYear+"/"+(fMonth+1)+"/"+fDay);  
    return returnDate;
}
