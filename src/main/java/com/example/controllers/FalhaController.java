package com.example.controllers;

import com.example.api.FalhasAPI;

import com.example.models.Falha;

import java.util.ArrayList;
import java.util.List;

public class FalhaController {
    private List<Falha> falhas;

    public FalhaController() {
        falhas = new ArrayList<>();
    }

    // Método para criar uma nova falha
    public Falha createFalha(Falha falha) {
        Falha novaFalha = FalhasAPI.createFalha(falha);
        if (novaFalha != null) {
            // Atualiza a lista de máquinas após criar uma nova
            readFalhas();
        }
        return novaFalha; // Retorna o objeto Maquina criado
    }

    // Método para obter a lista de falhas e armazenar na lista local
    public List<Falha> readFalhas() {
        List<Falha> falhas = FalhasAPI.getFalhas();
        return falhas;
    }

    // Método para atualizar uma falha existente
    public Falha updateFalha(Falha falha) {
        Falha novaFalha = FalhasAPI.updateFalha(falha);
        if (novaFalha != null) {
            // Atualiza a lista de máquinas após criar uma nova
            readFalhas();
        }
        return novaFalha; // Retorna o objeto Maquina criado
    }

    // Método para deletar uma falha pelo ID
    public String deleteFalha(String id) {
        String response = FalhasAPI.deleteFalha(id);
        if (response != null) {
            // Atualiza a lista de falhas após a exclusão
            readFalhas();
        }
        return response;
    }

    // Método para obter a lista de falhas (pode ser usado para outros propósitos)
    public List<Falha> getFalhas() {
        return falhas;
    }
}