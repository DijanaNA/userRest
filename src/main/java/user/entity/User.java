package main.java.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="korisnik")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique=true)
	private String username;
	private String email;
	private String password;
	private Date createdOn;
	private Boolean verified;
	private Boolean isAdmin;
	private Boolean signedIn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getSignedIn() {
		return signedIn;
	}

	public void setSignedIn(Boolean signedIn) {
		this.signedIn = signedIn;
	}

	public User(String username, String email, String password, Date createdOn, Boolean verified, Boolean isAdmin,
			Boolean signedIn) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.createdOn = createdOn;
		this.verified = verified;
		this.isAdmin = isAdmin;
		this.signedIn = signedIn;
	}

	public User() {

	}

}
