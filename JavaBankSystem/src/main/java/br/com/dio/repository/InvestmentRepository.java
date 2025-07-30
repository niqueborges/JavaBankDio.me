package br.com.dio.repository;

import br.com.dio.exception.AccountWithInvestmentException;
import br.com.dio.exception.InvestmentNotFoundException;
import br.com.dio.exception.WalletNotFoundException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Investment;
import br.com.dio.model.InvestmentWallet;
import br.com.dio.model.Money;

import java.util.ArrayList;
import java.util.List;

import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;

public class InvestmentRepository {

    private long nextId = 0;
    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    public Investment create(final long tax, final long daysToRescue, final long initialFunds) {
        this.nextId++;
        var investment = new Investment(this.nextId, tax, initialFunds);
        investments.add(investment);
        return investment;
    }

    public InvestmentWallet initInvestment(final AccountWallet account, final long id) {
        var accountsInUse = wallets.stream()
                .map(InvestmentWallet::getAccount)
                .toList();

        if (accountsInUse.contains(account)) {
            throw new AccountWithInvestmentException("A conta " + account + " já possui um investimento.");
        }

        var investment = findById(id);
        checkFundsForTransaction(account, investment.initialFunds());

        var wallet = new InvestmentWallet(account, investment, investment.initialFunds());
        wallets.add(wallet);

        List<Money> money = account.reduceMoney(investment.initialFunds());
        wallet.addMoney(money, wallet.getService(), "Investimento inicial de R$ " + investment.initialFunds() + " na carteira de investimentos.");

        return wallet;
    }

    public InvestmentWallet deposit(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        List<Money> money = wallet.getAccount().reduceMoney(funds);
        wallet.addMoney(money, wallet.getService(), "Depósito de R$ " + funds + " na carteira de investimentos.");
        return wallet;
    }

    public InvestmentWallet withdraw(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, funds);

        List<Money> money = wallet.reduceMoney(funds);
        wallet.getAccount().addMoney(money, wallet.getService(), "Saque de R$ " + funds + " da carteira de investimentos.");

        if (wallet.getFunds() == 0) {
            wallets.remove(wallet);
        }

        return wallet;
    }

    public void updateAmount(final long percent) {
        wallets.forEach(w -> w.updateAmount(percent));
    }

    public Investment findById(final long id) {
        return investments.stream()
                .filter(investment -> investment.id() == id)
                .findFirst()
                .orElseThrow(() -> new InvestmentNotFoundException("O investimento com ID " + id + " não foi encontrado!"));
    }

    public InvestmentWallet findWalletByAccountPix(final String pix) {
        return wallets.stream()
                .filter(wallet -> wallet.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("A carteira de investimentos com chave PIX " + pix + " não foi encontrada!"));
    }

    public List<InvestmentWallet> listWallets() {
        return List.copyOf(wallets);
    }

    public List<Investment> list() {
        return List.copyOf(investments);
    }
}


