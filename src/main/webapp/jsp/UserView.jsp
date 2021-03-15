<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.UserCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User View</title>
</head>

<body>
	<form action="<%=ORSView.USER_CTL%>" method="post">
	
		<div align="center" style="height: 420px;">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<%
			List l = (List) request.getAttribute("roleList");
		%>

		<%
			if (bean.getId() > 0) {
		%>
			<h1 align="center">Update User</h1>
		<%
			} else {
		%>
			<h1 align="center">Add User</h1>
		<%
			}
		%>

		<H2>
			<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
	
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
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
					placeholder="Enter Your First Name"
					value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
					color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
			</tr>
			<tr>
				<th>Last Name<font color="red">*</font></th>
				<td><input type="text" name="lastName"
					placeholder="Enter Your Last Name" class="form-control"
					value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
					color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
			</tr>
			<tr>
				<th>LoginId<font color="red">*</font></th>
				<td><input type="text" name="login" style="background-color: #f2f3f4;"
					placeholder="Must be an EmailId" class="form-control" 
					value="<%=DataUtility.getStringData(bean.getLogin())%>"
					<%=(bean.getId() > 0) ? "readonly" : ""%>><font color="red">
						<%=ServletUtility.getErrorMessage("login", request)%></font></td>
			</tr>
			<tr>
				<th>Password<font color="red">*</font></th>
				<td><input type="password" name="password"
					placeholder="Must be a Password" class="form-control"
					value="<%=DataUtility.getStringData(bean.getPassword())%>">
					<font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
			</tr>
			<tr>
				<th>Confirm Password<font color="red">*</font></th>
				<td><input type="password" name="confirmPassword"
					placeholder="Must be a Password" class="form-control"
					value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
					color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
			</tr>
			<tr>
				<th>Gender<font color="red">*</font></th>
				<td>
					<%
						HashMap map = new HashMap(); /* gender is a static preload bcz here we are giving data */
						map.put("","---Select---");
						map.put("M", "Male");
						map.put("F", "Female");

						String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
					%> <%=htmlList%> <font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font>
				</td>
			</tr>
			<tr>
				<!-- role is dynamic preload bcz data is coming from database  -->
				<th>Role<font color="red">*</font></th>
				<td><%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), l)%>
					<font color="red"><%=ServletUtility.getErrorMessage("roleId", request)%></font>
				</td>
			</tr>
			<tr>
				<th>Date Of Birth<font color="red">*</font></th>
				<td><input type="text" name="dob" readonly="readonly"
					placeholder="(mm/dd/yyyy)" class="form-control"
					value="<%=DataUtility.getDateString(bean.getDob())%>" id="datepicker">

					<font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
			</tr>

			<tr>
				<th></th>
				<td colspan="2">&emsp;&emsp;<input type="submit"
					name="operation" class="form-control"
					value="<%=(bean.getId() > 0) ? UserCtl.OP_UPDATE : UserCtl.OP_SAVE%>">
					&nbsp; <input type="submit" name="operation"
					value="<%=(bean.getId() > 0) ? UserCtl.OP_CANCEL : UserCtl.OP_RESET%>">
				</td>
			</tr>

		</table>
		</div>
	</form>
</body>
</html>
<%@ include file="Footer.jsp"%>