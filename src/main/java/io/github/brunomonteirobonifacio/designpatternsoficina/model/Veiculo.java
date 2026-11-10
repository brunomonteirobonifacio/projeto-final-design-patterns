package io.github.brunomonteirobonifacio.designpatternsoficina.model;

/**
 * Entidade principal: veículo recepcionado pela oficina.
 */
public record Veiculo(String placa, String marca, String modelo, int ano) {

    @Override
    public String toString() {
        return "%s %s %d (placa %s)".formatted(marca, modelo, ano, placa);
    }
}
