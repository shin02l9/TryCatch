package model.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.ChatServerSocket;
import model.dto.ChattingDto;
import model.dto.MsgDto;
import model.dto.RoomDto;

public class ChattingDao extends Dao{

	private static ChattingDao ChattingDao= new ChattingDao();
	public static ChattingDao getInstance() { return ChattingDao; }
	private ChattingDao() {}

	
	 // 1. 방만들때 보낼 내용
   public boolean createRoom(RoomDto dto) {
      
      String sql = "insert into chatroom( crtitle , mno )values( ? , ? )";
      try {
         ps = conn.prepareStatement(sql);
         ps.setString(1 ,dto.getCrtitle() );
         ps.setInt( 2 , dto.getMno() );
         ps.executeUpdate();
         
         return true;
      }catch (Exception e) {System.out.println(e);}
      return false;
   }
   // 2. 방목록 출력하기 
   public void printRoom( ) {
      
      List< RoomDto> list =new ArrayList<>();
      
      String sql = "select * from chatroom";
	  try {
	     ps = conn.prepareStatement(sql);
	     rs = ps.executeQuery();
	     while( rs.next() ) {
	        list.add( new RoomDto( 
	              rs.getInt("crno"), 
	              rs.getString("crtitle") ,
	              rs.getInt("mno")));
	     }
     
     // 방목록 구성을 메시지로 보내기 [ Dao(자바)에서 소켓에게 메시지 보내기 ]
     ObjectMapper mapper = new ObjectMapper();
        // 1. 방목록 리스트를 JSON형식의 문자열타입 
     String listString = mapper.writeValueAsString(list);
        // 2. MSGDTO 담기.
     MsgDto dto = new MsgDto("printRoom", null, 0);
     dto.setRoomList(listString);
     
     String dtoString = mapper.writeValueAsString(dto);
     
     ChatServerSocket socket = new ChatServerSocket();
     socket.onMessage(null,dtoString );
     // 
         
      }catch (Exception e) {System.out.println(e);}
   }

   // 3. 메시지 저장 
   
   public boolean setMessage( MsgDto msgDto ) {
	   
	   try {
		   String sql = "insert into chmessage( cmessage , crno , cmdate , mno ) values( ? , ? , ? , ? )";
		   		
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, msgDto.getContent());
		   ps.setInt(2, msgDto.getCrno());
		   ps.setString(3, LocalDate.now().toString() );
		   ps.setInt(4, msgDto.getMno());
		   
		   ps.executeUpdate();
		   
	   }catch (Exception e) {
		   System.out.println(e);
	   }
	   return false;
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   

}//Dao end
