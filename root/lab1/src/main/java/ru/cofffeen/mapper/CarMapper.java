package ru.cofffeen.mapper;

import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.dto.CarDTO;

public class CarMapper {
    public static Car fromDTO(CarDTO dto) {
        return Car.builder().
                name(dto.getName()).
                id(dto.getId()).
                brand(dto.getBrand()).
                basePrice(dto.getBasePrice()).
                driveType(dto.getDriveType()).
                body(PartMapper.bodyFromDTO(dto.getBody())).
                engine(PartMapper.engineFromDTO(dto.getEngine())).
                interior(PartMapper.interiorFromDTO(dto.getInterior())).
                steeringWheel(PartMapper.steeringWheelFromDTO(dto.getSteeringWheel())).
                transmission(PartMapper.transmissionFromDTO(dto.getTransmission())).
                wheel(PartMapper.wheelFromDTO(dto.getWheel())).
                build();
    }

    public static CarDTO toDTO(Car car) {
        return CarDTO.builder().
                name(car.getName()).
                id(car.getId()).
                brand(car.getBrand()).
                basePrice(car.getBasePrice()).
                driveType(car.getDriveType()).
                body(PartMapper.bodyToDTO(car.getBody())).
                engine(PartMapper.engineToDTO(car.getEngine())).
                interior(PartMapper.interiorToDTO(car.getInterior())).
                steeringWheel(PartMapper.steeringWheelToDTO(car.getSteeringWheel())).
                transmission(PartMapper.transmissionToDTO(car.getTransmission())).
                wheel(PartMapper.wheelToDTO(car.getWheel())).
                build();
    }
}
