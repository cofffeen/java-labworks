package ru.cofffeen.domain.entity.car.part;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SteeringWheel extends Part {
    private boolean isHeated;
    private int diameter;
}
