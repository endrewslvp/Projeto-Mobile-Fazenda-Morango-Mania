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

    private EditText editNome, editCPF, editTelefone;
    private Button btnContinuar,btnCancelarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        // Associar campos
        editNome = findViewById(R.id.editTextNomeCliente);
        editCPF = findViewById(R.id.editTextCPFCliente);
        editTelefone = findViewById(R.id.editTextTelefoneCliente);
        btnContinuar = findViewById(R.id.btnContinuar1);
        btnCancelarCad = findViewById(R.id.btnCancelarCad1);

        btnContinuar.setOnClickListener(v -> {

            if (editNome.getText().toString().isEmpty() || editCPF.getText().toString().isEmpty() || editTelefone.getText().toString().isEmpty()) {
                Toast.makeText(CadastroClienteActivity.this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
                return; // Não continua a execução se algum campo estiver vazio
            }

            Cliente clienteCPF = null;
            try {
                clienteCPF = new ClienteDAO().selecionarClientePorCPF(editCPF.getText().toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (clienteCPF == null){
                // Captura os dados e envia para a próxima tela
                Intent intent = new Intent(CadastroClienteActivity.this, CadastroCliente2Activity.class);
                intent.putExtra("nome", editNome.getText().toString());
                intent.putExtra("cpf", editCPF.getText().toString());
                intent.putExtra("telefone", editTelefone.getText().toString());
                startActivity(intent);
            }else{
                Toast.makeText(CadastroClienteActivity.this, "CPF já pertence a outra pessoa.", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancelarCad.setOnClickListener(v -> {
            finish();
        });
    }
}