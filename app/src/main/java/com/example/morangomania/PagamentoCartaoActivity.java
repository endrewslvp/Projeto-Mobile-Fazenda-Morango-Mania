package com.example.morangomania;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PagamentoCartaoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_cartao);

        EditText numeroCartao = findViewById(R.id.etNumeroCartao);
        EditText validadeCartao = findViewById(R.id.etValidadeCartao);
        EditText cvcCartao = findViewById(R.id.etCvcCartao);
        Button finalizarPagamento = findViewById(R.id.btnFinalizarPagamento);

        finalizarPagamento.setOnClickListener(v -> {
            // Adicionar validação e lógica de processamento
            Toast.makeText(this, "Pagamento processado!", Toast.LENGTH_SHORT).show();
        });
    }
}
