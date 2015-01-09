function validateDate(f) {

      // regular expresssion for checking of empty string
      reEmpty = new RegExp("[a-zA-Z.0-9]+");
      
      // check cut off date
      if (f.CutOffDate.value.search(reEmpty) >= 0){
          // check if expiry date is earlier than effective date 
          cutOffDate = formatDate(f.CutOffDate.value);
          kickOffDate = formatDate("31-Jul-2013");

          if (cutOffDate < kickOffDate){
              alert("Please enter the cut-off date which must be on or after the inception of QPS3, i.e. 31 Jul 2013.");            
              return false;
          }
      }

      return true;
}

function cpsSelectaction(f,obj){
    fsubmit=false;    

    if (obj.name  =="Search"){
        f.action="CPSSearch.servlet";
        fsubmit = validateDate(f);
    }
    if (obj.name  =="SearchReset"){
        f.action="CPSSearchReset.servlet";
        fsubmit=true;
    }

    if (fsubmit) f.submit();
    return;
}

function DownloadFile(form,obj){   
	fsubmit=false;    

    if (obj.name  =="Download"){
    	form.action = "CPSDownloadReport.servlet";
    	//form.value = "getPDF";
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