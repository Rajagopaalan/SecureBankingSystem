<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

    <title>Registration</title>
		<script src="<c:url value='/static/js/jquery-3.1.1.slim.min.js'/>" ></script>
<script src="<c:url value='/static/js/parsley.min.js'/>" ></script>
<link href="<c:url value='/static/css/parsley.css' />" rel="stylesheet"></link>		<script src="<c:url value='/static/js/jquery-3.1.1.slim.min.js'/>" ></script>
<script src="<c:url value='/static/js/parsley.min.js'/>" ></script>
<link href="<c:url value='/static/css/parsley.css' />" rel="stylesheet"></link>
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
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h2 class="page-header">User Registration Form</h1>
            <!--<h4 class="sub-header">Transaction Summary:</h2>-->
            <form:form method="POST" modelAttribute="user" class="form-horizontal" id="reg_form">
        <form:input type="hidden" path="id" id="id"/>
        
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="firstName">First Name</label>
            <div class="col-md-7">
              <form:input type="text" path="firstName" id="firstName" class="form-control input-sm" data-parsley-trigger="change" data-parsley-pattern="^[a-zA-Z]+$" required="true"/>
              <div class="has-error">
                <form:errors path="firstName" class="help-inline"/>
              </div>
            </div>
          </div>
        </div>
    
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="lastName">Last Name</label>
            <div class="col-md-7">
              <form:input type="text" path="lastName" id="lastName" class="form-control input-sm" data-parsley-pattern="^[a-zA-Z]+$" required="true" />
              <div class="has-error">
                <form:errors path="lastName" class="help-inline"/>
              </div>
            </div>
          </div>
        </div>
    
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="ssoId">SSO ID</label>
            <div class="col-md-7">
              <c:choose>
                <c:when test="${edit}">
                  <form:input type="text" path="ssoId" id="ssoId" class="form-control input-sm" disabled="true" data-parsley-type="alphanum" required="true"/>
                </c:when>
                <c:otherwise>
                  <form:input type="text" path="ssoId" id="ssoId" class="form-control input-sm" />
                  <div class="has-error">
                    <form:errors path="ssoId" class="help-inline"/>
                  </div>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </div>
    
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="password">Password</label>
            <div class="col-md-7">
              <c:choose>
                <c:when test="${edit}">
                  <form:input type="password" path="password" id="password" class="form-control input-sm" disabled="false" data-parsley-type="alphanum"/>
                </c:when>
                <c:otherwise>
                  <form:input type="password" path="password" id="password" class="form-control input-sm" />
                  <div class="has-error">
                    <form:errors path="ssoId" class="help-inline"/>
                  </div>
                </c:otherwise>
              </c:choose><div class="has-error">
                <form:errors path="password" class="help-inline"/>
              </div>
            </div>
          </div>
        </div>
        
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="Gender">Gender</label>
            <div class="col-md-7">
              <form:input type="text" path="gender" id="gender" class="form-control input-sm" />
              <div class="has-error">
                <form:errors path="gender" class="help-inline"/>
              </div>
            </div>
          </div>
        </div>
    
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="email">Email</label>
            <div class="col-md-7">
              <form:input type="text" path="email" id="email" class="form-control input-sm" data-parsley-type="email" required="true" />
              <div class="has-error">
                <form:errors path="email" class="help-inline"/>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="address">Address</label>
            <div class="col-md-7">
              <form:input type="text" path="address" id="address" class="form-control input-sm" />
              <div class="has-error">
                <form:errors path="address" class="help-inline"/>
              </div>
            </div>
          </div>
        </div>
        
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="phone">Phone</label>
            <div class="col-md-7">
              <form:input type="text" path="phone" id="phone" class="form-control input-sm" data-parsley-type="digits" required="true"/>
              <div class="has-error">
                <form:errors path="phone" class="help-inline"/>
              </div>
            </div>
          </div>
        </div>
        
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="ssn">SSN</label>
            <div class="col-md-7">
              <form:input  path="ssn" id="ssn" class="form-control input-sm" type="text"  data-parsley-type="digits" required="true"/>
              <div class="has-error">
                <form:errors path="ssn" class="help-inline"/>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="createdBy">Created By</label>
            <div class="col-md-7">
              <form:input type="text" path="createdBy" id="createdBy" class="form-control input-sm" />
              <div class="has-error">
                <form:errors path="createdBy" class="help-inline"/>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="userProfiles">Roles</label>
            <div class="col-md-7">
			<c:choose>
                <c:when test="${edit}">
              <form:select id = "oneValue" path="userProfiles" name="roles" items="${roles}" multiple="false" itemValue="id" itemLabel="type" class="form-control input-sm" required="" disabled="false" />
                </c:when>
                <c:otherwise>
              <form:select id = "oneValue" path="userProfiles" name="roles" items="${roles}" multiple="false" itemValue="id" itemLabel="type" class="form-control input-sm" required="" disabled="false" />
                  <div class="has-error">
                    <form:errors path="userProfiles" class="help-inline"/>
                  </div>
             </c:otherwise>            
             </c:choose>
             
            </div>
          </div>
        </div>
        

    
        <div class="row">
          <div class="form-actions floatRight">
            <c:choose>
              <c:when test="${edit}">
                <input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/list' />">Cancel</a>
              </c:when>
              <c:otherwise>
                <input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/list' />">Cancel</a>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </form:form>
      </div>
      <script type="text/javascript">

      $('#reg_form').parsley();
      
       
    </script>
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