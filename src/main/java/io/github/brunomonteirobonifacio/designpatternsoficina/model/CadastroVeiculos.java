package io.github.brunomonteirobonifacio.designpatternsoficina.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CadastroVeiculos {

    private final Map<String, Veiculo> veiculosPorPlaca = new LinkedHashMap<>();

    public void cadastrar(Veiculo veiculo) {
        verificarPlacaDisponivel(veiculo.placa());
        veiculosPorPlaca.put(veiculo.placa(), veiculo);
    }

    public void verificarPlacaDisponivel(String placa) {
        String placaNormalizada = Veiculo.validarPlaca(placa);
        if (veiculosPorPlaca.containsKey(placaNormalizada)) {
            throw new IllegalArgumentException("Já existe um veículo cadastrado com a placa " + placaNormalizada + ".");
        }
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return Optional.ofNullable(veiculosPorPlaca.get(Veiculo.normalizarPlaca(placa)));
    }

    public List<Veiculo> listar() {
        return List.copyOf(veiculosPorPlaca.values());
    }

    public boolean estaVazio() {
        return veiculosPorPlaca.isEmpty();
    }
}
