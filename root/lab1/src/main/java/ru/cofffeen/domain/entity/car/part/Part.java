package ru.cofffeen.domain.entity.car.part;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.cofffeen.domain.exception.DomainValidationException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@SuperBuilder
public abstract class Part {
    protected String name;
    protected UUID id;
    protected int price;
    protected Set<String> compatibleModels = new HashSet<>();

    public void addCompatibleModel(String model) {
        compatibleModels.add(model);
    }

    public void updatePrice(int newPrice) {
        if (newPrice <= 0) {
            throw new DomainValidationException("Price must be positive");
        }

        price = newPrice;
    }
}
