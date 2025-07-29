package br.com.dio.model;

import java.util.List;

import static br.com.dio.model.BankService.ACCOUNT;

public class AccountWallet extends Wallet {

    private final List<String> pixKeys;

    public AccountWallet(final List<String> pixKeys) {
        super(ACCOUNT);
        this.pixKeys = pixKeys;
    }

    public AccountWallet(final long initialAmount, final List<String> pixKeys) {
        super(ACCOUNT);
        this.pixKeys = pixKeys;
        addMoney(initialAmount, "Initial deposit on account creation");
    }

    public void addMoney(final long amount, final String description) {
        List<Money> generated = generateMoney(amount, description);
        this.money.addAll(generated);
    }
}