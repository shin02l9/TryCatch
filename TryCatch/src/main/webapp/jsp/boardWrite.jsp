<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/TryCatch/css/board/boardWrite.css" rel="stylesheet">
<!-- 뷰포트 : 반응형 동작 코드  -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 썸머노트 css 적용 - 부트스트랩v5 -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.20/summernote-lite.min.css" rel="stylesheet">
</head>
<body>
	<!-- JSP 안에 다른 JSP를 포함하기 -->
	<%@include file="../header.jsp" %>
	
	<div class="webcontaner">
		<div class="wrap">
			<div class="writerBox">
			</div>
			<form class="writeForm" action="">
				<div class="categoryBox">
					카테고리
					<select class="categorySelect" name="subcno">
						<option value="3">행사</option>
						<option value="4">잡담</option>
						<option value="5">스터디</option>
						<option value="6">Q&A</option>
						<option value="7">정보마당</option>
						<option value="8">구인구직</option>
						<option value="9">공지사항</option>
					</select>
				</div>
				<div class="categoryGuide">
					카테고리와 맞지 않는 글은 사전 통보 없이 삭제 될 수 있습니다.
				</div> 
				<div class="titleBox">
					제목 <input class="btitle" name="btitle" type="text">
				</div>
				<div class="editorBox">
					<textarea  class="summernote" id="summernote" name="bcontent"rows="" cols=""></textarea>
				</div>
			
			</form>	
			<div class="buttonBox">
				<button onclick="onWrite()" class="writeBtn" type="button">글쓰기</button>
			</div>
		</div>
	</div>
	
	
	
	<!-- JSP 안에 다른 JSP를 포함하기 -->
	<%@include file="../footer.jsp" %>

<!-- jquery : js 최신 라이브러리  -->
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<!-- 썸머노트 js 적용 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.20/summernote-lite.min.js"></script>
<!-- 썸머노트 한글적용  -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.20/lang/summernote-ko-KR.min.js"></script>
<script src="/TryCatch/js/board/boardWrite.js" type="text/javascript"></script>
</body>
</html>