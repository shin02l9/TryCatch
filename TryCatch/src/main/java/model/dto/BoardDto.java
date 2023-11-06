package model.dto;



public class BoardDto implements Comparable<BoardDto> {
	

	
	// 게시판
	private int bno;
	private String btitle;
	private String bcontent;
	private String bdate;
	private int bview;
	private int blike;
	
	// 회원번호, 회원이름(추가) , 서브 카테고리 번호 , 카테고리 이름(추가)
	private int mno;
	private String mname;
	private String mimg;
	private int mexp;
	private String mcolor;
	
	private int subcno;
	private String subcname;
	// 해시태그
	private int tno;
	private String tname;
	
	// 댓글
	private int reno;
	private String recontent;
	private int reindex;
	private String redate;
	private int relike;
	
	
	private BoardDto bDto;
	
	
	private int count;

	// 정렬을 하기 위해 정의한 메서드
	@Override
	public int compareTo(BoardDto o) {
		return o.count - this.count;
	}
	
	
	public BoardDto() {
		// TODO Auto-generated constructor stub
	}

	public BoardDto(int bno, String btitle, String bcontent, String bdate, int bview, int blike, int mno, String mname,
			String mimg, int subcno, String subcname, int tno, String tname, int reno, String recontent, int reindex,
			String redate, int relike , int mexp , String mcolor) {
		super();
		this.bno = bno;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.bdate = bdate;
		this.bview = bview;
		this.blike = blike;
		this.mno = mno;
		this.mname = mname;
		this.mimg = mimg;
		this.subcno = subcno;
		this.subcname = subcname;
		this.tno = tno;
		this.tname = tname;
		this.reno = reno;
		this.recontent = recontent;
		this.reindex = reindex;
		this.redate = redate;
		this.relike = relike;
		this.mexp = mexp;
		this.mcolor = mcolor;
	}

	// 글 전체보기 출력용
	public BoardDto(int bno, String subcname , String mname , String mimg ,
					String btitle , String bcontent ,
					int bview , String bdate ,
					int blike , String mcolor) {
		super();
		this.bno = bno;
		this.subcname = subcname;
		this.mname = mname;
		this.mimg = mimg;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.bview = bview;
		this.bdate = bdate;
		this.blike = blike;
		this.mcolor = mcolor;
	}
	
	// 개별 글 출력용
	public BoardDto(int bno, String btitle, 
					String bcontent, String bdate, 
					int bview, 
					int mno, int subcno,
					String mname, String mimg ,  String mcolor) {
		super();
		this.bno = bno;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.bdate = bdate;
		this.bview = bview;
		this.mno = mno;
		this.subcno = subcno;
		this.mname = mname;
		this.mimg = mimg;
		this.mcolor = mcolor;
	}	
	
	
	public BoardDto(int bno, String btitle, 
			String bcontent, String bdate, 
			int bview, 
			int mno, int subcno,
			String mname, String mimg,
			int count , String mcolor) {
	super();
	this.bno = bno;
	this.btitle = btitle;
	this.bcontent = bcontent;
	this.bdate = bdate;
	this.bview = bview;
	this.mno = mno;
	this.subcno = subcno;
	this.mname = mname;
	this.mimg = mimg;
	this.count = count;
	this.mcolor = mcolor;
	
	}	

	
	// 개별 글 출력용( 검색용 )
	public BoardDto(int bno, String btitle, 
					String bcontent, String bdate, 
					int bview, 
					int mno, int subcno,
					String mname, String mimg, int count) {
		super();
		this.bno = bno;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.bdate = bdate;
		this.bview = bview;
		this.mno = mno;
		this.subcno = subcno;
		this.mname = mname;
		this.mimg = mimg;
		this.count = count;
	}	

	//개별 글 출력용+ 좋아요 추가
	public BoardDto( BoardDto bDto , int blike) {
		super();
		this.bDto = bDto;
		this.blike = blike;
	}

	public BoardDto(int bno, String btitle, String bcontent, int bview, String mname) {
		super();
		this.bno = bno;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.bview = bview;
		this.mname = mname;
	}


	// 리뷰 출력용
	public BoardDto( int reno, String recontent, 
						int reindex, String redate, int relike ,
						String mname, String mimg , int mexp ,
						String mcolor) {
		super();
		this.reno = reno;
		this.recontent = recontent;
		this.reindex = reindex;
		this.redate = redate;
		this.relike = relike;
		this.mname = mname;
		this.mimg = mimg;
		this.mexp = mexp;
		this.mcolor = mcolor;
	}

	// 글 저장용 
	public BoardDto(String btitle, String bcontent, int mno, int subcno) {
		super();
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.mno = mno;
		this.subcno = subcno;
	}

	// 글 수정용 
	public BoardDto(int bno, String btitle, String bcontent, int subcno) {
		super();
		this.bno = bno;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.subcno = subcno;
	}
	
	
	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getBtitle() {
		return btitle;
	}

	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public String getBcontent() {
		return bcontent;
	}

	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}

	public String getBdate() {
		return bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}

	public int getBview() {
		return bview;
	}

	public void setBview(int bview) {
		this.bview = bview;
	}

	public int getBlike() {
		return blike;
	}

	public void setBlike(int blike) {
		this.blike = blike;
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
	
	public String getMimg() {
		return mimg;
	}

	public void setMimg(String mimg) {
		this.mimg = mimg;
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

	public int getTno() {
		return tno;
	}

	public void setTno(int tno) {
		this.tno = tno;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public int getReno() {
		return reno;
	}

	public void setReno(int reno) {
		this.reno = reno;
	}

	public String getRecontent() {
		return recontent;
	}

	public void setRecontent(String recontent) {
		this.recontent = recontent;
	}

	public int getReindex() {
		return reindex;
	}

	public void setReindex(int reindex) {
		this.reindex = reindex;
	}

	public String getRedate() {
		return redate;
	}

	public void setRedate(String redate) {
		this.redate = redate;
	}

	public int getRelike() {
		return relike;
	}

	public void setRelike(int relike) {
		this.relike = relike;
	}
	
	public BoardDto getbDto() {
		return bDto;
	}

	public void setbDto(BoardDto bDto) {
		this.bDto = bDto;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMexp() {
		return mexp;
	}

	public void setMexp(int mexp) {
		this.mexp = mexp;
	}
	
	public String getMcolor() {
		return mcolor;
	}

	public void setMcolor(String mcolor) {
		this.mcolor = mcolor;
	}

	@Override
	public String toString() {
		return "BoardDto [bno=" + bno + ", btitle=" + btitle + ", bcontent=" + bcontent + ", bdate=" + bdate
				+ ", bview=" + bview + ", blike=" + blike + ", mno=" + mno + ", mname=" + mname + ", mimg=" + mimg
				+ ", mexp=" + mexp + ", mcolor=" + mcolor + ", subcno=" + subcno + ", subcname=" + subcname + ", tno="
				+ tno + ", tname=" + tname + ", reno=" + reno + ", recontent=" + recontent + ", reindex=" + reindex
				+ ", redate=" + redate + ", relike=" + relike + ", bDto=" + bDto + ", count=" + count + "]";
	}

}
