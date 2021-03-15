<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%----style="text-decoration: none;(hyperlink pr underline htane ke liye) --%>
<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.ctl.LoginCtl"%>
<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@page import="in.co.rays.proj4.ctl.ORSView"%>
<%@page import="java.util.Calendar"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/ORSProject4/css/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="/ORSProject4/js/jquery-1.12.4.js"></script>
<script src="/ORSProject4/js/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : 'mm/dd/yy',
			yearRange : "-57:-18",
			defaultDate : "01/01/2000",

		});
	});

	$(function() {
		$("#datesun").datepicker({
			beforeShowDay : function(dt) {
				return [ dt.getDay() == 0 ? false : true ] ///// to disable sunday
			},
			changeMonth : true,
			changeYear : true,
			stepMonths : 12,
			yearRange : '+0:+5',
			//yearRange:"+10:"

			defaultDate : "01/01/2019"
		});
	});

	$(function() {
		$("#datefac").datepicker({
			beforeShowDay : function(dt) {
				return [ dt.getDay() == 0 ? false : true ] ///// to disable sunday
			},
			changeMonth : true,
			changeYear : true,
			yearRange : "-57:+0",
			defaultDate : "01/01/2018"
		//defaultDate:"01/01/1999"
		});
	});

	function selectAll(source) {
		checkboxes = document.getElementsByName('ids');
		for (var i = 0, n = checkboxes.length; i < n; i++) {
			checkboxes[i].checked = source.checked;
		}
	}

	function selectone(so) {
		checkboxes = document.getElementById('mainbox');
		unbox = document.getElementsByName('ids');
		var box = false;
		for (var i = 0, n = unbox.length; i < n; i++) {
			if (unbox[i].checked == true) {
				box = true;
			} else {
				box = false;
				break;
			}
		}
		checkboxes.checked = box;
	}
</script>

<style>
.header {
	/* background-image: url("img/bg6.jpg"); */
	background-color: #ffffff;
	position: relative;
}

.form-control {
	margin-top: 10px;
}

.tbl {
	border-collapse: collapse;
	width: 100%;
}

.tbl td, .tbl th {
	border: 1px solid #A9A9A9;
	padding: 8px;
	text-align: center;
}

.tbl th {
	padding-top: 6px;
	padding-bottom: 6px;
	text-align: center;
	/* background-color: #A9A9A9;  */
	color: black;
}

#customers {
	border-collapse: collapse;
	width: 70%;
	margin-right: 32px;
}

#customers td, #customers th {
	border: 1px solid #A9A9A9;
	padding: 8px;
	text-align: center;
}

#customer {
	border-collapse: collapse;
	width: 70%;
	margin-top: 25px;
	margin-right: 32px;
}

#customer td, #customer th {
	border: 1px solid #A9A9A9;
	padding: 8px;
	text-align: center;
}

.heading {
	height: 16px;
}

.msgval {
	height: 5px;
	padding-bottom: 25px;
}
</style>

</head>

