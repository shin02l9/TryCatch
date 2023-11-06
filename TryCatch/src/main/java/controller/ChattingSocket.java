package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.ChattingDao;
import model.dao.MemberDao;
import model.dto.ChatListDto;
import model.dto.ChattingDto;
import model.dto.MemberDto;
import model.dto.MsgDto;



@ServerEndpoint("/ChattingSocket")
public class ChattingSocket {

   //public static ArrayList<Session> connList = new ArrayList<>();
   public static Map< Session, ChatListDto> chatlist = new HashMap<>();
      
   // 1.
      @OnOpen // 클라이언트가 서버소켓의 접속했을때 매핑/연결   (JS에서 new WebSocket 객체 생성될때. 문제없이 접속되면)
      public void onOpen(Session session) {
         System.out.println("클라이언트소켓 접속 : "+session);
         System.out.println(session.getId());
         System.out.println(session.getRequestURI());
         // * 들어온 클라이언소켓들을 서버소켓에 저장...
         chatlist.put(session , new ChatListDto() ); // 접속된 소켓들의 명단을 가지고 있어야 추후에 통신가능
      }
      // 2. 클라이언트가 서버소켓과 연결이 닫혔을때 매핑/연결 (JS에서 웹소켓 객체를 초기화 = 새로고침[F5],페이지전환 등등)
      @OnClose
      public void onClose(Session session) throws Exception {
         
        // 리스트에서 나간 사람 제거하기전에 나간 사람 정보 빼오기.
        ChatListDto chatListDto = chatlist.get(session);
         
         chatlist.remove(session); // 나갔을때.. 다른 회원명단들에게 명단출력 메시지 보내기.
         
         // 알람 dto 구성 
         MsgDto msgDto = new MsgDto();
         MemberDto memberDto = MemberDao.getinstance().getInfo( chatListDto.getMno() );

         
         msgDto.setType("alarm"); msgDto.setContent(memberDto.getMname()+"님이 퇴장했습니다.");
         msgDto.setCrno( chatListDto.getCrno() );
         msgDto.setMno( chatListDto.getMno() );
         
         ObjectMapper mapper = new ObjectMapper();
         
         String ment = mapper.writeValueAsString( msgDto );
         
      try {
          onMessage( null ,  ment);
      } catch (JsonProcessingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
        
      } // 접속명단 리스트에서 제외
      
      // 3.
      @OnMessage // 클라이언트가 서버소켓에 메세지를 보냈을때 연결/매핑
      public void onMessage(Session session, String ment) throws Exception {
         System.out.println("ment !! : "+ment);//클라이언트소켓으로부터 메세지를 받았을 때
         // 현재 접속된 명단(클라이언트소켓)들에게 메세지 전달
      
       ObjectMapper mapper = new ObjectMapper();
         MsgDto msgDto = mapper.readValue( ment ,  MsgDto.class );
      
         // 1. 접속 메시지  type : "alarm",
         if( msgDto.getType().equals("alarm") ) {
            
            // * 현재 메시지를 보낸( 접속메시지 ) 세션의 Dto 찾아서 값 대입 
          chatlist.keySet().forEach(s->{// s : 접속명단에 있는 클라이언트소켓 중 하나
            if( s.equals( session ) ) {
               chatlist.get(s).setCrno(msgDto.getCrno() );
               chatlist.get(s).setMno( msgDto.getMno() );
               System.out.println( chatlist.get(s) );
               System.out.println("위에 내용 확인");
               
            }
         });
          
          chatlist.keySet().forEach(s->{// s : 접속명단에 있는 클라이언트소켓 중 하나
            try {s.getBasicRemote().sendText(ment);// .getBasicRemote() : 메세지 전송을 메소드 제공 
            } catch (IOException e) {System.out.println(e);}// .sendText : 메세지를 String타입으로 전송 [!예외처리 필수]
            
         });
            
         } // 메세지 DB처리하는 곳
         // 2. 일반 메시지  type:'message'
         else if( msgDto.getType().equals("message") ) {
        	 
        	 // msgDto를 dao에게 전달해서 db처리
        	 System.out.println( msgDto );
        	 ChattingDao.getInstance().setMessage( msgDto);
        	 
        	 
        	 
        	 
            chatlist.keySet().forEach(s->{// s : 접속명단에 있는 클라이언트소켓 중 하나
               try {s.getBasicRemote().sendText(ment);// .getBasicRemote() : 메세지 전송을 메소드 제공 
               } catch (IOException e) {System.out.println(e);}// .sendText : 메세지를 String타입으로 전송 [!예외처리 필수]
               
            });
         }
         // 3. 명단 출력 메시지 type:'clist'
         else if( msgDto.getType().equals("clist") ) {
           
            List<ChatListDto> list = new ArrayList<>();
            
            // 접속명단에 있는 회원의 방번호 가 메시지를 보낸 사람의 방번호와 같으면
            chatlist.keySet().forEach(s->{// s : 접속명단에 있는 클라이언트소켓 중 하나
               try {
                  if( chatlist.get(s).getCrno() == msgDto.getCrno() ) {
                     // 같은방에 있는 회원명단 만들기 
                	  
                	  ChatListDto dto = new ChatListDto();
                	  dto.setMno( chatlist.get(s).getMno() );
                	  
                	  MemberDto memberDto = MemberDao.getinstance().getInfo( chatlist.get(s).getMno() );

                      // 회원번호를 가지고 회원이미지,유저이름 구해서 dto에 추가 
                      
                	  dto.setImageUrl( memberDto.getMimg() );
                	  dto.setUsername( memberDto.getMname() );
                	  
                     list.add( dto );
                     
                     
                  }
               }catch (Exception e) {System.out.println(e);}
            });
            
            System.out.println( "명단 확인" + list );
            
            // 같은방 회원들에게만 메시지 보내기 
            chatlist.keySet().forEach(s->{// s : 접속명단에 있는 클라이언트소켓 중 하나
               try {
                  // 접속명단에 있는 회원의 방번호 가 메시지를 보낸 사람의 방번호와 같으면
                  if( chatlist.get(s).getCrno() == msgDto.getCrno() ) {
                     
                     String listString = mapper.writeValueAsString(list);
                    
                       MsgDto clist = new MsgDto();
                       clist.setType("clist");
                       clist.setClist(listString);
                       clist.setCrno( msgDto.getCrno() );
                       clist.setMdate(  LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                       
                       String ment2 = mapper.writeValueAsString(clist);
                       System.out.println(ment2);
                       chatlist.keySet().forEach( ss ->{// s : 접속명단에 있는 클라이언트소켓 중 하나
                            try {ss.getBasicRemote().sendText(ment2);// .getBasicRemote() : 메세지 전송을 메소드 제공 
                            } catch (IOException e) {System.out.println(e);}// .sendText : 메세지를 String타입으로 전송 [!예외처리 필수]
                            
                         });
                     }
                   
               }catch (Exception e) {System.out.println(e);}
            });
      }
 // -----------------------------------------------------------------------------------//        
         
         	// 회원이 채팅방을 나갔을 때
         	/*
	         if (msgDto.getType().equals("leave")) {
	             // 클라이언트가 채팅방을 나갔음을 처리
	             int crno = msgDto.getCrno();
	
	             // 클라이언트를 명단에서 제거
	             chatlist.remove(session);
	
	             // 클라이언트 명단 갱신
	             List<Integer> updatedClist = new ArrayList<>();
	             chatlist.keySet().forEach(s -> {
	                 if (chatlist.get(s).getCrno() == crno) {
	                     updatedClist.add(chatlist.get(s).getMno());
	                 }
	             });
	
	             // 갱신된 명단을 클라이언트에게 보내기
	             MsgDto clistMessage = new MsgDto();
	             clistMessage.setType("clist");
	             clistMessage.setClist(updatedClist.toString());
	             clistMessage.setCrno(crno);
	
	             String updatedClistMessage = mapper.writeValueAsString(clistMessage);
	
	             chatlist.keySet().forEach(s -> {
	                 if (chatlist.get(s).getCrno() == crno) {
	                     try {
	                         s.getBasicRemote().sendText(updatedClistMessage);
	                     } catch (IOException e) {
	                         System.out.println(e);
	                     }
	                 }
	             });
	         }
         */
         
         
   }//onmessage end   
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