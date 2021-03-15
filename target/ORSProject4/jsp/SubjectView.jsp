<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.ctl.SubjectCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.ctl.ORSView"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subject View</title>
</head>

<body>
	<form action="<%=ORSView.SUBJECT_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SubjectBean"
			scope="request"></jsp:useBean>
		<jsp:useBean id="model" class="in.co.rays.proj4.model.SubjectModel"
			scope="request"></jsp:useBean>

		<center  style="height: 360px;">
			<%
				List cList = (List) request.getAttribute("courseList");
			%>
			<%
				if (bean.getId() > 0) {
			%>
			<h1 align="center">Update Subject</h1>
			<%
				} else {
			%>
			<h1 align="center">Add Subject</h1>
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
			<input type="hidden" name="modifiedBy"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table align="center" style="margin-left: 38%">
				<tr>
					<th>Course Name:<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), cList)%>
						<font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font>
					</td>
				</tr>

				<tr>
					<th>Subject Name<font color="red">*</font></th>
					<td><input class="form-control"
						type="text" name="subjectName" placeholder="Enter Subject Name"
						value="<%=DataUtility.getStringData(bean.getSubjectName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("subjectName", request)%></font>
					</td>
				</tr>

				<tr>
					<th>Description<font color="red">*</font></th>
					<td><textarea style="resize: none; width: 168px; height: 40px;" rows="4"
							cols=20  name="description" class="form-control"
							placeholder="Enter Course Name" value=""><%=DataUtility.getStringData(bean.getDescription())%></textarea>
						<font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font>
					</td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2">&emsp;&emsp;<input type="submit"
						name="operation" class="form-control"
						value="<%=(bean.getId() > 0) ? SubjectCtl.OP_UPDATE : SubjectCtl.OP_SAVE%>">
						&nbsp; <input type="submit" name="operation"
						value="<%=(bean.getId() > 0) ? SubjectCtl.OP_CANCEL : SubjectCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
		</center>
	</form>
	</div>
</body>
</div>
<%@include file="Footer.jsp"%>
</html>
