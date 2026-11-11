package ru.cofffeen.dto.mapper;

import org.springframework.stereotype.Component;
import ru.cofffeen.dto.response.OperationResponse;
import ru.cofffeen.model.Operation;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OperationDtoMapper {

    public OperationResponse toResponse(Operation domain) {
        if (domain == null) return null;

        return OperationResponse.builder()
                .id(domain.getId())
                .type(domain.getType())
                .amount(domain.getAmount())
                .commission(domain.getCommission())
                .timestamp(domain.getTimestamp())
                .accountId(domain.getAccount() != null ? domain.getAccount().getId() : null)
                .relatedAccountId(domain.getRelatedAccount() != null ? domain.getRelatedAccount().getId() : null)
                .build();
    }

    public List<OperationResponse> toResponseList(List<Operation> domains) {
        if (domains == null) return List.of();
        return domains.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}