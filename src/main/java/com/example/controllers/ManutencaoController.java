package com.example.controllers;

import com.example.api.ManutencaoAPI;
import com.example.models.Manutencao;

import java.util.List;

public class ManutencaoController {
    // Atributo para armazenar a lista de manutenções
    private List<Manutencao> manutencoes;

    // Construtor que inicializa a lista de manutenções
    public ManutencaoController() {
        // Inicializa a lista de manutenções ao carregar
        manutencoes = readManutencoes();
    }

    // Método para criar uma nova manutenção
    public Manutencao createManutencao(Manutencao manutencao) {
        // Chama a API para criar a manutenção e armazena a resposta
        Manutencao novaManutencao = ManutencaoAPI.createManutencao(manutencao);
        if (novaManutencao != null) {
            // Atualiza a lista de manutenções após criar uma nova
            manutencoes = readManutencoes();
        }
        return novaManutencao; // Retorna a nova manutenção criada
    }

    // Método para ler todas as manutenções
    public List<Manutencao> readManutencoes() {
        // Chama o método getManutencoes da ManutencaoApi e armazena o resultado
        manutencoes = ManutencaoAPI.getManutencoes();
        return manutencoes; // Retorna a lista de manutenções
    }

    // Método para obter a lista de manutenções
    public List<Manutencao> getManutencoes() {
        return manutencoes;
    }

    // Método para atualizar uma manutenção existente
    public String updateManutencao(Manutencao manutencao) {
        String response = ManutencaoAPI.updateManutencao(manutencao);
        if (response != null && response.equals("Success")) {
            // Atualiza a lista de manutenções após a atualização
            manutencoes = readManutencoes();
        }
        return response; // Retorna a resposta da API
    }

    // Método para deletar uma manutenção pelo ID
    public String deleteManutencao(String id) {
        String response = ManutencaoAPI.deleteManutencao(id);
        if (response != null && response.equals("Success")) {
            // Atualiza a lista de manutenções após a exclusão
            manutencoes = readManutencoes();
        }
        return response; // Retorna a resposta da API
    }
}