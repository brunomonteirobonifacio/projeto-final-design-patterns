package io.github.brunomonteirobonifacio.designpatternsoficina.strategy;

import java.math.BigDecimal;

/**
 * Regra de orçamento para serviço em garantia: as peças são cobertas
 * pela garantia, portanto apenas a mão de obra é cobrada.
 */
public class OrcamentoGarantia implements RegraOrcamento {

    @Override
    public BigDecimal calcular(BigDecimal valorPecas, BigDecimal valorMaoDeObra) {
        return valorMaoDeObra;
    }

    @Override
    public String getDescricao() {
        return "Garantia (apenas mão de obra)";
    }
}
