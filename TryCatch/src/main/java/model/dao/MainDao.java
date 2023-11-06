package model.dao;

import java.util.ArrayList;

import model.dto.BoardDto;

public class MainDao extends Dao{
	//싱글톤
	private static MainDao mainDao = new MainDao();
	private MainDao () {}
	public static MainDao getinstance() { return mainDao; }
	
	
	// 1. 메인에 출력할 게시물 불러오기
	public ArrayList<BoardDto> printHot(){
		
		ArrayList<BoardDto> list = new ArrayList<>(); // 여러개 dto를 담을 리스트 객체 
		try {
			String sql = "select bno, btitle, bcontent, bview, m.mname from board b natural join member m order by bview desc limit 7;";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(); // s->q , i/d/u -> u ,
			while( rs.next() ) { // rs.next() : select 검색결과중 다음레코드 이동 =존재하면 true / 존재하지 않으면 false 
				// * 하나 레코드/하나 줄/ 하나 행/ 하나 방문록 / 하나 DTO
				// 레코드 --> DTO 변환 ( 레코드마다 각 필드 호출 = rs.get타입(필드순서번호) vs  rs.get타입("필드명") ) 
				BoardDto boardDto = new BoardDto(
						rs.getInt( 1 ), 
						rs.getString( 2 ), 
						rs.getString( 3 ), 
						rs.getInt( 4 ), 
						rs.getString( 5 ) );
				//VisitDto visitDto = new VisitDto( rs.getInt( "vno" ), rs.getString( "vwriter" ), rs.getString( "vpwd" ), rs.getString( "vcontent" ), rs.getString( "vdate" ) );
				// ** 여러개 dto를 저장할 리스트에 저장 
				list.add(boardDto);
			}
		}catch (Exception e) {System.out.println(e);}
		return list;  // 리스트 반환 
	}

}
