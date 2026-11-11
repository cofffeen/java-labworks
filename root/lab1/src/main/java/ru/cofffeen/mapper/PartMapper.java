package ru.cofffeen.mapper;

import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.dto.*;

public class PartMapper {
    public static Body bodyFromDTO(BodyDTO dto) {
        return Body.builder().
                bodyType(dto.getBodyType()).
                color(dto.getColor()).
                name(dto.getName()).
                id(dto.getId()).
                price(dto.getPrice()).
                compatibleModels(dto.getCompatibleModels()).
                build();
    }

    public static BodyDTO bodyToDTO(Body body) {
        return BodyDTO.builder().
                bodyType(body.getBodyType()).
                color(body.getColor()).
                name(body.getName()).
                id(body.getId()).
                price(body.getPrice()).
                compatibleModels(body.getCompatibleModels()).
                build();
    }

    public static Engine engineFromDTO(EngineDTO dto) {
        return Engine.builder().
                fuelType(dto.getFuelType()).
                horsePower(dto.getHorsePower()).
                name(dto.getName()).
                id(dto.getId()).
                price(dto.getPrice()).
                compatibleModels(dto.getCompatibleModels()).
                build();
    }

    public static EngineDTO engineToDTO(Engine engine) {
        return EngineDTO.builder().
                fuelType(engine.getFuelType()).
                horsePower(engine.getHorsePower()).
                name(engine.getName()).
                id(engine.getId()).
                price(engine.getPrice()).
                compatibleModels(engine.getCompatibleModels()).
                build();
    }

    public static Interior interiorFromDTO(InteriorDTO dto) {
        return Interior.builder().
                material(dto.getMaterial()).
                color(dto.getColor()).
                name(dto.getName()).
                id(dto.getId()).
                price(dto.getPrice()).
                compatibleModels(dto.getCompatibleModels()).
                build();
    }

    public static InteriorDTO interiorToDTO(Interior interior) {
        return InteriorDTO.builder().
                material(interior.getMaterial()).
                color(interior.getColor()).
                name(interior.getName()).
                id(interior.getId()).
                price(interior.getPrice()).
                compatibleModels(interior.getCompatibleModels()).
                build();
    }

    public static SteeringWheel steeringWheelFromDTO(SteeringWheelDTO dto) {
        return SteeringWheel.builder().
                diameter(dto.getDiameter()).
                isHeated(dto.isHeated()).
                name(dto.getName()).
                id(dto.getId()).
                price(dto.getPrice()).
                compatibleModels(dto.getCompatibleModels()).
                build();
    }

    public static SteeringWheelDTO steeringWheelToDTO(SteeringWheel steeringWheel) {
        return SteeringWheelDTO.builder().
                diameter(steeringWheel.getDiameter()).
                isHeated(steeringWheel.isHeated()).
                name(steeringWheel.getName()).
                id(steeringWheel.getId()).
                price(steeringWheel.getPrice()).
                compatibleModels(steeringWheel.getCompatibleModels()).
                build();
    }

    public static Transmission transmissionFromDTO(TransmissionDTO dto) {
        return Transmission.builder().
                transmissionType(dto.getTransmissionType()).
                gears(dto.getGears()).
                name(dto.getName()).
                id(dto.getId()).
                price(dto.getPrice()).
                compatibleModels(dto.getCompatibleModels()).
                build();
    }

    public static TransmissionDTO transmissionToDTO(Transmission transmission) {
        return TransmissionDTO.builder().
                transmissionType(transmission.getTransmissionType()).
                gears(transmission.getGears()).
                name(transmission.getName()).
                id(transmission.getId()).
                price(transmission.getPrice()).
                compatibleModels(transmission.getCompatibleModels()).
                build();
    }

    public static Wheel wheelFromDTO(WheelDTO dto) {
        return Wheel.builder().
                diameter(dto.getDiameter()).
                material(dto.getMaterial()).
                name(dto.getName()).
                id(dto.getId()).
                price(dto.getPrice()).
                compatibleModels(dto.getCompatibleModels()).
                build();
    }

    public static WheelDTO wheelToDTO(Wheel wheel) {
        return WheelDTO.builder().
                diameter(wheel.getDiameter()).
                material(wheel.getMaterial()).
                name(wheel.getName()).
                id(wheel.getId()).
                price(wheel.getPrice()).
                compatibleModels(wheel.getCompatibleModels()).
                build();
    }
}
