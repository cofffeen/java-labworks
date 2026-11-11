import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cofffeen.domain.entity.user.Client;
import ru.cofffeen.inmemory.InMemoryRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {
    private InMemoryRepository<Client, UUID> repository;
    private Client client;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRepository<>();
        clientId = UUID.randomUUID();
        client = Client.builder()
                .id(clientId)
                .name("Test client")
                .build();
    }

    @Test
    void add_ShouldAddEntity() {
        Client added = repository.add(client, clientId);

        assertEquals(client, added);
        assertEquals(client, repository.getById(clientId));
    }

    @Test
    void getById_WithExistingId_ShouldReturnEntity() {
        repository.add(client, clientId);

        Client found = repository.getById(clientId);
        assertEquals(client, found);
    }

    @Test
    void getById_WithNonExistingId_ShouldReturnNull() {
        Client found = repository.getById(UUID.randomUUID());
        assertNull(found);
    }

    @Test
    void update_WithNonExistingEntity_ShouldReturnNull() {
        Client result = repository.update(client);
        assertNull(result);
    }

    @Test
    void remove_ShouldDeleteEntity() {
        repository.add(client, clientId);
        repository.remove(client);

        assertNull(repository.getById(clientId));
    }

    @Test
    void getAll_ShouldReturnAllEntities() {
        Client client2 = Client.builder().id(UUID.randomUUID()).name("Test").build();

        repository.add(client, clientId);
        repository.add(client2, client2.getId());

        List<Client> all = repository.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(client));
        assertTrue(all.contains(client2));
    }
}