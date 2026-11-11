package ru.cofffeen.domain.entity.car.order;

import lombok.Builder;
import lombok.Getter;
import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.order.states.OrderBundlingError;
import ru.cofffeen.domain.entity.car.order.states.OrderCreated;
import ru.cofffeen.domain.entity.car.order.states.OrderState;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.user.Client;
import ru.cofffeen.domain.entity.user.Employee;

import java.util.UUID;

@Getter
@Builder
public class CustomOrderContext {
    private UUID id;
    private Client client;
    private Employee employee;
    private Car car;
    private int price;
    private OrderState orderState;

    public CustomOrderContext(Client client, Employee employee, Car car, UUID id) {
        this.client = client;
        this.employee = employee;
        this.car = car;
        this.id = id;

        this.price = this.car.calculatePrice();
        this.orderState = new OrderCreated();
    }

    public CustomOrderContext(UUID id, Client client, Employee employee, Car car, int price, OrderState orderState) {
        this.id = id;
        this.client = client;
        this.employee = employee;
        this.car = car;
        this.price = price;
        this.orderState = orderState;
    }

    public void nextState() {
        orderState.nextState();
    }

    public void cancel() {
        orderState.cancel();
    }

    public void trySetCustomParts(Body body, Engine engine, Interior interior, SteeringWheel steeringWheel, Transmission transmission, Wheel wheel) {
        try {
            car.updateBody(body);
            car.updateEngine(engine);
            car.updateInterior(interior);
            car.updateSteeringWheel(steeringWheel);
            car.updateTransmission(transmission);
            car.updateWheel(wheel);
        } catch (Exception e) {
            orderState = new OrderBundlingError();
        }
    }
}
