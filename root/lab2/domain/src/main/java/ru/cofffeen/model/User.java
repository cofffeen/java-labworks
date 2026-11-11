package ru.cofffeen.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {
    private Long id;
    private String login;
    private String password;
    private String name;
    private Integer age;
    private String gender;
    private String hairColor;
    private List<User> friends;
    private List<Account> accounts;
}
