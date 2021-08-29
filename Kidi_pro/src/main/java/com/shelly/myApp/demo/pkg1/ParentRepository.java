package com.shelly.myApp.demo.pkg1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ParentRepository {
	@Autowired
	IParentRepository parentRepo;
	@Autowired
	KidRepository kidRepo;
	@Autowired
	CourseRepository courseRepo;
	Random rand = new Random();

	/**
	 * Create new Parent
	 * 
	 * @param Parent
	 * @return new Parent or null if the email already exists
	 */
	public void initializParent () {
		Date d = new Date();
		for (int i = 0; i < 5; i++) {
			int r = rand.nextInt(2) + 120; 
			d.setYear(r);
			if (r == 121)
				r = rand.nextInt(7); 				
			else 
				r = rand.nextInt(6) + 6;
			d.setMonth(r);
				r = rand.nextInt(28);
			d.setDate(r);
			Parent c = new Parent ("name" + i, "9049406596", "klfgk", "edfg", d);
			addNewParent (c);
		}
		return;
	}
	@SuppressWarnings("deprecation")
	public void initializKid () {
		Date d = new Date();
		for (int i = 0; i < 15; i++) {
			int r = rand.nextInt(2) + 120; 
			d.setYear(r);
			if (r == 121)
				r = rand.nextInt(7); 				
			else 
				r = rand.nextInt(6) + 6;
			d.setMonth(r);
				r = rand.nextInt(28);
			d.setDate(r);
			List <Parent> lstParents =  GetAllAvailableParent(d);
			int numParents = lstParents.size();
			if (numParents != 0) {
				r = rand.nextInt(numParents);
				Kid k = addNewKid (lstParents.get(r).getId(), new Kid ("name" + i, d, Gender.Boy, d));
				List <Course> lstCourse = courseRepo.getAllAvailableCourses(d);
				int numCourses = lstCourse.size();
				if (numCourses != 0) {
					if (numCourses > 22)
						numCourses = 22;
					r = rand.nextInt(numCourses);		
					for (int j = 0; j < r; j++) {
						int num = rand.nextInt(numCourses);
						addKidToCourse (k.getParentId(), k.getId(), lstCourse.get(num).getID());
					}
				}
			}
		}
		return;
	}
	
	public List <Parent> GetAllAvailableParent (Date d){
		List <Parent> lstParents = new ArrayList <> ();
		for (Parent p : parentRepo.findAll()) {
			if (p.getActiveDate().getDate() < d.getDate())
				lstParents.add(p);
			}
		return lstParents; 
	}
	
	public void delete () {
		parentRepo.deleteAll();
	
	}
	public Parent addNewParent(Parent parent) {
		parent.setActive(Status.Active);
		parentRepo.save(parent);
		return parent;
	}

	/**
	 * Delete Parent - changes the status to not active
	 * 
	 * @param Parent id
	 * @return List of all parents
	 */
	public List<Parent> deleteParent(String id) {
		Optional<Parent> p = parentRepo.findById(id);
		if (p.isPresent()) {
			p.get().setActive(Status.InActive);
			parentRepo.save(p.get());
			for (String idKid : p.get().getKids()) {
				kidRepo.deleteKid(idKid);
			}
		}
		return getAllActiveParents();
	}

	/**
	 * @return List of all active parents
	 */
	public List<Parent> getAllActiveParents() {
		List<Parent> lstParent = new ArrayList<>();
		for (Parent p : parentRepo.findAll()) {
			if (p.getActive().equals(Status.Active))
				lstParent.add(p);
		}
		return lstParent;
	}

	/**
	 * Add new kid
	 * 
	 * @param parent id, kid
	 * @return the parent if found with the new kid or null
	 */
	public Kid addNewKid(String id, Kid kid) {
		Optional<Parent> parent = parentRepo.findById(id);
		if (parent.isPresent()) {
			kid.setParentId(parent.get().getId());
			Kid k = kidRepo.addNewKid(kid);
			parent.get().addKid(k.getId());
			parentRepo.save(parent.get());
			return kid; 
		} else
			new ResponseEntity<>("Parent not found", HttpStatus.NOT_ACCEPTABLE);
		return null;
	}

	/**
	 * Register kid to course
	 * 
	 * @param id of parent, id of kid, id of course
	 * @return the kid if found or null
	 */
	public Kid addKidToCourse(String parentId, String kidId, String courseId) {
		Optional<Parent> parent = parentRepo.findById(parentId);
		if (parent.isPresent()) {
			Kid kid = kidRepo.addCourseToKid(kidId, courseId);
			return kid;
		}
		return null;
	}

	/**
	 * remove kid from course
	 * 
	 * @param id of parent, id of kid, id of course
	 * @return the kid if found or null
	 */
	public Kid removeKidFromCourse(String parentId, String kidId, String courseId) {
		Optional<Parent> parent = parentRepo.findById(parentId);
		if (parent.isPresent()) {
			Kid kid = kidRepo.removeCourseFromKid(kidId, courseId);
			return kid;
		}
		return null;
	}

	/**
	 * Delete kid – changes the status to not active
	 * 
	 * @param id of parent, id of kid
	 * @return the parent of the kid if found or null
	 */
	public Parent deleteKid(String parentId, String kidId) {
		Optional<Parent> parent = parentRepo.findById(parentId);
		if (parent.isPresent()) {
			kidRepo.deleteKid(kidId);
		}
		return parent.get();
	}

	/**
	 * Find user
	 * 
	 * @param email
	 * @return parent if found or null
	 */

	/**
	 *
	 * @param id to find the kid with
	 * @return the kid if present, null otherwise.
	 */
	public Parent getParentWithId(String id) {

		Optional<Parent> optional = parentRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	
	public int getAllActiveParentSize() {
		return getAllActiveParents().size();
	}
	
	
	public int getNewParentsByPeriod(int period) {
		int countParents = 0;
		List<Parent> parents = getAllActiveParents();
		Date current = new Date();
		for(Parent p: parents) {
			long difference_In_Time = current.getTime() - p.getActiveDate().getTime();
			long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24));
			if (difference_In_Days >= 0) {
			if(period == 0) {
				if (difference_In_Days <= 7) 
					countParents++;
				System.out.println(difference_In_Days);
				
			}
			else if(period == 1) {
				if (difference_In_Days <= 35)
					countParents++;
			}
			else {
				if (difference_In_Days <= 365)
					countParents++;
			}
		}
		}
		return countParents;
	}
	
	
	public double newParentsPercentage(int period) {
		double allActiveKids = getAllActiveParentSize();
		double newActiveKids = getNewParentsByPeriod(period);
		double value = ((newActiveKids/allActiveKids)*100);
		String str =  String.format("%.1f", value);
        return Double.parseDouble(str);
	}
	
	//stubs : 
	List <Parent> temp = new ArrayList<> ();
	
	public List <Parent> randomParentsStub (){
		Date d = new Date();
		for (int i = 0; i < 50; i++) {
			int r = rand.nextInt(2) + 120; 
			d.setYear(r);
			if (r == 121)
				r = rand.nextInt(7); 				
			else 
				r = rand.nextInt(6) + 6;
			d.setMonth(r);
				r = rand.nextInt(28);
			d.setDate(r);
			Parent c = new Parent ("name" + i, "9049406596", "klfgk", "edfg", d);
			c.setActive(Status.Active);
			temp.add(c);
			r = rand.nextInt(5);
			if (r==0)
				c.setActive(Status.InActive);
		}
		return temp;
		
	}
	public List<Parent> getAllActiveParentsStub() {
		List<Parent> lstParent = new ArrayList<>();
		for (Parent p : temp) {
			if (p.getActive().equals(Status.Active))
				lstParent.add(p);
		}
		return lstParent;
	}

	
	public int getNewParentsByPeriodStub(int period) {
		int countParents = 0;
		List<Parent> parents = getAllActiveParentsStub();
		Date current = new Date();
		for(Parent p: parents) {
			long difference_In_Time = current.getTime() - p.getActiveDate().getTime();
			long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24));
			if (difference_In_Days >= 0) {
			if(period == 0) {
				if (difference_In_Days <= 7) 
					countParents++;
				System.out.println(difference_In_Days);
				
			}
			else if(period == 1) {
				if (difference_In_Days <= 35)
					countParents++;
			}
			else {
				if (difference_In_Days <= 365)
					countParents++;
			}
		}
		}
		return countParents;
	}
	
	
	public double newParentsPercentageStub(int period) {
		double allActiveKids = getAllActiveParentsStub().size();
		double newActiveKids = getNewParentsByPeriodStub(period);
		double value = ((newActiveKids/allActiveKids)*100);
		String str =  String.format("%.1f", value);
        return Double.parseDouble(str);
	}
	
}


