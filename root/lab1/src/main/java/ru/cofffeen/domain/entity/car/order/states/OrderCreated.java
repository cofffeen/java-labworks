package ru.cofffeen.domain.entity.car.order.states;

public class OrderCreated implements OrderState {

    @Override
    public OrderState nextState() {
        return new OrderApproved();
    }

    @Override
    public OrderState cancel() {
        return new OrderCancelled();
    }
}
