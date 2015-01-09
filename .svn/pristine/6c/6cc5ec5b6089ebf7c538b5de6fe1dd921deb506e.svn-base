
function selectaction(f,obj){    
    fsubmit=false;   
    if (obj.name =="Search"){        
        f.action="UserLogEnquiry.servlet";
        fsubmit=true;
    }
    if (obj.name =="SearchReset"){        
        f.action="UserLogEnquiryReset.servlet";
        fsubmit=true;
    }
    if (fsubmit) f.submit();
    return;
}

function changeorder(f,order,odir){    

    f.action="UserLogEnquiry.jsp";
    f.OrderBy.value =order;
    f.OrderDir.value =odir;
    f.submit();
    return;
}

function changepage(f){    

    f.action="UserLogEnquiry.jsp";   
    f.StartRec.value =(parseInt(f.GotoPage.value)-1)*parseInt(f.PageSize.value)+1;    
    f.submit();
    return;
}

function shiftpage(f,pageno){    

    f.action="UserLogEnquiry.jsp";   
    f.StartRec.value =(pageno-1)*parseInt(f.PageSize.value)+1;    
    f.submit();
    return;
}

function changepagesize(f){    

    f.action="UserLogEnquiry.jsp";   
    f.PageSize.value =(parseInt(f.SelectPageSize.value));    
    f.StartRec.value = 1;
    f.submit();
    return;
}