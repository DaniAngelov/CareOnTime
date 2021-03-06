package com.example.CareOnTime.model.entity;

import com.example.CareOnTime.model.enums.PillType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;

@Entity(name = "pills")
@NoArgsConstructor
@Getter
@Setter
public class Pill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private PillType pillType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pill_id", referencedColumnName = "id")
    private List<Frequency> frequencies;

    @Column
    @NotNull
    private String name;

    @OneToOne(mappedBy = "pill", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonBackReference
    private Duration duration;

    @Column(name = "user_id")
    @NotNull
    private Integer userId;

}
