if(loginState == false){alert('로그인된 회원만 입장할 수 있습니다.'); location.href = '/TryCatch/jsp/main.jsp';}

let clientSocket = new WebSocket(`ws://localhost/TryCatch/ChatServerSocket`)

   // 1. 서버 소켓과 접속했을때.
   clientSocket.onopen = e =>{
    console.log('서버연결:'+e );
   };
   
   // 2. 서버 소켓으로 부터 메시지를 받았을때.
   clientSocket.onmessage = e=>{
       console.log( e.data );
       let roomList =  JSON.parse( JSON.parse(e.data).roomList ) ; console.log( roomList );
       
       let html = ``;
       roomList.forEach( r => {
         html += `<button class="lobby-button room-button" onclick="enterRoom( ${r.crno} ,'${r.crtitle}')">${r.crtitle}</button>`
      })
       
       document.querySelector('.printRoomDiv').innerHTML = html;
       
   }

 // 채팅방 만들기 모달 열기
function createRoom() {
    let modal = document.getElementById('createRoomModal');
    modal.style.display = 'block';
}

// 채팅방 만들기 모달 닫기
function closeModal() {
    let modal = document.getElementById('createRoomModal');
    modal.style.display = 'none';
}

function createRoomFromModal() {
    let roomTitleInput = document.getElementById('roomTitle');
    let roomTitle = roomTitleInput.value.trim();
    	console.log(roomTitle+"룸타이틀 값 확인해라");
    console.log(loginMno);
    
    if (roomTitle === ''){alert('채팅방 제목을 입력하세요.'); return;}
       
       let ment ={
         type : "createRoom", // 메시지 전송타입중에 '방 만들래' 타입
         title : roomTitle , 
         mno : loginMno 
      }
			      
          clientSocket.send( JSON.stringify( ment ))
}
// 방 입장 버튼 클릭 시 동작
function enterRoom( crno , crtitle ) { //방제목 그대로 들어오는 기능
    
     alert(`${crtitle} 방에 입장합니다.`);
    
    location.href =`/TryCatch/jsp/chatting.jsp?cno=4&crno=${crno}`;  // 해당 페이지 이동
}


