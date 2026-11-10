# Projeto Final de Design Patterns — Oficina Mecânica

Sistema de console para uma oficina mecânica: abertura de ordens de serviço,
lançamento de itens (peças, serviços avulsos e pacotes de revisão) e cálculo do
orçamento conforme a regra de cobrança aplicada (padrão, frota ou garantia).

## Integrantes e contribuições

| Integrante | Contribuição |
|---|---|
| Bruno Monteiro Bonifácio | Estrutura inicial do repositório (pom.xml, .gitignore, entrypoint `Main`) e organização do README |
| Gianluca Andrade Silvestre | Parte 1 (branch `feature/strategy-orcamento`): entidades `Veiculo` e `OrdemServico`, folha e composto do Composite, regras de orçamento do Strategy e fluxo de console de ponta a ponta |
| Luís Filipe Damiani Colombo | *(a preencher)* |
| Roger Balcevicz | Interface `Item` do Composite (branch `feature/composite`) |

## Descrição do sistema

A aplicação roda inteiramente em memória (sem banco de dados) e segue o fluxo:

1. **Abrir ordem de serviço** — informa placa, marca, modelo e ano do veículo.
2. **Adicionar itens** — itens simples (com valor de peças e de mão de obra, qualquer um podendo ser zero) ou o pacote de revisão completa, que agrupa vários itens.
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
2 - Adicionar item simples (peça/serviço)
3 - Adicionar pacote de revisão completa
4 - Escolher regra de orçamento
5 - Exibir orçamento detalhado
0 - Sair
Opção: 1
Placa do veículo: ABC-1234
Marca: Fiat
Modelo: Argo
Ano: 2020
Ordem OS-001 aberta para o veículo Fiat Argo 2020 (placa ABC-1234).

Opção: 2
Descrição do item: Troca de pastilhas de freio
Valor de peças: 180
Valor de mão de obra: 120
Item adicionado à ordem OS-001.

Opção: 3
Pacote de revisão completa adicionado à ordem OS-001.

Opção: 4
Regras disponíveis:
1 - Padrão: peças + mão de obra
2 - Frota: 10% de desconto na mão de obra
3 - Garantia: apenas mão de obra
Regra: 2
Regra definida: Frota (10% de desconto na mão de obra)

Opção: 5
========= ORÇAMENTO =========
Ordem: OS-001
Veículo: Fiat Argo 2020 (placa ABC-1234)
Itens:
  - Troca de pastilhas de freio | peças: R$ 180,00 | mão de obra: R$ 120,00
  [Pacote] Pacote — Revisão completa
      - Troca de óleo e filtro | peças: R$ 145,00 | mão de obra: R$ 80,00
      - Revisão do sistema de freios | peças: R$ 0,00 | mão de obra: R$ 90,00
      - Verificação da suspensão | peças: R$ 0,00 | mão de obra: R$ 70,00

Total de peças: R$ 325,00
Total de mão de obra: R$ 360,00
Regra aplicada: Frota (10% de desconto na mão de obra)
TOTAL: R$ 649,00
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

* **Problema resolvido:** uma ordem contém itens individuais (peça/serviço) e pacotes
  que agrupam outros itens (ex.: revisão completa). O padrão permite tratar item
  simples e pacote de forma uniforme, totalizando peças e mão de obra de forma
  recursiva sem que a ordem saiba a diferença entre eles.
* **Classes envolvidas:**
  * `item.Item` (componente) — `getDescricao()`, `getValorPecas()`, `getValorMaoDeObra()`.
  * `item.ItemSimples` (folha) — um item com valores próprios.
  * `item.PacoteItens` (composto) — agrupa filhos e soma seus valores
    recursivamente.
  * `model.OrdemServico` usa a raiz composta para agregar todos os itens da ordem.

## Declaração de uso de IA (Parte 1)

* **Modelo utilizado:** GLM (Z.ai), via agente de programação OpenCode.
* **Partes em que foi utilizada:** implementação inicial das classes dos patterns
  (Strategy e Composite), entidades `Veiculo` e `OrdemServico`, fluxo de console em
  `Main` e redação inicial deste README.
* Todo o código gerado com auxílio de IA foi **revisado e ajustado pelos integrantes**.

*(A Parte 2 deve ser desenvolvida sem uso de IA; a Parte 3 é autoral.)*
