// A Activity CadastroClienteActivity permite o cadastro de um novo cliente no sistema.
// O usuário insere dados como nome, CPF e telefone, e o sistema verifica se o CPF já existe no banco.
// Caso o CPF não exista, os dados são enviados para uma segunda tela de cadastro. Se o CPF já estiver cadastrado, uma mensagem de erro é exibida.

package com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.DAO.ClienteDAO;
import com.example.morangomania.R;
import com.example.morangomania.model.Cliente;

import java.sql.SQLException;

public class CadastroClienteActivity extends AppCompatActivity {

    // Declaracao dos campos EditText e botoes utilizados na tela
    private EditText editNome, editCPF, editTelefone;
    private Button btnContinuar, btnCancelarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        // Inicializa os componentes da tela com os IDs definidos no layout XML
        inicializarComponentes();

        // Configura o clique do botao "Continuar"
        btnContinuar.setOnClickListener(v -> {
            // Verifica se todos os campos obrigatorios foram preenchidos
            if (editNome.getText().toString().isEmpty() || editCPF.getText().toString().isEmpty() || editTelefone.getText().toString().isEmpty()) {
                // Exibe uma mensagem de erro se algum campo estiver vazio
                Toast.makeText(CadastroClienteActivity.this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
                return; // Encerra a execucao e nao permite continuar se algum campo estiver vazio
            }

            // Verifica se o CPF informado ja existe no banco de dados
            Cliente clienteCPF = null;
            try {
                clienteCPF = new ClienteDAO().selecionarClientePorCPF(editCPF.getText().toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Se o CPF nao existir, envia os dados para a próxima tela
            if (clienteCPF == null){
                Intent intent = new Intent(CadastroClienteActivity.this, CadastroCliente2Activity.class);
                intent.putExtra("nome", editNome.getText().toString());
                intent.putExtra("cpf", editCPF.getText().toString());
                intent.putExtra("telefone", editTelefone.getText().toString());
                startActivity(intent);
            }else{
                // Caso o CPF ja exista, exibe uma mensagem de erro
                Toast.makeText(CadastroClienteActivity.this, "CPF já pertence a outra pessoa.", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura o clique do botao "Cancelar" para finalizar a atividade atual
        btnCancelarCad.setOnClickListener(v -> finish());
    }

    // Metodo para inicializar os componentes da interface
    private void inicializarComponentes() {
        editNome = findViewById(R.id.editTextNomeCliente);
        editCPF = findViewById(R.id.editTextCPFCliente);
        editTelefone = findViewById(R.id.editTextTelefoneCliente);
        btnContinuar = findViewById(R.id.btnContinuar1);
        btnCancelarCad = findViewById(R.id.btnCancelarCad1);
    }
}
