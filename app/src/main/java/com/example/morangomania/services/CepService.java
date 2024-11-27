package com.example.morangomania.services;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

public class CepService {

    // URL base da API que será usada para consultar o CEP
    private static final String BASE_URL = "https://viacep.com.br/ws/";

    // Interface que define os métodos de callback para o sucesso e falha
    public interface CepCallback {
        void onSuccess(String logradouro, String bairro, String cidade);  // Sucesso: retorna logradouro, bairro e cidade
        void onFailure(String errorMessage);  // Falha: retorna a mensagem de erro
    }

    // Método para buscar o endereço baseado no CEP
    public void buscarCep(String cep, final CepCallback callback) {
        // Cria um cliente HTTP usando OkHttpClient
        OkHttpClient client = new OkHttpClient();

        // Constrói a URL de consulta, com base no CEP fornecido
        String url = BASE_URL + cep + "/json/";

        // Cria a requisição HTTP com a URL
        Request request = new Request.Builder().url(url).build();

        // Envia a requisição de forma assíncrona
        client.newCall(request).enqueue(new Callback() {

            // Método chamado em caso de falha na requisição (problema de rede ou outros erros)
            @Override
            public void onFailure(Call call, IOException e) {
                // Quando a falha ocorre, chama o método onFailure do callback com a mensagem de erro
                callback.onFailure("Erro na conexão: " + e.getMessage());
            }

            // Método chamado quando a resposta é recebida da API
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Verifica se a resposta foi bem-sucedida (código 2xx)
                if (response.isSuccessful()) {
                    try {
                        // Converte o corpo da resposta (em JSON) para um objeto JSONObject
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        // Obtém os valores de logradouro, bairro e cidade da resposta JSON
                        String logradouro = jsonObject.optString("logradouro");
                        String bairro = jsonObject.optString("bairro");
                        String cidade = jsonObject.optString("localidade");

                        // Chama o método onSuccess do callback passando os valores obtidos
                        callback.onSuccess(logradouro, bairro, cidade);
                    } catch (Exception e) {
                        // Se ocorrer um erro ao processar o JSON, chama onFailure com a mensagem de erro
                        callback.onFailure("Erro ao processar JSON: " + e.getMessage());
                    }
                } else {
                    // Se o CEP não for encontrado ou houver outro problema com a resposta, chama onFailure
                    callback.onFailure("CEP não encontrado");
                }
            }
        });
    }
}
