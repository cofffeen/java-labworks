package ru.cofffeen.domain.entity.car.part;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;

@Getter
@SuperBuilder
public class Engine extends Part {
    private FuelType fuelType;
    private int horsePower;
}
