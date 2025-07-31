# 💸 Sistema Bancário com Carteiras Digitais

Este projeto é uma aplicação Java orientada a objetos, desenvolvida com o objetivo de consolidar conceitos fundamentais da programação orientada a objetos (POO), como herança, encapsulamento, polimorfismo, abstração e reuso de código. A aplicação simula um sistema bancário básico que permite a criação de contas, depósitos, saques, transferências via PIX, criação de investimentos e acompanhamento de histórico de transações.

## 🧠 Visão Geral

O sistema é orientado a objetos e simula operações bancárias comuns com foco em modularidade e extensibilidade. As carteiras representam diferentes formas de gerenciar o dinheiro dos usuários.

### 🔧 Funcionalidades

- ✅ Criação de contas bancárias com múltiplas chaves Pix
- 💰 Depósito e saque por chave Pix
- 📊 Auditoria de transações
- 📈 Suporte a carteiras de investimento
- 🏦 Realização e resgate de investimentos
- 🔄 Atualização de rendimentos para carteiras de investimento
- 📝 Listagem de contas bancárias e de investimento
- 📜 Exibição de extrato e histórico de transações da conta
- 🔒 Exceções personalizadas como `AccountNotFoundException`

## 🏗️ Estrutura do Projeto

```

src/
├── br.com.dio.exception/
│   ├── AccountNotFoundException.java
│   ├── AccountWithInvestmentException.java
│   ├── InvestmentNotFoundException.java
│   ├── NoFundsEnoughException.java
│   ├── PixInUseException.java
│   └── WalletNotFoundException.java
├── br.com.dio.model/
│   ├── AccountWallet.java
│   ├── BankService.java
│   ├── Investment.java
│   ├── InvestmentWallet.java
│   ├── Money.java
│   └── MoneyAudit.java
├── br.com.dio.repository/
│   ├── AccountRepository.java
│   ├── CommonsRepository.java
│   └── InvestmentRepository.java
└── br.com.dio/
└── Main.java

````

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Gradle
- Lombok
- JUnit (para testes futuros)
- IntelliJ IDEA

## 🚀 Como Executar

1.  Clone o repositório:
    ```bash
    git clone [https://github.com/niqueborges/JavaBankDio.me.git]
    ```
2.  Abra no IntelliJ IDEA (ou qualquer IDE Java)
3.  Navegue até o diretório `JavaBankSystem`.
4.  Compile e execute a classe principal `br.com.dio.Main.java`. Você pode usar o Gradle para isso:
    ```bash
    ./gradlew run
    ```

📌 **TODO**

-   Adicionar testes unitários com JUnit
-   Implementar carteira empresarial
-   Explorar opções para uma interface gráfica (web ou desktop)

🧑‍💻 **Autor**

Desenvolvido por Monique Borges

📧 contato: [seu-email@email.com]

🔗 GitHub: github.com/niqueborges
````