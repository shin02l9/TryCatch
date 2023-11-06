<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<link href="/TryCatch/css/myPoint.css" rel="stylesheet">
</head>
<body>
	<%@include file="../header.jsp" %> 
	<div class="webcontainer">	
		<h1 class="pointTitle"> 포인트 내역 </h1>	
		<!-- 테이블 -->
		<div class="pointContentWrap">
			<!-- 내역 하나 -->
			<div class="ContentBox">
				<div class="pointContent">출석체크</div>
				<div class="pointValue">10p</div>
				<div class="pointDate">2023-10-07 16:51</div>
			</div>
			<div class="ContentBox">
				<div class="pointContent">상품구매</div>
				<div class="pointValue">-1000p</div>
				<div class="pointDate">2023-10-06 17:05</div>
			</div>			
			<div class="ContentBox">
				<div class="pointContent">출석체크</div>
				<div class="pointValue">10p</div>
				<div class="pointDate">2023-10-05 12:51</div>
			</div>									
		</div>
		<div class="pageBtnWrap">
			<button type="button">&lt;</button>
			<button type="button">1</button>
			<button type="button">2</button>
			<button type="button">3</button>
			<button type="button">></button>
		</div>
		
	</div>
	<%@include file="../footer.jsp" %>
	<script src="/TryCatch/js/myPoint.js" type="text/javascript"></script>
</body>
</html>