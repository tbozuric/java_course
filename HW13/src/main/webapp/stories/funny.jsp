<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Funny story</title>
</head>

<%
    String[] textColors = {"Tomato", "Orange", "DodgerBlue", "Gray", "Violet", "MediumSeaGreen"};
    int random = new Random().nextInt(textColors.length);
    String textColor = textColors[random];
%>

<body bgcolor="${pickedBgColor}">
<p style="color:<%=textColor%>">
    A young man was planting some flower seeds on a sweltering day, sweating from the hot sun.
    His neighbor said, “You need to wait until the sun goes down, or plant in the morning when it is coolest.”
    The man said, “I can’t do that. It says on the package, ‘Plant in full sun!’ ”
</p>
<a href="${pageContext.request.contextPath}/index.jsp">Back to home page</a>
</body>
</html>
