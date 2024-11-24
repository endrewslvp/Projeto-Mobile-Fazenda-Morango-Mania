package com.example.morangomania.controles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.CadastroClienteActivity;
import com.example.morangomania.LoginClienteActivity;
import com.example.morangomania.R;


public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button btnLogin = findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(in);
                }
            });

            Button btnLoginClientes = findViewById(R.id.btnLoginClientes);
            btnLoginClientes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(MainActivity.this, LoginClienteActivity.class);
                    startActivity(in);
                }
            });
        }


    }