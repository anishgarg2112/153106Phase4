
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<title>Registration</title>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<div class="container" align="center">
		<h2>Enroll Associate Details Here</h2>
		<form:form action="registerCustomer"
			method="post" modelAttribute="customer">

			<div class="form-group">
				<label for="name" class="control-label col-sm-2">First Name:</label>

				<div class="col-sm-10">
					<form:input id="name" path="name" />
					<form:errors path="name" cssClass="error" />
				</div>
			</div>

			<div class="form-group">
				<label for="num" class="control-label col-sm-2">Phone No:</label>
				<div class="col-sm-10">
					<form:input id="num" path="mobileNo" />
					<form:errors path="mobileNo" cssClass="error" />
				</div>

			</div>
			<div class="form-group">
				<label for="balance" class="control-label col-sm-2">Amount:</label>
				<div class="col-sm-10">
					<form:input id="balance" path="wallet.balance" />
					<form:errors path="wallet.balance" cssClass="error" />
				</div>
			</div>

			<div class="form-group">
				<div class="col-offset-2 col-sm-10">
					<input type="submit" class="btn btn-primary" value="Submit" />
				</div>
			</div>
		</form:form>
	</div>
	<div align="left">
		<font color="red"> <c:if test="${not empty errormessage}">
	${errormessage}
	</c:if>
		</font>
	</div>
	<div align="left">
		<a href="">Back To home</a>
	</div>
</body>
</html>
