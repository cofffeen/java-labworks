import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;
import ru.cofffeen.dto.*;
import ru.cofffeen.inmemory.InMemoryRepository;
import ru.cofffeen.repository.Repository;
import ru.cofffeen.service.PartService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class PartServiceTest {
    private PartService partService;
    private Repository<Part, UUID> repository;
    private final String modelName = "Test model";

    @BeforeEach
    void setUp() {
        repository = new InMemoryRepository<>();
        partService = new PartService(repository);
    }

    @Test
    void addBody_ShouldCreateAndReturnBodyDTO() {
        Set<String> compatibleModels = Set.of(modelName);

        BodyDTO result = partService.addBody(
                "Test body",
                5000,
                compatibleModels,
                BodyType.SEDAN,
                "Red"
        );

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test body", result.getName());
        assertEquals(5000, result.getPrice());
        assertEquals(BodyType.SEDAN, result.getBodyType());
        assertEquals("Red", result.getColor());
        assertEquals(compatibleModels, result.getCompatibleModels());

        Part saved = repository.getById(result.getId());
        assertNotNull(saved);
        assertInstanceOf(Body.class, saved);
        assertEquals("Test body", saved.getName());
    }

    @Test
    void addEngine_ShouldCreateAndReturnEngineDTO() {
        Set<String> compatibleModels = Set.of(modelName);

        EngineDTO result = partService.addEngine(
                "Test engine",
                10000,
                compatibleModels,
                FuelType.PETROL,
                450
        );

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test engine", result.getName());
        assertEquals(10000, result.getPrice());
        assertEquals(FuelType.PETROL, result.getFuelType());
        assertEquals(450, result.getHorsePower());

        Part saved = repository.getById(result.getId());
        assertTrue(saved instanceof Engine);
    }

    @Test
    void addInterior_ShouldCreateAndReturnInteriorDTO() {
        Set<String> compatibleModels = Set.of(modelName);

        InteriorDTO result = partService.addInterior(
                "Test interior",
                3000,
                compatibleModels,
                "Test",
                "Black"
        );

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test interior", result.getName());
        assertEquals(3000, result.getPrice());
        assertEquals("Test", result.getMaterial());
        assertEquals("Black", result.getColor());

        Part saved = repository.getById(result.getId());
        assertInstanceOf(Interior.class, saved);
    }

    @Test
    void addSteeringWheel_ShouldCreateAndReturnSteeringWheelDTO() {
        Set<String> compatibleModels = Set.of(modelName);

        SteeringWheelDTO result = partService.addSteeringWheel(
                "Test wheel",
                1000,
                compatibleModels,
                true,
                350
        );

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test wheel", result.getName());
        assertEquals(1000, result.getPrice());
        assertTrue(result.isHeated());
        assertEquals(350, result.getDiameter());

        Part saved = repository.getById(result.getId());
        assertInstanceOf(SteeringWheel.class, saved);
        assertTrue(((SteeringWheel) saved).isHeated());
    }

    @Test
    void addTransmission_ShouldCreateAndReturnTransmissionDTO() {
        Set<String> compatibleModels = Set.of(modelName);

        TransmissionDTO result = partService.addTransmission(
                "Test transmission",
                4000,
                compatibleModels,
                TransmissionType.AUTOMATIC,
                8
        );

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test transmission", result.getName());
        assertEquals(4000, result.getPrice());
        assertEquals(TransmissionType.AUTOMATIC, result.getTransmissionType());
        assertEquals(8, result.getGears());

        Part saved = repository.getById(result.getId());
        assertInstanceOf(Transmission.class, saved);
    }

    @Test
    void addWheel_ShouldCreateAndReturnWheelDTO() {
        Set<String> compatibleModels = Set.of(modelName);

        WheelDTO result = partService.addWheel(
                "Test wheels",
                2000,
                compatibleModels,
                19,
                "Test"
        );

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test wheels", result.getName());
        assertEquals(2000, result.getPrice());
        assertEquals(19, result.getDiameter());
        assertEquals("Test", result.getMaterial());

        Part saved = repository.getById(result.getId());
        assertInstanceOf(Wheel.class, saved);
    }

    @Test
    void updatePrice_WithValidId_ShouldUpdate() {
        Set<String> compatibleModels = Set.of(modelName);
        BodyDTO bodyDTO = partService.addBody("Test Body", 5000, compatibleModels, BodyType.SEDAN, "Red");

        partService.updatePrice(bodyDTO.getId(), 6000);

        Part updated = repository.getById(bodyDTO.getId());
        assertEquals(6000, updated.getPrice());
    }

    @Test
    void updatePrice_WithInvalidId_ShouldThrowException() {
        UUID nonExistentId = UUID.randomUUID();

        assertThrows(NullPointerException.class, () -> {
            partService.updatePrice(nonExistentId, 6000);
        });
    }

    @Test
    void addCompatibleCar_ShouldAddModel() {
        Set<String> compatibleModels = new HashSet<>(Set.of("Test model 1"));
        BodyDTO bodyDTO = partService.addBody("Test Body", 5000, compatibleModels, BodyType.SEDAN, "Red");

        Car car = Car.builder()
                .id(UUID.randomUUID())
                .name("Test model 2")
                .build();

        partService.addCompatibleCar(bodyDTO.getId(), car);
        Part updated = repository.getById(bodyDTO.getId());

        assertTrue(updated.getCompatibleModels().contains("Test model 1"));
        assertTrue(updated.getCompatibleModels().contains("Test model 2"));
        assertEquals(2, updated.getCompatibleModels().size());
    }

    @Test
    void getById_ShouldReturnCorrectPart() {
        Set<String> compatibleModels = Set.of(modelName);
        BodyDTO bodyDTO = partService.addBody("Test Body", 5000, compatibleModels, BodyType.SEDAN, "Red");

        Part found = partService.getById(bodyDTO.getId());

        assertNotNull(found);
        assertEquals(bodyDTO.getId(), found.getId());
        assertEquals("Test Body", found.getName());
    }

    @Test
    void getAll_ShouldReturnAllParts() {
        partService.addBody("Test body", 5000, Set.of(modelName), BodyType.SEDAN, "Red");
        partService.addEngine("Test engine", 10000, Set.of(modelName), FuelType.PETROL, 300);
        partService.addWheel("Test wheel", 2000, Set.of(modelName), 18, "Test");

        List<Part> allParts = partService.getAll();

        assertEquals(3, allParts.size());
    }
}