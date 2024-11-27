package com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.DAO.ClienteDAO;
import com.example.morangomania.R;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;

import java.sql.SQLException;

public class CadastroCliente3Activity extends AppCompatActivity {

    private EditText editSenha, editConfirmarSenha;
    private Button btnCadastrar,btnCancelarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente3);

        // Associar campos
        editSenha = findViewById(R.id.editTextSenha);
        editConfirmarSenha = findViewById(R.id.editTextConfirmacaoSenha);
        btnCadastrar = findViewById(R.id.btnCadastrarCliente);
        btnCancelarCad = findViewById(R.id.btnCancelarCad3);

        // Recebe dados das telas anteriores
        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        String cpf = intent.getStringExtra("cpf");
        String telefone = intent.getStringExtra("telefone");
        String email = intent.getStringExtra("email");
        String cep = intent.getStringExtra("cep");
        String cidade = intent.getStringExtra("cidade");
        String rua = intent.getStringExtra("rua");
        String numero = intent.getStringExtra("numero");
        String bairro = intent.getStringExtra("bairro");

        btnCadastrar.setOnClickListener(v -> {

            // Verificar se todos os campos estão preenchidos
            if (editSenha.getText().toString().isEmpty() || editConfirmarSenha.getText().toString().isEmpty()) {
                Toast.makeText(CadastroCliente3Activity.this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
                return; // Não continua a execução se algum campo estiver vazio
            }

            String senha = editSenha.getText().toString();
            String confirmarSenha = editConfirmarSenha.getText().toString();

            if (senha.equals(confirmarSenha)) {
                // Criar objeto cliente
                Cliente cliente = new Cliente();
                Endereco endereco = new Endereco();
                cliente.setNome(nome);
                cliente.setTelefone(telefone);
                cliente.setEmail(email);
                cliente.setCpf(cpf);
                endereco.setCep(cep);
                endereco.setCidade(cidade);
                endereco.setRua(rua);
                endereco.setNumero(numero);
                endereco.setBairro(bairro);
                cliente.setEndereco(endereco);
                cliente.setSenha(senha);

                // Salvar no banco de dados
                ClienteDAO clienteDAO = new ClienteDAO();
                int resultado = 0;
                try {
                    resultado = clienteDAO.inserirCliente(cliente);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (resultado > 0) {
                    Toast.makeText(CadastroCliente3Activity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(CadastroCliente3Activity.this, LoginClienteActivity.class);
                    startActivity(in);
                } else {
                    Toast.makeText(CadastroCliente3Activity.this, "Erro ao cadastrar cliente.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CadastroCliente3Activity.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelarCad.setOnClickListener(v -> {
            finish();
        });
    }
}