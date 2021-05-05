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
	<c:if test="${user != null}">
	<div class="alert alert-info">Welcome back ${user.firstName}!</div>
	</c:if>
		<h3>TV Shows</h3>
		<table class="table">
		  	<tr>
			    <th scope="col">Show Name</th>
			    <th scope="col">Network</th>
			    <th scope="col">Average Rating</th>
		  	</tr>
		 	<c:forEach items="${allShows}" var="show">
		 	<tr>
			    <td><a href="/shows/${show.id}">${show.title}</a></td>
			    <td>${show.network}</td>
			    <td>${show.getAvg()}</td>
		 	</tr>
		 	</c:forEach>
		</table>
		<a class="btn btn-secondary" href="/shows/new" role="button">Add a show</a>
	</div>
</body>
</html>