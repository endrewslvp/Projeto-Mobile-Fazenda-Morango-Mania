// A Activity CadastroCliente3Activity permite que o usuário defina uma senha e confirme a senha para concluir o cadastro.
// O sistema verifica se as senhas coincidem e, caso positivo, salva os dados do cliente no banco de dados.
// O usuário pode alternar a visibilidade da senha ao clicar no ícone "olho" para mostrar ou ocultar a senha digitada.

package com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.DAO.ClienteDAO;
import com.example.morangomania.R;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;

import java.sql.SQLException;

public class CadastroCliente3Activity extends AppCompatActivity {

    private EditText editSenha, editConfirmarSenha;
    private Button btnCadastrar, btnCancelarCad;
    private ImageButton togglePasswordButton;
    private boolean isPasswordVisible = false; // Variável para controlar a visibilidade da senha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente3);

        // Inicializa os componentes da interface
        inicializarComponentes();

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

        // Ação ao clicar no botão Cadastrar
        btnCadastrar.setOnClickListener(v -> {
            // Verifica se os campos de senha e confirmação estão preenchidos
            if (editSenha.getText().toString().isEmpty() || editConfirmarSenha.getText().toString().isEmpty()) {
                Toast.makeText(CadastroCliente3Activity.this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
                return; // Interrompe se algum campo estiver vazio
            }

            String senha = editSenha.getText().toString();
            String confirmarSenha = editConfirmarSenha.getText().toString();

            // Verifica se as senhas coincidem
            if (senha.equals(confirmarSenha)) {
                // Criação do objeto Cliente e Endereco
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

                // Tenta salvar o cliente no banco de dados
                ClienteDAO clienteDAO = new ClienteDAO();
                int resultado = 0;
                try {
                    resultado = clienteDAO.inserirCliente(cliente);
                } catch (SQLException e) {
                    throw new RuntimeException(e); // Lança exceção caso ocorra erro no banco
                }

                // Feedback ao usuário
                if (resultado > 0) {
                    Toast.makeText(CadastroCliente3Activity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(CadastroCliente3Activity.this, LoginClienteActivity.class);
                    startActivity(in); // Redireciona para a tela de login
                } else {
                    Toast.makeText(CadastroCliente3Activity.this, "Erro ao cadastrar cliente.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CadastroCliente3Activity.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
            }
        });

        // Ação ao clicar no botão Cancelar
        btnCancelarCad.setOnClickListener(v -> finish());

        // Configura o botão para mostrar/ocultar a senha
        togglePasswordButton.setOnClickListener(v -> {
            if (isPasswordVisible) {
                // Oculta a senha
                editSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                togglePasswordButton.setImageResource(R.drawable.ic_invisivel);  // Ícone de "olho fechado"
            } else {
                // Mostra a senha
                editSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                togglePasswordButton.setImageResource(R.drawable.ic_visivel);  // Ícone de "olho aberto"
            }
            // Move o cursor para o final do texto da senha
            editSenha.setSelection(editSenha.length());
            isPasswordVisible = !isPasswordVisible;  // Alterna o estado da visibilidade
        });
    }

    // Metodo para inicializar os componentes da interface
    private void inicializarComponentes() {
        editSenha = findViewById(R.id.editTextSenha);
        editConfirmarSenha = findViewById(R.id.editTextConfirmacaoSenha);
        btnCadastrar = findViewById(R.id.btnCadastrarCliente);
        btnCancelarCad = findViewById(R.id.btnCancelarCad3);
        togglePasswordButton = findViewById(R.id.btnTogglePasswordCliente); // Imagem do botão de visibilidade da senha
    }
}
