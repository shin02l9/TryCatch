package model.dto;
// 색 상품 Dto
public class ColorProduct {
	// 상품 번호
	private int pcno;
	// 상품(색상) 이름
	private String pcname;
	private String pccode;
	// 장착 상태	
	private boolean pestate;
		
	public ColorProduct() {}
	
	

	public ColorProduct(int pcno, String pcname, String pccode) {
		super();
		this.pcno = pcno;
		this.pcname = pcname;
		this.pccode = pccode;
	}
	public ColorProduct(int pcno, String pcname, boolean pestate) {
		super();
		this.pcno = pcno;
		this.pcname = pcname;
		this.pestate = pestate;
	}	
	public String getPccode() {
		return pccode;
	}
	public void setPccode(String pccode) {
		this.pccode = pccode;
	}
	public int getPcno() {
		return pcno;
	}
	public void setPcno(int pcno) {
		this.pcno = pcno;
	}
	public String getPcname() {
		return pcname;
	}
	public void setPcname(String pcname) {
		this.pcname = pcname;
	}
	public boolean isPestate() {
		return pestate;
	}
	public void setPestate(boolean pestate) {
		this.pestate = pestate;
	}
	@Override
	public String toString() {
		return "ColorProduct [pcno=" + pcno + ", pcname=" + pcname + ", pestate=" + pestate + "]";
	}
}
