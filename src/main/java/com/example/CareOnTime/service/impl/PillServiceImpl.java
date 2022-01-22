package com.example.CareOnTime.service.impl;

import com.example.CareOnTime.model.dto.PillDto;
import com.example.CareOnTime.model.entity.Pill;
import com.example.CareOnTime.model.entity.User;
import com.example.CareOnTime.repository.PillRepository;
import com.example.CareOnTime.repository.UserRepository;
import com.example.CareOnTime.service.PillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class PillServiceImpl implements PillService {
    private PillRepository pillRepository;
    private UserRepository userRepository;

    @Autowired
    public PillServiceImpl(PillRepository pillRepository, UserRepository userRepository){
        this.pillRepository = pillRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Pill addPill(PillDto pillDto) {
        Pill pill = new Pill();
        pill.setName(pillDto.getName());
        pill.setDuration(pillDto.getDuration());
        pill.setFrequencies(pillDto.getFrequencies());
        pill.setPillType(pillDto.getPillType());
        pill.setUserId(pillDto.getUserId());

        User user = userRepository.getById(pillDto.getUserId());
        user.setLastActive(LocalDateTime.now());
        userRepository.save(user);

        return pillRepository.save(pill);
    }

    @Override
    public List<Pill> getAllPills(Integer userId) {
        User user = userRepository.getById(userId);
        user.setLastActive(LocalDateTime.now());
        userRepository.save(user);

        return pillRepository.findAllByUserId(userId);
    }

    @Override
    public List<Pill> getAllPillsByDate(Integer userId, LocalDate localDate) {
        List<Pill> allPills = pillRepository.findAllByUserId(userId);
        List<Pill> filteredPills = allPills.stream()
                // filter pills that are active by date
                .filter(pill ->  pill.getDuration().getStartDate().isBefore(localDate) &&
                        (pill.getDuration().getEndDate().isAfter(localDate) || pill.getDuration().getEndDate() == null))
                .collect(Collectors.toList());

        filteredPills.forEach(pill -> {
            // filter pills that are active by frequency
            pill.setFrequencies(pill.getFrequencies().stream()
                    .filter(frequency -> {
                Long daysFromStart = DAYS.between(pill.getDuration().getStartDate(), LocalDate.now());
                return daysFromStart % frequency.getEveryXDays() == 0;
            }).collect(Collectors.toList()));
        });

        return filteredPills;
    }

    @Override
    public void deletePillById(Integer id) {
        pillRepository.deleteById(id);
    }
}
