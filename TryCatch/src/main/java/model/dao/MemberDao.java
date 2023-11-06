package model.dao;

import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.AlarmMessageServerSocket;
import model.dto.AlarmMsgDto;
import model.dto.MemberDto;

public class MemberDao extends Dao{
	
	//싱글톤
	private static MemberDao memberDao = new MemberDao();
	private MemberDao() {}
	public static MemberDao getinstance() { return memberDao; }
	
	//1. 회원가입
	public synchronized boolean signUpSql( MemberDto memberDto ){
		try {
			String sql = "insert into member( mname, mpwd, memail, mimg) values( ? , ? , ? , 'default.webp')";
			ps = conn.prepareStatement(sql);
			ps.setString(1, memberDto.getMname()); ps.setString(2, memberDto.getMpwd());		
			ps.setString(3, memberDto.getMemail());
			if( ps.executeUpdate() == 1 )
				return true;
		}catch(Exception e) {e.printStackTrace();}			
		return false;
	} 
	//1-1. 아이디 중복검사
	public synchronized boolean checkId( String id ) {
		try {
			String sql = "select * from member where mid = "+id;
			// 검색 결과가 있으면 false 반환
			return conn.prepareStatement(sql).executeQuery().next() ? false : true;
		} catch (Exception e) {e.printStackTrace();}	
		return true;
	}
	// 1-2 이메일 중복검사
	public synchronized boolean checkEmail( String email ){
		try {
			String sql = "select * from member where email = "+email;			
			// 검색 결과가 있으면 false 반환
			return conn.prepareStatement(sql).executeQuery().next() ? false : true;
		} catch (Exception e) {e.printStackTrace();}			
		return true;
	}	
	//2. 로그인 
	public synchronized MemberDto loginSql( MemberDto memberDto ){
		try {			
			String sql = "select * from member where memail = ? and mpwd = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, memberDto.getMemail()); ps.setString(2, memberDto.getMpwd());
			rs = ps.executeQuery();
			if( rs.next() ) {
				int mno = rs.getInt("mno");
				int point =	PointDao.getinstance().getPoint(mno);
				System.out.println(attendanceCheck(mno)); 
					return new MemberDto( 
							mno ,rs.getString("mname"),
						rs.getString("memail"),rs.getString("mimg"),
						rs.getInt("mexp") , point);									
			}					
		} catch (Exception e) {e.printStackTrace();}
		return null;
	} 
	
	//4. 비밀번호찾기
	public synchronized String findPwSql( String email ){
		try {
			String sql = "select mpwd from member where memail = " + "'"+email+"'";
			rs = conn.prepareStatement(sql).executeQuery();
			if( rs.next() ) return rs.getString("mpwd");
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	// 5. 내정보 호출
	public synchronized MemberDto getInfo( int mno ) {
		try {
			rs = conn.prepareStatement("select * from member where mno = " + mno).executeQuery();
			if( rs.next() ) {
				int point =	PointDao.getinstance().getPoint(mno);				
				return new MemberDto( 
						rs.getInt("mno"),rs.getString("mname"),
						rs.getString("memail"),rs.getString("mimg"),
						rs.getInt("mexp") , point);				
			}
		} catch (Exception e) {e.printStackTrace();}
			return null;
	}
	//6-1 회원 수정 전 확인
	public synchronized boolean checkBeforUpdateMember( MemberDto memberDto ) {
		try {
			rs = conn.prepareStatement("select mname from member where mno = "+memberDto.getMno()).executeQuery();
			if(rs.next()) {
				if( rs.getString("mname").equals( memberDto.getMname()) && memberDto.getMimg() == null ) return false;					
			}
		} catch (Exception e) {e.printStackTrace();} return true;					
	}
	//6-2 회원수정
	public synchronized int updateMemberSql( MemberDto memberDto ){

		// 수정 내용이 이미 본인 정보와 같으면 2 반환
		if( !checkBeforUpdateMember(memberDto) ) return 2;

		try {
			String sql = "update member set mname = "+"'"+memberDto.getMname()+"' " ;
			// 수정할 이미지가 null이 아니면 sql문에 이미지 set 추가
			if( memberDto.getMimg() != null ) sql += ",mimg = "+"'"+memberDto.getMimg()+"' ";
			sql += "where mno = "+memberDto.getMno();
			// 업데이트 성공 3 반환 실패 시 0 반환
			return conn.prepareStatement(sql).executeUpdate() == 1 ? 3 : 0;
			
		} // unique에 걸려 예외(IntegrityConstraintViolationException)가 발생하면 1 반환
		catch(SQLIntegrityConstraintViolationException i) {return 1; }
		catch (Exception e) {e.printStackTrace();}	
		// 실패 0반환
		return 0;
	} 
	//7. 회원 탈퇴
	public synchronized boolean deleteMemberSql( MemberDto memberDto ){
		try {
			if(conn.prepareStatement("delete from member where mno = "+memberDto.getMno()).executeUpdate() == 1) return true;				
		} catch (Exception e) {e.printStackTrace();}				
		return false;
	} 	
	// 8. 출석 체크 날짜 확인
	public synchronized boolean attendanceDateCheck( int mno ) {
		// 하루 단위, 데이터베이스와 대조하여 중복된다면 포인트 지급 안함.
		String dateTime = DateTimeFormatter.ofPattern("yy-MM-dd").format(LocalDateTime.now());		
		try {
			ResultSet rs2 = conn.prepareStatement("select date_format( pdate , \"%y-%m-%d\") from point where mno = "+mno+" order by pdate desc").executeQuery();
			if(rs2.next()) return rs2.getString(1).equals(dateTime) ? false : true;
			else return true;
		} catch (Exception e) {e.printStackTrace();}
		return false;
	}
	// 9. 출석 체크
	public synchronized boolean attendanceCheck( int mno ) {
		System.out.println("attendanceCheck 출석체크 입장");
		
		try {
			// 날짜 체크 메소드 flase 반환 시 포인트 지급 안함
			System.out.println( attendanceDateCheck(mno) + " cheack" );
			if( !attendanceDateCheck(mno) ) return false;
			
			ps = conn.prepareStatement("insert into point( pcontent , pcount , mno ) values( ? , ? , ? )");
			ps.setString(1, "출석체크");ps.setInt(2, 10);ps.setInt(3, mno);
			
			// 예지 : 알람기능 -------------------------------------------------
			AlarmMsgDto dto = new AlarmMsgDto( mno , "⊙ 출석체크로 10p가 지급되었습니다.");
			ObjectMapper mapper = new ObjectMapper();
			
			AlarmMessageServerSocket serverSocket = new AlarmMessageServerSocket();
			serverSocket.OnMessage(null,  mapper.writeValueAsString(dto) );
			

			return ps.executeUpdate() == 1 ? true : false;
		} catch (Exception e) {e.printStackTrace();}
		return false;
	}
}// class end
