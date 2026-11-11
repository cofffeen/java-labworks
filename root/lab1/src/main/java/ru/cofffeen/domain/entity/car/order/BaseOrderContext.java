package ru.cofffeen.domain.entity.car.order;

import lombok.Builder;
import lombok.Getter;
import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.order.states.OrderCreated;
import ru.cofffeen.domain.entity.car.order.states.OrderState;
import ru.cofffeen.domain.entity.user.Client;
import ru.cofffeen.domain.entity.user.Employee;

import java.util.UUID;

@Getter
@Builder
public class BaseOrderContext {
    private UUID id;
    private Client client;
    private Employee employee;
    private Car car;
    private int price;
    private OrderState orderState;

    public BaseOrderContext(Client client, Employee employee, Car car, UUID id) {
        this.client = client;
        this.employee = employee;
        this.car = car;
        this.id = id;

        this.price = this.car.calculatePrice();
        this.orderState = new OrderCreated();
    }

    public BaseOrderContext(UUID id, Client client, Employee employee, Car car, int price, OrderState orderState) {
        this.id = id;
        this.client = client;
        this.employee = employee;
        this.car = car;
        this.price = price;
        this.orderState = orderState;
    }

    public OrderState nextState() {
        return orderState.nextState();
    }

    public OrderState cancel() {
        return orderState.cancel();
    }
}
