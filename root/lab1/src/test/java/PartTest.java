import org.junit.jupiter.api.Test;
import ru.cofffeen.domain.entity.car.part.Engine;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;
import ru.cofffeen.domain.exception.DomainValidationException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {

    @Test
    void addCompatibleModel_ShouldAddModel() {
        Engine engine = Engine.builder()
                .id(UUID.randomUUID())
                .name("Test engine")
                .price(5000)
                .compatibleModels(new HashSet<>())
                .fuelType(FuelType.PETROL)
                .horsePower(200)
                .build();

        engine.addCompatibleModel("Test model");
        assertTrue(engine.getCompatibleModels().contains("Test model"));
    }

    @Test
    void addCompatibleModel_ShouldNotRemoveExistingModels() {
        Set<String> initialModels = new HashSet<>(Set.of("Test model 1"));
        Engine engine = Engine.builder()
                .id(UUID.randomUUID())
                .name("Test engine")
                .price(5000)
                .compatibleModels(initialModels)
                .fuelType(FuelType.PETROL)
                .horsePower(200)
                .build();

        engine.addCompatibleModel("Test model 2");

        assertTrue(engine.getCompatibleModels().contains("Test model 1"));
        assertTrue(engine.getCompatibleModels().contains("Test model 2"));
        assertEquals(2, engine.getCompatibleModels().size());
    }

    @Test
    void updatePrice_WithPositivePrice_ShouldUpdate() {
        Engine engine = Engine.builder()
                .id(UUID.randomUUID())
                .name("Test engine")
                .price(5000)
                .compatibleModels(new HashSet<>())
                .fuelType(FuelType.PETROL)
                .horsePower(200)
                .build();

        engine.updatePrice(6000);
        assertEquals(6000, engine.getPrice());
    }

    @Test
    void updatePrice_WithZeroPrice_ShouldThrowException() {
        Engine engine = Engine.builder()
                .id(UUID.randomUUID())
                .name("Test engine")
                .price(5000)
                .compatibleModels(new HashSet<>())
                .fuelType(FuelType.PETROL)
                .horsePower(200)
                .build();

        assertThrows(DomainValidationException.class, () -> engine.updatePrice(0));
    }

    @Test
    void updatePrice_WithNegativePrice_ShouldThrowException() {
        Engine engine = Engine.builder()
                .id(UUID.randomUUID())
                .name("Test engine")
                .price(5000)
                .compatibleModels(new HashSet<>())
                .fuelType(FuelType.PETROL)
                .horsePower(200)
                .build();

        assertThrows(DomainValidationException.class, () -> engine.updatePrice(-1000));
    }
}