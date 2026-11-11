package ru.cofffeen.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class WheelDTO extends PartDTO {
    private int diameter;
    private String material;
}
