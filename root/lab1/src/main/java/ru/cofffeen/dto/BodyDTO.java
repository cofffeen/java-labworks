package ru.cofffeen.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;

@Getter
@SuperBuilder
public class BodyDTO extends PartDTO {
    private BodyType bodyType;
    private String color;
}
