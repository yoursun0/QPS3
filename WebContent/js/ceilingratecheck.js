function validateCR(f) {

       // regular expresssion for checking of empty string
      reEmpty = new RegExp("[a-zA-Z.0-9]+");

      // regular expression for checking date format
      reDateFormat = new RegExp("[0-9]{1,2}-[a-zA-Z]{3}-[0-9]{4}","i");

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

function changepage(location){
    if (location=="List"){
        window.location = "CeilingRateList.jsp";
        return;
    }
    if (location=="Import"){
        window.location = "CeilingRateUpload.jsp";
        return;
    }
    if (location=="Search"){
        window.location = "CeilingRateSearch.jsp";
        return;
    }
}

function selectaction(f,obj,key1,key2){
    fsubmit=false;    
    if (obj.name =="Delete"){
        f.action="CeilingRateDelete.jsp";
        f.selectedKey1.value=key1;
        f.selectedKey2.value=key2;
        fsubmit=true;        
    }
    if (obj.name =="Release"){
         if (confirm("Are you sure you want to release?")) {
            f.selectedKey1.value=key1;
            f.selectedKey2.value=key2;
            f.action="CeilingRateRelease.servlet";     
            fsubmit=true;
        }else{
             fsubmit=false;
        }         
        fsubmit=true;        
    }
    if (obj.name =="Cancel" || obj.name== "Return"){         
        f.action=f.PostScreen.value;
        fsubmit=true;
    }
    if (obj.name =="Continue"){
        new_window("CeilingRatePrintReport.servlet");        
        f.action="CeilingRateList.jsp";
        fsubmit=true;
    }
    if (obj.name =="Print"){
        new_window("CeilingRateFile.jsp?selectedKey1="+key1+"&selectedKey2="+key2);              
        fsubmit=false;
    }
    if (obj.name =="Import"){
        f.action="CeilingRateUpload.jsp";
        fsubmit=true;
    }
    if (obj.name =="ConfirmImport"){
        f.action="CeilingRateUpload.servlet";
        fsubmit = validateCR(f);
    }
    if (obj.name =="ConfirmDelete"){
         if (confirm("Are you sure you want to delete?")) {
             fsubmit=true;
             f.action="CeilingRateDelete.servlet";
        }else{
             fsubmit=false;
        }        
    }
        if (obj.name =="Search"){        
        f.action="CeilingRateSearch.servlet";
        fsubmit=true;
    }
    if (obj.name =="SearchReset"){        
        f.action="CeilingRateSearchReset.servlet";
        fsubmit=true;
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
