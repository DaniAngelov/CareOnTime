package com.example.CareOnTime.service.impl;

import com.example.CareOnTime.model.dto.UserDto;
import com.example.CareOnTime.model.entity.SendTask;
import com.example.CareOnTime.model.entity.User;
import com.example.CareOnTime.repository.UserRepository;
import com.example.CareOnTime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskScheduler taskScheduler;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,TaskScheduler taskScheduler){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public User registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setLastActive(LocalDateTime.now());
        taskScheduler.schedule(new SendTask(),new CronTrigger(user.getSchedule()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username '" + username + "' does not exist.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return user;
    }
}
