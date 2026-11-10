package io.github.brunomonteirobonifacio.designpatternsoficina.strategy;

import java.math.BigDecimal;

/**
 * Regra padrão de orçamento: cobra o valor total de peças
 * somado ao valor total de mão de obra.
 */
public class OrcamentoPadrao implements RegraOrcamento {

    @Override
    public BigDecimal calcular(BigDecimal valorPecas, BigDecimal valorMaoDeObra) {
        return valorPecas.add(valorMaoDeObra);
    }

    @Override
    public String getDescricao() {
        return "Padrão (peças + mão de obra)";
    }
}
