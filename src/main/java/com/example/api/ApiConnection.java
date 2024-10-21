package com.example.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Classe ApiConnection que contém métodos para realizar requisições HTTP
public class ApiConnection {
    // Constante que define a URL base da API
    private static final String API_URL = "http://localhost:3000/";

    // Método para realizar uma requisição GET
    public static String getData(String endpoint) {
        try {
            // Cria um objeto URL combinando a URL base com o endpoint fornecido
            URL url = new URL(API_URL + endpoint);
            
            // Abre uma conexão HTTP à URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Define o método HTTP como GET
            connection.setRequestMethod("GET");

            // Buffer para ler a resposta da requisição
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            // Lê a resposta linha por linha e acumula no StringBuilder
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Fecha o BufferedReader e desconecta a conexão
            in.close();
            connection.disconnect();

            // Retorna a resposta da requisição como uma string
            return content.toString();

        } catch (Exception e) {
            // Em caso de erro, imprime o stack trace e retorna null
            e.printStackTrace();
            return null;
        }
    }

    // Método para realizar uma requisição POST com um payload JSON
    public static String postData(String endpoint, String jsonPayload) {
        try {
            // Cria um objeto URL combinando a URL base com o endpoint fornecido
            URL url = new URL(API_URL + endpoint);
            
            // Abre uma conexão HTTP à URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Define o método HTTP como POST
            connection.setRequestMethod("POST");
            
            // Define os headers da requisição, indicando que está enviando e aceitando JSON
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true); // Habilita a escrita no corpo da requisição

            // Envia o JSON payload no corpo da requisição
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(jsonPayload);
                wr.flush();
            }

            // Buffer para ler a resposta da requisição
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            // Lê a resposta linha por linha e acumula no StringBuilder
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Fecha o BufferedReader e desconecta a conexão
            in.close();
            connection.disconnect();

            // Retorna a resposta da requisição como uma string
            return content.toString();

        } catch (Exception e) {
            // Em caso de erro, imprime o stack trace e retorna null
            e.printStackTrace();
            return null;
        }
    }

    // Método para realizar uma requisição PUT com um payload JSON
    public static String putData(String endpoint, String jsonPayload) {
        try {
            // Cria um objeto URL combinando a URL base com o endpoint fornecido
            URL url = new URL(API_URL + endpoint);
            
            // Abre uma conexão HTTP à URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Define o método HTTP como PUT
            connection.setRequestMethod("PUT");
            
            // Define os headers da requisição, indicando que está enviando e aceitando JSON
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true); // Habilita a escrita no corpo da requisição

            // Envia o JSON payload no corpo da requisição
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(jsonPayload);
                wr.flush();
            }

            // Buffer para ler a resposta da requisição
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            // Lê a resposta linha por linha e acumula no StringBuilder
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Fecha o BufferedReader e desconecta a conexão
            in.close();
            connection.disconnect();

            // Retorna a resposta da requisição como uma string
            return content.toString();

        } catch (Exception e) {
            // Em caso de erro, imprime o stack trace e retorna null
            e.printStackTrace();
            return null;
        }
    }

    // Método para realizar uma requisição DELETE
    public static String deleteData(String endpoint) {
        try {
            // Cria um objeto URL combinando a URL base com o endpoint fornecido
            URL url = new URL(API_URL + endpoint);
            
            // Abre uma conexão HTTP à URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Define o método HTTP como DELETE
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Accept", "application/json");

            // Buffer para ler a resposta da requisição
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            // Lê a resposta linha por linha e acumula no StringBuilder
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Fecha o BufferedReader e desconecta a conexão
            in.close();
            connection.disconnect();

            // Retorna a resposta da requisição como uma string
            return content.toString();

        } catch (Exception e) {
            // Em caso de erro, imprime o stack trace e retorna null
            e.printStackTrace();
            return null;
        }
    }
}
