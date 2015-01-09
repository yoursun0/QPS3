
function selectaction(f,obj){    
    fsubmit=false;   
    if (obj.name =="Search"){        
        f.action="AdminLogEnquiry.servlet";
        fsubmit=true;
    }
    if (obj.name =="SearchReset"){        
        f.action="AdminLogEnquiryReset.servlet";
        fsubmit=true;
    }
    if (fsubmit) f.submit();
    return;
}


function changeorder(f,order,odir){    

    f.action="AdminLogEnquiry.jsp";
    f.OrderBy.value =order;
    f.OrderDir.value =odir;
    f.submit();
    return;
}

function selectAll(f){
    i =0;
    while (f.Activity[i] != null){
        f.Activity[i].checked = true;
        i++;
    }
}

function unselectAll(f){
    i =0;
    while (f.Activity[i] != null){
        f.Activity[i].checked  = false;
        i++;
    }
}

function changepage(f){    

    f.action="AdminLogEnquiry.jsp";   
    f.StartRec.value =(parseInt(f.GotoPage.value)-1)*parseInt(f.PageSize.value)+1;    
    f.submit();
    return;
}

function shiftpage(f,pageno){    

    f.action="AdminLogEnquiry.jsp";   
    f.StartRec.value =(pageno-1)*parseInt(f.PageSize.value)+1;    
    f.submit();
    return;
}

function changepagesize(f){    

    f.action="AdminLogEnquiry.jsp";   
    f.PageSize.value =(parseInt(f.SelectPageSize.value));    
    f.StartRec.value = 1;
    f.submit();
    return;
}