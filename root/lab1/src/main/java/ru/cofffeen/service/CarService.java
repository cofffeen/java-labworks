package ru.cofffeen.service;

import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.DriveType;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.dto.CarDTO;
import ru.cofffeen.mapper.CarMapper;
import ru.cofffeen.repository.Repository;
import ru.cofffeen.service.filter.CarFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarService extends BaseService<Car> {
    public CarService(Repository<Car, UUID> repository) {
        this.repository = repository;
    }

    public CarDTO create(String name,
                         String brand,
                         int basePrice,
                         DriveType driveType,
                         Body body,
                         Engine engine,
                         Interior interior,
                         SteeringWheel steeringWheel,
                         Transmission transmission,
                         Wheel wheel) {
        Car car = Car.builder()
                .id(UUID.randomUUID())
                .name(name)
                .brand(brand)
                .basePrice(basePrice)
                .driveType(driveType)
                .body(body)
                .engine(engine)
                .interior(interior)
                .steeringWheel(steeringWheel)
                .transmission(transmission)
                .wheel(wheel)
                .build();

        return CarMapper.toDTO(repository.add(car, car.getId()));
    }

    public CarDTO update(UUID id,
                         String name,
                         String brand,
                         int basePrice,
                         DriveType driveType,
                         Body body,
                         Engine engine,
                         Interior interior,
                         SteeringWheel steeringWheel,
                         Transmission transmission,
                         Wheel wheel) {
        Car car = Car.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .basePrice(basePrice)
                .driveType(driveType)
                .body(body)
                .engine(engine)
                .interior(interior)
                .steeringWheel(steeringWheel)
                .transmission(transmission)
                .wheel(wheel)
                .build();

        return CarMapper.toDTO(repository.update(car));
    }

    public List<CarDTO> getByFilter(CarFilter filter) {
        List<CarDTO> matchingCars = new ArrayList<>();
        List<Car> cars = getAll();

        for (var car : cars) {
            if (filter.match(car)) {
                matchingCars.add(CarMapper.toDTO(car));
            }
        }

        return matchingCars;
    }
}
