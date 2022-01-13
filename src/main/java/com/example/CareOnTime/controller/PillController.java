package com.example.CareOnTime.controller;

import com.example.CareOnTime.model.dto.PillDto;
import com.example.CareOnTime.model.entity.Pill;
import com.example.CareOnTime.model.enums.FrequencyType;
import com.example.CareOnTime.model.enums.PillType;
import com.example.CareOnTime.service.PillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pills")
public class PillController {
    private final PillService pillService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PillController(PillService pillService){
        this.pillService = pillService;
    }

    @PostMapping
    public ResponseEntity<PillDto> addPill(@RequestBody PillDto pillDto){
        Pill pill = pillService.addPill(pillDto);
        return new ResponseEntity<>(modelMapper.map(pill, PillDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PillDto>> getPills(@RequestParam String username) {
        List<Pill> pills = pillService.getPills(username);
        List<PillDto> pillDtos = pills.stream().map(pill -> modelMapper.map(pill, PillDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(pillDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/pill-types")
    public ResponseEntity<List<PillType>> getPillTypes() {
        return new ResponseEntity<>(Arrays.asList(PillType.values()), HttpStatus.OK);
    }

    @GetMapping(path = "/frequency-types")
    public ResponseEntity<List<FrequencyType>> getFrequencies() {
        return new ResponseEntity<>(Arrays.asList(FrequencyType.values()), HttpStatus.OK);
    }
}
