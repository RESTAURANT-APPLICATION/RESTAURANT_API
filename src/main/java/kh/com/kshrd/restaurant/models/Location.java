package kh.com.kshrd.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

	@JsonProperty("ID")
	private Long id;
	@JsonProperty("NAME")
	private String name;
	@JsonIgnore
	private String typeCode;
	@JsonIgnore
	private String code;
	@JsonIgnore
	private Long parentId;
	
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
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + ", typeCode=" + typeCode + ", code=" + code + ", parentId="
				+ parentId + "]";
	}
	
}
