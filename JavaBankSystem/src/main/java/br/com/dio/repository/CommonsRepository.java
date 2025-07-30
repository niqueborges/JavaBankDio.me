package br.com.dio.repository;

import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.model.Money;
import br.com.dio.model.MoneyAudit;
import br.com.dio.model.Wallet;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.dio.model.BankService.ACCOUNT;
import static lombok.AccessLevel.PRIVATE;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class CommonsRepository {

    /**
     * Verifica se a carteira tem fundos suficientes para uma transação.
     */
    public static void checkFundsForTransaction(final Wallet source, final long amount) {
        if (source.getFunds() < amount) {
            throw new NoFundsEnoughException("Sua conta não tem dinheiro o suficiente para realizar essa transação.");
        }
    }

    /**
     * Gera uma lista de instâncias Money representando os fundos.
     */
    public static List<Money> generateMoney(final UUID transactionID, final long funds, final String description) {
        var audit = new MoneyAudit(transactionID, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(audit))
                .limit(funds)
                .toList();
    }
}


