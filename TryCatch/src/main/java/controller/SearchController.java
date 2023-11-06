package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.SearchDao;
import model.dto.BoardDto;

@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SearchController() {super();}
    
    // 키워드 전달 받아서 원하는 값 반환해주기 (신예지)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println( "getSearchList [ doGet ] 입장" );
		
		// 요청하기
		String keyword = request.getParameter("keyword"); // 검색 키워드
			System.out.println("keyword : "+keyword);
		
		// DAO 에게 받을 값을 저장할 list 만들어 두기
			// sql문으로 전달 받은 조회결과 게시물리스트
		List<BoardDto> list = new ArrayList<>();
			// list에서 중복제거 및 정렬 작업 후 response할 게시물리스트
		List<BoardDto> result = new ArrayList<>();
		
		// DAO 처리하기
			// DB에서 정보 받기 
			list = SearchDao.getinstance().searchAllPrint(keyword);
			System.out.println(list);
			// 누적계산( count )을 반복문을 중복하지 않도록 하기 위한 HashSet 
			// [ DP 메모이제이션 ]
			HashSet<Integer> memoMap = new HashSet<>();  
			
			// list 전체 탐색 실시
			for( int i=0; i<list.size(); i++ ) {
				
				// 탐색 대상 게시물의 bno를 저장
				int bno = list.get(i).getBno();
				// 누적계산을 저장하기 위한 count
				int count = 0;
				
				// hashSet 안에 값이 이미 있다면 중복계산할 필요가 없으므로
				// 다음 게시물을 탐색
				if( memoMap.contains( list.get(i).getBno() ) ) {
					continue;
				}
				
				// 이전에 탐색하지 않은 게시물은 리스트에 저장
				result.add( list.get(i) );
				
				// 현재 탐색 대상 게시물과 리스트 안에 존재하는 다른 게시물을 비교해서
				// 동일한 게시물이 존재하면 누적연산
				for( int j=0; j<list.size(); j++ ) {
					if( bno == list.get(j).getBno() ) {
						count++;
					}
				}
				
				memoMap.add(bno);
				list.get(i).setCount( count );
			}
			
			// 정렬 클래스를 사용
			// ex) Arrays.sort / Collections.sort
				// 정렬을 하기 위한 조건은 기본자료형, 혹은 문자열이 가능하지만
				// BoardDto는 해당되지 않으므로 인터페이스를 사용해서 정의를 해야함
			Collections.sort( result );

		// 매핑하기
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString( result );
		//System.out.println(jsonData);
		
		// 응답하기
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonData);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
