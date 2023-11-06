package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.dto.AlarmMsgDto;


@ServerEndpoint("/AlarmMessageServerSocket/{loginMno}")
public class AlarmMessageServerSocket {
		// 접속된 클라이언트 소켓들의 저장소
		public static Map< Session ,  AlarmMsgDto > clientList = new HashMap<>();
		@OnOpen
		public void onOpen( Session session, @PathParam("loginMno") String Member ) throws JsonMappingException, JsonProcessingException {
			System.out.println(">>>>>>>>>>>>> 소켓 @OnOpen ㅎㅇㅎㅇ ");
			

			int loginMno = Integer.parseInt(Member);
			System.out.println("접속된 회원번호 : "+loginMno);
			
			// 클라이언트소켓 리스트에 저장
			AlarmMsgDto alarmMsgDto = new AlarmMsgDto(loginMno);
			clientList.put( session ,  alarmMsgDto);
		}
		
		@OnMessage
		public void OnMessage( Session session,  String msg  ) throws IOException {
			System.out.println(">>>>>>>>>>>>> 소켓 @OnMessage ㅎㅇㅎㅇ ");
			System.out.println(msg);
			clientList.keySet().forEach( (c)->{
				try {
					c.getBasicRemote().sendText( msg );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		
		@OnClose
		public void onClose(Session session){
			clientList.remove(session);
		}				
}
