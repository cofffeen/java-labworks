package ru.cofffeen.service.filter;

import lombok.Builder;
import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.DriveType;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;

import java.util.Objects;

@Builder
public class CarFilter {
    private Integer minBasePrice;
    private Integer maxBasePrice;
    private Integer minPrice;
    private Integer maxPrice;
    private String brand;
    private BodyType bodyType;
    private FuelType fuelType;
    private Integer minPower;
    private Integer maxPower;
    private TransmissionType transmissionType;
    private DriveType driveType;
    private String interiorColor;

    public boolean match(Car car) {
        if (minBasePrice != null && car.getBasePrice() < minBasePrice) {
            return false;
        }

        if (maxBasePrice != null && car.getBasePrice() > maxBasePrice) {
            return false;
        }

        if (minPrice != null && car.calculatePrice() < minPrice) {
            return false;
        }

        if (maxPrice != null && car.calculatePrice() > maxPrice) {
            return false;
        }

        if (brand != null && !Objects.equals(car.getBrand(), brand)) {
            return false;
        }

        if (bodyType != null && car.getBody().getBodyType() != bodyType) {
            return false;
        }

        if (fuelType != null && car.getEngine().getFuelType() != fuelType) {
            return false;
        }

        if (minPower != null && car.getEngine().getHorsePower() < minPower) {
            return false;
        }

        if (maxPower != null && car.getEngine().getHorsePower() > maxPower) {
            return false;
        }

        if (transmissionType != null && car.getTransmission().getTransmissionType() != transmissionType) {
            return false;
        }

        if (driveType != null && car.getDriveType() != driveType) {
            return false;
        }

        if (interiorColor != null && !Objects.equals(car.getInterior().getColor(), interiorColor)) {
            return false;
        }

        return true;
    }
}
