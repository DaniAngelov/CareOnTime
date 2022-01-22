package com.example.CareOnTime.service;

import com.example.CareOnTime.model.dto.PillDto;
import com.example.CareOnTime.model.entity.Pill;

import java.time.LocalDate;
import java.util.List;

public interface PillService {
    Pill addPill(PillDto pillDto);
    List<Pill> getAllPills(Integer userId);
    List<Pill> getAllPillsByDate(Integer userId, LocalDate localDate);
    void deletePillById(Integer id);
}
