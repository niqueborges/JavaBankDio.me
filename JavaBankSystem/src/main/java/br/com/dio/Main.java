package br.com.dio;

import br.com.dio.model.AccountWallet;
import br.com.dio.model.Investment;
import br.com.dio.model.InvestmentWallet;
import br.com.dio.repository.InvestmentRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("💻 Projeto JavaBank iniciado com sucesso!");
        Scanner scanner = new Scanner(System.in);
        List<AccountWallet> contas = new ArrayList<>();
        InvestmentRepository investimentoRepo = new InvestmentRepository();
        InvestmentWallet carteiraInvestimento = null;

        while (true) {
            System.out.println("\n====== MENU BANCÁRIO ======");
            System.out.println("1. Criar uma conta");
            System.out.println("2. Criar um investimento");
            System.out.println("3. Fazer um investimento");
            System.out.println("4. Ver saldo");
            System.out.println("5. Depositar");
            System.out.println("6. Sacar");
            System.out.println("7. Ver extrato");
            System.out.println("8. Transferir entre contas");
            System.out.println("9. Sacar investimentos");
            System.out.println("10. Listar contas");
            System.out.println("11. Listar investimentos");
            System.out.println("12. Listar contas de investimento");
            System.out.println("13. Atualizar rendimentos");
            System.out.println("14. Histórico da conta");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // Limpa o buffer do scanner
                continue;
            }
            scanner.nextLine(); // limpar o buffer do nextInt()

            switch (opcao) {

                case 1 -> {
                    System.out.print("Digite o valor inicial (ou 0): ");
                    BigDecimal valorInicial = scanner.nextBigDecimal();
                    scanner.nextLine();

                    System.out.print("Digite uma chave PIX para associar à conta: ");
                    String pix = scanner.nextLine();
                    List<String> pixList = new ArrayList<>();
                    pixList.add(pix);

                    AccountWallet novaConta = new AccountWallet(valorInicial, pixList);
                    contas.add(novaConta);
                    System.out.println("✅ Conta criada com sucesso! ID da conta: " + (contas.size() - 1));
                }

                case 2 -> {
                    System.out.print("Digite o rendimento (% ao mês): ");
                    long rendimento = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Digite o prazo de resgate em dias: ");
                    long diasParaResgate = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Digite o valor mínimo de aplicação: ");
                    BigDecimal valorMinimo = scanner.nextBigDecimal();
                    scanner.nextLine();

                    Investment novoInvestimento = investimentoRepo.create(rendimento, diasParaResgate, valorMinimo);
                    System.out.println("✅ Investimento criado com ID: " + novoInvestimento.id());
                }

                case 3 -> {
                    if (contas.isEmpty()) {
                        System.out.println("⚠️ Crie uma conta antes de investir.");
                    } else {
                        System.out.print("Digite o ID da conta que deseja usar: ");
                        int contaId = scanner.nextInt();
                        scanner.nextLine();

                        if (contaId < 0 || contaId >= contas.size()) {
                            System.out.println("❌ Conta inválida.");
                            break;
                        }

                        AccountWallet conta = contas.get(contaId);

                        System.out.print("Digite o ID do investimento que deseja aplicar: ");
                        long idInvestimento = scanner.nextLong();
                        scanner.nextLine();

                        try {
                            carteiraInvestimento = investimentoRepo.initInvestment(conta, idInvestimento);
                            System.out.println("✅ Investimento iniciado com sucesso!");
                        } catch (Exception e) {
                            System.out.println("❌ Erro ao iniciar investimento: " + e.getMessage());
                        }
                    }
                }

                case 4 -> {
                    if (contas.isEmpty()) {
                        System.out.println("⚠️ Nenhuma conta criada ainda.");
                    } else {
                        System.out.print("Digite o ID da conta: ");
                        int contaId = scanner.nextInt();
                        scanner.nextLine();

                        if (contaId < 0 || contaId >= contas.size()) {
                            System.out.println("❌ Conta inválida.");
                        } else {
                            AccountWallet conta = contas.get(contaId);
                            System.out.println("💰 Saldo atual: R$ " + conta.getFunds());
                        }
                    }
                }

                case 5 -> {
                    if (contas.isEmpty()) {
                        System.out.println("⚠️ Crie uma conta antes de depositar.");
                    } else {
                        System.out.print("Digite o ID da conta para depósito: ");
                        int contaId = scanner.nextInt();
                        scanner.nextLine();

                        if (contaId < 0 || contaId >= contas.size()) {
                            System.out.println("❌ Conta inválida.");
                            break;
                        }

                        AccountWallet conta = contas.get(contaId);

                        System.out.print("Digite o valor a depositar: ");
                        BigDecimal deposito = scanner.nextBigDecimal();
                        scanner.nextLine();

                        System.out.print("Descrição do depósito: ");
                        String desc = scanner.nextLine();

                        conta.addFunds(deposito, desc);
                        System.out.println("✅ Depósito realizado.");
                    }
                }

                case 6 -> {
                    if (contas.isEmpty()) {
                        System.out.println("⚠️ Crie uma conta antes de sacar.");
                    } else {
                        System.out.print("Digite o ID da conta: ");
                        int contaId = scanner.nextInt();
                        scanner.nextLine();

                        if (contaId < 0 || contaId >= contas.size()) {
                            System.out.println("❌ Conta inválida.");
                            break;
                        }

                        AccountWallet conta = contas.get(contaId);

                        System.out.print("Digite o valor a sacar: ");
                        BigDecimal saque = scanner.nextBigDecimal();
                        scanner.nextLine();

                        System.out.print("Descrição do saque: ");
                        String descSaque = scanner.nextLine();

                        try {
                            conta.reduceFunds(saque, descSaque);
                            System.out.println("✅ Saque realizado com sucesso.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("⚠️ Erro ao sacar: " + e.getMessage());
                        }
                    }
                }

                case 7 -> {
                    if (contas.isEmpty()) {
                        System.out.println("⚠️ Nenhuma conta criada.");
                    } else {
                        System.out.print("Digite o ID da conta: ");
                        int contaId = scanner.nextInt();
                        scanner.nextLine();

                        if (contaId < 0 || contaId >= contas.size()) {
                            System.out.println("❌ Conta inválida.");
                        } else {
                            AccountWallet conta = contas.get(contaId);
                            System.out.println("📄 Extrato da conta:");
                            conta.getFinancialTransactions().forEach(System.out::println);
                        }
                    }
                }

                case 8 -> {
                    if (contas.size() < 2) {
                        System.out.println("⚠️ São necessárias pelo menos duas contas para fazer uma transferência.");
                        break;
                    }

                    System.out.println("Contas disponíveis:");
                    for (int i = 0; i < contas.size(); i++) {
                        System.out.println(i + " - Conta com saldo: R$ " + contas.get(i).getFunds());
                    }

                    try {
                        System.out.print("Informe o número da conta de origem: ");
                        int origemIndex = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Informe o número da conta de destino: ");
                        int destinoIndex = scanner.nextInt();
                        scanner.nextLine();

                        if (origemIndex == destinoIndex) {
                            System.out.println("❌ Conta de origem e destino não podem ser a mesma.");
                            break;
                        }

                        AccountWallet origem = contas.get(origemIndex);
                        AccountWallet destino = contas.get(destinoIndex);

                        System.out.print("Digite o valor a ser transferido: ");
                        BigDecimal valorTransferencia = scanner.nextBigDecimal();
                        scanner.nextLine();

                        System.out.print("Descrição da transferência: ");
                        String descricao = scanner.nextLine();

                        origem.reduceFunds(valorTransferencia, "Transferência para conta " + destinoIndex + ": " + descricao);
                        destino.addFunds(valorTransferencia, "Recebido de conta " + origemIndex + ": " + descricao);

                        System.out.println("✅ Transferência realizada com sucesso!");

                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("❌ Número de conta inválido.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Erro ao transferir: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Entrada inválida.");
                        scanner.nextLine();
                    }
                }

                case 9 -> {
                    System.out.println("📌 Funcionalidade de saque de investimentos ainda não implementada.");
                }

                case 10 -> {
                    System.out.println("📌 Listagem de contas ainda não implementada.");
                }

                case 11 -> {
                    System.out.println("📌 Listagem de investimentos ainda não implementada.");
                }

                case 12 -> {
                    System.out.println("📌 Listagem de contas com investimento ainda não implementada.");
                }

                case 13 -> {
                    System.out.println("📌 Atualização de rendimentos ainda não implementada.");
                }

                case 14 -> {
                    System.out.println("📌 Histórico de conta ainda não implementado.");
                }

                case 0 -> {
                    System.out.println("👋 Encerrando...");
                    scanner.close();
                    return;
                }

                default ->
                    System.out.println("❌ Opção inválida. Tente novamente.");
            }
        }
    }
}
