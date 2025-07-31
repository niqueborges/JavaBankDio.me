package br.com.dio.repository;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.PixInUseException;
import br.com.dio.model.AccountWallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;

public class AccountRepository {

    private final List<AccountWallet> accounts;

    public AccountRepository() {
        this.accounts = new ArrayList<>();
    }

    // Cria uma nova conta bancária após validar que as chaves PIX não estão em uso
    public AccountWallet create(final List<String> pix, final BigDecimal initialFunds) {
        Set<String> pixInUse = accounts.stream()
                .flatMap(a -> a.getPixKeys().stream())
                .collect(Collectors.toSet());

        for (final var p : pix) {
            if (pixInUse.contains(p)) {
                throw new PixInUseException("A chave PIX " + p + " já está em uso.");
            }
        }

        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    // Realiza depósito via chave PIX
    public void deposit(final String pix, final BigDecimal amount) {
        var target = findByPix(pix);
        target.addFunds(amount, "Depósito via PIX: " + pix);
    }

    // Realiza saque da conta
    public void withdraw(final String pix, final BigDecimal amount) {
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceFunds(amount, "Saque da conta");
    }

    // Realiza transferência entre duas contas usando PIX
    public void transferMoney(final String sourcePix, final String targetPix, final BigDecimal amount) {
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);

        var target = findByPix(targetPix);

        source.reduceFunds(amount, "Transferência PIX para: " + targetPix);
        target.addFunds(amount, "Transferência PIX recebida de: " + sourcePix);
    }

    // Busca uma conta com base na chave PIX
    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(a -> a.getPixKeys().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("A conta com a chave PIX " + pix + " não foi encontrada."));
    }
}
