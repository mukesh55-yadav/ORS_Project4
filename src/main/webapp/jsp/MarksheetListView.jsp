<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.ctl.MarksheetListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet List</title>
</head>

<html>
<body>

	<div align="center"  style="height: 620px;">

		<h1>Marksheet List</h1>

		<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="POST">
		
		<jsp:useBean id="marksheetmodel" class="in.co.rays.proj4.model.MarksheetModel"
				scope="request"></jsp:useBean>
		<jsp:useBean id="marksheetbean" class="in.co.rays.proj4.bean.MarksheetBean"
				scope="request"></jsp:useBean>

		<table width="100%">
				<tr>
					<td align="center"><label> Name :</label> <input type="text"
						name="name"
						value="<%=ServletUtility.getParameter("name", request)%>">
						&emsp; <label>RollNo :</label> <input type="text" name="rollNo"
						value="<%=ServletUtility.getParameter("rollNo", request)%>">&emsp;
						<input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_SEARCH%>">&emsp; <input
						type="submit" name="operation" value="<%=MarksheetListCtl.OP_RESET%>"></td>
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
						value="<%=MarksheetListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
				} else {
			%>
			<table border="1" width="100%" id="college" class="tbl">
				<tr bgcolor="#BFC9CA">
					<th>Select</th>
					<th>S.No</th>
					<th>RollNo</th>
					<th>Name</th>
					<th>Physics</th>
					<th>Chemistry</th>
					<th>Maths</th>
					<th>Total(Out of 300)</th>
					<th>Percentage(%)</th>
					<th>Grade</th>
					<th>Division</th>
					<th>Result</th>
					<th>Edit</th>
				</tr>
				
				<%
					}
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List l = ServletUtility.getList(request);
					Iterator<MarksheetBean> it = l.iterator();

					while (it.hasNext()) {

						MarksheetBean bean = it.next();
						String grade = null;
						String result = null;
						long phyMarks = DataUtility.getLong(bean.getPhysics());
						long chemMarks = DataUtility.getLong(bean.getChemistry());
						long MathMarks = DataUtility.getLong(bean.getMaths());
						long totalMarks = (phyMarks + chemMarks + MathMarks);
						float percentage = ((totalMarks) * 100) / 300;
						
						if (phyMarks >= 35 && chemMarks >= 35 && MathMarks >= 35) {
							if (totalMarks >= 195) {
								grade = "A";
								result = "Pass";
							} else if (totalMarks < 195 && totalMarks >= 150) {
								grade = "B";
								result = "Pass";
							} else {
								grade = "C";
								result = "Pass";
							}

						} else {
							grade = "D";
							result = "Fail";
						}
						
						String div = null;
						if (percentage >= 60) {
							div = "I";
						} else if (percentage >= 45 && percentage < 60) {
							div = "II";
						} else if (percentage >= 33 && percentage < 45) {
							div = "III";
						}else if (percentage <33){
							div="Fail";
						}
				%>
				<tr align="center">
					<td><input type="checkbox" name="ids" id="mainbox"
						onchange="selectone(this)" value="<%=bean.getId()%>"
						<%=((userBean.getRoleId() == RoleBean.ADMIN)) ? "" : "disabled"%>></td>
					<td><%=index++%></td>
					<td><%=bean.getRollNo()%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getPhysics()%></td>
					<td><%=bean.getChemistry()%></td>
					<td><%=bean.getMaths()%></td>
					<td><%=totalMarks%></td>
					<td><%=percentage%></td>
					<td><%=grade%></td>
					<td><%=div%></td>
					<%
						if (div.equalsIgnoreCase("Fail")) {
					%>
						<td style="color: red;">FAIL</td>
					<%
						} else {
					%>
						<td style="color: green;">PASS</td>
					<%
						}
					%>
					
					<%
						if (userBean.getRoleId() == 1) {
					%>
						<td><a href="MarksheetCtl?id=<%=bean.getId()%>">Edit</a></td>
					<%
						} else {
					%>
						<td><a>----</a></td>
					<%
						}
					%>
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
						value="<%=MarksheetListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>

					<td><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_DELETE%>"></td>
					<%
						}
					%>

					<td align="right"><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_NEXT%>"
						<%=((list.size() < pageSize) || marksheetmodel.nextPK() - 1 == marksheetbean.getId()) ? "disabled" : ""%>></td>
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