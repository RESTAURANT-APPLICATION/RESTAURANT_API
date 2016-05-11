package kh.com.kshrd.restaurant.forms;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RestaurantForm {
	
	@JsonIgnore
	private Long id;
	
	@JsonProperty(value="NAME")
	private String name;
	
	@JsonProperty(value="DESCRIPTION")
	private String description;
	
	@JsonProperty(value="ADDRESS")
	private String address;
	
	@JsonProperty(value="IS_DELIVERY")
	@Size(max=1)
	private String isDelivery;
	
	@JsonProperty(value="STATUS")
	@Size(max=1)
	private String status;
	
	@JsonProperty(value="MENU_IMAGES")
	List<MultipartFile> menuImages;
	
	@JsonProperty(value="RESTAURANT_IMAGES")
	List<MultipartFile> restaurantImages;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<MultipartFile> getMenuImages() {
		return menuImages;
	}
	public void setMenuImages(List<MultipartFile> menuImages) {
		this.menuImages = menuImages;
	}
	public List<MultipartFile> getRestaurantImages() {
		return restaurantImages;
	}
	public void setRestaurantImages(List<MultipartFile> restaurantImages) {
		this.restaurantImages = restaurantImages;
	}
	@Override
	public String toString() {
		return "RestaurantForm [id=" + id + ", name=" + name + ", description=" + description + ", address=" + address
				+ ", isDelivery=" + isDelivery + ", status=" + status + ", menuImages=" + menuImages
				+ ", restaurantImages=" + restaurantImages + "]";
	}
	
}
