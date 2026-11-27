package io.github.brunomonteirobonifacio.designpatternsoficina.item;

import java.math.BigDecimal;

public class PacoteRevisaoEletrica extends ItemComposto {

    public PacoteRevisaoEletrica() {
        super("Pacote — Revisão elétrica");

        BigDecimal valorHora = new BigDecimal("120.00");
        adicionar(new MaoDeObra("Diagnóstico elétrico", new BigDecimal("0.5"), valorHora));

        ItemComposto substituicao = new ItemComposto("Substituição de componentes");
        substituicao.adicionar(new Peca("Fusível", new BigDecimal("10.00"), 2));
        substituicao.adicionar(new Peca("Relé", new BigDecimal("45.00"), 1));
        substituicao.adicionar(new MaoDeObra("Substituição de componentes elétricos", BigDecimal.ONE, valorHora));
        adicionar(substituicao);
    }
}
