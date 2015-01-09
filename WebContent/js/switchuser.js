function validateDept(f) {

// check for selected dept 
    
    if (f.DeptId.value ==""){
       alert("Please select the Department name!");            
       f.DeptId.focus();       
        return false;    
    }
    return true;
}


function selectaction(f,obj){    
    fsubmit=false;   
    if (obj.name =="Continue"){                
        f.action="SwitchUser.servlet";
        fsubmit=validateDept(f);        
    }
    if (obj.name =="Cancel"){        
        f.action="index.jsp";
        fsubmit=true;
    }
    if (fsubmit) f.submit();
    return;
}

