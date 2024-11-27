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

    private EditText editTextName, editTextPhone, editTextEmail, editTextCpf, editTextCep,
            editTextStreet, editTextNumber, editTextNeighborhood, editTextCity;
    private Button buttonSave,btnVoltarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Inicializando os EditTexts
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextCep = findViewById(R.id.editTextCep);
        editTextStreet = findViewById(R.id.editTextStreet);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextNeighborhood = findViewById(R.id.editTextNeighborhood);
        editTextCity = findViewById(R.id.editTextCity);
        btnVoltarUsuario = findViewById(R.id.btnVoltarUsuario);
        buttonSave = findViewById(R.id.buttonSave);

        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");
        carregarDadosUsuario(cliente);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cliente clienteCPF = null;
                try {
                    clienteCPF = new ClienteDAO().selecionarClientePorCPF(editTextCpf.getText().toString());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (clienteCPF == null || editTextCpf.getText().toString().equals(cliente.getCpf())){

                    cliente.setNome(editTextName.getText().toString());
                    cliente.setTelefone(editTextPhone.getText().toString());
                    cliente.setEmail(editTextEmail.getText().toString());
                    cliente.setCpf(editTextCpf.getText().toString());
                    Endereco endereco = new Endereco();
                    endereco.setCep(editTextCep.getText().toString());
                    endereco.setRua(editTextStreet.getText().toString());
                    endereco.setNumero(editTextNumber.getText().toString());
                    endereco.setBairro(editTextNeighborhood.getText().toString());
                    endereco.setCidade(editTextCity.getText().toString());
                    cliente.setEndereco(endereco);

                    ClienteDAO clienteDAO = new ClienteDAO();
                    try {
                        clienteDAO.atualizarUsuario(cliente);
                        Toast.makeText(UserProfileActivity.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(UserProfileActivity.this, "CPF já pertence a outra pessoa.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVoltarUsuario.setOnClickListener(v -> {finish();});
    }

    private void carregarDadosUsuario(Cliente cliente) {

        if (cliente != null) {
            editTextName.setText(cliente.getNome());
            editTextPhone.setText(cliente.getTelefone());
            editTextEmail.setText(cliente.getEmail());
            editTextCpf.setText(cliente.getCpf());
            editTextCep.setText(cliente.getCep());
            editTextStreet.setText(cliente.getRua());
            editTextNumber.setText(cliente.getNumero());
            editTextNeighborhood.setText(cliente.getBairro());
            editTextCity.setText(cliente.getCidade());
        } else {
            Toast.makeText(this, "Erro ao carregar os dados do usuário", Toast.LENGTH_SHORT).show();
        }
    }
}
