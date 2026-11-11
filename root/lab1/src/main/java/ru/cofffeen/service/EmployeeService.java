package ru.cofffeen.service;

import ru.cofffeen.domain.entity.user.Employee;
import ru.cofffeen.domain.entity.user.EmployeeRole;
import ru.cofffeen.dto.EmployeeDTO;
import ru.cofffeen.mapper.EmployeeMapper;

import java.util.UUID;

public class EmployeeService extends BaseService<Employee> {
    public EmployeeDTO create(String name, EmployeeRole role) {
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .name(name)
                .role(role)
                .build();

        return EmployeeMapper.toDTO(repository.add(employee, employee.getId()));
    }

    public EmployeeDTO update(UUID id, String name, EmployeeRole role) {
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .name(name)
                .role(role)
                .build();

        return EmployeeMapper.toDTO(repository.update(employee));
    }
}
