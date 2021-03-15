<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.UserListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.model.RoleModel"%>
<%@include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User List</title>
</head>

<body>

	<div align="center" style="height: 630px;">

		<form action="<%=ORSView.USER_LIST_CTL%>" method="post" name="frm">

			<jsp:useBean id="model" class="in.co.rays.proj4.model.UserModel"
				scope="request"></jsp:useBean>
			<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
				scope="request"></jsp:useBean>
			<jsp:useBean id="roleModel" class="in.co.rays.proj4.model.RoleModel"
				scope="request"></jsp:useBean>
			<jsp:useBean id="roleBean" class="in.co.rays.proj4.bean.RoleBean"
				scope="request"></jsp:useBean>

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<%
				List roleList = (List) request.getAttribute("roleList");
			%>

			<h1>User List</h1>

			<table width="100%">
				<tr>
					<td align="center"><label>First Name :</label> <input
						type="text" name="firstName" placeholder="Enter Name"
						value="<%=ServletUtility.getParameter("firstName", request)%>">
						&emsp; <label>Login Id :</label> <input type="text" name="login"
						placeholder="Must be a Login_Id"
						value="<%=ServletUtility.getParameter("login", request)%>">
						&emsp;<label>Role :</label> <%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), roleList)%>
						&emsp; <input type="submit" name="operation"
						value="<%=UserListCtl.OP_SEARCH%>"> &emsp; <input
						type="submit" name="operation" value="<%=UserListCtl.OP_RESET%>"></td>
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
						value="<%=UserListCtl.OP_BACK%>"></td>
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
					<th>First Name</th>
					<th>Last Name</th>
					<th>Login Id</th>
					<th>Gender</th>
					<th>DOB</th>
					<th>Role</th>
					<th>Edit</th>
				</tr>

				<%
					}
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;
					Iterator<UserBean> it = list.iterator();
					while (it.hasNext()) {
						bean = it.next();
						RoleBean rBean = roleModel.findByPK(bean.getRoleId());
				%>
				<tr align="center">
					<td><input type="checkbox" name="ids" onclick="selectone(this)" value="<%=bean.getId()%>"
							<%=(bean.getRoleId() == 1) ? "disabled": " "%>></td>
					<td><%=index++%></td>
					<td><%=bean.getFirstName()%></td>
					<td><%=bean.getLastName()%></td>
					<td><%=bean.getLogin()%></td>
					<td><%=bean.getGender()%></td>
					<td><%=bean.getDob()%></td>
					<td><%=rBean.getName()%></td>
					<%
						if (bean.getRoleId() != 1) {
					%>
					<td><a href="UserCtl?id=<%=bean.getId()%>">Edit</a></td>
					<%
						} else {
					%>
					<td>----</td>
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
						value="<%=UserListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>

					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_DELETE%>"></td>
					<%
						}
					%>

					<td align="right"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"
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