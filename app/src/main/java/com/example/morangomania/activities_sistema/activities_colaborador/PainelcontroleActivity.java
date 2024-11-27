package com.example.morangomania.activities_sistema.activities_colaborador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.R;
import com.example.morangomania.activities_sistema.activities_cliente.compra.CompraActivity;

public class PainelcontroleActivity extends AppCompatActivity {

    Button buttonProducao, buttonVendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painelcontrole);

        buttonProducao = findViewById(R.id.btnAcessarProducao);
        buttonVendas = findViewById(R.id.btnCancelarCad1);

        buttonProducao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PainelcontroleActivity.this, ProdutoActivity.class);
                startActivity(in);
            }
        });

        buttonVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PainelcontroleActivity.this, CompraActivity.class);
                startActivity(in);
            }
        });
    }
}
