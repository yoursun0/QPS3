function validateACL(f,flag) {

       // regular expresssion for checking of empty string
      reEmpty = new RegExp("[a-zA-Z.0-9]+");
      reAlphaNum = new RegExp("^([0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+)[0-9a-zA-Z]*$");

      // check old password
      if (f.OldPassword.value.search(reEmpty) < 0){
          alert("Please input the old password!");
          f.OldPassword.select();
          f.OldPassword.focus();
          return false;
      }
      
      // check new password
      if (f.Password.value.search(reEmpty) < 0){
          alert("Please input the new password!");
          f.Password.select();
          f.Password.focus();
          return false;
      }
      
      // check confirm password
      if (f.ConfirmPassword.value.search(reEmpty) < 0){
          alert("Please input confirm password!");
          f.ConfirmPassword.select();
          f.ConfirmPassword.focus();
          return false;
      }
      
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

      return true;
}

function selectaction(f,obj){    
    fsubmit=false;   
    
    if (obj.name =="Cancel"){        
        f.action = "index.jsp";        
        fsubmit=true;
    }
   
    if (obj.name =="Update"){        
        f.action="ACLUpdate.jsp";
        fsubmit=true;
    }

    if (obj.name =="ConfirmUpdate"){
        fsubmit = validateACL(f,"update");
        if (fsubmit) f.action="ChangePassword.servlet";
    }
    
    if (fsubmit) f.submit();
    return;
}
