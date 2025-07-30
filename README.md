# 💸 Sistema Bancário com Carteiras Digitais

Este projeto é uma aplicação Java orientada a objetos, desenvolvida com o objetivo de consolidar conceitos fundamentais da programação orientada a objetos (POO), como herança, encapsulamento, polimorfismo, abstração e reuso de código. A aplicação simula um sistema bancário básico que permite a criação de contas, depósitos, saques, transferências via PIX, criação de investimentos e acompanhamento de histórico de transações.

## 🧠 Visão Geral

O sistema é orientado a objetos e simula operações bancárias comuns com foco em modularidade e extensibilidade. As carteiras representam diferentes formas de gerenciar o dinheiro dos usuários.

### 🔧 Funcionalidades

- ✅ Criação de contas bancárias com múltiplas chaves Pix
- 💰 Depósito e saque por chave Pix
- 📊 Auditoria de transações
- 📈 Suporte a carteiras de investimento
- 🔒 Exceções personalizadas como `AccountNotFoundException`

## 🏗️ Estrutura do Projeto

src/
├── br.com.dio.model/
│ ├── AccountWallet.java
│ ├── InvestmentWallet.java
│ ├── Money.java
│ └── MoneyAudit.java
├── br.com.dio.service/
│ └── BankService.java
├── br.com.dio.repository/
│ └── AccountRepository.java
├── br.com.dio.exception/
│ └── AccountNotFoundException.java


## 🛠️ Tecnologias Utilizadas

- Java 17+
- Gradle
- Lombok
- JUnit (para testes futuros)
- IntelliJ IDEA

## 🚀 Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
Abra no IntelliJ IDEA (ou qualquer IDE Java)

Compile e execute a classe principal ou scripts de teste

📌 TODO
 Criar interface para simulação via terminal

 Adicionar testes unitários com JUnit

 Implementar carteira empresarial

🧑‍💻 Autor
Desenvolvido por Monique Borges
📧 contato: [seu-email@email.com]
🔗 GitHub: github.com/niqueborges
