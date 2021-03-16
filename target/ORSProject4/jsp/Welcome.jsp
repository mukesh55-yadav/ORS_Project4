<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.ORSView"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>

<style>
body {
	background-size: cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
}

.welcome {
	margin-top: 150px;
}

.welcom {
	margin-top: 10px;
}
</style>
<body>
	<form action="<%=ORSView.WELCOME_CTL%>">
	
		<div align="center" style="height: 230px;">

		<h1 align="Center" class="welcome">
			<font size="10px" color="red">Welcome to ORS </font>
		</h1>

		<%
			UserBean beanUserBean = (UserBean) session.getAttribute("user");
			if (beanUserBean != null) {
				if (beanUserBean.getRoleId() == RoleBean.STUDENT) {
		%>

		<%-- <h2 align="Center" class="welcom">
			<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Click here to see your
				Marksheet </a>
		</h2> --%>

		<%
			}
			}
		%>
		
		</div>
	</form>
</body>
</html>
<%@ include file="Footer.jsp"%>