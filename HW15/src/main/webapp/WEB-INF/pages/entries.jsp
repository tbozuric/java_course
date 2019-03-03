<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Blog entries</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<h2>Blog entries</h2>

<c:forEach var="entry" items="${entries}">
    ${entry.title} - <a href="${nick}/${entry.id}"> Link </a>
    <c:if test="${currentUser}">
        <a href="${nick}/edit?id=${entry.id}">Edit</a>
    </c:if>
    <br>
    <br>
</c:forEach>

<c:if test="${currentUser}">
    <a href="${nick}/new">New blog entry</a>
</c:if>

<a href="${pageContext.request.contextPath}/servleti/main">Back to home page</a>
</body>
</html>
