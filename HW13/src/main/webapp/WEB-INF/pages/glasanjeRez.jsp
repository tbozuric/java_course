<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Rezultati</title>
    <style type="text/css">
        table.rez td {
            text-align: center
        }
    </style>
</head>

<body bgcolor="${pickedBgColor}">
<h1>Rezultati glasanja</h1>
<p>Ovo su rezultati glasanja</p>
<table border="1" cellspacing="0" class="rez">
    <thead>
    <tr>
        <th>Bend</th>
        <th>Broj glasova</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="record" items="${votes}">
        <tr>
            <td>${record.band.nameOfMusicalBand}</td>
            <td>${record.numberOfVotes}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400"/>

<h2>Rezultati u XLS formatu</h2>
<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>.</p>

<h2>Razno</h2>
<p>Primjeri pjesama pobjedničkih bendova:</p>
<ul>
    <c:forEach var="record" items="${winners}">
        <li><a href=${record.band.linkToSong} target="_blank">${record.band.nameOfMusicalBand}</a></li>
    </c:forEach>
</ul>
<a href="glasanje">Back to voting</a><br>
<a href="${pageContext.request.contextPath}/index.jsp">Back to home page</a><br>
</body>
</html>
