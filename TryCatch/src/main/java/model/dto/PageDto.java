package model.dto;

import java.util.ArrayList;

public class PageDto {

	private int page;
	private int listLimit;
	private int startrow;
	private int totalsize;
	private int totalpage;
	private int startBtn;
	private int endBtn;
	
	ArrayList<BoardDto> boardList;
	// 포인트 내역 불러올 때 사용
	ArrayList<PointDto> pointList;
	public PageDto() {
		// TODO Auto-generated constructor stub
	}

	public PageDto(int page, int listLimit, int startrow, int totalsize, int totalpage, int startBtn, int endBtn,
			ArrayList<BoardDto> boardList) {
		super();
		this.page = page;
		this.listLimit = listLimit;
		this.startrow = startrow;
		this.totalsize = totalsize;
		this.totalpage = totalpage;
		this.startBtn = startBtn;
		this.endBtn = endBtn;
		this.boardList = boardList;
	}
	// 포인트 내역 가져올 때 사용
	public PageDto(int page, int totalpage, int startBtn, int startrow,
			ArrayList<PointDto> pointList) {
		super();
		this.page = page;
		this.totalpage = totalpage;
		this.startBtn = startBtn;
		this.pointList = pointList;
		this.startrow = startrow;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getListLimit() {
		return listLimit;
	}

	public void setListLimit(int listLimit) {
		this.listLimit = listLimit;
	}

	public int getStartrow() {
		return startrow;
	}

	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	public int getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(int totalsize) {
		this.totalsize = totalsize;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public int getStartBtn() {
		return startBtn;
	}

	public void setStartBtn(int startBtn) {
		this.startBtn = startBtn;
	}

	public int getEndBtn() {
		return endBtn;
	}

	public void setEndBtn(int endBtn) {
		this.endBtn = endBtn;
	}

	public ArrayList<BoardDto> getBoardList() {
		return boardList;
	}

	public void setBoardList(ArrayList<BoardDto> boardList) {
		this.boardList = boardList;
	}

	public ArrayList<PointDto> getPointList() {
		return pointList;
	}

	public void setPointList(ArrayList<PointDto> pointList) {
		this.pointList = pointList;
	}

	@Override
	public String toString() {
		return "PageDto [page=" + page + ", listLimit=" + listLimit + ", startrow=" + startrow + ", totalsize="
				+ totalsize + ", totalpage=" + totalpage + ", startBtn=" + startBtn + ", endBtn=" + endBtn
				+ ", boardList=" + boardList + "]";
	}
	
	
	
}
