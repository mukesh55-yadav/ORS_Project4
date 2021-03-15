<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.MarksheetCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet View</title>
</head>

<body>
	<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.MarksheetBean"
			scope="request"></jsp:useBean>

		<%
			List l = (List) request.getAttribute("studentList");
		%>

		<div align="center" style="height: 360px;">
		
			<%
				if (bean.getId() > 0) {
			%>

			<h1 align="center">Update Marksheet</h1>

			<%
				} else {
			%>

			<h1 align="center">Add Marksheet</h1>

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


			<table align="center" style="margin-left:39%;">
				<tr>
					<th>Rollno<font color="red">*</font></th>
					<td><input type="text" name="rollNo"
						placeholder="Enter RollNo."
						value="<%=DataUtility.getStringData(bean.getRollNo())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>> <font
						color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
				</tr>
				<tr>
					<th>Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("studentId", String.valueOf(bean.getStudentId()), l)%>
						<font color="red"><%=ServletUtility.getErrorMessage("studentId", request)%></font>
					</td>
				</tr>
				<tr>
					<th>Physics<font color="red">*</font></th>
					<td><input type="text" name="physics" class="form-control"
						placeholder="Enter Physics Marks"
						value="<%=DataUtility.getStringData(bean.getPhysics())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("physics", request)%></font></td>

				</tr>
				<tr>
					<th>Chemistry<font color="red">*</font></th>
					<td><input type="text" name="chemistry" class="form-control"
						placeholder="Enter Chemistry Marks"
						value="<%=DataUtility.getStringData(bean.getChemistry())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font></td>

				</tr>
				<tr>
					<th>Maths<font color="red">*</font></th>
					<td><input type="text" name="maths" class="form-control"
						placeholder="Enter Maths Marks"
						value="<%=DataUtility.getStringData(bean.getMaths())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("maths", request)%></font></td>


				</tr>

				<tr>
					<th></th>
					<td colspan="2">&emsp;&emsp;<input type="submit"
						name="operation" class="form-control"
						value="<%=(bean.getId() > 0) ? MarksheetCtl.OP_UPDATE : MarksheetCtl.OP_SAVE%>">
						&nbsp; <input type="submit" name="operation"
						value="<%=(bean.getId() > 0) ? MarksheetCtl.OP_CANCEL : MarksheetCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
<%@ include file="Footer.jsp"%>
</html>