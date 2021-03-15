<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.RoleListCtl"%>
<%@page import="in.co.rays.proj4.ctl.BaseCtl"%>
<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role List</title>
</head>

<body>

	<div align="center"  style="height: 630px;">

		<form action="<%=ORSView.ROLE_LIST_CTL%>" method="post" name="frm">

			<jsp:useBean id="bean" class="in.co.rays.proj4.bean.RoleBean"
				scope="request"></jsp:useBean>
			<jsp:useBean id="model" class="in.co.rays.proj4.model.RoleModel"
				scope="request"></jsp:useBean>
			<jsp:useBean id="userbean" class="in.co.rays.proj4.bean.UserBean"
				scope="request"></jsp:useBean>

			<h1>Role List</h1>

			<table width="100%">
				<tr>
					<td align="center"><label>Name :</label> <input type="text"
						placeholder="Enter Roll Name" name="name"
						value="<%=ServletUtility.getParameter("name", request)%>">
						&nbsp; <input type="submit" name="operation"
						value="<%=RoleListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=RoleListCtl.OP_RESET%>">
					</td>
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
						value="<%=RoleListCtl.OP_BACK%>"></td>
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
					Iterator<RoleBean> it = list.iterator();
					while (it.hasNext()) {
						bean = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" name="ids"
						value="<%=bean.getId()%>"
						<%=(bean.getId() == 1) || (bean.getId() == 2) || (bean.getId() == 3) || (bean.getId() == 4)
						|| (bean.getId() == 5) ? "disabled" : ""%>></td>
					<td><%=index++%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getDescription()%></td>
					<%
						if ((bean.getId() == 1) || (bean.getId() == 2) || (bean.getId() == 3) || (bean.getId() == 4)
									|| (bean.getId() == 5)) {
					%>
						<td><a>-----</a> <%
 					} else {
 					%>
					<td><a href="RoleCtl?id=<%=bean.getId()%>">Edit</a></td>
					<%
						}
					%>
				</tr>
				<%
					}
					long i = bean.getId();

					System.out.println(model.nextPK() - 1);
					System.out.print("id= " + bean.getId());
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
						value="<%=RoleListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>

					<td><input type="submit" name="operation"
						value="<%=RoleListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						value="<%=RoleListCtl.OP_DELETE%>"></td>
					<%
						}
					%>

					<td align="right"><input type="submit" name="operation"
						value="<%=RoleListCtl.OP_NEXT%>"
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