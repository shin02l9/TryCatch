package model.dao;

import java.util.ArrayList;

import model.dto.CategoryDto;

public class CategoryDao extends Dao{
	
	//싱글톤
	private static CategoryDao categoryDao = new CategoryDao();
	private CategoryDao() {}
	public static CategoryDao getinstance() { return categoryDao; }
	
	
	//1. 카테고리 출력
	public ArrayList<CategoryDto> catePrint(){
		return null;
	}
	
	//2. 카테고리 
	
}// class end
