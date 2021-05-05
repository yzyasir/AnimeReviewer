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
		<h3>Network: ${singleShow.network}</h3>
	</div>
	<div class="container">
		<h2>Users who rated this show</h2>
		<table class="table table-dark"">
			<tr>
				<th scope="col">Name</th>
				<th scope="col">Rating</th>
			</tr>
			<c:forEach items="${rating.rating}" var="score">
			<tr>
				<td scope="col">${score.user.firstName}</td>
				<td scope="col">${score.rating}</td>
			</tr>
			</c:forEach>
		</table>
		<a class="btn btn-secondary" href="/shows/${singleShow.id}/edit" role="button">Edit</a>
	</div>
	<div class="container">
		<form:form action="/shows/${id}/rating" method="post"
			modelAttribute="newRating">
			<div class="form-group">
				<label>Leave a Rating</label>
				<form:input path="rating" class="form-control" />
				<form:errors path="rating" class="text-danger" />
			</div>
			<input type="submit" value="Rate!" class="btn btn-primary" />
		</form:form>
	</div>
</body>
</html>