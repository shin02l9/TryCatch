<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
<link href="/TryCatch/css/board.css" rel="stylesheet">
</head>
<body>
	<!-- JSP 안에 다른 JSP를 포함하기 -->
	<%@include file="../header.jsp" %>
	
	<div class="webcontainer"><!-- 전체공간 -->
		<div class="subcate">
		</div>
		<div class="boardHead"><!-- 페이지 출력수 , 글쓰기 -->
			<div>
				출력수
				<select class="liMit" onchange="selectLimit()">
					<option value="10">10</option>
					<option value="15">15</option>
					<option value="20">20</option>
				</select>
			</div>
			<div class="wrtbtnBox">
				<button class="writeBtn bTn" onclick="moveWrite()">글쓰기</button>
			</div>
		</div>
		<div class="contentBox"><!-- 게시물 공간 -->
			<table>
				<thead>
					<!-- <tr>
						<th>번호</th><th>카테고리</th><th>작성자</th><th>추천수</th><th>제목</th><th>조회수</th><th>작성일</th>
					</tr> -->
				</thead>
				<tbody class="contentBody">
	
				</tbody>
			</table>
		</div>
		<div class="boardBottom"><!-- 검색/페이지/글쓰기공간 -->
			<div class="pageBox"><!-- 페이지 구역 -->
			<!-- 	<button><</button>
				<button onclick="ctBoardView( 1 )" >1</button>
				<button onclick="ctBoardView( 2 )">2</button>
				<button>3</button>
				<button>4</button>
				<button>5</button>
				<button>></button> -->
			</div>
			<div class="searchBox">
				<select class="bbtn keyBoardList"> 
					<option value="bAll" > 전체 </option>
					<option value="btitle" > 제목 </option>
					<option value="bcontent" > 내용 </option>
					<option value="mid" > 작성자 </option>
				</select>
				<input onkeydown="enterBoard()" class="keywordBoardList" type="text">
				<button onclick="onSearchBoard()" class="searchBtn bTn" type="button">검색</button>
			</div>
		</div>
		
	</div>
	
	<!-- JSP 안에 다른 JSP를 포함하기 -->
	<%@include file="../footer.jsp" %>
	<script src="/TryCatch/js/board.js" type="text/javascript"></script>
</body>
</html>