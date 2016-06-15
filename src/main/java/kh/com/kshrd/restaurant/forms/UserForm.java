package kh.com.kshrd.restaurant.forms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserForm {

	@JsonProperty("SSID")
	private String ssid;

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	
}
