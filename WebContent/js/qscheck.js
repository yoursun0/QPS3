function validateQS(f) {

       // regular expresssion for checking of empty string
      reEmpty = new RegExp("[a-zA-Z.0-9]+");

      // check for empty

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
        window.location = "CPSList.jsp";
        return;
    }
    if (location=="Import"){
        window.location = "CPSUpload.jsp";
        return;
    }
}

function selectaction(f,obj,key){
    fsubmit=false;  
    if (obj.name =="Delete"){
        f.action="CPSDelete.jsp";
        f.selectedKey1.value=key;
        fsubmit=true;        
    }
    if (obj.name =="Cancel"){
        f.action="CPSList.jsp";
        fsubmit=true;
    }
    if (obj.name =="Import"){
        f.action="CPSUpload.jsp";
        fsubmit=true;
    }
    if (obj.name =="ConfirmImport"){
        f.action="CPSUpload.servlet";
        fsubmit = validateQS(f);
    }
    if (obj.name =="ConfirmDelete"){
         if (confirm("Are you sure you want to delete?")) {
             fsubmit=true;
             f.action="CPSDelete.servlet";
        }else{
             fsubmit=false;
        }
    }    
    if (obj.name =="Print"){
        new_window("CPSFile.jsp?selectedKey1="+key);              
        fsubmit=true;        
    }
    if (fsubmit) f.submit();
    return;
}
