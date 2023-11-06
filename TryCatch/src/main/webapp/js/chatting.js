
/*
   쿼리스트링에서 매개변수 가져오기
*/

let URLSearch = new URLSearchParams(location.search);
console.log(URLSearch.get("crno"))
let crno = URLSearch.get("crno");
let myImage = 'default.webp';

$.ajax({
	url:"/TryCatch/MemberProfileController",
	method: "get",
	data:{},
	success: r=>{console.log(r)
		myImage = r;
	},
	error:e=>{console.log(e)}
})


console.log('js입장');
// 1. 비 로그인시 입장 불가능
if( loginState == false ){ alert('회원전용 페이지입니다.'); location.href="/TryCatch/jsp/main.jsp"; }/*------위치확인-----*/
// let loginName = ``;
// 2. JS 클라이언트[유저] 소켓 만들기 
console.log( '채팅방에 입장한 아이디 : ' + loginName );/*-----------로그인아이디 이름 확인하고 일치시키기-----------*/

let clientSocket = new WebSocket(`ws://localhost/TryCatch/ChattingSocket`)/*------ip주소 이거 맞나??*/

   // --- 메소드 4가지 메소드 자동으로 실행
   // 1. (자동실행) 클라이언트소켓이 정상적으로 서버소켓 접속했을때
   clientSocket.onopen = e =>{
      console.log('뭐가들어올까'+e);
      // 1-2 : 만약에 접속에 성공하면 알림메세지 전송
      let ment ={
         type : "alarm",
         content : `${loginName}님이 입장했습니다.`,
         crno : crno ,
         mno : loginMno       
      }
      console.log('잘들어왔나'+ment);
      //해당 메세지를 받는 JAVA는 JSON타입 모르기때문에 문자열 타입으로 전송.
      clientSocket.send(JSON.stringify(ment));
      
      ment ={ // 현재 접속된 방에 명단 요청을 위한 메시지
         type : "clist",
         crno : crno 
      }
      clientSocket.send(JSON.stringify(ment));
      
      
   };
   // 2. (자동실행) 클라이언트소켓이 서버소켓과 연결에서 오류가 발생했을때.
   clientSocket.onerror = e => { console.log('서버와 오류발생:'+e ); };
      // 3. (자동실행) 클라이언트소켓이 서버소켓과 연결이 끊겼을때.
   clientSocket.onclose = e => { console.log('서버와 연결 끊김:'+e ); };
      // 4. (자동실행) 클라이언트소켓이 메시지를 받았을때.
   clientSocket.onmessage = e => onMent( e );
   
// 3. 서버에게 메시지 전송

function onSend(){
   // 3-1 textarea 입력값 호출
   
   let menValue = document.querySelector('.ment').value;
   
   if(menValue == ''||menValue == '\n'){
      document.querySelector('.ment').value=``;
      return;} 
   // 3-2 메세지 전송
   let today = new Date(); 
   let ment = {
	    type:'message' , 
	    fromname: loginName,
	    content : menValue, 
	    crno : crno,
	    mdate: today.getHours() + ":" + today.getMinutes(),
	    imageUrl: myImage,
	    mno : loginMno
	}
   
   clientSocket.send(JSON.stringify(ment));
   console.log('메세지를 전송합니다.' + ment);
   // 클라이언트소켓과 연결된 서버소켓에게 메시지 전송 ----> 서버소켓의 @OnMessage 으로 이동 
   // 3-3 메시지 전송 성공시 입력상자 초기화
   document.querySelector('.ment').value=``;
   
} 

