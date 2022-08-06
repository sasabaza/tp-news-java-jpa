<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Site web actualités</title>
	<link rel="stylesheet" href="<c:url value="/ressources/css/style.css" />">
</head>
<body>

<header>
	<h1><strong>Actualités</strong></h1>
</header>
<main>
	<jsp:include page="./inc/_menu.jsp"></jsp:include>
	<fieldset>
		<legend>5 dernières actualités</legend>
		<ul>
			<c:forEach items="${lastnews}" var="news" varStatus="status">
				<li>${ news.titre } - ${ news.description }</li>
			</c:forEach>		
		</ul>
	</fieldset>

</main>
<footer>
	<jsp:include page="./inc/_footer.jsp"></jsp:include>
</footer>
</body>
</html>