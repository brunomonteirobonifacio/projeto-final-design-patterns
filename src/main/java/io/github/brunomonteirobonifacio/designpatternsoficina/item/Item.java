package io.github.brunomonteirobonifacio.designpatternsoficina.item;

import java.math.BigDecimal;

public interface Item {

    String getDescricao();

    BigDecimal getValorPecas();

    BigDecimal getValorMaoDeObra();

    void exibir(int nivel);
}
