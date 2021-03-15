<%@page import="in.co.rays.proj4.bean.TimeTableBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.TimeTableCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tentative TimeTable</title>
</head>

<body>
	<form action="<%=ORSView.TIME_TABLE_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TimeTableBean"
			scope="request"></jsp:useBean>
		<jsp:useBean id="model" class="in.co.rays.proj4.model.TimeTableModel"></jsp:useBean>
		<center  style="height: 360px;">
			<%
				List cList = (List) request.getAttribute("courseList");
				List sList = (List) request.getAttribute("subjectList");
			%>

			<%
				if (bean.getId() > 0) {
			%>
			<h1 align="center">Update Timetable</h1>
			<%
				} else {
			%>
			<h1 align="center">Add Timetable</h1>
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

			<table align="center" style="margin-left: 38%">
				<tr>
					<th>Course Name:<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), cList)%>
						<font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font>
					</td>
				</tr>

				<tr>
					<th>Subject Name:<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), sList)%>
						<font color="red"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
					</td>
				</tr>

				<tr>
					<th>Exam Time:<font color="red">*</font></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("","--- Select Time ---");
							map.put("08:00 AM To 10:00 AM", "08:00 AM To 10:00 AM");
							map.put("12:00 PM To 02:00 PM", "12:00 PM To 02:00 PM");
							map.put("03:00 PM To 05:00 PM", "03:00 PM To 05:00 PM");
							String examTimeList = HTMLUtility.getList("examTime", bean.getExamTime(), map);
						%> <%=examTimeList%> <font color="red"><%=ServletUtility.getErrorMessage("examTime", request)%></font>
					</td>
				</tr>

				<tr>
					<th>Exam Date:<font color="red">*</font></th>
					<td><input class="form-control"
						type="text" placeholder="Enter Exam Date" name="examDate"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getExamDate())%>"
						id="datesun"> <font color="red"><%=ServletUtility.getErrorMessage("examDate", request)%></font>
					</td>
				</tr>
				
				<tr>
				<th></th>
				<td colspan="2">&emsp;&emsp;<input type="submit"
					name="operation" class="form-control"
					value="<%=(bean.getId() > 0) ? TimeTableCtl.OP_UPDATE : TimeTableCtl.OP_SAVE%>">
					&nbsp; <input type="submit" name="operation"
					value="<%=(bean.getId() > 0) ? TimeTableCtl.OP_CANCEL : TimeTableCtl.OP_RESET%>">
				</td>
			</tr>
				
			</table>


		</center>
	</form>

</body>
</div>
<%@include file="Footer.jsp"%>

</html>