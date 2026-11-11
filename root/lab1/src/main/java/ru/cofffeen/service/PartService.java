package ru.cofffeen.service;

import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;
import ru.cofffeen.dto.*;
import ru.cofffeen.mapper.PartMapper;
import ru.cofffeen.repository.Repository;

import java.util.Set;
import java.util.UUID;

public class PartService extends BaseService<Part> {
    public PartService(Repository<Part, UUID> repository) {
        this.repository = repository;
    }

    public BodyDTO addBody(String name,
                           int price,
                           Set<String> compatibleModels,
                           BodyType bodyType,
                           String color) {
        Body body = Body.builder()
                .id(UUID.randomUUID())
                .name(name)
                .price(price)
                .compatibleModels(compatibleModels)
                .bodyType(bodyType)
                .color(color)
                .build();

        return PartMapper.bodyToDTO((Body) repository.add(body, body.getId()));
    }

    public EngineDTO addEngine(String name,
                               int price,
                               Set<String> compatibleModels,
                               FuelType fuelType,
                               int horsePower) {
        Engine engine = Engine.builder()
                .id(UUID.randomUUID())
                .name(name)
                .price(price)
                .compatibleModels(compatibleModels)
                .fuelType(fuelType)
                .horsePower(horsePower)
                .build();

        return PartMapper.engineToDTO((Engine) repository.add(engine, engine.getId()));
    }

    public InteriorDTO addInterior(String name,
                                   int price,
                                   Set<String> compatibleModels,
                                   String material,
                                   String color) {
        Interior interior = Interior.builder()
                .id(UUID.randomUUID())
                .name(name)
                .price(price)
                .compatibleModels(compatibleModels)
                .material(material)
                .color(color)
                .build();

        return PartMapper.interiorToDTO((Interior) repository.add(interior, interior.getId()));
    }

    public SteeringWheelDTO addSteeringWheel(String name,
                                             int price,
                                             Set<String> compatibleModels,
                                             boolean isHeated,
                                             int diameter) {
        SteeringWheel steeringWheel = SteeringWheel.builder()
                .id(UUID.randomUUID())
                .name(name)
                .price(price)
                .compatibleModels(compatibleModels)
                .isHeated(isHeated)
                .diameter(diameter)
                .build();

        return PartMapper.steeringWheelToDTO((SteeringWheel) repository.add(steeringWheel, steeringWheel.getId()));
    }

    public TransmissionDTO addTransmission(String name,
                                           int price,
                                           Set<String> compatibleModels,
                                           TransmissionType transmissionType,
                                           int gears) {
        Transmission transmission = Transmission.builder()
                .id(UUID.randomUUID())
                .name(name)
                .price(price)
                .compatibleModels(compatibleModels)
                .transmissionType(transmissionType)
                .gears(gears)
                .build();

        return PartMapper.transmissionToDTO((Transmission) repository.add(transmission, transmission.getId()));
    }

    public WheelDTO addWheel(String name,
                          int price,
                          Set<String> compatibleModels,
                          int diameter,
                          String material) {
        Wheel wheel = Wheel.builder()
                .id(UUID.randomUUID())
                .name(name)
                .price(price)
                .compatibleModels(compatibleModels)
                .diameter(diameter)
                .material(material)
                .build();

        return PartMapper.wheelToDTO((Wheel) repository.add(wheel, wheel.getId()));
    }

    public void updatePrice(UUID id, int price) {
        var part = repository.getById(id);
        part.updatePrice(price);
    }

    public void addCompatibleCar(UUID id, Car car) {
        var part = repository.getById(id);
        part.addCompatibleModel(car.getName());
    }
}
