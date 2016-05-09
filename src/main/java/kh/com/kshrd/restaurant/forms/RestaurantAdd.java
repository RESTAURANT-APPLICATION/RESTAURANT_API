package kh.com.kshrd.restaurant.forms;

public class RestaurantAdd {
	
	private String name;
	private String description;
	private String address;
	private String isDelivery;
	private String status;
	
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
	@Override
	public String toString() {
		return "RestaurantAdd [name=" + name + ", description=" + description + ", address=" + address + ", isDelivery="
				+ isDelivery + ", status=" + status + "]";
	}
	
}

