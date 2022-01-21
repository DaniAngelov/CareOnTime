package com.example.CareOnTime.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String password;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private String name;

    @Column
    private LocalDateTime lastActive;

    @Column(name = "is_subscribed")
    @NotNull
    private boolean isSubscribed;

}
