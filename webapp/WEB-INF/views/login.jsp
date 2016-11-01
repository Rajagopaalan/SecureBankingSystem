<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login page</title>
		<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
		<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
		<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
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
	
	</head>

	<body>
		<div id="mainWrapper">
			<div class="login-container">
				<div class="login-card">
				<center>
				<img src="<c:url value='/static/SBA.png'/>" style="width:304px;height:228px;"/></center>
					<div class="login-form">
						<c:url var="loginUrl" value="/login" />
						<form id="form_login" action="${loginUrl}" method="post" class="form-horizontal">
							<c:if test="${param.error != null}">
								<div class="alert alert-danger">
									<p>Invalid username and password.</p>
								</div>
							</c:if>
							<c:if test="${param.logout != null}">
								<div class="alert alert-success">
									<p>You have been logged out successfully.</p>
								</div>
							</c:if>
							<div class="input-group input-sm">
								<label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
								<input type="text" class="form-control" id="username" name="ssoId" placeholder="Enter Username" data-parsley-type="alphanum" required>
							</div>
							<div class="input-group input-sm">
								<label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label> 
								<input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
							</div>
							
							<div class="input-group input-sm">
                              <div class="checkbox">
                                <label><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>  
                              </div>
                              <div class="checkbox">
                                <label><input type="checkbox" onchange="document.getElementById('subbut').disabled = !this.checked;" id="iamnotrobot" name="iamnot-robot"> I am not Robot</label>  
                              </div>
                            </div>

                            
							<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
								
							<div class="form-actions">
								<input type="submit" id="subbut"
									class="btn btn-block btn-primary btn-default" value="Log in" disabled="true">
							</div>
						</form>
						
						<form:form action="forgotPassword"  method="post">
						Email: <input type="text" name="email"/>
						<input type="submit" name="checkOtp" value="<spring:message text="Forgot Password?"/>" />
							<label>${emailerror}</label>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		<script>
			var checker = document.getElementById('iamnotrobot');
			var sendbtn = document.getElementById('subbut');
			checker.onchange = function() {
			  sendbtn.disabled = !this.checked;
			};
			</script>
<script type="text/javascript">

  $('#form_login').parsley();
   
</script>
<script>
		$(function(){
			$('#password').keyboard();
		});
	</script>
	</body>
</html>