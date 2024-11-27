// A Activity CadastroCliente2Activity permite que o usuário insira dados complementares de endereço como e-mail, CEP, cidade, rua, número e bairro.
// Ao digitar o CEP, o sistema automaticamente preenche os campos de endereço com as informações correspondentes.
// O sistema valida se todos os campos obrigatórios foram preenchidos antes de permitir o avanço para a próxima tela de cadastro.

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

    // Declaracao dos campos EditText e botoes utilizados na tela
    private EditText editEmail, editCEP, editCidade, editRua, editNumero, editBairro;
    private Button btnContinuar, btnCancelarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente2);

        // Inicializa os componentes da tela com os IDs definidos no layout XML
        inicializarComponentes();

        // Recebe dados da tela anterior
        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        String cpf = intent.getStringExtra("cpf");
        String telefone = intent.getStringExtra("telefone");

        // Adiciona listener para buscar o CEP automaticamente quando o campo for preenchido
        editCEP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Verifica se o campo de CEP tem 8 caracteres (tamanho esperado de um CEP)
                if (s.length() == 8) {
                    buscarEndereco(s.toString()); // Chama a busca de endereço
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Configura o clique do botao "Continuar"
        btnContinuar.setOnClickListener(v -> {
            // Verifica se todos os campos obrigatorios foram preenchidos
            if (editEmail.getText().toString().isEmpty() || editCEP.getText().toString().isEmpty() ||
                    editCidade.getText().toString().isEmpty() || editRua.getText().toString().isEmpty() ||
                    editNumero.getText().toString().isEmpty() || editBairro.getText().toString().isEmpty()) {

                // Exibe uma mensagem de erro se algum campo estiver vazio
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
            nextIntent.putExtra("cidade", editCidade.getText().toString());
            nextIntent.putExtra("rua", editRua.getText().toString());
            nextIntent.putExtra("numero", editNumero.getText().toString());
            nextIntent.putExtra("bairro", editBairro.getText().toString());
            startActivity(nextIntent); // Inicia a próxima tela
        });

        // Configura o clique do botao "Cancelar" para finalizar a atividade atual
        btnCancelarCad.setOnClickListener(v -> finish());
    }

    // Metodo para inicializar os componentes da interface
    private void inicializarComponentes() {
        editEmail = findViewById(R.id.editTextEmailCliente);
        editCEP = findViewById(R.id.editTextCepCliente);
        editCidade = findViewById(R.id.editTextCidadeCliente);
        editRua = findViewById(R.id.editTextRuaCliente);
        editNumero = findViewById(R.id.editTextNumeroCliente);
        editBairro = findViewById(R.id.editTextBairroCliente);
        btnContinuar = findViewById(R.id.btnContinuar2);
        btnCancelarCad = findViewById(R.id.btnCancelarCad2);
    }

    // Chama o servico de busca de CEP e preenche automaticamente os campos de endereco
    private void buscarEndereco(String cep) {
        CepService cepService = new CepService();
        cepService.buscarCep(cep, new CepService.CepCallback() {
            @Override
            public void onSuccess(String logradouro, String bairro, String cidade) {
                // Atualiza os campos de endereço com as informações obtidas do serviço
                runOnUiThread(() -> {
                    editRua.setText(logradouro);
                    editBairro.setText(bairro);
                    editCidade.setText(cidade);
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                // Exibe uma mensagem de erro se a busca do CEP falhar
                runOnUiThread(() -> {
                    Toast.makeText(CadastroCliente2Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
