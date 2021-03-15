<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.co.rays.proj4.ctl.SubjectListCtl"%>
<%@page import="in.co.rays.proj4.bean.SubjectBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subject List View</title>
</head>

<body>

	<div align="center"  style="height: 630px;">

		<form action="<%=ORSView.SUBJECT_LIST_CTL%>" method="post" name="frm">

			<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SubjectBean"
				scope="request"></jsp:useBean>
			<jsp:useBean id="model" class="in.co.rays.proj4.model.SubjectModel"
				scope="request"></jsp:useBean>

			<%
				List cList = (List) request.getAttribute("courseList");
			%>

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<h1>Subject List</h1>

			<table width="100%">
				<tr>
					<td align="center"><label>Course Name :</label> <%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), cList)%>
						&emsp; <label>Subject Name :</label> <input type="text"
						name="subjectName" placeholder="Enter Subject Name"
						value="<%=ServletUtility.getParameter("subjectName", request)%>">
						&emsp; <input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_SEARCH%>"> &emsp; <input
						type="submit"
						name="operation" value="<%=SubjectListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H2>
			<br>

			<%
				List list = ServletUtility.getList(request);
				if (list.size() == 0) {
			%>
			<table>
				<tr>
					<td><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
				} else {
			%>
			<table class="tbl">
				<tr bgcolor="#BFC9CA">
					<th><input type="checkbox" id="mainbox"
						onchange="selectAll(this)">Select All</th>		
					<th>S.No</th>
					<th>Course Name</th>
					<th>Subject Name</th>
					<th>Description</th>
					<th>Edit</th>
				</tr>
				<%
					}
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;
					Iterator<SubjectBean> it = list.iterator();
					while (it.hasNext()) {
						bean = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" name="ids" id="mainbox"
						onchange="selectone(this)" value="<%=bean.getId()%>"
						<%=((userBean.getRoleId() == RoleBean.ADMIN)) ? "" : "disabled"%>></td>			
					<td><%=index++%></td>
					<td><%=bean.getCourseName()%></td>
					<td><%=bean.getSubjectName()%></td>
					<td><%=bean.getDescription()%></td>
					<%
						if (userBean.getRoleId() == 1) {
					%>
						<td><a href="SubjectCtl?id=<%=bean.getId()%>">Edit</a></td>
					<%
						} else {
					%>
						<td><a>----</a></td>
					<%
						}
					%>
				</tr>
				<%
					}
				%>
			</table>
			<br>
			
			<%
				if (list.size() != 0) {
			%>

			<table style="width: 100%">

				<colgroup>
					<col style="width: 30%">
					<col style="width: 35%">
					<col style="width: 10%">
					<col style="width: 25%">
				</colgroup>

				<tr>
					<td align="left"><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>

					<td><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_DELETE%>"></td>
					<%
						}
					%>

					<td align="right"><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_NEXT%>"
						<%=((list.size() < pageSize) || model.nextPk() - 1 == bean.getId()) ? "disabled" : ""%>></td>
				</tr>
			</table>
			<%
				}
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
	</div>
</body>
</html>
<%@include file="Footer.jsp"%>