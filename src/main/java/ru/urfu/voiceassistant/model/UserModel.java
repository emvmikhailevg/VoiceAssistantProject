package ru.urfu.voiceassistant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * UserModel class describes the user model
 * @author Емельянов Микхаил <emelianov.mikhail@urfu.me>
 */
@Entity
@Table(name = "USERS_TABLE")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String login;

    @Setter
    @Getter
    private String password;

    @Override
    public String toString() {
        return "User: " +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel user = (UserModel) o;
        return id == user.id &&
                Objects.equals(email, user.email) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, login, password);
    }
}
