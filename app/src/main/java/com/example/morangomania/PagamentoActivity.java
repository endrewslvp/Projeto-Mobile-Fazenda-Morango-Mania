package com.example.morangomania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PagamentoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagamento);

        Button buttonNavigate = findViewById(R.id.btn_cancelar_pagamento);
        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PagamentoActivity.this, ListaProdutosActivity.class);
                startActivity(in);
            }
        });

        Button buttonNavigate1 = findViewById(R.id.btn_pagar_pagamento);
        buttonNavigate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in1 = new Intent(PagamentoActivity.this, QrcodeActivity.class);
                startActivity(in1);
            }
        });

    }
}