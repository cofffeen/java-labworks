import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.DriveType;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;
import ru.cofffeen.domain.exception.DomainValidationException;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarTest {
    private Car car;
    private Body body;
    private Engine engine;
    private Interior interior;
    private SteeringWheel steeringWheel;
    private Transmission transmission;
    private Wheel wheel;
    private final String modelName = "Test model";

    @BeforeEach
    void setUp() {
        Set<String> compatibleModels = Set.of(modelName);

        body = Body.builder()
                .id(UUID.randomUUID())
                .name("Test body")
                .price(5000)
                .compatibleModels(compatibleModels)
                .bodyType(BodyType.SEDAN)
                .color("Red")
                .build();

        engine = Engine.builder()
                .id(UUID.randomUUID())
                .name("Test engine")
                .price(10000)
                .compatibleModels(compatibleModels)
                .fuelType(FuelType.PETROL)
                .horsePower(450)
                .build();

        interior = Interior.builder()
                .id(UUID.randomUUID())
                .name("Test interior")
                .price(3000)
                .compatibleModels(compatibleModels)
                .material("Test")
                .color("Black")
                .build();

        steeringWheel = SteeringWheel.builder()
                .id(UUID.randomUUID())
                .name("Test steering wheel")
                .price(1000)
                .compatibleModels(compatibleModels)
                .isHeated(true)
                .diameter(350)
                .build();

        transmission = Transmission.builder()
                .id(UUID.randomUUID())
                .name("Test transmission")
                .price(4000)
                .compatibleModels(compatibleModels)
                .transmissionType(TransmissionType.AUTOMATIC)
                .gears(8)
                .build();

        wheel = Wheel.builder()
                .id(UUID.randomUUID())
                .name("Test wheels")
                .price(2000)
                .compatibleModels(compatibleModels)
                .diameter(19)
                .material("Test")
                .build();

        car = Car.builder()
                .id(UUID.randomUUID())
                .name("Test model")
                .brand("Test")
                .basePrice(50000)
                .driveType(DriveType.AWD)
                .body(body)
                .engine(engine)
                .interior(interior)
                .steeringWheel(steeringWheel)
                .transmission(transmission)
                .wheel(wheel)
                .build();
    }

    @Test
    void calculatePrice_ShouldReturnCorrectTotalPrice() {
        int expectedPrice = 50000 + 5000 + 10000 + 3000 + 1000 + 4000 + 2000;
        assertEquals(expectedPrice, car.calculatePrice());
    }

    @Test
    void calculatePrice_WithNullPart_ShouldThrowException() {
        Car invalidCar = Car.builder()
                .name(modelName)
                .brand("Tesla")
                .basePrice(50000)
                .driveType(DriveType.AWD)
                .body(null)
                .engine(engine)
                .interior(interior)
                .steeringWheel(steeringWheel)
                .transmission(transmission)
                .wheel(wheel)
                .build();

        assertThrows(DomainValidationException.class, invalidCar::calculatePrice);
    }

    @Test
    void calculatePrice_WithIncompatiblePart_ShouldThrowException() {
        Set<String> incompatibleModels = Set.of("Other Model");
        Body incompatibleBody = Body.builder()
                .id(UUID.randomUUID())
                .name("Incompatible Body")
                .price(5000)
                .compatibleModels(incompatibleModels)
                .bodyType(BodyType.SEDAN)
                .color("Blue")
                .build();

        assertThrows(
                DomainValidationException.class,
                () -> car.updateBody(incompatibleBody)
        );
    }

    @Test
    void updateBody_WithValidBody_ShouldUpdate() {
        Body newBody = Body.builder()
                .id(UUID.randomUUID())
                .name("New Body")
                .price(6000)
                .compatibleModels(Set.of(modelName))
                .bodyType(BodyType.COUPE)
                .color("Blue")
                .build();

        car.updateBody(newBody);
        assertEquals(newBody, car.getBody());
    }

    @Test
    void updateBody_WithIncompatibleBody_ShouldThrowException() {
        Body incompatibleBody = Body.builder()
                .id(UUID.randomUUID())
                .name("Incompatible Body")
                .price(5000)
                .compatibleModels(Set.of("Other Model"))
                .bodyType(BodyType.SEDAN)
                .color("Blue")
                .build();

        assertThrows(DomainValidationException.class, () -> car.updateBody(incompatibleBody));
    }
}