<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/TryCatch/css/room.css" rel="stylesheet">
</head>
<body>
	   <!-- JSP 안에 다른 JSP를 포함하기 -->
   <%@include file="../header.jsp" %>
   <div class="lobby-container">
        <button class="lobby-button create-room-button" onclick="createRoom()">+채팅방 만들기</button>
        <div class="printRoomDiv">
           
        </div>
        
    </div>
    
    <div class="ModalTotal">
       <div id="createRoomModal" class="modal">
           <div class="modal-content">
               <span class="close" onclick="closeModal()">&times;</span>
               <h2 class="modalheader">채팅방 만들기</h2>
               <input type="text" id="roomTitle" placeholder="채팅방 제목">
               <button class="lobby-button create-room-button" onclick="createRoomFromModal()">만들기</button>
           </div>
       </div>
   </div>
    
    <!-- JSP 안에 다른 JSP를 포함하기 -->
   <%@include file="../footer.jsp" %>
    <script src="/TryCatch/js/ExampleChatting.js" type="text/javascript"></script>
</body>
</html>