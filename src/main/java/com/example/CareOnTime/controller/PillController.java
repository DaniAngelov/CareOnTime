package com.example.CareOnTime.controller;

import com.example.CareOnTime.model.dto.PillDto;
import com.example.CareOnTime.model.entity.Pill;
import com.example.CareOnTime.model.enums.PillType;
import com.example.CareOnTime.service.PillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
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
    public ResponseEntity<PillDto> addPill(@Valid @RequestBody PillDto pillDto){
        Pill pill = pillService.addPill(pillDto);

        return new ResponseEntity<>(modelMapper.map(pill, PillDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePill(@PathVariable Integer id) {
        pillService.deletePillById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<List<PillDto>> getPillsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                              LocalDate localDate, @PathVariable Integer userId) {
        List<Pill> pills;
        if (localDate == null) {
            pills = pillService.getAllPills(userId);
        } else {
            pills = pillService.getAllPillsByDate(userId, localDate);
        }
        List<PillDto> pillDtos = pills.stream().map(pill -> modelMapper.map(pill, PillDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(pillDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/pill-types")
    public ResponseEntity<List<PillType>> getPillTypes() {
        return new ResponseEntity<>(Arrays.asList(PillType.values()), HttpStatus.OK);
    }
}
