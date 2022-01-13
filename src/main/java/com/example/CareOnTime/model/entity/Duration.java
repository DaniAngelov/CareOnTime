package com.example.CareOnTime.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity(name = "durations")
@NoArgsConstructor
@Getter
@Setter
public class Duration {

    @Id
    @Column(name = "pill_id")
    private Integer id;

    @Column(name = "start_date")
    @NotNull
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToOne
    @JoinColumn(name = "pill_id")
    @NotNull
    private Pill pill;
}
