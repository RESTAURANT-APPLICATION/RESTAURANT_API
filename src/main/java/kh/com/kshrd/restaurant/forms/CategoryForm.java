package kh.com.kshrd.restaurant.forms;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryForm {

	@JsonIgnore
	private String id;
	
	@JsonProperty("NAME")
	private String name;
	
	@JsonProperty("DESCRIPTION")
	private String description;
	
	@JsonProperty("STATUS")
	@Size(max=1)
	private String status;
	
	@JsonProperty("PARENT_ID")
	private Long parentId;
	
	@JsonProperty("INDEX")
	private int index;
	
	@JsonProperty("LEVEL")
	private String level;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "CategoryUpdate [id=" + id + ", name=" + name + ", description=" + description + ", status=" + status
				+ ", parentId=" + parentId + ", index=" + index + ", level=" + level + "]";
	}
	
}
