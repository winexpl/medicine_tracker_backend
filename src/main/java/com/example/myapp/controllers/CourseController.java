package com.example.myapp.controllers;

import java.util.Iterator;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.myapp.model.Course;
import com.example.myapp.repository.AccountRepository;
import com.example.myapp.repository.CourseRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@RestController
@RequestMapping("/courses")
@AllArgsConstructor
@NoArgsConstructor
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public
    Iterable<Course> getCourses()  {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = accountRepository.findByLogin(userLogin).get().getId();
        Iterable<Course> courses = courseRepository.findByAccountId(userId);
        Iterator<Course> course = courses.iterator();
        System.out.println(course.next().toString());
        return courses;
    }

    @PostMapping
    public Course postCourse(@RequestBody Course course) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = accountRepository.findByLogin(userLogin).get().getId();
        course.setAccountId(userId);
        if(courseRepository.findByMedicamentIdAndAccountId(course.getMedicamentId(), course.getAccountId()).isEmpty()) {
            courseRepository.save(course);
        } else { 
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Нарушение ограничения уникальности");
        }
        return course;
    }
    
    @PutMapping
    public ResponseEntity<Course> putCourse(@RequestBody Course course) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = accountRepository.findByLogin(userLogin).get().getId();
        course.setAccountId(userId);
        return (course.getId() != null && courseRepository.existsById(course.getId()) ?
                        new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK) :
                        new ResponseEntity<>(courseRepository.save(course), HttpStatus.CREATED));
    }

}
