package io.github.brunomonteirobonifacio.designpatternsoficina.item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

final class Formatacao {

    private static final DecimalFormatSymbols SIMBOLOS = DecimalFormatSymbols.getInstance(Locale.of("pt", "BR"));

    private Formatacao() {
    }

    static String moeda(BigDecimal valor) {
        DecimalFormat formato = new DecimalFormat("#,##0.00", SIMBOLOS);
        return "R$ " + formato.format(valor.setScale(2, RoundingMode.HALF_UP));
    }

    static String horas(BigDecimal horas) {
        return horas.stripTrailingZeros().toPlainString().replace('.', ',') + " h";
    }
}
