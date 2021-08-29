package com.shelly.myApp.demo.pkg1;


import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Course {
	
	@Id
	private String ID;
	
	@Field
	private String name;
	
	@Field 
	private Date startDate; 
	
	@Field 
	private Date finishDate;
	
	private Day day; 
    
	@Field 
	private String categoryId; 
	
	@Field 
	private Status status; 

	@Field
	private ArrayList<String> kidsIDs = new ArrayList<> ();
	
	@Field 
	private boolean [] existed;  
	@Field 

	private int numberOfMeetings; 
	
	
	
	public Course() {
		super();
	}

	public Course(String name,Date d, int numberOfMeetings) {
		super();
		this.name = name;
		this.startDate = d; 
		this.numberOfMeetings = numberOfMeetings;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ArrayList<String> getKidsIDs() {
		return kidsIDs;
	}

	public void setKidsIDs(ArrayList<String> kidsIDs) {
		this.kidsIDs = kidsIDs;
	}

	public boolean[] getExisted() {
		return existed;
	}
	public boolean getExistentMeet (int x) {
		return existed[x];
	}

	public void setExisted(boolean[] existed) {
		this.existed = existed;
	}

	public int getNumberOfMeetings() {
		return numberOfMeetings;
	}

	public void setNumberOfMeetings(int numberOfMeetings) {
		this.numberOfMeetings = numberOfMeetings;
	}
	
}
	
