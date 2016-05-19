package kh.com.kshrd.restaurant.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import kh.com.kshrd.restaurant.enums.ImageType;

public class Image implements Serializable {

	/**	
	 * 
	 */
	private static final long serialVersionUID = 4602078173925266957L;
	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("TITLE")
	private String title;
	
	@JsonProperty("DESCRIPTION")
	private String description;
	
	@JsonIgnore
	private Restaurant restaurant;
	
	@JsonProperty("URL")
	private String url;
	
	@JsonProperty("TYPE")
	private ImageType type;
	
	@JsonProperty("STATUS")
	private String status;
	
	@JsonProperty("CREATED_DATE")
	private String createdDate;
	
	@JsonProperty("CREATED_BY")
	private User createdBy;
	
	@JsonProperty("IS_THUMBNAIL")
	private String isThumbnail;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ImageType getType() {
		return type;
	}
	public void setType(ImageType type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public String getIsThumbnail() {
		return isThumbnail;
	}
	public void setIsThumbnail(String isThumbnail) {
		this.isThumbnail = isThumbnail;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Image [id=" + id + ", title=" + title + ", description=" + description + ", restaurant=" + restaurant
				+ ", url=" + url + ", type=" + type + ", status=" + status + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", isThumbnail=" + isThumbnail + "]";
	}
	
	public static void main(String[] args) {
		System.out.println(ImageType.MENU.getValue());
	}
	
}
