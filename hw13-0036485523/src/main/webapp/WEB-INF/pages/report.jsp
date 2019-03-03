<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Report</title>
</head>

<body bgcolor="${pickedBgColor}">
<h1>OS usage</h1>
<p>Here are the results of OS usage in survey that we completed.</p>
<img alt="Report" src="data:image/jpeg;base64,${image}"/>
<a href="${pageContext.request.contextPath}/index.jsp">Back to home page</a>

</body>
</html>
