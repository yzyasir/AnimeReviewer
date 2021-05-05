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
<title>Add Ratings here</title>
<link rel="stylesheet" href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css" />
<script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h2>${singleShow.title}</h2>
	<form:form action="/edit/update/${singleShow.id}" method="post"
		modelAttribute="singleShow">
		<div class="form-group">
			<label>Title</label>
			<form:input path="title" class="form-control" />
			<form:errors path="title" class="text-danger" />
		</div>
		<div class="form-group">
			<label>Network</label>
			<form:input path="network" class="form-control" />
			<form:errors path="network" class="text-danger" />
		</div>
		<input type="submit" value="Update" class="btn btn-primary" />
	</form:form>
	<a class="btn btn-secondary" href="/show/${singleShow.id}/delete" role="button">Delete</a>
</div>
</body>
</html>