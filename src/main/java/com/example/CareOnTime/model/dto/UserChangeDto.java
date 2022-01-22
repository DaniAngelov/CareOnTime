package com.example.CareOnTime.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class UserChangeDto {
    @Column
    private Integer id;

    @Column
    @NotNull
    private String password;

    @Column
    private String newUsername;

    @Column
    private String newPassword;
}
