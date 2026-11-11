import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cofffeen.domain.entity.car.Car;
import ru.cofffeen.domain.entity.car.DriveType;
import ru.cofffeen.domain.entity.car.part.*;
import ru.cofffeen.domain.entity.car.part.enums.BodyType;
import ru.cofffeen.domain.entity.car.part.enums.FuelType;
import ru.cofffeen.domain.entity.car.part.enums.TransmissionType;
import ru.cofffeen.service.filter.CarFilter;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CarFilterTest {
    private Car car;

    @BeforeEach
    void setUp() {
        Body body = Body.builder()
                .bodyType(BodyType.SEDAN)
                .color("Red")
                .compatibleModels(Set.of("Test model"))
                .build();

        Engine engine = Engine.builder()
                .fuelType(FuelType.PETROL)
                .horsePower(300)
                .compatibleModels(Set.of("Test model"))
                .build();

        Interior interior = Interior.builder()
                .color("Black")
                .material("Leather")
                .compatibleModels(Set.of("Test model"))
                .build();

        Transmission transmission = Transmission.builder()
                .transmissionType(TransmissionType.AUTOMATIC)
                .gears(8)
                .compatibleModels(Set.of("Test model"))
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
                .transmission(transmission)
                .build();
    }

    @Test
    void match_WithNoFilters_ShouldReturnTrue() {
        CarFilter filter = CarFilter.builder().build();
        assertTrue(filter.match(car));
    }

    @Test
    void match_WithBrandFilter_ShouldMatch() {
        CarFilter filter = CarFilter.builder().brand("Test").build();
        assertTrue(filter.match(car));

        CarFilter wrongFilter = CarFilter.builder().brand("Random").build();
        assertFalse(wrongFilter.match(car));
    }

    @Test
    void match_WithBodyTypeFilter_ShouldMatch() {
        CarFilter filter = CarFilter.builder().bodyType(BodyType.SEDAN).build();
        assertTrue(filter.match(car));

        CarFilter wrongFilter = CarFilter.builder().bodyType(BodyType.COUPE).build();
        assertFalse(wrongFilter.match(car));
    }

    @Test
    void match_WithFuelTypeFilter_ShouldMatch() {
        CarFilter filter = CarFilter.builder().fuelType(FuelType.PETROL).build();
        assertTrue(filter.match(car));

        CarFilter wrongFilter = CarFilter.builder().fuelType(FuelType.DIESEL).build();
        assertFalse(wrongFilter.match(car));
    }

    @Test
    void match_WithPriceRangeFilter_ShouldMatch() {
        CarFilter filter = CarFilter.builder()
                .minBasePrice(40000)
                .maxBasePrice(60000)
                .build();
        assertTrue(filter.match(car));

        CarFilter wrongFilter = CarFilter.builder()
                .minBasePrice(60000)
                .maxBasePrice(80000)
                .build();
        assertFalse(wrongFilter.match(car));
    }

    @Test
    void match_WithPowerRangeFilter_ShouldMatch() {
        CarFilter filter = CarFilter.builder()
                .minPower(250)
                .maxPower(350)
                .build();
        assertTrue(filter.match(car));

        CarFilter wrongFilter = CarFilter.builder()
                .minPower(400)
                .maxPower(500)
                .build();
        assertFalse(wrongFilter.match(car));
    }

    @Test
    void match_WithMultipleFilters_ShouldMatchOnlyWhenAllPass() {
        CarFilter filter = CarFilter.builder()
                .brand("Test")
                .bodyType(BodyType.SEDAN)
                .fuelType(FuelType.PETROL)
                .build();
        assertTrue(filter.match(car));

        CarFilter wrongFilter = CarFilter.builder()
                .brand("Test")
                .bodyType(BodyType.COUPE)
                .build();
        assertFalse(wrongFilter.match(car));
    }
}