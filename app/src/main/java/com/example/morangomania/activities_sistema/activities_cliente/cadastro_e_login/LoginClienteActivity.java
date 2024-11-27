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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.DAO.ClienteDAO;
import com.example.morangomania.R;
import com.example.morangomania.activities_sistema.activities_cliente.tela_inicial.InicioActivity;
import com.example.morangomania.model.Cliente;

import java.sql.SQLException;

public class LoginClienteActivity extends AppCompatActivity {

    EditText txtCpf, txtSenha;
    Button buttonLogin;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_cliente);

        txtCpf = findViewById(R.id.editTextCPFLogin);
        txtSenha = findViewById(R.id.editTextSenhaLogin);
        buttonLogin = findViewById(R.id.btnEntrarLogin);
        ImageButton togglePasswordButton = findViewById(R.id.btnTogglePassword);

        togglePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Oculta a senha
                    txtSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    togglePasswordButton.setImageResource(R.drawable.ic_invisivel);
                } else {
                    // Mostra a senha
                    txtSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    togglePasswordButton.setImageResource(R.drawable.ic_visivel);
                }
                // Move o cursor para o final da senha
                txtSenha.setSelection(txtSenha.length());
                isPasswordVisible = !isPasswordVisible;
            }
        });


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