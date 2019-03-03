<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Ankete</title>
</head>
<body>
<h1>Dostupne ankete</h1>
<c:forEach var="poll" items="${polls}">
    <a href="${pageContext.request.contextPath}/servleti/glasanje?pollID=${poll.id}">${poll.title}</a><br>${poll.message}
    <br><br>
</c:forEach>
</body>
</html>
