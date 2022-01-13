package com.example.CareOnTime.model.dto;

import com.example.CareOnTime.model.entity.Duration;
import com.example.CareOnTime.model.entity.Frequency;
import com.example.CareOnTime.model.enums.PillType;
import com.sun.istack.NotNull;
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
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PillDto {

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private PillType pillType;

    private List<Frequency> frequencies;

    @Column
    @NotNull
    private String name;

    private Duration duration;

    @Column(name = "user_id")
    @NotNull
    private Integer userId;

    @Column
    @NotNull
    private LocalDateTime lastActive;
}

