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
    public void exibir(int nivel) {
        System.out.println(Moeda.recuo(nivel) + "- Peça: " + descricao
                + " (" + quantidade + " x " + Moeda.formatar(precoUnitario) + ") = "
                + Moeda.formatar(getValorPecas()));
    }
}
