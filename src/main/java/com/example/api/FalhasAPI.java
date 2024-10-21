package com.example.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.models.Falha; // Certifique-se de que o pacote está correto

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Classe FalhasAPI para realizar operações com a API relacionada a falhas
public class FalhasAPI {

  // Método para obter todas as falhas da API
  public static List<Falha> getFalhas() {
    // Realiza uma requisição GET para o endpoint "falhas"
    String json = ApiConnection.getData("falhas");
    List<Falha> falhas = new ArrayList<>();

    // Verifica se a resposta JSON não é nula
    if (json != null) {
        try {
            // Converte a resposta em um JSONArray para processar múltiplos objetos
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Usa optString para evitar exceções caso um campo não exista
                String id = jsonObject.getString("id");
                String maquinaId = jsonObject.optString("maquinaId", "desconhecido");
                LocalDate data = LocalDate.parse(jsonObject.getString("data"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String problema = jsonObject.getString("problema");
                String prioridade = jsonObject.getString("prioridade");
                String operador = jsonObject.getString("operador");

                // Cria um objeto Falha e adiciona à lista
                Falha falha = new Falha(id, maquinaId, data, problema, prioridade, operador);
                falhas.add(falha);
            }
        } catch (JSONException e) {
            e.printStackTrace(); // Log de erros no parsing do JSON
        }
    }
    return falhas; // Retorna a lista de falhas
  }

  // Método para criar uma nova falha no servidor
  public static Falha createFalha(Falha falha) {
    // Cria um objeto JSON com os dados da falha
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("maquinaId", falha.getMaquinaID());
    jsonObject.put("data", falha.getData().toString()); // Converte LocalDate para String
    jsonObject.put("problema", falha.getProblema());
    jsonObject.put("prioridade", falha.getPrioridade());
    jsonObject.put("operador", falha.getOperador());

    // Converte o objeto JSON para string para enviar no payload da requisição
    String jsonPayload = jsonObject.toString();

    // Faz uma requisição POST para o endpoint "falhas" com o payload JSON
    String responseJson = ApiConnection.postData("falhas", jsonPayload);

    // Verifica se a resposta não é nula e processa
    if (responseJson != null) {
        JSONObject response = new JSONObject(responseJson);
        // Verifica se o ID está presente na resposta para confirmar que a criação foi bem-sucedida
        if (response.has("id")) {
            // Retorna a falha criada como um objeto Falha
            return new Falha(
                response.getString("id"),
                response.getString("maquinaId"),
                LocalDate.parse(response.getString("data")), // Converte para LocalDate
                response.getString("problema"),
                response.getString("prioridade"),
                response.getString("operador")
            );
        }
    }
    return null; // Retorna null se houver algum erro
  }

  // Método para atualizar uma falha existente no servidor
  public static Falha updateFalha(Falha falha) {
    // Cria um objeto JSON com os dados atualizados da falha
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("maquinaId", falha.getMaquinaID());
    jsonObject.put("data", falha.getData().toString()); // Converte LocalDate para String
    jsonObject.put("problema", falha.getProblema());
    jsonObject.put("prioridade", falha.getPrioridade());
    jsonObject.put("operador", falha.getOperador());

    // Converte o objeto JSON para string para enviar no payload da requisição
    String jsonPayload = jsonObject.toString();

    // Faz uma requisição PUT para atualizar a falha com o ID especificado
    String responseJson = ApiConnection.putData("falhas/" + falha.getId(), jsonPayload);

    // Verifica se a resposta não é nula e processa
    if (responseJson != null) {
        JSONObject response = new JSONObject(responseJson);
        // Verifica se o ID está presente para confirmar que a atualização foi bem-sucedida
        if (response.has("id")) {
            // Retorna a falha atualizada como um objeto Falha
            return new Falha(
                response.getString("id"),
                response.getString("maquinaId"),
                LocalDate.parse(response.getString("data")), // Converte para LocalDate
                response.getString("problema"),
                response.getString("prioridade"),
                response.getString("operador")
            );
        }
    }
    return null; // Retorna null se houver algum erro
  }

  // Método para deletar uma falha no servidor com base no ID
  public static String deleteFalha(String id) {
    // Faz uma requisição DELETE para o endpoint "falhas/{id}"
    return ApiConnection.deleteData("falhas/" + id);
  }
}
