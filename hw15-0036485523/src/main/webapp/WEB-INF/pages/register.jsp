<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<h3>Registration</h3>
<form method="post" action="${pageContext.request.contextPath}/servleti/register">
    <div>
        <div>
            <span class="formLabel">First name</span><input type="text" name="firstName"
                                                            value='<c:out value="${record.firstName}"/>' size="25">
        </div>
        <c:if test="${record.containsKey('firstName')}">
            <div class="errorInput"><c:out value="${record.getError('firstName')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
            <span class="formLabel">Last name</span><input type="text" name="lastName"
                                                           value='<c:out value="${record.lastName}"/>' size="25">
        </div>
        <c:if test="${record.containsKey('lastName')}">
            <div class="errorInput"><c:out value="${record.getError('lastName')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
            <span class="formLabel">Email </span><input type="text" name="email"
                                                        value='<c:out value="${record.email}"/>' size="25">
        </div>
        <c:if test="${record.containsKey('email')}">
            <div class="errorInput"><c:out value="${record.getError('email')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
            <span class="formLabel">Nick </span><input type="text" name="nick"
                                                       value='<c:out value="${record.nick}"/>' size="25">
        </div>
        <c:if test="${record.containsKey('nick')}">
            <div class="errorInput"><c:out value="${record.getError('nick')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
            <span class="formLabel">Password</span><input type="password" name="password"
                                                          value='<c:out value="${record.password}"/>' size="25">
        </div>
        <c:if test="${record.containsKey('password')}">
            <div class="errorInput"><c:out value="${record.getError('password')}"/></div>
        </c:if>
    </div>
    <p><input type="submit" value="Register"/></p>
</form>
<a href="${pageContext.request.contextPath}/servleti/main">Back to home page</a>
</body>
</html>
