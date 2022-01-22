package com.example.CareOnTime.controller;

import com.example.CareOnTime.exception.CustomRegisterException;
import com.example.CareOnTime.model.dto.UserDto;
import com.example.CareOnTime.model.dto.UserLoginDto;
import com.example.CareOnTime.model.entity.User;
import com.example.CareOnTime.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) throws CustomRegisterException {
        User user = userService.registerUser(userDto);
        return new ResponseEntity<>(modelMapper.map(user,UserDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        User user = userService.loginUser(userLoginDto);
        if (user != null) {
            return new ResponseEntity<>(modelMapper.map(user,UserDto.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
