package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.dao.MemberDao;
import model.dto.MemberDto;


@WebServlet("/MemberProfileController")
public class MemberProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberProfileController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 기준
		response.getWriter().print(((MemberDto)request.getSession().getAttribute("loginDto")).getMimg()); 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MultipartRequest multi = new MultipartRequest(
				request ,
				request.getSession().getServletContext().getRealPath("/memberimg") ,
				1024 * 1024 * 10,
				"UTF-8",
				new DefaultFileRenamePolicy()
		);
		MemberDto dto = new MemberDto(((MemberDto)request.getSession().getAttribute("loginDto")).getMno() , multi.getParameter("mname") ,  multi.getFilesystemName("file"));
		// 결과 반환 0 실패 , 1 중복 , 2 정보 일치 , 3 성공
		int result = MemberDao.getinstance().updateMemberSql(dto);
		if( result == 3) { request.getSession().setAttribute("loginDto",MemberDao.getinstance().getInfo(dto.getMno())); }
		response.getWriter().print(result);
	}
	// 회원탈퇴
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean result = MemberDao.getinstance().deleteMemberSql((MemberDto)req.getSession().getAttribute("loginDto"));
		// 세션 null로 설정
		if( result ) { req.getSession().setAttribute("loginDto", null );}
		resp.setContentType("apllication/json;charset=UTF-8");
		resp.getWriter().print(result);
	}

}
