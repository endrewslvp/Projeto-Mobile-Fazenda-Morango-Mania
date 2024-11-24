package com.example.morangomania;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CadastroCliente2Activity extends AppCompatActivity {

    private EditText editEmail, editCEP, editRua, editNumero, editBairro;
    private Button btnContinuar,btnCancelarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente2);

        // Associar campos
        editEmail = findViewById(R.id.editTextSenha);
        editCEP = findViewById(R.id.editTextConfirmacaoSenha);
        editRua = findViewById(R.id.editTextRua);
        editNumero = findViewById(R.id.editTextNumero);
        editBairro = findViewById(R.id.editTextBairro);
        btnContinuar = findViewById(R.id.btnCadastrarCliente);
        btnCancelarCad = findViewById(R.id.btnCancelarCad);

        // Recebe dados da tela anterior
        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        String cpf = intent.getStringExtra("cpf");
        String telefone = intent.getStringExtra("telefone");

        btnContinuar.setOnClickListener(v -> {
            // Captura dados e envia para a próxima tela
            Intent nextIntent = new Intent(CadastroCliente2Activity.this, CadastroCliente3Activity.class);
            nextIntent.putExtra("nome", nome);
            nextIntent.putExtra("cpf", cpf);
            nextIntent.putExtra("telefone", telefone);
            nextIntent.putExtra("email", editEmail.getText().toString());
            nextIntent.putExtra("cep", editCEP.getText().toString());
            nextIntent.putExtra("rua", editRua.getText().toString());
            nextIntent.putExtra("numero", editNumero.getText().toString());
            nextIntent.putExtra("bairro", editBairro.getText().toString());
            startActivity(nextIntent);
        });

        btnCancelarCad.setOnClickListener(v -> {
            finish();
        });
    }
}