package model.dto;

public class MemberDto {

	// 회원 테이블
	private int mno;
	private String mname;
	private String mpwd;
	private String memail;
	private String mimg;
	private int mexp;
	private int mpoint;
	public MemberDto() {
		super();
	}
	public MemberDto(int mno, String mname,  String mpwd, String memail, String mimg, int mexp) {
		super();
		this.mno = mno;
		this.mname = mname;
		this.mpwd = mpwd;
		this.memail = memail;
		this.mimg = mimg;
		this.mexp = mexp;
	}
	
	// 회원가입 생성자
	public MemberDto(String mname,  String mpwd, String memail) {
		super();
		this.mname = mname;
		this.mpwd = mpwd;
		this.memail = memail;
	}
	// 로그인 생성자
	public MemberDto( String memail, String mpwd ) {
		super();
		this.mpwd = mpwd;
		this.memail = memail;
	}
	// 정보 호출 생성자
		public MemberDto(int mno, String mname, String memail, String mimg, int mexp, int mpoint) {
		super();
		this.mno = mno;
		this.mname = mname;
		this.memail = memail;
		this.mimg = mimg;
		this.mexp = mexp;
		this.mpoint = mpoint;
	}			
	// 프로필 수정 시 사용하는 생성자	
	public MemberDto(int mno, String mname, String mimg) {
			super();
			this.mno = mno;
			this.mname = mname;
			this.mimg = mimg;
		}
	public int getMpoint() {
			return mpoint;
		}
		public void setMpoint(int mpoint) {
			this.mpoint = mpoint;
		}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMpwd() {
		return mpwd;
	}
	public void setMpwd(String mpwd) {
		this.mpwd = mpwd;
	}
	public String getMemail() {
		return memail;
	}
	public void setMemail(String memail) {
		this.memail = memail;
	}
	public String getMimg() {
		return mimg;
	}
	public void setMimg(String mimg) {
		this.mimg = mimg;
	}
	public int getMexp() {
		return mexp;
	}
	public void setMexp(int mexp) {
		this.mexp = mexp;
	}
	@Override
	public String toString() {
		return "MemberDTO [mno=" + mno + ", mname=" + mname + ", mname=" + mname + ", mpwd=" + mpwd + ", memail=" + memail
				+ ", mimg=" + mimg + ", mexp=" + mexp + "]";
	}
	

	
	
}
