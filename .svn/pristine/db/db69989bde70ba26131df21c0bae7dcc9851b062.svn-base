/*************************************************************************/
function GetInfo_InfoPdfOnClick()
{
    document.form.action               = "../qpsuser/AvgCeilingRateSLUser";
    document.form.request_action.value = "getInfoPdf";
    document.form.target               = "_self";
    document.form.submit();
}

/*************************************************************************/
function GetInfo_SCLinkOnClick(effective_date, sc)
{
    document.form.sc.value             = sc;    
    document.form.effective_date.value = effective_date;      
    document.form.action               = "../qpsuser/AvgCeilingRateSLUser";
    document.form.request_action.value = "showImagePage";
    document.form.target               = "_self";
    document.form.submit();
}

/*************************************************************************/
function ShowImage_DownloadFile()
{   
    document.form.action               = "../qpsuser/AvgCeilingRateSLUser";
    document.form.request_action.value = "getPDF";
    document.form.target               = "_self";
    document.form.submit();
}

function ShowImage_BtnBackOnClick()
{
    document.form.action               = "../qpsuser/AvgCeilingRateSLUser";
    document.form.request_action.value = "";
    document.form.target               = "_self";
    document.form.submit();
}

function ShowImage_PageNoOnClick(pageNo)
{
    document.form.action        = "../qpsuser/AvgCeilingRateShowImage.jsp";
    document.form.page_no.value = pageNo;
    document.form.target        = "_self";
    document.form.submit();
}
/*************************************************************************/