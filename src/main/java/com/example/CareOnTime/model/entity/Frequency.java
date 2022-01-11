package com.example.CareOnTime.model.entity;

import com.example.CareOnTime.model.enums.FrequencyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity(name = "frequencies")
@NoArgsConstructor
@Getter
@Setter
public class Frequency {

    @Id
    @Column
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private FrequencyType frequencyType;

    @Column
    private LocalTime localTime;
}
