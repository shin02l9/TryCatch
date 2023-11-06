<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>header</title>
	<!-- 부트스트랩 css -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
	<link href="/TryCatch/css/header.css" rel="stylesheet">
</head>
<body>
				
		<!-- ====================================================부트스트랩 회원가입 모달======================================================== -->
		<div class="modal fade" id="sighUpModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-xl">
		    <div class="modal-content">
		     <div class="row modalWrap">
		     	<div class="col signLeftWrap">
		     		<div class="col signHeader">
		     			<img src="/TryCatch/img/trycatch.png">
		     			<div>
		     				SignUp
		   	  			</div>
		     		</div>
		     		<div class="col signInput">		     		
		     			<input class="signUpName" type="text" placeholder="Username"/>
		     			<div class="signUpCheckName"><!-- 유효성 검사 --></div>
		     			<input class="signUpEmail" type="text" placeholder="Email"/>
		     			<div class="signUpCheckEmail"><!-- 유효성 검사 --></div>
		     			<input class="signUpPassword" type="password" placeholder="Password"/>
		     			<div class="signUpCheckPwd"><!-- 유효성 검사 --></div>
		     			<input class="signUpConfirm" type="password" placeholder="Confirm Password"/>
		     			<div class="signUpCheckConfirm"><!-- 유효성 검사 --></div>
		     		</div>
		     		<div class="col d-flex flex-column sighUpBtnBox">
		     			<button onclick="signUp()" type="button">Sign Up</button>
		     			<div>이미 계정이 있으신가요? <a class="wantLogin" href="#" data-bs-toggle="modal" data-bs-target="#loginModal">로그인하기 -></a></div>
		     		</div>
		     	</div>
		     	<div class="col signRightWrap">	
		     		<img src="/TryCatch/img/sign.png">	     			
		     	</div>
		     </div>
		    </div>
		  </div>
		</div><!-- 회원가입 모달 끝 -->
		
		<!-- ================================================로그인 모달================================================== -->
		<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-xl">
		    <div class="modal-content">
		     <div class="row modalWrap">
		     	<div class="col signLeftWrap">
		     		<div class="col signHeader">
		     			<img src="/TryCatch/img/trycatch.png">
		     			<div class="">
		     				Login
		     			</div>
		     		</div>
		     		<div class="col d-flex flex-column signInput">		     		
		     			<input class="loginEmail" type="text" placeholder="Email"/>
		     			<input class="loginPwd" type="password" placeholder="Password"/>
		     		</div>
		     		<div class="col d-flex flex-column sighUpBtnBox">
		     			<button onclick="login()"type="button">Login</button>
		     			<div>비밀번호를 잊으셨나요? <a class="wantLogin" onclick="findPasswordReset()" href="#"data-bs-toggle="modal" data-bs-target="#findPassword">비밀번호 찾기 -></a></div>
		     		</div>
		     	</div>
		     	<div class="col signRightWrap">	
		     		<img src="/TryCatch/img/sign.png">	     			
		     	</div>
		     </div>
		    </div>
		  </div>
		</div><!-- 로그인 모달 끝 -->
		<!-- 비밀번호 찾기 모달 // 동적 코드는 login.js에 있습니다.-->
		<div class="modal fade" id="findPassword" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-xl">
		    <div class="modal-content">
		     <div class="row modalWrap">
		     	<div class="col signLeftWrap">
		     		<div class="col signHeader">
		     			<img src="/TryCatch/img/trycatch.png">
		     		</div>
		     		<div class="findPasswordBox">
			     		<div class="col d-flex flex-column signInput">
			     			<span class="forFindPasswordEmail">Email</span>		     		
			     			<input class="findPasswordEmail" type="text" placeholder=""/>		     	
			     		</div>
			     		<div class="col d-flex flex-column sighUpBtnBox">
			     			<button onclick="findPassword()"type="button">find</button>
			     		</div>   
		     		</div>
		     				
		     	</div>
		     	<div class="col signRightWrap">	
		     		<img src="/TryCatch/img/sign.png">	     			
		     	</div>
		     </div>
		    </div>
		  </div>
		</div><!-- 비밀번호 찾기 모달 끝 -->
		
		
		<!-- 전체 구역  -->
		<div class="headerContainer">
			<!-- 내용 구역 -->
			<div class="headerWrap">
				<!-- 로고 이미지 -->
				<div class="headerLogoBox">
					<a href="/TryCatch/jsp/main.jsp"><img src="/TryCatch/img/trycatch.png"/></a>
				</div>
				<!-- 카테고리 전체 구역 -->
				<div class="headerRightWrap">
					<!-- 로그인 -->
					<div class="headerLoginBox">
						<div><a href="#"  onclick="openLogin()"data-bs-toggle="modal" data-bs-target="#loginModal">login</a></div>
						<div style="color:#949191"> | </div>
						<div><a href="#" onclick="openSignUp()" data-bs-toggle="modal" data-bs-target="#sighUpModal">signUp</a></div>
					</div>
					<!-- 카테고리 -->
					<div class="headerCategoryWrap">
						<div class="headerCategoryBox">
							<div><a href="/TryCatch/jsp/board.jsp?cno=1" class="TOPIC" onclick="changePage(1)">TOPIC</a></div>
							<div><a href="/TryCatch/jsp/board.jsp?cno=2" class="Q&A" onclick="changePage(2)">Q&A</a></div>
							<div><a href="/TryCatch/jsp/board.jsp?cno=3" class="infoRoom" onclick="changePage(3)">정보마당</a></div>
							<div><a href="/TryCatch/jsp/room.jsp" class="headerChatting" onclick="changePage(4)">실시간채팅</a></div>
							<div><a href="/TryCatch/jsp/board.jsp?cno=5" class="headerComunity" onclick="changePage(5)">커뮤니티</a></div>
							<div><a href="/TryCatch/jsp/board.jsp?cno=6" class="headerJobOffer" onclick="changePage(6)">구인구직</a></div>
						</div>
						<div class="headerSearchBox">
							<input onkeydown="enter()" class="keyword keywordHeader" type="text"/>
							<button onclick="onSearchStart()" type="button" class="searchIcon"></button>
						</div>		
					</div>					
				</div>
			</div>	
		</div>
		
		<div class="alarMsgBox">
<!-- 			<div class="alarmM"> 알림메세지 팝업창  
				<h4>※ 알림</h4>
				<p> <a href="#"> ⊙ 작성하신 게시글에 댓글이 달렸습니다. </a> </p>
				<p> <a href="#"> ⊙ 로그인으로 10p 지급되었습니다. </a> </p>
			</div> -->
		</div>

	<!-- 제이쿼리 최신 -->
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>	
	<!-- 부트스트랩 스크립트 -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
	<script src="/TryCatch/js/header.js" type="text/javascript"></script>
	<script src="/TryCatch/js/login.js" type="text/javascript"></script>
	<script src="/TryCatch/js/signup.js" type="text/javascript"></script>
</body>
</html>