package com.example.morangomania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.morangomania.DAO.CadastroDAO;
import com.example.morangomania.DAO.ClienteDAO;
import com.example.morangomania.controles.InicioActivity;
import com.example.morangomania.controles.LoginActivity;
import com.example.morangomania.controles.PainelcontroleActivity;
import com.example.morangomania.messages.Messages;
import com.example.morangomania.model.Cadastro;
import com.example.morangomania.model.Cliente;

import java.sql.SQLException;

public class LoginClienteActivity extends AppCompatActivity {

    EditText txtCpf, txtSenha;
    Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_cliente);

        txtCpf = findViewById(R.id.editTextCPFLogin);
        txtSenha = findViewById(R.id.editTextSenhaLogin);
        buttonLogin = findViewById(R.id.btnEntrarLogin);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(v -> {
            // Captura os dados e envia para a próxima tela
            Intent intent = new Intent(LoginClienteActivity.this, CadastroClienteActivity.class);
            startActivity(intent);
        });
    }

    public void login (View v) throws SQLException {

        String cpf = txtCpf.getText().toString();
        String senha = txtSenha.getText().toString();

        Cliente cliente = new ClienteDAO().selecionarCliente(cpf,senha);

        if (cliente != null){
            Intent in = new Intent(LoginClienteActivity.this, InicioActivity.class);
            in.putExtra("Cliente",cliente);
            startActivity(in);
        }else{
            Toast.makeText(this, "CPF ou senha incorreto(s). Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }
}