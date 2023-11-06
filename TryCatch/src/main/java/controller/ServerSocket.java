package controller;

public class ServerSocket {

}

/*

	* WebSocket 서버 소켓 라이브러리 제공
		1. 서버소켓 클래스 생성
			- 클래스 위에 서버소켓의 URL만들기
				@ServerEndpoint("/serversocket")
			- 만약에 서버소켓의 URL 결로상에 매개변수 받을때
				@ServerEndpoint("/serversocket/{매개변수명1}/{매개변수명2}")
		2. 서버소켓 메소드 제공 
				- 메소드 매개변수의 규칙 존재 
				
				1. 클라이언트 소켓이 접속했을때. (자동실행)되는 메소드
				@OnOpen
					1. 
					public void onOpen( Session session ) { }
						- Session : 서버소켓의 접속된 클라이언트소켓 정보
					2. 
					public void onOpen( Session session , @PathParam("매개변수명1") 타입 매개변수명 ) { }
						- @PathParam("매개변수명1") 타입 매개변수명 : 서버소켓(URL)경로상의 매개변수를 호출 
				
				2. 클라이언트 소켓과 오류가 발생했을때 . (자동실행)되는 메소드
				@OnError
					public void onError( Session session , Throwable throwable ) { }
						- Session : 서버소켓과 오류가 발생한 클라이언트소켓 정보 
						- Throwable : 오류 발생한 사유 정보 
				
				3. 클라이언트 소켓과 연결이 끊겼을때. (자동실행)되는 메소드
				@OnClose
					public void OnClose( Session session ) { }
						- Session : 서버소켓과 연결이 끊긴 클라이언트소켓 정보 
				
				4. 클라이언트 소켓으로부터 메시지를 받았을때. (자동실행)되는 메소드
				@OnMessage
					public void onMessage( Session session , String msg ) { } 
						- Session : 서버소켓에게 메시지를 보낸 클라이언트소켓 정보 
						- String 매개변수 : 클라이언트소켓으로 부터 받은 메시지 [문자열타입]
						
				5. 클라이언트소켓에게 메시지 전송 
					클라이언트소켓.getBasicRemote().sendText("내용");


*/