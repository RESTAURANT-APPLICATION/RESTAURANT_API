package kh.com.kshrd.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RestaurantLocation {

	@JsonIgnore
	private Long id;
	@JsonProperty("LONGITUDE")
	private String longitude;
	@JsonProperty("LATITUDE")
	private String latitude;
	@JsonProperty("COUNTRY")
	private Long country;
	@JsonProperty("PROVINCE")
	private Long province;
	@JsonProperty("DISTRCIT")
	private Long district;
	@JsonProperty("COMMUNE")
	private Long commune;
	@JsonProperty("VILLAGE")
	private Long village;
	@JsonProperty("STREET")
	private String street;
	@JsonProperty("NO")
	private String no;
	@JsonProperty("BRANCH")
	private String branch;
	@JsonIgnore
	private String status;
	@JsonIgnore
	private Restaurant restaurant;
	
	public RestaurantLocation(){
		this.status = "1";
		this.no = "";
		this.street = "";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Long getCountry() {
		return country;
	}
	public void setCountry(Long country) {
		this.country = country;
	}
	public Long getProvince() {
		return province;
	}
	public void setProvince(Long province) {
		this.province = province;
	}
	public Long getDistrict() {
		return district;
	}
	public void setDistrict(Long district) {
		this.district = district;
	}
	public Long getCommune() {
		return commune;
	}
	public void setCommune(Long commune) {
		this.commune = commune;
	}
	public Long getVillage() {
		return village;
	}
	public void setVillage(Long village) {
		this.village = village;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	@Override
	public String toString() {
		return "RestaurantLocation [id=" + id + ", longitude=" + longitude + ", latitude=" + latitude + ", country="
				+ country + ", province=" + province + ", district=" + district + ", commune=" + commune + ", village="
				+ village + ", street=" + street + ", no=" + no + ", branch=" + branch + ", status=" + status
				+ ", restaurant=" + restaurant + ", getId()=" + getId() + ", getLongitude()=" + getLongitude()
				+ ", getLatitude()=" + getLatitude() + ", getStatus()=" + getStatus() + ", getRestaurant()="
				+ getRestaurant() + ", getCountry()=" + getCountry() + ", getProvince()=" + getProvince()
				+ ", getDistrict()=" + getDistrict() + ", getCommune()=" + getCommune() + ", getVillage()="
				+ getVillage() + ", getStreet()=" + getStreet() + ", getNo()=" + getNo() + ", getBranch()="
				+ getBranch() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
