<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
After stemming sentence is ${answer}
<br>
after chunking,sentence is
<c:forEach items="${chunkStrings}" var="chunkString">
    <tr>      
        <td>${chunkString}</td>
    </tr>
</c:forEach>
<br>
after ngrams sentence is 
<c:forEach items="${ngrams}" var="ngram">
    <tr>      
        <td>${ngram}</td>
    </tr>
</c:forEach>
</body>
</html>