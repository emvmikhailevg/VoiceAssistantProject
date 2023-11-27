package ru.urfu.voiceassistant.dao;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {

    @NotEmpty(message = "Your login cannot be empty")
    private String login;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email must be valid")
    private String email;

    private String name;

    private String surname;

    private String birthday;

    private String number;

    @NotEmpty(message = "Password should not be empty")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$",
            message = "Password must be 8 to 16 characters long and contain at least one uppercase letter and one digit")
    private String password;
}
