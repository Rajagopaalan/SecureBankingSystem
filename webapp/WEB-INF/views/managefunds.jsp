<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Manage Funds</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet">
<script src="<c:url value='/static/js/jquery-3.1.1.slim.min.js'/>" ></script>
<script src="<c:url value='/static/js/parsley.min.js'/>" ></script>
<link href="<c:url value='/static/css/parsley.css' />" rel="stylesheet"></link>
   
   <!-- jQuery & jQuery UI + theme (required) -->
	
	<link href="<c:url value='/static/css/jquery-ui.min.css' />"  rel="stylesheet"></link>
	<script src="<c:url value='/static/js/jquery-latest.min.js'/>" ></script>
	<script src="<c:url value='/static/js/jquery-ui.min.js'/>" ></script>
	<script src="<c:url value='/static/js/bootstrap.min.js'/>" ></script>
	

	<!-- keyboard widget css & script (required) -->
	<link href="<c:url value='/static/css/keyboard.css' />"  rel="stylesheet"></link>
	<script src="<c:url value='/static/js/jquery.keyboard.js'/>" ></script>

    <!-- Custom styles for this template -->
    <link href="<c:url value='/static/css/dashboard.css' />" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
    function valtransfer(){
    	
    	if (document.getElementById('accradio').checked==true){
    		document.getElementById('emailinput').value="";
    		document.getElementById('accradio').value="";
    	}
if (document.getElementById('emailradio').checked==true){
	document.getElementById('accinput').value="";
	document.getElementById('phoneinput').value="";
    	}
if (document.getElementById('phoneradio').checked==true){
	document.getElementById('accinput').value="";
	document.getElementById('emailinput').value="";
}
    }
    </script>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">STATE BANK OF ARIZONA</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li style="text-transform: uppercase;"><a href="#">${loggedinuser}</a></li>
            <li><a href="<c:url value="/logout" />">LOGOUT</a></li>
          </ul>
         </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <sec:authorize access="hasRole('USER')">
              <li class="well">
                <a href="<c:url value='/home' />">HOME</a>
              </li>
              <li class="well">
                <a href="<c:url value='/accounts' />">VIEW ACCOUNT</a>
              </li>
              <li class="well">
                <a href="<c:url value='/managefunds' />">MANAGE FUNDS</a>
              </li>
              <li class="well">
                <a href="<c:url value='/viewtransactions' />">TRANSACTIONS</a>
              </li>
              <li class="well">
                <a href="<c:url value='/creditcard' />">CREDIT CARD</a>
              </li>
              <li class="well">
                <a href="<c:url value='/pendingapprovalsbyuser' />">PENDING APPROVALS</a>
              </li>
            </sec:authorize>
            <sec:authorize access="hasRole('MERCHANT')">
              <li class="well">
                <a href="<c:url value='/home' />">HOME</a>
              </li>
              <li class="well">
                <a href="<c:url value='/accounts' />">VIEW ACCOUNT</a>
              </li>
              <li class="well">
                <a href="<c:url value='/managefunds' />">MANAGE FUNDS</a>
              </li>
              <li class="well">
                <a href="<c:url value='/viewtransactions' />">TRANSACTIONS</a>
              </li>
              <li class="well">
                <a href="<c:url value='/creditcard' />">CREDIT CARD</a>
              </li>
              <li class="well">
                <a href="<c:url value='/submitpaymentbymerchant' />">SUBMIT PAYMENT</a>
              </li>
            </sec:authorize>
            <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
              <li class = "well">
                <a href="<c:url value='/pending_trans' />">PENDING TRANSACTIONS</a>   
              </li>
              <li class = "well">
                <a href="<c:url value='/downloadLogs' />">DOWNLOAD LOGS</a>   
              </li>
            </sec:authorize>
            <li class="well">
              <a href="<c:url value='/list' />">LIST OF USERS</a></span>
            </li>
          </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                 <h2>Choose Credit/Debit For Transaction</h2>
                 <br/>
<form:form action="transaction" ModelAttribute="transaction"  method="post" id="form_debit">
	
		<c:forEach items="${transaction}" var="transaction">
			<option value="${transaction}">${transaction}</option>
		</c:forEach>
	</select>
	<input type="radio" name="debitandcredit" value="debit" checked="checked"> Debit
	<input type="radio" name="debitandcredit" value="credit"> Credit
	Amount:<input type ="text" name="amount"  required >
	<input type="submit" name="submit"value="<spring:message text="perform transaction"/>"  />
	<c:if test="${!empty  status}">Operation:${status}</c:if>
</form:form>
<br/>
<hr/>
<br/>
<h2>Transfer Funds</h2>
<form:form action="transferFund"  method="post" id="form_transfer">
<table style="border: none">
<tr>
<td><input id="accradio" type="radio" name="radiotransfer" value="acc" checked="checked"> To Account: </td><td><input id="accinput" type="text" name="toAcc" /></td></tr>
<tr>
<td><input id="emailradio" type="radio" name="radiotransfer" value="email" > To Email: </td><td><input id="emailinput" type="email" name="toEmail" ></td></tr>
<tr>
<td><input id="phoneradio" type="radio" name="radiotransfer" value="phone" > To Phone: </td><td><input id="phoneinput" type="text" name="toPhone" ></td></tr>
<tr>
<td></td></tr>
<tr>
<td>Amount: <input type="text" name="amount" required/>
<input type="submit" name="transfer" onclick="valtransfer()"
			value="<spring:message text="Transfer Money"/>" />
${emptyparams} 
${negbal} 
${txnsuccess}
${inputformat}
</td>
</tr>
</table>
</form:form>
<br/>
<hr>
<br/>

<h3>Enter OTP for transactions over 10000$</h3>
<form:form action="validateOtp"  method="post" id="otpform">
OTP: <input type="text" name="otp" id="otp"/>
<input type="submit" name="checkOtp" data-parsley-type="digits" required
			value="<spring:message text="Validate OTP" />" />
			${otpstatus}
</form:form>

        </div>
      </div>
    </div>
<script>
$('#form_debit').parsley();
$('#form_transfer').parsley();
$('#otpform').parsley();
</script>
<script>
		$(function(){
			$('#otp').keyboard();
		});
	</script>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    
    
   
  </body>
</html>