package com.example.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.models.Manutencao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Classe ManutencaoAPI para realizar operações com a API relacionada a manutenções
public class ManutencaoAPI {

    // Método para obter a lista de manutenções
    public static List<Manutencao> getManutencoes() {
        // Faz uma requisição GET para o endpoint "historicoManutencao"
        String json = ApiConnection.getData("historicoManutencao");
        List<Manutencao> manutencoes = new ArrayList<>();

        // Verifica se o JSON recebido não é nulo
        if (json != null) {
            try {
                // Converte o JSON recebido em um JSONArray para processar múltiplos objetos
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // Converte o JSONObject para um objeto Manutencao e adiciona à lista
                    Manutencao manutencao = jsonToManutencao(jsonObject);
                    manutencoes.add(manutencao);
                }
            } catch (JSONException e) {
                e.printStackTrace(); // Log de erros no parsing do JSON
            }
        }
        return manutencoes; // Retorna a lista de manutenções
    }

    // Método auxiliar para converter JSONObject em Manutencao
    private static Manutencao jsonToManutencao(JSONObject jsonObject) {
        // Usa optString e optInt para evitar exceções ao obter os valores do JSON
        String id = jsonObject.optString("id", "desconhecido");
        String maquinaId = jsonObject.optString("maquinaId", "desconhecido");
        LocalDate data = LocalDate.parse(jsonObject.optString("data", LocalDate.now().toString()), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String tipo = jsonObject.optString("tipo", "desconhecido");
        String pecasTrocadas = jsonObject.optString("pecasTrocadas", "nenhuma");
        int tempoDeParada = jsonObject.optInt("tempoDeParada", 0);
        String tecnicoID = jsonObject.optString("tecnicoId", "desconhecido"); // Checar se "tecnicoId" é o nome correto
        String observacoes = jsonObject.optString("observacoes", "sem observações");

        // Retorna um objeto Manutencao preenchido com os dados do JSON
        return new Manutencao(id, maquinaId, data, tipo, pecasTrocadas, tempoDeParada, tecnicoID, observacoes);
    }

    // Método para criar uma nova manutenção no servidor
    public static Manutencao createManutencao(Manutencao manutencao) {
        // Converte o objeto Manutencao em um JSON para envio
        JSONObject jsonObject = manutencaoToJson(manutencao);
        String response = ApiConnection.postData("historicoManutencao", jsonObject.toString());

        // Verifica se a resposta não é nula
        if (response != null) {
            try {
                // Converte a resposta para JSONObject e cria um objeto Manutencao a partir dela
                JSONObject createdManutencaoJson = new JSONObject(response);
                return jsonToManutencao(createdManutencaoJson);
            } catch (JSONException e) {
                e.printStackTrace(); // Log de erros no parsing
            }
        }
        return null; // Retorna null em caso de falha
    }

    // Método para atualizar uma manutenção existente
    public static String updateManutencao(Manutencao manutencao) {
        // Converte o objeto Manutencao em um JSON para envio
        JSONObject jsonObject = manutencaoToJson(manutencao);
        // Faz uma requisição PUT para atualizar a manutenção com o ID especificado
        return ApiConnection.putData("historicoManutencao/" + manutencao.getId(), jsonObject.toString());
    }

    // Método para deletar uma manutenção no servidor usando o ID
    public static String deleteManutencao(String id) {
        // Faz uma requisição DELETE para remover a manutenção com o ID especificado
        return ApiConnection.deleteData("historicoManutencao/" + id);
    }

    // Método auxiliar para converter Manutencao em JSONObject
    private static JSONObject manutencaoToJson(Manutencao manutencao) {
        // Cria um objeto JSON com os dados da manutenção
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("maquinaId", manutencao.getMaquinaID());
        jsonObject.put("data", manutencao.getData().toString()); // Converte LocalDate para String
        jsonObject.put("tipo", manutencao.getTipo());
        jsonObject.put("pecasTrocadas", manutencao.getPecasTrocadas());
        jsonObject.put("tempoDeParada", manutencao.getTempoDeParada());
        jsonObject.put("tecnicoId", manutencao.getTecnicoID()); // Verificar o nome correto da chave
        jsonObject.put("observacoes", manutencao.getObservacoes());

        return jsonObject; // Retorna o JSON com os dados da manutenção
    }
}
