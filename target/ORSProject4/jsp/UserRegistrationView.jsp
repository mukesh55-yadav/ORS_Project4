<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.UserRegistrationCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration</title>
</head>
<body>
	<div align="center" style="height: 360px;">

		<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

			<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
				scope="request"></jsp:useBean>

			<h1>&emsp;&nbsp;User Registration</h1>
			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H2>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table align="center" style="margin-left: 38%;">
				<tr>
					<th>First Name<font color="red">*</font></th>
					<td><input type="text" name="firstName"
						placeholder="Enter your First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>
				<tr>
					<th>Last Name<font color="red">*</font></th>
					<td><input type="text" name="lastName" class="form-control"
						placeholder="Enter your Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th>LoginId<font color="red">*</font></th>
					<td><input type="text" name="login" class="form-control"
						placeholder="Enter your Login ID"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th>Password<font color="red">*</font></th>
					<td><input type="password" name="password"
						class="form-control" placeholder="Enter your Password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th>Confirm Password<font color="red">*</font></th>
					<td><input type="password" name="confirmPassword"
						class="form-control" placeholder="Confirm Password "
						value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>
				<tr>
					<th>Gender<font color="red">*</font></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("", "Select");
						    map.put("F", "Female");
						    map.put("M", "Male");
							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%><%=htmlList%> <font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>
				</tr>

				<tr>
					<th>Date of Birth<font color="red">*</font></th>
					<td><input id="datepicker" type="text" name="dob"
						class="form-control" readonly="readonly" placeholder="Enter your DOB"
						value="<%=DataUtility.getDateString(bean.getDob())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2">&emsp;&nbsp; <input type="submit" class="form-control"
						name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP%>">&emsp;&nbsp;<input
						type="submit" name="operation" class="form-control"
						value="<%=UserRegistrationCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>

</html>
<%@ include file="Footer.jsp"%>