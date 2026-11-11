package ru.cofffeen.dto;

import lombok.Builder;
import lombok.Getter;
import ru.cofffeen.domain.entity.car.DriveType;

import java.util.UUID;

@Getter
@Builder
public class CarDTO {
    private String name;
    private UUID id;
    private String brand;
    private int basePrice;
    private DriveType driveType;
    private BodyDTO body;
    private EngineDTO engine;
    private InteriorDTO interior;
    private SteeringWheelDTO steeringWheel;
    private TransmissionDTO transmission;
    private WheelDTO wheel;
}