// 4. 메세지를 받았을때 추후 행동(메소드) 선언
function onMent(e){
   console.log(e); // e: 메세지를 받았을 때 발생한 이벤트 정보가 들어있는 객체
   console.log(e.data); // .data 속성에 전달받은 메세지 내용
   
   let mentBox = JSON.parse(e.data); console.log(mentBox); // e.data : 서버로부터 전달받은 내용물 e.data  속성에 있는 상태

   // * 회원명단 메시지 이고 같은방이면 
   if( mentBox.type == 'clist' && mentBox.crno == crno ){
      console.log( mentBox.clist );
      let clist =  JSON.parse( mentBox.clist ) 
      let html = ``; 
      
      clist.forEach( c => {
		  
		    let imageUrl = c.imageUrl; // 이미지 URL 정보
	  		let username =c.username; // 회원 아이디 정보
		
 // ------------------------------------------------------- //		
         html+=`<div class="guest">
				<a class="profile-link" href="#"> <img src="/TryCatch/memberimg/${c.imageUrl}" />
				</a>
				<div class="user-info">
					<div class="username">${username}</div><!--user1-->
				</div>
				<div class="status online"></div><!--초록불 표시-->
			</div>`;
         
      })
      document.querySelector('.guestbox').innerHTML = html;
      return;
   }
   // * 회원명단 메시지 인데 같은방이 아닐때.
   if( mentBox.type == 'clist'){ return; }
   
	
	
	
	
   // 1. 어디에 출력할껀지 
   let chatcont = document.querySelector('.chatcont')
   // 2. 무엇을
   let html = ``;
      // 만약에 메세지 타입이 알림이면서 같은방 이면 
      if(mentBox.type == 'alarm' && mentBox.crno == crno ){
         html = `${typeHTML(mentBox)}`;
      }
      // 만약에 메세지 타입이 알림이 아니면[메세지, 이모티콘]
      // 2-2 만약에 내가 보냈으면. [ 보낸사람아이디와 로그인된사람의 아이디와 같으면 ]
         else if(mentBox.fromname == loginName){//--------------fromname, mname 확인하기 ------------------
         mentBox.content = mentBox.content.replace(/\n/g,'<br>');
         html = `<div class="rightcont">
                  <div class="subcont">
                     <div class="mname">${mentBox.fromname}</div>
                     <div class="mdate">${mentBox.mdate}</div>
                     ${typeHTML(mentBox)}
                  </div>
               </div>`;
               
      }else{// 2-3 내가 보낸 내용이 아니면 //--------------------frommimg 확인하기
         mentBox.content = mentBox.content.replace(/\n/g,'<br>');
         html = `
               <div class="leftcont">
                  <img class="pimg" src="/TryCatch/memberimg/${mentBox.imageUrl}"/>
                  <div class="tocont">
                     <div class="mname">${mentBox.fromname}</div>
                     <div class="subcont">
                        ${typeHTML(mentBox)}
                        <div class="mdate"> ${mentBox.mdate}</div>
                     </div>
                  </div>   
               </div>`
      }
   // 3. 누적 대입 [ 기존 채팅목록 에 이어서 추가 += ]
   chatcont.innerHTML += html;
   console.log(mentBox.mdate)
   // ------ 스크롤 최하단으로 내리기 ( 스크롤 이벤트 ) ------ //
   chatcont.scrollTop = chatcont.scrollHeight;
}// f end

// 5. textarea 입력창에서 입력할때마다 이벤트 발생 함수
function onEnterKey(){
   // 1. 만약에 ctrl + 엔터이면 줄바꿈
   if(window.event.keyCode ==13 && window.event.ctrlKey){ // 조합키 : 한번에 두개 이상 입력 가능한 키[ctrl.shift+alt 등]
      document.querySelector('ment').value += '\n'; return;}
   // 2. 만약에 입력한 키가 [엔터]이면 메세지 전송
   if(window.event.keyCode ==13){onSend(); return;}
}

// 6. 이모티콘 출력하기
getMmo();
function getMmo(){ // ---------------------------이모티콘 뭐넣을지 확인해보기
console.log('이모티콘 입장');
      document.querySelector('.emolistbox').innerHTML
      += `<img onclick="onEmoSend()"
               src="/TryCatch/img/imoji/actor.gif"/>`;
               
      document.querySelector('.emolistbox').innerHTML
      += `<img onclick="onEmoSend()"
               src="/TryCatch/img/imoji/error.gif"/>`;
               
   
}
// 7. 클릭한 이모티콘 서버로 보내기
function onEmoSend( i ){
   // 1. ment 구성
   let ment ={type: 'emo', content : i+""}// i+"" 하는 이유가 replace는 문자열만 가능
      // type : ment[메시지] , emo[이모티콘] , img[사진]
      // content : 내용물 
   // 2. 보내기
   clientSocket.send(JSON.stringify(ment));
         // JSON타입을 String타입으로 변환해주는 함수. [ 모양/형식/포멧 : JSON ] 
}
// 8. ment 타입에 따른 HTML 반환 함수
function typeHTML(ment){
console.log('typeHTML 입장'+ment);
   let html =``;
   // 1. 메세지 타입일때는 <div> 반환
   if(ment.type == 'message'){
      html += `<div class="content">${ment.content}</div>`;
   }
   // 2. 이모티콘 타입일때는 <img> 반환 
   else if(ment.type == 'emo'){
      html += `<img src = "/TryCatch/img/imoji/emo${ment.content}.gif">`;
   }
   // 3. 만약에 알림 타입 일때는 <div> 반환
   else if(ment.type == 'alarm'){
      html +=`<div class="alarm"> ${ment.content} </div>`;
   }
   return html;
}

//-------------------------------------------------------//
// 9. 채팅방에서 나갔을 때 
function leaveChatRoom() {
    // 나가기 버튼을 클릭하면 서버에 나갔음을 알리는 메시지를 전송
    const leaveMessage = {
        type: 'leave',
        crno: crno // 채팅방 번호
    };
    clientSocket.send(JSON.stringify(leaveMessage));
}



