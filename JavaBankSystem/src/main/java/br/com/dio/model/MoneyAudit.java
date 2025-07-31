package br.com.dio.model;

import java.time.OffsetDateTime;
import java.util.UUID;

// Registro imutável de uma transação financeira (auditoria)
public record MoneyAudit(
        UUID transactionId,      // ID único da transação
        BankService service,     // Tipo de serviço: conta ou investimento
        String description,      // Descrição da operação
        OffsetDateTime createdAt // Data/hora da operação
) {}
