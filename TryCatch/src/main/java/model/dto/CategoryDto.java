package model.dto;

public class CategoryDto {

	// 메인
	private int cno;
	private String cname;
	
	// 서브
	private int subcno;
	private String subcname;
	public CategoryDto(int cno, String cname, int subcno, String subcname) {
		super();
		this.cno = cno;
		this.cname = cname;
		this.subcno = subcno;
		this.subcname = subcname;
	}
	
	public CategoryDto(int subcno, String subcname) {
		super();
		this.subcno = subcno;
		this.subcname = subcname;
	}

	public CategoryDto() {
		super();
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public int getSubcno() {
		return subcno;
	}
	public void setSubcno(int subcno) {
		this.subcno = subcno;
	}
	public String getSubcname() {
		return subcname;
	}
	public void setSubcname(String subcname) {
		this.subcname = subcname;
	}
	@Override
	public String toString() {
		return "CategoryDTO [cno=" + cno + ", cname=" + cname + ", subcno=" + subcno + ", subcname=" + subcname + "]";
	}

	
	
	
	
	
	
}