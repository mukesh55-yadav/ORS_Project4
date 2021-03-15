<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.LoginCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<form action="<%=ORSView.LOGIN_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
			scope="request">
		</jsp:useBean>
		<jsp:useBean id="frontctl"
			class="in.co.rays.proj4.ctl.FrontController" scope="request">
		</jsp:useBean>

		<%
			String uri = (String) request.getAttribute("URI");
			System.out.print("uri in jsp" + uri);
		%>
		<input type="hidden" name="URI" value="<%=uri%>">

		<div align="center" style="height: 350px;">
			<h1>Login</h1>

			<H2>
				<%
					String msg = (String) request.getAttribute("message");
					if (msg != null) {
				%>
				<font color="red"> <%=msg%></font>
				<%
					}
				%>
			</H2>

			<H2>
				<font color="blue"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H2>

			 <input type="hidden" name="id" value="<%=bean.getId()%>">
			 <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			 <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			 <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			 <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table align="center" style="margin-left: 38%;">
				<tr>
					<th>LoginId<font color="red">*</font></th>
					<td><input type="text" name="login" size=20 placeholder="Enter your Login ID"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th>Password<font color="red">*</font></th>
					<td><input type="password" name="password" size=20 class="form-control"  placeholder="Enter your Password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation" class="form-control"
						value="<%=LoginCtl.OP_SIGN_IN%>"> &nbsp; <input
						type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>">
						&nbsp;</td>
				</tr>

				<tr>
					<th></th>
					<td style="height: 60px;"><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget  Password</b></a>&nbsp;</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
<%@ include file="Footer.jsp"%>