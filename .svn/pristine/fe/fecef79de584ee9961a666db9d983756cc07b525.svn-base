function validateContractor(f) {

       reEmpty = new RegExp("[a-zA-Z.0-9]+");  // regular expression for empty string
    // check contractor id 
	if (f.ContractorId.value.search(reEmpty) < 0){
            alert("Please input the Contractor ID!");
            f.ContractorId.select();
            f.ContractorId.focus();
            return false;
        }
    // check contractor name 
	if (f.ContractorName.value.search(reEmpty) < 0){
            alert("Please input the Contractor Name!");
            f.ContractorName.select();
            f.ContractorName.focus();
            return false;
        }
        return true;
}

function changepage(location){
    if (location=="List"){
        window.location = "ContractorList.jsp";
        return;
    }
    if (location=="Add"){
        window.location = "ContractorAdd.jsp";
        return;
    }
}

function selectaction(f,obj,key1){
    fsubmit=false;    
    if (obj.name =="Delete"){
        f.action="ContractorDelete.jsp";
        f.selectedKey1.value=key1;        
        fsubmit=true;        
    }
    if (obj.name =="Cancel"){
        f.action="ContractorList.jsp";
        fsubmit=true;
    }
    if (obj.name =="Add"){
        f.action="ContractorAdd.jsp";
        fsubmit=true;
    }
    if (obj.name =="Update"){
        f.action="ContractorUpdate.jsp";
        f.selectedKey1.value=key1;        
        fsubmit=true;
    }
    if (obj.name =="ConfirmAdd"){
        f.action="ContractorAdd.servlet";
        fsubmit = validateContractor(f);
    }
    if (obj.name =="ConfirmUpdate"){
        f.action="ContractorUpdate.servlet";
        fsubmit = validateContractor(f);
    }
    if (obj.name =="ConfirmDelete"){
         if (confirm("Are you sure you want to delete?")) {
             fsubmit=true;
             f.action="ContractorDelete.servlet";
        }else{
             fsubmit=false;
        }        
    }    
    if (fsubmit) f.submit();
    return;
}

function changeorder(f,order,odir){    

    f.action="ContractorList.jsp";
    f.OrderBy.value =order;
    f.OrderDir.value =odir;
    f.submit();
    return;
}
