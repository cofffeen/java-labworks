import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.DriveType;
import ru.cofffeen.domain.entity.car.order.BaseOrderContext;
import ru.cofffeen.domain.entity.car.order.states.OrderApproved;
import ru.cofffeen.domain.entity.car.order.states.OrderCancelled;
import ru.cofffeen.domain.entity.car.order.states.OrderCreated;
import ru.cofffeen.domain.entity.car.order.states.OrderState;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;
import ru.cofffeen.domain.entity.user.Client;
import ru.cofffeen.domain.entity.user.Employee;
import ru.cofffeen.domain.entity.user.EmployeeRole;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class BaseOrderContextTest {
    private Client client;
    private Employee employee;
    private UUID orderId;
    private Car car;
    private Body body;
    private Engine engine;
    private Interior interior;
    private SteeringWheel steeringWheel;
    private Transmission transmission;
    private Wheel wheel;
    private String modelName = "Test model";

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(UUID.randomUUID())
                .name("Test client")
                .birthDate("2000-01-01")
                .phone("1234567890")
                .email("test@example.com")
                .build();

        employee = Employee.builder()
                .id(UUID.randomUUID())
                .name("Test employee")
                .role(EmployeeRole.CAR_DEALERSHIP_ADMIN)
                .build();

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
                .name(modelName)
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

        orderId = UUID.randomUUID();
    }

    @Test
    void constructor_ShouldCreateOrderWithCorrectState() {
        BaseOrderContext order = new BaseOrderContext(client, employee, car, orderId);

        assertEquals(client, order.getClient());
        assertEquals(employee, order.getEmployee());
        assertEquals(car, order.getCar());
        assertEquals(orderId, order.getId());
        assertInstanceOf(OrderCreated.class, order.getOrderState());
    }

    @Test
    void nextState_ShouldTransitionState() {
        BaseOrderContext order = new BaseOrderContext(client, employee, car, orderId);

        OrderState newState = order.nextState();
        assertInstanceOf(OrderApproved.class, newState);
    }

    @Test
    void cancel_ShouldTransitionToCancelled() {
        BaseOrderContext order = new BaseOrderContext(client, employee, car, orderId);

        OrderState cancelled = order.cancel();
        assertInstanceOf(OrderCancelled.class, cancelled);
    }
}