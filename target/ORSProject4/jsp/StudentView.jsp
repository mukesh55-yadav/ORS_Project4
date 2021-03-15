<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.bean.CollegeBean"%>
<%@page import="in.co.rays.proj4.ctl.StudentCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.StudentBean"%>
<%@page import="java.util.Iterator"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<title>Student View</title>
</head>

<body>
	<form action="<%=ORSView.STUDENT_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.StudentBean"
			scope="request"></jsp:useBean>

		<%
			List l = (List) request.getAttribute("collegeList");
		%>

		<center  style="height: 360px;">
			<%
				if (bean.getId() > 0) {
			%>
			<h1>Update Student</h1>
			<%
				} else {
			%>
			<h1>Add Student</h1>
			<%
				}
			%>

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

			<table align="center" style="margin-left: 38%">
				<tr>
					<th>First Name<font color="red">*</font></th>
					<td><input
						type="text" name="firstName" placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th>Last Name<font color="red">*</font></th>
					<td><input
						type="text" name="lastName" placeholder="Enter Last Name" class="form-control"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th>EmailId<font color="red">*</font></th>
					<td><input
						type="text" name="email" placeholder="Must be an Email_Id" class="form-control"
						value="<%=DataUtility.getStringData(bean.getEmail())%>" style="background-color: #f2f3f4;"
						<%=(bean.getId() > 0) ? "readonly" : ""%>><font
						color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font></td>
				</tr>
				<tr>
					<th>Mobile No<font color="red">*</font></th>
					<td><input type="text" name="mobileNo" 
						placeholder="Enter MobileNo." class="form-control"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>

				<tr>

					<!-- role is dynamic preload bcz data is coming from database  -->
					<th>College List<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), l)%>
						<font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font>
					</td>

				</tr>
				<tr>
					<th>Date Of Birth<font color="red">*</font></th>
					<td><input type="text" placeholder="(mm/dd/yyyy)" name="dob"
						readonly="readonly" id="datepicker" class="form-control"
						value="<%=DataUtility.getDateString(bean.getDob())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2"> &emsp;&nbsp;<input type="submit"
						name="operation"  class="form-control"
						value="<%=(bean.getId() > 0) ? StudentCtl.OP_UPDATE : StudentCtl.OP_SAVE%>">
                         &emsp;&nbsp;
						<input type="submit" name="operation" class="form-control"
						value="<%=(bean.getId() > 0) ? StudentCtl.OP_CANCEL : StudentCtl.OP_RESET%>">
					</td>
				</tr>

			</table>
		</center>
	</form>
</body>
</html>
<%@ include file="Footer.jsp"%>