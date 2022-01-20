package com.example.CareOnTime.service;

import com.example.CareOnTime.model.dto.UserDto;
import com.example.CareOnTime.model.entity.User;

public interface UserService {
    User registerUser(UserDto userDto);
}
