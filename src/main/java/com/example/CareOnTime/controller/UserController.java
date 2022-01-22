package com.example.CareOnTime.controller;

import com.example.CareOnTime.exception.CustomUserException;
import com.example.CareOnTime.model.dto.UserChangeDto;
import com.example.CareOnTime.model.dto.UserDto;
import com.example.CareOnTime.model.dto.UserLoginDto;
import com.example.CareOnTime.model.entity.User;
import com.example.CareOnTime.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) throws CustomUserException {
        User user = userService.registerUser(userDto);
        return new ResponseEntity<>(modelMapper.map(user,UserDto.class), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDto> changeUser(@Valid @RequestBody UserChangeDto userChangeDto,
                                              @PathVariable Integer id) throws CustomUserException {
        User user = userService.changeUser(userChangeDto, id);

        return new ResponseEntity<>(modelMapper.map(user, UserDto.class), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody UserLoginDto userLoginDto) throws CustomUserException {
        User user = userService.loginUser(userLoginDto);

        return new ResponseEntity<>(modelMapper.map(user, UserDto.class), HttpStatus.OK);
    }
}
