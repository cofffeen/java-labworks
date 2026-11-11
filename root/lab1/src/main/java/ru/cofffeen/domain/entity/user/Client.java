package ru.cofffeen.domain.entity.user;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Client {
    private String name;
    private UUID id;
    private String birthDate;
    private String phone;
    private String email;
}
