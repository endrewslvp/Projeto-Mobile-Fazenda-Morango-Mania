package com.example.morangomania.activities_sistema;

// Importações necessárias para a funcionalidade desta classe
import android.content.Intent;  // Utilizado para iniciar novas atividades
import android.os.Bundle;       // Utilizado para recuperar e salvar estados da Activity
import android.view.View;       // Utilizado para manipulação de Views
import android.widget.Button;   // Classe para botões na interface

import androidx.appcompat.app.AppCompatActivity;  // Base para todas as Activities que usam a AppCompat

// Importações das Activities de login para cliente e colaborador
import com.example.morangomania.R;
import com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login.LoginClienteActivity;
import com.example.morangomania.activities_sistema.activities_colaborador.LoginActivity;

/**
 * MainActivity é a tela principal do aplicativo.
 * Esta tela fornece botões para redirecionar o usuário para as telas de login:
 * uma para clientes e outra para colaboradores.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout da Activity a partir do arquivo XML de layout
        setContentView(R.layout.activity_main);

        // Configura o botão para login de colaboradores
        Button btnLogin = findViewById(R.id.btnLogin); // Encontra o botão pelo ID no layout
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cria uma intenção para abrir a tela de login de colaborador
                Intent in = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(in); // Inicia a nova Activity
            }
        });

        // Configura o botão para login de clientes
        Button btnLoginClientes = findViewById(R.id.btnLoginClientes); // Encontra o botão pelo ID no layout
        btnLoginClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cria uma intenção para abrir a tela de login de cliente
                Intent in = new Intent(MainActivity.this, LoginClienteActivity.class);
                startActivity(in); // Inicia a nova Activity
            }
        });
    }
}
