package io.github.brunomonteirobonifacio.designpatternsoficina.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemComposto implements Item {

    private final String descricao;
    private final List<Item> itens = new ArrayList<>();

    public ItemComposto(String descricao) {
        this.descricao = descricao;
    }

    public void adicionar(Item item) {
        itens.add(item);
    }

    public List<Item> getItens() {
        return Collections.unmodifiableList(itens);
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public BigDecimal getValorPecas() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : itens) {
            total = total.add(item.getValorPecas());
        }
        return total;
    }

    @Override
    public BigDecimal getValorMaoDeObra() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : itens) {
            total = total.add(item.getValorMaoDeObra());
        }
        return total;
    }

    @Override
    public void exibir(int nivel) {
        System.out.println(Moeda.recuo(nivel) + "+ " + descricao
                + " (peças " + Moeda.formatar(getValorPecas())
                + " | mão de obra " + Moeda.formatar(getValorMaoDeObra()) + ")");
        for (Item item : itens) {
            item.exibir(nivel + 1);
        }
    }
}
