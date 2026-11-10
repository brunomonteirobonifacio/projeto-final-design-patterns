package io.github.brunomonteirobonifacio.designpatternsoficina.item;

import java.math.BigDecimal;

public class MaoDeObra implements Item {

    private final String descricao;
    private final BigDecimal horas;
    private final BigDecimal valorHora;

    public MaoDeObra(String descricao, BigDecimal horas, BigDecimal valorHora) {
        this.descricao = descricao;
        this.horas = horas;
        this.valorHora = valorHora;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public BigDecimal getValorPecas() {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getValorMaoDeObra() {
        return horas.multiply(valorHora);
    }

    @Override
    public void exibir(int nivel) {
        String horasTexto = horas.stripTrailingZeros().toPlainString().replace('.', ',');
        System.out.println(Moeda.recuo(nivel) + "- Mão de obra: " + descricao
                + " (" + horasTexto + " h x " + Moeda.formatar(valorHora) + ") = "
                + Moeda.formatar(getValorMaoDeObra()));
    }
}
