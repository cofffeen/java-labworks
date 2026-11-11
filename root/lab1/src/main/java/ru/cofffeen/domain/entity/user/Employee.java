package ru.cofffeen.domain.entity.user;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Employee {
    private String name;
    private UUID id;
    private EmployeeRole role;
}
