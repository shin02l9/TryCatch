package model.dto;

public class PointDto {

	
	// 포인트 테이블
	private int pno;
	private String pdate;
	private String pcontent;
	private int pcount;
	
	

	
	public PointDto(int pno, String pdate, String pcontent, int pcount) {
		super();
		this.pno = pno;
		this.pdate = pdate;
		this.pcontent = pcontent;
		this.pcount = pcount;
	}
	
	public PointDto() {
		super();
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getPdate() {
		return pdate;
	}
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}
	public String getPcontent() {
		return pcontent;
	}
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	public int getPcount() {
		return pcount;
	}
	public void setPcount(int pcount) {
		this.pcount = pcount;
	}
	@Override
	public String toString() {
		return "PointDTO [pno=" + pno + ", pdate=" + pdate + ", pcontent=" + pcontent + ", pcount=" + pcount + "]";
	}
	
	
	
	
	
}