<body>

	<div class="header">

		<%
			UserBean userBean = (UserBean) session.getAttribute("user");

			String welcomeMsg = "Hi,";
			// if(userBean !=null) {
			//		userLoggedIn=true;
			// else {
			//		userLoggedIn=false; }
			boolean userLoggedIn = userBean != null;

			if (userLoggedIn) {
				String role = (String) session.getAttribute("role");
				welcomeMsg += userBean.getFirstName() + " (" + role + ")";
			} else {
				welcomeMsg += "Guest";
			}
		%>


		<table style="width: 100%">
			<tr>
				<td width="90%"><a href="<%=ORSView.WELCOME_CTL%>"><b>Welcome</b></a>
					|| <%
					if (userLoggedIn) {
				%> <a
					href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><b>Logout</b></a>
					<%
						} else {
					%> <a href="<%=ORSView.LOGIN_CTL%>"><b>Login</b></a> <%
 	}
 %>
				</td>

				<td rowspan="2">
					<h1 align="right">
						<img src="<%=ORSView.APP_CONTEXT%>/img/customLogo.png" width="250"
							height="70">
					</h1>
				</td>
			</tr>

			<tr>
				<td>
					<h3><%=welcomeMsg%></h3>
				</td>
			</tr>

			<%
				if (userLoggedIn) {
			%>

			<tr>
				<td colspan="2"><a href="<%=ORSView.MY_PROFILE_CTL%>"><b>MyProfile</b></a>
					| <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><b>Change
							Password</b></a> | <%
					if (userBean.getRoleId() == RoleBean.ADMIN) {
				%> <a
					href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>GetMarksheet</b></a> | <a
					href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>Marksheet
							Merit List</b></a> | <a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add
							Marksheet</b></a> | <a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet
							List</b></a> | <a href="<%=ORSView.USER_CTL%>"><b>Add User</b></a> | <a
					href="<%=ORSView.USER_LIST_CTL%>"><b>User List</b></a> | <a
					href="<%=ORSView.COLLEGE_CTL%>"><b>Add College</b></a> | <a
					href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College List</b></a> | <a
					href="<%=ORSView.STUDENT_CTL%>"><b>Add Student</b></a> | <a
					href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a> | <a
					href="<%=ORSView.ROLE_CTL%>"><b>Add Role</b></a> | <a
					href="<%=ORSView.ROLE_LIST_CTL%>"><b>Role List</b></a> | <a
					href="<%=ORSView.COURSE_CTL%>"><b>Add Course</b></a> | <a
					href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a> | <a
					href="<%=ORSView.FACULTY_CTL%>"><b>Add Faculty</b></a> | <a
					href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a> | <a
					href="<%=ORSView.SUBJECT_CTL%>"><b>Add Subject</b></a> | <a
					href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject List</b></a>| <a
					href="<%=ORSView.TIME_TABLE_CTL%>"><b>Add TimeTable</b></a> | <a
					href="<%=ORSView.TIME_TABLE_LIST_CTL%>"><b>TimeTable List</b></a>|
					<a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank"><b>Java
							Doc</b></a> | <%
 	}
 %> <%
 	if (userBean.getRoleId() == RoleBean.STUDENT) {
 %> <a
					href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>GetMarksheet</b></a> | <a
					href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>Marksheet
							Merit List</b></a> | <%-- 	<a href="<%=ORSView.STUDENT_LIST_CTL%>"><b>StudentList</b></a> | 
			 	<a href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a> | 
			 	<a href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College List</b></a> | 
			 	<a href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a> | 
			 	<a href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject List</b></a> | 
			 	<a href="<%=ORSView.TIME_TABLE_LIST_CTL%>"><b>TimeTable List</b></a> |  --%>
					<a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank"><b>Java
							Doc</b></a> | <%
						}
					%> <%
 	if (userBean.getRoleId() == RoleBean.COLLAGE_SCHOOL) {
 %>
					<a href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>GetMarksheet</b></a> |
					<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>Marksheet
							Merit List</b></a> | <a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add
							Marksheet</b></a> | <a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet
							List</b></a> <a href="<%=ORSView.STUDENT_CTL%>"><b>Add Student</b></a> |
					<a href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a> | <a
					href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a> | <a
					href="<%=ORSView.TIME_TABLE_LIST_CTL%>"><b>TimeTable List</b></a> |
					<a href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a> | <a
					href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank"><b>Java
							Doc</b></a> | <%
 	}
 %> <%
 	if (userBean.getRoleId() == RoleBean.KIOSK) {
 %> <a
					href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>GetMarksheet</b></a> | <a
					href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>Marksheet
							Merit List</b></a> | <a href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College
							List</b></a> | <a href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course
							List</b></a> | <a href="<%=ORSView.TIME_TABLE_LIST_CTL%>"><b>TimeTable
							List</b></a> | <a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank"><b>Java
							Doc</b></a> | <%
 	}
 %> <%
 	if (userBean.getRoleId() == RoleBean.FACULTY) {
 %> <a
					href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>GetMarksheet</b></a> | <a
					href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>Marksheet
							Merit List</b></a> | <a href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student
							List</b></a> | <a href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject
							List</b></a> | <a href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course
							List</b></a> | <a href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty
							List</b></a> | <a href="<%=ORSView.TIME_TABLE_LIST_CTL%>"><b>TimeTable
							List</b></a> | <a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank"><b>Java
							Doc</b></a> | <%
 	}
 %></td>
			</tr>

			<%
				}
			%>
		</table>
	</div>
	<HR color='black'>
</body>
</html>