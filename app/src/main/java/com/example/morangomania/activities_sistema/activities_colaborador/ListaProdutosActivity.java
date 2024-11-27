package com.example.morangomania.activities_sistema.activities_colaborador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.R;
import com.example.morangomania.activities_sistema.activities_cliente.compra.PagamentoActivity;

public class ListaProdutosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaproduto);


        Button buttonNavigate = findViewById(R.id.btn_cancelar_pagamento);
        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ListaProdutosActivity.this, PagamentoActivity.class);
                startActivity(in);
            }
        });

    }
}
