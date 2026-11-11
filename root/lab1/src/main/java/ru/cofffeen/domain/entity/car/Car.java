package ru.cofffeen.domain.entity.car;

import lombok.Builder;
import lombok.Getter;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.exception.DomainValidationException;

import java.util.UUID;

@Getter
@Builder
public class Car {
    private String name;
    private UUID id;
    private String brand;
    private int basePrice;
    private DriveType driveType;
    private Body body;
    private Engine engine;
    private Interior interior;
    private SteeringWheel steeringWheel;
    private Transmission transmission;
    private Wheel wheel;

    public int calculatePrice() {
        validate();

        int price = basePrice;
        price += body.getPrice();
        price += engine.getPrice();
        price += interior.getPrice();
        price += steeringWheel.getPrice();
        price += transmission.getPrice();
        price += wheel.getPrice();

        return price;
    }

    private void validate() {
        if (body == null || !body.getCompatibleModels().contains(name)) {
            throw new DomainValidationException("Car has no compatible body");
        }
        if (engine == null || !engine.getCompatibleModels().contains(name)) {
            throw new DomainValidationException("Car has no compatible engine");
        }
        if (interior == null || !interior.getCompatibleModels().contains(name)) {
            throw new DomainValidationException("Car has no compatible interior");
        }
        if (steeringWheel == null || !steeringWheel.getCompatibleModels().contains(name)) {
            throw new DomainValidationException("Car has no compatible steering wheel");
        }
        if (transmission == null || !transmission.getCompatibleModels().contains(name)) {
            throw new DomainValidationException("Car has no compatible transmission");
        }
        if (wheel == null || !wheel.getCompatibleModels().contains(name)) {
            throw new DomainValidationException("Car has no compatible wheel");
        }
    }

    public void updateBody(Body body) {
        this.body = body;

        validate();
    }

    public void updateEngine(Engine engine) {
        this.engine = engine;

        validate();
    }

    public void updateInterior(Interior interior) {
        this.interior = interior;

        validate();
    }

    public void updateSteeringWheel(SteeringWheel steeringWheel) {
        this.steeringWheel = steeringWheel;

        validate();
    }

    public void updateTransmission(Transmission transmission) {
        this.transmission = transmission;

        validate();
    }

    public void updateWheel(Wheel wheel) {
        this.wheel = wheel;

        validate();
    }
}
