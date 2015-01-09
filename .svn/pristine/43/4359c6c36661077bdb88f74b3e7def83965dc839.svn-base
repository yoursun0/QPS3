function validateACL(f,flag) {

       // regular expresssion for checking of empty string
      reEmpty = new RegExp("[a-zA-Z.0-9]+");
      reAlphaNum = new RegExp("^([0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+)[0-9a-zA-Z]*$");
      reEmail = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

      if (flag == "add"){
        // check user id
	    if (f.UserId.value.search(reEmpty) < 0){
            alert("Please input the User ID!");
            f.UserId.select();
            f.UserId.focus();
            return false;
        }
	    
	      // check firstname
	      if (f.FirstName.value.search(reEmpty) < 0){
	          alert("Please input the First Name!");
	          f.FirstName.select();
	          f.FirstName.focus();
	          return false;
	      }

	      // check lastname
	      if (f.LastName.value.search(reEmpty) < 0){
	          alert("Please input the Last Name!");
	          f.LastName.select();
	          f.LastName.focus();
	          return false;
	      }

	      // check email
	      if ((f.Email.value.search(reEmpty) <0)||(!f.Email.value.match(reEmail))){
	          alert("Please input a valid Email Address!");
	          f.Email.focus();
	        return false;
	      }

	      // check department
	      if (f.DeptId.value.search(reEmpty) <0){
	          alert("Please select the Bureau/Department!");
	          f.DeptId.focus();
	        return false;
	      }

	      // check password
	      if (f.Password.value.search(reEmpty) < 0){
	          alert("Please input the password!");
	          f.Password.select();
	          f.Password.focus();
	          return false;
	      }
    }

      // check password
      if (f.Password.value.search(reEmpty) >= 0){
      
    	  // check password characters
    	  if (!f.Password.value.match(reEmpty)){
    		  alert("The password should only contain numeric and English characters!");
    		  f.Password.select();
    		  f.Password.focus();
    		  return false;
    	  }
      
    	  // must contains both alphabet and numbers
    	  if (!f.Password.value.match(reAlphaNum)){
    		  	alert("The password must contain both numeric and English characters!");
    		  	f.Password.select();
    		  	f.Password.focus();
    		  	return false;
    	  }
      
    	  // check password length
    	  if (f.Password.value.length < 6){
    		  alert("The password should contain at least 6 characters!");
    		  f.Password.select();
    		  f.Password.focus();
    		  return false;
    	  }
      
    	  // check confirm password
    	  if (f.Password.value != f.ConfirmPassword.value){
    		  alert("The two input passwords do not match.");
    		  f.Password.select();
    		  f.Password.focus();
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

        // check expiry date
        if (f.ExpiryDate.value.search(reEmpty) >= 0){
            // check if expiry date is earlier than effective date 
            effectiveDate = formatDate(f.EffectiveDate.value);
            expiryDate = formatDate(f.ExpiryDate.value);

            if (expiryDate < effectiveDate){
                alert("Expiry Date cannot be earlier than Effective Date!");
                f.EffectiveDate.select();
                f.EffectiveDate.focus();                
                return false;
            }
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
    /*if (location=="Import"){
        window.location = "ACLImport.jsp";
        return;
    }*/
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
         if (confirm("Are you sure you want to delete?")) {
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
    if (obj.name =="EnableUser"){        
        f.action="EnableUser.servlet";
        fsubmit = validateACL(f,"enable");        
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
