package br.com.dio.model;

public record Investment(
        long id,
        long interestRate,
        long daysToRescue,
        long initialFunds
) {}