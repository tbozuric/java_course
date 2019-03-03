<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body>
<jsp:include page="header.jsp"/>

<c:if test="${sessionScope.get('current.user.id') == null}">
    <h3>Login</h3><br>
    <div style="color: red"><c:out value="${error}"/>
    </div>
    <form method="post" action="main">
        <div>
            <div>
                <span class="formLabel">Username </span><input type="text" name="nick"
                                                               value='<c:out value="${record.username}"/>'
                                                               size="25">
            </div>
            <c:if test="${record.containsKey('notExist')}">
                <div class="errorInput"><c:out value="${record.getError('notExist')}"/></div>
            </c:if>
        </div>

        <div>
            <div>
                <span class="formLabel">Password</span><input type="password" name="password" size="25">
            </div>
            <c:if test="${record.containsKey('invalidPassword')}">
                <div class="errorInput"><c:out value="${record.getError('invalidPassword')}"/></div>
            </c:if>
        </div>

        <p><input type="submit" value="Login"/></p>
    </form>
    <a href="${pageContext.request.contextPath}/servleti/register">Registration</a>
</c:if>

<br>
<h2>List of registered authors: <br></h2>
<c:forEach var="user" items="${users}">
    ${user.firstName} , ${user.lastName} - <a
        href="${pageContext.request.contextPath}/servleti/author/${user.nick}">${user.nick}</a><br>
</c:forEach>

<c:if test="${users.size()==0}">
    There are no registered authors.
</c:if>

</body>
</html>
