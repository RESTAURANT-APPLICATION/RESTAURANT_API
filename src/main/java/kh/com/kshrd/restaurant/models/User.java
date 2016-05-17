package kh.com.kshrd.restaurant.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements UserDetails, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7783302294535892142L;

	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("EMAIL")
	private String email;
	
	@JsonIgnore	
	private String password;
	
	@JsonProperty("STATUS")
	private String status;
	
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	@JsonProperty("USERNAME")
	public String getUsername() {
		return this.email;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		switch (this.status) {
		case "0":
			return false;
		case "1":
			return true;
		}
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
