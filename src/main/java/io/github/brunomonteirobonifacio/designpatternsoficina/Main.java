package io.github.brunomonteirobonifacio.designpatternsoficina;

import io.github.brunomonteirobonifacio.designpatternsoficina.item.Item;
import io.github.brunomonteirobonifacio.designpatternsoficina.item.ItemComposto;
import io.github.brunomonteirobonifacio.designpatternsoficina.item.MaoDeObra;
import io.github.brunomonteirobonifacio.designpatternsoficina.item.Peca;
import io.github.brunomonteirobonifacio.designpatternsoficina.model.OrdemServico;
import io.github.brunomonteirobonifacio.designpatternsoficina.model.Veiculo;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.OrcamentoFrota;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.OrcamentoGarantia;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.OrcamentoPadrao;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.RegraOrcamento;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Fluxo de ponta a ponta da oficina, via console:
 * abrir a ordem de serviço -> lançar itens (peças, mão de obra e pacotes) ->
 * escolher a regra de orçamento (Strategy) -> exibir o orçamento detalhado.
 */
public class Main {

    private final Scanner entrada = new Scanner(System.in);
    private final NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    private final List<OrdemServico> ordens = new ArrayList<>();
    private OrdemServico ordem;
    private int proximoNumero = 1;

    void main() {
        System.out.println("=== Oficina Mecânica — Sistema de Ordens de Serviço ===");
        boolean executando = true;
        while (executando) {
            exibirMenu();
            String opcao = lerTexto("Opção");
            switch (opcao) {
                case "1" -> abrirOrdem();
                case "2" -> adicionarPeca();
                case "3" -> adicionarMaoDeObra();
                case "4" -> adicionarPacoteRevisao();
                case "5" -> escolherRegra();
                case "6" -> exibirOrcamento();
                case "7" -> listarOrdens();
                case "8" -> selecionarOrdem();
                case "0" -> executando = false;
                default -> System.out.println("Opção inválida, tente novamente.");
            }
        }
        System.out.println("Encerrando o sistema. Até logo!");
    }

    private void exibirMenu() {
        System.out.println();
        System.out.println("----- MENU -----");
        System.out.println("1 - Abrir ordem de serviço");
        System.out.println("2 - Adicionar peça");
        System.out.println("3 - Adicionar serviço de mão de obra");
        System.out.println("4 - Adicionar pacote de revisão completa");
        System.out.println("5 - Escolher regra de orçamento");
        System.out.println("6 - Exibir orçamento detalhado");
        System.out.println("7 - Listar ordens de serviço");
        System.out.println("8 - Selecionar ordem atual");
        System.out.println("0 - Sair");
        if (ordem != null) {
            System.out.println("Ordem atual: " + ordem.getNumero());
        }
    }

    private void abrirOrdem() {
        String placa = lerTexto("Placa do veículo");
        String marca = lerTexto("Marca");
        String modelo = lerTexto("Modelo");
        int ano = lerInteiro("Ano");
        String numero = "OS-%03d".formatted(proximoNumero++);
        ordem = new OrdemServico(numero, new Veiculo(placa, marca, modelo, ano));
        ordens.add(ordem);
        System.out.println("Ordem " + numero + " aberta para o veículo " + ordem.getVeiculo()
                + ". Ela agora é a ordem atual.");
    }

