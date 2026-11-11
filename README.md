# Projeto Final de Design Patterns — Oficina Mecânica

Sistema de console para uma oficina mecânica: abertura de ordens de serviço,
lançamento de itens (peças, serviços avulsos e pacotes de revisão) e cálculo do
orçamento conforme a regra de cobrança aplicada (padrão, frota ou garantia).

## Integrantes e contribuições

| Integrante | Contribuição |
|---|---|
| Bruno Monteiro Bonifácio | Estrutura inicial do repositório (pom.xml, .gitignore, entrypoint `Main`), organização do README e merge do PR #1 |
| Gianluca Andrade Silvestre | Parte 1 (branch `feature/strategy-orcamento`): entidades `Veiculo` e `OrdemServico`, regras de orçamento do Strategy e fluxo de console de ponta a ponta |
| Luís Filipe Damiani Colombo | *(a preencher)* |
| Roger Balcevicz | Pattern Composite completo (interface `Item`, `Peca`, `MaoDeObra`, `ItemComposto` e `Formatacao` — branch `feature/composite`, PR #1) |

## Descrição do sistema

A aplicação roda inteiramente em memória (sem banco de dados) e segue o fluxo:

1. **Abrir ordem de serviço** — informa placa, marca, modelo e ano do veículo.
2. **Lançar itens** — peças (quantidade × preço unitário), serviços de mão de obra
   (horas × valor/hora) e o pacote de revisão completa, que agrupa vários itens.
3. **Escolher a regra de orçamento** — Padrão, Frota ou Garantia.
4. **Exibir orçamento detalhado** — mostra a árvore de itens, os totais de peças e de mão de obra, a regra aplicada e o total final.

## Tecnologias e pré-requisitos

* **Linguagem:** Java 25 (JDK)
* **Build:** Maven 3.9+ (ou IDE como IntelliJ IDEA com Maven embutido)
* Sem banco de dados e sem dependências externas — apenas a biblioteca padrão.

## Como executar

Com Maven instalado:

```bash
mvn -q compile
java -cp target/classes io.github.brunomonteirobonifacio.designpatternsoficina.Main
```

Alternativa: abra o projeto no IntelliJ IDEA e execute a classe `Main`.

## Exemplo de uso

```
----- MENU -----
1 - Abrir ordem de serviço
2 - Adicionar peça
3 - Adicionar serviço de mão de obra
4 - Adicionar pacote de revisão completa
5 - Escolher regra de orçamento
6 - Exibir orçamento detalhado
0 - Sair
Opção: 1
Placa do veículo: ABC-1234
Marca: Fiat
Modelo: Argo
Ano: 2020
Ordem OS-001 aberta para o veículo Fiat Argo 2020 (placa ABC-1234).

Opção: 2
Descrição da peça: Pastilha de freio dianteira
Preço unitário: 180
Quantidade: 2
Peça adicionada à ordem OS-001.

Opção: 3
Descrição do serviço: Reparo do freio
Horas trabalhadas (ex.: 1.5): 1.5
Valor por hora: 100
Serviço adicionado à ordem OS-001.

Opção: 4
Pacote de revisão completa adicionado à ordem OS-001.

Opção: 5
Regras disponíveis:
1 - Padrão: peças + mão de obra
2 - Frota: 10% de desconto na mão de obra
3 - Garantia: apenas mão de obra
Regra: 2
Regra definida: Frota (10% de desconto na mão de obra)

Opção: 6
========= ORÇAMENTO =========
Ordem: OS-001
Veículo: Fiat Argo 2020 (placa ABC-1234)
Itens:
  - Peça: Pastilha de freio dianteira (2 x R$ 180,00) = R$ 360,00
  - Mão de obra: Reparo do freio (1,5 h x R$ 100,00) = R$ 150,00
  + Pacote — Revisão completa (peças R$ 145,00 | mão de obra R$ 240,00)
    - Peça: Óleo de motor e filtro (1 x R$ 145,00) = R$ 145,00
    - Mão de obra: Troca de óleo e filtro (1 h x R$ 80,00) = R$ 80,00
    - Mão de obra: Revisão do sistema de freios (1,5 h x R$ 60,00) = R$ 90,00
    - Mão de obra: Verificação da suspensão (1 h x R$ 70,00) = R$ 70,00

Total de peças: R$ 505,00
Total de mão de obra: R$ 390,00
Regra aplicada: Frota (10% de desconto na mão de obra)
TOTAL: R$ 856,00
=============================
```

## Patterns utilizados

### Strategy — regras de orçamento

* **Problema resolvido:** a mesma ordem de serviço precisa ser orçada por regras de
  cobrança diferentes (peça + mão de obra; frota com 10% de desconto na mão de obra;
  garantia cobrando só a mão de obra), e essa regra pode mudar a qualquer momento
  sem alterar a `OrdemServico` nem o restante do fluxo.
* **Classes envolvidas:**
  * `strategy.RegraOrcamento` (interface) — contrato `calcular(valorPecas, valorMaoDeObra)`.
  * `strategy.OrcamentoPadrao` — peças + mão de obra.
  * `strategy.OrcamentoFrota` — peças + 90% da mão de obra.
  * `strategy.OrcamentoGarantia` — apenas a mão de obra.
  * Contexto: `model.OrdemServico` mantém a regra vigente (`definirRegra`) e delega
    o cálculo do total a ela (`calcularTotal`).
  * Ponto de troca em tempo de execução: opção 4 do menu (`Main.escolherRegra`).

### Composite — itens da ordem de serviço

* **Problema resolvido:** uma ordem contém itens individuais (peças, serviços) e pacotes
  que agrupam outros itens (ex.: revisão completa). O padrão permite tratar item
  simples e pacote de forma uniforme, totalizando peças e mão de obra de forma
  recursiva sem que a ordem saiba a diferença entre eles.
* **Classes envolvidas:**
  * `item.Item` (componente) — `getDescricao()`, `getValorPecas()`, `getValorMaoDeObra()` e `formatar()`.
  * `item.Peca` (folha) — quantidade × preço unitário, compõe o total de peças.
  * `item.MaoDeObra` (folha) — horas × valor/hora, compõe o total de mão de obra.
  * `item.ItemComposto` (composto) — agrupa filhos e soma seus valores
    recursivamente; `formatar()` exibe a árvore de itens com recuo.
  * `item.Formatacao` (auxiliar) — formatação de moeda (R$) e horas.
  * `model.OrdemServico` usa a raiz composta para agregar todos os itens da ordem.

## Declaração de uso de IA (Parte 1)

* **Modelo utilizado:** GLM (Z.ai), via agente de programação OpenCode.
* **Partes em que foi utilizada:** implementação inicial das classes dos patterns
  (Strategy e Composite), entidades `Veiculo` e `OrdemServico`, fluxo de console em
  `Main` e redação inicial deste README.
* Todo o código gerado com auxílio de IA foi **revisado e ajustado pelos integrantes**.

*(A Parte 2 deve ser desenvolvida sem uso de IA; a Parte 3 é autoral.)*
