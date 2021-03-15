<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.ForgetPasswordCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forget Password</title>
</head>
<body>
	<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
			scope="request">
		</jsp:useBean>

		<div align="center" style="height: 395px;">
			<h1>Forgot your password?</h1>
			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
	
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H2>

			<h3>
				<i>Submit your email address and we'll send you password.</i>
			</h3>

			<table align="center" style="margin-left: 38%;">
				<tr>
					<th align="left"><b>Email Id : </b></th>
					<td><input type="text" name="login"
						placeholder="Enter your Email Id"
						value="<%=ServletUtility.getParameter("login", request)%>">&emsp;
						<input type="submit" name="operation"
						value="<%=ForgetPasswordCtl.OP_GO%>"> <input type="submit"
						name="operation" value="<%=ForgetPasswordCtl.OP_RESET%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
<%@ include file="Footer.jsp"%>