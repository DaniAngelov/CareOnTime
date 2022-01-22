package com.example.CareOnTime.task;

import com.example.CareOnTime.model.entity.User;
import com.example.CareOnTime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmailPublisher implements Runnable {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply.careontime@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        //emailSender.send(message);
    }

    @Override
    public void run() {
        // check all users and get the ones who are gonna receive an email

        List<User> users = userRepository.findAll();
        List<User> usersToReceiveEmail = users.stream()
                .filter(user -> user.getLastActive().plusSeconds(30).isBefore(LocalDateTime.now()) &&
                        user.isSubscribed())
                .collect(Collectors.toList());
        // send email to each of the returned users
        usersToReceiveEmail.forEach(user -> sendSimpleMessage(user.getEmail(), "Please come back",
                "Hi, "  + user.getUsername() + "\nYou haven't been active on our website for a while now. " +
                        "We nudge you to come back :)"));
        System.out.println("Send");
    }

}
