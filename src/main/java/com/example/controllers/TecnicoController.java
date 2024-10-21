package com.example.controllers;

import com.example.api.TecnicoAPI;
import com.example.models.Tecnico;

import java.util.ArrayList;
import java.util.List;

public class TecnicoController {
    private List<Tecnico> tecnicos;

    public TecnicoController() {
        tecnicos = new ArrayList<>();
    }

    // Método para criar um novo técnico
 // Método para criar um novo técnico
public Tecnico createTecnico(Tecnico tecnico) {
    Tecnico novoTecnico = TecnicoAPI.createTecnico(tecnico);
    if (novoTecnico != null) {
        // Atualiza a lista de técnicos após criar um novo
        readTecnicos();
    }
    return novoTecnico; // Retorna o objeto Técnico criado
}


 // Método para obter a lista de técnicos e armazená-la localmente
public List<Tecnico> readTecnicos() {
    // Obtém a lista de técnicos do API
    List<Tecnico> tecnicos = TecnicoAPI.getTecnicos();
    return tecnicos; // Retorna a lista de técnicos
}


    // Método para atualizar um técnico existente
    public String updateTecnico(Tecnico tecnico) {
        String response = TecnicoAPI.updateTecnico(tecnico);
        if (response != null) {
            // Atualiza a lista de técnicos após a modificação
            readTecnicos();
        }
        return response;
    }

    // Método para deletar um técnico pelo ID
    public String deleteTecnico(String id) {
        String response = TecnicoAPI.deleteTecnico(id);
        if (response != null) {
            // Atualiza a lista de técnicos após a exclusão
            readTecnicos();
        }
        return response;
    }

    // Método para obter a lista de técnicos (pode ser usada para outros propósitos)
    public List<Tecnico> getTecnicos() {
        return tecnicos;
    }
}