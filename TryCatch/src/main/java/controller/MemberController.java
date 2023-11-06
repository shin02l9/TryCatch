package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.MemberDao;
import model.dao.PointDao;
import model.dto.MemberDto;
import model.dto.PageDto;
import model.dto.PointDto;


@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberController() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String type = request.getParameter("type");		
		// id와 이메일 중복체크
		if( "checkNameAndEmail".equals(type) ) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			if( !MemberDao.getinstance().checkId(name))
				response.getWriter().print("existsName"); 
			else if( !MemberDao.getinstance().checkEmail(email) )
				response.getWriter().print("existsEmail");
			else 
				response.getWriter().print("pass");
		}
		// 로그인 여부 확인
		else if( "info".equals(type)) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(
					new ObjectMapper().writeValueAsString(
							((MemberDto)request.getSession().getAttribute("loginDto")))); 			
		}
		// 로그아웃
		else if( "logout".equals(type)) {
			
			int mno = ((MemberDto)request.getSession().getAttribute("loginDto")).getMno();
			String filePath = request.getServletContext().getRealPath("/DB/log/logFile"+((MemberDto)request.getSession().getAttribute("loginDto")).getMno()+".txt");
			try {
				WriteLog.getinstance().writeLog( filePath , mno + " logout");		
			} catch (Exception e) { System.out.println( e ); }
			
			request.getSession().setAttribute("loginDto", null);
		}
		// 비밀번호 찾기
		else if( "findPassword".equals(type)) {
			String mpwd = MemberDao.getinstance().findPwSql(request.getParameter("email"));
			if( mpwd != null)
				response.getWriter().print(mpwd);
			else
				response.getWriter().print("notFound");
		}
		// 포인트 내역 가져오기
		else if( "getPointLog".equals(type)) {
			// 현재 페이지
			int page = Integer.parseInt(request.getParameter("page"));
			// 전체 레코드 수
			int totalsize = PointDao.getinstance().getPointLogTotalSize(((MemberDto)request.getSession().getAttribute("loginDto")).getMno());
			// 레코드 시작 
			int startrow = (page-1) * 10;
			// 총 페이지 수
			int totalpage = totalsize%10 == 0 ? 
								totalsize/10 : 
									totalsize/10 + 1;
			// 출력할 버튼 개수
			int btnsize = 5;
			// 시작 버튼 숫자
			int startbtn = ((page-1) / btnsize ) * btnsize + 1;
			// 끝 버튼 숫자
			int endbtn = startbtn+(btnsize-1);
			// 총페이지 수로 제한두기
			if( endbtn >= totalpage ) endbtn = totalpage;
			// startrow에 따른 게시물 수
			ArrayList<PointDto> result = PointDao.getinstance().getPointLog( 
											((MemberDto)request.getSession().getAttribute("loginDto")).getMno(), startrow);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print( 
					new ObjectMapper().writeValueAsString(
							new PageDto( page , totalpage, startbtn,startrow , result ))); 														
		}	
	}
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String type = request.getParameter("type");
		boolean	result = false;
		// 회원가입
		if("signUp".equals(type)) {
			result = MemberDao.getinstance().signUpSql(
					new MemberDto(request.getParameter("name"), request.getParameter("pwd"),
									request.getParameter("email")));																				
		}
		// 로그인
		else if("login".equals(type)) {
			MemberDto loginDto = MemberDao.getinstance().loginSql( new MemberDto( request.getParameter("loginEmail") , request.getParameter("loginPwd")));		
			if( loginDto != null )  { 
				request.getSession().setAttribute("loginDto", loginDto);
				String filePath = request.getServletContext().getRealPath("/DB/log/logFile"+loginDto.getMno()+".txt");
				System.out.println(filePath);
				result = true;
				try {
					WriteLog.getinstance().writeLog( filePath , loginDto.getMno() + " login");		
				} catch (Exception e) { System.out.println( e ); }				
			}
			else 
				result = false;				
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(result);							
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		

	}
}
