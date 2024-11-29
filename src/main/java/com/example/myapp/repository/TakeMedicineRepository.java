package com.example.myapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.model.TakeMedicine;
import java.util.List;



@Repository
public interface TakeMedicineRepository extends CrudRepository<TakeMedicine,UUID> {
    @Query(value = "SELECT tm.tm_id, tm.tm_datetime, tm.c_id, tm.tm_state " +
               "FROM takemedicine tm " +
               "JOIN course c ON tm.c_id = c.c_id " +
               "WHERE c.a_id = :id", nativeQuery = true)
    List<TakeMedicine> findByAccountId(UUID id);

    @Query(value = "SELECT tm.tm_id, tm.tm_datetime, tm.c_id, tm.tm_state " +
               "FROM takemedicine tm " +
               "JOIN course c ON tm.c_id = c.c_id " +
               "WHERE c.a_id = :accountId AND tm.c_id= :courseId", nativeQuery = true)
    List<TakeMedicine> findByCourseIdAndAccountId(UUID courseId, UUID accountId);
}
