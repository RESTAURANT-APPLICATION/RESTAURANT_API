package kh.com.kshrd.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Telephone {
	
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private Restaurant restaurant;
	@JsonProperty(value="NUMBER")
	private String telephone;
	@JsonIgnore
	private String status;
	
	public Telephone(){
		this.status = "1";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
/*	@Override
	public String toString() {
		return "Telephone [id=" + id + ", restaurant=" + restaurant + ", telephone=" + telephone + ", status=" + status
				+ "]";
	}*/
	
}
