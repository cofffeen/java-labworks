package ru.cofffeen.dto;

import lombok.Builder;
import lombok.Getter;
import ru.cofffeen.domain.entity.user.EmployeeRole;

import java.util.UUID;

@Getter
@Builder
public class EmployeeDTO {
    private String name;
    private UUID id;
    private EmployeeRole role;
}
