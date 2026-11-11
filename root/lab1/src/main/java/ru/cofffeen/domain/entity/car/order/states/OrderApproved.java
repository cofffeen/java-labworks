package ru.cofffeen.domain.entity.car.order.states;

public class OrderApproved implements OrderState {

    @Override
    public OrderState nextState() {
        return new OrderReady();
    }

    @Override
    public OrderState cancel() {
        return new OrderCancelled();
    }
}