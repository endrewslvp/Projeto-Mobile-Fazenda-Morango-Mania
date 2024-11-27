// Classe que gerencia a tela de perfil do usuário, permitindo a visualização e atualização dos dados
// A tela exibe campos para nome, telefone, e-mail, CPF e endereço do usuário, além de um botão para salvar as alterações

package com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.DAO.ClienteDAO;
import com.example.morangomania.R;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;

import java.sql.SQLException;

public class UserProfileActivity extends AppCompatActivity {

    // Declaração dos EditTexts e botões que serão utilizados na tela
    private EditText editTextName, editTextPhone, editTextEmail, editTextCpf, editTextCep,
            editTextStreet, editTextNumber, editTextNeighborhood, editTextCity;
    private Button buttonSave, btnVoltarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Inicializa os componentes da interface com os IDs do layout XML
        inicializarComponentes();

        // Recupera o objeto Cliente passado pela Intent e carrega os dados na tela
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        carregarDadosUsuario(cliente);

        // Configura a ação do botão "Salvar"
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Valida se o CPF inserido já existe no banco de dados
                verificarECadastrarCliente(cliente);
            }
        });

        // Configura o botão "Voltar" para finalizar a atividade atual
        btnVoltarUsuario.setOnClickListener(v -> finish());
    }

    // Método para inicializar os componentes da interface
    private void inicializarComponentes() {
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextCep = findViewById(R.id.editTextCep);
        editTextStreet = findViewById(R.id.editTextStreet);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextNeighborhood = findViewById(R.id.editTextNeighborhood);
        editTextCity = findViewById(R.id.editTextCity);
        buttonSave = findViewById(R.id.buttonSave);
        btnVoltarUsuario = findViewById(R.id.btnVoltarUsuario);
    }

    // Método para carregar os dados do cliente nos campos de texto
    private void carregarDadosUsuario(Cliente cliente) {
        if (cliente != null) {
            // Preenche os campos com os dados do cliente
            editTextName.setText(cliente.getNome());
            editTextPhone.setText(cliente.getTelefone());
            editTextEmail.setText(cliente.getEmail());
            editTextCpf.setText(cliente.getCpf());
            editTextCep.setText(cliente.getEndereco().getCep());
            editTextStreet.setText(cliente.getEndereco().getRua());
            editTextNumber.setText(cliente.getEndereco().getNumero());
            editTextNeighborhood.setText(cliente.getEndereco().getBairro());
            editTextCity.setText(cliente.getEndereco().getCidade());
        } else {
            // Exibe mensagem caso o objeto cliente seja nulo
            Toast.makeText(this, "Erro ao carregar os dados do usuario", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para verificar se o CPF já existe e atualizar os dados do cliente
    private void verificarECadastrarCliente(Cliente cliente) {
        try {
            // Busca um cliente no banco de dados pelo CPF inserido
            Cliente clienteCPF = new ClienteDAO().selecionarClientePorCPF(editTextCpf.getText().toString());

            // Verifica se o CPF já existe ou se é o mesmo do cliente atual
            if (clienteCPF == null || editTextCpf.getText().toString().equals(cliente.getCpf())) {
                // Atualiza os dados do cliente com as informações inseridas nos campos
                cliente.setNome(editTextName.getText().toString());
                cliente.setTelefone(editTextPhone.getText().toString());
                cliente.setEmail(editTextEmail.getText().toString());
                cliente.setCpf(editTextCpf.getText().toString());

                // Cria um novo objeto Endereco e define os dados inseridos
                Endereco endereco = new Endereco();
                endereco.setCep(editTextCep.getText().toString());
                endereco.setRua(editTextStreet.getText().toString());
                endereco.setNumero(editTextNumber.getText().toString());
                endereco.setBairro(editTextNeighborhood.getText().toString());
                endereco.setCidade(editTextCity.getText().toString());

                // Associa o endereço ao cliente
                cliente.setEndereco(endereco);

                // Atualiza o cliente no banco de dados
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.atualizarUsuario(cliente);

                // Exibe mensagem de sucesso
                Toast.makeText(UserProfileActivity.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();
            } else {
                // Exibe mensagem de erro caso o CPF já esteja cadastrado em outro usuário
                Toast.makeText(UserProfileActivity.this, "CPF já pertence a outra pessoa.", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            // Trata erros de banco de dados
            Toast.makeText(UserProfileActivity.this, "Erro ao atualizar dados.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
