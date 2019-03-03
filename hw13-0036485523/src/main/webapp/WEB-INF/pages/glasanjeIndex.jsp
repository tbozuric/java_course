<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Voting</title>
</head>

<body bgcolor="${pickedBgColor}">

<h1>Glasanje za omiljeni band</h1>
<p>Od sljedećih bendova, koji Vam je najdraži? Kliknite na link da biste glasali!</p>
<ol>
    <c:forEach var="record" items="${bands}">
        <li><a href="glasanje-glasaj?id=${record.key}">${record.value.nameOfMusicalBand}</a></li>
    </c:forEach>
</ol>
<a href="${pageContext.request.contextPath}/index.jsp">Back to home page</a>
</body>
</html>
