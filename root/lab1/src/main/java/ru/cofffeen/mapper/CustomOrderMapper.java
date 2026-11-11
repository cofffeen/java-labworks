package ru.cofffeen.mapper;

import ru.cofffeen.domain.entity.car.order.CustomOrderContext;
import ru.cofffeen.dto.CustomOrderDTO;

public class CustomOrderMapper {
    public static CustomOrderContext fromDTO(CustomOrderDTO dto) {
        return CustomOrderContext.builder().
                orderState(dto.getOrderState()).
                id(dto.getId()).
                client(ClientMapper.fromDTO(dto.getClient())).
                employee(EmployeeMapper.fromDTO(dto.getEmployee())).
                car(CarMapper.fromDTO(dto.getCar())).
                price(dto.getPrice()).build();
    }

    public static CustomOrderDTO toDTO(CustomOrderContext entity) {
        return CustomOrderDTO.builder().
                orderState(entity.getOrderState()).
                id(entity.getId()).
                client(ClientMapper.toDTO(entity.getClient())).
                employee(EmployeeMapper.toDTO(entity.getEmployee())).
                car(CarMapper.toDTO(entity.getCar())).
                price(entity.getPrice()).build();
    }
}
