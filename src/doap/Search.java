package doap;

import java.util.Date;

public class Search {

	private Integer id = null;
	private String url;
	private String text;
	private String img;
	private Date date;
	private Integer parent_id = null;

	public Search() {

	}

	public Search(Integer id) {
		this.id = id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		
		return this.id != null ? id : null ;
	}	

	public static Search getInstance() {
		return new Search();
	}





	public Date getDate() {
		return date;
	}

	public void setDate(Date d) {
		this.date = d;
	}



	public String toString() {
		String s = "Search -> id :" + id
				+ " ,Search ->  url : " + url
				+ " ,Search ->  text : " + text
				+ " ,Search ->  img : " + img
				+ " ,Search ->  date : " + date
				+ " ,Search ->  parent : " + parent_id;

		return s;
	}

	/**
	 * @return the parent_id
	 */
	public Integer getParentId() {
		
		return this.parent_id != null ? this.parent_id : null ;
	}

	/**
	 * @param parent_id the parent_id to set
	 */
	public void setParentId(int parent_id) {
		this.parent_id = parent_id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		return this.img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
