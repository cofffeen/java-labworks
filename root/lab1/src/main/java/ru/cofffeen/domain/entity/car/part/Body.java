package ru.cofffeen.domain.entity.car.part;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;

@Getter
@SuperBuilder
public class Body extends Part {
    private BodyType bodyType;
    private String color;
}
