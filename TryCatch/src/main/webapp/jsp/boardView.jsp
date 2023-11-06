<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/TryCatch/css/board/boardView.css" rel="stylesheet">
</head>
<body>
<%@include file="../header.jsp"%>

	<div class="webcontaner"><!-- 전체공간 -->
		<div class="wrapBox">
			<div class="conTitle">
				<!-- <p>JVM이 뭔가요</p> -->
			</div>
			<div class="conWriterBox">
			
				<!-- <div class="memberImg">
					<img src="/TryCatch/memberimg/default.webp">
				</div> -->
				<!-- <div class="writerName">
					김철수
				</div>
				<div>
					<img class="iconImg" src="/TryCatch/img/boomupicon.png">0
				</div>
				<div>
					<img class="iconImg" src="/TryCatch/img/viewicon.png">0
				</div> -->
				
			</div>
			<div class="writeTime">
					
			</div>
			<div class="contentBox">
				<div class="bornCon">
					
				</div>
				<div class="btnBox">
					
				</div>
			</div>
			<div class="reviewBox">
				<div class="writeReview">
					<!-- 리뷰를 달아주세요!
					<textarea class="reviewArea" placeholder="매너있는 댓글은 작성자에게 힘이 됩니다."></textarea>
					<button onclick="reviewWrite(0)" class="reviewBtn" type="button">등록</button> -->
				</div>
				<div class="reviewView">
					<div class="reconBox">
		<!-- 				<div class="rewriter">
							병아리개발자
						</div>
						<div class="reviewTime">
							2023-09-18 18:13:01
						</div>
						<div class="recon">
							그래서 jvm이 뭐라고요..?
						</div>
					</div>-->
						
					</div>
				</div>
			</div>
		</div>
	</div>


<%@include file="../footer.jsp" %>
<script src="/TryCatch/js/board/boardView.js" type="text/javascript"></script>
</body>
</html>