package ru.cofffeen.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;

@Getter
@SuperBuilder
public class TransmissionDTO extends PartDTO {
    private TransmissionType transmissionType;
    private int gears;
}
