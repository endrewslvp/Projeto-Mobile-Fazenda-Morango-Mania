package com.example.morangomania;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroClienteActivity extends AppCompatActivity {

    private EditText editNome, editCPF, editTelefone;
    private Button btnContinuar,btnCancelarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        // Associar campos
        editNome = findViewById(R.id.editTextNomeCliente);
        editCPF = findViewById(R.id.editTextCPFCliente);
        editTelefone = findViewById(R.id.editTextTelefoneCliente);
        btnContinuar = findViewById(R.id.btnContinuar1);
        btnCancelarCad = findViewById(R.id.btnCancelarCad1);

        btnContinuar.setOnClickListener(v -> {
            // Captura os dados e envia para a próxima tela
            Intent intent = new Intent(CadastroClienteActivity.this, CadastroCliente2Activity.class);
            intent.putExtra("nome", editNome.getText().toString());
            intent.putExtra("cpf", editCPF.getText().toString());
            intent.putExtra("telefone", editTelefone.getText().toString());
            startActivity(intent);
        });
        btnCancelarCad.setOnClickListener(v -> {
            finish();
        });

    }
}