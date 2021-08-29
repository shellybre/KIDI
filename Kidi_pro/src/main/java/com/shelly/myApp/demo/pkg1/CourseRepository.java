package com.shelly.myApp.demo.pkg1;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

@Repository
public class CourseRepository {
	@Autowired
	ICourseRepository CourseRepository;
	@Autowired
	CategoryRepository categoryRepository;

	// @Autowired
	// KidRepository kidRepo;

	long DAY_IN_MS = 1000 * 60 * 60 * 24;
	Random rand = new Random();

	@SuppressWarnings("deprecation")
	public void initializCourse() {
		Date d = new Date(); // start Date
		for (int i = 0; i < 3; i++) {
			int r = rand.nextInt(2) + 120;
			d.setYear(r);
			if (r == 121)
				r = rand.nextInt(7);
			else
				r = rand.nextInt(6) + 6;
			d.setMonth(r);
			r = rand.nextInt(28);
			d.setDate(r);
			r = rand.nextInt(60); // number of meetings
			Course c = new Course("number " + i, d, r);
			int numCat = categoryRepository.getNumberOfCategories();
			r = rand.nextInt(numCat); // category
			addANewCourse(c, categoryRepository.getAllCategories().get(r).getId());

		}
		return;
	}

	public int[] getNumKidsPerCategoris() {
		int[] temp = new int[5];
		int r;
		for (int i = 0; i < 5; i++) {
			r = rand.nextInt(301);
			temp[i] = r;
		}
		return temp;
	}

	public int getNumCategoris() {
		return 5;
	}

	public List<int[]> getGraph(int period) { // insied = num days, out = num collections
		List<int[]> temp = new ArrayList<>();
		int r;
		int[] arr;
		if (period == 0) {
			for (int i = 0; i < 5; i++) {
				temp.add(new int[7]);
				arr = temp.get(i);
				for (int j = 0; j < 7; j++) {
					r = rand.nextInt(301);
					arr[j] = r;
				}
			}
		} else if (period == 1) {
			for (int i = 0; i < 5; i++) {
				temp.add(new int[5]);
				arr = temp.get(i);
				for (int j = 0; j < 5; j++) {
					r = rand.nextInt(301);
					arr[j] = r;
				}
			}
		} else {
			for (int i = 0; i < 5; i++) {
				temp.add(new int[12]);
				arr = temp.get(i);
				for (int j = 0; j < 12; j++) {
					r = rand.nextInt(301);
					arr[j] = r;
				}
			}
		}
		return temp;
	}

	/**
	 * Returns a specific course
	 * 
	 * @param Course ID
	 * @return Course if it was found, null if it was not found
	 */

	public void deleteAll() {
		CourseRepository.deleteAll();
	}

	/*
	 * public void deleteDocCourse (String id) { Optional<Course> course =
	 * CourseRepository.findById(id); if (course.isPresent()) { for (String kid :
	 * course.get().getKidsIDs()) kidRepo.removeCourseFromKid(kid, id);
	 * CourseRepository.deleteById(id); }
	 * 
	 * }
	 */
	public Course getASpecificCourse(String ID) {
		Optional<Course> course = CourseRepository.findById(ID);
		if (course.isPresent())
			return course.get();
		return null;
	}

	public List<Course> getAllAvailableCourses(Date d) {
		List<Course> lstCourse = new ArrayList<>();
		for (Course c : CourseRepository.findAll()) {
			if (c.getFinishDate().getTime() > d.getTime())
				lstCourse.add(c);
		}
		return lstCourse;
	}

