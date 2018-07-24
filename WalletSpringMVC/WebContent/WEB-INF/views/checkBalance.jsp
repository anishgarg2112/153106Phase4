<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Balance</title>
</head>
<body>
	<div align="center">

		<table>

			<form:form action="showBalance" method="post"
				modelAttribute="customer">

				<tr>
					<td>Phone No.:</td>
					<td><form:input path="mobileNo" size="30" /></td>
					<td><form:errors path="mobileNo" cssClass="error" /></td>
				</tr>

				<tr>
					<td><input type="submit" value="Submit" /></td>
				</tr>
			</form:form>
		</table>
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