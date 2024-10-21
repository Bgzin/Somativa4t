package com.example.controllers;

import com.example.api.MaquinaAPI;
import com.example.models.Maquina; // Certifique-se de que o pacote está correto
import java.util.ArrayList;
import java.util.List;

public class MaquinaController {
    private List<Maquina> maquinas;

    public MaquinaController() {
        maquinas = new ArrayList<>();
    }

    // Método para criar uma nova máquina
    public Maquina createMaquina(Maquina maquina) {
        // Chama a API e espera um objeto Maquina em resposta
        Maquina novaMaquina = MaquinaAPI.createMaquina(maquina);
        if (novaMaquina != null) {
            // Atualiza a lista de máquinas após criar uma nova
            readMaquinas();
        }
        return novaMaquina; // Retorna o objeto Maquina criado
    }

    // Método para listar todas as máquinas
    public List<Maquina> readMaquinas() {
        maquinas = MaquinaAPI.getMaquinas();
        return maquinas;
    }

    // Método para atualizar uma máquina
    public String updateMaquina(Maquina maquina) {
        String response = MaquinaAPI.updateMaquina(maquina);
        if (response != null) {
            // Opcionalmente, atualiza a lista de máquinas após a atualização
            readMaquinas();
        }
        return response; // Retorna resposta da API
    }

    // Método para deletar uma máquina
    public String deleteMaquina(String id) {
        String response = MaquinaAPI.deleteMaquina(id);
        if (response != null) {
            // Opcionalmente, atualiza a lista de máquinas após a exclusão
            readMaquinas();
        }
        return response; // Retorna resposta da API
    }
}