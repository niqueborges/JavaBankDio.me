package br.com.dio.repository;

import br.com.dio.exception.AccountWithInvestmentException;
import br.com.dio.exception.InvestmentNotFoundException;
import br.com.dio.exception.WalletNotFoundException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Investment;
import br.com.dio.model.InvestmentWallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;

public class InvestmentRepository {

    private long nextId = 0;
    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    // Cria um novo produto de investimento
    public Investment create(final long tax, final long daysToRescue, final BigDecimal initialFunds){
        this.nextId++;
        var investment = new Investment(this.nextId, tax, daysToRescue, initialFunds);
        investments.add(investment);
        return investment;
    }

    // Inicializa uma carteira de investimento com base na conta e produto
    public InvestmentWallet initInvestment(final AccountWallet account, final long id) {
        boolean accountInUse = wallets.stream().anyMatch(w -> w.getAccount().equals(account));
        if (accountInUse) {
            throw new AccountWithInvestmentException("A conta já possui um investimento ativo.");
        }

        var investment = findById(id);
        checkFundsForTransaction(account, investment.initialFunds());

        var wallet = new InvestmentWallet(investment, account);

        account.reduceFunds(investment.initialFunds(), "Aplicação no investimento ID: " + investment.id());
        wallet.addFunds(investment.initialFunds(), "Investimento inicial de R$ " + investment.initialFunds());

        wallets.add(wallet);
        return wallet;
    }

    // Realiza depósito em carteira de investimento a partir da conta associada
    public void deposit(final String pix, final BigDecimal amount) {
        var wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet.getAccount(), amount);

        wallet.getAccount().reduceFunds(amount, "Depósito na carteira de investimentos");
        wallet.addFunds(amount, "Depósito de R$ " + amount);
    }

    // Realiza resgate (saque) da carteira de investimento para a conta associada
    public void withdraw(final String pix, final BigDecimal amount) {
        var wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, amount);

        wallet.reduceFunds(amount, "Resgate da carteira de investimentos");
        wallet.getAccount().addFunds(amount, "Valor resgatado do investimento");

        // Se a carteira ficar com saldo zero, ela é removida
        if (wallet.getFunds().compareTo(BigDecimal.ZERO) == 0) {
            wallets.remove(wallet);
        }
    }

    // Atualiza o rendimento de todas as carteiras de investimento
    public void updateAllEarnings(final BigDecimal percent) {
        wallets.forEach(w -> w.updateEarnings(percent));
    }

    // Busca um investimento pelo ID
    public Investment findById(final long id) {
        return investments.stream()
                .filter(investment -> investment.id() == id)
                .findFirst()
                .orElseThrow(() -> new InvestmentNotFoundException("O investimento com ID " + id + " não foi encontrado!"));
    }

    // Busca uma carteira de investimento com base na chave PIX da conta associada
    public InvestmentWallet findWalletByAccountPix(final String pix) {
        return wallets.stream()
                .filter(wallet -> wallet.getAccount().getPixKeys().contains(pix))
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("A carteira de investimentos com chave PIX " + pix + " não foi encontrada!"));
    }

    // Lista todas as carteiras de investimento ativas
    public List<InvestmentWallet> listWallets() {
        return List.copyOf(wallets);
    }

    // Lista todos os produtos de investimento disponíveis
    public List<Investment> list() {
        return List.copyOf(investments);
    }
}


