package com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.services.CepService;
import com.example.morangomania.R;

public class CadastroCliente2Activity extends AppCompatActivity {

    private EditText editEmail, editCEP,editCidade, editRua, editNumero, editBairro;
    private Button btnContinuar,btnCancelarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente2);

        // Associar campos
        editEmail = findViewById(R.id.editTextEmailCliente);
        editCEP = findViewById(R.id.editTextCepCliente);
        editCidade = findViewById(R.id.editTextCidadeCliente);
        editRua = findViewById(R.id.editTextRuaCliente);
        editNumero = findViewById(R.id.editTextNumeroCliente);
        editBairro = findViewById(R.id.editTextBairroCliente);
        btnContinuar = findViewById(R.id.btnContinuar2);
        btnCancelarCad = findViewById(R.id.btnCancelarCad2);

        // Recebe dados da tela anterior
        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        String cpf = intent.getStringExtra("cpf");
        String telefone = intent.getStringExtra("telefone");

        // Adiciona listener para buscar o CEP automaticamente
        editCEP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 8) { // CEP tem 8 dígitos
                    buscarEndereco(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnContinuar.setOnClickListener(v -> {

            if (editEmail.getText().toString().isEmpty() || editCEP.getText().toString().isEmpty() ||
                    editCidade.getText().toString().isEmpty() || editRua.getText().toString().isEmpty() ||
                    editNumero.getText().toString().isEmpty() || editBairro.getText().toString().isEmpty()) {

                Toast.makeText(CadastroCliente2Activity.this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
                return; // Não continua a execução se algum campo estiver vazio
            }

            // Captura dados e envia para a próxima tela
            Intent nextIntent = new Intent(CadastroCliente2Activity.this, CadastroCliente3Activity.class);
            nextIntent.putExtra("nome", nome);
            nextIntent.putExtra("cpf", cpf);
            nextIntent.putExtra("telefone", telefone);
            nextIntent.putExtra("email", editEmail.getText().toString());
            nextIntent.putExtra("cep", editCEP.getText().toString());
            nextIntent.putExtra("cidade",editCidade.getText().toString());
            nextIntent.putExtra("rua", editRua.getText().toString());
            nextIntent.putExtra("numero", editNumero.getText().toString());
            nextIntent.putExtra("bairro", editBairro.getText().toString());
            startActivity(nextIntent);
        });

        btnCancelarCad.setOnClickListener(v -> {
            finish();
        });
    }
    // Chama o serviço de busca de CEP
    private void buscarEndereco(String cep) {
        CepService cepService = new CepService();
        cepService.buscarCep(cep, new CepService.CepCallback() {
            @Override
            public void onSuccess(String logradouro, String bairro, String cidade) {
                runOnUiThread(() -> {
                    editRua.setText(logradouro);
                    editBairro.setText(bairro);
                    editCidade.setText(cidade);
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() -> {
                    Toast.makeText(CadastroCliente2Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

}