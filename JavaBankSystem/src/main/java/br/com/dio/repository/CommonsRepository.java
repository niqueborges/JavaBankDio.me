package br.com.dio.repository;

import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.model.Money;
import br.com.dio.model.MoneyAudit;
import br.com.dio.model.Wallet;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import java.math.BigDecimal;

import static br.com.dio.model.BankService.ACCOUNT;
import static lombok.AccessLevel.PRIVATE;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class CommonsRepository {

    public static void checkFundsForTransaction(final Wallet source, final BigDecimal amount) {
        if (source.getFunds().compareTo(amount) < 0) {
            throw new NoFundsEnoughException("Sua conta não tem saldo o suficiente para realizar essa transação.");
        }
    }

    /**
     * Método legado — atualmente não utilizado no projeto.
     * Usado anteriormente para simular dinheiro individualizado (com histórico).
     */
    @Deprecated
    public static List<Money> generateMoney(final UUID transactionID, final long funds, final String description) {
        var audit = new MoneyAudit(transactionID, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(audit))
                .limit(funds)
                .toList();
    }
}

