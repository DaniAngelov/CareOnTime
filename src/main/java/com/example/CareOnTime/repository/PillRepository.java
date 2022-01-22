package com.example.CareOnTime.repository;

import com.example.CareOnTime.model.entity.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PillRepository extends JpaRepository<Pill, Integer> {

    List<Pill> findAllByUserId(Integer userId);
}
