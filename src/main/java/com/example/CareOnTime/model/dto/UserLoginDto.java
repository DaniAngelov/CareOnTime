package com.example.CareOnTime.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginDto {
    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String password;

}
