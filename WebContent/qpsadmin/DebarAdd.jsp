<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="qpses.business.DebarInfo,
                                qpses.business.DebarDataBean,
                                qpses.business.DebarInfo,
                                qpses.util.SysManager, 
                                java.util.HashMap,
                                java.util.List,
                                java.util.ArrayList"%>
                               
<%!private String getValue(String temp){ return (temp == null )? "" : temp;}%>
<%
    // initialization
    boolean searched = false;
	DebarDataBean debarDB =new DebarDataBean();
    List allDebarList = new ArrayList();

    String order_by = "";                                                                                
    String order_dir = "";
    // get list order
    HashMap orderHash = (HashMap) session.getAttribute("DEBAR_LIST_ORDER");
    if (orderHash !=null){
        order_by = (String) orderHash.get("ORDER_BY");
        order_dir = (String) orderHash.get("ORDER_DIR");
        session.removeAttribute("DEBAR_LIST_ORDER");
    } else{
        order_by = getValue(request.getParameter("OrderBy"));
        order_dir = getValue(request.getParameter("OrderDir"));
    }          
    if (order_by.equals("")) order_by = "Dept";
    if (order_dir.equals("")) order_dir = "ASC";

    allDebarList = debarDB.selectDebarment(order_by,order_dir);
    // check  for message and get last input from session variables
    String msg =   getValue((String)  session.getAttribute("DEBAR_MSG"));
    String msgtype =   getValue((String)  session.getAttribute("DEBAR_MSGTYPE"));
    if (!msg.equals("")){
        session.removeAttribute("DEBAR_MSG");
        session.removeAttribute("DEBAR_MSGTYPE");
        session.removeAttribute("DEBAR_DATA");
    }

%>  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SOA-QPS3 Quality Professional Services Information System Administration - Add User</title>
        <link rel="STYLESHEET" type="text/css" href="../styles/style.css">
        <link rel="STYLESHEET" type="text/css" href="../styles/calendar.css">
        <script language="JavaScript" src="../js/simplecalendar.js" type="text/javascript"></script>                        
        <script language="JavaScript" src="../js/debarcheck.js" type="text/javascript"></script>
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <fieldset class="function"> 
            <table class="function_title"><tr><td>Contractor Debarment Maintenance</td></tr></table>            		        
            <form name="form1" method="POST" action="">
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <tr> 
                        <td width="20%" class="tabUnselectLeft" onclick="changepage('List')">Debarment List</td>
                        <td width="21%" class="tabSelectedMiddle">Debar Contractor</td>
                        <td width="56%">&nbsp;</td>
                    </tr>                    
                </table>       
                <table border="0" cellspacing="0" cellpadding="0" width="98%" class="tableborder" align="center">
                    <td class="title1"> 
                        <div align="left">Add a new debarment</div>
                    </td>
                    </tr>
                    <tr> 
                    
                        <td> <%= (! msg.equals(""))? "<div class=\"errMessage\">"+ msg + "</div>":""%> 
                            <table width="100%" border="0" cellspacing="0" cellpadding="5">
                                <tr> 
                                    <td> 
                                        <div id="dropdown" align="center"> 
                                            <table width="98%" cellspacing="1" class="tableBackground">
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Service Category Group</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <select id="Category"  class="step1" name="Category">
                                                            <option value="">Please select service category group</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Contractor Name</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <select id="ContractorName" class="step2" name="ContractorName">
                                                            <option value="">Please select contractor</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Contractor ID</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <input type="text" id="ContractorId" name="ContractorId" value="Contractor ID" size="30" maxlength="30" readonly disabled />
                                                    </td>
                                                </tr>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Start Date</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                    <input type="text" name="StartDate" value="" class="inputText" size="10"  readonly/>
                                                    <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;"  
                                                    onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';"   
                                                    onClick="g_Calendar.show(event,'form1.StartDate',true, 'dd-mmm-yyyy');return false"> 
                                                    <img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt=""></a> 
                                                    (dd-MMM-yyyy) </td>
                                                </tr>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">End Date </td>
                                                    <td class="tableVerticalContentAlignLeft1" width="71%"> 
                                                    <input type="text" name="EndDate" value="" class="inputText" size="10"  readonly/>
                                                    <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;"  
                                                    onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';"   
                                                    onClick="g_Calendar.show(event,'form1.EndDate',true, 'dd-mmm-yyyy');return false"> 
                                                    <img src="../images/calendar.gif" name="imgCalendar" width="34" height="21" border="0" alt=""></a> 
                                                    (dd-MMM-yyyy) </td>
                                                </tr>
                                                <tr> 
                                                    <td class="tableVerticalHeaderAlignLeft1" width="25%">Release?</td>
                                                    <td class="tableVerticalContentAlignLeft1" width="75%"> 
                                                        <input type="radio" name="Release" value="Y">Yes</input>
                                                        <input type="radio" name="Release" value="N" checked="checked" >No</input>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <div align="center"></div>
                            <table border="0" cellpadding="3" align="center">
                                <tr> 
                                    <td width="50%">                                        
                                        <div align="center"><a href="#"> <img src="../images/btn_add.jpg" width="98" height="42" name="ConfirmAdd" onclick="selectaction(form1,this)" border="0" alt="Add"></a></div>
                                    </td>
                                    <td width="50%">                                        
                                        <div align="center"><a href="#"><img src="../images/btn_cancel.jpg" width="98" height="42" name="Cancel" onclick="selectaction(form1,this)" border="0" alt="Cancel"></a></div>
                                    </td>
                                </tr>
                            </table>    
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="PostScreen" value="DebarList.jsp">                  
            </form>                
        </fieldset> 		    
        
        <!-- Scripts here -->
        <script language="JavaScript" type="text/javascript" src="../js/jquery.min.js"></script>
        <script language="JavaScript" type="text/javascript" src="../js/knockout-min.js"></script>
        <script language="JavaScript" type="text/javascript" src="../js/jquery.cascadingdropdown.js"></script>  
		<script type="text/javascript">
		function viewmodel() {
            this.phones = ko.observableArray([]);
            this.loading = ko.observable(false);
        }

        var dropdown = new viewmodel();

        ko.applyBindings(dropdown, document.getElementById('dropdown'));
        $(function(){

            $('#dropdown').cascadingDropdown({
                selectBoxes: [
                    {
                        selector: '.step1',
                        source: [{ label: 'Cat 1', value: '1' }, { label: 'Cat 2 Minor', value: '2/N' }, { label: 'Cat 2 Major', value: '2/J' }, { label: 'Cat 3 Minor', value: '3/N' }, { label: 'Cat 3 Major', value: '3/J' }, { label: 'Cat 4', value: '4' }]
                    },
                    {
                        selector: '.step2',
                        requires: ['.step1'],
                        source: function(request, response) {
                            $.getJSON('options.jsp', request, function(data) {
                                var selectOnlyOption = data.length <= 1;
                                response($.map(data, function(item, index) {
                                    return {
                                        label: item.name,
                                        value: item.id,
                                        selected: selectOnlyOption
                                    };
                                }));
                            });
                        }
                    }
                ]
            });
            
            $('#ContractorName').change(function(){
                $('#ContractorId').val($(this).val());
                document.getElementById('ContractorId').disabled = false;
            });

            $('#Category').change(function(){
                $('#ContractorId').val("Please select Contractor Above");
                $('#ContractorName').val("Please select contractor");
                document.getElementById('ContractorId').disabled = true;
            });
        });
			
		</script>			      		
    </body>
</html>
