<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="mealsTo" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table class="mealsTo">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    <tr>
    </thead>
    <tbody>
    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <mealsTo:forEach items="${mealsTo}" var="meal">
        <tr class="${meal.excess ? 'exceeded' : 'not-exceeded'}">
            <td> ${f:formatLocalDateTime(meal.dateTime)}</td>
            <td> ${meal.description}</td>
            <td> ${meal.calories}</td>
        </tr>
    </mealsTo:forEach>
    </tbody>
</table>
</body>
</html>