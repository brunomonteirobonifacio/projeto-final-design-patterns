package io.github.brunomonteirobonifacio.designpatternsoficina.item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class Moeda {

    private static final DecimalFormatSymbols SIMBOLOS = DecimalFormatSymbols.getInstance(Locale.of("pt", "BR"));

    private Moeda() {
    }

    public static String formatar(BigDecimal valor) {
        DecimalFormat formato = new DecimalFormat("#,##0.00", SIMBOLOS);
        return "R$ " + formato.format(valor.setScale(2, RoundingMode.HALF_UP));
    }

    static String recuo(int nivel) {
        return "    ".repeat(nivel);
    }
}
