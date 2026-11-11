package ru.cofffeen.mapper;

import ru.cofffeen.domain.entity.user.Employee;
import ru.cofffeen.dto.EmployeeDTO;

public class EmployeeMapper {
    public static Employee fromDTO(EmployeeDTO dto) {
        return Employee.builder().
                name(dto.getName()).
                id(dto.getId()).
                role(dto.getRole()).
                build();
    }

    public static EmployeeDTO toDTO(Employee employee) {
        return EmployeeDTO.builder().
                name(employee.getName()).
                id(employee.getId()).
                role(employee.getRole()).
                build();
    }
}
