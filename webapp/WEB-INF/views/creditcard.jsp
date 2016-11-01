<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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

    <title>Credit Card</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet">
<script src="<c:url value='/static/js/jquery-3.1.1.slim.min.js'/>" ></script>
<script src="<c:url value='/static/js/parsley.min.js'/>" ></script>
<link href="<c:url value='/static/css/parsley.css' />" rel="stylesheet"></link>
   

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
          <h2 class="page-header">Credit Card</h2>
          <h4>Credit Card Account Details</h4>
          <c:if test="${!empty listCards}">
          	<table class="table table-striped">
          	<tr>
          		<th width="80">Card ID</th>
          		<th width="120">Cardholder Name</th>
          		<th width="120">Expiry Date</th>
          		<th width="120">CVV</th>
          		<th width="120">Credit Limit</th>
          		<th width="120">Amount Spent</th>
          		<th width="120">Due Date</th>
          	</tr>
          	<c:forEach items="${listCards}" var="card">
          		<tr>
          			<td>${card.cardId}</td>
          			<td>${card.chName}</td>
          			<td>${card.expDate}</td>
          			<td>${card.cvv}</td>
          			<td>${card.creditLimit}</td>
          			<td>${card.amountSpent}</td>
          			<td>${card.dueDate}</td>
          		</tr>
          	</c:forEach>
          	</table>
          </c:if>
		<br/>
		<hr/>
          <h4>Make Payment To Merchant</h4>
          <form:form action="makePayment"  method="post" id="form_merchantpay">
          To Account: <input type="text" name="toAcc" data-parsley-type="digits" required/>
          Amount: <input type="text" name="amount" data-parsley-type="digits" required/>
          <input type="submit" name="payment"
          			value="<spring:message text="Get OTP"/>" /> ${status}
          		
          </form:form>
          ${payment}

<form:form action="validateOtpCC"  method="post" id="form_merchantpay">
          
		Enter OTP:	<input type="text" name="otp"  />			
           <input type="submit" name="otpvalidate"
          			value="<spring:message text="Validate and Pay"/>" /> ${otpstatus}	
           </form:form>
           
		<br/>
		<hr/>
          <h4>Pay Credit Card Bill</h4>
          <form:form action="payCreditCard"  method="post" id="form_payccbill">
          Amount: <input type="text" name="amount" data-parsley-type="digits" required/>
          <input type="submit" name="Pay Bill"
          			value="<spring:message text="Credit Card Payment"/>" />
          </form:form>
          ${payCCBill}
			
			<br/>
			<br/>
		<hr/>
          <h4>Credit Limit Increment Request</h4>
          <form:form action="creditlimit"  method="post">
          <input type="submit" name="Credit Limit"
          			value="<spring:message text="Increase Credit Limit"/>" />
          </form:form>
          ${newcreditlimit}

			
			<br/>
			<br/>
		<hr/>
          <h4>Display Credit Card Transactions</h4>
          <form:form action="cctransaction" ModelAttribute="cctransaction"  method="post">
          	<input type="submit" name="display" value="<spring:message text="Display transaction"/>" />
          </form:form>
          <c:if test="${!empty listcctransactions}">
          <table border=2>
          	<tr>
          		<th>Transaction Id</th>
          		<th>Amount</th>
          		<th>Merchant</th>
          		<th>Date</th>
          		<th>Description</th>
          	</tr>
          <c:forEach items="${listcctransactions}" var="listtransactions">
          	<tr>
          		<td>${listtransactions.ccTranscId} </td>
          		<td>${listtransactions.amount} </td>
          		<td>${listtransactions.merchant} </td>
          		<td>${listtransactions.date}</td>
          		<td>${listtransactions.trxDesc}</td>
          	</tr>
          </c:forEach>
          </table>
          </c:if>
        </div>
      </div>
    </div>
<script>
$('#form_payccbill').parsley();
$('#form_merchantpay').parsley();
</script>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../../dist/js/bootstrap.min.js"></script>
   
  </body>
</html>