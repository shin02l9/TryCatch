<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>
<link href="/TryCatch/css/main.css" rel="stylesheet">
</head>
<body>
	<!-- JSP 안에 다른 JSP를 포함하기 -->
	<%@include file="../header.jsp" %>
	
	
	
	<!----------------------------------------------캐러셀(BS) 이미지슬라이드 ----------------------------------------------------------->
	<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
	  <div class="carousel-indicators">
	    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
	    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
	    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
	  </div>
	  <div class="carousel-inner">
	    <div class="carousel-item active" data-bs-interval="2000">
	      <img src="/TryCatch/img/mainIMG.jpg" class="d-block w-100" alt="...">
	    </div>
	    <div class="carousel-item" data-bs-interval="2000">
	      <img src="/TryCatch/img/mainIMG2.jpg" class="d-block w-100" alt="...">
	    </div>
	    <div class="carousel-item" >
	      <img src="/TryCatch/img/mainIMG3.jpg" class="d-block w-100" alt="...">
	    </div>
	  </div>
	  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
	    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	    <span class="visually-hidden">Previous</span>
	  </button>
	  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
	    <span class="carousel-control-next-icon" aria-hidden="true"></span>
	    <span class="visually-hidden">Next</span>
	  </button>
	</div>
	
	
	<div class="mainBottom" style="">
		<h3> ★ HOT TOPIC ★</h3>
		<table class="maintable" style="table-layout: fixed;" >
			<!-- innerHTML -->
		</table>
	</div>
	
	
	<div class="mainBtnbox">
		<button onclick="gotoQna()" class="mainBtn" type="button"> 프로젝트를 하다가 막힐땐! 개발자 Q&A가 도와드려요. </button>
		<button onclick="gotoChat()" class="mainBtn" type="button"> 빠르게 답변을 받고 싶다면? 실시간 채팅에 질문해 보세요. </button>
	</div>
	
	
	
	
	
	
	
	
	<!-- JSP 안에 다른 JSP를 포함하기 -->
	<%@include file="../footer.jsp" %>
	<script src="/TryCatch/js/main.js" type="text/javascript"></script>
</body>
</html>