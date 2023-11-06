package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.BoardDao;
import model.dao.Filtering;
import model.dto.BoardDto;
import model.dto.CategoryDto;
import model.dto.MemberDto;
import model.dto.PageDto;


@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BoardController() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int type = Integer.parseInt(request.getParameter("type"));
		/*
			0 = 서브 카테고리 불러오기
			1 = 카테고리별 출력
			2 = 개별 게시물 출력
			3 = 리뷰출력
			4 = 수정할 리뷰 가져오기
			5 = 공지사항 가져오기
		*/
		
		
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		
		// 카테고리별 출력
		if( type == 1 ) {
		
			int subcno = Integer.parseInt(request.getParameter("subcno"));
			
			int listLimit = Integer.parseInt( request.getParameter("listLimit") );
			int page =  Integer.parseInt( request.getParameter("page") );
			
			String key = request.getParameter("key");
			String keyword = request.getParameter("keyword");
			System.out.println("key : " + key);
			//시작 레코드
			int startrow = (page-1)*listLimit;
			
			int totalsize = BoardDao.getinstance().getTotalSize(subcno);
			
			int totalpage = totalsize%listLimit == 0 ? 
								totalsize/listLimit :
									totalsize/listLimit+1;
			
			int btnsize = 5;
			
			int startBtn = ( (page-1)/btnsize ) * btnsize + 1;
			int endBtn = startBtn+( btnsize - 1 );
			
			if( endBtn >= totalpage ) {
				endBtn = totalpage;
			}
			
			
			ArrayList<BoardDto> result = BoardDao.getinstance().getList(subcno , listLimit , startrow , key , keyword );
			
			PageDto pageDto = new PageDto(page, listLimit, startrow, totalsize, totalpage, startBtn, endBtn, result);
			
			json = mapper.writeValueAsString(pageDto);
		
			
		}
		
		//개별 개시물 출력
		else if( type == 2) {
			
			int bno = Integer.parseInt( request.getParameter("bno") );
			

			BoardDto result = BoardDao.getinstance().getBoard(bno);
			
			int blike = BoardDao.getinstance().getblike(bno);
			
			BoardDto boardDto = new BoardDto(result, blike);
			
			System.out.println(boardDto);
			
			json = mapper.writeValueAsString(boardDto);
			
		}
		
		else if( type == 3) {
			
			int bno = Integer.parseInt( request.getParameter("bno") );
			
			ArrayList<BoardDto> result = BoardDao.getinstance().getReview( bno );
			
			json = mapper.writeValueAsString(result);
			
		}
		
		else if(type == 4) {
			int reno = Integer.parseInt(request.getParameter("reno"));
			
			String result = BoardDao.getinstance().getreUpdate( reno );
			
			json = mapper.writeValueAsString(result);
			
		}
		
		else if(type == 5) {
			
			int subcno = Integer.parseInt(request.getParameter("subcno"));
			
			ArrayList<BoardDto> result = BoardDao.getinstance().getNoticeList(subcno);

			json = mapper.writeValueAsString(result);
			
		}
		
		else if (type == 0) {
			
			int cno = Integer.parseInt( request.getParameter("cno") );
			
			ArrayList<CategoryDto> result = BoardDao.getinstance().getsubCategory(cno);
			
			json = mapper.writeValueAsString(result);
		}
		
		
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(json);
	
		
	}
		
		
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type");
		int mno = ((MemberDto)request.getSession().getAttribute("loginDto")).getMno();

		if(type.equals("boomup")) {
				
			int bno = Integer.parseInt(request.getParameter("bno") );
			
			int result = BoardDao.getinstance().boomUp(bno, mno);
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result);
			
		}
		else if( type.equals("reviewWrite")) {
			
			String recontent = request.getParameter("recontent");
			int reindex = Integer.parseInt(request.getParameter("reindex"));
			int bno = Integer.parseInt(request.getParameter("bno"));
			
			boolean result = BoardDao.getinstance().reviewWrite(recontent, reindex, bno, mno);
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result);
			
		}
		
		else if(type.equals("reviewUp")) {
			
		int reno = Integer.parseInt(request.getParameter("reno") );
		
		int result = BoardDao.getinstance().reviewUp(reno, mno);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(result);
		
		}
		
		else if(type.equals("onWrite")) {
			
			int subcno = Integer.parseInt(request.getParameter("subcno"));
			String btitle = request.getParameter("btitle");
			String bcontent = request.getParameter("bcontent");
			System.out.println(btitle);
				
			
			String filepath = request.getServletContext().getRealPath("/DB"+"/DoNotOpen2.txt");
			System.out.println(filepath);
			
			btitle = Filtering.getinstance().slangFilter(filepath,btitle);
			
			
				
			BoardDto boardDto = new BoardDto( btitle ,bcontent , mno , subcno );
			
			boolean result = BoardDao.getinstance().onWrite( boardDto );
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result);
		}
		
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		String type = request.getParameter("type");
		
		if( type.equals("reviewUpdate") ) {
			
			int reno = Integer.parseInt(request.getParameter("reno"));
			String recontent = request.getParameter("recontent");
			
			boolean result = BoardDao.getinstance().reviewUpdate( reno , recontent );
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result);
			
		}
		
		if( type.equals("onUpdate")) {
			
			int bno = Integer.parseInt(request.getParameter("bno"));
			String btitle = request.getParameter("btitle");
			String bcontent = request.getParameter("bcontent");
			int subcno = Integer.parseInt(request.getParameter("subcno"));
			
			
			BoardDto boardDto = new BoardDto(bno, btitle, bcontent, subcno);
			
			boolean result = BoardDao.getinstance().onUpdate( boardDto );
					
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result);
			
		}
			
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type");
		int mno = ((MemberDto)request.getSession().getAttribute("loginDto")).getMno();
		
		System.out.println(type);
		
		if(type.equals("boardDelete")) {
			
			int bno = Integer.parseInt(request.getParameter("bno"));
			
			boolean result = BoardDao.getinstance().onDelete(bno , mno);
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result);
		}
		
		else if(type.equals("reviewDelete")) {
			
			int reno = Integer.parseInt(request.getParameter("reno"));
			
			boolean result = BoardDao.getinstance().reviewDelete(reno , mno);
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result);

		}
		
	}

}
