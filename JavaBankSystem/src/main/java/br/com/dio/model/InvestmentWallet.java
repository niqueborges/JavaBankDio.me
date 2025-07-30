package br.com.dio.model;

import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.dio.model.BankService.INVESTMENT;

@Getter
@ToString
public class InvestmentWallet extends Wallet {

    private final Investment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getService(), "Invested amount");
    }

    public void updateEarnings(final long percent) {
        long earnings = Math.round(getFunds() * (percent / 100.0));
        var audit = new MoneyAudit(UUID.randomUUID(), getService(), "Interest earned", OffsetDateTime.now());
        List<Money> gains = Stream.generate(() -> new Money(audit))
                .limit(earnings)
                .toList();
        this.money.addAll(gains);
    }
}
