# Projeto Final de Design Patterns — Oficina Mecânica

Sistema de console para uma oficina mecânica: cadastro de veículos, abertura de ordens de serviço,
lançamento de itens (peças, serviços avulsos e pacotes de revisão) e cálculo do
orçamento conforme a regra de cobrança aplicada (padrão, frota ou garantia).

## Integrantes e contribuições

| Integrante | Contribuição |
|---|---|
| Bruno Monteiro Bonifácio | Estrutura inicial do repositório (pom.xml, .gitignore, entrypoint `Main`), organização do README e merge do PR #1 |
| Gianluca Andrade Silvestre | Parte 1 (branch `feature/strategy-orcamento`): entidades `Veiculo` e `OrdemServico`, regras de orçamento do Strategy e fluxo de console de ponta a ponta |
| Luís Filipe Damiani Colombo | Cadastro e listagem de veículos em memória (branch `feature/veiculos`): campo `dono` e validação/normalização de placa em `Veiculo`, classe `CadastroVeiculos` e opções de menu para cadastrar, listar e abrir a ordem a partir de um veículo cadastrado |
| Roger Balcevicz | Pattern Composite completo (interface `Item`, `Peca`, `MaoDeObra`, `ItemComposto` e `Formatacao` — branch `feature/composite`, PR #1) |

## Descrição do sistema

A aplicação roda inteiramente em memória (sem banco de dados) e segue o fluxo:

1. **Cadastrar veículo** — informa placa, marca, modelo, ano e dono. A placa é única
   e aceita o padrão antigo (ABC1234) ou Mercosul (ABC1D23), com ou sem hífen.
2. **Listar veículos** — mostra todos os veículos cadastrados em memória.
3. **Abrir ordem de serviço** — escolhe, pela placa, um veículo já cadastrado.
4. **Lançar itens** — peças (quantidade × preço unitário), serviços de mão de obra
   (horas × valor/hora) e o pacote de revisão completa, que agrupa vários itens.
5. **Escolher a regra de orçamento** — Padrão, Frota ou Garantia.
6. **Exibir orçamento detalhado** — mostra a árvore de itens, os totais de peças e de mão de obra, a regra aplicada e o total final.

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
1 - Cadastrar veículo
2 - Listar veículos
3 - Abrir ordem de serviço
4 - Adicionar peça
5 - Adicionar serviço de mão de obra
6 - Adicionar pacote de revisão completa
7 - Escolher regra de orçamento
8 - Exibir orçamento detalhado
0 - Sair
Opção: 1
Placa (ABC1234 ou ABC1D23): ABC-1234
Marca: Fiat
Modelo: Argo
Ano: 2020
Dono: Maria Souza
Veículo cadastrado: Fiat Argo 2020 (placa ABC1234) — dono: Maria Souza.

Opção: 1
Placa (ABC1234 ou ABC1D23): BRA2E19
Marca: Toyota
Modelo: Corolla
Ano: 2023
Dono: Carlos Lima
Veículo cadastrado: Toyota Corolla 2023 (placa BRA2E19) — dono: Carlos Lima.

Opção: 2
Veículos cadastrados (2):
PLACA    | MARCA/MODELO         | ANO  | DONO
ABC1234  | Fiat Argo            | 2020 | Maria Souza
BRA2E19  | Toyota Corolla       | 2023 | Carlos Lima

Opção: 3
Placa do veículo: abc1234
Ordem OS-001 aberta para o veículo Fiat Argo 2020 (placa ABC1234) — dono: Maria Souza.

Opção: 4
Descrição da peça: Pastilha de freio dianteira
Preço unitário: 180
Quantidade: 2
Peça adicionada à ordem OS-001.

Opção: 5
Descrição do serviço: Reparo do freio
Horas trabalhadas (ex.: 1.5): 1.5
Valor por hora: 100
Serviço adicionado à ordem OS-001.

Opção: 6
Pacote de revisão completa adicionado à ordem OS-001.

Opção: 7
Regras disponíveis:
1 - Padrão: peças + mão de obra
2 - Frota: 10% de desconto na mão de obra
3 - Garantia: apenas mão de obra
Regra: 2
Regra definida: Frota (10% de desconto na mão de obra)

Opção: 8
========= ORÇAMENTO =========
Ordem: OS-001
Veículo: Fiat Argo 2020 (placa ABC1234) — dono: Maria Souza
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

Situações tratadas no cadastro de veículos:

* placa repetida → `Já existe um veículo cadastrado com a placa ABC1234.`
* placa fora do padrão → `Placa inválida. Use o formato ABC1234 ou ABC1D23.`
* abrir ordem sem veículos cadastrados ou com placa inexistente → mensagem orientando a usar as opções 1 ou 2.

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
  * Ponto de troca em tempo de execução: opção 7 do menu (`Main.escolherRegra`).

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
* **Modelo utilizado (cadastro de veículos):** Claude Opus 5.5 (Anthropic), via claude.ai.
* **Partes em que foi utilizada:** classe `CadastroVeiculos`, campo `dono` e validação de
  placa em `Veiculo`, opções 1–3 do menu em `Main` e atualização deste README
  (fluxo, exemplo de uso e contribuição do Luís).
* Todo o código gerado com auxílio de IA foi **revisado e ajustado pelos integrantes**.

*(A Parte 2 deve ser desenvolvida sem uso de IA; a Parte 3 é autoral.)*
