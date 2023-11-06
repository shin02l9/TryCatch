package controller;

public class MapperExample {
	private String type;
	private String title;
	public MapperExample() {
		// TODO Auto-generated constructor stub
	}
	public MapperExample(String type, String title) {
		super();
		this.type = type;
		this.title = title;
	}
	String getType() {
		return type;
	}
	void setType(String type) {
		this.type = type;
	}
	String getTitle() {
		return title;
	}
	void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "MapperExample [type=" + type + ", title=" + title + "]";
	}
	
	
	
}
