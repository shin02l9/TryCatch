package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.AlarmMessageServerSocket;
import model.dto.AlarmMsgDto;
import model.dto.BoardDto;
import model.dto.CategoryDto;

public class BoardDao extends Dao{
	
	//싱글톤
	private static BoardDao boardDao = new BoardDao();
	private BoardDao () {}
	public static BoardDao getinstance() { return boardDao; }
	
	
	//하위 카테고리 가져오기
	public ArrayList<CategoryDto> getsubCategory( int cno ){
		
		ArrayList<CategoryDto> list = new ArrayList<>();
		
		try {
			
			String sql = "select subcno , subcname from subcategory where cno = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, cno);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				CategoryDto dto = new CategoryDto( rs.getInt(1) , rs.getString(2) );
				
				list.add(dto);
				
			}
			
		} catch (Exception e) {
			System.out.println( "getsubCategory" + e );
		}
		return list;
	}
	
	
	//1. 글 등록
	public boolean onWrite( BoardDto boardDto ) {
		
		try {
			
			String sql = "insert into board ( btitle , bcontent , mno , subcno ) "
						+"values ( ? , ? , ? , ? )";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, boardDto.getBtitle());
			ps.setString(2, boardDto.getBcontent());
			ps.setInt(3, boardDto.getMno());
			ps.setInt(4, boardDto.getSubcno());
			
			int count = ps.executeUpdate();
			
			if( count == 1 ) {
				expUpWrite( boardDto.getMno() );
				pointUpWrite( boardDto.getMno() );
				return true;
			}
			
		} catch (Exception e) {
			System.out.println( "onWrite : " + e);
		}
		return false;
	}
	
	//2. 글 출력 (전체)
	public ArrayList<BoardDto> getList( int subcno , int listLimit , int startrow , String key, String keyword ) {
		// 담아둘 전역 리스트 선언
		ArrayList<BoardDto> list = new ArrayList<>();
			
		try {
			
			String sql = "select b.bno , s.subcname , m.mname , m.mimg , "
						+"b.btitle , b.bcontent , b.bview , b.bdate , m.mcolor "
						+ "from board b natural join member m natural join subcategory s ";
			
			if( subcno > 1 && subcno != 2 ) {
				
				sql += " where s.subcno = " + subcno + " ";
				
			} 
			
			else if (subcno == 2 ) {
				
				sql = "select b.bno , s.subcname , m.mname , m.mimg , "
						+"b.btitle , b.bcontent , b.bview , b.bdate , m.mcolor "
						+ "from board b natural join member m natural join subcategory s ";
				
			}
			
			else if( subcno == -1) {
			
				sql = "select b.bno , s.subcname , m.mname , m.mimg , "
						+"b.btitle , b.bcontent , b.bview , b.bdate , m.mcolor "
						+ "from board b natural join member m natural join subcategory s "
						+ " where (s.subcno = 4 or s.subcno = 5) ";
			}
			
			// 검색 기능 추가 
			if( !keyword.isEmpty() ) {
			
				if( subcno > 1 || subcno == -1 ) { sql += " and ";} 
				
				else { sql += " where "; }
				
				sql += " (btitle like '%" + keyword + "%' or bcontent like '%" + keyword + "%')";
				
			} 
			
			if(subcno != 2) {
			
			sql += " order by b.bdate desc limit "+ startrow +" , " + listLimit ;
			
			}
			
			if( subcno == 2) {
				sql += " order by b.bview desc limit "+ startrow +" , " + listLimit ;
			}
			
			System.out.println( "글 출력 sql:"+sql);
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			int blike = 0;
			int bno = 0;
			while (rs.next()) {
				
				bno = rs.getInt(1);
				
				blike = getblike(bno);
				
				
				BoardDto dto = new BoardDto(// bno		   //subcname
											rs.getInt(1) , rs.getString(2) , 
											//mname			 //mimg
											rs.getString(3), rs.getString(4) ,
											// btitle 		  // bcontent
											rs.getString(5) , rs.getString(6) ,
											//bview		   // bdate		     //blike
											rs.getInt(7) , rs.getString(8) , blike ,
											//mcolor
											rs.getString(9));
				
				list.add(dto);
				
				blike = 0;
				bno = 0;
			}//wh end
			
		} catch (Exception e) {
			System.out.println("getList" + e);
		}
		
		return list;
	}
	//2-1. 게시물 수 출력
	public int getTotalSize( int subcno  ) {
		
		try {
			
			String sql = "select count(*) from board ";
			
			if (subcno > 1 && subcno != 2){
				sql += "where subcno = "+subcno;
			}
			
			else if(subcno == -1) {
				sql += "where subcno = 4 or subcno = 5";
			}
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return 0;
	}
	
	// 2-2 공지사항 글 리스트 상단에 출력
	public ArrayList<BoardDto> getNoticeList( int subcno){
		
		ArrayList<BoardDto> list = new ArrayList<>();
		
		try {
			
			String sql = "select b.bno , s.subcname , m.mname , m.mimg , "
					+"b.btitle , b.bcontent , b.bview , b.bdate , m.mcolor "
					+ "from board b natural join member m natural join subcategory s "
					+ "where s.subcno = " + subcno;
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			int blike = 0;
			int bno = 0;
			while (rs.next()) {
				
				bno = rs.getInt(1);
				
				blike = getblike(bno);
				
				
				BoardDto dto = new BoardDto(// bno		   //subcname
											rs.getInt(1) , rs.getString(2) , 
											//mname			 //mimg
											rs.getString(3), rs.getString(4) ,
											// btitle 		  // bcontent
											rs.getString(5) , rs.getString(6) ,
											//bview		   // bdate		     //blike
											rs.getInt(7) , rs.getString(8) , blike ,
											//mcolor
											rs.getString(9)
											);
				
				list.add(dto);
				
				blike = 0;
				bno = 0;
			}//wh end
			
		} catch (Exception e) {
			System.out.println( "getNoticeList : " + e );
		}
		
		return list;
		
	}
	
	//3. 글 출력 (개별)
	public BoardDto getBoard( int bno ) {
		
		viewIncrease( bno ); 
		
		try {
		 	
			String sql = "select b.* , m.mname , m.mimg , m.mexp , m.mcolor "
						+ "from board b natural join member m where bno = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, bno);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				
				BoardDto boardDto = new BoardDto(// bno		  //btitle
												rs.getInt(1), rs.getString(2), 
												//bcontent		 //bdate
												rs.getString(3), rs.getString(4), 
												//bview		  // mno
												rs.getInt(5), rs.getInt(6),
												//subcno	  //mname
												rs.getInt(7), rs.getString(8), 
												//mimg			 //mexp
												rs.getString(9) , rs.getInt(10) ,
												//mcolor
												rs.getString(11));
				return boardDto;
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	// 3-1 리뷰 출력
	public ArrayList<BoardDto> getReview ( int bno ){
		
		ArrayList<BoardDto> list = new ArrayList<>();
		
		try {
			
			String sql = "select r.reno , r.recontent , r.reindex , "
						+"r.redate , m.mname , m.mimg , m.mexp , m.mcolor "
						+"from review r natural join member m "
						+"where bno = ?"; 
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, bno);
			
			rs = ps.executeQuery();
			
			int relike = 0;
			int reno = 0;
			
			while ( rs.next() ) {
				
				reno = rs.getInt(1);
				
				relike = getrelike(reno);
				
				BoardDto boardDto = new BoardDto(// reno	  //recontent
												rs.getInt(1), rs.getString(2), 
												//reindex	  //redate
												rs.getInt(3), rs.getString(4),
												//relike	  //mname
												relike , rs.getString(5), 
												//mimg				//mexp
												rs.getString(6) , rs.getInt(7) ,
												//mcolor
												rs.getString(8));
				list.add(boardDto);
				
				reno = 0;
				relike = 0;
			}
			
		} catch (Exception e) {
			System.out.println( "getReview" + e);
		}
		return list;
	}

	
	
	//3-2 추천수 출력
	public int getblike ( int bno ) {
		
		try {
			
			String sql = "select count(*) from boardlike where bno = ?";
			
			PreparedStatement ps2 = conn.prepareStatement(sql);
			
			ps2.setInt(1, bno);
			
			ResultSet rs2 = ps2.executeQuery();
			
			if(rs2.next()) {
				return rs2.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println( "getblike" + e );
		}
		
		return 0;
	}
	
	
	//4. 글 수정
	public boolean onUpdate( BoardDto boardDto ) {
		
		try {
			
			String sql = "update board set btitle = ? , bcontent = ? , subcno = ? where bno = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, boardDto.getBtitle() );
			ps.setString(2, boardDto.getBcontent());
			ps.setInt(3, boardDto.getSubcno());
			ps.setInt(4, boardDto.getBno());
			
			int count = ps.executeUpdate();
			
			if(count==1) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("onUpdate" + e);
		}
		
		return false;
	}
	
	//5. 글 삭제
	public boolean onDelete( int bno , int mno ) {
		
		try {
			
			String sql = "delete from board where bno = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, bno);
			
			int count = ps.executeUpdate();
			
			if(count == 1) {
				expDownWrite(mno);
				pointDownWrite(mno);
				return true;
			}
			
		} catch (Exception e) {
			System.out.println( "onDelete" + e);
		}
		
		return false;
	}

	//6. 조회수 증가
	public boolean viewIncrease( int bno ) {
		
		try {
			
			String sql = "update board set bview = bview + 1 where bno = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, bno);
			
			int count = ps.executeUpdate();
			
			if( count == 1) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	//7. 추천수 증가
	public int boomUp( int bno , int mno ) {
		
		try {
			
			String sql = "";
			
			if( getBoomUp(bno, mno) ) {
				sql = "delete from boardlike where bno = ? and mno = ?";
			} else { sql = "insert into boardlike values ( ? , ? )";
				// 예지 : 알람기능 -------------------------------------------------
				int boardmno = SearchDao.getinstance().forAlarmMessage(bno);
				AlarmMsgDto dto = new AlarmMsgDto( boardmno , bno, "⊙ 작성한 글에 추천수가 올랐습니다.");
				ObjectMapper mapper = new ObjectMapper();
	
				AlarmMessageServerSocket serverSocket = new AlarmMessageServerSocket();
				serverSocket.OnMessage(null,  mapper.writeValueAsString(dto) );
			
			}
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, bno);
			ps.setInt(2, mno);
			
			int count = ps.executeUpdate();
			
			System.out.println(count);
			
			if(count == 1) {
			
				sql = "select count(*) from boardlike where bno = ?";
				
				PreparedStatement ps2 = conn.prepareStatement(sql);
				
				ps2.setInt(1, bno);
				
				ResultSet rs2 = ps2.executeQuery();
				
				if(rs2.next()) {
				
					if( rs2.getInt(1)%10 == 0) {
					
						sql = "select mno from board where bno = " + bno;
						
						PreparedStatement ps3 = conn.prepareStatement(sql);
						
						ResultSet rs3 = ps3.executeQuery();
						
						if(rs3.next()) {
						
							expUpBoomUp( rs3.getInt(1) );
							pointUpBoomUp( rs2.getInt(1) , rs3.getInt(1) );
						}
					}
					
				
					
					return rs2.getInt(1);
				}
			}
		} catch (Exception e) {
			System.out.println("boomUp : "+e);
		}
		return 0;
	}
	
	// 추천 할 게시물 회원 존재 여부 확인
	public boolean getBoomUp( int bno , int mno ) {
		
		try {
			
			String sql = "select * from boardlike where bno = ? and mno = ?"; 
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, bno);
			ps.setInt(2, mno);
			
			rs = ps.executeQuery();
			
			if( rs.next()) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println( "getBoomUp" + e );
		}
		return false;
	}
	
	// 리뷰 등록 
	public boolean reviewWrite( String recontent , int reindex , int bno , int mno ) {
		// reindex가 0 이면 일반 댓글이고 0보다 크면 대댓글임
		
		try {
			
			String sql = "insert into review "
						+ "( recontent , reindex , bno , mno ) "
						+ "values ( ? , ? , ? , ?)";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, recontent);
			ps.setInt(2, reindex);
			ps.setInt(3, bno);
			ps.setInt(4, mno);
			
			int count = ps.executeUpdate();
			
			if(count == 1) {
				expUpReview( mno );
				pointUpReview( mno );
				
				// 예지 : 알람기능 -------------------------------------------------
				if ( reindex == 0 ) { // 댓글 ---------------------------------

					int boardMno = SearchDao.getinstance().forAlarmMessage(bno);
					System.out.println("boardMno : "+boardMno);
					
					AlarmMsgDto dto = new AlarmMsgDto( boardMno , bno, "⊙ 작성한 글에 댓글이 등록되었습니다.");
					ObjectMapper mapper = new ObjectMapper();

					AlarmMessageServerSocket serverSocket = new AlarmMessageServerSocket();
					serverSocket.OnMessage(null,  mapper.writeValueAsString(dto) );
					
				} else if ( reindex > 0 ) { // 대댓글 ---------------------------------
					int reviewMno = SearchDao.getinstance().forAlarmMessageR( reindex );
					
					System.out.println("reviewMno : "+reviewMno);
					
					AlarmMsgDto dto = new AlarmMsgDto( reviewMno ,  reviewMno, "⊙ 작성한 댓글에 대댓글이 등록되었습니다.");
					ObjectMapper mapper = new ObjectMapper();

					AlarmMessageServerSocket serverSocket = new AlarmMessageServerSocket();
					serverSocket.OnMessage(null,  mapper.writeValueAsString(dto) );
				}
				
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("reviewWrite : " + e);
		}
		return false;
	}
	
	//7. 리뷰 추천수 증가
	public int reviewUp( int reno , int mno ) {
			
		try {
			String sql = "";
			
			if( getreviewUp(reno, mno) ) {
				sql = "delete from reviewlike where reno = ? and mno = ?";
			} else {
				
				sql = "insert into reviewlike values ( ? , ? )";
				// 예지 : 알람기능 -------------------------------------------------
				int boardRmno = SearchDao.getinstance().forAlarmMessageR(reno);
				AlarmMsgDto dto = new AlarmMsgDto( boardRmno , reno, "⊙ 작성한 댓글에 추천수가 올랐습니다.");
				ObjectMapper mapper = new ObjectMapper();

				AlarmMessageServerSocket serverSocket = new AlarmMessageServerSocket();
				serverSocket.OnMessage(null,  mapper.writeValueAsString(dto) );
			}

			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, reno);
			ps.setInt(2, mno);
			
			int count = ps.executeUpdate();
			
			if(count == 1) {
				
				sql = "select count(*) from reviewlike where reno = ?";
				
				PreparedStatement ps2 = conn.prepareStatement(sql);
				
				ps2.setInt(1, reno);
				
				ResultSet rs2 = ps2.executeQuery();
				
				if(rs2.next()) {
					
					return rs2.getInt(1);
				}
				
			}
			
		} catch (Exception e) {
			System.out.println("reviewUp" + e);
		}
		
		return 0;
	}
		
	// 리뷰 추천 할 게시물 회원 존재 여부 확인
	public boolean getreviewUp( int reno , int mno ) {
		
		try {
			
			String sql = "select * from reviewlike where reno = ? and mno = ?"; 
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, reno);
			ps.setInt(2, mno);
			
			rs = ps.executeQuery();
			
			if( rs.next()) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println( "getreviewUp" + e );
		}
		return false;
	}

	//리뷰 추천수 출력
	public int getrelike ( int reno ) {
		
		try {
			
			String sql = "select count(*) from reviewlike where reno = ?";
			
			PreparedStatement ps2 = conn.prepareStatement(sql);
			
			ps2.setInt(1, reno);
			
			ResultSet rs2 = ps2.executeQuery();
			
			if(rs2.next()) {
				return rs2.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println( "getrelike" + e );
		}
		
		return 0;
	}

	//리뷰 삭제
	public boolean reviewDelete( int reno , int mno ) {
		
		try {
			
			if(reviewCheck(reno)) {
				return false;
			}
			else {
				String sql = "delete from board where bno = ?";
				
				ps = conn.prepareStatement(sql);
				
				ps.setInt(1, reno);
				
				int count = ps.executeUpdate();
				
				if(count == 1) {
					expDownReview(mno);
					pointDownReview(mno);
					return true;
				}
			}
			 
		} catch (Exception e) {
			System.out.println( "onDelete" + e);
		}
		
		return false;
	}
	
	//리뷰 삭제할때 일반 댓글은 삭제가능 대댓글은 삭제 불가
	public boolean reviewCheck( int reno ) {
		
		try {
			
			String sql = "select * from review where reindex = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, reno);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("reviewCheck " + e);
		}
		return false;
		
	}
	// 리뷰 수정할때 작성된 리뷰 내용 가져오는 함수
	public String getreUpdate( int reno ) {
		
		try {
			
			String sql = "select recontent from review where reno = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, reno);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				String result = rs.getString(1);
				return result;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	// 리뷰수정
	public boolean reviewUpdate( int reno , String recontent ) {
		
		try {
			
			String sql = "update review set recontent = ? where reno = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, recontent);
			ps.setInt(2, reno);
			
			int count = ps.executeUpdate();
			
			if(count == 1) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("reviewUpdate : " + e);
		}
		
		return false;
	}
	
	// 글 작성시 exp 15 증가
	public boolean expUpWrite( int mno ) {
		
		try {
			
			String sql = "update member set mexp = mexp+15 where mno = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mno);
			
			int count = ps.executeUpdate();
			
			if(count == 1 ) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("expUpWrite " + e);
		}
		return false;
	}
	
	//댓글 작성시 exp 10 증가
	public boolean expUpReview( int mno ) {
		
		try {
			
			String sql = "update member set mexp = mexp+10 where mno = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mno);
			
			int count = ps.executeUpdate();
			
			if(count == 1 ) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("expUpReview " + e);
		}
		return false;
	}
	
	// 글 삭제하면 exp 15 감소
	public boolean expDownWrite( int mno ) {
		
		try {
			
			String sql = "update member set mexp = mexp-15 where mno = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mno);
			
			int count = ps.executeUpdate();
			
			if(count == 1 ) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("expUpWrite " + e);
		}
		return false;
	}
	
	// 댓글 삭제하면 exp 10 감소
	public boolean expDownReview( int mno ) {
		
		try {
			
			String sql = "update member set mexp = mexp-10 where mno = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mno);
			
			int count = ps.executeUpdate();
			
			if(count == 1 ) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("expUpReview " + e);
		}
		return false;
	}
	
	// 글 작성시 +3 포인트
	public boolean pointUpWrite( int mno ) {
		
		try {
			
			String sql = "insert into point ( pcontent , pcount , mno ) "
							+ "values( '게시글 작성' , 3 , ?  ) ";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mno);
			
			int count = ps.executeUpdate();
			
			if(count == 1) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("pointUpWrite : " + e);
		}
		
		return false;
		
	}
	
	// 댓글 작성시 +1 포인트
	public boolean pointUpReview( int mno ) {
		
		try {
			
			String sql = "insert into point ( pcontent , pcount , mno ) "
							+ "values( '댓글 작성' , 1 , ?  ) ";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mno);
			
			int count = ps.executeUpdate();
			
			if(count == 1) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("pointUpWrite : " + e);
		}
		
		return false;
		
	}
	
	// 글 삭제시 -3 포인트
	public boolean pointDownWrite( int mno ) {
		
		try {
			
			String sql = "insert into point ( pcontent , pcount , mno ) "
							+ "values( '게시글 삭제' , -3 , ?  ) ";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mno);
			
			int count = ps.executeUpdate();
			
			if(count == 1) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("pointUpWrite : " + e);
		}
		
		return false;
		
	}
	
	// 댓글 삭제시 -1 포인트
	public boolean pointDownReview( int mno ) {
		
		try {
			
			String sql = "insert into point ( pcontent , pcount , mno ) "
							+ "values( '댓글 삭제' , -1 , ?  ) ";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mno);
			
			int count = ps.executeUpdate();
			
			if(count == 1) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("pointUpWrite : " + e);
		}
		
		return false;
		
	}
	
	// 추천수 +10 단위로 exp +20
	public boolean expUpBoomUp( int mno ) {
		
		try {
			
			String sql = "update member set mexp = mexp+20 where mno = ?";
			
			PreparedStatement ps5 = conn.prepareStatement(sql);
			
			ps5.setInt(1, mno);
			
			int count = ps5.executeUpdate();
			
			if(count == 1 ) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("expUpWrite " + e);
		}
		return false;
	}
	
	// 추천수 +10 단위로 point +10
	public boolean pointUpBoomUp( int bCount , int mno ) {
		
		try {
			
			String sql = "select * from point "
								+ "where pcontent = '추천 "+bCount+" 적립' and mno = "+ mno;
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return false;
			}
			
			sql = "insert into point ( pcontent , pcount , mno ) "
							+ "values( '추천 "+ bCount +" 적립' , 10 , ?  ) ";
			
			PreparedStatement ps6 = conn.prepareStatement(sql);
			
			ps6.setInt(1, mno);
			
			int count = ps6.executeUpdate();
			
			if(count == 1) {
				return true;
			}
			
		} catch (Exception e) {
			System.out.println("pointUpWrite : " + e);
		}
		
		return false;
	}
	
}// class end


