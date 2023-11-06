<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/TryCatch/css/mypage.css" rel="stylesheet">
</head>
<body>
	<%@include file="../header.jsp" %>
	<div class="webcontainer">		
		<form class="profileForm">
			<!-- 프로필 이미지 -->
			<div class="profileImgBox">
				<!-- 이미지 구역 -->
				<div class="prifileImg">
					<label class="fileUploadLabel" for="profileFile"></label>
					<img class="nowProfileImg" src="/TryCatch/memberimg/default.webp"/>
				</div>		
				<!-- 현재 display:none 상태 -->			
				<input type="file" name="file" id="profileFile" onchange="fileImgChange(this)" accept="image/*">	
			</div>
			<!-- 내용 구역 -->
			<div class="provileContentBox">
				<div class="profileNameBox"><!-- 이름 -->
					<div class="text">Name</div>
					<input type="text" class="mname" name="mname" placeholder="Name" />
				</div>
				<div class="gradeAndExpBox"><!-- 등급과 경험치 -->
					<div class="profileGradeBox">
						<div class="text">Grade</div>
						<div class="mgrade">bit</div>
					</div>
					<div class="profileExpBox"> 
						<div class="text">Exp</div>
						<div class="mexp">150</div>
					</div>
				</div>
				<div class="profilePointBox"><!-- 포인트 -->
					<div class="text">Point</div>
					<div class="mpoint">320p</div>
				</div>
			</div>
			<!-- 버튼 구역 -->
			<div class="profileButtonBox">
				<button class="editBtn" type="button" onclick="profileEdit()" >Edit</button>
				<button class="deleteBtn" type="button" onclick="profileDelete()" >Delete Account</button>
			</div>
		</form>
	</div>

	<%@include file="../footer.jsp" %>
	<script src="/TryCatch/js/mypage.js" type="text/javascript"></script>
</body>
</html>