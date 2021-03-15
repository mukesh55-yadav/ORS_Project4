<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.StudentListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student List</title>
</head>

<body>

	<div align="center" style="height: 620px;">

		<form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post" name="frm">

			<jsp:useBean id="bean" class="in.co.rays.proj4.bean.StudentBean"
				scope="request"></jsp:useBean>
			<jsp:useBean id="model" class="in.co.rays.proj4.model.StudentModel"
				scope="request"></jsp:useBean>

			<h1>Student List</h1>

			<table width="100%">
				<tr>
					<td align="center"><label> First Name :</label> <input
						type="text" name="firstName" placeholder="Enter First Name"
						value="<%=ServletUtility.getParameter("firstName", request)%>">
						&emsp; <label>Last Name :</label> <input type="text"
						name="lastName" placeholder="Enter Last Name"
						value="<%=ServletUtility.getParameter("lastName", request)%>">
						&emsp; <label>Email_Id :</label> <input name="email"
						placeholder="Must be an Email_Id"
						value="<%=ServletUtility.getParameter("email", request)%>">
						&emsp; <input type="submit" name="operation"
						value="<%=StudentListCtl.OP_SEARCH%>"> &emsp; <input
						type="submit" name="operation"
						value="<%=StudentListCtl.OP_RESET%>"></td>
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
						value="<%=StudentListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
				} else {
			%>
			<table class="tbl">
				<tr bgcolor="#BFC9CA">
					<th><input type="checkbox" id="mainbox"
						onchange="selectAll(this)">Select All</th>
					<th>S.No.</th>
					<th>First Name.</th>
					<th>Last Name.</th>
					<th>Date Of Birth.</th>
					<th>Mobile No.</th>
					<th>Email ID.</th>
					<th>Edit</th>
				</tr>
				<%
					}
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;
					Iterator<StudentBean> it = list.iterator();
					while (it.hasNext()) {
						bean = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" name="ids" id="mainbox"
						onchange="selectone(this)" value="<%=bean.getId()%>"
						<%=((userBean.getRoleId() == RoleBean.ADMIN)) ? "" : "disabled"%>></td>
					<td><%=index++%></td>
					<td><%=bean.getFirstName()%></td>
					<td><%=bean.getLastName()%></td>
					<td><%=bean.getDob()%></td>
					<td><%=bean.getMobileNo()%></td>
					<td><%=bean.getEmail()%></td>
					<%
						if (userBean.getRoleId() == 1) {
					%>
					<td><a href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value="<%=StudentListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>

					<td><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_DELETE%>"></td>
					<%
						}
					%>

					<td align="right"><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_NEXT%>"
						<%=((list.size() < pageSize) || model.nextPK() - 1 == bean.getId()) ? "disabled" : ""%>></td>
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