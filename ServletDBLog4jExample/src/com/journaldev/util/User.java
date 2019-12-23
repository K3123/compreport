package com.journaldev.util;

import java.io.Serializable;


/**
 * @author k3123
 *
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 6297385302078200511L;

	private String name;
	private String email;
	private int id;
	private String country;

    public User(String nm, String em, String country, int i) {
        // TODO Auto-generated constructor stub
    	this.name		= nm;
    	this.id			= i;
    	this.country 	= country;
    	this.email		= em;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "User [name=" + this.name + ", email=" + this.email + ", id=" + this.id + ", country=" + this.country + "]";
	}
    
    

}
