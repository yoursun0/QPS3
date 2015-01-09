function validateDate(f) {

    // regular expresssion for checking of empty string
    reEmpty = new RegExp("[a-zA-Z.0-9]+");
    
    // check cut off date
    if (f.CutOffStartDate.value.search(reEmpty) >= 0){
        // check if expiry date is earlier than effective date 
        cutOffStartDate = formatDate(f.CutOffStartDate.value);
        kickOffDate = formatDate("31-Jul-2013");
        
        if (cutOffStartDate == 0){
            alert("The cut-off date has invalid date format.");            
            return false;
        }
        
        if (cutOffStartDate < kickOffDate){
            alert("Please enter the cut-off date which must be on or after the inception of QPS3, i.e. 31 Jul 2013.");            
            return false;
        }
        
        if (f.CutOffEndDate.value.search(reEmpty) >= 0){
            // check if expiry date is earlier than effective date 
            cutOffEndDate = formatDate(f.CutOffEndDate.value);

            if (cutOffEndDate < cutOffStartDate){
                alert("The input Cut-Off date range is invalid. Please enter again!");            
                return false;
            }
        }
    }

    return true;
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

function CPARSelectaction(f,obj){
    fsubmit=false;    

    if (obj.name  =="Search"){
        f.action="CPARRecordSearch.servlet";
        fsubmit = validateDate(f);
        if (fsubmit) _ajaxLoadWithData("CPARRecordSearch.servlet", {Category:$("#Category").val(), ContractorName:$("#ContractorName").val(), ContractorId:$("#ContractorId").val(), CutOffStartDate:$("#CutOffStartDate").val(), CutOffEndDate:$("#CutOffEndDate").val(), CPARState:$("#CPARState").val()}, "#search_results");
    }
    
    if (obj.name  =="SearchReset"){
        f.action="CPARRecordSearchReset.servlet";
        fsubmit=true;
        if (fsubmit) f.submit();
    }


    return;
}

function DownloadFile(form,obj){   
	fsubmit=false;    

    if (obj.name  =="DownloadCPARsOutstanding"){
    	form.action = "CPAROutstandingDownload.servlet";
    	//form.value = "getPDF";
    	form.target = "_self";
        fsubmit = true;
    }
    
    if (obj.name  =="DownloadCPARsRecord"){
    	form.action = "CPARRecordDownload.servlet";
    	//form.value = "getPDF";
    	form.target = "_self";
        fsubmit = true;
    }

    if (fsubmit) form.submit();
    return;
}

function _ajaxLoadWithData(inUrl, inData, inSelector) {
	$.ajax({
		type: "POST",
		cache: false,
		url: inUrl,
		//data: inData,
		data: inData,
		success: function(data) {
			$(inSelector).html(data);
		},
		error: function(xhr, type, exception){ 
			//alert("Ajax Error: " + xhr.statusText+exception);
		} 
	});
	return false;
}

