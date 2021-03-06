package kh.com.kshrd.restaurant.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Restaurant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1694313553848111798L;

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
	
	@JsonProperty("MENUS")
	private List<Image> menus = new ArrayList<Image>();
	
	@JsonProperty("IMAGES")
	private List<Image> restaurantImages = new ArrayList<Image>();
	
	@JsonIgnore
	private String[] restaurantImagesDeleted;
	
	@JsonIgnore
	private String[] menuImagesDeleted;
	
	//@JsonProperty("CATEGORIES")
	@JsonIgnore
	private List<Category> categories = new ArrayList<Category>();
	
	@JsonProperty("CATEGORY")
	private String category;
	
	@JsonProperty("LOCATION")
	private RestaurantLocation location;
	
	@JsonProperty("TELEPHONE")
	private Telephone telephone;

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

	public List<Image> getMenus() {
		return menus;
	}

	public void setMenus(List<Image> menus) {
		this.menus = menus;
	}
	
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Image> getRestaurantImages() {
		return restaurantImages;
	}
	
	public void setRestaurantImages(List<Image> restaurantImages) {
		this.restaurantImages = restaurantImages;
	}

	public RestaurantLocation getLocation() {
		return location;
	}

	public void setLocation(RestaurantLocation location) {
		this.location = location;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Telephone getTelephone() {
		return telephone;
	}

	public void setTelephone(Telephone telephone) {
		this.telephone = telephone;
	}
	
	public String[] getRestaurantImagesDeleted() {
		return restaurantImagesDeleted;
	}

	public void setRestaurantImagesDeleted(String[] restaurantImagesDeleted) {
		this.restaurantImagesDeleted = restaurantImagesDeleted;
	}

	public String[] getMenuImagesDeleted() {
		return menuImagesDeleted;
	}

	public void setMenuImagesDeleted(String[] menuImagesDeleted) {
		this.menuImagesDeleted = menuImagesDeleted;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", description=" + description + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", status=" + status + ", address=" + address + ", isDelivery=" + isDelivery + ", thumbnail="
				+ thumbnail + ", menus=" + menus + ", restaurantImages=" + restaurantImages
				+ ", restaurantImagesDeleted=" + Arrays.toString(restaurantImagesDeleted) + ", menuImagesDeleted="
				+ Arrays.toString(menuImagesDeleted) + ", categories=" + categories + ", category=" + category
				+ ", location=" + location + ", telephone=" + telephone + ", getId()=" + getId() + ", getName()="
				+ getName() + ", getDescription()=" + getDescription() + ", getCreatedDate()=" + getCreatedDate()
				+ ", getUpdatedDate()=" + getUpdatedDate() + ", getCreatedBy()=" + getCreatedBy() + ", getUpdatedBy()="
				+ getUpdatedBy() + ", getStatus()=" + getStatus() + ", getAddress()=" + getAddress()
				+ ", getIsDelivery()=" + getIsDelivery() + ", getThumbnail()=" + getThumbnail() + ", getMenus()="
				+ getMenus() + ", getCategories()=" + getCategories() + ", getRestaurantImages()="
				+ getRestaurantImages() + ", getLocation()=" + getLocation() + ", getCategory()=" + getCategory()
				+ ", getTelephone()=" + getTelephone() + ", getRestaurantImagesDeleted()="
				+ Arrays.toString(getRestaurantImagesDeleted()) + ", getMenuImagesDeleted()="
				+ Arrays.toString(getMenuImagesDeleted()) + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
