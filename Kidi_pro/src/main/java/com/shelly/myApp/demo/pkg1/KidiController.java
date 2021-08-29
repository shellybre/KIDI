package com.shelly.myApp.demo.pkg1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KidiController {
	
	@Autowired
	ParentRepository parentRepo;	
	@Autowired
	KidRepository kidRepo;
	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	CourseRepository courseRepo; 
	
	@DeleteMapping ("/deleteAllDocuments")
	public void deleteAllDocuments () {
		parentRepo.delete();
		kidRepo.deleteAll();
		categoryRepo.deleteAll();
		courseRepo.deleteAll();
	}
	
	@PostMapping ("/initializCategories")
	public void initializCategories (){
		 categoryRepo.initializCategories();
	}
	
	@PostMapping ("/initialize")
	public void initialize () {
		courseRepo.initializCourse();
		parentRepo.initializParent();
		parentRepo.initializKid();
		
	}
	
	//Category controller  : 
	
	@PostMapping ("/initializCategory")
	public void initializCategory (){
		categoryRepo.initializCategory();
	}

	@GetMapping ("/getAllCategories")
	public ArrayList<Category> getAllCategories() {
		return categoryRepo.getAllCategories();
	}
	
	@GetMapping ("/getNumberOfCategories")
	public int getNumberOfCategories (){
		return categoryRepo.getNumberOfCategories();
	}
	
	@DeleteMapping ("/deleteAllCategories")
	public void deleteAll () {
		categoryRepo.deleteAll();
	}
	
	//Course controller : 
	
	@PostMapping ("/initializCourse")
	public void initializCourse () {
		courseRepo.initializCourse();
	}

	@GetMapping ("/getAllCourses")
	public List<Course> getAllCourses() {
		return courseRepo.getAllCourses();
	}

	@GetMapping ("/getCourseKids/{id}")
	public ArrayList<String> getCourseKids(@PathVariable String ID) {
		return courseRepo.getCourseKids(ID);
	}
	
	@GetMapping ("/getNumberActivitiesByPeriod/{period}")
	public int getNumberActivitiesByPeriod (@PathVariable int period) {										
		return courseRepo.getNumberActivitiesByPeriod(period);
	}
	
	@GetMapping ("/getActiveActivities/{period}")
	public int getActiveActivities (@PathVariable int period) {						
		return courseRepo.getActiveActivities(period); 
	}
	
	@GetMapping ("/getNumberActivities")
	public float getNumberActivities () {						
		return courseRepo.getNumberActivities();
	}

	@GetMapping ("/getNumberActiveActivities")
	public float getNumberActiveActivities () {				
		return courseRepo.getNumberActiveActivities();
	}

	@GetMapping ("/getPercentActivities/{period}")
	public double getPercentActivities (@PathVariable int period) {				
		return courseRepo.getPercentActivities(period);
	}

	@GetMapping ("/getPercentMeetings/{period}")
	public double getPercentMeetings (@PathVariable int period) {					
		return courseRepo.getPercentMeetings(period);
	}
	
	@GetMapping ("/getPercentMeetingsHappend/{period}")
	public double getPercentMeetingsHappend (@PathVariable int period) {				
		return courseRepo.getPercentMeetingsHappend(period);
	}

	@DeleteMapping ("/deleteAllCourses")
	public void deleteAllCourses () {
		courseRepo.deleteAll();
	}

	//Parent controller 
	
	@PostMapping ("/initializParent")
	public void initializParent () {
		parentRepo.initializParent();
	}
	@DeleteMapping ("/deleteParents")
	public void delete () {
		parentRepo.delete();
	
	}
	
	@DeleteMapping ("/deleteParent/{id}")
	public List<Parent> deleteParent(@PathVariable String id) {
		return parentRepo.deleteParent(id);
	}

	@GetMapping ("/getAllActiveParents")
	public List<Parent> getAllActiveParents() {
		return parentRepo.getAllActiveParents();
	}
	
	@GetMapping ("/getAllActiveParentSize")
	public int getAllActiveParentSize() {
		return parentRepo.getAllActiveParentSize();
	}
	
	@GetMapping ("/getNewParentsByPeriod/{period}")								//for the exam !!
	public int getNewParentsByPeriod(@PathVariable int period) {
		return parentRepo.getNewParentsByPeriod(period);
	}
	
	@GetMapping ("/newParentsPercentage/{period}")							  //for the exam !!
	public double newParentsPercentage(@PathVariable int period) {
		return parentRepo.newParentsPercentage(period);
	}
	
	//Kid controller : 
	
	
	@PostMapping ("/initializKid")
	public void initializKid () {
		parentRepo.initializKid();
	}
	@PutMapping ("/removeKidFromCourse/{parentId}/{kidId}/{courseId}")
	public Kid removeKidFromCourse(@PathVariable String parentId,@PathVariable String kidId,@PathVariable String courseId) {
		return parentRepo.removeKidFromCourse(parentId, kidId, courseId);
	}

	@GetMapping ("/deleteKid/{parentId}/{kidId}")
	public Parent deleteKid(@PathVariable String parentId,@PathVariable String kidId) {
		return parentRepo.deleteKid(parentId, kidId);
	}

	@GetMapping ("/getAllActiveKids")
	public List<Kid> getAllActiveKids() {
		return kidRepo.getAllActiveKids();
	}

	@GetMapping ("/getAllActiveKidsSize")
	public int getAllActiveKidsSize() {
		return kidRepo.getAllActiveKidsSize();
	}
	
	@GetMapping ("/getNewKidsByPeriod/{period}")							//for the exam !!
	public int getNewKidsByPeriod(@PathVariable int period) {
		return kidRepo.getNewKidsByPeriod(period);
	}
	
	@GetMapping ("/newKidsPercentage/{period}")							//for the exam !!
	public double newKidsPercentage(@PathVariable int period) {
		return kidRepo.newKidsPercentage(period);
	}
	
	@DeleteMapping ("/deleteAllKids")
	public void deleteAllKids() {
		kidRepo.deleteAll();
	}
	@GetMapping ("getNumKidsPerCategoris")	
	public int [] getNumKidsPerCategoris () {
		return courseRepo.getNumKidsPerCategoris();
	}
	@GetMapping ("/getNumCategoris")	
	public int getNumCategoris () {
		return 5;
	}
	@GetMapping ("/getGraph/{period}")	
	public List <int []> getGraph (@PathVariable int period){ 
		return courseRepo.getGraph(period);
	}
	
	@GetMapping ("/kidsPerCategories/{period}")
	public int[] kidsPerCategories(@PathVariable int period) {
		return courseRepo.kidsPerCategories(period);
	}
	
	@GetMapping ("/totalPerCatgoryGraph/{period}")
	public ArrayList<int[]> totalPerCatgoryGraph(@PathVariable int period) {
		return courseRepo.totalPerCatgoryGraph(period);
	}

	@PostMapping ("/randomParentsStub")
	public List <Parent> randomParentsStub (){
		return parentRepo.randomParentsStub();
		
	}
	
	@GetMapping ("/getAllActiveParentsStub")
	public List<Parent> getAllActiveParentsStub() {
		return parentRepo.getAllActiveParentsStub();
	}

	@GetMapping ("/getNewParentsByPeriodStub/{period}")
	public int getNewParentsByPeriodStub(@PathVariable int period) {
		return parentRepo.getNewParentsByPeriodStub(period);
	}
	
	@GetMapping ("/newParentsPercentageStub/{period}")
	public double newParentsPercentageStub(@PathVariable int period) {
		return parentRepo.newParentsPercentageStub(period);
	}
	
}



