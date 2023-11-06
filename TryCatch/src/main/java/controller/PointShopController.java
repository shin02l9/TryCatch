package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.PointShopDao;
import model.dto.ColorProduct;
import model.dto.MemberDto;


@WebServlet("/PointShopController")
public class PointShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PointShopController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 버튼 랜더링
		response.setContentType("application/json;charset=UTF-8");
		String type = request.getParameter("type");
		List<ColorProduct> result;
		ObjectMapper mapper = new ObjectMapper();
		// 색상 리스트 가져오기
		if( "getColorList".equals( type )) {
			result = PointShopDao.getInstance().getProductList();
			response.getWriter().print( mapper.writeValueAsString(result));
		}
		// 구매 리스트 가져오기
		else if( "getBuyList".equals( type ) ) {
			result = PointShopDao.getInstance().getBuyList(((MemberDto)request.getSession().getAttribute("loginDto")).getMno()); 
			if( result == null ) response.getWriter().print(false);
			else response.getWriter().print( mapper.writeValueAsString(result) );
		}
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 구매하기
		response.getWriter().print(
				PointShopDao.getInstance().onBuy(
						((MemberDto)request.getSession().getAttribute("loginDto")).getMno()
						,Integer.parseInt(request.getParameter("pcno"))));	
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 장착하기
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(
				PointShopDao.getInstance().onEquip(
						((MemberDto)request.getSession().getAttribute("loginDto")).getMno() 
						, Integer.parseInt(request.getParameter("pcno")))
				);	
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
