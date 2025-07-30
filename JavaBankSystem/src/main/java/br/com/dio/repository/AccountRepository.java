package br.com.dio.repository;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.exception.PixInUseException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Money;

import java.util.ArrayList;
import java.util.List;

import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;

public class AccountRepository {

    private final List<AccountWallet> accounts;

    public AccountRepository() {
        this.accounts = new ArrayList<>();
    }

    public AccountWallet create(final List<String> pixKeys, final long initialFunds) {
        // Verifica se alguma chave PIX já está em uso
        List<String> pixInUse = accounts.stream()
                .flatMap(account -> account.getPix().stream())
                .toList();

        for (String pix : pixKeys) {
            if (pixInUse.contains(pix)) {
                throw new PixInUseException("A chave PIX " + pix + " já está em uso.");
            }
        }

        AccountWallet newAccount = new AccountWallet(initialFunds, pixKeys);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long amount) {
        AccountWallet target = findByPix(pix);
        target.addMoney(amount, "Depósito");
    }

    public void withdraw(final String pix, final long amount) {
        AccountWallet source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        AccountWallet source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);

        AccountWallet target = findByPix(targetPix);
        String message = "Transferência de R$ " + amount + " de " + sourcePix + " para " + targetPix;

        List<Money> transferred = source.reduceMoney(amount);
        target.addMoney(transferred, source.getService(), message);
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(account -> account.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("A conta com a chave PIX " + pix + " não foi encontrada."));
    }
}
