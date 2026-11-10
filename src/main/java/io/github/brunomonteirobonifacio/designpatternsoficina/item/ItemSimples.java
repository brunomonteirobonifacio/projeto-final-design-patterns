package io.github.brunomonteirobonifacio.designpatternsoficina.item;

import java.math.BigDecimal;

/**
 * Folha do Composite: representa um item individual da ordem de serviço,
 * como uma peça trocada ou um serviço avulso (ex.: troca de pastilhas).
 */
public class ItemSimples implements Item {

    private final String descricao;
    private final BigDecimal valorPecas;
    private final BigDecimal valorMaoDeObra;

    public ItemSimples(String descricao, BigDecimal valorPecas, BigDecimal valorMaoDeObra) {
        this.descricao = descricao;
        this.valorPecas = valorPecas;
        this.valorMaoDeObra = valorMaoDeObra;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public BigDecimal getValorPecas() {
        return valorPecas;
    }

    @Override
    public BigDecimal getValorMaoDeObra() {
        return valorMaoDeObra;
    }
}
