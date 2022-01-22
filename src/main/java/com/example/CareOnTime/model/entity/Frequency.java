package com.example.CareOnTime.model.entity;

import com.example.CareOnTime.model.enums.FrequencyType;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Column(name = "every_x_days")
    @NotNull
    private Integer everyXDays;

    @Column
    @NotNull
    private LocalTime time;
}
