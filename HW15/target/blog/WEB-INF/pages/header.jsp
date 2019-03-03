<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
</head>
<body>
<p>
<h6>
    <c:choose>
        <c:when test="${sessionScope.get('current.user.id') != null}">
            Logged in: <br>
            <h5><c:out value="${sessionScope.get('current.user.fn')}"/> <c:out
                    value="${sessionScope.get('current.user.ln')}"/></h5>
            <a href="${pageContext.request.contextPath}/servleti/logout">Logout</a><br>
        </c:when>
        <c:otherwise>
            Not logged in. <br>
        </c:otherwise>
    </c:choose>
</h6>
<br>
----------------------------------------------------------------------------------------------
</p>
<br>
</body>
</html>
