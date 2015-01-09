
function selectaction(f,obj){    
    fsubmit=false;   
    if (obj.name =="Search"){        
        f.action="SRVLogEnquiry.servlet";
        fsubmit=true;
    }
    if (obj.name =="SearchReset"){        
        f.action="SRVLogEnquiryReset.servlet";
        fsubmit=true;
    }
    if (fsubmit) f.submit();
    return;
}

function changeorder(f,order,odir){    

    f.action="SRVLogEnquiry.jsp";
    f.OrderBy.value =order;
    f.OrderDir.value =odir;
    f.submit();
    return;
}

function changepage(f){    

    f.action="SRVLogEnquiry.jsp";   
    f.StartRec.value =(parseInt(f.GotoPage.value)-1)*parseInt(f.PageSize.value)+1;    
    f.submit();
    return;
}

function shiftpage(f,pageno){    

    f.action="SRVLogEnquiry.jsp";   
    f.StartRec.value =(pageno-1)*parseInt(f.PageSize.value)+1;    
    f.submit();
    return;
}

function changepagesize(f){    

    f.action="SRVLogEnquiry.jsp";   
    f.PageSize.value =(parseInt(f.SelectPageSize.value));    
    f.StartRec.value = 1;
    f.submit();
    return;
}
