package io.github.brunomonteirobonifacio.designpatternsoficina.item;

import java.math.BigDecimal;
import java.util.List;

public class PacoteRevisaoEletricaTest {

    public static void main(String[] args) {
        testarValores();
        testarHierarquiaFormatada();
        System.out.println("Testes do pacote de revisão elétrica passaram.");
    }

    private static void testarValores() {
        PacoteRevisaoEletrica pacote = new PacoteRevisaoEletrica();
        Item diagnostico = pacote.getItens().get(0);
        Item substituicao = pacote.getItens().get(1);

        verificarValor("0.00", diagnostico.getValorPecas(), "peças do diagnóstico");
        verificarValor("60.00", diagnostico.getValorMaoDeObra(), "mão de obra do diagnóstico");
        verificarValor("65.00", substituicao.getValorPecas(), "peças da substituição");
        verificarValor("120.00", substituicao.getValorMaoDeObra(), "mão de obra da substituição");
        verificarValor("65.00", pacote.getValorPecas(), "peças do pacote");
        verificarValor("180.00", pacote.getValorMaoDeObra(), "mão de obra do pacote");
    }

    private static void testarHierarquiaFormatada() {
        Item pacote = new PacoteRevisaoEletrica();
        List<String> esperado = List.of(
                "+ Pacote — Revisão elétrica (peças R$ 65,00 | mão de obra R$ 180,00)",
                "    - Mão de obra: Diagnóstico elétrico (0,5 h x R$ 120,00) = R$ 60,00",
                "    + Substituição de componentes (peças R$ 65,00 | mão de obra R$ 120,00)",
                "        - Peça: Fusível (2 x R$ 10,00) = R$ 20,00",
                "        - Peça: Relé (1 x R$ 45,00) = R$ 45,00",
                "        - Mão de obra: Substituição de componentes elétricos (1 h x R$ 120,00) = R$ 120,00"
        );

        String formatado = pacote.formatar();
        if (!esperado.equals(formatado.lines().toList())) {
            throw new AssertionError("Hierarquia inesperada:\n" + formatado);
        }
    }

    private static void verificarValor(String esperado, BigDecimal obtido, String descricao) {
        if (obtido.compareTo(new BigDecimal(esperado)) != 0) {
            throw new AssertionError(descricao + ": esperado " + esperado + ", obtido " + obtido);
        }
    }
}

// oque raios é um relé?