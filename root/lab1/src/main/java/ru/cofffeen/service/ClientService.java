package ru.cofffeen.service;

import ru.cofffeen.domain.entity.user.Client;
import ru.cofffeen.dto.ClientDTO;
import ru.cofffeen.mapper.ClientMapper;

import java.util.UUID;

public class ClientService extends BaseService<Client> {
    public ClientDTO create(String name, String birthDate, String phone, String email) {
        Client client = Client.builder()
                .id(UUID.randomUUID())
                .name(name)
                .birthDate(birthDate)
                .phone(phone)
                .email(email)
                .build();

        return ClientMapper.toDTO(repository.add(client, client.getId()));
    }

    public ClientDTO update(UUID id, String name, String birthDate, String phone, String email) {
        var client = Client.builder()
                .id(id)
                .name(name)
                .birthDate(birthDate)
                .phone(phone)
                .email(email)
                . build();

        return ClientMapper.toDTO(repository.update(client));
    }
}
