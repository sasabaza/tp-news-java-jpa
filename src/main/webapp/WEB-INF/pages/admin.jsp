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
	<title>Admin - Site web actualités</title>
	<link rel="stylesheet" href="<c:url value="/ressources/css/style.css" />">
</head>
<body>

<header>
	<h1><strong>Admin</strong></h1>
</header>
<main>
	<jsp:include page="./inc/_menu.jsp"></jsp:include>
<c:choose >
	<c:when test="${sessionScope['access']}">
		<div class="espace">
			Ajouter des news
			<div><input type="text" name="titre" id="titre" placeholder="titre"></div>
			<div><input type="text" name="description" id="description" placeholder="description"></div>
			<div><input type="button" id="buttonAddNews" value="Ajouter"></div>		
		</div>
		
		<div class="espace">
			Modifier et supprimer des news			
			<ul>
				<c:forEach items="${recentnews}" var="news" varStatus="status">
					<li>
						<input type="hidden" name="idnews${ news.id }" id="idnews${ news.id }" value="${ news.id }">
						<input type="text" name="titrem${ news.id }" id="titrem${ news.id }" value="${ news.titre }">
						<input type="text" name="descriptionm${ news.id }" id="descriptionm${ news.id }" value="${ news.description }">
						<input type="button" class="btnModify" id="${ news.id }" value="Modifier">
						<input type="button" class="btnRemove" id="${ news.id }" value="Supprimer">
					</li>
				</c:forEach>		
			</ul>
		</div>
		
		<div class="espace">
			Ajout utilisateur
			<div><input type="text" name="username" id="username" placeholder="username"></div>
			<div><input type="password" name="password" id="password" placeholder="password"></div>
			<div><input type="button" id="buttonAddUser" value="Ajouter"></div>			
		</div>
	</c:when>
	<c:otherwise>
		<div></div>
	</c:otherwise>
</c:choose>	
	

</main>

<footer>
	<jsp:include page="./inc/_footer.jsp"></jsp:include>
</footer>

<script>	

	let buttonAddNews = document.querySelector('#buttonAddNews');
	buttonAddNews.addEventListener('click', function(){
	
	let titre = document.querySelector('#titre').value;
	let description = document.querySelector('#description').value;
	
	var details = {'titre': titre, 'description': description};
	var formBody = [];
	for (var property in details) {
	  var encodedKey = encodeURIComponent(property);
	  var encodedValue = encodeURIComponent(details[property]);
	  formBody.push(encodedKey + "=" + encodedValue);
	}
	formBody = formBody.join("&");
	
	const postMethod = {
			 method: 'POST',
			 headers: {
				 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
			 },
			 body: formBody
			}
	
	fetch('<c:url value="/api/news/new"/>', postMethod)
	 .then((response) => {

			if(response.status === 200) {
				window.location.href = '<c:url value="/admin"/>';
			}
			
		});
	});
	
	
	let buttonAddUser = document.querySelector('#buttonAddUser');
	buttonAddUser.addEventListener('click', function(){
	
	let username = document.querySelector('#username').value;
	let password = document.querySelector('#password').value;
	
	var details = {'username': username, 'password': password};
	var formBody = [];
	for (var property in details) {
	  var encodedKey = encodeURIComponent(property);
	  var encodedValue = encodeURIComponent(details[property]);
	  formBody.push(encodedKey + "=" + encodedValue);
	}
	formBody = formBody.join("&");
	
	const postMethod = {
			 method: 'POST',
			 headers: {
				 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
			 },
			 body: formBody
			}
	
	fetch('<c:url value="/api/user/new"/>', postMethod)
	 .then((response) => response.json())
	});
	
	
	
	
	const buttonsRemove = document.querySelectorAll('.btnRemove');
	buttonsRemove.forEach(function(currentBtn){
	  currentBtn.addEventListener('click', (event) => {
		
		let idButton = event.target.id;
			 
		fetch('<c:url value="/api/news/id/"/>' + idButton, {method: 'DELETE'})
		.then((response) => {

			if(response.status === 200) {
				window.location.href = '<c:url value="/admin"/>';
			}
		
		}
		
		);
		 

		  
	  })});
	
	
	const buttonsModify = document.querySelectorAll('.btnModify');
	buttonsModify.forEach(function(currentBtn){
	  currentBtn.addEventListener('click', (event) => {
		
		let idButton = event.target.id;
			 
	 	let titrem = document.querySelector('#titrem' + idButton).value;
		let descriptionm = document.querySelector('#descriptionm' + idButton).value;
		
		var details = {'titre': titrem, 'description': descriptionm};
		var formBody = [];
		for (var property in details) {
		  var encodedKey = encodeURIComponent(property);
		  var encodedValue = encodeURIComponent(details[property]);
		  formBody.push(encodedKey + "=" + encodedValue);
		}
		formBody = formBody.join("&");
		
		const putMethod = {
				 method: 'PUT',
				 headers: {
					 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
				 },
				 body: formBody
				}
		
		fetch('<c:url value="/api/news/id/"/>' + idButton, putMethod)
		 .then((response) => {

				if(response.status === 200) {
					window.location.href = '<c:url value="/admin"/>';
				}
				
			}
		 );		 

		  
	  })});
	

	
	</script>	

</body>
</html>