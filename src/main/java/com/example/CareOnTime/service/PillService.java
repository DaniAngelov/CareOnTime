package com.example.CareOnTime.service;

import com.example.CareOnTime.model.dto.PillDto;
import com.example.CareOnTime.model.entity.Pill;

import java.util.List;

public interface PillService {
    Pill addPill(PillDto pillDto);
    List<Pill> getPills(String username);
}
