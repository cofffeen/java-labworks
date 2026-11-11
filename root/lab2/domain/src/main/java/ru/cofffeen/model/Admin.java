package ru.cofffeen.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Admin {
    private Long id;
    private String login;
    private String password;
    private String name;
}
