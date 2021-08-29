package com.shelly.myApp.demo.pkg1;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICourseRepository extends MongoRepository<Course, String>{

}
