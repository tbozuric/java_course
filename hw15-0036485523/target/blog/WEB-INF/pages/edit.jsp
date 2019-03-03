<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>

<body>
<jsp:include page="header.jsp"/>

<c:choose>
    <c:when test="${empty entryId}">
        New entry blog
    </c:when>
    <c:otherwise>
        Edit entry blog
    </c:otherwise>
</c:choose>

<form action="${pageContext.request.contextPath}/servleti/save" method="post">

    <div>
        <div>
            <span class="formLabel">Title</span><input type="text" name="title" value='<c:out value="${record.title}"/>'
                                                       size="40">
        </div>
        <c:if test="${record.containsKey('title')}">
            <div class="errorInput"><c:out value="${record.getError('title')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
            <div>
                <span class="formLabel">Message</span><input type="text" name="message"
                                                             value='<c:out value="${record.message}"/>'
                                                             size="100">
            </div>
        </div>
        <c:if test="${record.containsKey('message')}">
            <div class="errorInput"><c:out value="${record.getError('message')}"/></div>
        </c:if>
    </div>

    <input type="hidden" id="nick" name="nick" value="${nick}">
    <input type="hidden" id="id" name="id" value="${entryId}">

    <div class="formControls">
        <span class="formLabel">&nbsp;</span>
        <input type="submit" name="method" value="Save">
    </div>
</form>
<a href="${pageContext.request.contextPath}/servleti/main">Back to home page</a>
</body>
</html>
