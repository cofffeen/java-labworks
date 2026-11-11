package ru.cofffeen.domain.entity.car.order.states;

public class OrderCancelled implements OrderState {

    @Override
    public OrderState nextState() {
        return null;
    }

    @Override
    public OrderState cancel() {
        return null;
    }
}