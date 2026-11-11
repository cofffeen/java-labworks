package ru.cofffeen.domain.entity.car.part;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Wheel extends Part {
    private int diameter;
    private String material;
}
