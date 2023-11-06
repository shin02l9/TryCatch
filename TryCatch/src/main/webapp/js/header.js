let loginState = false;
let loginName = ``;
let loginMno = 0;
let headerURLSearch = new URLSearchParams(location.search);
console.log(headerURLSearch.get("cno"))
let nowCno = headerURLSearch.get("cno");
// 현재 색상
let equipColor = `black`;
getLoginInfo();
function getLoginInfo(){
	
	$.ajax({
		url : "/TryCatch/MemberController" ,
		method : "get" ,
		async : false,	
		data : {type : 'info'},
		success : r=> {
			let headerLoginBox = document.querySelector('.headerLoginBox')
			let html = ``;
			console.log( r );
			if( r == null ){ // 비로그인
				loginState = false; 
				html += `
						<div><a href="#" onclick="openLogin()"data-bs-toggle="modal" data-bs-target="#loginModal">login</a></div>
						<div style="color:#949191"> | </div>
						<div><a href="#" onclick="openSignUp()" data-bs-toggle="modal" data-bs-target="#sighUpModal">signUp</a></div>
						`
			}else{ // 로그인				
			
				loginState = true; loginName = r.mname; loginMno =r.mno;

				html += `							
							<div class="memberImg"><a href="/TryCatch/jsp/mypage.jsp"><img class="mimg" src="/TryCatch/memberimg/${r.mimg}"></a></div>												
							<div style="margin: 0px 32px 0px 19px;" class="btn-group">
							  <button type="button" class="btn loginName" data-bs-toggle="dropdown">${r.mname}</button>
							  <button type="button" class="btn  dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
							    <span class="visually-hidden">Toggle Dropdown</span>
							  </button>
							  <ul class="dropdown-menu">
							  	<li><a class="dropdown-item" href="/TryCatch/jsp/mypage.jsp">마이 페이지</a></li>
							    <li><a class="dropdown-item" href="/TryCatch/jsp/myPoint.jsp">포인트 내역</a></li>
							    <li><a class="dropdown-item" href="/TryCatch/jsp/pointShop.jsp">포인트 상점</a></li>
							    <li><hr class="dropdown-divider"></li>							    						    
							    <li><a class="dropdown-item" onclick="logout()">logout</a></li>
							  </ul>
							</div>						
						`	
			}
			// - 구성된 html 대입
			headerLoginBox.innerHTML = html;
		},
		error : e => {}
	});	
}
changePage( nowCno );
// 헤더 카테고리 선택시 색 변화
function changePage( cno ){
	document.querySelector('.headerCategoryBox').innerHTML = 
						`<div><a href="/TryCatch/jsp/board.jsp?cno=1" class="TOPIC ${cno == 1? 'changeColor' : ''}" onclick="changePage(1)">TOPIC</a></div>
							<div><a href="/TryCatch/jsp/board.jsp?cno=2" class="Q&A ${cno == 2? 'changeColor' : ''}" onclick="changePage(2)">Q&A</a></div>
							<div><a href="/TryCatch/jsp/board.jsp?cno=3" class="infoRoom ${cno == 3? 'changeColor' : ''}" onclick="changePage(3)">정보마당</a></div>
							<div><a href="/TryCatch/jsp/room.jsp?cno=4" class="headerChatting ${cno == 4? 'changeColor' : ''}" onclick="changePage(4)">실시간채팅</a></div>
							<div><a href="/TryCatch/jsp/board.jsp?cno=5" class="headerComunity ${cno == 5? 'changeColor' : ''}" onclick="changePage(5)">커뮤니티</a></div>
							<div><a href="/TryCatch/jsp/board.jsp?cno=6" class="headerJobOffer ${cno == 6? 'changeColor' : ''}" onclick="changePage(6)">구인구직</a></div>`
	
}
// 로그아웃
function logout(){	
	$.ajax({
		url : "/TryCatch/MemberController" ,
		method : "get" ,
		data : {type : "logout"},
		success : r => {
			alert('로그아웃 되었습니다.');
			loginState = false;
			location.href="/TryCatch/jsp/main.jsp";
		} ,
		error : e => {console.log(e)} 
	});	
}



// 검색창에서 엔터키를 눌렀을때
function enter(){
	console.log( 'enter() 실행' )
	let keywordHeader = document.querySelector('.keywordHeader');
	if( event.keyCode == 13 ){
	onSearchStart();
	return;
	}
}

// 검색 키워드 다른 JS에게 전달하기 ( 공백일땐 실행 안되게 제약조건 )
function onSearchStart(){
	let keywordHeader = document.querySelector('.keywordHeader');
	if( keywordHeader.value == '' ){
		alert('검색 키워드를 올바르게 입력해주세요.'); 
		keywordHeader.value = ''; 
		return;
	}
	location.href='/TryCatch/jsp/search.jsp?keyword='+keywordHeader.value;
	keywordHeader.value = '';
}

// 알림 팝업창 ----------------------------------------------------
//JS 클라이언트[유저] 소켓 만들기
let clientSocketMsg = new WebSocket(`ws://localhost/TryCatch/AlarmMessageServerSocket/${loginMno}`)
	// 클라이언트 소켓이 생성되었을때 자동으로 서버소켓에 접속한다.---> 서버 소켓의 @OnOpen
	// 서버소켓 URL에 매개변수 전달하기 [ 주로 식별자 전달 ] 
	// --- 메소드 4가지 메소드 자동으로 실행 
		// 1. (자동실행) 클라이언트소켓이 정상적으로 서버소켓 접속했을때
	clientSocketMsg.onopen = e => { console.log(e)
		/*let msg = { type : 'alarm', content : `${loginMno}님이 입장하셨습니다.` }
		clientSocket.send( JSON.stringify(msg) );*/
		console.log('서버와 접속성공'); 
		} ;
		// 2. (자동실행) 클라이언트소켓이 서버소켓과 연결에서 오류가 발생했을때.
	clientSocketMsg.onerror = e => { console.log(e ); };
		// 3. (자동실행) 클라이언트소켓이 서버소켓과 연결이 끊겼을때.
	clientSocketMsg.onclose = e => { console.log(e ); };
		// 4. (자동실행) 클라이언트소켓이 메시지를 받았을때.
	clientSocketMsg.onmessage = e => onMsgAlarm( e ); 
		// send 보내기는 없음.

// 알람 프론트 출력하기 ------------------------------------------------------------------------
function onMsgAlarm(m){
	console.log('★ 받은 알람 ★')
	console.log(m.data)
	
	// 변환하기
	let msginfo = JSON.parse( m.data );
	console.log(msginfo.bno)
	console.log('msginfo.mno ' +msginfo.mno)
	console.log('loginMno ' +loginMno)
	console.log(msginfo.msg)
	
	let alarMsgBox = document.querySelector('.alarMsgBox').innerHTML = ` 
		<div class="alarmM">
			<h4>※ 알림</h4>
		</div>`;
			
	let alarmM = document.querySelector('.alarmM');
	HTML = ``;
	console.log("result = " + msginfo.mno == loginMno )
	if( msginfo.mno == loginMno ){
		HTML += `
		<p> <a href="#"> ${msginfo.msg} </a> </p>
		`;
		
		// 알림 메시지 표시할 때 클래스 추가
    	alarmM.classList.add('show');
	}
	alarmM.innerHTML += HTML;


    // 5초 후에 알림 창 숨기기
    setTimeout(() => {
        alarmM.classList.remove('show');
    }, 3000); // 5000 밀리초(5초) 후에 클래스 제거
}





