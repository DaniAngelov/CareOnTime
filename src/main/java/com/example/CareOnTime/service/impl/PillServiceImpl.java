package com.example.CareOnTime.service.impl;

import com.example.CareOnTime.model.dto.PillDto;
import com.example.CareOnTime.model.entity.Pill;
import com.example.CareOnTime.repository.PillRepository;
import com.example.CareOnTime.service.PillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PillServiceImpl implements PillService {
    PillRepository pillRepository;

    @Autowired
    public PillServiceImpl(PillRepository pillRepository){
        this.pillRepository = pillRepository;
    }


    @Override
    public Pill addPill(PillDto pillDto) {
        Pill pill = new Pill();
        pill.setName(pillDto.getName());
        pill.setDuration(pillDto.getDuration());
        pill.setFrequencies(pillDto.getFrequencies());
        pill.setPillType(pillDto.getPillType());
        pill.setUserId(pillDto.getUserId());

        return pillRepository.save(pill);
    }

    @Override
    public List<Pill> getPills(String username) {
        return pillRepository.findByUsername(username);
    }
}
