package io.github.brunomonteirobonifacio.designpatternsoficina;

import io.github.brunomonteirobonifacio.designpatternsoficina.item.Item;
import io.github.brunomonteirobonifacio.designpatternsoficina.item.ItemSimples;
import io.github.brunomonteirobonifacio.designpatternsoficina.item.PacoteItens;
import io.github.brunomonteirobonifacio.designpatternsoficina.model.OrdemServico;
import io.github.brunomonteirobonifacio.designpatternsoficina.model.Veiculo;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.OrcamentoFrota;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.OrcamentoGarantia;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.OrcamentoPadrao;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.RegraOrcamento;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Fluxo de ponta a ponta da oficina, via console:
 * abrir a ordem de serviço -> adicionar itens (simples ou pacotes) ->
 * escolher a regra de orçamento (Strategy) -> exibir o orçamento detalhado.
 */
public class Main {

    private final Scanner entrada = new Scanner(System.in);
    private final NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

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
                case "2" -> adicionarItemSimples();
                case "3" -> adicionarPacoteRevisao();
                case "4" -> escolherRegra();
                case "5" -> exibirOrcamento();
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
        System.out.println("2 - Adicionar item simples (peça/serviço)");
        System.out.println("3 - Adicionar pacote de revisão completa");
        System.out.println("4 - Escolher regra de orçamento");
        System.out.println("5 - Exibir orçamento detalhado");
        System.out.println("0 - Sair");
    }

    private void abrirOrdem() {
        if (ordem != null) {
            System.out.println("Atenção: já existia uma ordem aberta, ela foi substituída.");
        }
        String placa = lerTexto("Placa do veículo");
        String marca = lerTexto("Marca");
        String modelo = lerTexto("Modelo");
        int ano = lerInteiro("Ano");
        String numero = "OS-%03d".formatted(proximoNumero++);
        ordem = new OrdemServico(numero, new Veiculo(placa, marca, modelo, ano));
        System.out.println("Ordem " + numero + " aberta para o veículo " + ordem.getVeiculo() + ".");
    }

    private void adicionarItemSimples() {
        if (exigirOrdemAberta()) {
            return;
        }
        String descricao = lerTexto("Descrição do item");
        BigDecimal valorPecas = lerValor("Valor de peças");
        BigDecimal valorMaoDeObra = lerValor("Valor de mão de obra");
        ordem.adicionarItem(new ItemSimples(descricao, valorPecas, valorMaoDeObra));
        System.out.println("Item adicionado à ordem " + ordem.getNumero() + ".");
    }

    private void adicionarPacoteRevisao() {
        if (exigirOrdemAberta()) {
            return;
        }
        PacoteItens pacote = new PacoteItens("Pacote — Revisão completa");
        pacote.adicionar(new ItemSimples("Troca de óleo e filtro", new BigDecimal("145.00"), new BigDecimal("80.00")));
        pacote.adicionar(new ItemSimples("Revisão do sistema de freios", BigDecimal.ZERO, new BigDecimal("90.00")));
        pacote.adicionar(new ItemSimples("Verificação da suspensão", BigDecimal.ZERO, new BigDecimal("70.00")));
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
            imprimirItem(item, "  ");
        }
        System.out.println();
        System.out.println("Total de peças: " + moeda.format(ordem.getValorPecas()));
        System.out.println("Total de mão de obra: " + moeda.format(ordem.getValorMaoDeObra()));
        System.out.println("Regra aplicada: " + ordem.getRegra().getDescricao());
        System.out.println("TOTAL: " + moeda.format(ordem.calcularTotal()));
        System.out.println("=============================");
    }

    private void imprimirItem(Item item, String recuo) {
        if (item instanceof PacoteItens pacote) {
            System.out.println(recuo + "[Pacote] " + item.getDescricao());
            for (Item filho : pacote.getItens()) {
                imprimirItem(filho, recuo + "    ");
            }
        } else {
            System.out.println(recuo + "- " + item.getDescricao()
                    + " | peças: " + moeda.format(item.getValorPecas())
                    + " | mão de obra: " + moeda.format(item.getValorMaoDeObra()));
        }
    }

    private boolean exigirOrdemAberta() {
        if (ordem == null) {
            System.out.println("Nenhuma ordem aberta. Use a opção 1 antes.");
            return true;
        }
        return false;
    }

    private String lerTexto(String rotulo) {
        System.out.print(rotulo + ": ");
        return entrada.nextLine().trim();
    }

    private int lerInteiro(String rotulo) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(rotulo));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, informe um número inteiro.");
            }
        }
    }

    private BigDecimal lerValor(String rotulo) {
        while (true) {
            try {
                return new BigDecimal(lerTexto(rotulo).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, informe um número (ex.: 150 ou 150.50).");
            }
        }
    }
}
