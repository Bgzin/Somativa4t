package com.example.api;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.models.Tecnico; // Certifique-se de que o pacote está correto

import java.util.ArrayList;
import java.util.List;

public class TecnicoAPI {

    public static List<Tecnico> getTecnicos() {
        // Faz uma requisição GET para o endpoint "tecnicos" e armazena a resposta JSON
        String json = ApiConnection.getData("tecnicos");
        List<Tecnico> tecnicos = new ArrayList<>();
    
        // Verifica se a resposta JSON não é nula
        if (json != null) {
            // Converte a string JSON para um JSONArray
            JSONArray jsonArray = new JSONArray(json);
    
            // Itera sobre o JSONArray para criar objetos Tecnico
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
    
                // Cria um objeto Tecnico com base nos dados do JSON
                Tecnico tecnico = new Tecnico(
                        jsonObject.getString("id"),
                        jsonObject.getString("nome"),
                        jsonObject.getString("especialidade"),
                        jsonObject.getString("disponibilidade") // Disponibilidade como String
                );
    
                // Adiciona o tecnico à lista de tecnicos
                tecnicos.add(tecnico);
            }
        }
        // Retorna a lista de tecnicos
        return tecnicos;
    }
    
    // Método para criar um novo técnico no servidor
public static Tecnico createTecnico(Tecnico tecnico) {
    // Cria um objeto JSON com os dados do técnico
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("nome", tecnico.getNome());
    jsonObject.put("especialidade", tecnico.getEspecialidade());
    jsonObject.put("disponibilidade", tecnico.getDisponibilidade());

    // Converte o objeto JSON para string para enviar no payload da requisição
    String jsonPayload = jsonObject.toString();

    // Faz uma requisição POST para o endpoint "tecnicos" com o payload JSON
    String responseJson = ApiConnection.postData("tecnicos", jsonPayload);

    // Verifica se a resposta não é nula e processa
    if (responseJson != null) {
        JSONObject response = new JSONObject(responseJson);
        // Verifica se o ID está presente na resposta
        if (response.has("id")) {
            // Retorna o técnico criado como um objeto
            return new Tecnico(
                response.getString("id"),
                response.getString("nome"),
                response.getString("especialidade"),
                response.getString("disponibilidade")
            );
        }
    }
    return null; // Retorna nulo se houver algum erro
}

    // Método para atualizar um técnico existente no servidor
    public static String updateTecnico(Tecnico tecnico) {
        // Cria um objeto JSON com os dados atualizados do técnico
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", tecnico.getNome());
        jsonObject.put("especialidade", tecnico.getEspecialidade());
        jsonObject.put("disponibilidade", tecnico.getDisponibilidade());

        // Converte o objeto JSON para string para enviar no payload da requisição
        String jsonPayload = jsonObject.toString();

        // Faz uma requisição PUT para atualizar o técnico com o ID especificado
        return ApiConnection.putData("tecnicos/" + tecnico.getId(), jsonPayload);
    }

    // Método para deletar um técnico no servidor com base no ID
    public static String deleteTecnico(String id) {
        // Faz uma requisição DELETE para o endpoint "tecnicos/{id}"
        return ApiConnection.deleteData("tecnicos/" + id);
    }
}