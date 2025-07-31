package br.com.dio.model;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static br.com.dio.model.BankService.INVESTMENT;

@Getter
@ToString
public class InvestmentWallet extends Wallet {

    private final Investment investment;   // Produto de investimento associado
    private final AccountWallet account;   // Conta de origem do dinheiro investido

    // Construtor: define a carteira como INVESTIMENTO
    public InvestmentWallet(final Investment investment, final AccountWallet account) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
    }

    // Atualiza o saldo com base nos rendimentos (percentual informado)
    public void updateEarnings(final BigDecimal percent) {
        if (percent.compareTo(BigDecimal.ZERO) <= 0) {
            return; // Nada a fazer se a taxa for zero ou negativa
        }

        // Calcula o rendimento proporcional ao saldo atual
        BigDecimal earnings = this.funds
                .multiply(percent)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        // Garante que a comparação seja feita com mesmo número de casas decimais
        if (earnings.compareTo(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)) > 0) {
            addFunds(earnings, "Rendimentos do investimento");
        }
    }
}
