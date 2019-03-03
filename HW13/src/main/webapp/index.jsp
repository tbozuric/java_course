<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Index</title>
</head>

<body bgcolor="${pickedBgColor}">
<a href="colors.jsp">Background color chooser</a><br>
<a href="${pageContext.request.contextPath}/trigonometric?a=0&b=90">Trigonometric functions</a><br>
<form action="trigonometric" method="GET">
    Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
    Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
    <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
</form>
<a href="stories/funny.jsp">Funny story</a><br>
<a href="glasanje">Voting</a><br>
<a href="reportImage">OS usage</a><br>
<a href="powers?a=1&b=100&n=3">Powers-Excel</a><br>
<a href="appinfo.jsp">Server information</a>

</body>
</html>
