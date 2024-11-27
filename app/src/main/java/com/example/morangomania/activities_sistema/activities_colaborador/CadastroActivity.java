package com.example.morangomania.activities_sistema.activities_colaborador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.R;
import com.example.morangomania.activities_sistema.MainActivity;

public class CadastroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button buttonNavigate = findViewById(R.id.btnContinuar1);
        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(in);
            }
        });

        Button buttonNavigate1 = findViewById(R.id.btnCancelarCad1);
        buttonNavigate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in1 = new Intent(CadastroActivity.this, MainActivity.class);
                startActivity(in1);
            }
        });


    }
}
