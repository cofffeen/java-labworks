package ru.cofffeen.domain.entity.car.part;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Interior extends Part {
    private String material;
    private String color;
}
