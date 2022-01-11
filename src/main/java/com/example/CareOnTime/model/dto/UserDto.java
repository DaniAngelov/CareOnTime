package com.example.CareOnTime.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String name;
}
