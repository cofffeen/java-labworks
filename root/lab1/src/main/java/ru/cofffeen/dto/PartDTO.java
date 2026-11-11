package ru.cofffeen.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@SuperBuilder
public abstract class PartDTO {
    private String name;
    private UUID id;
    private int price;
    private Set<String> compatibleModels;
}
