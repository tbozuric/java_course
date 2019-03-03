<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Entry</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<h1><c:out value="${blogEntry.title}"/></h1>
<p><c:out value="${blogEntry.text}"/></p>

<c:if test="${currentUser}">
    <a href="${pageContext.request.contextPath}/servleti/author/${blogEntry.creator.nick}/edit?id=${blogEntry.id}">Edit</a>
</c:if>
<br>

<c:if test="${!blogEntry.comments.isEmpty()}">
    <h4>Comments</h4>
    <ul>
        <c:forEach var="e" items="${blogEntry.comments}">
            <li>
                <div style="font-weight: bold">[User=<c:out value="${e.userEMail}"/>] <c:out
                        value="${e.postedOn}"/></div>
                <div style="padding-left: 10px;"><c:out value="${e.message}"/></div>
            </li>
        </c:forEach>
    </ul>
</c:if>

<br>
<h2>Add new comment</h2>
<br>

<form method="post"
      action="${pageContext.request.contextPath}/servleti/author/${blogEntry.creator.nick}/${blogEntry.id}">
    <%--Only creator adds comment without mail, other users (logged in and others) enter the mail
    (for example, if a registered user wants to add a comment via another mail)--%>
    <c:if test="${not currentUser}">
        <div>
            <div>
                <span class="formLabel">Email </span><input type="text" name="email"
                                                            value='<c:out value="${record.email}"/>' size="25">
            </div>
            <c:if test="${record.containsKey('email')}">
                <div class="errorInput"><c:out value="${record.getError('email')}"/></div>
            </c:if>
        </div>
    </c:if>


    <div>
        <div>
            <div>
                <span class="formLabel">Comment </span><input type="text" name="comment"
                                                              value='<c:out value="${record.comment}"/>' size="25">
            </div>
        </div>
        <c:if test="${record.containsKey('comment')}">
            <div class="errorInput"><c:out value="${record.getError('comment')}"/></div>
        </c:if>
    </div>
    <p><input type="submit" value="Comment"/></p>
</form>

<a href="${pageContext.request.contextPath}/servleti/main">Back to home page</a>

</body>
</html>
