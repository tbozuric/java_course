<%@ page import="hr.fer.zemris.java.model.Poll" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Voting</title>
</head>


<body>
<%Poll poll = ((Poll) request.getAttribute("poll")); %>
<h1>
    <%= poll.getTitle()%>
</h1>
<p>
    <%=poll.getMessage()%>
</p>
<ol>
    <c:forEach var="record" items="${options}">
        <li>
            <a href="${pageContext.request.contextPath}/servleti/glasanje-glasaj?id=${record.id}">${record.optionTitle}</a>
        </li>
    </c:forEach>
</ol>
<a href="${pageContext.request.contextPath}/index.html">Povratak na početnu stranicu</a>
</body>
</html>
