<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj4.ctl.CollegeCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College View</title>
</head>

<body>
	<center style="height: 360px;">
		<form action="<%=ORSView.COLLEGE_CTL%>" method="POST">
     			<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CollegeBean"
				scope="request"></jsp:useBean>
        <!--  <div class="heading"> -->
			<%
				if (bean.getId() > 0) {
			%>
			<h1 align="center">Update College</h1>
			<%
				} else {
			%>
			<h1 align="center">Add College</h1>
			<%
				}
			%>
		<!-- 	</div> -->
         <!--   <div class="msgval"> -->
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>		
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H2>
		<!-- 	</div> -->

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table align="center" style="margin-left: 39%;">
				<tr>
					<th>Name<font color="red">*</font></th>
					<td><input type="text" name="name"
						placeholder="Enter College Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>

				<tr>
					<th>Address<font color="red">*</font></th>
					<td><textarea
							style="resize: none; width: 168px; height: 40px;" rows="4"
							cols=20 name="address" class="form-control"
							placeholder="Enter College Address"><%=DataUtility.getStringData(bean.getAddress())%></textarea><font
						color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font>
					</td>
				</tr>

				<tr>
					<th>State<font color="red">*</font></th>
					<td><input type="text" name="state" class="form-control"
						placeholder="Enter College State"
						value="<%=DataUtility.getStringData(bean.getState())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
				</tr>
				<tr>
					<th>City<font color="red">*</font></th>
					<td><input type="text" name="city" class="form-control"
						placeholder="Enter College City"
						value="<%=DataUtility.getStringData(bean.getCity())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
				</tr>
				<tr>
					<th>PhoneNo<font color="red">*</font></th>
					<td><input type="text" name="phoneNo" class="form-control"
						placeholder="Enter College PhoneNo"
						value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2">&emsp;&emsp;<input type="submit"
						name="operation" class="form-control"
						value="<%=(bean.getId() > 0) ? CollegeCtl.OP_UPDATE : CollegeCtl.OP_SAVE%>">
						&nbsp; <input type="submit" name="operation"
						value="<%=(bean.getId() > 0) ? CollegeCtl.OP_CANCEL : CollegeCtl.OP_RESET%>">
					</td>
				</tr>

			</table>
		</form>
	</center>
</body>

</html>
<%@ include file="Footer.jsp"%>

<%-- <tr>
	<th></th>
	<%
		if (bean.getId() > 0) {
	%>
	<td colspan="2"><input type="submit" class="form-control"
		name="operation" value="<%=CourseCtl.OP_UPDATE%>">&emsp;
		&emsp; <input type="submit" class="form-control" name="operation"
		value="<%=CourseCtl.OP_CANCEL%>"></td>
	<%
		} else {
	%>
	<td colspan="2">&emsp;&nbsp;<input type="submit"
		class="form-control" name="operation" value="<%=CourseCtl.OP_SAVE%>">&emsp;
		&emsp; <input type="submit" name="operation"
		value="<%=CourseCtl.OP_RESET%>"></td>

	<%
		}
	%>

</tr> --%>