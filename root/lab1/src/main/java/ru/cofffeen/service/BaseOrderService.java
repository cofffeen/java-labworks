package ru.cofffeen.service;

import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.order.BaseOrderContext;
import ru.cofffeen.domain.entity.car.order.states.OrderState;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.user.Client;
import ru.cofffeen.domain.entity.user.Employee;
import ru.cofffeen.dto.BaseOrderDTO;
import ru.cofffeen.mapper.BaseOrderMapper;
import ru.cofffeen.service.filter.BaseOrderFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseOrderService extends BaseService<BaseOrderContext> {
    public BaseOrderDTO create(Client client,
                               Employee employee,
                               Car car,
                               int price,
                               OrderState orderState) {
        BaseOrderContext baseOrder = BaseOrderContext.builder()
                .id(UUID.randomUUID())
                .client(client)
                .employee(employee)
                .car(car)
                .price(price)
                .orderState(orderState)
                .build();

        return BaseOrderMapper.toDTO(repository.add(baseOrder, baseOrder.getId()));
    }

    public BaseOrderDTO update(UUID id,
                                   Client client,
                                   Employee employee,
                                   Car car,
                                   int price,
                                   OrderState orderState) {
        BaseOrderContext baseOrder = BaseOrderContext.builder()
                .id(id)
                .client(client)
                .employee(employee)
                .car(car)
                .price(price)
                .orderState(orderState)
                .build();

        return BaseOrderMapper.toDTO(repository.update(baseOrder));
    }

    public List<BaseOrderDTO> getByFilter(BaseOrderFilter filter) {
        List<BaseOrderDTO> matchingOrders = new ArrayList<>();
        List<BaseOrderContext> orders = getAll();

        for (var order : orders) {
            if (filter.match(order)) {
                matchingOrders.add(BaseOrderMapper.toDTO(order));
            }
        }

        return matchingOrders;
    }
}
