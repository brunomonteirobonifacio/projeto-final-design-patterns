package io.github.brunomonteirobonifacio.designpatternsoficina.model;

import io.github.brunomonteirobonifacio.designpatternsoficina.item.Item;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.RegraOrcamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entidade principal: ordem de serviço de um veículo.
 *
 * O contexto do Strategy: mantém a {@link RegraOrcamento} vigente e delega a ela
 * o cálculo do orçamento, permitindo trocar a regra de cobrança sem alterar esta classe
 * (nem o restante do fluxo da ordem).
 *
 * Os itens são mantidos em uma lista de {@link Item}, a interface do composite:
 * peças, serviços de mão de obra e pacotes são tratados de forma uniforme, sem que
 * esta classe precise conhecer as classes concretas que os implementam.
 */
public class OrdemServico {

    private final String numero;
    private final Veiculo veiculo;
    private final List<Item> itens = new ArrayList<>();
    private RegraOrcamento regra;

    public OrdemServico(String numero, Veiculo veiculo) {
        this.numero = numero;
        this.veiculo = veiculo;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public void definirRegra(RegraOrcamento regra) {
        if (regra == null) {
            throw new IllegalArgumentException("A regra de orçamento não pode ser nula.");
        }
        this.regra = regra;
    }

    public BigDecimal getValorPecas() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : itens) {
            total = total.add(item.getValorPecas());
        }
        return total;
    }

    public BigDecimal getValorMaoDeObra() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : itens) {
            total = total.add(item.getValorMaoDeObra());
        }
        return total;
    }

    public boolean temRegra() {
        return regra != null;
    }

    public BigDecimal calcularTotal() {
        if (regra == null) {
            throw new IllegalStateException("Escolha uma regra de orçamento antes de calcular o total.");
        }
        return regra.calcular(getValorPecas(), getValorMaoDeObra());
    }

    public String getNumero() {
        return numero;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public RegraOrcamento getRegra() {
        return regra;
    }

    public List<Item> getItens() {
        return Collections.unmodifiableList(itens);
    }
}
