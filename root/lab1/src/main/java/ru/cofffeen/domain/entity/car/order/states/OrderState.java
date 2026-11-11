package ru.cofffeen.domain.entity.car.order.states;

public interface OrderState {
    public OrderState nextState();

    public OrderState cancel();
}
