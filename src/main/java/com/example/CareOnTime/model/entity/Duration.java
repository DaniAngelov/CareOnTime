package com.example.CareOnTime.model.entity;

import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @MapsId
    @NotNull
    @JsonManagedReference
    @JsonIgnore
    private Pill pill;
}
