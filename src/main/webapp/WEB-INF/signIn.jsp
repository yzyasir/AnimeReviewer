<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page import = "java.io.*,java.util.*" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign In and Register</title>
<link rel="stylesheet" href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css" />
<script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
	<h2>Please Login or Register</h2>
		<c:if test="${user != null}">
		<div class="alert alert-info">Welcome back ${user.firstName}!</div>
		</c:if>
		<div class="row">
			<form:form class="col-sm-8" action="/register" method="post"
				modelAttribute="registeringUser">
				<div class="row">
					<div class="form-group col-sm-6">
						<label>First Name</label>
						<form:input path="firstName" class="form-control" />
						<form:errors path="firstName" class="text-danger" />
					</div>
					<div class="form-group col-sm-6">
						<label>Last Name</label>
						<form:input path="lastName" class="form-control" />
						<form:errors path="lastName" class="text-danger" />
					</div>
				</div>
				<div class="form-group">
					<label>Email</label>
					<form:input path="email" class="form-control" />
					<form:errors path="email" class="text-danger" />
				</div>
				<div class="row">
					<div class="form-group col-sm-6">
						<label>Password</label>
						<form:input type="password" path="password" class="form-control" />
						<form:errors path="password" class="text-danger" />
					</div>
					<div class="form-group col-sm-6">
						<label>Confirm Password</label>
						<form:input type="password" path="confirm" class="form-control" />
						<form:errors path="confirm" class="text-danger" />
					</div>
				</div>
				<input type="submit" value="Register" class="btn btn-primary" />
			</form:form>
			<form:form class="col-sm-4" action="/login" method="post"
				modelAttribute="loginUser">
				<div class="form-group">
					<label>Email</label>
					<form:input path="email" class="form-control" />
					<form:errors path="email" class="text-danger" />
				</div>
				<div class="form-group">
					<label>Password</label>
					<form:input type="password" path="password" class="form-control" />
					<form:errors path="password" class="text-danger" />
				</div>
				<input type="submit" value="login" class="btn btn-primary" />
			</form:form>
		</div>
	</div>
</body>
</html>