	/**
	 * Adds a new course
	 * 
	 * @param Course
	 * @return All Course
	 */
	@SuppressWarnings("deprecation")
	public List<Course> addANewCourse(Course course, String categoryID) {
		Category courseCategory = categoryRepository.getCategoryById(categoryID);
		if (courseCategory != null) {
			course.setCategoryId(categoryID);
			Calendar date = Calendar.getInstance();
			date.set(Calendar.YEAR, course.getStartDate().getYear());
			date.set(Calendar.MONTH, course.getStartDate().getMonth());
			date.set(Calendar.DAY_OF_MONTH, course.getStartDate().getDate());
			date.add(Calendar.DAY_OF_YEAR, course.getNumberOfMeetings() * 7);
			Date d = new Date();
			d.setYear(date.getTime().getYear() + 1900);
			d.setMonth(date.getTime().getMonth());
			d.setDate(date.getTime().getDate());
			;
			course.setFinishDate(d);
			d = new Date();
			if (course.getFinishDate().getTime() > d.getTime()) {
				long difference_In_Time = d.getTime() - course.getStartDate().getTime();
				long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
				course.setNumberOfMeetings((int) Math.ceil((double) difference_In_Days / 7));
			}
			boolean[] temp = new boolean[course.getNumberOfMeetings()];
			int random;
			for (int i = 0; i < course.getNumberOfMeetings(); i++) {
				random = rand.nextInt(5);
				if (random == 0)
					temp[i] = false;
				else
					temp[i] = true;
			}
			course.setExisted(temp);
			int temp_day = course.getStartDate().getDay();
			switch (temp_day) {
			case 0:
				course.setDay(Day.Sunday);
				break;
			case 1:
				course.setDay(Day.Monday);
				break;
			case 2:
				course.setDay(Day.Tuesday);
				break;
			case 3:
				course.setDay(Day.Wednesday);
				break;
			case 4:
				course.setDay(Day.Thursday);
				break;
			case 5:
				course.setDay(Day.Friday);
				break;
			case 6:
				course.setDay(Day.Saturday);
				break;
			}
			course.setStatus(Status.Active);
			CourseRepository.save(course);
		}
		return CourseRepository.findAll();
	}

	/**
	 * Returns all Course
	 * 
	 * @return all the Course that exist
	 */
	public List<Course> getAllCourses() {
		return CourseRepository.findAll();
	}

	/**
	 * Returns a course's category
	 * 
	 * @param Course ID
	 * @return Returns course category if the course was found, null otherwise
	 */
	public Category getCourseCategory(String courseID) {
		Optional<Course> course = CourseRepository.findById(courseID);
		if (course.isPresent()) {
			String categoryID = course.get().getCategoryId();
			for (Category c : categoryRepository.getAllCategories()) {
				if (c.getId().equals(categoryID))
					return c;
			}
		}
		return null;
	}

	/**
	 * Returns a specific course's kids
	 * 
	 * @param Course ID
	 * @return returns course's kids if the course exists
	 */
	public ArrayList<String> getCourseKids(String ID) {
		Optional<Course> course = CourseRepository.findById(ID);
		if (course.isPresent())
			return course.get().getKidsIDs();
		return null;
	}

	/**
	 * Adds a kid to a course
	 * 
	 * @param Course ID, Kid ID
	 * @return returns true if the kid was added successfully, false otherwise.
	 */
	public boolean addKidToCourse(String courseID, String kidID) {
		Optional<Course> course = CourseRepository.findById(courseID);
		if (course.isPresent()) {
			if (!(course.get().getKidsIDs().contains(kidID))) {
				if (course.get().getKidsIDs().add(kidID)) {
					CourseRepository.save(course.get());
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Removes kid from course
	 * 
	 * @param Course ID, Kid ID
	 * @return returns true if the kid was removed successfully , false otherwise.
	 */
	public boolean removeKidFromCourse(String courseID, String kidID) {
		Optional<Course> course = CourseRepository.findById(courseID);
		if (course.isPresent()) {
			if (course.get().getKidsIDs().contains(kidID)) {
				if (course.get().getKidsIDs().remove(kidID)) {
					CourseRepository.save(course.get());
					return true;
				}
			}
		}
		return false;
	}

	public int getNumberActivitiesByPeriod(int period) { // get all the activities supposed to be in the selected period
		int count = 0;
		Date date = new Date();
		for (Course c : CourseRepository.findAll()) {
			long difference_In_Time = date.getTime() - c.getFinishDate().getTime();
			long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24) % 365); // check when the course ends
																							// (if <0 still not
																							// finished)
			if (period == 0) {
				if (difference_In_Days <= 7) { // one week = one meeting per course
					count++;
				}
			} else if (period == 1) { // month = max 5 meetings per course
				if (difference_In_Days <= 35) {
					int temp = c.getNumberOfMeetings();
					if (temp > 5)
						temp = 5;
					count += temp;
				}
			} else {
				if (difference_In_Days <= 365) {
					int temp = c.getNumberOfMeetings();
					if (temp >= 52)
						temp = 52;
					count += temp;
				}
			}

		}
		return count;
	}

	public int getActiveActivities(int period) { // get all the activities that happened in the selected period
		int count = 0;
		Date date = new Date();
		for (Course c : CourseRepository.findAll()) {
			long difference_In_Time = date.getTime() - c.getFinishDate().getTime();
			long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24) % 365); // check when the course ends
																							// (if <0 still not
																							// finished)
			if (period == 0) {
				if (difference_In_Days <= 7) {
					if (c.getExistentMeet(c.getNumberOfMeetings() - 1)) // if the meeting is true
						count++;
				}
			} else if (period == 1) {
				if (difference_In_Days <= 35) {
					int temp = c.getNumberOfMeetings() - 5; // 5 meetings back or to the beginning
					if (temp < 0)
						temp = 0;
					for (int i = c.getNumberOfMeetings() - 1; i >= temp; i--) {
						if (c.getExistentMeet(i))
							count++;
					}
				}
			} else {
				if (difference_In_Days <= 365) {
					int temp = c.getNumberOfMeetings() - 52; // 5 meetings back or to the beginning
					if (temp < 0)
						temp = 0;
					for (int i = c.getNumberOfMeetings() - 1; i >= temp; i--) {
						if (c.getExistentMeet(i))
							count++;
					}
				}
			}
		}
		return count;
	}

