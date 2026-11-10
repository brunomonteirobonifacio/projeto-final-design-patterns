package io.github.brunomonteirobonifacio.designpatternsoficina.model;

import io.github.brunomonteirobonifacio.designpatternsoficina.item.Item;
import io.github.brunomonteirobonifacio.designpatternsoficina.item.PacoteItens;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.OrcamentoPadrao;
import io.github.brunomonteirobonifacio.designpatternsoficina.strategy.RegraOrcamento;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entidade principal: ordem de serviço de um veículo.
 *
 * O contexto do Strategy: mantém a {@link RegraOrcamento} vigente e delega a ela
 * o cálculo do orçamento, permitindo trocar a regra de cobrança sem alterar esta classe
 * (nem o restante do fluxo da ordem).
 *
 * Os itens também são mantidos como uma estrutura Composite: a lista de itens
 * é o próprio {@link PacoteItens} raiz, que agrega itens simples e pacotes.
 */
public class OrdemServico {

    private static final String DESCRICAO_ITENS = "Itens da ordem de serviço";

    private final String numero;
    private final Veiculo veiculo;
    private final PacoteItens itens = new PacoteItens(DESCRICAO_ITENS);
    private RegraOrcamento regra = new OrcamentoPadrao();

    public OrdemServico(String numero, Veiculo veiculo) {
        this.numero = numero;
        this.veiculo = veiculo;
    }

    public void adicionarItem(Item item) {
        itens.adicionar(item);
    }

    public void definirRegra(RegraOrcamento regra) {
        if (regra == null) {
            throw new IllegalArgumentException("A regra de orçamento não pode ser nula.");
        }
        this.regra = regra;
    }

    public BigDecimal getValorPecas() {
        return itens.getValorPecas();
    }

    public BigDecimal getValorMaoDeObra() {
        return itens.getValorMaoDeObra();
    }

    public BigDecimal calcularTotal() {
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
        return itens.getItens();
    }
}
