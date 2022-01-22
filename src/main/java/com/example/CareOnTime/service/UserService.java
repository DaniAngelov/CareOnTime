package com.example.CareOnTime.service;

import com.example.CareOnTime.exception.CustomUserException;
import com.example.CareOnTime.model.dto.UserChangeDto;
import com.example.CareOnTime.model.dto.UserDto;
import com.example.CareOnTime.model.dto.UserLoginDto;
import com.example.CareOnTime.model.entity.User;

public interface UserService {
    User registerUser(UserDto userDto) throws CustomUserException;
    User loginUser(UserLoginDto userLoginDto) throws CustomUserException;
    User changeUser(UserChangeDto userChangeDto, Integer id) throws CustomUserException;
}
