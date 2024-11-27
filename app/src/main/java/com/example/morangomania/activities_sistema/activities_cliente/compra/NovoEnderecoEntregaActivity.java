// A Activity NovoEnderecoEntregaActivity permite que o cliente cadastre um novo endereço de entrega.
// Ela inclui a funcionalidade de buscar o endereço automaticamente ao inserir o CEP e também confirma o cadastro do endereço.

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

    // Declaração dos componentes da interface
    private EditText editCEP, editCidade, editRua, editNumero, editBairro;
    private Button btnCadastrar, btnCancelarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_novo_endereco_entrega);

        // Recebe os dados do cliente, método de pagamento e total da compra
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);

        // Inicializa os componentes da tela
        editCEP = findViewById(R.id.editTextCepEnderecoNovo);
        editCidade = findViewById(R.id.editTextCidadeEnderecoNovo);
        editRua = findViewById(R.id.editTextRuaEnderecoNovo);
        editNumero = findViewById(R.id.editTextNumeroEnderecoNovo);
        editBairro = findViewById(R.id.editTextBairroEnderecoNovo);
        btnCadastrar = findViewById(R.id.btnCadastrarEnderecoNovo);
        btnCancelarCad = findViewById(R.id.btnCancelarCadEndereco);

        // Ação do botão para cadastrar o novo endereço
        btnCadastrar.setOnClickListener(v -> {

            // Verifica se todos os campos estão preenchidos
            if (verificarCamposPreenchidos()) {
                Endereco enderecoEntrega = new Endereco();
                enderecoEntrega.setCep(editCEP.getText().toString());
                enderecoEntrega.setCidade(editCidade.getText().toString());
                enderecoEntrega.setBairro(editBairro.getText().toString());
                enderecoEntrega.setRua(editRua.getText().toString());
                enderecoEntrega.setNumero(editNumero.getText().toString());

                // Passa os dados do endereço e o cliente para a próxima Activity (ResumoPedidoActivity)
                Intent nextIntent = new Intent(NovoEnderecoEntregaActivity.this, ResumoPedidoActivity.class);
                nextIntent.putExtra("EnderecoEntrega", enderecoEntrega);
                nextIntent.putExtra("Cliente", cliente);
                nextIntent.putExtra("metodoPagamento", metodoPagamento);
                nextIntent.putExtra("totalCompra", totalCompra);
                startActivity(nextIntent);
            } else {
                // Exibe uma mensagem de erro se algum campo estiver vazio
                Toast.makeText(NovoEnderecoEntregaActivity.this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
            }
        });

        // Ação do botão para cancelar o cadastro e voltar à tela anterior
        btnCancelarCad.setOnClickListener(v -> finish());

        // Adiciona listener para buscar automaticamente o endereço ao digitar o CEP
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

    // Método que chama o serviço de busca de endereço pelo CEP
    private void buscarEndereco(String cep) {
        CepService cepService = new CepService();
        cepService.buscarCep(cep, new CepService.CepCallback() {
            @Override
            public void onSuccess(String logradouro, String bairro, String cidade) {
                runOnUiThread(() -> {
                    // Preenche automaticamente os campos de rua, bairro e cidade
                    editRua.setText(logradouro);
                    editBairro.setText(bairro);
                    editCidade.setText(cidade);
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() -> {
                    // Exibe uma mensagem de erro caso não consiga buscar o endereço
                    Toast.makeText(NovoEnderecoEntregaActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // Verifica se todos os campos estão preenchidos
    private boolean verificarCamposPreenchidos() {
        return !editCEP.getText().toString().isEmpty() &&
                !editRua.getText().toString().isEmpty() &&
                !editBairro.getText().toString().isEmpty() &&
                !editCidade.getText().toString().isEmpty() &&
                !editNumero.getText().toString().isEmpty();
    }
}
