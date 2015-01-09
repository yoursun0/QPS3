function validateACL(f,flag) {

       // regular expresssion for checking of empty string
      reEmpty = new RegExp("[a-zA-Z.0-9]+");

      if (flag == "add"){
        // check user id
	if (f.UserId.value.search(reEmpty) < 0){
            alert("Please input the User ID!");
            f.UserId.select();
            f.UserId.focus();
            return false;
        }
        // check department
        if (f.DeptId.value==0){
            alert("Please select the Bureau/Department!");
            f.DeptId.focus();
          return false;
        }
    }
        // check effective date
        if (f.EffectiveDate.value.search(reEmpty) < 0){
            alert("Please input the Effective Date!");
             f.EffectiveDate.select();
             f.EffectiveDate.focus();
             return false;
        }

        // check if any checed expiry date

        if (! f.ExpiryDate[0].checked &&
            ! f.ExpiryDate[1].checked){
            alert("Please select the Expiry Date!");            
            f.ExpiryDate[0].focus();
            return false;    
        }
    
        // check if expiry date is earlier than effective date 
        effectiveDate = formatDate(f.EffectiveDate.value);

        if (f.ExpiryDate[0].checked){
            expiryDate = formatDate(f.ExpiryDate[0].value);
        }else if (f.ExpiryDate[1].checked){
            expiryDate = formatDate(f.ExpiryDate[1].value);
        }                      

        if (expiryDate < effectiveDate){
            alert("Expiry Date cannot be earlier than Effective Date!");
            f.EffectiveDate.select();
            f.EffectiveDate.focus();                
            return false;
        }

        // check if no file selected

        
        return true;
}

function validateACLImport(f) {

// regular expresssion for checking of empty string
      reEmpty = new RegExp("[a-zA-Z.0-9]+");

// check for selected dept 
    
    if (f.DeptId.value ==""){
       alert("Please select the Department name!");            
       f.DeptId.focus();       
        return false;    
    }

    if (f.ImportFileName.value.search(reEmpty) < 0){
            alert("Please input the import file name!");
            f.ImportFileName.select();
            f.ImportFileName.focus();
            return false;
        }

    return true;
}

function formatDate(inputDate){
    var mArray = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
    splitDate = inputDate.split("-");
    fDay = splitDate[0];
    for (i=0;i<12;i++){
        if (splitDate[1] == mArray[i]){
            fMonth = i;
            break;
        }        
    }
    fYear = splitDate[2];    
    returnDate = new Date(fYear+"/"+(fMonth+1)+"/"+fDay);  
    return returnDate;
}

function changepage(location){
    if (location=="Search"){
        window.location = "ACLSearch.jsp";
         return;
    }
    if (location=="List"){
        window.location = "ACLList.jsp";
        return;
    }
    if (location=="Add"){
        window.location = "ACLAdd.jsp";
        return;
    }
    if (location=="Import"){
        window.location = "ACLImport.jsp";
        return;
    }
}

function selectaction(f,obj,key1,key2,key3){    
    fsubmit=false;   
    if (obj.name =="Delete"){        
        f.action="ACLDelete.jsp";
        f.selectedKey1.value=key1;
        f.selectedKey2.value=key2;
        f.selectedKey3.value=key3;
        fsubmit=true;        
    }
    if (obj.name =="Cancel" || obj.name=="Return"){        
        f.action = f.PostScreen.value;        
        fsubmit=true;
    }
    if (obj.name=="Return"){        
        f.action = "ACLList.jsp";     
        fsubmit=true;
    }

    if (obj.name =="Add"){        
        f.action="ACLAdd.jsp";
        fsubmit=true;
    }
    if (obj.name =="Search"){        
        f.action="ACLSearch.servlet";
        fsubmit=true;
    }
    if (obj.name =="SearchReset"){        
        f.action="ACLSearchReset.servlet";
        fsubmit=true;
    }
    if (obj.name =="Update"){        
        f.action="ACLUpdate.jsp";
        f.selectedKey1.value=key1;
        f.selectedKey2.value=key2;
        f.selectedKey3.value=key3;
        fsubmit=true;
    }
    if (obj.name =="ConfirmAdd"){
        f.action="ACLAdd.servlet";
        fsubmit = validateACL(f,"add");
    }
    if (obj.name =="ConfirmUpdate"){
        f.action="ACLUpdate.servlet";
        fsubmit = validateACL(f,"update");
    }
    if (obj.name =="ConfirmDelete"){
         if (confirm("Are you sure?")) {
             fsubmit=true;
             f.action="ACLDelete.servlet";
        }else{
             fsubmit=false;
        }        
    }
    if (obj.name =="ConfirmImport"){
        f.action="ACLImport.servlet";        
        fsubmit=validateACLImport(f);
    }
    if (obj.name =="UnlockUser"){        
        f.action="UnlockSingleUser.servlet";
        fsubmit = validateACL(f,"unlock");        
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
