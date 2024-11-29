package com.example.myapp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.model.Course;


@Repository
public interface CourseRepository extends CrudRepository<Course, UUID> {
    List<Course> findByAccountId(UUID accountId);
    List<Course> findByMedicamentIdAndAccountId(UUID medicamentId, UUID accountId);
}
