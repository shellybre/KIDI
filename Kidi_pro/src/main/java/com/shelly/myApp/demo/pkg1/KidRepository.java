package com.shelly.myApp.demo.pkg1;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KidRepository {

	@Autowired
	IKidRepository kidRepo;
	@Autowired
	CourseRepository courseRepo;
	
	Random rand = new Random();

	public Kid addNewKid(Kid kid) {
		kid.setStatus(Status.Active);
		kidRepo.save(kid);
		return kid;
	}

	public void deleteAll() {
		kidRepo.deleteAll();
	}
	
	
	/**
	 *
	 * @param id to find the kid with
	 * @return the kid if present, null otherwise.
	 */
	public Kid getKidWithId(String id) {

		Optional<Kid> optional = kidRepo.findById(id);
		if (optional.isPresent()) {
			System.out.println("KID IS PRESENT");
			return optional.get();
		}
		System.out.println("KID ISNT PRESENT");
		return null;
	}

	/**
	 *
	 * @param kidId    to add Course to his active courses
	 * @param courseId to add to kids active courses list
	 * @return kid object with course added to active courses.
	 */
	public Kid addCourseToKid(String kidId, String courseId) {
		Optional<Kid> optional = kidRepo.findById(kidId);
		if (optional.isPresent()) {
			System.out.println("Kid Found, now setting course to his studies.");
			Kid kid = optional.get();
			kid.addCourse(courseId);
			kidRepo.save(kid);
			courseRepo.addKidToCourse(courseId, kidId);
			return kid;
		}
		return null;
	}

	/**
	 *
	 * @param kidId    kid to remove course from
	 * @param courseId of course that finished/cancelled
	 * @return
	 */
	public Kid removeCourseFromKid(String kidId, String courseId) {
		Optional<Kid> optional = kidRepo.findById(kidId);
		if (optional.isPresent()) {
			Kid kid = optional.get();
			kid.deleteCourse(courseId);
			kidRepo.save(kid);
			courseRepo.removeKidFromCourse(courseId, kidId);
			return optional.get();
		}
		return null;
	}

	/**
	 * method that changes the status of a kid from active to inactive.
	 * 
	 * @param kidId of a kid that has finished his studies with kidi.
	 * @return true if changed, false otherwise.
	 */
	public boolean deleteKid(String kidId) {
		Optional<Kid> optional = kidRepo.findById(kidId);
		if (optional.isPresent()) {
			Kid kid = optional.get();
			if (kid.getStatus().equals(Status.Active)) {
				kid.setStatus(Status.InActive);
				kidRepo.save(kid);
				for (String course : optional.get().getActiveCourses())
					removeCourseFromKid(kidId, course);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * @return List of all active parents
	 */
	public List<Kid> getAllActiveKids() {
		List<Kid> lstKid = new ArrayList<>();
		for (Kid k : kidRepo.findAll()) {
			if (k.getStatus().equals(Status.Active))
				lstKid.add(k);
		}
		return lstKid;
	}

	
	public int getAllActiveKidsSize() {
		return getAllActiveKids().size();
	}
	
	
	public int getNewKidsByPeriod(int period) {
		int countKids = 0;
		List<Kid> kids = getAllActiveKids();
		Date current = new Date();
		for(Kid k: kids) {
			long difference_In_Time = current.getTime() - k.getActiveDate().getTime();
			long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24));
			if (difference_In_Days >= 0) {
			if(period == 0) {
				if (difference_In_Days <= 7) 
					countKids++;
				System.out.println(difference_In_Days);
				
			}
			else if(period == 1) {
				if (difference_In_Days <= 35)
					countKids++;
			}
			else {
				if (difference_In_Days <= 365)
					countKids++;
			}
		}
		}
		return countKids;
	}
	
	
	public double newKidsPercentage(int period) {
		double allActiveKids = getAllActiveKidsSize();
		double newActiveKids = getNewKidsByPeriod(period);
		double value = ((newActiveKids/allActiveKids)*100);
		String str =  String.format("%.1f", value);
        return Double.parseDouble(str);

	}
}
