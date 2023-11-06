package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.dto.PointDto;

public class PointDao extends Dao{
	
	//싱글톤
	private static PointDao pointDao = new PointDao();
	private PointDao () {}
	public static PointDao getinstance() { return pointDao; }
	//0. 포인트 총합 반환 메서드
	public int getPoint( int mno ) {
		try {
			System.out.println("mno = " + mno);
 			rs = conn.prepareStatement("select sum(pcount) from point where mno = " + mno).executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {e.printStackTrace();}
		return -1;
	}
	//1. 포인트 내역 호출
	public ArrayList<PointDto> getPointLog( int mno , int startrow){
		ArrayList<PointDto> list = new ArrayList<>();
		System.out.println(mno);
		try {
			rs = conn.prepareStatement("select pno,date_format(pdate, '%Y-%m-%d %h:%m')"
						+ ",pcontent,pcount,mno from point "
						+ "where mno = "+ mno
						+ " order by pdate desc limit "+startrow+" , 10 ").executeQuery();
			while( rs.next() ) {
				list.add( new PointDto( rs.getInt("pno")
						, rs.getString(2)
						, rs.getString("pcontent")
						, rs.getInt("pcount")));
			} return list; 
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	//2. 포인트 입출력 내역 추가(수정)
	public PointDao updatePointSql( String content , int pcount , int mno ){
		
		return null;
	}
	// 3. 총 포인트 레코드 수
	public int getPointLogTotalSize( int mno ) {
		try {
			rs = conn.prepareStatement("select count(*) from point where mno = " + mno).executeQuery();
			if( rs.next() ) return rs.getInt(1); 
		} catch (Exception e) {e.printStackTrace();}
		return 0;
	}
}// class end
