import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.DriveType;
import ru.cofffeen.domain.entity.car.order.CustomOrderContext;
import ru.cofffeen.domain.entity.car.order.states.OrderBundlingError;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;
import ru.cofffeen.domain.entity.user.Client;
import ru.cofffeen.domain.entity.user.Employee;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomOrderContextTest {
    private Client client;
    private Employee employee;
    private Car car;
    private UUID orderId;
    private Body compatibleBody;
    private Body incompatibleBody;
    private Engine engine;
    private Interior interior;
    private SteeringWheel steeringWheel;
    private Transmission transmission;
    private Wheel wheel;
    private final String modelName = "Test model";

    @BeforeEach
    void setUp() {
        client = Client.builder().id(UUID.randomUUID()).build();
        employee = Employee.builder().id(UUID.randomUUID()).build();

        Set<String> compatibleModels = Set.of(modelName);

        String modelName = "Test model";

        compatibleBody = Body.builder()
                .id(UUID.randomUUID())
                .name("Compatible Body")
                .price(5000)
                .compatibleModels(Set.of(modelName))
                .build();

        incompatibleBody = Body.builder()
                .id(UUID.randomUUID())
                .name("Incompatible Body")
                .price(5000)
                .compatibleModels(Set.of("Other Model"))
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
                .body(compatibleBody)
                .engine(engine)
                .interior(interior)
                .steeringWheel(steeringWheel)
                .transmission(transmission)
                .wheel(wheel)
                .build();

        orderId = UUID.randomUUID();
    }

    @Test
    void trySetCustomParts_WithCompatibleParts_ShouldUpdateSuccessfully() {
        CustomOrderContext order = new CustomOrderContext(client, employee, car, orderId);

        order.trySetCustomParts(compatibleBody, engine, interior, steeringWheel, transmission, wheel);

        assertEquals(compatibleBody, order.getCar().getBody());
        assertFalse(order.getOrderState() instanceof OrderBundlingError);
    }

    @Test
    void trySetCustomParts_WithIncompatibleParts_ShouldSetErrorState() {
        CustomOrderContext order = new CustomOrderContext(client, employee, car, orderId);

        order.trySetCustomParts(incompatibleBody, engine, null, null, null, null);

        assertInstanceOf(OrderBundlingError.class, order.getOrderState());
    }
}