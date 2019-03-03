<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Trigonometric</title>
</head>

<body bgcolor="${pickedBgColor}">
<h1>Trigonometric functions(sin and cos) print page.</h1>
<table border="1">
    <thead>
    <tr>
        <th>Angle</th>
        <th>sin</th>
        <th>cos</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="record" items="${trigonometricValues}">
        <tr>
            <td>${record.angle}</td>
            <td> ${record.sinValue} </td>
            <td> ${record.cosValue} </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/index.jsp">Back to home page</a>
</body>
</html>
