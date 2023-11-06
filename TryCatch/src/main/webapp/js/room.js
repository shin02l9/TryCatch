
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

// 모달에서 채팅방 만들기
function createRoomFromModal() {
    let roomTitleInput = document.getElementById('roomTitle');
    let roomTitle = roomTitleInput.value.trim();
    
    if (roomTitle === '') {
        alert('채팅방 제목을 입력하세요.');
        return;
    }

    // 버튼 생성
    let newRoomButton = document.createElement('button');
    newRoomButton.className = 'lobby-button room-button';
    newRoomButton.textContent = roomTitle;
    newRoomButton.onclick = function() {
        enterRoom(roomTitle);
    };

    // 로비 컨테이너에 버튼 추가
    let lobbyContainer = document.querySelector('.lobby-container');
    lobbyContainer.appendChild(newRoomButton);

    alert('"' + roomTitle + '" 채팅방을 만듭니다.');
    closeModal(); // 모달 닫기
}

// 방 입장 버튼 클릭 시 동작
function enterRoom(crno) {
	
    alert(`${crno} 방에 입장합니다.`);
    
    location.href = `/TryCatch/jsp/chatting.jsp?cno=4&crno=${crno}`;  // 해당 페이지 이동
    // 여기에 방 입장 동작을 추가할 수 있습니다.
}