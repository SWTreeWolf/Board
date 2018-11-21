package dto;

import java.util.ArrayList;
import java.util.List;

public class BoardDTO {
	private String date;
	private String contents;
	private List<String> uploadfile = new ArrayList<String>();
	private int index;
	private String exists;
	private String writer;
	private String title;

	public BoardDTO() {
		// TODO Auto-generated constructor stub
	}

	public BoardDTO(int index, String title, String writer, String date, String contents) {
		this.index = index;
		this.title = title;
		this.writer = writer;
		this.date = date;
		this.contents = contents;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public List<String> getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(List<String> uploadfile) {
		this.uploadfile = uploadfile;
	}

	public String getExists() {
		return exists;
	}

	public void setExists(String exists) {
		this.exists = exists;
	}	
}