    private void listarOrdens() {
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem de serviço aberta.");
            return;
        }
        System.out.println("Ordens de serviço:");
        for (OrdemServico os : ordens) {
            String regra = os.temRegra() ? os.getRegra().getDescricao() : "sem regra escolhida";
            String total = os.temRegra() ? moeda.format(os.calcularTotal()) : "-";
            String atual = os == ordem ? " (atual)" : "";
            System.out.println("  " + os.getNumero() + atual + " | " + os.getVeiculo()
                    + " | " + regra + " | total: " + total);
        }
    }

    private void selecionarOrdem() {
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem de serviço aberta. Use a opção 1 antes.");
            return;
        }
        String numero = lerTexto("Número da ordem (ex.: OS-001)");
        for (OrdemServico os : ordens) {
            if (os.getNumero().equalsIgnoreCase(numero)) {
                ordem = os;
                System.out.println("Ordem atual: " + ordem.getNumero() + ".");
                return;
            }
        }
        System.out.println("Ordem " + numero + " não encontrada.");
    }

    private void adicionarPeca() {
        if (exigirOrdemAberta()) {
            return;
        }
        String descricao = lerTexto("Descrição da peça");
        BigDecimal precoUnitario = lerValor("Preço unitário");
        int quantidade = lerInteiro("Quantidade");
        ordem.adicionarItem(new Peca(descricao, precoUnitario, quantidade));
        System.out.println("Peça adicionada à ordem " + ordem.getNumero() + ".");
    }

    private void adicionarMaoDeObra() {
        if (exigirOrdemAberta()) {
            return;
        }
        String descricao = lerTexto("Descrição do serviço");
        BigDecimal horas = lerValor("Horas trabalhadas (ex.: 1.5)");
        BigDecimal valorHora = lerValor("Valor por hora");
        ordem.adicionarItem(new MaoDeObra(descricao, horas, valorHora));
        System.out.println("Serviço adicionado à ordem " + ordem.getNumero() + ".");
    }

    private void adicionarPacoteRevisao() {
        if (exigirOrdemAberta()) {
            return;
        }
        ItemComposto pacote = new ItemComposto("Pacote — Revisão completa");
        pacote.adicionar(new Peca("Óleo de motor e filtro", new BigDecimal("145.00"), 1));
        pacote.adicionar(new MaoDeObra("Troca de óleo e filtro", BigDecimal.ONE, new BigDecimal("80.00")));
        pacote.adicionar(new MaoDeObra("Revisão do sistema de freios", new BigDecimal("1.5"), new BigDecimal("60.00")));
        pacote.adicionar(new MaoDeObra("Verificação da suspensão", BigDecimal.ONE, new BigDecimal("70.00")));
        ordem.adicionarItem(pacote);
        System.out.println("Pacote de revisão completa adicionado à ordem " + ordem.getNumero() + ".");
    }

    private void escolherRegra() {
        if (exigirOrdemAberta()) {
            return;
        }
        System.out.println("Regras disponíveis:");
        System.out.println("1 - Padrão: peças + mão de obra");
        System.out.println("2 - Frota: 10% de desconto na mão de obra");
        System.out.println("3 - Garantia: apenas mão de obra");
        RegraOrcamento regra = switch (lerTexto("Regra")) {
            case "1" -> new OrcamentoPadrao();
            case "2" -> new OrcamentoFrota();
            case "3" -> new OrcamentoGarantia();
            default -> null;
        };
        if (regra == null) {
            System.out.println("Regra inválida.");
            return;
        }
        ordem.definirRegra(regra);
        System.out.println("Regra definida: " + regra.getDescricao());
    }

    private void exibirOrcamento() {
        if (exigirOrdemAberta()) {
            return;
        }
        System.out.println();
        System.out.println("========= ORÇAMENTO =========");
        System.out.println("Ordem: " + ordem.getNumero());
        System.out.println("Veículo: " + ordem.getVeiculo());
        System.out.println("Itens:");
        for (Item item : ordem.getItens()) {
            item.formatar().lines().forEach(linha -> System.out.println("  " + linha));
        }
        System.out.println();
        System.out.println("Total de peças: " + moeda.format(ordem.getValorPecas()));
        System.out.println("Total de mão de obra: " + moeda.format(ordem.getValorMaoDeObra()));
        if (ordem.temRegra()) {
            System.out.println("Regra aplicada: " + ordem.getRegra().getDescricao());
            System.out.println("TOTAL: " + moeda.format(ordem.calcularTotal()));
        } else {
            System.out.println("Nenhuma regra de orçamento escolhida. Use a opção 5 para calcular o total.");
        }
        System.out.println("=============================");
    }

    private boolean exigirOrdemAberta() {
        if (ordem == null) {
            System.out.println("Nenhuma ordem aberta. Use a opção 1 antes.");
            return true;
        }
        return false;
    }

    private String lerTexto(String rotulo) {
        while (true) {
            System.out.print(rotulo + ": ");
            String texto = entrada.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Este campo não pode ficar vazio.");
        }
    }

    private int lerInteiro(String rotulo) {
        while (true) {
            try {
                int valor = Integer.parseInt(lerTexto(rotulo));
                if (valor > 0) {
                    return valor;
                }
                System.out.println("O valor deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, informe um número inteiro.");
            }
        }
    }

    private BigDecimal lerValor(String rotulo) {
        while (true) {
            try {
                BigDecimal valor = new BigDecimal(lerTexto(rotulo).replace(',', '.'));
                if (valor.signum() > 0) {
                    return valor;
                }
                System.out.println("O valor deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, informe um número (ex.: 150 ou 150.50).");
            }
        }
    }
}
