package io.github.brunomonteirobonifacio.designpatternsoficina.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Composto do Composite: representa um pacote de itens (ex.: revisão completa),
 * que agrupa outros itens — simples ou não — e totaliza seus valores de forma recursiva.
 */
public class PacoteItens implements Item {

    private final String descricao;
    private final List<Item> itens = new ArrayList<>();

    public PacoteItens(String descricao) {
        this.descricao = descricao;
    }

    public void adicionar(Item item) {
        itens.add(item);
    }

    public void remover(Item item) {
        itens.remove(item);
    }

    public List<Item> getItens() {
        return Collections.unmodifiableList(itens);
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public BigDecimal getValorPecas() {
        return itens.stream()
                .map(Item::getValorPecas)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getValorMaoDeObra() {
        return itens.stream()
                .map(Item::getValorMaoDeObra)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
