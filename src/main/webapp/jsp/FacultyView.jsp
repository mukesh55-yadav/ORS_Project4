<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.FacultyCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty View</title>
</head>

<body>

	<form action="<%=ORSView.FACULTY_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.FacultyBean"
			scope="request"></jsp:useBean>
		<jsp:useBean id="facultyModel"
			class="in.co.rays.proj4.model.FacultyModel" scope="request"></jsp:useBean>
		<jsp:useBean id="collegeModel"
			class="in.co.rays.proj4.model.CollegeModel" scope="request"></jsp:useBean>
		<center  style="height: 390px;">
			<%
				if (bean.getId() > 0) {
			%>
			<h1>Update Faculty</h1>
			<%
				} else {
			%>
			<h1>Add Faculty</h1>
			<%
				}
			%>

			<%
				List subList = (List) request.getAttribute("subjectList");
				List collegeList = (List) request.getAttribute("collegeList");
			%>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>

				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H2>

			<table align="center" style="margin-left: 38%;">

				<tr>
					<th>First Name<font color="red">*</font></th>
					<td><input type="text" name="firstName"
						placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>

				<tr>
					<th>Last Name<font color="red">*</font></th>
					<td><input type="text" name="lastName" class="form-control"
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</td>
				</tr>

				<tr>
					<th>Login Id<font color="red">*</font></th>
					<td><input type="text" name="loginId" class="form-control"
						value="<%=DataUtility.getStringData(bean.getLoginId())%>"
						placeholder="Must be a Login_Id"
						style="background-color: #f2f3f4;"
						<%=(bean.getId() > 0) ? "readonly" : ""%>> <font
						color="red"><%=ServletUtility.getErrorMessage("loginId", request)%></font>
					</td>
				</tr>

				<tr>
					<th>Date of Joining<font color="red">*</font></th>
					<td><input type="text" name="doj" class="form-control"
						placeholder="Enter Date of Joining" readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDoj())%>"
						id="datefac"> <font color="red"><%=ServletUtility.getErrorMessage("doj", request)%></font>
					</td>
				</tr>


				<tr>
					<th>Mobile No<font color="red">*</font></th>
					<td><input type="text" name="mobileNo" class="form-control"
						placeholder="Enter MobileNo."
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
					</td>
				</tr>

				<tr>
					<th>College Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), collegeList)%>
						<font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font>
					</td>
				</tr>


				<tr>
					<th>Subject Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), subList)%>
						<font color="red"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
					</td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2">&emsp;&emsp;<input type="submit"
						name="operation" class="form-control"
						value="<%=(bean.getId() > 0) ? FacultyCtl.OP_UPDATE : FacultyCtl.OP_SAVE%>">
						&nbsp; <input type="submit" name="operation"
						value="<%=(bean.getId() > 0) ? FacultyCtl.OP_CANCEL : FacultyCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
		</center>

	</form>

</body>
</div>
<%@ include file="Footer.jsp"%>
</html>