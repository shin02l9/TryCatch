package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.ChattingDao;
import model.dto.MsgDto;
import model.dto.RoomDto;

@ServerEndpoint("/ChatServerSocket")
public class ChatServerSocket {
   
   // 접속명단. 
   public static List<Session> clientList = new ArrayList<>();
   
   @OnOpen
   public void onOpen( Session session ) { 
      clientList.add(session); 
      ChattingDao.getInstance().printRoom( ); // 접속했을때 한번 방목록 메시지 전송
   }
   
   @OnClose
   public void onClose( Session session ) { clientList.remove(session); }
   
   @OnMessage // 클라이언트가 서버소켓에 메세지를 보냈을때 연결/매핑
   public void onMessage(Session session, String ment  ) throws IOException {
      
      // {"type":"createRoom","title":"아무나"} 받는내용이 문자열타입의 JSON형태라서 사용하기 힘들.
      // DTO로 변경하자 : JAVA는 JSON 아니고 DTO 사용하니까.
         // ObjectMapper라이브러리 를 이용해서 문자열타입 JSON을 DTO로 변환
      ObjectMapper mapper = new ObjectMapper();
      MsgDto msgDto = mapper.readValue( ment ,  MsgDto.class );   System.out.println( msgDto );
         // MsgDto [type=createRoom, title=아무나]
      
      // 만약에 메시지내용중 type속성이 createRoom 이면 
      if( msgDto.getType().equals( "createRoom" )) { // 방만들어 이면 
         
         RoomDto dto = new RoomDto();
         dto.setCrtitle( msgDto.getTitle() );
         dto.setMno( msgDto.getMno() );
         
         Boolean result = ChattingDao.getInstance().createRoom(dto);
         if( result ) {
            // 방만들기 성공했을때 접속된 클라이언소켓들에게 방목록 보내기.
            ChattingDao.getInstance().printRoom( );
         }
      }else if( msgDto.getType().equals( "chatting" ) ) { // 채팅이면
         
      }else if( msgDto.getType().equals( "printRoom") ) {
         
         // 전송된 모든 회원들에게 방목록 전달.
         clientList.forEach( (c)->{
            try { c.getBasicRemote().sendText(ment);
            } catch (IOException e) { e.printStackTrace();}
         });
         
      }
   }
}