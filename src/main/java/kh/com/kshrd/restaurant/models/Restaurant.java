package kh.com.kshrd.restaurant.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Restaurant implements Serializable{

	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("NAME")
	private String name;
	
	@JsonProperty("DESCRIPTION")
	private String description;
	
	@JsonProperty("CREATED_DATE")
	private String createdDate;
	
	@JsonProperty("UPDATED_DATE")
	private String updatedDate;
	
	@JsonProperty("CREATED_BY")
	private User  createdBy;
	
	@JsonProperty("UPDATED_BY")
	private User updatedBy;
	
	@JsonProperty("STATUS")
	private String status;
	
	@JsonProperty("ADDRESS")
	private String address;
	
	@JsonProperty("IS_DELIVERY")
	private String isDelivery;
	
	@JsonProperty("THUMBNAIL")
	private String thumbnail;

	public Restaurant(){
		
	}
	
	public Restaurant(Long id, String name, String description, String createdDate, String updatedDate, User createdBy,
			User updatedBy, String status, String address, String isDelivery, String thumbnail) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.status = status;
		this.address = address;
		this.isDelivery = isDelivery;
		this.thumbnail = thumbnail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsDelivery() {
		return isDelivery;
	}

	public void setIsDelivery(String isDelivery) {
		this.isDelivery = isDelivery;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", description=" + description + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", status=" + status + ", address=" + address + ", isDelivery=" + isDelivery + ", thumbnail="
				+ thumbnail + "]";
	}

}
