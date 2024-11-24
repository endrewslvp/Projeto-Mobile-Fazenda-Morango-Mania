package com.example.morangomania.controles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.morangomania.DAO.CadastroDAO;
import com.example.morangomania.R;
import com.example.morangomania.messages.Messages;
import com.example.morangomania.model.Cadastro;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    EditText txtCpf, txtSenha;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCpf = findViewById(R.id.editTextCPFLogin);
        txtSenha = findViewById(R.id.editTextSenhaLogin);
        buttonLogin = findViewById(R.id.btnEntrarLogin);
    }

    public void login (View v) throws SQLException {

        String cpf = txtCpf.getText().toString();
        String senha = txtSenha.getText().toString();

        Cadastro usuario = new CadastroDAO().selecionarUsuario(cpf,senha);

        if (usuario != null){
            Intent in = new Intent(LoginActivity.this, PainelcontroleActivity.class);
            startActivity(in);
        }else{
            Messages.showMessageBox(this,"ERRO","CPF ou senha incorreto(s). Tente novamente.");
        }
    }


}


