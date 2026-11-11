package ru.cofffeen.domain.entity.car.order.states;

public class OrderBundlingError implements OrderState {

    @Override
    public OrderState nextState() {
        return new OrderCancelled();
    }

    @Override
    public OrderState cancel() {
        return new OrderCancelled();
    }
}