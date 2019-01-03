package com.pitangproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entidade responsável por armazenar os telefones dos usuários
 * 
 * @author Thiago Gitirana
 *
 */
@Entity
public class Phone {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "phone_id")
	private int id;
	private long number;
	private int areaCode;
	private String countryCode;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Phone() {
		super();
	}

	public Phone(long number, int areaCode, String countryCode) {
		super();
		this.number = number;
		this.areaCode = areaCode;
		this.countryCode = countryCode;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
