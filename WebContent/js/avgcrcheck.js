function validateAvgCR(f) {

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

        if (f.ServiceCategory.value==0){
            alert("Please select the Service Category!");
            f.ServiceCategory.focus();
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
        window.location = "AvgCeilingRateList.jsp";
        return;
    }
    if (location=="Upload"){
        window.location = "AvgCeilingRateUpload.jsp";
        return;
    }
    if (location=="Search"){
        window.location = "AvgCeilingRateSearch.jsp";
        return;
    }
}

function selectaction(f,obj,key1,key2){
    fsubmit=false;    
    if (obj.name =="Delete"){
        f.action="AvgCeilingRateDelete.jsp";
        f.selectedKey1.value=key1;
        f.selectedKey2.value=key2;
        fsubmit=true;        
    }
    if (obj.name =="Cancel"){

        f.action = f.PostScreen.value
        fsubmit=true;
    }

    if (obj.name =="Release"){
         if (confirm("Are you sure you want to release?")) {
            f.selectedKey1.value=key1;
            f.selectedKey2.value=key2;
            f.action="AvgCeilingRateRelease.servlet";     
            fsubmit=true;
        }else{
             fsubmit=false;
        }         
        fsubmit=true;        
    }

    if (obj.name =="Upload"){
        f.action="AvgCeilingRateUpload.jsp";
        fsubmit=true;
    }
    if (obj.name =="ConfirmUpload"){
        f.action="AvgCeilingRateUpload.servlet";
        fsubmit = validateAvgCR(f);
    }
    if (obj.name =="ConfirmDelete"){
         if (confirm("Are you sure you want to delete?")) {
             fsubmit=true;
             f.action="AvgCeilingRateDelete.servlet";
        }else{
             fsubmit=false;
        }        
    }    
    if (obj.name =="Search"){        
        f.action="AvgCeilingRateSearch.servlet";
        fsubmit=true;
    }
    if (obj.name =="SearchReset"){        
        f.action="AvgCeilingRateSearchReset.servlet";
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

