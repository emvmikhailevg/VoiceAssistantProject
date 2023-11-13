package ru.urfu.voiceassistant.dao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {

    private Long id;

    @NotEmpty(message = "Your login cannot be empty")
    private String login;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
