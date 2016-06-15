package kh.com.kshrd.restaurant.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7783302294535892142L;

	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("SSID")
	private String ssid;
	
	@JsonProperty("STATUS")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
