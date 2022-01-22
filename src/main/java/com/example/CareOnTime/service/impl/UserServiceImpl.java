package com.example.CareOnTime.service.impl;

import com.example.CareOnTime.exception.CustomUserException;
import com.example.CareOnTime.model.dto.UserChangeDto;
import com.example.CareOnTime.model.dto.UserDto;
import com.example.CareOnTime.model.dto.UserLoginDto;
import com.example.CareOnTime.model.entity.User;
import com.example.CareOnTime.repository.UserRepository;
import com.example.CareOnTime.service.UserService;
import com.example.CareOnTime.task.EmailPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private TaskScheduler taskScheduler;
    private EmailPublisher emailPublisher;
    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TaskScheduler taskScheduler,
                           EmailPublisher emailPublisher, RestTemplateBuilder restTemplateBuilder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskScheduler = taskScheduler;
        this.emailPublisher = emailPublisher;
        this.restTemplate = restTemplateBuilder.build();
        taskScheduler.schedule(this.emailPublisher, new CronTrigger("*/10 * * * * *"));
    }

    private String[] getPasswordsBasedOnPrefix(String passwordPrefix) {
        String url = "https://api.enzoic.com/passwords/" + passwordPrefix;
        String result = this.restTemplate.getForObject(url, String.class);
        return result.split(":[0-9]+\r\n");
    }

    private void checkPassword(String password) throws CustomUserException {
        String[] similarPasswords = getPasswordsBasedOnPrefix(password.substring(0, 5));
        String upperCasePassword = password.toUpperCase();
        for (int i = 0; i < similarPasswords.length; i++) {
            if (upperCasePassword.contains(similarPasswords[i])) {
                System.out.println("AAAAAAAAAAAAA");
                throw new CustomUserException("Password is easily compromised!");
            }
        }
    }

    @Override
    public User loginUser(UserLoginDto userLoginDto) throws CustomUserException {
        User user = userRepository.findByUsername(userLoginDto.getUsername());
        if (user == null || !passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new CustomUserException("Username or password doesn't match!");
        }
        // change last active every time they login
        user.setLastActive(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    @Override
    public User changeUser(UserChangeDto userChangeDto, Integer id) throws CustomUserException {
        User user = userRepository.getById(id);
        if (!passwordEncoder.matches(userChangeDto.getPassword(), user.getPassword())) {
            throw new CustomUserException("Password doesn't match!");
        }
        if (userChangeDto.getNewPassword() != null) {
            user.setPassword(passwordEncoder.encode(userChangeDto.getPassword()));
        }
        if (userChangeDto.getNewUsername() != null) {
            user.setUsername(userChangeDto.getNewUsername());
        }
        user.setLastActive(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User registerUser(UserDto userDto) throws CustomUserException {
        User userByUsername = userRepository.findByUsername(userDto.getUsername());
        if (userByUsername != null) {
            throw new CustomUserException("Username is already taken!");
        }

        User userByEmail = userRepository.findByEmail(userDto.getEmail());
        if (userByEmail != null) {
            throw new CustomUserException("Email is already taken!");
        }

        if (!userDto.getEmail().matches("^\\S+@\\S+$")) {
            throw new CustomUserException("Email is not valid!");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        //checkPassword(encodedPassword);
        user.setPassword(encodedPassword);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setLastActive(LocalDateTime.now());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username '" + username + "' does not exist.");
        }

        return user;
    }


}
