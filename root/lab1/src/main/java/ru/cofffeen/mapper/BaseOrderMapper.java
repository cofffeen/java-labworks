package ru.cofffeen.mapper;

import ru.cofffeen.domain.entity.car.order.BaseOrderContext;
import ru.cofffeen.dto.BaseOrderDTO;

public class BaseOrderMapper {
    public static BaseOrderContext fromDTO(BaseOrderDTO dto) {
        return BaseOrderContext.builder().
                orderState(dto.getOrderState()).
                id(dto.getId()).
                client(ClientMapper.fromDTO(dto.getClient())).
                employee(EmployeeMapper.fromDTO(dto.getEmployee())).
                car(CarMapper.fromDTO(dto.getCar())).
                price(dto.getPrice()).build();
    }

    public static BaseOrderDTO toDTO(BaseOrderContext entity) {
        return BaseOrderDTO.builder().
                orderState(entity.getOrderState()).
                id(entity.getId()).
                client(ClientMapper.toDTO(entity.getClient())).
                employee(EmployeeMapper.toDTO(entity.getEmployee())).
                car(CarMapper.toDTO(entity.getCar())).
                price(entity.getPrice()).build();
    }
}