	public float getNumberActivities() { // get all the hours supposed to be ever
		float count = 0;
		for (Course c : CourseRepository.findAll()) {
			count += c.getNumberOfMeetings();
		}
		return count;
	}

	public float getNumberActiveActivities() { // get all the hours of meetings that happened
		float count = 0;
		for (Course c : CourseRepository.findAll()) {
			for (int i = 0; i < c.getNumberOfMeetings(); i++) {
				if (c.getExistentMeet(i))
					count++;
			}
		}
		return count;
	}

	public double getPercentActivities(int period) { // percent of meetings that happened from all the planed meetings
														// in specific period
		double total = getNumberActivitiesByPeriod(period);
		double half = getActiveActivities(period);
		double value = ((half / total) * 100);
		String str = String.format("%.1f", value);
		return Double.parseDouble(str);
	}

	public double getPercentMeetings(int period) { // percent of planed meetings in specific period from *all* the
													// planed meetings
		double total = getNumberActivities();
		double half = getNumberActivitiesByPeriod(period);
		double value = ((half / total) * 100);
		String str = String.format("%.1f", value);
		return Double.parseDouble(str);
	}

	public double getPercentMeetingsHappend(int period) { // percent of meetings that happened in specific period from
															// *all* the meetings that happened
		double total = getNumberActiveActivities();
		double half = getActiveActivities(period);
		double value = ((half / total) * 100);
		String str = String.format("%.1f", value);
		return Double.parseDouble(str);
	}

