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

    <title>Pending Approvals</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet">

   

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
          <h2 class="page-header">Pending Approvals</h2>
          <!--<h4 class="sub-header">Transaction Summary:</h2>-->
          <c:if test="${!empty usermerchantrequest}"> User:  Merchant requests<br>
          <div class="table-responsive">
            <table class="table table-striped">
              <tr>
              <th>Transaction Id</th>
              <th>Account number </th>
              <th>Amount</th>
              <th>Transaction_Merchant</th>
              <th>status</th>
              </tr>
              <c:forEach items="${usermerchantrequest}" var="usermerchantrequest">
              <tr>
              <td>${usermerchantrequest.transc_id} </td>
              <td>${usermerchantrequest.transc_acc_num} </td>
              <td>${usermerchantrequest.transc_amt} </td>
              <td>${usermerchantrequest.transc_merchant}</td>
              <c:if test="${empty  approved}">
              <td><a href="<c:url value='/approve-${usermerchantrequest.transc_id}'/>">approve</a></td></c:if></td>
              <c:if test="${!empty  approved}">
              <td>approved</td></c:if>
              </tr>
              </c:forEach>
            </table>
          </div>
        </c:if>
        </div>
      </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../../dist/js/bootstrap.min.js"></script>
   
  </body>
</html>
