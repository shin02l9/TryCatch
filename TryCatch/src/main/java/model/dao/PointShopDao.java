package model.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.dto.ColorProduct;

public class PointShopDao extends Dao{
	//싱글톤
	private static PointShopDao pointShopDao = new PointShopDao();
	private PointShopDao () {}
	public static PointShopDao getInstance() { return pointShopDao; }
	// 유저 구매 내역 리스트 
	public List<ColorProduct> getBuyList( int mno ){		
		List<ColorProduct> list = new ArrayList<>();
		try {
			ResultSet rs2;
			rs = conn.prepareStatement("select * from userBuyLog where mno = " + mno).executeQuery();
			while( rs.next() ) {
				// 상품 번호
				int pcno = rs.getInt("pcno");
				rs2 = conn.prepareStatement("select * from productList where pcno = "+pcno).executeQuery();
				rs2.next();
				list.add( new ColorProduct( pcno , rs2.getString("pcname"),rs.getBoolean("pestate"))  );
			}
			return list;
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	// 상품 리스트
	public List<ColorProduct> getProductList(){
		List<ColorProduct> list = new ArrayList<>();
		try {
			rs = conn.prepareStatement("select * from productList").executeQuery();
			while( rs.next() ) {
				list.add( new ColorProduct( 
						rs.getInt("pcno"),
						rs.getString("pcname"),
						rs.getString("pccode")
						));
			}
			return list;
		}catch (Exception e) {e.printStackTrace();}
		return null;		
	}
	// 구매 메소드 
	// 포인트 부족 0, 성공 1, 실패 2, 에러 3
	public int onBuy( int mno , int pcno ) {
		try {
			// 포인트가 1000 미만이면 0 반환
			if( !(PointDao.getinstance().getPoint(mno) >= 1000)  ) return 0;
			ps = conn.prepareStatement("insert into userBuyLog( mno , pcno ) values( ? , ? )");
			ps.setInt( 1 , mno ); ps.setInt( 2 , pcno );
			if( ps.executeUpdate() == 1) {
				conn.prepareStatement(
						"insert into point( pcontent , pcount , mno ) values( '"+pcno+"번 상품 구매'" +"  , "+ -1000+ " , "+mno+" )").executeUpdate();
				return 1;				
			}else return 2;			
		} catch (Exception e) {e.printStackTrace();}
		return 3;
	}
	// 장착하기
	public boolean onEquip( int mno , int pcno ) {
		try {
			//현재 true상태인 레코드 0으로 만든 후 1로 수정
			String sql = "update userBuyLog set pestate = case "
					+ "when pestate = 1 and mno = "+mno+" then 0 "
							+ "when pcno = "+pcno+" and mno = "+mno+" then 1 "
									+ "else pestate end where mno = "+ mno;
			conn.prepareStatement(sql).executeUpdate();
			// 멤버 테이블 컬러 장착 메서드
			onEquipMember( mno , pcno);
			return true;
		} catch (Exception e) {e.printStackTrace();}
		return false;
	}
	// onEquip()과 더불어 member 테이블 색상 컬럼또한 업데이트
	public boolean onEquipMember( int mno , int pcno ) {
		try {
			String sql = "update member m ,"
					+ "(select pccode from member m , productList p where m.mno = "+mno+" and p.pcno = "+pcno+") p "
					+ "set m.mcolor = p.pccode where m.mno = "+mno;
			conn.prepareStatement(sql).executeUpdate();
			return true;
		} catch (Exception e) {e.printStackTrace();}
		return false;
	}
}
