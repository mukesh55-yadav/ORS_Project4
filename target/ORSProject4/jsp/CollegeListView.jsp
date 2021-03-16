<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.CollegeListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College List</title>
</head>

<body>

	<div align="center" style="height: 620px;">

		<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="post">

			<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CollegeBean"
				scope="request"></jsp:useBean>
			<jsp:useBean id="model" class="in.co.rays.proj4.model.CollegeModel"
				scope="request"></jsp:useBean>

			<h1>College List</h1>

			<table style="width: 100%">
				<tr>
					<td align="center"><label> Name :</label> <input type="text"
						name="name" placeholder="Enter College Name"
						value="<%=ServletUtility.getParameter("name", request)%>">
						&emsp; <label>City :</label> <input type="text" name="city"
						placeholder="Enter College City"
						value="<%=ServletUtility.getParameter("city", request)%>">
						&emsp; <input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_SEARCH%>"> &emsp; <input
						type="submit" name="operation"
						value="<%=CollegeListCtl.OP_RESET%>"></td>

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
						value="<%=CollegeListCtl.OP_BACK%>"></td>
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
					<th>Name</th>
					<th>Address</th>
					<th>State</th>
					<th>City</th>
					<th>PhoneNo</th>
					<th>Edit</th>
				</tr>
				<%
					}
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;
					Iterator<CollegeBean> it = list.iterator();
					while (it.hasNext()) {
						bean = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" name="ids" id="mainbox"
						onchange="selectone(this)" value="<%=bean.getId()%>"
						<%=((userBean.getRoleId() == RoleBean.ADMIN)) ? "" : "disabled"%>></td>
					<td><%=index++%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getAddress()%></td>
					<td><%=bean.getState()%></td>
					<td><%=bean.getCity()%></td>
					<td><%=bean.getPhoneNo()%></td>
					<%
						if (userBean.getRoleId() == 1) {
					%>
						<td><a href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value="<%=CollegeListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>

					<td><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_DELETE%>"></td>
					<%
						}
					%>

					<td align="right"><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_NEXT%>"
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

<%-- <%if(pageNo==1 ) {%>
		<td><input style="color: maroon; font-family: cursive;"
			type="submit" name="operation" disabled="disabled"
			value="<%=CollegeListCtl.OP_PREVIOUS%>"></td>
		<%} else { %>
		<td><input style="color: maroon; font-family: cursive;"
			type="submit" name="operation" value="<%=CollegeListCtl.OP_PREVIOUS%>"></td>
		<% } %>
 --%>

<%-- <% if ((model.nextPK() - 1) == bean.getId() || list.size() < pageSize) { %>
		<td align="right"><input
		style="color: maroon; font-family: cursive;" type="submit"
		name="operation" disabled="disabled"
		value="<%=CollegeListCtl.OP_NEXT%>"></td>
	 <% } else { %>
		<td align="right"><input
		style="color: maroon; font-family: cursive;" type="submit"
		name="operation" value="<%=CollegeListCtl.OP_NEXT%>"></td>
	<% } %> 
--%>

