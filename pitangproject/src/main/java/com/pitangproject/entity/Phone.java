package com.pitangproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private int id;
	private long number;
	private int areaCode;
	private String countryCode;

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

}
