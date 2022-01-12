package com.example.CareOnTime.controller;

import com.example.CareOnTime.model.dto.PillDto;
import com.example.CareOnTime.model.dto.UserDto;
import com.example.CareOnTime.model.entity.Pill;
import com.example.CareOnTime.model.entity.User;
import com.example.CareOnTime.service.PillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PillDto> savePill(@RequestBody PillDto pillDto){
        Pill pill = pillService.addPill(pillDto);
        return new ResponseEntity<>(modelMapper.map(pill, PillDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PillDto>> getPills(@RequestParam String username) {
        List<Pill> pills = pillService.getPills(username);
        List<PillDto> pillDtos = pills.stream().map(pill -> modelMapper.map(pill, PillDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(pillDtos, HttpStatus.OK);
    }
}
