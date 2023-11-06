package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.MainDao;
import model.dto.BoardDto;


@WebServlet("/MainHottopicController")
public class MainHottopicController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MainHottopicController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<BoardDto> result = new ArrayList<>();
			
		// 요청 없음
		
		// DAO 처리
		result = MainDao.getinstance().printHot();
		
		System.out.println(result);
		
		// 매핑
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonArray = objectMapper.writeValueAsString( result ); // JSON타입으로 변환은 불가능하지만 JSON형식의 문자열타입 로 변환 
			System.out.println( jsonArray );
		// 응답 
		// response.getWriter().print(result); // 응답은 가능하나... js가  ArrayList타입 사용이 불가능 [ 문제발생 ]
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print( jsonArray );
	}


}
