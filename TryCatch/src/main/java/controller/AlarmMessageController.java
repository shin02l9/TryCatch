package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.SearchDao;

@WebServlet("/AlarmMessageController")
public class AlarmMessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AlarmMessageController() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println( "AlarmMessageController [ doGet ] 입장" );
		int bno = Integer.parseInt( request.getParameter("bno") ) ;
		int result = SearchDao.getinstance().forAlarmMessage(bno);
		response.getWriter().print(result);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
