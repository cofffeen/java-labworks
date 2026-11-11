package ru.cofffeen.domain.entity.car.part;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;

@Getter
@SuperBuilder
public class Transmission extends Part {
    private TransmissionType transmissionType;
    private int gears;
}
