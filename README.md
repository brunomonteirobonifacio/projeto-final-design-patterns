# Projeto Final de Design Patterns — Oficina Mecânica

Sistema de console para uma oficina mecânica: cadastro de veículos, abertura de ordens de serviço,
lançamento de itens (peças, serviços avulsos e pacotes de revisão) e cálculo do
orçamento conforme a regra de cobrança aplicada (padrão, frota, garantia ou prioritária).

## Integrantes e contribuições

| Integrante | Contribuição |
|---|---|
| Bruno Monteiro Bonifácio | Estrutura inicial do repositório (pom.xml, .gitignore, entrypoint `Main`), organização do README e revisão de PRs |
| Gianluca Andrade Silvestre | Parte 1 (branch `feature/strategy-orcamento`): entidades `Veiculo` e `OrdemServico`, regras de orçamento do Strategy e fluxo de console de ponta a ponta |
| Luís Filipe Damiani Colombo | Cadastro e listagem de veículos em memória (branch `feature/veiculos`): campo `dono` e validação/normalização de placa em `Veiculo`, classe `CadastroVeiculos` e opções de menu para cadastrar, listar e abrir a ordem a partir de um veículo cadastrado |
| Roger Balcevicz | Pattern Composite completo (interface `Item`, `Peca`, `MaoDeObra`, `ItemComposto` e `Formatacao` — branch `feature/composite`, PR #1); listagem e seleção de ordens de serviço, validação de entradas e remoção da regra padrão da `OrdemServico` (branch `feature/ordem-servico`, PR #3) |

### Contribuições da Parte 2

- **Isabel Bastos — Composite:** Parte 2 (branch `feature/extensao`): análise das classes do Composite; criação e teste do pacote composto de revisão elétrica, com peças e serviços e cálculo dos valores.

- **Sophia dos Santos — Strategy:** Parte 2 (branch `feature/extensao`): análise das regras existentes e criação da estratégia `OrcamentoPrioritario`, com acréscimo de 20% na mão de obra; verificação dos cálculos com itens e pacotes.

- **Elias Cabreira Maciel — Integração e Pull Request:** Parte 2 (branch `feature/extensao`): criação do fork e da branch, organização do trabalho da equipe, integração das funcionalidades ao menu, testes manuais, atualização do README e preparação do Pull Request.

## Descrição do sistema

A aplicação roda inteiramente em memória (sem banco de dados) e segue o fluxo:

1. **Cadastrar veículo** — informa placa, marca, modelo, ano e dono. A placa é única
   e aceita o padrão antigo (ABC1234) ou Mercosul (ABC1D23), com ou sem hífen.
2. **Listar veículos** — mostra todos os veículos cadastrados em memória.
3. **Abrir ordem de serviço** — escolhe, pela placa, um veículo já cadastrado.
4. **Lançar itens** — peças (quantidade × preço unitário), serviços de mão de obra
   (horas × valor/hora), o pacote de revisão completa e o pacote de revisão elétrica.
5. **Escolher a regra de orçamento** — Padrão, Frota, Garantia ou Prioritária, com acréscimo
   de 20% na mão de obra. A ordem não tem regra
   padrão: o total só é calculado depois que uma regra é escolhida.
6. **Exibir orçamento detalhado** — mostra a árvore de itens, os totais de peças e de mão de obra, a regra aplicada e o total final.
7. **Listar ordens de serviço** — mostra todas as ordens abertas, com veículo, regra e total.
8. **Selecionar ordem atual** — escolhe, pelo número (ex.: OS-001), em qual ordem os
   próximos itens e regras serão lançados.
9. **Comparar as quatro regras de orçamento** — mostra o total da mesma ordem em Padrão,
   Frota, Garantia e Prioritária, sem trocar a regra vigente.

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
9 - Listar ordens de serviço
10 - Selecionar ordem atual
11 - Comparar regras de orçamento
12 - Adicionar pacote de revisão elétrica
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
Ordem OS-001 aberta para o veículo Fiat Argo 2020 (placa ABC1234) — dono: Maria Souza. Ela agora é a ordem atual.

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
4 - Prioritária: acréscimo de 20% na mão de obra
Regra: 2
Regra definida: Frota (10% de desconto na mão de obra)

Opção: 8
========= ORÇAMENTO =========
Ordem: OS-001
Veículo: Fiat Argo 2020 (placa ABC1234) — dono: Maria Souza
Itens:
  - Peça: Pastilha de freio dianteira (2 x R$ 180,00) = R$ 360,00
  - Mão de obra: Reparo do freio (1,5 h x R$ 100,00) = R$ 150,00
  + Pacote — Revisão completa (peças R$ 395,00 | mão de obra R$ 300,00)
      + Troca de óleo (peças R$ 215,00 | mão de obra R$ 60,00)
          - Peça: Óleo de motor (4 x R$ 45,00) = R$ 180,00
          - Peça: Filtro de óleo (1 x R$ 35,00) = R$ 35,00
          - Mão de obra: Troca de óleo (0,5 h x R$ 120,00) = R$ 60,00
      + Freios (peças R$ 180,00 | mão de obra R$ 120,00)
          - Peça: Pastilhas de freio (1 x R$ 180,00) = R$ 180,00
          - Mão de obra: Substituição das pastilhas de freio (1 h x R$ 120,00) = R$ 120,00
      + Alinhamento e balanceamento (peças R$ 0,00 | mão de obra R$ 120,00)
          - Mão de obra: Alinhamento e balanceamento (1 h x R$ 120,00) = R$ 120,00

Total de peças: R$ 755,00
Total de mão de obra: R$ 450,00
Regra aplicada: Frota (10% de desconto na mão de obra)
TOTAL: R$ 1.160,00
=============================

Opção: 9
Ordens de serviço:
  OS-001 (atual) | Fiat Argo 2020 (placa ABC1234) — dono: Maria Souza | Frota (10% de desconto na mão de obra) | total: R$ 1.160,00

Opção: 11
Comparação de regras — ordem OS-001:
  Padrão (peças + mão de obra): R$ 1.205,00
  Frota (10% de desconto na mão de obra) (regra atual): R$ 1.160,00
  Garantia (apenas mão de obra): R$ 450,00
  Prioritário (20% de acréscimo na mão de obra): R$ 1.295,00

Opção: 12
Pacote de revisão elétrica adicionado à ordem OS-001.

- Exemplos utilizando a opção 12:
Opção: 7
Regras disponíveis:
1 - Padrão: peças + mão de obra
2 - Frota: 10% de desconto na mão de obra
3 - Garantia: apenas mão de obra
4 - Prioritária: acréscimo de 20% na mão de obra
Regra: 4
Regra definida: Prioritário (20% de acréscimo na mão de obra)

Opção: 8
  + Pacote — Revisão elétrica (peças R$ 65,00 | mão de obra R$ 180,00)
      - Mão de obra: Diagnóstico elétrico (0,5 h x R$ 120,00) = R$ 60,00
      + Substituição de componentes (peças R$ 65,00 | mão de obra R$ 120,00)
          - Peça: Fusível (2 x R$ 10,00) = R$ 20,00
          - Peça: Relé (1 x R$ 45,00) = R$ 45,00
          - Mão de obra: Substituição de componentes elétricos (1 h x R$ 120,00) = R$ 120,00
Total de peças: R$ 820,00
Total de mão de obra: R$ 630,00
TOTAL prioritário: R$ 1.576,00
```

Situações tratadas no cadastro de veículos:

* placa fora do padrão → `Não foi possível cadastrar: Placa inválida. Use o formato ABC1234 ou ABC1D23.`
* placa repetida → `Não foi possível cadastrar: Já existe um veículo cadastrado com a placa ABC1234.`
* a placa é conferida logo depois de digitada, antes de pedir os demais campos.
* ano fora do intervalo (1900 até o ano seguinte ao atual) é avisado logo depois de digitado e pedido de novo; campos vazios também são pedidos de novo.
* abrir ordem sem veículos cadastrados ou com placa inexistente → mensagem orientando a usar as opções 1 ou 2.

## Como testar a extensão

1. Inicie a aplicação conforme a seção **Como executar**.
2. Tente adicionar o pacote de revisão elétrica antes de abrir uma ordem. O sistema deve informar que nenhuma ordem está aberta.
3. Cadastre um veículo e abra uma ordem de serviço para ele.
4. Adicione o pacote de revisão elétrica pela opção **12**.
5. Escolha a regra **Prioritária** pela opção **7** e, em seguida, selecione **4**.
6. Exiba o orçamento pela opção **8**. O pacote deve apresentar:
   - Peças: **R$ 65,00**
   - Mão de obra: **R$ 180,00**
   - Total prioritário: **R$ 281,00**
7. Compare as regras pela opção **11**. Para uma ordem contendo somente esse pacote, os totais esperados são:

| Regra | Total esperado |
|---|---:|
| Padrão | R$ 245,00 |
| Frota | R$ 227,00 |
| Garantia | R$ 180,00 |
| Prioritária | R$ 281,00 |

A comparação deve identificar a regra prioritária como atual, sem alterar a regra selecionada na ordem.


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
  * `strategy.OrcamentoPrioritario` — peças + 120% da mão de obra.
  * Contexto: `model.OrdemServico` mantém a regra vigente (`definirRegra`) e delega
    o cálculo do total a ela (`calcularTotal`).
  * Ponto de troca em tempo de execução: opção 7 do menu (`Main.escolherRegra`).
  * Comparação de regras: opção 11 do menu (`Main.compararRegras`) calcula o total da
    mesma ordem com cada uma das quatro regras, sem trocar a regra vigente da ordem.

### Composite — itens da ordem de serviço

* **Problema resolvido:** uma ordem contém itens individuais (peças, serviços) e pacotes
  que agrupam outros itens — inclusive outros pacotes, como a revisão completa, que
  agrupa os sub-pacotes de troca de óleo, freios e alinhamento. O padrão permite tratar
  item simples e pacote de forma uniforme, totalizando peças e mão de obra de forma
  recursiva sem que a ordem saiba a diferença entre eles.
* **Classes envolvidas:**
  * `item.Item` (componente) — `getDescricao()`, `getValorPecas()`, `getValorMaoDeObra()` e `formatar()`.
  * `item.Peca` (folha) — quantidade × preço unitário, compõe o total de peças.
  * `item.MaoDeObra` (folha) — horas × valor/hora, compõe o total de mão de obra.
  * `item.ItemComposto` (composto) — agrupa filhos e soma seus valores
    recursivamente; `formatar()` exibe a árvore de itens com recuo.
  * `item.PacoteRevisaoEletrica` — pacote composto com diagnóstico elétrico e
    substituição de componentes; inclui fusíveis, relé e mão de obra, totalizando
    R$ 65,00 em peças e R$ 180,00 em mão de obra.
  * Opção 12 do menu adiciona esse pacote à ordem atual.
  * `item.Formatacao` (auxiliar) — formatação de moeda (R$) e horas.
  * `model.OrdemServico` mantém os itens numa `List<Item>` e os manipula sempre
    pela interface, como cliente do composite.

## Declaração de uso de IA (Parte 1)

* **Modelo utilizado:** GLM (Z.ai), via agente de programação OpenCode.
* **Partes em que foi utilizada:** implementação inicial das classes do pattern
  Strategy, entidades `Veiculo` e `OrdemServico`, fluxo de console em
  `Main` e redação inicial deste README.
* **Modelo utilizado (cadastro de veículos):** Claude Opus 5.5 (Anthropic), via claude.ai.
* **Partes em que foi utilizada:** classe `CadastroVeiculos`, campo `dono` e validação de
  placa em `Veiculo`, opções 1–3 do menu em `Main`, resolução do conflito do `Main` com a
  branch `feature/ordem-servico` e atualização deste README (fluxo, exemplo de uso e contribuições).
* Todo o código gerado com auxílio de IA foi **revisado e ajustado pelos integrantes**.
