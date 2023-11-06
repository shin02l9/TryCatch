<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> search </title>
<link href="/TryCatch/css/search.css" rel="stylesheet">
</head>
<body style="background-color: #F8FAFC;" >
	<!-- JSP 안에 다른 JSP를 포함하기 -->
	<%@include file="../header.jsp" %>
	
	<div class="wrap" > <!-- 전체레아이아웃 --> 
	
		<div class="searchBox">	<!-- 검색창 구역 -->
			<div class="searchInputBox">
				<!-- innerHTML -->
			</div>
			<div class="searchCount"> 
				<!-- innerHTML -->
			</div>
		</div>
		
		<div class="printContent"> <!-- 검색 내용 출력 구역 -->
		
			<div class="contH">   <!-- 출력 토픽 ----------------------------------------------------->
				<h3> TOPIC </h3>
				<button onClick="detailview(1)" > 더보기 ▶ </button>
			</div>
			<div class="printContTOPIC cont">  
				<!-- innerHTML -->
			</div>  <!-- 출력 토픽 end -->
			
			<div class="contH">   <!-- 출력 QnA ----------------------------------------------------->
				<h3> Q&A </h3>
				<button onClick="detailview(2)" > 더보기 ▶ </button>
			</div>
			<div class="printContQnA cont">  
				<!-- innerHTML -->
			</div>  <!-- 출력 QnA end -->
			
			<div class="contH">   <!-- 출력 정보마당 ----------------------------------------------------->
				<h3> 정보마당 </h3>
				<button onClick="detailview(3)" > 더보기 ▶ </button>
			</div>
			<div class="printContInfo cont">  
				<!-- innerHTML -->
			</div>  <!-- 출력 정보마당 end -->
		
			<div class="contH">   <!-- 출력 커뮤니티 ----------------------------------------------------->
				<h3> 커뮤니티 </h3>
				<button onClick="detailview(5)" > 더보기 ▶ </button>
			</div>
			<div class="printContCommu cont">  
				<!-- innerHTML -->
			</div>  <!-- 출력 커뮤니티 end -->
			
			<div class="contH">   <!-- 출력 그 외 ----------------------------------------------------->
				<h3> 구인구직 </h3>
				<button onClick="detailview(6)" > 더보기 ▶ </button>
			</div>
			<div class="printContG cont">  
				<!-- innerHTML -->
			</div>  <!-- 출력 커뮤니티 end -->
		
		
			<!-- 푸터와 여백두기위해 만든 데드스페이스 -->
			<div class="margin"></div>
		</div> <!-- 검색 내용 출력 구역 end-->
	</div> <!-- 전체레아이아웃 end --> 


	<!-- JSP 안에 다른 JSP를 포함하기 -->
	<%@include file="../footer.jsp" %>
	<script src="/TryCatch/js/search.js" type="text/javascript"></script>

</body>
</html>