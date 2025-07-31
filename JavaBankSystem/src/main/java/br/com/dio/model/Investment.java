package br.com.dio.model;

import java.math.BigDecimal;

// Representa um produto de investimento disponível no sistema
public record Investment(
        long id,              // ID único do investimento
        long tax,             // Taxa de rendimento (percentual)
        long daysToRescue,    // Dias mínimos para resgatar
        BigDecimal initialFunds     // Valor inicial exigido
) {}
