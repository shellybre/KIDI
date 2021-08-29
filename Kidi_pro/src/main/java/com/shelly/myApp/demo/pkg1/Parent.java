package com.shelly.myApp.demo.pkg1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Parent {
	@Id
	private String id;
	@Field
	private String fullName;
	@Field
	private String phoneNumber;
	@Field
	private String email;
	@Field
	private String password;
	@Field
	private String paymentMethod; // ??
	@Field
	private String paymentDetails;
	@Field
	private Status active;
	@Field
	private List<String> kids;
	// @Field
	// private List <Bills> bill;
	@Field
	private Date activeDate; // first time login

	public Parent() {
		super();
	}

	public Parent(String fullName, String phoneNumber, String email, String password, Date date) {
		super();
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		active = Status.Active;
		this.kids = new ArrayList<>();
		activeDate = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public List<String> getKids() {
		return kids;
	}

	public void setKids(List<String> kidsIds) {
		this.kids = kidsIds;
	}

	public void removeKid(String kidId) {
		kids.remove(kidId);

	}

	public void addKid(String kidId) {
		kids.add(kidId);
	}
	/*
	 * public List<Bills> getBill() { return bill; } public void setBill(List<Bills>
	 * bill) { this.bill = bill; }
	 */

	public Status getActive() {
		return active;
	}

	public void setActive(Status s) {
		active = s;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	@Override
	public String toString() {
		return "Parent [fullName=" + fullName + ", phoneNumnber=" + phoneNumber + ", email=" + email + "]";
	}

}