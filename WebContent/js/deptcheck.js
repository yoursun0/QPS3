function validateDept(f) {

       reEmpty = new RegExp("[a-zA-Z.0-9]+");  // regular expression for empty string
    // check dp department id 
	if (f.DPDeptId.value.search(reEmpty) < 0){
            alert("Please input the Department ID for DP!");
            f.DPDeptId.select();
            f.DPDeptId.focus();
            return false;
        }
    // check SOA-QPS department id 
	if (f.SOADeptId.value.search(reEmpty) < 0){
            alert("Please input the Department ID for SOA-QPS!");
            f.SOADeptId.select();
            f.SOADeptId.focus();
            return false;
        }

    // check contractor name 
	if (f.DeptName.value.search(reEmpty) < 0){
            alert("Please input the Department Name!");
            f.DeptName.select();
            f.DeptName.focus();
            return false;
        }
        return true;
}

function changepage(location){
    if (location=="Search"){
        window.location = "DeptSearch.jsp";
         return;
    }
    if (location=="List"){
        window.location = "DeptList.jsp";
        return;
    }
    if (location=="Add"){
        window.location = "DeptAdd.jsp";
        return;
    }
}

function selectaction(f,obj,key1,key2){
    fsubmit=false;    
    if (obj.name =="Delete"){
        f.action="DeptDelete.jsp";
        f.selectedKey1.value=key1;        
        f.selectedKey2.value=key2;     
        fsubmit=true;        
    }
    if (obj.name =="Cancel"){
        f.action = f.PostScreen.value; 
        fsubmit=true;
    }
    if (obj.name =="Add"){
        f.action="DeptAdd.jsp";
        fsubmit=true;
    }
    if (obj.name =="Update"){
        f.action="DeptUpdate.jsp";
        f.selectedKey1.value=key1;        
        f.selectedKey2.value=key2;     
        fsubmit=true;
    }
    if (obj.name =="ConfirmAdd"){
        f.action="DeptAdd.servlet";
        fsubmit = validateDept(f);
    }
    if (obj.name =="ConfirmUpdate"){
        f.action="DeptUpdate.servlet";
        fsubmit = validateDept(f);
    }
    if (obj.name =="ConfirmDelete"){
         if (confirm("Are you sure you want to delete?")) {
             fsubmit=true;
             f.action="DeptDelete.servlet";
        }else{
             fsubmit=false;
        }        
    }    
    if (obj.name =="Search"){        
        f.action="DeptSearch.servlet";
        fsubmit=true;
    }
    if (obj.name =="SearchReset"){        
        f.action="DeptSearchReset.servlet";
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
