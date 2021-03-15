<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.ChangePasswordCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password</title>
</head>
<body>
	<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<div align="center" style="height: 360px;">
			<h1>Change Password</h1>
			
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
					<th>Old Password<font color="red">*</font></th>
					<td><input type="password" name="oldPassword"  class="form-control"
						value=<%=DataUtility.getString(request.getParameter("oldPassword") == null ? ""
					: DataUtility.getString(request.getParameter("oldPassword")))%>><font
						color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
				</tr>

				<tr>
					<th>New Password<font color="red">*</font></th>
					<td><input type="password" name="newPassword"  class="form-control"
						value=<%=DataUtility.getString(request.getParameter("newPassword") == null ? ""
					: DataUtility.getString(request.getParameter("newPassword")))%>><font
						color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
				</tr>

				<tr>
					<th>Confirm Password<font color="red">*</font></th>
					<td><input type="password" name="confirmPassword"  class="form-control"
						value=<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? ""
					: DataUtility.getString(request.getParameter("confirmPassword")))%>><font
						color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>

				<tr class="form-control">
					<th></th>
					<td colspan="2"><input type="submit" name="operation"  class="form-control"
						value="<%=ChangePasswordCtl.OP_SAVE%>"> 
						&nbsp; <input type="submit" name="operation"  class="form-control"
						value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>"> &nbsp;</td>
				</tr>

			</table>
			
		</div>
	</form>
</body>
</html>
<%@ include file="Footer.jsp"%>