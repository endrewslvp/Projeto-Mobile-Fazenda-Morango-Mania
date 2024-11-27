// A Activity LoginClienteActivity permite que os clientes façam login no sistema.
// Possui funcionalidades para mostrar/ocultar a senha e redireciona o usuário para a tela inicial ou de cadastro.

package com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login;

// Importações necessárias para esta Activity
import android.content.Intent;  // Usado para navegar entre Activities
import android.os.Bundle;       // Usado para salvar e recuperar o estado da Activity
import android.text.method.HideReturnsTransformationMethod; // Mostra a senha em texto visível
import android.text.method.PasswordTransformationMethod;    // Oculta a senha com asteriscos
import android.view.View;       // Manipulação de elementos visuais
import android.widget.Button;    // Botões da interface
import android.widget.EditText;  // Campo de texto para CPF e senha
import android.widget.ImageButton; // Botão de imagem para mostrar/ocultar senha
import android.widget.Toast;     // Mostra mensagens curtas ao usuário

import androidx.activity.EdgeToEdge;  // Configuração de bordas em telas modernas
import androidx.appcompat.app.AppCompatActivity;  // Base para todas as Activities usando AppCompat

// Importações de classes do projeto
import com.example.morangomania.DAO.ClienteDAO;
import com.example.morangomania.R;
import com.example.morangomania.activities_sistema.activities_cliente.tela_inicial.InicioActivity;
import com.example.morangomania.model.Cliente;

import java.sql.SQLException;  // Exceções SQL para tratamento de erro no login

/**
 * LoginClienteActivity permite que os clientes realizem login no sistema.
 * Inclui funcionalidades para mostrar/ocultar senha e redireciona para o cadastro ou tela inicial após autenticação.
 */
public class LoginClienteActivity extends AppCompatActivity {

    // Declaração dos elementos da interface
    EditText txtCpf, txtSenha;
    Button buttonLogin;
    private boolean isPasswordVisible = false;  // Variável para controlar a visibilidade da senha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Habilita o uso de tela inteira
        setContentView(R.layout.activity_login_cliente);  // Define o layout da Activity

        // Inicializa os componentes da interface
        txtCpf = findViewById(R.id.editTextCPFLogin);
        txtSenha = findViewById(R.id.editTextSenhaLogin);
        buttonLogin = findViewById(R.id.btnEntrarLogin);
        ImageButton togglePasswordButton = findViewById(R.id.btnTogglePassword);

        // Configura o botão para mostrar/ocultar a senha
        togglePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Oculta a senha
                    txtSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    togglePasswordButton.setImageResource(R.drawable.ic_invisivel);  // Ícone de "olho fechado"
                } else {
                    // Mostra a senha
                    txtSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    togglePasswordButton.setImageResource(R.drawable.ic_visivel);  // Ícone de "olho aberto"
                }
                // Move o cursor para o final do texto da senha
                txtSenha.setSelection(txtSenha.length());
                isPasswordVisible = !isPasswordVisible;  // Alterna o estado da visibilidade
            }
        });

        // Configura o botão para navegar para a tela de registro
        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(v -> {
            // Abre a tela de cadastro de cliente
            Intent intent = new Intent(LoginClienteActivity.this, CadastroClienteActivity.class);
            startActivity(intent);
        });
    }

    // Metodo chamado ao tentar realizar o login
    public void login(View v) {
        // Captura os dados de CPF e senha digitados pelo usuário
        String cpf = txtCpf.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();

        // Validação para verificar se os campos não estão vazios
        if (cpf.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return; // Sai do metodo se os campos estiverem vazios
        }

        try {
            // Consulta o cliente no banco de dados usando o DAO
            Cliente cliente = new ClienteDAO().selecionarCliente(cpf, senha);

            if (cliente != null) { // Se o cliente for encontrado (login correto)
                // Redireciona para a tela inicial, passando o objeto Cliente
                Intent in = new Intent(LoginClienteActivity.this, InicioActivity.class);
                in.putExtra("Cliente", cliente);
                startActivity(in);
            } else {
                // Exibe mensagem de erro se CPF ou senha estiver incorreto
                Toast.makeText(this, "CPF ou senha incorreto(s). Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            // Trata erros relacionados ao banco de dados
            Toast.makeText(this, "Erro ao conectar ao banco de dados. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            e.printStackTrace(); // Imprime o erro no log para depuração
        }
    }

}
