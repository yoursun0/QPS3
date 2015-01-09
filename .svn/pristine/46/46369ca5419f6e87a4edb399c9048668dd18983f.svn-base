function validateACL(f) {
    i =0;
    selected = false;
    while (document.all("ChkEdit"+i) != null){
        if (document.all("ChkEdit"+i).checked){
            selected= true;          
        }
        i++;
    }
    if (selected){
        return true;
    }else{
        return false;
    }
}

function selectAll(){
    i =0;
    while (document.all("ChkEdit"+i) != null){
        document.all("ChkEdit"+i).checked = true;
        i++;
    }
}

function unselectAll(){
    i =0;
    while (document.all("ChkEdit"+i) != null){
        document.all("ChkEdit"+i).checked = false;
        i++;
    }
}

function selectaction(f,obj){    
    fsubmit=false;   
    if (obj.name =="UnlockUser"){        
        f.action="UnlockUser.servlet";
        fsubmit = validateACL(f);        
    }
    if (obj.name =="Cancel"){        
        f.action="ACLList.jsp";
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
