package ru.cofffeen.service.filter;

import ru.cofffeen.domain.entity.car.order.CustomOrderContext;

import java.util.UUID;

public class CustomOrderFilter {
    private UUID clientID;
    private UUID employeeID;

    public boolean match(CustomOrderContext order) {
        if (clientID != null && order.getClient().getId() != clientID) {
            return false;
        }

        if (employeeID != null && order.getEmployee().getId() != employeeID) {
            return false;
        }

        return true;
    }
}
