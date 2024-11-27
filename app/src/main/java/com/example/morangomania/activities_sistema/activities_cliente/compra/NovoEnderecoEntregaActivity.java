package com.example.morangomania.activities_sistema.activities_cliente.compra;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.services.CepService;
import com.example.morangomania.R;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;

public class NovoEnderecoEntregaActivity extends AppCompatActivity {

    private EditText editCEP,editCidade, editRua, editNumero, editBairro;
    private Button btnCadastrar,btnCancelarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_novo_endereco_entrega);

        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);

        editCEP = findViewById(R.id.editTextCepEnderecoNovo);
        editCidade = findViewById(R.id.editTextCidadeEnderecoNovo);
        editRua = findViewById(R.id.editTextRuaEnderecoNovo);
        editNumero = findViewById(R.id.editTextNumeroEnderecoNovo);
        editBairro = findViewById(R.id.editTextBairroEnderecoNovo);
        btnCadastrar = findViewById(R.id.btnCadastrarEnderecoNovo);
        btnCancelarCad = findViewById(R.id.btnCancelarCadEndereco);

        btnCadastrar.setOnClickListener(v -> {

            Endereco enderecoEntrega = new Endereco();
            enderecoEntrega.setCep(editCEP.getText().toString());
            enderecoEntrega.setCidade(editCidade.getText().toString());
            enderecoEntrega.setBairro(editBairro.getText().toString());
            enderecoEntrega.setRua(editRua.getText().toString());
            enderecoEntrega.setNumero(editNumero.getText().toString());

            Intent nextIntent = new Intent(NovoEnderecoEntregaActivity.this, ResumoPedidoActivity.class);
            nextIntent.putExtra("EnderecoEntrega",enderecoEntrega);
            nextIntent.putExtra("Cliente",cliente);
            nextIntent.putExtra("metodoPagamento", metodoPagamento);
            nextIntent.putExtra("totalCompra", totalCompra);
            startActivity(nextIntent);
        });

        btnCancelarCad.setOnClickListener(v -> {
            finish();
        });

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
                    Toast.makeText(NovoEnderecoEntregaActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}