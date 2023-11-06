package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dao {
	
	//1. 필드
		//[ import java.sql ]
		public Connection conn; // DB 연동 객체
		public PreparedStatement ps; //연동된 DB 조작 객체
		public ResultSet rs; // SQL 조작 결과를 가져오는 객체
		
		//2. 생성자 : 객체 생성시 초기화 담당
			//기본생성자에 DB 연동 코드 작성 * DAO 객체 생성시 바로 DB 연동
		public Dao() { 
			
			try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/TryCatch"
													, "root" ,"1234");
				System.out.println("안내] DB 연동 성공");
			}
			catch (Exception e) {
				System.out.println("경고] DB연동 실패");
			}// try catch end
		}//Dao end
	}//class end
