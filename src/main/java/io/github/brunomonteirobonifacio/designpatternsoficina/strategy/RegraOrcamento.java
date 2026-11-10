package io.github.brunomonteirobonifacio.designpatternsoficina.strategy;

import java.math.BigDecimal;

/**
 * Strategy: contrato das regras de orçamento da ordem de serviço.
 * Cada implementação define como o valor final é calculado a partir
 * do total de peças e do total de mão de obra dos itens.
 */
public interface RegraOrcamento {

    BigDecimal calcular(BigDecimal valorPecas, BigDecimal valorMaoDeObra);

    String getDescricao();
}
