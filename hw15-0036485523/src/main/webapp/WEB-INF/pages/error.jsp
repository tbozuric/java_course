<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Error</title>
</head>
<body>

<jsp:include page="header.jsp"/>

<p><c:out value="${error}"/></p>

<a href="${pageContext.request.contextPath}/servleti/main">Back to home page</a>
</body>
</html>
