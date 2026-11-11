package ru.cofffeen.service;

import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.order.CustomOrderContext;
import ru.cofffeen.domain.entity.car.order.states.OrderState;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.user.Client;
import ru.cofffeen.domain.entity.user.Employee;
import ru.cofffeen.dto.CustomOrderDTO;
import ru.cofffeen.mapper.CustomOrderMapper;
import ru.cofffeen.service.filter.CustomOrderFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomOrderService extends BaseService<CustomOrderContext> {
    public CustomOrderDTO create(Client client,
                                 Employee employee,
                                 Car car,
                                 int price,
                                 OrderState orderState) {
        CustomOrderContext customOrder = CustomOrderContext.builder()
                .id(UUID.randomUUID())
                .client(client)
                .employee(employee)
                .car(car)
                .price(price)
                .orderState(orderState)
                .build();

        return CustomOrderMapper.toDTO(repository.add(customOrder, customOrder.getId()));
    }

    public CustomOrderDTO update(UUID id,
                                   Client client,
                                   Employee employee,
                                   Car car,
                                   int price,
                                   OrderState orderState) {
        CustomOrderContext customOrder = CustomOrderContext.builder()
                .id(id)
                .client(client)
                .employee(employee)
                .car(car)
                .price(price)
                .orderState(orderState)
                .build();

        return CustomOrderMapper.toDTO(repository.update(customOrder));
    }

    public List<CustomOrderDTO> getByFilter(CustomOrderFilter filter) {
        List<CustomOrderDTO> matchingOrders = new ArrayList<>();
        List<CustomOrderContext> orders = getAll();

        for (var order : orders) {
            if (filter.match(order)) {
                matchingOrders.add(CustomOrderMapper.toDTO(order));
            }
        }

        return matchingOrders;
    }

    public void trySetCustomParts(UUID id, Body body, Engine engine, Interior interior, SteeringWheel steeringWheel, Transmission transmission, Wheel wheel) {
        var order = repository.getById(id);
        order.trySetCustomParts(body, engine, interior, steeringWheel, transmission, wheel);
    }
}
