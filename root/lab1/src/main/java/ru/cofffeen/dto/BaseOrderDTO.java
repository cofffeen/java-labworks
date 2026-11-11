package ru.cofffeen.dto;

import lombok.Builder;
import lombok.Getter;
import ru.cofffeen.domain.entity.car.order.states.OrderState;

import java.util.UUID;

@Getter
@Builder
public class BaseOrderDTO {
    private UUID id;
    private ClientDTO client;
    private EmployeeDTO employee;
    private CarDTO car;
    private int price;
    private OrderState orderState;
}
