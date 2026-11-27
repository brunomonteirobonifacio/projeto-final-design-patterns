package io.github.brunomonteirobonifacio.designpatternsoficina.strategy;

import java.math.BigDecimal;

/**
 * Regra de orçamentopara atendimento prioritário: aplica 20% de acréscimo
 * sobre o valor da mão de obra; o valor das peças é cobrado integralmente.
 */
public class OrcamentoPrioritario implements RegraOrcamento {

    private static final BigDecimal FATOR_ACRESCIMO_MAO_DE_OBRA = new BigDecimal("1.20");

    @Override
    public BigDecimal calcular(BigDecimal valorPecas, BigDecimal valorMaoDeObra) {
        BigDecimal maoDeObraComAcrescimo = valorMaoDeObra.multiply(FATOR_ACRESCIMO_MAO_DE_OBRA);
        return valorPecas.add(maoDeObraComAcrescimo);
    }

    @Override
    public String getDescricao() {
        return "Prioritário (20% de acréscimo na mão de obra)";
    }
}
