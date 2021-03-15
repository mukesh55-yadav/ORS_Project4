<%@page import="java.util.GregorianCalendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.GetMarksheetCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Marksheet</title>
</head>

<body>
	<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.MarksheetBean"
			scope="request"></jsp:useBean>

		<center  style="height: 540px;">
			<h1>Get Marksheet</h1>

			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</H2>

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table align="center" style="margin-left: 38%;">
				<tr>
					<th>RollNo :</th>&emsp;
					<td><input type="text" name="rollNo"
						value="<%=ServletUtility.getParameter("rollNo", request)%>">&emsp;
						<input type="submit" name="operation"
						value="<%=GetMarksheetCtl.OP_GO%>">&nbsp; <input
						type="submit" name="operation"
						value="<%=GetMarksheetCtl.OP_RESET%>">&nbsp; <font
						color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
				</tr>
			</table>
			<br><br><br>

			<%
				if (bean.getRollNo() != null && bean.getRollNo().trim().length() > 0) {

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
							grade = "c";
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
			
			<table border=1 id="customers">
				<tr>
					<td colspan="4" bgcolor="#BFC9CA"><b>Provisional Marksheet</b></td>
				</tr>
				<tr>
					<td>Rollno :</td>
					<td colspan="3"><%=DataUtility.getStringData(bean.getRollNo())%></td>
				</tr>
				<tr>
					<td class="space">Name :</td>
					<td colspan="3" class="space"><%=DataUtility.getStringData(bean.getName())%></td>
				</tr>
				<tr>
					<td><b>Subject:</b></td>
					<td><b>Maximum Marks:</b></td>
					<td><b>Minimum Marks:</b></td>
					<td><b>Obtained Marks:</b></td>
				</tr>
				<tr>
					<td>Physics</td>
					<td>100</td>
					<td>35</td>
					<td><%=DataUtility.getStringData(bean.getPhysics())%></td>
				</tr>
				<tr>
					<td>Chemistry</td>
					<td>100</td>
					<td>35</td>
					<td><%=DataUtility.getStringData(bean.getChemistry())%></td>
				</tr>
				<tr>
					<td>Math</td>
					<td>100</td>
					<td>35</td>
					<td><%=DataUtility.getStringData(bean.getMaths())%></td>
				</tr>
				<tr>
					<td>Total</td>
					<td>300</td>
					<td></td>
					<td><%=totalMarks%></td>
				</tr>
			</table>
			
			<br>
			<table id="customers">
				<tr>
					<th>Result</th>
					<%
						if ("PASS".equalsIgnoreCase(result)) {
					%>
					<td align="center"><font color="green"><%=result%></font></td>
					<th>Grade:</th>
					<td align="center"><font color="green"><%=grade%></font></td>
					<%
						} else {
					%>

					<td align="center"><font color="red"><%=result%></font></td>
					<th>Grade:</th>
					<td align="center"><font color="green"><%=grade%></font></td>
					<%
						}
					%>
					
					<th>Division:</th>
					<td><%=div%></td>
					
					<th>Percentage:</th>
					<td align="center"><%=percentage%>%</td>
					
				</tr>
			</table>
			<%
				}
			%>
		</center>
	</form>
</body>

<%@ include file="Footer.jsp"%>
</html>