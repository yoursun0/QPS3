/*************************************************************************/
function GetInfo_LinkOnClick(effective_date, scg)
{
    document.form.scg.value            = scg;    
    document.form.effective_date.value = effective_date;      
    
    document.form.action               = "../qpsuser/DiscountRateSLUser";
    document.form.request_action.value = "showImagePage";
    document.form.target               = "_self";
    document.form.submit();
}      

/*************************************************************************/
function ShowImage_DownloadFile()
{   
    document.form.action               = "../qpsuser/DiscountRateSLUser";
    document.form.request_action.value = "getPDF";
    document.form.target               = "_self";
    document.form.submit();
}

function ShowImage_BtnBackOnClick()
{
    document.form.action               = "../qpsuser/DiscountRateSLUser";
    document.form.request_action.value = "";
    document.form.target               = "_self";
    document.form.submit();
}

function ShowImage_PageNoOnClick(pageNo)
{
    document.form.action        = "../qpsuser/DiscountRateShowImage.jsp";
    document.form.page_no.value = pageNo;
    document.form.target        = "_self";
    document.form.submit();
}
/*************************************************************************/
