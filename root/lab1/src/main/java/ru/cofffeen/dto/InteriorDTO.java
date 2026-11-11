package ru.cofffeen.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class InteriorDTO extends PartDTO {
    private String material;
    private String color;
}
