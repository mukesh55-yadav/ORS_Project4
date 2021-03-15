<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%----welcome page pr 3pages hote h , header,footer,welcome
and hello guest 1 normal message h jo header pr hard coding krkke rkhi h  
 --%>
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
	margin-top: 140px;
}

.welcom {
	margin-top: 10px;
}
</style>
<body>
	<form action="<%=ORSView.WELCOME_CTL%>">
	
		<div align="center" style="height: 200px;">

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