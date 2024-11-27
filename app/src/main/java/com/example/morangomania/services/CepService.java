package com.example.morangomania.services;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

public class CepService {

    private static final String BASE_URL = "https://viacep.com.br/ws/";

    // Interface para o callback
    public interface CepCallback {
        void onSuccess(String logradouro, String bairro, String cidade);
        void onFailure(String errorMessage);
    }

    public void buscarCep(String cep, final CepCallback callback) {
        OkHttpClient client = new OkHttpClient();
        String url = BASE_URL + cep + "/json/";

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Erro na conexão: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String logradouro = jsonObject.optString("logradouro");
                        String bairro = jsonObject.optString("bairro");
                        String cidade = jsonObject.optString("localidade");

                        callback.onSuccess(logradouro, bairro, cidade);
                    } catch (Exception e) {
                        callback.onFailure("Erro ao processar JSON: " + e.getMessage());
                    }
                } else {
                    callback.onFailure("CEP não encontrado");
                }
            }
        });
    }
}
