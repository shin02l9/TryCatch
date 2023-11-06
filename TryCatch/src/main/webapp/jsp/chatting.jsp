<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chatting</title>
	<link href="/TryCatch/css/chatting.css" rel="stylesheet">

	<!-- 부트스트랩 css -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
	
	<!-- 폰트어썸 : 웹에서 아이콘(이미지) 제공  -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css">
	
</head>
<body>
	<!-- JSP 안에 다른 JSP를 포함하기 -->
	<%@include file="../header.jsp" %>
	
	
	<!-- -------채팅 테마 전체 구역 -------- -->
	<div class="chatcontainer">
		<!--채팅전체구역-->
		<div class="chatbox">
			<!--채팅내용 목록-->
			<div class="chatcont">
				<!--JS에서 HTML 대입되는 자리-->
			</div>
			<!--채팅입력창, 전송버튼 구역-->
			<div class="chatbottom">
				<textarea onkeyup="onEnterKey()" class="ment"></textarea>
				<button onclick="onSend()" type="button">전송</button>
			</div>
			
			<!--이모티콘, 첨부파일 등등 구역-->
			<div class="dropdown">
				<button class="emobtn" type="button" data-bs-toggle="dropdown">
					<i class="far fa-grin-squint"></i> <!--폰트어썸의 아이콘코드  -->
				</button>
				<ul class="dropdown-menu emolistbox">
				</ul>
			</div>
					
		</div><!--chatbox end-->
		<!--참여인원전체구역-->
		<div class="guestbox">
			<div class="Showguest">참여인원</div>
			<div class="guest">
				<a class="profile-link" href="#"> <img src="/TryCatch/img/park.jpg"
					alt="">
				</a>
				<div class="user-info">
					<div class="username">손가락코딩킬러</div><!--user1-->
					<div class="nickname"></div><!--닉네임or아이디-->
				</div>
				<div class="status online"></div><!--초록불 표시-->
			</div>
				<!-- 추가 게스트들 -->
		</div><!--guestbox end  -->
	</div>
	
	<!-- 부트스트랩 js -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
	
	<script src="/TryCatch/js/chatting.js" type="text/javascript"></script>
	<%@include file="../footer.jsp" %>
</body>
</html>






