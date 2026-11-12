package io.github.brunomonteirobonifacio.designpatternsoficina.strategy;

import java.math.BigDecimal;

/**
 * Regra de orçamento para veículos de frota: aplica 10% de desconto
 * sobre o valor da mão de obra; o valor das peças é cobrado integralmente.
 */
public class OrcamentoFrota implements RegraOrcamento {

    private static final BigDecimal FATOR_DESCONTO_MAO_DE_OBRA = new BigDecimal("0.90");

    @Override
    public BigDecimal calcular(BigDecimal valorPecas, BigDecimal valorMaoDeObra) {
        BigDecimal maoDeObraComDesconto = valorMaoDeObra.multiply(FATOR_DESCONTO_MAO_DE_OBRA);
        return valorPecas.add(maoDeObraComDesconto);
    }

    @Override
    public String getDescricao() {
        return "Frota (10% de desconto na mão de obra)";
    }
}
