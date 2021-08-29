package com.shelly.myApp.demo.pkg1;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

	@Autowired
	ICategoryRepository categoryRepo;

	int j = 0; 
	Random rand = new Random ();
	
	public void initializCategory (){
		int r = rand.nextInt(7) + 1;
		System.out.print(r);
		for (int i = 0; i < r; i++) {
			createCategory (new Category ("name" + i, "image" + i));
		}
	}
	public void initializCategories (){
		createCategory (new Category ("name" + j, "image" + j));
		j++;

	}

	/**
	 * 
	 * @param category new category to add
	 * @return new category added.
	 */
	public Category createCategory(Category category) {
		return categoryRepo.save(category);
	}
	/**
	 * number of kids per category per week
	 *
	 * @return HashMap of kids in categories
	 */
/*	public HashMap<String, Integer> kidsPerCategoriesPerWeek() {
		HashMap<String, Integer> kidsInCategories = new HashMap<>();
		Date date = new Date();
		for(Course c: iCourseRepo.findAll()) {
			long difference_In_Time = date.getTime() - c.getFinishDate().getTime();
			long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24) % 365);
			if(difference_In_Days <= 7) {
				int kidsInCourse = (courseRepo.getCourseKids(c.getID())).size();
				String courseCategory = c.getCategoryId();
				int count = kidsInCategories.get(courseCategory);
				kidsInCategories.put(courseCategory, count + kidsInCourse);
			}
		}
		return kidsInCategories;
	}
*/
	/**
	 * 
	 * @param id of category wants to retrieve
	 * @return Category
	 */
	public Category getCategoryById(String id) {
		Optional<Category> optional = categoryRepo.findById(id);
		if (optional.isPresent()) {
			Category cat = optional.get();
			System.out.println("Returns Category now.");
			return cat;
		}
		System.out.println("Category with" + id + "Not Found.");
		return null;
	}

	/**
	 * 
	 * @return a list of all categories.
	 */
	public ArrayList<Category> getAllCategories() {
		return (ArrayList<Category>) categoryRepo.findAll();
	}
	public int getNumberOfCategories (){
		return  categoryRepo.findAll().size();
	}
	
	public void deleteAll () {
		categoryRepo.deleteAll();
	}
	

}
