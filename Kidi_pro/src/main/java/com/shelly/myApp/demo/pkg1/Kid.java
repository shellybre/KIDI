package com.shelly.myApp.demo.pkg1;


import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Kid {
	@Id
	private String id;
	@Field
	private String fullName;
	@Field
	private Date dateOfBirth;
	@Field
	private Gender gender;
	@Field
	private ArrayList<String> activeCourses = new ArrayList<>();
	@Field
	private ArrayList<String> completedCourses = new ArrayList <>();
	@Field
	private String parentId;
	@Field
	private Status status;
	@Field
	private Date activeDate;
	@Field
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Kid() {

	}

	public Kid(String fullName, Date dateOfBirth, Gender gender, Date date) {
		super();
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		activeDate = date;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public ArrayList<String> addCourse(String courseID) {
		if (activeCourses.contains(courseID)) {
			System.out.println("Couldn't add, the course already enrolled");
			return null;
		}
		activeCourses.add(courseID);
		return activeCourses;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<String> getActiveCourses() {
		return activeCourses;
	}

	public void setActiveCourses(ArrayList<String> activeCourses) {
		this.activeCourses = activeCourses;
	}

	public ArrayList<String> getCompletedCourses() {
		return completedCourses;
	}

	public void setCompletedCourses(ArrayList<String> completedCourses) {
		this.completedCourses = completedCourses;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public boolean deleteCourse(String courseId) {
		if (activeCourses.remove(courseId)) {
			completedCourses.add(courseId);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Kid [fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + ", parentId=" + parentId + "]";
	}
}