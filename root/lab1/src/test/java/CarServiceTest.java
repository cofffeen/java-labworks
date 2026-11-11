import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.DriveType;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;
import ru.cofffeen.dto.CarDTO;
import ru.cofffeen.inmemory.InMemoryRepository;
import ru.cofffeen.repository.Repository;
import ru.cofffeen.service.filter.CarFilter;
import ru.cofffeen.service.CarService;
import ru.cofffeen.service.PartService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {
    private CarService carService;
    private Repository<Car, UUID> repository;
    private PartService partService;
    private Body body;
    private Engine engine;
    private Interior interior;
    private SteeringWheel steeringWheel;
    private Transmission transmission;
    private Wheel wheel;
    private final String modelName = "Test model";

    @BeforeEach
    void setUp() {
        repository = new InMemoryRepository<>();
        carService = new CarService(repository);

        partService = new PartService(new InMemoryRepository<>());

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
    }

    @Test
    void create_ShouldCreateAndReturnCarDTO() {
        CarDTO result = carService.create(
                modelName,
                "Test",
                50000,
                DriveType.AWD,
                body,
                engine,
                interior,
                steeringWheel,
                transmission,
                wheel
        );

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(modelName, result.getName());
        assertEquals("Test", result.getBrand());
        assertEquals(50000, result.getBasePrice());
        assertEquals(DriveType.AWD, result.getDriveType());
        assertNotNull(result.getBody());
        assertNotNull(result.getEngine());

        Car saved = repository.getById(result.getId());
        assertNotNull(saved);
        assertEquals(modelName, saved.getName());
    }

    @Test
    void getById_ShouldReturnCorrectCar() {
        CarDTO created = carService.create(
                modelName,
                "Test",
                50000,
                DriveType.AWD,
                body,
                engine,
                interior,
                steeringWheel,
                transmission,
                wheel
        );

        Car found = carService.getById(created.getId());

        assertNotNull(found);
        assertEquals(created.getId(), found.getId());
        assertEquals(modelName, found.getName());
    }

    @Test
    void getAll_ShouldReturnAllCars() {
        carService.create("Test model 1", "Test", 50000, DriveType.AWD, body, engine, interior, steeringWheel, transmission, wheel);
        carService.create("Test model 2", "Test", 40000, DriveType.RWD, body, engine, interior, steeringWheel, transmission, wheel);
        carService.create("Test model 3", "Test", 80000, DriveType.AWD, body, engine, interior, steeringWheel, transmission, wheel);

        List<Car> allCars = carService.getAll();

        assertEquals(3, allCars.size());
    }

    @Test
    void getByFilter_WithBrandFilter_ShouldReturnMatchingCars() {
        carService.create("Test model 1", "Test", 50000, DriveType.AWD, body, engine, interior, steeringWheel, transmission, wheel);
        carService.create("Test model 2", "Random", 60000, DriveType.AWD, body, engine, interior, steeringWheel, transmission, wheel);
        carService.create("Test model 3", "TestTest", 45000, DriveType.FWD, body, engine, interior, steeringWheel, transmission, wheel);

        CarFilter filter = CarFilter.builder()
                .brand("Test")
                .build();

        List<CarDTO> result = carService.getByFilter(filter);

        assertEquals(1, result.size());
        assertEquals("Test", result.getFirst().getBrand());
    }

    @Test
    void getByFilter_WithPriceRange_ShouldReturnMatchingCars() {
        carService.create("Test model 1", "Test", 40000, DriveType.RWD, body, engine, interior, steeringWheel, transmission, wheel);
        carService.create("Test model 2", "Test", 50000, DriveType.AWD, body, engine, interior, steeringWheel, transmission, wheel);
        carService.create("Test model 3", "Test", 80000, DriveType.AWD, body, engine, interior, steeringWheel, transmission, wheel);

        CarFilter filter = CarFilter.builder()
                .minBasePrice(45000)
                .maxBasePrice(70000)
                .build();

        List<CarDTO> result = carService.getByFilter(filter);

        assertEquals(1, result.size());
        assertEquals(50000, result.getFirst().getBasePrice());
    }

    @Test
    void getByFilter_WithMultipleFilters_ShouldReturnMatchingCars() {
        carService.create("Test model 1", "Test", 40000, DriveType.RWD, body, engine, interior, steeringWheel, transmission, wheel);
        carService.create("Test model 2", "Test", 50000, DriveType.AWD, body, engine, interior, steeringWheel, transmission, wheel);
        carService.create("Test model 3", "Random", 60000, DriveType.AWD, body, engine, interior, steeringWheel, transmission, wheel);

        CarFilter filter = CarFilter.builder()
                .brand("Test")
                .minBasePrice(45000)
                .driveType(DriveType.AWD)
                .build();

        List<CarDTO> result = carService.getByFilter(filter);

        assertEquals(1, result.size());
        assertEquals("Test model 2", result.getFirst().getName());
    }

    @Test
    void remove_ShouldDeleteCar() {
        CarDTO created = carService.create(
                modelName,
                "Test",
                50000,
                DriveType.AWD,
                body,
                engine,
                interior,
                steeringWheel,
                transmission,
                wheel
        );

        carService.remove(created.getId());

        assertThrows(RuntimeException.class, () -> {
            carService.getById(created.getId());
        });
    }
}