<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Given sentence is:<%=request.getAttribute("sentence") %><br>
Output is:
<br>
JSON:
<br>
<%=request.getAttribute("tmp") %>
<br>
<%=request.getAttribute("kd1") %>
<br>
<%=request.getAttribute("kd2") %>
<br>
</body>

</html>