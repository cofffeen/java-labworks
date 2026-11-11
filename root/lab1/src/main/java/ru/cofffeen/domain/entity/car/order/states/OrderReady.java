package ru.cofffeen.domain.entity.car.order.states;

public class OrderReady implements OrderState {

    @Override
    public OrderState nextState() {
        return new OrderCompleted();
    }

    @Override
    public OrderState cancel() {
        return new OrderCancelled();
    }
}