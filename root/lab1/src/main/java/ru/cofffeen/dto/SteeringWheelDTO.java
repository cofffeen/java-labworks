package ru.cofffeen.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SteeringWheelDTO extends PartDTO {
    private boolean isHeated;
    private int diameter;
}
