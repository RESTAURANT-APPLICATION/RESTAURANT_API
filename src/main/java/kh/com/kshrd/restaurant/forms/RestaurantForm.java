package kh.com.kshrd.restaurant.forms;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RestaurantForm {
	
	@JsonIgnore
	private Long id;
	
	@JsonProperty(value="NAME")
	@NotNull
	@NotEmpty
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
	@NotNull
	@NotEmpty
	List<String> menuImages;
	
	@JsonProperty(value="RESTAURANT_IMAGES")
	@NotNull
	@NotEmpty
	List<String> restaurantImages;
	
	@JsonProperty(value="RESTAURANT_CATEGORY")
	@NotNull
	@NotEmpty
	private String restaurantCategory;
	
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
	public List<String> getMenuImages() {
		return menuImages;
	}
	public void setMenuImages(List<String> menuImages) {
		this.menuImages = menuImages;
	}
	public List<String> getRestaurantImages() {
		return restaurantImages;
	}
	public void setRestaurantImages(List<String> restaurantImages) {
		this.restaurantImages = restaurantImages;
	}
	
	public String getRestaurantCategory() {
		return restaurantCategory;
	}
	public void setRestaurantCategory(String restaurantCategory) {
		this.restaurantCategory = restaurantCategory;
	}
	@Override
	public String toString() {
		return "RestaurantForm [id=" + id + ", name=" + name + ", description=" + description + ", address=" + address
				+ ", isDelivery=" + isDelivery + ", status=" + status + ", menuImages=" + menuImages
				+ ", restaurantImages=" + restaurantImages + ", restaurantCategory=" + restaurantCategory + "]";
	}
	
}
