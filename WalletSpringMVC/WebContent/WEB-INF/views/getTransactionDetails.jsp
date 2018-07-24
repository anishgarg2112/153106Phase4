<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
table, th, td {
    border: 1px solid black;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div align="center">

		<table style="width:100%">
			<tr>
				<th>Transaction Id:</th>
				<th>Amount:</th>
				<th>Transaction Type:</th>
				<th>Beneficiary:</th>
			</tr>

			<c:forEach var="t" items="${transaction}">
				<tr>
					<td>${t.id}</td>
					<td>${t.amount}</td>
					<td>${t.trans_type}</td>
					<td>${t.pay_to}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>