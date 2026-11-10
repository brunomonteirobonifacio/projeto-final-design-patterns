package io.github.brunomonteirobonifacio.designpatternsoficina.item;

import java.math.BigDecimal;

public class Peca implements Item {

    private final String descricao;
    private final BigDecimal precoUnitario;
    private final int quantidade;

    public Peca(String descricao, BigDecimal precoUnitario, int quantidade) {
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public BigDecimal getValorPecas() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public BigDecimal getValorMaoDeObra() {
        return BigDecimal.ZERO;
    }

    @Override
    public String formatar() {
        return "- Peça: " + descricao + " (" + quantidade
                + " x " + Formatacao.moeda(precoUnitario) + ") = " + Formatacao.moeda(getValorPecas());
    }
}
