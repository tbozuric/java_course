<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%

    ServletContext context = request.getServletContext();
    long elapsedTime = System.currentTimeMillis() - (long)context.getAttribute("time");
    long seconds = elapsedTime / 1000;
    long minutes = seconds / 60;
    long hours = minutes / 60;
    long days = hours / 24;

    String time = days + " days, " + hours % 24 + " hours, " + minutes % 60 + " minutes, " + seconds % 60 + " seconds";
%>
<body bgcolor="${pickedBgColor}">
    <h1>Server information</h1>
    <p>
        The server is running  : <%=time%>
    </p>
    <a href="index.jsp">Back to home page</a>
</body>
</html>
