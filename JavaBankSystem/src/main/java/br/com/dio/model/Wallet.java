package br.com.dio.model;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
public abstract class Wallet {

    private final BankService service;               // Tipo de carteira (conta ou investimento)
    protected BigDecimal funds;                     // Saldo atual da carteira
    protected final List<MoneyAudit> financialTransactions; // Histórico de transações

    // Construtor: inicia a carteira com saldo zero e histórico vazio
    public Wallet(final BankService service) {
        this.service = service;
        this.funds = BigDecimal.ZERO;
        this.financialTransactions = new ArrayList<>();
    }

    // Adiciona valor à carteira e registra a transação
    public void addFunds(final BigDecimal amount, final String description) {
        this.funds = this.funds.add(amount);
        this.financialTransactions.add(createAudit(description));
    }

    // Remove valor da carteira e registra a transação
    public void reduceFunds(final BigDecimal amount, final String description) {
        this.funds = this.funds.subtract(amount);
        this.financialTransactions.add(createAudit(description));
    }

    // Cria um registro de auditoria para cada movimentação
    // Método auxiliar para registrar auditoria de uma transação financeira.
    // Pode ser sobrescrito por subclasses no futuro, se desejado.
    protected MoneyAudit createAudit(final String description) {
        return new MoneyAudit(UUID.randomUUID(), this.service, description, OffsetDateTime.now());
    }

}

