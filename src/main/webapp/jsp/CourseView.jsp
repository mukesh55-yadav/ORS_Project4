<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.CourseCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.ctl.ORSView"%>
<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course View</title>
</head>

<body>
	<form action="<%=ORSView.COURSE_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CourseBean"
			scope="request"></jsp:useBean>
		<center  style="height: 360px;">
			<%
				List l = (List) request.getAttribute("courseList");
			%>
			<%
				if (bean.getId() > 0) {
			%>
				<h1>Update Course</h1>
			<%
				} else {
			%>
				<h1>Add Course</h1>
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

			<table align="center" style="margin-left: 38%;">
				<tr>
					<th>Course<font color="red">*</font></th>
					<td><input type="text" 
						placeholder="Enter Course Name" name="courseName"
						value="<%=DataUtility.getStringData(bean.getCourseName())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("courseName", request)%></font>
					</td>
					<%-- <td><%=HTMLUtility.getList("courseName", bean.getCourseName(), l)%></td> --%>
				</tr>

				<tr>

					<th>Description<font color="red">*</font></th>
					<td><textarea style="resize: none; width: 168px; height: 40px;" rows="4"
							cols=20 name="description" class="form-control"
							placeholder="Enter Course Name"><%=DataUtility.getStringData(bean.getDescription())%></textarea>
						<font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font>
					</td>
				</tr>
				
				<tr>
					<th></th>
					<td colspan="2"> &emsp;&nbsp;<input type="submit"
						name="operation"  class="form-control"
						value="<%=(bean.getId() > 0) ? CourseCtl.OP_UPDATE : CourseCtl.OP_SAVE%>">
                         &emsp;&nbsp;
						<input type="submit" name="operation" class="form-control"
						value="<%=(bean.getId() > 0) ? CourseCtl.OP_CANCEL : CourseCtl.OP_RESET%>">
					</td>
				</tr>

			</table>
		</center>
	</form>
</body>
</html>
<%@include file="Footer.jsp"%>