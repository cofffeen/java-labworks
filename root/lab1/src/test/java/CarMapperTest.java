import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.DriveType;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;
import ru.cofffeen.dto.*;
import ru.cofffeen.mapper.CarMapper;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class CarMapperTest {
    private Car car;
    private Body body;
    private Engine engine;
    private Interior interior;
    private SteeringWheel steeringWheel;
    private Transmission transmission;
    private Wheel wheel;
    private final String MODEL_NAME = "Test model";

    @BeforeEach
    void setUp() {
        Set<String> compatibleModels = Set.of(MODEL_NAME);

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
                .name(MODEL_NAME)
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
    void toDTO_ShouldMapAllFields() {
        CarDTO dto = CarMapper.toDTO(car);

        assertEquals(car.getId(), dto.getId());
        assertEquals(car.getName(), dto.getName());
        assertEquals(car.getBrand(), dto.getBrand());
        assertEquals(car.getBasePrice(), dto.getBasePrice());
        assertEquals(car.getDriveType(), dto.getDriveType());
        assertNotNull(dto.getBody());
        assertNotNull(dto.getEngine());
    }

    @Test
    void fromDTO_ShouldMapAllFields() {
        CarDTO dto = CarMapper.toDTO(car);

        Car newCar = CarMapper.fromDTO(dto);

        assertEquals(dto.getId(), newCar.getId());
        assertEquals(dto.getName(), newCar.getName());
        assertEquals(dto.getBrand(), newCar.getBrand());
        assertEquals(dto.getBasePrice(), newCar.getBasePrice());
        assertEquals(dto.getDriveType(), newCar.getDriveType());
        assertNotNull(newCar.getBody());
        assertNotNull(newCar.getEngine());
    }
}