package ru.cofffeen.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;

@Getter
@SuperBuilder
public class EngineDTO extends PartDTO {
    private FuelType fuelType;
    private int horsePower;
}
