package ru.cofffeen.mapper;

import ru.cofffeen.domain.entity.user.Client;
import ru.cofffeen.dto.ClientDTO;

public class ClientMapper {
    public static Client fromDTO(ClientDTO dto) {
        return Client.builder().
                name(dto.getName()).
                id(dto.getId()).
                birthDate(dto.getBirthDate()).
                phone(dto.getPhone()).
                email(dto.getEmail()).
                build();
    }

    public static ClientDTO toDTO(Client client) {
        return ClientDTO.builder().
                name(client.getName()).
                id(client.getId()).
                birthDate(client.getBirthDate()).
                phone(client.getPhone()).
                email(client.getEmail()).
                build();
    }
}
