<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.MyProfileCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Profile</title>
</head>
<body>
	<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
			scope="request">
		</jsp:useBean>

		<div align="center"  style="height: 360px;">
			<h1>My Profile</h1>

			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H2>
			<H2>
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
					<th>LoginId<font color="red">*</font></th>
					<td><input type="text" name="login" style="background-color: #f2f3f4;"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						readonly="readonly"> <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
					</td>
				</tr>

				<tr>
					<th>First Name<font color="red">*</font></th>
					<td><input type="text" name="firstName" class="form-control"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>

				<tr>
					<th>Last Name<font color="red">*</font></th>
					<td><input type="text" name="lastName"  class="form-control"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th>Gender<font color="red">*</font></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("","---Select---");
							map.put("M", "Male");
							map.put("F", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%>
					</td>
				</tr>
				<tr>
					<th>Mobile No<font color="red">*</font></th>
					<td><input type="text" name="mobileNo"  class="form-control"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Date Of Birth<font color="red">*</font></th>
					<td><input id="datepicker" type="text" name="dob"  class="form-control"
						readonly="readonly" placeholder="Select DOB"
						value="<%=DataUtility.getDateString(bean.getDob())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"  class="form-control"
						value="<%=MyProfileCtl.OP_SAVE%>"> &nbsp; <input
						type="submit" name="operation" value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD%>">
						&nbsp;</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
<%@ include file="Footer.jsp"%>