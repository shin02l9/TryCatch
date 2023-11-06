package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.dto.BoardDto;

public class SearchDao extends Dao {
	
	//싱글톤
	private static SearchDao searchDao = new SearchDao();
	private SearchDao() {}
	public static SearchDao getinstance() { return searchDao; }

	/* 
	 	[ 출력 기준(조건) ]
		 	1. 만약 키워드(단어)가 1개 라면
		 		1-1. 무조건. 제목에 키워드가 한번이상 언급되어 있어야한다.
		 		1-2. 내용에 키워드가 3번 이상 포함되어 있는 순서로 정렬해야한다. ( 3번이상부터는 무의미함 )
		 		1-3. 그 중에서 조회수가 많은 순으로 정렬한다.
		 		1-4. 그 중에서 날짜를 최신순으로 정렬한다.	    		    
		 	2. 만약 키워드(단어)가 2개 이상이라면...? 
		 		2-1. 제목에 입력된 키워드 모두 언급되어 있는 글이 우선순위이다.
		 		2-2. 내용에 키워드가 3번 이상 포함되어 있는 순서로 정렬해야한다. ( 3번이상부터는 무의미함 )
		 		2-3. 그 중에서 조회수가 많은 순으로 정렬한다.
		 		2-4. 그 중에서 날짜를 최신순으로 정렬한다.
	 
	 */
	
	public List<BoardDto> searchAllPrint( String keyword ){
		System.out.println( "!! searchAllPrint Dao 입장");
		List< BoardDto > list = new ArrayList<>();

		try {
			String sql = "";
			String word = "";
			
			// 키워드가 2개 이상의 단어로 조합된 문자열일 경우 띄어쓰기를 기준으로 분리해야함.
			String[] keywords = keyword.split("\\s+");
			
			for ( int i = 0; i < keywords.length; i++ ) {
				word = keywords[i];
				System.out.println("keywords["+i+"]"+keywords[i]);
				
				sql = "select *,"
						+ "    (CHAR_LENGTH(btitle) - CHAR_LENGTH(REPLACE(btitle, '"+word+"', ''))) / CHAR_LENGTH('"+word+"') as count_btitle, "
						+ "    (CHAR_LENGTH(bcontent) - CHAR_LENGTH(REPLACE(bcontent, '"+word+"', ''))) / CHAR_LENGTH('"+word+"') as count_bcontent "
						+ " from (\r\n"
						+ "	select b.*, m.mname, m.mimg "
						+ "    from board b "
						+ "    natural join member m "
						+ "	where btitle like '%"+word+"%' or bcontent like '%"+word+"%' "			
						+ " ) as filtered_data "
						+ " order by "
						+ "    case "
						+ "		when count_btitle >= 2 then 1 " 	 // -- 제목에 키워드가 1번 이상부터는 영향력없게 무조건 1으로 표기 0,1은 실제
						+ "        when count_bcontent >= 4 then 3 " // -- 내용에 키워드가 3번 이상부터는 영향력없게 무조건 3으로 표기 0,1,2,3은 실제
						+ "        else count_bcontent "
						+ "    end desc, "
						+ "    bview desc," 	// 조회수 높은 순 정렬
						+ "    bdate desc; "; 	// 날짜 최신순 정렬
				System.out.println(sql);
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
					while( rs.next() ) { 
						BoardDto boardDto = new BoardDto(
								 rs.getInt( 1 ), 	// Bno
								 rs.getString( 2 ),	// Btitle
								 rs.getString( 3 ),	// Bcontent
								 rs.getString( 4 ),	// Bdate
								 rs.getInt( 5 ),	// Bview
								 rs.getInt( 6 ),	// Mno
								 rs.getInt( 7 ),	// Subcno
								 rs.getString( 8 ),	// Mname
								 rs.getString( 9 ),	// Mimg
								 0					// count
						);
						list.add(boardDto);
					}
				}
			} catch( Exception e ) {System.out.println(e);}
		return list;
		
	}
	
	// 게시글 작성자의 회원 정보 구해오기 
	public int forAlarmMessage( int bno ) {
		System.out.println("forAlarmMessage DAO 입장");
		try {
			String sql = "select mno from board where bno = "+bno;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next()) { // 다음 레코드로 이동
	            int boardMno = rs.getInt(1); // 현재 레코드의 mno 값을 가져옴
	            System.out.println("boardMno : " + boardMno);
	            return boardMno;
	        } else {
	            // 레코드가 없을 경우 처리
	            System.out.println("해당하는 레코드를 찾을 수 없습니다.");
	        }
		} catch (Exception e) {System.out.println(e);}
		return 0;
	}
	
	// 댓글 작성자의 회원 정보 구해오기 
		public int forAlarmMessageR( int reno ) {
			System.out.println("forAlarmMessage DAO 입장");
			System.out.println("reno : "+reno);
			try {
				String sql = "select mno from review where reno = "+reno;
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				
				if (rs.next()) { // 다음 레코드로 이동
		            int reviewMno = rs.getInt(1); // 현재 레코드의 mno 값을 가져옴
		            System.out.println("reviewMno : " + reviewMno);
		            return reviewMno;
		        } else {
		            // 레코드가 없을 경우 처리
		            System.out.println("해당하는 레코드를 찾을 수 없습니다.");
		        }
			} catch (Exception e) {System.out.println(e);}
			return 0;
		}
	
}// class end