	/**
	 * counts kids per category and returns an array of number of kids per category by period
	 * 
	 * @param period
	 * @return a sorted list of number of kids per category
	 */
	public int[] kidsPerCategories(int period) {
		HashMap<String, Set<String>> kidsInCategories = new HashMap<>();
		Date date = new Date();
		for (Course c : getAllCourses()) {
			long difference_In_Time = date.getTime() - c.getFinishDate().getTime();
			long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24));
			if (period == 0)
				if (difference_In_Days <= 7) {
					String courseCategory = c.getCategoryId();
					Set<String> categoryKids = kidsInCategories.get(courseCategory);
					if (categoryKids == null)
						categoryKids = new HashSet<>();
					for (String kid : c.getKidsIDs())
						categoryKids.add(kid);
					kidsInCategories.put(courseCategory, categoryKids);
				}
			if (period == 1)
				if (difference_In_Days <= 35) {
					String courseCategory = c.getCategoryId();
					Set<String> categoryKids = kidsInCategories.get(courseCategory);
					if (categoryKids == null)
						categoryKids = new HashSet<>();
					for (String kid : c.getKidsIDs())
						categoryKids.add(kid);
					kidsInCategories.put(courseCategory, categoryKids);

				}
			if (period == 2)
				if (difference_In_Days <= 365) {
					String courseCategory = c.getCategoryId();
					Set<String> categoryKids = kidsInCategories.get(courseCategory);
					if (categoryKids == null)
						categoryKids = new HashSet<>();
					for (String kid : c.getKidsIDs())
						categoryKids.add(kid);
					kidsInCategories.put(courseCategory, categoryKids);
				}
		}
		int[] kids = new int[categoryRepository.getNumberOfCategories()];
		TreeMap<String, Set<String>> sorted = new TreeMap<>();
		sorted.putAll(kidsInCategories);
		int i = 0;
		for (Map.Entry<String, Set<String>> mapElement : sorted.entrySet())
			kids[i++] = mapElement.getValue().size();
		return kids;
	}

	/**
	 * counts kids per category and returns an array of number of kids per category by period
	 * 
	 * @param period
	 * @return a sorted list of number of kids per category
	 */
	@SuppressWarnings("deprecation")
	public ArrayList<int[]> totalPerCatgoryGraph(int period) {
		HashMap<String, ArrayList<Set<String>>> kidsInCategories = new HashMap<>();
		Date date = new Date();
		for (Course c: getAllCourses()) {
			long differenceInTime = date.getTime() - c.getFinishDate().getTime();
			long differenceInDays = (differenceInTime / (1000 * 60 * 60 * 24));
				if(period == 0) 
					if (differenceInDays <= 7) {
						String courseCategory = c.getCategoryId();
						ArrayList<Set<String>> categoryKids = kidsInCategories.get(courseCategory);
						if (categoryKids == null) {
							ArrayList<Set<String>> week = new ArrayList<>();
							for (int i = 0; i < 7; i++)
								week.add(new HashSet<String>());
							categoryKids = week;
						}
						for(String kid: c.getKidsIDs())
							categoryKids.get(c.getStartDate().getDay()).add(kid);
						kidsInCategories.put(courseCategory, categoryKids);
					}
				
				if (period == 1)
					if (differenceInDays <= 35) {
						String courseCategory = c.getCategoryId();
						ArrayList<Set<String>> categoryKids = kidsInCategories.get(courseCategory);
						if (categoryKids == null) {
							ArrayList<Set<String>> month = new ArrayList<>();
							for (int i = 0; i < 5; i++)
								month.add(new HashSet<String>());
							categoryKids = month;	
						}
						long differenceInTimeWeekStart = date.getTime() - c.getStartDate().getTime();
						long differenceInDaysMonth = (differenceInTimeWeekStart / (1000 * 60 * 60 * 24));
						if(differenceInDays <= 7) {
							for(String kid: c.getKidsIDs())
								categoryKids.get(0).add(kid);
							kidsInCategories.put(courseCategory, categoryKids);
						}
						if(differenceInDays <= 14 & differenceInDaysMonth >= 7) {
							for(String kid: c.getKidsIDs())
								categoryKids.get(1).add(kid);
							kidsInCategories.put(courseCategory, categoryKids);
						}
						if(differenceInDays <= 21 & differenceInDaysMonth >= 14) {
							for(String kid: c.getKidsIDs())
								categoryKids.get(2).add(kid);
							kidsInCategories.put(courseCategory, categoryKids);
						}
						if(differenceInDays <= 28 & differenceInDaysMonth >= 21) {
							for(String kid: c.getKidsIDs())
								categoryKids.get(3).add(kid);
							kidsInCategories.put(courseCategory, categoryKids);
						}
						if(differenceInDays <= 35 & differenceInDaysMonth >= 28) {
							for(String kid: c.getKidsIDs())
								categoryKids.get(4).add(kid);
							kidsInCategories.put(courseCategory, categoryKids);
						}
					}
			
				if (period == 2)
					if (differenceInDays <= 365) {
						String courseCategory = c.getCategoryId();
						ArrayList<Set<String>> categoryKids = kidsInCategories.get(courseCategory);
						if (categoryKids == null) {
							ArrayList<Set<String>> year = new ArrayList<>();
							for (int i = 0; i < 12; i++)
								year.add(new HashSet<String>());
							categoryKids = year;	
						}
						int start = c.getStartDate().getMonth();
						int finish = c.getFinishDate().getMonth();
						if(start > finish) {
							for(int i = start; i < 12; i++) {
								for(String kid: c.getKidsIDs())
									categoryKids.get(i).add(kid);
								kidsInCategories.put(courseCategory, categoryKids);
							}
							for(int i = 0; i < finish; i++) {
								for(String kid: c.getKidsIDs())
									categoryKids.get(i).add(kid);
								kidsInCategories.put(courseCategory, categoryKids);
							}
						}
						else {
							for(int i = start; i <= finish; i++) {
								for(String kid: c.getKidsIDs())
									categoryKids.get(i).add(kid);
								kidsInCategories.put(courseCategory, categoryKids);
							}
						}
			
						}
					}
		ArrayList<int[]> kids = new ArrayList<int[]>();
		TreeMap<String, ArrayList<Set<String>>> sorted = new TreeMap<>();
		sorted.putAll(kidsInCategories);
		for (Map.Entry<String, ArrayList<Set<String>>> mapElement : sorted.entrySet()) {
			if(period == 0) {
				int [] days = new int[7];
				for(int i = 0; i < days.length; i++)
					days[i] = mapElement.getValue().get(i).size();
				kids.add(days);
			}
			if(period == 1) {
				int [] weeks = new int[5];
				for(int i = 0; i < weeks.length; i++)
					weeks[i] = mapElement.getValue().get(i).size();
				kids.add(weeks);
			}
			if(period == 2) {
				int [] months = new int[12];
				for(int i = 0; i < months.length; i++)
					months[i] = mapElement.getValue().get(i).size();
				kids.add(months);
			}
		}
		return kids;
	}
}
