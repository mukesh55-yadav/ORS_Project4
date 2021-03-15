<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Calendar"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Online Result System</title>
</head>
<body>
	<div id ="footer"></div>
	<div class ="contaniner" style ="position: absolute; bottom: 0px; left: 520px;"> </div>
</body>

<%
		Calendar c = Calendar.getInstance();
	%>
	<br><div
		style="position: fixed; height: 55px; bottom: 0; width: 100%; text-align: center; background-color: #ffffff">
		<HR color='black'>
		<H4 style="margin: 10px">
			 <b> &copy; <%=c.getWeekYear()%> Rays Technologies
			</b> 
		</H4>
		<HR color='black'>
	</div>
</br>
</html>

<!-- color : #FADBD8 -->