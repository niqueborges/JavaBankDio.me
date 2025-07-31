package br.com.dio.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

import static br.com.dio.model.BankService.ACCOUNT;

@Getter
public class AccountWallet extends Wallet {

    private final List<String> pixKeys;

    public AccountWallet(final List<String> pixKeys) {
        super(ACCOUNT);
        this.pixKeys = pixKeys;
    }

    public AccountWallet(final BigDecimal initialAmount, final List<String> pixKeys) {
        super(ACCOUNT);
        this.pixKeys = pixKeys;

        if (initialAmount.compareTo(BigDecimal.ZERO) > 0) {
            addFunds(initialAmount, "Depósito inicial na criação da conta");
        }
    }

    // Método legado não utilizado no projeto atual. Mantido apenas para fins de estudo.
    @Deprecated
    public void addMoney(final long amount, final String description) {
        // Método antigo baseado em long e Money — substituído por addFunds(BigDecimal)
        // Remover futuramente se não for mais necessário.
    }

    /**
     * Realiza um saque, verificando se há saldo suficiente.
     *
     * @param amount Valor a ser sacado.
     * @param description Descrição da transação.
     * @throws IllegalArgumentException se o valor for inválido ou saldo insuficiente.
     */
    public void withdraw(final BigDecimal amount, final String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor de saque deve ser positivo.");
        }

        if (this.funds.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para saque.");
        }

        reduceFunds(amount, description);
    }

    /**
     * Exibe o extrato da conta com todas as transações realizadas.
     */
    public void printStatement() {
        System.out.println("\n📄 Extrato da Conta:");
        if (financialTransactions.isEmpty()) {
            System.out.println("Nenhuma transação registrada.");
        } else {
            for (var audit : financialTransactions) {
                System.out.println("- " + audit.createdAt() + " | " + audit.description());
            }
        }
        System.out.println("Saldo atual: R$ " + funds);
    }

}
