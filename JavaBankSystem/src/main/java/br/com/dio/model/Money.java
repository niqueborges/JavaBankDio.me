package br.com.dio.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Money {

    // Armazena o histórico de movimentações (auditorias) desse dinheiro
    private final List<MoneyAudit> history = new ArrayList<>();

    // Construtor: adiciona a primeira entrada no histórico
    public Money(final MoneyAudit history) {
        this.history.add(history);
    }

    // Permite adicionar uma nova entrada de histórico
    public void addHistory(final MoneyAudit history) {
        this.history.add(history);
    }
}

