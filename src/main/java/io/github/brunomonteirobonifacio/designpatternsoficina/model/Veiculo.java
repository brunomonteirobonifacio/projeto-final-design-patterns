package io.github.brunomonteirobonifacio.designpatternsoficina.model;

import java.time.Year;
import java.util.Locale;
import java.util.regex.Pattern;

public record Veiculo(String placa, String marca, String modelo, int ano, String dono) {

    private static final Pattern FORMATO_PLACA = Pattern.compile("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}");

    public Veiculo {
        placa = validarPlaca(placa);
        marca = obrigatorio(marca, "marca");
        modelo = obrigatorio(modelo, "modelo");
        dono = obrigatorio(dono, "dono");
        validarAno(ano);
    }

    public static String normalizarPlaca(String placa) {
        if (placa == null) {
            return "";
        }
        return placa.toUpperCase(Locale.ROOT).replace("-", "").replace(" ", "");
    }

    public static String validarPlaca(String placa) {
        String placaNormalizada = normalizarPlaca(placa);
        if (!FORMATO_PLACA.matcher(placaNormalizada).matches()) {
            throw new IllegalArgumentException("Placa inválida. Use o formato ABC1234 ou ABC1D23.");
        }
        return placaNormalizada;
    }

    public static void validarAno(int ano) {
        int anoMaximo = Year.now().getValue() + 1;
        if (ano < 1900 || ano > anoMaximo) {
            throw new IllegalArgumentException("Ano inválido. Informe um ano entre 1900 e " + anoMaximo + ".");
        }
    }

    private static String obrigatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }

    @Override
    public String toString() {
        return "%s %s %d (placa %s) — dono: %s".formatted(marca, modelo, ano, placa, dono);
    }
}
