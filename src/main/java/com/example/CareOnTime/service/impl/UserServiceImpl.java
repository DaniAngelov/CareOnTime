package com.example.CareOnTime.service.impl;

import com.example.CareOnTime.model.dto.UserDto;
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

    private void checkPassword(String password) {
        String[] similarPasswords = getPasswordsBasedOnPrefix(password.substring(0, 5));
        String upperCasePassword = password.toUpperCase();
        for (int i = 0; i < similarPasswords.length; i++) {
            if (upperCasePassword.contains(similarPasswords[i])) {
                System.out.println("AAAAAAAAAAAAA");
                // throw custom exception for password - make it registration exception with text what's wrong
                // add throws to the method name
            }
        }
    }

    @Override
    public User registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        // check username already in use and if so throw exception
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        //checkPassword(encodedPassword);
        user.setPassword(encodedPassword);
        user.setName(userDto.getName());
        // check email already in use and if so throw exception
        user.setEmail(userDto.getEmail());
        user.setLastActive(LocalDateTime.now());
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
