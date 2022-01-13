package com.example.CareOnTime.repository;

import com.example.CareOnTime.model.entity.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PillRepository extends JpaRepository<Pill, Integer> {

    @Query(nativeQuery = true, value = "SELECT p.* FROM pills AS p JOIN users AS u ON p.user_id = u.id " +
            "JOIN durations AS d ON p.id = d.pill_id WHERE u.username = :username AND d.start_date < CURDATE() " +
            "AND (CURDATE() < d.end_date OR d.end_date IS NULL)")
    List<Pill> findByUsername(@Param("username") String username);
}
